<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ 
	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<title>ユーザー退会</title>
</head>
<body>

	<div class="container" style="padding: 30px">
		<%-- header--%>
		<jsp:include page="/WEB-INF/jsp/include/header.jsp" />

		<div class="user"
			style="padding: 30px; display: flex; justify-content: center;">
			<table border="1" style="width: 500px;">

				<tr>
					<th><div class="blank"
							style="padding: 20px; background-color: #EEEEEE;">
							<h4>退会確認</h4>
						</div></th>
				</tr>

				<tr>
					<td><div class="blank" style="padding: 20px">
							<form action="/healthylife/UserWithdraw" method="post">
								<div style="margin-top: 20px; margin-left: 110px">ユーザー情報を削除しますか？</div>
								<p></p>
								<div style="margin-top: 20px; margin-left: 120px">
									<input type="radio" style="margin-left: 30px"
										id="is_withdrawaled" name="is_withdrawaled" value="0">はい
									<input type="radio" style="margin-left: 40px"
										id="is_withdrawaled" name="is_withdrawaled" value="1" checked>いいえ
								</div>
								<p></p>
								<input type="submit" value="実行"
									style="margin-top: 20px; margin-left: 200px">
							</form>

						</div></td>
				</tr>
			</table>
		</div>

		<div style="margin-left: 290px;">
			<p>
				<a href="/healthylife/Main">症状検索履歴に戻る</a>
			</p>
		</div>

	</div>

</body>
</html>