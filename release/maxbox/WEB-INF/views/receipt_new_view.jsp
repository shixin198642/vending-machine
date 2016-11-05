<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="top_html.jsp" %>

<div class="mainbox">
	<div class="nav-panel clearfix">
		<div class="fl nav">
			<label for="" class="labelbox">
			    <select class="drop-select drop-180" name="" id="">
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
			<span class="end">收货单管理</span>
		</div>
	</div>
	<div class="content">
		<div class="tab">
			<!-- 选中状态添加now -->
			<a class="now" href="<%=context%>/web/receipt_home.action?menu_id=10" title="新收货单">新收货单</a>
			<a href="<%=context%>/web/receipt_history.action?menu_id=10" title="历史收货单">历史收货单</a>
		</div>
		<!-- 切换tab显示对应tabinfo-panel -->
		<div style="display:block;" class="tabinfo-panel">
			<!-- 筛选 -->
			<div class="tab-search-panel">
				<div class="tab-search-box">
					<label for="" class="labelbox">
						<div class="tab-search-tit">
							<select class="drop-select drop-110" name="" id="">
								<option value="">采购订单号</option>
								<option value="">采购订单号</option>
								<option value="">采购订单号</option>
							</select>
						</div>
						<div class="tab-search-con">
							<input type="text" />
						</div>
						<div class="tab-search-tit">包含 SKU#</div>
						<div class="tab-search-con">
							<input type="text" />
						</div>
					</label>
					<div class="btn-search-panel">
						<input class="btn-search" type="button" />
					</div>
				</div>
			</div>
			<div class="exact-search">
				<dl class="clearfix">
					<dt>供应商</dt>
					<dd>
						<label for="" class="labelbox">
							<select class="drop-select drop-all" name="" id="">
								<option value="">全部</option>
								<option value="">全部</option>
								<option value="">全部</option>
							</select>
						</label>
					</dd>
					<dt>状态</dt>
					<dd>
						<label for="" class="labelbox">
							<select class="drop-select drop-80" name="" id="">
								<option value="">全部</option>
								<option value="">全部</option>
								<option value="">全部</option>
							</select>
						</label>
					</dd>
					<dt>预计到货时间</dt>
					<dd>
						<label for="" class="labelbox">
							<select class="drop-select drop-110" name="" id="">
								<option value="">全部</option>
								<option value="">全部</option>
								<option value="">全部</option>
							</select>
						</label>
					</dd>
					<dt>最后更新时间</dt>
					<dd>
						<label for="" class="labelbox">
							<select class="drop-select drop-110" name="" id="">
								<option value="">全部</option>
								<option value="">全部</option>
								<option value="">全部</option>
							</select>
						</label>
					</dd>
				</dl>
			</div>
			<div class="table table-a">
				<table width="100%" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>收货单编号</th>
							<th>供应商</th>
							<th>包裹追踪号码</th>
							<th>采购订单号码</th>
							<th>状态</th>
							<th>预计到货日期</th>
							<th>最后更新时间</th>
							<th>备注</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					    <c:forEach items="${receiptList}" var="receipt">
						<tr>
							<td>
								<a class="colgreen" href="" title="${receipt.id}">${receipt.id}</a>
							</td>
							<td>
								<a class="colgreen" href="" title="${receipt.supplier_name}">${receipt.supplier_name}</a>
							</td>
							<td>${receipt.tracking_id}</td>
							<td>
								<a class="colgreen" href="">${receipt.order_id}</a>
							</td>
							<td>${receipt.status}</td>
							<td>03-03</td>
							<td>${receipt.updateDatetime}</td>
							<td>${receipt.remark}</td>
							<td>
								<span class="alink">
									<a class="colgreen" href="" title="处理">处理</a>
									<a class="colgreen" href="" title="打印">打印</a>
								</span>
							</td>
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
</body>
</html>