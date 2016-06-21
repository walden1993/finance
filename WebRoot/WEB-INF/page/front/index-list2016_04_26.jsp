<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="首页" var="title" scope="request" />
<jsp:include page="/include/head.jsp"></jsp:include><!-- 引用公共head -->
<link rel="stylesheet" type="text/css" href="css/index.css" />
<style>
.onload_relative{ position: relative; overflow: hidden;}
.onload_banner{ display:none; width:100%;height:355px; position:absolute;top:-355px;background:no-repeat top center;z-index:10;}
.onload_banner a{ display:block;width:100%;height:100%;}
.onload_closed{margin-top:10px;margin-right:40px; float:right;width:28px; cursor: pointer;transition:all 1s ease-out;}
.onload_closed:hover{transform: rotate(30deg);
-ms-transform: rotate(360deg);		/* IE 9 */
-webkit-transform: rotate(360deg);	/* Safari and Chrome */
-o-transform: rotate(360deg);		/* Opera */
-moz-transform: rotate(360deg);	}
</style>
<script type="text/javascript">var menuIndex = 6;</script>
</head>

<body>
	<jsp:include page="/include/top.jsp"></jsp:include>


<div class="onload_relative">
<!--  <div class="onload_banner" style="display: block; top: 0px; background: url(https://www.sanhaodai.com/upload/lists/20150925/201509251027535471.png)  40% 0 no-repeat;">
	<div class="onload_closed"><img src="images/x.png" width="28" height="31"></div>
	<a href="activityIndex_national.mht" >&nbsp;</a>
 </div> -->

<div id="carousel-example-generic" class="carousel slide fullSlide" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators hidden">
    <s:iterator value="#request.bannerList" var="banner" status="index">			
		<s:if test="#index.index<3">
			<li data-target="#carousel-example-generic" class="myli <s:if test='#index.index==0'>active</s:if>" data-slide-to="${index.index}"></li>
			<li data-target="#carousel-example-generic" class="myli <s:if test='#index.index==0'>active</s:if>" data-slide-to="${index.index}"></li>
		</s:if>
	</s:iterator>
  </ol>
	
  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
  	<s:iterator value="#request.bannerList" var="banner" status="index">			
		<s:if test="#index.index<3">
			<div class="item <s:if test='#index.index==0'>active</s:if>">
				<s:if test="#banner.companyURL!=null  && #banner.companyURL!=''">
					<a target="_blank" href="${banner.companyURL}" ><div  style="height:395px; display:block;background: url(./${banner.companyImg}) 40% 0 no-repeat;"></div></a>
				</s:if>
		  		<s:else>
					<a target="_blank" href="javascript:void(0);" ><div style="height:395px; display:block;background:  url(./${banner.companyImg}) 40% 0 no-repeat;"></div></a>
				</s:else>
			</div>
		</s:if>
	</s:iterator>	
  </div>

  <!-- Controls -->
  <a class="carousel-control bg-none" style="width: 10%;" href="#carousel-example-generic" role="button" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="myright carousel-control bg-none" style="width: 10%;"  href="#carousel-example-generic" role="button" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>

      <div class="ban_top w950"> 
    <!--浮层-->
    <!--登录-->
    <div class="right">
          <div class="login">
        <div class="text-center logout  <s:if test='#session.user != null'>hidden</s:if>"> 
        	<div class="center" style="padding: 5px 65px;border-bottom: 0.1px solid #A0A0A0;">
        		<h4 style="line-height: 1.5;color:#E6E6E6;">证监会批准的独立基金销售机构</h4>
        	</div>
        	<div class="userlogin ">
        		<form class="form-horizontal">
					  <div class="form-group pl30 pt30">
					    <div class="col-sm-11">
					      <input type="email" class="input" id="inputEmail3" placeholder="用户名/手机/邮箱">
					    </div>
					  </div>
					  <div class="form-group pl30 pt5">
					    <div class="col-sm-11">
					      <input type="password" class="input" id="inputPassword3" placeholder="密码">
					    </div>
					  </div>
