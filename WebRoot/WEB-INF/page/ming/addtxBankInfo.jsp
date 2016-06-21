<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>添加银行卡</title>
    <jsp:include page="html5meta.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
	<link id="skin" rel="stylesheet" href="css/common.css" />
  <style>
  .bank {
    position: absolute;top: -35px;left: 5px;background: #FFCC99;border: 1px solid #FF9966; border-radius: 4px;text-align: center;padding: 5px;color: red;font-weight: bold;font-size: 15px;
  }
  </style>
</head>
<body>	
	<jsp:include page="header.jsp"></jsp:include>
  		<div>
  		<table class="table-condensed  mt10 mb10" width="100%">
		  <tr>
		    <td width="30%" align="right">开户名称：<br /></td>
		    <td width="70%"><span class="txt" id="cardUserName1">
		    <s:if test="%{#request.realName==''}">
		    	<s:property value="#request.realName" default="---" ></s:property>
		    </s:if><s:else>
		    	<shove:sub value="#request.realName" size="1" suffix="***"/>*
		    </s:else>
		    </span></td>
		  </tr>
		  <tr>
		    <td align="right">开户银行名称：<br /></td>
		    <td>
		    <s:select id="bankName1" name="bankName"
                            cssStyle="inp188"
                            list="#request.bankList" listKey="selectName"
                            listValue="selectName" headerKey="" headerValue="--请选择--"/><span class="red">*</span><small>请添加相同开户名称的银行卡</small></td>
		  </tr>
		  <tr id="jg">
                    <td align="right">开户行所在地：
                    </td>
                    <td>
                        <s:select id="province" name="workPro"
                            cssStyle="width:107px;"
                            list="#request.provinceList" listKey="regionId"
                            listValue="regionName" headerKey="" headerValue="--请选择--"
                            onchange="javascript:if($('#province').val()!=''){$('#u_Pro_City').html('')}" />
                        <s:select id="city" name="cityId"
                            cssStyle="width:107px;"
                            list="#request.cityList" listKey="regionId"
                            listValue="regionName" headerKey="" headerValue="--请选择--"
                            onchange="javascript:if($('#city').val()!=''){$('#u_Pro_City').html('')}" />
                        <span class="red">*</span>
                        <span style="color: red; float: none;" id="u_Pro_City"
                            class="formtips"></span>
                    </td>
                </tr>
           <tr>
            <td align="right">支行名称：<br /></td>
            <td><input type="text" class="inp188" id="subBankName1"  maxlength="19"  onkeyup="this.value=this.value.replace(/[\d]/g,'')" onafterpaste="this.value=this.value.replace(/[\d]/g,'')" /><span class="red">*</span></td>
          </tr>
		  <tr>
		    <td align="right">银行卡号：<br /></td>
		    <td style="position: relative;"><div class="bank"  style="display: none;"  id="join"></div><input type="text" class="inp188"   onfocus="if(this.value){document.getElementById('join').style.display='block'}"  onblur="document.getElementById('join').style.display='none'"   id="bankCard1"   maxlength="24"  onkeyup="this.value=this.value.replace(/[^\d]/g,'');joinSpace(this,1); " onafterpaste="this.value=this.value.replace(/[^\d]/g,'') " /><span class="red">*请输入以6开头[6236、6216除外]的银行卡</span></td>
		  </tr>
		  <tr>
            <td align="right">确认银行卡号：<br /></td>
            <td style="position: relative;"><div class="bank"  style="display: none;"  id="join1"></div><input type="text" class="inp188"  onfocus="if(this.value){document.getElementById('join1').style.display='block'}"  onblur="document.getElementById('join1').style.display='none'"  id="comfirbankCard1"  maxlength="24"  onkeyup="this.value=this.value.replace(/[^\d]/g,'');joinSpace(this);" onafterpaste="this.value=this.value.replace(/[^\d]/g,'') " /><span class="red">*</span></td>
          </tr>
		  <tr>
		    <td align="right">三好资本交易密码：<br /></td>
		    <td><input type="password" class="inp188"  id="dealpwd"/>
		      <span class="txt"><i class="red">*非银行卡交易密码,<a href="javascript:window.parent.reloadsearchDealPwd()" >忘记交易密码?</a></i></span><br /></td>
		  </tr>
		 </table>
		 <div class="control-group text-center">
		    <span style="color: red; float: none;" id="bk1_tip" class="formtips"></span>
            <span id="bankInfo" ></span><br />
            <input type="button" class="btn btn-info pr50 pl50  mr100" id="addbank" value="添加"/>
            <input type="button"  class="btn btn-info pr50 pl50   w100" id="addbankquxiao" value="返回"/>
      </div>
		  
		  </div>
		  <div  class="ml100 red">
      <div>温馨提示*</div>
      <ol>
        <li>1、如果您填写的开户行支行不正确，可能将无法成功提现，由此产生的提现费用将不予返还。 </li>
        <li>2、如果您不确定开户行支行名称，可打电话到所在地银行的营业网点询问或上网查询。 </li>
        <li>3、不支持提现至信用卡账户。</li>
      </ol>
    </div>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
