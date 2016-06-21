<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>移动端版本设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<link rel="stylesheet" href="../../../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		
		<script>
		   $(function(){
		     $("#friendAward").click(function(){
		       
		        $.post("showCostType.mht","type=3",function(data){
		             $("#showcontent").html("");
		            $("#feind").attr("bgcolor","#CC0000");
		            $("#showcontent").html(data);	        
		        });
		      
		     });
		    
		   });
	 
	
	</script>	

		
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table width="10%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td  width="8%" height="28"  class="main_alll_h2">
									<a href="updateAppOptionsInit.mht">移动端版本设置</a>
								</td>
							</tr>
						</table>
					</div>
					 
					<div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
						<!-- 内容显示位置 -->
						
						
						<table width="60%" border="0" cellspacing="1" cellpadding="3" align="center">
							<tr style="height: 20px;"></tr>
							<tr>
								<td style="width: 150px; height: 30px;"
									class="blue12">
									<span style="font-weight: bold">移动端当前版本号: </span>
								</td>
								<td align="left" class="f66">
									<s:textfield id="curNum" name="paramMap.curNum"
										 theme="simple" value="%{#request.appMap.curNum}"></s:textfield>
									<span class="require-field" id="e_host"></span>
								</td>
							</tr>
							<tr>
								<td style="width: 150px; height: 30px;"
									class="blue12">
									<span style="font-weight: bold">移动端当前版本名称: </span>
								</td>
								<td align="left" class="f66">
									<s:textfield id="curNum_name" name="paramMap.curNum"
										 theme="simple" value="%{#request.appMap.version_name}"></s:textfield>
									<span class="require-field" id="e_host"></span>
								</td>
							</tr>
							<tr>
								<td style="width: 150px; height: 30px;"
									class="blue12">
									<span style="font-weight: bold">下载地址: </span>
								</td>
								<td align="left" class="f66">
									<s:textfield id="appurl" name="paramMap.appurl"
										 theme="simple" value="%{#request.appMap.url}"></s:textfield>
									<span class="require-field" id="e_host"></span>
								</td>
							</tr>
							<tr>
								<td	class="blue12">
									<span style="font-weight: bold">当前版本更新内容: </span>
								</td>
								<td align="left" class="f66">
									<s:textarea id="content" name="paramMap.content"
										 cols="30" rows="8" value="%{#request.appMap.content}"></s:textarea>
									<span class="require-field" id="e_otherTags"></span>
								</td>
							</tr>	
							<tr>
								<td	class="blue12">
									<span style="font-weight: bold">是否强制更新: </span>
								</td>
								<td align="left" class="f66">
								<s:select list="#{0:'否',1:'是'}" id="appforce" name="paramMap.appforce"
										 theme="simple" value="%{#request.appMap.appforce}"></s:select>
									<span class="require-field" id="e_host"></span>
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									
									<button id="btn_tijiao"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; height: 26px; border: 0px"></button>
									&nbsp; &nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<img id="img" src="../images/NoImg.GIF"
										style="display: none; width: 100px; height: 100px;" />
								</td>
							</tr>
						</table>
						
						<br />
					</div>
				</div>
			</div>
			<script>
		$(function(){
		  	 
	  	 $('#btn_tijiao').click(function(){
			  	 if($("#curNum").val()==""){
			  		$("#e_host").html("移动端当前版本号不能为空!");
					return false;
			  	}else{
			  		$("#e_host").html("*");
				  }
				  
			   if($("#content").val()==""){
			  		$("#e_otherTags").html("当前版本更新内容不能为空!");
					return false;
			  	}else{
			  		$("#e_otherTags").html("*");
				  }
		   if (!confirm("你确认更改吗?")){
				return false;
		  }
		  $("#btn_tijiao").attr("disabled",true);
		   var param ={};
		  param["paramMap.curNum"] = $("#curNum").val();
		  param["paramMap.content"] = $("#content").val();
		  param["paramMap.appforce"] = $("#appforce").val();
		  param["paramMap.appurl"] = $("#appurl").val();
		   param["paramMap.version_name"] = $("#curNum_name").val();
          $.post("updateAppOptions.mht",param,function(data){
               var callBack = data.msg;  
                if(callBack == undefined || callBack == ''){
                 $('#showcontent').html(data);
                }else if(callBack == 1){
		          alert("操作成功!");
		          window.location.reload();
                }else{
                	alert(callBack);
                }    
                $("#btn_tijiao").attr("disabled",false);
          });
		  	 

          });
		
	    
		});
		</script>
	</body>
</html>
