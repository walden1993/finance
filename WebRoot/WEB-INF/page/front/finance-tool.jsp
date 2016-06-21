<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<c:set value="计算器" scope="request" var="title"/>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link rel="stylesheet" href="css/new_inside.css" type="text/css" />
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	


<div class="ny_content clearfix">
  <div class="nlist_tab clearfix w950">
         <ul class="w950"><!-- setTab('one',1,2) -->
          <li id="one1" onclick="rateCal()"  class="hover" >利息计算器</li>
          <li style="border-left:1px solid #d1dfea;padding:0;"></li> 
        </ul>        
  </div>
  <div class="w950 contain_main clearfix fang">
   <div class="clearfix mt50" id="con_one_1">
   <form action="" method="get">
     <ul class="calc_list clearfix">
       <li>
         <div class="gird15">借款金额：</div>
         <div class="gird16">
            <span class="yuan">元</span>
            <input name="borrowSum" id="borrowSum" type="text" />
            <p class="gray1">投资金额只能是1的整数倍 </p>
            <p class="red" style="display:none;" id="">填写数字</p>
         </div>
       </li>
       <li>
         <div class="gird15">年利率： </div>
         <div class="gird16">
            <span class="yuan">%</span>
            <input name="yearRate" id="yearRate" type="text" />
            <p class="gray1">投资金额只能是1的整数倍 </p>
            <p class="red" style="display:none;" id="rateErr">填写数字</p>
         </div>
       </li>
       <li>
         <div class="gird15">借款期限：</div>
         <div class="gird16">
            <span class="yuan">月</span>
            <input name="borrowTime" id="borrowTime" type="text" />
            <p class="red" id="monthErr"></p>
         </div>
       </li>
       <li>
         <div class="gird15">还款方式：</div>
         <div class="gird16">
            <select id="repayWay" name="repayWay">
              <option>按月还款</option>
              <option>先息后本</option>
              <option>一次还本付息</option>
            </select>
            <p class="red" id="repayErr"></p>
         </div>
         
       </li>
       <li><span class="red" id="show_error" align="left"></span></li>
       
     </ul>
     
     <!-- span id="showPayTable"></span> -->
     <div class="text-center mb20"><a class="button w140" href="#" onclick="rateCalculate();">马上计算</a></div>
     <ul class="calc_totle clearfix">
        <li>每个月将偿还：<span id="aa">0.00</span></li>
        <li>月利率：<span id="bb">0%</span></li>
        <li>还款本息总额：<span id="cc">0元</span></li>
     </ul>
     <div class="clearfix tab_f" id="showPayTable">
       
     </div>
    </form>
   </div>
   <div class="clearfix mt50" id="con_one_2" style="display:block;" >

   </div>
   <div class="clearfix mt50" id="con_one_3" style="display:none;" >
<div class="gjxtab">
	    <table width="90%" border="0" cellspacing="0" cellpadding="0">
		  	<tr>
		    	<td>请输入手机号：
		      <input type="text" class="inp280" id="phoneNum" /> <a href="javascript:void(0);" class="chaxun" onclick="javascript:queryPhone();">查询</a></td>
		    </tr>
		</table>
	    </div>
	    <p class="gjjg" >查询结果：
		    <strong>
		    <span id="queryPhoneInfo">暂无查询信息</span></strong></p>
		    
   </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
    <jsp:include page="/include/footer.jsp"></jsp:include>
