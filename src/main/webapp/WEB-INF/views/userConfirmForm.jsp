<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true"%>
<c:set var="loginId" value="${pageContext.request.getSession(false)==null ? null : pageContext.request.session.getAttribute('id')}"/>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>openboard</title>
  <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
  <style>
    * { box-sizing:border-box; }
    a { text-decoration: none; }
    form {
      width:400px;
      height:450px;
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
    input[type='text'], input[type='password'] {
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
      margin-top :4px;
    }

    button {
      background-color: rgb(89,117,196);
      color : white;
      width:300px;
      height:50px;
      font-size: 17px;
      border : none;
      border-radius: 5px;
      cursor: pointer;
      margin : 30px 0 10px 0;
    }

    button:hover {
      background: rgba(89, 117, 196, 0.91);
    }
    #title {
      font-size : 40px;
      font-weight: bold;
      margin: 40px 0 10px 0;
    }
    #msg {
      height: 30px;
      text-align:center;
      font-size:16px;
      color:red;
      margin-bottom: 20px;
    }
  </style>
</head>
<body>

<form action="<c:url value="/account/confirm"/>" method="post" onsubmit="return formCheck(this);">
  <div id="title">회원정보변경</div>
  <div id="msg">
    <c:if test="${not empty param.msg}">
      <i class="fa fa-exclamation-circle">${param.msg}</i>
    </c:if>
  </div>

  <label for="">아이디</label>
  <input type="text" name="id" value="${loginId}" readonly>
  <label for="">비밀번호</label>
  <input type="password" name="pwd" placeholder="비밀번호" autofocus>

  <button>확인</button>

  <script>
    function formCheck(frm) {
      let msg ='';

      if(frm.pwd.value.length==0) {
        setMessage('비밀번호를 입력해주세요.', frm.pwd);
        return false;
      }
      return true;
    }
    function setMessage(msg, element){
      document.getElementById("msg").innerHTML = ` ${'${msg}'}`;
      if(element) {
        element.select();
      }
    }
  </script>
</form>
</body>
</html>