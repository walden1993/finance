<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>财务管理-用户银行卡管理</title>
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
				param["paramMap.userName"] = $("#userName").val();
				param["paramMap.checkUser"] = $("#checkUser").val();
				param["paramMap.realName"] = $("#realName").val();
				param["paramMap.userType"] = $("#userType").val();
				param["paramMap.modifiedTimeStart"]= $("#modifiedTimeStart").val();
				param["paramMap.modifiedTimeEnd"]= $("#modifiedTimeEnd").val();
				param["paramMap.checkTimeStart"]= $("#checkTimeStart").val();
				param["paramMap.checkTimeEnd"]= $("#checkTimeEnd").val();
				param["paramMap.cardStatus"]= "2";
		 		$.dispatchPost("queryUserBankList2.mht",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
				$("#dataInfo").html(data);
			}
			
		 	function allAuditing(){
                if(!window.confirm("确定要批量审核吗?")){
                    return;
                }
                var stIdArray = [];
                $("input[name='b_ids']:checked").each(function(){
                    stIdArray.push($(this).val());
                });
                if(stIdArray.length == 0){
                    alert("请选择需要审核的银行卡");
                    return ;
                }
                var ids = stIdArray.join(",");
                $.post("batchUpdateUserBankInfo.mht?commonId="+ids,null,function(data){
                	 window.location.href= "queryNoAuditBank.mht";
                });
            }
		 	
		    function checkAll(e){
                
                if(e.checked){
                    $(".auditing").attr("checked","checked");
                }else{
                    $(".auditing").removeAttr("checked");
                }
            }
		 	
			function show(id){
   			  var url = "queryOneBankCardInfo.mht?bankId="+id;
              ShowIframe("银行卡详情显示",url,600,400);
   			}
						
		</script>
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table  border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="xxk_all_a">
								<a href="queryUserBankInit.mht">资金管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" class="xxk_all_a">
								<a href="queryModifyBankInit.mht">用户银行卡变更</a>
							</td>
							<td  width="120" class="main_alll_h2">
								<a href="queryNoAuditBank.mht">未审核的银行卡</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td>
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
									<td class="f66" align="left" width="50%" height="36px">
										用&nbsp;户&nbsp;名：&nbsp;&nbsp;
										<input id="userName" name="paramMap.username" type="text"/>&nbsp;&nbsp;
										开户名称：
										<input id="realName" name="realName" type="text"/>&nbsp;&nbsp;
										
										&nbsp;&nbsp;
										用户类型 ：
										<s:select list="#{-1:'--请选择--',1:'个人',2:'企业'}" id="userType"></s:select>
									</td>
								</tr>
								<tr>
								  <td class="f66" align="left" width="50%" height="36px">
								        提交时间：
										<input id="modifiedTimeStart" name="paramMap.modifiedTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										&nbsp;--&nbsp;
										<input id="modifiedTimeEnd" name="paramMap.modifiedTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
									   &nbsp;&nbsp;
									  
										&nbsp;&nbsp;
										<input id="bt_search" type="button" value="搜索"  />
								  </td>
								</tr>		
								</tbody>					
						</table>
						<span id="dataInfo" > </span>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
