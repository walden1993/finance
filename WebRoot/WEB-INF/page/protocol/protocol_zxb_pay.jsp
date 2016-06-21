<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="涨薪宝自动转入协议范本" var="title" scope="request" />
<script type="text/javascript">
	var menuIndex = 5;
</script>
</head>

<body>
	<div class="w">
		
		<s:if test="#request.userMap.username!=null">
		<div class=" f14" style="margin: 0 auto;">
		</s:if>
		<s:else>
		<div class=" f14" style="margin: 0 auto;background: url('images/protocol_img.png') no-repeat;">
		</s:else>
		
			<p style="text-align: center; line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 19px;">三好资本“涨薪宝”投资协议</span>
</p>
<p style="text-align: right; line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;display: none">编号：（001）三好资本涨字第&nbsp;&nbsp;&nbsp;&nbsp;${userMap.id+10001}&nbsp;&nbsp;&nbsp;&nbsp;号</span>
</p>
<p style="line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">&nbsp;</span>
</p>
<p style="line-height: 2em; ">
    <s:if test="#request.userMap.username!=null">
    	<span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">甲方：${personMap.realName}<s:if test="#request.personMap.realName==null || #request.personMap.realName==''">尚未实名</s:if>，身份证件号码：${personMap.idNo}<s:if test="#request.personMap.realName==null || #request.personMap.realName==''">尚未实名</s:if>，三好资本用户名：${userMap.username}</span>
    </s:if>
    <s:else>
    	<span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">甲方：_______，身份证件号码：________，三好资本用户名：__________</span>
    </s:else>
</p>
<p style="line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">乙方：深圳市易付通金融信息技术有限公司</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">风险提示：</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">尊敬的投资客户（甲方）：为了维护您的自身权益、防范投资风险，请在签署本协议及投资前仔细阅读本协议各条款、以及与本产品相关的全部产品规则，以充分知悉、了解本“涨薪宝”产品的运作规则、投资范围以及协议各方的权利和义务。您一旦加入本投资产品即视为对本协议全部条款及相关业务规则已充分理解并完全接受。</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">甲方声明：本方已对本协议全面了解，在协议签署时已完全知悉本协议的全部条款及各方的权利义务，本方自愿签署本协议。</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">甲方为在乙方开办的三好资本互联网金融服务网站（域名<a href="http://www.sanhaodai.com" target="_blank">www.sanhaodai.com</a>，以下简称“三好资本网站”）的注册会员，甲方认可和遵守三好资本网站的交易模式及网站公布的全部规则。甲乙双方经友好协商，本着平等自愿、诚实信用的原则，就甲方自愿投资认购&nbsp;“涨薪宝”产品达成如下协议，共同遵守：</span>
</p>
<p></p>
<p style="margin-left: 28px; line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;"><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">一、“</span><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px; ">涨薪宝”产品说明</span></span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">1.1“涨薪宝”是由三好资本平台与金融机构共同设计、打造的一款活期理财产品，按日计息，随转随出，属于三好资本余额增值服务；</span>
</p>
<p style="margin-left: 27px; line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px; ">1.2该产品为不定期产品，没有期限限制，计息中金额可以随时申请赎回，</span>
</p>
<p style="line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px; ">赎回资金立即到账显示为“三好资本”的余额；</span>
</p>
<p style="line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px; ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.3“涨薪宝”认购总额（以网站发布金额为准）</span>
</p>
<p style="line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;"></span>
</p>
<p style="text-indent: 32px; line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;"><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px; ">二、</span><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">“</span><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px; ">涨薪宝”预期收益</span></span>
</p>
<p style="line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;"><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px; ">&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;2.1甲方投资购买“涨薪宝”产品的，其年化利率(以网站公布为准),</span><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; line-height: 31px; ">投资认购以人民币</span><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; line-height: 31px;">1000元起认购。</span></span>
</p>
<p style="text-indent: 24px; line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px; ">&nbsp;2.2本产品由第三方深圳市华融融资担保有限公司提供全部本息保证担保。</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">2.3&nbsp;乙方根据产品发行的实际需要，可以将“涨薪宝”产品分为若干期进行发布。甲方本次申请加入的“涨薪宝”为<s:if test="#request.name==null">“_______”</s:if><s:else>“${name}”</s:else>。</span>
</p>
<p style="line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;"><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px; ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.4&nbsp;甲方加入“涨薪宝”的，根据自身实际情况，</span><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; ">甲方支付完毕全部投资资金，且经乙方确认后即成功加入“涨薪宝”，投资收益开始计算。&nbsp;</span></span>
</p>
<p style="line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; "></span>
</p>
<p style="line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、认购方式及收益的计算</span>
</p>
<p style="text-indent: 32px; line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;"><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">3.1甲方登录三好资本网站平台，根据操作指引，确认三好资本余额充足后，点击“</span><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px; ">涨薪宝</span><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">”并进行认购操作。</span></span>
</p>
<p style="text-indent: 32px; line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;"><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">3.2甲方投资认购“</span><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px; ">涨薪宝</span><span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">”后，收益的计算方式为：</span></span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;">&nbsp;</span>
</p>
<style>
									.profit {
										    margin: 10px 0;
										    max-width: 1000px;
										    table-layout: auto;
										    border: 1px solid #d6d6d6;
										    border-collapse: collapse;
										    text-align: left;
										}
									.profit tr th{
									    font-size: 14px;
									    border: 1px solid #d6d6d6;
									    padding: 8px;
									    min-width: 60px;
									    vertical-align: middle;
									    text-align: left;
									}
									.profit td{line-height: 30px;padding:0 10px; }
									.profit thead {
									    background-color: #A1B5C7;
									    color: #fff;
									    font-size: 14px;
									    border: 1px solid #d6d6d6;
									}
									.profit span {
									    font-size: 14px;
									}
									</style>
									<span >涨薪宝转入后，收益首次显示时间如下：（节假日顺延）</span>
