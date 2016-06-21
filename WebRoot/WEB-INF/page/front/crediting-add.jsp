<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="wrap nymain">
<div class="bigbox" style="border:none">
<div class="sqdk w950" style="background: none;">
    <div class="r-main bg-white mt20 mb20 ">
    <div class="rmainbox m20">
    <p class="edtxt">当前信用额度：<strong>${creditMap.creditLimit}</strong>元
     ,可用信用额度：<strong>${creditMap.usableCreditLimit}</strong>元</p>
    <div class="tab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right"><p>申请账户：</p></td>
    <td>${user.userName}
</td>
  </tr>
  <tr>
    <td align="right"><p>申请类型：</p></td>
    <td>信用额度
</td>
  </tr>
  <tr>
    <td align="right"><p>申请资金：<span class="fred">*</span></p></td>
    <td><input type="text" class="input w200" id="applyAmount" name="paramMap.applyAmount" value="${paramMap.applyAmount}"/><s:fielderror fieldName="paramMap.applyAmount"></s:fielderror></td>
  </tr>
  <tr>
    <td align="right" valign="top"><p>详细说明：<s:fielderror fieldName="paramMap.applyDetail"></s:fielderror></p></td>
    <td><textarea id="applyDetail" name="paramMap.applyDetail" class="txt420">${paramMap.applyDetail}</textarea></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td class="tishi">温馨提示：额度申请原则上每次<span style="text-decoration:underline;">最多申请1万</span>。
      额度申请后，无论申请是否批准，<br />
      必须隔一个月后才能再次申请，每个月只能申请一次。如有问题,请与客服联系</td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top:20px;">
    <s:if test="creditingApplyStatus == 0">
       <span class="fred">${apply}</span>
    </s:if>
    <s:else>
    <a href="javascript:void(0);" id="bcbtn" class="button w140">确 定</a>
    </s:else>
    </td>
  </tr>
    </table>

    </div>
    <div class="edtab">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th align="center">序号</th>
    <th align="center">申请类型</th>
    <th align="center">申请资金</th>
    <th align="center">详情说明</th>
    <th align="center">状态</th>
  </tr>
<s:if test="pageBean.page!=null || pageBean.page.size>0">
<s:set name="counts" value="#request.pageNum"/>
    <s:iterator value="pageBean.page" var="credting" status="s">
    <tr>
    <td align="center"><s:property value="#s.count+#counts"/></td>
    <td align="center"><s:property value="#credting.creditingName"/></td>
    <td align="center"><s:property value="#credting.applyAmount"/></td>
    <td align="center" class="ck"><s:property value="#credting.applyDetail"/></td>
    <td align="center" class="ck">
    <s:if test="#credting.status ==1">审核中</s:if>
    <s:elseif test="#credting.status ==2">审核失败</s:elseif>
    <s:elseif test="#credting.status ==3">审核通过</s:elseif>
    </td>
  </tr>
  </s:iterator>
  </s:if>
  <s:else>
      <tr><td colspan="5" align="center">没有数据</td></tr>
  </s:else>
</table>
</div>
    <div class="fenye">
    <s:if test="pageBean.page!=null || pageBean.page.size>0">
          <div class="page" style=" padding-top:20px;">
                    <p>
                       <shove:page url="creditingInit.mht" curPage="${pageBean.pageNum}" showPageCount="6" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
					   </shove:page>
                    </p>
          </div>    
    </s:if>
    </div>
    </div>
    </div>
  </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<script type="text/javascript">
$(function(){
	//$("#zq_hover a").removeClass('nav_first');
	//$("#jk_hover a").addClass('class','nav_first');
	$("#jk_hover").attr('class','nav_first');
	$("#jk_hover div").removeClass('none');
    //样式选中
  //  dqzt(3);
	  var sd=parseInt($(".l-nav").css("height"));
    var sdf=parseInt($(".r-main").css("height"));
	 $(".l-nav").css("height",sd>sdf?sd:sdf-15);
});		     
</script>
<script type="text/javascript">
<!--
    $("#jumpPage").attr("href","javascript:void(null)");
	$("#jumpPage").click(function(){
	     var curPage = $("#curPageText").val();
		 if(isNaN(curPage)){
			alert("输入格式不正确!");
			return;
		 }
    window.location.href="creditingInit.mht?curPage="+curPage+"&pageSize=${pageBean.pageSize}";
	});
//-->
</script>
<script type="text/javascript">
       var flag = true;
       $(function(){
	      $("#bcbtn").click(function(){
	         if(flag){
	           flag = false;
	           param['paramMap.applyAmount']=$('#applyAmount').val();
	           param['paramMap.applyDetail']=$('#applyDetail').val();
	           //--add by houli 增加信用类型
	           param['paramMap.creditingName']='信用额度';
	           //--end
	           $.dispatchPost('addCrediting.mht',param,function(data){
		         var callBack = data.msg;
		         if(callBack == 1){
		                alert("操作成功!");
		                window.location.href= 'creditingInit.mht';
		                return false;
		         }
		         flag = true;
		         alert(callBack);
		       });
		     }
	      });
      });		     
</script>
</body>
</html>
