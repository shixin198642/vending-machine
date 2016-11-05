<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.mjitech.constant.CommonConstants"%>
<%@ page import="com.mjitech.constant.RequestConstants"%>
<%@ page import="com.mjitech.constant.WebPageConstants"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="top_html.jsp" %>
<style>
<!--
.labelbox input{width:250px;}.dl-style2 dd{width:280px;}
-->
</style>
			<div class="mainbox">
				<div class="nav-panel clearfix">
					<div class="fl nav">
						<span>供应商</span><i class="arrow arrow-gray-r"></i><span class="end">新增供应商</span>
					</div>
					<div class="fr btn-mod-panel"><a class="btn-mod btn-skin-gray" href="javascript:send('addsupplier')" title="保存">保存</a><a class="btn-mod btn-skin-gray" href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.LIST_SUPPLIER%>?menu_id=20" title="放弃编辑">放弃编辑</a></div>
				</div>
				<form id="addsupplier" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ADD_SUPPLIER%>">
				<div class="content">
					<div class="info-panel">
						<div class="hd">基本信息</div>
						<div class="bd">
							<div>
								<dl class="infobox">
									<dt><span class="star">*</span>公司名称</dt>
									<dd>
										<label for="" class="labelbox">
											<input class="w386" type="text" id="sname" name="sname">
										</label>
									</dd>
									<dt><span class="star">*</span>通讯地址</dt>
									<dd>
										<label for="" class="labelbox">
											<input class="w500" type="text" name="address">
										</label>
									</dd>
									<dt class="vtop">备注</dt>
									<dd>
										<textarea class="w520" name="remarks" id="" cols="30" rows="7"></textarea>
									</dd>
									<dd style = "display:none">
										<input id="id" name="id" value=${supplierid}>
									</dd>
								</dl>
							</div>
						</div>
					</div>
					<div class="info-panel">
							<div class="hd">联系人名单</div>
							<div class="bd">
								<div class="table table-d">
									<table width="100%" cellspacing="0" cellpadding="0">
										<thead>
											<tr>
												<td>姓名</td>
												<td>联系电话</td>
												<td>传真</td>
												<td>Email</td>
												<td>备注</td>
												<td>操作</td>
											</tr>
										</thead>
										<tbody class="suppliercs">
											<!-- 如果没有联系人s -->
											<tr id="nosuppliercs">
												<td colspan="6">暂无联系人</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="clearfix bottom-info">
									<div class="fl">
										<a class="btn-mod btn-skin-green" href="javascript:addsupplier()" title="添加联系人">+ 添加联系人</a>
									</div>
									<div class="fr">
										<dl>
											<dt><span class="star">*</span> 主要联系人</dt>
											<dd>
												<label for="" class="labelbox">
													<select class="drop-select drop-all" name="mid" id="mid">
														<option value="-1">联系人</option>
													</select>
												</label>
											</dd>
										</dl>
									</div>
								</div>
							</div>
						</div>
					<div class="info-panel">
						<div class="hd">交易信息</div>
						<div class="bd">
							<dl class="dl-style2">
								<dt>户名</dt>
								<dd>
									<label for="" class="labelbox">
										<input class="w150" type="text" name="payname">
									</label>
								</dd>
								<dt>银行账号</dt>
								<dd>
									<label for="" class="labelbox">
										<input class="w150" type="text" name="account">
									</label>
								</dd>
							 </dl>
							 <dl class="dl-style2">
								<dt>开户行</dt>
								<dd>
									<label for="" class="labelbox">
										<input class="w150" type="text" name="bank">
									</label>
								</dd>
								<dt>收货地址</dt>
								<dd>
									<label for="" class="labelbox">
										<input class="w386" type="text" name="shipaddress">
									</label>
								</dd>
									<span class="ml20" style="margin-left:0px;margin-top:10px;"><input id="ckb" type="checkbox">与通讯地址相同</span>
							</dl>
						</div>
					</div>
				</div>	
				</form>
			</div>
		</div>
	</div>
</div>
<form style="display:none" id="deletesuppliercontact" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.DEL_SUPPLIERCONTACT%>">
	<input type="text" name="id"/>
