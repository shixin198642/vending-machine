<%@page import="com.mjitech.constant.WarehouseConstants"%>
<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="top_html.jsp" %>
<%
List<WarehouseManager> wms = (List<WarehouseManager>)request.getAttribute("managers");
Warehouse selected = (Warehouse)request.getAttribute("selected_store_machine");
List<Warehouse> warehouses = (List<Warehouse>)request.getAttribute("all_store_machines");
%>
<div class="mainbox">
	<div class="nav-panel clearfix">
		<div class="fl nav">
			<label for="" class="labelbox">
				<select class="drop-select" name="machine_store" id="machine_store" onchange="changeMachineStore(this.value)">
				<%for(Warehouse w : warehouses){ %>
					<option value="<%=w.getId() %>" <%if(selected.getId()==w.getId()){ %>selected="selected"<%} %>><%=w.getName() %></option>
				<%} %>
				</select>
			</label>
			<span>
				<a href="" title="<%=WarehouseConstants.getTypeName(selected.getType()) %>"><%=WarehouseConstants.getTypeName(selected.getType()) %></a>
			</span>
			<i class="arrow arrow-gray-r"></i>
			<span class="end"><%=WarehouseConstants.getTypeName(selected.getType()) %>详情</span>
		</div>
		<div class="fr btn-mod-panel">
			<a class="btn-mod btn-skin-green" href="" title="创建新门店/机器">+ 创建新门店/机器</a>
		</div>
	</div>
	<div class="content">
		<div class="plr20 pt30">
			<div class="clearfix">
				<div class="info-panel">
					<div class="hd">基本信息</div>
					<div class="bd">
						<div class="clump-pro-info-panel">
							<div class="plr20 pro-info-panel">
								<dl class="pro-info-pre">
									<dt><%=WarehouseConstants.getTypeName(selected.getType()) %>编号</dt>
									<dd><%=selected.getId() %></dd>
									<dt><%=WarehouseConstants.getTypeName(selected.getType()) %>名称</dt>
									<dd><%=selected.getName() %></dd>
									<dt>地址</dt>
									<dd><%=selected.getAddress() %></dd>
									<dt>状态</dt>
									<dd><%=WarehouseConstants.getStatusName(selected.getStatus()) %></dd>
								</dl>
								<%if(selected.getManagers()!=null){ %>
								<%for(WarehouseManager wm : selected.getManagers()){ %>
								<dl class="pro-info-pre">
									<dt><%=WarehouseConstants.getManagerTypeName(wm.getType()) %></dt>
									<dd><%=wm.getManager().getDisplayName() %></dd>
									<dt>联系电话</dt>
									<dd><%=wm.getManager().getMobile() %></dd>
								</dl>
								<%} %>
								<%} %>
							</div>
						</div>
						<!-- div class="upload-img-panel">
							<div class="upload-img">
								<img src="../images/sidebar.png" alt="" />
							</div>
							<div class="btn-mod-panel">
								<a class="btn-mod btn-skin-green" href="" title="修改商品图片">修改商品图片</a>
							</div>
						</div-->
					</div>
				</div>
				<div class="info-panel">
					<div class="hd">仓库信息</div>
					<div class="bd">
						<div class="clump-pro-info-panel">
							<%if(selected.getWarehouseParent()!=null){ %>
							<div class="plr20 pro-info-panel">
								<dl class="pro-info-pre">
									<dt>仓库编号</dt>
									<dd><%=selected.getWarehouseParent().getId() %></dd>
									<dt>仓库名称</dt>
									<dd>
										<a class="colgreen" href="javascript:void(0)" title="<%=selected.getWarehouseParent().getName() %>"><%=selected.getWarehouseParent().getName() %></a>
									</dd>
									<%if(selected.getWarehouseParent().getManagers()!=null){ %>
									<%for(WarehouseManager wm : selected.getWarehouseParent().getManagers()){ %>
									<dt>仓库负责人</dt>
									<dd><%=wm.getManager().getDisplayName() %></dd>
									<dt>联系电话</dt>
									<dd><%=wm.getManager().getMobile() %></dd>
									<%} %>
									<%} %>
								</dl>
								<dl class="pro-info-pre">
									<dt>配送员</dt>
									<dd>王某某</dd>
									<dt>联系电话</dt>
									<dd>010-00000</dd>
								</dl>
							</div>
							<%} %>				
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div><!--mainbox-->

		</div>
	</div>
</div>
<script>
function changeMachineStore(wid){
	location.href=context+"<%=WebPageConstants.COMMON_PREFIX+WebPageConstants.MACHINE_DETAIL_HOME%>?warehouseId="+wid;
}
</script>
</body>
</html>