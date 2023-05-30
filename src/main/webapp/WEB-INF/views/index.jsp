<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"%>
<c:set var="loginId" value="${pageContext.request.getSession(false)==null ? null : pageContext.request.session.getAttribute('id')}"/>
<c:set var="loginOutLink" value="${loginId==null ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId==null ? 'Login' : 'Logout'}"/>
<c:set var="Signup" value="${loginId==null ? 'Sign up' : 'Hello! '+=loginId}"/>
<c:set var="SignupLink" value="${loginId==null ? '/register/add' : '/account/confirm'}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>openboard</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">openboard</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li id="signup"><a href="<c:url value='${SignupLink}'/>">${Signup}</a></li>
    </ul>
</div>
<div style="text-align:center">
    <h1>This is HOME</h1>
    <h1>This is HOME</h1>
    <h1>This is HOME</h1>
</div>

<script>
    let resultMsg = "${resultMsg}";
    if(resultMsg=="MOD_OK") alert("성공적으로 수정되었습니다.");
    if(resultMsg=="REG_OK") alert("회원가입이 완료되었습니다.");


</script>

</body>
</html>