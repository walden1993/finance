<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<jsp:include page="/include/head.jsp"></jsp:include>
	</head>

	<body>
		<!-- 引用头部公共部分 -->
		<jsp:include page="/include/top.jsp"></jsp:include>
		<div class="nymain">
		<div class="bigbox" style="border:none">
				
				<div class="sqdk" style="background: none;">
					<div class="l-nav">
						<ul>
								<li class="on">
								<a><span>step1 </span> 基本资料</a>
							</li>
							<li>
							<s:if test="#request.from != null && #request.from!='' && #session.user.authStep>=2">
							  <s:if test="#session.user.userType==1">
							  	<a href="userPassData.mht?from=${from }"><span>step2 </span> 上传资料</a>
							  </s:if>
							  <s:else>
							  	<a href="companyPictureDate.mht?from=${from }"><span>step2 </span> 上传资料</a>
							  </s:else>
							</s:if>
							
							<s:else>
								<s:if test="#session.user.authStep>=4">
									<s:if test="#session.user.userType==1">
										<a href="userPassData.mht"><span>step2 </span> 上传资料</a>
									</s:if>
									<s:else>
										<a href="companyPictureDate.mht"><span>step2 </span> 上传资料</a>
							  		</s:else>
								</s:if>	
								<s:else>
								     <a><span>step2 </span> 上传资料</a>
								</s:else>
							</s:else>
							</li>
							<li>
							<s:if test="#request.from != null && #request.from!='' && #session.user.authStep>=2">
								<s:if test="#session.stepMode ==1">
								     <a href="addBorrowInit.mht?t=${session.borrowWay}"><span>step3 </span> 发布贷款</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a href="creditingInit.mht"><span>step3 </span>  申请额度</a>
								  </s:elseif>
								  <s:else>
								    <a href="addBorrowInit.mht?t=0"><span>step3 </span> 发布贷款</a>	
								  </s:else>
							</s:if>
							<s:else>
								<s:if test="#session.user.authStep>=5">
								  <s:if test="#session.stepMode ==1">
								     <a href="addBorrowInit.mht?t=${session.borrowWay}"><span>step3 </span> 发布贷款</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a href="creditingInit.mht"><span>step3 </span> 申请额度</a>
								  </s:elseif>
								  <s:else>
								    <a href="addBorrowInit.mht?t=0"><span>step3 </span> 发布贷款</a>	
								  </s:else>
								</s:if>	
								<s:else>
								  <s:if test="#session.stepMode ==1">
								       <a><span>step3 </span> 发布贷款</a>							  
								  </s:if>
								  <s:elseif test="#session.stepMode ==2">
								     <a><span>step3 </span> 申请额度</a>
								  </s:elseif>
								</s:else>
							</s:else>
							</li>
						</ul>
					</div>
					<div class="r-main">
						<div class="til01">
							<ul>
								<li  id="li_geren">
									详细信息
								</li>
								<s:if test="#session.user.userType ==1">
								<li  id="li_work" >
									工作认证信息
								</li>
								</s:if>
								<li  id="li_vip" class="on">
									申请vip
								</li>
							</ul>
						</div>
						<div class="rmainbox">
                       
							<div class="box01"   id="div_vip">
								<p class="tips" style="color: #ff0000;">
									只需交纳少量会员费即可成为网站会员，会员可享受以下特权。<br/>
									投资者：
									<br />
									网站合作商提供投资担保，享受100%本金保障。对于担保标、推荐标，还能100%保利息。（普通用户仅保障本金）
									有专业客服跟踪服务，体验尊贵感受。 享有尊贵VIP身份标识。<br /> 借款者：<br />享有借款资格，及时缓解资金压力。
									参与网站举行的各种活动。
								</p>
								<div class="tab">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<s:if test="#request.vippagemap.vipStatus==1">
										 <tr>
										    <td align="right">VIP会员会费：</td>
										    <td><font color='red'>${session.vipFee }</font>元。</td>
										  </tr>
										  <tr>
										    <td align="right">账户可用余额：</td>
										    <td>
											    <font color='red'>${vippagemap.usableSum }</font>元。&nbsp;
												<s:if test="#request.vippagemap.usableSum < #session.vipFee">
												(余额不足，<a href="rechargeInit.mht" style="color:#FF9623;font-size:14px;">马上充值</a>)
												</s:if>
										    </td>
										  </tr>
										 </s:if>
										<tr>
											<td align="right">
												您的状态是：
											</td>
											<td>
											<s:if test="#request.vippagemap.vipStatus==2">尊敬的vip会员</s:if>
											<s:else>普通会员</s:else>
											</td>
										</tr>
										<tr>
											<td align="right">
												用 户 名：
											</td>
											<td>
												${vippagemap.username }
											</td>
										</tr>
										<s:if test="#session.user.userType ==1">
										<tr>
											<td align="right">
												姓 名：
											</td>
											<td>
												${vippagemap.realName }
												
											</td>
										</tr>
										</s:if>
										<tr>
											<td align="right">
												邮 箱：
											</td>
											<td>
												${vippagemap.email }
											</td>
										</tr>
										<tr>
											<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>选择客服：
											</td>
											<td>
											
												<a id = "kefuname">${vippagemap.kefuname }</a>
												<input type="hidden" value="${vippagemap.kfid }" name="paramMap.kefu" id="kefu"/>
											    
											    <s:if test="#request.vippagemap.vipStatus==2">&nbsp;</s:if>
											    <s:else>
											    <input type="button" id="btn_sp" class="scbtn"
									             style="cursor: pointer;" value="选择客服" onclick="fff()"/>
												</s:else>
												<span style="color: red; float: none;" id="u_kefu"
														class="formtips"></span>
														
											</td>
										</tr>
										<s:if test="%{#request.isVip eq 'false'}">
										<tr>
											<td align="right">
													<span style="color: red; float: none;" 
														class="formtips">*</span>验 证 码：
											</td>
											<td>
												<input type="text" class="inp100x" name="paramMap.code" id="code"/>
											
												 <img src="admin/imageCode.mht?pageId=vip" title="点击更换验证码" style="cursor: pointer;"
									id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
												
												
												<span style="color: red; float: none;" id="u_code"
														class="formtips"></span>
											</td>
										</tr>
										</s:if>
										<tr>
											<td align="right">&nbsp;
												
											</td>
											<s:if test="#request.vippagemap.vipStatus==2">&nbsp;</s:if>
											<s:else><td style="padding-top: 20px;">
											  <input  type="button" id="vip_btn"  class="button w140" value="我要申请"/>
											</td></s:else>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- 引用底部公共部分 -->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
	function fff(){
		$.fancybox.open({
             href: 'querykefu.mht',
             type: 'ajax',
             padding: 5,
             fitToView: true,
             width: 700,
             height: 400,
             autoSize: false,
             closeClick: false
         });
	}
