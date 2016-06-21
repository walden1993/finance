<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <style>
    
/*体验标*/
.ty_content{width:940px;margin-bottom:15px;}
.ty_content .l_info{background:#fef2f4;width:158px;display:block; height:100%;position:relative;padding-bottom:150px;}
.ty_content .l_info .nypic{background:url("images/ty_btn.png") no-repeat 0 -141px; width:138px; height:103px; position:absolut; display:block;top:0;left:0; text-indent:-999}
.ty_content .l_info .nypic01{background:url("images/ty_btn.png") no-repeat 0 -246px; width:138px; height:103px; position:absolut; display:block;top:0;left:0; text-indent:-999}
.ty_content .l_info .nypic02{background:url("images/ty_btn.png") no-repeat 0 -351px; width:138px; height:103px; position:absolut; display:block;top:0;left:0; text-indent:-999}
.ty_content .l_info .nyinfo{padding-top:47px;}
.ty_content .l_info h3,.ty_content .l_info p{text-align:center;font-size:15px;color:#666;}
.ty_content .l_info h3{font-size:24px;}
.ty_content .ratelist{padding:30px 30px 15px 45px; height:306px;width:460px; margin:0;border-right:#f7f5f5 solid 1px; position:relative;}
.ty_content .ratelist ul li{line-height:36px;font-size:15px;color:#666;}
.ty_content .ratelist .interest_sj{position: absolute;left:330px;top:150px;}
.ty_content .want_exp{width:306px;}
.ty_content .want_exp .title{background:#fcfcfc;padding:0 15px; line-height:45px;height:45px;border-bottom:1px solid #f7f5f5;}
.ty_content .want_exp .bond{padding:15px;font-size:15px;}
.ty_content .want_exp .bond li{line-height:25px;}
.ty_content .want_exp .bond li .btn{background-position:0 0px;margin-top:15px;background:url("images/ty_btn.png") no-repeat; height:32px; line-height:32px; display:block; text-align:center; cursor:pointer;color:#fff;}
.ty_content .want_exp .bond li .nologinbtn{background-position:0 0px;margin-top:15px;background:url("images/ty_btn.png") no-repeat; background-position:0px -74px; height:32px; line-height:32px; display:block; text-align:center; cursor:pointer;color:#fff;}

.ty_content .want_exp .bond li .btn:hover{ background-position:0 -36px;}
.ty_content .want_exp .bond .ny_table td{border:1px solid #f7f5f5; line-height:22px; font-size:13px;padding:0 10px; text-align: center;}

.plan_tab{height:40px;border-bottom:2px solid #fd5d6a;margin:0 auto;}
.plan_tab ul{margin:0px;padding:0px;}
.plan_tab li{font:16px/39px "microsoft yahei";color:#333;padding:0 40px;height:39px;text-align:center;float:left;cursor:pointer;}
.plan_tab li.hover{border:none;background:#fd5d6a;height:40px; line-height:40px;color:#fff;position:relative;}
.ny_plan_info{padding:20px 30px; line-height:28px;background: #fff;}
.ny_plan_info .pub_tit{font-size:18px;color:#333;margin-top:15px;}
.ny_plan_info .con_info{ text-indent:2em; font-size:14px;color:#666;padding:10px 0;}
.plan_tab_con{width:700px; margin:0 auto;padding-top:10px; line-height:24px;}
.plan_tab_con01{width:850px;margin:0 auto;padding-top:10px;line-height:34px;font-size:14px;}
.plan_tab_con01 table{border:1px solid #f7f5f5; background:#fff; line-height:34px;}
.plan_tab_con01 .leve_titbj{background:#fcfcfc; color:#333;}
.plan_tab_con01 table tr{border-bottom:1px solid #f7f5f5;}

.registstep{ clear:both; border:#ddd 1px solid;background:url(images/htab_03.jpg) repeat-x; margin-top:12px; height:274px}
.registstep a{ display:block; height:45px; width:100%}
.registstep h3{ height:31px; font-size:14px;  font-weight:normal; padding-top:8px; padding-left:10px}
.registstep s{ display:inline-block; background:url(images/sprit.png) no-repeat 0 -322px; height:24px; width:10px; vertical-align: top; margin-right:10px}
.registstep ul{ padding-top:20px}
.registstep li{ width:193px; margin:0 auto}
.registstep .registstep0{ background:url(images/sprit.png) no-repeat 0 -412px; width:193px; height:12px; margin-bottom:8px; margin-top:8px; *margin-top:0;+margin-top:8px; }
.registstep .registstep1{ background:url(images/sprit.png) no-repeat 0 -355px; width:193px; height:48px}
.registstep .registstep2{ background:url(images/sprit.png) no-repeat 0 -429px; width:193px; height:48px}
.registstep .registstep3{ background:url(images/sprit.png) no-repeat 0 -504px; width:193px; height:48px}
.links{ clear:both; width:1000px; height:20px; background:#666666; margin:12px auto 0 auto; background:url(images/parten_13.jpg) repeat-x; height:79px; border:#e4e4e4 1px solid}
.links-title{ padding:0; background: url(images/parten_11.jpg) no-repeat; display:block; width:127px; height:79px; float:left}
.links ul{ float:left; width:870px}
.links li{ float:left; margin:18px 5px 0 15px; +margin:18px 5px 0 13px}
.links li img{ border:#bec0be 1px solid}
.links li img:hover{ border:#fff 1px solid}

.progeos{ float:left;text-align:left;margin-right:5px;margin-top:6px; border:#ddd 1px solid;width:73px;height:10px; padding:1px; zoom:1; font-size:0px;}
.progeos div{ background:url(images/process-move.gif) repeat-x; width:52%; height:10px}

.kinds-list{ clear:both; width:1000px; margin:12px auto}
.tz-step{ clear:both; height:50px}
.kindsnr-list{ clear:both}
.kindsnr-list li{ background:#fcf7ed; padding:5px; margin-top:10px}
.kindsnr-cnet{ border:#f0d5bc 1px solid; background:#fff; padding:20px 40px; line-height:24px; font-size:14px; color:#666}
.kindsnr-cnet h3{ padding:0; font-size:16px}
.kindsnr-cnet .kindsnr-link,.kindsnr-link2,.kindsnr-link3,.kindsnr-link4,.kindsnr-link5{ clear:both; text-align:right; overflow:hidden}
.kindsnr-link a{ display:block; float:right; color:#fff; background:url(images/sprit.png) no-repeat 0 -615px; height:37px; text-align:center; line-height:30px; width:93px}
.kind1{ color:#af4f16; padding-bottom:5px}
.kind2{ color:#8a1f94; padding-bottom:5px}
.kind3{ color:#70a810; padding-bottom:5px}
.kind4{ color:#1777b0; padding-bottom:5px}
.kind5{ color:#d06103; padding-bottom:5px}
.kindsnr-link2 a{ display:block; float:right; color:#fff; background:url(images/sprit.png) no-repeat 0 -657px; height:37px; text-align:center; line-height:30px; width:93px}
.kindsnr-link3 a{ display:block; float:right; color:#fff; background:url(images/sprit.png) no-repeat 0 -698px; text-align:center;height:37px;  line-height:30px; width:93px}
.kindsnr-link4 a{ display:block; float:right; color:#fff; background:url(images/sprit.png) no-repeat 0 -739px; text-align:center;height:37px;  line-height:30px; width:93px}
.kindsnr-link5 a{ display:block; float:right; color:#fff; background:url(images/sprit.png) no-repeat 0 -780px; text-align:center;height:37px;  line-height:30px; width:93px}
.sqxyed{ clear:both; background:#ebebeb; line-height:60px; height:60px; font-size:16px; padding-left:20px; color:#d06103}
.sqxyed div{ float:left}
.sqxyed a{ display:block; float:right; background: url(images/sprit.png) no-repeat 0 -826px; width:186px; height:60px}

.lcmain_lptyl{ float:left; width:670px; border:#ddd 1px solid; padding:15px}
.lcmain_lptyl h2{ font-size:16px; padding:0; color:#b70000}
.lcmain_lptyl p{ padding:0}
.lcmain_lptyl .gginfo li{ clear:both; overflow:hidden; border-bottom:#CCCCCC 1px solid; padding-bottom:10px; margin-bottom:10px}
.lcmain_lptyl .gginfo li .pic{ float:left}
.lcmain_lptyl .gginfo li .info{ float: right; width:509px; line-height:24px}
.lcmain_lptyl h3{ padding:0; color:#b70000; font-size:14px}
    
    </style>
    <script type="text/javascript">var menuIndex = 1;</script>
    
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="wrap">
<div class="reg_img_bj"></div>

<div class="ty_content w950 clearfix">
	<ol class="breadcrumb mb0 bg-none">
			  <li><a href="/">首页 </a></li>
			  <li class="active">涨薪宝</li>
		   </ol>
  <div class="clearfix bg-white">
  <div class="l_info fl">
  	   <s:if test="%{#request.lastestExperienceMap.status == 0}">
       <!--状态一--><div class="nypic"></div>
       </s:if>
       <s:elseif test="%{#request.lastestExperienceMap.status == 1}">   
       <!--状态二--><div class="nypic01"></div>
       </s:elseif>
       <s:else>
       <!--状态三--><div class="nypic02"></div>
       </s:else>
       <div class="nyinfo"><h3>投资体验</h3><p>${lastestExperienceMap.borrowTitle }</p></div>
  </div>
  <div class="fl ratelist">
    <ul>
    <s:hidden value="%{#request.lastestExperienceMap.id}" id="tiyanId"></s:hidden>
      <li>预期年化收益率：<span class="f30 orange">${lastestExperienceMap.annualRate}</span> <span class="f20 orange">%</span></li>
      <li class="mb10">计划体验总金额：<span class="f20 orange">￥<s:if test="%{#request.lastestExperienceMap.borrowAmount<10000}">${lastestExperienceMap.borrowAmount}元</s:if><s:else>${lastestExperienceMap.borrowAmountsecend}万元</s:else></span></li>
      
      <s:if test="%{#request.lastestExperienceMap.status==0 }">
      <li>剩余时间：<span id="remainTimeOne"></span></li>
      </s:if>
      
      <li>投资期限：${lastestExperienceMap.deadline}个月</li>
      <li>收益返还：<s:if test="%{#request.lastestExperienceMap.paymentMode == 1}">按月还息</s:if>
      			    <s:if test="%{#request.lastestExperienceMap.paymentMode == 2}">按天还息</s:if>
      </li>
      <li>投资方式：<span class="orange">体验劵</span></li>
      <li>投资体验说明：<a href="investment_experience_instruction.mht" class=" fancybox.ajax single_image"  style="color:#2183C0;outline: ;cursor: pointer;">查看</a></li>
    </ul>
    <div class="interest_sj">
       <p class="tc"><span class="orange f30"><fmt:formatNumber pattern="#,#00.0#">${(100*lastestExperienceMap.annualRate)/12}</fmt:formatNumber></span> <span class="orange f20">元/月</span><br/>每10000元收益</p>
    </div>
  </div>
  <div class="want_exp fl">
     <div class="title clearfix"><span class="fl f20">我要体验</span><span class="fr hidden"><a href="callcenter.mht" class="orange">获取体验劵</a></span></div>
    <ul class="bond">
    	<s:if test="#request.lastestExperienceMap.hasInvestAmount>0">
        <li>剩余体验金额：
        <span class="f20 orange">￥<s:if test="%{#request.lastestExperienceMap.SurplusAmount<10000 }">${lastestExperienceMap.SurplusAmount}元</s:if><s:else>${lastestExperienceMap.SurplusAmountsecend}万元</s:else></span>
        </li>
        </s:if>
        <s:else>
        <li>剩余体验金额：<span class="f20 orange">￥<s:if test="%{#request.lastestExperienceMap.borrowAmount<10000}">${lastestExperienceMap.borrowAmount}元</s:if><s:else>${lastestExperienceMap.borrowAmountsecend}万元</s:else></span></li>
        </s:else>
        <li>最低体验金额：<span class="orange">${lastestExperienceMap.minTenderedSum}</span>元</li>
        <li>最高体验金额：<s:if test="%{#request.lastestExperienceMap.maxTenderedSum==-1.00}">不限额</s:if><s:else><span class="orange">${lastestExperienceMap.maxTenderedSum}</span>元</s:else></li>
        <!--未登录状态-->
        <s:if test="#session.user ==null">
        <li>您的可用体验券：<a href="javascript:void(0);" onclick="denglukejian()" class="blue">登录</a>可见</li>
        </s:if>
        <s:else>
        <!--登录状态-->
        <s:if test="%{#request.isInvest!=1}">
        
        <li>
          <p>我的可用体验券：<span style="color:red;font-size:12px">(*只显示该标的可用体验券)</span></p>
          
          <s:if test="%{#request.experienceList ==null || #request.experienceList.size()==0}">
                <p class="orange mt30 mb30 tc">无可用的体验券！ <a href='experienceTicket.mht' style='font-weight:bold;text-decoration: underline;'>马上激活</a></p>
          </s:if>
          <s:else>
         
           <table width="100%" border="0" cellspacing="0" class="ny_table">
              <tr>
                <td >选择</td>
                <td >金额</td>
                <td >过期时间</td>
              </tr>
              
              <%--<s:select list="experienceList"  listKey="ticketNo" listValue="amount"   cssClass="sel_70"  theme="" size="%{#request.experienceList.size()}"></s:select>
              --%>
             <s:iterator var="bean"  value="#request.experienceList"  status="s"  >
               <tr   onclick="ck(this)">
                <td>
                <input type="checkbox"  name="ticketNo"   value="${bean.ticketNo }"  <s:if test="#s.index==0">checked="checked"</s:if>  />
              </td>
                <td>
                ${bean.amount }
              </td>
              <td>
             <fmt:formatDate pattern="yyyy-MM-dd"   value="${bean.disableDate}" />
              </td>
              </tr>
             </s:iterator>
              
              <%--<tr>
                <td colspan="2"><s:select  cssStyle="width:100%;border:0px"  len="%{#request.experienceList.size}"  headerKey=""  headerValue="------请选择体验券------" name="myexperience"  id="ticketNo"  list="#request.experienceList"   listKey="ticketNo" listValue="str"   size="3"></s:select>
              </td>
              </tr>
              
              <s:iterator value="#request.experienceList"   id="experienceList" >
              <tr>
                <td><s:property value="#experienceList.amount" /></td>
                <td><s:property value="#experienceList.disableDate" /></td>
              </tr>
              </s:iterator>
            --%></table>
            
            </s:else>
        </li>
        <s:if test="%{#request.lastestExperienceMap.status==0  }">
         <s:if test="%{#request.experienceList !=null && #request.experienceList.size()!=0}">
               <div class="cell clearfix" style="margin: 10px 0px">
                 
                 <div class="info">
                    <span class="red">*</span>验证码：<input name="paramMap.code"  maxlength="3" style="border:1px solid #D9D9D9;width: 40px;"  id="code"/>&nbsp;<span class="ml5"><img src="imageCodeAlgorithm.mht"  title="点击更换验证码"
                                            style="cursor: pointer;" id="codeNum" width="85" height="20"
                    onclick="javascript:switchCode()" /></span>&nbsp;<span><a href="javascript:void()" onclick="switchCode()" style="color: blue;">换一换?</a></span>
                </div>
             </div>
             <li><span style="color: red;" id="s_code" class="formtips">*请输入验证码中问号的结果</span></li>
               <li><a href="#" class="<s:if test='#session.user!=null'>btn</s:if><s:else>nologinbtn</s:else>"    onclick="experience();"  >立即体验</a></li>
          </s:if>
        </s:if>
        </s:if>
        <s:else>
            <br/><br/><br/><span style="color:red">你已体验过此标了，不能重复体验。</span>
        </s:else>
        </s:else>
         
        
     </ul>
  </div> 
</div>

</div>
<!--标的详情-->
<div class="w950 clearfix pt20">
   <div class="plan_tab clearfix">
        <ul>
          <li id="one1" onclick="setTab('one',1,2)"  class="hover">投资体验详情</li>
          <li id="one2" onclick="setTab('one',2,2)" style="display: none;" >参与记录</li>
          <li style="border-left:1px solid #ededed;padding:0;"></li>
        </ul>
   </div>
   <div class="ny_plan_info clearfix " id="con_one_1">
     <div class="pub_tit"><h3>投资体验介绍</h3></div>
     <div>${lastestExperienceMap.detail}<br/>
     <span style="font-family:微软雅黑;line-height:28px;color:#666666;">说明：体验券、体验收益金有效期一个月。</span>
     <div>
    <div class="pub_tit"><h3>体验流程</h3></div>
     <div class="con_info tc"><img src="images/ty_lc_pic.jpg" width="691" height="234" /></div>
  </div>
   <div class="ny_plan_info clearfix " id="con_one_2" style="display:none;">
     <div class="plan_tab_con01 clearfix">
     <div class="fr">
        <span class="mr10">加入人次<em class="f18 orange">${investExperienceListCount}</em>人</span>    
        <span class="mr10">投标总额<em class="f18 orange">${lastestExperienceMap.hasInvestAmount}</em>元</span>
     </div>
     <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tc">
          <tr class="leve_titbj">
            <td width="330">序号</td>
            <td width="156">投标人</td>
            <td width="362">投标金额</td>
            <td width="362">投标时间</td>
          </tr>
          
          <s:if test="%{#request.investExperienceList !=null && #request.investExperienceList.size()>0}">
			<s:iterator value="#request.investExperienceList" id="investExperienceList">
          
          
          <tr>
            <td width="330">1</td>
            <td width="156"><s:property value="#investExperienceList.username" /></td>
            <td width="362">￥<s:property value="#investExperienceList.amount" /></td>
            <td width="362"><s:property value="#investExperienceList.useTime" /></td>
          </tr>
          
	           </s:iterator>
			</s:if>
			
    </table>
    </div>
  </div>
</div>
<div id="remainSeconds" style="display: none;">
			${lastestExperienceMap.remainTime}
<!--底部-->
<!-- 引用底部公共部分 -->     
</div></div></div></div>
<jsp:include page="/include/footer.jsp"></jsp:include>
<script>


function tztysm(){
	$.jBox("iframe:investment_experience_instruction.mht", {
        title: "投资体验说明书",
        width: 600,
        height: 250,
        buttons: { '关闭': true }
    });
}
function experience(){
	param["paramMap.code"] =$("#code").val();
    param["paramMap.id"] = $("#tiyanId").val();
    param["paramMap.ticketNo"] =$(':checkbox[name=ticketNo][checked]').val();
    $.dispatchPost("experienceStart.mht",param,function(data){
          if(data==1){//体验券编号无效。
        	  alert("体验券编号无效。")
          }else if(data==2){//您已体验本标。
        	  alert("您已体验本标")
          }else if(data==3){//体验标可投余额已不足。
              alert("体验标可投余额已不足。")
          }else if(data==4){//参数丢失，请重新执行。
        	  alert("参数丢失，请重新执行。")
          }else if(data==5){//请选择体验券。
        	  alert("请选择体验券。")
          }else if(data==6){//您尚未登录，请登录后在操作。
              alert("您尚未登录，请登录后在操作。")
              window.location.href="login.mht";
          }else if(data==7){//您尚未登录，请登录后在操作。
              alert("请输入验证码结果");
          }else if(data==8){//您尚未登录，请登录后在操作。
              alert("验证码已失效")
              switchCode();
          }else if(data==9){//您尚未登录，请登录后在操作。
              alert("验证码结果不正确")
              window.location.href="experience.mht";
          }else if(data==10){//您尚未登录，请登录后在操作。
              alert("您的验证码结果已输错3次，请10分钟后在参与体验标活动");
              switchCode();
          }else{
        	  if(confirm("恭喜你，成功参与体验投资，招标结束后，开始计息。")){
                  window.location.href="financeExperience.mht";
            }
          }
    })
}
function  denglukejian(){
	window.location.href = 'login.mht';
	return;
}

</script>

<script type="text/javascript">
	//初始化
	function switchCode(){
	    var timenow = new Date();
	    $("#codeNum").attr("src","imageCodeAlgorithm.mht?d="+timenow);
	}
	var SysSecond;
	var InterValObj;

	$(document).ready(function() {
		$(':checkbox[name=ticketNo]').each(function(){
            $(this).click(function(){
                if($(this).attr('checked')){
                    $(':checkbox[name=ticketNo]').removeAttr('checked');
                    $(this).attr('checked','checked');
                }
            });
        });
		SysSecond = parseInt($("#remainSeconds").html()); //这里获取倒计时的起始时间 
			InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行 
		});

	//将时间减去1秒，计算天、时、分、秒 
	function SetRemainTime() {
		if (SysSecond > 0) {
			SysSecond = SysSecond - 1;
			var second = Math.floor(SysSecond % 60); // 计算秒     
			var minite = Math.floor((SysSecond / 60) % 60); //计算分 
			var hour = Math.floor((SysSecond / 3600) % 24); //计算小时 
			var day = Math.floor((SysSecond / 3600) / 24); //计算天 
			var times = day + "天" + hour + "小时" + minite + "分" + second + "秒";
			$("#remainTimeOne").html(times);
		} else {//剩余时间小于或等于0的时候，就停止间隔函数 
			window.clearInterval(InterValObj);
			//这里可以添加倒计时时间为0后需要执行的事件 
		}
	}

	PanicBuynowtime = new Date().getTime();
	var _time = $('.DownTime').attr('data-time');
	if (_time != '') {
		var endtime = new Date(_time).getTime();
		var youtime = endtime - PanicBuynowtime;
		PanicBuynowtime = PanicBuynowtime + 1000;
		var seconds = youtime / 1000;
		var minutes = Math.floor(seconds / 60);
		var hours = Math.floor(minutes / 60);
		var days = Math.floor(hours / 24);
		var CDay = days;
		var CHour = hours % 24;
		var CMinute = minutes % 60;
		var CSecond = Math.floor(seconds % 60);
		var tmilliseconds = parseInt((youtime % 1000) / 100, 10);
			if (endtime <= PanicBuynowtime) {
				$('.DownTime').html("体验结束!");
			} else {
				$('.DownTime').html('剩余:'+CDay + "天" + CHour + "时" + CMinute + "分" + CSecond + "秒");
			}
	} else {
		$(this).html("投资体验预告");
	}
	
	
</script>
</body>
</html>
