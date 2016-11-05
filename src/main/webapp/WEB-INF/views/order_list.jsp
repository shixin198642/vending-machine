<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.mjitech.constant.CommonConstants"%>
<%@ page import="com.mjitech.constant.RequestConstants"%>
<%@ page import="com.mjitech.constant.WebPageConstants"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="top_html.jsp" %>
	<div class="mainbox">
		<div class="nav-panel clearfix">
			<div class="fl nav">
				<span>采购订单</span><i class="arrow arrow-gray-r"></i><span class="end">首页</span>
			</div>
			<div class="fr btn-mod-panel"><a class="btn-mod btn-skin-blue" href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ADDINIT_ORDER%>" title="创建订单">+ 创建订单</a></div>
		</div>
		<div class="content">
			<div class="table table-a">
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th width="10%">订单编号</th>
							<th width="30%">供应商名称</th>
							<th width="10%">订单总金额</th>
							<th width="15">收货仓库</th>
							<th width="10%">订单状态</th>
							<th width="15%">最后更新时间</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
					<c:if test="${list.size()>0}">
					<c:forEach items="${list}" var="order">
						<tr>
							<td class="colgreen"><a href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.DETAIL_ORDER+"?id="%>${order.id}"><fmt:formatDate value="${order.orderdate}" pattern="yyMMddHHmmss"/></a></td>
							<td>${order.suppliername}</td>
							<td>${order.payamt}</td>
							<td>${order.storehousename}</td>
							<td>${order.orderstatename}</td>
							<td><fmt:formatDate value="${order.updateDatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td class="select-action">
								<a class="colgreen" href="javascript:;" title="选择操作">选择操作<i class="arrow arrow-green-b"></i></a>
								<div class="select-opts">
									<a class="colgreen" href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ALTERINIT_ORDER%>?id=${order.id}" title="编辑订单">编辑订单</a>
									<a class="colgreen" href="javascript:;" title="打印合同">打印合同</a>
									<a class="colgreen" href="javascript:;" title="导出为PDF">导出为PDF</a>
									<a class="colgreen" href="javascript:delorder(${order.id})" title="删除订单">删除订单</a>
								</div>
							</td>
						</tr>
					</c:forEach>
					</c:if>
					</tbody>
				</table>
			</div>
		</div>	
	</div>
		</div>
	</div>
</div>
<form style="display:none" id="deleteorder" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.DEL_ORDER%>">
	 order id:<input type="text" name="id"/>
</form>
<script src="<%=request.getContextPath() %>/js/jquery-3.0.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/json.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.validate.min.js"></script>
<script>
function delorder(id){
	$("#deleteorder").find("input").val(id);
	$('body').append("<div style=\"display:block\" class=\"modal-msk-light\"></div>");
	$("body").append("<div class=\"modal-container delete-tip\"><div class=\"modal-tip\"><div class=\"modal-bd\"><div class=\"t-c\"><p class=\"col333\">确认要删除该订单吗？</p></div><div class=\"tip-btns\"><a class=\"btn-action btn-blue\" href=\"javascript:send('deleteorder')\" title=\"确定\">确定</a><a class=\"btn-action btn-cancel\" href=\"javascript:cancel_ordersku()\" title=\"取消\">取消</a></div></div></div></div>");
	$(".modal-container").show();
}

function cancel_ordersku(){
	$(".modal-msk-light").remove();
	$(".modal-container").remove();
}

function send(formname){
	  var theform = $("#"+formname);
	  var formurl = theform.attr('action');
	  var data = JSON.stringify(theform.serializeObject());
	  $.ajax( {    
		    url:formurl,
		    dataType:'json',
		    data:data,
		    contentType: "application/json",
		    type:'post',   
		    success:function(data) {
	    		if(formname=="deleteorder"){
	    			cancel_ordersku();//去掉两个div
					location.href = "<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.LIST_ORDER%>";
	    		}
		     },    
		     error : function(request, status, e) {}    
		});  
}
</script>

</body>
</html>