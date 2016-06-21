<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<div class="pub_tb bottom"><h3>三好资本公告</h3></div><div class="clearfix"></div>
<ul class="n_notic_list clearfix mt20">
                 <s:if test="pageBean.page==null || pageBean.page.size==0">
                      没有网站公告
                </s:if>
                <s:else>
                  <s:iterator value="pageBean.page" var="bean" status="sta">
                   <div class="content_list">                   
                    <div class="left news_ctx2">
                        <div class="news_ctxTitle">
                            <a href="frontNewsDetails.mht?id=${bean.id}" target="_blank">${bean.title}</a>
                        </div>
                        <div class="gfgg_ctxIntro">
                            <a href="frontNewsDetails.mht?id=${bean.id}" target="_blank">${bean.content}</a>
                        </div>
                        <div class="news_ctxPdate">
                        <span>${bean.publishTime}</span>
                            <span>浏览次数：${bean.visits}</span>
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
                 </s:iterator>
                </s:else>
                </ul>
               <div class="page">
     <shove:page curPage="${pageBean.pageNum}" showPageCount="10" funMethod="doqueryGfggJumpPage" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
    </div>
        
        