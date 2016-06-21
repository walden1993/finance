<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
<jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link href="css/css.css"  rel="stylesheet" type="text/css" />
  </head> 
  <body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="nymain">
  <div class="kfbox">
    <div class="l_nav">
     <div class="l_navmain">
    <h2>帮助中心</h2>
    <ul class="help" style="width:236px; margin-top:0; font-size:14px">
      <s:iterator value="#request.types" id="callcenter" var="bean">
	    <s:if test="#bean.id==#request.typeId">
		    <li class="on"><a href="callcenter.mht?type=true&cid=${bean.id}" >${bean.helpDescribe }</a></li>
	    </s:if>
	    <s:else>
	    	<li><a href="callcenter.mht?type=true&cid=${bean.id}">${bean.helpDescribe }</a></li>
	    </s:else>
	</s:iterator>
    </ul>
    <h2 style="text-align:left; padding-left:20px"><span><a href="callkfs.mht">更多>></a></span>联系客服</h2>
    <s:if test="#request.lists ==null || #request.lists.size<=0">
        暂无数据
    </s:if>
    <s:else>
    <ul class="lxkf">
	    <s:iterator value="#request.lists"  var="bean">
		     <li>
		      <a target="_blank" 
		      href="http://wpa.qq.com/msgrd?v=3&uin=${QQ}&site=qq&menu=yes" >
		      <shove:img src="${bean.kefuImage }" defaulImg="images/hslogo_42.jpg" title="${bean.name }" width="72" height="72"  ></shove:img>
		      </a>
		      </li>
	    </s:iterator>
     </ul>
    </s:else>
    </div>
    </div>
    <div class="r_main" id="kfs">
      <div class="box" style="padding-bottom:20px;">
     <h2>联系客服</h2>
      <div class="boxmain">
      
      <s:if test="#request.lists == null || #request.lists.size<=0">
           暂无数据
      </s:if>
      <s:else>
       <ul class="kefu">
        <s:iterator value="#request.lists" var="bean" status="sta">
              <li>
              <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${QQ}&site=qq&menu=yes" class="tx">
              <shove:img src="${bean.kefuImage }" defaulImg="images/hslogo_42.jpg" title="${bean.name }" width="72" height="72"  ></shove:img>
              </a><br/>
		      <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${QQ}&site=qq&menu=yes">${bean.name }</a><br/>
		      <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${QQ}&site=qq&menu=yes">
		      <img border="0" src="http://wpa.qq.com/pa?p=1:${QQ}:1" 
		      alt="点击这里给我发消息" title="点击这里给我发消息"/></a>
              </li>
        </s:iterator>
        </ul>
      </s:else>
      </div>
      </div>
</div> 
</div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<script type="text/javascript">
    function showAnswer(id){
        //alert(id);
		$.dispatchPost("callcenterAnswer.mht",{id:id},initCallBack);
 	}
 	
 	function initCallBack(data){
 		$("#dataInfo").html(data);
 	}
 	
 	function cp(id){
	    $("#qs1").html("答："+$("#qss"+id).text());
	}
	
	 $("#jumpPage").attr("href","javascript:void(null)");
	 $("#curPageText").attr("class","cpage");
		$("#jumpPage").click(function(){
		     var curPage = $("#curPageText").val();
			 if(isNaN(curPage)){
				alert("输入格式不正确!");
				return;
			 }
	    window.location.href="callcenter.mht?curPage="+curPage+"&pageSize=${pageBean.pageSize}";
		});
	
	$(function(){
    //样式选中
	var sd=parseInt($(".l_navmain").css("height"));
	var sf=parseInt($(".r_main").css("height"));
	if(sd<sf){
	$(".l_navmain").css("height",sf)
	}
		dqzt(6);
 
	});	
</script>

</body>
</html>