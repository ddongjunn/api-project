# 삼쩜삼 백엔드엔지니어 채용 과제

swagger : http://localhost:8080/swagger-ui/

1) signup <br>
    회원 가입 가능 유저를 확인하기 위해서 WHITELIST 테이블에 회원 가입이 가능한 유저의 정보를 넣어두고
    회원 가입 요청시 입력받은 정보가 WHITELIST 데이터가 존재하는지 체크 한 후 회원가입진행
---
2) login<br>
   로그인이 완료될 경우 token 발급<br>
   SpringSecurity UsernamePasswordAuthenticationFilter 실행전에 TokenAuthenticationFilter
   가 동작하도록 구현하였고, 해당 클래스에서 토큰의 유무 아이디 비밀번호 검증을 진행. 
   TokenProvider를 통해서 토큰 발급 및 검증
---
3) me <br>
   인증 토큰을 이용해서 자기 정보(아이디, 이름, 생년월일)를 조회
---
4) scrap<br>
   scrap API를 호출하여 받아온 정보를 DB에 저장.
   ScrapResponse...Dto 구성하여 DB에 저장 
---
5) refund<br>
   scrap 데이터를 기반으로 결정세액, 퇴직연금세액공제금액 계산.
   계산방식이 수정될 수 있는 부분을 고려하여 계산값들을 개별로 나누어서 구현
    