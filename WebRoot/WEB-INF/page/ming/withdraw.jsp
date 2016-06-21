<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>三好资本-提现</title>
   <jsp:include page="html5meta.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link href="css/common.css"  rel="stylesheet" type="text/css" />
    <link href="css/user.css"  rel="stylesheet" type="text/css" />
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
</head>

<body>

<jsp:include page="header.jsp"></jsp:include>

<!-- 引用头部公共部分 -->
<div class="mainwarp">
      <div>
         <div class="uesr_border personal">
            <div class="pub_tb bottom"><i></i>提现</div>
            <div class="clearfix re_prompt">
              <span class="f18 red clearfix">温馨提示</span>
              <div class="fr">
              • 
              <s:if test="#request.min_withdraw==null">凡是在三好资本充值未投标的用户，15天以内提现收取本金 <font color="red"> ${costFee}%</font>，15天以后提现免费</s:if>
              <s:else>凡是在三好资本充值未投标的用户，15天以内提现收取本金<font color="red">${costFee}%</font>，最低<font color="red">${min_withdraw}</font>元，15天以后提现免费。</s:else>
              <br/>
• 确保您的银行账号的开户名和您的网站上的真实姓名一致。<br/> 
• 在双休日和法定节假日期间，用户可以申请提现，三好资本会在下一个工作日进行处理。<br/>
• 平台禁止洗钱、信用卡套现、虚假交易等行为，一经发现并确认，将终止该账户的使用。</div>
            </div>
            
            <div class="pre-paid withdraw">
                  <div class="pub_tb clearfix bottom">
                       <div class="fl red"><i></i>填写提现信息</div>
                       <div class="fr gird3_btn" style="margin:0">
                          <span><a href="mybank.mht" class="orange mr5">添加银行卡</a></span>
                          <span><a href="#txr" class="blue hidden">提现记录</a></span>
                     </div>
                 </div>
                 <ul class="withdraw_list">
                   <li class="clearfix"> 
                        <div class="clearfix">选择银行卡：</div>
                        <div class="gird11" style="width: 100%;">
                          <ol class="sprit" style="width: 100%;">
                            
      <s:if test="#request.banks!=null && #request.banks.size>0">
       <s:iterator value="#request.banks"  var="bean" status="sta">
	    
	      <!-- 并且银行卡的状态为绑定状态 -->
	      <s:if test="#bean.cardStatus==1">
		      
		      <li id="sy_${bean.id }" onclick="afocusBank('sy_${bean.id }')" class="clearfix <s:if test='#request.defaultBcard==#bean.id'>on</s:if>">
                   <div class="fl" >
                     <span><input name="wbank" id="radsy_${bean.id }" type="radio" value="${ bean.id}" onclick="radioStylesel('sy_${bean.id }')" <s:if test='#request.defaultBcard==#bean.id'>checked</s:if> /></span>
                       <span class="pr30">${bean.bankName}</span>
                       <span class="pr30">尾号：${fn:substring(bean.cardNo,fn:length(bean.cardNo)-4,fn:length(bean.cardNo))}</span>
                   </div>
                   <span class="fr"><a href="javascript:deleteMyBankInfo('${bean.id }');">删除</a><a href="javascript:defaultFix('${bean.id }');" id="fixsy_${bean.id }" ><s:if test='#request.defaultBcard==#bean.id'>默认卡号</s:if><s:else>设为默认</s:else>  </a></span>
               </li>
		      
		   </s:if>
		</s:iterator>
	</s:if>
	<s:else>
	    <tr><td>&nbsp;</td><td colspan="9" align="left" >
	    <a href="mybank.mht" style='color:#FC9320;font-weight:bold;text-decoration: underline;'>暂未设置提现银行，请先进行设置</a>
	    暂未设置提现银行，请先进行设置
	    </td></tr>
	</s:else>
                            
                          </ol>
                        </div>
                    </li>
                    <li class="clearfix">
                       <div class="fl">可用余额：</div>
                       <div class="gird11"><span class="red"><s:property value="#request.usableSum" default="---" ></s:property></span>元</div>
                       
                    </li>
                    <li class="clearfix">
                       <div>提现金额：</div>
                       <div class="gird11"><span class="yuan">元</span><input type="text" class="input_border w220" id="dealMoney" onchange="countFee1(this)" /><span class="red f12 ln26">&nbsp;每笔提现金额至少为100元以上</span></div>
                    </li>
                    <li class="clearfix">
                       <div>到账金额：</div>
                       <div class="gird11" id="realMoney"></div>
                    </li>
                    <li class="clearfix">
                       <div class="fl">手续费：</div>
                       <div class="gird11" id="acctFee"></div>
                    </li>
                    <li class="clearfix">
                       <div class="fl">预计到账时间：<b class="red">${nextWorkTime }</b><span class="gray6 f12">1-2个工作日（双休日和法定节假日顺延）之内到账</span></div>
                    </li>
                    <li class="clearfix">
                       <div class="fl">交易密码：</div>
                       <div class="gird11">
                       
                       <!--<input type="password" class="inp140" id="dealpwd" />
    					 span style="color: red; float: none;" id="pwd_tip" class="formtips"></span>  -->
                       
                         <input type="password" class="input_border w220" id="dealpwd" /><a href="javascript:searchDealPwd()" class="ln26 blue f14 hidden">忘记交易密码？</a>
                         <p class="clear"></p>
                         <p id="pwd_tip" class="f14 red" style="color: red; display: none;">您输入错了</p> 
                         
                       </div>
                    </li>
                    <li class="clearfix">
                       <div class="fl">验证码 ：</div>
                       <div class="gird11">
                           <input type="text" class="input_border" style="width: 90px;" id="code"/><input type="button" value="发送手机验证码" class="btn" id="clickCode"  onclick="clickCode()"/>
                           <br /><p style="width: 200px;">手机尾号：${ bindingPhoneSecret}</p>
                           <input id="type" name="type" value="drawcash" type="hidden"/>
                           <!-- input type="button" value="60秒后重新发送" class="greybtn" style="display:none;"/> -->
                           <p class="clear"></p>
                           <span style="color: red; float: none;" id="code_tip" class="formtips">
					      	<s:if test="#request.ISDEMO==1">* 演示站点不发送短信</s:if>
					      </span>
                         <p class="f14 in26 mt5 hidden">收不到短信验证码？点击这里<a href="#" onclick="clickCode('vioce')" class="red">获取语言验证码</a></p>
                       </div>
                    </li>
                    <li class="clearfix">
                       <div class="gird12">&nbsp;</div>
                       <div class="gird11">
                           <input type="button" id="btn_submit" class="ui_btn01 usebtn" value="确认提交"/>
                       </div>
                    </li>                            
                 </ul>
            </div>     
            
            <div class="uesr_border recommend clearfix"> 
             <div class="pub_tb bottom  table-responsive" id="txr" ><i></i>提现记录</div>
           <div class="biaoge" style="margin-top:25px;">
          <span id="withdraw"></span>

          </div>
         </div>
             
        </div>
    </div>
