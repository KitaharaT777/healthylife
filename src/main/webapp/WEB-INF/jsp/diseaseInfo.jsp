<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<title>病名詳細</title>
</head>
<body>

	<div class="container" style="padding: 30px">
		<%-- header--%>
		<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

		<div style="padding-top: 10px;">
			<h1>病名情報</h1>
		</div>

		<div class="add_wrapper" style="padding-top: 5px;">
			<%-- <p>id</p> --%>
			<input type="hidden" name="id"
				value="<c:out value="${DiseaseItem.id}" />">

			<h5>病名</h5>
			<c:out value="${DiseaseItem.nameOfDisease}" />
			<p></p>
			<table style="border-top: 1px solid black; width: 1000px;">
				<tr>
					<td></td>
				</tr>
			</table>
			<p></p>
			<h5>症状</h5>
			<c:out value="${DiseaseItem.info}" />

			<c:if test="${DiseaseItem.link!=null && not empty DiseaseItem.link }">
				<p></p>
				<h6>
					<a href="${DiseaseItem.link}" target="_blank">病名をさらに詳しく見る</a>
				</h6>
			</c:if>

		</div>

		<p></p>
		<div style="margin-left: 0px; margin-top: 20px; height: 30px;">
			<p>
				<a href="/healthylife/Main">検索履歴へ戻る</a>
			</p>
		</div>
	</div>

</body>
</html>