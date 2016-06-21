<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link rel="stylesheet" href="css/bidlist.css" type="text/css" />
    <script type="text/javascript">var menuIndex = 1;</script>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>

<%-- <div class="ny_content clearfix">
  <div class="w1002 location"><a href="<%=application.getAttribute("basePath")%>">首页</a> > 我要投资</div>
  <div class="bd_detail clearfix item_list">
     <div class="screening">
         <div class="til"><span class="fl">筛选投资项目</span><span class="fr f16"><a href="borrowNew.mht"  target="_blank"><img src="images/list_pic01_03.png" width="17" height="16"  class="mr5"/>填写借款申请</a></span></div>
         <dl class="clearfix">
           <dt>标的类型：</dt>
           <dd id="type"><a href="javascript:;" class="cur_one"  data=""  onclick="a('')">不限</a><a href="javascript:;"   onclick="a('4')" data="4"  >实地考察</a><a href="javascript:;" data="3">信用标</a><a href="javascript:;"  onclick="a('5')"  data="5"  >担保借款</a>
           <!-- a href="javascript:;"   onclick="a('2')"  data="2"  >秒还借款</a><a href="javascript:;"  onclick="a('1')" data="1"  >净值借款</a> --></dd>
         </dl>
         <!-- dl>
           <dt>还款方式：</dt>
           <dd id="paymentMode">
           <a href="javascript:;" data="" class="cur_one"  onclick="b('')">不限</a><a href="javascript:;"  data="1"   onclick="b('1')">按月分期还款</a><a href="javascript:;" data="2"   onclick="b('2')">按月付息、到期还本</a><a href="javascript:;"  data="4"   onclick="b('4')">一次性还款</a>
           </dd>
         </dl>
         <dl>
           <dt>借款目的：</dt>
           <dd id="purpose"> <a href="javascript:;" data=""  class="cur_one"  onclick="c('')">不限</a>
           <s:iterator  value="#request.borrowPurposeList"  var="bean">
                 <a href="javascript:;" onclick="c('${selectValue}')" data="${selectValue}">${selectName}</a>
           </s:iterator>
           </dd>
         </dl>  -->
         <dl>
           <dt>项目状态：</dt>
           <dd id="proStatus">
                 <a href="javascript:;" class="cur_one"  data=""  onclick="b('')">不限</a>
                 <a href="javascript:;"  onclick="b('1')" data="1"  >预告中</a>
				 <a href="javascript:;"  onclick="b('2')"  data="2"  >投标中</a>
				 <a href="javascript:;"  onclick="b('3')"  data="3"  >已满标</a>
				 <a href="javascript:;"  onclick="b('4')"  data="4"  >还款中</a>
				 <a href="javascript:;"  onclick="b('5')"  data="5"  >已还款 </a>
           </dd>
         </dl> 
         <dl>
           <dt>借款期限：</dt>
           <dd  id="deadline"> <a href="javascript:;" data=""  class="cur_one"  onclick="d('')"  >不限</a>
           <s:iterator  value="#request.borrowDeadlineList"  var="bean">
                 <a href="javascript:;" onclick="d('${selectValue}')"     data="${selectValue}">${selectName}</a>
           </s:iterator>
           </dd>
         </dl>
    </div>
  </div>
	<div class="clearfix w1002" id="dataInfo">
	</div>
</div> --%>



