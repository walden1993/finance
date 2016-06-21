<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
</head>
<script type="text/javascript">
      function stickie(s,id){
    	  var param = {};
    	  param["paramMap.stickie"] = s;
    	  param["paramMap.id"] = id;
    	  $.post("hasStickie.mht",param,function(data){
    		  if(data==1){
    			  window.location.reload();
    		  }else{
    			  alert("操作失败！");
    		  }
    	  })
      }
      
      function setNewBorrow(s,id){
    	  var param = {};
    	  param["paramMap.isNew"] = s;
    	  param["paramMap.id"] = id;
    	  $.post("setNewBorrow.mht",param,function(data){
    		  if(data==1){
    			  window.location.reload();
    		  }else{
    			  alert("操作失败！");
    		  }
    	  })
      }
      
      function related(id){
    	  window.location.href = "related.mht?id="+id;
      }
</script>
</html>
<div style="padding: 10px 10px 0px 0px;" >
		<table id="gvNews" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th style="width: 4%;" scope="col">
						序号
					</th>
					<th style="width: 4%;" scope="col">
						借款ID
					</th>
					<th style="width: 8%;" scope="col">
						用户名
					</th>
					<th style="width: 7%;" scope="col">
						真实姓名
					</th>
					<th style="width: 10%;" scope="col">
						企业全称
					</th>
					<th style="width: 6%;" scope="col">
						用户类型
					</th>
					<th style="width: 8%;" scope="col">
						通过认证数量
					</th>
					<th style="width: 8%;" scope="col">
						标的类型
					</th>
					<th style="width: 8%;" scope="col">
						借款标题
					</th>
					<th style="width: 8%;" scope="col">
						借款金额
					</th>
					<th style="width: 5%;" scope="col">
						利率
					</th>
					<th style="width: 5%;" scope="col">
						期限
					</th>
					<th style="width: 6%;" scope="col">
					   	 筹标期限
					</th>
					<th style="width: 5%;" scope="col">
					   	状态
					</th>
					<th style="width: 14%;" scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="14">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
				<s:set name="acounts" value="#request.pageNum"/>
				<s:iterator value="pageBean.page" var="bean" status="s">
					<tr class="gvItem">
						<td>
							<s:property value="#s.count+#acounts"/>
						</td>
						<td align="center">
							${bean.id}
						</td>
						<td align="center">
							${username}
						</td>
						<td>
							<s:if test="#bean.realName!=null && #bean.realName!=''">${realName}</s:if>
							<s:else>--</s:else>
						</td>
						<td>
							<s:if test="#bean.orgName!=null && #bean.orgName!=''">${orgName}</s:if>
							<s:else>--</s:else>
						</td>
						<td>
							<s:if test="#bean.userType==1">个人</s:if>
							<s:if test="#bean.userType==2">企业</s:if>
						</td>
						<td>
						   <s:if test="#bean.counts==null">
							<span class="require-field">0</span>个
							</s:if>
							<s:else>
							 ${counts}个
							</s:else>
						</td>
						<td>
							<s:if test="#bean.borrowWay==1">
							净值借款
							</s:if>
							<s:elseif test="#bean.borrowWay==2">
							秒还借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==3">
							信用借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==4">
							实地考察借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==5">
							机构担保借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==6">
							流转标借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==6">
							流转标借款
							</s:elseif>
							<s:elseif test="#bean.borrowWay==7">
							债权转让借款
							</s:elseif>
						</td>
						<td>
						<a href="../financeDetail.mht?id=${id}"  target="_blank" >${borrowTitle}</a>
					</td>
						<td>
							${borrowAmount}
					</td>
						<td>
							${annualRate}%
					</td>
						<td>
							${deadline }
							<s:if test="%{#bean.isDayThe ==1}">个月</s:if>
							<s:else>天</s:else>
					</td>
					<td>
					
					
					<s:if test="%{#bean.borrowShow==2}">${deadline }个月</s:if>
							<s:else>
							<s:if test="%{#bean.raiseTerm ==0}">无期限</s:if>
							<s:else>
							${raiseTerm }天 </s:else>
							</s:else>
						
					</td>
					<td>
							<s:if test="#bean.flag==0">
					  		等待资料认证
					        </s:if>
							<s:elseif test="#bean.borrowStatus==1">
							初审中
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==2">
							招标中
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==3">
							满标
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==4">
							还款中
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==5">
							已还完
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==6">
							流标
							</s:elseif>
					</td>
					<td>
					           <a  href="javascript:void(0)"  onclick="related(${bean.id})">相关材料&nbsp;&nbsp;</a>
					         <s:if test="#bean.stickie==1"><a  href="javascript:void(0)"  onclick="stickie(0,${bean.id})">置顶&nbsp;&nbsp;</a></s:if>
					         <s:else><a  href="javascript:void(0)"  onclick="stickie(1,${bean.id})">不置顶&nbsp;&nbsp;</a></s:else>
					        <s:if test="#bean.borrowStatus==1">
					          <!-- add by houli  等待资料的借款不能进行审核 -->
					          <s:if test="#bean.flag==0">
					             <a href="borrowwdetail.mht?id=${id}">查看</a>
					          </s:if>
					          <s:else>
								 <a href="borrowfdetail.mht?id=${id}">查看</a>
					          </s:else>
							</s:if>
							<s:elseif test="#bean.borrowStatus==2">
								<a href="borrowTenderInDetail.mht?id=${id}">查看</a>
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==3">
								<a href="borrowFullScaleDetail.mht?id=${id}">查看</a>
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==4">
							---
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==5">
							---
							</s:elseif>
							<s:elseif test="#bean.borrowStatus==6">
							<a href="borrowFlowMarkDetail.mht?id=${id}">查看</a>
							</s:elseif>
							
							<s:if test="#bean.isNew==1"><a  href="javascript:void(0)"  onclick="setNewBorrow(0,${bean.id})">取消新手标&nbsp;&nbsp;</a></s:if>
					         <s:else><a  href="javascript:void(0)"  onclick="setNewBorrow(1,${bean.id})">设为新手标&nbsp;&nbsp;</a></s:else>
							
					</td>
					</tr>
				</s:iterator>
				</s:else>
			</tbody>
		</table>
</div>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize}" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
