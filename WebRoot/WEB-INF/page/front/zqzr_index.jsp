<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh_cn">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
</head>

<body>
<div  class="p20 f14">
<!-- 引用头部公共部分 -->

<h3 style="text-align:center;">
	债权转让协议
</h3>
<p style="text-align:right;">
	<br />
</p>
<p style="text-align:right;">
	编号：（三好资本）债转字第<u>${map.DatesNumber}${map.id }</u>号<span class="red ml20">(注*：协议中带*的数量不等于原始字数，仅加密使用)</span>
</p>
<p style="text-align:right;">
	&nbsp;
</p>
<p>
	&nbsp;
</p>
<p>
	甲方（转让人）：<%--个人 --%>
    <u><s:if test="#request.map.userType==1">
        <%--判断是否是本人访问 --%>
       <s:if test="#session.user.userName==#request.map.username">
            <%--判断是否有实名认证 --%>
            <s:if test="#request.map.realName=='' || #request.map.realName==null">${map.username}</s:if>
            <s:else>${map.realName }</s:else>
       </s:if>
       <s:else> 
            <s:if test="#request.map.realName=='' || #request.map.realName==null"><shove:sub  value="#request.map.username" size="1"   suffix="**" /></s:if>
            <s:else><shove:sub  value="#request.map.realName" size="1"   suffix="**" /></s:else>
       </s:else>
    </s:if>
    <%--企业 --%>
    <s:else>
           <%--判断是否是本人访问 --%>
           <s:if test="#session.user.userName==#request.map.username">
               <%--判断是否有实名认证 --%>
                <s:if test="#request.map.orgName=='' || #request.map.orgName==null">${map.username}</s:if>
                <s:else>${map.orgName }</s:else>
           </s:if>
           <s:else> 
                 <%--判断是否有实名认证 --%>
                <s:if test="#request.map.orgName=='' || #request.map.orgName==null"><shove:sub  value="#request.map.username" size="1"   suffix="***" /></s:if>
                <s:else><shove:sub  value="#request.map.orgName" size="1"   suffix="***" /></s:else>
           </s:else>
    </s:else></u>
</p>
<p style="text-indent:21.0000pt;">
    <s:if test="#request.map.userType==1">
        身份证号：<u><%--判断是否是本人访问 --%>
       <s:if test="#session.user.userName==#request.map.username">
            ${map.isno}
       </s:if>
       <s:else> 
            <shove:sub  value="#request.map.isno" size="3"   suffix="***************" />
       </s:else>
    </s:if></u>
    <%--企业 --%>
    <s:else>
           营业执照号：<u><%--判断是否是本人访问 --%>
           <s:if test="#session.user.userName==#request.map.username">
                ${map.reg_num }
           </s:if>
           <s:else> 
                <shove:sub  value="#request.map.reg_num" size="3"   suffix="*********" />
           </s:else>
    </s:else></u>
</p>
<p style="text-indent:21.0000pt;">
	三好资本平台网站用户名：<%--个人 --%>
    <u><s:if test="#request.map.userType==1">
        <%--判断是否是本人访问 --%>
       <s:if test="#session.user.userName==#request.map.username">
            ${map.username}
       </s:if>
       <s:else> 
            <shove:sub  value="#request.map.username" size="1"   suffix="**" />
       </s:else>
    </s:if>
    <%--企业 --%>
    <s:else>
           <%--判断是否是本人访问 --%>
           <s:if test="#session.user.userName==#request.map.username">
                ${map.username }
           </s:if>
           <s:else> 
                <shove:sub  value="#request.map.username" size="1"   suffix="***" />
           </s:else>
    </s:else></u>
</p>
<p style="text-indent:21.0000pt;">
	联系电话：<u><%--判断是否是本人访问 --%>
       <s:if test="#session.user.userName==#request.map.username">
            ${map.phone}
       </s:if>
       <s:else> 
            <shove:sub  value="#request.map.phone" size="3"   suffix="***************" />
       </s:else></u>
