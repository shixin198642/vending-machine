$(document).ready(function(){
	$(document).keyup(function(event){
	  if(event.keyCode ==13){
		  login();
	  }
	});
})


function checkRemember(ischeck){
	if(ischeck){
		$("#remember_login").val("y");
	}else{
		$("#remember_login").val("n");
	}
	
}

function login(){
	  $("#error_tip").hide();
	  if(!$("#username").val()){
		  $("#error_tip").html("须填写用户名");
		  $("#error_tip").show();
		  return false;
	  }
	  if(!$("#password").val()){
		  $("#error_tip").html("须填写密码");
		  $("#error_tip").show();
		  return false;
	  }
	  var theform = $("#login_form");
	  var formurl = context+"/web/login.action";
	  var data = JSON.stringify(theform.serializeObject());
	  $.ajax( {    
		    url:formurl,
		    dataType:'json',
		    data:data,
		    contentType: "application/json",
		    type:'post',   
		    success:function(data) {
		    	if(typeof(data)!='object'){
		    		data = jQuery.parseJSON(data);
		    	}
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