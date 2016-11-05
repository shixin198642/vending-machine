<%@page import="com.mjitech.constant.SkuConstants"%>
<%@ page pageEncoding="UTF-8"%>
<%Sku sku = (Sku)request.getAttribute("sku");
List<SkuBrand> brands = (List<SkuBrand>)request.getAttribute("all_brands");
List<SkuType> types = (List<SkuType>)request.getAttribute("all_types");
List<DictArea> countries = (List<DictArea>)request.getAttribute("all_countries");
List<SkuSpec> specs = (List<SkuSpec>) request.getAttribute("specs");
SkuType selectedType = (SkuType)request.getAttribute("selected_type");
%>
<%@include file="top_html.jsp" %>
			<div class="mainbox">
				<div class="nav-panel clearfix">
					<div class="fl nav">
						<span><a href="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+"/"+WebPageConstants.SKU_LIST%>">商品</a></span><i class="arrow arrow-gray-r"></i><span class="end"><%if(sku.getStatus()==SkuConstants.STATUS_DRAFT){ %>创建<%}else{ %>编辑<%} %>商品</span>
					</div>
				</div>
				<div class="content">
				<form id="sku_form">
					<div class="plr20 pt30">
						<div class="info-panel">
							
							<input type="hidden" id="sku_id" name="sku_id" value="<%=sku.getId()%>"/>
							<div class="hd">基本信息</div>
							<div class="bd">
								<div>
									<dl class="infobox">
										<dt><span class="star">*</span>SKU Number</dt>
										<dd>
										<%=sku.getSkuNumber()%>	
										</dd>
										<dt><span class="star">*</span>商品条形码</dt>
										<dd>
											<label for="" class="labelbox">
												<input type="text" id="barcode" name="barcode" value="<%=sku.getBarcode()%>"/>
												<a class="bg-style1 line-style btn-verify" href="" title="验证">验证</a>
											</label>
											<a class="colgreen ml20" href="" title="扫描条形码">扫描条形码</a>
										</dd>
										
										<dt><span class="star">*</span>商品名称</dt>
										<dd>
											<label for="" class="labelbox">
												<input class="w386" type="text" id="sku_name" name="name" value="<%=sku.getName()%>">
											</label>
											<a class="colgreen ml20" href="" title="商品命名规范">商品命名规范</a>
										</dd>
										<dt><span class="star">*</span>商品简称</dt>
										<dd>
											<label for="" class="labelbox">
												<input class="w386" type="text" id="sku_shortname" name="shortName" value="<%=sku.getShortName()%>">
											</label>
										</dd>
										<dt><span class="star">*</span>品牌</dt>
										<dd>
											<laebl class="labelbox">
											<select class="drop-select drop-all" name="brand" id="brand">
												<%for(SkuBrand brand:brands){ %>
												<option value="<%=brand.getId()%>" <%if(sku.getBrand()==brand.getId()){ %>selected<%} %>><%=brand.getName() %></option>
												<%} %>
											</select>
											</laebl>
											<dl class="inlblk clearfix">
												<dt>生产地</dt>
												<dd>
													<laebl class="labelbox">
													<select class="drop-select drop-all" name="country" id="country">
														<%for(DictArea country:countries){ %>
														<option value="<%=country.getId()%>" <%if(sku.getCountry()==country.getId()){ %>selected<%} %>><%=country.getName() %></option>
														<%} %>
													</select>
													</laebl>
												</dd>
											</dl>
										</dd>
										<dt><span class="star">*</span>分类</dt>
										<dd class="category">
											<laebl class="labelbox">
												<select class="drop-select drop-all" name="parentCategory" id="parentCategory" onchange="changeParentType(this.value)">
														<%
														SkuType currentType = types.get(0);
														for(SkuType type:types){ %>
														<option value="<%=type.getId()%>" <%if(selectedType!=null && selectedType.getParentId()==type.getId()){ currentType=type;%>selected<%} %>><%=type.getName() %></option>
														<%} %>
													</select>
											</laebl>
											<laebl class="labelbox">
												<select class="drop-select drop-all" name="category" id="category" onchange="changeSkuCategory(this.value)">
													<%
														for(SkuType type:currentType.getSubTypes()){ %>
														<option value="<%=type.getId()%>" <%if(selectedType!=null && selectedType.getId()==type.getId()){ %>selected<%} %>><%=type.getName() %></option>
														<%} %>
												</select>
											</laebl>
										</dd>
										<dt><span class="star">*</span>零售单位</dt>
										<dd>
											<laebl class="labelbox"><input class="w80" name="unit" type="text" placeholder="个" value="<%=sku.getUnit()%>"/></laebl>
											<dl class="inlblk inl-two clearfix">
												<dt>建议零售价</dt>
												<dd>
													<laebl class="labelbox"><input type="text" placeholder="0.0" name="msrp" value="<%=sku.getMsrp() %>"/></laebl>
												</dd>
												<dt>保质期（天）</dt>
												<dd>
													<laebl class="labelbox"><input type="text" placeholder="0" name="expirationDays" value="<%=sku.getExpirationDays() %>"></laebl>
												</dd>
											</dl>
										</dd>
										
										<dt>包装规格</dt>
										<dd>
											<laebl class="labelbox"><input type="text" name="length" value="<%=sku.getLength() %>" placeholder="长（mm）"></laebl>
											<laebl class="labelbox"><input type="text" name="width" value="<%=sku.getWidth() %>" placeholder="宽（mm）"></laebl>
											<laebl class="labelbox"><input type="text" name="height" value="<%=sku.getHeight() %>" placeholder="高（mm）"></laebl>
										</dd>
										<dt>标签</dt>
										<dd>
											<laebl class="labelbox"><input class="w500" type="text" name="tags" value="<%=sku.getTags() %>" placeholder="多个标签之间用逗号分隔"></laebl>
										</dd>
										<dt class="vtop">备注</dt>
										<dd>
											<textarea class="w500" name="remarks" id="" cols="30" rows="4"><%=sku.getRemarks() %></textarea>
										</dd>
									</dl>
									<div class="upload-img-panel">
										<input type="hidden" id="new_sku_image" name="new_sku_image"/>
										<input type="hidden" id="new_sku_image_id" name="new_sku_image_id"/>
										<div class="upload-img">
											<%String skuimage = sku.getImagePath();
											if(skuimage==null||skuimage.length()==0){skuimage="images/noimage.jpg";}else{skuimage="static"+skuimage;}%>
											<img id="sku_image" src="<%=context %>/<%=skuimage%>" alt="<%=sku.getName()%>">
										</div>
										<div class="imgmsk"><a href="javascript:void(0)" onclick="uploadTmpImage()" title="上传商品图片">上传商品图片</a></div>
									</div>
								</div>
							</div>
							
						</div>
						<div class="info-panel">
							<div class="hd">采购规格</div>
							<div class="bd">
								<div>
									<input type="hidden" name="spec_count" id="spec_count" value="<%=specs.size()%>"/>
									<dl class="infobox clearfix" id="spec_container">
										<%for(int i=1;i<=specs.size();i++){ 
											SkuSpec spec = specs.get(i-1);%>
										<dt><%if(i==1){ %><span class="star">*</span><%} %>
											规格<em class="size-num"><%=i %></em></dt>
										<dd>
											<label for="" class="labelbox">
												<input type="hidden" name="spec_id_<%=i%>" value="<%=spec.getId()%>"/>
												<input type="hidden" name="spec_type_<%=i %>" id="spec_type_<%=i %>" value="<%=spec.getType()%>"/>
												<input class="w57" type="text" name='spec_amount_<%=i %>' value="<%=spec.getAmount()==0?"":spec.getAmount()%>"/>
												<span class="line-style bg-style1 w50">个</span>
											</label>
											<em class="plr20">／</em>
											<label for="" class="labelbox">
												<input class="w68" type="text" name="spec_unit_<%=i %>" value="<%=spec.getUnit()==null?"":spec.getUnit() %>" placeholder="箱">
											</label>
											<span class="ml20" id="spec_default_span_<%=i%>">
											<%if(spec.getType()==0){ %>
											<a class="colgreen" href="javascript:void(0)" onclick="setDefaultSpec(<%=i %>)" title="设置为默认规格">设置为默认规格</a>
											<%}else{ %>
											<i class="checked"></i>默认规格
											<%} %>
											</span>
										</dd>
										<%} %>
										
										
									</dl>
								</div>
							</div>
						</div>
						<div class="info-panel" id="attributes_div">
							<%@include file="sku_attributes_inc.jsp" %>
						</div>
						<div class="info-panel">
							<div class="hd">库存管理</div>
							<div class="bd">
								<div class="clearfix">
									<dl class="infobox category-panel">
										<dt>最大库存值</dt>
										<dd>
											<label for="" class="labelbox">
												<input type="text" name="maxStock" id="maxStock" value="<%=sku.getMaxStock() %>" placeholder="0">
											</label>
										</dd>
										</dd>
										<dt>安全库存值</dt>
										<dd>
											<label for="" class="labelbox">
												<input type="text" name="safeStock" id="safeStock" value="<%=sku.getSafeStock() %>" placeholder="0">
											</label>
										</dd>
										</dd>
										<dt>最低库存值</dt>
										<dd>
											<label for="" class="labelbox">
												<input type="text" name="minStock" id="minStock" value="<%=sku.getMinStock() %>" placeholder="0">
											</label>
										</dd>
									</dl>
								</div>
								<!-- 总宽度为600 -->
								<div class="kcmanage-panel">
									<div class="prognum clearfix">
										<!-- 不需要计算 -->
										<span class="start">0</span>
										<!-- 距离最左侧的位置所占百分比减1，只需要计算enum -->
										<!-- 88/600=15% 减1=14% -->
										<span style="left:14%;" class="enum" id="min_stock_span"><%=sku.getMinStock() %></span>
										<!-- (88+360)/600=75% 减1=74% -->
										<span style="left:74%;" class="enum" id="safe_stock_span"><%=sku.getSafeStock() %></span>
										<!-- 不需要计算 -->
										<span class="end" id="max_stock_span"><%=sku.getMaxStock() %></span>
									</div>
									<div class="progbar clearfix">
										<!-- 宽度值的百分比 -->
										<!-- 88/600=15% -->
										<span style="width:15%;" class="prog1" id="min_stock_progress"></span>
										<!-- 360/600=60% -->
										<span style="width:60%;" class="prog2" id="safe_stock_progress"></span>
										<!-- 150/600=25% -->
										<span style="width:25%;" class="prog3" id="max_stock_progress"></span>
									</div>
									<div class="progtxt clearfix">
										<!-- 距离最左侧的位置所占百分比减5 -->
										<!-- 88/600=15% 减5=10% -->
										<span style="left:10%;" class="etxt" id="min_stock_word">最低库存值</span>
										<span style="left:70%;" class="etxt" id="safe_stock_word">安全库存值</span>
										<span class="end">最大库存值</span>
									</div>
								</div>
							</div>
						</div>
						<div class="btn-action-panel">
							<input type="hidden" name="status" id="status" value="<%=sku.getStatus()%>"/>
							<input type="checkbox" name="disable_sku" onclick="disableSku(this.value)" <%if(sku.getStatus()==SkuConstants.STATUS_UNPUBLISHED){ %>checked<%} %>/>暂不供应此商品
							<a class="btn-action btn-blue" href="javascript:void(0)" onclick="save()" title="完成并保存">完成并保存</a>
							<a class="btn-action btn-cancel" href="javascript:void(0)" onclick="discard()" title="放弃编辑">放弃编辑</a>
						</div>
					</div>
					</form>
				</div>	
			</div>
		</div>
	</div>
</div>
<%@include file="upload_file_div_inc.jsp" %>
<%String sessionId = request.getSession().getId(); %>
<script>
var sessionid = '<%=sessionId%>';
</script>
<script src="<%=context%>/js/jquery-3.0.0.min.js"></script>
<script src="<%=context%>/js/jquery-ui.js"></script>
<script src="<%=context%>/js/base.js"></script>
<script src="<%=context%>/js/add_update_sku.js"></script>
<script src="<%=context%>/js/jquery.uploadify.min.js"></script>
</body>
</html>