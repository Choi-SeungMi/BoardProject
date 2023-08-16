# BoardProject
Spring과 Java를 사용한 게시판 서비스

<br>
<br>

## 1. 개요

- 개발 인원: 1명
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

![1](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/a12f6227-b435-46ce-b288-2f85ae5a6086)


### board
![2](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/f0d38f02-b9c8-4ba2-b52a-c6c0a0c87d04)


### user_info

![3](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/a0acb740-4124-4e89-8686-c43fa3bf0b68)

### comment
![4](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/b7199454-9859-4388-abf3-6b0fe0df3a87)


---

## 4. API설계

### 게시판 관련 API
![5](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/a3424eca-31fb-434f-a94e-d17b5fc2be25)


### 댓글 관련 API
![6](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/6998b8ad-5b69-470d-8d46-6e7869db62bb)

### 회원 관련 API
![7](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/a2ea6425-73b5-4110-a431-4930a48e6539)

### 회원가입 관련 API
![8](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/59597c55-abf0-4acd-969a-7d8419e8b4d8)


### 로그인 관련 API
![9](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/b975ba9b-13df-40ba-a8f2-2855df989f2e)

---

## 5. 화면구성

### 로그인 페이지

![10](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/904f133d-43dc-4380-9ed4-2bf4ea8754b1)


<br>

### 로그인 후 상단바 변경

![11](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/1a1e51e0-b182-47aa-b551-1aaa00f99188)

<br>

### 회원가입 페이지
![12](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/51c445bb-378f-48d5-89d1-a4e3ef14eee6)


<br>

### 게시판 목록 페이지
![13](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/0761ee95-96be-45c7-8061-370b2a158709)


<br>

### 게시글 읽기 페이지

![14](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/5462fdc5-eb0d-450b-a6d0-0ee61fb71f90)

### 게시글 작성 페이지

![15](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/ad4a5590-6c23-4870-9ca4-f1ba11cd1351)

### 게시글 수정 페이지
![16](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/63284a0a-a2b3-4aa2-a357-5d5fdf6fac4b)


<br>

### 회원정보 변경 로그인 페이지
![17](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/4784fa6d-6c0e-481f-8b94-782aa96995d8)


<br>

### 회원정보 변경 페이지
![18](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/c1098e6f-00e1-4f07-bc3a-a4e84129bb70)


<br>

### 계정찾기 페이지

![19](https://github.com/Choi-SeungMi/BoardProject/assets/115157482/3cc245be-2292-4020-82c9-161de12ea3f6)