</div>

<!-- 引用底部公共部分<jsp:include page="footer.jsp"></jsp:include> -->     
<script type="text/javascript" src="script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
	var flags = false;
	  //手机号码绑定
		  var timers=60;
		  var tipId;
		 
		   
		   function   clickCode(vioce){
               var phone=${request.bindingPhone};
               if($("#clickCode").attr("disabled") && $("#clickCode").attr("disabled")=="disabled"){
            	   alert("验证码已发送,请稍候...");
                   return
               }else{ 
                  $.post("phoneCheck.mht","phone="+phone,function(datas){
                      var param={};
                      param["paramMap.phone"] = phone;
                      if(vioce){
                    	  param["paramMap.vioce"] = true;
                      }
                      timers=180;
                      $.post("sendSMSTX.mht",param,function(data){
                          if(data==1){
                        	  if(vioce){
                        		  $("#vioce").css("display","block");
                                  setTimeout(function(){
                                      $("#vioce").css("display","none");
                                  },60000)
                              }
                        	  window.setTimeout(timer,0);
                          }else if(data==3){
                              alert("对不起，您今日的短信发送量已上限。")
                          }else{
                              alert("验证码发送失败，请联系客服")
                          }
                      });

                  });
                  
                }
             
         }
		   
		   //定时
		   function timer(){
		    
		       if(timers>=0){
		    	   document.getElementById("clickCode").setAttribute("class","greybtn");
		    	   $("#clickCode").attr("disabled","true");
		           $("#clickCode").val(timers+"秒后重新发送");
		           timers--;
		           window.setTimeout(timer,1000);
		       }else{
		          document.getElementById("clickCode").setAttribute("class","blue_btn");
		          $("#clickCode").val("重新发送验证码")
		          $("#clickCode").removeAttr("disabled");
		           $.post("removeCode.mht","",function(data){});
		           
		       }
		   }
		</script>

