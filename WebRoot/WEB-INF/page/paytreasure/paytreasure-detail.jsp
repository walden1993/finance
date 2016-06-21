<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="涨薪宝详情" scope="request" var="title"></c:set>
<jsp:include page="/include/head.jsp"></jsp:include>
<link rel="stylesheet" href="css/bidlist.css" type="text/css"  media="screen"/>
<style type="text/css">
a:HOVER {
	color: #26b0ff;
}
</style>
<script type="text/javascript">
	var menuIndex = 1;
</script>
</head>
<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="wrap mb15">
		<div class="w950 detail_borrow ">
			<ol class="breadcrumb mb0 bg-none">
			  <li><a href="/">首页 </a></li>
			  <li class="active">涨薪宝</li>
		   </ol>
			<div class="one">
				<div class="info_left fl">
					<%-- <div style="margin:10px 22px 0 22px;">
							<div class="f15 pt30">预计年化收益率</div>
							<h1 style="font-size: 70px;">${map.paytreasure.rate}%</h1>
							<div class="f15">预计收益到账时间：${map.showdate}，每份收益${map.paytreasure.payprofit}，10000元/份</div>
						</div>
					</div> --%>
					<div style="margin:10px 22px 0 22px;">
			         
			          <div class="info_left_title  f25 ">
				      <div class="fl bold700" style="position:relative;max-width:550px;overflow: hidden;white-space:nowrap;text-overflow:ellipsis;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;">
				      涨薪宝-金秋1号
				      <s:if test="#request.map.paytreasure.isopen==1">
				      ——<span class="red">(尚未开始)</span>
				      </s:if>
				      </div>
			           
			          </div>
			       <div class="info_left_main">
			         <table cellpadding="0" cellspacing="0" class="infoTab" width="100%">
			           <tbody>
			           <tr valign="center">
			          		<td><h1>年化收益率</h1><h2 class="orange"><span class="f40">${map.paytreasure.rate}</span>%</h2></td>
			          		<td style="border:none;"><h1>每万份收益&nbsp;</h1><h2 class="orange"><span class="f30">${map.paytreasure.payprofit}</span>元</h2></td>
			           </tr>
			         </tbody></table>
			       </div>
			       <div class="info_left_bottom clearfix">
			       	<div class="fl w050 ">
					 <div class="info_left_bottom_path fl">
					   	 <p>收益方式：<span class="color000">按日计息</span></p>
					 </div>
					 <div class="info_left_bottom_path fl">
					    <p class="fl">认购条件：<span class="color000">1000元起购</span>
					    </p>
					 </div>
					 </div>
					 <div class="fr w050">
					 	<div class="info_left_bottom_path fl">
					    <p>赎回方式：<span class="color000">可随时赎回</span></p>
						 </div>
						 <div class="info_left_bottom_path fl">
						    <p>安全保障：<span class="color000">100%本息保障</span></p>
						 </div>
					 </div>
					 
			       </div>
			         </div>
				</div>
				<div class="info_right fr">
					<div style="margin:10px 25px 0 25px;">
						<div class="info_left_title">
							<div class="bold700 f25 fl"
								style="position:relative;max-width:550px;overflow: hidden;white-space:nowrap;text-overflow:ellipsis;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 1;border-bottom: 1px solid #ccc;">转入金额</div>
							<s:if test="#session.user!=null"><div class="fr"><a class="blue" href="outPayTreasureInit.mht">我要转出</a></div></s:if>
						</div>
						<div class="info_right_ye">
				         	<s:if test="#session.user!=null"><p class="fl" style="max-width: 200px;overflow: hidden;">账户可用余额：<span class="f14"><font class="orange">${map.usableSum.usableSum}</font></span> 元</p><a class="fr button-a mt5" href="rechargeInit.mht" target="_blank">充值</a></s:if><s:else>账户可投余额：登录后可见，<a href="login.mht" class="red">立即登录</a></s:else>
				         </div>

						<div class="info_amount">
							<input type="text" maxlength="10" name="borrowAmount" data-container="body"
								data-placement="left" data-content="" id="borrowAmount"
								onblur="calcAmount(this.value)" style="ime-mode:disabled;"
								ondragenter="return false"
								onpaste="return!clipboardData.getData('text').match(/[\u4e00-\u9fa5]/gi)"
								onkeypress="if(event.keyCode<48||event.keyCode>57||event.keyCode==8)event.returnValue=false;"
								placeholder="请输入投资金额" class="input h40"
								onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'100')}else{this.value=this.value.replace(/\D/g,'')}"
								onkeyup="calcAmount(this.value);" autocomplete="off">
							<div class="clear"></div>
						</div>
						<div class="mt20">
							<input type="password" value="" class="input h40"
								data-container="body" data-placement="left" data-content=""
								id="dealPWD" placeholder="请输入交易密码" autocomplete="off">
						</div>
						<div class="mt20"><input type="checkbox" checked="checked" disabled="disabled" id="proctol"/>我同意<a href="payTreasureProtocol.mht" id="proctol_a" class="blue fancybox.ajax">《三好资本涨薪宝投资协议》</a></div>
						<div class="info_right_sy mt20 w">
							<div class="fl">
								每日收益：<span id="yjsy">0</span>元
							</div>

							<input id="invest" type="button" class="button w120 fr"
								value="立即加入">
						</div>

					</div>
				</div>
			</div>

			<div class="two mt8 clearfix">
				<div class="left  fl mb20 wrap">
					<div class="m20">
						<ul class="nav nav-tabs  f16" role="tablist">
							<li role="presentation" class="active"><a href="#home"
								aria-controls="home" role="tab" data-toggle="tab">涨薪宝介绍</a>
							</li>
							<li role="presentation"><a href="#profile"
								aria-controls="profile" role="tab" data-toggle="tab">产品详情</a>
							</li>
							<li role="presentation"><a href="#messages"
								aria-controls="messages" role="tab" data-toggle="tab">常见问题</a>
							</li>
						</ul>

						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="home">
								<div class="clearfix">
									<div class="fl">
										<p>
										    <span style=";font-family:微软雅黑;font-size:14px"><br/></span>
										</p>
										<p>
										    <span style=";font-family:微软雅黑;font-weight:bold;font-size:19px">活期理财，随存随取</span>
										</p>
										<p>
										    <span style=";font-family:微软雅黑;font-size:14px">赎回最快一日到账，存取灵活自由</span>
										</p>
									</div>
									<div   class="fr text-center w050 mt20"  ><img alt="三好资本" height="120" width="150" src="images/financedetail/zxb_1.png" /></div>
								</div>
								<hr/>
								<div class="clearfix">
									<div class="fl">
										<p>
										    <span style=";font-family:微软雅黑;font-weight:bold;font-size:19px">超高的收益率</span>
										</p>
										<p>
										    <span style=";font-family:微软雅黑;font-size:14px">年化收益<span class="money bold">${map.paytreasure.rate}%</span>，是银行活期存款的15倍，是宝宝类产品的2倍</span>
										</p>
										<p>
										    <span style=";font-family:微软雅黑;font-size:14px">(注：年化收益将随所投资的金融产品的收益波动会略有上下的浮动)</span>
										</p>
										
									</div>
										<div   class="fr text-center w050 mt20"  ><img alt="三好资本" height="120" width="150" src="images/financedetail/zxb_2.png" /></div>
									</div>
								<hr/>
								<div class="clearfix">
									<div class="fl">
										<p>
										    <span style=";font-family:微软雅黑;font-weight:bold;font-size:19px">独有的安全策略</span>
										</p>
										<p>
										    <span style=";font-family:微软雅黑;font-size:14px">由担保机构<span class="money bold">全额担保 </span>100%本息保障</span>
										</p>
										<p>
										    <br/>
										</p>
									</div>
									<div   class="fr text-center w050 mt20"  ><img alt="三好资本" height="120" width="150" src="images/financedetail/zxb_3.png" /></div>
								</div>
								
								<hr/>
								<div class="clearfix">
									<div class="fl">
									<p>
										    <span style=";font-family:微软雅黑;font-weight:bold;font-size:19px">涨薪宝转入后，收益首次显示时间如下：（节假日顺延）</span>
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
										
									</div>
									<%-- <div   class="fr  w050 "  >
									<p>
										    <br/>
										</p>
										<p style="line-height: 1.75em;">
										    <span style="font-size: 14px;">您在当日15：00前转入涨薪宝的资金在第二个工作日进行份额确认。</span>
										</p>
										<p style="line-height: 1.75em;">
										    <span style="color: rgb(255, 0, 0); font-size: 14px;">注：</span>
										</p>
										<p style="line-height: 1.75em;">
										    <span style="font-size: 14px;">1、15:00后转入的资金会顺延1个工作日确认。</span>
										</p>
										<p style="line-height: 1.75em;">
										    <span style="font-size: 14px;">2、双休日及国家法定假期间，不进行份额确认。</span>
										</p>
										<p style="line-height: 1.75em;">
										    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;"></span><br/>
										</p>
									</div> --%>
								</div>
								
								
									
							</div>
							<div role="tabpanel" class="tab-pane" id="profile">
								<table style="margin-top: 10px;border: 1px solid #eee;" class="w">
    <tbody>
        <tr class="firstRow">
            <td style="background: rgb(240, 244, 247); border-width: 1px; border-style: solid; border-color: rgb(214, 219, 222) rgb(232, 236, 239) rgb(232, 236, 239) rgb(214, 219, 222); padding: 15px 20px;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">名称</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-top-color: rgb(214, 219, 222); border-bottom-color: rgb(232, 236, 239); border-top-width: 1px; border-bottom-width: 1px; border-top-style: solid; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">涨薪宝-金秋1号</span>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">介绍</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">涨薪宝是由平台与相关金融机构提供的理财产品，共同设计打造的一款活期理财产品，按日计息，随转随出。</span>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">投资范围</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">货币基金、ETF基金、固定收益类信托计划、基金公司资管计划、契约式私募投资基金等稳定收益投资。</span>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">期限</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">不定期，计息中金额可以随时申请赎回</span>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">认购总额</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">2000万元</span>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">预期年化收益率</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">${map.paytreasure.rate}%</span>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">计息日期</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">根据工作日的15：00点前后，按计息规则结算。</span>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">加入规则</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; margin-top: 0px; margin-right: 0px; margin-bottom: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">加入金额1000元起</span>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">赎回规则</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">赎回资金1元起</span>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">赎回到账时间</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">涨薪宝赎回资金立即到账三好资本余额</span>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">每日赎回处理上限</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">无上限</span>
                </p>
            </td>
        </tr>
        <tr>
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">费用</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">加入费率：0.00%</span><span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;"><br/></span><span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">赎回费率：0.00%</span>
                </p>
            </td>
        </tr>
        <tr class="hidden">
            <td style="background: rgb(240, 244, 247); padding: 15px 20px; border-right-color: rgb(232, 236, 239); border-bottom-color: rgb(232, 236, 239); border-left-color: rgb(214, 219, 222); border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid;" valign="top" width="100">
                <p style="text-align: right; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">投资协议</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); padding: 15px 20px; border-bottom-color: rgb(232, 236, 239); border-bottom-width: 1px; border-bottom-style: solid;" valign="top" width="448">
                <p style="text-align: left; text-indent: 0px;">
                    <span style="color: rgb(105, 115, 123); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;">[</span><span style="color: rgb(52, 156, 216); letter-spacing: 0px; font-family: 微软雅黑; font-size: 12px; font-style: normal; font-weight: normal;"><a href="">点击查看</a></span>]
                </p>
            </td>
        </tr>
    </tbody>
