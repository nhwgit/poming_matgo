package com.pomingmatgo.gameservice.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pomingmatgo.gameservice.global.session.RoomSessionData;
import com.pomingmatgo.gameservice.global.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class MessageSender {
    private final ObjectMapper objectMapper;
    private final SessionManager sessionManager;
    public <T> Mono<Void> sendMessageToSession(WebSocketSession session, WebSocketResDto<T> response) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(response);
            WebSocketMessage webSocketMessage = session.textMessage(jsonMessage);
            return session.send(Mono.just(webSocketMessage));
        } catch (Exception e) {
            return Mono.empty(); //todo: 예외처리로직  추가해야함
        }
    }

    public <T> Mono<Void> sendMessageToUsers(Collection<WebSocketSession> users, WebSocketResDto<T> response) {
        return Flux.fromIterable(users)
                .flatMap(session -> sendMessageToSession(session, response))
                .then();
    }

    public <T> Mono<Void> sendMessageToAllUser(long roomId, WebSocketResDto<T> response) {
        return Flux.fromIterable(sessionManager.getAllUser(roomId))
                .flatMap(session -> sendMessageToSession(session, response))
                .then();
    }

    public Mono<String> askUser(long roomId, int playerNum, String question) {
        WebSocketSession session = sessionManager.getSession(roomId, playerNum);
        if (session == null) {
            return Mono.error(new IllegalStateException("Session not found"));
        }

        Sinks.One<String> sink = Sinks.one();

        session.send(Mono.just(session.textMessage(question)))
                .doOnError(sink::tryEmitError)
                .subscribe();

        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .next()
                .doOnNext(sink::tryEmitValue)
                .doOnError(sink::tryEmitError)
                .subscribe();

        return sink.asMono();
    }

}
