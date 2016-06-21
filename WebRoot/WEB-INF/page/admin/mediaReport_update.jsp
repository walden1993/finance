<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>更新内容</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript" src="../common/dialog/popup.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		
		<script>
			var editor_content;
			KindEditor.ready(function(K) {
				editor_content = K.create('textarea[name="paramMap.content"]', {
					cssPath : '../kindeditor/plugins/code/prettify.css',
					uploadJson : 'kindEditorUpload.mht',
					fileManagerJson : 'kindEditorManager.mht',
					allowFileManager : true
				});
			});
			
			 $(function(){
		      
		      //提交表单
				$("#btn_save").click(function(){
					$(this).hide();
					$("#tr_content").val(editor_content.html());
					
					var right=document.getElementById('sright'); 
                    for(i=1; i <right.length; i++) {
                       right[i].selected = true;   
                    }
					
					$("#updateTeam").submit();
					return false;
				});
				
				//上传图片
				$("#btnUpImg").click(function(){
					var dir = getDirNum();
					var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'report/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
					json = encodeURIComponent(json);
					//window.showModalDialog("uploadFileAction.mht?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
					window.open('uploadFileAction.mht?obj='+json,'newwindow','width=500,height=400,left=400,top=150,resizable=yes');
				});
				
				if("${paramMap.imgPath}" != ""){
					$("#img").attr("src","../${paramMap.imgPath}");
				}
		  });
		   	function uploadCall(basepath,fileName,cp){
		  		if(cp == "img"){
		  			var path = "upload/"+basepath+"/"+fileName;
		  			$("#imgPath").val(path);
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
		<s:head />
	</head>
	<body>
		<form id="updateTeam" name="updateTeam" action="updateMediaReport.mht" method="post">
			
			<s:hidden id="id" name="paramMap.id" />
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100" height="28"  class="xxk_all_a">
									<a href="mediaReportListinit.mht">媒体报道管理</a>
								</td>
								<td width="2">
									&nbsp;
								</td>
								<td width="100"  class="main_alll_h2">
									<a href="updateMediaReportInit.mht?id=${paramMap.id}">修改媒体报道</a>
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
					 
					<div style="width: auto; background-color: #FFF; padding: 10px;">
												<table width="100%" border="0" cellspacing="1" cellpadding="3">
												<tr>
                                <td style="width: 100px; height: 25px;" align="right"
                                    class="blue12">
                                    动态类别
                                </td>
                                <td align="left" class="f66">
                                    <select id="tb_newsType" name="paramMap.newsType"  >
                                        <option value="1" <s:if test="paramMap.newsType==1">selected</s:if> >三好资本动态</option>
                                        <option value="2" <s:if test="paramMap.newsType==2">selected</s:if> >互联网金融</option>
                                        <option value="3" <s:if test="paramMap.newsType==3">selected</s:if> >三好资本问答</option>
                                     </select>
                                     <span class="require-field">*</span>
                                </td>
                            </tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									新闻标题
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.title"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['title']"></s:fielderror></span>
								</td>
							</tr>
							
								<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									新闻网址
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.url"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field"><s:fielderror fieldName="paramMap['url']"></s:fielderror></span>
								</td>
							</tr>
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									新闻来源
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_title" name="paramMap.source"
										cssClass="in_text_250" theme="simple"></s:textfield>
									<span class="require-field">*<s:fielderror fieldName="paramMap['source']"></s:fielderror></span>
								</td>
							</tr>
							                         <tr>
                                <td style="width: 100px; height: 25px;" align="right"
                                    class="blue12">
                                    标签
                                </td>
                                <td align="left" class="f66">
<s:optiontransferselect 
            id="sleft"
            doubleId="sright"
              name="label"  
               leftTitle="未选中"  
              list="#session.labels1"
              doubleName="paramMap.label"
              rightTitle="已选中" 
              doubleList="#session.labels2"  
              multiple="true" 
              doubleOndblclick="moveSelectedOptions(document.getElementById('sright'), document.getElementById('sleft'), false, '-1', '');" 
              ondblclick="moveSelectedOptions(document.getElementById('sleft'), document.getElementById('sright'), false, '-1', '');" 
              addToLeftLabel="向左移动" 
              addToRightLabel="向右移动" 
              addAllToRightLabel="全部右移" 
              addAllToLeftLabel="全部左移"
               allowSelectAll="true" 
               headerKey="-1" 
               headerValue="---请选择---" 
               emptyOption="false"   
               doubleHeaderKey="-1" 
              doubleHeaderValue="---请选择---" 
               doubleMultiple="true" 
               doubleEmptyOption="false"  
               allowUpDownOnLeft="false"
               allowUpDownOnRight="false"
               listKey="id"
               listValue="selectName"
               doubleListKey="id"
               doubleListValue="selectName"
                >
            </s:optiontransferselect>
                                    <span class="require-field">*<s:fielderror fieldName="paramMap['label']"></s:fielderror></span>
                                </td>
                            </tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									图片：
								</td>
								<td align="left" class="f66">
									<img id="img" src="../images/NoImg.GIF" style="width: 100px;height: 100px;"/>
									<s:hidden id="imgPath" name="paramMap.imgPath" />
									<input id="btnUpImg" value="上传" type="button"/>
									<span class="require-field"><s:fielderror fieldName="paramMap['imgPath']"></s:fielderror></span>
								</td>
							</tr>
							
							<tr>
								<td style="height: 25px;" align="right" class="blue12">
									新闻简述
								</td>
								<td align="left" class="f66">
									<textarea id="tr_content" name="paramMap.content" rows="20" class="textareash"
											style="width: 670px; padding:5px;">
										<s:property value="paramMap.content"/>
									</textarea>
									<span class="require-field"><s:fielderror fieldName="paramMap['content']"></s:fielderror></span>
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									状态：
								</td>
								<td align="left" class="f66">
									<input type="radio" name="state" value="2" 
									   		<s:if test='paramMap.state == 2'>checked="checked"</s:if>
																					<s:else></s:else>
									    />开启 
									 <input type="radio" name="state" value="1"
									    <s:if test='paramMap.state == 1'>checked="checked"</s:if>
																					<s:else></s:else>
									    />隐藏
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 30px;" align="right"
									class="blue12">
									是否置顶：
								</td>
								<td align="left" class="f66">
									<input type="radio" name="stick" value="2" 
									   		<s:if test='paramMap.stick == 2'>checked="checked"</s:if>
																					<s:else></s:else>
									    />是
									 <input type="radio" name="stick" value="1"
									    <s:if test='paramMap.stick == 1'>checked="checked"</s:if>
																					<s:else></s:else>
									    />否
								</td>
							</tr>
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									序号：
								</td>
								<td align="left" class="f66">
									<s:textfield id="tb_price" name="paramMap.sort"
										cssClass="in_text_150" theme="simple"></s:textfield>
									<span class="require-field">&nbsp; &nbsp;<s:fielderror fieldName="paramMap['sort']"></s:fielderror></span>
								</td>
							</tr>
					
							
							<tr>
								<td style="width: 100px; height: 25px;" align="right"
									class="blue12">
									发布时间：
								</td>
								<td align="left" class="f66">
								    <input class="in_text_150" type="text"  value="${paramMap.publishTime}" name="paramMap.publishTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})"/>
									
									
									<span class="require-field">&nbsp; &nbsp;<s:fielderror fieldName="paramMap['publishTime']"></s:fielderror></span>
									
								</td>
							</tr>
							
							
							<tr>
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
		</form>
	</body>
</html>
