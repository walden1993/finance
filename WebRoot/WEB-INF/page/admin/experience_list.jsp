<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css" />

<script type="text/javascript" src="../css/popom.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
			<th style="width: 100px;" scope="col">
					序号
				</th>
				<th style="width: 100px;" scope="col">
					批次号
				</th>
				<th style="width: 100px;" scope="col">
					券号
				</th>
				<th style="width: 100px;" scope="col">
					金额
				</th>
				<th style="width: 100px;" scope="col">
					用户名
				</th>
				<th style="width: 160px;" scope="col">
					使用项目
				</th>
				<th style="width: 160px;" scope="col">
                   使用时间
                </th>
				<th style="width: 160px;" scope="col">
					绑定时间
				</th>
				<th style="width: 100px;" scope="col">
					状态
				</th>
				<th style="width: 100px;" scope="col">
					操作
				</th>
			</tr>
			<s:if test="pageBean.page==null || pageBean.page.size==0">
				<tr align="center" class="gvItem">
					<td colspan="10">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
							${(pageBean.pageNum-1)*20+s.index+1 }
						</td>
						<td>
							${batchId }
						</td>
						<td>
							${ticketNo }
						</td>
						<td>
							${amount }
						</td>
						<td>
							<s:if test="#bean.username==null || #bean.username==''">/</s:if><s:else>${username }</s:else>
						</td>
						<td>
							<s:if test="#bean.borrowTitle==null || #bean.borrowTitle==''">/</s:if><s:else>${borrowTitle }</s:else>
						</td>
						<td>
							<s:if test=" #bean.useTime==null">/</s:if><s:else>${fn:substring(useTime,0,10)}&nbsp;${fn:substring(useTime,10,19)}</s:else>
                        </td>
						<td>
							<s:if test="#bean.bindingTime==null">/</s:if><s:else>${fn:substring(bindingTime,0,10)}&nbsp;${fn:substring(bindingTime,10,19)}</s:else>
						</td>
						<td>
							<s:if test="#bean.ticketStatus==0">未绑定</s:if>
							<s:if test="#bean.ticketStatus==1">冻结</s:if>
							<s:if test="#bean.ticketStatus==2">已使用</s:if>
							<s:if test="#bean.ticketStatus==3">已绑定</s:if>
						</td>
						<td>
							<s:if test="#bean.ticketStatus==0">
                                <a href="javascript:experienceBind('${ticketId }','${ticketNo }')">绑定</a>&nbsp;&nbsp;<a href="javascript:experienceUp('1','${ticketId }','冻结')">冻结</a>
                            </s:if>
                            <s:if test="#bean.ticketStatus==1">
                                <a href="javascript:experienceUp('0','${ticketId }','解冻')">解冻</a>
                            </s:if>
							<s:if test="#bean.ticketStatus==3">
                                <a href="javascript:experienceUp('1','${ticketId }','冻结')">冻结</a>
                            </s:if>
						</td>
					</tr>
				</s:iterator>
			</s:else>
		</tbody>
	</table>
</div>

<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>
	<script type="text/javascript">
    function experienceUp(status,id,str){
        if(!confirm("您确认要"+str+"此体验券？")){
           return ;
        }
        var param = {};
        param["paramMap.status"] = status;
        param["paramMap.id"] = id;
        $.post("experienceUp.mht",param,function(data){
            if(data==-1){
                alert("参数丢失。");
            }else if(data==-2){
                alert("操作失败。");
            }else if(parseInt(data) ==parseInt(status)){
                alert(str+"成功。");
                window.location.reload();
            }else{
                alert("出现异常。");
            }
        })
    }
    function experienceBind(id,ticketNo){
    	var url= "experienceBindInit.mht?paramMap.id="+id+"&paramMap.ticketNo="+ticketNo;
        $.jBox("iframe:"+url, {
        title: "绑定体验券",
        width: 400,
        height: 350,
        buttons: { '关闭': false }
        });
    }
	
	</script>
