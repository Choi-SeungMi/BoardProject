<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false"%>
<html>
<head>
    <title>계정찾기</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <style>
    * { box-sizing:border-box; }
    form {
    width:480px;
    height:400px;
    display : flex;
    flex-direction: column;
    align-items:center;
    position : absolute;
    top:50%;
    left:50%;
    transform: translate(-50%, -50%) ;
    border: 1px solid rgb(89,117,196);
    border-radius: 10px;
    }
    p{
        margin: 0 10px 0 10px;
    }
    input{
    width: 300px;
    height: 40px;
    border : 1px solid rgb(89,117,196);
    border-radius:5px;
    padding: 0 10px;
    margin-bottom: 10px;
    }

    label {
    width:300px;
    height:30px;
    margin-top :30px;
    }
    button {
    background-color: rgb(89,117,196);
    color : white;
    width:150px;
    height:50px;
    font-size: 17px;
    border : none;
    border-radius: 5px;
    margin : 20px 0 30px 0;
    cursor: pointer;
    }
    button:hover {
    background: rgba(89, 117, 196, 0.91);
    }
    .title {
    font-size : 50px;
    font-weight: bold;
    margin: 40px 0 30px 0;
    }
    </style>
</head>
<body>



<script>
    let emailCheckMsg = "${emailCheckMsg}";
    if(emailCheckMsg=="FIND_ERR") alert("일치하는 계정이 없습니다. 이메일주소를 다시 확인해주세요.")
    if(emailCheckMsg=="FIND_OK") alert("${email}으로 아이디 및 임시비밀번호를 보내드렸습니다.")
</script>
<form action="<c:url value='/account/finduser'/>" method="post">
<div class="title">계정찾기</div>
    <p>회원 가입시 입력하신 이메일 주소를 입력하시면,<br>
    해당 이메일로 아이디 및 임시비밀번호를 보내드립니다.</p>

<label for="">이메일 주소</label>
<input name="email" type="text" placeholder="이메일을 입력해주세요">
<div aria-colspan="2">
    <button type="button" onclick="location.href=<c:url value='/'/>">취소</button>
    <button id="findBtn">계정찾기</button>
</div>
</form>

</body>
</html>
