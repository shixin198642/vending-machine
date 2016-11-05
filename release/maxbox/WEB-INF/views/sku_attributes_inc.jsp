<%@page import="com.mjitech.constant.SkuTypeAttributeConstants"%>
<%@ page pageEncoding="UTF-8" import="java.util.*,com.mjitech.model.*"%>
<% List<SkuAttribute> attributes = (List<SkuAttribute>)request.getAttribute("attributes"); %>
<div class="hd">分类属性</div>
							<div class="bd">
								<div class="clearfix">
									<dl class="infobox category-panel">
										<%
										int index = 1;
										for(SkuAttribute sa : attributes){ %>
										<%if(sa.getSkuTypeAttribute().getType()==SkuTypeAttributeConstants.ATTRIBUTE_TYPE_INPUT){ %>
										<dt><%=sa.getSkuTypeAttribute().getName() %></dt>
										<dd>
											<label for="" class="labelbox droplist-panel">
												<input type="hidden" name="attribute_id_<%=index %>" value="<%=sa.getId()%>"/>
												<input type="hidden" name="attribute_skutype_<%=index %>" value="<%=sa.getSkuTypeId()%>"/>
												<input type="hidden" name="attribute_skuattributetype_<%=index %>" value="<%=sa.getSkuTypeAttributeId()%>"/>
												<input type="text" name="attribute_value_<%=index %>" value="<%=sa.getValue()%>">
												<%if(sa.getSkuTypeAttribute().getUnit()!=null&&sa.getSkuTypeAttribute().getUnit().length()>0){ %>
												<span class="drop-text"><%=sa.getSkuTypeAttribute().getUnit() %></span>
												<%} %>
											</label>
										</dd>
										<%}else if(sa.getSkuTypeAttribute().getType()==SkuTypeAttributeConstants.ATTRIBUTE_TYPE_SELECT){
											String options = sa.getSkuTypeAttribute().getOptions();
											String[] selectOptions = options.split(",");
										%>
										<dt><%=sa.getSkuTypeAttribute().getName() %></dt>
										<dd>
											<label for="" class="labelbox droplist-panel">
												<input type="hidden" name="attribute_id_<%=index %>" value="<%=sa.getId()%>"/>
												<input type="hidden" name="attribute_skutype_<%=index %>" value="<%=sa.getSkuTypeId()%>"/>
												<input type="hidden" name="attribute_skuattributetype_<%=index %>" value="<%=sa.getSkuTypeAttributeId()%>"/>
												<select class="drop-select drop-all" name="attribute_value_<%=index %>">
													<%for(String selectOption : selectOptions){ %>
													<option value="<%=selectOption%>"><%=selectOption%></option>
													<%} %>
												</select>
												<%if(sa.getSkuTypeAttribute().getUnit()!=null&&sa.getSkuTypeAttribute().getUnit().length()>0){ %>
												<span class="drop-text"><%=sa.getSkuTypeAttribute().getUnit() %></span>
												<%} %>
											</label>
										</dd>
										<%} %>
										<%
										index++;} %>
										
									</dl>
								</div>
							</div>