<script>
$(function(){
    //样式选中
     $("#licai_hover").attr('class','nav_first');
    <%-- $("#licai_hover div").removeClass('none');
     $('.tabmain').find('li').click(function(){
        $('.tabmain').find('li').removeClass('on');
        $(this).addClass('on');
     });--%>
     
});          

    
</script>
    <script type="text/javascript">
    function rateCal(){
       $("#con_one_1").show();
 	   $("#con_one_2").hide();
 	   var aitem = $("li[id^='one']");
 	   aitem.removeClass("hover");
	   $("#one1").addClass("hover");
    }
    
    
    
    function initList(){
       $.post("queryAboutInfo.mht?msg=15", function(data) {
    	   $("#con_one_1").hide();
    	   $("#con_one_2").show();
           $("#con_one_2").html(data);
           var aitem = $("li[id^='one']");
           aitem.removeClass("hover");
     	   $("#one2").addClass("hover");
        });
    }
    
        function queryPhone(){//手机归属地查询
            var phoneNum = $("#phoneNum").val();
            var isPhone = new Object();
            //手机号正则表达式(11位) 
            isPhone=/^((13[0-9])|(15[^4,\D])|(18[0,5-9]))\d{8}$/;
            if(phoneNum == ""){
              $("#queryPhoneInfo").html("请先输入11位手机号码，否则不能查询！");
              return;
            }
            if(!isPhone.test(phoneNum)){
                $("#queryPhoneInfo").html("请输入正确的手机号码进行查询！");
                  return;
            }
            param["paramMap.phoneNum"] = phoneNum;
            $.dispatchPost("queryPhoneInfo.mht",param,queryPhoneBack);
        }
        
        function queryPhoneBack(data){
            $("#queryPhoneInfo").html(data);
            //alert(data);
            /*var array=data.split("|");
            if(array[1]=="true"){//新窗口打开查询结果
              $("#queryPhoneInfo").html("");
              window.open(array[0]);
            }else{//显示错误信息
               $("#queryPhoneInfo").html(array[0]);
            }*/
            //window.location = $("#queryPhoneInfo").text();
            //document.getElementById("myPhone").href=$("#queryPhoneInfo").text();
            
        }
        
        function queryIP(){//IP地址查询
            var ipAddress = $("#ipAddress").val();
            var isIPAddress = new Object();
            //IP地址正则表达式
            isIPAddress=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
            if(ipAddress == ""){
              $("#queryIPInfo").html("请先输入一个完整的IP地址进行查询！");
              return;
            }
            if(!isIPAddress.test(ipAddress)){
                $("#queryIPInfo").html("请输入正确的IP地址进行查询！");
                  return;
            }
            param["paramMap.ipAddress"] = ipAddress;
            $.dispatchPost("queryIPInfo.mht",param,queryIPBack);
        }
        
        function queryIPBack(data){
            $("#queryIPInfo").html(data);
            /*var array=data.split("|");
            if(array[1]=="true"){//新窗口打开查询结果
              $("#queryIPInfo").html("");
              window.open(array[0]);
            }else{//显示错误信息
               $("#queryIPInfo").html(array[0]);
            }*/
        }
        
        function rateNumJudge(){//验证利息计算输入数字是否正确
           if($("#borrowSum").val()==""){
              $("#show_error").html("借款金额不能为空");
              $("#showPayTable").html("");
              return;
           }else if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#borrowSum").val())){
               $("#show_error").html("请输入正确的数字进行计算");
               $("#showPayTable").html("");
               return;
           }else if($("#yearRate").val()==""){
              $("#show_error").html("年利率不能为空");
              $("#showPayTable").html("");
              return;
           }else if(!/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test($("#yearRate").val())){
              $("#show_error").html("请输入正确的年利率");
              $("#showPayTable").html("");
              return;
           }
           if($("#manual").attr("checked")){
              if($("#borrowTime").val()==""){
                   $("#show_error").html("借款期限不能为空");
                   $("#showPayTable").html("");
                   return;
               }else if(isNaN($("#borrowTime").val())){
                    $("#show_error").html("请输入正确借款期限");
                    $("#showPayTable").html("");
                     return;
               }else if($("#borrowTime").val() > 25){
                  $("#show_error").html("天标时间不能超过25天");
                    $("#showPayTable").html("");
                     return;
               }
           }else{
              if($("#borrowTime").val()==""){
                   $("#show_error").html("借款期限不能为空");
                   $("#showPayTable").html("");
                   return;
               }else if(!/^[0-9]*[1-9][0-9]*$/.test($("#borrowTime").val())){
                    $("#show_error").html("请输入正确借款期限");
                    $("#showPayTable").html("");
                     return;
               }
           }
           
           $("#show_error").html("");
        }
        function rateCalculate(){//利息计算
            rateNumJudge();
            
            var aa = 0;
            var bb = 0;
            var cc = 0;
            if($("#show_error").text()!=""){
               return;
            }
            if($("#manual").attr("checked")){//天标计算
                var _array = [];
                _array.push("<table>");
                _array.push("<tr><th style='width: 150px;'>期限</th><th style='width: 150px;'>所还本息</th><th style='width: 150px;'>所还本金</th>"
                +"<th style='width: 150px;'>所还利息</th><th style='width: 150px;'>总还款额</th></tr>");
                param["paramMap.borrowSum"] = $("#borrowSum").val();
                param["paramMap.yearRate"] = $("#yearRate").val();
                param["paramMap.borrowTime"] = $("#borrowTime").val();
                param["paramMap.repayWay"] = $("#repayWay").get(0).selectedIndex;
            
            
                $.dispatchPost("toolsCalculateDay.mht",param,function(data){
                   if(data == 1){
                      $("#show_error").html("请填写正确信息");
                      $("#showPayTable").html("");
                      return;
                   }else if(data == 2){
                      $("#show_error").html("年利率太低，请重新输入");
                      $("#showPayTable").html("");
                      return;
                   }
                     aa = data.map.monForRateA;
                     bb = data.map.monRate;
                     cc = data.map.allPay;
                         
                        _array.push("<tr><td align='center'>"+data.map.mon+"天</td><td align='center'>"+data.map.monForRateA+"</td>"
                        +"<td align='center'>"+data.map.monForA+"</td>"
                        +"<td align='center'>"+data.map.monForRate+"</td><td align='center'>"+cc+"</td></tr>");
                    
                    _array.push("</table>");
                    $("#showPayTable").html(_array.join(""));
                    $("#result").show();
                    $("#totalResult").hide();
                    $("#dd").html(cc);
                });
            
            }else{
            
                var _array = [];
                _array.push("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='tc'>");
                _array.push("<tr class='leve_titbj'><td>期数</td><td>月还本息</td><td>月还本金</td>"
                +"<td>月还利息</td><td>本息余额</td></tr>");
                param["paramMap.borrowSum"] = $("#borrowSum").val();
                param["paramMap.yearRate"] = $("#yearRate").val();
                param["paramMap.borrowTime"] = $("#borrowTime").val();
                param["paramMap.repayWay"] = $("#repayWay").get(0).selectedIndex;
            
            
                $.dispatchPost("frontfinanceTools.mht",param,function(data){
                   if(data == 1){
                      $("#show_error").html("请填写正确信息");
                      $("#showPayTable").html("");
                      return;
                   }else if(data == 2){
                      $("#show_error").html("年利率太低，请重新输入");
                      $("#showPayTable").html("");
                      return;
                   }
                    for(var i = 0; i < data.length; i ++){
                      if(i == 0){
                         aa = data[i].monForRateA;
                         bb = data[i].monRate;
                         cc = data[i].allPay;
                      }//data[i].mon
                        _array.push("<tr><td align='center'>"+(i+1)+"</td><td align='center'>"+data[i].monForRateA+"</td>"
                        +"<td align='center'>"+data[i].monForA+"</td>"
                        +"<td align='center'>"+data[i].monForRate+"</td><td align='center'>"+data[i].rateARemain+"</td></tr>");
                    }
                    _array.push("</table>");
                    $("#showPayTable").html(_array.join(""));
                    $("#result").hide();
                    $("#totalResult").show();
                    $("#aa").html(aa);
                    $("#bb").html(bb+"%");
                    $("#cc").html(cc);
                });
            }
        }
        
        
        function changeStatus(){
            if($("#manual").attr("checked")){
               $("#timeType").html("天");
               $("#borrowTime").attr('value','');
               $("#repayWay").attr('disabled','disabled');
            }else{
              $("#timeType").html("月");
              $("#repayWay").removeAttr('disabled');
            }
        }
        
        /*function rateCalculate(){//利息计算
            param["paramMap.borrowSum"] = $("#borrowSum").val();
            param["paramMap.yearRate"] = $("#yearRate").val();
            param["paramMap.borrowTime"] = $("#borrowTime").val();
            param["paramMap.repayWay"] = $("#repayWay").get(0).selectedIndex;
            $.dispatchPost("frontfinanceTools.mht",param,function(data){
               //alert(data);
                var array=data.split("|");
                $("#aa").html(array[0]);
                $("#bb").html(array[1]+"%");
                $("#cc").html(array[2]);
            });
        }*/
        
        /*function initCallBack(data){
            alert(data);
            var array=data.split("|");
            $("#aa").html(array[0]);
            $("#bb").html(array[1]);
            $("#cc").html(array[2]);
        }*/
        
        $(function(){
            $('.tabmain').find('li').click(function(){
            $('.tabmain').find('li').removeClass('on');
            $(this).addClass('on');
            $('.lcmain_l').children('div').hide();
            $('.lcmain_l').children('div').eq($(this).index()).show();
            })
        });
        
        //加载借款协议范本
        /*function queryBrrowClause(){
            //alert($("#borrowClause").text());
            param["paramMap.title"] = $("#borrowClause").text();
            $.dispatchPost("queryBorrowClause.mht",param,function(data){
               //alert(data);
                var array=data.split("|");
                $("#borrowClauseTitle").html(array[0]);
                $("#borrowClauseContent").html(array[1]);
            });
        }*/
    </script>
</body>
</html>