</table>
<p>
    &nbsp;
</p>
							</div>

							<div role="tabpanel" class="tab-pane" id="messages">
								<div class="mt10">
									<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
									<h2 style=";font-family:微软雅黑;font-weight:bold;font-size:19px">介绍</h2>
									<div class="panel panel-default">
									    <div class="panel-heading" role="tab" id="headingOne_1">
									      <h4 class="panel-title">
									        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne_1" aria-expanded="false" aria-controls="collapseOne_1">
									          什么是涨薪宝？
									        </a>
									      </h4>
									    </div>
									    <div id="collapseOne_1" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne_1">
									      <div class="panel-body"><p>
										<span style="font-size:14px;line-height:2;">涨薪宝是由平台与相关金融机构提供的理财产品，共同设计打造的一款活期理财产品，按日计息，随转随出。</span><span></span> 
										</p></div>
									    </div>
									  </div>
									  
									  <div class="panel panel-default">
									    <div class="panel-heading" role="tab" id="headingOne_2">
									      <h4 class="panel-title">
									        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne_2" aria-expanded="false" aria-controls="collapseOne_1">
									          涨薪宝使用有期限限制？
									        </a>
									      </h4>
									    </div>
									    <div id="collapseOne_2" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne_2">
									      <div class="panel-body"><p>
										<span style="font-size:14px;line-height:2;">不定期产品，没有期限限制，计息中金额可以随时申请赎回。</span><span></span> 
										</p></div>
									    </div>
									  </div>
									  
									  <div class="panel panel-default">
									    <div class="panel-heading" role="tab" id="headingOne_3">
									      <h4 class="panel-title">
									        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne_3" aria-expanded="false" aria-controls="collapseOne_1">
									          涨薪宝年化利率是多少？
									        </a>
									      </h4>
									    </div>
									    <div id="collapseOne_3" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne_3">
									      <div class="panel-body"><p>
										<span style="font-size:14px;line-height:2;">涨薪宝年化利率现为${map.paytreasure.rate}%</span><span></span> 
										</p></div>
									    </div>
									  </div>
									  
									  <h2 style=";font-family:微软雅黑;font-weight:bold;font-size:19px">收益</h2>
									  <div class="panel panel-default">
									    <div class="panel-heading" role="tab" id="headingOne_4">
									      <h4 class="panel-title">
									        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne_4" aria-expanded="false" aria-controls="collapseOne_4">
									          涨薪宝的收益如何计算？
									        </a>
									      </h4>
									    </div>
									    <div id="collapseOne_4" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne_4">
									      <div class="panel-body"><p>
										<span style="font-size:14px;line-height:2;">涨薪宝每日万份收益为${map.paytreasure.payprofit}元，投资一年，年化收益率为${map.paytreasure.payprofit}*365/10000=${map.paytreasure.rate}%</span><span></span> 
										</p></div>
									    </div>
									  </div>
									  
									  
									  <div class="panel panel-default">
									    <div class="panel-heading" role="tab" id="headingOne_5">
									      <h4 class="panel-title">
									        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne_5" aria-expanded="false" aria-controls="collapseOne_5">
									         加入涨薪宝后什么时候显示收益？
									        </a>
									      </h4>
									    </div>
									    <div id="collapseOne_5" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne_5">
									      <div class="panel-body">
									      <p>
    <span style="font-family: 微软雅黑,Microsoft YaHei;"></span>
