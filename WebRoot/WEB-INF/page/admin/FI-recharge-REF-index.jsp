<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>异常线上充值订单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="../css/admin/popom.js"></script>
		<script type="text/javascript">
			 $(document).ready(function(){
			 	$("#bt_search").click(function(){
					var orderId = $("#orderId").val();
					if(orderId==null || orderId==''){
						alert("请输入充值单号");
						return;
					};
				    param["paramMap.orderId"] = orderId;
					$.post("queryREF.mht",param,function(data){
						if(data==null || data.orderId==null || data.orderId==''){
							$("#info").html("充值单号不存在");
						}else{
							var result = data.result;
							var retCode = data.retCode;    //交易返回码       0001：成功0002：失败  
                            var retMsg   =data.retMsg;// 交易返回信息          
                            var seqId   = data.seqId;//    本条交易信息序列号           
                            var orderId =data.orderId;//    商品订单号           
                            var  amount  = data.amount;//   订单金额            
                            var orderDate = data.orderDate;//    订单日期        YYYY-MM-DD如：（2010-01-01）    
                            var completeDate = data.completeDate;//        此交易完成时间         
                            var status =data.status;//  ordStatus   交易订单状态标识            
                            var statusDes  = data.statusDes;//     交易订单状态描述        根据status翻译  
                            var settleDate  = data.settleDate;//    对账日期        与completeDate相同
                            param = data;
							$("#ryf_status").text(retCode=='0001'?"成功":"失败");
							$("#shd_status").text(result==0?"成功":"失败");
							$("#orderid").text(orderId);
							$("#amount").text(amount+"元");
						};
					});
				});
			 });
		</script>
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table  border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="120" height="28" class="xxk_all_a">
								<a href="queryRechargeRecordInit.mht">充值记录</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" class="xxk_all_a">
								<a href="queryRechargeFirstInit.mht">第一次充值查询</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="120" class="main_alll_h2">
                                <a href="queryREFInit.mht">异常线上充值订单</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
							<td width="2">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<div  style=" background-color: #fff;text-align: center;margin-left: 10%;margin-top: 3%;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
										充值单号：<input id="orderId" name="paramMap.orderId" type="text"/><span class="red"  id="info"></span>
										&nbsp;&nbsp;
										<%--充值时间：
										<input id="reStartTime" name="paramMap.rechargeTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										－－
										<input id="reEndTime" name="paramMap.rechargeTime" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>
										&nbsp;&nbsp;
										用户类型：
										<s:select id="userType" list="#{-1:'-请选择-',2:'企业',1:'个人'}" ></s:select>
										&nbsp;&nbsp;
										--%>
									</td>
								</tr>
								<tr>
								    <td class="f66" align="left" width="50%" height="36px">充值类型：<s:select id="rechargeType"  cssStyle="width:151px"  list="rechargeTypes" name="paramMap.rechargeType" listKey="typeId" listValue="typeValue" headerKey="-100" headerValue="--请选择--" />
                                        &nbsp;&nbsp; </td>
								</tr>
								<tr>
                                    <td class="f66" align="left" width="50%" height="36px"><input  style="margin-left: 5%" id="bt_search" type="button" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"  /></td>
                                </tr>
							</tbody>
						</table>
					</div>
					
					<div id="result"  style=" background-color: #fff;text-align: center;margin-left: 10%;margin-top: 3%;display:none">
                        <table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
                            width="100%" border="0">
                            <tbody>
                                <tr>
                                    <td class="f66" align="left" width="50%" height="36px">
                                        <h5>查询结果</h5>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="f66" align="left" width="50%" height="36px">支付平台到账状态：<span id="ryf_status"></span></td>
                                </tr>
								 <tr>
                                    <td class="f66" align="left" width="50%" height="36px">三好资本平台到账状态：<span id="shd_status"></span></td>
                                </tr>
								<tr>
                                    <td class="f66" align="left" width="50%" height="36px">单号：<span id="orderid"></span></td>
                                </tr>
								<tr>
                                    <td class="f66" align="left" width="50%" height="36px">金额：<span id="amount"></span></td>
                                </tr>
                                <tr>
                                    <td class="f66" align="left" width="50%" height="36px"><input  style="margin-left: 5%" id="bt_update" type="button" value="&nbsp;&nbsp;更新状态&nbsp;&nbsp;"  /></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
					
					
				</div>
			</div>
			</div>
	</body>
</html>
