<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
</style>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include> 
<div id="guide-step" style="margin-top:0px;">
</div>
<div class="nymain wrap">
  <div class="wdzh w950">
  <ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li><a href="home.mht">我的账户</a></li>
	  <li class="active">自动投标</li>
  </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
    <div class="r_main w750 fr bg-white">
      <div class="box">
      <div class="pub_tb bottom">
        <h3 style="border:none; "><a href="homeBorrowRecycleList.mht" style="color:#a6a6a6;">我的投资</a></h3>
        <h3>自动投标</h3>
        </div>
      <div class="boxmain2">
      <div class="biaoge2" style="margin-top:0px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="listab">
  <tr>
    <th colspan="2" align="left"> 设置我的自动投标工具</th>
    </tr>
  <tr>
    <td align="right"><p>自动投标状态：</p></td>
    <td><p id="statusText" style="text-align: left"><s:if test="%{automaticBidMap.bidStatus ==2}">开启状态</s:if><s:else>关闭状态</s:else></p></td>
  </tr>
  <tr>
    <td align="right"><p>您的账户余额：</p></td>
    <td>${automaticBidMap.usableSum}元
        <input type="hidden" value="${automaticBidMap.usableSum}" id="usableSum" name="usableSum" />
    </td>
  </tr>
  <tr>
    <td align="right"><p>最大投标金额：<span class="fred">*</span></p></td>
    <td>
    <s:if test="#request.automaticBidMap.bidAmount==null || #request.automaticBidMap.bidAmount==''">
    <input type="text" class="inp100x" id="bidAmount" maxlength="20"  value="100"  style="ime-mode:disabled;" ondragenter="return false" onpaste="return!clipboardData.getData('text').match(/[\u4e00-\u9fa5]/gi)" onkeypress="if(event.keyCode&lt;48||event.keyCode&gt;57||event.keyCode==8)event.returnValue=false;"/>
    </s:if>
    <s:else>
    <input type="text" class="inp100x" id="bidAmount" maxlength="20"  value="${automaticBidMap.bidAmount}"   style="ime-mode:disabled;" ondragenter="return false" onpaste="return!clipboardData.getData('text').match(/[\u4e00-\u9fa5]/gi)" onkeypress="if(event.keyCode&lt;48||event.keyCode&gt;57||event.keyCode==8)event.returnValue=false;"/>
    </s:else>
      元(只能输入整数)</td>
  </tr>
  <tr>
    <td align="right"><p>利率范围：<span class="fred">*</span></p></td>
    <td>
    <s:if test="#request.automaticBidMap.rateStart==null || #request.automaticBidMap.rateStart==''">
    <input type="text" class="inp100x" id="rateStart" maxlength="20"  value="10" />
    </s:if>
    <s:else>
    <input type="text" class="inp100x" id="rateStart" maxlength="20"  value="${automaticBidMap.rateStart}"/>
    </s:else>
      % --
      <s:if test="#request.automaticBidMap.rateEnd==null || #request.automaticBidMap.rateEnd==''">
    <input type="text" class="inp100x" id="rateEnd" maxlength="20"  value="20"/>
    </s:if>
    <s:else>
    <input type="text" class="inp100x" id="rateEnd" maxlength="20"  value="${automaticBidMap.rateEnd}"/>
    </s:else>
%</td>
  </tr>
  <tr>
    <td align="right"><p>借款期限：</p></td>
    <td>
    <s:if test="#request.automaticBidMap.deadlineStart==null || #request.automaticBidMap.deadlineStart==''">
   <s:select list="#{-1:'1天',-15:'15天',-25:'25天',1:'1个月',2:'2个月',3:'3个月',4:'4个月',5:'5个月',6:'6个月',7:'7个月',8:'8个月',9:'9个月',10:'10个月',11:'11个月',12:'12个月',18:'18个月',24:'24个月',30:'30个月',36:'36个月'}" id="deadlineStart"  cssClass="sel_70" listKey="key" listValue="value" name="automaticBidMap.deadlineStart"  value="1"></s:select>
    </s:if>
    <s:else>
    <s:select list="#{-1:'1天',-15:'15天',-25:'25天',1:'1个月',2:'2个月',3:'3个月',4:'4个月',5:'5个月',6:'6个月',7:'7个月',8:'8个月',9:'9个月',10:'10个月',11:'11个月',12:'12个月',18:'18个月',24:'24个月',30:'30个月',36:'36个月'}" id="deadlineStart"  cssClass="sel_70" listKey="key" listValue="value" name="automaticBidMap.deadlineStart"></s:select>
    </s:else>
       --
     <s:if test="#request.automaticBidMap.deadlineEnd==null || #request.automaticBidMap.deadlineEnd==''">
    <s:select list="#{-1:'1天',-15:'15天',-25:'25天',1:'1个月',2:'2个月',3:'3个月',4:'4个月',5:'5个月',6:'6个月',7:'7个月',8:'8个月',9:'9个月',10:'10个月',11:'11个月',12:'12个月',18:'18个月',24:'24个月',30:'30个月',36:'36个月'}" id="deadlineEnd"  cssClass="sel_70" listKey="key" listValue="value" name="automaticBidMap.deadlineEnd"  value="6"></s:select>
    </s:if>
    <s:else>
    <s:select list="#{-1:'1天',-15:'15天',-25:'25天',1:'1个月',2:'2个月',3:'3个月',4:'4个月',5:'5个月',6:'6个月',7:'7个月',8:'8个月',9:'9个月',10:'10个月',11:'11个月',12:'12个月',18:'18个月',24:'24个月',30:'30个月',36:'36个月'}" id="deadlineEnd"  cssClass="sel_70" listKey="key" listValue="value" name="automaticBidMap.deadlineEnd"></s:select>
    </s:else>
      
