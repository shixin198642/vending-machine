<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String context = request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta http-equiv="Content-Type" content="text/html; charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
<title>首页</title>
<link type="text/css" rel="stylesheet" href="<%=context %>/css/reset.css">
<link type="text/css" rel="stylesheet" href="<%=context %>/css/layout.css">
<link type="text/css" rel="stylesheet" href="<%=context %>/css/jquery-ui.css">
<link type="text/css" rel="stylesheet" href="<%=context %>/css/uploadify.css">
<script>
var context = '<%=context%>';
</script>
<script src="<%=context %>/js/jquery-3.0.0.min.js"></script>
<script src="<%=context %>/js/jquery-ui.js"></script>
<script src="<%=context %>/js/base.js"></script>
</head>
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Expires","0");
%>
<body>
<div class="wrapper">
	<div class="wrap clearfix">
		<%@include file="left.jsp" %>
		<div class="layout">
		   <%@include file="header.jsp" %>