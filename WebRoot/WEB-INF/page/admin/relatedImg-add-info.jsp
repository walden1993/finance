<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>相关材料图片添加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		
		<script>
			$(function(){
				//提交表单
				$("#btn_save").click(function(){
					$("#addLinks").submit();
					return false;
				});
				
				$("#btnUpImg").click(function(){
					var dir = getDirNum();
					var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'lists/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
					json = encodeURIComponent(json);
					//window.showModalDialog("uploadFileAction.mht?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
					window.open('uploadFileAction.mht?obj='+json,'newwindow','width=500,height=400,left=400,top=150,resizable=yes');
				});
				if("${paramMap.imgPath}"!=""){
					$("#img").attr("src","../${paramMap.imgPath}");
				}
				
			  });
			
				function uploadCall(basepath,fileName,cp){
			  		if(cp == "img"){
			  			var path = "upload/"+basepath+"/"+fileName;
			  			$("#companyImg").val(path);
						$("#img").attr("src","../"+path);
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
		
	</head>
	<body>
		<form id="addLinks" name="addLinks" action="addRelatedImgInfo.mht" method="post">
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28" class="xxk_all_a">
									<a href="related.mht?id=${id}">图片管理</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100" class="main_alll_h2">
									<a href="addRelatedImgInit.mht?id=${id}">添加图片</a>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 <s:hidden  name="id" value="%{#request.id}"></s:hidden>
					<div style="width: auto; background-color: #FFF; padding: 10px;">
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									图片：
								</td>								
								<td align="left" class="f66">
									<img id="img" src="../images/NoImg.GIF" style="width: 100px;height: 100px;"/>
								     <s:hidden id="companyImg" name="paramMap.imgPath" />
									 <input id="btnUpImg" value="浏览..." type="button"/>
									<span class="require-field">*<s:fielderror fieldName="paramMap['imgPath']">
									</s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
								 类型：
								</td>
								<td>
								<s:select id="paramMap.type"  name="paramMap.type" list="imgtype"   listKey="id"  listValue="selectName"  headerKey=""  headerValue="请选择" ></s:select>
								<span class="require-field">*<s:fielderror fieldName="paramMap['type']"></s:fielderror></span>
								</td>
							</tr>
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									名称：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_id" name="paramMap.name" 
										cssClass="in_text_2" cssStyle="width: 150px" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['name']"></s:fielderror></span>
								</td>
							</tr>
									<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									描述：
								</td>
								<td align="left" class="f66">
									<s:textarea id="tb_id" name="paramMap.description"   rows="3"
										cssStyle="width: 150px" theme="simple"></s:textarea>
									<span class="require-field"><s:fielderror fieldName="paramMap['description']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
                                <td style="width: 100px; height: 25px;" align="right"
                                    class="blue12">
                                    是否打码：
                                </td>
                                <td align="left" class="f66">
                                <s:radio   id="tb_id"   list="#{0:'是',1:'否'}"  value="0"  name="paramMap.isa" ></s:radio>
                                    <span class="require-field">*<s:fielderror fieldName="paramMap['isa']"></s:fielderror></span>
                                </td>
                            </tr>
							<%--<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发布时间：
								</td>
								<td align="left" class="f66">
									<s:textfield name="paramMap.publishTime" cssClass="in_text_2" cssStyle="width: 150px" theme="simple"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"  id="publishtime" ></s:textfield>
									<!-- <s:date name="paramMap.publishTime" format="dd/MM/yyyy" /> -->
									<span class="require-field">*<s:fielderror fieldName="paramMap['publishTime']"></s:fielderror></span>
								</td>
							</tr>
							--%><tr>
								<td height="36" align="right" class="blue12">
									<s:hidden name="action"></s:hidden>
									&nbsp;
								</td>
								<td>
									<button id="btn_save"
										style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"></button>
									&nbsp;
									<button type="reset"
										style="background-image: url('../images/admin/btn_chongtian.jpg'); width: 70px; height: 26px; border: 0px"></button>
									&nbsp; &nbsp;
									<span class="require-field"><s:fielderror fieldName="actionMsg" theme="simple"></s:fielderror></span>
								</td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