</p>
<p style="">
	&nbsp;&nbsp;&nbsp;
</p>
<p>
	乙方&nbsp;（受让人）：<u><s:iterator var="mapBeans" value="#request.user_invest_map"  status="sta">
                     <s:if test="#sta.index!=0">,</s:if>
                         <%--判断是否是本人访问 --%>
                       <s:if test="#session.user.userName==#request.mapBeans.username">
                                <%--判断是个人还是企业 --%>
                                <s:if test="#request.mapBeans.userType==1">
                                    <%--判断是否有实名认证 --%>
                                    <s:if test="#request.mapBeans.realName=='' || #request.mapBeans.realName==null">${mapBeans.username}</s:if>
                                    <s:else>${mapBeans.realName}</s:else>
                                </s:if>
                                <s:else>
                                     <%--判断是否有实名认证 --%>
                                    <s:if test="#request.mapBeans.orgName=='' || #request.mapBeans.orgName==null">${mapBeans.username}</s:if>
                                    <s:else>${mapBeans.orgName}</s:else>
                                </s:else>
                       </s:if>
                       <s:else> 
                               <s:if test="#request.mapBeans.userType==1">
                                     <%--判断是否有实名认证 --%>
                                    <s:if test="#request.mapBeans.realName=='' || #request.mapBeans.realName==null"><shove:sub  value="#request.mapBeans.username" size="1"   suffix="***" /></s:if>
                                    <s:else><shove:sub  value="#request.mapBeans.realName" size="1"   suffix="***" /></s:else>
                              </s:if>
                              <s:else>
                                    <%--判断是否有实名认证 --%>
                                    <s:if test="#request.mapBeans.orgName=='' || #request.mapBeans.orgName==null"><shove:sub  value="#request.mapBeans.username" size="1"   suffix="***" /></s:if>
                                    <s:else><shove:sub  value="#request.mapBeans.orgName" size="1"   suffix="***" /></s:else>
                              </s:else>
                       </s:else>
                 </s:iterator></u>
</p>
<p style="text-indent:21.0000pt;">
	身份证号(或组织机构代码)：<u><s:iterator var="mapBeans" value="#request.user_invest_map"  status="sta">
                     <s:if test="#sta.index!=0">,</s:if>
                         <%--判断是否是本人访问 --%>
                         <s:if test="#session.user.userName==#request.mapBeans.username">
                                ${mapBeans.idNo}
                       </s:if>
                       <s:else> 
                             <shove:sub  value="#request.mapBeans.idNo" size="3"   suffix="***********" />
                       </s:else>
                 </s:iterator></u>
</p>
<p style="text-indent:21.0000pt;">
	三好资本平台网站用户名：<u><s:iterator var="mapBeans" value="#request.user_invest_map"  status="sta">
                     <s:if test="#sta.index!=0">,</s:if>
                         <%--判断是否是本人访问 --%>
                         <s:if test="#session.user.userName==#request.mapBeans.username">
                                ${mapBeans.username}
                       </s:if>
                       <s:else> 
                             <shove:sub  value="#request.mapBeans.username" size="1"   suffix="***" />
                       </s:else>
                 </s:iterator></u>
</p>
<p style="text-indent:21.0000pt;">
	联系电话：<u><s:iterator var="mapBeans" value="#request.user_invest_map"  status="sta">
                     <s:if test="#sta.index!=0">,</s:if>
                         <%--判断是否是本人访问 --%>
                         <s:if test="#session.user.userName==#request.mapBeans.username">
                                ${mapBeans.phone}
                       </s:if>
                       <s:else> 
                             <shove:sub  value="#request.mapBeans.phone" size="3"   suffix="***********" />
                       </s:else>
                 </s:iterator></u>
</p>
<p style="">
	&nbsp;
</p>
<p>
	丙方(居间人)：深圳市易付通金融信息技术有限公司
</p>
<p style="text-indent:15.7500pt;">
	住址：深圳市福田区滨河路5022号联合广场A座3楼
