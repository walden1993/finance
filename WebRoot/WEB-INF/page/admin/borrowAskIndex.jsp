<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
    <head>
        <title>借款申请</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="expires" content="0" />
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
        <meta http-equiv="description" content="This is my page" />
        <link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
        <link id="skin" rel="stylesheet" href="../css/jbox/Skins/Blue/jbox.css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src="../script/jquery.dispatch.js"></script>
        <script  language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
        
        <script type="text/javascript">
            $(function(){
                
                initListInfo(param);
                $("#bt_search").click(function(){
                 param["pageBean.pageNum"] = 1;
                 param["paramMap.contact_phone"] = $("#contact_phone").val();
                 param["paramMap.contact_name"] = $("#contact_name").val();
                 param["paramMap.borrowerType"] = $("#borrowerType").val();
                 param["paramMap.borrowWay"] = $("#borrowWay").val();
                 param["paramMap.createTimeStart"] = $("#createTimeStart").val();
                 param["paramMap.createTimeEnd"] = $("#createTimeEnd").val();
                 param["paramMap.hasMortgage"] = $("#hasMortgage").val();
                 param["paramMap.hasDistribution"] = $("#hasDistribution").val();
                 if( param["paramMap.createTimeStart"]>param["paramMap.createTimeEnd"]){
                     alert("起始日期不能小于截至日期")
                     return;
                 }
                 initListInfo(param);
                });
            
                    
            });
            
            function initListInfo(praData){
                
                $.post("borrowAskList.mht",praData,initCallBack);
            }
            
            function initCallBack(data){
                $("#dataInfo").html(data);
            }
            
            
            function del(id){
                if(!window.confirm("确定要删除吗?")){
                    return;
                }
                
                window.location.href= "deleteDownload.mht?id="+id;
            }
            
            function checkAll(e){
                if(e.checked){
                    $(".downloadId").attr("checked","checked");
                }else{
                    $(".downloadId").removeAttr("checked");
                }
            }
            
            //弹出窗口关闭
            function close(){
                 ClosePop();              
            }
        </script>
    </head>
    <body style="min-width: 1000px">
        <div id="right">
            <div style="padding: 15px 10px 0px 10px;">
                <div>
                    <table  border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="100" height="28" class="main_alll_h2">
                                <a href="borrowAsk.mht">借款申请</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                        </tr>
                    </table>
                    </div>
                    <div
                        style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
                        <table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
                            width="100%" border="0">
                            <tbody>
                                <tr>
                                    <td class="f66" align="left" width="80%" height="36px">
                                        联系人手机 ：
                                        <input id="contact_phone" name="paramMap.contact_phone" type="text"/>&nbsp;&nbsp;
                                        联系人姓名：
                                        <input id="contact_name" name="paramMap.contact_name" type="text"/>&nbsp;&nbsp;&nbsp;
                                        申请时间：
                                       <input id="createTimeStart"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  name="paramMap.createTime" type="text"/>&nbsp;&nbsp;
                                        至<input id="createTimeEnd"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  name="paramMap.createTime" type="text"/>&nbsp;&nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td class="f66" align="left" width="80%" height="36px">
                                          借款类型：
                                        <s:select list="#request.syspar"  headerKey=""  headerValue="全部"   listKey="selectKey" listValue="selectName"   id="borrowWay"></s:select>&nbsp;&nbsp;&nbsp;
                                        借款方类型：
                                        <s:select list="#{'':'--全部--',1:'个人',2:'企业'}" id="borrowerType"></s:select>&nbsp;&nbsp;&nbsp;
                                        是否有抵押：
                                        <s:select  list="#{'':'--全部--',0:'是',1:'否'}" id="hasMortgage"></s:select>&nbsp;&nbsp;&nbsp;
                                       是否分配：
                                       <s:select list="#{'':'--全部--',0:'是',1:'否'}" id="hasDistribution"></s:select>&nbsp;&nbsp;&nbsp;
                                        <input id="bt_search" type="button" value="搜索"  />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    <span id="dataInfo"> </span>
                    </div>
                </div>
            </div>
    </body>
</html>
