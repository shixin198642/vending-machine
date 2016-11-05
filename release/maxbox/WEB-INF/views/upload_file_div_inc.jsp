<%@ page pageEncoding="UTF-8"%>
<div class="modal-msk" id="common_mask"></div>
<div id="dialog_div">
<div class="modal-container upload-tip" id="upload_image_div">
	<div class="modal-tip">
		<div class="modal-hd">
			<h4 class="modal-title">上传商品图片</h4>
			<a class="modal-close" href="javascript:void(0)" title="关闭" onclick="closeUploadImage()"></a>
		</div>
		<div class="modal-bd">
			<div class="upload-img-box">
				<input type="hidden" id="uploaded_img_path" value=""/>
				<input type="hidden" id="uploaded_img_id" value="0"/>
				<input type="file" name="file_upload" id="file_upload" />
				<span class="ml20">支持jpg、png、bmp格式的图片，且文件小于20M</span>
			</div>
			<div class="img-view-panel clearfix">
				<div class="actualimg fl"><img id="uploaded_img" width="160" height="160" src="<%=context %>/images/noimage.jpg" alt=""></div>
				
			</div>
			<div class="tip-btns">
				<a class="btn-action btn-blue" href="javascript:void(0)" title="保存" onclick="saveTmpImage()">保存</a>
				<a class="btn-action btn-cancel" href="javascript:void(0)" onclick="closeUploadImage()" title="取消">取消</a>
			</div>
		</div>
	</div>
</div>
</div>