<div class="wrap mt20">
    	<div class="bd_detail clearfix item_list w950">
     <div class="screening">
         <div class="til"><span class="fl">筛选投资项目</span><span class="fr f16"><a href="rechargeInit.mht" target="_blank"><img src="images/list_pic01_03.png" width="17" height="16" class="mr5">立即充值</a></span></div>
         <dl class="clearfix">
           <dt>标的类型：</dt>
           <dd id="type"><a href="javascript:;" class="cur_one" data="0" onclick="a('0')">不限</a><a href="javascript:;" onclick="a('4')" data="4">实地考察</a><a href="javascript:;" onclick="a('5')" data="5">担保借款</a><a href="javascript:;" onclick="a('7')" data="7">债权转让</a>
           <!-- a href="javascript:;"   onclick="a('2')"  data="2"  >秒还借款</a><a href="javascript:;"  onclick="a('1')" data="1"  >净值借款</a> --></dd>
         </dl>
         <!-- dl>
           <dt>还款方式：</dt>
           <dd id="paymentMode">
           <a href="javascript:;" data="" class="cur_one"  onclick="b('')">不限</a><a href="javascript:;"  data="1"   onclick="b('1')">按月分期还款</a><a href="javascript:;" data="2"   onclick="b('2')">按月付息、到期还本</a><a href="javascript:;"  data="4"   onclick="b('4')">一次性还款</a>
           </dd>
         </dl>
         <dl>
           <dt>借款目的：</dt>
           <dd id="purpose"> <a href="javascript:;" data=""  class="cur_one"  onclick="c('')">不限</a>
           <a href="javascript:;" onclick="c('1')" data="1">长期使用</a>
           <a href="javascript:;" onclick="c('2')" data="2">资金周转</a>
           <a href="javascript:;" onclick="c('3')" data="3">短期周转</a>
           <a href="javascript:;" onclick="c('4')" data="4">创业借款</a>
           <a href="javascript:;" onclick="c('5')" data="5">其他借款</a>
           <a href="javascript:;" onclick="c('6')" data="6">普通借款</a>
           </dd>
         </dl>  -->
         <dl>
           <dt>项目状态：</dt>
           <dd id="proStatus">
                 <a href="javascript:;" class="cur_one" data="0" onclick="b('0')">不限</a>
                 <%-- <a href="javascript:;" onclick="b('1')" data="1">预告中</a> --%>
				 <a href="javascript:;" onclick="b('2')" data="2">投标中</a>
				 <%-- <a href="javascript:;" onclick="b('3')" data="3">已满标</a> --%>
				 <a href="javascript:;" onclick="b('4')" data="4">还款中</a>
				 <a href="javascript:;" onclick="b('5')" data="5">已还完 </a>
           </dd>
         </dl> 
         <dl>
           <dt>借款期限：</dt>
           <dd id="deadline"> <a href="javascript:;" data="0" class="cur_one" onclick="d('0')">不限</a>
           <a href="javascript:;" onclick="d('1')" data="1">1个月</a>
           <%-- <a href="javascript:;" onclick="d('2')" data="2">2个月</a> --%>
           <a href="javascript:;" onclick="d('3')" data="3">3个月</a>
           <a href="javascript:;" onclick="d('6')" data="6">6个月</a>
            <!--<a href="javascript:;" onclick="d('7')" data="7">6个月以上</a>
           <a href="javascript:;" onclick="d('12')" data="12">12个月</a>
           <a href="javascript:;" onclick="d('18')" data="18">18个月</a>
           <a href="javascript:;" onclick="d('24')" data="24">24个月</a> -->
           </dd>
         </dl>

</div>
    </div>
</div>




<div class="borrow_list" id="dataInfo">
     <div class="loadingImg w h300"></div>
</div>

<div class="clearfix"></div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script>
function timersTask(){
	if(item.length>0){
	    for(var i = 0;i<item.length;i++){
	        var time = item[i].getAttribute("_time");
	        SetRemainTime(time,i);
	    }
	   window.setTimeout("timersTask()", 1000)
	}
}

function SetRemainTime(time,i) {
    if (time > 0) {
        time = time - 1;
        var second = Math.floor(time % 60); // 计算秒     
        var minite = Math.floor((time / 60) % 60); //计算分 
        var hour = Math.floor((time / 3600) % 24); //计算小时 
        var day = Math.floor((time / 3600) / 24); //计算天 
        var times = day + "天" + hour + "时" + minite + "分" + second + "秒";
        item[i].innerHTML=times;
    } else {//剩余时间小于或等于0的时候，就停止间隔函数 
        item[i].setAttribute("class","btn01");
        item[i].innerHTML="立即投标";
        item[i].onclick = function(){checkTou(item[i].getAttribute("_id"),1)};
    }
    item[i].setAttribute("_time",time);
}
</script>

<script type="text/javascript">

var param ={};

