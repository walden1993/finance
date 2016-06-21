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
					<a target="_blank" href="${banner.companyURL}" ><div  style="height:355px; display:block;background: url(./${banner.companyImg}) 40% 0 no-repeat;"></div></a>
				</s:if>
		  		<s:else>
					<a target="_blank" href="javascript:void(0);" ><div style="height:355px; display:block;background:  url(./${banner.companyImg}) 40% 0 no-repeat;"></div></a>
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
    <div class="left">
    	<div class="top_title"></div>
    
          <div class="top_ico">
        <ul>
              <li><a href="bidlist.mht">
                <div class="ico1"><i>&nbsp;</i></div>
                <dl>
                优质投资项目
              </dl>
                </a></li>
              <li><a href="jg_hrrzdb.mht">
                <div class="ico2"><i>&nbsp;</i></div>
                <dl>
                雄厚担保体系
              </dl>
                </a></li>
              <li><a href="activityIndex_safety.do">
                <div class="ico3"><i>&nbsp;</i></div>
                <dl>
                完善法律保障
              </dl>
                </a></li>
              <li><a href="capitalEnsure.mht">
                <div class="ico4"><i>&nbsp;</i></div>
                <dl>
                安全保障管理
              </dl>
                </a></li>
              <li><a href="queryAboutInfo.mht?msg=110">
                <div class="ico5"><i>&nbsp;</i></div>
                <dl>
                国资战略参股
              </dl>
                </a></li>
            </ul>
        <div class="clear"></div>
      </div>
        </div>
    <!--登录-->
    <div class="right">
          <div class="login">
        <div class="text-center logout  <s:if test='#session.user != null'>hidden</s:if>"> <span class="bold200 f40"><%-- ${investMap.maxRate }% --%>15.0%</span><br/>
              <span class="e4e4e4">最高年化收益</span>
              <p><span class="bold600 f16">40倍&nbsp;</span><span class="e4e4e4">银行活期存款收益</span></p>
              <a href="reg.mht" target="_blank" class="button-login mt10">快速注册</a>
              <p class="e4e4e4">已有帐号？<a href="login.mht"  target="_blank" class="white f13" style="text-decoration: underline;">立即登录</a></p>
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
          <div class="index_top">
        <div class="index_top_bg"><b>统计</b>三好贷数据</div>
        <div class="clearfix"></div>
        <div class="data p5">
              <table width="100%">
            <tbody>
                  <tr>
                <td height="15">累积投资额</td>
                <td align="right">获得投资收益</td>
              </tr>
                  <tr class="a0a0a0 font14px">
                <td height="20"><span class="f16 text-danger"><span>${investMap.totalInvestment }</span></td>
                <td align="right"><span class="f16 text-danger"><span>${investMap.totalHasInvestment }</span></td>
              </tr>
                  <tr>
                <td height="15">平台注册会员数</td>
                <td align="right">借款项目总数</td>
              </tr>
                  <tr class="a0a0a0 font14px">
                <td height="20"><span class="f16 text-danger">${investMap.regAcount}人</span></td>
                <td align="right"><span class="f16 text-danger">${investMap.sumBorrow }个项目</span></td>
              </tr>
                </tbody>
          </table>
            </div>
      </div>
        </div>
  </div>
      <div class="clear"></div>
    </div>

