<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.mjitech.constant.SellOrderConstants"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.mjitech.constant.WarehouseConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="top_html.jsp" %>

<%
List<Warehouse> storesMachines = (List<Warehouse>)request.getAttribute("all_store_machines");
Warehouse selected = (Warehouse)request.getAttribute("selected");
List<SellOrder> orders = (List<SellOrder>)request.getAttribute("orders");
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SellOrder condition = (SellOrder)request.getAttribute("condition");
%>
<div class="mainbox">
	<div class="nav-panel clearfix">
		<div class="fl nav">
			<label for="" class="labelbox">
				<select class="drop-select" name="" id="warehouses">				
				<%for(Warehouse w:storesMachines){ %>
				<option value="<%=w.getId() %>" <%if(w.getId()==selected.getId()){ %>selected="selected"<%} %>><%=w.getName() %></option>
				<%} %>
				</select>
			</label>
			<span>
				<a href="" title="<%=WarehouseConstants.getTypeName(selected.getType()) %>"><%=WarehouseConstants.getTypeName(selected.getType()) %></a>
			</span>
			<i class="arrow arrow-gray-r"></i>
			<span class="end">销售订单</span>
		</div>
	</div>
	<div class="content">
		<div class="exact-search">
						<form id="search_form" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELL_HISTORY_HOME%>" method="get">
						<input type="hidden" id="form_warehouseid" name="warehouseId"/>
						<dl class="clearfix">
							<dt>订单状态</dt>
							<dd>
								<label for="" class="labelbox">
									<select class="drop-select drop-all" name="status" id="order_status" onchange="changeStatus(this.value)">
										<option value="0">全部状态</option>
										<%for(int status : SellOrderConstants.getStatusNames().keySet()){ %>
										<option value="<%=status%>" <%if(condition!=null && condition.getStatus()==status){ %>selected<%} %>><%=SellOrderConstants.getStatusName(status)%></option>
										<%} %>
									</select>
								</label>
							</dd>
						</dl>
						</form>
					</div>
		<div class="table table-a">
			<table width="100%" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th>订单编号</th>
						<th>销售</th>
						<th>总金额</th>
						<th>优惠金额</th>
						<th>实付金额</th>
						<th>商品件数</th>
						<th>订单状态</th>
						<th>购买时间</th>
					</tr>
				</thead>
				<tbody>
					<%if(orders!=null){ %>
					<%for(SellOrder order : orders){
						int totalSkuCount = 0;
						StringBuilder skucount = new StringBuilder("");
						
					%>
					<tr>
						<td>
							<a class="colgreen" href="" title="<%=order.getOrderNumber() %>"><%=order.getOrderNumber() %></a>
							<%if(order.getSkus()!=null && order.getSkus().size()>0){ %>
							<div class="itemcard">
								<div class="info-panel">
									<%
									for(SellOrderSku sku : order.getSkus()){
									totalSkuCount += sku.getCount();
									if(skucount.length()>0){
										skucount.append("+");
									}
									skucount.append(sku.getCount()).append(sku.getSku().getUnit());%>
									<dl class="dl-style5">
										<dt>商品编号</dt>
										<dd><%=sku.getSku().getSkuNumber()%></dd>	
										<dt>商品名称</dt>
										<dd><%=sku.getSku().getName()%></dd>
									</dl>
									<%} %>
								</div>
							</div>
							<%} %>
						</td>
						<td><%=order.getSeller()==null?"":order.getSeller().getDisplayName() %></td>
						<td><%=order.getTotalPrice() %></td>
						<td>0</td>
						<td><%=order.getTotalPrice() %></td>
						<td><%=skucount %></td>
						<td><%=SellOrderConstants.getStatusName(order.getStatus()) %></td>
						<td><%=sdf.format(order.getSellTime()) %></td>
					</tr>
					<%} %>
					<%} %>
				</tbody>
			</table>
		</div>
	</div>
</div><!--mainbox-->

		</div>
	</div>
</div>
<script>
$(function() {
	$( "#warehouses" ).change(function() {
	    //console.log($( this ).val());
	    var wh_id= $( this ).val();
	    location.href="<%=context %>/web/sell_history_home.action?warehouseId="+wh_id;	
    });  
});
function changeStatus(status){
	$("#form_warehouseid").val($("#warehouses").val());
	$("#search_form").submit();
	
}
</script>
</body>
</html>