function a(v){
	param["paramMap.type"] =  v==0?'':v;
	$("#type>a").removeClass("cur_one");
	$("#type>a[data="+v+"]").addClass("cur_one");
	param["pageBean.pageNum"]=1;
	initListInfo(param);
}
function b(v){
	param["paramMap.proStatus"]  = v==0?'':v;
	$("#proStatus>a").removeClass("cur_one");
    $("#proStatus>a[data="+v+"]").addClass("cur_one");
    param["pageBean.pageNum"]=1;
    initListInfo(param);
}
function c(v){
	param["paramMap.purpose"]  =  v==0?'':v;
	$("#purpose>a").removeClass("cur_one");
    $("#purpose>a[data="+v+"]").addClass("cur_one");
    param["pageBean.pageNum"]=1;
    initListInfo(param);
}
function d(v){
	param["paramMap.deadline"] =  v==0?'':v;
	$("#deadline>a").removeClass("cur_one");
    $("#deadline>a[data="+v+"]").addClass("cur_one");
    param["pageBean.pageNum"]=1;
    initListInfo(param);
}
var item;
$(document).ready(function(){
	    var ck_type = "${paramMap.type}";
	    var no = ck_type.split(',');
	    if(no != ''){
	       for(var i=0;i<no.length;i++){
	          $('#ck_mode_'+no[i]).attr('checked','checked');
	       }
	    }
	    initListInfo(param);
});

function initCallBack(data){
    $("#dataInfo").html(data);
    item = $("div[name='timers']");
    timersTask();
}

function initListInfo(param){
	param["paramMap.m"]=1;
	$.dispatchPost("financeList.mht",param,initCallBack);
}

function  checkTou(id,type){
	 var param = {};
	 param["id"] = id;
     $.dispatchPost('financeInvestInit.mht',param,function(data){
	   var callBack = data.msg;
	   if(callBack !=undefined){
	     alert(callBack);
	   }else{
		   if(type==2){
				var url = "subscribeinit.mht?borrowid="+id;
		     	 $.jBox("iframe:"+url, {
			    		title: "我要购买",
			    		width: 450,
			    		height: 450,
			    		buttons: {  }
			    		});
			}else{
				 window.location.href= 'financeInvestInit.mht?id='+id;
		   }
	   }
	 });
}
function closeMthod(){
	window.jBox.close();
	window.location.reload();
}

function clearComponentVal(){
        param = {};
        $('#titles').val('');
        $('#paymentMode').get(0).selectedIndex=0;
        $('#purpose').get(0).selectedIndex=0;
        $('#deadline').get(0).selectedIndex=0;
        $('#reward').get(0).selectedIndex=0;
        $('#arStart').get(0).selectedIndex=0;
}


		function rateNumJudge(){//验证利息计算输入数字是否正确
	 	   if($("#borrowSum").val()==""){
	 	      $("#show_error").html("&nbsp;&nbsp;投资金额不能为空");
	 	      $("#showIncome").html("");
	 	   }else 
	 	   if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#borrowSum").val())){
	 	       $("#show_error").html("&nbsp;&nbsp;请输入正确的投资金额进行计算");
	 	       $("#showIncome").html("");
	 	   }else 
	 	   if($("#yearRate").val()==""){
	 	      $("#show_error").html("&nbsp;&nbsp;年利率不能为空");
	 	      $("#showIncome").html("");
	 	   }else 
	 	   if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#yearRate").val())){
	 	      $("#show_error").html("&nbsp;&nbsp;请输入正确的年利率");
	 	      $("#showIncome").html("");
	 	   }else 
	 	   if($("#borrowTime").val()==""){
	 	       $("#show_error").html("&nbsp;&nbsp;投资期限不能为空");
	 	       $("#showIncome").html("");
	 	   }else 
	 	   if(!/^[0-9]*[1-9][0-9]*$/.test($("#borrowTime").val())){
		 	    $("#show_error").html("&nbsp;&nbsp;请输入正确投资期限");
		 	    $("#showIncome").html("");
	 	   }else 
	 	    if(!/^[0-9].*$/.test($("#bidReward").val())){
		 	      $("#show_error").html("&nbsp;&nbsp;请输入正确的投资奖励");
		 	      $("#showIncome").html("");
	 	   }else 
		 	if(!/^[0-9].*$/.test($("#bidRewardMoney").val())){
			 	      $("#show_error").html("&nbsp;&nbsp;请输入正确的现金奖励 ");
			 	      $("#showIncome").html("");
	 	    }else{
	 	      $("#show_error").html("");
	 	   }
	 	}
	 	
	 	function rateCalculate(){//利息计算
	 	    var str = rateNumJudge();
	 	    param["paramMap.borrowSum"] = $("#borrowSum").val();
	        param["paramMap.yearRate"] = $("#yearRate").val();
	        param["paramMap.borrowTime"] = $("#borrowTime").val();
	        param["paramMap.repayWay"] = $("#repayWay").get(0).selectedIndex;
	        param["paramMap.bidReward"] = $("#bidReward").val();
	        param["paramMap.bidRewardMoney"] = $("#bidRewardMoney").val();
	        
	        var _array = [];
	        
			if($("#show_error").text()!=""){
			   return;
			}
			$.dispatchPost("incomeCalculate.mht",param,function(data){
			   //只有一条记录
			   if(data == 1){
			     $("#show_error").html("请填写完整信息进行计算");
			     return;
			   }
			   _array.push("<table>");
			    for(var i = 0; i < data.length; i ++){
			    	data[i].income2year = data[i].income2year < 1 ? "0" + data[i].income2year : data[i].income2year;
			    	data[i].rateSum = data[i].rateSum < 1 ? "0" + data[i].rateSum : data[i].rateSum;
					_array.push("<tr><td style='padding-left:20px'><span>投标奖励：</span><span>"+data[i].reward+"元</span><br/>"
					+"<span>年化收益：</span><span>"+data[i].income2year+"%</span><br/>"
					+"<span>总计利息：</span><span>"+data[i].rateSum+"元</span><br/>"
					+"<span>每月还款：</span><span>"+data[i].monPay+"元</span><br/>"
					+"<span>总共收益：</span><span>"+data[i].allSum+"元</span><br/>"
					+"<span>总计收益：</span><span>"+data[i].netIncome+"元(扣除10%管理费)</span></td></tr>");
					/*_array.push("<p>投标奖励："+data[i].reward+"元</p><br /><br />"
					+"<p>年化收益："+data[i].income2year+"元</p><br /><br />"
					+"<p>总收益："+data[i].allSum+"元</p><br /><br />"
					+"<p>每月还款："+data[i].monPay+"元</p><br /><br />"
					+"<p>总计收益(扣除10%管理费)："+data[i].netIncome+"元</p>");*/
				}
				//_array.push("</table>");
				$("#showIncome").html(_array.join(""));
			});
	 	}

