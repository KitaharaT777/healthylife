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
		
	//押した更新ボタンのTodoList情報
		TodoList todolist=(TodoList) session.getAttribute("todolist");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- BootstrapのCSS読み込み -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
 
<title>病名詳細および修正</title>
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
	
	<div style="padding-top:10px;"><h1>TODO LIST 更新</h1></div>

	<div class="add_wrapper" style="padding-top:5px; font-weight:bold;">
	<form action="/healthylife/diseaseUpdate" method="post">
	
	<%-- <p>TODOid</p> --%>
	<input type="hidden" name="idno" value="<%= todolist.getId() %>">
	
	<p>登録日</p>
	<input type="date" name="registration_date" style="width:200px" value="<%= todolist.getRegistration_date() %>"><br>
	<c:if test="${not empty msgReg}" ><div style="color:red"><p>${msgReg}</p></div>
	</c:if>
	<p></p>
	<p>TODO項目</p>
	<input type="text" name="todo" style="width:700px" value="<%= todolist.getTodo_item() %>"><br>
	<c:if test="${not empty msgTodo}" ><div style="color:red"><p>${msgTodo}</p></div>
	</c:if>
	<p></p>
	<p>期限日</p>
	<input type="date" name="expiration_date" style="width:200px" value="<%= todolist.getExpiration_date() %>"><br>
	<c:if test="${not empty msgExpi}" ><div style="color:red"><p>${msgExpi}</p></div>
	</c:if>
	
	<p></p>
	<%--<p>完了日</p>--%>
	<input type="hidden" name="finished_date" value="<%= todolist.getFinished_date() %>">
	<c:if test="${todolist.finished_date == ''}" var="flg" />
	<c:if test="${!flg}" ><input type="checkbox" id="comlete" name="complete" value="1" checked><span>完了にする</span><br></c:if>
	<c:if test="${flg}" ><input type="checkbox" id="comlete" name="complete" value="1" ><span>完了にする</span><br></c:if>
	<input type="hidden" name="today" id="today"><%-- 今日の日付取得用は見えないようにする --%>
	
	<p></p>
	<input type="checkbox" id="delete" name="delete" value="1"><span>削除する</span><br>
	<p></p>
	<input type="submit" value="更新" name="updatebtn"><br>
	</form>
	</div>

	<div class="action_wrapper" style="padding-top:30px;">
	<%--<table>
	<tbody><tr style="font-weight:bold">
		<td style="width:100px">登録日</td>
		<td style="width:300px">TODO項目</td>
		<td style="width:100px">期限日</td>
		<td style="width:100px">完了日</td>
		<td style="width:60px" align="center">削除</td>
		<td style="width:60px"></td>
	</tr></tbody>
	</table>
	--%>
	<%-- 一覧表示 --%>
	<%-- 
	<c:forEach var="todoList" items="${todoList}">
		<%-- 実行ボタンのform --%>
	<%-- 	<form action="./Action" method="post">
	--%>	<%-- 削除フラグ1は表示しない　--%>
	<%-- 	<c:if test="${todoList.is_deleted==0}">
		<input type="hidden" name="idno" value="<c:out value="${todoList.id}" />"><%-- 実行ボタンNoは見えないようにする --%>
	<%-- 	<table><tr>
	--%>	   <%--<c:if test="${todoList.is_completed==0}"><td style="width:100px"></c:if><c:if test="${todoList.is_completed==1}"><td style="width:100px; text-decoration: line-through 1px solid #ff0000;"></c:if><c:out value="${todoList.expiration_date}" /></td>
		   <c:if test="${todoList.is_completed==0}"><td style="width:300px"></c:if><c:if test="${todoList.is_completed==1}"><td style="width:300px; text-decoration: line-through 1px solid #ff0000;"></c:if><c:out value="${todoList.todo_item}" /></td>
	       斜線なしで表示させる--%>
	 <%--  	   <td style="width:100px"><c:out value="${todoList.registration_date}" /></td>
		   <td style="width:300px"><c:out value="${todoList.todo_item}" /></td>
		   <td style="width:100px"><c:out value="${todoList.expiration_date}" /></td>
		   <td style="width:100px"><c:out value="${todoList.finished_date}" /></td>
	 --%>  
		   <%-- <c:out value="${todoList.is_completed}" /> 確認用
		   <td style="width:60px" align="center"><input type="radio" id="complete<c:out value="${todoList.id}" />" name="complete" value="0" <c:if test="${todoList.is_completed==0}"> checked </c:if>></td>
		   <td style="width:60px" align="center"><input type="radio" id="complete<c:out value="${todoList.id}" />" name="complete" value="1" <c:if test="${todoList.is_completed==1}"> checked </c:if>></td>
	   	　　完了フラグは表示--%>
	   
		   <%-- <c:out value="${todoList.is_deleted}" /> 確認用--%>
	<%--	   <td style="width:60px" align="center"><input type="checkbox" id="delete<c:out value="${todoList.id}" />" name="delete" value="1" <c:if test="${todoList.is_deleted==1}"> checked </c:if>></td>
	  
		   <td style="width:60px" align="center"><input type="submit" value="更新" id="action<c:out value="${todoList.id}" />"></td>
		</tr></table>
		</c:if>
		</form>
	</c:forEach>
	--%>
	</div>
</div>

</body>
</html>