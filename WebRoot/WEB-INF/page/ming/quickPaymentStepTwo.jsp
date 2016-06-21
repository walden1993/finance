<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();

%> 
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>三好贷-充值</title>
  <jsp:include page="html5meta.jsp"></jsp:include>
   <link rel="stylesheet" type="text/css" href="css/user.css" />
   <link rel="stylesheet" type="text/css" href="css/common.css" />
   <link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
</head>

<body>
<jsp:include page="header.jsp"></jsp:include>
<!--主题内容-->
<div class="mainwarp">
       <!--左侧内容-->
    <div class="uesr_border personal">
          <div class="pub_tb bottom"><i></i>充值</div>
          ${result}
     </div>
</div>
<!--主题内容-->
<%-- 引用底部公共部分
<jsp:include page="footer.jsp"></jsp:include> --%>     
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>

	function quickPay(){
		param["paramMap.realName"] = $("#realName").val();
		param["paramMap.idCard"] = $("#idCard").val();
		param["paramMap.cardNo"] = $("#cardNo").val();
		param["paramMap.cardMobile"] = $("#cardMobile").val();
		param["paramMap.rechargeMoney"] = $("#rechargeMoney").val();
		$.dispatchPost("quickPaymentStepOne.mht",param,function(data){
			alert(data)
		})
	}
	
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
	$("#addMoneyNew").addClass('on');
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
	  }else if($("#money").val() < 1){
	      $("#money_tip").html("最小金额不能低于1元"); 
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
      }else{
	      $("#addrecharge").attr("disabled",true);
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
	      		jQuery.jBox.open("iframe:recharge_confirm.mht", "充值确认", 600,250,{ buttons: { },closed: function () { window.location.href="rechargeInit.mht" }  });
	      	}else{
	      		$("#addrecharge").attr("disabled",false);
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
		   window.location.href="rechargeInit.mht?mobile=f98c59c3-ab9e-43f2-b2d1-2703d1e01850";
	   }else{
		   window.location.href="queryFundrecordInit.mht";
	   }
   }

      
</script>
</body>
</html>
