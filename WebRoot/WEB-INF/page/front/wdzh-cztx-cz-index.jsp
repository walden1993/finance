<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();

%> 
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set value="充值" var="title" scope="request" />
   <jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<!--主题内容-->
<div class="mainwarp">
  <div class="user_location clearfix w950">
  <ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li><a href="home.mht">我的账户</a></li>
	  <li class="active">充值</li>
  </ol>
  <div class="w950 clearfix">
       <!--右侧导航-->
       <%@include file="/include/left.jsp" %>
       <!--左侧内容-->
       <div class="fr w750">
       	<div class="pub_tb bottom clearfix">
       		  <h3>充值</h3>
			  <h3 style="border:none;"><a href="withdrawLoad.mht" style="color:#a6a6a6;">提现</a></h3>
			  <h3 style="border:none;"><a href="mybank.mht" style="color:#a6a6a6;">我的银行卡</a></h3>
			  <h3 style="border:none; "><a href="queryFundrecordInit.mht" style="color:#a6a6a6;">资金记录</a></span></h3>
			  <div class="clearfix"></div>
	      </div>
         <div class="uesr_border personal">
            <div class="clearfix re_prompt">
              <span class="promot_img"></span>
              • <s:if test="#request.min_withdraw==null">凡是在三好资本充值未投标的用户，15天以内提现收取本金<font color="red"> ${costFee}%</font>，15天以后提现免费</s:if>
        <s:else>凡是在三好资本充值未投标的用户，15天以内提现收取本金<font color="red">${costFee}%</font>，最低<font color="red">${min_withdraw}</font>元，15天以后提现免费。</s:else><br/>
              • 三好资本禁止信用卡套现、虚假交易、洗钱等行为,一经发现将禁止该账户使用。<br/>
              • 充值到账时间一般为1秒钟~1分钟，如果长时间未到账，请联系客服：${sitemap.servicePhone}
            </div>
           <div class="uesr_balance clearfix " >
              <div class="fl"><b>充值账户：</b><span class="f14">${email }</span></div>                                   
              <div class="fl ml100"><b>账户可用余额：</b><span class="red f14">${usableSum}元</span></div>
              <div class="fr"><a href="queryFundrecordInit.mht" class="blue"><i class="balance_img"></i>查看资金记录</a></div>
           </div>
           
           <s:if test="#request.paySystemUpgrade==1">
           <div class="clearfix uesr_balance red">
           <img src="images/jg.PNG" width="50" height="44" class="fl mr10"/>${upgradeNotes } </div>
           </s:if>
           
           
          <form id="form1" name="form1" method="post" action="">
           <div class="clearfix pre-paid">
              <ul class="paid_money clearfix">
                <li class="clearfix">
                  <div class="list_on">充值金额：</div>
                  <div class="input-group list_th">
					  <input type="text" class="form-control"  id="money"   style="ime-mode:disabled;" ondragenter="return false" onpaste="return!clipboardData.getData('text').match(/[\u4e00-\u9fa5]/gi)" onkeypress="if(event.keyCode&lt;48||event.keyCode&gt;57||event.keyCode==8)event.returnValue=false;"    aria-label="Amount (to the nearest dollar)">
					  <span class="input-group-addon">元</span>
					</div>
					<span class="red">(*只能充值整数)</span>
                    <p class="red fl" id="money_tip"></p>
                    <a href="frontNewsDetails.mht?id=6" class="blue fr"  target="_blank"><i class="card_list_img"></i>查看网银限额</a>       
                </li>                
                <li class="clearfix" <s:if test="#request.lastBank==null || #request.lastBank==''">style="display:none"</s:if>  >
                  <div class="list_on">您上次的支付方式：</div>
                  <div class="list_tw">
                    <div>
                        <input name="bankType" type="radio" checked="checked"  value="${lastBank}" class="mt8 mr10"/>
                        <div class="card_bd"><span class="${lastBank} card"></span></div>
                    </div>
                    <input type="button" value="立即充值"   style="margin-top: 3px; "     <s:if test="#request.paySystemUpgrade==1"> class="graybtn" id="addrechargeF"</s:if><s:else> class="btn" id="addrecharge2" </s:else> /><p class="red mt10"></p>
                  </div>
                </li>
              </ul>
              
              <div class="clear"></div>
              <div class="card_list clearfix">
	              <s:if test="#session.user.userType==1">
	              <div class="clearfix"><div class="fl"><h2>网银支付</h2> <span class="fl ml5 pt20">- 需开通网银</span></div></div>
	              </s:if> 
	              <s:else>
		              <ul class="tabbtn" >
	                    <li id="one1" onclick="setTab('one',1,2);setCompany(1);"  class="hover">个人网银支付</li>
	                    <li id="one2" onclick="setTab('one',2,2);setCompany(2);">企业网银支付</li>
	                </ul>      
	              </s:else>
              
                 <div  id="con_one_1" class="clearfix mt20" >
                 <ul class="bankcard">
                                      <li >
                        <input name="bankType" type="radio" value="BOC" class="mt8 mr10"/>
                        <div class="card_bd"><span class="BOC card" title="中国银行">中国银行</span></div>
                   </li>
                   <li>
                        <input name="bankType" type="radio" value="ICBC" class="mt8 mr10"/>
                        <div class="card_bd"><span class="ICBC card" title="中国工商银行">中国工商银行</span></div>
                   </li>
                   <li>
                        <input name="bankType" type="radio" value="CCB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CCB card" title="中国建设商银行">中国建设商银行</span></div>
                   </li>
                   <li>
                        <input name="bankType" type="radio" value="ABC" class="mt8 mr10"/>
                        <div class="card_bd"><span class="ABC card" title="中国农业银行">中国农业银行</span></div>
                   </li>
                   <li>
                        <input name="bankType" type="radio" value="CMB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CMB card" title="招商银行">招商银行</span></div>
                   </li>
                   <li>
                        <input name="bankType" type="radio" value="BCM" class="mt8 mr10"/>
                        <div class="card_bd"><span class="BCM card" title="交通银行">交通银行</span></div>
                   </li>
                   <li>
                        <input name="bankType" type="radio" value="PSBC" class="mt8 mr10"/>
                        <div class="card_bd"><span class="PSBC card" title="邮政储蓄银行">邮政储蓄银行</span></div>
                   </li>
                    <li>
                        <input name="bankType" type="radio" value="CEB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CEB card" title="光大银行">光大银行</span></div>
                   </li>
                    <li>
                        <input name="bankType" type="radio" value="CMBC" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CMBC card" title="民生银行">民生银行</span></div>
                   </li>
                    <li>
                        <input name="bankType" type="radio" value="CIB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CIB card" title="兴业银行">兴业银行</span></div>
                   </li>
                   <%--  <li>
                        <input name="bankType" type="radio" value="SDB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="SDB card" title="深圳发展银行">深圳发展银行</span></div>
                   </li> --%>
                    <li>
                        <input name="bankType" type="radio" value="GDB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="GDB card" title="广发银行">广发银行</span></div>
                   </li>
                    <li>
                        <input name="bankType" type="radio" value="SPDB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="SPDB card" title="浦发银行">浦发银行</span></div>
                   </li>
                    <li>
                        <input name="bankType" type="radio" value="SPA" class="mt8 mr10"/>
                        <div class="card_bd"><span class="SPA card" title="平安银行">平安银行</span></div>
                   </li>
                    <li>
                        <input name="bankType" type="radio" value="NBCB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="NBCB card" title="宁波银行">宁波银行</span></div>
                   </li>
                   <li>
                        <input name="bankType" type="radio" value="HZB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="HZCB card" title="杭州银行">杭州银行</span></div>
                   </li>
                    <!-- <li>
                        <input name="bankType" type="radio" value="SHB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="SHB card" title="上海银行">上海银行</span></div>
                   </li>
                    <!-- <li>
                        <input name="bankType" type="radio" value="WZCB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="WZCB card" title="温州银行">温州银行</span></div>
                   </li>
				   <li>
                        <input name="bankType" type="radio" value="HXB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="HXB card" title="华夏银行">华夏银行</span></div>
                   </li>
                    <li>
                        <input name="bankType" type="radio" value="CBHB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CBHB card" title="渤海银行">渤海银行</span></div>
                   </li> -->
                   <li>
                        <input name="bankType" type="radio" value="NJCB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="NJCB card" title="南京银行">南京银行</span></div>
                   </li>
                   <li>
                        <input name="bankType" type="radio" value="SPDB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="PDCB card" title="上海浦东发展银行">上海浦东发展银行</span></div>
                   </li>
                   <li>
                        <input name="bankType" type="radio" value="SRCB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="SRCB card" title="上海农村商业银行">上海农村商业银行</span></div>
                   </li>
                  <li>
                        <input name="bankType" type="radio" value="HKBEA" class="mt8 mr10"/>
                        <div class="card_bd"><span class="HKBEA card" title="东亚银行">东亚银行</span></div>
                   </li>
                   <li>
                        <input name="bankType" type="radio" value="CITIC" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CITIC card" title="中信银行">中信银行</span></div>
                   </li>
				   <li>           
                        <input name="bankType" type="radio" value="BCCB" class="mt8 mr10"/>
                        <div class="card_bd"><span style="background-image: url('images/BCCB.png');" class="card" title="北京银行">北京银行</span></div>
                   </li>
                 </ul>
                 <p class="clear"></p>
                   <p class="flip">选择其他银行↓</p>
              </div>
              
                 <div  id="con_one_2" style="display:none;" class="clearfix mt20">
                 <ul class="bankcard">
                   <li >
                         <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>
                        <input name="bankType" type="radio" value="BOC" class="mt8 mr10"/>
                        <div class="card_bd"><span class="BOC card" title="中国银行">中国银行</span></div>
                   </li>
                   <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>
                        <input name="bankType" type="radio" value="ICBC" class="mt8 mr10"/>
                        <div class="card_bd"><span class="ICBC card" title="中国工商银行">中国工商银行</span></div>
                   </li>
                   <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>
                        <input name="bankType" type="radio" value="CCB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CCB card" title="中国建设商银行">中国建设商银行</span></div>
                   </li>
                   <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                   
                        <input name="bankType" type="radio" value="ABC" class="mt8 mr10"/>
                        <div class="card_bd"><span class="ABC card" title="中国农业银行">中国农业银行</span></div>
                   </li>
                   <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                   
                        <input name="bankType" type="radio" value="CMB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CMB card" title="招商银行">招商银行</span></div>
                   </li>
                   <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                   
                        <input name="bankType" type="radio" value="BCM" class="mt8 mr10"/>
                        <div class="card_bd"><span class="BCM card" title="交通银行">交通银行</span></div>
                   </li>
                   <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                   
                        <input name="bankType" type="radio" value="CITIC" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CITIC card" title="中信银行">中信银行</span></div>
                   </li>
                    <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                    
                        <input name="bankType" type="radio" value="CEB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CEB card" title="光大银行">光大银行</span></div>
                   </li>
                    <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                    
                        <input name="bankType" type="radio" value="CMBC" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CMBC card" title="民生银行">民生银行</span></div>
                   </li>
                    <!-- <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                    
                        <input name="bankType" type="radio" value="CIB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="CIB card" title="兴业银行">兴业银行</span></div>
                   </li> -->
                    <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                    
                        <input name="bankType" type="radio" value="NBCB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="NBCB card" title="宁波银行">宁波银行</span></div>
                   </li>
                    <!-- <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                    
                        <input name="bankType" type="radio" value="GDB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="GDB card" title="广发银行">广发银行</span></div>
                   </li> -->
                    <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                    
                        <input name="bankType" type="radio" value="SPDB" class="mt8 mr10"/>
                        <div class="card_bd"><span class="SPDB card" title="浦发银行">浦发银行</span></div>
                   </li>
                    <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                    
                        <input name="bankType" type="radio" value="SPA" class="mt8 mr10"/>
                        <div class="card_bd"><span class="SPA card" title="交通银行">平安银行</span></div>
                   </li>
                    <li>
                        <div class="qiye_icon"><img src="images/qiye_icon.png" width="32" height="32" /></div>                    
                        <input name="bankType" type="radio" value="BCCB" class="mt8 mr10"/>
                        <div class="card_bd"><span style="background-image: url('images/BCCB.png');" class="card" title="北京银行">北京银行</span></div>
                   </li>
                 </ul>
                 <p class="clear"></p>
                   <p class="flip">选择其他银行↓</p>
              </div>
              </div>
              <input id="xinfu" type="hidden" checked="checked"  name="pay"  sss="ddd"  value="1"  style="float: none;">
              <input id="company" type="hidden"  value="1">
              <div class="lh40 text-center"><input type="button" value="立即充值"    <s:if test='#request.paySystemUpgrade==1'> class="button_over w140  h40" id="addrechargeL"</s:if><s:else> class="button w140 h40"  id="addrecharge"</s:else> /> <p class="red" ></p></div>
           </div> 
          </form>                 
         </div>
        </div>
     </div>
