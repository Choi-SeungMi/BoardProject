<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page session="true"%>
<c:set var="loginId" value="${pageContext.request.getSession(false)==null ? null : pageContext.request.session.getAttribute('id')}"/>
<c:set var="loginOutLink" value="${loginId==null ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId==null ? 'Login' : 'Logout'}"/>
<c:set var="SignupLink" value="${loginId==null ? '/register/add' : '/'}"/>
<c:set var="Signup" value="${loginId==null ? 'Sign up' : 'Hello! '+=loginId}"/>
<c:set var="SignupLink" value="${loginId==null ? '/register/add' : '/account/confirm'}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>openboard</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.0/moment.min.js"></script>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: "Noto Sans KR", sans-serif;
        }
        .container {
            width : 50%;
            margin : auto;
        }
        .comment-container {
            width : 50%;
            margin-bottom: 100px;
            margin : auto;
        }
        .writing-header {
            position: relative;
            margin: 20px 0 0 0;
            padding-bottom: 10px;
            border-bottom: 1px solid #323232;
        }
        input {
            width: 100%;
            height: 35px;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            padding: 8px;
            background: #f8f8f8;
            outline-color: #e6e6e6;
        }
        textarea {
            width: 100%;
            background: #f8f8f8;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            resize: none;
            padding: 8px;
            outline-color: #e6e6e6;
        }
        .frm {
            width:100%;
        }
        .btn {
            background-color: rgb(236, 236, 236); /* Blue background */
            border: none;
            color: black;
            padding: 6px 12px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
        }
        .btn:hover {
            text-decoration: underline;
        }

        #comments {
            color: black;
            height : 100%;
            width: 100%;
            display: block;
            background-color: white;
            margin-bottom: 100px;
        }
        #comments > li {
            color: black;
            height : 100%;
            width: 100%;
            display: block;
            align-items: center;
            padding-bottom: 10px;
            padding-top: 10px;
            outline-color: #e6e6e6;
            border-bottom: 1px dotted rgba(50, 50, 50, 0.55);

        }
        .comment{
            font-weight: bold;
        }

        #sendBtn {
            border: none;
            padding: 5px 20px;
            cursor: pointer;
            background-color: #30426e; color: white;
            font-weight: bold;
            border-radius: 5px;
        }
        #sendBtn:hover {
            background: rgba(48, 66, 110, 0.75);
        }

        #commentInput {
            height : 50px;
        }

    </style>
</head>
<body>
<div id="menu">
    <ul id="global-menu">
        <li id="logo">openboard</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li id="signup"><a href="<c:url value='${SignupLink}'/>">${Signup}</a></li>
    </ul>
</div>
<script>
    let msg = "${msg}";
    if(msg=="WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요.");
    if(msg=="MOD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요.");
</script>
<div class="container">
    <h2 class="writing-header">게시판 ${mode=="new" ? "글쓰기" : "읽기"}</h2>
    <form id="form" class="frm" action="" method="post">
        <input type="hidden" name="bno" value="${boardDto.bno}">

        <input name="title" type="text" value="<c:out value='${boardDto.title}'/>" placeholder="  제목을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>
        <textarea name="content" rows="20" placeholder=" 내용을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><c:out value='${boardDto.content}'/></textarea><br>

        <c:if test="${mode eq 'new'}">
            <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 등록</button>
        </c:if>

        <c:if test="${boardDto.writer eq loginId}">
            <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i> 수정</button>
            <button type="button" id="removeBtn" class="btn btn-remove"><i class="fa fa-trash"></i> 삭제</button>
        </c:if>
        <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>
    </form>
