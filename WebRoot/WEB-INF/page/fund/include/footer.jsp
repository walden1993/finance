<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>

<div class="footer">
<div class="w1180">
<ul>
<li>
<h3>关于我们</h3>
<p><a href="queryAboutInfo.mht?msg=101" target="_blank">平台资讯</a></p>
<p><a href="queryAboutInfo.mht?msg=110" target="_blank">运营主体</a></p>
<p><a href="queryAboutInfo.mht?msg=111">业务模式</a></p>
<p><a href="jg_zf.mht" target="_blank">资费说明</a></p>
</li>

<li>
<h3>帮助我们</h3>
<p><a href="queryAboutInfo.mht?msg=106&amp;cid=1" target="_blank">新手入门</a></p>
<p><a href="queryAboutInfo.mht?msg=106&amp;cid=3" target="_blank">我要投资</a></p>
<p><a href="queryAboutInfo.mht?msg=106&amp;cid=8" target="_blank">我要提现</a></p>
</li>

<li style="width: 200px;">
<h3>关注我们</h3>
<a href="javascript:void(0);" class="weixin"></a>
<div class="weixin_pop"></div>
<a href="#.html" class="weibo"></a>
</li>

<li class="w340">
<h2>
<p>客服热线</p>
${sitemap.servicePhone}
</h2>
<p>工作日 上午9:00-下午18:00</p>

<p><span>${sitemap.companyName}版权所有</span><br>
<span>${sitemap.certificate }&nbsp;|&nbsp;©2013-2016&nbsp;${sitemap.regionName}</span>
</p>

</li>

</ul>
</div>


<div class="site_secruity">
<a href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&dn=www.sanhaodai.com&lang=zh_cn" target="_blank" class="norton"></a>
<a href="https://search.szfw.org/cert/l/CX20140825008763005051" target="_blank" class="cheng"></a>
<a href="http://webscan.360.cn/index/checkwebsite?url=www.sanhaodai.com" target="_blank" class="safe"></a>
<a href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&dn=www.sanhaodai.com&lang=zh_cn" target="_blank" class="cfca"></a>
</div>
<script type="text/javascript">
$(".footer a.weixin").hover( function(){
	$(this).next().show().stop().animate({left:"-158px",opacity:"1"},"0.3");
	},function(){
		$(this).next().stop().animate({left:"-138px",opacity:"0"},"0.2",function(){
			$(this).hide();
			});
			});
$("#mobilelink").hover( function(){
	$(this).next().show().stop().animate({right:"158px",opacity:"1",top:"100",'z-index':"99999"},"0.3");
	},function(){
		$(this).next().stop().animate({right:"138px",opacity:"0",top:"100"},"0.2",function(){
			$(this).hide();
			});
			});			
</script>
</div>
