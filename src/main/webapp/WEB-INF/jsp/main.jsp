<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>

<%-- 初回のメインページ表示に必要 --%>
<%@ page import="dao.AddDAO" %>
<%@ page import="model.GetListLogic" %>
<%@ page import="model.TodoList" %>
<%@ page import="model.Account" %>
<%@ page import="model.UserModel" %>
<%@ page import="java.util.List" %>
<%@ page import="servlet.MainServlet" %>
<%@ page import="model.TodoSearch" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>

<% 
	System.out.println("mainPage");	

	session.removeAttribute("adderrTodo");
	session.removeAttribute("loginerrUser");

	//セッションスコープからインスタンスを取得
	UserModel loginUser=(UserModel) session.getAttribute("loginUser");
	
	TodoSearch todosearch=(TodoSearch) session.getAttribute("search");
	System.out.println("FlgSearch(): " +todosearch.getFlgSearch());
	
	/*
	String key=todosearch.getTodoSearch();

	if(todosearch.getFlgSearch()==0){
	//Todoリストを取得してリクエストスコープに保存
		GetListLogic getListLogic=new GetListLogic();
		List<TodoList> todoList=getListLogic.execute(loginUser);
		request.setAttribute("todoList", todoList);
	}else if(todosearch.getFlgSearch()==1){ //Search実行時
		if(key.length()==0){
			GetListLogic getListLogic=new GetListLogic();
			List<TodoList> todoList=getListLogic.execute(loginUser);
			request.setAttribute("todoList", todoList);
		}else{
		GetListLogic getListLogic=new GetListLogic();
		List<TodoList> todoList=getListLogic.executeSearch(loginUser,todosearch.getTodoSearch());
		request.setAttribute("todoList", todoList);
		}
	}
	*/
	/*
	//期限日チェック(必要か？)
	String todaydate=request.getParameter("today");
	System.out.println("TodayMainjsp:"+todaydate);
	
	Calendar date=Calendar.getInstance();	
	System.out.println("DateMainjsp:"+date.get(Calendar.YEAR)+date.get(Calendar.MONTH)+date.get(Calendar.DATE));
	String today= String.valueOf(date.get(Calendar.YEAR))+"-"+String.valueOf((date.get(Calendar.MONTH)+1))+"-"+String.valueOf(date.get(Calendar.DATE));
	System.out.println("DateMainjsp2:"+today);
	String date1="2022-12-10";
	System.out.println("DateCompare:"+ today.compareTo(date1));
	String date2="2022-10-10";
	System.out.println("DateCompare:"+ today.compareTo(date2));
	String date3="2022-10-12";
	System.out.println("DateCompare:"+ today.compareTo(date3));
	*/
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- BootstrapのCSS読み込み -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
 
<title>症状検索履歴</title>
</head>
<body>
<script type="text/javascript">
    //今日の日時を表示
        window.onload = function () {
            //今日の日時を表示
            var date = new Date()
            var year = date.getFullYear()
            var month = date.getMonth() + 1
            var day = date.getDate()
          
            var toTwoDigits = function (num, digit) {
              num += ''
              if (num.length < digit) {
                num = '0' + num
              }
              return num
            }
            
            var yyyy = toTwoDigits(year, 4)
            var mm = toTwoDigits(month, 2)
            var dd = toTwoDigits(day, 2)
            var ymd = yyyy + "-" + mm + "-" + dd;
            
            document.getElementById("today").value = ymd;
        }
    ;
    
</script>

<div class="container" style="padding:30px">
	<%-- header--%>
	<jsp:include page="/WEB-INF/jsp/include/header.jsp" />
	
	<div style="padding-top:10px"><h1>検索履歴</h1></div>
	<input type="hidden" name="today" id="today"><%-- 今日の日付取得用は見えないようにする --%>

	<div class="action_wrapper" style="padding-top:5px;">
	<table>
	<tbody>
	<tr style="font-weight:bold">
		<td style="width:80px"></td>
		<td style="width:100px"></td>
		<td style="width:620px"></td>
		<td style="width:80px">TOP</td>
		<td style="width:80px"></td>
		<td style="width:80px"></td>
	</tr>
	<tr style="font-weight:bold">
		<td style="width:80px">マーク</td>
		<td style="width:100px">年月日</td>
		<td style="width:620px">検索ワード</td>
		<td style="width:80px">1</td>
		<td style="width:80px">2</td>
		<td style="width:80px">3</td>
	</tr>
	</tbody>
	</table>

	<%-- 一覧表示 --%>
	<c:forEach var="searchList" items="${searchList}">
		<%-- 実行ボタンのform --%>
		<form action="./UpdateMove" method="post">
		<%-- 削除フラグ1は表示しない　--%>
		<c:if test="${searchList.isDeleted==0}">
		<input type="hidden" name="idno" value="<c:out value="${searchList.id}" />"><%-- 実行ボタンNoは見えないようにする --%>
		
		<table style="border-top: 1px solid black;">
		
		<%-- 検索結果リスト --%>
		<tr>
		   <td style="width:80px; padding-left:20px;"><input type="checkbox" id="mark" name="mark" value="1"></td>

		   <td style="width:100px"><c:out value="${searchList.searchDate}" /><input type="hidden" name="searchDate" value="<c:out value="${searchList.searchDate}" />"></td>
		   <td style="width:620px"><c:out value="${searchList.word}" /><input type="hidden" name="word" value="<c:out value="${searchList.word}" />"></td>
		   <%-- <td style="width:100px"><c:out value="${searchList.expiration_date}" /><input type="hidden" name="expiration_date" value="<c:out value="${searchList.expiration_date}" />"></td>
		   <td style="width:100px"><c:out value="${searchList.finished_date}" /><input type="hidden" name="finished_date" value="<c:out value="${searchList.finished_date}" />"></td>
	  --%>
		   <td style="width:60px" align="center"><input type="submit" value="削除" id="action<c:out value="${todoList.id}" />"></td>
		   
		</tr>
		
		</table>
		
		</c:if>
		</form>
	</c:forEach>
	</div>
</div>

</body>
</html>