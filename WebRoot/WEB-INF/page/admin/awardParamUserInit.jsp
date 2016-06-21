<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>财务管理-充值记录</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../css/admin/popom.js"></script>
		<script type="text/javascript">
			$(function(){
				param["pageBean.pageNum"] = 1;
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
				
				
			});
			
			function initListInfo(param){
				param["paramMap.termNo"] = $("#termNo").val();
				param["paramMap.username"] = $("#username").val();
				param["paramMap.status"] = $("#status").val();
				param["paramMap.paramId"] = $("#paramId").val();
				param["paramMap.sendAward"]= $("#sendAward").val();
				param["paramMap.startTime"] = $("#startTime").val();
				param["paramMap.endTime"] = $("#endTime").val();
				 if($("#startTime").val() &&   $("#endTime").val()  &&   $("#startTime").val()> $("#endTime").val()){
		                alert("起止日期不能小于截止日期");
		                return;
		            }
		 		$.dispatchPost("awardParamUserList.mht",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
		 	
		 	function sendAwards(){
                if(!window.confirm("确定要派奖吗?")){
                    return;
                }
                var stIdArray = [];
                $("input[name='cb_ids']:checked").each(function(){
                    stIdArray.push($(this).val());
                });
                if(stIdArray.length == 0){
                    alert("请选择需要派奖的用户");
                    return ;
                }
                var ids = stIdArray.join(",");
                $.dispatchPost("sendAward.mht?",{commonId:ids},function(data){
                    initListInfo(param);
                })
            }
            
            function sendAward(id){
                if(!window.confirm("确定要派奖吗?")){
                    return;
                }
                $.dispatchPost("sendAward.mht?",{commonId:id},function(data){
                	initListInfo(param);
                })
            }
		 	
		    function checkAll(e){
                if(e.checked){
                    $(".helpId").attr("checked","checked");
                }else{
                    $(".helpId").removeAttr("checked");
                }
            }
		</script>
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="xxk_all_a">
								<a href="awardManage.mht">抽奖管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							
							<td width="120" class="main_alll_h2">
								<a href="awardParamUserInit.mht?termId=${termId}">抽奖明细</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="25%" height="36px">
										抽奖期数：&nbsp;&nbsp;
										<s:select id="termNo" list="#request.award"  value="#request.termId"  listKey="termNo" listValue="termNoStr" headerKey="" headerValue="--请选择--" />
                                        &nbsp;&nbsp;
										用户名：&nbsp;&nbsp;
                                        <input id="username"  type="text"/>
                                        &nbsp;&nbsp;
                                        状态：
                                        <s:select id="status"   list="#{'':'-全部-',0:'未中奖',1:'中奖'}" />
                                        &nbsp;&nbsp;
										派奖状态：
                                        <s:select id="sendAward" list="#{'':'-全部-',0:'未派奖',1:'已派奖'}" ></s:select>
                                        &nbsp;&nbsp;
                                        奖品名称:
                                        <s:select id="paramId" list="#request.awardParams"   listKey="id" listValue="awardName" headerKey="" headerValue="--请选择--" />
                                        &nbsp;&nbsp;
									</td>
								</tr>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										抽奖时间：
                                        <input id="startTime" class="Wdate"  name="paramMap.startTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                                        &nbsp;--&nbsp;
                                        <input id="endTime"  class="Wdate"  name="paramMap.endTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                                       &nbsp;&nbsp;
										<input id="bt_search" type="button" value="查 找"  />
										 &nbsp;&nbsp;
                                            <input id="excel" onclick="" type="button"
                        value="导出EXCEL" name="btnExportExcel" />
                                        &nbsp;&nbsp;
                                       
									</td>
								</tr>
							</tbody>
						</table>
						<span id="dataInfo"> </span>
					</div>
				</div>
			</div>
			</div>
	</body>
</html>
