<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="top_html.jsp" %>
<%List<Warehouse> warehouseList = (List<Warehouse>)request.getAttribute("warehouseList");
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
				<option value="<%=w.getId() %>" <%if(w.getId()==warehouseId){ %>selected="selected"<%} %>><%=w.getName() %></option>
				<%} %>
				</select>
			</label>
			<span>
				<a href="" title="采购订单">仓储库存</a>
			</span>
			<i class="arrow arrow-gray-r"></i>
			<span class="end">商品库存</span>
		</div>
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
						<th>存货周转天数</th>
						<th>月周转达标率</th>
						<th>库存位置</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<%if(inventoryList!=null){ %>
					<%for(Inventory inv : inventoryList){ %>
					<tr>
						<td>
							<a class="colgreen" href="" title="<%=inv.getSku().getSkuNumber()%>"><%=inv.getSku().getSkuNumber()%></a>
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
										<a class="btn-action btn-blue" href="" title="查看商品详情">查看商品详情</a>
									</div>
								</div>
							</div>
						</td>
						<td>
							<a class="colgreen" href="" title="可口可乐樱桃味汽水300ml"><%=inv.getSku().getName()%></a>
						</td>
						<td><%=inv.getSku().getBarcode()%></td>
						<td><%=inv.getQuantity()%></td>
						<td>8</td>
						<td>20%</td>
						<td><%=inv.getPosition()%></td>
						<%if(inv.getQuantity()<=inv.getSku().getMinStock()){ %>
						<td class="colred">低于最低值</td>
						<%}else{ %>
						<td>正常</td>
						<%} %>
						<td>
							<span class="alink">
								<a class="colgreen" href="" title="详情">详情</a>
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

<script type="text/javascript">
$(function() {
	$( "#warehouses" ).change(function() {
	    //console.log($( this ).val());
	    var wh_id= $( this ).val();
	    location.href="<%=context %>/web/warehouse_request_home.action?warehouse_id="+wh_id;	
    });  
});
</script>
</body>
</html>