</p>
<p style="text-indent:15.7500pt;">
	法定代表人：陈飞忠
</p>
<p style="text-indent:21pt;">
	&nbsp;
</p>
<p style="text-indent:21pt;">
	鉴于：
</p>
<p style="text-indent:21pt;">
	1、居间人为互联网域名为www.sanhaodai.com网站(以下简称“三好资本平台”)的运营管理人，依法提供金融信息咨询及相关服务。
</p>
<p style="text-indent:21pt;">
	2、转让人及受让人均了解三好资本平台的服务内容、操作及运营模式，均自愿在三好资本平台注册，自愿接受三好资本平台的服务，同意遵守三好资本平台的各项行为准则和发布的各项规章制度。居间人通过三好资本平台提供专门的债权转让居间服务。甲乙双方的债权转让与受让事宜均通过三好资本服务网站进行。
</p>
<p style="text-indent:21pt;">
	3、借款人<u><shove:utils text="${transferUser.name}" start="1" end="2" /></u>（身份证号/组织机构代码：<u><shove:utils text="${transferUser.code}" start="2" end="4" /></u>，以下简称债务人）向甲方借款人民币<u>${map.borrowAmount}</u>，借款期限<u>${map.deadline}</u><s:if test="#request.map.isDayThe==1">个月</s:if>
						<s:if test="#request.map.isDayThe==2">天</s:if>。双方签署了有关的《借款合同》及担保合同，甲方已依照《借款合同》的约定发放了借款款项，债务人已收妥该借款款项。甲方拥有对债务人由此而产生的合法债权追索和请求权。
</p>
<p style="">
	<br />
</p>
<p style="text-indent:21pt;">
	根据《中华人民共和国合同法》有关债权转让的规定，就甲方通过三好资本服务网站将拥有的上述债权转让给乙方事宜，各方经协商一致，达成如下条款，共同遵守：
</p>
<p style="">
	　　第一条　转让债权
</p>
<p style="margin-left:39pt;text-indent:-18pt;">
	1、&nbsp;甲方截止<u>${map.starTime }</u>日拥有的债权本金人民币<u>${map.borrowAmount}</u>及其利息收益权。
</p>
<p style="margin-left:39pt;text-indent:-18pt;">
	2、&nbsp;甲方转让该全部债权或部分债权后，从属于该借款债权相应的担保从权利一并转让乙方。&nbsp;本协议标的债权的担保情况为见丙方就协议债权转让投资发标介绍。
</p>
<p style="margin-left:39pt;text-indent:-18pt;">
	3、&nbsp;甲方向乙方转让债权的标的金额为借款本金人民币<u>￥${sumMap.sumPal }</u>元及利息收益权。
</p>
<p style="text-indent:20.25pt;">
	第二条　债权转让价格及支付
</p>
<p style="text-indent:21pt;">
	1、甲方向乙方转让上述债权的价格为人民币<u>￥${sumMap.sumPal }</u>元（以下简称债权转让价格）。
</p>
<p style="text-indent:21pt;">
	2、乙方受让该债权时，委托并授权丙方将乙方应支付的债权转让价款从其在三好资本服务网站开立的电子账户（以下称乙方的三好资本账户）中划付到甲方在三好资本服务网站开设的专门收款账户（以下称甲方的三好资本账户）。
</p>
<p style="text-indent:20.25pt;">
	第三条　甲方的陈述和保证&nbsp;
</p>
<p style="text-indent:20.25pt;">
	1、甲方保证对债务人及其担保人拥有合法的债权，保证向乙方转让的债权真实合法,甲方有权转让该债权。
</p>
<p style="text-indent:21pt;">
	2、本协议签署后，甲方保证不减免对债务人的债务，不减免对担保人的担保责任及相关的抵押、质押等债务担保措施。
</p>
<p style="text-indent:21pt;">
	3、乙方支付债权转让价款后，甲方保证依照本协议的回购价格，无条件向乙方回购所转让的债权。
