<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<jsp:include page="/include/head.jsp"></jsp:include>
<link href="css/inside.css"  rel="stylesheet" type="text/css" />

</head>
<body style="line-height: 24px;">
<div class="nymain">
  <!--<div class="lcnav">
    <div class="tab">
<div class="tabmain">
  <ul><li >借款协议</li>
  </ul>
    </div>
    </div>
    
  </div>-->
   <!--  <div class="lcmain"> 
    <div class="lcmain_l" style="width: 98%">-->
    <div class="lctab" style="padding:0 10px; width: 98% ;border:none" >
    <div class="gginfo" style="border:none">
    <h2 style="font-size: 20px">借款协议</h2>
  <p align="right">
	 合同编号: <b><u>P2P—${map.DatesNumber}${map.id }</u><span class="red ml20">(注*：协议中带*的数量不等于原始字数，仅加密使用)</span></b>
</p>

<p>
    <br />
</p>
<p>
    <br />
</p>
<p>
    本借款合同（以下简称“本合同”）以融资项目实际成交时间为签订日期。
</p>
<p>
    协议的三方为：
</p>
<p>
    甲方（以下简称为借款人或融资人）：
    <%--个人 --%>
    <s:if test="#request.map.userType==1">
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
    </s:else>
</p>
<p>
    在三好资本平台用户名为：
    <%--个人 --%>
    <s:if test="#request.map.userType==1">
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
    </s:else>
</p>
<p>
    <%--个人 --%>
    <s:if test="#request.map.userType==1">
        身份证号：<%--判断是否是本人访问 --%>
       <s:if test="#session.user.userName==#request.map.username">
            ${map.isno}
       </s:if>
       <s:else> 
            <shove:sub  value="#request.map.isno" size="3"   suffix="***************" />
       </s:else>
    </s:if>
    <%--企业 --%>
    <s:else>
           营业执照号：<%--判断是否是本人访问 --%>
           <s:if test="#session.user.userName==#request.map.username">
                ${map.reg_num }
           </s:if>
           <s:else> 
                <shove:sub  value="#request.map.reg_num" size="3"   suffix="*********" />
           </s:else>
    </s:else>
</p>
<p>
    地址：
    <%--个人 --%>
    <s:if test="#request.map.userType==1">
        <%--判断是否是本人访问 --%>
       <s:if test="#session.user.userName==#request.map.username">
            ${map.address1}
       </s:if>
       <s:else> 
            <shove:sub  value="#request.map.address1" size="3"   suffix="***************" />
       </s:else>
    </s:if>
    <%--企业 --%>
    <s:else>
           <%--判断是否是本人访问 --%>
           <s:if test="#session.user.userName==#request.map.username">
                ${map.address2 }
           </s:if>
           <s:else> 
                <shove:sub  value="#request.map.address2" size="3"   suffix="*********" />
           </s:else>
    </s:else>
</p>

<p>
    联系电话：
        <%--判断是否是本人访问 --%>
       <s:if test="#session.user.userName==#request.map.username">
            ${map.phone}
       </s:if>
       <s:else> 
            <shove:sub  value="#request.map.phone" size="3"   suffix="***************" />
       </s:else>
</p>

<p>
    &nbsp;