</script>
<script type="text/javascript">
	function switchCode(){
		var timenow = new Date();
		$('#codeNum').attr('src','admin/imageCode.mht?pageId=vip&d='+timenow);
	};
</script>
<script type="text/javascript">
		  /*
		   $(function(){
		   $("#btn_sp").click(function(){
		      //window.showModalDialog("uploadFileAction.mht?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
		      var   rv   =   showModalDialog("querykefu.mht");
               $("#kefuname").html(rv[0]);
               $("#kefu").attr("value",rv[1]);
		   });
		   })
		   */
	$(function(){
	//--------------add by houli 判断用户是否已经申请了vip，如果申请了则该页面显示灰色
	   var isVip = '${isVip}';
	   if(isVip == "true"){//页面操作按钮变灰色
	       $("#vip_btn").attr('style','display:none;');
	       $("#btn_sp").attr('style','display:none;');
	       $("#context").attr('disabled','disabled');
	       $("#code").attr('disabled','disabled'); 
	       $("#codeNum").attr('style','display:none;');
	       
	   }
	
	
	    $('#code').blur(function(){
	     if($('#code').val()==""){
	         $('#u_code').html("验证码不能为空");
	         return ;
	      }else{
	       $('#u_code').html("");
	      }
	      
	   }); 
	   
	   
	   
	   $('#btn_sp').click(function(){
	          if($("#kefu").val()==""){
	       $("#u_kefu").html("客服不能为空");
	       return ;
	    }else{
	     $("#u_kefu").html("");
	    }
	     });
	});
		   
		
</script>
		
<script type="text/javascript" src="css/popom.js"></script>
<script type="text/javascript">
	//初始化
	$(function(){
		$("#li_geren").click(function(){
			var userType='${session.user.userType}';//用户是否是企业用户
			var from = '${from}';
			if(userType==2){
				window.location.href='queryCompayData.mht?from='+from;
			}else{
				window.location.href='querBaseData.mht?from='+from;
			}
		});
	     
	     
		$("#li_work").click(function(){
			var from = '${from}';
	       	window.location.href='querWorkData.mht?from='+from;
		});
	     
		$("#li_vip").click(function(){
			var from = '${from}';
			window.location.href='quervipData.mht?from='+from;
		});
	       
	});
 
    
	$(function(){
		$("#vip_btn").click(function(){
			var param= {};
			param["paramMap.pageId"] = "vip";
			param["paramMap.code"] = $("#code").val();
			param["paramMap.content"] = $("#context").val();
			param["paramMap.kefu"] = $("#kefu").val();
			//----add by houli
			param["paramMap.from"] = '${from}';
			var from = '${from}';
			  //alert(from);
			var btype = '${btype}';
			if(btype == ''){
				btype = '${session.borrowWay}';
			}
			$.post("updataUserVipStatus.mht",param,function(data){
				if(data.msg==1){
					alert("申请vip成功!");
					if(${user.userType}==2){
						window.location.href="companyPictureDate.mht?from="+from+"&btype="+btype;
					}else{
						window.location.href="userPassData.mht?from="+from+"&btype="+btype;
					}
				}else if(data.msg==5){
					$("#u_code").html("验证码错误");
					switchCode();
				}else if(data.msg==2){
					$("#u_kefu").html("跟踪人不能为空");
					switchCode();
				}else if(data.msg==13){
					window.location.href="querBaseData.mht";
				}else if(data.msg==14){
					window.location.href="querWorkData.mht?btype="+btype;
				}else{
					alert(data.msg);
					switchCode();
				}
			});
		});
	});
    /*
	function fffb(){
		alert(("ss");
		$.jBox("iframe:querykefu.mht", {
			title: "选择客服",
			width: 800,
			height: 500,
			buttons: {  }
		});
	}*/
    
	function ffff(f,d){
		$("#kefuname").html(f);
		$("#kefu").attr("value",d);
		$.fancybox.close();
		if($('#kefu').val() != ''){
			$('#u_kefu').html('');
		}
	}
	function ae(){
		alert("ae");
	}

</script>
		
</body>
</html>
