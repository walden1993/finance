<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title> <s:if test="strutsAction==1">
                                新增抽奖
                                </s:if>
                                <s:else>
                                 修改抽奖
                                </s:else></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<link id="skin" rel="stylesheet" href="../css/jbox/Skins/Blue/jbox.css" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
        <link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
					   <tr>
                            <td width="100" id="today" height="28"  class="xxk_all_a">
                                <a href="awardManage.mht">抽奖管理</a>
                            </td>
                            <td width="100" id="today" height="28"  class="main_alll_h2">
                                <s:if test="strutsAction==1">
			                    <a href="addAwardTerm.mht">新增抽奖</a>
			                    </s:if>
			                    <s:else>
			                     <a href="addAwardTerm.mht?type=2&id=${paramMap.id}">修改抽奖</a>
			                    </s:else>
                            </td>
                        </tr>
					</table>
					
					
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">

	<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
		<tbody>
			<tr>
				<td class="f66" align="left" width="5%"  height="36px">
					抽奖期数：<span class="require-field">*&nbsp;</span>
				</td>
				<td class="f66" align="left"  height="36px">
					第&nbsp;
					<s:if test="strutsAction==1">
					<input type="text" name="paramMap.termNo"  value="${paramMap.termNo}" id="termNo" size="3"/>
					</s:if>
					<s:else>
					  <font color="red"> ${paramMap.termNo}</font>
					</s:else>
					&nbsp;期
				</td>
			</tr>
			<tr>
				<td class="f66" align="left"  height="36px">
					抽奖主题：<span class="require-field">*&nbsp;</span>
				</td>
				<td class="f66" align="left"  height="36px">
					<input type="text" name="paramMap.luckTheme" id="luckTheme" value="${paramMap.luckTheme}" />
				</td>
			</tr>
			<tr>
				<td class="f66" align="left"   height="36px">
					抽奖时间：<span class="require-field">*&nbsp;</span>
				</td>
				<td class="f66" align="left"  height="36px">
					<input id="timeStart" class="Wdate" value="${paramMap.startTime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>&nbsp;&nbsp;
					--
					<input id="timeEnd" class="Wdate" value="${paramMap.endTime}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>&nbsp;&nbsp; 
				</td>
			</tr>
			<tr>
                <td class="f66" align="left"  height="36px">
                    红包个数：
                </td>
			<s:if test="strutsAction==1">
                <td class="f66" align="left"  height="36px">
                    <input type="text" name="paramMap.awardNo" id="awardNo" value="10000" />
                </td>
            </s:if>
            <s:else>
            <td class="f66" align="left"  height="36px">
                    ${paramMap.awardNo}
                </td>
            </s:else>
             </tr>
			<s:if test="strutsAction==2">
		            <tr>
		                <td class="f66" align="left"   height="36px">
		                    奖品设置：<span class="require-field">*&nbsp;</span>
		                </td>
		                <td class="f66" align="left"   height="36px">
		                    <input type="button" name="paramMap.fixPrize" value="设置" onclick="fixPrize(${paramMap.id});" />  &nbsp;&nbsp;<span>已有<font color="red">${paramMap.awardsize}</font>个奖品</span>
		                </td>
		            </tr>                   
            </s:if>
			<tr>
                  <td style="height: 25px;" align="left" class="blue12">
                      抽奖规则：<span class="require-field">*&nbsp;</span>
                  </td>
                  <td align="left" class="f66">
                      <textarea id="awardDesc" name="paramMap.awardDesc" rows="15" class="textareash"
                              style="width: 670px; padding:5px;">
                      </textarea>
                  </td>
           </tr>
			<tr>
				<td class="f66" align="left" height="36px">
					<input type="button" id="savePrize" name="savePrize" value="保存" onclick="savePrize();" />
					
				</td>
				<td class="f66" class="f66"  height="36px">
					<input type="button" id="retClose" name="retClose" value="返回" onclick="back();" />
				</td>
			</tr>
			
		</tbody>
	</table>
</div>
<div id="content"  style="display: none;">
${paramMap.awardDesc}
</div>
</div>
		</div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript" src="../css/popom.js"></script>
        <script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
        <script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
        <script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>
<script type="text/javascript">
    var editor_content;
    KindEditor.ready(function(K) {
        editor_content = K.create('textarea[name="paramMap.awardDesc"]', {
            cssPath : '../kindeditor/plugins/code/prettify.css',
            uploadJson : 'kindEditorUpload.mht',
            fileManagerJson : 'kindEditorManager.mht',
            allowFileManager : true
        });
        editor_content.html($("#content").html());
    });
    
    function fixPrize(id){
        $.jBox("iframe:listAwardParamInit.mht?id="+id, {
              title: "抽奖管理",
              width: 700,
              height: 500,
              buttons: { '关闭': true }
          });
    }
    
    function back(){
        window.location.href="awardManage.mht";
    }
    function savePrize(){
        param["paramMap.luckTheme"] = $("#luckTheme").val();
        param["paramMap.timeStart"] = $("#timeStart").val();
        param["paramMap.timeEnd"] = $("#timeEnd").val();
        param["paramMap.awardDesc"] = editor_content.html();
        param["paramMap.termNo"] = $("#termNo").val();
        param["paramMap.awardNo"] = $("#awardNo").val();
        if(!$("#luckTheme").val()  || !$("#timeStart").val()  || !$("#timeEnd").val() || !editor_content.html() || ( !$("#termNo").val() && ${strutsAction}==1)){
        	alert("带*的必填");
        	return;
        }
        
        param["strutsAction"] = '${strutsAction}';
        if(param["strutsAction"] ==2){
        	param["paramMap.id"] = '${paramMap.id}';
        	param["paramMap.termNo"] ='${paramMap.termNo}';
        }
        
         if($("#timeStart").val() &&   $("#timeEnd").val()  &&   $("#timeStart").val()> $("#timeEnd").val()){
             alert("起止日期不能小于截止日期");
             return;
         }
        $.dispatchPost("addAwardTermMain.mht",param,function(data){
            if(data.termId>0){
            	if(${strutsAction}==1) {
            		alert("添加成功！");
            	}else{
            		alert("修改成功！");
            	}
            	window.location.href="awardManage.mht";
            }else{
            	alert("操作失败！");
            }
        });
    }
</script>		
	</body>
</html>
