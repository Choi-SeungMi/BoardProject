<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page session="false"%>
<html>
<head>
  <style>
    body, html {
      height: 100%;
    }
    .container {
      position: relative;
      width: 100%;
      height: 80%;
    }

    .message{
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
    }
  </style>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>

  <title>Error</title>
</head>
<body>

<div class="container">
  <div class="message">
    <i class="fa fa-exclamation-circle">Sorry!</i>
    <h3>오류가 발생했습니다. 잠시 후 다시 시도해주세요.</h3>
    <p id="move"></p>
  </div>
</div>
<script>
  const delay = 3000

  const movePage = function (){
    let countdown = delay/1000

    const interval = setInterval(()=>{
      document.getElementById("move").innerHTML = countdown + "초 후 홈으로 이동합니다."
      countdown--;
      if (countdown < 0) {
        clearInterval(interval);
        window.location.href ="http://localhost/openboard/"
      }
    },1000)
  }

  setTimeout(movePage,delay)


</script>

</body>
</html>
