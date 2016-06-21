 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th colspan="8">信用等级及信用对应分数</th>
    </tr>
  <tr>
    <td align="center">等级</td>
    <td align="center">AA</td>
    <td align="center">A</td>
    <td align="center">B</td>
    <td align="center">C</td>
    <td align="center">D</td>
    <td align="center">E</td>
    <td align="center">HR</td>
    </tr>
  <tr>
    <td align="center">分数</td>
    <td align="center">100以上</td>
    <td align="center">99-90</td>
    <td align="center">89-80</td>
    <td align="center">79-70</td>
    <td align="center">69-50</td>
    <td align="center">49-30</td>
    <td align="center">30以下</td>
    </tr>
  <tr>
    <td align="center">标志</td>
    <td align="center"><img src="images/ico_15.jpg" width="34" height="22" /></td>
    <td align="center"><img src="images/ico_13.jpg" width="34" height="22" /></td>
    <td align="center"><img src="images/ico_11.jpg" width="34" height="22" /></td>
    <td align="center"><img src="images/ico_09.jpg" width="34" height="22" /></td>
    <td align="center"><img src="images/ico_07.jpg" width="34" height="22" /></td>
    <td align="center"><img src="images/ico_05.jpg" width="34" height="22" /></td>
    <td align="center"><img src="images/ico_03.jpg" width="34" height="22" /></td>
    </tr>
