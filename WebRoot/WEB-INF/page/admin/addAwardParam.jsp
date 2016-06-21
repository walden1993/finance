<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title> 新增奖品</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>

	<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
		<tbody>
			<s:if test="strutsAction==2">
			 <tr>
                <td class="f66" align="left" width="5%"  height="36px">
                    编号：
                </td>
                <td class="f66" align="left"  height="36px">
                   ${paramMap.id}
                </td>
            </tr>
			</s:if>
			<tr>
				<td class="f66" align="left"  height="36px">
					奖品名称：
				</td>
				<td class="f66" align="left"  height="36px">
				<s:textfield name="paramMap.awardName" id="awardName" ></s:textfield>
				</td>
			</tr>
			<tr>
				<td class="f66" align="left"   height="36px">
					图片:
				</td>
				<td class="f66" align="left"   height="36px">
					<img id="img" src="../images/NoImg.GIF" style="width: 100px;height: 100px;"/>
								     <s:hidden id="picAddr" name="paramMap.picAddr" />
									 <input id="btnUpImg" value="浏览..." type="button"/>
									<span class="require-field">*<s:fielderror fieldName="paramMap['picAddr']">
									</s:fielderror></span>
				</td>
			</tr>
			<tr>
				<td class="f66" align="left"   height="36px">
					备注:
				</td>
				<td class="f66" align="left"   height="36px">
					<textarea name="paramMap.remark" rows="3" cols="30" id="remark"></textarea>
				</td>
			</tr>
			<!-- tr>
				<td class="f66" align="left"   height="36px">
					是否可中：
				</td>
				<td class="f66" align="left"  height="36px">
					<select id="isUsable">
					  <option value="0">否</option>
					  <option value="1">是</option>
					</select>&nbsp;&nbsp; 
				</td>
			</tr> 
			
			<tr>
				<td class="f66" align="left"   height="36px">
					排序:
				</td>
				<td class="f66" align="left"   height="36px">
					<input type="text" name="myOrder" id="myOrder" value=""/>
				</td>
			</tr>
			<tr>
				<td class="f66" align="left"   height="36px">
					中奖概率：
				</td>
				<td class="f66" align="left"   height="36px">
					<input type="text" name="prizeRate" id="prizeRate" value=""/>%
				</td>
			</tr> -->
			
			<tr>
				<td class="f66" align="left" height="36px">
					<input type="button" id="savePrize" name="savePrize" value="保存"  />
				</td>
				<td class="f66" align="left"   height="36px">
					<input type="button" id="retClose" name="retClose" value="返回" onclick="retPrize();" />
				</td>
			</tr>
			
		</tbody>
	</table>
<s:actionmessage/>
</div>

			</div>
		</div>
	</body>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript">

	$(function(){
	$("#btnUpImg").click(function(){
		var dir = getDirNum();
		var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'lists/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall','cp':'img'}";
		json = encodeURIComponent(json);
		window.open('uploadFileAction.mht?obj='+json,'newwindow','width=500,height=400,left=400,top=150,resizable=yes');
	});
	if("${paramMap.picAddr}"!=""){
		$("#img").attr("src","../${paramMap.picAddr}");
	}
	$("#savePrize").click(function(){
		savePrizeParam();
	});
	});
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

	function uploadCall(basepath,fileName,cp){
  		if(cp == "img"){
  			var path = "upload/"+basepath+"/"+fileName;
  			$("#picAddr").val(path);
			$("#img").attr("src","../"+path);
  		}
	}
	
	function retPrize(){
		window.parent.jBox.close();
	}
	
	function savePrizeParam(){
		param["paramMap.awardName"] = $("#awardName").val();
		param["paramMap.remark"] = $("#remark").val();
		param["paramMap.picAddr"] = $("#picAddr").val();
		param["strutsAction"] = ${strutsAction};
		if(${strutsAction}==2){
			param["paramMap.id"] = '${paramMap.id}';
		}
		$.dispatchPost("awardParamEdit.mht",param,initCallBack1);
	}
	
	function initCallBack1(data){
		if(data>0){
			alert("操作成功!");
		}else{
			alert("操作失败!");
		}
		window.parent.initListInfo(null);
		window.parent.jBox.close();
	}
</script>
</html>
