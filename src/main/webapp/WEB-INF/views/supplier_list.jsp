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
						<span>供应商</span><i class="arrow arrow-gray-r"></i><span class="end">首页</span>
					</div>
					<div class="fr btn-mod-panel"><a class="btn-mod btn-skin-blue" href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ADDINIT_SUPPLIER+"?menu_id=21"%>" title="创建供应商">+ 创建供应商</a></div>
				</div>
				<div class="content">
					<div class="table table-a">
						<table width="100%" cellspacing="0" cellpadding="0">
							<thead>
								<tr>
									<th class="tb-chkbox"><input type="checkbox"></th>
									<th>供应商编号</th>
									<th>公司名称</th>
									<th>主要联系人</th>
									<th>联系电话</th>
									<th>传真</th>
									<th>Email</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<c:if test="${list.size()>0}">
							<c:forEach items="${list}" var="supplier">
								<tr>
									<td class="tb-chkbox"><input type="checkbox"></td>
									<td class="colgreen"><a href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.DETAIL_SUPPLIER+"?id="%>${supplier.id}">${supplier.id}</a></td>
									<td class="colgreen"><a href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.DETAIL_SUPPLIER+"?id="%>${supplier.id}">${supplier.sname}</a></td>
									<td>${supplier.mcname}</td>
									<td>${supplier.mtel}</td>
									<td>${supplier.mfax}</td>
									<td>${supplier.memail}</td>
									<td>
										<span class="alink">
											<a class="colgreen" href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ALTERINIT_SUPPLIER%>?id=${supplier.id}" title="编辑">编辑</a>
											<a class="colgreen" href="javascript:del_supplier(${supplier.id})" title="删除">删除</a>
										</span>
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
<form style="display:none" id="deletesupplier" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.DEL_SUPPLIER%>">
	 supplier id:<input type="text" name="id"/>
</form>
<script src="<%=request.getContextPath() %>/js/jquery-3.0.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/json.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.validate.min.js"></script>
<script>
function del_supplier(id){
	$("#deletesupplier").find("input").val(id);
	$('body').append("<div style=\"display:block\" class=\"modal-msk-light\"></div>");
	$("body").append("<div class=\"modal-container delete-tip\"><div class=\"modal-tip\"><div class=\"modal-bd\"><div class=\"t-c\"><p class=\"col333\">确认要删除此供应商吗？</p></div><div class=\"tip-btns\"><a class=\"btn-action btn-blue\" href=\"javascript:send('deletesupplier')\" title=\"确定\">确定</a><a class=\"btn-action btn-cancel\" href=\"javascript:cancel_addsuppliercs()\" title=\"取消\">取消</a></div></div></div></div>");
	$(".modal-container").show();
}

function cancel_addsuppliercs(){
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
	    		//var html = JSON.stringify(data);
	    		//alert("ok");
	    		if(formname=="deletesupplier"){
	    			cancel_addsuppliercs();//去掉两个div
					location.href = "<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.LIST_SUPPLIER%>";
	    		}
		     },    
		     error : function(request, status, e) {  
		    	 //alert("no");
//		         alert(JSON.stringify(request));
//		          alert(status);
//		          alert(e);
		     }    
		});  
}
</script>

</body>
</html>