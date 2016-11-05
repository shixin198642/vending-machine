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
			<a class="btn-mod btn-skin-blue" href="<%=context %>/web/create_picking_form.action?warehouse_id=${warehouseId}" title="创建分拣单">+创建分拣单</a>
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
				    <c:forEach items="${pickingList}" var="picking">
					<tr>
						<td>
							<a class="colgreen" href="<%=context %>/web/get_picking.action?pickingId=${picking.id}"">${picking.id}</a>
						</td>
						<td>等待分拣</td>
						<td>查看</td>
						<td>${picking.createDatetime}</td>
						<td>${picking.updateDatetime}</td>
						<td>
							<span class="alink">
								<a class="colgreen" href="" title="打印">打印</a>
							</span>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div><!--mainbox-->


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