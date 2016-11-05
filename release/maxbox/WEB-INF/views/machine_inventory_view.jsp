<%@page import="com.mjitech.constant.WarehouseConstants"%>
<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="top_html.jsp" %>
<%
List<Warehouse> warehouseList = (List<Warehouse>)request.getAttribute("warehouseList");
int warehouseId = (Integer)request.getAttribute("warehouseId");
List<Inventory> inventoryList = (List<Inventory>)request.getAttribute("inventoryList");
Warehouse selected = (Warehouse)request.getAttribute("selected");
%>		
<div class="mainbox">
	<div class="nav-panel clearfix">
		<div class="fl nav">
			<label for="" class="labelbox">
				<select class="drop-select drop-180" name="" id="warehouses">
				<%for(Warehouse w:warehouseList){ %>
				<option value="<%=w.getId() %>" <%if(w.getId()==selected.getId()){ %>selected="selected"<%} %>><%=w.getName() %></option>
				<%} %>
				</select>
			</label>
			<span>
				<a href="" title="采购订单"><%=WarehouseConstants.getTypeName(selected.getType()) %>库存</a>
			</span>
			<i class="arrow arrow-gray-r"></i>
			<span class="end">商品库存</span>
		</div>
		<div class="fr btn-mod-panel"><a class="btn-mod btn-skin-blue" href="javascript:void(0)" onclick="addNewInventory()" title="添加库存商品">+ 添加库存商品</a></div>
	</div>
	<div class="content">
		<div class="table table-a">
			<table width="100%" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th>SKU#</th>
						<th>商品名称</th>
						<th>条形码</th>
						<th>现有库存</th>
						<th>零售价</th>
						<th>月周转达标率</th>
						<th>库存位置</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<%if(inventoryList != null){ %>
				    <%for(Inventory inv : inventoryList){ %>
					<tr>
						<td>
							<a class="colgreen" href="" title="<%=inv.getSku().getSkuNumber()%>">
							<%=inv.getSku().getSkuNumber()%>
							<input type="hidden" id="skunumber_<%=inv.getId() %>" value="<%=inv.getSku().getSkuNumber()%>"/>
							<input type="hidden" id="skuname_<%=inv.getId() %>" value="<%=inv.getSku().getName()%>"/>
							<input type="hidden" id="quantity_<%=inv.getId() %>" value="<%=inv.getQuantity()%>"/>
							<input type="hidden" id="sellprice_<%=inv.getId() %>" value="<%=inv.getSellprice()%>"/>
							</a>
							<div class="itemcard">
								<div class="info-panel">
									<div class="pic">
										<img src="../images/sprite.png" alt="" />
									</div>
									<dl class="dl-style5">
										<dt>商品编号</dt>
										<dd><%=inv.getSku().getSkuNumber()%></dd>										
										<dt>商品条形码</dt>
										<dd><%=inv.getSku().getBarcode()%></dd>
										<dt>商品名称</dt>
										<dd><%=inv.getSku().getName()%></dd>
									</dl>
									<dl class="infobox">
										<dt>保质期</dt>
										<dd><%=inv.getSku().getExpirationDays()%>天</dd>
										<dt>建议零售价</dt>
										<dd><%=inv.getSellprice()%></dd>
									</dl>
									<div class="tip-btns">
										<a class="btn-action btn-blue" href="javascript:void(0)" title="查看商品详情">查看商品详情</a>
									</div>
								</div>
							</div>
						</td>
						<td>
							<a class="colgreen" href="" title="<%=inv.getSku().getName()%>"><%=inv.getSku().getName()%></a>
						</td>
						<td><%=inv.getSku().getBarcode()%></td>
						<td><%=inv.getQuantity()%><%=inv.getSku().getUnit() %></td>
						<td><%=inv.getSellprice()%></td>
						<td>20%</td>
						<td><%=inv.getPosition()%></td>
						<%if(inv.getQuantity()<=inv.getSku().getMinStock()){ %>
						<td class="colred">低于最低值</td>
						<%}else{ %>
						<td>正常</td>
						<%} %>
						<td>
							<span class="alink">
								<a class="colgreen" href="javascript:void(0)" onclick="modifyInventoryQuantity(<%=inv.getId() %>)" title="调整库存">调整库存</a>
								<a class="colgreen" href="javascript:void(0)" onclick="modifyPrice(<%=inv.getId() %>)" title="减少">调增价格</a>
							</span>
						</td>
					</tr>
					<%} %>		
					<%} %>				
				</tbody>
			</table>
		</div><!--table-->
	</div> <!--content-->
