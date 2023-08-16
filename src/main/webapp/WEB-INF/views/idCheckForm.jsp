<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"%>

<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <style>
        h2{
            text-align: center;
        }
        h3 {
            color: orangered;
            text-align: center;
        }

    </style>
    <title>아이디 중복확인</title>
</head>
<body>

<c:choose>
    <c:when test="${idCheckMsg eq 'true'}">
        <h3>사용가능한 아이디 입니다.</h3>
        <button id="useBtn" onclick="useId()">사용하기</button>
    </c:when>
    <c:otherwise>
        <h3>이미 사용중인 아이디입니다.</h3>
    </c:otherwise>
</c:choose>



<button onclick="unUseId()">닫기</button>

<script>

    const useId = function (){
        window.opener.document.getElementById("isIdChecked").innerText = "true"
        window.opener.document.getElementById("id").readOnly = true
        window.opener.document.getElementById("id").style.backgroundColor = "#e6e6e6"
        window.opener.document.getElementById("id").style.fontWeight = "bold"
        window.opener.document.getElementById("pwd").select()
        self.close()
    }

    const unUseId = function (){
        window.opener.document.getElementById("isIdChecked").innerText = "false"
        window.opener.document.getElementById("id").readOnly = false
        window.opener.document.getElementById("id").style.backgroundColor = "white"
        window.opener.document.getElementById("id").style.fontWeight = ""
        window.opener.document.getElementById("id").select()
        self.close()
    }

</script>

</body>
</html>
