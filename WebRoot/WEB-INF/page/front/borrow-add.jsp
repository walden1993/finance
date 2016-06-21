<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
	    <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<form id="form" action="addBorrow.mht" method="post">
<div class="nymain wrap">
<div class="bigbox w950 bg-white">
<div class="sqdk">
	<div class="l-nav fl w030">
    <ul>
    <li>
    	<s:if test="#session.user.userType==2">
    		<a href="queryCompayData.mht?from=1"><span>step1 </span> 基本资料</a>
    	</s:if>
    	<s:else>
    		<a href="querBaseData.mht?from=1"><span>step1 </span> 基本资料</a>
    	</s:else>
    </li>
    <li>
    	<s:if test="#session.user.userType==2">
    		<a href="companyPictureDate.mht?from=1"><span>step2 </span> 上传资料</a>
    	</s:if>
    	<s:else>
    		<a href="userPassData.mht?from=1"><span>step2 </span> 上传资料</a>
    	</s:else>
   	</li>
    <li class="on last">
    	<s:if test="#session.user.userType==2">
    		<a href="javascript:void(0);"><span>step3 </span> 发布融资</a>
    	</s:if>
    	<s:else>
    		<a href="javascript:void(0);"><span>step3 </span> 发布贷款</a>
    	</s:else>
    </li>
    </ul>
    </div>
    <div class="r-main w070 fr">
    <div class="til01">
	<ul id="ul"><li class="on">发布贷款</li></ul>
	<span class="fred" style="color: red;font-size: 12px; padding-left: 80px;line-height: 50px;"><s:fielderror fieldName="enough"></s:fielderror></span>
	</div>
    <div class="rmainbox">
   
    <p class="tips"><span class="fred">*</span> 为必填项，所有资料均会严格保密。 </p>
    <div class="tab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="2" align="left">借款基本信息</th>
    </tr>
  <tr>
    <td align="right">借款标题：<span class="fred">*</span></td>
    <td><input name="paramMap.title" type="text" class="input w400"  value="${paramMap.title}"/>
    <span class="fred"><s:fielderror fieldName="paramMap['title']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <td align="right">借款图片：<span class="fred">*</span><input type="hidden" id="imgPath"  name="paramMap.imgPath" value="${paramMap.imgPath}"/>
    </td>
    <td>
    <s:if test="#session.user.userType==2">
    		<input type="hidden" id="personalImg" value="${user.orgheadimg}"/>
    	</s:if>
    	<s:else>
    		<input type="hidden" id="personalImg" value="${user.personalHead}"/>
    	</s:else>
        <label><input type="radio" name="radio" id="r_1" checked="checked" value="1" />上传借款图片</label> 
        <label><input type="radio" name="radio" id="r_2" value="2" />使用用户头像 </label>
        <label><input type="radio" name="radio" id="r_3" value="3" />使用系统头像</label>
        <span class="fred"><s:fielderror fieldName="paramMap['imgPath']"></s:fielderror></span>
        <input type="hidden" id="radioval" name="paramMap.radioval" value="${paramMap.radioval}"/>
        </td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td>
    <div id="tab_1"><a href="javascript:void(0);" id="btn_personalHead" class="scbtn">上传图片</a></div>
    <div id="tab_2" style="display:none;"></div>
    <div id="tab_3" style="display:none;">
    <table id="sysimg" style="width:400px;">
        <tr>
            <td class="tx">
            <s:iterator var="sysImage" value="sysImageList">
              <span><img src="${sysImage.selectName}" width="30" height="30"/></span>
            </s:iterator>
            </td>
        </tr>
    </table>
    </div>
    </td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
     <td class="tx"><img id="img" src="${headImg}" width="62" height="62"/></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td class="tishi">（推荐使用您的生活近照，或其他与借款用途相关的图片，<br />
      有助增加借款成功几率。严禁使用他人照片） </td>
  </tr>
  <tr>
    <td align="right">借款标的：</td>
    <td><span id="typeName" class="fred">${session.typeName}</span></td>
  </tr>
  <tr>
  <s:if test="#session.t==5 || #session.t==7">
    <td align="right">担保机构：<span class="fred">*</span></td>
    <td>
    <s:select list="borrowAgentList" id="agent" name="paramMap.agent" cssClass="sel_140" listKey="id" listValue="selectName"  headerKey=""  headerValue="--请选择--"></s:select>
    <span class="fred"><s:fielderror fieldName="paramMap['agent']"></s:fielderror></span>
    </td>
  </tr>
   <tr>
    <td align="right">担保方式：<span class="fred">*</span></td>
    <td>
    <s:select list="borrowAgentWayList" id="agentWay" name="paramMap.agentWay" cssClass="sel_140" listKey="id" listValue="selectName"  headerKey=""  headerValue="--请选择--"></s:select>
    <span class="fred"><s:fielderror fieldName="paramMap['agentWay']"></s:fielderror></span>
    </td>
  </tr>
  </s:if>
  <s:if test="#session.t==7">
    <td align="right">借款机构/借款人：<span class="fred">*</span></td>
    <td><input type="text" id="transfername" name="paramMap.transfername" class="input w140" value="${paramMap.transfername}"/><span class="fred"><s:fielderror fieldName="paramMap['transfername']"></s:fielderror></span></td>
  </tr>
   <tr>
    <td align="right">组织代码/身份证：<span class="fred">*</span></td>
    <td><input type="text" id="transfercode" name="paramMap.transfercode" class="input w140" value="${paramMap.transfercode}"/><span class="fred"><s:fielderror fieldName="paramMap['transfercode']"></s:fielderror></span></td>
  </tr>
  </s:if>
  <tr>
    <td align="right">借款目的：<span class="fred">*</span></td>
    <td class="tishi">
    <s:select list="borrowPurposeList" id="purpose" name="paramMap.purpose" cssClass="sel_140" listKey="selectValue" listValue="selectName"  headerKey="" headerValue="--请选择--"></s:select>
    <span class="fred"><s:fielderror fieldName="paramMap['purpose']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <td align="right">借款期限：<span class="fred">*</span></td>
    <td >
    <s:select list="borrowDeadlineMonthList" id="deadLine" name="paramMap.deadLine" cssClass="sel_140" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select>
    <s:select list="borrowDeadlineDayList" id="deadDay" cssStyle="display:none;" name="paramMap.deadDay" cssClass="sel_140" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select>
    <span class="fred"><s:fielderror fieldName="paramMap['deadLine']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
     <td></td>
     <td>
    <label><s:checkbox name="paramMap.daythe" id="daythe"/>&nbsp;置为天标</label>     
     </td>
  </tr>
  <tr>
    <td align="right">还款方式：<span class="fred">*</span></td>
    <td >
    <s:select id="paymentMode" name="paramMap.paymentMode" list="borrowRepayWayList" cssClass="sel_140" listKey="id" listValue="name" headerKey="" headerValue="--请选择--"></s:select>
    <span class="fred"><s:fielderror fieldName="paramMap['paymentMode']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <td align="right">借款总额：<span class="fred">*</span></td>
    <td><input type="text" id="amount" name="paramMap.amount" class="input w140" value="${paramMap.amount}"/><span class="fred"><s:fielderror fieldName="paramMap['amount']"></s:fielderror></span></td>
  </tr>
  <tr>
    <td align="right">年利率：<span class="fred">*</span></td>
    <td><input type="text" name="paramMap.annualRate" maxlength="5" value="${paramMap.annualRate}" class="input w140" />%<span class="fred"><s:fielderror fieldName="paramMap['annualRate']"></s:fielderror></span></td>
  </tr>
  <s:if test="#request.subscribeStatus!=1">
  <tr>
    <td align="right">最低投标金额：<span class="fred">*</span></td>
    <td>
    <s:select list="borrowMinAmountList" name="paramMap.minTenderedSum" cssClass="sel_140" listKey="key" listValue="value"   ></s:select>
    <span class="fred"><s:fielderror fieldName="paramMap['minTenderedSum']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <td align="right">最多投标金额：</td>
    <td>
    <s:select list="borrowMaxAmountList" name="paramMap.maxTenderedSum" cssClass="sel_140" listKey="key" listValue="value"  headerKey="" headerValue="没有限制" ></s:select>
    </td>
  </tr>
  </s:if>
  <s:else>
  <td align="right">最小认购单位：<span class="fred">*</span></td>
    <td>
    <s:textfield name="paramMap.subscribe"  cssClass="inp100x"/>元
    <span class="fred"><s:fielderror fieldName="paramMap['subscribe']"></s:fielderror></span>
    	<input name="subscribeStatus" type="hidden" id="subscribeStatus"  value="${subscribeStatus}"/>
  
    </td>
  </s:else>
  <tr>
    <td align="right">筹标期限：<span class="fred">*</span></td>
    <td>
    <input type="hidden" name="validateDay" value="${validateDay }" />
    <s:if test="#request.validateDay==1">
    	<input type="hidden" name="paramMap.raiseTerm" value="0" />无限制
    </s:if>
    <s:else>
    	<s:select list="borrowRaiseTermList" id="raiseTerm" name="paramMap.raiseTerm" cssClass="sel_140" listKey="key" listValue="value"   headerKey="" headerValue="--请选择--"></s:select>
    	<span class="fred"><s:fielderror fieldName="paramMap['raiseTerm']"></s:fielderror></span>
    </s:else>
    
    </td>
  </tr>
  <tr>
    <td align="right">借款详情：</td>
    <td  style="line-height:normal"><textarea name="paramMap.detail"   id="detail"  style="width: 455px; height: 100px;" >${paramMap.detail}</textarea></td>
  </tr>
  <tr>
     <td>&nbsp;</td>
     <td><span class="fred"><s:fielderror fieldName="paramMap['detail']"></s:fielderror></span>
     
     
     
     </td>
  </tr>
  <s:if test="#request.award_status==1">
  <tr>
    <th colspan="2" align="left"> 投标奖励</th>
    </tr>
  <tr>
    <td align="right">&nbsp;<input type="hidden" name="award_status" value="${award_status }" /><input type="hidden" id="excitation" name="paramMap.excitationType" value="${paramMap.excitationType}"/></td>
    <td><input type="radio" name="excitationType" checked="checked" id="radio_1" value="1" />
      不设置奖励</td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td><input type="radio" name="excitationType" id="radio_2" value="2" />
      固定总额按投标比例分配奖励
      <input type="text" id="sum" name="paramMap.sum" value="${paramMap.sum}" class="input w140 gray" disabled="disabled"/>
      元<span class="fred"><s:fielderror fieldName="paramMap['sum']"></s:fielderror></span></td>
  </tr>
  <tr>
    <td align="right">&nbsp;<input type="hidden" id="excitationMode" name="paramMap.excitationMode" value="${paramMap.excitationMode}"/></td>
    <td><input type="radio" name="excitationType" id="radio_3" value="3" />
      借款总额百分比分配奖励
      <input type="text" id="sumRate" name="paramMap.sumRate" maxlength="20" value="${paramMap.sumRate}" class="input w140 gray" disabled="disabled"/>
      %<span class="fred"><s:fielderror fieldName="paramMap['sumRate']"></s:fielderror></span></td>
  </tr>
  </s:if>
  <s:if test="#request.password_status == 1">
  <tr>
    <th colspan="2" align="left"> 设置投标密码</th>
    </tr>
  <tr>
    <td align="right">&nbsp;<input type="hidden" name="password_status" value="${password_status }" /></td>
    <td>投标密码：
	    <input type="password" id="investPWD" value="${paramMap.investPWD }" disabled="disabled" name="paramMap.investPWD" maxlength="20" class="input w140" />
	    &nbsp;
	    <label><s:checkbox id="hasPWD" name="paramMap.hasPWD" />&nbsp;有密码</label>
   </td>
  </tr>
  <tr>
	  <td>
	  <span class="fred"><s:fielderror fieldName="paramMap['investPWD']"></s:fielderror>
	  </span>
	  </td>
  </tr>
  </s:if>
  <tr>
    <th colspan="2" align="left"> 提交借款信息</th>
    </tr>
  <tr>
    <td align="right"> 验证码：</td>
    <td><input type="text" class="input w140" name="paramMap.code" id="code"/>
		 <img src="" title="点击更换验证码" style="cursor: pointer;"
		 	  id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
    <span class="fred"><s:fielderror fieldName="paramMap['code']"></s:fielderror></span>
    </td>
  </tr>
  <tr>
    <td align="center" colspan="2"><span class="fred"><s:fielderror fieldName="paramMap['allError']"></s:fielderror></span></td>
  	</tr>
    <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top:20px;"><a id="bcbtn"  class="button w140">提交发布</a></td>
  </tr>
    </table>
    </div>
    </div>
    </div>
  </div>
  </div>
