<%@page import="com.mjitech.constant.WebPageConstants"%>
<%@ page pageEncoding="UTF-8"%>
<%
String context = request.getContextPath();
%>
<!DOCTYPE html> 
<html> 
	<head> 
	<title>支付</title> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1"> 

	<link rel="stylesheet" href="<%=context %>/css/jquery.mobile.min.css" />
    <style>
	.ui-font-gray{color:#999; font-size:12px;}
	</style>
	<script type="text/javascript" src="<%=context %>/js/jquery-1.6.4.min.js"></script>
	<script type="text/javascript" src="<%=context %>/js/jquery.mobile.min.js"></script>
</head> 
<div id="user" data-role="page">

	<div data-role="header">
    	
		<h1>支付成功</h1>
	</div><!-- /头部 -->

	<div data-role="content">	
		<form>
        	<ul data-role="listview" data-inset="true">
                <li data-role="fieldcontain">
                    	支付成功
                    
                    
                </li>
                <li data-role="fieldcontain">
                    <input type="submit" value="确定" data-theme="b" />
                </li>
            </ul>
        </form>	
	</div><!-- /内容 -->

	<div data-role="footer">
		<h4>By 华沁数码</h4>
	</div><!-- /底部 --></div><!-- /页面 -->

</body>
</html>