</p>

        <s:if test="#request.user_invest_map==null || #request.user_invest_map.size==0">
             　暂无贷出者
         </s:if>
         <s:else>
	         <p>
	               乙方（以下简称投资人或贷款人）:
		         <s:iterator var="mapBeans" value="#request.user_invest_map"  status="sta">
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
                 </s:iterator>
	         </p>
	         <p> 在三好资本平台用户名为：
		         <s:iterator var="mapBeans" value="#request.user_invest_map"  status="sta">
                     <s:if test="#sta.index!=0">,</s:if>
                         <%--判断是否是本人访问 --%>
                         <s:if test="#session.user.userName==#request.mapBeans.username">
                                ${mapBeans.username}
                       </s:if>
                       <s:else> 
                             <shove:sub  value="#request.mapBeans.username" size="1"   suffix="***" />
                       </s:else>
                 </s:iterator>
	         </p>
             <p> 身份证号：
                 <s:iterator var="mapBeans" value="#request.user_invest_map"  status="sta">
                     <s:if test="#sta.index!=0">,</s:if>
                         <%--判断是否是本人访问 --%>
                         <s:if test="#session.user.userName==#request.mapBeans.username">
                                ${mapBeans.idNo}
                       </s:if>
                       <s:else> 
                             <shove:sub  value="#request.mapBeans.idNo" size="3"   suffix="***********" />
                       </s:else>
                 </s:iterator>
             </p>	       
             <p> 地址：
                 <s:iterator var="mapBeans" value="#request.user_invest_map"  status="sta">
                     <s:if test="#sta.index!=0">,</s:if>
                         <%--判断是否是本人访问 --%>
                         <s:if test="#session.user.userName==#request.mapBeans.username">
                                ${mapBeans.address1}${mapBeans.address2}
                       </s:if>
                       <s:else> 
                             <shove:sub  value="#request.mapBeans.address1" size="2"   suffix="*******" />
                             <shove:sub  value="#request.mapBeans.address2" size="2"   suffix="*******" />***
                       </s:else>
                 </s:iterator>
             </p>          
             <p> 联系电话：
                 <s:iterator var="mapBeans" value="#request.user_invest_map"  status="sta">
                     <s:if test="#sta.index!=0">,</s:if>
                         <%--判断是否是本人访问 --%>
                         <s:if test="#session.user.userName==#request.mapBeans.username">
                                ${mapBeans.phone}
                       </s:if>
                       <s:else> 
                             <shove:sub  value="#request.mapBeans.phone" size="3"   suffix="***********" />
                       </s:else>
                 </s:iterator>
             </p>                                      
        </s:else>
<p>
    <br />
</p>
<p>
    <br />
</p>


<div style="display: none;">
<p align="right">
     签约日期: <b><u>${map.DateTime}</u></b>
</p>
<p>
    协议三方定义:
</p>
<p>
    贷出者:
    
    
    
    
    
            <s:if test="#request.user_invest_map==null || #request.user_invest_map.size==0">
             　暂无贷出者
            </s:if>
            <s:else>
                <s:iterator var="mapBeans" value="#request.user_invest_map" >
            <b><u>
            
            <s:if test="#request.map.userType==1">
            
                <s:if test="%{#mapBeans.realName==''}">${mapBeans.username}</s:if>
                <s:else>${mapBeans.realName}</s:else>
                
            </s:if>
            <s:else>
                ${mapBeans.realName}
            </s:else>
            
            </u></b>
            <b><u>;</u></b> 
            </s:iterator>
            </s:else>
            
            
            
            
            
    
     ，以下称“贷出方”；
</p>
<p>
    <s:if test="#request.map.userType==1">
    借入者: <b>${map.realName} &nbsp;&nbsp;&nbsp; (<shove:sub  value="#request.map.isno" size="12"   suffix="******" />)</b>，以下称“借入方”；
    </s:if>
    <s:else>
    借入者: <b>${map.orgName} </b>，以下称“借入方”；
    </s:else>
</p>
<p>
    管理方: <b><u>三好资本</u></b>，以下称“管理方”。
</p>
<p>&nbsp;
    
</p>
<p>
    郑重承诺:
</p>
<p>
 ${contentMap.content }
</p>
<p>
    贷出者:
</p>
</div>
<p>&nbsp;
	
