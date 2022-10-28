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
<%-- <%@ page import="org.apache.commons.lang3.time.DateUtils" %>
--%>
<% 
	System.out.println("mainAdminPage");	

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
 
<title>HealthyLife</title>
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
	<jsp:include page="/WEB-INF/jsp/include/headerAdmin.jsp" />
	
	<div style="padding-top:10px"><h1>病名情報一覧</h1></div>
	<input type="hidden" name="today" id="today"><%-- 今日の日付取得用は見えないようにする --%>
	
	<div class="action_wrapper" style="padding-top:5px;">
	
	<table>
	<tbody><tr style="font-weight:bold">
		<td style="width:100px">ID</td>
		<td style="width:700px">病名</td>
		<td style="width:100px"></td>
		<td style="width:100px"></td>
		<td style="width:80px">詳細/修正</td>
	</tr></tbody>
	</table>

	<%-- 一覧表示 --%>
	<c:forEach var="diseaseList" items="${diseaseList}">
		<%-- 実行ボタンのform --%>
		<form action="./UpdateMove" method="post">
		<%-- 削除フラグ1は表示しない　--%>
		<c:if test="${diseaseList.isDeleted==0}">
		
		<input type="hidden" name="idno" value="<c:out value="${diseaseList.id}" />"><%-- 実行ボタンNoは見えないようにする --%>
		
		<table style="border-top: 1px solid black;">
		
		<%-- Diseaseリスト --%>
		<tr>
		   <td style="width:100px"><c:out value="${diseaseList.nameId}" /><input type="hidden" name="name_id" value="<c:out value="${diseaseList.nameId}" />"></td>
		   <td style="width:900px"><c:out value="${diseaseList.nameOfDisease}" /><input type="hidden" name="name_of_disease" value="<c:out value="${diseaseList.nameOfDisease}" />"></td>
		   <%-- <td style="width:100px"><c:out value="${diseaseList.expiration_date}" /><input type="hidden" name="expiration_date" value="<c:out value="${diseaseList.expiration_date}" />"></td>
		   <td style="width:100px"><c:out value="${diseaseList.finished_date}" /><input type="hidden" name="finished_date" value="<c:out value="${diseaseList.finished_date}" />"></td>
		   --%>
		   <td style="width:80px" align="center"><input type="submit" value="詳細/修正" id="action<c:out value="${diseaseList.id}" />"></td>
		</tr>
		
		</table>
		
		</c:if>
		</form>
	</c:forEach>
	</div>
</div>

</body>
</html>