<!-- 					  <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
					      <div class="checkbox">
					        <label>
					          <input type="checkbox">记住帐号
					        </label>
					      </div>
					    </div>
					  </div>
 -->				 <div class="form-group pl30 pt10">
					    <div class="col-sm-11">
					      <button type="submit" class="login_button ">立即登录</button>
					    </div>
					  </div>
					  
					  <div class="form-group pl50">
					    <div class="col-sm-9">
					  	 	 <a class="fl" style="color:#E8E8E8;">忘记密码</a><a class="fr" style="color:#E8E8E8;">免费入户</a>
					    </div>
					  </div>
					</form>
        	</div>
        </div>
        <div class="  f14 login_in <s:if test='#session.user == null'>hidden</s:if>">
			<div class="mb15 white ">欢迎您，<span class="f25">${user.userName}</span></div>
			<div class="mb15  white">
				<p>
					可用余额：<span class="white bold600 f14"> ${paramMap.usableSum}&nbsp;</span><span
						class="f12">元</span>
						<a href="rechargeInit.mht" class="button-a fr f12 m0 p0 pl5 pr5"  >立即充值</a>
				</p>
					<%-- <span id="element6" class="mr10">
					</span>${safeMap.words} --%>
				<div class="progress" style="margin:15px 0;">
				  <div class="progress-bar   progress-bar-${safeMap.eng} " role="progressbar" aria-valuenow="${safeMap.number}" aria-valuemin="0" aria-valuemax="100" style="width:${safeMap.number}%">
				    <font color="white">安全等级：${safeMap.words}</font>
				  </div>
				  	<s:if test="%{#request.safeMap.number<100}"><a href="securityCent.mht" ><span class="f12 red text-center fr" style="width: ${100-safeMap.number}%">立即加强</span></a></s:if>
				</div>
				<div class="mt15">
					<a href="home.mht" class="button-login"  >我的账户</a>
				</div>
			</div>
		</div>
      </div>
          
        </div>
  </div>
      <div class="clear"></div>
    
    <div class="left w950">
          <div class="top_ico w065 fl">
        <ul>
              <li><a href="bidlist.mht">
                <div class="ico1"><i>&nbsp;</i></div>
                <dl>
               民生银行托管
              </dl>
                </a></li>
              <li><a href="jg_hrrzdb.mht">
                <div class="ico2"><i>&nbsp;</i></div>
                <dl>
                国资战略参股
              </dl>
                </a></li>
              <li><a href="activityIndex_safety.do">
                <div class="ico3"><i>&nbsp;</i></div>
                <dl>
                证监会批准
              </dl>
                </a></li>
              
            </ul>
      </div>
      
      <!-- banner下面的 -->
      <div class="index_top w033 fr">
        <div class="data  f16" style="padding: 40px 40px;">
              <table width="100%">
	           	   <tbody>
		               <tr height="30">
		                <td><span class="ioc1">运营时间</span></td>
		                <td align="left" class="text-danger f18">2年203天</td>
		               </tr>
		              <tr  height="60">
		                <td><span class="ioc2">投资收益</span></td>
		                <td align="left" class="text-danger f18">${investMap.totalHasInvestment }</td>
		              </tr>
		              <tr height="30">
		                <td><span class="ioc3">服务客户</span></td>
		                <td align="left" class="text-danger f18">${investMap.regAcount}人</td>
		              </tr>
	                </tbody>
          		</table>
            </div>
      </div>
      
        </div>
    
    </div>
<div class="clearfix"></div>