<!--动态、公告-->
<div class="dn w950">
      <div class="dynamic fl ">
    <div class="outline">
          <div class="title fl">动态</div>
          <a class="more" href="queryAboutInfo.mht?msg=103"  target="_blank">更多>></a></div>
    <div class="content">
          <div class="fl"><img id="dynamicImg"   class="loadingImg" src="images/blank.gif"   data-echo="${meikuSanhaoStick[0].imgPath}" width="90" height="80" /></div>
          <ul class="fl">
          
          <s:iterator value="#request.meikuSanhaoStick"  begin="0"  end="2" var="sanhaostick"	status="sts">
          		<li onmouseover="showDynamicImg('${imgPath}','dynamicImg')" ><a href="frontMedialinkId.mht?id=${id}" target="_blank"><span class="info">${sanhaostick.title }</span><span class="fr"><fmt:formatDate value="${publishTime}" pattern="yyyy-MM-dd" type="date" dateStyle="default"/></span></a></li>
		 </s:iterator>
		 
		 <script>
		 	function showDynamicImg(img,id){
		 		if($("#"+id).attr("src")!=img)
		 		$("#"+id).attr("src",img);
		 	}
		 </script>
		 
      </ul>
        </div>
  </div>
      <div class="notice fr">
    <div class="outline">
          <div class="title fl">公告</div>
          <a class="more" href="queryAboutInfo.mht?msg=100"  target="_blank">更多>></a></div>
    <div class="content">
          <ul class="fl">
          <s:iterator value="#request.notices" var="serviceNews" begin="0" end="2"	status="sts">
				<li><a href="frontNewsDetails.mht?id=${id}" target="_blank"><span class="info">${serviceNews.title}</span><span class="fr"><fmt:formatDate value="${publishTime}" type="date" pattern="yyyy-MM-dd" dateStyle="default"/></span></a></li>
		  </s:iterator>
      </ul>
        </div>
  </div>
    </div>
<div class="newshare" style="position: relative;height: 255px;display: none;">
      <div class="w950" style="height: 255px;">
    <div class="outline">
          <div class="title fl">新手专享</div>
        </div>
    <div class="main" style="z-index: 100">
          <div class="borrow fl w050"><!--  <span class="ico"><img src="images/ico_5.png" /></span> -->
        <div class="left fl">
              <table width="100%" class="bold600" style="z-index:10;">
            <tr height="30">
                  <td colspan="4"><p class="title"><a href="financeDetail.mht?id=${newBorrow.id}" >${newBorrow.borrowTitle}</a> </p></td>
                </tr>
            <tr class="disctription" height="30">
                  <td>融资总额(元)</td>
                  <td>可投金额(元)</td>
                  <td>年化利率</td>
                  <td>投资期限</td>
                </tr>
            <tr height="30">
                  <td class="money">${newBorrow.borrowAmount}</td>
                  <td class="money">${newBorrow.remainAmount}</td>
                  <td class="rate">${newBorrow.annualRate}%</td>
                  <td class="deadline"><span class="number">${newBorrow.deadline}</span><span class="word"><s:if test="%{#request.newBorrow.isDayThe ==1}">个月</s:if>
			            	<s:else>天</s:else></span></td>
                </tr>
            <tr height="30">
                  <td colspan="4"><div class="fl disctription"><img src="images/new_ico7.png" class="mr10" />融资进度</div>
                <div class="progress fl" style="height:6px;border-radius:0px; width:50%;margin:7px 10px 0 10px; ">
                      <div class="progress-bar"  role="progressbar" aria-valuenow="60" aria-valuemin="0"aria-valuemax="100" style="width: ${newBorrow.schedules}%;background-color:#337ab7;"> <span class="sr-only">60% Complete</span> </div>
                    </div>
                <p class="fl">${newBorrow.schedules}</p></td>
               <%--  </tr>
            <tr height="30">
                  <td  colspan="4"><img src="images/new_ico8.png" class="mr10" /><span class="disctription mr10">到期时间</span><span class="C337AB7">${fn:split(newBorrow.remainTimeEnd, ' ')[0]}</span></td>
                </tr> --%>
            <tr height="30">
                  <td  colspan="4"><img src="images/new_ico9.png" class="mr10" /><span class="disctription mr10">还款方式</span><span class="C337AB7"><s:if test="%{#request.newBorrow.paymentMode == 1}">按月还款</s:if>
                                                <s:elseif
                                                    test="%{#request.newBorrow.paymentMode == 2}">先息后本</s:elseif>
                                                <s:elseif
                                                    test="%{#request.newBorrow.paymentMode == 3}">秒还</s:elseif>
                                                <s:elseif
                                                    test="%{#request.newBorrow.paymentMode == 4}">一次性还款</s:elseif></span></td>
                </tr>
          </table>
            </div>
        <div class="right fl text-center p10">
              <p><img src="images/guarantee.png" class="pr10"/>机构担保借款</p>
             		 <s:if test="%{#request.newBorrow.borrowStatus == 1}">
			             <a class="button w140">初审中</a>
				        </s:if>
				         <s:elseif test="%{#request.newBorrow.borrowStatus == 2}">
				         	   <a href="financeDetail.mht?id=${newBorrow.id}"   class="button w140">立即投标</a>		    
				        </s:elseif>
				        <s:elseif test="%{#request.newBorrow.borrowStatus == 3}">
				            <a class="button_over w140">复审中</a>&nbsp;
				        </s:elseif>
				        <s:elseif test="%{#request.newBorrow.borrowStatus == 4}">
				            <a class="button_over w140">
				            <s:if test="%{#request.newBorrow.borrowShow == 2}">回购中</s:if>
				          	<s:else>已满标</s:else>
				          	</a>&nbsp;
				        </s:elseif>
				        <s:elseif test="%{#request.newBorrow.borrowStatus == 5}">
				           <a class="button_over w140">已还完</a>&nbsp;
				        </s:elseif>
				        <s:else>
				              <a class="button w140">立即投资</a>&nbsp;
				        </s:else>
              
            </div>
      </div>
          <div class="fr w033"> <img data-echo="images/rookielinkbg.png"   class="loadingImg fr" src="images/blank.gif"   height="205" /> </div>
        </div>
  	<div class="main" style="position: relative;top: -205px;text-align: center;line-height: 205px;font-size: 40px;color: #FEFEFE;background: rgba(179, 174, 183, 0.89);">
  		敬请期待！
  	</div>
  </div>
    </div>
