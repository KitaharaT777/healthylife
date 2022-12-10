<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- ページのタイトルを設定する --%>
<%
pageContext.setAttribute("title", "エラー", PageContext.PAGE_SCOPE);
%>

<!DOCTYPE html>
<html lang="jp">

<head>
<meta charset="UTF-8">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- BootstrapのCSS読み込み -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">

</head>

<body>
	<div class="container" style="padding: 30px">
		<%-- <div class="container-md">--%>

		<%-- navbar --%>
		<%--<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
		--%>
		<c:if test="${loginUser.id!=1}">
			<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
		</c:if>
		<c:if test="${loginUser.id==1}">
			<jsp:include page="/WEB-INF/jsp/include/headerAdmin.jsp" />
		</c:if>

		<p></p>
		<p></p>

		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="alert alert-danger" role="alert">エラーが発生しました。</div>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>

	<%-- navbarで使用するJavaScript --%>
	<%--<%@ include file="/WEB-INF/jsp/include/js.jsp"%></body>
	--%>

</body>
</html>