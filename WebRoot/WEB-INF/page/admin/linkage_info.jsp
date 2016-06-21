<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
	<div id="right">
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						借款目的
					</th>
					<th scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
					<s:iterator value="pageBean.page" var="bean" status="st">
						<tr class="gvItem">
							<td>
							${name }
							</td>
							<td>
								<a style="cursor: pointer;" ids="${id }" onclick="javascript:fff(this)">修改</a> 
								&nbsp;&nbsp;
							 <a style="cursor: pointer;" id="shanchu" onclick="javascript:window.location.href='deletetage.mht?id=${id }'">删除</a>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
<script type="text/javascript">

   function fff(data){
                var t = $(data).attr("ids");
		    	ShowIframe("修改借款目的",'updatetage.mht?id='+t,500,200);
		    }
		    function ffff(f,d){
		    	var param = {};
		    	param["paramMap.id"]=d;
		    	param["paramMap.tagd"] = f;
		    	$.post("updatetagereal.mht",param,function(data){
		    	var callBack = data.msg;
		    	if(data==1){
		    	  alert("修改成功");
		    	  window.location.reload();
		    	}else if(data==0){
		    	alert("修改失败");
		    	}else  if(callBack == undefined || callBack == ''){
		                 $('#right').html(data);
		                 $(this).show();
		                 }
		    	});
		      ClosePop();
		    }

</script>
<script>

</script>




	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