</table>
   <div class="biaoge" >
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <th colspan="4">信用总分：${userMsg.t_crediting}分&nbsp;<img src="images/ico_${userMsg.t_credit}.jpg" title="${userMsg.t_crediting}分" width="33" height="22" /> </th>
    </tr>
 <tr>
    <td align="center">&nbsp;</td>
    <td align="center">项目</td>
    <td align="center">状态</td>
    <td align="center">信用分数</td>
    </tr>
  <tr>
    <td align="center">基本信息</td>
    <td align="center">企业详细信息</td>
    <td align="center">
     <s:if test="#request.userMsg.t_person_status == '通过'">
     	${userMsg.t_person_status}
     </s:if>
     <s:else>
     	<a href="queryCompayData1.mht">${userMsg.t_person_status}</a>
     </s:else> 
    </td>
    <td align="center">
     	${userMsg.t_person_score}
    </td>
    </tr>
    <!--  <tr>
    <td align="center">工作/联系人信息</td>
    <td align="center">
     <s:if test="#request.userMsg.t_work_status == '通过'&&#request.userMsg.t_work_connector=='通过'">
     	${userMsg.t_work_status}/${userMsg.t_work_connector} 
     </s:if>
     <s:elseif test="#request.userMsg.t_work_status == '通过' &&#request.userMsg.t_work_connector=='审核失败'">
     	${userMsg.t_work_status}/<a href="queryCompayData.mht">${userMsg.t_work_connector}</a>
     </s:elseif>
     <s:elseif test="#request.userMsg.t_work_status == '通过' &&#request.userMsg.t_work_connector=='审核中'">
     	${userMsg.t_work_status}/<a href="queryCompayData.mht">${userMsg.t_work_connector}</a>
     </s:elseif>
     <s:elseif test="#request.userMsg.t_work_status == '审核中'&&#request.userMsg.t_work_connector=='通过'">
     	${userMsg.t_work_status}/<a href="queryCompayData.mht">${userMsg.t_work_connector}</a>
     </s:elseif>
     <s:elseif test="#request.userMsg.t_work_status == '审核失败'&&#request.userMsg.t_work_connector=='通过'">
     	${userMsg.t_work_status}/<a href="queryCompayData.mht">${userMsg.t_work_connector}</a>
     </s:elseif>
     <s:else>
     	<a href="queryCompayData.mht">${userMsg.t_work_status}</a><a href="querWorkData.mht">${userMsg.t_work_connector}</a>
     </s:else> 
    </td>
    <td align="center">
     	${userMsg.t_work_score}
    </td>
    </tr>-->
  <tr>
    <td rowspan="3" align="center">必要信用认证</td>
    <td align="center">营业执照</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_1_status == '通过'">
     	${userMsg.t_materrial_1_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_1_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_1_score}
    </td>
    </tr>
  <tr>
    <td align="center">税务登记证</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_2_status == '通过'">
     	${userMsg.t_materrial_2_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_2_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_2_score}
    </td>
    </tr>
  <tr>
    <td align="center">组织机构代码证</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_3_status == '通过'">
     	${userMsg.t_materrial_3_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_3_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_3_score}
    </td>
    </tr>
  
  <tr>
    <td rowspan="15" align="center">可选认证</td>
    <td align="center">近两年及今年完税证明</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_6_status == '通过'">
     	${userMsg.t_materrial_6_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_6_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_6_score}
    </td>
  </tr>
  <tr>
    <td align="center">增值税纳税申请表</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_7_status == '通过'">
     	${userMsg.t_materrial_7_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_7_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_7_score}
    </td>
  </tr>
  <tr>
    <td align="center">现场</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_4_status == '通过'">
     	${userMsg.t_materrial_4_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_4_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_4_score}
    </td>
    </tr>
  <tr>
    <td align="center">央行信用报告</td>
    <td align="center">
   	 <s:if test="#request.userMsg.t_materrial_5_status == '通过'">
     	${userMsg.t_materrial_5_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_5_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_5_score}
    </td>
    </tr>
  <tr>
    <td align="center">所得税纳税申请表</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_8_status == '通过'">
     	${userMsg.t_materrial_8_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_8_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_8_score}
    </td>
  </tr>
  <tr>
    <td align="center">营业税纳税申请表</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_9_status == '通过'">
     	${userMsg.t_materrial_9_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_9_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_9_score}
    </td>
  </tr>
  <tr>
    <td align="center">开户许可证（基本开户行）</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_10_status == '通过'">
     	${userMsg.t_materrial_10_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_10_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_10_score}
    </td>
  </tr>
  <tr>
    <td align="center">开户许可证（主要结算行）</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_11_status == '通过'">
     	${userMsg.t_materrial_11_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_11_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_11_score}
    </td>
    </tr>
  <tr>
    <td align="center">房产证明</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_12_status == '通过'">
     	${userMsg.t_materrial_12_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_12_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_12_score}
    </td>
    </tr>
   <!--  
  <tr>
    <td align="center">视频</td>
    <td align="center">
     
      <s:if test="#request.map.tmshipingauditStatus==3">
         通过
      </s:if>
      <s:elseif test="#request.map.tmshipingauditStatus==2"><a href="userPassData.mht" >不通过</a></s:elseif>
      <s:elseif test="#request.map.tmshipingauditStatus==1">待审核</s:elseif>
      <s:else>待上传</s:else>
     

    </td>
    <td align="center">
    
 ${map.tmshipingcriditing }
    
    </td>
    </tr>
   --> 
  <tr>
    <td align="center">项目承包合同书</td>
    <td align="center">
     <s:if test="#request.userMsg.t_materrial_13_status == '通过'">
     	${userMsg.t_materrial_13_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_13_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_13_score}
    </td>
    </tr>
  <tr>
    <td align="center">相关营运资质</td>
   	<td align="center">
   	 <s:if test="#request.userMsg.t_materrial_14_status == '通过'">
     	${userMsg.t_materrial_14_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_14_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_14_score}
    </td>
 </tr>
      <tr>
          <td align="center">授信申请书及附件</td>
    <td align="center">
     <s:if test="request.userMsg.t_materrial_15_status == '通过'">
     	${userMsg.t_materrial_15_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_15_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_15_score}
    </td>
    </tr> 
       <tr>
          <td align="center">年检纪录</td>
    <td align="center">
     <s:if test="request.userMsg.t_materrial_16_status == '通过'">
     	${userMsg.t_materrial_16_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_16_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_16_score}
    </td>
    </tr> 
     <tr>
          <td align="center">贷款卡</td>
    <td align="center">
     <s:if test="request.userMsg.t_materrial_17_status == '通过'">
     	${userMsg.t_materrial_17_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_17_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_17_score}
    </td>
    </tr> 
     <tr>
          <td align="center">担保</td>
    <td align="center">
     <s:if test="request.userMsg.t_materrial_18_status == '通过'">
     	${userMsg.t_materrial_18_status}
     </s:if>
     <s:else>
     	<a href="companyPictureDate.mht">${userMsg.t_materrial_18_status}</a>
     </s:else>
    </td>
    <td align="center">
    	${userMsg.t_materrial_18_score}
    </td>
    </tr> 
    
 
  <tr>
    <td rowspan="7" align="center">三好资本还款积分</td>
    <th align="center">项目</th>
    <th align="center">次数</th>
    <th align="center">分数</th>
  </tr>
   <tr>
    <td align="center">按时还款</td>
    <td align="center">
       ${userMsg.t_pre_count}
    </td>
    <td align="center">
    	${userMsg.t_pre_score}
    </td>
  </tr>
  <tr>
    <td align="center">提前还款</td>
    <td align="center">
       ${userMsg.t_pre_16_count}
    </td>
    <td align="center">
    	${userMsg.t_pre_16_score}
    </td>
    </tr>
  <tr>
    <td align="center">迟还款（逾期一天以上至10天以内的还款）</td>
    <td align="center">
       ${userMsg.t_over_10_count}
    </td>
    <td align="center">
    	${userMsg.t_over_10_score}
    </td>
    </tr>
  <tr>
    <td align="center">逾期还款（11-30天）</td>
    <td align="center">
       ${userMsg.t_over_30_count}
    </td>
    <td align="center">
    	${userMsg.t_over_30_score}
    </td>
    </tr>
  <tr>
    <td align="center">逾期还款（逾期31天以上至90天以内）</td>
    <td align="center">
       ${userMsg.t_over_90_count}
    </td>
    <td align="center">
    	${userMsg.t_over_90_score}
    </td>
    </tr>
  <tr>
    <td align="center">逾期还款（逾期90天以上的还款）</td>
    <td align="center">
       ${userMsg.t_over_91_count}
    </td>
    <td align="center">
    	${userMsg.t_over_91_score}
    </td>
    </tr>
 </table>
 </div>