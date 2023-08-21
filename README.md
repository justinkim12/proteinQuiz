# proteinQuiz


# 프로잭트 소개

프로틴 퀴즈는 단백질 맞추기 퀴즈입니다. 

임의의 닉네임을 배정받고 시작하면 음식 사진을 보고

몇 그람의 단백질이 있는지 맞추는 퀴즈를 합니다.

10문제가 끝나면 자신의 순위를 알 수 있습니다.

# 데이터 출처

https://www.data.go.kr/data/15100070/standard.do

# 퀴즈 링크

https://web-proteinquizfront-20zynm2mlk2a7j4g.sel4.cloudtype.app/

---

# 프로잭트 기술 스택

- Back
    - Spring 2.7.9
    - Java 11
- DB
    - MariaDB 10.5
- Front
    - React
        - npm 8.15.0

---

# DB ERD

![image](https://github.com/justinkim12/proteinQuiz/assets/47134132/373bef00-3a76-4516-b685-7fb9dff03d81)


- player : 유저들의 점수들을 저장하는 테이블
    - rank를 활용하여 등수를 가져옴
- log : 유저들의 활동 기록들을 저장하는 테이블
- food_info : 음식들의 기본적인 정보를 저장하는 테이블
- food : 음식을 문제로 낼 때 필요한 정보들을 저장하는 테이블
- food_rate : 음식 문제에 대한 정답률을 저장하는 테이블

---

# 프로잭트 코드 기본 설명

## Domain

- Error, food, Logging, player 객체들을 포함한 Repository, Service가 있는 패키지입니다.
    - Error는 에러를 담는 객체가 있습니다.
    - food, player는 기본적인 도메인으로 DTO와 Service, Repositry가 있습니다.
    - Logging에는 파일화 되는 로그를 DB에 자동으로 추가하는 코드가 있습니다.
        - 이는 CRON 식으로 하루마다 실행되도록 코딩되어 있습니다.
- Repository는 jdbc로 구현되어 있습니다.
    - DbConfig에서 빈 등록을 합니다.

## Web

- ArgumentResolver, Controller, Interceptor들을 포함하는 패키지입니다.
    
    ### ArgumentResolver
    
    - @Player 어노테이션을 만들고, 해당 어노테이션이 붙어있는 메서드 검증
    - 세션이 있는지 검증하고 있으면 세션에 있는 객체 반환
    - WebConfig에서 추가
    
    ### Controller
    
    - PlayerController와 QuizController는 RestAPI URL 요청 시의 수행됩니다.
    - ApiExceptionController는 DataAccess와 관련된 예외를 글로벌하게 Handle합니다.
    
    ### Interceptor
    
    - 잘못된 URL 접근에 대해 처리합니다.
    - WebConfig에서 추가

## Resources

- application.properites에서 database 정보가 담겨있습니다.
    - 보안을 위해 환경 변수 처리했습니다.
- logback-spring.xml은 spring 서버에 남겨진 로그들을 파일로 저장하는 코드입니다.

---

# 프론트 코드

https://github.com/justinkim12/proteinQuizFront
