<%@page import="com.mjitech.constant.WebPageConstants"%>
<%@ page pageEncoding="UTF-8" import="com.mjitech.model.*"%>
<%
	Userinfo myinfo = (Userinfo) request.getAttribute("myinfo");
%>
<div class="header clearfix">
	<div class="header-l fl">
		<div class="fl select-action group-box">
			<a href="" title="快速新建"><span>快速新建</span><i
				class="arrow arrow-gray2-b"></i>
			</a>
			<div class="select-opts">
				<a href="<%=context %>/web/<%=WebPageConstants.ADD_SKU_PAGE %>" title="商品">+ 商品</a> <a href="" title="供应商">+ 供应商</a> <a
					href="" title="采购订单">+ 采购订单</a>
			</div>
		</div>
		<div class="search-panel fl clearfix">
			<input id="common_search_input" class="search-con line-style" type="text"
				placeholder="商品/订单/供应商" onkeydown="enterCommonSearch()">
			<div class="btn-search-panel">
				<input class="btn-search" type="button" onclick="commonSearch()">
			</div>
		</div>
	</div>
	<div class="header-r fr">
		<a class="mail" href=""><i>2</i>
		</a> <a class="set" href=""></a>
		<!-- 未登录显示 -->
		<!-- a class="us-login" href="" title="登录">登录</a-->
		<!-- 登录后显示 -->
		<a class="us-name" href="javascript:void(0)"><%=myinfo.getDisplayName()%></a>
		<a class="logout line-style"
			href="<%=WebPageConstants.buildURL(request.getContextPath(),
					WebPageConstants.LOGOUT)%>"
			title="退出登录">退出登录</a>
	</div>
</div>