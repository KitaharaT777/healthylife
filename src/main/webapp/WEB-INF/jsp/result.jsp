<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
//System.out.println("ResultPage");
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

<title>症状検索結果</title>
</head>
<body>

	<div class="container" style="padding: 30px">
		<%-- header--%>
		<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

		<div style="padding-top: 10px">
			<h1>疑われる症状</h1>
			<div style="padding-left: 10px; color: red;">
				※必ずしも検索結果の病気であるわけではありません。<br> ※ご心配の場合は医師にご相談ください。
			</div>
		</div>

		<p></p>
		<div style="padding-left: 0px; font-weight: bold;">
			<h5>＜総評＞</h5>
		</div>
		<%-- 総評表示 --%>
		<c:forEach var="resultList" items="${resultList}">
			<div style="padding-left: 10px;">
				<c:out value="${resultList.comment}" />
			</div>
		</c:forEach>

		<div class="action_wrapper" style="padding-top: 15px;">
			<table>
				<tbody>
					<tr style="font-weight: bold">
						<td style="width: 100px">可能性</td>
						<td style="width: 100px">病名</td>
						<td style="width: 620px"></td>
						<td style="width: 80px"></td>
						<td style="width: 80px"></td>
						<td style="width: 80px"></td>
					</tr>
				</tbody>
			</table>

			<%-- 検索結果リスト --%>
			<c:forEach var="resultList" items="${resultList}">
				<form action="./DiseaseInfo" method="get">
					<table style="border-top: 1px solid black;">
						<tr>
							<td style="width: 100px"><c:out
									value="${resultList.result1Prob}" />％<input type="hidden"
								name="result1Prob"
								value="<c:out value="${resultList.result1Prob}" />"></td>
							<td style="width: 0px"><input type="hidden"
								name="result1NameId"
								value="<c:out value="${resultList.result1NameId}" />"></td>
							<c:forEach var="diseaseList" items="${diseaseList}">
								<c:if test="${resultList.result1NameId==diseaseList.id}">
									<td style="width: 620px"><c:out
											value="${diseaseList.nameOfDisease}" /><input type="hidden"
										name="nameOfDisease"
										value="<c:out value="${diseaseList.nameOfDisease}" />"></td>
									<td style="width: 0px"><input type="hidden" name="id"
										value="<c:out value="${diseaseList.id}" />"></td>
								</c:if>
							</c:forEach>
							<td style="width: 60px" align="center"><input type="hidden"
								name="name_id"
								value="<c:out value="${resultList.result1NameId}" />"> <input
								type="submit" value="詳細"
								id="action<c:out value="${resultList.result1NameId}" />"></td>
						</tr>
					</table>
				</form>
				<form action="./DiseaseInfo" method="get">
					<table style="border-top: 1px solid black;">
						<tr>
							<td style="width: 100px"><c:out
									value="${resultList.result2Prob}" />％<input type="hidden"
								name="result2Prob"
								value="<c:out value="${resultList.result2Prob}" />"></td>
							<td style="width: 0px"><input type="hidden"
								name="result2NameId"
								value="<c:out value="${resultList.result2NameId}" />"></td>
							<c:forEach var="diseaseList" items="${diseaseList}">
								<c:if test="${resultList.result2NameId==diseaseList.id}">
									<td style="width: 620px"><c:out
											value="${diseaseList.nameOfDisease}" /><input type="hidden"
										name="nameOfDisease"
										value="<c:out value="${diseaseList.nameOfDisease}" />"></td>
									<td style="width: 0px"><input type="hidden" name="id"
										value="<c:out value="${diseaseList.id}" />"></td>
								</c:if>
							</c:forEach>
							<td style="width: 60px" align="center"><input type="hidden"
								name="name_id"
								value="<c:out value="${resultList.result2NameId}" />"> <input
								type="submit" value="詳細"
								id="action<c:out value="${resultList.result2NameId}" />"></td>
						</tr>
					</table>
				</form>
				<form action="./DiseaseInfo" method="get">
					<table style="border-top: 1px solid black;">
						<tr>
							<td style="width: 100px"><c:out
									value="${resultList.result3Prob}" />％<input type="hidden"
								name="result3Prob"
								value="<c:out value="${resultList.result3Prob}" />"></td>
							<td style="width: 0px"><input type="hidden"
								name="result3NameId"
								value="<c:out value="${resultList.result3NameId}" />"></td>
							<c:forEach var="diseaseList" items="${diseaseList}">
								<c:if test="${resultList.result3NameId==diseaseList.id}">
									<td style="width: 620px"><c:out
											value="${diseaseList.nameOfDisease}" /><input type="hidden"
										name="nameOfDisease"
										value="<c:out value="${diseaseList.nameOfDisease}" />"></td>
									<td style="width: 0px"><input type="hidden" name="id"
										value="<c:out value="${diseaseList.id}" />"></td>
								</c:if>
							</c:forEach>
							<td style="width: 60px" align="center"><input type="hidden"
								name="name_id"
								value="<c:out value="${resultList.result3NameId}" />"> <input
								type="submit" value="詳細"
								id="action<c:out value="${resultList.result3NameId}" />"></td>
						</tr>
					</table>
				</form>
			</c:forEach>
		</div>

		<form action="./Main" method="post">
			<div class="check" style="padding-top: 10px; font-weight: bold;">
				<input type="checkbox" id="new_mark" name="new_mark" value="1" />
				気になる
				<div style="padding-left: 30px; font-weight: normal;">「症状検索履歴に戻る」を選択した場合のみ、チェックした症状検索結果の「気になる」登録がされます。</div>
			</div>

			<div>
				<p></p>
				<div style="margin-left: 0px;">
					<input type="submit" value="症状検索履歴に戻る" id="return_result">
				</div>
			</div>
		</form>
	</div>

</body>
</html>