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

<%-- head部を読み込む --%>
<%--<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
--%>
<body>
	<div class="container-md">

		<%-- navbar --%>
		<%@ include file="/WEB-INF/jsp/include/header.jsp"%>

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
</html>