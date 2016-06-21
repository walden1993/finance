<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/index.css" />
<link rel="stylesheet" type="text/css" href="css/new_inside.css" />
<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" />
<link rel="stylesheet" href="css/lightbox.css" type="text/css" media="screen" />
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include> 


<!--内容区域-->
<div class="pay_content">
  <div class="w950 location"><a href="<%=application.getAttribute("basePath")%>">首页</a> > 网站地图</div>
  <div class="webmap clearfix fang w950">
       <h2>网站地图</h2>
       <div class="pt30">
           <dl class="webmap_info clearfix">
                <dt>我要投资</dt>
                <dd><a href="bidlist.mht" target="_blank">投资列表</a><a href="paytreasuredetail.mht" target="_blank">涨薪宝</a><a href="experience.mht" target="_blank">投资体验</a><a href="calctool.mht" target="_blank">工具箱</a></dd>
           </dl>
           <dl class="webmap_info clearfix">
                <dt>我要融资</dt>
                <dd><a href="borrowNew.mht" target="_blank">借款申请</a></dd>
           </dl>
           <dl class="webmap_info clearfix">
                <dt>安全保障</dt>
                <dd><a href="capitalEnsure.mht" target="_blank">安全保障</a></dd>
           </dl>
           <dl class="webmap_info clearfix">
                <dt>关于我们</dt>
                <dd><a href="queryAboutInfo.mht?msg=99" target="_blank">公司介绍</a><a href="queryAboutInfo.mht?msg=100" target="_blank">网站公告</a><a href="queryAboutInfo.mht?msg=103" target="_blank">平台资讯</a><a href="queryAboutInfo.mht?msg=105" target="_blank">联系我们</a><a href="queryAboutInfo.mht?msg=104" target="_blank">合作伙伴</a><a href="jg_zf.mht" target="_blank">资费说明</a><a href="jg_protol.mht" target="_blank">协议范本</a></dd>
           </dl>
           <dl class="webmap_info clearfix">
                <dt>帮助中心</dt>
               <dd><a href="queryAboutInfo.mht?msg=106&cid=1" target="_blank">新手入门</a><a href="queryAboutInfo.mht?msg=106&cid=3" target="_blank">我要理财</a><a href="queryAboutInfo.mht?msg=106&cid=2" target="_blank">我要借款</a><a href="queryAboutInfo.mht?msg=106&cid=7" target="_blank">还款管理</a><a href="queryAboutInfo.mht?msg=106&cid=8" target="_blank">我要提现</a><a href="queryAboutInfo.mht?msg=106&cid=4" target="_blank">我的账户</a><a href="queryAboutInfo.mht?msg=106&cid=9" target="_blank">申请VIP</a><a href="queryAboutInfo.mht?msg=106&cid=6" target="_blank">其他</a></dd>
           </dl>
           <dl class="webmap_info clearfix">
                <dt>我的账户</dt>
               <dd><a href="rechargeInit.mht"  target="_blank">充值</a><a href="withdrawLoad.mht" target="_blank">提现</a><a href="financeStatisInit.mht" target="_blank">收益查看</a><a href="homeBorrowInvestList.mht" target="_blank">交易记录</a></dd>
           </dl>
           <dl class="webmap_info clearfix">
                <dt>其他</dt>
               <dd><a href="reg.mht" target="_blank">注册</a><a href="login.mht" target="_blank">登录</a><a href="activityIndex_app_zt.mht" target="_blank">手机客户端</a></dd>
           </dl>
       </div>
  </div>
</div>

<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
