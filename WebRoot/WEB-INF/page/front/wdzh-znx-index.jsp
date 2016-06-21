<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set value="消息中心"  var="title"  scope="request" />
<jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="wrap nymain">
  <div class="wdzh w950">
  	<ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li><a href="home.mht">我的账户</a></li>
	  <li class="active">消息中心</li>
  </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
     <div class="pub_tb bottom w750 fr"><h3>消息中心</h3></div>
    <div class="uesr_border personal w750 fr">
      	<div class="tabtil mt20">
	        <ul class="nav nav-tabs">
	        <li role="presentation"  class="active" onclick="showSysMails();"><a href="#">系统消息</a></li>
	        <li role="presentation"  onclick="showReceiveMails();"><a href="#">收件箱</a></li>
	        <%-- <li role="presentation"  onclick="showSendMails();"><a href="#">发件箱</a></li>
	        <li role="presentation" ><a href="#">发信息</a></li> --%>
	        <li role="presentation" onclick="settingMail();"><a href="#">消息设置</a></li>
        </ul>
        </div>
      <div class="box p30">
        <div id="sysInfo"></div>
     </div>
	  <div class="box p30" style="display:none;">
        <div id="receiveInfo"></div>
    </div>
     <%--  <div class="box p30" style="display:none;">
        <div id="sendInfo"></div>
     </div>
      <jsp:include page="wdzh-znx-sendMail.jsp"></jsp:include> --%>
      <div class="box p30" style="display:none;">
        <div id="setting"></div>
     </div>
    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
 <script>
    $(function(){
		 //displayDetail(4,6);
	  $('.tabtil').find('li').click(function(){
	    $('.tabtil').find('li').removeClass('active');
	    $(this).addClass('active');
	    $('.tabtil').nextAll('div').hide();
        $('.tabtil').nextAll('div').eq($(this).index()).show();
   	  });
   	   initListInfo();
   	 
	});
	
	 function initListInfo(){
	 	$.dispatchPost("querySysMailsInit.mht",param,function(data){
          		$("#sysInfo").html(data);
       	});
	 }

	
	function switchCode(){
		var timenow = new Date();
		$("#codeNum").attr("src","admin/imageCode.mht?pageId=userlogin&d="+timenow);
	}
	
	
	
      //收件人
       $("#receiver").blur(function(){
	     if($("#receiver").val()==""){
	       $("#s_receiver").html("*收件人不能为空");
	     }else{
	       checkRegister();
	     }
	  });
	  //标题
       $("#title").blur(function(){
	     if($("#title").val()==""){
	       $("#s_title").html("*标题不能为空");
	     }else{
	       $("#s_title").html("");
	     }
	  });
	  //验证码
       $("#code").blur(function(){
	     if($("#code").val()==""){
	       $("#s_code").html("*验证码不能为空");
	     }else{
	       $("#s_code").html("");
	     }
	  });
       
       //内容框
       $("#content").blur(function(){
	     if($("#content").val()==""){
	       $("#s_content").html("*内容不能为空");
	     }else{
	       $("#s_content").html("");
	     }
	  });
        
     
