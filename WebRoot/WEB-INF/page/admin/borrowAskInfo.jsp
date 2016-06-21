<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
<div>
    <table id="help" style="width: 100%; color: #333333;" cellspacing="1"
        cellpadding="3" bgcolor="#dee7ef" border="0"
    >
        <tbody>
            <tr class=gvHeader>
                <th  style="width: 8%"scope="col">借款类型</th>
                <th style="width: 8%" scope="col">联系人姓名</th>
                <th style="width:8%" scope="col">联系人手机</th>
                <th style="width: 8%" scope="col">借款方类型</th>
                <th style="width: 8%" scope="col">借款金额</th>
                <th style="width: 8%" scope="col">借款期限(月)</th>
                <th style="width: 8%" scope="col">是否分配</th>
                <th style="width: 8%" scope="col">是否有抵押物</th>
                <th style="width: 8%" scope="col">分配时间</th>
                <th style="width:8%" scope="col">配分到</th>
                <th style="width: 8%" scope="col">申请时间</th>
                <th style="width: 12%" scope="col">操作</th>
            </tr>
            <s:if test="pageBean.page==null || pageBean.page.size==0">
                <tr align="center" class="gvItem">
                    <td colspan="12">暂无数据</td>
                </tr>
            </s:if>
            <s:else>
                <s:set name="counts" value="#request.pageNum" />
                <s:iterator value="pageBean.page" var="bean" status="s">
                    <tr class="gvItem">
                        <td>${borrowWay }</td>
                        <td>${contact_name }</td>
                        <td>${contact_phone }</td>
                        <td><s:if test="#bean.borrowerType==1">个人</s:if><s:else>企业</s:else></td>
                        <td>${borrowAmount }</td>
                        <td>${borrowLine }</td>
                        <td><s:if test="#bean.hasDistribution==0">是</s:if><s:else>否</s:else></td>
                        <td><s:if test="#bean.hasMortgage==0">是</s:if><s:else>否</s:else></td>
                        <td>${distributionDate }</td>
                        <td>${userName }</td>
                        <td>${createTime}</td>
                        <td>
                                <s:if test="#bean.hasDistribution==1"><a href="javascript:void(0)"    onclick="fenpei('${id }')">分配</a>&nbsp;&nbsp;&nbsp;</s:if>
                               <a href="javascript:void(0)"  onclick="checkDetail('${id}')">查看</a>
                        </td>
                    </tr>
                </s:iterator>
            </s:else>
              <%--<tr>
              <td><input id="excel" type="button" value="导出Excel" name="excel"/>
               </td>
                <td>总条数：</td>
                <td>${pageBean.totalNum}</td>
            </tr> --%>
        </tbody>
    </table>
</div>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script>
      function checkDetail(id){
    	  $.jBox("iframe:borrowAskCheck.mht?id="+id, {
    	        title: "借款申请详情",
    	        width: 700,
    	        height: 450,
    	        buttons: { '关闭': true }
    	    });
      }
      function fenpei(id){
    	  $.jBox("iframe:borrowAskFenpeiInit.mht?id="+id, {
    	        title: "分配",
    	        width: 500,
    	        height: 200,
    	        buttons: { '关闭': true }
    	    });
      }
</script>
<shove:page curPage="${pageBean.pageNum}" showPageCount="10"
    pageSize="${pageBean.pageSize }" theme="jsNumber"
    totalCount="${pageBean.totalNum}" 
></shove:page>
