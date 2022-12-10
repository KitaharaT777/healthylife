<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 初回のメインページ表示に必要 --%>
<%@ page import="model.UserModel"%>

<%
//System.out.println("mainAdminPage");	

session.removeAttribute("loginerrUser");

//セッションスコープからインスタンスを取得
UserModel loginUser = (UserModel) session.getAttribute("loginUser");
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
			<h1>病名情報一覧</h1>
		</div>

		<%-- インポート/エクスポートボタン --%>
		<div style="float: left; padding-top: 20px; padding-left: 500px;">
			<div style="float: left;">
				<form action="./Import" method="get">
					<input type="submit" value="インポート" id="import">
				</form>
			</div>
			<div style="float: left; padding-left: 10px;">
				<form action="./Export" method="get">
					<input type="submit" value="エクスポート(全病名情報)" id="export">
				</form>
			</div>
		</div>
		<%-- インポート/エクスポート後にメッセージを表示 --%>
		<div style="clear: both; padding-left: 0px;">
			<div style="float: right; padding-right: 20px; margin-top:-15px; color:green;">
			<%--<c:if test="${msg_data.import!=null}">--%>
				<span>${msg_data}</span>
			<%--</c:if>--%>
			</div>
		</div>

		<div class="action_wrapper" style="padding-top: 5px; clear: both;">

			<table>
				<tbody>
					<tr style="font-weight: bold">
						<td style="width: 100px">ID</td>
						<td style="width: 700px">病名</td>
						<td style="width: 100px"></td>
						<td style="width: 100px"></td>
						<td style="width: 80px">詳細/修正</td>
					</tr>
				</tbody>
			</table>

			<%-- 一覧表示 --%>
			<c:forEach var="diseaseList" items="${diseaseList}">
				<%-- 実行ボタンのform --%>
				<form action="./DiseaseUpdate" method="get">
					<%-- 削除フラグ1は表示しない　--%>
					<c:if test="${diseaseList.isDeleted==0}">

						<input type="hidden" name="user_id" value="<c:out value="1" />">
						<input type="hidden" name="id"
							value="<c:out value="${diseaseList.id}" />">
						<%-- 実行ボタンNoは見えないようにする --%>

						<table style="border-top: 1px solid black;">

							<%-- Diseaseリスト --%>
							<tr>
								<td style="width: 100px"><c:out
										value="${diseaseList.nameId}" /><input type="hidden"
									name="name_id" value="<c:out value="${diseaseList.nameId}" />"></td>
								<td style="width: 900px"><c:out
										value="${diseaseList.nameOfDisease}" /><input type="hidden"
									name="name_of_disease"
									value="<c:out value="${diseaseList.nameOfDisease}" />"></td>
								<td style="width: 80px" align="center"><input type="submit"
									value="詳細/修正" id="action<c:out value="${diseaseList.id}" />"></td>
							</tr>

						</table>

					</c:if>
				</form>
			</c:forEach>
		</div>
	</div>

</body>
</html>