<div class="clearfix"></div>
<div class="wrap">
	<div class="w950  mt15">
		<div class="zxb  bg-white clearfix p10">
			<a href="paytreasuredetail.mht"><div class="w030 one1 fl">
				<div class="ico fl "></div>
				<div class="fl pt10">
					<h3>涨薪宝</h3>
					<p>活期理财</p>
					<p>年化收益率<span class="red">${zxb.paytreasure.rate}%</span></p>
				</div>
			</div></a>
			<a href="http://jijin.ronge88.com" target="_blank"><div class="w030 two1 fl">
				<div class="ico fl"></div>
				<div class="fl pt10">
					<h3>优选基金</h3>
					<p>精选高品质</p>
					<p>投资风向标</p>
				</div>
			</div></a>
			<a href="bidlist.mht"><div class="w030 three1 fl">
				<div class="ico fl "></div>
				<div class="fl pt10">
					<h3>优质项目</h3>
					<p>年化率高达<span class="red">15.0%</span></p>
					<p>保本保息</p>
				</div>
			</div></a>				
		</div>
	</div>
</div>
<div class="clearfix"></div>
<div class="borrow_list">
      <div class="w950 mt20 mb20">
    <div class="outline">
          <div class="title fl">项目列表</div>
          <a class="more" href="bidlist.mht">更多>></a> </div>
    <div class="list bold600" >
        <s:if test="#request.pageBeanList.size>0">
     				<s:iterator value="#request.pageBeanList" status="index" var="finance" begin="0" end="3">
          <div class="item fl p-relative mb10" <s:if test="#index.index==3 || #index.index==7  "> style='float:right'</s:if>  <s:if test="#index.index==0 || #index.index==4 "> style='margin-left:0px;'</s:if> >
          
          <%--  <s:if test="#finance.id>=296 && #finance.deadline>=3">
          	<span class="ico"><img src="images/activity_ioc.png" /></span>
          </s:if>
          <s:if test="#finance.id>=263 && #finance.deadline==1">
          	<span class="ico"><img src="images/award_2.png" /></span>
          </s:if> --%>
          
        <table width="100%" >
               <tr height="75px;">
            <td colspan="2" class="title buttom_border  pr10"><a href="financeDetail.mht?id=${finance.id}"  target="_blank">${finance.borrowTitle }</a></td>
          </tr>
             <tr>
            <td class="disctription ">借款金额</td>
            <td class="money">${finance.borrowAmount}</td>
          </tr>
              <tr>
            <td class="disctription">年化利率</td>
            <td class="rate"><s:property value="%{#finance.annualRate-#finance.rewardRate}" default="0"/>%
            <s:if test="#finance.rewardRate>0">
          	 +奖${finance.rewardRate}%
         	 </s:if>
            </td>
          </tr>
              <tr>
            <td class="disctription">投资周期</td>
            <td class="deadline"><span class="number"><s:property value="#finance.deadline" default="0"/></span><span class="word"> <s:if test="%{#finance.isDayThe ==1}">个月</s:if>
			            	<s:else>天</s:else></span></td>
          </tr>
              <%-- <tr>
            <td class="disctription">到期时间</td>
            <td class="C337AB7"><fmt:formatDate value="${finance.remainTimeEnd}" type="date"  pattern="yyyy-MM-dd" dateStyle="default"/>  </td>
          </tr> --%>
              <tr>
            <td class="disctription">还款方式</td>
            <td class="color9a9797 "><s:if test="%{#finance.paymentMode == 1}">按月分期还款(等额本息还款)</s:if>
                                                <s:elseif
                                                    test="%{#finance.paymentMode == 2}">按月付息，到期还本</s:elseif>
                                                <s:elseif
                                                    test="%{#finance.paymentMode == 3}">秒还</s:elseif>
                                                <s:elseif
                                                    test="%{#finance.paymentMode == 4}">一次性还款</s:elseif></td>
          </tr>
           <tr>
            <td colspan="2" valign="bottom" style="height:10px;" class="buttom_border"></td></tr>
          <tr>
            <td class="disctription">可投金额</td>
            <td class="deadline text-right money"   style="padding-right:20px;"><s:property value="#finance.investNum" default="0"/></td>
          </tr>
          <tr>
            <td colspan="2"  style="padding-right:20px;height: 5px;">
                <div class="progress fl" style="height:6px;border-radius:0px; width:100%; margin:5px 0px;">
                      <div class="progress-bar progress-bar-danger"  role="progressbar" aria-valuenow="60" aria-valuemin="0"aria-valuemax="100" style="width: ${finance.schedules}%;"> <span class="sr-only">60% Complete</span> </div>
                    </div>
            </td>
          </tr>
          <tr>
            <td class="disctription">融资进度</td>
            <td class="deadline text-right money"   style="padding-right:20px;">${finance.schedules}%</td>
          </tr>
              <tr>
            <td colspan="2"  style="padding:0px;">
            	<s:if test="%{#finance.borrowStatus == 1}">
			             <a class="button w" style="padding: 2px;"   target="_blank">初审中</a>
				        </s:if>
				         <s:elseif test="%{#finance.borrowStatus == 2}">
				         	   <a class="button w" style="padding: 2px;" href="financeDetail.mht?id=${finance.id}"  target="_blank">查看</a>    
				        </s:elseif>
				        <s:elseif test="%{#finance.borrowStatus == 3}">
				            <a class="button_over w" style="padding: 2px;"   target="_blank">复审中</a>
				        </s:elseif>
				        <s:elseif test="%{#finance.borrowStatus == 4}">
				            <a class="button_over w" style="padding: 2px;"   target="_blank">
				            <s:if test="%{#finance.borrowShow == 2}">回购中</s:if>
				          	<s:else>已满标</s:else>
				          	</a>
				        </s:elseif>
				        <s:elseif test="%{#finance.borrowStatus == 5}">
				           <a class="button_over w" style="padding: 2px;"   target="_blank">已还完</a>
				        </s:elseif>
				        <s:else>
				              <a class="button_over w" style="padding: 2px;"   target="_blank">流标</a>
				        </s:else>
            </td>
          </tr>
            </table>
      </div>
      
      </s:iterator>
      
      </s:if>
      
      
        </div>
  </div>
    </div>
