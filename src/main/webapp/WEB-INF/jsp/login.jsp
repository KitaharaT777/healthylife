<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ page import="model.UserModel" %>
<% 
	//System.out.println("loginPage");	

	session.removeAttribute("adderrUser");
	//session.removeAttribute("adderrTodo");

	int loginflg=0;
	int loginerrflg=0;
	
	try{
		//セッションスコープからインスタンスを取得
		UserModel loginUser=(UserModel) session.getAttribute("loginUser");
		String loginflg_str=String.valueOf(loginUser.getLoginflg());
		
		UserModel loginerrUser=(UserModel) session.getAttribute("loginerrUser");
		String loginerrflg_str=String.valueOf(loginerrUser.getLoginflg());
	
		if(null==loginflg_str){
			loginflg=0;
		}else{
			loginflg=1;
			session.removeAttribute("loginUser");
			//System.out.println("XXXX");
		}
		
		if(null==loginerrflg_str){
			loginerrflg=0;
		}else{
			loginerrflg=1;
			//System.out.println("XXXX");
		}
	}catch(NullPointerException e) {
        e.printStackTrace();
    }
	System.out.println("loginflg(Login): " +loginflg);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- BootstrapのCSS読み込み -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
 
<title>ログイン</title>
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

<div class="container" style="padding:30px; display: flex; justify-content: center;">
<%-- 
<div class="user" style="border:solid gray 3px">
--%>
<div class="user">
<table border="1" style="width:500px;">
<tr><th><div class="blank" style="padding:20px; background-color:#EEEEEE;"><h4>Healthy Life（症状検索システム）<br>会員ログインへめんそ～れ</h4></div></th></tr>
<tr><td><div class="blank" style="padding:20px">

<c:if test="${not empty msg}" >
	<c:if test="${msg =='ログアウトしました'}">
	<div class="msg" style="padding:20px; background-color:#CCFF33;">
		<p>${msg}</p>
	</div>
	</c:if>
	<c:if test="${msg !='ログアウトしました'}">
	<div class="msg" style="padding:20px; background-color:#FFCCCC;">
		<p>${msg}</p>
	</div>
	</c:if>
<p></p>
</c:if>

<form action="/healthylife/Login" method="post">
E-mailアドレス<br><input type="text" name="email" style="width:460px" value="<c:out value="${loginerrUser.email}" />">
<p></p>
パスワード<br><input type="password" name="pass" style="width:460px" value="<c:out value="${loginerrUser.pass}" />">
<p></p>
<input type="submit" value="ログイン" style="margin-left:180px">
<input type="hidden" name="today" id="today"><%-- 今日の日付取得用は見えないようにする --%>
<input type="hidden" name="loginflg" value="0"><%-- ログインフラグ用は見えないようにする --%>
</form>

</div>
</td></tr>
</table>

<p></p>
<a href="/healthylife/UserRegister">新規会員登録はこちら</a>

<p></p>
<div class="comment" style="width:500px; color:red;">※検索した症状結果はあくまでも推測値であって確実なものではありません。不安な場合は医師の診断を受けてください。</div>

</div>
</div>
</body>
</html>