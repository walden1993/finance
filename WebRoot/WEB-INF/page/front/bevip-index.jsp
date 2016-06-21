<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <jsp:include page="/include/head.jsp"></jsp:include>
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	
<div class="wrap">
  <div class="wdzh w950">
  <ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li><a href="home.mht">我的账户</a></li>
	  <li class="active">申请vip</li>
  </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
    
    
        <div class="r_main w750 fr bg-white mb20">
      <div class="pub_tb bottom clearfix">
		  <h3>申请VIP</h3>
		  <div class="clearfix"></div>
      </div>
      <div class="m20">
    <p class="tipd" style="color:#ff0000;">
    只需交纳少量会员费即可成为网站会员，会员可享受以下特权。<br/>
</p>
		<table class="table table-bordered  w">
			<tr>
				<td width="80">投资者</td>
				<td>网站合作商提供投资担保，享受100%本金保障。对于担保标、推荐标，还能100%保利息。（普通用户仅保障本金）
		有专业客服跟踪服务，体验尊贵感受。
		享有尊贵VIP身份标识。</td>
			</tr>
			<tr>
				<td>借款者</td>
				<td>享有借款资格，及时缓解资金压力。
		参与网站举行的各种活动。</td>
			</tr>
		</table>
    <div class="tab mt10">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="p10" style="line-height: 30px;">
    <s:if test="#request.userMap.vipStatus==1">
  <tr>
    <td align="right">VIP会员会费：</td>
    <td><font color='red'>${session.vipFee }</font>元。</td>
  </tr>
  <tr>
    <td align="right">账户可用余额：</td>
    <td>
	    <font color='red'>${userMap.usableSum }</font>元。&nbsp;
		<s:if test="#request.userMap.usableSum < #session.vipFee">
		(余额不足，<a href="rechargeInit.mht" style="color:#FF9623;font-size:14px;">马上充值</a>)
		</s:if>
    </td>
  </tr>
  </s:if>
  <tr>
    <td align="right">您的状态是：</td>
    <td>	<s:if test="#request.userMap.vipStatus==2">尊敬的vip会员</s:if>
											<s:else>普通会员</s:else></td>
  </tr>
  <tr>
    <td align="right">用　户　名：</td>
    <td> ${userMap.username }</td>
  </tr>
  <s:if test="#request.userMap.realName!=null">
  <tr>
    <td align="right">姓　　　名：</td>
    <td>${userMap.realName }</td>
  </tr>
  </s:if>
  <tr>
    <td align="right">邮　　　箱：</td>
    <td>	${userMap.email }</td>
  </tr>
  <tr>
    <td align="right"><span style="color: red; float: none;" 
														class="formtips">*</span>选择客服：</td>
    <td>		<a id = "kefuname">${userMap.kefuname }</a>
												<input type="hidden" value="${userMap.kfid }" name="paramMap.kefu" id="kefu"/>
											    <input type="button" id="btn_sp" class="scbtn"
									             style="cursor: pointer;" value="选择客服" />
												
												<span style="color: red; float: none;" id="u_kefu"
														class="formtips"></span></td>
  </tr>
 <!--   <tr>
    <td align="right">	<span style="color: red; float: none;" 
														class="formtips">*</span>备 注：</td>
    <td>
    <textarea class="txt420" name="paramMap.content" id="context"></textarea>
												<span style="color: red; float: none;" id="u_content"
														class="formtips"></span>
    </td>
  </tr> -->
  <tr>
    <td align="right">	<span style="color: red; float: none;" 
														class="formtips">*</span>验 证 码：</td>
    <td>		<input type="text" style="border: 1px solid #dedede;" class="inp100x" name="paramMap.code" id="code"/>
												<!--  <img src="admin/imageCode.mht?pageId=vip" width="52" height="20" id="codeN"/>-->
												 <img src="admin/imageCode.mht?pageId=vip" title="点击更换验证码" style="cursor: pointer;"
									id="codeNum" width="46" height="18" onclick="javascript:switchCode()" />
												
												
												<span style="color: red; float: none;" id="u_code"
														class="formtips"></span></td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td style="padding-top:20px;"> <input  type="button" id="vip_btn"  class="button w140" value="我要申请"/></td>
  </tr>
    </table>

    </div>
    </div>
    </div>


</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
 <script>
		    
		    $("#btn_sp").fancybox({
		    	href:'querykefu.mht',
		    	type:'ajax',
				maxWidth	: 600,
				maxHeight	: 400,
				fitToView	: false,
				width		: '70%',
				height		: '70%',
				autoSize	: false,
				closeClick	: false,
				openEffect	: 'none',
				closeEffect	: 'none'
			});
		    
		    function ffff(f,d){
		       $("#kefuname").html(f);
               $("#kefu").attr("value",d);
		    	if($('#kefu').val() != ''){
		    	   $('#u_kefu').html('');
		    	}
		      $.fancybox.close();
		    }
    
    </script>
    <script>
    	
		      function switchCode(){
			     var timenow = new Date();
			     $('#codeNum').attr('src','admin/imageCode.mht?pageId=vip&d='+timenow);
		    };
    </script>
    <script>
    $(function(){
    
    //样式选中
     //$("#zh_hover").attr('class','nav_first');
	 //$("#zh_hover div").removeClass('none');
	 //$('#li_1').addClass('on');
	 //displayDetail(0,1);
	 $("#homeNew").addClass('on');
     $('#context').blur(function(){
		        if($('#context').val()==""){
		            $('#u_content').html("内容不能为空");
		              return ;
		         }else{
		          $('#u_content').html("");
		         }
		      }); 
		       $('#code').blur(function(){
		        if($('#code').val()==""){
		            $('#u_code').html("验证码不能为空");
		            return ;
		         }else{
		          $('#u_code').html("");
		         }
		         
		      }); 
    
    
    });
        
    </script>
    <script>
    $(function(){
    
          $("#vip_btn").click(function(){
		      var param= {};
		      param["paramMap.pageId"] = "vip";
		      param["paramMap.code"] = $("#code").val();
		     // param["paramMap.content"] = $("#context").val();
		      param["paramMap.kefu"] = $("#kefu").val();
		      $.post("updateUserVip.mht",param,function(data){
		      	if(data.msg == "virtual"){
					window.location.href = "noPermission.mht";
					return ;
	     		}
		        if(data.msg==1){
		          alert("申请vip成功");
		          window.location.href='home.mht';
		          //window.location.href="jumptopiturepage.mht";
		        }else{
							alert(data.msg);
			        }
		      });
		      });
    
    
    
    });
    
    </script>
</body>
</html>