function citySelectInit(parentId, regionType){
    var _array = [];
    _array.push("<option value=''>--请选择--</option>");
    var param = {};
    param["regionType"] = regionType;
    param["parentId"] = parentId;
    $.post("ajaxqueryRegion.mht",param,function(data){
        for(var i = 0; i < data.length; i ++){
            _array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
        }
        $("#city").html(_array.join(""));
    });
}
function registedPlaceCity(parentId, regionType){
    var _array = [];
    _array.push("<option value=''>--请选择--</option>");
    var param = {};
    param["regionType"] = regionType;
    param["parentId"] = parentId;
    $.post("ajaxqueryRegion.mht",param,function(data){
        for(var i = 0; i < data.length; i ++){
            _array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
        }
        $("#registedPlaceCity").html(_array.join(""));
    });
}
$("#province").change(function(){
    var provinceId = $("#province").val();
    citySelectInit(provinceId, 2);
});
     $("#registedPlacePro").change(function(){
    var provinceId = $("#registedPlacePro").val();
    registedPlaceCity(provinceId, 2);
});

$("#addbank").click(function(){
    addBankInfo();
});
$("#addbankquxiao").click(function(){
	window.location="mybank.mht";
});
//添加提现银行信息
function addBankInfo(){
		if($("#bankName1").val()=="" || $("#subBankName1").val()=="" ||$("#bankCard1").val()=="" || $("#dealpwd").val()=="" || isBlank($("#province").val()) || isBlank($("#city").val())){
		   $("#bk1_tip").html("请完整填写信息,带*号的为必填选项");
		   return;
		 }else if(isNaN($("#bankCard1").val())){
		   $("#bk1_tip").html("请输入正确的银行卡号");
		   return;
		 }else if($("#comfirbankCard1").val()!=$("#bankCard1").val()){
			 $("#bk1_tip").html("两次输入的银行卡号不一致");
	           return;
		 }else if($("#bankCard1") && $("#bankCard1").val().substring(0,1)!='6'){
			 $("#bk1_tip").html("请输入以6开头的银行卡");
			 return;
		 }else if($("#bankCard1") && $("#bankCard1").val().substring(0,4)=='6236'){
			 $("#bk1_tip").html("不能输入以6236开头的银行卡");
			 return;
		 }else if($("#bankCard1") && $("#bankCard1").val().substring(0,4)=='6216'){
			 $("#bk1_tip").html("不能输入以6216开头的银行卡");
			 return;
		 }
		 param["paramMap.bankName"] = $("#bankName1").val();
		 param["paramMap.subBankName"] = $("#subBankName1").val();
		 param["paramMap.bankCard"] = $("#bankCard1").val();
		 param["paramMap.cardUserName"] = $("#cardUserName1").text();
		 param["paramMap.dealpwd"] = $("#dealpwd").val();
		 param["paramMap.province"] = $("#province").val();
		 param["paramMap.city"] = $("#city").val();
 		if(!window.confirm("确定要添加银行卡绑定吗?")){
			return;
		}
 		
 		$.dispatchPost("addBankInfo.mht",param,function(data){
	       if(data == 1){
	       	$("#bk1_tip").html("请输入有效的银行卡信息");
	       	$("#bankCard1").attr("value","");//银行卡信息错误，只清空银行卡信息
	       	return;
	       }else if(data == 2){
	          alert("你已经添加了九张银行卡信息，未绑定的银行卡信息可以删除\n否则需要申请更换银行");
	          window.parent.location.href="mybank.mht";
	       }else if(data == 3){
	          alert("请正确填写参数");
	       }else if(data == 4){
	    	   $("#bk1_tip").html("请输入以6开头的银行卡");
           }else if(data == 5){
	          alert("交易密码错误");
	          $("#addbank").attr("disabled",false);
	       }else if(data == 6){
	          alert("该银行卡账号已被绑定");
	          //parent.location.href="mybank.mht";
	       }else{
	    	  alert("银行卡已提交审核");
	    	  window.parent.location.href="mybank.mht";
		   }
		});
}

function joinSpace(obj,t){
	var v = obj.value.toString();
	if(v){
		var html = "";
		for(var i = 1;i<=v.length;i++){
			if(i%4==0){
				html+=v[i-1];
				html+="  "
			}else{
				html+=v[i-1];
			}
		}
		if(t){
	        if($("#join")){
	            $("#join").attr("style","display:block");
	            $("#join").html(html);
	        }
	    }else{
	        if($("#join1")){
	            $("#join1").attr("style","display:block;top:40px;");
	            $("#join1").html(html);
	        }
	    }
	}else{
		if(t){
	        if($("#join")){
	        	$("#join").attr("style","display:none")
	            $("#join").html("");
	        }
	    }else{
	        if($("#join1")){
	        	$("#join1").attr("style","display:none")
	            $("#join1").html("");
	        }
	    }
	}
}

</script>
</body>
</html>