</p>
<p style="">
	　&nbsp;&nbsp;第四条　乙方的陈述和保证
</p>
<p  style="text-indent:21pt;">
	1、乙方保证依照本协议的约定受让所转让的债权，按时足额向甲方支付债权转让价款。
</p>
<p style="text-indent:21pt;">
	2、乙方支付债权转让价款后，在转让标的所载明的借款债务到期届满，乙方有权要求甲方以本协议约定的回购价格回购其所收购的债权。
</p>
<p style="text-indent:15.8pt;">
	第五条　债权的委托管理
</p>
<p style="text-indent:21pt;">
	1、乙方对所受让的债权，委托甲方代为管理和服务。甲方应该及时向乙方通报债务人及担保人的相关经营、财务等情况信息，监督借款债务人及时清偿债务本息，以维护乙方对债权的合同权益。
</p>
<p style="text-indent:21pt;">
	2、甲方接受乙方对所转让债权进行管理，并有权根据本协议向乙方收取债权管理费。
</p>
<p style="text-indent:15.8pt;">
	第六条&nbsp;&nbsp;债权回购
</p>
<p style="text-indent:21pt;">
	1、在乙方投资期间届满时，甲方应以等同于债权转让价格的款项（即转让价格）即人民币<u>￥${sumMap.sumPal }</u>元无条件回购乙方所受让的债权。
</p>
<p style="text-indent:21pt;">
	2、投资期间是指从乙方全额支付购买该债权的款项之次日起至甲方回购债权之日止，乙方实际持有该转让债权时间。
</p>
<p style="margin-left:10.5pt;text-indent:10.5pt;">
	3、乙方受让本次债权项目的投资期间为<u>${map.deadline}</u><s:if test="#request.map.isDayThe==1">个月</s:if>
						<s:if test="#request.map.isDayThe==2">天</s:if>。即从<u>${map.starTime }</u>日起至<u>${map.endTime }</u>日为止。乙方的投资本该债权的持续时间不得少于1个月。计算整数月后，剩余时间不足一个月的按其实际持有债权的天数计算。
</p>
<p style="text-indent:15.8pt;">
	第七条&nbsp;&nbsp;债权投资收益、委托管理费及回购款
</p>
<p style="text-indent:21pt;">
	基于本协议第五条的约定，甲方转让债权后，根据乙方的委托对债权进行日常管理。对债权有关收益的分配和支付，双方约定如下：
</p>
<p style="text-indent:21pt;">
	1、债权投资收益：乙方债权投资目标收益为年化<u>${map.annualRate}%</u>，甲方在进行债权管理所收取的债权利息中按月向乙方支付。每月支付时间为<u>${map.endTime }</u>号前，最后一月随回购款一并支付。甲方承诺无论债权利息是否按时收取，均对乙方实现该收益承担支付义务。
</p>
<p style="text-indent:21pt;">
	2、委托管理费：乙方同意除目标收益外的剩余债权收益作为支付给甲方的债权管理费用，由甲方向债务人收取债权利息并完成乙方债权目标收益支付后直接扣收。甲方承诺该委托管理费对乙方的债权投资收益不存在任何影响。甲方除通过扣收因受托债权管理获得的债权收益获取委托管理费途径外，乙方并不承担任何有关委托管理费的给付义务。
</p>
<p style="text-indent:21pt;">
	3、回购款支付：甲方应在乙方投资期间届满当日委托并授权丙方将债权回购款从甲方的三好资本账户划付至乙方的三好资本账户。如甲方逾期支付回购款，则按照每日万分之五承担违约责任。
</p>
<p style="text-indent:15.8pt;">
	第八条　保密　
</p>
<p style="text-indent:21pt;">
	一方对因本次债权转让而获知的另一方的商业机密负有保密义务，不得向其他第三方泄露，但中国现行法律、法规另有规定的或经另一方书面同意的情况除外。　&nbsp;
</p>
<p style="">
	　&nbsp;第九条　争议的解决　
