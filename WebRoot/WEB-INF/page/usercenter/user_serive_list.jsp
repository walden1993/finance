<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本</title>
</head>

<body style="max-width: 400px;" class="bg-white">
<div class="nymain">
  <div class="kfbox">
    <div class="r_main" >
      <div class="box">
      <div class="pub_tb bottom clearfix">
		  <h3>申请VIP</h3>
		  <div class="clearfix"></div>
      </div>
      <div class="boxmain">
      <ul class="kefu">
      
      <s:iterator value="#request.map" var="bean" status="sta">
            <li class="fl mr20"><a  class="tx">
            <shove:img src="${kefuImage}" defaulImg="images/default-img.jpg" width="72" height="72"></shove:img>
          </a><br/>
      <a>${name }</a><br/>
      <a  href="javascript:void(0);">
         <input style="cursor: pointer;" type="button" value="选择客服"   onclick="javascript:ffff('${name}',${id});" class="scbtn" />
      </a></li>
      </s:iterator>
      </ul>
      </div>
      </div> 
    </div>
  </div>
</div>
<script type="text/javascript">
  function  click_b(data,data2){
       var rt = new Array(2);
        rt[0] =   data;
        rt[1] = data2;
         window.returnValue = rt;
         window.close();
  }
</script>
</body>
</html>