</script>
<script>
   //检查用户名是否有效
    function checkRegister(){
        param["paramMap.receiver"] = $("#receiver").val();
		$.post("judgeUserName.mht",param,function(data){
              if(data == 1 ){
                 $("#s_receiver").html("*收件人不存在或者还不是您的好友！");
              }else{
                 $("#s_receiver").html("");
              }
		});
     }
       function returnToPage_(pNum,type){
       if(type == 2){ //系统邮件
         returnToPage_s(pNum);
         return;
       }else if(type == 1){//收件箱
         returnToPage_r(pNum);
         return;
       }else if(type ==100){//发件箱
          returnToPage_ss(pNum);
          return;
       }
	}
     function addMail(){
        $("#btnSave").attr("disabled",true);
     	param["paramMap.receiver"] = $("#receiver").val();
     	param["paramMap.title"] = $("#title_s").val();
     	param["paramMap.content"] = $("#content").val();
     	param["paramMap.code"] = $("#code").val();
     	param["paramMap.pageId"] = "userlogin";
     	if($("#receiver").val()==""){
	       $("#s_receiver").html("*收件人不能为空");
	       $("#btnSave").attr("disabled",false);
	       return;
	     }
	     if($("#title_s").val()==""){
	       $("#s_title").html("*标题不能为空");
	       $("#btnSave").attr("disabled",false);
	       return;
	     }
	     if($("#content").val()==""){
	       $("#s_content").html("*内容不能为空");
	       $("#btnSave").attr("disabled",false);
	       return;
	     }
	     if($("#code").val()==""){
	       $("#s_code").html("*验证码不能为空");
	       $("#btnSave").attr("disabled",false);
	       return;
	     }
	     //有错误提示的时候不提交
	     if($("#s_receiver").text()!="" || $("#s_title").text()!="" ||$("#s_content").text()!=""
	       ||$("#s_code").text()!=""){
	          return;
	       }
     	$.dispatchPost("addMail.mht",param,function(data){
     	switchCode();
     	   if(data == 5){
     	     $("#s_code").html("验证码错误");
     	     $("#code").attr("value","");
     	     $("#btnSave").attr("disabled",false);
     	     return;
     	   }else if(data == 1){
     	     alert("邮件发送失败，请重新发送");
     	     $("#btnSave").attr("disabled",false);
     	     return;
     	   }else if(data == 8){
     	     alert("你是黑名单用户不能发生站内信");
     	     $("#btnSave").attr("disabled",false);
     	     return;
     	   }else{
     	   	 alert("信息发送成功");
     	   	 switchCode();
     	   	 $("#title_s").attr("value","");
     	   	 $("#code").attr("value","");
     	   	 $("#receiver").attr("value","");
     	   	 $("#content").attr("value","");
     	   	 $("#btnSave").attr("disabled",false);
     	   }
     	});
     }
     
     function showReceiveMails(){
        param["pageBean.pageNum"]=1;
        $.dispatchPost("queryReceiveMailsInit.mht",null,function(data){
           $("#receiveInfo").html(data);
        });
     }
     
     //设置消息
     function settingMail(){
        $.dispatchPost("szform.mht",null,function(data){
           $("#setting").html(data);
        });
     }
     
     function showSendMails(){
        param["pageBean.pageNum"]=1;
        $.dispatchPost("querySendMailsInit.mht",null,function(data){
           $("#sendInfo").html(data);
        });
     }
     //显示系统消息
     function showSysMails(){
        param["pageBean.pageNum"]=1;
        $.dispatchPost("querySysMailsInit.mht",null,function(data){
           $("#sysInfo").html(data);
        });
     }
     
     //收件箱全选
     function checkAll_Receive(e){
   		if(e.checked){
   			$(".re").attr("checked","checked");
   		}else{
   			$(".re").removeAttr("checked");
   		}
   	 }
   	 
   	 function checkAll_Send(e){
   		if(e.checked){
   			$(".se").attr("checked","checked");
   		}else{
   			$(".se").removeAttr("checked");
   		}
   	 }
   	 
   	 function checkAll_Sys(e){
   		if(e.checked){
   			$(".sys").attr("checked","checked");
   		}else{
   			$(".sys").removeAttr("checked");
   		}
   	 }
   	 
   	 function cacel1(){
        $("#checkbox1").removeAttr("checked");
   	 }
   	function cacel2(){
        $("#checkbox5").removeAttr("checked");
    }
   	function cacel3(){
        $("#checkbox6").removeAttr("checked");
     }
   	 
	//弹出窗口关闭
   		function close(){
   			 ClosePop();  			  
   		}
	
	
	//设置邮件信息为已读
	/*function readedSends(){
 		if(!window.confirm("确定要将所选邮件标记为已读吗?")){
 			return;
 		}
 		var stIdArray = [];
		$("input[name='sendMail']:checked").each(function(){
			stIdArray.push($(this).val());
		});
		if(stIdArray.length == 0){
			alert("请选择要标记的内容");
			return ;
		}
		var ids = stIdArray.join(",");
		$.dispatchPost("updateSend2Readed.mht",{ids:ids},function(data){
           $("#sendInfo").html(data);
        });
	}*/
	
	
	
	
	
	//设置邮件信息为未读
	/*function unReadSends(){
 		if(!window.confirm("确定要将所选邮件标记为未读吗?")){
 			return;
 		}
 		var stIdArray = [];
		$("input[name='sendMail']:checked").each(function(){
			stIdArray.push($(this).val());
		});
		if(stIdArray.length == 0){
			alert("请选择要标记的内容");
			return ;
		}
		var ids = stIdArray.join(",");
		$.dispatchPost("updateSend2UNReaded.mht",{ids:ids},function(data){
           $("#sendInfo").html(data);
        });
	}*/
	
	
	
</script>
</body>
</html>
