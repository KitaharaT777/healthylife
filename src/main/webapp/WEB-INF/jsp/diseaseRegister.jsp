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

<title>病名登録</title>
</head>
<body>

	<div class="container" style="padding: 30px">
		<%-- header--%>
		<jsp:include page="/WEB-INF/jsp/include/headerAdmin.jsp" />

		<div style="padding-top: 10px;">
			<h1>病名登録</h1>
		</div>

		<div class="add_wrapper" style="padding-top: 5px; font-weight: bold;">
			<form action="/healthylife/DiseaseRegister" method="post">

				病名ID<br> <input type="text" name="name_id" id="name_id"
					style="width: 700px"
					class="form-control<c:if test="${errors.name_id!=null}"> is-invalid</c:if>"
					value="<c:out value="${diseaseItem.name_id}"/>">
				<c:if test="${errors.name_id!=null}">
					<span class="text-danger">${errors.name_id}</span>
				</c:if>

				<p></p>
				病名<br> <input type="text" name="name_of_disease"
					id="name_of_disease" style="width: 700px"
					class="form-control<c:if test="${errors.name_of_disease!=null}"> is-invalid</c:if>"
					value="<c:out value="${diseaseItem.name_of_disease}"/>">
				<c:if test="${errors.name_of_disease!=null}">
					<span class="text-danger">${errors.name_of_disease}</span>
				</c:if>

				<p></p>
				症状<br>
				<textarea rows="10" cols="60" name="information" id="information"
					style="width: 1000px"
					class="form-control<c:if test="${errors.information!=null}"> is-invalid</c:if>"><c:out
						value="${diseaseItem.information}" /></textarea>
				<c:if test="${errors.information!=null}">
					<span class="text-danger">${errors.information}</span>
				</c:if>

				<p></p>
				参考リンク<br> <input type="text" name="link" id="link"
					style="width: 700px"
					class="form-control<c:if test="${errors.link!=null}"> is-invalid</c:if>"
					value="<c:out value="${diseaseItem.link}"/>">
				<c:if test="${errors.link!=null}">
					<span class="text-danger">${errors.link}</span>
				</c:if>

				<p></p>
				<input type="checkbox" id="is_deleted" name="is_deleted" value="1"><span>削除フラグ</span><br>

				<p></p>
				<p></p>
				<input type="submit" value="登録" name="addbtn"><br>
			</form>
		</div>

	</div>

</body>
</html>