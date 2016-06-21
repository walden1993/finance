<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<div class="pub_tb bottom"><s:if test="#request.newsType==1"><h3>三好资本动态</h3></s:if><s:elseif test="#request.newsType==2"><h3>互联网金融</h3></s:elseif><s:else><h3>动态</h3></s:else></div><div class="clearfix"></div>
<div class="m20">
    <s:if test="pageBean.page==null || pageBean.page.size==0">
               <div align="center">
                  <span>暂无数据</span>
               </div>
        </s:if>
        
        <s:else>
    <s:iterator value="pageBean.page" var="bean">
                 <dl class="mt_dl">
        <dt><a href="frontMedialinkId.mht?id=${bean.id}"  target="_blank"><img src="${bean.imgPath}" width="160" height="80" /></a></dt>
        <dd>
          <h2> <a href="frontMedialinkId.mht?id=${bean.id}" target="_blank">${bean.title}</a></h2>
          <p>${bean.content}</p>
          <p class="grayc">${bean.publishTime}</p>
        </dd>
      </dl>
      </s:iterator>
     </s:else>
            <div class="page">
            <s:if test="#request.newsType==1||#request.newsType==2">
            <shove:page curPage="${pageBean.pageNum}" showPageCount="10" funMethod="doMtbdJumpPage1" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
            </s:if>
            <s:else>
            <shove:page curPage="${pageBean.pageNum}" showPageCount="10" funMethod="doMtbdJumpPage" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
            </s:else>
     
    </div>
        </div>