<script type="text/javascript">
var flag = false;
  $(function(){
          //$("#zh_hover").attr('class','nav_first');
	      //$('#li_2').addClass('on');
	      $("#cashNew").addClass('on');
		  $('.tabmain').find('li').click(function(){
		  $('.tabmain').find('li').removeClass('on');
        
       });  
       param["pageBean.pageNum"] = 1;
	    initListInfo(param);
	 });
  
   	function initListInfo(praData){
   	
		$.dispatchPost("queryWithdrawList.mht",praData,initCallBack);
	}
	function initCallBack(data){
		$("#withdraw").html(data);
	}
	
	function jumpUrl(obj){
       window.location.href=obj;
    }
	 
	$("#dealpwd").blur(function(){
	     if($("#dealpwd").val()==""){
	       $("#pwd_tip").html("交易密码不能为空");
	     }else{
	       $("#pwd_tip").html("");
	     }
    });
    
    $("#dealMoney").blur(function(){
	     if($("#dealMoney").val()=="" ){
	       $("#money_tip").html("提现金额不能为空");
	     }else if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#dealMoney").val())){
	       $("#money_tip").html("请输入正确的提现金额，必须为大于0的数字"); 
	     }else if($("#dealMoney").val()<=100 ){
	       $("#money_tip").html("提现金额必须大于100元"); 
	     }else if( $("#dealMoney").val()> ${usableSum}){
	      //alert(${usableSum});
	       $("#money_tip").html("提现金额不能大于可用余额"); 
	     }else if($("#dealMoney").val()>${max_withdraw}){
	    	 $("#money_tip").html("提现金额不能大于${max_withdraw}"); 
	     }else{
	       $("#money_tip").html(""); 
	     }
    });
    
    $("#code").blur(function(){
	     if($("#code").val()==""){
	       $("#code_tip").html("验证码不能为空");
	     }else{
	       $("#code_tip").html("");
	    }
    });
    
    $("#btn_submit").click(function(){   
		   addWithdraw();
		});
		
    function addWithdraw(){
        if($("input:radio[name='wbank'][checked]").val()==undefined){
          alert("请设置或者选择提现银行卡信息");
             return;
        }else if($("#dealpwd").val()==""){
           alert("请填写交易密码");
             return;
         }else if($("#dealMoney").val()=="" ){
            alert("请填写提现金额");
             return;
         }else if($("#dealMoney").val() <= 100 ){
            alert("提现金额应大于100元");
            $("#dealMoney").focus();
            return;
         }else   if($("#code").val()==""){
            $("#code_tip").html("验证码不能为空");
            return;
         }
         if($("#money_tip").text() != ""){
             return;
         }
        
        if(!window.confirm("确定添加提现记录")){
          return;
        }else{
         $("#btn_submit").attr("disabled",true);
        }
        
         param["paramMap.dealpwd"] = $("#dealpwd").val();
         param["paramMap.money"] = $("#dealMoney").val();
         param["paramMap.cellPhone"] = '${bindingPhone}';
         param["paramMap.code"] = $("#code").val();
         param["paramMap.bankId"] = $("input:radio[name='wbank'][checked]").val();
         param["paramMap.type"] = $("#type").val();
         if(flag)
            return;
       	 $.dispatchPost("addWithdraw.mht",param,function(data){
    	   flag = false;
	       if(data.msg == 1){
              flag  = true;
              timers=0;
              alert("申请提现成功");
              jumpUrl('withdrawLoad.mht');
          }else if(data.msg==2){
        	  $("#btn_submit").attr("disabled",false);
        	  if(confirm("为了更快的处理您的提现，请完善您银行卡的地址信息，是否立即填写？")){
        		  jQuery.jBox.open("iframe:updateBankPCinit.mht?bankId="+param["paramMap.bankId"], "填写开户行所在地", 700,350,{ buttons: { }});
        	  }
          }else{
           $("#btn_submit").attr("disabled",false);
          	  alert(data.msg);
          	  return;
          	   //jumpUrl('withdrawLoad.mht');
          }
       });
    }
    
    $("#send_phoneCode").click(function(){
		var param = {"type":"drawcash"};
		$.dispatchPost("sendPhoneCode.mht",param,function(data){
			if(data==1){
				alert("发送成功");
			}
			alert("验证码：" + data);
		});
	});


    function countFee1(obj){

    	if($("#dealMoney").val() <= 100 ){
            alert("提现金额应大于100元");
            $("#dealMoney").focus();
            return;
         }
        
    	var param = {};
    	param["paramMap.money"] = obj.value;

    	$.dispatchPost("withdrawCount.mht",param,function(data){
			if(data.ret==1){
				//alert("调用成功");
				var fee = data.ret_desc;
				var mdealMoney = $("#dealMoney").val() - fee;
				$("#realMoney").html(mdealMoney+"元");
				$("#acctFee").html(fee+"元");
			} else{
				alert(data.ret_desc);
				$("#dealMoney").focus();
			}
		});
		
    }


    function radioStylesel(syid){
		var syids = $("li[id^='sy_']");
		syids.removeClass("on");
		$("#"+syid).addClass("on");
    }


    ///defaultFix.mht?bcard=${bean.id }
    function defaultFix(bid){
    	//$("#fixsy_"+bid).attr("display","none");
    	var param = {};
    	param["paramMap.defaultBcard"] = bid;

    	$.dispatchPost("defaultFix.mht",param,function(data){
			if(data.ret==1){
			}
		});
    	var fsyids = $("a[id^='fixsy_']");
    	for (var it = 0; it <fsyids.length; it++){
    		fsyids[it].innerHTML="设为默认";
        }
    	$("#fixsy_"+bid).html("设置成功");
    	
    }

	function afocusBank(bid){
		var syids = $("li[id^='sy_']");
		syids.removeClass("on");
		$("#"+bid).addClass("on");
		$("#rad"+bid).attr("checked","checked");
	}
	
	//删除该条已经添加的银行卡信息
	function deleteMyBankInfo(id){
		if(!window.confirm("确定要删除吗?")){
			return;
		}
		$.dispatchPost("deleteMyBankInfo.mht",{bankId:id},function(data){
			window.location.href="withdrawLoad.mht?mobile=f98c59c3-ab9e-43f2-b2d1-2703d1e01850";
		});
	}
    
</script>

</body>
</html>