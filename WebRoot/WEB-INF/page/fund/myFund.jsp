<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>高端理财</title>
		<link rel="stylesheet" href="css/public.css" type="text/css"/>
		<link rel="stylesheet" href="css/hefm.css" type="text/css"/>
		<script type="text/javascript" src="js/doT.js" ></script>
		<script type="text/javascript" src="js/jquery.min.js" ></script>
		<script type="text/javascript" src="js/util0607.js" ></script>
	</head>
	<body><jsp:include page="/WEB-INF/page/fund/include/top.jsp"/>
		<div class="pf-main-content">
			<div class="center-wrap">
				<div class="center-content">
					<!--左侧菜单-->
					<div id="left_menu" class="left-menu">
						<div class="my-account">
							<span class="img-block img-myAccount"></span><span class="font-size-18 color-title desc">我的账户</span>
						</div><ul class="menu-list">
								<li class="li-activity"><span class="imgDiv"><span class="img-block img-point-red"></span></span>账户总览</li><li>
									<span class="imgDiv"><span class="img-block img-point-grey"></span></span>我的资产
								</li><li>
									<span class="imgDiv"><span class="img-block img-point-grey"></span></span>基金
								</li><li>
									<span class="imgDiv"><span class="img-block img-point-grey"></span></span>涨薪宝
								</li><li>
									<span class="imgDiv"><span class="img-block img-point-grey"></span></span>投资管理
								</li><li>
									<span class="imgDiv"><span class="img-block img-point-grey"></span></span>充值提现
								</li><li>
									<span class="imgDiv"><span class="img-block img-point-grey"></span></span>安全中心
								</li><li>
									<span class="imgDiv"><span class="img-block img-point-grey"></span></span>消息中心
								</li><li>
									<span class="imgDiv"><span class="img-block img-point-grey"></span></span>友情链接  
								</li>
						</ul>
					</div>
					<div id="rigth-content" class="rigth-content">
					</div>
				</div>	
			</div>
		</div>
		<jsp:include page="/WEB-INF/page/fund/include/footer.jsp"/>
	</body>
	<script type="text/javascript">
		$(function(){
			//我的基金
			ss.getRequest({url:"MY_FUND",callback:function(data){
				$("#rigth-content").children().remove();
				$("#rigth-content").html(data);
			}});
			//风险承受能力测试
			/*ss.getRequest({url:"RISK_TEST",callback:function(data){
				$("#rigth-content").children().remove();
				$("#rigth-content").html(data);
			}});*/
		});
	</script>
</html>
