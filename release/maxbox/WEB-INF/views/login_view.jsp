<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String context = request.getContextPath();
String username = (String)request.getAttribute("username");
if(username==null){
	username = "";
}
%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
<title>登录</title>
<script>
var context = '<%=context%>';
</script>
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
				<form id="login_form">
				<dl class="clearfix">
					<dt>账号：</dt>
					<dd>
						<label for="" class="labelbox">
							<input type="text" name="username" id="username" value="<%=username%>"/>
						</label>
					</dd>
					<dt>密码：</dt>
					<dd>
						<label for="" class="labelbox">
							<input type="password" name="password" id="password"/>
						</label>
						<a class="link-style" href="" title="忘记密码">忘记密码？</a>
					</dd>
					<dt></dt>
					<dd>
						<label>
						<input type="hidden" name="remember_login" id="remember_login" value="n"/>
						<input class="checkbox" type="checkbox" onclick="checkRemember(this.checked)"/>记住我
						</label>
					</dd>
				</dl>
				</form>
				<div class="error-tip" id="error_tip" style="display:none">用户名错误</div>
			</div>
			<div class="tip-btns">
				<a class="btn-login" href="javascript:void(0)" onclick="login()" title="登录">登录</a>
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
<script src="<%=context%>/js/jquery-3.0.0.min.js"></script>
<script src="<%=context%>/js/base.js"></script>
<script src="<%=context%>/js/login.js"></script>
</html>