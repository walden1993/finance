<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
    <head>
        <title>资料下载-内容维护</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="expires" content="0" />
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
        <meta http-equiv="description" content="This is my page" />
        <link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../script/jquery-2.0.js"></script>
        <script type="text/javascript" src="../script/jquery.dispatch.js"></script>
        <script type="text/javascript" src="../css/admin/popom.js"></script>
        <script type="text/javascript">
            $(function(){
                
                param["pageBean.pageNum"] = 1;
                initListInfo(param);
                $("#bt_search").click(function(){
                if($("#titleName").val()==""){
                          alert("请输入系统参数类型");
                          return false;
                        }
                    param["pageBean.pageNum"] = 1;
                    var param1 = {};
                    param1["paramMap.titleName"] = $("#titleName").val();
                    param1["paramMap.introduce"] = $("#introduce").val();
                    $.post("addSyspar.mht",param1,function(data){
                    
                    var callBack = data.msg;
                        if(data==1){

                           alert("操作成功");
                            window.location.reload();
                        }else if(data==2){
                            alert("增加失败");
                            return ;
                        }else if(data==0){
                            alert("请输入系统参数类型");
                            return ;
                        }
                        if(callBack == undefined || callBack == ''){
                            $('#right').html(data);
                            $(this).show();
                        }
                    });
                    initListInfo(param);
                });
                
                    
            });
            
            function initListInfo(praData){
                praData["paramMap.title"] = $("#titleName").val();
                $.post("querySysparInfo.mht",praData,initCallBack);
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
            
        </script>
    </head>
    <body style="min-width: 1000px">
        <div id="right">
            <div style="padding: 15px 10px 0px 10px;">
                <div>
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="100" height="28" class="xxk_all_a">
                                <a href="linkageinit.mht">借款目的</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                            <td width="100"   class="xxk_all_a">
                                <a href="queryAcountIndex.mht">担保机构</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                                <td width="100"   class="xxk_all_a">
                                <a href="queryInversIndex.mht">反担保方式 </a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                            <td width="100"   class="xxk_all_a">
                                <a href="queryImageIndex.mht">系统头像设置</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                            <td width="100"   class="main_alll_h2">
                                <a href="querySysparIndex.mht">系统参数设置</a>
                            </td>
                            <td width="100"   class="xxk_all_a">
                                &nbsp; <a href="querySysparList.mht">单参数设置</a>
                            </td>
                        </tr>
                    </table>
                    <div
                        style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
                        <table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
                            width="30%" border="0">
                            <tbody>
                                <tr>
                                    <td class="f66" align="left" height="36px">
                                        添加系统参数类型：
                                    </td>
                                    <td  class="f66" align="left" height="36px"><input id="titleName" name="paramMap.typename" type="text"/><span style="color:red">*</span></td>
                                </tr>
                                <tr>
                                    <td  class="f66" align="left"  height="36px">
                                          系统参数介绍：
                                    </td>
                                    <td  class="f66" align="left"  height="36px">
                                        <s:textarea rows="4" id="introduce"></s:textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2"  class="f66" align="left"  height="36px"> <input id="bt_search" type="button" value="添加"  /></td>
                                </tr>
                            </tbody>
                        </table>
                        <span id="dataInfo"> </span>
                    </div>
                </div>
            </div>
    </body>
</html>