</td>
  </tr>
  <tr style="display: none">
    <td align="right"><p>信用等级范围：</p></td>
    <td>
    <s:select list="#{1:'HR',2:'E',3:'D',4:'C',5:'B',6:'A',7:'AA'}" id="creditStart"     cssClass="sel_70" listKey="key" listValue="value" value="1"></s:select>
    --
    <s:select list="#{1:'HR',2:'E',3:'D',4:'C',5:'B',6:'A',7:'AA'}" id="creditEnd"  cssClass="sel_70" listKey="key" listValue="value" name="7"></s:select>
    </td>
  </tr>
  <tr>
    <td align="right"><p>账户保留金额：<span class="fred">*</span></p></td>
    <td>
    <s:if test="#request.automaticBidMap.remandAmount==null || #request.automaticBidMap.remandAmount==''">
    <input type="text" class="inp100x" id="remandAmount" maxlength="20"  value="1"/>
    </s:if>
    <s:else>
    <input type="text" class="inp100x" id="remandAmount" maxlength="20"  value="${automaticBidMap.remandAmount}"/>
    </s:else>
元<i>&nbsp;(*账户保留余额不能为0元)</i></td>
  </tr>
  <tr>
    <td align="right"><p>借款类型：<span class="fred">*</span></p></td>
    <td>
    <s:if test="#request.checkList==null">
    <s:checkboxlist name="checkList.id" value="{3,4,5}" list="#{3:'信用借款',4:'实地考察借款',5:'机构担保借款',7:'债权转让标'}" ></s:checkboxlist>
    </s:if>
    <s:else>
    <s:checkboxlist name="checkList.id" value="checkList.{#this.id}" list="#{3:'信用借款',4:'实地考察借款',5:'机构担保借款',7:'债权转让标'}"  listKey="key" listValue="value"></s:checkboxlist>
    </s:else>
     
</td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top:5px;">
   <a href="javascript:void(0);" id="setbtn" class="bcbtn"><s:if test="%{automaticBidMap.bidStatus ==2}">关闭自动投标</s:if><s:else>
         开启自动投标</s:else></a>
    <!--<a href="javascript:void(0);" id="savebtn" class="bcbtn">保存设置</a>
    -->
    <div class=""><input type="button" value="保存设置" id="savebtn" class="bcbtn"/></div>
    </td>
  </tr>
    </table>
    </div>
    <p class="tips" style="margin-top:15px;text-align: left"><strong>自动投标工具说明</strong><br/>
    
1、贷款进入招标中十五分钟后，才会启动自动投标。<br/>

2、投标进度达到95%时停止自动投标。若投标后投标进度超过95%，则按照投标进度达到95%的金额向下取50的倍数金额值投标。<br/>

3、单笔投标金额若超过该标贷款总额的20%，则按照20%比例的金额向下取50的倍数金额值投标。<br/>

4、满足自动投标规则的金额小于设定的每次投标金额，也会进行自动投标。<br/>

5、贷款用户在获得贷款时会自动关闭自动投标，以避免借款被用作自动投标资金。<br/>

6、投标排序规则如下：<br/>

<span style="padding-left: 30px;">a）投标序列按照开启自动投标的时间先后进行排序。</span><br/>
<span style="padding-left: 30px;">b）每个用户每个标仅自动投标一次，投标后，排到队尾。</span><br/>
<span style="padding-left: 30px;">c）轮到用户投标时没有符合用户条件的标，也视为投标一次，重新排队。</span></p>
        </div>
</div>
    </div>
  </div>
</div>
<input type="hidden" id="s" value="${automaticBidMap.bidStatus}"/>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.cookie.js"></script>
<script>
$(function(){
    //displayDetail(1,2);
     
     $('#savebtn').click(function(){
         var chk_value =[];  
         $('input[name="checkList.id"]:checked').each(function(){  
             chk_value.push($(this).val());  
         });
         $("#savebtn").attr("disabled",true);
         param['paramMap.usableSum'] = $('#usableSum').val();
         param['paramMap.bidAmount'] = $('#bidAmount').val();
         param['paramMap.rateStart'] = $('#rateStart').val();
         param['paramMap.rateEnd'] = $("#rateEnd").val();
         param['paramMap.deadlineStart'] = $('#deadlineStart').val();
         param['paramMap.deadlineEnd'] = $('#deadlineEnd').val();
         param['paramMap.creditStart'] = $('#creditStart').val();
         param['paramMap.creditEnd'] = $('#creditEnd').val();
         param['paramMap.remandAmount'] = $('#remandAmount').val();
         param['paramMap.borrowWay']=''+chk_value;
         $.dispatchPost('automaticBidModify.mht',param,function(data){
                 $("#savebtn").attr("disabled",false);
           alert(data.msg);
         });
     });
     $('#setbtn').click(function(){
         var str = $('#s').val();
         param['paramMap.s']=str;
         $.dispatchPost('automaticBidSet.mht',param,function(data){
           if(data.msg == 1){
              alert('操作成功');
              window.location.href='automaticBidInit.mht';
              return false;
           }
           alert(data.msg);
         });
     });
});      
</script>
</body>
</html>