</div> <!--mainbox-->
			
			
		</div>
	</div>
</div>
<div id='add_deduct_dialog' style="display:none">
商品：<span id="current_sku"></span><br/>
编号：<span id="current_skunumber"></span><br/>
当前库存：<span id="current_quanty"></span><br/>
操作：<select id="current_action"><option value="add">增加</option><option value="deduct">减少</option></select>
数量：<input type="text" id="quantity" size="3" style="margin-bottom:3px; border: 1px solid #ccc"/><br/>
说明：<textarea id="description"></textarea><br/>
<form id="change_quantity_form">
<input type="hidden" id="change_quantity" name="quantity"/>
<input type="hidden" id="change_description" name="description"/>
<input type="hidden" id="change_inv_id" name="inv_id"/>
</form>
</div>

<div id='modify_price_dialog' style="display:none">
商品：<span id="price_sku"></span><br/>
编号：<span id="price_skunumber"></span><br/>
当前价格：<span id="price_price"></span><br/>
新价格：<input type="text" id="price" size="3" style="margin-bottom:3px; border: 1px solid #ccc"/><br/>
<form id="change_price_form">
<input type="hidden" id="changeprice_price" name="price"/>
<input type="hidden" id="changeprice_inv_id" name="inv_id"/>
</form>
</div>

<div id='add_new_inventory_dialog' style="display:none">
<table border="0">
<tr><td width="40%">商品编号：</td><td width="60%"><input type="text" id="new_skunumber" size="10" style="margin-bottom:3px; border: 1px solid #ccc"/></td></tr>
<tr><td>价格：</td><td><input type="text" id="new_price" size="10" style="margin-bottom:3px; border: 1px solid #ccc"/></td></tr>
<tr><td>数量：</td><td><input type="text" id="new_quantity" size="3" style="margin-bottom:3px; border: 1px solid #ccc"/></td></tr>
<tr><td>说明：</td><td><textarea id="new_description"></textarea><br/></td></tr>
</table>

<form id="new_inventory_form">
<input type="hidden" id="warehouse_id" name="warehouseId" value="<%=selected.getId()%>"/>
<input type="hidden" id="newinventory_skunumber" name="skunumber"/>
<input type="hidden" id="newinventory_quantity" name="quantity"/>
<input type="hidden" id="newinventory_price" name="price"/>
<input type="hidden" id="newinventory_description" name="description"/>
</form>
</div>
</body>
<script>

$(function() {
	$( "#warehouses" ).change(function() {
	    //console.log($( this ).val());
	    var wh_id= $( this ).val();
	    location.href="<%=context %>/web/get_inventory_by_machine.action?warehouse_id="+wh_id;	
    }); 
	$("#quantity").numeral();
	$("#new_quantity").numeral();
});

function modifyPrice(invid){
	var skuname = $("#skuname_"+invid).val();
	var skunumber = $("#skunumber_"+invid).val();
	var price = $("#sellprice_"+invid).val();
	$("#price_sku").html(skuname);
	$("#price_skunumber").html(skunumber);
	$("#price_price").html(price);
	$( "#modify_price_dialog" ).dialog({
		 modal: true,
		 title: "调整'"+skuname+"'价格",
		 buttons: {
		        "OK": function() {
		          savePrice(invid);
		        },
		        Cancel: function() {
		          $( this ).dialog( "close" );
		        }
		      }
	});
}

function modifyInventoryQuantity(invid){
	var skuname = $("#skuname_"+invid).val();
	var skunumber = $("#skunumber_"+invid).val();
	var quantity = $("#quantity_"+invid).val();
	$("#current_quanty").html(quantity);
	$("#current_sku").html(skuname);
	$("#current_skunumber").html(skunumber);
	$( "#add_deduct_dialog" ).dialog({
		 modal: true,
		 title: "调整'"+skuname+"'库存",
		 buttons: {
		        "OK": function() {
		          saveQuantity(invid,"add");
		        },
		        Cancel: function() {
		          $( this ).dialog( "close" );
		        }
		      }
	});
}

