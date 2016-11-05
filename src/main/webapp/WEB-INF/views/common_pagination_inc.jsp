<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.mjitech.constant.WebPageConstants,com.mjitech.model.*,org.apache.commons.lang3.*"%>
<%
Pagination pagination = (Pagination)request.getAttribute("pagination");
%>
<div class="pages">
	<div class="page-con">
		<%if(pagination.getPreviousPage()!=null){ %>
		<a href="<%=context+pagination.getPreviousPage().getUrl() %>" title="上一页">上一页</a>
		<%} %>
		<%if(pagination.getFirstPage()!=null){ %>
		<a href="<%=context+pagination.getFirstPage().getUrl() %>" title="第<%=pagination.getFirstPage().getPage() %>页"><%=pagination.getFirstPage().getPage() %></a>
		<%} %>
		<%if(pagination.isShowPreviousDot()){ %>
		<span>…</span>
		<%} %>
		<%for(PaginationPage tmp : pagination.getPreviousPages()){ %>
		<a href="<%=context+tmp.getUrl() %>" title="第<%=tmp.getPage() %>页"><%=tmp.getPage() %></a>
		<%} %>
		<a class="now" href="javascript:void(0);" title="第<%=pagination.getCurrentPage() %>页"><%=pagination.getCurrentPage() %></a>
		<%for(PaginationPage tmp : pagination.getNextPages()){ %>
		<a href="<%=context+tmp.getUrl() %>" title="第<%=tmp.getPage() %>页"><%=tmp.getPage() %></a>
		<%} %>
		
		
		<%if(pagination.isShowNextDot()){ %>
		<span>…</span>
		<%} %>
		<%if(pagination.getLastPage()!=null){ %>
		<a href="<%=context+pagination.getLastPage().getUrl() %>" title="第<%=pagination.getLastPage().getPage() %>页"><%=pagination.getLastPage().getPage() %></a>
		<%} %>
		<%if(pagination.getNextPage()!=null){ %>
		<a href="<%=context+pagination.getNextPage().getUrl() %>" title="下一页">下一页</a>
		<%} %>
		共<%=pagination.getTotal() %>条记录
	</div>
</div>