<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/taglib.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<jsp:include page="/include/head.jsp"></jsp:include><!-- 引用公共head -->
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <link rel="stylesheet" type="text/css" href="css/common.css" />
    <link rel="stylesheet" type="text/css" href="css/index.css" />
        <link rel="stylesheet" type="text/css" href="css/user.css" />    
    <style type="text/css">
      ul,li{margin:0;padding:0}
      #scrollDiv{height:182px;overflow:hidden}
    </style>
    
  </head>
  
  <body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="ny_content">
<s:hidden name="paramMap.param" id="param"  />
<div class="reg_matter clearfix">
  <div class="step_one clearfix">填写账号信息</div>
<form action="register.mht" method="post">
  <div class="fl reg_con_info">
  <div class="reg_tab clearfix">
        <ul>
          <li id="one1" onclick="setComTab('${source}')">个人用户注册</li>
          <li id="one2"  class="hover">企业用户注册</li>
          <li style="border-left:1px solid #ddd;padding:0;"></li>
        </ul>
   </div>
   <div class="reg_con_info clearfix mt30" id="con_one_1"></div>
  <div class="reg_con_info clearfix mt30" id="con_one_2" >
        <div class="cell clearfix">
                <div class="bt01"><span class="red">*</span>企业全称：</div>
                
                <div class="info">
                    <s:hidden id="rgeType" value="1"  /> 
                    <input type="text" class="text"  name="paramMap.orgName" id="orgName"/>
                    <p class="error_tip"><span style="color: red" id="s_orgName" class="success_tip" ></span></p>
                </div>
             </div>
  </div>
 
   <div class="reg_con_info">
            <div class="cell clearfix">
                <div class="bt01"><span class="red">*</span>手机号码：</div>
                <div class="info">
                    <input type="text" class="text" name="paramMap.cellphone" id="cellphone" />
                    <p class="error_tip"><span style="color: red" id="s_cellphone" class="success_tip"> </span></p>
                </div>
             </div>
             <div class="cell clearfix">
                 <div class="bt01"><span class="red">*</span>手机验证码：</div>
                 <div class="info">
                    <input style="width: 110px;" type="text" class="text1" id="cellcode"  name="paramMap.cellcode" />&nbsp;&nbsp;
                    <!--  <input type="button" value="获取验证码" class="reg_goon" style="display:none;"/>
                    <span class="reg_wait">60秒发送中...</span>-->
                   <input type="button" id="codeImg"  class="blue_btn"  value="获取手机验证码"  style="cursor: pointer;width: 110px;"  height="25" onclick="checkCode()" />
                    <span id="codeSpan" style="color: blue;"></span>
                    <p class="error_tip"><span id="s_cellcode" style="color: blue;"></span></p>
                    <p  style="color: #666;margin-top: 15px;" >收不到短信验证码? 点击这里<a onclick="checkCode('vioce')"  style="cursor: pointer;" class="orange" >获取语音验证码</a></p>  
                    <p id="vioce" style="display: none;"><font color="red">获取语音验证码需要呼叫您的手机，请保持手机畅通，</font>注意接听 400-6000583 的来电。</p>
                 </div>
             </div>
             <div class="cell clearfix">
                 <div class="bt01"><span class="red">*</span>用户名：</div>
                 <div class="info">
                    <input type="text" class="text" name="paramMap.userName"  id="userName"/>
                    <p class="error_tip"><span  style="color: red" id="s_userName" class="formtips"></span></p>
                 </div>
             </div>
             <div class="cell clearfix">
                 <div class="bt01"><span class="red">*</span>登录密码：</div>
                 <div class="info">
                 <input  type="password" class="text" name="paramMap.password" id="password"/>
                     <p class="error_tip"><span  style="color: red" id="s_password" class="formtips"></span></p>
                 </div>
             </div>
             <div class="cell clearfix">
                 <div class="bt01"><span class="red">*</span>确认密码：</div>
                 <div class="info">
                 <input type="password" class="text" name="paramMap.confirmPassword" id="confirmPassword"/>
                     <p class="error_tip"><span  style="color: red" id="s_confirmPassword" class="formtips"></span></p>
                 </div>
             </div>
             <div class="cell clearfix">
                 <div class="bt01"><span class="red"></span>E-mail：</div>
                 <div class="info">
                 	<input type="text" class="inp202"  name="paramMap.email" id="email"/>
                     <p class="error_tip"><span style="color: red" id="s_email" class="formtips"></span></p>
                 </div>
             </div>
             <div class="cell clearfix">
                 <div class="bt01"><span class="red"></span>推荐人：</div>
                 <div class="info">
	                <s:if test="paramMap.refferee==''">
					<input type="text" class="text" name="paramMap.refferee" id="refferee" value="${paramMap.refferee }"/>
					</s:if>
					<s:else>
						<input type="text" class="text" name="paramMap.refferee" id="refferee" value="${paramMap.refferee }"  />
					</s:else>
                    <p class="error_tip"><span id="s_refferee11" class="fred"></span></p>
                 </div>
                 <input type="hidden" id="source" value="${source }" />
             </div>             
             <div class="cell clearfix">
                  <div class="bt01">&nbsp;</div>
                  <div class="info f14">
                  
                    <p class="pt10">
	                    <input type="checkbox" id="agre2" checked="checked" />&nbsp; 我已经阅读并同意
	                    <a style="cursor: pointer;" class="reg_blue" onclick="fff()">使用条款</a>和
	                    <a style="cursor: pointer;" class="reg_blue" onclick="ffftip()">隐私条款</a>
                    </p>
                    <p class="pt20"><input type="button"  id="btn_register" value="下一步" class="reg_btn" style="cursor: pointer;"/></p>
                    
                  </div>
             </div>
          </div>
  </div>
  </form>
   <div class="fr reg_right_pic"></div>  
 </div>
 </div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>



<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript" src="script/fancybox.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript" src="script/zhucheJS.js"></script><!-- 引用验证js -llz -->



<!-- 
<script type="text/javascript" src="script/jquery-2.0.js"></script>
<script type="text/javascript" src="script/xl.js"></script>

<script type="text/javascript" src="script/jquery-2.0.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>
 -->


  </body>
</html>