var is_saving = false;
function saveQuantity(invid,action){
	if(is_saving){
		return;
	}
	if(!_checkFormElement($("#quantity"),'请填写数量')){
		return false;
	}
	if(!_checkFormElement($("#description"),'请填写说明')){
		return false;
	}
	
	$("#change_quantity").val($('#quantity').val());
	$("#change_inv_id").val(invid);
	$("#change_description").val($('#description').val());
	var action = $("#current_action").val();
	var url = context+'/web/<%=WebPageConstants.ADD_INVENTORY_QUANTITY%>';
	if(action=='deduct'){
		if(parseInt($("#quantity").val()) > parseInt($("#quantity_"+invid).val())){
			alert("减少的数值不可以比现有库存小");
			return false;
		}
		url = context+'/web/<%=WebPageConstants.DEDUCT_INVENTORY_QUANTITY%>';
	}
	is_saving = true;
	$.ajax( {    
	    url:url,
	    data: $("#change_quantity_form").serialize(),
	    type:'POST',
	    dataType:'json',
	    success:function(data) {
	    	if(typeof(data)!='object'){
	    		data = jQuery.parseJSON(data);
	    	}
	    	if(data.is_succ){
	    		location.href=location.href;
	    	}
	    	else{
	    		alert(data.error_message);
	    		is_saving = false;
	    	}
	     },    
	     error : function(request, status, e) {
	         alert(JSON.stringify(request));
	          alert(status);
	          alert(e);
	          is_saving = false;
	     }    
	}); 
}

var is_saving_price = false;
function savePrice(invid){
	if(is_saving_price){
		return;
	}
	if(!_checkFormElement($("#price"),'请填写价格')){
		return false;
	}
	if(isNaN(parseFloat($("#price").val()))){
		alert("请输入数字");
		return false;
	}
	if(parseFloat($("#price").val())<=0){
		alert("价格不可以为负");
		return false;
	}
	$("#changeprice_price").val($('#price').val());
	$("#changeprice_inv_id").val(invid);
	var url = context+'/web/<%=WebPageConstants.MODIFY_INVENTORY_PRICE%>';
	is_saving_price = true;
	$.ajax( {    
	    url:url,
	    data: $("#change_price_form").serialize(),
	    type:'POST',
	    dataType:'json',
	    success:function(data) {
	    	if(typeof(data)!='object'){
	    		data = jQuery.parseJSON(data);
	    	}
	    	if(data.is_succ){
	    		location.href=location.href;
	    	}
	    	else{
	    		alert(data.error_message);
	    		is_saving_price = false;
	    	}
	     },    
	     error : function(request, status, e) {
	         alert(JSON.stringify(request));
	          alert(status);
	          alert(e);
	          is_saving_price = false;
	     }    
	}); 
}
function addNewInventory(){
	$( "#add_new_inventory_dialog" ).dialog({
		 modal: true,
		 title: "增加商品库存",
		 buttons: {
		        "OK": function() {
		          saveNewInventory();
		        },
		        Cancel: function() {
		          $( this ).dialog( "close" );
		        }
		      }
	});
	
}
var is_saving_new_inventory;
function saveNewInventory(){
	if(is_saving_new_inventory){
		return;
	}
	if(!_checkFormElement($("#new_price"),'请填写价格')){
		return false;
	}
	if(isNaN(parseFloat($("#new_price").val()))){
		alert("请输入数字");
		return false;
	}
	if(parseFloat($("#new_price").val())<=0){
		alert("价格不可以为负");
		return false;
	}
	$("#newinventory_quantity").val($('#new_quantity').val());
	$("#newinventory_price").val($('#new_price').val());
	$("#newinventory_skunumber").val($('#new_skunumber').val());
	$("#newinventory_description").val($('#new_description').val());
	var url = context+'/web/<%=WebPageConstants.ADD_NEW_INVENTORY%>';
	is_saving_new_inventory = true;
	$.ajax( {    
	    url:url,
	    data: $("#new_inventory_form").serialize(),
	    type:'POST',
	    dataType:'json',
	    success:function(data) {
	    	if(typeof(data)!='object'){
	    		data = jQuery.parseJSON(data);
	    	}
	    	if(data.is_succ){
	    		location.href=location.href;
	    	}
	    	else{
	    		alert(data.error_message);
	    		is_saving_new_inventory = false;
	    	}
	     },    
	     error : function(request, status, e) {
	         alert(JSON.stringify(request));
	          alert(status);
	          alert(e);
	          is_saving_new_inventory = false;
	     }    
	}); 
}
</script>
</html>