</form>
<script src="<%=request.getContextPath() %>/js/jquery-3.0.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/json.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.validate.min.js"></script>
<script>
	
  var supplierid = ${supplierid};
  var temp = {};//修改时需要将修改记录更新到grid中
  
  function addsupplier(){
	  $('body').append("<div style=\"display:block\" class=\"modal-msk-light\"></div>");
	  $("body").append("<div class=\"modal-container\"><form id=\"addsuppliercs\" action=\"<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ADD_SUPPLIERCONTACT%>\"><div class=\"modal-tip\"><div class=\"modal-hd\"><h4 class=\"modal-title\">新增联系人</h4><a class=\"modal-close\" href=\"javascript:cancel_addsuppliercs()\" title=\"关闭\"></a></div><div class=\"modal-bd\"><div class=\"info-panel\"><dl class=\"clearfix infobox\"><dt><span class=\"star\">*</span>姓名</dt><dd><laebl class=\"labelbox\"><input type=\"text\" name=\"cname\"></laebl></dd><dt><span class=\"star\">*</span>联系电话</dt><dd><laebl class=\"labelbox\"><input type=\"text\" name=\"tel\"></laebl></dd><dt>传真</dt><dd><laebl class=\"labelbox\"><input type=\"text\" name=\"fax\"></laebl></dd><dt>Email</dt><dd><laebl class=\"labelbox\"><input class=\"w200\" type=\"text\" name=\"email\"></laebl></dd><dt>备注</dt><dd><laebl class=\"labelbox\"><input class=\"w340\" type=\"text\" name=\"remarks\"></laebl></dd><dt></dt><dd><input id=\"type\" name=\"type\" type=\"checkbox\">设置为主要联系人</dd><input type=\"text\" name=\"supplierid\" style=\"display:none\" value=\""+supplierid+"\"/></dl></div><div class=\"tip-btns\"><a class=\"btn-action btn-blue\" href=\"javascript:addsuppliercs()\" title=\"保存\">保存</a><a class=\"btn-action btn-cancel\" href=\"javascript:cancel_addsuppliercs()\" title=\"取消\">取消</a></div></div></div></form></div>");
  	  $(".modal-container").show();
	  $("#addsuppliercs").validate({
			errorPlacement: function(error, element) {
				// Append error within linked label
				$(element).parent().parent().append(error);
			},
			errorElement: "span",
			rules: {
				cname: "required",
				tel:"required"
			},
			messages: {
				cname: "请输入联系人名称",
				tel:"请输入联系人电话"
			}
	  })
  }
  
  //修改之前先查询修改的这条记录
  function altersuppliersc(id){
	  $('body').append("<div style=\"display:block\" class=\"modal-msk-light\"></div>");
	  $('body').append("<form style=\"display:block\" id=\"findsuppliercontact\" action=\"<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.FIND_SUPPLIERCONTACT%>\"><input type=\"text\" name=\"id\"/></form>")
	  $("#findsuppliercontact").find("input").val(id);
	  send("findsuppliercontact");
  } 
  //准备修改并发送ajax
  function updatesuppliercs(){
	  if($("#type").prop("checked")){
		  $("#type").val("1");
	  }else{
		  $("#type").val("2"); // checkbox不选中无法传值，待改进。
		  $("#type").attr("checked",'checked'); //折中方案
	  }
	  send("altersuppliercs");
  }
  
  
  function addsuppliercs(){
	  if($("#type").prop("checked")){
		  $("#type").val("1");
	  }else{
		  $("#type").val("2");
		  $("#type").attr("checked",'checked'); 
	  }
	  send("addsuppliercs");
  }
  
  
  function deletesuppliersc(id){
	  $("#deletesuppliercontact").find("input").val(id);
	  $('body').append("<div style=\"display:block\" class=\"modal-msk-light\"></div>");
	  $("body").append("<div class=\"modal-container delete-tip\"><div class=\"modal-tip\"><div class=\"modal-bd\"><div class=\"t-c\"><p class=\"col333\">确认要删除此联系人吗？</p></div><div class=\"tip-btns\"><a class=\"btn-action btn-blue\" href=\"javascript:send('deletesuppliercontact')\" title=\"确定\">确定</a><a class=\"btn-action btn-cancel\" href=\"javascript:cancel_addsuppliercs()\" title=\"取消\">取消</a></div></div></div></div>");
	  $(".modal-container").show();
  }

  function send(formname){
	  
	  var theform = $("#"+formname);
	  if(!theform.valid()){
		 return;
	  }
	  
		if(formname=="addsupplier" && $("#mid").val()=='-1'){
			alert(formname+$("#mid").val());
			$('body').append("<div style=\"display:block\" class=\"modal-msk-light\"></div>");
			$('body').append("<div style=\"display:block\" class=\"modal-container errorinfo-tip\"><div class=\"modal-tip\"><div style=\"display:none\" class=\"modal-hd\"><h4 class=\"modal-title\">异常信息</h4><a class=\"modal-close\" href=\"javascript:void(0)\" title=\"关闭\"></a></div><div class=\"modal-bd\"><div class=\"errinfo-panel\"><div class=\"icon-error-tip\"></div><div class=\"errinfo-con\">供应商最少要维护一个主要联系人！</div></div><div class=\"tip-btns\"><a class=\"btn-action btn-blue\" href=\"javascript:cancel_addsuppliercs()\" title=\"确定\">确定</a></div></div></div></div>");
			//$(".modal-container").show();
			return;
		}
	  
	  var formurl = theform.attr('action');
	  var data = JSON.stringify(theform.serializeObject());
	  temp = theform.serializeObject();
	  $.ajax( {    
		    url:formurl,
		    dataType:'json',
		    data:data,
		    contentType: "application/json",
		    type:'post',   
		    success:function(data) {
	    		//var html = JSON.stringify(data);
	    		//alert("ok");
	    		if(formname=="addsupplier"){
					location.href = "<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.LIST_SUPPLIER%>";
	    		}else if(formname=="addsuppliercs"){
	    			cancel_addsuppliercs();
	    			if($("#nosuppliercs")){
	    				$("#nosuppliercs").remove();
	    			}
	    			$(".suppliercs").append("<tr class=\"suppliercontact\" id=\"contact"+data.suppliercontact.id+"\"><td>"+data.suppliercontact.cname+"</td><td>"+data.suppliercontact.tel+"</td><td>"+data.suppliercontact.fax+"</td><td>"+data.suppliercontact.email+"</td><td>"+data.suppliercontact.remarks+"</td><td><span class=\"alink\"><a class=\"colgreen\" href=\"javascript:altersuppliersc("+data.suppliercontact.id+")\" title=\"编辑\">编辑</a><a class=\"colgreen\" href=\"javascript:deletesuppliersc("+data.suppliercontact.id+")\" title=\"删除\">删除</a></span></td></tr>");
	    			$("#mid").append("<option value='"+data.suppliercontact.id+"'>"+data.suppliercontact.cname+"</option>");
	    			if(data.suppliercontact.type=='1'){
	    				$("#mid").val(data.suppliercontact.id);
	    			}
	    			
	    		}else if(formname=="deletesuppliercontact"){
	    			cancel_addsuppliercs();//去掉两个div
	    			$('body').append("<div style=\"display:block\" class=\"modal-msk-light\"></div>");
	    			$('body').append("<div class=\"modal-container success-tip\"><div class=\"modal-tip\"><div class=\"modal-bd\"><div class=\"t-c pt30\"><i class=\"checked\"></i>删除成功</div></div></div></div>");
	    			$(".modal-container").show();
	    			setTimeout("deleteok()",2000);
	    		}else if(formname=="findsuppliercontact"){
	    			cancel_addsuppliercs();//去掉两个div
	    			$("#findsuppliercontact").remove();
	    			$('body').append("<div style=\"display:block\" class=\"modal-msk-light\"></div>");
	    			$('body').append("<div class=\"modal-container\"><form id=\"altersuppliercs\" action=\"<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ALTER_SUPPLIERCONTACT%>\"><div class=\"modal-tip\"><div class=\"modal-hd\"><h4 class=\"modal-title\">编辑联系人</h4><a class=\"modal-close\" href=\"javascript:cancel_addsuppliercs()\" title=\"关闭\"></a></div><div class=\"modal-bd\"><div class=\"info-panel\"><dl class=\"clearfix infobox\"><dt><span class=\"star\">*</span>姓名</dt><dd><laebl class=\"labelbox\"><input type=\"text\" name=\"cname\" value='"+data.result.cname+"'></laebl></dd><dt><span class=\"star\">*</span>联系电话</dt><dd><laebl class=\"labelbox\"><input type=\"text\" name=\"tel\" value='"+data.result.tel+"'></laebl></dd><dt>传真</dt><dd><laebl class=\"labelbox\"><input type=\"text\" name=\"fax\" value='"+data.result.fax+"'></laebl></dd><dt>Email</dt><dd><laebl class=\"labelbox\"><input class=\"w200\" type=\"text\" name=\"email\" value='"+data.result.email+"'></laebl></dd><dt>备注</dt><dd><laebl class=\"labelbox\"><input class=\"w340\" type=\"text\" name=\"remarks\" value='"+data.result.remarks+"'></laebl></dd><dt></dt><dd><input id=\"type\" name=\"type\" type=\"checkbox\">设置为主要联系人</dd><input type=\"text\" name=\"id\" style=\"display:none\" value='"+data.result.id+"'/></dl></div><div class=\"tip-btns\"><a class=\"btn-action btn-blue\" href=\"javascript:updatesuppliercs()\" title=\"保存\">保存</a><a class=\"btn-action btn-cancel\" href=\"javascript:cancel_addsuppliercs()\" title=\"取消\">取消</a></div></div></div></form></div>");
	    			if(data.result.type=='1'){
	    				$("#type").attr("checked",'checked'); 
	    			}
	    			$(".modal-container").show();
	    			$("#altersuppliercs").validate({
	    				errorPlacement: function(error, element) {
	    					$(element).parent().parent().append(error);
	    				},
	    				errorElement: "span",
	    				rules: {
	    					cname: "required",
	    					tel:"required"
	    				},
	    				messages: {
	    					cname: "请输入联系人名称",
	    					tel:"请输入联系人电话"
	    				}
	    			})
	    		}else if(formname=="altersuppliercs"){
	    			cancel_addsuppliercs();//去掉两个div
	    			$("#contact"+temp.id).html("<td>"+temp.cname+"</td><td>"+temp.tel+"</td><td>"+temp.fax+"</td><td>"+temp.email+"</td><td>"+temp.remarks+"</td><td><span class=\"alink\"><a class=\"colgreen\" href=\"javascript:altersuppliersc("+temp.id+")\" title=\"编辑\">编辑</a><a class=\"colgreen\" href=\"javascript:deletesuppliersc("+temp.id+")\" title=\"删除\">删除</a></span></td></tr>");
	    			$("#mid").find("option[value='"+temp.id+"']").remove();
	    			$("#mid").append("<option value='"+temp.id+"'>"+temp.cname+"</option>");
	    			//如果修改成主要联系人则选中
	    			if(temp.type=='1'){
	    				$("#mid").val(temp.id);
	    			}
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
  
  function cancel_addsuppliercs(){
		$(".modal-msk-light").remove();
		$(".modal-container").remove();
  }
  
  function deleteok(){
	  cancel_addsuppliercs();//去掉两个div
	  //删除页面一条记录
	  var id = $("#deletesuppliercontact").find("input").val();
	  $("#contact"+id).remove();
	  $("#mid option[value="+id+"]").remove(); //删除Select中Value='id'的Option 
	  if($(".suppliercontact").length == 0){
		  //显示没有联系人
		  $(".suppliercs").append("<tr id=\"nosuppliercs\"><td colspan=\"6\">暂无联系人</td></tr>");
		  $("#mid").find("option[value=-1]").attr("selected",true);
	  }
  }
  
  $(document).ready(function(){
	  
	  //复制通讯地址到收货地址
	  $("#ckb").click(function(){
		  if($(this).prop("checked")) {
			  var addval = $("[name='address']").val();
			  $("[name='shipaddress']").val(addval);
		  } 
	  })
	  
	  //jquery-validate
	  $("#addsupplier").validate({
		errorPlacement: function(error, element) {
			// Append error within linked label
			$(element).parent().parent().append(error);
		},
		errorElement: "span",
		rules: {
			sname: "required",
			address: "required"
		},
		messages: {
			sname: "请输入供应商名称",
			address: "请输入供应商地址"
		}
	 })	 
	 
  });
  
</script>
</body>
</html>