</p>
<p style="">
	　　本协议适用中华人民共和国有关法律，受中华人民共和国法律管辖。
</p>
<p style="">
	　　各方当事人对本协议有关条款的理解或履行发生争议时，应通过友好协商的方式予以解决。如果经协商未达成书面协议，各方当事人均同意向丙方所在地人民法院提起诉讼。　
</p>
<p style="">
	　&nbsp;第十条　其他
</p>
<p style="text-indent:20.25pt;">
	本协议自各方根据三好资本平台公布的线上电子文本生成的要件而生效。
</p>
<p style="text-indent:20.25pt;">
	本协议一式三份，各方当事人各执一份，具有相同法律效力。
</p>
<p style="">
	&nbsp;（以下无正文，为签字页）
</p>
<p style="">
	&nbsp;
</p>
<p style="">
	甲方（签字/盖章）：<u><s:if test="#request.map.userType==1">
        <%--判断是否是本人访问 --%>
       <s:if test="#session.user.userName==#request.map.username">
            <%--判断是否有实名认证 --%>
            <s:if test="#request.map.realName=='' || #request.map.realName==null">${map.username}</s:if>
            <s:else>${map.realName }</s:else>
       </s:if>
       <s:else> 
            <s:if test="#request.map.realName=='' || #request.map.realName==null"><shove:sub  value="#request.map.username" size="1"   suffix="**" /></s:if>
            <s:else><shove:sub  value="#request.map.realName" size="1"   suffix="**" /></s:else>
       </s:else>
    </s:if>
    <%--企业 --%>
    <s:else>
           <%--判断是否是本人访问 --%>
           <s:if test="#session.user.userName==#request.map.username">
               <%--判断是否有实名认证 --%>
                <s:if test="#request.map.orgName=='' || #request.map.orgName==null">${map.username}</s:if>
                <s:else>${map.orgName }</s:else>
           </s:if>
           <s:else> 
                 <%--判断是否有实名认证 --%>
                <s:if test="#request.map.orgName=='' || #request.map.orgName==null"><shove:sub  value="#request.map.username" size="1"   suffix="***" /></s:if>
                <s:else><shove:sub  value="#request.map.orgName" size="1"   suffix="***" /></s:else>
           </s:else>
    </s:else></u>
</p>
<p style="">
	转让人签字&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</p>
<p style="">
	&nbsp;
</p>
<p style="">
	乙方（或委托代理人）（签字）：<u><s:iterator var="mapBeans" value="#request.user_invest_map"  status="sta">
                     <s:if test="#sta.index!=0">,</s:if>
                         <%--判断是否是本人访问 --%>
                       <s:if test="#session.user.userName==#request.mapBeans.username">
                                <%--判断是个人还是企业 --%>
                                <s:if test="#request.mapBeans.userType==1">
                                    <%--判断是否有实名认证 --%>
                                    <s:if test="#request.mapBeans.realName=='' || #request.mapBeans.realName==null">${mapBeans.username}</s:if>
                                    <s:else>${mapBeans.realName}</s:else>
                                </s:if>
                                <s:else>
                                     <%--判断是否有实名认证 --%>
                                    <s:if test="#request.mapBeans.orgName=='' || #request.mapBeans.orgName==null">${mapBeans.username}</s:if>
                                    <s:else>${mapBeans.orgName}</s:else>
                                </s:else>
                       </s:if>
                       <s:else> 
                               <s:if test="#request.mapBeans.userType==1">
                                     <%--判断是否有实名认证 --%>
                                    <s:if test="#request.mapBeans.realName=='' || #request.mapBeans.realName==null"><shove:sub  value="#request.mapBeans.username" size="1"   suffix="***" /></s:if>
                                    <s:else><shove:sub  value="#request.mapBeans.realName" size="1"   suffix="***" /></s:else>
                              </s:if>
                              <s:else>
                                    <%--判断是否有实名认证 --%>
                                    <s:if test="#request.mapBeans.orgName=='' || #request.mapBeans.orgName==null"><shove:sub  value="#request.mapBeans.username" size="1"   suffix="***" /></s:if>
                                    <s:else><shove:sub  value="#request.mapBeans.orgName" size="1"   suffix="***" /></s:else>
                              </s:else>
                       </s:else>
                 </s:iterator></u>
