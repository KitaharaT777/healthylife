<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="model.UserModel"%>
<% 
	//セッションスコープからインスタンスを取得
	UserModel loginUser=(UserModel) session.getAttribute("loginUser");
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

<title>ユーザー情報更新</title>
</head>
<body>

	<div class="container" style="padding: 30px">
		<%-- header--%>
		<c:if test="${loginUser.id!=1}">
			<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
		</c:if>
		<c:if test="${loginUser.id==1}">
			<jsp:include page="/WEB-INF/jsp/include/headerAdmin.jsp" />
		</c:if>

		<div class="user"
			style="padding: 30px; display: flex; justify-content: center;">
			<table border="1" style="width: 500px;">

				<tr>
					<th><div class="blank"
							style="padding: 20px; background-color: #EEEEEE;">
							<h4>会員情報更新</h4>
						</div></th>
				</tr>

				<tr>
					<td><div class="blank" style="padding: 20px">
							<form action="/healthylife/UserUpdate" method="post">

								<%--一般ユーザーでログイン--%>
								<c:if test="${loginUser.id!=1}">
									<%-- ユーザーIDは自動採番にしたい--%>
									<%--一般ユーザーでは非表示--%>
									<input type="hidden" name="userId" style="width: 460px"
										value="<c:out value="${loginUser.userId}" />">
									<c:if test="${not empty msgUserId}">
										<div style="color: red">
											<p>${msgUserId}</p>
										</div>
									</c:if>

	メールアドレス<br>
									<input type="text" name="usermail" id="usermail"
										style="width: 460px"
										class="form-control<c:if test="${errors.usermail!=null}"> is-invalid</c:if>"
										value="<c:out value="${loginUser.email}"/>">
									<c:if test="${errors.usermail!=null}">
										<span class="text-danger">${errors.usermail}</span>
									</c:if>

									<p></p>
	パスワード<br>
									<input type="password" name="pass" id="pass"
										style="width: 460px"
										class="form-control<c:if test="${errors.pass!=null}"> is-invalid</c:if>"
										value="<c:out value="${loginUser.pass}"/>">
									<c:if test="${errors.pass!=null}">
										<span class="text-danger">${errors.pass}</span>
									</c:if>

									<p></p>
	生年月日<br>
									<input type="date" name="birthday" id="birthday"
										style="width: 200px"
										class="form-control<c:if test="${errors.birthday!=null}"> is-invalid</c:if>"
										value="<c:out value="${loginUser.birthday}"/>">
									<c:if test="${errors.birthday!=null}">
										<span class="text-danger">${errors.birthday}</span>
									</c:if>

									<p></p>
									<%--性別はバリデーションチェックしない--%>
									<c:if test="${loginUser.sex!=1}">
		性別<input type="radio" style="margin-left: 30px" id="sex" name="sex"
											value="0" checked>男
		<input type="radio" style="margin-left: 20px" id="sex" name="sex"
											value="1">女
	</c:if>
									<c:if test="${loginUser.sex==1}">
		性別<input type="radio" style="margin-left: 30px" id="sex" name="sex"
											value="0">男
		<input type="radio" style="margin-left: 20px" id="sex" name="sex"
											value="1" checked>女
	</c:if>
									<br>
									<p></p>
									<input type="submit" value="更新" style="margin-left: 0px">

									<%-- 一般ユーザーのみ表示--%>
									<div style="float: right; margin-left: 90px;">
										<a href="/healthylife/UserWithdraw">退会する</a>
									</div>
								</c:if>


								<%--管理者でログイン--%>
								<c:if test="${loginUser.id==1}">
									<%-- ユーザーIDは自動採番にしたい--%>
	ユーザーID<br>
									<input type="text" name="userId" id="userId"
										style="width: 460px"
										class="form-control<c:if test="${errors.userId!=null}"> is-invalid</c:if>"
										value="<c:out value="${userItem.userId}"/>">
									<c:if test="${errors.userId!=null}">
										<span class="text-danger">${errors.userId}</span>
									</c:if>

									<p></p>
	メールアドレス<br>
									<input type="text" name="usermail" id="usermail"
										style="width: 460px"
										class="form-control<c:if test="${errors.usermail!=null}"> is-invalid</c:if>"
										value="<c:out value="${userItem.email}"/>">
									<c:if test="${errors.usermail!=null}">
										<span class="text-danger">${errors.usermail}</span>
									</c:if>

									<p></p>
	パスワード<br>
									<input type="text" name="pass" id="pass" style="width: 460px"
										class="form-control<c:if test="${errors.pass!=null}"> is-invalid</c:if>"
										value="<c:out value="${userItem.pass}"/>">
									<c:if test="${errors.pass!=null}">
										<span class="text-danger">${errors.pass}</span>
									</c:if>

									<p></p>
	生年月日<br>
									<input type="date" name="birthday" id="birthday"
										style="width: 200px"
										class="form-control<c:if test="${errors.birthday!=null}"> is-invalid</c:if>"
										value="<c:out value="${userItem.birthday}"/>">
									<c:if test="${errors.birthday!=null}">
										<span class="text-danger">${errors.birthday}</span>
									</c:if>

									<p></p>
									<%--性別はバリデーションチェックしない--%>
									<c:if test="${loginUser.sex!=1}">
	性別<input type="radio" style="margin-left: 30px" id="sex" name="sex"
											value="0" checked>男
		<input type="radio" style="margin-left: 20px" id="sex" name="sex"
											value="1">女
	</c:if>
									<c:if test="${loginUser.sex==1}">
	性別<input type="radio" style="margin-left: 30px" id="sex" name="sex"
											value="0">男
	<input type="radio" style="margin-left: 20px" id="sex" name="sex"
											value="1" checked>女
	</c:if>

									<br>
									<p></p>
									<input type="submit" value="更新" style="margin-left: 0px">
								</c:if>

							</form>

						</div></td>
				</tr>
			</table>

		</div>

		<c:if test="${loginUser.id!=1}">
			<div style="margin-left: 290px;">
				<p>
					<a href="/healthylife/Main">症状検索履歴に戻る</a>
				</p>
			</div>
		</c:if>
		<c:if test="${loginUser.id==1}">
			<div style="margin-left: 290px;">
				<p>
					<a href="/healthylife/UserList">ユーザー一覧に戻る</a>
				</p>
			</div>
		</c:if>

	</div>
</body>
</html>