</p>
<div style="padding-left: 20px; padding-right: 20px; position:relative; backrgroun:#990000;clear:both;top:0;min-height:150px;">
<table border="1" cellspacing="0" cellpadding="0" style="border:none;padding-left: 5px;"   >
	<tbody>
		<tr>
			<td  valign="middle" style="border:solid black 1.0pt;background:#D9D9D9;">
				<p>
					<span style="font-family:宋体;color:windowtext;">出借人名称</span><span style="font-family:&quot;color:windowtext;"></span>
				</p>
			</td>
			<td  width="180px;" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
				<p>
					<span style="font-family:宋体;color:windowtext;">借出金额</span><span style="font-family:&quot;color:windowtext;">(</span><span style="font-family:宋体;color:windowtext;">人民币</span><span style="font-family:&quot;color:windowtext;">)</span>
				</p>
			</td>
			<td  width="180px;" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
				<p>
					<span style="font-family:宋体;color:windowtext;">借款期限</span><span style="font-family:&quot;color:windowtext;"></span><span style="font-family:宋体;color:windowtext;"></span><span style="font-family:&quot;color:windowtext;"></span>
				</p>
			</td>
			
				<td  width="180px;" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
				<p>
					<span style="font-family:宋体;color:windowtext;">年利率</span><span style="font-family:&quot;color:windowtext;"></span><span style="font-family:宋体;color:windowtext;"></span><span style="font-family:&quot;color:windowtext;"></span>
				</p>
			</td>
			
					<td  width="180px;" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
				<p>
					<span style="font-family:宋体;color:windowtext;">借款开始日期</span><span style="font-family:&quot;color:windowtext;"></span><span style="font-family:宋体;color:windowtext;"></span><span style="font-family:&quot;color:windowtext;"></span>
				</p>
			</td>
			
					<td  width="180px;" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
				<p>
					<span style="font-family:宋体;color:windowtext;">借款到期日期</span><span style="font-family:&quot;color:windowtext;"></span><span style="font-family:宋体;color:windowtext;"></span><span style="font-family:&quot;color:windowtext;"></span>
				</p>
			</td>
			
			<td width="180px;" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
				<p>
					<span style="font-family:宋体;color:windowtext;">总还款本息</span><span style="font-family:&quot;color:windowtext;">(</span><span style="font-family:宋体;color:windowtext;">人民币</span><span style="font-family:&quot;color:windowtext;">)</span>
				</p>
			</td>
		</tr>
		<s:if test="#request.investMaps==null || #request.investMaps.size==0">
					暂无数据
	</s:if>
	<s:else>
        
	<s:iterator var="mapBean" value="#request.investMaps" >
		
		<tr>
			<td width="150px" valign="top" style="border:solid black 1.0pt;background:white;">
				<p>
					<span style="font-family:宋体;color:windowtext;">
					       <%--判断是否是本人访问 --%>
					       <s:if test="#session.user.userName==#request.mapBean.username">
					            ${mapBean.username}
					       </s:if>
					       <s:else> 
					            <shove:sub  value="#request.mapBean.username" size="1"   suffix="****" />
					       </s:else>
					        ${bean.realName } ${bean.idNo }
					</span><span style="font-family:&quot;color:windowtext;"></span>
				</p>
			</td>
			<td valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;">￥${mapBean.recivedPrincipal }</span>
				</p>
			</td>
			
				<td  valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;">${mapBean.deadline }<s:if test="#mapBean.isDayThe==1"> 个月</s:if>
	<s:if test="#mapBean.isDayThe==2"> 天</s:if></span>
				</p>
			</td>
			
			
				<td width="100px" valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;">${mapBean.annualRate }%</span>
				</p>
			</td>
			
				<td  valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;">
					${mapBean.starTime}
					</span>
				</p>
			</td>
			
					<td valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;">	${mapBean.endTime} </span>
				</p>
			</td>
			
			<td valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;">￥${mapBean.sumPI }</span>
				</p>
			</td>
		</tr>
		</s:iterator>
		</s:else>
		
				<tr>
			<td width="150px" valign="top" style="border:solid black 1.0pt;background:white;">
				<p>
					<span style="font-family:宋体;color:windowtext;">总金额：</span>
				</p>
			</td>
			<td valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;">￥${sumMap.sumPal }</span>
				</p>
			</td>
			
				<td  valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;">${mapBean.deadline }<s:if test="#mapBean.isDayThe==1"> 个月</s:if>
	<s:if test="#mapBean.isDayThe==2"> 天</s:if></span>
				</p>
			</td>
			
			
				<td valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;"></span>
				</p>
			</td>
			
				<td  valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;"></span>
				</p>
			</td>
			
					<td  valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;">总还本息：</span>
				</p>
			</td>
			
			<td valign="top" style="border:solid black 1.0pt;background:white;">
				<p align="center" style="text-align:center;">
					<span style="font-family:&quot;color:windowtext;">￥${sumMap.sumPI }</span>
				</p>
			</td>
		</tr>
	</tbody>
</table>
<div style="position:absolute;bottom:0; margin-bottom:0px;height:180px;right:0;"><img src="images/yinzhang.png" style="width:200px;height:200px;"/></div>
</div>

