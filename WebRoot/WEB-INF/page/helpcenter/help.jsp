<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="帮助中心" scope="request" var="title"/>
<jsp:include page="/include/head.jsp"></jsp:include>
<link rel="stylesheet" href="css/helpcenter.css" type="text/css" />
<script type="text/javascript">var menuIndex = 5;</script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<!--内容区域-->


<div class="wrap mb20">
<!--内容-->
<div class="myhelpcenter clearfx w950">
<ol class="breadcrumb mb0 bg-none">
		  <li><a href="/">首页 </a></li>
		  <li><a href="queryAboutInfo.mht?msg=99">关于我们</a></li>
		  <li class="active">帮助中心</li>
	  </ol>
<div style="float:left;">
<a href="queryAboutInfo.mht?msg=106" class="seek">
   <div class="logo"><img src="images/searchlogo.png"></div>
   <p style="color: rgb(255, 255, 255); background: #DE4A4A;">搜索</p>
</a>
<div class="myhelpcenter_left" style="padding-top:16px">
     <!--常见问题-->
    <div class="myhelpcenter01">
    	<h2>常见问题</h2>
        <ul>
        	<s:iterator value="#request.types" var="type" status="index" >
	        	<a href="javascript:cp('${id}')" style="background: rgb(255, 255, 255);">
	        		<div><img src="images/hp_ico_${index.index}.png" style="width:15px;height:18px;margin:10px;margin-left:-20px;float:left;">
	        			${helpDescribe}
	        		</div>
	        	</a>
        	</s:iterator>
        </ul>
    </div>
    <!--常见问题end-->
    
    
<!--更多-->
    <div class="myhelpcenter01">
    	<h2>更多</h2>
        <ul>
        
        	<a href="javascript:shdJs()" style="background: rgb(255, 255, 255);">
        		<div>
        		<img src="images/hp_ico_6.png" style="width:15px;height:18px;margin:10px;margin-left:-20px;float:left;">
        		名词解释</div>
        	</a>
        	
        	<a href="calctool.mht" style="background: rgb(255, 255, 255);">
        		<div>
        		<img src="images/hp_ico_7.png" style="width:15px;height:18px;margin:10px;margin-left:-20px;float:left;">
        		理财工具</div>
        	</a>
        </ul>
    </div>
    <!--更多end-->
    </div>
</div>
<div class="myhelpcenter_right">
	<!--我的主页-->
	    <div class="myhelpcenter02">
			<div class="myhelpcenter03"><span>搜索</span></div>
	    </div>
	    <!--我的主页end-->
	    <div class=" clearfx hjh">
	           <!--帮助中心右边1-->
	         <div class="left">
                  <div class="seek_hot">
                     <form >
	                  <div class="input-group">
	                  <input type="text" id="queryString" class="form-control" placeholder="请输入你要搜索的关键字" aria-describedby="basic-addon2">
	                  <span class="input-group-addon" id="basic-addon2"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></span>
	                </div>
                    </form>
                    
                  </div>
                   <div class="title_cy">
	                   <a style="padding-left:0px;" >风险</a>|<a >密码</a>|<a >实名认证</a>|<a >费用</a>|<a >利息</a>|<a >提现</a>|<a >合同</a>|
	                   <a > 担保公司</a>|<a >充值</a>|<a >投资</a>|<a >合法性</a>|<a>账户</a>|<a>网银</a>                         
                   </div>
                   <div class="search_result">
                   
                   </div>
					
	          </div>
	          
	          <!--帮助中心右边1end-->

	          <!--帮助中心右边2-->
	          <jsp:include page="/WEB-INF/page/helpcenter/static_right.jsp"></jsp:include>
	      	<!--帮助中心右边2end-->
	    </div>
</div>
</div>
</div>
    <div class="clear"></div>

<%-- <div class="n_about_top">
  <div class="w1002 location clearfix"><a href="<%=application.getAttribute("basePath")%>">首页</a> > 关于我们</div>
  <div class="w1002 anchor clearfix">
  <ul class="n_about_menu">
       <li><a href="queryAboutInfo.mht?msg=99" >关于我们</a></li>
       <li><a href="queryAboutInfo.mht?msg=100">网站公告</a></li>
       <li><a href="queryAboutInfo.mht?msg=101">平台资讯</a></li>
       <li><a href="queryAboutInfo.mht?msg=104">合作伙伴</a></li>
       <li><a href="queryAboutInfo.mht?msg=105">联系我们</a></li>
       <li class="on"><a href="queryAboutInfo.mht?msg=106">帮助中心</a></li>
       <li><a href="queryAboutInfo.mht?msg=107">人才招聘</a></li>
   </ul>
   </div>
