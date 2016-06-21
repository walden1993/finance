<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<c:set value="用户注册" scope="request" var="title"/>
	<jsp:include page="/include/head.jsp"></jsp:include><!-- 引用公共head -->
	<link rel="stylesheet" type="text/css" href="css/register.css">
	<link rel="stylesheet" href="framework/nice-validator-0.7.3/jquery.validator.css">
  </head>
  
  <body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	



<div class="wrap"  style="margin:50px 0;background:#fff">
	<div class="w950" style="max-height:500px;">
    	<div ><img src="images/log_pic.png"/></div>
        <div class="reg">
        	<form  method="post" autocomplete="off" data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
            	<div class="username mb15">
                	<input  type="text" name="paramMap.userName" id="userName" data-ok="" tabindex="1" data-rule="用户名:required;username;length[1~20];remote[ajaxNewCheckRegister.mht];" placeholder="请输入您的用户名" />
                </div>
                <div class="u-info mb10 ml10 right-msg">
                	<span class="ico"></span><span class="msg">邮箱或手机号不能为空</span>
                </div>
                <div class="password mb15">
                	<input  type="password" name="paramMap.password" data-ok=""  id="password" tabindex="2" data-rule="密码:required;password" placeholder="请输入您的登录密码" />
                </div>
                <div class="p-info mb10 ml10 right-msg">
                	<span class="ico"></span><span class="msg">请输入您的登录密码</span>
                </div>
                <div class="password mb15">
                	<input  type="password" tabindex="3" name="paramMap.confirmPassword" data-ok="" id="confirmPassword" data-rule="确认密码:required;password;match(paramMap.password);" placeholder="请再次输入登录密码" />
                </div>
                <div class="cf-info mb10 ml10 right-msg">
                	<span class="ico"></span><span class="msg">确认密码不能为空</span>
                </div>
                <%-- <div class="email mb15 ">
                	<input  type="email" tabindex="4" data-rule="email;remote[ajaxNewCheckRegister.mht];" data-ok="" name="paramMap.email" id="email" placeholder="请输入您的邮箱(选填项)" />
                </div>
                <div class="e-info mb10 ml10 right-msg">
                	<span class="ico"></span><span class="msg">邮箱不正确</span>
                </div> --%>
                <div class="mobilephone mb15">
                	<input  type="text" tabindex="5" data-rule="手机号:required;mobile;remote[ajaxNewCheckRegister.mht];" data-ok=""  name="paramMap.cellphone" id="cellphone" placeholder="请输入您的手机号" />
                </div>
                <div class="m-info mb10 ml10 right-msg">
                	<span class="ico"></span><span class="msg">手机号不能为空</span>
                </div>
                                
                <div class="code mb15">
                	<input type="text" tabindex="6" max="4" min="4" data-rule="验证码:required;remote[ajaxNewCheckRegister.mht];" data-ok=""  name="paramMap.cellcode" id="cellcode" placeholder="请输入验证码" maxlength=4 />
                    <button class="blue_btn h38 w130" type="button" onclick="checkCode()"  id="codeImg" data-rule="验证码:required;remote[ajaxNewCheckRegister.mht];"  >发送验证码</button><span class="msg-box n-right" style="position: relative;" for="cellcode"></span>
               	</div>
                <div class="vioce-info mb10 ml10">
                	<span class="msg">收不到短信验证码? 点击<a onclick="checkCode('vioce')" style="cursor: pointer;" class="blue">获取语音验证码</a></span>
                </div>
                
                <div class="reffere mb15">
                	<input type="text" tabindex="7" value="${paramMap.refferee}" data-rule="remote[queryValidRecommer.mht];" name="paramMap.refferee"  data-ok="" id="refferee" placeholder="请输入推荐人用户名(选填项)" />
                </div>
               
 				<div class="protol-info ml10">
                	<span class="msg">
	                    <input type="checkbox" id="agre2" style="vertical-align: text-bottom;" checked="checked">&nbsp; 我已经阅读并同意
	                    <a style="cursor: pointer;" href="querytips.mht" id="querytip1"  class="blue fancybox.ajax">使用条款</a>和
	                    <a style="cursor: pointer;" href="querytip.mht" id="querytip2" class="blue fancybox.ajax">隐私条款</a>
                    </span>
                </div>
                <div class="submit mt10">
                	<button class="login_button w250"  type="submit" tabindex="8">注册</button>
               	 </div>
                 
                 <div class="text-center mt10">
                 	<span>已有三好资本账号？<a href="login.mht" class="blue">立即登录</a></span>
                 </div>
            </form>
        </div>
        <div class="clearfix"></div>
    </div>
    <div class="clearfix"></div>
