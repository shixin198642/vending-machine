<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="top_html.jsp" %>


<div class="mainbox">
				<div class="nav-panel clearfix">
					<div class="fl nav">
						<span><a href="" title="仓储库存">仓储库存</a></span><i class="arrow arrow-gray-r"></i><span class="end">分拣单 002322</span>
					</div>
					<div class="fr btn-mod-panel">
						<a class="btn-mod btn-skin-green" href="" title="打印">打印</a>
					</div>
				</div>
				<div class="content">
					<div class="plr20 pt30">
						<div class="clearfix">
							<div class="info-l fl bgfff">
								<div class="posbaner">等待分拣<i class="arrow triangle"></i></div>
								<div class="plr20 brd1 mb20">
									<div class="deal-tit clearfix">
										<div class="fl">
											<p>分拣单编号</p>
											<p class="deal-num">002366</p>
										</div>
										<div class="fl column2">
											<p>仓库<em>北京市第四仓库</em></p>
											<p>日期<em>2016 年 6 月 2 日</em></p>
										</div>
										<div class="fl barcode"><img src="<%=context %>/images/u10492.png" alt=""></div>
									</div>
									<div class="info-panel">
										<div class="hd gray-hd">分拣单信息</div>
										<div class="bd">
											<div class="pb0">
												<dl class="infobox">
													<dt>仓库负责人</dt>
													<dd>王某某</dd>
													<dt>联系电话</dt>
													<dd>010-2300056</dd>
												</dl>
											</div>
										</div>
									</div>
									<div class="info-panel">
										<div class="hd gray-hd">分拣清单</div>
											<div class="bd">
												<div class="table table-a imp0">
													<table width="100%" cellspacing="0" cellpadding="0">
														<thead>
															<tr>
																<th class="tb-chkbox"><input type="checkbox"></th>
																<th>SKU#</th>
																<th>商品名称</th>
																<th>批次</th>
																<th>库存位置</th>
																<th>请求数量</th>
																<th>实际分拣数量</th>
																<th>异常信息</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td class="tb-chkbox"><input type="checkbox"></td>
																<td>
																	<a class="colgreen" href="" title="869999">869999</a>
																	<div class="itemcard">
																		<div class="info-panel">
																			<div class="pic">
																				<img src="../images/sprite.png" alt="">
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
																	<a class="colgreen" href="" title="可口可乐樱桃味汽水300ml">可口可乐樱桃味汽水300ml</a>
																	<div class="itemcard">
																		<div class="info-panel">
																			<div class="pic">
																				<img src="../images/sprite.png" alt="">
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
																<td>2016-04-09</td>
																<td>040802</td>
																<td>6</td>
																<td></td>
																<td class="expt-overflow">
																	<span class="text-overflow"></span><a class="colgreen" href="" title="分拣异常">分拣异常</a>
																</td>
															</tr>
															<!-- 分拣异常添加class bgred -->
                              <tr class="bgred">
																<td class="tb-chkbox"><input type="checkbox"></td>
																<td><a class="colgreen" href="" title="869999">869999</a></td>
																<td><a class="colgreen" href="" title="可口可乐樱桃味汽水300ml">可口可乐樱桃味汽水300ml</a></td>
																<td>2016-04-09</td>
																<td>6</td>
																<td>箱</td>
																<td>5</td>
																<td class="expt-overflow">
																	<span class="text-overflow">缺货, 过期,缺货, 过期,缺货, 过期</span><a class="colgreen" href="" title="分拣异常">编辑</a>
																</td>
															</tr>
														</tbody>
														<tfoot>
															<tr>
																<td class="tb-chkbox"><input type="checkbox"></td>
																<td align="left" colspan="7">
																	<a class="btn-action btn-blue" href="" title="批量分拣成功">批量分拣成功</a>
																</td>
															</tr>
														</tfoot>
													</table>
												</div>
											</div>
									</div>
								</div>
							</div>
							<div class="info-r fl">
								<div class="tabbar tabbar2">
									<div class="tabcon clearfix">
										<!-- 切换tab添加class now -->
										<span class="now"><em>分拣</em></span>
										<span><em>分拣结束</em></span>
										<i class="justify-fix"></i>
									</div>
									<div class="tabline clearfix">
				            <!-- 切换tab添加class now -->
				            <i class="now">1</i>
				            <i class="end">✓</i>
				            <span class="justify-fix"></span>
				          </div>
								</div>
								<!-- 验货s -->
								<div class="info-panel">
									<div class="hd"> 分拣</div>
									<div class="bd">
										<div class="process-panel">
											<p>已处理 <span>15 / 25</span></p>
											<div class="process-box">
												<!-- 通过style width控制进度百分比 -->
												<div style="width:40%;" class="process"></div>
											</div>
										</div>
										<div class="tip-btns">
											<a class="btn-action btn-blue" href="" title="分拣结束，生成配送单">分拣结束，生成配送单</a>
										</div>
									</div>
								</div>
								<!-- 验货e -->
								<!-- 验货结果s -->
								<div class="info-panel">
									<div class="hd">分拣结果</div>
									<div class="bd">
										<div class="pb0">
											<dl class="dl-style5">
												<dt>分拣结果</dt>
												<dd>部分分拣成功</dd>
												<dt>通过</dt>
												<dd>9种商品</dd>
												<dt>异常</dt>
												<dd>3种商品</dd>
											</dl>
										</div>
										<!-- 有“分拣异常的商品”时显示 -->
										<div style="" class="plr10">
											<div class="info-panel">
												<div class="hd gray-hd">分拣异常的商品</div>
												<div class="bd">
													<div class="error-prdt-panel">
														<div class="error-prdt-box">
															<h4>
																<span class="mr10">＃000126</span>
																<span>可口可乐樱桃味汽水300ml</span>
															</h4>
															<div class="error-prdt clearfix">
																<p class="fl">货物有遗漏<span><em class="colred">3</em>箱</span></p>
															</div>
															<div class="error-prdt clearfix">
																<p class="fl">货物有遗漏<span><em class="colred">3</em>箱</span></p>
															</div>
														</div>
														<div class="error-prdt-box">
															<h4>
																<span class="mr10">＃000126</span>
																<span>可口可乐樱桃味汽水300ml</span>
															</h4>
															<div class="error-prdt clearfix">
																<p class="fl">货物有遗漏<span><em class="colred">3</em>箱</span></p>
															</div>
															<div class="error-prdt clearfix">
																<p class="fl">货物有遗漏<span><em class="colred">3</em>箱</span></p>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!-- 验货结果e -->
							</div>
						</div>
					</div>
				</div>	
</div> <!-- mainbox -->


		</div>
	</div>
</div>

<script type="text/javascript">
$(function() {
	$( "#warehouse_select" ).change(function() {
	    var wh_id= $( this ).val();
	    location.href="<%=context %>/web/list_picking.action?warehouse_id="+wh_id;	
    });      
});
</script>

</body>
</html>