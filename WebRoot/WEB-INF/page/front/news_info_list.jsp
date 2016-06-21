<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
		<div class="w1002 n_about_mission clearfix">
			<!--  div class="news_bar"> left news_content_wrap
				<h3>官方公告</h3>
			</div> -->
			<div class="news_content">
				<div class="content_bar"></div>
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
               <div class="paging">
     <shove:page curPage="${pageBean.pageNum}" showPageCount="10" funMethod="doqueryGfggJumpPage" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
    </div>
                </div>
                <div class="clear"></div>
			</div>
		
		