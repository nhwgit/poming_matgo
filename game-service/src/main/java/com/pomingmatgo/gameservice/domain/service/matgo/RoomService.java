package com.pomingmatgo.gameservice.domain.service.matgo;

import com.pomingmatgo.gameservice.domain.GameState;
import com.pomingmatgo.gameservice.domain.repository.GameStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final GameStateRepository gameStateRepository;
    public Mono<Long> joinRoom(long userId, long roomId) {
        return gameStateRepository.findById(roomId)
                .switchIfEmpty(Mono.error(new IllegalStateException("Room does not exist"))) // 방이 없으면 예외
                .flatMap(gameState -> {
                    if (isRoomFull(gameState))
                        return Mono.error(new IllegalStateException("Room is already full")); // 방이 꽉 찼으면 예외
                    if (isUserAlreadyInRoom(gameState, userId))
                        return Mono.error(new IllegalStateException("Player already in the room")); // 이미 입장했으면 예외
                    // 방에 유저를 추가하고 저장
                    return updateGameState(gameState, userId)
                            .thenReturn(isRoomFull(gameState)? gameState.getRoomId() : 0L);
                });
    }

    private boolean isRoomFull(GameState gameState) {
        return gameState.getPlayer1Id() != 0 && gameState.getPlayer2Id() != 0;
    }

    private boolean isUserAlreadyInRoom(GameState gameState, long userId) {
        return userId == gameState.getPlayer1Id() || userId == gameState.getPlayer2Id();
    }

    private Mono<Void> updateGameState(GameState gameState, long userId) {
        if (gameState.getPlayer1Id() == 0) {
            gameState.setPlayer1Id(userId);
        } else if (gameState.getPlayer2Id() == 0) {
            gameState.setPlayer2Id(userId);
        }
        return gameStateRepository.save(gameState).then();
    }

    public Mono<Long> createRoom(long roomId) {
        GameState gameState = new GameState();
        gameState.setRoomId(roomId);
        return gameStateRepository.create(gameState);
    }

}
