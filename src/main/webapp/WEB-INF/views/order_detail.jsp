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
				<span>采购订单</span><i class="arrow arrow-gray-r"></i><span class="end">采购订单详情</span>
			</div>
		</div>
		<form id="alterorder" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ALTER_ORDER%>">
		<div class="content">
			<div class="plr20 pt30">
				<div class="clearfix">
					<div class="bgfff">
						<div class="plr20 brd1">
							<div class="deal-tit clearfix">
								<div class="fl">
									<p>订单合同编号</p>
									<p class="deal-num"><fmt:formatDate value="${order.orderdate}" pattern="yyMMddHHmmss"/></p>
								</div>
								<span>日期<em><fmt:formatDate value="${order.orderdate}" pattern="yyyy 年 MM 月 dd 日"/></em></span>
							</div>
							<div class="clearfix hd0">
								<div class="half mr10">
									<div class="info-panel">
										<div class="hd gray-hd"><span>甲方</span>订货方信息</div>
										<div class="bd">
											<div>
												<dl class="infobox pb0">
													<dt>公司名称</dt>
													<dd>北京华沁智联科技有限公司</dd>
													<dt>地址</dt>
													<dd>北京市朝阳区光华路9号SOHO3Q-S01E05</dd>
													<dt>法定代表人</dt>
													<dd>${order.username}</dd>
													<dt>电话</dt>
													<dd>${order.usertel}</dd>
												</dl>
											</div>
										</div>
									</div>
								</div>
								<div class="half">
									<div class="info-panel">
										<div class="hd gray-hd"><span>乙方</span>供应商信息</div>
										<div class="bd">
											<div>
												<dl class="infobox pb0">
													<dt>公司名称</dt>
													<dd class="colgreen">${order.suppliername}</dd>
													<dt>地址</dt>
													<dd>${order.supplieraddress}</dd>
													<dt>法定代表人</dt>
													<dd>${order.supplierusername}</dd>
													<dt>电话</dt>
													<dd>${order.supplierusertel}</dd>
												</dl>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<div class="info-panel">
								<div class="hd gray-hd clearfix">
									<em class="fl">商品清单</em>
									<!-- <em class="fr colgray1">货币单位：元</em> -->
								</div>
									<div class="bd">
										<div class="table table-a imp0">
											<table width="100%" cellspacing="0" cellpadding="0">
												<thead>
													<tr>
														<td width="10%">SKU#</td>
														<td width="25%">商品</td>
														<td width="10%">箱规</td>
														<td width="10%">采购数量</td>
														<td width="10%">单位</td>
														<td width="10%">单价</td>
														<td width="10%">总价</td>
														<td width="15%">备注</td>
													</tr>
												</thead>
												<tbody>
													<c:if test="${list.size()>0}">
														<c:forEach items="${list}" var="orderSku" varStatus="status">
															<tr>
																<td class="colgreen">${orderSku.skunumber}</td>
																<td class="colgreen">${orderSku.skuname}</td>
																<td>${orderSku.specname}</td>
																<td>${orderSku.orderamt}</td>
																<td>${orderSku.unit}</td>
																<td>${orderSku.specprice}</td>
																<td><a class="flag">${orderSku.amount}</a></td>
																<td>${orderSku.remarks}</td>
															</tr>
														</c:forEach>
													</c:if>
												</tbody>
											</table>
										</div>
										<div class="clearfix msg-tip">
											<span class="fr sum">总计<em>${order.payamt}</em></span>
										</div>
									</div>
							</div>
							
							<div class="clearfix">
								<div class="half mr10">
									<div class="info-panel">
										<div class="hd gray-hd clearfix">款项结算</div>
										<div class="bd">
											<div>
												<dl class="infobox pb0">
													<dt>结算形式</dt>
													<dd><c:if test="${order.settlemode == 1}">先款后货</c:if><c:if test="${order.settlemode == 2}">先货后款</c:if></dd>
													<dt>支付方式</dt>
													<dd><c:if test="${order.paymode == 1}">银行转账</c:if></dd>
													<dt>乙方户名</dt>
													<dd>${order.payname}</dd>
													<dt>收款帐号</dt>
													<dd>${order.payaccount}</dd>
													<dt>开户行</dt>
													<dd>${order.paybank}</dd>
												</dl>
											</div>
										</div>
									</div>
								</div>
								<div class="half">
									<div class="info-panel">
										<div class="hd gray-hd clearfix">收货信息</div>
										<div class="bd">
											<div>
												<dl class="infobox pb0">
													<dt>收货仓库</dt>
													<dd>${order.storehousename}</dd>
													<dt>地址</dt>
													<dd>${order.storehouseaddress}</dd>
													<dt>联系人</dt>
													<dd>${order.storehousemanagername}</dd>
													<dt>电话</dt>
													<dd>${order.storehousemanagertel}</dd>
												</dl>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<div class="info-panel">
								<div class="hd gray-hd">合同条款</div>
									<div class="bd">
										<div class="plr20">
											根据《中华人民共和国合同法》等法律法规，甲乙双方本着长期合作、互惠互利的原则，经平等协商，就甲方长期从乙方处采购商品相关事宜，双方达成以下条款，以兹共同遵守。
											<p style="line-height: 35px;font-weight: bold;">
											1. 基本协议与具体协议
											</p>
											<p style="margin-bottom: 5px;">
											1.1 鉴于甲方拟长期从乙方处采购商品，双方经友好协商签订本长期采购协议。本协 议为双方关于甲方从乙方处采购商品的长期基本协议，对双方商定的基本条款进行约定。
											</p>
											<p style="margin-bottom: 5px;">
											1.2 本协议生效后，甲方从乙方处采购商品时，甲乙双方可以就商品数量、规格型号、价格、供货时间等具体事项进行协商达成一致，以附件形式保存或记载于采购订单（以下统称“具体协议”），乙方接受甲方采购订单之时视为具体协议成立。除非另有特别说明，双方之间的每一次采购供货均受具体协议及本协议的约束。具体协议未包含的内容以本协议为准，具体协议与本协议不一致时以具体协议内容为准。
											</p>
											<div class="hideContent" style="display: none;">
												<p style="line-height: 35px;font-weight: bold;">
												2. 合作期限
												</p>
												<p style="margin-bottom: 5px;">
												本协议自双方签订之日起生效，有效期为____年，自____年____月____日至____年____月____日止。协议有效期满双方均无异议可续签。
												</p>
												<p style="line-height: 35px;font-weight: bold;">
												3. 采购商品及价格
												</p>
												<p style="margin-bottom: 5px;">
												3.1 甲方采购商品的名称、规格型号、数量、单价、供货时间等以双方另行达成的具体协议为准，乙方无正当理由不得拒绝接受甲方订单。
												</p>
												<p style="margin-bottom: 5px;">
												3.2 双方确认价格及采购清单后乙方不可提高商品售价，否则甲方有权拒绝购买该商品并不承担违约责任。如遇商品价格优惠或折扣，则自动适用于甲方的采购，多余货款可折抵新商品或退还甲方。
												</p>
												<p style="line-height: 35px;font-weight: bold;">
												4. 款项结算
												</p>
												<p style="margin-bottom: 5px;">
												4.1 除双方在具体协议中另有约定外，甲方采购商品时均适用<font style="text-decoration: underline;background-color: yellow;">先款后货</font>。
												</p>
												<p style="margin-bottom: 5px;">
												先款后货：甲方付款后(以甲方转账成功之时其计算)，乙方 24 小时内发货，（发货日期以物流运输单所载日期为准）。
												</p>
												<p style="margin-bottom: 5px;">
												先货后款：甲方验收商品后2个工作日内付款。
												</p>
												<p style="margin-bottom: 5px;">
												4.2 支付方式：<font style="text-decoration: underline;background-color: yellow;">银行转账</font>
												</p>
												<p style="margin-bottom: 5px;">
												甲方采取银行转账方式与乙方结算采购款项，乙方指定收款账号信息如下：
												</p>
												<p style="margin-bottom: 5px;">
												乙方户名：
												</p>
												<p style="margin-bottom: 5px;">
												银行账号：
												</p>
												<p style="margin-bottom: 5px;">
												开户行：
												</p>
												<p style="margin-bottom: 5px;">
												乙方如收款账户信息发生变化，应及时通知甲方，否则应承担相应后果。
												</p>
												<p style="margin-bottom: 5px;">
												4.3 乙方为甲方开具增值税专用发票（请财务核实发票付款信息）。在金额较小时，乙方为甲方开具收款收据。
												</p>
												<p style="line-height: 35px;font-weight: bold;">
												5. 商品质量执行标准
												</p>
												<p style="margin-bottom: 5px;">
												5.1 乙方所供应的商品剩余保质期不得少于 2/3 有效保质期（自商品交付日计算），否则有权拒收或者退货，运费由乙方承担。
												</p>
												<p style="margin-bottom: 5px;">
												5.2 乙方承诺商品质量符合国家法律法规及相关行业最新标准，甲方在仓储、运输、销售过程中发现乙方交付的商品不符合本协议、具体协议、商品报价单等的要求及国家有关质量规定的，有权拒收商品或要求在指定时间内退货，由此产生的所有风险及费用由乙方承担；同时甲方有权暂停支付质量瑕疵商品所对应的货款或要求乙方立即退款。
												</p>
												<p style="margin-bottom: 5px;">
												5.3 如甲方顾客因乙方商品质量问题提出索赔要求的，由乙方承担相关赔偿责任。
												</p>
												<p style="margin-bottom: 5px;">
												5.4若乙方提供的任何商品被检验出含有违法、违禁成份，或被确认为假冒伪劣，或违反相关法律规定的，甲方有权立即将该商品撤架及退给乙方，乙方除须赔偿由此给甲方造成的各项损失（如：政府部门罚款、鉴定费、检验费、诉讼费等），还应立即向甲方支付双方本采购合总价款的10%作为违约金。
												</p>
												<p style="margin-bottom: 5px;">
												5.5 甲方从乙方处采购的商品，不论任何时间，如出现厂家召回商品的情况，乙方应立即退回甲方应召回商品的全部货款，甲方负责处理召回商品事宜，如需乙方提供协助，乙方应予以协助。
												</p>
												<p style="line-height: 35px;font-weight: bold;">
												6. 乙方权利义务
												</p>
												<p style="margin-bottom: 5px;">
												6.1 乙方必须将本公司合法有效的进出口代理资质、营业执照、卫生许可证原件、送交甲方查验，并向甲方提供本公司的进出口代理资质复印件、营业执照复印件、卫生许可证复印件以及公司法定代表人及业务负责人身份证复印件供甲方备案。
												</p>
												<p style="margin-bottom: 5px;">
												6.2 乙方有义务向甲方提供甲方所需要的有关商品的资料，包括但不限于商品生产厂家的信息与相关资质、商品检测报告、海关检测的相关证明等。
												</p>
												<p style="margin-bottom: 5px;">
												6.3 乙方在运输过程中应采取相应保护措施，避免商品被挤压变形、破碎、落尘等影响商品售卖的情况。甲方验收商品时如发现前述情况，有权拒绝签收并要求乙方重新发货，发生的额外运输费用由乙方承担。如给甲方造成直接经济损失，乙方应负责赔偿。
												</p>
												<p style="margin-bottom: 5px;">
												6.4 乙方对甲方销售商品的零售价格无要求。甲方自行确定销售价格，乙方不得以甲方零售价格不符合其要求主张任何赔偿或损失。
												</p>
												<p style="line-height: 35px;font-weight: bold;">
												7. 运输及发货、验收
												</p>
												<p style="margin-bottom: 5px;">
												7.1 乙方安排运送货物的物流公司并承担运输费用及运输风险。乙方将商品送至甲方指定地点。
												</p>
												<p style="margin-bottom: 5px;">
												7.2 乙方承担运输过程中的保险费用及保管不善等责任。
												</p>
												<p style="margin-bottom: 5px;">
												7.3 甲方对商品的验收并不免除乙方因商品质量瑕疵而应承担的责任。
												</p>
												<p style="line-height: 35px;font-weight: bold;">
												8.包装要求
												</p>
												<p style="margin-bottom: 5px;">
												8.1 乙方应在《商品报价单》上注明内外包装材质、质量、尺寸及规格。商品包装应符合国家关于商品包装的相关要求。
												</p>
												<p style="margin-bottom: 5px;">
												8.2 甲方在包装方面另有特殊要求时，须与乙方商量，双方经协商一致后达成《特殊包装协议》，该协议视为本合同之补充协议，与本合同具有同等法律效力。
												</p>
												<p style="line-height: 35px;font-weight: bold;">
												9. 瑕疵担保和退货
												</p>
												<p style="margin-bottom: 5px;">
												9.1 即使在甲方验收后发现本商品存在瑕疵或数量不足的，经查证属乙方责任，乙方应于接到甲方通知后24小时内安排更换、补货或退货，并承担所有相关责任与费用。
												</p>
												<p style="margin-bottom: 5px;">
												9.2 不论甲方验收前后，乙方对于甲方提出的数量不足之类的瑕疵有异议时，在收到甲方通知后的24小时以内，乙方有权委托甲方认可的第三方检验机构对本商品进行检验。如乙方未能在24小时内委托甲方认可的第三方检验机构对本商品进行检验的，视为乙方认可该商品存在瑕疵，乙方应承担退货责任。
												</p>
												<p style="margin-bottom: 5px;">
												9.3 甲乙双方事先通过《商品报价单》约定店铺可以退换货的商品，乙方承担店铺及物流中心的退换货并承担相应费用。
												</p>
												<p style="margin-bottom: 5px;">
												9.4 甲乙双方约定对于可退换货商品，选择以下方式进行退货：乙方在接到退货通知15日之内至物流中心提取货物，未按时提货的，乙方应向甲方支付退货商品进货价格5%的延迟退货仓储保管费；乙方延迟30日的，视为乙方放弃该批货物的所有权，甲方有权自行处理，并有权要求乙方立即退还应退换货商品的价款。
												</p>
												<p style="line-height: 35px;font-weight: bold;">
												10. 违约责任
												</p>
												<p style="margin-bottom: 5px;">
												10.1 任何一方直接或间接违反本合同的任何条款或不及时、充分地承担本合同项下其应当承担的义务而构成违约行为的，守约方有权以书面通知要求违约方纠正其违约行为并采取充分、有效、及时的措施消除违约后果并赔偿守约方因违约方之违约行为而遭致的损失。
												</p>
												<p style="margin-bottom: 5px;">
												10.2 任何一方因过错，给对方带来损失，包括经济损失、名誉损失等，守约方有权提出解除合同，并要求对方赔偿损失。
												</p>
												<p style="margin-bottom: 5px;">
												10.3 任何一方逾期应承担逾期违约责任，具体如下：如乙方无故逾期交付商品，每逾期一日，甲方有权要求乙方支付当次采购价格总金额的20%作为违约金。预期超过3日的，甲方有权选择解除合同并要求乙方退还已支付全部商品价款及商品价款50%的违约金。
												</p>
												<p style="margin-bottom: 5px;">
												10.4 不论甲方是否已验收乙方提供的商品，在任何时候发现乙方提供的产品质量不合格，乙方除应双倍退还甲方已支付价款外，因为产品质量问题给消费者及甲方造成的全部损失及责任，均由乙方独自承担。如甲方先行向消费者做出赔偿，则甲方有权向乙方追偿。
												</p>
												<p style="line-height: 35px;font-weight: bold;">
												11.争议及解决
												</p>
												<p style="line-height: 35px;font-weight: bold;">
												12.其他
												</p>
												<p style="margin-bottom: 5px;">
												12.1 双方通讯地址及联系方式同本合同首部所列地址及联系方式。双方可在交流中选择微信、电子邮箱等形式交流信息，双方另行通知的微信号及邮箱地址均为有效联系方式。
												</p>
												<p style="margin-bottom: 5px;">
												12.2 本合同未尽事宜由甲乙双方协商并进行补充。
												</p>
												<p style="margin-bottom: 5px;">
												12.3 本合同一式两份，甲、乙双方各执一份。
												</p>
											</div>
											<div class="colgreen showContent" style="padding-top: 15px;cursor: pointer;">显示全部内容</div>
										</div>
									</div>
							</div>
							<div class="info-panel">
								<div class="hd gray-hd">确认签字</div>
								<div class="bd">
									<dl class="dl-style9">
										<dt>甲方名称与盖章</dt>
										<dd>北京华沁智联科技有限公司</dd>
										<dt>乙方名称与盖章</dt>
										<dd></dd>
										<dt>负责人签名</dt>
										<dd></dd>
										<dt>负责人签名</dt>
										<dd></dd>
									</dl>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="fr btn-mod-panel" style="margin: 20px 0 20px 0;"><a class="btn-mod btn-skin-green" href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ALTERINIT_ORDER%>?id=${order.id}" title="编辑订单">编辑订单</a><a class="btn-mod btn-skin-green" href="javascript:;" title="导出为PDF">导出为PDF</a><a class="btn-mod btn-skin-green" href="javascript:;" title="打印">打印</a></div>
			</div>
		</div>	
	</form>
</div>

<div style="display:none;" class="modal-msk-light"></div>
<form style="display:none" id="deleteordersku" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.DEL_ORDERSKU%>">
	<input type="text" name="id"/>
	<label name="amount"></label>
</form>
<script src="<%=request.getContextPath() %>/js/jquery-3.0.0.min.js"></script>
<script src="<%=request.getContextPath() %>/js/json.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery.validate.min.js"></script>
<script type="text/javascript">
//显示合同全部内容
$(".showContent").click(function(e){
	var hideContent = $(".hideContent");
	var flag = hideContent.css("display");
	if (flag == "none") {
		hideContent.css("display","block");
		$(this).html("收起部分内容");
	} else {
		hideContent.css("display","none");
		$(this).html("显示全部内容");
	}
});
</script>
</body>
</html>