<table class="profit" border="1" cellpadding="1" cellspacing="1" style="width: 100%;margin: 10px 0; max-width: 1000px;table-layout: auto; border: 1px solid #d6d6d6; border-collapse: collapse; text-align: left;"> 
 <thead> 
  <tr> 
   <th scope="col"><span style="line-height: 20.796875px;">转入时间</span></th> 
   <th scope="col"><span style="line-height: 20.796875px;">确认份额</span></th> 
   <th scope="col"><span style="line-height: 20.796875px;">首次发放收益时间</span></th> 
  </tr> 
 </thead> 
 <tbody> 
  <tr> 
   <td><span style="line-height: 20.796875px;">周四15:00（含15:00）～周五15:00</span></td> 
   <td><span style="line-height: 20.796875px;">下周一</span></td> 
   <td><span style="line-height: 20.796875px;">下周二</span></td> 
  </tr> 
  <tr> 
   <td><span style="line-height: 20.796875px;">周五15:00（含15:00）～下周一15:00</span></td> 
   <td><span style="line-height: 20.796875px;">下周二</span></td> 
   <td><span style="line-height: 20.796875px;">下周三</span></td> 
  </tr> 
  <tr> 
   <td><span style="line-height: 20.796875px;">周一15:00（含15:00）～周二15:00</span></td> 
   <td><span style="line-height: 20.796875px;">周三</span></td> 
   <td><span style="line-height: 20.796875px;">周四</span></td> 
  </tr> 
  <tr> 
   <td><span style="line-height: 20.796875px;">周二15:00（含15:00）～周三15:00</span></td> 
   <td><span style="line-height: 20.796875px;">周四</span></td> 
   <td><span style="line-height: 20.796875px;">周五</span></td> 
  </tr> 
  <tr> 
   <td><span style="line-height: 20.796875px;">周三15:00（含15:00）～周四15:00</span></td> 
   <td><span style="line-height: 20.796875px;">周五</span></td> 
   <td><span style="line-height: 20.796875px;">周六</span></td> 
  </tr> 
 </tbody> 
</table>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;">&nbsp;</span>
</p>
<p>
    <br/>
