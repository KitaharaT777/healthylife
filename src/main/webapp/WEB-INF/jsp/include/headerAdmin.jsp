<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.UserModel" %>
<%@ page import="model.Account" %>
<%@ page import="model.TodoSearch" %>
<%@ page import="model.TodoList" %>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<% 
	//System.out.println("headerPage");	

	//セッションスコープからインスタンスを取得
	UserModel loginUser=(UserModel) session.getAttribute("loginUser");
	TodoSearch todosearch=(TodoSearch) session.getAttribute("search");
	TodoList todoList2=(TodoList) session.getAttribute("todosearch");
	//System.out.println("Headtodosearch(): " +todosearch.getTodoSearch());
	int flg=0;
	String key=todosearch.getTodoSearch();
	//String key="";
	System.out.println("key: " +key);
	//System.out.println("keylength: " +key.length());
	if(null==key){
		flg=1;
		key="";
	}else{
		System.out.println("keylength: " +key.length());
	}
	System.out.println("HeaderFlg: " +flg);
%>
<div class="header" style="transform:scale(1,1); height:140px; margin-top:-30px; padding:10px; background-color:#EEEEEE;">

<div style="float:left; margin-left:10px;"><h3>HealthyLife</h3></div>

<div class="menu-left" style="padding:5px;">
<div style="float:left; margin-left:10px;"><p>ようこそ、<%= loginUser.getEmail() %>さん</p></div>
<div style="float:left; margin-left:20px;"><a href="/healthylife/DiseaseRegister">病名追加</a></div>
<div style="float:left; margin-left:20px;"><a href="/healthylife/Add">病名一覧</a></div>
<div style="float:left; margin-left:20px;"><a href="/healthylife/UserRegister">アカウント追加</a></div>
<div style="float:left; margin-left:20px;"><a href="/healthylife/UserList">アカウント一覧</a></div>
<%-- <div style="float:left;"><p>ID:  <%= loginUser.getUserId() %>     NAME:  <%= loginUser.getUserName() %></p></div>--%>

<div style="float:left; margin-left:310px;"><p><a href="/healthylife/UserUpdate">管理者情報修正</a></p></div>

<form action="./Logout" method="post">
<div style="float:left; margin-left:10px;"><p><a href="/healthylife/Login">ログアウト</a></p></div>
</form>

<div style="clear:both; margin-left:10px;">検索ワード：単語の間に全角スペースをいれてください</div>

<%-- 検索 --%>
<div class="search">
<form action="./Main" method="get">
	
	<div style="float:left; margin-left:10px;">
	<input type="search" name="todosearch" value="<%= key %>" style="width:800px">
	</div>
	
	<div style="float:left; margin-left:10px;"><input type="submit" value="Search"></div>
	
</form>
<c:if test="${not empty msgSearch}" ><div style="color:red"><p>${msgSearch}</p></div>
</c:if>
</div>
	
</div>


</div>