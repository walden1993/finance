<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<!DOCTYPE html>
<html lang="zh-cn">

	<head>
	<c:set value="${paramMap.title}" var="title" scope="request"></c:set>
		<jsp:include page="/include/head.jsp"></jsp:include>
	</head>

	<body>
		<jsp:include page="/include/top.jsp"></jsp:include>
		
		
		<!--介绍-->
<div class="ny_content">
<div class="w950 pt15 pb15"><a href="<%=application.getAttribute("basePath")%>">P2P网贷</a> > <a href="queryAboutInfo.mht?msg=<s:if test="#request.paramMap.newsType==1">103</s:if><s:else>101</s:else>">平台资讯</a> > ${paramMap.title}</div>
<div class="w950 clearfix pb15">
  <div class="clearfix fl det_xx">
      <div class="text-center f18 n_abt_detail_tit"><h3><b>${paramMap.title}</b></h3></div>
      <div class="laiyuan"><span>时间：${paramMap.publishTime}</span><span>来源：${paramMap.source}</span></div>
      <div class="abt_detail_info">
          ${paramMap.content }
      </div>  
     <div class="newsView_brief mt20 mb20">
                            <div class="fl">
                                <s:if test="#request.aftermap!=null">
         上一篇：<a style="max-width: 30px; overflow: hidden;"
                                        href="frontMedialinkId.mht?id=${aftermap.id}">${aftermap.title}</a>
                                </s:if>
                                <s:else>
                                    <a>&nbsp;&nbsp;&nbsp;</a>
                                </s:else>
                            </div>
                            <div class="fr">
                                <s:if test="#request.premap!=null">
      下一篇：<a style="max-width: 30px; overflow: hidden;"
                                        href="frontMedialinkId.mht?id=${premap.id}">${premap.title}</a>
                                </s:if>
                                <s:else>
                                    <a>&nbsp;&nbsp;&nbsp;</a>
                                </s:else>
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
      <div class="bdsharebuttonbox" data-tag="share_1">
    <a class="bds_mshare" data-cmd="mshare"></a>
    <a class="bds_qzone" data-cmd="qzone" href="#"></a>
    <a class="bds_tsina" data-cmd="tsina"></a>
    <a class="bds_baidu" data-cmd="baidu"></a>
    <a class="bds_renren" data-cmd="renren"></a>
    <a class="bds_tqq" data-cmd="tqq"></a>
    <a class="bds_more" data-cmd="more">更多</a>
    <a class="bds_count" data-cmd="count"></a><span class="fr">浏览次数：${paramMap.visits}</span>
</div>

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
      
      <s:if test="#request.relation!=null &&#request.relation.size()>0">
	      <div class=" w clearfix fl mb20">
	        <div class="xg clearfix mb10 mt30"><h1>相关阅读</h1></div>
	        <ul class="n_notic_list clearfix w">
	          <s:iterator value="#request.relation"  var="re"  status="st">
	               <li><a href="frontMedialinkId.mht?id=${ re.id}"  target="_blank"  ><span class="lbt">${re.title }</span><span class="ldt"><fmt:formatDate  value="${re.publishTime }"  pattern="yyyy-dd-MM"   /></span></a></li>
	          </s:iterator>
	         </ul>
	      </div>
      </s:if>
      
  </div>
	  <s:if test="#request.hotnews!=null && #request.hotnews.size()>0">
            <div class="fr hot_news">
                <h1>热门推荐</h1>
	            <s:iterator value="#request.hotnews" var="new"  status="st">
	                   <dl>
                         <dt><img  onclick="javascript:window.location.href='frontMedialinkId.mht?id=${new.id}'"  style="cursor: pointer;" src="${new.imgPath}"   alt="${new.title }"   title="${new.title }"  /></dt>
	                        <dd>
	                            <p class="tit"><a href="frontMedialinkId.mht?id=${new.id}">${new.title }</a></p>
	                            <p>${new.source }</p>
	                            <p><fmt:formatDate  value="${new.publishTime}"  pattern="yyyy-MM-dd HH:mm:ss"  /></p>
	                          </dd>
                        </dl>
	            </s:iterator>
            </div>	   
	  </s:if>
	  <s:else>
            <div class="fr hot_news">
                <h1>热门推荐</h1>
                       <dl>
                            <dd>
                                <p>暂无推荐</p>
                              </dd>
                        </dl>
            </div>  
	  </s:else>
</div>
</div>
		
		
		<!-- 引用底部公共部分 -->
		<jsp:include page="/include/footer.jsp"></jsp:include>
	</body>
</html>