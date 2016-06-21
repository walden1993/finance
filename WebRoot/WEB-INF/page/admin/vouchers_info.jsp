<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div>
	<table id="help" style="width: 100%; color: #333333;" cellspacing="1"
		cellpadding="3" bgcolor="#dee7ef" border="0">
		<tbody>
			<tr class=gvHeader>
				<th style="width: 100px;" scope="col">
					批次号
				</th>
				<th style="width: 100px;" scope="col">
					标题
				</th>
				<th style="width: 100px;" scope="col">
					券数
				</th>
				<th style="width: 100px;" scope="col">
					金额
				</th>
				<th style="width: 100px;" scope="col">
					类型
				</th>
				<th style="width: 100px;" scope="col">
					申请部门
				</th>
				<th style="width: 100px;" scope="col">
					申请人
				</th>
				<th style="width: 100px;" scope="col">
					操作人
				</th>
				<th style="width: 260px;" scope="col">
					开始时间
				</th>
				<th style="width: 260px;" scope="col">
					过期时间
				</th>
				<th style="width: 260px;" scope="col">
					创建时间
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
					<td colspan="13">
						暂无数据
					</td>
				</tr>
			</s:if>
			<s:else>
				<s:set name="counts" value="#request.pageNum" />
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
							${id }
						</td>
						<td>
							${title }
						</td>
						<td>
							${qty }
						</td>
						<td>
							${amount }
						</td>
						<td>
							<s:if test="#bean.type==0">体验券</s:if>
							<s:if test="#bean.type==1">现金券</s:if>
						</td>
						<td>
							${applyDept }
						</td>
						<td>
							${applyBy }
						</td>
						<td>
							${createBy }
						</td>
						<td>
							${fn:substring(enableDate,0,10)}&nbsp;${fn:substring(enableDate,10,19)}
						</td>
						<td>
							${fn:substring(disableDate,0,10)}&nbsp;${fn:substring(disableDate,10,19)}
						</td>
						<td>
							${fn:substring(createTime,0,10)}&nbsp;${fn:substring(createTime,10,19)}
						</td>
						<td>
							<s:if test="#bean.status==1">正常</s:if>
							<s:if test="#bean.status==2">冻结</s:if>
						</td>
						<td>
							<a href="experienceInit.mht?batch=${id}">查看</a>
							<s:if test="#bean.status==1">
								<a href="javascript:experienceUp('2','${id }','冻结')" >冻结</a>
							</s:if>
							<s:if test="#bean.status==2">
								<a href="javascript:experienceUp('1','${id }','解冻')">解冻</a>
							</s:if>
						</td>
					</tr>
				</s:iterator>
			</s:else> 
		</tbody>
	</table>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
    <script type="text/javascript">
             function experienceUp(status,id,str){
                 if(!confirm("您确认要"+str+"此批次体验券？")){
                    return ;
                 }
                 var param = {};
                 param["paramMap.status"] = status;
                 param["paramMap.id"] = id;
                 param["paramMap.batch"] = "batch";
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
    </script>
</div>

<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
	pageSize="${pageBean.pageSize }" theme="jsNumber"
	totalCount="${pageBean.totalNum}"></shove:page>
	