</div>
<script>
    $(document).ready(function(){
        let formCheck = function() {
            let form = document.getElementById("form");
            if(form.title.value=="") {
                alert("제목을 입력해 주세요.");
                form.title.focus();
                return false;
            }
            if(form.content.value=="") {
                alert("내용을 입력해 주세요.");
                form.content.focus();
                return false;
            }
            return true;
        }
        $("#commentNewBtn").on("click", function(){
            location.href="<c:url value='/board/write'/>";
        });
        $("#writeBtn").on("click", function(){
            let form = $("#form");
            form.attr("action", "<c:url value='/board/write'/>");
            form.attr("method", "post");
            if(formCheck())
                form.submit();
        });
        $("#modifyBtn").on("click", function(){
            let form = $("#form");
            let isReadonly = $("input[name=title]").attr('readonly');
            // 1. 읽기 상태이면, 수정 상태로 변경
            if(isReadonly=='readonly') {
                $(".writing-header").html("게시판 수정");
                $("input[name=title]").attr('readonly', false);
                $("textarea").attr('readonly', false);
                $("#modifyBtn").html("<i class='fa fa-pencil'></i> 등록");
                $(".comment-container").css('display', 'none')
                return;
            }
            // 2. 수정 상태이면, 수정된 내용을 서버로 전송
            form.attr("action", "<c:url value='/board/modify${searchCondition.queryString}'/>");
            form.attr("method", "post");
            if(formCheck())
                form.submit();
        });
        $("#removeBtn").on("click", function(){
            if(!confirm("정말로 삭제하시겠습니까?")) return;
            let form = $("#form");
            form.attr("action", "<c:url value='/board/remove${searchCondition.queryString}'/>");
            form.attr("method", "post");
            form.submit();
        });
        $("#listBtn").on("click", function(){
            location.href="<c:url value='/board/list${searchCondition.queryString}'/>";
        });
    });

</script>

<br>
<br>
<br>

<!--댓글기능-->
<c:if test="${mode ne 'new'}">
<div class="comment-container"><h3>댓글</h3></div>
    <div class="comment-container">
        <input  id="commentInput" type="text" name="comment"><br>
        <button id="sendBtn" type="button">댓글 쓰기</button>
    </div>
    <br>
    <br>
<div class="comment-container" id="commentList"></div>
</c:if>
<div id="replyForm" style="display: none">
<input type="text" name="replyComment">
<button id="wrtRepBtn" type="button">등록</button>
</div>

<div id="modForm" style="display: none">
    <input type="text" name="modComment">
    <button id="modRepBtn" type="button">등록</button>
</div>

