<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="model.UserModel"%>
<%
session.removeAttribute("loginUser");
session.removeAttribute("loginerrUser");
session.removeAttribute("msg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- BootstrapのCSS読み込み -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">

<title>ユーザー登録</title>
</head>
<body>

	<div class="container"
		style="padding: 30px; display: flex; justify-content: center;">

		<div class="user">
			<table border="1" style="width: 500px;">

				<tr>
					<th><div class="blank"
							style="padding: 20px; background-color: #EEEEEE;">
							<h4>ユーザー登録</h4>
						</div></th>
				</tr>

				<tr>
					<td><div class="blank" style="padding: 20px">
							<form action="/healthylife/UserRegister" method="post">

								<%-- ユーザーIDは非表示、自動採番にしたい--%>
								<%--バリデーションチェック用の仮ユーザーID--%>
								<input type="hidden" name="userId" style="width: 460px"
									value="999999">
								<%--ユーザーID<br><input type="text" name="userId"  style="width:460px" value="<c:out value="${adderrUser.userId}" />">
	<c:if test="${not empty msgUserId}" ><div style="color:red"><p>${msgUserId}</p></div>
	</c:if>
	<p></p>--%>

								メールアドレス<br> <input type="text" name="usermail"
									id="usermail" style="width: 460px"
									class="form-control<c:if test="${errors.usermail!=null}"> is-invalid</c:if>"
									value="<c:out value="${user.usermail}"/>">
								<c:if test="${errors.usermail!=null}">
									<span class="text-danger">${errors.usermail}</span>
								</c:if>

								<p></p>
								パスワード<br> <input type="password" name="pass" id="pass"
									style="width: 460px"
									class="form-control<c:if test="${errors.pass!=null}"> is-invalid</c:if>"
									value="<c:out value="${user.pass}"/>">
								<c:if test="${errors.pass!=null}">
									<span class="text-danger">${errors.pass}</span>
								</c:if>

								<p></p>
								生年月日<br> <input type="date" name="birthday" id="birthday"
									style="width: 200px"
									class="form-control<c:if test="${errors.birthday!=null}"> is-invalid</c:if>"
									value="<c:out value="${user.birthday}"/>">
								<c:if test="${errors.birthday!=null}">
									<span class="text-danger">${errors.birthday}</span>
								</c:if>

								<p></p>
								<%-- 性別はバリデーションチェックなし --%>
								性別<input type="radio" style="margin-left: 30px" id="sex"
									name="sex" value="0" checked>男 <input type="radio"
									style="margin-left: 20px" id="sex" name="sex" value="1">女

								<br>
								<p></p>
								<input type="submit" value="登録" style="margin-left: 200px">
							</form>

						</div></td>
				</tr>
			</table>

			<p></p>
			<a href="/healthylife/Login">ログインページに戻る</a>
		</div>
	</div>
</body>
</html>