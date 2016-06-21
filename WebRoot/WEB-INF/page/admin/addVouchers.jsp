<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>新增代金券</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script  language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		

		
	</head>
	<body style="min-width: 1000px;">
					<div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
						<!-- 内容显示位置 -->
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
						<td id="secodetble" style="width: 60%">
						<table  border="0" cellspacing="1" cellpadding="3" width="60%">
							<tr>
							<td>
						    <table>
							 <tr><td class="main_alll_h2" width="100px" height="20"  align="center">
							<a  href="addVouchers.mht">新增代金券</a></td>
							<td>&nbsp;</td>
							</tr>
							</table>
							</td>
							</tr>
							<tr>
							<td align="center">
							<table>
							<!--  <tr>
                                <td height="36" align="right" class="blue12">
                                 	批次号：
								</td>
								<td align="left" class="f66">			
									&nbsp;&nbsp;&nbsp;<input type="text" class="inp188" id="address" />
								</td> 							
							
							</tr>-->
							<tr>
							    <td height="36" align="right" class="blue12">
									标题：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
								  	<input type="text" class="inp188" id="title" />
								</td> 	
							
							</tr>
							
							<tr>
								<td height="36" align="right" class="blue12">
									代金券类别：
								</td>
								<td>
									<span style="color: red; float: none;" class="formtips">*</span>
									<select id="type"  >
									    <option value=" " >请选择</option>
                                        <option value="0" >体验券</option>
                                        <option value="1" >现金券</option>
									 </select>
								</td>
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">
									金额：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
								  	<input type="text" class="inp188" id="amount" />
								</td> 	
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">
									数量：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
									<input type="text" class="inp188" id="number" />&nbsp;&nbsp;最多2000张
								</td> 	
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">
									开始时间：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
									<input id="timeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;当天早上00：00：00
								</td> 	
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">
									过期时间：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
									<input id="timeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;当天晚上23：59：59
								</td> 	
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">申请人：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
								  	<input type="text" class="inp188" id="applicant" />
								</td> 	
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">申请部门：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
								  	<input type="text" class="inp188" id="departments" />
								</td> 	
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">操作人：
								</td>
								<td align="left" class="f66">
								  	&nbsp;&nbsp;${userName}
								  	<input type="hidden" class="inp188" name="operation" id="operation" value="${userName}" />
								</td> 	
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<input id="search" type="button" value="生成代金券" name="search" />
								</td>
								<td align="left" class="f66">
									&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" value="&nbsp;&nbsp;取 消&nbsp;&nbsp;" onclick="javascript:history.go(-1);">
								</td>
								</tr>
								</table></td>
							</tr>
						</table>
						 </td>
						  </tr>
						</table>
						<br />
					</div>
	<script  type="text/javascript">
	$("#search").click(function(){
		if($("#timeStart").val()> $("#timeEnd").val()){
            alert("起止日期不能小于截止日期");
            return;
       }
	    var param = {};
	    param["paramMap.title"] = $("#title").val();
	    param["paramMap.type"] = $("#type").val();
	    param["paramMap.amount"] = $("#amount").val();
		if(isNaN($("#amount").val())){
			alert("请输入正确的金额");
			return null;
		}else if(parseInt($("#amount").val())<=0 ||  $("#amount").val() >10000000){
			alert("金额大小在1~10000000之内");
			return;
		}
	    param["paramMap.number"] = $("#number").val();
		if(isNaN($("#number").val())){
            alert("请输入正确的数量");
            return null;
        }else if(parseInt($("#number").val())<=0 ||  $("#number").val() >2000){
            alert("数量大小在1~2000之内");
            return;
        }
		param["paramMap.timeStart"] = $("#timeStart").val();
		var time = $("#timeEnd").val();
		param["paramMap.timeEnd"] = time+" 23:59:59";
		param["paramMap.applicant"] = $("#applicant").val();
		param["paramMap.departments"] = $("#departments").val();
		param["paramMap.operation"] = $("#operation").val();
	    $.post("addVouchersList.mht",param,function(data){
	       var callBack = data.msg;
	       if(callBack == undefined || callBack == ''){
		      $('#right').html(data);
		      $(this).show();
		   }
	       if(data.msg=="新增成功"){
	         alert("新增成功");
	         window.location.href='queryVouchersInit.mht';
	       }else{
		       if(data.msg=="新增失败"){
		         alert("新增失败");
		       }
		       if(data.msg=="请填写标题"){
		         alert("请填写标题");
		       }
		       if(data.msg=="请选择类型"){
		          alert("请选择类型");
		       }
		       if(data.msg=="请填写金额"){
		        alert("请填写金额");
		       }
		       if(data.msg=="请填写数量"){
		        alert("请填写数量");
		       }
		       if(data.msg=="请填写开始时间"){
		         alert("请填写开始时间");
		       }
		       if(data.msg=="请填写失效时间"){
		         alert("请填写失效时间");
		       }
		       if(data.msg=="请填写申请人"){
		         alert("请填写申请人");
		       }
		       if(data.msg=="请填写申请时间"){
		         alert("请填写申请时间");
		       }
		       if(data.msg=="请填写操作人"){
		            alert("请填写操作人");
		       }
	       }
	    });
		});
	</script>
	</body>
</html>