<div class="last">
      <div class="dn w950">
      
      <div class="dynamic fl">
          <div class="outline">
        <div class=" title fl">互联网金融</div>
        <a class="more" href="queryAboutInfo.mht?msg=101"  target="_blank">更多>></a></div>
          <div class="content"><div class="fl"><img id="meikuInternetStickImg"   class="loadingImg" src="images/blank.gif"   data-echo="${meikuInternetStick[0].imgPath}" width="90" height="80" /></div>
        <ul class="fl">
        	<s:iterator  value="#request.meikuInternetStick" var="sanhaostick"	status="sts">
              <li onmouseover="showDynamicImg('${imgPath}','meikuInternetStickImg')" ><a href="frontMedialinkId.mht?id=${id}" target="_blank"><span class="info">${sanhaostick.title }</span><span class="fr"><fmt:formatDate value="${publishTime}"  pattern="yyyy-MM-dd" type="date" dateStyle="default"/></span></a></li>
            </s:iterator>
            </ul>
      </div>
        </div>
      
    <div class="notice fr ">
          <div class="outline">
        <div class="title fl ">三好贷问答</div>
        <a class="more" href="queryAboutInfo.mht?msg=108"  target="_blank">更多>></a></div>
          <div class="content">
        <ul class="fl">
        	<s:iterator  value="#request.sanhaodaiQA" var="sanhaostick"	status="sts">
              <li onmouseover="showDynamicImg('${imgPath}','sanhaodaiQA')" ><a href="frontMedialinkId.mht?id=${id}" target="_blank"><span class="info">${sanhaostick.title }</span><span class="fr"><fmt:formatDate value="${publishTime}"  pattern="yyyy-MM-dd" type="date" dateStyle="default"/></span></a></li>
            </s:iterator>
            </ul>
        </div>
        </div>
  </div>
      <div class="hzjg w950">
    <div class="outline">
          <div class="title fl " style="width: 120px;">合作机构</div>
        </div>
    <div class="mydpx lanmu cl">
          <div class="bottom">
        <table width="100%">
              <tbody>
            <tr>
                  <td><img class="pointer" id="arrLeft"   class="loadingImg" src="images/blank.gif"   data-echo="images/left.png"></td>
                  <td><div id="scrollbox" style="overflow: hidden;margin: 0 auto;">
                      <div style="overflow: hidden; zoom: 1; width: 100%;">
                      <div style="float: left;">
                          <ul>
                          <%-- <s:iterator value="linksList.{?#this.type==5}" var="page"	status="sts"></s:iterator> --%>
					      	<li>
						      	<div>
							      	<a href="http://www.wangdaizhijia.com/"  target="_blank">
							      		<img data-echo="images/hzjg/m1_a.jpg"  class="loadingImg" src="images/blank.gif"   hover="images/hzjg/m1.jpg" height="100" width="125" title="三好贷" />
							      	</a>
						      	</div>
					      	</li>
					      	<li>
						      	<div>
							      	<a href="http://www.p2peye.com/  "  target="_blank">
							      		<img data-echo="images/hzjg/m2_a.jpg"  class="loadingImg" src="images/blank.gif"   hover="images/hzjg/m2.jpg" height="100" width="125" title="三好贷" />
							      	</a>
						      	</div>
					      	</li>
					      	<li>
						      	<div>
							      	<a href="http://shenzhen.rong360.com/"  target="_blank">
							      		<img data-echo="images/hzjg/m3_a.jpg"  class="loadingImg" src="images/blank.gif"   hover="images/hzjg/m3.jpg" height="100" width="125" title="三好贷" />
							      	</a>
						      	</div>
					      	</li>
					      	<li>
						      	<div>
							      	<a href="http://www.szhuarong.com/"  target="_blank">
							      		<img data-echo="images/hzjg/m4_a.jpg"   class="loadingImg" src="images/blank.gif"   hover="images/hzjg/m4.jpg" height="100" width="125" title="三好贷" />
							      	</a>
						      	</div>
					      	</li>
					      	<li>
						      	<div>
							      	<a href="http://www.szhuarong.com/"  target="_blank">
							      		<img data-echo="images/hzjg/m5_a.jpg"   class="loadingImg" src="images/blank.gif"   hover="images/hzjg/m5.jpg" height="100" width="125" title="三好贷" />
							      	</a>
						      	</div>
					      	</li>
					      	<li>
						      	<div>
							      	<a href="https://www.rongyf.com/hrpay/"  target="_blank">
							      		<img data-echo="images/hzjg/m6_a.jpg"   class="loadingImg" src="images/blank.gif"   hover="images/hzjg/m6.jpg" height="100" width="125" title="三好贷" />
							      	</a>
						      	</div>
					      	</li>
					      	<li>
						      	<div>
							      	<a href="https://www.qhee.com/"  target="_blank">
							      		<img data-echo="images/hzjg/m7_a.jpg"   class="loadingImg" src="images/blank.gif"   hover="images/hzjg/m7.jpg" height="100" width="125" title="三好贷" />
							      	</a>
						      	</div>
					      	</li>
					      	<li>
						      	<div>
							      	<a href="javascrit:void(0)" >
							      		<img  data-echo="images/hzjg/m8_a.jpg"   class="loadingImg" src="images/blank.gif"   hover="images/hzjg/m8.jpg" height="100" width="125" title="三好贷" />
							      	</a>
						      	</div>
					      	</li>
					      	<li>
						      	<div>
							      	<a href="http://www.wangdaicaifu.com/"  target="_blank">
							      		<img  data-echo="images/hzjg/m11_a.jpg"   class="loadingImg" src="images/blank.gif"   hover="images/hzjg/m11.jpg" height="100" width="125" title="三好贷" />
							      	</a>
						      	</div>
					      	</li>
                        </ul>
                        </div>
                    </div>
                    </div></td>
                  <td><img class="pointer" id="arrRight"  class="loadingImg" src="images/blank.gif"   data-echo="images/right.png"></td>
                </tr>
          </tbody>
            </table>
      </div>
        </div>
  </div>
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
    </div>
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
		
		var scrollPic_02 = new ScrollPic();
		scrollPic_02.scrollContId   = "scrollbox"; //内容容器ID
		scrollPic_02.arrLeftId      = "arrLeft";//左箭头ID
		scrollPic_02.arrRightId     = "arrRight"; //右箭头ID

		scrollPic_02.frameWidth     = 870;//显示框宽度
		scrollPic_02.pageWidth      = 144; //翻页宽度

		scrollPic_02.speed          = 10; //移动速度(单位毫秒，越小越快)
		scrollPic_02.space          = 10; //每次移动像素(单位px，越大越快)
		scrollPic_02.autoPlay       = true; //自动播放
		scrollPic_02.autoPlayTime   = 3; //自动播放间隔时间(秒)

		scrollPic_02.initialize(); //初始化
		
		$("#scrollbox").find("img").mouseenter(function(){
			var hover = $(this).attr("hover");
			var src = $(this).attr("src");
			$(this).attr("src",hover);
			$(this).attr("hover",src);
		}).mouseleave(function(){
			var hover = $(this).attr("src");
			var src = $(this).attr("hover");
			$(this).attr("src",src);
			$(this).attr("hover",hover);
		});
})
</script>


</body>
</html>
