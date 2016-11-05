<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.mjitech.constant.CommonConstants"%>
<%@ page import="com.mjitech.constant.RequestConstants"%>
<%@ page import="com.mjitech.constant.WebPageConstants"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="top_html.jsp" %>
<style>
<!--
body{font-family: "microsoft yahei";}
#auto, #skuauto {position: absolute;z-index: 1;background-color: #FFFFFF;border: 1px solid #BEBEBE;line-height: 30px;}
#auto>li {width: 270px;height: 30px;border: 0;}
#auto>li:hover {background: #f5f5f5;}
#skuauto>li {width: 260px;height: 30px;border: 0;padding-left: 10px;}
#skuauto>li:hover {background: #f2f2f2;}
.info-l,.info-r{width:49.5%;margin-right:0px;}
.labelbox input{width:250px;} 
.drop-select{height:34px;line-height: 34px;}
.con-column2 .info-panel {height: 340px;}
.disabled-input{background-color: #f5f5f5;}
.input-radio{width: auto;}.dl-style2 dd{width:280px;}
-->
</style>
	<div class="mainbox">
		<div class="nav-panel clearfix">
			<div class="fl nav">
				<span>采购订单</span><i class="arrow arrow-gray-r"></i><span class="end">新增订单</span>
			</div>
		</div>
		<form id="addorder" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ADD_ORDER%>">
		<div class="content">
			<div class="plr20 pt30">
				<div class="clearfix con-column2">
					<div class="info-l fl">
						<div class="info-panel">
							<div class="hd">甲方（采购方）信息</div>
							<div class="bd">
								<div>
									<dl class="infobox">
										<dt>公司名称</dt>
										<dd>
											<label for="" class="labelbox">
												<input type="text" id="companyName" class="disabled-input" name="companyName" disabled="disabled" value="北京华沁智联科技有限公司"/>
											</label>
										</dd>
										<dt>通讯地址</dt>
										<dd>
											<label for="" class="labelbox">
												<input type="text" id="companyAddr" class="disabled-input" name="companyAddr" disabled="disabled" value="北京市朝阳区光华路9号SOHO3Q-S01E05"/>
											</label>
										</dd>
										<dt><span class="star">*</span>法定代表人</dt>
										<dd>
											<label for="" class="labelbox">
												<select class="drop-select drop-all" name="userid" id="userid" style="width: 270px;" onchange="getUserTelephone(this);">
													<option value="0">——请选择——</option>
													<c:forEach items="${userList}" var="users">
														<option value="${users.id}" attr_tel="${users.mobile }">${users.displayName}</option>
													</c:forEach>
												</select>
											</label>
										</dd>
										<dt><span class="star">*</span>电话</dt>
										<dd>
											<label for="" class="labelbox">
												<input type="text" id="usertel" name="usertel" />
											</label>
										</dd>
									</dl>
								</div>  
							</div>
						</div>
					</div>
					<div style="width:1%;height:360px;float:left;">&nbsp;</div>
					<div class="info-r fl">
						<div class="info-panel">
							<div class="hd">乙方（供货方）信息</div>
							<div class="bd">
								<div>
									<dl class="infobox">
										<dt><span class="star">*</span>公司名称</dt>
										<dd>
											<label for="" class="labelbox" style="position: relative;">
												<input class="w386" type="text" id="suppliername" name="suppliername" placeholder="供应商编号或公司名称">
											</label>
											<ul id="auto" style="display: none;"></ul>
										</dd>
										<dt>通讯地址</dt>
										<dd>
											<label for="" class="labelbox">
												<input class="w386 disabled-input" type="text" id="supplieraddress" name="supplieraddress" disabled="disabled">
											</label>
										</dd>
										<dt><span class="star">*</span>法定代表人</dt>
										<dd>
											<label for="" class="labelbox">
												<input class="w386" type="text" id="supplierusername" name="supplierusername">
											</label>
										</dd>
										<dt><span class="star">*</span>电话</dt>
										<dd>
											<label for="" class="labelbox">
												<input class="w386" type="text" id="supplierusertel" name="supplierusertel">
											</label>
										</dd>
										<dd style = "display:none">
											<input id="id" name="id" value=${orderid}>
											<input id="orderstate" name="orderstate">
											<input id="supplierid" name="supplierid">
											<input id="suppliercontactid" name="suppliercontactid" />
											<input id="supplieraccount">
											<input id="supplierbank">
											<input id="payamt" name="payamt" />
										</dd>
									</dl>
								</div>
							</div>
						</div>
					</div>
					
				</div>
				
				<div class="info-panel">
					<div class="hd">商品清单</div>
					<div class="bd">
						<div class="table table-d">
							<table width="100%" cellspacing="0" cellpadding="0">
								<thead>
									<tr>
										<td width="7%">SKU#</td>
										<td width="20%">商品</td>
										<td width="10%">箱规</td>
										<td width="10%">采购数量</td>
										<td width="8%">单位</td>
										<td width="10%">单价</td>
										<td width="10%">总价</td>
										<td width="15%">备注</td>
										<td width="10%">操作</td>
									</tr>
								</thead>
								<tbody class="ordersku">
									<!-- 如果没有商品 -->
									<tr id="noordersku">
										<td colspan="9">暂无商品</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="clearfix bottom-info">
							<div class="fl">
								<a class="btn-mod btn-skin-green" href="javascript:addorderskuPop()" title="添加商品">+ 添加商品</a>
							</div>
							<div class="fr">
								<div class="total-sum">总计：<span id="totalamount">0.00</span></div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="info-panel">
					<div class="hd">款项结算</div>
					<div class="bd">
						<dl>
							<dt class="w100"><span class="star">*</span>结算形式</dt>
							<dd style="width:300px;">
								<span>
									<input type="radio" name="settlemode" class="input-radio" value="1" checked="checked"/> 先款后货 
									<input type="radio" name="settlemode" class="input-radio" value="2" style="margin-left:10px;"/> 先货后款
								</span>
							</dd>
							<dt style="width:70px;text-align: right;">支付方式</dt>
							<dd>
								<label for="" class="labelbox">
									<select class="drop-select drop-all disabled-input" name="paymode" id="paymode" style="width: 270px;" disabled="disabled">
										<option value="1">银行转账</option>
									</select>
								</label>
							</dd>
						</dl>
						<dl class="dl-style2">
							<dt>乙方户名</dt>
							<dd style="width:300px;">
								<label for="" class="labelbox">
									<input class="w166" type="text" name="payname" />
								</label>
							</dd>
							<dt style="width:100px;text-align: right;"><input type="checkbox" id="ckb" style="margin-top:3px;"/> 与供应商一致</dt>
						</dl>
						<dl class="dl-style2">
							<dt>收款账号</dt>
							<dd style="width:300px;">
								<label for="" class="labelbox">
									<input class="w166" type="text" name="payaccount" />
								</label>
							</dd>
							<dt style="width:70px;text-align: right;">开户行</dt>
							<dd>
								<label for="" class="labelbox">
									<input type="text" name="paybank" />
								</label>
							</dd>
						</dl>
					</div>
				</div>
				
				<div class="info-panel">
					<div class="hd">收货信息</div>
					<div class="bd">
						<dl class="dl-style2">
							<dt><span class="star">*</span>收货仓库</dt>
							<dd>
								<label for="" class="labelbox">
									<select class="drop-select drop-220" name="storehouseid" id="storehouseid" style="width: 270px;height: 34px;">
										<option value="0">——请选择——</option>
										<c:forEach items="${storeList}" var="stores">
											<option value="${stores.id}">${stores.name}</option>
										</c:forEach>
									</select>
								</label>
							</dd>
							<dt>收货地址</dt>
							<dd>
								<label for="" class="labelbox">
									<input class="w200 disabled-input" type="text" id="storehouseaddress" name="storehouseaddress" disabled="disabled">
								</label>
							</dd>
							
						</dl>
						<dl class="dl-style2">
							<dt><span class="star">*</span>仓库联系人</dt>
							<dd>
								<label for="" class="labelbox">
									<input type="text" id="storehousemanagername" name="storehousemanagername">
								</label>
							</dd>
							<dt><span class="star">*</span>联系电话</dt>
							<dd>
								<label for="" class="labelbox">
									<input type="text" id="storehousemanagertel" name="storehousemanagertel">
								</label>
							</dd>
						</dl>
					</div>
				</div>
				<div class="fr btn-mod-panel"><input id="sure" type="checkbox" style="margin: 3px;">订单已编辑完毕，确认无误<a class="btn-mod btn-skin-green" href="javascript:send('addordercontract')" title="订单已编辑完毕，确认无误，生成合同">完成，生成合同</a><a class="btn-mod btn-skin-gray" href="javascript:send('addorder')" title="保存">保存</a><a class="btn-mod btn-skin-gray" href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.LIST_ORDER%>?menu_id=22" title="放弃编辑">放弃编辑</a></div>
			</div>
		</div>	
		</form>
	</div>

<div style="display:none;" class="modal-msk-light"></div>
<form style="display:none" id="deleteordersku" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.DEL_ORDERSKU%>">
	<input type="text" name="id"/>
	<label name="amount"></label>
</form>
<script src="<%=request.getContextPath() %>/js/jquery-3.0.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/json.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.validate.min.js"></script>
<script>
	
  var orderid = ${orderid};
  var temp = {};//修改时需要将修改记录更新到grid中  
  
  //点击添加商品按钮进行弹窗显示
  function addorderskuPop(){
	  //显示浮层及弹框
	  $("body").append("<div class=\"modal-container product-modal\" style=\"top: 40%;\"><form id=\"addordersku\" action=\"<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ADD_ORDERSKU%>\"><div class=\"modal-tip\"><div class=\"modal-hd\"><h4 class=\"modal-title\">新增商品</h4><a class=\"modal-close\" href=\"javascript:cancel_addordersku()\" title=\"关闭\"></a></div><div class=\"modal-bd\"><div class=\"info-panel\"><dl class=\"infobox\"><dd style = \"display:none\"><input name=\"orderid\" value=${orderid}><input name=\"skuid\"><input name=\"amount\"></dd><dt>SKU#</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"skunumber\" onkeyup=\"javascript:skucheck();\"/></label><ul id=\"skuauto\" style=\"display: none;\"></ul></dd><dt>商品</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"skuname\" /></label></dd><dt>箱规</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"specname\" /></label></dd><dt>采购数量</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"orderamt\" onchange=\"javascript:calculation();\" /></label></dd><dt>单位</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"unit\" /></label></dd><dt>单价</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"specprice\" onchange=\"javascript:calculation();\" /></label></dd><dt>总价</dt><dd><span class=\"specamount\" style=\"height: 22px;line-height: 22px;padding: 6px 10px;display:block;\">0.00</span></dd> <dt>备注</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"remarks\" /></label></dd></dl></div><div class=\"tip-btns\"><a class=\"btn-action btn-blue\" href=\"javascript:send('addordersku')\" title=\"保存\">保存</a><a class=\"btn-action btn-cancel\" href=\"javascript:cancel_addordersku()\" title=\"取消\">取消</a></div></div></div></form></div>");
	  $(".modal-msk-light").show();
  	  $(".modal-container").show();
  }
  
  //点击编辑按钮进行弹窗显示：先查询该订单商品id的记录
  function alterordersku(id){
	  $(".modal-msk-light").show();
	  $('body').append("<form style=\"display:block\" id=\"findordersku\" action=\"<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.FIND_ORDERSKU%>\"><input type=\"text\" name=\"id\"/></form>")
	  $("#findordersku").find("input").val(id);
	  send("findordersku");
  }
  
  //点击删除按钮进行的操作
  function deleteordersku(id, amount){
	  $("#deleteordersku").find("input").val(id);
	  $("#deleteordersku").find("label").val(amount);
	  $(".modal-msk-light").show();
	  $("body").append("<div class=\"modal-container delete-tip\"><div class=\"modal-tip\"><div class=\"modal-bd\"><div class=\"t-c\"><p class=\"col333\">确认要删除此商品吗？</p></div><div class=\"tip-btns\"><a class=\"btn-action btn-blue\" href=\"javascript:send('deleteordersku')\" title=\"确定\">确定</a><a class=\"btn-action btn-cancel\" href=\"javascript:cancel_addordersku()\" title=\"取消\">取消</a></div></div></div></div>");
	  $(".delete-tip").show();
  }

  //表单提交保存
  function send(formname){
	//如果点击的是生成合同，则修正订单状态标识
	  if (formname == "addordercontract") {
		  if ($("#sure")[0].checked) {
			  $("[name='orderstate']").val("1");
			  formname = "addorder";
		  } else {
			  $(".modal-msk-light").show();
			  $("body").append("<div class=\"modal-container sure-tip\"><div class=\"modal-tip\"><div class=\"modal-bd\"><div class=\"t-c\"><p class=\"col333\">如果您的订单已编辑完毕，但未确认是否无误，将无法生成合同。</p></div><div class=\"tip-btns\"><a class=\"btn-action btn-cancel\" href=\"javascript:cancel_addordersku()\" title=\"关闭\">关闭</a></div></div></div></div>");
			  $(".sure-tip").show();
			  return;
		  }
	  }
	  
	  var theform = $("#"+formname);
	  if(!theform.valid()){
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
	    		if(formname=="addorder") {
					location.href = "<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.LIST_ORDER%>";
	    		} else if(formname=="addordersku") {
	    			cancel_addordersku();
	    			if($("#noordersku")){
	    				$("#noordersku").remove();
	    			}
	    			//主表的订单商品总金额赋值
	    			var totalamount = $("#payamt").val();
	    			totalamount = Number(totalamount) + Number($("[name='amount']").val());
	    			$("#payamt").val(totalamount.toFixed(2));
	    			$("#totalamount").html(totalamount.toFixed(2));
	    			//清空弹框中的字段值
	    			$("[name='skuid']").val("");
	    			$("[name='skunumber']").val("");
	    			$("[name='skuname']").val("");
	    			$("[name='skuspecid']").val("");
	    			$("[name='specname']").val("");
	    			$("[name='unit']").val("");
	    			$("[name='orderamt']").val("");
	    			$("[name='specprice']").val("");
	    			$("[name='remarks']").val("");
	    			$("[name='amount']").val("");
	    			$(".specamount").html("0.00");
	    			//订单商品列表显示内容
	    			$(".ordersku").append("<tr class=\"ordersku_row\" id=\"osku"+data.ordersku.id+"\"><td>"+data.ordersku.skunumber+"</td><td>"+data.ordersku.skuname+"</td><td>"+data.ordersku.specname+"</td><td>"+data.ordersku.orderamt+"</td><td>"+data.ordersku.unit+"</td><td>"+data.ordersku.specprice+"</td><td class=\"flag\">"+data.ordersku.amount+"</td><td>"+data.ordersku.remarks+"</td><td><span class=\"alink\"><a class=\"colgreen\" href=\"javascript:alterordersku("+data.ordersku.id+")\" title=\"编辑\">编辑</a><a class=\"colgreen\" href=\"javascript:deleteordersku("+data.ordersku.id+","+data.ordersku.amount+")\" title=\"删除\">删除</a></span></td></tr>");
	    		}else if(formname=="deleteordersku"){
	    			cancel_addordersku();//去掉两个div
	    			$(".modal-msk-light").show();
	    			$('body').append("<div class=\"modal-container success-tip\"><div class=\"modal-tip\"><div class=\"modal-bd\"><div class=\"t-c pt30\"><i class=\"checked\"></i>删除成功</div></div></div></div>");
	    			$(".success-tip").show();
	    			setTimeout("deleteok()",2000);
	    		}else if(formname=="findordersku"){
	    			cancel_addordersku();//去掉两个div
	    			$("#findordersku").remove();
	    			$(".modal-msk-light").show();
	    			$('body').append("<div class=\"modal-container product-modal\" style=\"top: 40%;\"><form id=\"alterordersku\" action=\"<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ALTER_ORDERSKU%>\"><div class=\"modal-tip\"><div class=\"modal-hd\"><h4 class=\"modal-title\">编辑商品</h4><a class=\"modal-close\" href=\"javascript:cancel_addordersku()\" title=\"关闭\"></a></div><div class=\"modal-bd\"><div class=\"info-panel\"><dl class=\"infobox\"><dd style = \"display:none\"><input name=\"orderid\" value=${orderid}><input name=\"id\" value='"+data.result.id+"'><input name=\"skuid\" value='"+data.result.skuid+"'><input name=\"amount\" value='"+data.result.amount+"'></dd><dt>SKU#</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"skunumber\" value='"+data.result.skunumber+"' onkeyup=\"javascript:skucheck();\"/></label><ul id=\"skuauto\" style=\"display: none;\"></ul></dd><dt>商品</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"skuname\" value='"+data.result.skuname+"' /></label></dd><dt>箱规</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"specname\" value='"+data.result.specname+"' /></label></dd><dt>采购数量</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"orderamt\" value='"+data.result.orderamt+"' onchange=\"javascript:calculation();\" /></label></dd><dt>单位</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"unit\" value='"+data.result.unit+"' /></label></dd><dt>单价</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"specprice\" value='"+data.result.specprice+"' onchange=\"javascript:calculation();\" /></label></dd><dt>总价</dt><dd><span class=\"specamount\" style=\"height: 22px;line-height: 22px;padding: 6px 10px;display:block;\">"+data.result.amount+"</span></dd> <dt>备注</dt><dd><label for=\"\" class=\"labelbox\"><input type=\"text\" name=\"remarks\" value='"+data.result.remarks+"' /></label></dd></dl></div><div class=\"tip-btns\"><a class=\"btn-action btn-blue\" href=\"javascript:send('alterordersku')\" title=\"保存\">保存</a><a class=\"btn-action btn-cancel\" href=\"javascript:cancel_addordersku()\" title=\"取消\">取消</a></div></div></div></form></div>");
	    			$(".modal-container").show();
	    		}else if(formname=="alterordersku"){
	    			cancel_addordersku();//去掉两个div
	    			//订单商品列表显示内容
	    			$("#osku"+temp.id).html("<td>"+temp.skunumber+"</td><td>"+temp.skuname+"</td><td>"+temp.specname+"</td><td>"+temp.orderamt+"</td><td>"+temp.unit+"</td><td>"+temp.specprice+"</td><td class=\"flag\">"+temp.amount+"</td><td>"+temp.remarks+"</td><td><span class=\"alink\"><a class=\"colgreen\" href=\"javascript:alterordersku("+temp.id+")\" title=\"编辑\">编辑</a><a class=\"colgreen\" href=\"javascript:deleteordersku("+temp.id+","+temp.amount+")\" title=\"删除\">删除</a></span></td></tr>");
	    			//主表的订单商品总金额赋值
	    			var skuRow = $(".ordersku_row");
	    			var _amount = 0;
	    			for (var x = 0; x < skuRow.length; x++) {
	    				var row = skuRow[x];
	    				var amount_row = $(row).find(".flag").html();
	    				_amount = Number(_amount) + Number(amount_row);
	    			}
	    			$("#payamt").val(_amount.toFixed(2));
	    			$("#totalamount").html(_amount.toFixed(2));
	    		}
		     },    
		     error : function(request, status, e) {}    
		});  
  	}
  
    //关闭弹窗
    function cancel_addordersku(){
		$(".modal-msk-light").hide();
		$(".modal-container").remove();
    }
  
    //订单商品删除成功的操作
  function deleteok(){
	  $(".modal-msk-light").hide();
	  $(".success-tip").hide();
	  
	  //删除页面一条记录
	  var id = $("#deleteordersku").find("input").val();
	  var amount = $("#deleteordersku").find("label").val();
	  //主表的订单商品总金额赋值
	  var totalamount = $("#payamt").val();
	  totalamount = Number(totalamount) - Number(amount);
	  $("#payamt").val(totalamount.toFixed(2));
	  $("#totalamount").html(totalamount.toFixed(2));
	  //移除删除的订单商品记录
	  $("#osku"+id).remove();
	  //如果订单商品记录已全部删除，则显示暂无商品
	  if($(".ordersku").find("tr").length == 0){
		  $(".ordersku").append("<tr id=\"noordersku\"><td colspan=\"9\">暂无商品</td></tr>");
	  }
  }
  
  //供应商相关字段赋值
  function pitchOn(id,sname,address,mid,mcname,mtel,mfax,account,bank,payname) {
	  $("#supplierid").val(id == "null" ? "" : id);
	  $("#suppliername").val(sname == "null" ? "" : sname);
	  $("#supplieraddress").val(address == "null" ? "" : address);
	  $("#suppliercontactid").val(mid == "null" ? "" : mid);
	  $("#supplierusername").val(mcname == "null" ? "" : mcname);
	  $("#supplierusertel").val(mtel == "null" ? "" : mtel);
	  $("#supplieraccount").val(account == "null" ? "" : account);
	  $("#supplierbank").val(bank == "null" ? "" : bank);
	  //隐藏“下拉框”
	  $("#auto").hide();
  }
  
	//商品根据输入的内容不同过滤显示不同商品集合，联想引入
	function skucheck(){
		// 1.首先获取文本框内容 
		var wordText=$("[name='skunumber']").val();
		// 2.将内容发送给服务器(文本不为空的情况下才发送数据)
		var searchurl = "<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.CONDITIONLIST_SKU%>";
		var data = {"condition":wordText};
		if(wordText!=""){
			$.ajax({
				url : searchurl,
				type : 'post',
				dataType : 'json',
				data: data,
				success: function(result) {
					//先清空，后赋值
					$("#skuauto").empty();
					//解析返回的list到 #auto 中
					if (result.length > 0) {
						for(var i = 0; i < result.length; i++) {
							$("#skuauto").append("<li refid=\"" + result[i].id + "\" onclick=\"pitchSkuOn('" + result[i].id + "','" + result[i].skuNumber + "','" + result[i].name + "','" + result[i].unit + "')\">" + result[i].skuNumber + " — " + result[i].name + "</li>");
						}
						$("#skuauto").show();
					}
				}
			});
		} else {
			$("#skuauto").hide();
		}
	}
	
	//商品相关字段赋值
	function pitchSkuOn(id, skunumber, name, unit){
		$("[name='skuid']").val(id == "null" ? "" : id);
		$("[name='skunumber']").val(skunumber == "null" ? "" : skunumber);
		$("[name='skuname']").val(name == "null" ? "" : name);
		$("[name='unit']").val(unit == "null" ? "" : unit);
		//隐藏“下拉框”
		$("#skuauto").hide();
	}
	
	//金额计算
	function calculation() {
		var quantity = $("[name='orderamt']").val();
		var price = $("[name='specprice']").val();
		var amount = Number(quantity) * Number(price);
		$("[name='amount']").val(amount.toFixed(2));//隐藏域金额赋值
		$(".specamount").html(amount.toFixed(2));//页面金额赋值
	}
	
  // 根据选择的用户填充用户电话
  function getUserTelephone(obj) {
	  var flag = $(obj).attr("id");
	  var value = $(obj).val();
	  if (flag == "userid") {
		  $("input[name='usertel']").val($(obj).find("option[value='"+value+"']").attr("attr_tel"));
	  }
	  if (flag == "storehousemanagerid") {
		  $("input[name='storehousemanagertel']").val($(obj).find("option[value='"+value+"']").attr("attr_tel"));
	  }
  }
  
  	//供应商根据输入的内容不同过滤显示供应商集合，联想引入
  	function searchSupplier(){
		// 1.首先获取文本框内容 
		var wordText=$("#suppliername").val();
		// 2.将内容发送给服务器(文本不为空的情况下才发送数据)
		var autoNode=$("#auto");
		autoNode.empty();
		autoNode.append("<li>正在查询...</li>"); 
		var searchurl = "<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.CONDITIONLIST_SUPPLIER%>";
		var data = {"condition":wordText};
		if(wordText!=""){
			$.ajax({
				url : searchurl,
				type : 'post',
				dataType : 'json',
				data: data,
				success: function(result) {
					//先清空，后赋值
					//解析返回的list到 #auto 中
					if (result.length > 0) {
						autoNode.empty();
						for(var i = 0; i < result.length; i++) {
							autoNode.append("<li refid=\"" + result[i].id + "\" onclick=\"pitchOn('" + result[i].id + "','" + result[i].sname + "','" + result[i].address + "','" + result[i].mid + "','" + result[i].mcname + "','" + result[i].mtel + "','" + result[i].mfax + "','" + result[i].account + "','" + result[i].bank + "','" + result[i].payname + "')\">" + result[i].sname + "</li>");
						}
					}else{
						autoNode.empty();
						autoNode.append("<li>未找到该供应商</li>");    
					}
					autoNode.show();
				}
			});
		} else {
			autoNode.hide();
		}
	}
  
	$(document).ready(function(){
		var delayTime = '';
		$("#suppliername").keyup(function(event){
			clearTimeout(delayTime);
		  	delayTime = setTimeout("searchSupplier();",400);  
		});
		
		//根据仓库的选择不同，显示不同仓库信息
		$("#storehouseid").change(function(){
			var storehouseid = $("#storehouseid").val();
			if(storehouseid == 0) {
				$("#storehouseaddress").val("");
			} else {
				var searchurl = "<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.FIND_WAREHOUSE%>";
				var param = JSON.stringify({"id":storehouseid});
				$.ajax({
					url : searchurl,
					type : 'post',
					dataType : 'json',
					contentType:"application/json",
					data: param,
					success: function(data) {
						$("#storehouseaddress").val(data.result.address);
					}
				});
			}
		});
		
		//复制通讯地址到收货地址
		$("#ckb").click(function(){
		   if($(this).prop("checked")) {
		   	    $("[name='payname']").val($("#suppliername").val());
				$("[name='payaccount']").val($("#supplieraccount").val());
				$("[name='paybank']").val($("#supplierbank").val());
		   } 
	    });
		
		//jquery-validate
		$("#addorder").validate({
			errorPlacement: function(error, element) {
				// Append error within linked label
				$(element).parent().parent().append(error);
			},
			errorElement: "span",
			rules: {
				userid: {
					required: true,
					userid: "userid"
				},
				usertel: "required",
				suppliername: "required",
				supplierusername: "required",
				supplierusertel: "required",
				storehouseid: {
					required: true,
					storehouseid: "storehouseid"
				},
				storehousemanagername: "required",
				storehousemanagertel: "required"
				
			},
			messages: {
				userid: "请选择法定代表人",
				usertel: "请输入法定代表人电话",
				suppliername: "请输入供货方公司名称",
				supplierusername: "请输入供货方法定代表人",
				supplierusertel: "请输入供货方法定代表人电话",
				storehousemanagername: "请输入仓库联系人",
				storehousemanagertel: "请输入仓库联系人电话"
			}
		 });
		$.validator.addMethod("userid",function(value,element){
			if (value != "0") {
				return true;
			}
		},"请选择法定代表人");
		$.validator.addMethod("storehouseid",function(value,element){
			if (value != "0") {
				return true;
			}
		},"请选择收货仓库");
	});
</script>
</body>
</html>