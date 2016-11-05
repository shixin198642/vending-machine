<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="top_html.jsp" %>

<div class="mainbox">
				<form id="pickingForm" action="<%=context %>/web/create_picking.action" >
				<input name="warehouse_id" value="${param.warehouse_id}" style="display:none" />
                <div class="content">
					<div class="plr20 pt30">
						<div class="info-panel">
							<div class="hd">分拣单信息</div>
							<div class="bd">
								<div class="clearfix">
									<dl class="info-style">
										<dt><span class="star">*</span>负责人</dt>
										<dd>
											<label for="" class="labelbox">
												<select class="drop-select drop-180" name="userId" id="userList">
												</select>
											</label>
										</dd>
										<div class="clearfix"></div>
										<dt><span class="star">*</span>电话号码</dt>
										<dd>
											<label for="" class="labelbox">
												<input type="text" id="phoneNumber">
											</label>
										</dd>
									</dl>
								</div>
							</div>
						</div>
						<div class="info-panel">
							<div class="hd">商品清单</div>
							<div class="bd">
								<div class="table table-nobord">
									<table width="100%" cellspacing="0" cellpadding="0">
										<thead>
											<tr>
												<th></th>
												<td>商品</td>
												<td>批次</td>
												<td>库存位置</td>
												<td>请求数量</td>
												<td></td>
											</tr>
										</thead>
										<tbody id="pickingTBody">
											<tr>
												<th><span class="bgnum">1</span></th>
												<td>
													<label for="" class="labelbox">
														<select class="drop-select drop-220 skuDropList" name="skuId" id="requestDefault">
															<option>选择商品</option>
														</select>
													</label>
												</td>
												<td>
													<label for="" class="labelbox">
														<input class="w100 pici" type="text" placeholder="YYYY-DD-MM">
													</label>
												</td>
												<td>
													<label for="" class="labelbox">
														<input class="w100 location" type="text">
													</label>
												</td>
												<td>
													<label for="" class="labelbox">
														<input class="w30" type="text" name="quantity" />
													</label>
												</td>
												<td><a class="colgreen deleteRequest" href="#" title="删除" style="visibility: hidden;">删除</a></td>
											</tr>
											<tr>
												<th></th>
												<td align="left" colspan="7"><a class="colgreen" href="#" title="增加商品" id="addRequestBtn">+ 增加商品</a></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="btn-action-panel">
							<a class="btn-action btn-blue" href="#" title="完成，生成分拣单" id="createPickingBtn">完成，生成分拣单</a>
							<a class="btn-action btn-cancel" href="#" title="放弃编辑" id="cancelPickingBtn">放弃编辑</a>
						</div>
					</div>
				</div> <!--content-->
				</form>
				
</div><!--mainbox-->


		</div>
	</div>
</div>

<script type="text/javascript">
$(function() {
	var userArray = ${userArray};
	var userList = $("#userList");

	$.each(userArray, function(index, item) {
	    userList.append($('<option>', {value:item.id, text:item.displayName}))
	    if(index == 0){        
	        $("#phoneNumber").val(item.mobile);
	    }
	});    

	$( "#userList" ).change(function() {
	    var userId= $( this ).val();
		$.each(userArray, function(index, item) {
		    if(item.id == userId){        
		        $("#phoneNumber").val(item.mobile);
		    }
		});	    
    });
    
    var inventoryArray= ${inventoryArray};
    var requestDefault = $("#requestDefault");
	$.each(inventoryArray, function(index, item) {
	    var sku = item.sku;
	    requestDefault.append($('<option>', {value:sku.id, text:sku.name}));
	});     
	
	var skuTotal = 1;
	$("#addRequestBtn").click(function() {
	    skuTotal = skuTotal + 1;
		var row = $('<tr>');
		var header = $('<th><span class="bgnum">' + skuTotal + '</span></th>');
		var label = $('<label for="" class="labelbox">');
		var select = $('<select name="skuId" class="drop-select drop-220 skuDropList">');
		var intput0 = $('<input class="w100 pici" type="text" placeholder="YYYY-DD-MM">');
		var intput1 = $('<input class="w100 location" type="text">');
		var intput2 = $('<input name="quantity" class="w30" type="text">');
		var deleteColumn = $('<td><a class="colgreen deleteLink" href="#" title="删除">删除</a></td>');
		
		select.append($('<option value="">选择商品</option>'));
		$.each(inventoryArray, function(index, item) {
		    var sku = item.sku;
		    select.append($('<option>', {value:sku.id, text:sku.name}));
		});
		
		var td1 = $('<td>').append($('<label for="" class="labelbox">').append(select));
		var td2 = $('<td>').append($('<label for="" class="labelbox">').append(intput0));
		var td3 = $('<td>').append($('<label for="" class="labelbox">').append(intput1));
	    var td4 = $('<td>').append($('<label for="" class="labelbox">').append(intput2));
	    
	    row.append(header);
	    row.append(td1);
	    row.append(td2);
	    row.append(td3);
	    row.append(td4);
	    row.append(deleteColumn);
	
	    $('#pickingTBody tr:last').before(row);
	    
		$(".deleteLink").on("click",function() {
	        var td = $(this).parent();
	        var tr = td.parent();
	        //change the background color to red before removing
	        tr.css("background-color","#FF3700");
	        tr.fadeOut(400, function(){
	            tr.remove();
	        });		
		});
		
		$(".skuDropList").change(function() {
			var myself = $( this );		
			var skuId= myself.val();
			$.each(inventoryArray, function(index, item) {
			    var sku = item.sku;
			    if(skuId == sku.id){
			    	var trRow = myself.closest("tr");		   				    	
			    	trRow.find("input.pici").val( sku.createDatetime );
			    	trRow.find("input.location").val( item.position );
			    }
			}); 		
		});		
	});
	
	$(".skuDropList").change(function() {
	    var myself = $( this );
		var skuId= myself.val();
		$.each(inventoryArray, function(index, item) {
		    var sku = item.sku;
		    if(skuId == sku.id){		    
		    	var trRow = myself.closest("tr");		   				    	
		    	trRow.find("input.pici").val( sku.createDatetime );
		    	trRow.find("input.location").val( item.position );
		    }
		}); 		
	});
	
	$("#createPickingBtn").click(function() {
		$('#pickingForm').trigger('submit');      
    });
    		
});
</script>

</body>
</html>