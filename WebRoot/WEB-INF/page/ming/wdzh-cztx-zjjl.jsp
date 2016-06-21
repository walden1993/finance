<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>资金记录</title>
	<jsp:include page="html5meta.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="script/bootstrap/css/bootstrap.min.css">
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="header.jsp"></jsp:include>	
<div class="mainwarp">
      <div class="uesr_border personal">
     
       <div class="pub_tb bottom"><i></i>我的投资</div>
      <div class="boxmain2">
        <div class="uesr_serch mt20">
         类型:  
         <s:select list="potypeList" id="momeyType" name="momeyType" cssClass="input_border w100" listKey="operateType" listValue="fundMode"   headerKey="" headerValue="--请选择--"></s:select>
   周期：
   <select id="cycle" name="cycle">
     <option value="3m">最近3个月</option>
     <option value="1m">最近1个月</option>
     <option value="1w">最近1周</option>
     <option value="today">今天</option>
   </select>
        
       日期：<input id="startTime" type="text" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
       -<input id="endTime" type="text" class="input_border w100" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
       
        <a href="javascript:void(0)" class="serch_btn" onclick="fundRecordList();">查询</a>
        </div>
        <div class="biaoge" id="fundRecord">
        </div>
      </div>
      </div>
</div>
</div>
<!-- 引用底部公共部分 -->     
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script>
    $(function(){
	     //$("#zh_hover").attr('class','nav_first');
	      //$('#li_2').addClass('on');
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
