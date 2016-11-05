<%@ page pageEncoding="UTF-8"%>
<% java.util.List<Sku> skus = (java.util.List<Sku>) request.getAttribute("skus"); 
java.util.List<SkuType> parentTypes = (java.util.List<SkuType>) request.getAttribute("parentTypes");
java.util.List<SkuType> childTypes = (java.util.List<SkuType>) request.getAttribute("childTypes");
Sku condition = (Sku) request.getAttribute("condition");%>
<%@include file="top_html.jsp" %>
			
			<div class="mainbox">
				<div class="nav-panel clearfix">
					<div class="fl nav">
						<span><a href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+"/"+WebPageConstants.SKU_LIST%>">商品</a></span><i class="arrow arrow-gray-r"></i><span>商品列表</span>
					</div>
					<div class="fr btn-mod-panel"><a class="btn-mod btn-skin-blue" href="<%=context %>/web/add_sku_page.action" title="创建新商品">+ 创建新商品</a></div>
				</div>
				<div class="content">
				<div class="exact-search">
						<form id="search_form" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+"/"+WebPageConstants.SKU_LIST%>" method="get">
						<dl class="clearfix">
							<dt>分类</dt>
							<dd>
								<label for="" class="labelbox">
									<select class="drop-select drop-all" name="parent_type" id="parent_type" onchange="changeParentType(this.value)">
										<option value="0">全部分类</option>
										<%for(SkuType parentType : parentTypes){ %>
										<option value="<%=parentType.getId()%>" <%if(condition.getParentCategory()==parentType.getId()){ %>selected<%} %>><%=parentType.getName()%></option>
										<%} %>
									</select>
								</label>
							</dd>
							<dd>
								<label for="" class="labelbox">
									<select class="drop-select drop-all" name="sku_type" id="sku_type" onchange="changeChildType(this.value)">
										<option value="0">全部分类</option>
										<%if(childTypes!=null){ %>
										<%for(SkuType childType : childTypes){ %>
										<option value="<%=childType.getId()%>" <%if(condition.getCategory()==childType.getId()){ %>selected<%} %>><%=childType.getName()%></option>
										<%} %>
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
									<th class="tb-chkbox"><input type="checkbox"></th>
									<th>SKU#</th>
									<th>商品名称</th>
									<th>商品条形码</th>
									<th>品牌</th>
									<th>分类</th>
									<th>生产地</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<%for(Sku sku : skus){ %>
								<tr>
									<td class="tb-chkbox"><input type="checkbox"></td>
									<td class="colgreen"><%=sku.getSkuNumber() %></td>
									<td class="colgreen" title="<%=sku.getName() %>">
									<%=sku.getName().length()>20?sku.getName().substring(0,17)+"...":sku.getName() %>
									</td>
									<td><%=sku.getBarcode() %></td>
									<td><%=sku.getBrandName() %></td>
									<td><%=sku.getCategoryName() %></td>
									<td><%=sku.getCountryName() %></td>
									<td><a class="colgreen" href="<%=context %>/web/update_sku_page.action?sku_id=<%=sku.getId() %>" title="编辑">编辑</a></td>
								</tr>
								<%} %>
								
							</tbody>
						</table>
					</div>
					<%@include file="common_pagination_inc.jsp" %>
					
				</div>	
			</div>
		</div>
	</div>
</div>
<script>
function changeParentType(parentType){
	$("#sku_type").val(0);
	$("#search_form").submit();
}
function changeChildType(chlidType){
	$("#search_form").submit();
}
</script>
</body>
</html>