</div>
<!--主题内容-->
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
    function setCompany(v){
    	$("#company").val(v);
    }

	$("#addrecharge2").click(function(){
	    addRechargeInfo();
	    
	 });

    $(".bankcard").find("li").click(function(){
        $(".uesron").removeClass("uesron");
        $(this).addClass("uesron");
        $(this).find("input").attr("checked","true");
    })
    
    $(".list_tw").click(function(){
    	$(this).find("input").attr("checked","true");
    	$(".uesron").removeClass("uesron");
    })
    
   
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

$(document).ready(function(){
	//displayDetail(2,4);
	var one2 = 1;
	var isshow = false;
	$("#con_one_2").find("li:gt(7)").attr("style","display:none"); 
	$("#con_one_2").find(".flip").click(function(){
	    if(isshow){
	        isshow = false;
	    }else{
	        isshow = true;
	    }
	    $("#con_one_2").find("li:gt(7)").slideToggle("slow");
	    if(isshow){
	    	$("#con_one_2").find(".flip").html("选择其他银行↑")
	    }else{
	    	$("#con_one_2").find(".flip").html("选择其他银行↓")
	    }
	  });
	
	var isshow1 = false;
	$("#con_one_1").find("li:gt(7)").slideToggle("slow");
	$("#con_one_1").find(".flip").click(function(){
	    if(isshow1){
	        isshow1 = false;
	    }else{
	        isshow1 = true;
	    }
	    $("#con_one_1").find("li:gt(7)").slideToggle("slow");
	    if(isshow1){
	    	$("#con_one_1 .flip").html("选择其他银行↑")
	    }else{
	    	$("#con_one_1 .flip").html("选择其他银行↓")
	    }
	  }); 
});

  $(function(){
  	/*$("#xinfu").click();
  	$("#div_xinfu").attr("style","display:block;");
         $("#zh_hover").attr('class','nav_first');
	      $('#li_2').addClass('on');
		  $('.tabmain').find('li').click(function(){
		  $('.tabmain').find('li').removeClass('on');
        
       }); 
      */ /**
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
        $("html,body").animate({scrollTop:$(".uesr_balance").offset().top},500);
        return;
      }else if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#money").val())){
	       $("#money_tip").html("请输入正确的充值金额，必须为大于0的数字"); 
	       $("html,body").animate({scrollTop:$(".uesr_balance").offset().top},500);
	       return;
	  }else if($("#money").val() < 3){
	      $("#money_tip").html("最小金额不能低于3元"); 
	      $("html,body").animate({scrollTop:$(".uesr_balance").offset().top},500);
	       return;
	  }else if($("#money").val().indexOf('.')!=-1){
          $("#money_tip").html("请输入正确的充值金额，金额必须为整数"); 
          $("html,body").animate({scrollTop:$(".uesr_balance").offset().top},500);
          return;
      }else{
	     $("#money_tip").html("");
	  }
      
      
       var payType = -1;
      //if($("#gopay").attr("checked")=="checked"){
		    payType= 1;
     // }	else if($("#ipay").attr("checked")=="checked"){
     	// 	payType = 2;
     // }else if ($("#xinfu").attr("checked")=="checked") {
      	//	payType = 4;
     // }else{
      //	  alert("请选择充值类型");
     // 	  return ;
     // }
      
      var bankType = "";
      
   	  bankType=$("input[name='bankType']:checked").val();
    
      
      if(bankType=="" || bankType==undefined){
    	  alert("请选择银行类型");
    	  return;
      }   
	  
	  if(!window.confirm("确定进行帐户充值")){
        return;
      }
      
      var money = $("#money").val();
      var company = $("#company").val();
      var type = "";
      //if($("#ipay").attr("checked")=="checked"){
	      	//window.open("ipayPayment.mht?divType=li_2&money="+money+"&gatewayType="+bankType);
	     // }else if($("#gopay").attr("checked")=="checked"){
	      	//window.open("gopayPayment.mht?divType=li_2&money="+money+"&bankCode="+bankType);
	     // }else if($("#xinfu").attr("checked")=="checked") {
	      	//window.open("xinfupayPayment.mht?divType=li_2&money="+money+"&xinfuCode="+bankType);
	      	
	      	var typeCode = 1;//扩展使用
	      	
	      	var pWin = window.open("xinfupayPayment.mht?divType=li_2&money="+money+"&xinfuCode="+bankType+"&typeCode="+typeCode+"&company="+company);
	      	if(pWin.closed!= null){
	      		$.fancybox.open({
		                href: 'recharge_confirm.mht?bankId='+param["paramMap.bankId"],
		                type: 'ajax',
		                padding: 5,
		                fitToView: true,
		                width: 600,
		                height: 300,
		                autoSize: false,
		                closeClick: false
		            });
	      	}
	      	//recharge_confirm();
	      //}else{
	      //	alert("请选择充值类型");
	      //}
	      //$("#addrecharge").attr("disabled",false);
   }
   
   
   function jumpUrl(obj){
       window.location.href=obj;
    }


   //window.onunload = function(){ window.opener.location.reload(); }

   function callBackRecharge(type){
	   jQuery.jBox.close();
	   if(type!=1){
		   window.location.href="rechargeInit.mht";
	   }else{
		   window.location.href="queryFundrecordInit.mht";
	   }
   }

      
</script>
</body>
</html>
