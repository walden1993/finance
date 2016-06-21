<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
  <jsp:include page="/include/head.jsp"></jsp:include>
  <script type="text/javascript">var menuIndex = 2;</script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include> 

<!--内容区域-->
<div class="pay_banner"></div>
<div class="pay_content">
   <div class="w950 location"><a href="<%=application.getAttribute("basePath")%>" >首页</a> > 我要借款</div>
   <div class="pay_bj w950 clearfix">
     <div class="fl nav_lft_info w590">
        <h1>借款类型</h1>
        <ul class="pay_list clearfix">
          <li>
             <div class="tit">机构担保借款</div> 
             <div class="pic"><img src="images/pay_pic01.jpg" width="76" height="76" /></div>
             <div class="info">
               <p><b>借款额度：10,000,000.00元</b></p>
               <p>是指三好资本的合作伙伴为相应的借款提供连带保证，并负有连带担保责任的借款。</p>
             </div>
          </li>
          <li>
             <div class="tit01">实地考察借款（抵押）</div> 
             <div class="pic"><img src="images/pay_pic02.jpg" width="76" height="76" /></div>
             <div class="info">
               <p><b>借款额度：10,000,000.00元</b></p>
               <p>个人或企业现场考察审批借款。可提供相应的房产、车辆、设备抵押，更好的获取借款。</p>
             </div>
          </li>
          <li>
             <div class="tit02">其他借款</div> 
             <div class="pic"><img src="images/pay_pic03.jpg" width="76" height="76" /></div>
             <div class="info">
               <p class="tc">即将推出...</p>
             </div>
          </li>
        </ul>
     </div>
     <div class="fr nav_rig_info clearfix">
        <div class="tit clearfix"><h1>借款申请</h1><span class="ml10">目前只支持深圳区域借款申请</span></div>
        <form action="AddborrowNew.mht" method="post"  id="form">
       <div class="loan_con clearfix">
         <ul>
         <li>
             <div class="l_title">借款方类型：<i class="red">*</i></div>
             <div class="info">
                <s:radio onblur="validateForm(5)"  name="paramMap.borrowerType"  value="1"   list="#{1:'个人用户','2':'企业用户' }" ></s:radio>
               <p class="red"><span id="info_5"></span><s:fielderror fieldName="borrowerType" /></p>
             </div>
             </li>
           <li>
             <div class="l_title">联系人姓名：<i class="red">*</i></div>
             <div class="info">
                <input onblur="validateForm(1)" onfocus="clearInfo(1)" maxlength="10"  id="contact_name" name="paramMap.contact_name" type="text" class="put01"  />
                <p class="red"><span id="info_1"></span><s:fielderror fieldName="contact_name" /></p>
             </div>
             </li>
             <li>
             <div class="l_title">联系手机/电话：<i class="red">*</i></div>
             <div class="info">
               <input onblur="validateForm(2)"  onfocus="clearInfo(2)"  maxlength="20"    id="contact_phone" name="paramMap.contact_phone" type="text" class="put01" />
               <p class="red"><span id="info_2"></span><s:fielderror fieldName="contact_phone" /></p>
             </div>
             </li>
            <li>
             <div class="l_title">性别：<i class="red">*</i></div>
             <div class="info">
                <s:radio  onblur="validateForm(3)"   name="paramMap.sex" list="#{0:'男','1':'女' }"  value="0"></s:radio>
               <p class="red"><span id="info_3"></span><s:fielderror fieldName="sex" /></p>
             </div>
             </li>
             <li>
             <div class="l_title">年龄：<i class="red">*</i></div>
             <div class="info">
               <span class="yuan">岁</span>
               <input onblur="validateForm(4)"  onfocus="clearInfo(4)"    id="age"  name="paramMap.age" type="text" class="put01"  maxlength="3" />
               <p class="red"><span id="info_4"></span><s:fielderror fieldName="age" /></p>
             </div>
             </li>
             <li>
             <div class="l_title">借款类型：<i class="red">*</i></div>
             <div class="info">
               <s:select onblur="validateForm(6)"  list="#session.syspar"  headerKey=""   headerValue="----请选择----"  listKey="selectKey"  listValue="selectName"  onfocus="clearInfo(6)"  id="borrowWay" name="paramMap.borrowWay" class="sel"></s:select>
               <p class="red"><span id="info_6"></span><s:fielderror fieldName="borrowWay" /></p>
             </div>
             </li>
             <li>
             <div class="l_title">是否有抵押物：<i class="red">*</i></div>
             <div class="info">
                <s:radio onblur="validateForm(7)" onfocus="clearInfo(7)"  name="paramMap.hasMortgage" list="#{0:'是','1':'否' }"  value="0"></s:radio>
               <p class="red"><span id="info_7"></span><s:fielderror fieldName="hasMortgage" /></p>
             </div>
             </li>
             <li>
             <div class="l_title">借款金额：<i class="red">*</i></div>
             <div class="info">
               <span class="yuan">元</span>
               <input onblur="validateForm(8)" onfocus="clearInfo(8)"   id="borrowAmount" name="paramMap.borrowAmount" type="text" class="put01"  maxlength="10" />
               <p class="red"><span id="info_8"></span><s:fielderror fieldName="borrowAmount" /></p>
             </div>
             </li>
             <li>
             <div class="l_title">借款期限：<i class="red">*</i></div>
             <div class="info">
               <span class="yuan">月</span>
               <input onblur="validateForm(9)" onfocus="clearInfo(9)"     id="borrowLine" name="paramMap.borrowLine" type="text" class="put01" maxlength="2" />
               <p class="red"><span id="info_9"></span><s:fielderror fieldName="borrowLine" /></p>
             </div>
             </li>
             <li>
             <div class="l_title">借款描述：<i class="red">*</i></div>
             <div class="info">
               <textarea onblur="validateForm(10)" maxlength="500" onfocus="clearInfo(10)"  id="describe" name="paramMap.describe"  class="area"></textarea>
               <p class="red"><span id="info_10"></span><s:fielderror fieldName="describe" /></p>
             </div>
             </li>
             <li>
             <div class="l_title">验证码：<i class="red">*</i></div>
             <div class="info">
               <input onblur="validateForm(11)" maxlength="4" onfocus="clearInfo(11)"  id="code" name="paramMap.code"  class="put01"/>
               <p class="red"><span id="info_11"></span><s:fielderror fieldName="code" /></p>
               <img src="imageCode.mht?pageId=addNewBorrow&time=<%=new java.util.Date().getTime() %>" title="点击更换验证码" style="cursor: pointer;" id="codeNum" width="76" height="44" onclick="javascript:switchCode()">
             </div>
             </li>
             <li>
             <div class="l_title">&nbsp;</div>
             <div class="info">
               <input type="button" value="提交申请" class="button w"  id="btn_save" />
               <p class="red"><s:actionerror/></p>
             </div>
             </li>
         </ul>
       </div>
       </form>
     </div>
   </div>
