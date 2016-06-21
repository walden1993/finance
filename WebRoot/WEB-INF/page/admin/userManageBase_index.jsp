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
		<script  language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){
				
				initListInfo(param);
				$("#bt_search").click(function(){
				 param["pageBean.pageNum"] = 1;
				 param["paramMap.userName"] = $("#userName").val();
				 param["paramMap.realName"] = $("#realName").val();
				 param["paramMap.orgName"] = $("#orgName").val();
				 param["paramMap.userType"] = $("#userType").val();
				 param["paramMap.createTimeStart"] = $("#createTimeStart").val();
				 param["paramMap.lastLoginTimeStart"] = $("#lastLoginTimeStart").val();
				 param["paramMap.createTimeEnd"] = $("#createTimeEnd").val();
                 param["paramMap.lastLoginTimeEnd"] = $("#lastLoginTimeEnd").val();
				 param["paramMap.refereName"] = $("#refereName").val();
				 param["paramMap.sms"] = $("#sms").val();
				 
				 if( param["paramMap.createTimeStart"]>param["paramMap.createTimeEnd"]){
					 alert("起始日期不能小于截至日期")
					 return;
				 }
				 if( param["paramMap.lastLoginTimeStart"]>param["paramMap.lastLoginTimeEnd"]){
					 alert("起始日期不能小于截至日期")
                     return;
                 }
				 
			     initListInfo(param);
				});
			
					
			});
			
			function initListInfo(praData){
			    
		 		$.post("queryUserManageInfo.mht",praData,initCallBack);
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
								<a href="queryUserManageInfoIndex.mht">用户基本信息</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
                            <td width="100" id="today" height="28"   class="xxk_all_a">
                                <a href="addUserIndex.mht">手动添加用户</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                            <td width="100" id="today" height="28"   class="xxk_all_a">
                                <a href="abnormalUserIndex.mht">异常用户</a>
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
									<td class="f66" align="left" width="100%" height="36px">
										用户名 ：
										<input id="userName" name="paramMap.userName" style="width: 150px;"     type="text"/>&nbsp;&nbsp;
										真实姓名 ：
										<input id="realName" name="paramMap.realName"  style="width: 60px;"  type="text"/>&nbsp;&nbsp;
										手机号：
										<input id="sms" name="paramMap.sms"  style="width: 110px;"  type="text"/>&nbsp;&nbsp;
										企业全称：
										<input id="orgName" name="paramMap.orgName"  style="width: 60px;"  type="text"/>&nbsp;&nbsp;&nbsp;
										用户类型：
										<s:select list="#{-1:'--全部--',1:'个人',2:'企业'}" id="userType"></s:select>
										邀请人用户名：
                                        <input id="refereName" name="paramMap.refereName"  style="width: 50px;"  type="text"/>&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
								<tr>
                                    <td class="f66" align="left" width="80%" height="36px">
                                        创建时间：
                                        <input id="createTimeStart"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  name="paramMap.createTime" type="text"/>&nbsp;&nbsp;
                                        至<input id="createTimeEnd"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  name="paramMap.createTime" type="text"/>&nbsp;&nbsp;
                                        最后登陆时间：
                                        <input id="lastLoginTimeStart"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"   name="paramMap.lastLoginTime" type="text"/>&nbsp;&nbsp;
                                        至<input id="lastLoginTimeEnd"  class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"   name="paramMap.lastLoginTime" type="text"/>&nbsp;&nbsp;
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
