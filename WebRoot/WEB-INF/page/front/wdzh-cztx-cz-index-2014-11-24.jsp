<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();

%> 
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本</title>
   <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<style>
body { font-size:12px; color:#333}
table { line-height:33px;width:720px;}
table tr{}
table tr td { line-height:33px;}
table tr td input{ height:33px; cursor:pointer; margin:1px; float:left}
table tr td label {line-height:33px; height:33px}
.border_ { border:none;width:720px; overflow:hidden; margin-left:0px; margin-right:0px;}
.border_ table tr td .border_ table tr td strong {
	font-size: 14px;
}
</style>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
  <div class="wdzh">
    <div class="l_nav">
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
    </div>
    <div class="r_main">
    <div class="tabtil">
        <ul>
         <li onclick="jumpUrl('queryFundrecordInit.mht');">资金记录</li>
        <li class="on" onclick="jumpUrl('rechargeInit.mht');">充值</li>
        <li  onclick="jumpUrl('withdrawLoad.mht');">提现</li>
        </ul>
     </div>
      <div class="box" >
      <div class="boxmain2" >
      
      <p class="tips">
        温馨提示:凡是在三好资本充值未投标的用户，15天以内提现收取本金${costFee}%，15天以后提现免费
三好资本禁止信用卡套现、虚假交易等行为,一经发现将予以处罚,包括但不限于：限制收款、冻结账户、永久停止服务,并有可能影响相关信用记录。
        </p>
        <div class="biaoge2">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="11%">真实姓名：</td>
    <td width="89%"><strong>${realName }</strong></td>
  </tr>
  <tr>
    <td>账号：</td>
    <td><strong>${email }</strong></td>
  </tr>
  <tr>
    <td>充值金额：</td>
    <td><input id="money" type="text" class="inp140" />元
    <span style="color: red; float: none;" id="money_tip" class="formtips"></span>&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="http://www.sanhaodai.com/frontNewsDetails.mht?id=6"  target="_blank" style="text-decoration:underline;color:red;" >查看银行限额</a>
    </td>
  </tr>
  <tr>
    <td>充值类型：</td>
   <td style="">
   <s:if test="#request.gopay==2">
   <input id="gopay" type="radio" name="pay" value="2" style="float: none;"  />&nbsp;
   <img src="images/gopay.png" width="121px" height="33px"/>&nbsp;&nbsp;&nbsp;&nbsp; 
   </s:if>
   <s:if test="#request.IPS==2">
   <input id="ipay" type="radio" name="pay"  value="1" style="float: none;" />&nbsp;&nbsp;
   <img src="images/ipay.png" width="121px" height="33px"/>
    </s:if>
   <s:if test="#request.ref==2">
   <input id="xinfu" type="radio" name="pay" sss='ddd' value="1" style="float: none;" />&nbsp; 网上银行
   
   
   <!--
   <img src="images/ipay.png" width="121px" height="33px"/>
    -->
    
    
    
    </s:if>
     <s:if test="#request.xf==2">
   <input id="xinfu" type="radio" name="pay"  value="1" style="float: none;" />&nbsp;新付支付
   <!--
   <img src="images/ipay.png" width="121px" height="33px"/>
    -->
    </s:if>
    
    
     <!--
     <input id="xinfu" type="radio" name="pay"  value="4" style="float: none;" />&nbsp;&nbsp;
  	 新付支付
  	 -->
  	<!--<input id="xinfu" type="radio" name="pay"  value="5" style="float: none;" />&nbsp;
  	 融E付
   -->
   
   
   <input type="radio"  name="pay"  id="xxpay" value="3" style="float: none;"/>&nbsp;线下充值
   </td>
    </tr>
   <tr>
  	<td colspan="2">
  		  <div class="border_" id="div_gopay" style="display:none; ">
  <table border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
          <td colspan="5" ><strong>请选择支付方式：</strong></td>
    </tr>
        <tr valign="middle">
          <td><input  type="radio"   name="bankType" value="ICBC" id="rd_gopay_icbc" />
            <img src="images/banks/bank_01.png" width="128" height="33" /></td>
          <td><input type="radio"  name="bankType"  value="CMB" />
            <img src="images/banks/bank_02.png" width="128" height="33" /></td>
          <td><input type="radio"   name="bankType"  value="CCB" />
            <img src="images/banks/bank_03.png" width="128" height="33" /></td>
          <td><input type="radio"  name="bankType"  value="ABC" />
            <img src="images/banks/bank_04.png" width="128" height="33" /></td>
          <td><input type="radio"  name="bankType" value="BOC" />
            <img src="images/banks/bank_05.png" width="128" height="33" /></td>
        </tr>
        <tr>
          <td><input type="radio"  name="bankType"  value="SPDB" />
            <img src="images/banks/bank_06.png" width="128" height="33" /></td>
          <td><input type="radio"   name="bankType"   value="SDB" />
            <img src="images/banks/bank_07.png" width="128" height="33" /></td>
          <td><input type="radio"   name="bankType"   value="CIB" />
            <img src="images/banks/bank_08.png" width="128" height="33" /></td>
          <td><input type="radio"   name="bankType"   value="BOBJ" />
            <img src="images/banks/bank_09.png" width="128" height="33" /></td>
          <td><input type="radio"   name="bankType"   value="CEB" />
            <img src="images/banks/bank_10.png" width="128" height="33" /></td>
        </tr>
        <tr>
          <td><input type="radio"   name="bankType"   value="BOCOM" />
            <img src="images/banks/bank_11.png" width="128" height="33" /></td>
          <td><input type="radio"   name="bankType"   value="CMBC" />
            <img src="images/banks/bank_12.png" width="128" height="33" /></td>
          <td><input type="radio"   name="bankType"   value="CITIC" />
            <img src="images/banks/bank_13.png" width="128" height="33" /></td>
          <td><input type="radio"   name="bankType"   value="GDB" />
            <img src="images/banks/bank_14.png" width="128" height="33" /></td>
          <td><input type="radio"   name="bankType"   value="PAB" />
            <img src="images/banks/bank_15.png" width="128" height="33" /></td>
        </tr>
        <tr>
          <td><input type="radio"   name="bankType"   value="PSBC" />
            <img src="images/banks/bank_16.png" width="128" height="33" /></td>
         
          <td><input type="radio"   name="bankType"   value="SRCB" />
            <img src="images/banks/bank_18.png" width="128" height="33" /></td>
            <td><input type="radio"   name="bankType"   value="BOS" />
            <img src="images/banks/bank_19.png" width="128" height="33" /></td>
            <td><input type="radio"   name="bankType"   value="BOC" />
            <img src="images/banks/bank_20.png" width="128" height="33" /></td>
           
        <td><input type="radio"   name="bankType"   value="TCCB" />
            <img src="images/banks/bank_30.png" width="128" height="33" /></td>
         
         <td></td>
     
        
        </tr>
     
      
        <tr>
          <td height="10"></td>
          <td height="10"></td>
          <td height="10"></td>
          <td height="10"></td>
          <td height="10"></td>
        </tr>
        <tr>
          <td colspan="2"><input  type="radio"  name="bankType"   value="DEFAULT" />
            <span style="float:left; margin-left:10px">使用国付宝支付<font color="#999999"></font></span></td>
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <!-- 
        <tr>
          <td colspan="2">&nbsp;</td>
          <td><input name="下一步" type="submit" value="下一步" style="border:0px; color:#FFF; font-weight:bold; margin:10px; width:100px; background:#1064AC"/></td>
          <td></td>
          <td></td>
        </tr>
         -->
  </table>
</div>
<div class="border_" id="div_xinfu"  style="display:none; " >
  
    <table border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
          <td colspan="5" ><strong>
          请选择支付方式：
          </strong></td>
    </tr>
    <tr>
          <td>
                <input type="radio"   name="bankType"   value="ICBC" id="rd_xinfu_icbc" />
                <img src="images/bankIco/gongshang.gif" width="128" height="33" />
          </td>
          <td>
                <input type="radio"   name="bankType"   value="ABC" />
                <img src="images/bankIco/nongye.gif" width="128" height="33" />
            </td>
          <td>
                 <input type="radio"   name="bankType"   value="CCB" />
                 <img src="images/bankIco/jianshe.gif" width="128" height="33" />
          </td>
          <td>
                <input type="radio"   name="bankType"   value="BOC" />
                <img src="images/bankIco/zhongguo.gif" width="128" height="33" />
           </td>
          <td>
                <input type="radio"   name="bankType"   value="CMB" />
                 <img src="images/bankIco/zhaohang.gif" width="128" height="33" />
           </td>
        </tr>
        <tr>
            
            <td>
                <input type="radio" name="bankType" value="SPDB" />
                <img src="images/bankIco/shangpufa.gif" width="128" height="33" />
            </td>
            
            <td>
                <input type="radio" name="bankType"  value="GDB"/>
                <img src="images/bankIco/guangfa.gif" width="128" height="33" />
            </td>
            <td>
                <input type="radio" name="bankType" value="BCM" />
                <img src="images/bankIco/jiaotong.gif" width="128" height="33" />
            </td>
            <td>
                <input type="radio" name="bankType" value="PSBC" />
                <img src="images/bankIco/youzheng.gif" width="128" height="33" />
            </td>
            <td>
                <input type="radio" name="bankType" value="SPA" />
                <img src="images/bankIco/pingan.gif"  width="128" height="33"/>
            </td>
            
            <!--
            <td>
                <input type="radio" name="bankType" value="CNCB" />
                <img src="images/banks/bank_13.png" width="128" height="33" />
            </td>
        -->
        </tr>
        <tr>
            
            <td>
                <input type="radio" name="bankType" value="CMBC" />
                <img src="images/bankIco/minsheng.gif" width="128" height="33" />
            </td>
            
            <td>
                <input type="radio" name="bankType"  value="CEB"/>
                <img src="images/bankIco/guangda.gif" width="128" height="33" />
            </td>
            <td>
                <input type="radio" name="bankType" value="SHB" />
                <img src="images/bankIco/shanghaibank.gif" width="128" height="33" />
            </td>
            <td>
                <input type="radio" name="bankType"  value="CITIC"/>
                <img src="images/bankIco/zhongxin.gif" width="128" height="33" />
            </td>
            
            <td>
                <input type="radio" name="bankType" value="HXB" />
                <img src="images/bankIco/huaxia.gif" width="128" height="33" />
            </td>
            
           
            <!--
            <td>
                <input type="radio" name="bankType" value="SHB" />
                <img src="images/banks/bank_19.png" width="128" height="33" />
            </td>
            -->
        </tr>
        <!--<tr>

            

            
            <td>
                <input type="radio" name="bankType" value="PAB" />
                <img src="images/banks/bank_15.png"  width="128" height="33"/>
            </td>
            
            <td>
                <input type="radio" name="bankType" value="BCCB"/>
                <img src="images/bank_bob.gif" width="128" height="33" />
            </td>
            
            <td>
                 <input type="radio" name="bankType" value="BOC" />
                 <img src="images/bank_cnmb.gif" width="128" height="33" />         
            </td>
           
        </tr> -->
        <tr>
            
            <td>
                <input type="radio" name="bankType" value="CBHB" />
                <img src="images/bankIco/buohai.gif" width="128" height="33" />
            </td>
            <td>
                <input type="radio" name="bankType" value="NJCB" />
                <img src="images/bankIco/nanjing.gif" width="128" height="33" />
            </td>
            <td>
                <input type="radio" name="bankType" value="WZCB" />
                <img src="images/bankIco/wenzhou.png" width="128" height="33" />
            </td>
            <td>
                <input type="radio" name="bankType" value="CDCB" />
                <img src="images/bankIco/chengdu.png" width="128" height="33" />
            </td>
            <td>
                <input type="radio" name="bankType" value="NBCB" />
                <img src="images/bankIco/ningbo.gif" width="128" height="33" />
            </td>
        </tr>
        <tr>
            <td>
                <input type="radio" name="bankType" value="SRCB" />
                <img src="images/bankIco/shanghainongcun.png" width="128" height="33" />
            </td>
            <td>
                <input type="radio" name="bankType" value="HKBEA" />
                <img src="images/bankIco/dongya.gif" width="128" height="33" />
            </td>
            <td>
                <input type="radio" name="bankType" value="SDB" />
                <img src="images/bankIco/shenfa.gif" width="128" height="33" />
            </td>
             <td>
                <input type="radio" name="bankType" value="CIB" />
                <img src="images/bankIco/xingye.gif"  width="128" height="33"/>
            </td>
             <td>
                <input type="radio" name="bankType"  value="HZB"/>
                <img src="images/bank_hz.gif" width="128" height="33" />
            </td>
        </tr>
 </table> 
</div>
 <div class="border_" id="div_ipay"  style="display:none; " >
  <table border="0" align="center" cellpadding="0" cellspacing="0" >
    <tr>
          <td colspan="5" ><strong>请选择支付方式：</strong></td>
    </tr>
        <tr valign="middle">
          <td><input  type="radio"  name="bankType" value="01" id="rd_ipay_jieji" />
            人民币借记卡</td>
          <td><input type="radio"  name="bankType"  value="128" />
            信用卡支付</td>
          <td><input type="radio"   name="bankType"  value="04" />
            IPS账户支付</td>
          <td><input type="radio"  name="bankType"  value="16" />
           电话支付</td>
          <td><input type="radio"  name="bankType" value="32" />
            手机支付</td>
        </tr>
        <tr>
          <td colspan="5"><input type="radio"  name="bankType"  value="1024" />
            手机语音支付</td>
         
        </tr>
       
    
       <tr>
          <td height="10"></td>
          <td height="10"></td>
          <td height="10"></td>
          <td height="10"></td>
          <td height="10"></td>
        </tr>
       
        <tr>
         
          <td></td>
          <td></td>
          <td></td>
        </tr>
        <!-- 
        <tr>
          <td colspan="2">&nbsp;</td>
          <td><input name="下一步" type="submit" value="下一步" style="border:0px; color:#FFF; font-weight:bold; margin:10px; width:100px; background:#1064AC"/></td>
          <td></td>
          <td></td>
        </tr>
         -->
	  </table>
	</div>
	  	</td>
	  </tr>
	  <tr>
	  	  <td style="padding-top:20px;" colspan="2"> 
	    <div id="xxdiv"  style="display:none;">
	    	<table  style="line-height:30px;">
	    		<tr align="center" height="30px">
	    			<td>&nbsp;</td>
	    			<td>银行</td>
	    			<td>账号</td>
	    			<td>开户人</td>
	    			<td>开户行</td>
	    		</tr>
	    	<s:iterator value="#request.banksList" var="bank" status="count">
	    		<tr align="center">
	    			<td><input type="radio"  value="${bank.bankname }" name="paramMap.banks" style="float:none;"
	    			<s:if test="#count.count == 1">checked="checked"</s:if> /></td>
	    			<td><image src="${bank.bankimage }" style="width:130px;height:60px;"/></td>
	    			<td>${bank.Account }</td>
	    			<td>${bank.accountname }</td>
	    			<td>${bank.accountbank }</td>
	    		</tr>
	    	</s:iterator>
				         <tr>
					    <td align="right">
					  交易编号：
					    </td>
					    <td align="center">
				 <input type="text" class="inp140" style="margin-top: 2px; width: 180px;float:none;" id="rechargeNumber_text"/></td>
					    <td colspan="3">填写网上交易流水号或ATM机汇款交易号或其他号码，只供查询用 
					    </td>
				  </tr>
				         <tr >
					    <td align="right">
					  线下充值备注：
					    </td>
					    <td align="center">
					    <input type="text" class="inp140" style="margin-top: 2px;width: 180px;float:none;"  id="remark_text"/></td>
					    <td colspan="3">请注明您的用户名，转账银行卡和转账流水号，以及转账时间 
					    </td>
				  </tr>
				  <tr>
				  <td colspan="5" align="center">
				  <a id="saveii" class="bcbtn">提交充值</a>
				  </td>
				  </tr>
	    	</table>
        </div>			
				</td>	
	  </tr>
	</table>

        </div>
        <div class="btn" id="btn_submit">
        
        <input type="button" class="bcbtn" value="保存并继续" id="addrecharge" />
        
        <!--
        <a href="javascript:void(0);" class="bcbtn" onclick="addRechargeInfo();">保存并继续</a>
        --></div>
        
        <p id="p_about" class="tips2" style="border:none;">
        </p>
        <div class="biaoge" style="margin-top:25px;">
           <span id="rechargeInfo"></span>
          </div>
      
      
      </div>
</div>
     
    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>	
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript" src="css/popom.js"></script>
<script>

 	$("#saveii").click(function(){
		 jQuery.jBox.open("post:queryRechartips.mht", "充值委托书", 600,400,{ buttons: { } });
	});

</script>
<script>
		//添加线下充值
		function addRechargeoutline(name){
		   var param ={};
		   param["paramMap.realname"] = name;
		   param["paramMap.bankName"] = $("input[name='paramMap.banks']:checked").val();
		   param["paramMap.money"] = $("#money").val();
		   param["paramMap.rechargeNumber"] = $("#rechargeNumber_text").val();
		   param["paramMap.remark"] = $("#remark_text").val();
		   $.post("addRechargeoutline.mht",param,function(data){
			   if(data.msg=='提交成功'){
				 alert(data.msg);
			     window.location.href="rechargeInit.mht";
			   }else{
			     alert(data.msg);
			   }
		   });
		}
</script>
<script>
  $(function(){
    //displayDetail('0');
	$("#addMoneyNew").addClass('on');
  	$("#xinfu").click();
  	$("#div_xinfu").attr("style","display:block;");
         $("#zh_hover").attr('class','nav_first');
	      $('#li_2').addClass('on');
		  $('.tabmain').find('li').click(function(){
		  $('.tabmain').find('li').removeClass('on');
        
       }); 
       /**
       param["pageBean.pageNum"] = 1;
	    initListInfo(param);
	   */
	    $("#gopay").click(function(){
	    	$("#div_gopay").attr("style","display:block;");
	    	$("#btn_submit").attr("style","display:block;");
	    	 $("#xxdiv").attr("style","display:none;");
			$("#div_ipay").attr("style","display:none;");
			$("#div_xinfu").attr("style","display:none;");
			$("#rd_gopay_icbc").attr("checked","checked");
	    	$("#p_about").html("国付宝简介:国付宝信息科技有限公司（以下简称“国付宝”）是商务部中国国际电子商务中心（以下简称“CIECC”）与海航商业控股有限公司（以下简称“海航商业”）合资成立，"+
	    	"针对政府及企业的需求和电子商务的发展，精心打造的国有背景的，引入社会诚信体系的独立第三方电子支付平台，也是“金关工程”的重要组成部分。"+
			"国付宝信息科技有限公司成立于2011年1月25日，由商务部中国国际电子商务中心与海航商业控股有限公司合作成立，主要经营第三方支付业务。"+
			"公司注册资本14285.72万元，主要经营第三方支付业务，互联网支付及移动电话支付（全国）。");
	    });
	    $("#ipay").click(function(){
	    	$("#div_gopay").attr("style","display:none;");
	   	 	$("#div_ipay").attr("style","display:block;");
	   	 	$("#btn_submit").attr("style","display:block;");
	    	 $("#xxdiv").attr("style","display:none;");
	    	 $("#div_xinfu").attr("style","display:none;");
			$("#rd_ipay_jieji").attr("checked","checked");
	    	$("#p_about").html(" 环迅支付简介:迅付信息科技有限公司（简称：环迅支付），是中国最早成立的第三方支付企业。公司在2011年获颁中国人民银行首批《支付业务许可证》。"+
	    	"公司平台支持所有国内主流银行及国际信用卡，为全球60万家商户及2000万用户提供金融级的支付体验。");
	    });
	    
	   $("#xinfu").click(function(){
	    	$("#div_gopay").attr("style","display:none;");
	    	$("#div_ipay").attr("style","display:none;");
	   	 	$("#div_xinfu").attr("style","display:block;");
	   	 	$("#btn_submit").attr("style","display:block;");
	    	 $("#xxdiv").attr("style","display:none;");
			$("#rd_xinfu_icbc").attr("checked","checked");
	    	//$("#p_about").html(" 新付支付简介:");
	    	$("#p_about").html(" 融E付付简介:");
	    });
	    //线下充值
	    $("#xxpay").click(function(){
	    	$("#rechargeDetail").html("以下是通过线下进行充值。");
			$("#div_gopay").attr("style","display:none;");
	   	 	$("#div_ipay").attr("style","display:none;");
	        $("#btn_submit").attr("style","display:none;");
	        $("#div_xinfu").attr("style","display:none;");
	        $("#xxdiv").attr("style","display:block;");
	        $("#xxdiv").attr("class","biaoge");   
	        $("#p_about").attr("style"," border:none; display:none;");
	        $("#p_about").html(" 线下充值的金额最小10000，奖励千分之三，小于10000没有奖励。");
	    });
	    $("#gopay").click();
	 });
	 
	 function initListInfo(praData){
	 	
		$.dispatchPost("queryRechargeInfo.mht",praData,initCallBack);
	}
	function initCallBack(data){
		$("#rechargeInfo").html(data);
	}
   
   	$("#addrecharge").click(function(){
		   addRechargeInfo();
		   
		});
	
   function addRechargeInfo(){
      if($("#money").val() == ""){
        $("#money_tip").html("请输入充值金额");
        return;
      }else if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#money").val())){
	       $("#money_tip").html("请输入正确的提现金额，必须为大于0的数字"); 
	       return;
	  }else if($("#money").val() < 0.01){
	      $("#money_tip").html("最小金额不能低于0.01"); 
	       return;
	  }else{
	     $("#money_tip").html("");
	  }
      
       var payType = -1;
      if($("#gopay").attr("checked")=="checked"){
		    payType= 1;
      }	else if($("#ipay").attr("checked")=="checked"){
     	 	payType = 2;
      }else if ($("#xinfu").attr("checked")=="checked") {
      		payType = 4;
      }else{
      	  alert("请选择充值类型");
      	  return ;
      }
      var bankType = "";
     
   	  bankType=$("input[name='bankType']:checked").val();
    
      
      if(bankType=="" || bankType==undefined){
    	  alert("请选择银行类型");
    	  return;
      }   
	  
	  if(!window.confirm("确定进行帐户充值")){
        return;
      }else{
	  $("#addrecharge").attr("disabled",true);
	  }
      var money = $("#money").val();
      var type = "";
      if($("#ipay").attr("checked")=="checked"){
	      	window.open("ipayPayment.mht?divType=li_2&money="+money+"&gatewayType="+bankType);
	      }else if($("#gopay").attr("checked")=="checked"){
	      	window.open("gopayPayment.mht?divType=li_2&money="+money+"&bankCode="+bankType);
	      }else if($("#xinfu").attr("checked")=="checked") {
	      	//window.open("xinfupayPayment.mht?divType=li_2&money="+money+"&xinfuCode="+bankType);
	      	
	      	
	      	var pWin = window.open("xinfupayPayment.mht?divType=li_2&money="+money+"&xinfuCode="+bankType);
	      	if(pWin.closed!= null){
	      	    //判断子窗口没被关闭
	      		$("#addrecharge").attr("disabled",true);
	      		jQuery.jBox.open("post:recharge_confirm.mht", "充值确认", 600,250,{ buttons: { },closed: function () { window.location.href="rechargeInit.mht" }  });
	      	}else{

	      		$("#addrecharge").attr("disabled",false);

		    }
	      	
	      	recharge_confirm();
	      }else{
	      	alert("请选择充值类型");
	      }
	      //$("#addrecharge").attr("disabled",false);
   }
   
   function jumpUrl(obj){
       window.location.href=obj;
    }


   //window.onunload = function(){ window.opener.location.reload(); }

   

      
</script>
</body>
</html>
