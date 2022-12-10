<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.UserModel"%>

<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
//System.out.println("headerPage");	

//セッションスコープからインスタンスを取得
UserModel loginUser = (UserModel) session.getAttribute("loginUser");
%>
<div class="header"
	style="transform: scale(1, 1); height: 145px; margin-top: -30px; padding: 10px; background-color: #EEEEEE;">

	<div style="float: left; margin-left: 10px;">
		<h3>HealthyLife</h3>
	</div>

	<div class="menu-left" style="padding: 5px;">
		<%-- UserIDは見えないようにする --%>
		<input type="hidden" id="id" name="id"
			value="<%=loginUser.getId()%>">
		<div style="float: left; margin-left: 10px;">
			<p>
				ようこそ、<%=loginUser.getEmail()%>さん
			</p>
		</div>

		<div style="float: left; margin-left: 300px;">
			<p>
				<a href="/healthylife/Main">検索履歴</a>
			</p>
		</div>
		<div style="float: left; margin-left: 20px;">
			<p>
				<a href="/healthylife/UserUpdate">会員情報修正</a>
			</p>
		</div>

		<form action="./Logout" method="post">
			<div style="float: left; margin-left: 20px;">
				<p>
					<a href="/healthylife/Login">ログアウト</a>
				</p>
			</div>
		</form>

		<div style="clear: both; margin-left: 10px;">検索ワード：単語の間に全角スペースをいれてください</div>

		<%-- 検索 --%>
		<div class="search">
			<form action="./Main" method="get">

				<div style="float: left; margin-left: 10px;">
					<input type="search" name="key" id="key" style="width: 800px"
						class="form-control<c:if test="${errors.key!=null}"> is-invalid</c:if>"
						<c:if test="${errors.key!=null}">value="<c:out value="${key.key}"/>"</c:if>
						<c:if test="${errors.key==null}">value="<c:out value="${key}"/>"</c:if>>
				</div>

				<div style="float: left; margin-left: 10px;">
					<input type="submit" value="Search">
				</div>

				<%-- エラーメッセージ --%>
				<c:if test="${errors.key!=null}">
					<div style="clear: both; margin-left: 10px; padding-bottom: 12px;">
						<span class="text-danger">${errors.key}</span>
						<p></p>
					</div>
				</c:if>

			</form>
		</div>

	</div>

</div>