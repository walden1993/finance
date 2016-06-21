<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>手动添加用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript" src="../common/dialog/popup.js"></script>

</head>
<body style="min-width: 1000px;">
	<div id="showcontent"
		style="width: auto; background-color: #FFF; padding: 10px;">
		<!-- 内容显示位置 -->
		<table width="100%" style="float: left;" border="0" cellspacing="1"
			cellpadding="3">
			<tr>
				<table>
					<tr>
						<td width="100" height="28" class="xxk_all_a"><a
							href="queryUserManageInfoIndex.mht">用户基本信息</a></td>
						<td width="2">&nbsp;</td>
						<td width="100" id="today" height="28" class="main_alll_h2">
							<a href="addUserIndex.mht">手动添加用户</a></td>
						<td width="2">&nbsp;</td>
						<td width="100" id="today" height="28" class="xxk_all_a"><a
							href="abnormalUserIndex.mht">异常用户</a></td>
						<td width="2">&nbsp;</td>
					</tr>
				</table>
			</tr>
			<tr>
				<td id="secodetble" style="width: 100%">
					<table border="0" style="float: left;" cellspacing="1"
						cellpadding="3" width="50%">
						<tr>
							<td align="center">
								<!-- <tr>
                                <td height="36" align="right" class="blue12">
                                    状态：
                                </td>
                                <td align="left" class="f66">           
                                    &nbsp;&nbsp;&nbsp;<input type="text" class="inp188" id="address" />
                                </td>                           
                            </tr> -->
								<tr>
									<td height="36" align="right" class="blue12">用户类型：</td>
									<td align="left" class="f66"><select id="userType">
											<option value="1">个人</option>
											<option value="2">企业</option>
									</select><span class="require-field">*&nbsp;</span></td>
								</tr>
								<tr>
									<td height="36" align="right" class="blue12">手机号码：</td>
									<td align="left" class="f66"><input type="text"
										class="inp188" id="mobilePhone" /><span class="require-field">*&nbsp;(13600000000)</span>
									</td>

								</tr>
								<tr>
									<td height="36" align="right" class="blue12">邮箱：</td>
									<td align="left" class="f66"><input type="text"
										class="inp188" id="email" /><span class="require-field">&nbsp;</span>
									</td>

								</tr>
								<tr>
									<td height="36" align="right" class="blue12">用户名：</td>
									<td align="left" class="f66"><input type="text"
										class="inp188" id="username" /><span class="require-field">*&nbsp;</span>
									</td>
								</tr>
								<tr>
									<td height="36" align="right" class="blue12">登录密码：</td>
									<td align="left" class="f66"><input type="password"
										class="inp188" id="password" value="123456" /><span
										class="require-field">*&nbsp;(默认123456)</span></td>
								</tr>
								<tr>
									<td height="36" align="right" class="blue12">确认登录密码：</td>
									<td align="left" class="f66"><input type="password"
										class="inp188" id="comfirpassword" value="123456" /><span
										class="require-field">*&nbsp;</span></td>
								</tr>

								<tr>
									<td height="36" align="right" class="blue12">交易密码：</td>
									<td align="left" class="f66"><input type="password"
										class="inp188" id="dealpwd" value="654321" /><span
										class="require-field">*&nbsp;(默认654321)</span></td>
								</tr>
								<tr>
									<td height="36" align="right" class="blue12">确认交易密码：</td>
									<td align="left" class="f66"><input type="password"
										class="inp188" id="comfirdealpwd" value="654321" /><span
										class="require-field">*&nbsp;</span></td>
								</tr>
							<tr>
								<td height="36" align="right" class="blue12">推荐人用户名：</td>
								<td align="left" class="f66"><input type="text"
									class="inp188" id="refferee" /><span class="require-field">&nbsp;</span>
								</td>
							</tr>

							<tr>
								<td height="36" align="right" class="blue12"></td>
								<td align="left" class="f66"><span>带<span
										class="require-field">*&nbsp;</span>的为必填项</span><br /> <input
									id="add" type="button" value="&nbsp;&nbsp;添  加&nbsp;&nbsp;"
									name="search" /> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
									value="&nbsp;&nbsp;取 消&nbsp;&nbsp;"
									onclick="javascript:history.go(-1);">
								</td>
							</tr>
							</td>
						</tr>
					</table>
					
					
					<table border="0" cellspacing="1" style="float: right;"
						cellpadding="3" width="50%" id="per">
						
						<tr>
									<td height="36" align="right" class="blue12">真实姓名：</td>
									<td align="left" class="f66"><input type="text"
										class="inp188" id="realName" /><span class="require-field">&nbsp;</span>
									</td>
								</tr>
								<tr>
									<td height="36" align="right" class="blue12">身份证号码：</td>
									<td align="left" class="f66"><input type="text"
										class="inp188" id="idNo" /><span class="require-field">&nbsp;</span>
									</td>
								</tr>
						</table>

					<table border="0" cellspacing="1" style="float: right;display: none;"
						cellpadding="3" width="50%" id="org">

								<tr>
									<td height="36" align="right" class="blue12">企业名称：</td>
									<td align="left" class="f66"><input type="text"
										class="inp188" id="orgName" /><span class="require-field">*&nbsp;</span>
									</td>
								</tr>
								<tr>
									<td height="36" align="right" class="blue12">企业地址：</td>
									<td align="left" class="f66"><input type="text"
										class="inp188" id="address" /></td>

								</tr>
								<tr>
									<td height="36" align="right" class="blue12">法人代表：</td>
									<td align="left" class="f66"><input type="text"
										class="inp188" id="legal_person" /></td>

								</tr>
								<tr>
									<td height="36" align="right" class="blue12">工商注册号：</td>
									<td align="left" class="f66"><input type="text"
										class="inp188" id="reg_num" /></td>
								</tr>
								<tr>
									<td height="36" align="right" class="blue12">组织机构代码：</td>
									<td align="left" class="f66"><input type="text"
										class="inp188" id="organization_code" /></td>
								</tr>
								<tr>
									<td height="36" align="right" class="blue12">简称：</td>
									<td align="left" class="f66"><input type="text"
										class="inp188" id="easyName" /><span class="require-field">*使用大写拼音缩写，例如：华融简称“HR”&nbsp;</span></td>
								</tr>
						</div>
					</table>
		</table>
	</div>


	<script type="text/javascript">
    	
		function  checkMobile(str) {
    var  re = /^1\d{10}$/
    if (re.test(str)) {
        return true;
    }else{
       return false;
    }
}

		$("#userType").change(function(){
            if(this.value==1){
				$("#org").hide();
				$("#per").show();
            }else{
				$("#org").show();
				$("#per").hide();
            }
        })
		
    $("#add").click(function(){
        var param = {};
		
		param["paramMap.userType"] = $("#userType").val();
		
		 if(param["paramMap.userType"]==2){
		 	param["paramMap.orgName"] = $("#orgName").val();
			if(param["paramMap.orgName"]==null || param["paramMap.orgName"]=='' ){
            alert("请填写企业名称");
            return;
        }else if(param["paramMap.orgName"].length>15){
            alert("企业名称长度不能超过20个字符。");
            return;
        }
        }else{
			param["paramMap.orgName"]="";
		}
		
        param["paramMap.username"] = $("#username").val();
        if(param["paramMap.username"]==null || param["paramMap.username"]=='' ){
        	alert("请填写用户名");
        	return;
        }else if(param["paramMap.username"].length>15){
			alert("用户名长度不能超过20个字符。");
			return;
		}
        param["paramMap.mobilePhone"] = $("#mobilePhone").val();
		if(param["paramMap.mobilePhone"]==null || parseInt(param["paramMap.mobilePhone"])==''){
			alert("请输入手机号码");
			return;
		}else if( !checkMobile(param["paramMap.mobilePhone"])){
			alert("请输入正确的手机号码");
			return;
		}
        param["paramMap.password"] = $("#password").val();
		if(param["paramMap.password"]==null || parseFloat(param["paramMap.password"])==''){
			alert("请输入密码");
			return;
		}else if(param["paramMap.password"].length>13 || param["paramMap.password"]<6){
			alert("请输入正确的密码长度6~13");
            return;
		}
 
        param["paramMap.dealpwd"] = $("#dealpwd").val();
		if(param["paramMap.dealpwd"]==null || parseFloat(param["paramMap.dealpwd"])==''){
            alert("请输入交易密码");
            return;
        }else if(param["paramMap.dealpwd"].length>13 || param["paramMap.dealpwd"]<6){
            alert("请输入正确的交易密码长度6~13");
            return;
        }
		
		param["paramMap.comfirpassword"] = $("#comfirpassword").val();
		if(param["paramMap.comfirpassword"]!=null && param["paramMap.comfirpassword"]!=''){
			if(param["paramMap.comfirpassword"]!=param["paramMap.password"]){
				alert("两次登录密码不一致，请重新输入。");
			}
		}else if(param["paramMap.comfirpassword"].length>13 || param["paramMap.comfirpassword"]<6){
            alert("请输入正确的登录密码长度6~13");
            return;
        }else{
			alert("请再次输入登录密码");
			return;
		}
		param["paramMap.comfirdealpwd"] = $("#comfirdealpwd").val();
		if(param["paramMap.comfirdealpwd"]!=null && param["paramMap.comfirdealpwd"]!=''){
            if(param["paramMap.comfirdealpwd"]!=param["paramMap.dealpwd"]){
                alert("两次交易密码不一致，请重新输入。");
            }
        }else if(param["paramMap.comfirdealpwd"].length>13 || param["paramMap.comfirdealpwd"]<6){
            alert("请输入正确的交易密码长度6~13");
            return;
        }else{
            alert("请再次输入交易密码");
            return;
        }
		param["paramMap.email"] = $("#email").val();
		
		var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;  
         if (param["paramMap.email"]!=null && param["paramMap.email"]!='' && !pattern.test(param["paramMap.email"])) {  
                alert("请输入正确的邮箱地址。");  
               return;
         }  
		
		param["paramMap.refferee"] = $("#refferee").val();
		
		param["paramMap.realName"] = $("#realName").val();
		
		param["paramMap.idNo"] = $("#idNo").val();
		
		param["paramMap.orgName"] = $("#orgName").val();
		param["paramMap.address"] = $("#address").val();
		param["paramMap.legal_person"] = $("#legal_person").val();
		param["paramMap.reg_num"] = $("#reg_num").val();
		param["paramMap.organization_code"] = $("#organization_code").val();
		param["paramMap.easyName"] = $("easyName").val();
		
		if(param["paramMap.idNo"]!=null &&param["paramMap.idNo"]!='' &&  !IdCardValidate(param["paramMap.idNo"])){
			alert("请输入正确的身份证号码");
			return false;
		}
		
        $.post("addUserSystem.mht",param,function(data){
           if(data==1){
             alert("手机号不能为空！");
           }else if(data==2){
              alert("用户名不为空！");
           }else if(data==3){
              alert("用户名长度大于2且小于20！");
           } else if(data==4){
              alert("用户名不能包含特殊字符！");
           }else if(data==5){
              alert("用户名不能以下划线开头！");
           }else if(data==6){
              alert("企业名称不能为空！");
           }else if(data==7){
              alert("企业名称长度大于2且小于20！");
           } else if(data==8){
              alert("企业名称不能包含特殊字符！");
           }else if(data==9){
              alert("企业名称不能以下划线开头的！");
           } else if(data==10){
              alert("登录密码不能为空！");
           } else if(data==11){
              alert("用户名已被使用，请重新输入！");
           }else if(data==13){
              alert("手机号已被使用，请重新输入！");
           }else if(data==14){
              alert("企业名称已被使用，请重新输入！");
           }else if(data==15){
              alert("交易密码不能为空！");
           }else if(data==16){
              alert("交易密码不能与登录密码一样！");
           }else if(data==17){
              alert("注册失败！");
           }else if(data==19){
              alert("请再次输入交易密码！");
           }else if(data==20){
              alert("两次的交易密码不一致，请重新输入！");
           }else if(data==21){
              alert("请再次输入登录密码！");
           }else if(data==22){
              alert("两次的登录密码不一致，请重新输入！");
           }else if(data==23){
              alert("推广人不存在，请重新输入！");
           }else if(data==24){
              alert("你输入的身份证已被注册，请重新输入！");
           }else if(data==25){
               alert("你输入的邮箱已被注册，请重新输入！");
           }else{
		   	  alert("注册成功！");
			  window.location.href="queryUserManageInfoIndex.mht";
		   }
        });
        });
		
		var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
