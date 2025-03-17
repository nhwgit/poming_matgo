# 1:1 고스톱 게임
구현 중 입니다.

최신 소스코드: game-service branch

현재 구현된 내용(백엔드만 구현, 프론트엔드 향후 구현)

인증 서비스
- OAuth2 서버
- jwt

대기실&유저 서비스
- 회원가입(이메일 인증 기반)
- 로그인(인증서버와 연동)
- 기술스택: spring boot mvc, jpa, redis

게임 서비스
- 게임로직 구현중(70% 완료)
- 기술스택: spring webflux, redis, WebSocket

spring cloud 또는 aws로 서버 관리 예정

로깅 서비스 예정
- 게임 리플레이 등
