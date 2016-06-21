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
					<div id="buy_sum" class="buy-sum">
						
					</div>
				</div>	
			</div>
		</div>
		<jsp:include page="/WEB-INF/page/fund/include/footer.jsp"/>
	</body>
	<script type="text/javascript">
		$(function(){
			//topbanner
			/*ss.getRequest({url:"TOP_BANNER",callback:function(data){
				$("#buy_sum").before(data);
			}});*/
			//基金开户
			/*ss.getRequest({url:"FUND_OPEN_AN_ACCOUNT",callback:function(data){
				$("#buy_sum").css("height","471px");
				$("#buy_sum").append(data);
			}});*/
			//确认购买
			/*ss.getRequest({url:"BUY_CONFIRM",callback:function(data){
				$("#buy_sum").css("height","578px");
				$("#buy_sum").append(data);
			}});*/
			//认购成功
			/*ss.getRequest({url:"SUBSCRIPTION_SUCCESS",callback:function(data){
				$("#buy_sum").css("height","478px");
				$("#buy_sum").append(data);
			}});*/
			//申购成功
			/*ss.getRequest({url:"APPLY_FOR_PURCHASE_SUCCESS",callback:function(data){
				$("#buy_sum").css("height","478px");
				$("#buy_sum").append(data);
			}});*/
			var type=${param.type};
			if(type ==1){
				//购买
				ss.getRequest({url:"BUY_SUM",callback:function(data){
					$("#buy_sum").css("height","400px");
					$("#buy_sum").append(data);
				}});
			}else{
				//赎回
				ss.getRequest({url:"REDEMPTION",callback:function(data){
					$("#buy_sum").css("height","400px");
					$("#buy_sum").append(data);
				}});
			}
			//赎回成功
			/*ss.getRequest({url:"REDEMPTION_SUCCESS",callback:function(data){
				$("#buy_sum").css("height","478px");
				$("#buy_sum").append(data);
			}});*/
			//赎回确认
			/*ss.getRequest({url:"REDEMPTION_CONFIRM",callback:function(data){
				$("#buy_sum").css("height","400px");
				$("#buy_sum").append(data);
			}});*/
		})
	</script>
</html>
