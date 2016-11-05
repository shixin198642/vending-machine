<%@ page pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<% Map<String,String> params = (Map<String,String>)request.getAttribute("params");%>
<%for(String key : params.keySet()){ %>
<%=key %>:<%=params.get(key) %><br/>
<%} %>
this is test.