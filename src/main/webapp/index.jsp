<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String context = request.getContextPath();
%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
<title>登录</title>
<link type="text/css" rel="stylesheet" href="<%=context %>/css/reset.css">
<link type="text/css" rel="stylesheet" href="<%=context %>/css/login.css">
</head>
<body>
<div class="wrapper">
	<div class="wrap">
	<div class="login-panel">
		<h3><span class="logo"></span>仓储管理系统</h3>
		<!-- 登录 s -->
		<div class="login-container">
			<div class="loginbox">
				<dl class="clearfix">
					<dt>账号：</dt>
					<dd>
						<label for="" class="labelbox">
							<input type="text">
						</label>
					</dd>
					<dt>密码：</dt>
					<dd>
						<label for="" class="labelbox">
							<input type="password">
						</label>
						<a class="link-style" href="" title="忘记密码">忘记密码？</a>
					</dd>
					<dt></dt>
					<dd>
						<label><input class="checkbox" type="checkbox">记住我</label>
					</dd>
				</dl>
				<!-- <div class="error-tip">用户名错误</div> -->
			</div>
			<div class="tip-btns">
				<a class="btn-login" href="" title="登录">登录</a>
			</div>
		</div>
		<!-- 登录 e -->
		<!-- 找回密码 s -->
		<div style="display:none;" class="login-container">
			<div class="loginbox">
				<dl class="clearfix">
					<dt>Email:</dt>
					<dd>
						<label for="" class="labelbox">
							<input type="text">
						</label>
					</dd>
				</dl>
			</div>
			<div class="tip-btns">
				<a class="btn-login" href="" title="发送密码">发送密码</a>
				<a class="link-style" href="" title="返回登录">返回登录</a>
			</div>
		</div>
		<!-- 找回密码 e -->
		<!-- 找回密码 s -->
		<div style="display:none;" class="login-container">
			<div class="findpwd-box">
				<p class="t-c">密码已发送到你的邮箱 example@example.com，请及时查收。</p>
			</div>
			<div class="tip-btns">
				<a class="btn-login" href="" title="登录">登录</a>
			</div>
		</div>
		<!-- 找回密码 e -->
	</div>
	</div>
</div>
</body>
</html>