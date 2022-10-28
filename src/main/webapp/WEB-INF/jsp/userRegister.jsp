<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ page import="model.UserModel" %>
<%
	session.removeAttribute("loginUser");
	session.removeAttribute("loginerrUser");
	session.removeAttribute("msg");
	
	//セッションスコープからインスタンスを取得
	//Account adderrUser=(Account) session.getAttribute("adderrUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- BootstrapのCSS読み込み -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
 
<title>ユーザー登録</title>
</head>
<body>

<div class="container" style="padding:30px; display: flex; justify-content: center;">

<div class="user">
<table border="1" style="width:500px;">

<tr><th><div class="blank" style="padding:20px; background-color:#EEEEEE;"><h4>ユーザー登録</h4></div></th></tr>

<tr><td><div class="blank" style="padding:20px">
<form action="/healthylife/AddAccount" method="post">
E-mailアドレス<br><input type="text" name="usermail"  style="width:460px"  value="<c:out value="${adderrUser.email}" />">
<c:if test="${not empty msgMail}" ><div style="color:red"><p>${msgMail}</p></div>
</c:if>
<p></p>
パスワード<br><input type="password" name="pass"  style="width:460px" value="<c:out value="${adderrUser.pass}" />">
<c:if test="${not empty msgPass}" ><div style="color:red"><p>${msgPass}</p></div>
</c:if>
<p></p>
ニックネーム<br><input type="text" name="username"  style="width:460px" value="<c:out value="${adderrUser.userName}" />">
<c:if test="${not empty msgName}" ><div style="color:red"><p>${msgName}</p></div>
</c:if>
<p></p>
<input type="submit" value="登録" style="margin-left:200px">
<input type="hidden" name="today" id="today"><%-- 今日の日付取得用は見えないようにする --%>
</form>

</div>
</td></tr>
</table>

<p></p>
<a href="/healthylife/Login">ログインページはこちら</a>
</div></div>
</body>
</html>