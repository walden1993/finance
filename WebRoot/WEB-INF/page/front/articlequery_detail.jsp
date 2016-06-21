<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
	    <title>${paramMap.title}</title>
		<jsp:include page="/include/head_gywm.jsp"></jsp:include>
		<script type="text/javascript" src="script/front.js"></script>
		<link rel="stylesheet" type="text/css" href="css/new_inside.css">
	</head>

	<body>
		
		
		<!--介绍-->
<div class="ny_content">
<div class="w1002 location"><a href="articleList.mht">小文章</a> &gt;  ${paramMap.title}</div>
<div class="w1002 clearfix pb15">
  <div class="clearfix fl det_xx">
      <div class="tc f18 n_abt_detail_tit"><h1><b>${paramMap.title}</b></h1></div>
      <div class="laiyuan"><span>时间：${paramMap.publishTime}</span><span>来源：${paramMap.source}</span></div>
      <div class="abt_detail_info">
          ${paramMap.content }
      </div>  
     <div class="newsView_brief mt20 mb20">
                            <div class="left">
                                 
                            </div>
                            <div class="right">
                                
                            </div>
                            <div class="clear"></div>
                        </div>
      <div class="category clearfix">
	      <s:if test="#request.labels!=null && #request.labels.size()>0">
	               <s:iterator value="#request.labels"  var="label"  status="st">
	                        <s:if  test="#request.paramMap.newsType==1">
	                        <a href="queryAboutInfo.mht?msg=103&lable=${label.id}">${label.selectName}</a>
	                        </s:if>
	                        <s:else>
	                        <a href="queryAboutInfo.mht?msg=101&lable=${label.id}">${label.selectName}</a>
	                        </s:else>
	               </s:iterator>
	      </s:if>
      </div>
      <div class="clearfix share">
      

<script>
    window._bd_share_config = {
        common : {
            bdText : '${paramMap.title}',
            bdDesc : '${paramMap.title}',
            bdUrl : '<%=application.getAttribute("basePath")%>',   
            bdPic : '${paramMap.imgPath}'
        },
        share : [{
            "bdSize" : 16
        }],
        image : [{
            viewType : 'list',
            viewPos : 'top',
            viewColor : 'black',
            viewSize : '16',
            viewList : ['qzone','tsina','huaban','tqq','renren']
        }],
        selectShare : [{
            "bdselectMiniList" : ['qzone','tqq','kaixin001','bdxc','tqf']
        }]
    }
    with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];
</script>
      
      <%--
        <div class="bshare-custom"><a title="分享到" href="http://www.bShare.cn/" id="bshare-shareto"  data="'bdDes':'${paramMap.content}','text':'${paramMap.content}', 'title':'${paramMap.title}'"   class="bshare-more">分享到</a><a title="分享到QQ空间" class="bshare-qzone">QQ空间</a><a title="分享到新浪微博" class="bshare-sinaminiblog">新浪微博</a><a title="分享到人人网" class="bshare-renren">人人网</a><a title="分享到腾讯微博" class="bshare-qqmb">腾讯微博</a><a title="分享到网易微博" class="bshare-neteasemb">网易微博</a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a><span class="BSHARE_COUNT bshare-share-count">0</span></div>
        <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
        <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js">
    </script>
      --%></div>
      
      
  </div>
	  
</div>
</div>
		<div align="center"><a href="index.mht">p2p网贷平台</a></div>
		
		<!-- 引用底部公共部分 -->
	</body>
</html>