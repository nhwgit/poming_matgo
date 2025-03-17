# 1:1 고스톱 게임

최신 소스코드: game-service branch

현재 구현된 내용(백엔드만 구현, 프론트엔드 향후 구현)

인증 서비스
- OAuth2 서버
- jwt

대기실&유저 서비스
- 회원가입(이메일 인증 기반)
- 로그인(인증서버와 연동)
- todo: 친구추가
- 기술스택: spring boot mvc, jpa, redis

게임 서비스
- 게임로직 구현중(점수계산, 기본로직 구현 거의 운료)
- todo: 흔들기/뻑등 특수로직 구현, 점수에 따른 고/스톱 기능 구현, 보너스점수(피박 등) 구현
- todo: 보안기능 추가
- 기술스택: spring webflux, redis, WebSocket

todo: spring cloud 또는 aws로 서버 관리 예정
todo: 배포

todo: 로깅 서비스
- 게임 리플레이 등
