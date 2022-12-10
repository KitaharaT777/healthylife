<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 初回のメインページ表示に必要 --%>
<%@ page import="model.UserModel"%>

<%
//System.out.println("mainPage");	

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

<title>症状検索履歴</title>
</head>
<body>

	<script type="text/javascript">
 	//「気になる」Checkboxのon/offでDB更新の処理
        function checkfunc(value) {
        
        console.log("You have bought "+value); //HTMLのF12検証モードで表示
        //System.out.println("You have bought "+value);
        
	     // app.js（DB接続したい　実装保留）
        const mysql = require('mysql');
        const connection = mysql.createConnection({
          host: 'ホスト名',
          user: 'ユーザー名',
          password: 'パスワード',
          database: 'データベース名'
        });
        connection.connect((err) => {
          if (err) throw err;
          console.log('Connected!');
        });
        console.log("SQL接続");
        
        }         
</script>

	<div class="container" style="padding: 30px">
		<%-- header--%>
		<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

		<div style="padding-top: 10px">
			<h1>症状検索履歴</h1>
		</div>
		<%--onclick確認用<input type="checkbox" id="mark" name="mark" value="0" onchange="checkfunc(this.value)" />test
	--%>

		<div class="action_wrapper" style="padding-top: 5px;">
			<table>
				<tbody>
					<tr style="font-weight: bold">
						<td style="width: 80px"></td>
						<td style="width: 100px"></td>
						<td style="width: 393px"></td>
						<td style="width: 40px">TOP</td>
						<td style="width: 110px"></td>
						<td style="width: 40px"></td>
						<td style="width: 110px"></td>
						<td style="width: 40px"></td>
						<td style="width: 110px"></td>
					</tr>
					<tr style="font-weight: bold">
						<td style="width: 80px">気になる</td>
						<td style="width: 100px">検索日</td>
						<td style="width: 393px">検索ワード</td>
						<td style="width: 40px">1</td>
						<td style="width: 110px"></td>
						<td style="width: 40px">2</td>
						<td style="width: 110px"></td>
						<td style="width: 40px">3</td>
						<td style="width: 110px"></td>
					</tr>
				</tbody>
			</table>

			<%-- 一覧表示 --%>
			<c:forEach var="searchList" items="${searchList}">
				<%-- 実行ボタンのform --%>
				<form action="./Main" method="post">
					<%-- 削除フラグ1は表示しない　--%>
					<c:if test="${searchList.isDeleted==0}">
						<input type="hidden" name="idno"
							value="<c:out value="${searchList.id}" />">
						<%-- 実行ボタンNoは見えないようにする --%>

						<table style="border-top: 1px solid black;">

							<%-- 検索結果リスト --%>
							<tr>
								<c:if test="${searchList.mark==0}">
									<td style="width: 80px; padding-left: 20px;"><input
										type="checkbox" id="mark" name="mark" value="1"
										onchange="checkfunc(this.value)" onclick="return false;" /></td>
								</c:if>
								<c:if test="${searchList.mark==1}">
									<td style="width: 80px; padding-left: 20px;"><input
										type="checkbox" id="mark" name="mark" value="1" checked
										onchange="checkfunc(this.value)" onclick="return false;" /></td>
								</c:if>

								<td style="width: 100px"><c:out
										value="${searchList.searchDate}" /><input type="hidden"
									name="searchDate"
									value="<c:out value="${searchList.searchDate}" />"></td>
								<td style="width: 400px"><c:out value="${searchList.word}" /><input
									type="hidden" name="word"
									value="<c:out value="${searchList.word}" />"></td>

								<td style="width: 40px"><c:out
										value="${searchList.result1Prob}" />％</td>
								<c:forEach var="diseaseList" items="${diseaseList}">
									<%-- NameIDでなくid で一致させないといけない　--%>
									<c:if test="${searchList.result1NameId==diseaseList.id}">
										<td style="width: 110px"><c:out
												value="${diseaseList.nameOfDisease}" /><input type="hidden"
											name="nameOfDisease"
											value="<c:out value="${diseaseList.nameOfDisease}" />"></td>
									</c:if>
								</c:forEach>

								<td style="width: 40px"><c:out
										value="${searchList.result2Prob}" />％</td>
								<c:forEach var="diseaseList" items="${diseaseList}">
									<c:if test="${searchList.result2NameId==diseaseList.id}">
										<td style="width: 110px"><c:out
												value="${diseaseList.nameOfDisease}" /><input type="hidden"
											name="nameOfDisease"
											value="<c:out value="${diseaseList.nameOfDisease}" />"></td>
									</c:if>
								</c:forEach>

								<td style="width: 40px"><c:out
										value="${searchList.result3Prob}" />％</td>
								<c:forEach var="diseaseList" items="${diseaseList}">
									<c:if test="${searchList.result3NameId==diseaseList.id}">
										<td style="width: 110px"><c:out
												value="${diseaseList.nameOfDisease}" /><input type="hidden"
											name="nameOfDisease"
											value="<c:out value="${diseaseList.nameOfDisease}" />"></td>
									</c:if>
								</c:forEach>
								<td style="width: 60px" align="center"><input type="submit"
									value="削除" id="action<c:out value="${searchList.id}" />"></td>

							</tr>
						</table>

					</c:if>
				</form>
			</c:forEach>
		</div>
	</div>

</body>
</html>