</div>
</form>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script src="kindeditor/kindeditor-min.js" type="text/javascript"></script>
<script charset="utf-8" src="kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="kindeditor/plugins/code/prettify.js"></script>
<script>
//编辑框
    var editor_content;
$(function(){
    KindEditor.ready(function(K) {
        editor_content = K.create('textarea[name="paramMap.detail"]', {
            cssPath : 'kindeditor/plugins/code/prettify.css',
            uploadJson : 'kindEditorUpload.mht',
            fileManagerJson : 'kindEditorManager.mht',
            allowFileManager : true
        });
    });
})

var flag = true;
$(document).ready(function(){
 var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);
	 
	 var img='${paramMap.imgPath}';
	 if(img=="" ){
		img = "images/default-img.jpg";
	}
	 $('#imgPath').val(img);
	 $("#img").attr("src",img);
     //$('#imgPath').val('${paramMap.imgPath}');
    // $("#img").attr("src",'${paramMap.imgPath}');
     $('#purpose').val('${paramMap.purpose}');
     $('#deadLine').val('${paramMap.deadLine}');
     $('#paymentMode').val('${paramMap.paymentMode}');
     $('#raiseTerm').val('${paramMap.raiseTerm}');
     $('#excitation').val('${paramMap.excitationType}');
     $('#excitationMode').val('${paramMap.excitationMode}');
     $('#radioval').val('${paramMap.radioval}');
     var excitation = $('#excitation').val();
     var mode = $('#excitationMode').val();
     var radioval = $('#radioval').val();
     if(radioval !=''){
          $('#r_'+radioval).attr('checked','true');
          if(radioval ==1){
             $('#tab_1').css('display','block');
          }
          if(radioval ==2){
             $('#tab_2').css('display','block');
             $('#tab_1').css('display','none');
          }
          if(radioval ==3){
             $('#tab_3').css('display','block');
             $('#tab_1').css('display','none');
          }
     }
     if(excitation != ''){
        $('#radio_'+excitation).attr('checked','true');
        if(excitation == 2){
	       $('#sum').removeClass('gray');
	       $('#sum').removeAttr('disabled');
	       $('#sumRate').addClass('gray');
	       $('#sumRate').attr('disabled');
	       $('#sumRate').val('');
	    }else if(excitation == 3){
	       $('#sumRate').removeClass('gray');
	       $('#sumRate').removeAttr('disabled');
	       $('#sum').addClass('gray');
	       $('#sum').attr('disabled');
	       $('#sum').val('');
	    }
     }else{
        $('#excitation').val('1');
     }
     if(mode == '2'){
        $('#mode').attr('checked','true');
     }else{
        $('#excitationMode').val("1");
     }
});
var paymentModeHtml ;
$(function(){
   //样式选中
   paymentModeHtml = $("#paymentMode").html();
	 $('input[name="excitationType"]').click(function(){
	    if($(this).val() == 2){
	       $('#sum').removeClass('gray');
	       $('#sum').removeAttr('disabled',true);
	       $('#sumRate').addClass('gray');
	       $('#sumRate').attr('disabled',true);
	       $('#sumRate').val('');
	    }else if($(this).val() == 3){
	       $('#sumRate').removeClass('gray');
	       $('#sumRate').removeAttr('disabled',true);
	       $('#sum').addClass('gray');
	       $('#sum').attr('disabled',true);
	       $('#sum').val('');
	    }else{
	       $('#sumRate').addClass('gray');
	       $('#sumRate').attr('disabled',true);
	       $('#sumRate').val('');
	       $('#sum').addClass('gray');
	       $('#sum').attr('disabled',true);
	       $('#sum').val('');
	    }
	    $('#excitation').val($(this).val());
	 });
	 $('#mode').click(function(){
	    var check = $(this).attr('checked');
	    if(check == 'checked'){
	        $('#excitationMode').val('2');	    
	    }else{
	        $('#excitationMode').val('1');
	    }
	 });
	 $('#hasPWD').click(function(){
	    var hasPWD = $('#hasPWD').attr('checked');
        if(hasPWD =='checked'){
          $('#investPWD').attr('disabled',false);
        }else{
          $('#investPWD').attr('disabled',true);
        }
        $('#investPWD').val('');
	  });
	  
	 $('#daythe').click(function(){
	      showdays();
	 });
	 $('#bcbtn').click(function(){
		 $("#detail").val(editor_content.html());
		 if($("#subscribeStatus").val()!=1){
	       var arStart = $('#paramMap_minTenderedSum').val();
	       var amount = $('#amount').val();
		   arStart = parseFloat(arStart);
		   amount = parseFloat(amount);
		   var arEnd = $('#paramMap_maxTenderedSum').val();
		   arEnd = parseFloat(arEnd);
		   if(arStart > arStart){
			   alert('最多投标金额不能小于最低投标金额!');
			   $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
		      return false;
		   }
		   if(arStart > amount){
			   alert('最低投标金额不能超过借款金额!');
			   $('#paramMap_minTenderedSum')[0].selectedIndex = 0;
			   return false;
		   }
		   if(arEnd > amount){
			   alert('最多投标金额不能超过借款金额!');
			   $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
			   return false;
		   }
		 }
		 //  alert("您申请的借款已经提交管理人员进行审核，谢谢！")
		   if(flag){
	           $('#form').submit();
	           flag = false;
		   }
	 });
	 $("#paramMap_maxTenderedSum").change(function(){
	       var arStart = $('#paramMap_minTenderedSum').val();
		   arStart = parseFloat(arStart);
		   var arEnd = $(this).val();
		   arEnd = parseFloat(arEnd);
		   if(arEnd < arStart){
			   alert('最多投标金额不能小于最低投标金额!');
			   $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
		   }
	 });
	 $("#paramMap_minTenderedSum").change(function(){
	       $('#paramMap_maxTenderedSum')[0].selectedIndex = 0;
	 });
	 //上传图片
	 $("#btn_personalHead").click(function(){
			var dir = getDirNum();
			var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':1.0,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
			json = encodeURIComponent(json);
			//window.showModalDialog("uploadFileAction.mht?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
			window.open('uploadFileAction.mht?obj='+json,'newwindow','width=500,height=400,left=400,top=150,resizable=yes');
			var headImgPath = $("#img").attr("src");
			if(headImgPath ==""){
				alert("上传失败！");	
			}
	  });
	  $('#sysimg img').click(function(){
	      $('#imgPath').val($(this).attr('src'));
	      $('#img').attr('src',$(this).attr('src'));
	  });
	  $('#r_1').click(function(){
	      $('#tab_1').css('display','block');
	      $('#tab_2').css('display','none');
	      $('#tab_3').css('display','none');
	      $("#img").css('display','block');
	      $('#radioval').val('1');
	      img = "images/default-img.jpg";
	      $("#img").attr("src",img);
	  });
	 

 
	  $('#r_2').click(function(){
	      var personalImg=$('#personalImg').val();
	      $('#tab_1').css('display','none');
	      $('#tab_2').css('display','block');
	      $('#tab_3').css('display','none');
	      $("#img").css('display','block');
	      $('#radioval').val('2');
	      $('#img').attr('src',personalImg);
	      $('#imgPath').val(personalImg);
	  });
	  $('#r_3').click(function(){
	      $('#tab_1').css('display','none');
	      $('#tab_2').css('display','none');
	      $('#tab_3').css('display','block');
	      $("#img").css('display','block');
	      $('#radioval').val('3');
	     // img = "images/default-img.jpg";
	      //$("#img").attr("src",img);
	  });
	  $('#paymentMode').change(function(){
	      var check = $('#daythe').attr('checked');
	      if(check == true){
	         $('#paymentMode').get(0).selectedIndex = 1;
	      }
	  });
	  
	 
	  
      switchCode();
      showdays();
      
      //add by houli 进入页面发布借款的时候给用户提示信息
    var msg = '${request.msg}';
    var ulimit = '${request.usableCreditLimit}';
    var limit = '${request.creditLimit}';
    if(msg != null && msg !=""){
       alert("   您的信用额度是"+limit+"，可用借款额度是"+ulimit+"。 \n"+msg);
    }
    if($("#investPWD").val()!=''){
    	  $('#investPWD').attr('disabled',false);
    	  $('#hasPWD').attr('checked',true);
     }
//--------end
});
function switchCode(){
	    var timenow = new Date();
	    $('#codeNum').attr('src','admin/imageCode.mht?pageId=borrow&d='+timenow);
};			
	     
