<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.net.URLDecoder"%>
<%@ page session="false"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
  <style>
    * { box-sizing:border-box; }
    form {
      width:400px;
      height:650px;
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
    .input-field {
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
      margin: 40px 0 10px 0;
    }
    #msg {
      height: 30px;
      text-align:center;
      font-size:13px;
      color:red;
      margin-bottom: 20px;
    }
    .container{
      width: 300px;
      height: 40px;
    }

    #idCheckBtn, #emailCheckBtn {
      width: 70px;
      height: 25px;
      font-size: 13px;
      margin: 0;
    }

    .sns-chk {
      margin-top : 5px;
    }
  </style>
  <title>Register</title>
</head>
<body>

<span id="isIdChecked" style="display: none">false</span>
<form id="regForm" action="<c:url value="/register/add"/>" method="post" onsubmit="return formCheck();">
<form:form modelAttribute="userDto">
  <div class="title">Register</div>
  <div id="msg">
    <c:if test="${not empty param.msg}">
      <i class="fa fa-exclamation-circle"> ${URLDecoder.decode(param.msg)}</i>
    </c:if>
  </div>

  <div class="container" aria-colspan="2">
  <label for="">아이디</label> <button type="button" id="idCheckBtn" onclick="showPopup();">중복확인</button>
  </div>
  <input class="input-field" type="text" id="id" name="id" placeholder="4~12자리의 영대소문자와 숫자 조합" value="<c:out value='${userDto.id}'/>">
  <label for="">비밀번호</label>
  <input class="input-field" type="password" id="pwd" name="pwd" placeholder="8~12자리의 영대소문자와 숫자 조합" value="<c:out value='${userDto.pwd}'/>">
  <label for="">이름</label>
  <input class="input-field" type="text" id="name" name="name" placeholder="홍길동" value="<c:out value='${userDto.name}'/>">
  <label for="">이메일</label>
  <input class="input-field" type="text" id="email" name="email" placeholder="example@openboard.co.kr" value="<c:out value='${userDto.email}'/>">
  <label for="">생일</label>
  <input class="input-field" type="text" id="birth" name="birth" placeholder="2023-01-01" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${userDto.birth}"/>">

  <div aria-colspan="2">
  <button id="regBtn">회원 가입</button>
  <button type="button" onclick="location.href=<c:url value='/'/>">취소</button>
  </div>
  <span>이미 회원이신가요? <a href="<c:url value='/login/login'/>">로그인</a></span>
  <br>
</form:form>


  <script>

    const obj = [
      {box:document.getElementById("id"),regExp:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{4,12}$/, msg1:'아이디를 입력해주세요.', msg2: '4~12자리의 영대소문자와 숫자를 포함해주세요.'},
      {box:document.getElementById("pwd"),regExp:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,12}$/, msg1:'패스워드를 입력해주세요.', msg2: '8~12자리의 영대소문자와 숫자를 포함해주세요.'},
      {box:document.getElementById("name"),regExp:/^[가-힣a-zA-Z ]{2,10}$/, msg1: '이름을 입력해주세요.', msg2: '정확한 이름을 입력해주세요.'},
      {box:document.getElementById("email"),regExp:/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i, msg1:'이메일을 입력해주세요.', msg2: '정확한 이메일을 입력해주세요.'},
      {box:document.getElementById("birth"),regExp:/^(19[0-9][0-9]|20\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/, msg1:'생일을 입력해주세요.', msg2: '정확한 생일을 입력해주세요.'}
    ]

    const msg = document.getElementById("msg")

  let resultMsg = "${resultMsg}";
  if(resultMsg=="REG_ERR") alert("회원가입에 실패하였습니다. 다시 시도해 주세요.");
  if(resultMsg=="REG_EMAIL_ERR"){
    msg.innerHTML = "이미 사용중인 이메일입니다."
    obj[3].box.select()
  }


  const formCheck = function (){
    let idCheck = document.getElementById("isIdChecked").textContent

    for(let i = 0 ; i < obj.length ; i++){
      let value = obj[i].box.value;
      let regExp = obj[i].regExp;

      if(isEmpty(value)) {
        msg.innerHTML = obj[i].msg1
        obj[i].box.select()
        return false
      }
      if(!regExp.test(value)) {
        msg.innerHTML = obj[i].msg2
        obj[i].box.select()
        return false
      }

    }

    if(idCheck != 'true'){
      msg.innerHTML = "아이디 중복확인을 해주세요."
      obj[0].box.select()
      return false
    }
    return true
  }

  const isEmpty = function(target) {
    if (
            target === null ||
            target == null ||
            target === undefined ||
            target === "" ||
            target == ""
    ) {
      return true;
    }
    return false;
  }

  const showPopup = function () {
    let value = obj[0].box.value;
    let regExp = obj[0].regExp;

    if(isEmpty(value)){
      msg.innerHTML = '아이디를 입력해주세요.'
      return
    }
    if(!regExp.test(value)) {
      msg.innerHTML = obj[0].msg2
      obj[0].box.select()
      return false
    }
    else {
      msg.innerHTML = ''
      let id = document.getElementById("id").value
      window.open("<c:url value="/register/idcheck?id="/>"+id, "idwin", "width=400, height=350, left=100, top=50");
    }
  }

  const sendEmail = function (){

    let email = obj[3].box.value;
    let regExp = obj[3].regExp;

    if(isEmpty(email)){
      msg.innerHTML = '이메일을 입력해주세요.'
      return
    }
    if(!regExp.test(email)) {
      msg.innerHTML = obj[3].msg2
      obj[3].box.select()
      return false
    }
    else {
      msg.innerHTML = ''

      $.ajax({
        type: 'POST',       // 요청 메서드
        url: '/openboard/register/emailcheck',  // 요청 URI
        data: {email: email},  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success: function () {
          alert('인증코드를 이메일로 전송했습니다.');
        },
        error: function (request) {
          if(request.responseText == 'EMAIL_ALREADY'){
            msg.innerHTML = '이미 사용중인 이메일입니다.'
            obj[3].box.select()
          }
          if(request.responseText == 'EMAIL_ERR'){
            alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
          }

        } // 에러가 발생했을 때, 호출될 함수
      }) // $.ajax()
    }
  };

</script>
</body>
</html>
