<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="项目模式" var="title"  scope="request" />
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">var menuIndex = 5;</script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>

<!--内容区域-->
<div class="wrap">
  <div class="w950">
	  <ol class="breadcrumb mb0 bg-none">
		  <li><a href="/">首页 </a></li>
		  <li>关于我们</li>
		  <li class="active">业务模式</li>
	  </ol>
  	<jsp:include page="/WEB-INF/page/helpcenter/aboutme_left.jsp"></jsp:include>
    <div class="r_main fr w750 mb bg-white">
    	<div class="pub_tb bottom"><h3>业务模式</h3></div><div class="clearfix"></div>
    	<div class="model_con_t dbjk_model pr70">
    		<img src="images/model_one.png" width="270" height="280" class="fl">
			<table  class="fl"><tbody><tr><td>
			<table>
			<tbody>
			<tr><td colspan="3" height="20"></td></tr>
			<tr><td width="20"></td><td width="15"><img src="images/ico1.png"></td><td>借款方向融资性担保公司提供足额抵押，提出贷款需求</td></tr>
			<tr><td></td><td><img src="images/ico1.png"></td><td>融资性担保公司审核借款方资质、背景、还款能力之后，推荐优质借款项目给三好资本</td></tr>
			<tr><td></td><td><img src="images/ico1.png"></td><td>三好资本风控部门再次进行审核借款资料之后借款项目信息发布到平台</td></tr>
			<tr><td></td><td><img src="images/ico1.png"></td><td>投资人通过三好资本平台以直接投资的形式投资有担保的借款项目</td></tr>
			<tr><td></td><td><img src="images/ico1.png"></td><td>项目的借款凑集完成后，三好资本放款到借款方</td></tr>
			<tr><td></td><td><img src="images/ico1.png"></td><td>借款方按合同约定支付利息和本金，投资人获得利息和本金</td></tr>
			<tr><td></td><td><img src="images/ico1.png"></td><td>担保公司对借款项目提供全额本息担保，逾期先行赔付，保障投资人资金安全</td></tr>
			</tbody></table>
			</td>
			<td></td></tr></tbody></table>
		</div>
		
		<div class="model_con_t border_top_dcdcdc clearfix" style="background: #F3F9C4;">
			
			<table width="100%"><tbody><tr><td>
			<table>
			<tbody><tr><td colspan="3" class="model_top"><span>合作机构</span>严格合作机构审核，控制风险源头</td></tr>
			<tr><td colspan="3" height="20"></td></tr>
			<tr><td width="50"></td><td width="110"><div class="model_radius">客观指标</div></td><td class="font16px">注册资本，信用等级，经营年限，财务状况</td></tr>
			<tr><td width="50"></td><td width="110"><div class="model_radius">经营指标</div></td><td class="font16px">项目周转频率、坏账率、法律诉讼记录</td></tr>
			<tr><td width="50"></td><td width="110"><div class="model_radius">综合评定</div></td><td class="font16px">确定授信额度</td></tr>
			
			</tbody></table>
			</td>
			<td>
				<div class="gundong">
				    <a href="jg_hrrzdb.mht"  class="mr20"><img src="images/hzjg/m9.png"></a>
					<a><img src="images/hzjg/m10.png"></a>
				</div>
			</td></tr></tbody></table>
		</div>
		<div class="model_con_t border_top_dcdcdc " style="background: #F9F9F9;">
		<table width="100%">
		<tbody><tr><td colspan="3" class="model_top"><span>资金安全</span>100%本息保障</td></tr>
		<tr><td colspan="3" height="20"></td></tr>
		<tr><td width="50"></td><td width="110"><div class="model_radius font12px">第一重保障</div></td><td class="font16px">合作机构为其推荐的每一笔借款提供100%连带责任担保，进行全额赔付。</td></tr>
		<tr><td width="50"></td><td width="110"><div class="model_radius font12px">第二重保障</div></td><td class="font16px">若合作机构不能赔付，三好资本要求合作机构提供保证金，一旦合作机构无法代偿，三好资本会启动保证金进行代偿。</td></tr>
		<tr><td width="50"></td><td width="110"><div class="model_radius font12px">第三重保障</div></td><td class="font16px">若保证金也不能覆盖风险，借款合同仍然有效，三好资本会协助投资人通过法律程序解决违约问题。</td></tr>
		<tr><td colspan="3" align="center" class=" pt40 pb20"><img src="images/model_img5.png"></td></tr>
		</tbody></table>
		</div>
    </div>
  </div>
</div>
<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	$(".html_left a:eq(2)").addClass("hover");
})
</script>
</body>
</html>