function showdays(){
         
          var check = $('#daythe').attr('checked');
	      if(check == 'checked'){
	        $('#deadLine').css('display','none');
	        $('#deadDay').css('display','block');
	        $('#paymentMode').attr('disabled',true);
	       // $('#paymentMode').get(0).selectedIndex = 1;
	         $("#paymentMode").html("<option value='1'>到期还本付息</option>");
	      }else{
	        $('#paymentMode').attr('disabled',false);
	        $('#paymentMode').get(0).selectedIndex = 0;
	        $('#deadLine').css('display','block');
	        $('#deadDay').css('display','none');
	        $("#paymentMode").html("");
	        $("#paymentMode").html(paymentModeHtml);
	      }
}

</script>
<script>
function uploadCall(basepath,fileName,cp){
	if(cp == "img"){
		var path = "upload/"+basepath+"/"+fileName;
		$("#img").attr("src",path);
		$("#setImg").attr("src",path);
		$("#imgPath").val(path);
	}
}
function getDirNum(){
	var date = new Date();
	var m = date.getMonth()+1;
	var d = date.getDate();
	if(m<10){
		m = "0"+m;
    }
	if(d<10){
	   d = "0"+d;
	}
	var dirName = date.getFullYear()+""+m+""+d;
	return dirName; 
}
</script>
</body>
</html>