</p>
<p style="margin-right: 21px; text-align: justify; line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;;"><span style="font-family: 宋体; font-weight: bold; font-size: 14px;">&nbsp;&nbsp;四、“</span><span style="font-family: 宋体; font-weight: bold; font-size: 14px; ">涨薪宝</span><span style="font-family: 宋体; font-weight: bold; font-size: 14px;">”投资范围</span></span>
</p>
<p style="margin-right: 21px; text-indent: 32px; text-align: justify; line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">4.1乙方聘请专业技术团队，运用科学的管理手段，主要投资货币基金、ETF基金、固定收益类信托计划、基金公司资管计划、契约式私募投资基金等稳定收益投，保证资金的保值增值。</span>
</p>
<p style="margin-right: 21px; text-indent: 32px; text-align: justify; line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">&nbsp;</span>
</p>
<p style="line-height: 2em;">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、甲方授权&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">5.1甲方在此无条件且不可撤销地同意并确认：自甲方认购“涨薪宝”产品之日起，乙方可通过三好资本网站系统在本协议项下甲方认可的投资范围内进行自动投标，并通过三好资本网站系统以甲方名义签署相关借款协议、债权转让协议及依照规则应签署的其他协议；甲方对此等自动投标和签署相关借款协议、债权转让协议等安排已充分知悉并理解；该等签署的协议均视为甲方真实意思的表示，甲方对该等法律文件的效力均予以认可且无任何异议，并自愿接受该等签署的借款协议、债权转让协议及系统自动生成的其他相关协议之约束及承担后果。&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">5.2甲方在此无条件且不可撤销地同意并确认：甲方通过三好资本网站系统自动投标而签署之借款协议、债权转让协议等法律文件或其中的相关条款生效后，乙方即可根据该等协议和本协议相关约定，对相关款项进行划扣、支付、转账、冻结以及行使其他权利，甲方对此均予以接受和认可。&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">六、手续费用</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">6.1甲方投资“涨薪宝”产品免付手续费及赎回费用。乙方根据交易需要收取费用的，由甲方参照三好资本网站公布的交易规则和收费标准向乙方支付。</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">七、服务保障&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">7.1为降低甲方因投资标的过于集中所带来的信用违约风险，乙方可以对甲方加入“涨薪宝”的投资金额将根据三好资本网站的有关规则安排分散化的自动投标。&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">7.2为保证投资的透明性，在加入“涨薪宝”产品后，甲方可以随时登陆三好资本网站查看“涨薪宝”资金的详细投资去向。</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">八、意外事件&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">8.1如因司法机关或行政机关采取强制措施导致甲方加入“涨薪宝”对应的资金被全部或部分扣划、冻结，则视为甲方提前退出“涨薪宝”，本协议自动终止。本协议因此而自动终止的，甲方将不再享有相应的收益。&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">九、税费负担</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">9.1甲方因加入“涨薪宝”所获收益而产生的相关税收缴纳义务，应由甲方根据中国法律、法规等的规定自行向当地主管税务机关申报、缴纳，乙方不承担任何代扣代缴的义务或责任。否则，甲方应负责赔偿因其违反上述规定致使乙方遭受的全部损失。</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">十、甲方声明和保证&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">10.1在签署本协议以前，甲方已认真阅读本协议有关条款，对有关条款不存在任何疑问或异议，并对协议双方的权利、义务、责任与风险有清楚和准确的理解。&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">10.2甲方保证所使用的资金为合法取得，且甲方对该等资金拥有完全且无瑕疵的处分权。&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">10.3甲方保证为履行本协议而向乙方提供的全部资料均真实、有效。&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-weight: bold; font-size: 14px;">十一、其他&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">11.1甲方需要通过三好资本网站或客服人员等方式了解相关信息，如未及时查询，或由于通讯故障、系统故障以及其他不可抗力等因素影响使甲方无法及时了解相关信息，由此产生的责任和风险由甲方自行承担。&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">11.2本服务协议和甲方通过点击确认与乙方签署的相关协议，以及乙方在三好资本网站不定期公示的交易规则、说明、公告等涉及甲乙双方权利义务的法律文件，共同构成了约束甲方接受乙方在本协议项下提供服务时双方行为的整体协议，甲方均须予以遵守。如有违反，甲方应当自行承担相关法律后果。&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">11.3由于地震、火灾、战争等不可抗力导致的交易中断、延误的，甲乙双方互不承担责任，但在条件允许的情况下，应采取一切必要的补救措施以减小不可抗力造成的损失。&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">11.4本协议项下产生的纠纷，双方先行协商解决，协商不成的，任何一方可向乙方所在地有管辖权的人民法院提起诉讼。&nbsp;</span>
</p>
<p style="text-indent: 28px; line-height: 2em; ">
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">11.5本协议自甲方在三好资本网站点击确认并支付完毕全部投资资金、经乙方确认后生效。本协议采用电子文本形式制成，可以有一份或者多份并且每一份具有同等法律效力，并长期保存在乙方为此设立的专用服务器上备查，对各方均具有法律约束力。&nbsp;</span>
</p>
<p>
    <br/>
</p>
		</div>
	</div>
</body>
</html>