<p>&nbsp;</p>

<div align="center">
<p>
    <p>
        丙方(以下简称居间人)：<span style="background-color:#FFFFFF;color:#E53333;">深圳市易付通金融信息技术有限公司</span>
    </p>
    <p>
        住址：<span style="color:#E53333;">深圳市福田区滨河路5022号联合广场A座3楼</span>
    </p>
    <p>
        法定代表人：<span style="color:#000000;">陈飞忠</span>
    </p>
</p>
<p>&nbsp;
    
</p>
<div style="position:relative;height:150px;">
    <div style="padding-top:30px;"> <p>
        丁方（以下简称担保人）：深圳市华融融资担保有限公司
    </p>
    <p>
        住址：深圳市福田区滨河大道5022号联合广场A座302
    </p>
    <p>
       法定代表人：舒曼
    </p>
    </div>
    <div style="position:absolute; top:-20px;left:100px; "><img src="images/yinzhang1.png"  style="width:200px;height:200px;"/></div>
</div>

<p>&nbsp;
    
</p>
<p>
    鉴于：
</p>
<p>
    1.居间人为互联网域名为www.sanhaodai.com网站(以下简称“三好资本平台”)的运营管理人，依法提供金融信息咨询及相关服务。
</p>
<p>
    2.借款人及投资人均已在三好资本平台注册，且同意遵守三好资本平台的各项行为准则。
</p>
<p>
    3.借款人向投资人申请借款，投资人同意向借款人出借款项，且借款人、投资人均为具有完全民事权利能力和完全民事行为能力的自然人或法人，根据中华人民共和国相关法律法规，合同各方本着平等自愿、诚实信用的原则，经协商一致，达成本协议，并保证共同遵守执行
</p>
<p>
    4.担保人系依法成立并有效存续的专业担保机构，具备融资担保的资质。经协议各方友好协商，就借款人借款、投资人投资、融资担保及相关服务事宜达成如下一致意见，以资共同遵守。
</p>
</div>

<p>&nbsp;
	
</p>
<p>
</p>

	
</p>
<div align="center"  style="display: none">
	<table border="1" cellspacing="0" cellpadding="0" style="border:none;">
		<tbody>
			<tr>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">借款详细用途</span><span style="font-family:&quot;color:windowtext;">:</span>
					</p>
				</td>
				<td width="397" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">${map.purpose}</span><span style="font-family:&quot;color:windowtext;"></span>
					</p>
				</td>
			</tr>
			<tr>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:宋体;color:windowtext;">借款本金数额</span><span style="font-family:&quot;color:windowtext;">:</span>
					</p>
				</td>
				<td width="397" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:&quot;color:windowtext;">${map.borrowAmount}</span>
					</p>
				</td>
			</tr>
			<tr>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">应偿还本息数额</span><span style="font-family:&quot;color:windowtext;">:</span>
					</p>
				</td>
				<td width="397" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:&quot;color:windowtext;">￥${sumMap.sumPI }</span>
					</p>
				</td>
			</tr>
			<tr>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:宋体;color:windowtext;">还款分期月数</span><span style="font-family:&quot;color:windowtext;">:</span>
					</p>
				</td>
				<td width="397" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:&quot;color:windowtext;">${map.deadline}</span><span style="font-family:宋体;color:windowtext;">
						<s:if test="#request.map.isDayThe==1">个月</s:if>
						<s:if test="#request.map.isDayThe==2">天</s:if>
						</span><span style="font-family:&quot;color:windowtext;"></span>
					</p>
				</td>
			</tr>
			<tr>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">借款利率</span><span style="font-family:&quot;color:windowtext;">:</span>
					</p>
				</td>
				<td width="397" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:&quot;color:windowtext;">${map.annualRate}%</span>
					</p>
				</td>
			</tr>
			<tr>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:宋体;color:windowtext;">还款方式</span><span style="font-family:&quot;color:windowtext;">:</span>
					</p>
				</td>
				<td width="397" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:宋体;color:windowtext;">
					   <s:if test="#request.map.paymentMode==1">
	按月分期
	</s:if>
				<s:elseif test="#request.map.paymentMode==2">
	按先息后本还款
	</s:elseif>
				<s:elseif test="#request.map.paymentMode==3">
	秒还
	</s:elseif>
				<s:elseif test="#request.map.paymentMode==4">
	一次性还本付息
	</s:elseif>
					</span><span style="font-family:&quot;color:windowtext;"></span>
					</p>
				</td>
			</tr>
			
			<tr>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:宋体;color:windowtext;">借款描述</span><span style="font-family:&quot;color:windowtext;">:</span>
					</p>
				</td>
				<td width="397" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:宋体;color:windowtext;">${map.detail}</span><span style="font-family:&quot;color:windowtext;"></span>
					</p>
				</td>
			</tr>
			<tr>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">还款日</span><span style="font-family:&quot;color:windowtext;">:</span>
					</p>
				</td>
				<td width="397" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">每月${map.days }日（24：00前，节假日不顺延）</span><span style="font-family:&quot;color:windowtext;"></span>
					</p>
				</td>
			</tr>
			<tr>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:宋体;color:windowtext;">还款起止日期</span><span style="font-family:&quot;color:windowtext;">:</span>
					</p>
				</td>
				<td width="397" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:Courier;color:red;">${map.starTime }  至  ${map.endTime }  止<span style="font-family:Courier;color:red;"></span></span>
					</p>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div >
