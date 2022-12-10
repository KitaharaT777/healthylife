<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
	//System.out.println("userListPage");	
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

<title>HealthyLife</title>
</head>
<body>

	<div class="container" style="padding: 30px">
		<%-- header--%>
		<jsp:include page="/WEB-INF/jsp/include/headerAdmin.jsp" />

		<div style="padding-top: 10px; float: left;">
			<h1>ユーザー一覧</h1>
		</div>

		<%--ボタン非表示(追加機能とするか？)
	<div style="float:left; padding-top:20px; padding-left:600px;">
		<div style="float:left;"><form action="./Import" method="get"><input type="submit" value="インポート" id="import"></form></div>
		<div style="float:left; padding-left:10px;"><form action="./Export" method="get"><input type="submit" value="エクスポート" id="export"></form></div>
	</div>
	--%>

		<div class="action_wrapper" style="padding-top: 5px; clear: both;">

			<table>
				<tbody>
					<tr style="font-weight: bold">
						<td style="width: 100px">ID</td>
						<td style="width: 700px">メールアドレス</td>
						<td style="width: 100px"></td>
						<td style="width: 100px"></td>
						<td style="width: 80px">詳細/修正</td>
					</tr>
				</tbody>
			</table>

			<%-- 一覧表示 --%>
			<c:forEach var="userList" items="${userList}">
				<%-- 実行ボタンのform --%>
				<form action="./UserUpdate" method="get">
					<%-- 管理者は表示しない　--%>
					<c:if test="${userList.id!=1}">

						<%-- 削除フラグ1は表示しない　--%>
						<c:if test="${userList.isDeleted==0}">

							<input type="hidden" name="user_id" value="<c:out value="1" />">
							<input type="hidden" name="id"
								value="<c:out value="${userList.id}" />">
							<%-- 実行ボタンNoは見えないようにする --%>

							<table style="border-top: 1px solid black;">

								<%-- Diseaseリスト --%>
								<tr>
									<td style="width: 100px"><c:out value="${userList.userId}" /><input
										type="hidden" name="userId"
										value="<c:out value="${userList.userId}" />"></td>
									<td style="width: 900px"><c:out value="${userList.email}" /><input
										type="hidden" name="email"
										value="<c:out value="${userList.email}" />"></td>
									<td style="width: 80px" align="center"><input
										type="submit" value="詳細/修正"
										id="action<c:out value="${userList.id}" />"></td>
								</tr>

							</table>

						</c:if>
					</c:if>
				</form>
			</c:forEach>
		</div>
	</div>

</body>
</html>