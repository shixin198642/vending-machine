<%@ page pageEncoding="UTF-8"%>
<%@include file="top_html.jsp" %>			
			<div class="mainbox">
				<div class="nav-panel clearfix">
					<div class="fl nav">
						<span><a href="" title="仓储库存">仓储库存</a></span><i class="arrow arrow-gray-r"></i><span class="end">北京第一仓库</span>
					</div>
					<div class="fr btn-mod-panel"><a class="btn-mod btn-skin-green" href="" title="编辑商品信息">编辑商品信息</a><a class="btn-mod btn-skin-green" href="" title="关联供应商">关联供应商</a></div>
				</div>
				<div class="content">
					<div class="tab">
						<!-- 选中状态添加now -->
						<a class="now" href="" title="仓库信息">仓库信息</a>
						<a href="" title="库存管理">库存管理</a>
						<a href="" title="收货单">收货单</a>
					</div>
					<!-- 切换tab显示对应tabinfo-panel -->
					<div style="display:block;" class="tabinfo-panel">
						<div class="clearfix">
							<div class="info-l fl">
								<div class="info-panel">
									<div class="hd">基本信息</div>
									<div class="bd">
										<div class="plr20">
											<div class="infobox pro-info-pre">
												<dl>
													<dt>仓库编号</dt>
													<dd>00123</dd>
													<dt>仓库名称</dt>
													<dd>北京第一仓库</dd>
													<dt>类型</dt>
													<dd>自建仓库</dd>
													<dt>地址</dt>
													<dd>北京市 北京市 朝阳区 惠新东街100号凤凰大厦底商109-C</dd>
												</dl>
											</div>
											<div class="infobox">
												<dl>
													<dt>长度</dt>
													<dd>3.3米</dd>
													<dt>宽度</dt>
													<dd>3.0米</dd>
													<dt>高度</dt>
													<dd>2.0米</dd>
													<dt>备注</dt>
													<dd>备货周期较长。</dd>
												</dl>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="info-r fl">
								<div class="info-panel category">
									<div class="hd">库管信息</div>
									<div class="bd">
										<div class="w150x150"><img src="" alt=""></div>
										<div class="dl-style3">
											<dl>
												<dt>姓名</dt>
												<dd>王某某</dd>
												<dt>Email</dt>
												<dd><a class="colgreen" href="">sllan@163.com</a></dd>
												<dt>电话号码</dt>
												<dd>18012365589</dd>
											</dl>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="info-panel">
							<div class="hd">基本信息</div>
							<div class="bd">
								
							</div>
						</div>
					</div>
				</div>	
			</div>
			</div>
		</div>
	</div>
</div>
</body>
<script src="<%=context%>/js/jquery-3.0.0.min.js"></script>
<script src="<%=context%>/js/base.js"></script>
<script src="<%=context%>/js/warehouse.js"></script>

</html>