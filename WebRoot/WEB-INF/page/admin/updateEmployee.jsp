<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <title>更新员工信息</title>
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
        <script  language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		
    </head>
    <body style="min-width: 1000px;">
                    <div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
                        <!-- 内容显示位置 -->
                        <table width="100%" border="0" cellspacing="1" cellpadding="3">
                        <tr>
                        <table>
                            <tr>
                            <td width="100" height="28" class="xxk_all_a">
                                <a href="employeeInit.mht">公司员工</a>
                            </td>
                            <td width="2">
                                &nbsp; 
                            </td>
                            <td width="100" id="today" height="28"   class="xxk_all_a">
                                <a href="addEmployeeInit.mht">添加员工</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                            <td width="100" id="today" height="28"   class="main_alll_h2">
                                <a href="updateEmployeeInit.mht?id=${paramMap.id }">更新员工信息</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                        </tr>
                            </table>
                        </tr>
                        <tr>
                        <td id="secodetble" style="width: 60%">
                        <table  border="0" cellspacing="1" cellpadding="3" width="60%">
                            <tr>
                            <td align="center">
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    平台用户名：
                                </td>
                                <td align="left" class="f66">           
                                    <input type="text" class="inp188" id="username"  disabled="true"  value="${paramMap.username }" ><span class="require-field">*&nbsp;</span>
                                    <input name="paramMap.userId"  id="userId" type="hidden"  />
                                </td>                           
                            </tr>
							  <tr>
                                <td height="36" align="right" class="blue12">
                                  邮箱：
                                </td>
                                <td align="left" class="f66">
                                    <input type="text" class="inp188" id="email"  value="${paramMap.email }"  disabled="true" />
                                </td>   
                            
                            </tr>
							<tr>
                                <td height="36" align="right" class="blue12">
                                    姓名：
                                </td>
                                <td align="left" class="f66">
                                    <input type="text" class="inp188" id="realname"  value="${paramMap.realName }"   disabled="true" />
                                </td>   
                            </tr>
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    手机：
                                </td>
                                <td align="left" class="f66">
                                    <input type="text" class="inp188" id="phone"  value="${paramMap.cellPhone}" disabled="true" />
                                </td>   
                            
                            </tr>
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    部门：
                                </td>
                                <td align="left" class="f66">
                                    <s:select list="#request.departments"   listKey="selectKey"  listValue="selectName"  headerKey=""  headerValue="----请选择部门----"  name="paramMap.department" class="inp188" id="department" /><span class="require-field">*&nbsp;</span>
                                </td>   
                            </tr>
                           	<tr>
                               <td height="36" align="right" class="blue12">
                                   职位：
                               </td>
                               <td align="left" class="f66">
                                  <input type="text"  value="${paramMap.job }" id="job"  class="inp188"  id="orgName"  /><span class="require-field">*&nbsp;</span>
                               </td>   
                            </tr>
							<s:if test="#request.paramMap.hasWork1==0">
							         <tr>
		                               <td height="36" align="right" class="blue12">
		                                   是否离职：
		                               </td>
		                               <td align="left" class="f66">
		                                  <s:select  list="#{'0':'是','1':'否'}"  name="paramMap.hasWork1"  id="hasWork"></s:select><span class="require-field">*&nbsp;</span>
		                               </td>   
                                    </tr>
                                    <tr>
                                       <td height="36" align="right" class="blue12">
                                           离职时间：
                                       </td>
                                       <td align="left" class="f66">
                                          <input id="leavetime"    class="Wdate" value="${paramMap.leavetime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  name="paramMap.entrytime"  type="text"/>&nbsp;&nbsp;<span class="require-field">*&nbsp;</span>
                                       </td>   
                                    </tr>
							</s:if>
							<tr>
                               <td height="36" align="right" class="blue12">
                                   入职时间：
                               </td>
                               <td align="left" class="f66">
                                  <input id="entrytime"    class="Wdate" value="${paramMap.entrytime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  name="paramMap.entrytime"  type="text"/>&nbsp;&nbsp;<span class="require-field">*&nbsp;</span>
                               </td>   
                            </tr>
                            <tr>
                                <td height="36" align="right" class="blue12">
                                    
                                </td>
                                <td align="left" class="f66">
                                	<span>带<span class="require-field">*&nbsp;</span>的为必填项</span><br/>
                                    <input id="add" type="button" value="&nbsp;&nbsp;修改&nbsp;&nbsp;" name="search" />
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="button" value="&nbsp;&nbsp;取 消&nbsp;&nbsp;" onclick="javascript:history.go(-1);">
                                </td>
                                </tr>
                                </td>
                            </tr>
                        </table>
                         </td>
                          </tr>
                        </table>
                        <br />
                    </div>
    <script  type="text/javascript">
       function searchUser(){
    	   var username = $("#username").val();
    	   if(isBlank(username)){
    		   $("#info1").html("请输入平台用户名");
    		   return;
    	   }else{
    		   $("#info1").html("");
    	   }
    	   var param={};
    	   param["paramMap.username"] = username;
    	   $.post("searchEmployee.mht",param,function(data){
    		    if(data != null){
    		    	$("#phone").val(data.cellPhone);
    		    	$("#realname").val(data.realName);
    		    	$("#email").val(data.email);
    		    	$("#userId").val(data.id);
    		    	$("#username").val(data.username);
    		    }else{
    		    	$("#phone").val("");
    		    	$("#userId").val("");
                    $("#realname").val("");
                    $("#email").val("");
    		    	$("#info1").html("找不到您要查询的用户信息，请重新查找");
    		    }
    	   })
       }	
    
		
    $("#add").click(function(){
        var param = {};
        param["paramMap.id"] = ${paramMap.id}
        param["paramMap.department"] = $("#department").val();
        param["paramMap.job"] = $("#job").val();
        param["paramMap.entrytime"] = $("#entrytime").val();
        param["paramMap.leavetime"] = $("#leavetime").val();
        param["paramMap.hasWork"] = $("#hasWork").val();
		
		if(isBlank(param["paramMap.department"])){
			alert("请选择部门")
            return;
		}
		
		if(isBlank(param["paramMap.job"])){
            alert("请输入职位")
            return;
        }
		
		if(param["paramMap.job"].length>25){
			alert("职位名称长度不能超过12个字符");
            return;
		}
		
		if(isBlank(param["paramMap.entrytime"])){
            alert("请选择入职时间")
            return;
        }
		
		
        $.post("updateEmployee.mht",param,function(data){
           if(data==1){
        	   alert("更新成功！");
               window.location.href="employeeInit.mht";
           }else{
		   	  alert("更新失败！");
		   }
        });
        });
		
    //去掉字符串头尾空格   
	function trim(str) {   
	    return str.replace(/(^\s*)|(\s*$)/g, "");   
	}
 </script>
</body>
</html>

