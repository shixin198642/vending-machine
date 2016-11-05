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
			<span class="end">入库请求</span>
		</div>
	</div>
	<div class="content">
		<div class="paddbox">
			<div class="btn-mod-panel">
				<!-- 未选中列表条目时此按钮为禁用状态；选中至少一项后变为可用去掉btn-mod-disabled。 -->
				<a class="btn-mod btn-skin-blue btn-mod-disabled" href="" title="生成入库单">生成入库单
				</a>
			</div>
		</div>
		<div class="table table-a">
			<table width="100%" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th class="tb-chkbox">
							<input type="checkbox" />
						</th>
						<th>SKU#</th>
						<th>商品名称</th>
						<th>批次</th>
						<th>入库数量</th>
						<th>单位</th>
						<th>收货单号</th>
						<th>库存位置</th>
						<th>入库申请时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
                    <c:forEach items="${receiptList}" var="receipt">
                    <c:forEach items="${receipt.receiptItems}" var="receiptItem">                    
					<tr>
						<td class="tb-chkbox">
							<input type="checkbox" />
						</td>
						<td>
							<a class="colgreen" href="" title="${receiptItem.sku.id}">${receiptItem.sku.id}</a>
							<div class="itemcard">
								<div class="info-panel">
									<div class="pic">
										<img src="../images/sprite.png" alt="" />
									</div>
									<dl class="dl-style5">
										<dt>SKU＃</dt>
										<dd>180天</dd>
										<dt>商品条形码</dt>
										<dd>3068320000343</dd>
										<dt>商品名称</dt>
										<dd>可口可乐樱桃味汽水300ml</dd>
									</dl>
									<dl class="infobox">
										<dt>保质期</dt>
										<dd>180天</dd>
										<dt>建议零售价</dt>
										<dd>2.50</dd>
									</dl>
									<div class="tip-btns">
										<a class="btn-action btn-blue" href="" title="查看商品详情">查看商品详情</a>
									</div>
								</div>
							</div>
						</td>
						<td>
							<a class="colgreen" href="" title="可口可乐樱桃味汽水300ml">${receiptItem.sku.name}</a>
							<div class="itemcard">
								<div class="info-panel">
									<div class="pic">
										<img src="../images/sprite.png" alt="" />
									</div>
									<dl class="dl-style5">
										<dt>SKU＃</dt>
										<dd>180天</dd>
										<dt>商品条形码</dt>
										<dd>3068320000343</dd>
										<dt>商品名称</dt>
										<dd>可口可乐樱桃味汽水300ml</dd>
									</dl>
									<dl class="infobox">
										<dt>保质期</dt>
										<dd>180天</dd>
										<dt>建议零售价</dt>
										<dd>2.50</dd>
									</dl>
									<div class="tip-btns">
										<a class="btn-action btn-blue" href="" title="查看商品详情">查看商品详情</a>
									</div>
								</div>
							</div>
						</td>
						<td>2016-06-06</td>
						<td>${receiptItem.quantity}</td>
						<td>瓶</td>
						<td>
							<a class="colgreen" href="" title="000232">${receipt.id}</a>
						</td>
						<td>A1B45344</td>
						<td>06-01 18:00</td>
						<td>
							<a class="colgreen" href="" title="移位">移位</a>
						</td>
					</tr>
					</c:forEach>	
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div><!--mainbox-->

		</div>
	</div>
</div>
</body>
</html>