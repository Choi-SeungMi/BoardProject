<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

        .sns-chk {
            margin-top : 5px;
        }
    </style>
    <title>회원정보수정</title>
</head>
<body>

<script>
    let resultMsg = "${resultMsg}";
    if(resultMsg=="MOD_ERR") alert("회원정보 수정에 실패하였습니다. 다시 시도해 주세요.")
</script>


    <form:form modelAttribute="userDto" id="infoForm" method="post" onsubmit="return formCheck()">
    <div class="title">회원정보수정</div>
    <div id="msg">
        <c:if test="${not empty param.msg}">
            <i class="fa fa-exclamation-circle"> ${param.msg}</i>
        </c:if>
    </div>

    <label for="">아이디</label>
    <input class="input-field" type="text" id="id" name="id" value="${userDto.id}" readonly>
    <label for="">비밀번호</label>
    <input class="input-field" type="password" id="pwd" name="pwd" placeholder="비밀번호 입력">
    <label for="">이름</label>
    <input class="input-field" type="text" id="name" name="name" value="${userDto.name}">
    <label for="">이메일</label>
    <input class="input-field" type="text" id="email" name="email" value="${userDto.email}" readonly>
    <label for="">생일</label>
    <input class="input-field" type="text" id="birth" name="birth" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${userDto.birth}"/>">

        <div aria-colspan="2">
    <button id="modBtn">확인</button>
    <button type="button" id="backBtn">취소</button>
        </div>

    <br>
    </form:form>


    <script>

        const obj = [
            {box:document.getElementById("pwd"),regExp:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,12}$/, msg1:'패스워드를 입력해주세요.', msg2: '8~12자리의 영대소문자와 숫자를 포함해주세요.'},
            {box:document.getElementById("name"),regExp:/^[가-힣a-zA-Z ]{2,10}$/, msg1: '이름을 입력해주세요.', msg2: '정확한 이름을 입력해주세요.'},
            {box:document.getElementById("email"),regExp:/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i, msg1:'이메일을 입력해주세요.', msg2: '정확한 이메일을 입력해주세요.'},
            {box:document.getElementById("birth"),regExp:/^(19[0-9][0-9]|20\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/, msg1:'생일을 입력해주세요.', msg2: '정확한 생일을 입력해주세요.'}
        ]

        const msg = document.getElementById("msg")
        const infoForm = document.getElementById("infoForm")

        const formCheck = function (){
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

        $('#backBtn').click(function () {

            location.href = "<c:url value='/'/>"
        })

        $(document).ready(function() {

            infoForm.action = "<c:url value='/account/usermodify'/>"

        })


    </script>
</body>
</html>
