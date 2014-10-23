<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<title>Login Page</title>
</head>
<body>
	<!--
	<div id="formsContent">
		<h2>admin login</h2>
		<form action="<c:url value="/j_spring_security_check" />"
			class="admin-login-form" method="post">
			<fieldset>
				<legend>admin login</legend>
				User ID<input type="text" name="j_username" /> Password<input
					type="password" name="j_password" /> Remember Me<input
					type="checkbox" name="_spring_security_remember_me" />
				<button type="submit">Submit</button>
				<button type="reset">reset</button>
			</fieldset>
		</form>
	</div>
-->
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4">
				<h1 class="text-center login-title">sign in your account</h1>
				<div class="account-wall">
					<form class="form-signin" action="j_spring_security_check" method="post">
						<input type="text" class="form-control" placeholder="User Name"
							required autofocus name="j_username"> 
						<input type="password"
							class="form-control" placeholder="Password" required name="j_password">
						
						<label class="checkbox pull-left"> <input type="checkbox"
							value="remember-me" name="_spring_security_remember_me"> Remember me
						</label>
						<button class="btn btn-lg btn-primary btn-block" type="submit">
							Sign in</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>