<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="top_html.jsp" %>


<div class="mainbox">
	<div class="nav-panel clearfix">
		<div class="fl nav">
			<label for="" class="labelbox">
				<select class="drop-select drop-all" name="" id="warehouse_select">
				<c:forEach items="${warehouseList}" var="warehouse">
				    <c:choose> 
					  <c:when test="${warehouse.id == warehouseId}">
					    <option value="${warehouse.id}" selected="selected">${warehouse.name}</option>
					  </c:when>
					  <c:otherwise>
					    <option value="${warehouse.id}">${warehouse.name}</option>
					  </c:otherwise>
					</c:choose>			    
				</c:forEach>
				</select>
			</label>
			<span>
				<a href="" title="仓储库存">仓储库存</a>
			</span>
			<i class="arrow arrow-gray-r"></i>
			<span class="end">机器补货请求</span>
		</div>
	</div>
	<div class="content">
		<div class="tab">
			<!-- 选中状态添加now -->
			<a href="<%=context%>/web/warehouse_request_home.action?menu_id=13" title="新请求">新请求</a>
			<a class="now" href="<%=context%>/web/warehouse_request_history.action?menu_id=13" title="历史请求">历史请求</a>
		</div>
		<!-- 切换tab显示对应tabinfo-panel -->
		<div style="display:block;" class="tabinfo-panel">
			<div class="table table-a">
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>请求机器</th>
							<th>SKU#</th>
							<th>商品名称</th>
							<th>请求数量</th>
							<th>分拣数量</th>
							<th>状态</th>
							<th>分拣单号</th>
							<th>配送单号</th>
							<th>最后更新时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestList}" var="request">
						<tr>
							<td class="tb-chkbox">
								<input type="checkbox" />
							</td>
							<td>
								<a class="colgreen" href="" title="${request.machineName}">${request.machineName}</a>
							</td>
							<td>
								<a class="colgreen" href="" title="${request.sku.id}">${request.sku.id}</a>
							</td>
							<td>
								<a class="colgreen" href="" title="${request.sku.name}">${request.sku.name}</a>
							</td>
							<td>A1B45344</td>
							<td>${request.quantity}</td>
							<td>${request.quantityInventory}</td>
							<td>06-01 18:00</td>
						</tr>
						</c:forEach>			
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div><!--mainbox-->


		</div>
	</div>
</div>

<!-- 编辑分拣数量 s -->
<div style="display:none" class="modal-container delete-tip">
	<div class="modal-tip">
		<div class="modal-bd">
			<div class="t-c">
				<div class="kclocation">
					<dl class="dl-style5">
						<dt>分拣数量</dt>
						<dd>
							<label for="" class="labelbox">
								<input class="w60" type="text">
							</label>
						</dd>
					</dl>
				</div>
			</div>
			<div class="tip-btns">
				<a class="btn-action btn-blue" href="" title="保存修改">保存修改</a>
				<a class="btn-action btn-cancel" href="" title="取消">取消</a>
			</div>
		</div>
	</div>
</div>
<!-- 编辑分拣数量 e -->
</body>
</html>