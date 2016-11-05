function warehouse_get(){
	  var formurl = context+"/web/warehouse_get.action?id=1";
	  $.ajax( {    
		    url:formurl,
		    dataType:'json',
		    data:data,
		    contentType: "application/json",
		    type:'post',   
		    success:function(data) {
	    		if(data.is_succ){
	    			location.href=context+"/web/sku_list.action";
	    		}else{
	    			 $("#error_tip").html(data.error_message);
	    			  $("#error_tip").show();
	    		}
		     },    
		     error : function(request, status, e) {    
		         alert(JSON.stringify(request));
		         alert(status);
		         alert(e);
		     }    
		}); 
}