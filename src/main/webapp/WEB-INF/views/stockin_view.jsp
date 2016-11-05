<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="top_html.jsp" %>



<div class="mainbox">
	<div class="nav-panel clearfix">
		<div class="fl nav">
			<label for="" class="labelbox">
				<select class="drop-select drop-180" name="" id="">
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
			<span class="end">入库单</span>
		</div>
	</div>
	<div class="content">
		<!-- <div class="paddbox"> <div class="btn-mod-panel"> <a class="btn-mod 
			btn-skin-blue" href="" title="生成入库单">重新生成入库单</a> </div> </div> -->
		<div class="table table-a">
			<table width="100%" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th>入库单编号</th>
						<th>负责人</th>
						<th>联系电话</th>
						<th>状态</th>
						<th>入库日期</th>
						<th>实际入库实际</th>
					</tr>
				</thead>
				<tbody>
				    <c:forEach items="${stockInList}" var="stockIn">
					<tr>
						<td>
							<a class="colgreen" href="" title="869999">${stockIn.id}</a>
						</td>
						<td>${stockIn.userinfo.displayName}</td>
						<td>${stockIn.userinfo.mobile}</td>
						<td>${stockIn.type}</td>
						<td>06-01</td>
						<td>${stockIn.updateDatetime}</td>
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
</body>
</html>