<div class="wrap">
	<div class="w950">
		<!-- 热门推荐 -->
		<div class="hot">
			<div class="hot_title">
				<p><span class="h3 mr5">热门推荐</span><span><a>基金</a>|<a>债权</a>|<a>私募</a></span></p>
			</div>
			<div class="hot_list">
				<a href="https://e.lufunds.com/jijin/detail?productId=8512950" target="_blank">
				<div class="hot_item w032">
					<table>
						<tr>
							<td colspan="4" align="center" height="70"><span class="bt">建信稳定增长C 530008</span></td>
						</tr>
						<tr align="center" height="30">
							<td class="color9a9797 f14" width="33%">最新增值</td>
							<td class="color9a9797 f14 " width="34%">近三个月收益</td>
							<td class="color9a9797 f14 " width="33%">申购率</td>
							<td></td>
						</tr>
						<tr align="center" height="30">
							<td class="f16 colorde4a4a">1.2570</td>
							<td class="f16 colorde4a4a">1.58%</td>
							<td class="f16 colorde4a4a">0.08%</td>
							<td class="f16 colorde4a4a"></td>
						</tr>
						<tr align="center" height="80">
							<td colspan="2" width="50%" class="color9a9797 f14">单日投资无限额</td>
							<td  colspan="2"  width="50%"><a style="color: #E49018;border-bottom: 1px dashed;font-size: 14px;">立即查看</a></td>
						</tr>
					</table>
				</div>
				</a>
				<a href="https://e.lufunds.com/jijin/detail?productId=8512950" target="_blank">
				<div class="hot_item  w032" style="margin: 0 18px;">
					<table>
						<tr>
							<td colspan="4" align="center" height="70"><span class="bt">建信稳定增长C 530008</span></td>
						</tr>
						<tr align="center" height="30">
							<td class="color9a9797 f14" width="33%">预期年化率</td>
							<td class="color9a9797 f14 " width="33%">投资期限</td>
						</tr>
						<tr align="center" height="30">
							<td class="f16 colorde4a4a">15%</td>
							<td class="f16 colorde4a4a">3个月</td>
						</tr>
						<tr align="center" height="80">
							<td width="50%" class=" f14"><span class="color9a9797">计划金额</span><p class="colorde4a4a">1,500,000元</p></td>
							<td width="50%"><a style="color: #E49018;border-bottom: 1px dashed;font-size: 14px;">立即查看</a></td>
						</tr>
					</table>
				</div>
				</a>
				<a href="https://e.lufunds.com/jijin/detail?productId=8512950" target="_blank">
				<div class="hot_item  w032">
					<table>
						<tr>
							<td colspan="4" align="center" height="70"><span class="bt">格上创富</span></td>
						</tr>
						<tr align="center" height="30">
							<td class="color9a9797 f14" width="33%">累计收益</td>
							<td class="color9a9797 f14 " width="34%">投资门槛</td>
							<td class="color9a9797 f14 " width="33%">基金经理</td>
							<td></td>
						</tr>
						<tr align="center" height="30">
							<td class="f16 colorde4a4a">61.87%</td>
							<td class="f16 colorde4a4a">100万</td>
							<td class="f16 colorde4a4a">李明</td>
							<td class="f16 colorde4a4a"></td>
						</tr>
						<tr align="center" height="80">
							<td  colspan="2" width="50%" class=" f14"><span class="color9a9797">2015年收益</span><p class="colorde4a4a">37.32%</p></td>
							<td  colspan="2" width="50%"><a style="color: #E49018;border-bottom: 1px dashed;font-size: 14px;">立即预约</a></td>
						</tr>
					</table>
				</div>
				</a>
			</div>
			<div class="clearfix"></div>
		</div>
		
		<!-- 涨薪宝 -->
		<div class="zxb">
			<img src="images/index/zxb/logo.png" class="fl"/>
			<div class="fl left" style="width: 706px;height: 162px;">
				<div class="fl">
					<h2 style="margin-left: 100px;">涨薪宝—金秋一号</h2>
					<div>
						<ul>
							<li class="ml20 mt30">
								<img alt="" src="images/index/zxb/rate.png"><span>预计年化收益率<i class="f18 colorde4a4a">5.10</i><font class="colorde4a4a">%</font></span>
							</li>
							<li class="ml40 mt30">
								<img alt="" src="images/index/zxb/wrate.png"><span>每万份收益<i class="f18 colorde4a4a">1.37</i>元</span>
							</li>
						</ul>
					</div>
				</div>
				<div class="fl">
					<a class="button w200" style="margin-top: 50px;margin-left: 20px;" href="paytreasuredetail.mht" >立即购买</a>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
		
		<!-- app下载 -->
		<div class="phone text-center">
			<div class="fl w020 mt30 ml20">
				<img alt="" src="images/index/phone/ios.png">
				<img alt="" src="images/index/phone/ad.png" class="mt30">
			</div>
			<div class="fl w020 mt30 mr20 ml0">
				<img alt="" src="images/index/phone/code.png">
			</div>
			<div class="fl w050">
				<img alt="" src="images/index/phone/iphone.png">
			</div>
		</div>
		
		<!-- 理财列表 -->
		<div class="list">
		<div class="list_title">
			<h3>投资理财</h3>
		</div>
		<main>

		  <input id="tab1" type="radio" name="tabs" checked>
		  <label for="tab1" class="w033">公募基金</label>
		
		  <input id="tab2" type="radio" name="tabs">
		  <label for="tab2" class="w033">优质债权</label>
		
		  <input id="tab3" type="radio" name="tabs">
		  <label for="tab3" class="w033">高端理财</label>
		  
		  <section id="content1">
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		  </section>
		
		  <section id="content2">
		    <div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		  </section>
		
		  <section id="content3">
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		    	<div class="item w p20">
		    		<table class="w">
		    			<tr height="40" >
		    				<td colspan="5" class="f20">广发聚财信用B</td>
		    			</tr>
		    			<tr height="40"  class="color9a9797 f16">
		    				<td>近一周收益</td>
		    				<td>近三个月收益</td>
		    				<td>近一年收益</td>
		    				<td>债权类型</td>
		    				<td>申购费率</td>
		    				<td rowspan="2">
		    					<button class="login_button">立即投资</button>
		    				</td>
		    			</tr>
		    			<tr height="30" class="f18 colorde4a4a">
		    				<td>0.15%</td>
		    				<td>2.87%</td>
		    				<td>16.32%</td>
		    				<td>债权型</td>
		    				<td>0.00</td>
		    			</tr>
		    		</table>
		    	</div>
		  </section>
		
		</main>
				
		</div>
		
		<!-- 最新资讯 -->
		<div class="zx">
			<div class="zx_title"><h3>最新资讯</h3></div>
			<div class="fl w062 left">
				<div>
					<div class="dynamic fl">
				        <div class="outline">
				            <div class="fl">平台动态</div>
				            <a class="more" href="queryAboutInfo.mht?msg=101"  target="_blank">更多>></a>
				        </div>
			          	<div class="content"><div class="fl"><img id="meikuInternetStickImg"   class="loadingImg" src="images/blank.gif"   data-echo="${meikuInternetStick[0].imgPath}" width="90" height="80" /></div>
				        <ul class="fr">
				        	<s:iterator  value="#request.meikuInternetStick" var="sanhaostick"	status="sts">
				              <li onmouseover="showDynamicImg('${imgPath}','meikuInternetStickImg')" ><a href="frontMedialinkId.mht?id=${id}" target="_blank"><span class="info">${sanhaostick.title }</span><span class="fr"><fmt:formatDate value="${publishTime}"  pattern="yyyy-MM-dd" type="date" dateStyle="default"/></span></a></li>
				            </s:iterator>
				         </ul>
				         		<script>
							 	function showDynamicImg(img,id){
							 		if($("#"+id).attr("src")!=img)
							 		$("#"+id).attr("src",img);
							 	}
							 </script>  
			      		</div>
			        </div>
			        
			        <div class="dynamic fl mt20">
				        <div class="outline">
				            <div class="fl">平台动态</div>
				            <a class="more" href="queryAboutInfo.mht?msg=101"  target="_blank">更多>></a>
				        </div>
			          	<div class="content"><div class="fl"><img id="meikuInternetStickImg"   class="loadingImg" src="images/blank.gif"   data-echo="${meikuInternetStick[0].imgPath}" width="90" height="80" /></div>
				        <ul class="fr">
				        	<s:iterator  value="#request.meikuInternetStick" var="sanhaostick"	status="sts">
				              <li onmouseover="showDynamicImg('${imgPath}','meikuInternetStickImg')" ><a href="frontMedialinkId.mht?id=${id}" target="_blank"><span class="info">${sanhaostick.title }</span><span class="fr"><fmt:formatDate value="${publishTime}"  pattern="yyyy-MM-dd" type="date" dateStyle="default"/></span></a></li>
				            </s:iterator>
				         </ul>
				         		<script>
							 	function showDynamicImg(img,id){
							 		if($("#"+id).attr("src")!=img)
							 		$("#"+id).attr("src",img);
							 	}
							 </script>  
			      		</div>
			        </div>
				</div>
			</div>
			
			<div class="fr">
				<img alt="基金牌照" src="images/index/jjpz.png">
			</div>
		</div>		
		
		<div class="clearfix"></div>
		
	</div>
	<!-- 合作机构 -->
	<div class="shd-footer">
    <ul class="info-wrap">
        <li>
            <a  target="_blank" class="qualification">
                基金销售资格
                <i></i>
            </a>
        </li>
        <li>
            <a  target="_blank" class="supervise">
                监督机构
                <i></i>
            </a>
        </li>
        <li>
            <a  target="_blank" class="bank">
                监管银行
                <i></i>
            </a>
        </li>
        <li>
            <a target="_blank" class="self-discipline">
                自律组织
                <i></i>
            </a>
        </li>
    </ul>
	</div>
