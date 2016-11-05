<%--
/**************************************************
 *
 * NAME: jsp
 *
 * PURPOSE: 
 *
 * AUTHOR: teng
 *
 * HISTORY：
 * 2016/06/11 MAXBOX/ 创建文件
 *
 *************************************************/
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow-x:hidden;">
<%@ page import="com.mjitech.constant.CommonConstants"%>
<%@ page import="com.mjitech.constant.RequestConstants"%>
<%@ page import="com.mjitech.constant.WebPageConstants"%>
<head>
<%
	response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Expires","0");
%> 
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
	        json = json.replace(reg, ': ');
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
	  var data = JSON.stringify(theform.serializeObject());
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
  <a href="<%=request.getContextPath()%>">go back to www</a>
  
  <h1>test login</h1>
  <form id="login" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.LOGIN%>">
  username:<input type="text" name="username"/><br/>
  password:<input type="text" name="password"/><br/>
  remember login: yes <input type="radio" name="<%=RequestConstants.PARAMETER_NAME_REMEBER_LOGIN %>" value="yes" checked="checked" /> no <input type="radio" name="<%=RequestConstants.PARAMETER_NAME_REMEBER_LOGIN %>" value="no"/>
  <br/>
  return:<div id="login_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('login')"/>
  </form>
  <h1>test is login</h1>
  <form id="is_login" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.CHECK_IS_LOGIN%>"><br/>
  return:<div id="is_login_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('is_login')"/>
  </form>
  
  <h1>test logout</h1>
  <form id="logout" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.LOGOUT%>">
  return:<div id="logout_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('logout')"/>
  </form>

  <h1>test warehouse add</h1>
  <form id="addwarehouse" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.WAREHOUSE_ADD%>">
    name:<input type="text" name="name" value="test name" /><br/>
    type: <select name="type"><option>warehouse</option><option selected="selected">store</option><option>machine</option></select>
    province:<input type="text" name="province"  value="100" /><br/>
    region: <input type="text" name="region"  value="222" /><br/>
    city:<input type="text" name="city" value="123" /><br/>
    address:<input type="text" name="address" value="address" /><br/>
    return:<div id="addwarehouse_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('addwarehouse')"/>
  </form>
  
  <h1>test get warehouse by id</h1>
  <form id="getwarehouse" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.WAREHOUSE_GET%>">
	 warehouse id:<input type="text" name="id" value="1"/><br/>
     return:<div id="getwarehouse_ret"></div>
     <input type="button" name="ok" value="ok" onclick="send('getwarehouse')"/>
  </form>  
  
  <h1>test delete warehouse by id</h1>
  <form id="deletewarehouse" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.WAREHOUSE_DELETE%>">
	 warehouse id:<input type="text" name="id" value="1"/><br/>
     return:<div id="deletewarehouse_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('deletewarehouse')"/>
  </form>
  
 <h1>test warehouse update</h1>
  <form id="updatewarehouse" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.WAREHOUSE_UPDATE%>">
	 id:<input type="text" name="id" value="1"/><br/>
     name:<input type="text" name="name" value="new name" /><br/>
     type: <select name="type"><option>warehouse</option><option selected="selected">store</option><option>machine</option></select>
     province:<input type="text" name="province"  value="100" /><br/>
     region: <input type="text" name="region"  value="222" /><br/>
     city:<input type="text" name="city" value="123" /><br/>
     address:<input type="text" name="address" value="address" /><br/>
     return:<div id="updatewarehouse_ret"></div>
     <input type="button" name="ok" value="ok" onclick="send('updatewarehouse')"/>
  </form>  
  
  <h1>test supplier add</h1>
  <form id="addsupplier" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ADD_SUPPLIER%>">
	 same:<input type="text" name="sname" value="北京元和恒泰贸易有限公司"/><br/>
	 address:<input type="text" name="address" value="北京市朝阳区广渠门外大街22号"/><br/>
	 shipaddress:<input type="text" name="shipaddress" value="北京市朝阳区广渠门外大街22号"/><br/>
	 account:<input type="text" name="account" value="1234567890"/><br/>
	 bank:<input type="text" name="bank" value="北京银行长安支行"/><br/>
	 remarks:<textarea name="remarks">发货特别特别特别慢</textarea><br/>
     return:<div id="addsupplier_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('addsupplier')"/>
  </form>
  
  <h1>test find supplier detail by id</h1>
  <form id="findsupplier" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.FIND_SUPPLIER%>">
	 supplier id:<input type="text" name="id" value="1"/><br/>
     return:<div id="findsupplier_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('findsupplier')"/>
  </form>
  
  <h1>test delete supplier by id</h1>
  <form id="deletesupplier" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.DEL_SUPPLIER%>">
	 supplier id:<input type="text" name="id" value="1"/><br/>
     return:<div id="findsupplier_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('deletesupplier')"/>
  </form>
  
 <h1>test supplier update</h1>
  <form id="altersupplier" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ALTER_SUPPLIER%>">
	 id:<input type="text" name="id" value="2"/><br/>
	 same:<input type="text" name="sname" value="北京元和恒泰贸易有限公司 "/><br/>
	 address:<input type="text" name="address" value="北京市朝阳区广渠门外大街22号-update"/><br/>
	 shipaddress:<input type="text" name="shipaddress" value="北京市朝阳区广渠门外大街22号-update"/><br/>
	 account:<input type="text" name="account" value="1234567890"/><br/>
	 bank:<input type="text" name="bank" value="北京银行长安支行"/><br/>
	 remarks:<textarea name="remarks">发货特别特别特别慢-update</textarea><br/>
     return:<div id="altersupplier_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('altersupplier')"/>
  </form>
  
  <h1>test suppliercontact add</h1>
  <form id="addsuppliercontact" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ADD_SUPPLIERCONTACT%>">
   supplier id:<input type="text" name="supplierid" value="2"/><br/>
   contact name:<input type="text" name="cname" value="张三"/><br/>
   tel:<input type="text" name="tel" value="12345"/><br/>
   fax:<input type="text" name="fax" value="54321"/><br/>
   mail:<input type="text" name="email" value="hello@123.com"/><br/>
   main contact<select name="type">
      <option value="1">是</option>
      <option value="2" selected>否</option>
      </select><br/>
	 remarks:<textarea name="remarks">物流进度也联系他</textarea><br/>
     return:<div id="addsuppliercontact_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('addsuppliercontact')"/>
  </form>
  
  <h1>test find suppliercontact detail by id</h1>
  <form id="findsuppliercontact" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.FIND_SUPPLIERCONTACT%>">
	 suppliercontact id:<input type="text" name="id" value="1"/><br/>
     return:<div id="findsuppliercontact_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('findsuppliercontact')"/>
  </form>
  
  <h1>test delete suppliercontact by id</h1>
  <form id="deletesuppliercontact" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.DEL_SUPPLIERCONTACT%>">
	 supplier id:<input type="text" name="id" value="1"/><br/>
     return:<div id="deletesuppliercontact_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('deletesuppliercontact')"/>
  </form>
  
 <h1>test suppliercontact update</h1>
 <form id="altersuppliercontact" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ALTER_SUPPLIERCONTACT%>">
   supplier id:<input type="text" name="id" value="2"/><br/>
   contact name:<input type="text" name="cname" value="张三"/><br/>
   tel:<input type="text" name="tel" value="12345"/><br/>
   fax:<input type="text" name="fax" value="54321"/><br/>
   mail:<input type="text" name="email" value="hello@123.com"/><br/>
   main contact<select name="type">
      <option value="1">是</option>
      <option value="2" selected>否</option>
      </select><br/>
	 remarks:<textarea name="remarks">物流进度也联系他</textarea><br/>
     return:<div id="altersuppliercontact_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('altersuppliercontact')"/>
  </form>
  
  <h1>test find supplier-contact-list by supplier-id</h1>
  <form id="findsuppliercontactlist" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.FIND_SUPPLIERCONTACTLIST%>">
	 supplier-id:<input type="text" name="id" value="1"/><br/>
     return:<div id="findsuppliercontactlist_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('findsuppliercontactlist')"/>
  </form>
  
  <h1>test sku-spec add</h1>
  <form id="addskuspec" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.ADD_SKUSPEC%>">
   sku id:<input type="text" name="skuid" value="3"/><br/>
   amount:<input type="text" name="amount" value="12"/><br/>
   unit:<input type="text" name="unit" value="箱"/><br/>
   default spec<select name="type">
      <option value="1">是</option>
      <option value="2" selected>否</option>
      </select><br/>
     return:<div id="addskuspec_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('addskuspec')"/>
  </form>
  
 <h1>test delete skuspec by sku-id</h1>
  <form id="deleteskuspec" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.DEL_SKUSPEC%>">
	 sku id:<input type="text" name="id" value="1"/><br/>
     return:<div id="deleteskuspec_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('deleteskuspec')"/>
  </form>
  
 <h1>test find skuspec-list by sku-id</h1>
  <form id="findskuspeclist" action="<%=request.getContextPath()+WebPageConstants.COMMON_PREFIX+WebPageConstants.FIND_SKUSPECLIST%>">
	 supplier-id:<input type="text" name="id" value="3"/><br/>
     return:<div id="findskuspeclist_ret"></div>
    <input type="button" name="ok" value="ok" onclick="send('findskuspeclist')"/>
  </form>
  
  <br/>
 </body>
</html>
