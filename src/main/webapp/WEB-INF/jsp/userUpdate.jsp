<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ page import="model.UserModel" %>
<% 
	//セッションスコープからインスタンスを取得
	//HttpSession session=request.getSession();
	UserModel loginUser=(UserModel) session.getAttribute("loginUser");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- BootstrapのCSS読み込み -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
 
<title>ユーザー情報更新</title>
</head>
<body>
<script type="text/javascript">
    //今日の日時を表示
        window.onload = function () {
            //今日の日時を表示
            var date = new Date()
            var year = date.getFullYear()
            var month = date.getMonth() + 1
            var day = date.getDate()
          
            var toTwoDigits = function (num, digit) {
              num += ''
              if (num.length < digit) {
                num = '0' + num
              }
              return num
            }
            
            var yyyy = toTwoDigits(year, 4)
            var mm = toTwoDigits(month, 2)
            var dd = toTwoDigits(day, 2)
            var ymd = yyyy + "-" + mm + "-" + dd;
            
            document.getElementById("today").value = ymd;
        }
    ;
    
</script>

<div class="container" style="padding:30px">
<%-- header--%>
<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

<div class="user" style="padding:30px; display: flex; justify-content: center;">
<table border="1" style="width:500px;">

<tr><th><div class="blank" style="padding:20px; background-color:#EEEEEE;"><h4>会員情報更新</h4></div></th></tr>

<tr><td><div class="blank" style="padding:20px">
<form action="/sharedTodo/UpdateAccount" method="post">
E-mailアドレス<br><input type="text" name="usermail" value="<%= loginUser.getEmail() %>" style="width:460px">
<c:if test="${not empty msgMail}" ><div style="color:red"><p>${msgMail}</p></div>
</c:if>
<p></p>
パスワード<br><input type="password" name="pass" value="<%= loginUser.getPass() %>" style="width:460px">
<c:if test="${not empty msgPass}" ><div style="color:red"><p>${msgPass}</p></div>
</c:if>
<p></p>
ニックネーム<br><input type="text" name="username" value="<%= loginUser.getUserName() %>" style="width:460px">
<c:if test="${not empty msgName}" ><div style="color:red"><p>${msgName}</p></div>
</c:if>
<p></p>
<input type="submit" value="更新">
<input type="hidden" name="today" id="today"><%-- 今日の日付取得用は見えないようにする --%>
</form>

</div>
</td></tr>
</table>
</div>

</div>
</body>
</html>