<p>
    第一条：借款金额、期限、利率、用途、还款方式
</p>
<p>
    借款人同意通过居间人的“三好资本”平台向投资人借款，投资人同意通过居间人的“三好资本”平台向借款人投资并委托“三好资本”平台办理款项发放事宜：
</p>
<p>
    1．借款金额为本金人民币：<span style="text-decoration: underline;">${map.borrowAmount}</span>。具体投资人的出借金额以上述列表为准。
</p>
<p style="margin-left:31.5000pt;text-indent:-31.5000pt;">
    2．借款期限为<span style="text-decoration: underline;">${map.deadline}</span><s:if test="#request.map.isDayThe==1">个月</s:if><s:if test="#request.map.isDayThe==2">天</s:if>。借款起息日为:<span style="text-decoration: underline;">${map.starTime }</span> ,借款到期日为:<span style="text-decoration: underline;">${map.endTime }</span> 。详见还款计划书。
</p>
<p>【详见还款计划书（还款模型）】。</p>
<p>
    3．年利率（即预期年收益率）<span style="text-decoration: underline;">${map.annualRate}%</span>，本合同的利率为固定利率。月利率=年利率/12；日利率=年利率/360。
</p>
<p>
    4．借款用途为：<span style="text-decoration: underline;">${map.purpose}</span>。&nbsp;
</p>
<p>
    借款人承诺:所借款项按规定用途使用，不用于投资房地产和股票、债券、期货等的炒作，不挪作他用，不进行违法活动，否则由此产生的后果均由借款人承担。
</p>
<p>
    5．还款方式为：<span style="text-decoration: underline;"><s:if test="#request.map.paymentMode==1">按月分期</s:if><s:elseif test="#request.map.paymentMode==2"> 按先息后本还款</s:elseif><s:elseif test="#request.map.paymentMode==3">秒还</s:elseif><s:elseif test="#request.map.paymentMode==4">一次性还本付息</s:elseif></span>。
</p>
</div>

<p>&nbsp;
	
</p>
<p>
	所投标总体还款计划书
</p>
<p>&nbsp;
	
