<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本</title>
<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />

</head>

<body>

<div class="nymain" style="width: 600px;">
  <div class="wdzh" style="width: 600px;">
    <div class="r_main" style="width: 600px;">
<div class="box" style="width: 600px;" >
        <div class="boxmain2" style="padding-top:10px; width: 600px;" >
         <div class="biaoge2" style="margin-top:0px; width: 600px;" >
    <table width="600px;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="2" align="left" style="padding-top:0px;"> 变更银行卡信息</th>
    </tr>
    <tr>
    <td width="18%" align="right">开户账户：</td>
    <td width="83%">${realName }<span class="txt"></span></td>
  </tr>
    <tr>
    <td width="18%" align="right">原银行卡号：</td>
    <td width="83%">${bankCard }<span class="txt"></span></td>
  </tr>
  
  <tr>
    <td align="right">新的开户行：</td>
    <td><input type="text" class="inp188" id="bankName_" />
      <span class="txt">输入您的开户银行名称</span></td>
  </tr>
  <tr>
    <td align="right" >新的支行：</td>
    <td><input type="text" class="inp188" id="subBankName_" />
      <span class="txt">输入您的开户支行</span></td>
  </tr>
  <tr>
    <td align="right">新的卡号：</td>
    <td><input type="text" class="inp188" id="bankCard_" />
      <span class="txt">输入您的卡号</span></td>
  </tr>
   <tr>
    <td align="right">交易密码：</td>
    <td><input type="password" class="inp188" id="dealpwd_" />
      <span class="txt">输入您的交易密码</span></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top:10px;"><a href="javascript:changeBankInfo();" class="bcbtn">变更</a></td>
  </tr>
   <tr>
  		<td colspan="2"><span style="color: red; float: none;" id="bk1_tip_" class="formtips"></span></td>
  </tr>
    </table>

    </div>
    </div>
</div>
    </div>
  </div>
</div>

<script type="text/javascript" src="script/jquery-2.0.js"></script>
<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script> 
<script>

   $(function(){
   
      $("#bankName_").focus();
      //提现银行卡设置1
	       $("#bankName_").blur(function(){
			     if($("#bankName_").val()==""){
			       $("#bk1_tip_").html("开户银行名称不能为空");
			     }else{
			       $("#bk1_tip_").html("");
			     }
		    });
		    
		    $("#subBankName_").blur(function(){
			     if($("#subBankName_").val()==""){
			       $("#bk1_tip_").html("开户支行不能为空");
			     }else{
			       $("#bk1_tip_").html("");
			     }
		    });

		    $("#dealpwd_").blur(function(){
			     if($("#dealpwd_").val()==""){
			       $("#bk1_tip_").html("交易密码不能为空");
			     }else{
			       $("#bk1_tip_").html("");
			     }
		    });
		    
		    $("#bankCard_").blur(function(){
			     if($("#bankCard_").val()==""){
			       $("#bk1_tip_").html("卡号不能为空");
			     }else if(isNaN($("#bankCard_").val())){
			        $("#bk1_tip_").html("请输入正确的银行卡号");
			     }else{
			       $("#bk1_tip_").html("");
			     }
		    });
      
   });
   
   function changeBankInfo(){
     if($("#bankName_").val()=="" ||$("#subBankName_").val()=="" || $("#bankCard_").val()=="" || $("#dealpwd_").val()==""){
        $("#bk1_tip_").html("请填写完整信息");
        return;
     }
     
     if($("#bk1_tip_").text()!=""){
       return;
     }
     
     if(!window.confirm("确定要变更吗?")){
	 	return;
	 }
     param["paramMap.bankId"] = ${bankId};
     param["paramMap.mBankName"] = $("#bankName_").val();
     param["paramMap.mSubBankName"] = $("#subBankName_").val();
     param["paramMap.mBankCard"] = $("#bankCard_").val();
     param["paramMap.dealpwd"] = $("#dealpwd_").val();
     
     $.dispatchPost("updateBankInfo.mht",param,function(data){
        if(data == 1){
          window.parent.tipjBox("交易密码错误，变更失败，请重新变更！")          
          return;
        }
        alert("变更申请成功");
        window.parent.history.go(0);
        window.parent.window.jBox.close() ;
        
     });
     
   }
</script>
</body>
</html>
