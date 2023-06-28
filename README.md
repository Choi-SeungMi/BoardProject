# BoardProject
게시판 기능이 있는 사이트를 만들어 보면서 MVC패턴, 서블릿과 JSP, 어노테이션, 쿠키, 세션 등의
사용방법과 처리과정에 대해 학습함

<br>
<br>

## 1. 개요

- 개발 인원: 1명
- 개발 기간: 2023.05.08 ~ 
- 주요 기능
  - 게시판: CRUD 기능, 조회수, 페이징 및 검색
  - 댓글: CRUD 기능, 대댓글(답글)
  - 사용자: 회원가입 및 로그인, 아이디 중복검사, 유효성검사, 회원정보 수정, 계정찾기
- 개발 언어: Java 11, javascript, css, html
- 개발 환경: Spring 5.0.7, jdk 11, IntelliJ IDEA 2023.1.1 ,Windows 10
- 데이터베이스: MySQL
- 형상관리: GitHub


## 2. 요구사항

### 회원가입 페이지
- 유효성 검사

  - 아이디는 4~12자리이며, 영문 대소문자와 숫자로 구성
  - 비밀번호는 8~12자리이며, 영문 대소문자와 숫자로 구성
  - 이메일 형식패턴 적용
  - 생일은 날짜형식 패턴 적용


- 중복확인

  - 아이디를 입력한 후 중복확인 버튼을 눌렀을 때 DB에 존재하는 아이디라면, '이미 사용중인 아이디입니다.'라는
메세지를 새로운 팝업으로 보여주고 '닫기'버튼만 활성화
  - DB에 존재하지 않는 아이디라면, '사용가능한 아이디 입니다.'라는 메세지를 팝업으로 보여주고 '사용하기'와
'닫기'버튼을 활성화
  - '사용하기'버튼을 누르면 아이디 수정이 불가능, 다시 중복확인 버튼을 눌러 닫기버튼을 누르면 수정가능
  - 모든 정보를 입력 후 회원가입 버튼을 눌렀을 때 이메일 주소가 이미 DB에 존재한다면 '이미 사용중인 이메일
입니다.'라는 메세지 표시

---

### 로그인 페이지

- 로그인을 하지 않은 경우에는 아래 페이지만 이용가능
  - 홈
  - 로그인 페이지
  - 회원가입 페이지
  - 계정찾기

(로그인을 하지 않은 상태로 로그인이 필요한 경로에 접속한 경우 로그인 페이지로 이동)


- 로그인 검사
  - 아이디와 비밀번호가 DB 데이터와 일치하지 않다면, '아이디 또는 비밀번호가 일치하지 않습니다.' 메세지
    보여주기
  - 모든 검사가 통과하면 로그인 후 이전에 있었던 경로의 페이지로 이동
    ex)로그인하지 않은 상태에서 게시판메뉴를 눌러 로그인 페이지로 접근 했다면, 로그인 후 게시판메뉴로 이동
    
---    
    
### 회원정보 수정
- 비밀번호, 이름, 생일만 수정가능
- 회원가입과 동일한 유효성 검사

---

### 계정찾기
- 아이디 또는 비밀번호를 잊어버렸을 때 이메일 주소를 통해 계정을 확인할 수 있는 기능
- 가입시 입력한 이메일 주소로 아이디와 임시비밀번호를 전송하여 계정을 찾을 수 있음

---

### 게시글 검사
- 게시글 작성 및 수정시 제목과 내용이 빈칸으로 작성되지 않도록 검사
- 내가 작성한 글만 수정 및 삭제 버튼 활성화

---

### 댓글 검사
- 댓글 작성 및 수정시 빈칸으로 작성되지 않도록 검사
- 내가 작성한 댓글만 수정 및 삭제 버튼 활성화
- 게시글 삭제 시 해당 글의 댓글 데이터도 함께 삭제

---

## 3. DB설계

![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/5218cede-c783-4697-90e5-d1cd9b2094cb)

### board
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/7c4d848e-47a4-48c3-aa7a-93c708a59982)

### user_info
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/724a183b-c36f-4b0c-be3f-e6b4fa08b505)

### comment
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/11a36b0b-b3e5-4a0c-a96c-22fe20606f7c)

---

## 4. API설계

### 게시판 관련 API
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/be3685b9-4dc7-4afc-8031-400033fe8531)

### 댓글 관련 API
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/fa026999-2363-4cda-99e5-31bd208514f3)

### 회원 관련 API
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/fedb54a2-9d35-424b-a5b8-5007bbdb022b)

### 회원가입 관련 API
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/5dd5fa83-f21d-4096-bba8-a696e6eba58d)

### 로그인 관련 API
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/2eff4588-1c07-405a-ad4e-029bb213bdf1)

---

## 5. 화면구성

### 로그인 페이지

![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/ebd39268-a2d5-4ccd-b5d6-9f2e9ecc532f)

<br>

### 로그인 후 상단바 변경
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/83a51035-0a67-411f-aea5-70d2f99be2ba)

<br>

### 회원가입 페이지
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/378d427b-63dc-4001-820e-715815e9c0bc)

<br>

### 게시판 목록 페이지
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/3c622a38-c38d-4d91-9796-45f3694cfa51)

<br>

### 게시글 읽기 페이지
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/284bb07a-f49b-4b9d-893b-971113fb2556)

### 게시글 작성 페이지
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/62ddbc54-73a0-4cbc-ab02-d8fbabd89d12)

### 게시글 수정 페이지
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/404c1ab6-4d36-4532-a533-c88afc639126)


<br>

### 회원정보 변경 로그인 페이지
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/de63ea31-b9f3-493c-94af-4f073b480129)

<br>

### 회원정보 변경 페이지
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/06fe4bd8-d5dc-41de-9378-0c6aa2fd4bb8)

<br>

### 계정찾기 페이지
![image](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/90571e2d-c46b-463a-8153-30dc6a63a0aa)