</p>
<span>涨薪宝转入后，收益首次显示时间如下：（节假日顺延）</span>
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
<table width="561" class="hidden">
    <tbody>
        <tr style="height: 33px;" class="firstRow">
            <td style="background: rgb(79, 129, 189); border-width: 1px; border-style: solid dotted solid solid; border-color: rgb(79, 129, 189) windowtext rgb(79, 129, 189) rgb(79, 129, 189); padding: 0px;" valign="center" width="216">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(255, 255, 255); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: bold;">转入时间</span>
                </p>
            </td>
            <td style="background: rgb(79, 129, 189); border-width: 1px 1px 1px 0px; border-style: solid solid solid dotted; border-color: rgb(79, 129, 189) rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="152">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(255, 255, 255); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: bold;">开始计算收益</span>
                </p>
            </td>
            <td style="background: rgb(79, 129, 189); border-width: 1px 1px 1px 0px; border-style: solid solid solid dotted; border-color: rgb(79, 129, 189) rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="192">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(255, 255, 255); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: bold;">收益到账</span>
                </p>
            </td>
        </tr>
        <tr style="height: 24px;">
            <td style="background: rgb(255, 255, 255); border-width: medium 0px 1px 1px; border-style: none dotted solid solid; border-color: currentColor windowtext rgb(79, 129, 189) rgb(79, 129, 189); padding: 0px;" valign="center" width="216">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: bold;">周一15:00至周二15:00</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px; border-style: none solid solid dotted; border-color: currentColor rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="152">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: normal;">周三</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px; border-style: none solid solid dotted; border-color: currentColor rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="192">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: normal;">周四</span>
                </p>
            </td>
        </tr>
        <tr style="height: 30px;">
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px; border-style: none dotted solid solid; border-color: currentColor windowtext rgb(79, 129, 189) rgb(79, 129, 189); padding: 0px;" valign="center" width="216">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: bold;">周二15:00至周三15:00</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px 0px; border-style: none solid solid dotted; border-color: currentColor rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="152">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: normal;">周四</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px 0px; border-style: none solid solid dotted; border-color: currentColor rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="192">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: normal;">周五</span>
                </p>
            </td>
        </tr>
        <tr style="height: 26px;">
            <td style="background: rgb(255, 255, 255); border-width: medium 0px 1px 1px; border-style: none dotted solid solid; border-color: currentColor windowtext rgb(79, 129, 189) rgb(79, 129, 189); padding: 0px;" valign="center" width="216">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: bold;">周三15:00至周四15:00</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px; border-style: none solid solid dotted; border-color: currentColor rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="152">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: normal;">周五</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px; border-style: none solid solid dotted; border-color: currentColor rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="192">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: normal;">周六</span>
                </p>
            </td>
        </tr>
        <tr style="height: 26px;">
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px; border-style: none dotted solid solid; border-color: currentColor windowtext rgb(79, 129, 189) rgb(79, 129, 189); padding: 0px;" valign="center" width="216">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: bold;">周四15:00至周五15:00</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px 0px; border-style: none solid solid dotted; border-color: currentColor rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="152">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: normal;">下周一</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px 0px; border-style: none solid solid dotted; border-color: currentColor rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="192">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: normal;">下周二</span>
                </p>
            </td>
        </tr>
        <tr style="height: 28px;">
            <td style="background: rgb(255, 255, 255); border-width: medium 0px 1px 1px; border-style: none dotted solid solid; border-color: currentColor windowtext rgb(79, 129, 189) rgb(79, 129, 189); padding: 0px;" valign="center" width="216">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: bold;">周五15:00至周一15:00</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px; border-style: none solid solid dotted; border-color: currentColor rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="152">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: normal;">下周二</span>
                </p>
            </td>
            <td style="background: rgb(255, 255, 255); border-width: medium 1px 1px; border-style: none solid solid dotted; border-color: currentColor rgb(79, 129, 189) rgb(79, 129, 189) windowtext; padding: 0px;" valign="center" width="192">
                <p style="text-align: center; line-height: 25px; text-indent: 0px;">
                    <span style="color: rgb(0, 0, 0); letter-spacing: 0px; font-family: 微软雅黑; font-size: 16px; font-style: normal; font-weight: normal;">下周三</span>
                </p>
            </td>
        </tr>
    </tbody>
