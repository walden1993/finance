<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="涨薪宝转出" scope="request" var="title"></c:set>
<jsp:include page="/include/head.jsp"></jsp:include>
<style type="text/css">
</style>
<script type="text/javascript">
	var menuIndex = 4;
</script>

</head>
<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="wrap  mb20">
  <div class="w950 wdzh">
  	<ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li class="active">员工列表</li>
 	 </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
     <%@include file="/include/left.jsp" %>
      <!-- /div -->
    
	<div class="r_main fr w750 mb">
		<div class="pub_tb bottom">
        <h3 style="border:none; "><a href="companyEmployeeInit.mht" style="color:#a6a6a6;">员工列表</a></h3>
        <h3 style="border:none; "><a href="wageExoportEmployeeInit.mht" style="color:#a6a6a6;">薪水管理</a></h3>
        <h3>批量员工管理</h3>
        </div>
        
        <div class="uesr_border personal clearfix h500"  align="center">
        	 <s:form action="exportCompanyEmployee.mht"  id="addemployees"  enctype="multipart/form-data"  method="post">
            <table>
                <tr height="60">
                    <td align="right" class=""><h5>导入添加名单：</h5></td>
                    <td><s:file label="导入"  name="files.files"  value="导入"  id="files" cssStyle="border:1px solid #ccc;"></s:file></td>
                </tr>
                <tr height="60">
                    <td><input type="button" class="button w100" id="bind" value="确认"  /></td>
                    <td align="right"><input type="reset" class="button_over w100  ml20"  id="cacel" value="重置"/></td>
               </tr>
            </table>
           </s:form>
            <div  class="mt20">
            <span><span class="red">(请先下载模版)</span>模版<a href="#" onclick='javascript:downModel();' target="_blank"  style="color: #00F;font-size: 0.83em">下载</a></span>
        	</div>
        </div>
	
  </div>
</div>
	
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript">
		displayDetail(7,10);
		
		function downModel(){
			window.location.href = encodeURI(encodeURI("downloadCompanyEmployeeModel.mht"));
		}
		$(function(){
			$("#bind").click(function(){
				$("#addemployees").submit();
				setTimeout(function(){
					alert("操作完毕，请查看操作结果");
					$("#files").val("");
				},500);
			})
		})
	 
	</script>
</body>
</html>

