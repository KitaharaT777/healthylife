<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 初回のメインページ表示に必要 --%>
<%@ page import="model.UserModel"%>
<%
//セッションスコープからインスタンスを取得
//HttpSession session=request.getSession();
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

<title>病名詳細および修正</title>
</head>
<body>

	<div class="container" style="padding: 30px">
		<%-- header--%>
		<jsp:include page="/WEB-INF/jsp/include/headerAdmin.jsp" />

		<div style="padding-top: 10px;">
			<h1>病名情報詳細および修正</h1>
		</div>

		<div class="add_wrapper" style="padding-top: 5px; font-weight: bold;">
			<form action="/healthylife/DiseaseUpdate" method="post">

				<%-- idは非表示 --%>
				<input type="hidden" name="id"
					value="<c:out value="${DiseaseItem.id}" />"> 病名ID<br>
				<input type="text" name="name_id" id="name_id" style="width: 700px"
					class="form-control<c:if test="${errors.name_id!=null}"> is-invalid</c:if>"
					value="<c:out value="${DiseaseItem.nameId}"/>">
				<c:if test="${errors.name_id!=null}">
					<span class="text-danger">${errors.name_id}</span>
				</c:if>

				<p></p>
				病名<br> <input type="text" name="name_of_disease"
					id="name_of_disease" style="width: 700px"
					class="form-control<c:if test="${errors.name_of_disease!=null}"> is-invalid</c:if>"
					value="<c:out value="${DiseaseItem.nameOfDisease}"/>">
				<c:if test="${errors.name_of_disease!=null}">
					<span class="text-danger">${errors.name_of_disease}</span>
				</c:if>

				<p></p>
				症状<br>
				<textarea rows="10" cols="60" name="information" id="information"
					style="width: 1000px"
					class="form-control<c:if test="${errors.information!=null}"> is-invalid</c:if>"><c:out
						value="${DiseaseItem.info}" /></textarea>
				<c:if test="${errors.information!=null}">
					<span class="text-danger">${errors.information}</span>
				</c:if>

				<p></p>
				参考リンク<br> <input type="text" name="link" id="link"
					style="width: 700px"
					class="form-control<c:if test="${errors.link!=null}"> is-invalid</c:if>"
					value="<c:out value="${DiseaseItem.link}"/>">
				<c:if test="${errors.link!=null}">
					<span class="text-danger">${errors.link}</span>
				</c:if>

				<c:if
					test="${DiseaseItem.link!=null && not empty DiseaseItem.link }">
					<p></p>
					<a href="${DiseaseItem.link}" target="_blank">参考リンク先に移動する</a>
				</c:if>

				<p></p>
				<input type="checkbox" id="delete" name="delete" value="1"><span>削除する</span><br>

				<p></p>
				<p></p>
				<input type="submit" value="更新" name="updatebtn"><br>
			</form>
		</div>

		<p></p>
		<div style="margin-top: 20px; height: 30px;">
			<a href="/healthylife/AdminMain">病名情報一覧に戻る</a>
		</div>
		<p></p>
		<p></p>

	</div>

</body>
</html>