</p>
<p style="">
	&nbsp;
</p>
<p style="">
	丙方（签字/盖章）：深圳市易付通金融信息技术有限公司
</p>
<p style="">
	授权人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</p>
<p>
	&nbsp;
</p>
<p>
	签约地点：深圳市福田区
</p>
<p>
	签约时间： <u>${map.DateTime}</u>
</p>
<p>
	&nbsp;
</p>
<%--

<div class="nymain">
  <div class="lcnav">
    <div class="tab">
<div class="tabmain">
  <ul><li class="on" style="padding:0 20px;">债权转让及受让协议</li>
  </ul>
    </div>
    </div>
    <div class="line">
    </div>
  </div>
  <div class="lcmain">
    <div class="lcmain_l" style="width: 98%">
    <div class="lctab" style="padding:0 10px; width: 98%">
    <div class="gginfo">
    <h2>${paramMap.columName}</h2>
    <p>

	出借编号：${map.borrowId}${map.id }<br/>
	
	尊敬的 <u>&nbsp;&nbsp;
		<s:if test="#request.map.dbrealNam==''">
					${map.username }
		</s:if>
		<s:else>
			${map.dbrealName }
		</s:else>
	&nbsp;&nbsp;</u>  先生/女士，您好！
	通过三好资本资质评估与筛选，推荐您以受让他人既有的借贷合同的方式，通过p2p网络平台的方式，自助出借资金给如下借款人，详见《债权列表》，在您接受该批债权转让时，预期您的出借获益情况如下：
	    </p>
		<h2>债权列表</h2>
<ul>
	<li style="width: 200px;" >
		转让人（原债权人）
		<s:if test="#request.map.realName==''">
					${map.username }
		</s:if>
		<s:else>
			${map.realName }
		</s:else>
	</li>
	<li style="width: 350px;">
		  身份证号码：  ${map.idNo}
	</li>
	<li style="width: 200px;">
		  平台用户名 ： ${map.username }
	</li>
</ul>                   
<ul>
	<li style="width: 200px;" >
		  受让人（新债权人）： <s:if test="#request.map.dbrealName==''">
					${map.dbusername }
		</s:if>
		<s:else>
			${map.dbrealName }
		</s:else>
	</li>
	<li style="width: 350px;">
                         身份证号码 ： ${map.dbisno }
	</li>
	<li style="width: 200px;">
		  平台用户名 ： ${map.dbusername }
	</li>
</ul>
               <div style="clear: both;"></div>                                                      
转让债权明细：<span style="width: 300px;"></span>  货币单位：人民币（元） 
<table border="1" cellpadding="0" cellspacing="0" width="95%" >
	<tr>
		<td align="center">序号</td>
		<td align="center">借款人姓名</td>
		<td align="center">身份证号</td>
		<td align="center">出借到期日</td>
		<td align="center">债权价值</td>
		<td align="center">转让比例</td>
		<td align="center">本次转让债权价值</td>
		<td align="center">到期日资产总额</td>
	</tr>
	<tr>
		<td align="center">1</td>
		<td align="center">${mapContent.realName }</td>
		<td align="center">${mapContent.idNo }</td>
		<td align="center">${mapContent.auditTime }</td>
		<td align="center">${sumMap.sumPI }</td>
		<td align="center">${map.debtSum / sumMap.sumPI * 100}%</td>
		<td align="center">${map.debtSum }</td>
		<td align="center">${map.debtSum }</td>
	</tr>
</table>
    <p class="zw">${paramMap.content}</p>
    </div>

    </div>
    </div>
  
  </div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>

<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<script>
dqzt(3);
</script> --%>
</div>
</body>
</html>