</div>
<!--介绍-->
<div class="n_gray_bj">
   <div class="w1002 pt20 pb20 fang"><h1>帮助中心</h1></div>
   <div class="w1002 about_help clearfix">
   <div class="help_nav n_abt_help clearfix">
	    <ul>
	    <s:iterator value="#request.types" id="callcenter" var="bean">
	        <s:if test="#bean.id==#request.typeId">
	            <li  class="cur1"><a href="queryAboutInfo.mht?msg=106&cid=${bean.id}" >${bean.helpDescribe }</a></li>
	        </s:if>
	        <s:else>
	            <li><a href="queryAboutInfo.mht?msg=106&cid=${bean.id}">${bean.helpDescribe }</a></li>
	        </s:else>
	    </s:iterator>
	    </ul>
	    <div class="curBg"></div>
        <div class="cls"></div>
    </div>
    <p class="clear"></p>
    <div class="p30 clearfix">
      <div class="box clearfix mb30">
      <ol class="n_abt_qa clearfix">
      <s:if test="pageBean.page!=null || pageBean.page.size>0">
          <s:iterator value="pageBean.page"  var="bean" status="sta">
           <li><a id="qs" href="javascript:showAnswer(${bean.questionId})" onclick="javascript:cp(${bean.questionId});"><strong>问：</strong><span id="qss${bean.questionId}">${bean.question}</span></a>
          <s:if test="#sta.index==0">
          <script type="text/javascript">
          $(function(){
              cp(${questionId});
          })
          </script>
          </s:if>
          </li>
          </s:iterator>
          </s:if>
          <s:else>
              暂无数据
          </s:else>
      </ol>
      </div>
      <div class="fenye">
      <div class="page">
    <p>
        <shove:page url="queryAboutInfo.mht"  theme="number"   curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }"   totalCount="${pageBean.totalNum}">
            <s:param name="cid" >${cid}</s:param>
            <s:param name="msg" >106</s:param>
        </shove:page>
    </p>
      </div>
      </div>
      <p class="clear"></p>
      <div class="infomation mt30 clearfix">
         <div class="tit tc"><h1 id="qs1"></h1></div>
         <div class="mt20 info_con">
           <p id="dataInfo">
            </p>
        </div>
      </div>
    </div>  
   </div> 
</div> --%>
<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
var param={};
$(document).ready(function(){
	$("#basic-addon2").click(function(){
    	var queryString = $("#queryString").val();
    	if(queryString){
    		param["paramMap.queryString"]=queryString;
    		$.dispatchPost("callcenterSearch.mht",param,initCallBack);
    	}
    })
    $(".title_cy a").css("cursor","pointer");
    $(".title_cy a").click(function(){
    	$("#queryString").val($(this).text());
    	param["paramMap.queryString"]=$(this).text();
    	$.dispatchPost("callcenterSearch.mht",param,initCallBack);
    })
})

function shdJs(){
	$.dispatchPost("callcenterAnswerMore.mht",null,function(data){ 
		$(".myhelpcenter_right").html(data);
		$(".shd_js a").click(function(){	
			var index=$(".shd_js a").index(this);
			$(".shd_js a").removeClass("active").eq(index).addClass("active")
			$(".tz").hide().eq(index).stop().fadeIn();
	    });
	});
}

function initCallBack(data){
    var queryString = $("#queryString").val();
	data = data.replace(new RegExp(queryString,"gm"),"<font style=\'color:red;font-size:14px;\'>"+queryString+"</font>")
    $(".search_result").html(data);
}

function initListInfo(data){
	$.dispatchPost("callcenterSearch.mht",data,initCallBack);
}

$(function(){
	$("#jumpPage").attr("href","javascript:void(null)");
    $("#curPageText").attr("class","cpage");
       $("#jumpPage").click(function(){
            var curPage = $("#curPageText").val();
            if(isNaN(curPage)){
               alert("输入格式不正确!");
               return;
            }
       window.location.href="queryAboutInfo.mht?msg=106&curPage="+curPage+"&pageSize=${pageBean.pageSize}&cid="+${cid};
    });
    
    var cpid = GetRequest('cid');
    if(cpid){
    	cp(cpid);
    }
    var js = GetRequest('js');
    if(js){
    	shdJs();
    }
})

function cp(id){
    //$("#qs1").html("问："+$("#qss"+id).text());
    $.dispatchPost("callcenterAnswer.mht",{id:id},function(data){$(".myhelpcenter_right").html(data);});
}
//搜索
function qa(id){
    //$("#qs1").html("问："+$("#qss"+id).text());
    $.dispatchPost("callcenterAnswer.mht",{id:id},function(data){ $(".myhelpcenter_right").html(data);});
}

$(function(){
	$(".myhelpcenter_left ul a").hover(function(){
		var i=$(".myhelpcenter_left ul a").index(this);
		$(".myhelpcenter_left ul a").eq(i).css({background:"rgb(255, 241, 241)"});
},function(){
	 $(".myhelpcenter_left ul a").css({background:"#fff"});
});	
});


</script>


<script type="text/javascript">
$(".myhelpcenter01 ul a").click(function(){	
	var index=$(".myhelpcenter01 ul a").index(this);
	$(".myhelpcenter01 ul a").removeClass("active").eq(index).addClass("active")
		$(".tz").hide().eq(index).stop().fadeIn();
    });

</script>
</body>
</html>
