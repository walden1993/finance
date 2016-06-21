<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<script type="text/javascript">
       function mediaDetail(param){
      
          $.post("frontMediaReportDetails.mht","id="+param,function(data){
                  $("#showcontent").html("");
	              $("#showcontent").html("<h3>"+data.title+"</h3>"+
	                             "<p class='time'></p>"+
	                           "<p class='zw'>"+data.content+"</p>");
          });
       }
</script>

		<div id="showcontent" class="left news_content_wrap">
			<div class="news_bar">
				<s:if test="#request.newsType==1"><h3>三好资本动态</h3></s:if><s:elseif test="#request.newsType==2"><h3>互联网金融</h3></s:elseif><s:else><h3>动态</h3></s:else>
			</div>
			<div class="news_content">
				<div class="content_bar"></div>
	
			<s:if test="pageBean.page==null || pageBean.page.size==0">
			   <div align="center">
				  <span>暂无数据</span>
			   </div>
		</s:if>
   	 <s:else>
	<s:iterator value="pageBean.page" var="bean">
				<div class="content_list">
					<div class="left">
						<a href="frontMedialinkId.mht?id=${bean.id}"  target="_blank"><img src="${bean.imgPath}" width="160" height="80" /></a>
					</div>
					<div class="left news_ctx">
						<div class="news_ctxTitle">
							<a href="frontMedialinkId.mht?id=${bean.id}" target="_blank">${bean.title}</a>
						</div>
						<div class="news_ctxIntro">
							<a href="frontMedialinkId.mht?id=${bean.id}" target="_blank">${bean.content}</a>
						</div>
						<div class="news_ctxPdate">
							<span>${bean.publishTime}</span>							
						</div>
					</div>
					<div class="clear"></div>
				</div>
			  </s:iterator>
     </s:else>
			</div>
			<div class="paging">
			<s:if test="#request.newsType==1||#request.newsType==2">
			<shove:page curPage="${pageBean.pageNum}" showPageCount="10" funMethod="doMtbdJumpPage1" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
			</s:if>
			<s:else>
			<shove:page curPage="${pageBean.pageNum}" showPageCount="10" funMethod="doMtbdJumpPage" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
			</s:else>
     
    </div>
		</div>