</p>
<div style="padding-left: 20px; padding-right: 20px;">
	<table border="1" cellspacing="0" cellpadding="0" style="border:none;"  >
		<tbody>
			<tr>
				<td width="57" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">期数</span><span style="font-family:&quot;color:windowtext;"></span>
					</p>
				</td>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">年利率</span><span style="font-family:&quot;color:windowtext;"></span><span style="font-family:宋体;color:windowtext;"></span><span style="font-family:&quot;color:windowtext;"></span>
					</p>
				</td>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">应还时间</span><span style="font-family:&quot;color:windowtext;"></span><span style="font-family:宋体;color:windowtext;"></span><span style="font-family:&quot;color:windowtext;"></span>
					</p>
				</td>
				<td width="198" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">应还本息</span><span style="font-family:&quot;color:windowtext;"></span>
					</p>
				</td>
					<td width="198" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">还款本金</span><span style="font-family:&quot;color:windowtext;"></span>
					</p>
				</td>
						<td width="198" valign="top" style="border:solid black 1.0pt;background:#D9D9D9;">
					<p>
						<span style="font-family:宋体;color:windowtext;">还款利息</span><span style="font-family:&quot;color:windowtext;"></span>
					</p>
				</td>
			</tr>
			
				<s:if test="#request.borrow_map==null || #request.borrow_map.size==0">
						暂无数据
			</s:if>	<s:else>
	<s:iterator value="#request.borrow_map" var="bean">
			<tr>
				<td width="57" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:&quot;color:windowtext;">${bean.repayPeriod }</span>
					</p>
				</td>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:&quot;color:windowtext;">${bean.annualRate }%</span>
					</p>
				</td>
				<td width="142" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:&quot;color:windowtext;"><s:date name="#bean.repayDate" format="yyyy-MM-dd"/></span>
					</p>
				</td>
				<td width="198" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:&quot;color:windowtext;">${bean.isp }</span>
					</p>
				</td>
					<td width="198" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:&quot;color:windowtext;">${bean.stillPrincipal }</span>
					</p>
				</td>
					<td width="198" valign="top" style="border:solid black 1.0pt;background:white;">
					<p>
						<span style="font-family:&quot;color:windowtext;">${bean.stillInterest }</span>
					</p>
				</td>
			</tr>
			</s:iterator>
			</s:else>
			
		</tbody>
	</table>
</div>
<p>&nbsp;
	
</p>
<p>&nbsp;
	
</p>

<div style="color:windowtext;">
  ${mapContent.content}
  </div>
<p>
	（以下无正文）
</p>
<p>
    <br />
</p>
<p>
    甲方： 
    <%--个人 --%>
    <s:if test="#request.map.userType==1">
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
    </s:else>
</p>
<p style="display: none;">
    在三好资本平台用户名为：${map.username }
</p>
<p>
    &nbsp;
</p>

 <s:if test="#request.user_invest_map==null || #request.user_invest_map.size==0">
      　暂无贷出者
  </s:if>
  <s:else>
             <p>
                   乙方：
                 <s:iterator var="mapBeans" value="#request.user_invest_map"  status="sta">
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
                 </s:iterator>
             </p>
        </s:else>
        <%--<s:else>
            <p>
                   乙方：
                        判断是否是本人访问 
                       <s:if test="#session.user.realName==#user_invest_map[0].realName">
                                判断是个人还是企业 
                                <s:if test="#request.user_invest_map[0].userType==1">
                                    判断是否有实名认证 
                                    <s:if test="#user_invest_map[0].realName=='' || #user_invest_map[0].realName==null">${user_invest_map[0].username}</s:if>
                                    <s:else>${user_invest_map[0].realName}</s:else>
                                </s:if>
                                <s:else>
                                     判断是否有实名认证 
		                            <s:if test="#user_invest_map[0].realName=='' || #user_invest_map[0].realName==null">${user_invest_map[0].username}</s:if>
		                            <s:else>${user_invest_map[0].realName}</s:else>
                                </s:else>
                       </s:if>
                       <s:else> 
		                       <s:if test="#request.user_invest_map[0].userType==1">
		                             判断是否有实名认证 
                                    <s:if test="#user_invest_map[0].realName=='' || #user_invest_map[0].realName==null"><shove:sub  value="#request.user_invest_map[0].username" size="1"   suffix="***" /></s:if>
                                    <s:else><shove:sub  value="#request.user_invest_map[0].realName" size="1"   suffix="***" /></s:else>
		                      </s:if>
		                      <s:else>
		                          <shove:sub  value="#request.user_invest_map[0].orgName" size="1"   suffix="***" />
		                      </s:else>
                       </s:else>
             </p>
        </s:else>

--%><p>
    <br />
</p>


<p>
	丙方：<b><u>深圳市易付通金融信息技术有限公司</u></b>
</p>
<p>
    丁方：<b><u>深圳市华融融资担保有限公司</u></b>
</p>
<p>
    <p>
        ${map.DateTime}
    </p>
    <p>
        合同签订地：深圳市福田区
    </p>
</p>
<br />
</div></div><!-- </div> </div> --></div>

<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<script>
//dqzt(2);
</script>

</body>
</html>

