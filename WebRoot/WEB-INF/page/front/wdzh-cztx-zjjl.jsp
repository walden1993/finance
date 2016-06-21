<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set value="资金记录"  var="title"  scope="request" />
   <jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="wrap">
 <div class="w950 clearfix">
 	<ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li><a href="home.mht">我的账户</a></li>
	  <li class="active">资金记录</li>
  </ol>
      <!-- 引用我的帐号主页左边栏 -->
      <%@include file="/include/left.jsp" %>
    <div class="fr w750 clearfix">
    <div class="pub_tb bottom clearfix">
		  <h3 style="border:none; "><a href="rechargeInit.mht" style="color:#a6a6a6;">充值</a></h3>
		  <h3 style="border:none;"><a href="withdrawLoad.mht" style="color:#a6a6a6;">提现</a></h3>
		  <h3 style="border:none;"><a href="mybank.mht" style="color:#a6a6a6;">我的银行卡</a></h3>
		  <h3><span>资金记录</span></h3>
		  <div class="clearfix"></div>
      </div>
      <div class="uesr_border personal">
      <div class="boxmain2">
        <div class="uesr_serch mt20"><span class="fl h32 mr10" style="line-height: 32px;">类型:</span><div class="w100 fl"><s:select list="potypeList" id="momeyType" name="momeyType" cssClass="input_border w100"  listKey="operateType" listValue="fundMode"   headerKey="" headerValue="--请选择--"/></div><span class="fl h32 mr10  ml10"  style="line-height: 32px;"> 周期：</span><select id="cycle" name="cycle" class="input_border w100 fl" >
   	 <option value="">-请选择-</option>
     <option value="3m">最近3个月</option>
     <option value="1m">最近1个月</option>
     <option value="1w">最近1周</option>
     <option value="today">今天</option>
   </select>
        
       日期：<input id="startTime" type="text" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
       -<input id="endTime" type="text" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
       
        <button  class="button ml20 h34 w100" onclick="fundRecordList();">查询</button>
        </div>
        <div class="biaoge" id="fundRecord">
        </div>
      </div>
      </div>
    </div>
  </div>
</div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script>
    $(function(){
	      //displayDetail(2,4);
	      $("#fundRecordNew").addClass('on');
		  $('.tabmain').find('li').click(function(){
		  $('.tabmain').find('li').removeClass('on');
        
       }); 
       param["pageBean.pageNum"] = 1;
	    initListInfo(param);
	 });
	 
	 function initListInfo(praData){
		$.dispatchPost("queryFundrecordList.mht",praData,initCallBack);
	}
	function initCallBack(data){
		$("#fundRecord").html(data);
	}
	
   function fundRecordList(){
      if($("#startTime").val()!="" && $("#endTime").val()!=""){
	        if($("#startTime").val() >$("#endTime").val() ){
	         alert("结束日期要大于开始日期");
	         return;
	      }
      }
      param["pageBean.pageNum"] = 1;
      param["paramMap.startTime"]=$("#startTime").val();
      param["paramMap.endTime"]=$("#endTime").val();
      param["paramMap.cycle"]=$("#cycle").val();
      param["paramMap.momeyType"]=$("#momeyType").find("option:selected").text();
      $.dispatchPost("queryFundrecordList.mht",param,function(data){
         $("#fundRecord").html(data);
      });
   }
   
   function jumpUrl(obj){
          window.location.href=obj;
       }
</script>
</body>
</html>
