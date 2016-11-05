$.fn.serializeObject = function()
  {
      var o = {};
      var a = this.serializeArray();
      $.each(a, function() {
          if (o[this.name] !== undefined) {
              if (!o[this.name].push) {
                  o[this.name] = [o[this.name]];
              }
              o[this.name].push(this.value || '');
          } else {
              o[this.name] = this.value || '';
          }
      });
      return o;
  };
  
  $(function(){  
	  
	  //文本框只能输入数字，并屏蔽输入法和粘贴  
	  $.fn.numeral = function() {     
	             $(this).css("ime-mode", "disabled");     
	             this.bind("keypress",function(e) {     
	             var code = (e.keyCode ? e.keyCode : e.which);  //兼容火狐 IE      
	                 if(!$.support.boxModel&&(e.keyCode==0x8))  //火狐下不能使用退格键     
	                 {     
	                      return ;     
	                     }     
	                     return code >= 48 && code<= 57;     
	             });     
	             this.bind("blur", function() {     
	                 if (this.value.lastIndexOf(".") == (this.value.length - 1)) {     
	                     this.value = this.value.substr(0, this.value.length - 1);     
	                 } else if (isNaN(this.value)) {     
	                     this.value = "";     
	                 }     
	             });     
	             this.bind("paste", function() {     
	                 var s = clipboardData.getData('text');     
	                 if (!/\D/.test(s));     
	                 value = s.replace(/^0*/, '');     
	                 return false;     
	             });     
	             this.bind("dragenter", function() {     
	                 return false;     
	             });     
	             this.bind("keyup", function() {     
	             if (/(^0+)/.test(this.value)) {     
	                 this.value = this.value.replace(/^0*/, '');     
	                 }     
	             });     
	         };     
	   
	 });  
  
  function enterCommonSearch(){
	 var event=arguments.callee.caller.arguments[0]||window.event;
     if (event.keyCode == 13){    
    	 commonSearch();    
     }
  }
  
  function commonSearch(){
	  var input = $("#common_search_input").val();
	  var data = {input:input};
	  data = JSON.stringify(data);
	  $.ajax( {    
		    url:context+"/web/common_search.action",
		    dataType:'json',
		    data:data,
		    contentType: "application/json",
		    type:'post',   
		    success:function(data) {
		    	
	  		if(data.is_succ){
	  			if('sku' in data){
	  				location.href=context+"/web/update_sku_page.action?sku_id="+data.sku.id;
	  			}
	  		}else{
	  			 
	  		}
		     },    
		     error : function(request, status, e) {    
		         alert(JSON.stringify(request));
		         alert(status);
		         alert(e);
		     }    
		}); 
  }
  function _checkFormElement(ele,text){
		if(jQuery.trim(ele.val())==''){
			alert(text);
			return false;
		}
		return true;
	}
  