</script>
<script type="text/javascript">
		$(function(){
			$("span#tit").each(function(){
				if($(this).text().length > 6){
					$(this).text($(this).text().substring(0,8)+"..");
				}
			});
	
			var m = '${paramMap.m}';
			if(m == ''){
				m = 1;
			}
			$("#tab_"+m).addClass("on");
			$("#tab_1").click(function(){
			    window.location.href = "bidlist.mht?m=1"
			});
			$("#tab_2").click(function(){
			   window.location.href = "bidlist.mht?m=2"
			});
			$("#tab_3").click(function(){
               window.location.href = "bidlist.mht?m=3"
			});
			$("#tab_4").click(function(){
			   window.location.href = "bidlist.mht?m=4"
			});
			$("#tab_5").click(function(){
			   window.location.href = "bidlist.mht?m=5"
			});
			
			
			$("#search").click(function(){
				var chk_value = [];
				$('input[name="ck_mode"]:checked').each(function(){  
		             chk_value.push($(this).val());  
		        });
		        if(chk_value.length != 0){
		             $("#type").val(chk_value);
		             $("#m").val('1');             
		        }else{
		            $("#type").val("");
		        }
                $("#searchForm").submit();
			});
			$("#arEnd").change(function(){
			    var arStart = $('#arStart').val();
			    arStart = parseInt(arStart);
			    var arEnd = $(this).val();
			    arEnd = parseInt(arEnd);
			    if(arEnd < arStart){
			      alert('金额范围不能小于开始!');
			    }
			});
			$("#arStart").change(function(){
			    var arEnd = $('#arEnd').val();
			    arEnd = parseInt(arEnd);
			    var arStart = $(this).val();
			    arStart = parseInt(arStart);
			    if(arEnd < arStart){
			      alert('金额范围不能小于开始!');
			    }
			});
			
			$("#jumpPage").click(function(){
			     var curPage = $("#curPageText").val();
			    
				 if(isNaN(curPage)){
					alert("输入格式不正确!");
					return;
				 }
				 $("#pageNum").val(curPage);
   				var chk_value = [];
				$('input[name="ck_mode"]:checked').each(function(){  
		             chk_value.push($(this).val());  
		        });
		        if(chk_value.length != 0){
		             param['paramMap.m'] = '1';
		             $("#type").val(chk_value);                  
		        }else{
		            $("#type").val("");
		        }
                $("#searchForm").submit();
			});
		});
		
	
</script>

</body>
</html>
