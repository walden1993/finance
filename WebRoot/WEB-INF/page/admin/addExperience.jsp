<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title>新建体验标</title>
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
        
        <link rel="stylesheet" href="../kindeditor/themes/default/default.css" />
        <link rel="stylesheet" href="../kindeditor/plugins/code/prettify.css" />
        <script charset="utf-8" src="../kindeditor/kindeditor.js"></script>
        <script charset="utf-8" src="../kindeditor/lang/zh_CN.js"></script>
        <script charset="utf-8" src="../kindeditor/plugins/code/prettify.js"></script>

        
    </head>
    <body style="min-width: 1000px;">
                    <div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
                        <!-- 内容显示位置 -->
                        <table width="100%" border="0" cellspacing="1" cellpadding="3">
                        <tr>
                        <td id="secodetble" style="width: 60%">
                        <table  border="0" cellspacing="1" cellpadding="3" width="100%">
                            <tr>
                            <td>
                            <table>
                            <tr>
                             <td width="100" height="28"  class="xxk_all_a">
                                    <a href="queryExperienceInit.mht">体验标列表</a>
                                </td>
                                <td width="2">
                                    &nbsp;
                                </td>
                                <td width="100"  class="main_alll_h2">
                                    <a href="addExperience.mht">新建体验标</a>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            </table>
                            </td>
                            </tr>
                            <tr>
                            <td align="center">
                            <!-- <tr>
                                <td height="36" align="right" class="blue12">
                                    状态：
                                </td>
                                <td align="left" class="f66">           
                                    &nbsp;&nbsp;&nbsp;<input type="text" class="inp188" id="address" />
                                </td>                           
                            </tr> -->
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    标题：<span class="require-field">*&nbsp;</span>
                                </td>
                                <td align="left" class="f66">
                                    <input type="text" class="inp188" id="title" />
                                </td>   
                            
                            </tr>
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    体验总金额：<span class="require-field">*&nbsp;</span>
                                </td>
                                <td align="left" class="f66">
                                    <input type="text" class="inp188" id="amount" />
                                </td>   
                            </tr>
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    年利率：<span class="require-field">*&nbsp;</span>
                                </td>
                                <td align="left" class="f66">
                                    <input type="text" class="inp188" id="annualRate" />&nbsp;&nbsp;%
                                </td>   
                            </tr>
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    期限：<span class="require-field">*&nbsp;</span>
                                </td>
                                <td>
                                    <select id="deadline"  >
                                        <option  value="">－请选择－</option>
                                        <option value="1" >1</option>
                                        <option value="2" >2</option>
                                        <option value="3" >3</option>
                                     </select>&nbsp;&nbsp;个月
                                </td>
                            </tr>
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    筹标期限：<span class="require-field">*&nbsp;</span>
                                </td>
                                <td align="left" class="f66">
                                    <input type="text" class="inp188" id="raiseTerm" />&nbsp;&nbsp;天
                                </td>   
                            </tr>
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    收益返还方式：<span class="require-field">*&nbsp;</span>
                                </td>
                                <td align="left" class="f66">
                                    <select id="paymentMode"  >
                                        <option value=" " >－请选择－</option>
                                        <option value="1" >按月还息</option>
                                     </select>
                                </td>   
                            </tr>
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    最小体验金额：<span class="require-field">*&nbsp;</span>
                                </td>
                                <td align="left" class="f66">
                                    <select id="minSum"  >
                                        <option value="">－请选择－</option>
                                        <option value="1000" >1000</option>
                                        <option value="2000" >2000</option>
                                        <option value="5000" >5000</option>
                                        <option value="10000" >10000</option>
                                     </select>
                                </td>   
                            </tr>
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    最大体验金额：<span class="require-field">*&nbsp;</span>
                                </td>
                                <td align="left" class="f66">
                                    <select id="maxSum"  >
                                        <option value="" >－请选择－</option>
                                        <option value="1000" >1000</option>
                                        <option value="2000" >2000</option>
                                        <option value="5000" >5000</option>
                                        <option value="10000" >10000</option>
                                        <option value="-1" >不限制</option>
                                     </select>
                                </td>   
                            </tr>
                            <tr>
                                <td style="height: 25px;" align="right" class="blue12">
                                    详细描述：<span class="require-field">*&nbsp;</span>
                                </td>
                                <td align="left" class="f66">
                                    <textarea id="detail" name="paramMap.content" rows="15" class="textareash"
                                            style="width: 670px; padding:5px;">
                                    </textarea>
                                </td>
                            </tr>
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    
                                </td>
                                <td align="left" class="f66">
                                    <input id="search" type="button" value="&nbsp;&nbsp;发 布&nbsp;&nbsp;" name="search" />
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="button" value="&nbsp;&nbsp;取 消&nbsp;&nbsp;" onclick="javascript:history.go(-1);">
                                </td>
                                </tr>
                                </td>
                            </tr>
                        </table>
                         </td>
                          </tr>
                        </table>
                        <br />
                    </div>
    <script  type="text/javascript">
    var editor_content;
    KindEditor.ready(function(K) {
        editor_content = K.create('textarea[name="paramMap.content"]', {
            cssPath : '../kindeditor/plugins/code/prettify.css',
            uploadJson : 'kindEditorUpload.mht',
            fileManagerJson : 'kindEditorManager.mht',
            allowFileManager : true
        });
    });
    
    $("#search").click(function(){
        var param = {};
        param["paramMap.title"] = $("#title").val();
        if(param["paramMap.title"]==null || param["paramMap.title"]=='' ){
        	alert("请填写标题");
        	return;
        }else if(param["paramMap.title"].length>15){
			alert("标题长度不能超过16个字符。");
			return;
		}
        param["paramMap.amount"] = $("#amount").val();
		if(isNaN(param["paramMap.amount"]) || parseInt(param["paramMap.amount"])==0){
			alert("请输入正确的体验总金额");
			return;
		}else if( parseInt(param["paramMap.amount"])>10000000  ||  parseInt(param["paramMap.amount"])<1000){
			alert("体验总金额不能大于10000000小于1000");
			return;
		}
        param["paramMap.annualRate"] = $("#annualRate").val();
		if(isNaN(param["paramMap.annualRate"]) || parseFloat(param["paramMap.annualRate"])==0.0){
			alert("请输入正确的年利率(0.1%~30.0%)");
			return;
		}else if(parseFloat(param["paramMap.annualRate"])>30 || parseFloat(param["paramMap.annualRate"])<0.1){
			alert("请输入正确的年利率(0.1%~30.0%)");
            return;
		}
        param["paramMap.deadline"] = $("#deadline").val();
        param["paramMap.raiseTerm"] = $("#raiseTerm").val();
		if(isNaN(param["paramMap.raiseTerm"])){
			alert("请输入正确的筹款期限");
			return;
		}else if(parseInt(param["paramMap.raiseTerm"])<=0 || parseInt(param["paramMap.raiseTerm"])>30){
			alert("筹款期限1-30天之内");
			return ;
		}
        param["paramMap.paymentMode"] = $("#paymentMode").val();
        param["paramMap.minSum"] = $("#minSum").val();
        param["paramMap.maxSum"] = $("#maxSum").val();
		
		
		if(parseInt(param["paramMap.minSum"])>parseInt(param["paramMap.maxSum"])  && parseInt(param["paramMap.maxSum"])!=-1){
			alert("最小体验金额不能大于最大体验金额")
			return;
		}
		
        $("#detail").val(editor_content.html());
        var content=""+$("#detail").val()+"";
        param["paramMap.detail"] = content;
        $.post("addExperienceInfo.mht",param,function(data){
           var callBack = data.msg;
           if(callBack == undefined || callBack == ''){
              $('#right').html(data);
              $(this).show();
           }
           if(data.msg=="新增成功"){
             alert("新增成功！");
             window.location.href='queryExperienceInit.mht';
           }else{
               if(data.msg=="新增失败"){
                 alert("新增失败！");
               }
               if(data.msg=="请填写标题"){
                 alert("请填写标题");
               }
               if(data.msg=="请填写总额"){
                  alert("请填写总额");
               }
               if(data.msg=="请填写年利率"){
                alert("请填写年利率");
               }
               if(data.msg=="请填写期限"){
                alert("请选择期限");
               }
               if(data.msg=="请填写筹标期限"){
                 alert("请填写筹标期限");
               }
               if(data.msg=="请填写还款方式"){
                 alert("请选择收益返还方式");
               }
               if(data.msg=="请填写最小额"){
                 alert("请选择最小额");
               }
               if(data.msg=="请填写最大额"){
                 alert("请选择最大额");
               }
               if(data.msg=="请填写描述"){
                    alert("请填写描述");
               }
           }
        });
        });
    </script>
    </body>
</html>

