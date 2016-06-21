<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
		<!--我的主页-->
	    <div class="myhelpcenter02">
			<div class="myhelpcenter03"><span>名词解释</span></div>
	    </div>
	    <!--我的主页end-->
	    <div class=" clearfix hjh">
	           <!--帮助中心右边1-->
	         <div class="left">
	         <div class="shd_js clearfix">
                <a href="javascript:;"class="active">借款人</a>
                <a href="javascript:;" class="">投资人</a>
                <a href="javascript:;" class="">投资列表</a>
                <a href="javascript:;" class="">募集</a>
                <a href="javascript:;" class="">投资</a>
                <a href="javascript:;" class="">放款</a>
                <a href="javascript:;" class="">流标</a>
                <a href="javascript:;" class="">投标金额</a>
                <a href="javascript:;" class="">冻结金额</a>
                <a href="javascript:;" class="">等额本息</a>   
                
                <a href="javascript:;" class="">账户总资产</a>
                <a href="javascript:;" class="">预期总收益</a>
                <a href="javascript:;" class="">累计投资</a>
                <a href="javascript:;" class="">累计收益</a>
                <a href="javascript:;" class="">待收总额</a>
                <a href="javascript:;" class="">待收收益</a>
                <a href="javascript:;" class="">进行中项目</a>
                <a href="javascript:;" class="">已完成项目</a>
                <!-- <a href="javascript:;">账户总额</a>
                <a href="javascript:;">可用金额</a> -->

                <!-- <a href="javascript:;">加权收益率</a> -->
	         </div>
	         <div class="line"></div>
	         <p class="tz" style="display: block;">是指在深圳本市内的个人和企业的借款，个人通过自己名下的房产和车辆抵押借款；企业需通过机构担保借款（有抵押或质押的借款）。</p>
	          <p class="tz" style="display: none;">也称投资人，是指已经或准备在网站上进行出借活动的用户。凡18周岁以上的中国大陆地区公民，都可以成为理财人。</p>
	          <p class="tz" style="display: none;">当借款人成功发布一个借款请求时，我们将包含各种贷款信息的该请求称为一个借款列表。</p>
	          <p class="tz" style="display: none;">是指理财人将其账户内指定的金额出借给其指定的借款项目的行为。</p>
	          <p class="tz" style="display: none;">是指理财人将其账户内指定的金额出借给其指定的借款列表的行为。</p>
	          <p class="tz" style="display: none;">指一个借款列表满标后且已符合放款标准，三好资本将把所筹资金打入借款人在三好资本的账户中。即成功贷款。</p>
	          <p class="tz" style="display: none;">是指一个借款列表的投标期限已过，但是贷款没有足额筹集齐，即贷款失败。</p>
	          <p class="tz" style="display: none;">是指理财人对借款列表进行投标的金额。</p>
	          <p class="tz" style="display: none;">1、用户对某借款列表进行投标后，在此列表的招标期结束之前，这部分投标金额将被锁定，“冻结金额”是指因这类型锁定金额的总额。如果列表放款，这部分金额将转给该列表的借款人；如果该列表流标，这部分金额将立即变为用户的可用金额。
   <br/> 2、用户对个人账户内可用余额作提现申请后，些笔申请提现金额将会被锁定冻结，待平台按规定的提现时间内审核和转账。如果该笔提现申请未能通过审核（此笔提现申请失败），提现金额解冻并退还至用户个人账户的可用余额中。如果提现审核和转帐成功，此笔提现金额将会在用户个人的账户中扣减。</p>
	          <p class="tz" style="display: none;">定义：等额本息还款法是一种被广泛采用的还款方式，在还款期内，每月偿还同等数额的贷款(包括本金和利息)。借款人每月还款额中的本金比重逐月递增、利息比重逐月递减。其计算公式如下：
							每月还款额=[贷款本金×月利率×（1+月利率）^还款总期数]÷[（1+月利率）^还款总期数-1] 
							因计算中存在四舍五入，最后一期还款金额与之前略有不同。</p>
							
			  <p class="tz" style="display: none;">账户总资产=可用余额+冻结金额+待收本金+待收利息+红包金额</p>
			  <p class="tz" style="display: none;">预期总收益=累计收益+待收收益</p>
			  <p class="tz" style="display: none;">累计已投资项目的投资本金总额</p>
			  <p class="tz" style="display: none;">累计已完成投资项目的收益总额</p>
			  <p class="tz" style="display: none;">还款中投资项目的待收本金加待收收益。</p>
			  <p class="tz" style="display: none;">还款总投资项目的待收收益。</p>
			  <p class="tz" style="display: none;">进行中的项目=还款中的项目+筹款中的项目</p>
			  <p class="tz" style="display: none;">投资项目已结束并成功还款的投资项目</p>
	          
	          </div>
	          <!--帮助中心右边1end-->
				<jsp:include page="/WEB-INF/page/helpcenter/static_right.jsp"></jsp:include>
	          <!--帮助中心右边2-->
	         
	    <!--帮助中心右边2end-->


	    </div>
