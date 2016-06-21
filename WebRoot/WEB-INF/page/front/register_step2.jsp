<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="zh-cn">
  <head>
	<jsp:include page="/include/head.jsp"></jsp:include><!-- 引用公共head -->
	<link href="css/register.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">
		window.location.href = 'registerTwo.mht';
	</script>
	
  </head>
  
  <body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="wrap">
<div class="w950 reg_matter clearfix">
  <div class="step_two clearfix"></div>
  <div class="fl reg_con_info">
     <div class="jy_tit"><span class="gray3">交易密码：</span>用于平台账户信息变动或资金变动时，做校验使用，请牢记您的交易密码！</div>
     <div class="jy_notic f16"><span class="red">注意：</span>交易密码不能和帐号登录密码一样。</div>
     <div class="reg_con_info clearfix " id="con_one_1">
        <div>
             <div class="cell clearfix">
                 <div class="bt01"><span class="red">*</span>交易密码：</div>
                 <div class="info">
                    <input name="" id="tradPassWord" type="password" class="text" />
                     <p class="error_tip"><span  style="color: red" id="s_tradPassWord" class="formtips"></span></p>
                 </div>
             </div>
             <div class="cell clearfix">
                 <div class="bt01"><span class="red">*</span>确认交易密码：</div>
                 <div class="info">
                    <input name="" id="reTradPassWord" type="password" class="text" />
                    <p class="error_tip"><span  style="color: red" id="s_reTradPassWord" class="formtips"></span></p>
                 </div>
             </div>             
             <div class="cell clearfix">
                 <div class="bt01">&nbsp;</div>
                  <div class="info f14">
                    <!--  <p class="pt10">
	                    <input type="checkbox" id="agre2" checked="checked" class="mr5" />&nbsp; 我已经阅读并同意
	                    <a style="cursor: pointer;" class="reg_blue ml5 mr5" onclick="fff()">使用条款</a>和
	                    <a style="cursor: pointer;" onclick="ffftip()">隐私条款</a>
                    </p>-->
                    <p class="pt20"><input type="button" onclick="registerTwoBtn()"  class="reg_btn" value="下一步"/></p>
                   
               	</div> 
             </div>
          </div>
  </div>
  </div>
 <div class="fr reg_right_pic"></div> 
 </div>
 </div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" >
//回车登录
document.onkeydown=function(e){
  if(!e)e=window.event;
  if((e.keyCode||e.which)==13){
	  registerTwoBtn();
  }
  
}

//注册第二步
function registerTwoBtn(){
      var falg = true;
      if(falg){
         falg = false;
              
          if($("#tradPassWord").val()==""){
                    $("#s_tradPassWord").html("请输入您的交易密码");
                    falg = true;
                    return false;
          }
          
           if($("#reTradPassWord").val()==""){
                    $("#s_reTradPassWord").html("请输入您的确认交易密码");
                    falg = true;
                    return false;
          }
           
           if($("#tradPassWord").val()!=$("#reTradPassWord").val()){
                    $("#s_reTradPassWord").html("交易密码和确认交易密码不一致");
                    falg = true;
                    return false;
          }
//          if(!$("#agre2").attr("checked")){
//             alert("请勾选我已阅读并同意《使用条款》和《隐私条款》");
//             falg = true;
//            return false;
//          }
           
             var param = {};
                 param["paramMap.tradPassWord"] = $("#tradPassWord").val();
                 param["paramMap.reTradPassWord"] = $("#reTradPassWord").val();
                 
            $.post("registerTwo.mht",param,function(data){
                 if(data.mailAddress=='1'){
                     $("#s_tradPassWord").html("交易密码不能为空");             
                      falg = true;
                   
                   }else if(data.mailAddress=='2'){            
                     $("#s_reTradPassWord").html("确认交易密码不能为空"); 
                     falg = true;
                   
                   }else if(data.mailAddress=='3'){            
                     $("#s_reTradPassWord").html("交易密码和确认交易密码不一致"); 
                     falg = true;
                   
                   }else if(data.mailAddress=='4'){            
                     $("#s_reTradPassWord").html("注册失败"); 
                     falg = true;
                   
                   }else if(data.mailAddress=='5'){            
                     $("#s_reTradPassWord").html("交易密码不能和登录密码一致"); 
                     falg = true;
                   
                   }else if(data.mailAddress=='注册成功'){             
                     
                     falg = true;
                     if(data.isMail==1){
                         window.location.href="registerThirdInit.mht?mail="+data.mail+"&isMail="+data.isMail;
                     }else{
                         window.location.href="registerThirdInit.mht?isMail="+data.isMail;
                     }
                     
                   }
                
            });
              
     }
    
}
</script><!-- 引用验证js -llz -->

  </body>
</html>
