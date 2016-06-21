<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE>
<html>
	<head>
	<title>${paramMap.title}</title>
		<jsp:include page="/include/head.jsp"></jsp:include>
	</head>
	<body>
		<jsp:include page="/include/top.jsp"></jsp:include>
		<div class="w950 mt20 mb20">
			<div class="newsView_content_wrap">
				<div class="newsView_bar">
					<span class="newsView_local"><a target="_self"  href="queryAboutInfo.mht?msg=99">关于我们</a> > <a href="queryAboutInfo.mht?msg=100" >官方公告</a> > ${paramMap.title}</span>
				</div>
				<div class="newsView_content">
					<div class="content_bar"></div>
					<div class="newsView_list">
						<div class="newsView_title">
							${paramMap.title}
						</div>
						<div class="newsView_pdate">
							<span>发布时间：${paramMap.publishTime}</span>
							<span>浏览次数：${paramMap.visits}</span>
							<span>发布者：${paramMap.username}</span>
						</div>
						<div class="newsView_ctx">
							${paramMap.content }
						</div>

						<div class="newsView_brief">
							<div class="fl ml10">
								<s:if test="#request.lastDate!=null">
         上一篇：<a style="max-width: 30px; overflow: hidden;"
										href="frontNewsDetails.mht?id=${request.lastDate.id}">${request.lastDate.title}</a>
								</s:if>
								<s:else>
									<a>&nbsp;&nbsp;&nbsp;</a>
								</s:else>
							</div>
							<div class="fr mr10">
								<s:if test="#request.previousDate!=null">
      下一篇：<a style="max-width: 30px; overflow: hidden;"
										href="frontNewsDetails.mht?id=${request.previousDate.id}">${request.previousDate.title}</a>
								</s:if>
								<s:else>
									<a>&nbsp;&nbsp;&nbsp;</a>
								</s:else>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
	</body>
</html>