</div>

<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
  $(function(){
	  $("#btn_save").click(function(){
          if(validateForm1()){
              $("form").submit();
              switchCode();
          }
      });
  })
  
  function clearInfo(i){
	  switch (i) {
	case 1:
        $("#info_1").html("");
        break;
	case 2:
        $("#info_2").html("");
        break;
	case 3:
        $("#info_3").html("");
        break;
	case 4:
        $("#info_4").html("");
        break;
	case 5:
        $("#info_5").html("");
        break;
	case 6:
        $("#info_6").html("");
        break;
	case 7:
        $("#info_7").html("");
        break;
	case 8:
        $("#info_8").html("");
        break;
	case 9:
        $("#info_9").html("");
        break;
	case 10:
	      $("#info_10").html("");
		break;
	case 11:
	      $("#info_11").html("");
		break;
	default:
		break;
	}
  }
  
  function checkPhone(phone)
  {
	  var reg = new  RegExp("\\d{3}-\\d{8}|\\d{4}-\\d{7}")
      // 验证手机号码，包含153，159号段
      return reg.test(phone);
  }

  function checkNumber(number)
  {
      // 验证电话号码
      var reg = new RegExp("^0{0,1}(13[0-9]|15[3-9]|15[0-2]|18[0-9])[0-9]{8}$");
      return reg.test(number);
  }

  function validateForm1(){
      var borrowerType = $("input[name='paramMap.borrowerType']:checked").val();
     if(isBlank(borrowerType)){
         $("#info_5").html("请选择借款方类型");
         return false;
     }else{
         $("#info_5").html("");
     }
     
     var contact_name = $("#contact_name").val();
     
     if(isBlank(contact_name)){
         $("#info_1").html("请填写联系人姓名");
         return false ;
     }else{
         $("#info_1").html("");
     }
     
     var contact_phone = $("#contact_phone").val();
     
     if(isBlank(contact_phone)){
         $("#info_2").html("请填写联系人手机/电话");
         return false;
     }else{
         if(!checkPhone(contact_phone) && !checkNumber(contact_phone)){
             $("#info_2").html("请填写正确的联系人手机/电话");
         }else{
             $("#info_2").html("");
         }
     }
     
     var sex =  $("input[name='paramMap.sex']:checked").val();
     
     if(isBlank(sex)){
         $("#info_3").html("请选择性别");
         return false;
     }else{
         $("#info_3").html("");
     }
     
     var age = $("#age").val();
     
     if(borrowerType==1 && isBlank(age)){
         $("#info_4").html("请填写年龄");
         return false ;
     }else{
         $("#info_4").html("");
     }
     
     var borrowWay = $("#borrowWay").val();
     if(isBlank(borrowWay)){
         $("#info_6").html("请选择借款方式");
         return false;
     }else{
         $("#info_6").html("");
     }
     
     var hasMortgage = $("input[name='paramMap.hasMortgage']:checked").val();
     
     if(isBlank(hasMortgage)){
         $("#info_7").html("请选择是否有抵押");
         return false;
     }else{
         $("#info_7").html("");
     }
     
     var borrowAmount = $("#borrowAmount").val();
     
     if(isBlank(borrowAmount)){
         $("#info_8").html("请填写借款金额");
         return false;
     }else{
         $("#info_8").html("");
     }
     
     var borrowLine = $("#borrowLine").val();
     
     
     if(isBlank(borrowLine)){
         $("#info_9").html("请填写借款期限");
         return false;
     }else{
         $("#info_9").html("");
     }
     
     var describe = $("#describe").val();
     
     if(isBlank(describe)){
         $("#info_10").html("请填写借款描述");
         return false;
     }else{
         $("#info_10").html("");
     }
     
     var code = $("#code").val();
     
     if(isBlank(code)){
         $("#info_11").html("请填写验证码");
         return false;
     }else{
         $("#info_11").html("");
     }
     return true;
  }
  
   function validateForm(i){
	   var borrowerType = $("input[name='paramMap.borrowerType']:checked").val();
	   switch (i) {
	    case 1:
	    	var contact_name = $("#contact_name").val();
	        if(isBlank(contact_name)){
	            $("#info_1").html("请填写联系人姓名");
	            return false ;
	        }else{
	            $("#info_1").html("");
	        }
	        break;
	    case 2:
	    	var contact_phone = $("#contact_phone").val();
	        
	        if(isBlank(contact_phone)){
	            $("#info_2").html("请填写联系人手机/电话");
	            return false;
	        }else{
	            if(!checkPhone(contact_phone) && !checkNumber(contact_phone)){
	                $("#info_2").html("请填写正确的联系人手机/电话");
	            }else{
	                $("#info_2").html("");
	            }
	        }
	        break;
	    case 3:
	    	var sex =  $("input[name='paramMap.sex']:checked").val();
	        
	        if(isBlank(sex)){
	            $("#info_3").html("请选择性别");
	            return false;
	        }else{
	            $("#info_3").html("");
	        }
	        break;
	    case 4:
	    	var age = $("#age").val();
	        
	        if(borrowerType==1 && isBlank(age)){
	            $("#info_4").html("请填写年龄");
	            return false ;
	        }else{
	            $("#info_4").html("");
	        }
	        break;
	    case 5:
	    	
	        if(isBlank(borrowerType)){
	            $("#info_5").html("请选择借款方类型");
	            return false;
	        }else{
	            $("#info_5").html("");
	        }
	        break;
	    case 6:
	    	var borrowWay = $("#borrowWay").val();
	        if(isBlank(borrowWay)){
	            $("#info_6").html("请选择借款方式");
	            return false;
	        }else{
	            $("#info_6").html("");
	        }
	        break;
	    case 7:
	    	var hasMortgage = $("input[name='paramMap.hasMortgage']:checked").val();
	        if(isBlank(hasMortgage)){
	            $("#info_7").html("请选择是否有抵押");
	            return false;
	        }else{
	            $("#info_7").html("");
	        }
	        break;
	    case 8:
	    	 var borrowAmount = $("#borrowAmount").val();
	         
	         if(isBlank(borrowAmount)){
	             $("#info_8").html("请填写借款金额");
	             return false;
	         }else{
	             $("#info_8").html("");
	         }
	        break;
	    case 9:
	    	var borrowLine = $("#borrowLine").val();
	        if(isBlank(borrowLine)){
	            $("#info_9").html("请填写借款期限");
	            return false;
	        }else{
	            $("#info_9").html("");
	        }
	        break;
	    case 10:
	    	var describe = $("#describe").val();
	        
	        if(isBlank(describe)){
	            $("#info_10").html("请填写借款描述");
	            return false;
	        }else{
	            $("#info_10").html("");
	        }
	        break;
	    case 10:
	    	var code = $("#code").val();
	        
	        if(isBlank(code)){
	            $("#info_11").html("请填写验证码");
	            return false;
	        }else{
	            $("#info_11").html("");
	        }
	        break;
	    default:
	        break;
	    }
	  return true;
   }
   //初始化
	function switchCode(){
		var timenow = new Date();
		$("#codeNum").attr("src","admin/imageCode.mht?pageId=userlogin&d="+timenow);
	}
</script>
</html>
