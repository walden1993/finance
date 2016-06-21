<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>管理首页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3"/>
	<meta http-equiv="description" content="This is my page"/>
	<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="../css/user_css.css" />

</head>



<body>
<div class="main_alll">
	<s:if test="#session.map.ddcountright != 0 || #session.map.pcountright != 0 || #session.map.fcountright != 0 || #session.map.zccountright != 0 || #session.map.zacountright != 0">
	<h2 class="main_alll_h2">审核管理</h2>
    <div class="main_div_a">
    	<strong><b>借款管理</b></strong>
        <s:if test="#session.map.ddcountright != 0"><p>借款等待资料审核： (<a href="borroww.mht"><span style="color:red;">${session.map.ddcount }</span></a>)条</p></s:if>
        <s:if test="#session.map.pcountright != 0"><p>借款初审审核：  (<a href="borrowf.mht"><span style="color:red;">${session.map.pcount }</span></a>)条</p></s:if>
        <s:if test="#session.map.fcountright != 0"><p>借款满标审核： (<a href="borrowFullScale.mht"><span style="color:red;">${session.map.fcount}</span></a>)条</p></s:if>
        <s:if test="#session.map.zccountright != 0"><p>申请债权转让： (<a href="queryApplyDebtInit.mht"><span style="color:red;">${session.map.zccount }</span></a>)条</p></s:if>
        <s:if test="#session.map.zacountright != 0"><p>债权转让中：(<a href="queryAuctingAssignmentDebtInit.mht"><span style="color:red;"> ${session.map.zacount }</span></a>)条</p></s:if>
    </div>
    </s:if>
    <s:if test="#session.map.jbxxcountright != 0 || #session.map.jbzlcountright != 0 || #session.map.sjcountright != 0 || #session.map.edcountright != 0 || #session.map.kxcountright != 0">
    <div class="main_div_a">
    	<strong><b>认证管理</b></strong>
        <s:if test="#session.map.jbxxcountright != 0"><p>用户基本信息审核(待审核)： (<a href="queryPersonInfolistindex.mht?types=1"><span style="color:red;">${session.map.jbxxcount }</span></a>)条</p></s:if>
        <s:if test="#session.map.jbzlcountright != 0"><p>个人用户基本资料审核(待审核)：  (<a href="rechargeecordsInit.mht?types=1"><span style="color:red;">${session.map.jbzlcount }</span></a>)条</p></s:if>
        <s:if test="#session.map.jbzlcountright != 0"><p>企业用户基本资料审核(待审核)：  (<a href="rechargeecordsInit.mht?types=2"><span style="color:red;">${session.map.jbzlorgcount }</span></a>)条</p></s:if>
        <s:if test="#session.map.sjcountright != 0"><p>手机变更申请(审核中)： (<a href="updatephoneIndexChange.mht?types=2"><span style="color:red;">${session.map.sjcount}</span></a>)条</p></s:if>
        <s:if test="#session.map.edcountright != 0"><p>额度申请(审核中)： (<a href="querycreditindex.mht?types=1"><span style="color:red;">${session.map.edcount }</span></a>)条</p></s:if>
        <s:if test="#session.map.kxcountright != 0"><p>可选资料认证：(<a href="queryselectInitindex.mht?types=4"><span style="color:red;"> ${session.map.kxcount }</span></a>)条</p></s:if>
    </div>
    </s:if>
    <s:if test="#session.map.ddtxcountright != 0 || #session.map.zctxcountright != 0 || #session.map.yhbgcountright != 0">
    <div class="main_div_a">
    	<strong><b>资金管理</b></strong>
        <s:if test="#session.map.ddtxcountright != 0"><p>等待审核的提现(审核中)： (<a href="queryCheckInit.mht"><span style="color:red;">${session.map.ddtxcount }</span></a>)条</p></s:if>
        <s:if test="#session.map.zctxcountright != 0"><p>转账中的提现(转账中)：  (<a href="queryTransInit.mht"><span style="color:red;">${session.map.zctxcount }</span></a>)条</p></s:if>
        <s:if test="#session.map.yhbgcountright != 0"><p>银行卡变更申请(审核中)： (<a href="queryModifyBankInit.mht?types=2"><span style="color:red;">${session.map.yhbgcount}</span></a>)条</p></s:if>
    </div>
    </s:if>
</div>
</body>
