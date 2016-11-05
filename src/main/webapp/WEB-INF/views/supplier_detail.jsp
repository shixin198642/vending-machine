<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.mjitech.constant.CommonConstants"%>
<%@ page import="com.mjitech.constant.RequestConstants"%>
<%@ page import="com.mjitech.constant.WebPageConstants"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="top_html.jsp" %>
			<div class="mainbox">
				<div class="nav-panel clearfix">
					<div class="fl nav">
						<span><a href="" title="供应商">供应商</a></span><i class="arrow arrow-gray-r"></i><span class="end">${supplier.sname}</span>
					</div>
					<div class="fr btn-mod-panel">
						<a class="btn-mod btn-skin-green" href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ALTERINIT_SUPPLIER%>?id=${supplier.id}" title="编辑供应商信息">编辑供应商信息</a>
						<a class="btn-mod btn-skin-green" href="" title="关联商品">关联商品</a>
					</div>
				</div>
				<div class="content">
					<div class="tab">
						<!-- 选中状态添加now -->
						<a class="now" href="" title="供应商详情">供应商详情</a>
						<a href="" title="相关商品">相关商品(<i class="colgreen">12</i>)</a>
					</div>
					<!-- 切换tab显示对应tabinfo-panel -->
					<div style="display:block;" class="tabinfo-panel">
						<div class="clearfix">
							<div class="info-l fl">
								<div class="info-panel">
									<div class="hd">基本信息</div>
									<div class="bd">
										<div class="plr20">
											<div class="clump-pro-info-panel">
												<div class="pro-info-panel">
													<dl class="pro-info-pre">
														<dt>供应商编号</dt>
														<dd>${supplier.id}</dd>
														<dt>公司名称</dt>
														<dd>${supplier.sname}</dd>
														<dt>通讯地址</dt>
														<dd>${supplier.address}</dd>
													</dl>
													<dl class="pro-info-pre">
														<dt>主要联系人</dt>
														<dd>${supplier.mcname}</dd>
														<dt>Email</dt>
														<dd><a class="colgreen">${supplier.memail}</a></dd>
														<dt>电话号码</dt>
														<dd>${supplier.mtel}</dd>
														<dt>传真</dt>
														<dd>${supplier.mfax}</dd>
														<dt>备注</dt>
														<dd>${supplier.remarks}</dd>
													</dl>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="info-r fl">
								<div class="info-panel">
									<div class="hd">交易信息</div>
									<div class="bd">
										<div>
											<dl class="order-manage">
												<dt>银行账号</dt>
												<dd>${supplier.account}</dd>
												<dt>开户行</dt>
												<dd>${supplier.bank}</dd>
												<dt>发货地址</dt>
												<dd>${supplier.shipaddress}</dd>
											</dl>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="info-panel">
							<div class="hd">联系人名单</div>
							<div class="bd">
								<div class="table table-d">
									<table width="100%" cellspacing="0" cellpadding="0">
										<thead>
											<tr>
												<td>姓名</td>
												<td>联系电话</td>
												<td>传真</td>
												<td>Email</td>
												<td>备注</td>
											</tr>
										</thead>
										<tbody>
										<c:if test="${list.size()>0}">
										<c:forEach items="${list}" var="supplierContact" varStatus="status">
											<tr>
												<td>${supplierContact.cname}</td>
												<td>${supplierContact.tel}</td>
												<td>${supplierContact.fax}</td>
												<td><a class="colgreen">${supplierContact.email}</a></td>
												<td>${supplierContact.remarks}</td>
											</tr>
										</c:forEach>
										</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div style="display:block;" class="tabinfo-panel">
						<div class="table table-a">
							<table width="100%" cellspacing="0" cellpadding="0">
								<thead>
									<tr>
										<th>SKU#</th>
										<th>商品名称</th>
										<th>商品条形码</th>
										<th>保质期</th>
										<th>箱规</th>
										<th>箱规报价</th>
										<th>单位</th>
										<td>单件价格</td>
										<td>单位</td>
										<th>操作</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>	
			</div>
		</div>
	</div>
</div>
</body>
<style>
	.modal-container{display:block; margin:0; position:static;}
	.wrapper{ height:auto;}
</style>
</html>