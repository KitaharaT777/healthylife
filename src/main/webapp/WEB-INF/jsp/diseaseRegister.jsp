<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>

<%-- 初回のメインページ表示に必要 --%>
<%@ page import="dao.AddDAO" %>
<%@ page import="model.GetListLogic" %>
<%@ page import="model.TodoList" %>
<%@ page import="model.UserModel" %>
<%@ page import="java.util.List" %>
<% 
	//セッションスコープからインスタンスを取得
	//HttpSession session=request.getSession();
	UserModel loginUser=(UserModel) session.getAttribute("loginUser");
	//Todoリストを取得してリクエストスコープに保存
	GetListLogic getListLogic=new GetListLogic();
	List<TodoList> todoList=getListLogic.execute(loginUser);
	request.setAttribute("todoList", todoList);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- BootstrapのCSS読み込み -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
 
<title>病名登録</title>
</head>
<body>

<div class="container" style="padding:30px">
	<%-- header--%>
	<jsp:include page="/WEB-INF/jsp/include/headerAdmin.jsp" />
	
	<div style="padding-top:10px;"><h1>病名登録</h1></div>

	<div class="add_wrapper" style="padding-top:5px; font-weight:bold;">
	<form action="/healthylife/DiseaseRegister" method="post">
	<p>登録日</p>
	<input type="date" name="registration_date" style="width:200px" value="<c:out value="${adderrTodo.registration_date}" />">
	<c:if test="${not empty msgReg}" ><div style="color:red"><p>${msgReg}</p></div>
	</c:if>
	<p></p>
	<p>病名ID</p>
	<input type="text" name="name_id" style="width:700px" value="<c:out value="${adderrTodo.todo_item}" />">
	<c:if test="${not empty msgTodo}" ><div style="color:red"><p>${msgTodo}</p></div>
	</c:if>
	<p></p>
	<p>病名</p>
	<input type="text" name="name_of_disease" style="width:700px" value="<c:out value="${adderrTodo.todo_item}" />">
	<c:if test="${not empty msgTodo}" ><div style="color:red"><p>${msgTodo}</p></div>
	</c:if>
	<p></p>
	<p>症状</p>
	<textarea rows="10" cols="60" name="information" style="width:700px" value="<c:out value="${adderrTodo.todo_item}" />"></textarea>
	<c:if test="${not empty msgTodo}" ><div style="color:red"><p>${msgTodo}</p></div>
	</c:if>
	<p></p>
	<p>期限日</p>
	<input type="date" name="expiration_date" style="width:200px" value="<c:out value="${adderrTodo.expiration_date}" />">
	<%--初期値今日<input type="date" name="expiration_date" style="width:200px" id="today2">--%>
	<c:if test="${not empty msgExpi}" ><div style="color:red"><p>${msgExpi}</p></div>
	</c:if>
	<p></p>
	
	<input type="checkbox" id="comlete" name="complete" value="1"><span>完了にする</span><br>
	<p></p>
	<input type="hidden" name="today" id="today"><%-- 今日の日付取得用は見えないようにする --%>
	<p></p>
	<input type="submit" value="登録" name="addbtn"><br>
	</form>
	</div>

</div>

</body>
</html>