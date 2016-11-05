function changeParentType(typeid){
	$.ajax( {    
	    url:context+'/web/get_subtypes.action?type_id='+typeid,
	    type:'get',   
	    success:function(data) {
	    	if(typeof(data)!='object'){
	    		data = jQuery.parseJSON(data);
	    	}
	    	if(data.is_succ){
	    		var types = data.types;
	    		$("#category").empty();
	    		for(var i=0;i<types.length;i++){
	    			var type = types[i];
	    			if(i==0){
	    				changeSkuCategory(type.id);
	    			}
	    			$("#category").append("<option value='"+type.id+"'>"+type.name+"</option>");
	    		}
	    	}
    		
	     },    
	     error : function(request, status, e) {    
	         alert(JSON.stringify(request));
	          alert(status);
	          alert(e);
	     }    
	}); 
}
function changeSkuCategory(typeid){
	var skuid = $("#sku_id").val();
	$.ajax( {    
	    url:context+'/web/sku_attributes.action?sku_id='+skuid+"&type_id="+typeid,
	    type:'get',   
	    success:function(data) {
	    	$("#attributes_div").html(data);
    		
	     },    
	     error : function(request, status, e) {    
	         alert(JSON.stringify(request));
	          alert(status);
	          alert(e);
	     }    
	}); 
}

function save(){
	if(!_checkFormElement($("#barcode"),'商品条形码不能为空')){
		return false;
	}
	if(!_checkFormElement($("#sku_name"),'商品名称不能为空')){
		return false;
	}
	$.ajax( {    
	    url:context+'/web/add_update_sku.action',
	    data: $("#sku_form").serialize(),
	    type:'POST',
	    dataType:'json',
	    success:function(data) {
	    	if(typeof(data)!='object'){
	    		data = jQuery.parseJSON(data);
	    	}
	    	if(data.is_succ){
	    		location.href=context+"/web/sku_list.action";
	    	}
	    	else{
	    		alert(data.error_message);
	    	}
	     },    
	     error : function(request, status, e) {    
	         alert(JSON.stringify(request));
	          alert(status);
	          alert(e);
	     }    
	}); 
}

function discard(){
	location.href=context+"/web/sku_list.action";
}

function setDefaultSpec(i){
	$("#spec_default_span_"+i).html('<i class="checked"></i>默认规格');
	$("#spec_type_"+i).val(1);
	for(var j=1;j<10;j++){		
		if(j!=i&&$("#spec_default_span_"+j).length>0){
			$("#spec_type_"+j).val(0);
			$("#spec_default_span_"+j).html('<a class="colgreen" href="javascript:void(0)" onclick="setDefaultSpec('+j+')" title="设置为默认规格">设置为默认规格</a>');
		}
		
	}	
}

function addSpec(){
	$("#spec_container").append();
}

function disableSku(ischecked){
	if(ischecked){
		$("#status").val(2);
	}else{
		$("#status").val(3);
	}
}

function uploadTmpImage(){
	$("#common_mask").show();
	$("#upload_image_div").show();
	//$("#dialog_div").dialog();
}

function closeUploadImage(){
	$("#common_mask").hide();
	$("#upload_image_div").hide();
}
$(function() {
    $('#file_upload').uploadify({
    	'buttonText' : '选择文件',
        'swf'      : context+'/js/uploadify.swf',
        'auto':true,  
        'fileObjName'   : 'file',  
        'uploader' : context+'/web/upload_tmp_image.action;jsessionid='+sessionid,
        'onUploadError' : function(file, errorCode, errorMsg, errorString) {
            alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
        },
	    'onUploadSuccess' : function(file, data, response) {
	    	if(typeof(data)!='object'){
	    		data = jQuery.parseJSON(data);
	    	}
	    	if(data.is_succ){
	    		$("#uploaded_img").attr("src",context+"/static/"+data.file.filePath);
	    		$("#uploaded_img_path").val(data.file.filePath);
	    		$("#uploaded_img_id").val(data.file.id);
	    	}
	    }
        
    });
});

function saveTmpImage(){
	$("#new_sku_image").val($("#uploaded_img_path").val());
	$("#sku_image").attr("src",$("#uploaded_img").attr("src"));
	$("#new_sku_image_id").val($("#uploaded_img_id").val());
	closeUploadImage();
}

function refreshStockProgress(){
	var min = parseInt($("#minStock").val());
	var max = parseInt($("#maxStock").val());
	var safe = parseInt($("#safeStock").val());
	var minPro = 0;
	var safePro = 0;
	var maxPro = 0;
	var minWordPos = 0;
	var safeWordPos = 0;
	if(max!=0){
		minPro = min/max*100;
		safePro = (safe-min)/max*100;
		maxPro = 100-safePro-minPro;
		if(minPro>5){
			minWordPos = minPro-5;
		}
		else{
			minWordPos = 1;
		}
		if((minPro+safePro)>5){
			safeWordPos = minPro+safePro-5;
		}
		else{
			safeWordPos = 1;
		}
		$("#min_stock_span").html(min);
		$("#max_stock_span").html(max);
		$("#safe_stock_span").html(safe);
		$("#min_stock_span").css("left",(minWordPos+4)+"%");
		$("#safe_stock_span").css("left",(safeWordPos+4)+"%");
		$("#min_stock_progress").css("width",minPro+"%");
		$("#safe_stock_progress").css("width",safePro+"%");
		$("#max_stock_progress").css("width",maxPro+"%");
		$("#min_stock_word").css("left",minWordPos+"%");
		$("#safe_stock_word").css("left",safeWordPos+"%");
	}
}
function checkStockInput(){
	var min = parseInt($("#minStock").val());
	var max = parseInt($("#maxStock").val());
	var safe = parseInt($("#safeStock").val());
	if(isNaN(max)){
		max = 0;
	}
	if(isNaN(safe)){
		safe = 0;
	}
	if(isNaN(min)){
		min = 0;
	}
	if(safe>max){
		safe = max;
	}
	if(min>safe){
		min = safe;
	}
	$("#safeStock").val(safe);
	$("#minStock").val(min);
}
$(document).ready(function(){
	refreshStockProgress();
	$("#minStock").numeral();
	$("#maxStock").numeral();
	$("#safeStock").numeral();
	$("#minStock").on('input',function(e){
		checkStockInput();
		refreshStockProgress();
		
	});
	$("#maxStock").on('input',function(e){
		checkStockInput();
		refreshStockProgress();
	});
	$("#safeStock").on('input',function(e){
		checkStockInput();
		refreshStockProgress();
	});
});