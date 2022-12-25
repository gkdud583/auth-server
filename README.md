# Smile-gate WinterDev 1인 프로젝트 정하영 - 인증서버 구현
## 기능

| API | METHOD | URI | ROLE |
| --- | --- | --- | --- |
| 회원가입 | POST | /api/v1/users/register | PUBLIC |
| 로그인 | POST | /api/v1/users/login | PUBLIC |
| 로그아웃 | POST | /api/v1/users/logout | USER OR ADMIN |
| 토큰 재발행 | POST | /api/v1/users/token/refresh | USER OR ADMIN |
| 전체 유저 조회 | GET | /api/v1/users | ADMIN |


---
## 아키텍처
![스크린샷 2022-12-25 오후 9 01 44](https://user-images.githubusercontent.com/60775067/209467120-48ba283f-73b8-4836-89d7-8d2b81d6d65e.png)


---

## 인증 과정
![스크린샷 2022-12-25 오후 9 02 21](https://user-images.githubusercontent.com/60775067/209467133-56707907-35f2-4c0d-b68c-d8c653c28c6e.png)


- 사용자는 이메일과 비밀번호 정보를 이용하여 회원가입 후 로그인한다. 서버는 정보 확인 후 access token(인증용, 유효시간 30분), refresh token(access token 재발급용, 유효시간 2시간, 1회 사용 가능) 을 응답으로 보내준다.
- 사용자는 refresh token이 유효하다면 refresh token를 이용해서 access token을 재발행 할 수 있다. refresh token은 일회용이기 때문에 재발행 시 access token, refresh token을 새롭게 생성하여 응답으로 보내준다.
- redis 데이터는 만료시간을 정해 일정 시간 후에 삭제될 수 있도록 한다.

> redis를 사용한 이유:
> 
> - inmemory database: rdb에 비해 접근 속도가 빠름
> - 다양한 자료구조 지원
> - 백업 방법 지원
> - 싱글 스레드로 동작: 동시성 문제 해결

> 
> 
> 
> refresh token 일회용으로 처리한 이유:
> 
> refresh token도 탈취될 가능성이 있기 때문에 한 번만 사용할 수 있도록 처리
> 

---
## 기술 스택

- Java 17
- gradle 7.5.1
- Spring boot 3.0.0
- Spring data jpa
- Spring data redis
- Spring starter web
- Spring security
- mysql 8.0.31
- jwt
- thymeleaf

---
## 🙋‍♀️ 리뷰 받고 싶은 부분 

1.

```java
@PostMapping("login")
  public LoginResponse login(@RequestBody @Valid LoginRequest request,
    HttpServletResponse httpServletResponse) {
    log.info("access : {}", now());
    TokenResponse tokenResponse = userService.login(request);
    setRefreshTokenCookie(httpServletResponse, tokenResponse.getRefreshToken());
    return LoginResponse.from(tokenResponse.getAccessToken());
  }
```

로그인 성공시 access token은 body에, refresh token은 cookie에 내려주도록 했습니다. 쿠키를 전달할 때 httpServletResponse 객체를 사용하게 되는데 이 객체를 Service 단까지 내려주는게 적절하지 않은 것 같아 controller ↔ service 간 토큰 전달을 위해 TokenResponse라는  dto 객체를 사용해서 서비스에서 토큰 정보를 받아오고 컨트롤러에서 httpServletResponse 을 사용해서 쿠키를 담는 식으로 구현했습니다. HttpServletResponse를 서비스까지 내려주는 게 더 나은 방법일까요?

 2. 

controller → service → repository 구조로 개발을 하였는데, 이때 dto 를 생성하는 곳에 dto 클래스를 두었습니다.  (controller에서 생성하는 응답 dto 일 경우 controller.dto 패키지에 위치, service에서 생성하는 응답 dto일 경우 service.dto 패키지에 위치) 이렇게 하는 게 적절한 방법일지 궁금합니다.

---
## 개발하면서 궁금했던 부분

1. 비밀번호 암호화에서 strength (해시 함수 몇 번 돌릴지)를 어떻게 결정하는지 궁금합니다. 보안과 api 응답 속도 사이에 트레이드오프가 존재하는데 이 부분은 어떻게 결정하는 게 좋을까요?
2. 아키텍처 설계 과정이 궁금합니다. 예를 들어 DB 이중화나 캐시 서버 같은 것들은 처음 개발할 때부터 고려하는 것인지 아니면 성능 테스트를 하면서 개선점을 찾고 서버 성능을 올리기 위해 도입을 고민하는 것인지 궁금합니다.
3. 신규 서비스 성능 테스트를 할 때 vuser, TPS는 어떻게 산정하는지 궁금합니다. 

---
## 진행 중이나 아직 완료하지 못한 부분

- pinpoint & ngrinder 또는 jmeter 사용해서 부하 테스트 진행하여 개선점 확인하기
- 인증 → api gateway로 분리
