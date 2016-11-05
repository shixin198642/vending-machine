<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="top_html.jsp" %>

<div class="mainbox">
	<div class="nav-panel clearfix">
		<div class="fl nav">
			<label for="" class="labelbox">
				<select class="drop-select drop-all" name="" id="machine_select">
				<c:forEach items="${warehouseList}" var="warehouse">
				    <c:choose> 
					  <c:when test="${warehouse.id == machineId}">
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
				<a href="" title="门店机器">门店机器</a>
			</span>
			<i class="arrow arrow-gray-r"></i>
			<span class="end">机器补货请求</span>
		</div>
		<div class="fr btn-mod-panel">
			<a id="request_create" class="btn-mod btn-skin-blue" href="#" title="创建机器补货请求">+ 创建机器补货请求</a>
		</div>
	</div>
	<div class="content">
		<div class="table table-a">
			<table width="100%" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th>SKU#</th>
						<th>商品名称</th>
						<th>请求仓库</th>
						<th>请求数量</th>
						<th>实际配送数量</th>
						<th>状态</th>
						<th>配送单号</th>
						<th>最后更新时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${requestList}" var="request">
					<tr>
						<td>
							<a class="colgreen" href="" title="869999">${request.sku.id}</a>
						</td>
						<td>
							<a class="colgreen" href="" title="可口可乐樱桃味汽水300ml">${request.sku.name}</a>
							<div class="itemcard">
								<div class="info-panel">
									<div class="pic">
										<img src="../images/sprite.png" alt="" />
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
							<a class="colgreen" href="" title="${request.warehouseName}">${request.warehouseName}</a>
						</td>
						<td>${request.quantity}</td>
						<td></td>
						<td>等待处理</td>
						<td>
							<a class="colgreen" href="#" title=""></a>
						</td>
						<td>${request.updateDatetime}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div><!--mainbox-->



<!--
<div class="modal-container" id="request_create_panel">
	<div class="modal-tip">
		<div class="modal-hd">
			<h4 class="modal-title">创建机器补货请求</h4>
			<a id="xclose" class="modal-close" href="javascript:void(0)" title="关闭"></a>
		</div>
		<div class="modal-bd">
			<div class="info-panel">
			    <form id="requestForm">
			    <input type="hidden" name="machineId" value="${machineId}" />
				<dl class="clearfix infobox">
					<dt><span class="star">*</span>商品名称</dt>
					<dd>
						<laebl class="labelbox">
							<select class="drop-select drop-220" name="skuId">
								<option value="">选择商品</option>
								<c:forEach items="${requestList}" var="request">
								<option value="${request.sku.id}">${request.sku.name}</option>
								</c:forEach>								
							</select>
						</laebl>
					</dd>
					<dt><span class="star">*</span>请求数量</dt>
					<dd>
						<laebl class="labelbox">
							<input class="w80" type="text" name="quantity">
						</laebl>
					</dd>
					</form>
				</dl>
			</div>
			<div class="tip-btns">
				<a id="request_create_save" class="btn-action btn-blue" href="#" title="保存">保存</a>
				<a id="request_create_cancel" class="btn-action btn-cancel" href="#" title="取消">取消</a>
			</div>
		</div>
	</div>
</div>

-->
<!-- 创建机器补货请求 e -->
<div class="modal-container" id="request_create_panel">
	<div class="modal-tip">
		<div class="modal-hd">
			<h4 class="modal-title">创建机器补货请求</h4>
			<a class="modal-close" href="javascript:void(0)" title="关闭"></a>
		</div>
		<div class="modal-bd">
			<div class="info-panel">
				<dl class="clearfix infobox">
				    <form id="requestForm">
			        <input type="hidden" name="machineId" value="${machineId}" />
					<dt><span class="star">*</span>商品名称</dt>
					<dd>
						<laebl class="labelbox">
							<select class="drop-select drop-220" name="skuId">
								<option value="">选择商品</option>
								<c:forEach items="${skuList}" var="sku">
								<option value="${sku.id}">${sku.name}</option>
								</c:forEach>								
							</select>
						</laebl>
					</dd>
					<div style="clear:both"></div>
					<dt><span class="star">*</span>请求数量</dt>
					<dd>
						<laebl class="labelbox">
							<input class="w80" type="text" name="quantity"/>
						</laebl>
						<span class="error" style="display:none">错误信息</span>
					</dd>
					</form>
				</dl>
			</div>
			<div class="tip-btns">
				<a id="request_create_save" class="btn-action btn-blue" href="" title="提交">提交</a>
				<a id="request_create_cancel" class="btn-action btn-cancel" href="" title="取消">取消</a>
			</div>
		</div>
	</div>
</div>
<!-- 创建机器补货请求 s -->


		</div>
	</div>
</div>

<script type="text/javascript">
$(function() {
	$("#request_create").click(function() {
        $(".modal-container").show();
    });
	$("#request_create_cancel").click(function() {
        $(".modal-container").hide();
    });
	$("#xclose").click(function() {
        $(".modal-container").hide();
    });  
    $("#requestForm").submit(function(e) {	
		var url = "<%=context %>/web/create_request.action";
		$.ajax({
		   type: "POST",
		   url: url,
	       data: $("#requestForm").serialize(), // serializes the form's elements.
		   success: function(data){
		       location.href="<%=context %>/web/get_request_by_machine.action?machine_id="+${machineId};	
		   }
		});		
		e.preventDefault(); // avoid to execute the actual submit of the form.
    }); //form submit
	$("#request_create_save").click(function() {
		$('#requestForm').trigger('submit');      
    });
    
	$( "#machine_select" ).change(function() {
	    var wh_id= $( this ).val();
	    location.href="<%=context %>/web/get_request_by_machine.action?machine_id="+wh_id;	
    });       
});
</script>
</body>
</html>