</div>

<div class="clearfix"></div>

<div class="wrap">
	<div class="w950">
    	<table class="table table-bordered text-center" width="100%">
        	<tr>
            	<td></td>
                <td class="bg">三好资本项目</td>
                <td>银行活期存款</td>
                <td>银行一年期定存</td>
                <td>余额宝</td>
                <td>信托产品</td>
            </tr>
        	<tr>
            	<td>年化收益</td>
            	<td class="bg">10%-15%</td>
                <td>0.35%</td>
                <td>3%-3.5%</td>
                <td>&lt;5%</td>
                <td>9%-10%</td>
            </tr>
        	<tr>
            	<td>年限</td>
                <td class="bg">1-6个月</td>
                <td>不限制</td>
                <td>12个月</td>
                <td>不限制</td>
                <td>12-24个月</td>
            </tr>
        	<tr>
            	<td>安全性</td>
                <td class="bg">全额本息保障</td>
                <td>高</td>
                <td>高</td>
                <td>高</td>
                <td>高</td>
            </tr>   
        	<tr>
            	<td>投资门槛</td>
                <td class="bg">100元起投</td>
                <td>不限制</td>
                <td>不限制</td>
                <td>不限制</td>
                <td>100万起投</td>
            </tr>                                                                                 
        </table>
    </div>
</div>


<%-- <div class="ny_content">
<s:hidden name="paramMap.param" id="param"  />
<div class="reg_matter clearfix">
 <div class="step_one clearfix">填写账号信息</div>
<form action="register.mht" method="post">
  <div class="fl reg_con_info">
  <div class="reg_tab clearfix">
        <ul>
          <li id="one1"  class="hover">个人用户注册</li><!--  onclick="setTab('one',1,2,'g')"  -->
          <li id="one2"   onclick="setOrgTab('${source }')">企业用户注册</li><!-- -->
          <li style="border-left:1px solid #ddd;padding:0;"></li>
        </ul>
   </div>
   <div class="reg_con_info clearfix mt30" id="con_one_1"></div>
  <div class="reg_con_info clearfix mt30" id="con_one_2" style="display:none;">
        <div class="cell clearfix">
                <div class="bt01"><span class="red">*</span>企业全称：</div>
                
                <div class="info">
                    <s:hidden id="rgeType" value="1"  /> 
                   <input type="text" class="text"  name="paramMap.orgName" id="orgName"/>
                    <p class="error_tip"><span style="color: red" id="s_orgName" class="success_tip" ></span></p>
                </div>
             </div>
  </div>
 <s:hidden id="rgeType" value="2"  /> 
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
                    <input style="width: 110px;" maxlength="4" type="text" class="text1" id="cellcode"  name="paramMap.cellcode" />&nbsp;&nbsp;
                    <!--  <input type="button" value="获取验证码" class="reg_goon" style="display:none;"/>
                    <span class="reg_wait">60秒发送中...</span>-->
                    <input type="button" id="codeImg"  class="blue_btn"  value="获取手机验证码"  style="cursor: pointer;width: 110px;"  height="25" onclick="checkCode()" />
                    <span id="codeSpan" style="color: blue;"></span>
                    <p class="error_tip" ><span id="s_cellcode" style="color: blue;"></span></p>
                    <p style="color: #666;margin-top: 15px;">收不到短信验证码? 点击这里<a onclick="checkCode('vioce')"  style="cursor: pointer;" class="orange" >获取语音验证码</a></p>  
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
 </div> --%>
<!-- 引用底部公共部分 -->    

 <div class="modal fade bs-example-modal-lg onemodal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
    </div>
  </div>
</div>
<div class="modal fade bs-example-modal-lg twomodal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
    </div>
  </div>
</div>
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/zhucheJS.js"></script><%-- 引用验证js -llz --%>
<script type="text/javascript" src="framework/nice-validator-0.7.3/jquery.validator.js"></script>
<script type="text/javascript" src="framework/nice-validator-0.7.3/local/zh_CN.js"></script>
<script type="text/javascript">
$('form').on('valid.form', function(e, form){
    register();
});
var options ={
	fitToView	: false,
	width		: '90%',
	height		: '90%',
	autoSize	: false,
	closeClick	: false,
	openEffect	: 'none',
	closeEffect	: 'none'
};
$("#querytip1").fancybox(options);
$("#querytip2").fancybox(options);

</script>
</body>
</html>
