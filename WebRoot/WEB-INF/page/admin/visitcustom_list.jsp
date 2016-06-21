<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="css/inside.css"  rel="stylesheet" type="text/css" />
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<div style="padding: 15px 10px 0px 10px;">
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 35px;" scope="col">
						序号
					</th>
					<th style="width: 150px;" scope="col">
						注册时间
					</th>
					<th style="width: 150px;" scope="col">
						推荐人
					</th>
					<th style="width: 150px;" scope="col">
						用户名
					</th>
					<th style="width: 150px;" scope="col">
						姓名
					</th>
					<th style="width: 150px;" scope="col">
						性别
					</th>
					<th style="width: 150px;" scope="col">
						手机号
					</th>
					<th style="width: 200px;" scope="col">
						最后一次登录时间
					</th>
					<th style="width: 150px;" scope="col">
						最近一次投资时间
					</th>
					<th style="width: 150px;" scope="col">
						最近一次投资金额
					</th>
					<th style="width: 150px;" scope="col">
						累计投资金额
					</th>
					<th style="width: 150px;" scope="col">
						账户可用余额
					</th>
					<th style="width: 150px;" scope="col">
						操作记录
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="13">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="counts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem" onclick="ajaxOne('${id}')">
						<td>
							<s:property value="#s.count+#counts"/>
						</td>
						<td align="center">
							${createTime}
						</td>
						<td>
							${redName}
						</td>
						<td>
						${username}
						</td>
						<td>
						${realName} 
						</td>
						<td>
						${sex }
						</td>
						<td>
						${mobilePhone}
						</td>
						<td>
						    ${lastDate}
						</td>
						<td>
						${investTime}
						</td>
						<td>
						${investAmount}
						</td>
						<td>
						${investSum }
						</td>
						<td>
						${usableSum }
						</td>
						<td>
						<a href="#" onclick="addNewVisit('${id}')">新增</a>
						</td>
						
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>

<br/><br/>
<div id="visitDetail">

</div>

<script type="text/javascript">
function ajaxOne(id){
	param["paramMap.id"] = id;
	$.dispatchPost("queryVisitCustom.mht",param,callbackFun2);
}

function callbackFun2(data)
{
  
  if (data.list.length == 0){
	  $("#visitDetail").html("<font color='#ff0000'>该用户没有回访记录!</font>");
	  return;
  }
  var sb = "<table style='width: 100%; color: #333333;' cellspacing='1' cellpadding='3' bgcolor='#dee7ef' border='0'>";
  sb += "<tr><th>沟通时间</th><th>联系方式</th><th>沟通类型</th><th>客户来源</th><th>沟通内容</th><th>操作</th></tr>";
  for (var i =0; i < data.list.length; i++ ){
	    sb += "<tr class='gvItem'><td>"+data.list[i].createTime+
	    "</td><td>"+data.list[i].linkType+
	    "</td><td>"+data.list[i].visitType+"</td><td>"+data.list[i].knowType+"</td><td>"+data.list[i].content+
	    "</td><td><a href='#' onclick='visitDetail("+data.list[i].id+");'>查看</a> <a href='#' onclick=editContent("+data.list[i].id+",'"+data.list[i].createdBy+"')>编辑</a></td></tr>";
	  
	  }
  sb +="</table>";
  $("#visitDetail").html(sb);
  
}

function addNewVisit(userId){
	window.location.href="gotoVisitCustomAdd.mht?userId="+userId;
}
function editContent(id,createdBy){
	var loginName = "${sessionScope.admin.userName}";
	if (createdBy != loginName){
		alert("只能编辑自己的创建的记录！");
		return;
	}
	
	window.location.href="gotoVisitCustomEdit.mht?id="+id;
}
function visitDetail(id){
	var url ="visitCustomDetail.mht?id="+id;
	window.open(url,'newwindow','height=500,width=800,left=200,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
}
</script>