function IdCardValidate(idCard) { 
    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
    if (idCard.length == 15) {   
        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");                // 得到身份证数组   
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
            return true;   
        }else {   
            return false;   
        }   
    } else {   
        return false;   
    }   
}   
/**  
 * 判断身份证号码为18位时最后的验证位是否正确  
 * @param a_idCard 身份证号码数组  
 * @return  
 */  
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0;                             // 声明加权求和变量   
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];            // 加权求和   
    }   
    valCodePosition = sum % 11;                // 得到验证码所位置   
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
}   
/**  
  * 验证18位数身份证号码中的生日是否是有效生日  
  * @param idCard 18位书身份证字符串  
  * @return  
  */  
function isValidityBrithBy18IdCard(idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    // 这里用getFullYear()获取年份，避免千年虫问题   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
            return false;   
    }else{   
        return true;   
    }   
}   
  /**  
   * 验证15位数身份证号码中的生日是否是有效生日  
   * @param idCard15 15位书身份证字符串  
   * @return  
   */  
  function isValidityBrithBy15IdCard(idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
                return false;   
        }else{   
            return true;   
        }   
  }   
//去掉字符串头尾空格   
function trim(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
}
		/**  
 * 通过身份证判断是男是女  
 * @param idCard 15/18位身份证号码   
 * @return 'female'-女、'male'-男  
 */  
function maleOrFemalByIdCard(idCard){   
    idCard = trim(idCard.replace(/ /g, ""));        // 对身份证号码做处理。包括字符间有空格。   
    if(idCard.length==15){   
        if(idCard.substring(14,15)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else if(idCard.length ==18){   
        if(idCard.substring(14,17)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else{   
        return null;   
    }   
}
    </script>
</body>
</html>