</table>
<p>
    <span style="font-family: 微软雅黑,Microsoft YaHei;"></span>&nbsp;
</p>
									      </div>
									    </div>
									    
									  </div>
									  
									  <h2 style=";font-family:微软雅黑;font-weight:bold;font-size:19px">购买</h2>
									  <div class="panel panel-default">
									    <div class="panel-heading" role="tab" id="headingOne_6">
									      <h4 class="panel-title">
									        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne_6" aria-expanded="false" aria-controls="collapseOne_6">
									          如何购买涨薪宝？
									        </a>
									      </h4>
									    </div>
									    <div id="collapseOne_6" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne_6">
									      <div class="panel-body"><p>
										<span style="font-size:14px;line-height:2;">直接在涨薪宝系统中购买。</span><span></span> 
										</p></div>
									    </div>
									  </div>
									  
									  
									  
									  <div class="panel panel-default">
									    <div class="panel-heading" role="tab" id="headingOne_7">
									      <h4 class="panel-title">
									        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne_7" aria-expanded="false" aria-controls="collapseOne_7">
									          加入有限制吗？
									        </a>
									      </h4>
									    </div>
									    <div id="collapseOne_7" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne_7">
									      <div class="panel-body"><p>
										<span style="font-size:14px;line-height:2;">1000元起，无限制</span><span></span> 
										</p></div>
									    </div>
									  </div>
									  
									  <h2 style=";font-family:微软雅黑;font-weight:bold;font-size:19px">赎回</h2>
									  <div class="panel panel-default">
									    <div class="panel-heading" role="tab" id="headingOne_8">
									      <h4 class="panel-title">
									        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne_8" aria-expanded="false" aria-controls="collapseOne_8">
									         涨薪宝是否收费？
									        </a>
									      </h4>
									    </div>
									    <div id="collapseOne_8" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne_8">
									      <div class="panel-body"><p>
										<span style="font-size:14px;line-height:2;">所有操作的免费的。</span><span></span> 
										</p></div>
									    </div>
									  </div>
									  
									  <div class="panel panel-default">
									    <div class="panel-heading" role="tab" id="headingOne_9">
									      <h4 class="panel-title">
									        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne_9" aria-expanded="false" aria-controls="collapseOne_9">
									         有无赎回限制？
									        </a>
									      </h4>
									    </div>
									    <div id="collapseOne_9" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne_9">
									      <div class="panel-body"><p>
										<span style="font-size:14px;line-height:2;">无限制。</span><span></span> 
										</p></div>
									    </div>
									  </div>
									  
									  
									  
									</div>
								</div>
							</div>

							<div role="tabpanel" class="tab-pane" id="cailiao"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript">
		$(function(){
			$('#proctol_a').fancybox();
			
			$("#invest").click(function(){
				var amount = $("#borrowAmount").val();
				var dealpwd = $("#dealPWD").val();
				var proctol = $("#proctol").val();
				param["paramMap.amount"] = amount;
				param["paramMap.dealpassword"] = dealpwd;
				if(${map.paytreasure.isopen}==1){show("尚未开始，敬请期待");return;}
				if(isBlank(amount)){show("请输入投资金额");return;}
				if(isBlank(dealpwd)){show("请输入交易密码");return;}
				$.dispatchPost("intoPayTreasure.mht",param,function(data){
					if(data==1){
						show("请输入投资金额");
					}if(data==11){
						show("尚未开始，敬请期待");
					}else if(data==2){
						show("您输入的金额有误，请重新输入");
					}else if(data==12){
						show("起购金额为1000元,请重新输入");
					}else if(data==13){
						show("只能输入两位小数，请重新输入");
					}else if(data==3){
						show("请输入交易密码");
					}else if(data==4){
						show("请重新登录，<a href='login.mht' class='red'>立即登录</a>");
					}else if(data==5){
						show("交易密码不正确");
					}else if(data==6){
						show("您的投资金额超过可用余额，请重新输入");
					}else if(data==7){
						show("投资失败，请重新操作");
					}else if(data==8){
						show("投资成功，请进入您的账户中心查看您的涨薪宝明细",function(){window.location.reload();});
					}
				})
			});
		})	
		
		function calcAmount(value){
			var payprofit = parseFloat('${map.paytreasure.payprofit}');
			var result = value/10000*payprofit;
			$("#yjsy").html(result.toFixed(2))
		}	
	</script>
</body>
</html>