</div>

	<%-- <div class="last">
      <div class="hzlj w950">
      <div class="outline">
          <div class="title fl style="width: 120px;"">合作链接</div>
        </div>
    <div class="clearfix friendlyLink">
          <div class="clearfix friendlyLinkList"> 
          	<s:iterator value="linksList.{?#this.type==1}" var="page"	status="sts">
			      	<a href="${companyURL}" title="${page.companyName}"   target="_blank">${page.companyName}</a>
			</s:iterator>
          </div>
        </div>
  </div>
    </div> --%>
<div class="clearfix"></div>
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="js/scrollPic.js"></script>
<script type="text/javascript" src="js/jquery.easing.min.js"></script>

<script type="text/javascript">

$(function () {
	/* $(".onload_banner").show();
	$(".onload_banner").animate({top:'-100px' },500).animate({top:"0px"},1600,'easeOutElastic');
	setTimeout('$(".onload_banner").fadeOut();',12000);//十秒后隐藏
	$('.onload_closed').click(function(){
		$('.onload_banner').fadeOut();
	}); */
});

function show(){
	$(".fancybox-effects-a").click();
}
$(document).ready(function() {
	$(".fancybox-effects-a").fancybox({
			helpers: {
				title : {
					type : 'outside'
				},
				overlay : {
					speedOut : 0
				}
			}
		});
		
})
 		
</script>


</body>
</html>