<script>

    let bno = ${boardDto.bno};

    let showList = function(bno){
        $.ajax({
            type:'GET',       // 요청 메서드
            url: '/openboard/comments?bno='+bno,  // 요청 URI
            success : function(result){
                $('#commentList').html(toHTML(result));   // 서버로부터 응답이 도착하면 호출될 함수
            },
            error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
        }); // $.ajax()
    }

    $(document).ready(function() {

        showList(bno);

        $('#wrtRepBtn').click(function () {

            let comment = $("input[name=replyComment]").val();
            let pcno = $("#replyForm").parent().attr("data-pcno");

            if (comment.trim() == '') {
                alert("댓글을 입력해주세요.");
                $("input[name=replyComment]").focus()
                return;

            }

            $.ajax({
                type: 'POST',       // 요청 메서드
                url: '/openboard/comments?bno=' + bno,  // 요청 URI
                headers: {"content-type": "application/json"}, // 요청 헤더
                data: JSON.stringify({pcno: pcno, bno: bno, comment: comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                success: function (result) {
                    alert(result);
                    showList(bno);
                },
                error: function () {
                    alert("error")
                } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()

            $("#replyForm").css("display", "none")
            $("input[name=replyComment]").val('')
            $("#replyForm").appendTo("body");

        });

        $('#modRepBtn').click(function () {

            let comment = $("input[name=modComment]").val();
            let cno = $(this).attr("data-cno");

            if (comment.trim() == '') {
                alert("댓글을 입력해주세요.");
                $("input[name=modComment]").focus()
                return;

            }

            $.ajax({
                type: 'PATCH',       // 요청 메서드
                url: '/openboard/comments/' + cno,  // 요청 URI
                headers: {"content-type": "application/json"}, // 요청 헤더
                data: JSON.stringify({cno: cno, comment: comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                success: function (result) {
                    alert(result);
                    showList(bno);
                },
                error: function () {
                    alert("error")
                } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()

            $("#modForm").css("display", "none")
            $("input[name=modComment]").val('')
            $("#modForm").appendTo("body");

        });

        $('#sendBtn').click(function () {

            let comment = $("input[name=comment]").val();

            if (comment.trim() == '') {
                alert("댓글을 입력해주세요.");
                $("input[name=comment]").focus()
                return;

            }

            $.ajax({
                type: 'POST',       // 요청 메서드
                url: '/openboard/comments?bno=' + bno,  // 요청 URI
                headers: {"content-type": "application/json"}, // 요청 헤더
                data: JSON.stringify({bno: bno, comment: comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                success: function (result) {
                    alert(result);
                    showList(bno);
                },
                error: function () {
                    alert("error")
                } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()

            $("input[name=comment]").val('');
        });

        $('#commentList').on("click", ".modBtn", function () {

            //1. modForm을 옮기고
            $("#modForm").appendTo($(this).parent());

            //2. 글을 입력할 폼을 보여준다.
            $("#modForm").css("display", "block");

            let cno = $(this).parent().attr("data-cno");

            //this(modBtn)의 부모 (li태그)의 span태그 중 comment 클래스의 text값을 가져옴
            let comment = $("span.comment", $(this).parent()).text();

            //1. comment의 내용을 input에 뿌려주기
            $("input[name=modComment]").val(comment);

            //2. cno 전달하기
            $("#modRepBtn").attr("data-cno", cno);
        })

        $('#commentList').on("click", ".replyBtn", function () {
            //1. replyForm을 옮기고
            $("#replyForm").appendTo($(this).parent());

            //2. 답글을 입력할 폼을 보여준다.
            $("#replyForm").css("display", "block");


        });

        //.delBtn에 클릭 이벤트를 바로 추가해놓으면 응답이 오기전 버튼에 이벤트 추가 메서드가 실행됨.
        //응답이 오기 전에는 버튼이 생성되기 전이므로 이벤트가 안걸리게 됨
        //그러므로 응답이 오기전에 있는 commentList객체 안에 있는 delBtn에 이벤트를 걸어줘야 함.
        $('#commentList').on("click", ".delBtn", function () {

            let cno = $(this).parent().attr("data-cno");
            let bno = $(this).parent().attr("data-bno");

            $.ajax({
                type: 'DELETE',       // 요청 메서드
                url: '/openboard/comments/' + cno + '?bno=' + bno,  // 요청 URI
                success: function (result) {
                    alert(result);
                    showList(bno);
                },
                error: function () {
                    alert("error")
                } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        });

    });

    let toHTML = function (comments) {
        let tmp = "<ul id='comments'>";

        comments.forEach(function (comment) {
            tmp += '<li data-cno=' + comment.cno
            tmp += ' data-pcno=' + comment.pcno
            tmp += ' data-bno=' + comment.bno + '>'
            if(comment.cno!=comment.pcno)
                tmp += 'ㄴ'
            tmp += ' 작성자 : <span class="commenter">' + comment.commenter + '</span><br>'
            tmp += ' 작성일 : ' + moment(comment.up_date).format("YYYY-MM-DD HH:mm")+'<br><br>'
            tmp += ' <span class="comment">' + comment.comment + '</span><br><br>'
            if(comment.commenter=="${loginId}") {
                tmp += '<button class="delBtn">삭제</button>'
                tmp += '<button class="modBtn">수정</button>'
            }
            tmp += '<button class="replyBtn">답글</button>'
            tmp += '</li>'
        })
        return tmp + "</ul>";

    }
</script>


</body>
</html>