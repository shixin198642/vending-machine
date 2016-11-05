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
			<span class="end">分拣单</span>
		</div>
		<div class="fr btn-mod-panel">
			<a class="btn-mod btn-skin-blue" href="newproduct.html" title="创建分拣单">+
				创建分拣单</a>
		</div>
	</div>
	<div class="content">
		<div class="table table-a">
			<table width="100%" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th>分拣单编号</th>
						<th>状态</th>
						<th>配送单</th>
						<th>请求分拣时间</th>
						<th>最后更新时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<a class="colgreen" href="" title="869999">869999</a>
						</td>
						<td>等待分拣</td>
						<td></td>
						<td>06-01 18:00</td>
						<td>06-01 18:00</td>
						<td>
							<span class="alink">
								<a class="colgreen" href="" title="打印">打印</a>
							</span>
						</td>
					</tr>
					<tr>
						<td>
							<a class="colgreen" href="" title="869999">869999</a>
						</td>
						<td>分拣成功</td>
						<td>
							<span class="alink">
								<!-- 点击【查看】后文案变成【隐藏】 -->
								<a class="colgreen" href="" title="隐藏">隐藏</a>
							</span>
						</td>
						<td>06-01 18:00</td>
						<td>06-01 18:00</td>
						<td>
							<span class="alink">
								<a class="colgreen" href="" title="打印">打印</a>
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<table class="table-order" width="100%" cellspacing="0"
								cellpadding="0">
								<thead>
									<tr>
										<th>
											<div>
												<span class="col666">配送单</span>
												<span class="col999">(2)</span>
											</div>
											<!-- <div><a class="colgreen" href="" title="新建配送单">＋ 新建配送单</a></div> -->
										</th>
										<td>配送单编号</td>
										<td>请求机器</td>
										<td>状态</td>
										<td>配送日期</td>
										<td>配送员</td>
										<td>操作</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th></th>
										<td>
											<a class="colgreen" href="" title="869999">869999</a>
										</td>
										<td>
											<a class="colgreen" href="" title="北京一号线国贸站机器A">北京一号线国贸站机器A</a>
										</td>
										<td>等待配送</td>
										<td>06-01</td>
										<td>王某某</td>
										<td>
											<span class="alink">
												<a class="colgreen" href="" title="打印">打印</a>
											</span>
										</td>
									</tr>
									<tr>
										<th></th>
										<td>
											<a class="colgreen" href="" title="869999">869999</a>
										</td>
										<td>
											<a class="colgreen" href="" title="北京一号线国贸站机器A">北京一号线国贸站机器A</a>
										</td>
										<td>全部配送成功</td>
										<td>06-01</td>
										<td>王某某</td>
										<td>
											<span class="alink">
												<a class="colgreen" href="" title="打印">打印</a>
											</span>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<a class="colgreen" href="" title="869999">869999</a>
						</td>
						<td class="colred">分拣异常</td>
						<td>
							<span class="alink">
								<a class="colgreen" href="" title="查看">查看</a>
							</span>
						</td>
						<td>06-01 18:00</td>
						<td>06-01 18:00</td>
						<td>
							<span class="alink">
								<a class="colgreen" href="" title="打印">打印</a>
							</span>
						</td>
					</tr>
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