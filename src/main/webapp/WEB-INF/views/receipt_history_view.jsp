<%@ page pageEncoding="UTF-8"%>
<%@include file="top_html.jsp" %>

<div class="mainbox">
	<div class="nav-panel clearfix">
		<div class="fl nav">
			<label for="" class="labelbox">
				<select class="drop-select drop-180" name="" id="">
					<option value="">北京市第二仓库</option>
					<option value="">北京市第二仓库</option>
					<option value="">北京市第二仓库</option>
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
			<a class="" href="<%=context%>/web/receipt_home.action?menu_id=10" title="新收货单">新收货单</a>
			<a class="now" href="<%=context%>/web/receipt_history.action?menu_id=10" title="历史收货单">历史收货单</a>
		</div>
		<!-- 切换tab显示对应tabinfo-panel -->

		<div style="display:block;" class="tabinfo-panel">
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
						<tr>
							<td>
								<a class="colgreen" href="" title="554422424">554422424</a>
							</td>
							<td>
								<a class="colgreen" href="" title="北京好滋味食品商贸公司">北京好滋味食品商贸公司</a>
							</td>
							<td>LD12022456</td>
							<td>
								<a class="colgreen" href="">00232</a>
							</td>
							<td>验货失败</td>
							<td>03-03</td>
							<td>06-01 18:00</td>
							<td>可口可乐</td>
							<td>
								<span class="alink">
									<a class="colgreen" href="" title="打印">打印</a>
								</span>
							</td>
						</tr>
						<tr>
							<td>
								<a class="colgreen" href="" title="554422424">554422424</a>
							</td>
							<td>
								<a class="colgreen" href="" title="北京好滋味食品商贸公司">北京好滋味食品商贸公司</a>
							</td>
							<td>LD12022456</td>
							<td>
								<a class="colgreen" href="">00232</a>
							</td>
							<td>拒绝收货</td>
							<td>03-03</td>
							<td>06-01 18:00</td>
							<td>加急</td>
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
	</div>
</div><!--mainbox-->

		</div>
	</div>
</div>
</body>
</html>