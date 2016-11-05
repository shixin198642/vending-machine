<%@ page pageEncoding="UTF-8"%>
<!doctype html>
<%@page import="com.mjitech.constant.*"%>
<html lang="zh-cn">
 <head>
  <meta charset="UTF-8">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <script src="<%=request.getContextPath() %>/vendor/jquery.js"></script>
  <script src="<%=request.getContextPath()%>/vendor/jquery.ajaxfileupload.js"></script>
  <script>
  var formatJson = function(json, options) {
	    var reg = null,
	        formatted = '',
	        pad = 0,
	        PADDING = '    '; // one can also use '\t' or a different number of spaces
	  
	    // optional settings
	    options = options || {};
	    // remove newline where '{' or '[' follows ':'
	    options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
	    // use a space after a colon
	    options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
	  
	    // begin formatting...
	    if (typeof json !== 'string') {
	        // make sure we start with the JSON as a string
	        json = JSON.stringify(json);
	    } else {
	        // is already a string, so parse and re-stringify in order to remove extra whitespace
	        json = JSON.parse(json);
	        json = JSON.stringify(json);
	    }
	  
	    // add newline before and after curly braces
	    reg = /([\{\}])/g;
	    json = json.replace(reg, '\r\n$1\r\n');
	  
	    // add newline before and after square brackets
	    reg = /([\[\]])/g;
	    json = json.replace(reg, '\r\n$1\r\n');
	  
	    // add newline after comma
	    reg = /(\,)/g;
	    json = json.replace(reg, '$1\r\n');
	  
	    // remove multiple newlines
	    reg = /(\r\n\r\n)/g;
	    json = json.replace(reg, '\r\n');
	  
	    // remove newlines before commas
	    reg = /\r\n\,/g;
	    json = json.replace(reg, ',');
	  
	    // optional formatting...
	    if (!options.newlineAfterColonIfBeforeBraceOrBracket) {        
	        reg = /\:\r\n\{/g;
	        json = json.replace(reg, ':{');
	        reg = /\:\r\n\[/g;
	        json = json.replace(reg, ':[');
	    }
	    if (options.spaceAfterColon) {         
	        reg = /\:/g;
	        json = json.replace(reg, ':');
	    }
	  
	    $.each(json.split('\r\n'), function(index, node) {
	        var i = 0,
	            indent = 0,
	            padding = '';
	  
	        if (node.match(/\{$/) || node.match(/\[$/)) {
	            indent = 1;
	        } else if (node.match(/\}/) || node.match(/\]/)) {
	            if (pad !== 0) {
	                pad -= 1;
	            }
	        } else {
	            indent = 0;
	        }
	  
	        for (i = 0; i < pad; i++) {
	            padding += PADDING;
	        }
	  
	        formatted += padding + node + '\r\n';
	        pad += indent;
	    });
	  
	    return formatted;
	};

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
  function showHidePre(formname){
	  var resultpre = $("#"+formname+"_pre");
	  if(resultpre.is(":hidden")){
		  resultpre.show();  
	}else{
		resultpre.hide(); 
	}
  }
  
  function send(formname){
	  var theform = $("#"+formname);
	  var formurl = theform.attr('action');
	  var resultdiv = $("#"+formname+"_ret");
	  var rawdata = theform.serializeObject();
	  
	  if(formname=='test_buyer_submit_carts'){
		  var newdata = {};
		  var storeIds = [];
		  for(var i=0;i<rawdata['storeId'].length;i++){
			  
		  	  if(rawdata['storeId'][i]){

		  			storeIds.push(rawdata['storeId'][i]);		 
		  	  }
			   
		  }
		  if(storeIds.length==0){
			  return false;
		  }
		  newdata.storeIds=storeIds;
		  rawdata=newdata;
		  console.log(rawdata);
	  }
	  if(formname=='add_order'){
		  var newdata = {};
		  var cart = [];
		  for(var i=0;i<rawdata['sku_id'].length;i++){
			  
		  	  if(rawdata['sku_id'][i]){
		  		  var cartobj = {};
				  cartobj.sku_id=rawdata['sku_id'][i];
				  cartobj.count=rawdata['count'][i];
				  cart.push(cartobj);		 
		  	  }
			   
		  }
		  if(cart.length==0){
			  return false;
		  }
		  newdata.cart=cart;
		  newdata.open_id=rawdata['open_id'];
		  rawdata=newdata;
	  }
	  var data = JSON.stringify(rawdata);
	  $.ajax( {    
		    url:formurl,
		    dataType:'json',
		    data:data,
		    contentType: "application/json",
		    type:'post',   
		    success:function(data) {
	    		//var html = JSON.stringify(data);
	    		var precontent = formatJson(data);
	    		var html = "<input type='button' value='show/hide' onclick='showHidePre(\""+formname+"\")'/>";
	    		html+="<pre id='"+formname+"_pre'>"+precontent+"</pre>";
	    		
		        resultdiv.html(html);
		    	
		    	
		    	
		     },    
		     error : function(request, status, e) {    
		         alert(JSON.stringify(request));
		          alert(status);
		          alert(e);
		     }    
		});  
  }
  
  $(document).ready(
	function(){
  	}
  );
  </script>
  <title>Document</title>
 </head>
 <body>
 <h1>test login with openid</h1>
 url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_TEST_OPENID%> <br/>
  <form id="login_with_openid" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_TEST_OPENID%>">
  open_id:<input type="text" name="open_id"/><br/>
  return:<div id="login_with_openid_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('login_with_openid')"/>
  </form>
  <br/>
  <h1>test get sku detail</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_GET_SKU_DETAIL%> <br/>
  <form id="get_sku_detail" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_GET_SKU_DETAIL%>">
  sku_number:<input type="text" name="sku_number"/><br/>
  open_id:<input type="text" name="open_id"/><br/>
  return:<div id="get_sku_detail_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('get_sku_detail')"/>
  </form>
 <br/>
  <h1>test add order</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_ADD_ORDER%> <br/>
  <form id="add_order" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_ADD_ORDER%>">
  open_id:<input type="text" name="open_id"/><br/>
  sku_id:<input type="text" name="sku_id"/> count:<input type="text" name="count"/><br/>
  sku_id:<input type="text" name="sku_id"/> count:<input type="text" name="count"/><br/>
  sku_id:<input type="text" name="sku_id"/> count:<input type="text" name="count"/><br/>
  sku_id:<input type="text" name="sku_id"/> count:<input type="text" name="count"/><br/>
  return:<div id="add_order_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('add_order')"/>
  </form>
  <br/>
  <h1>test get order detail</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_ORDER_DETAIL%> <br/>
  <form id="get_order_detail" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_ORDER_DETAIL%>">
  order_number:<input type="text" name="order_number"/><br/>
  open_id:<input type="text" name="open_id"/><br/>
  return:<div id="get_order_detail_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('get_order_detail')"/>
  </form>
 <br/>
 <h1>test get pay url</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_REQUEST_PAY%> <br/>
  <form id="request_pay_url" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_REQUEST_PAY%>">
  order_number:<input type="text" name="order_number"/><br/>
  open_id:<input type="text" name="open_id"/><br/>
  return:<div id="request_pay_url_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('request_pay_url')"/>
  </form>
 <br/>
 <h1>test get order status</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_ORDER_STATUS%> <br/>
  <form id="get_order_status" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_ORDER_STATUS%>">
  order_number:<input type="text" name="order_number"/><br/>
  open_id:<input type="text" name="open_id"/><br/>
  return:<div id="get_order_status_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('get_order_status')"/>
  </form>
 <br/>
 <h1>test get order list</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_ORDER_LIST%> <br/>
  <form id="get_order_list" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.SELLER_ORDER_LIST%>">
  open_id:<input type="text" name="open_id"/><br/>
  return:<div id="get_order_list_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('get_order_list')"/>
  </form>
  
  <br/>
  
   <h1>test buyer get weixin config params</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_GET_JSAPI_PARAMS%> <br/>
  <form id="test_buyer_get_weixin_config" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_GET_JSAPI_PARAMS%>">
  url:<input type="text" name="url" value=""/><br/>
  return:<div id="test_buyer_get_weixin_config_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_get_weixin_config')"/>
  </form>
 <br/>
  
 <h1>test get buyer main page data</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_MAINPAGE_DATA%> <br/>
  <form id="get_buyer_mainpage_data" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_MAINPAGE_DATA%>">
  storeId:<input type="text" name="storeId" value="0"/><br/>
  return:<div id="get_buyer_mainpage_data_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('get_buyer_mainpage_data')"/>
  </form>
 <br/>
 
  <h1>test get buyer main page data by geo</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_MAINPAGE_DATA_BY_GEO%> <br/>
  <form id="get_buyer_mainpage_data_by_geo" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_MAINPAGE_DATA_BY_GEO%>">
  longitude:<input type="text" name="longitude" value="0"/><br/>
  latitude:<input type="text" name="latitude" value="0"/><br/>
  return:<div id="get_buyer_mainpage_data_by_geo_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('get_buyer_mainpage_data_by_geo')"/>
  </form>
 <br/>
 
 <h1>test buyer get all stores data</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_GET_ALL_STORES%> <br/>
  <form id="get_buyer_allstores_data" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_GET_ALL_STORES%>">
  return:<div id="get_buyer_allstores_data_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('get_buyer_allstores_data')"/>
  </form>
 <br/>
 
 <h1>test buyer login with openid</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_TEST_LOING_WITH_OPENID%> <br/>
  <form id="test_buyer_login_with_openid" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_TEST_LOING_WITH_OPENID%>">
  openid:<input type="text" name="openid" value="123456"/><br/>
  return:<div id="test_buyer_login_with_openid_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_login_with_openid')"/>
  </form>
 <br/>
 
 <h1>test buyer add sku to cart</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_ADD_SKU_TO_CART%> <br/>
  <form id="test_buyer_add_sku_to_cart" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_ADD_SKU_TO_CART%>">
  storeId:<input type="text" name="storeId" value=""/><br/>
  skuId:<input type="text" name="skuId" value=""/><br/>
  count:<input type="text" name="count" value=""/><br/>
  return:<div id="test_buyer_add_sku_to_cart_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_add_sku_to_cart')"/>
  </form>
 <br/>
 
 <h1>test buyer remove sku from cart</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_REMOVE_SKU_FROM_CART%> <br/>
  <form id="test_buyer_remove_sku_from_cart" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_REMOVE_SKU_FROM_CART%>">
  storeId:<input type="text" name="storeId" value=""/><br/>
  skuId:<input type="text" name="skuId" value=""/><br/>
  count:<input type="text" name="count" value=""/><br/>
  return:<div id="test_buyer_remove_sku_from_cart_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_remove_sku_from_cart')"/>
  </form>
 <br/>
 
  <h1>test buyer get cart</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_GET_CART%> <br/>
  <form id="test_buyer_get_cart" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_GET_CART%>">
  return:<div id="test_buyer_get_cart_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_get_cart')"/>
  </form>
 <br/>
 
 <h1>test buyer clear cart</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_CLEAR_CART%> <br/>
  <form id="test_buyer_clear_cart" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_CLEAR_CART%>">
  storeId:<input type="text" name="storeId" value=""/><br/>
  return:<div id="test_buyer_clear_cart_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_clear_cart')"/>
  </form>
 <br/>
 
 <h1>test buyer submit cart</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_SUBMIT_CART%> <br/>
  <form id="test_buyer_submit_cart" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_SUBMIT_CART%>">
  storeId:<input type="text" name="storeId" value=""/><br/>
  return:<div id="test_buyer_submit_cart_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_submit_cart')"/>
  </form>
 <br/>
 
 <h1>test buyer submit carts</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_SUBMIT_CARTS%> <br/>
  <form id="test_buyer_submit_carts" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_SUBMIT_CARTS%>">
  storeId:<input type="text" name="storeId" value=""/><br/>
  storeId:<input type="text" name="storeId" value=""/><br/>
  storeId:<input type="text" name="storeId" value=""/><br/>
  storeId:<input type="text" name="storeId" value=""/><br/>
  return:<div id="test_buyer_submit_carts_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_submit_carts')"/>
  </form>
 <br/>
 
  <h1>test buyer get pay params</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_REQUEST_PAY%> <br/>
  <form id="test_buyer_get_pay_params" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_REQUEST_PAY%>">
  order_number:<input type="text" name="order_number" value=""/><br/>
  return:<div id="test_buyer_get_pay_params_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_get_pay_params')"/>
  </form>
 <br/>
 
 <h1>test buyer get order list</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_ORDER_LIST%> <br/>
  <form id="test_buyer_get_order_list" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_ORDER_LIST%>">
  storeId:<input type="text" name="storeId" value="0"/><br/>
  return:<div id="test_buyer_get_order_list_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_get_order_list')"/>
  </form>
 <br/>
 
  <h1>test buyer get order detail</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_ORDER_DETAIL%> <br/>
  <form id="test_buyer_get_order_detail" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_ORDER_DETAIL%>">
  order_number:<input type="text" name="order_number" value=""/><br/>
  return:<div id="test_buyer_get_order_detail_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_get_order_detail')"/>
  </form>
 <br/>
 
 <h1>test buyer get sku detail</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_SKU_DETAIL%> <br/>
  <form id="test_buyer_get_sku_detail" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_SKU_DETAIL%>">
  sku_number:<input type="text" name="sku_number" value=""/><br/>
  storeId:<input type="text" name="storeId" value=""/><br/>
  return:<div id="test_buyer_get_sku_detail_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_get_sku_detail')"/>
  </form>
 <br/>
 
  <h1>test buyer get brand detail</h1>
  url:http://www.mjitech.com<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_BRAND_DETAIL%> <br/>
  <form id="test_buyer_get_brand_detail" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.BUYER_BRAND_DETAIL%>">
  brandId:<input type="text" name="brandId" value=""/><br/>
  return:<div id="test_buyer_get_brand_detail_ret"></div>
  <input type="button" name="ok" value="ok" onclick="send('test_buyer_get_brand_detail')"/>
  </form>
 <br/>
 
 </body>
</html>
