<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>查看体验标</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/css.css" rel="stylesheet" type="text/css" />
		<link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css" />
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
        <script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
	</head>
	<body style="min-width: 1000px;">
					<div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
						<!-- 内容显示位置 -->
						<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
						<td id="secodetble" style="width: 60%">
						<table  border="0" cellspacing="1" cellpadding="3" width="60%">
							<tr>
							<td>
						    <table>
							 <tr>
							 <td width="100" height="28"  class="xxk_all_a">
                                    <a href="queryExperienceInit.mht">体验标列表</a>
                                </td>
                                <td width="2">
                                    &nbsp;
                                </td>
                                <td width="100"  class="main_alll_h2">
                                    <a href="javascript:void(0)">查看体验标</a>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
							</tr>
							</table>
							</td>
							</tr>
							<tr>
                                <td height="36" align="right" class="blue12">
                                 	状态：
								</td>
								<td align="left" class="f66">			
									&nbsp;&nbsp;
									<s:if test="#request.map.status==0">
										<input type="text" class="inp188" id="address" value="招标中" disabled='disabled' />
									</s:if>
									<s:if test="#request.map.status==1">
										<input type="text" class="inp188" id="address" value="还款中" disabled='disabled'/>
									</s:if>
									<s:if test="#request.map.status==2">
										<input type="text" class="inp188" id="address" value="已还完" disabled='disabled'/>
									</s:if>
								</td> 							
							
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">
									标题：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
								  	<input type="text" class="inp188" id="title" value="${map.borrowTitle }" disabled='disabled'/>
								</td> 	
							
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">
									体验总金额：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
								  	<input type="text" class="inp188" id="amount" value="${map.borrowAmount }" disabled='disabled'/>
								</td> 	
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">
									年利率：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
									<input type="text" class="inp188" id="annualRate" value="${map.annualRate }" disabled='disabled'/>&nbsp;&nbsp;%
								</td> 	
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									期限：
								</td>
								<td>
									<span style="color: red; float: none;" class="formtips">*</span>
									<select id="deadline"  disabled='disabled'>
										<option value=" " >－请选择－</option>
                                        <option value="1" <s:if test="#request.map.deadline==1">selected="selected"</s:if>>1</option>
                                        <option value="2" <s:if test="#request.map.deadline==2">selected="selected"</s:if>>2</option>
                                        <option value="3" <s:if test="#request.map.deadline==3">selected="selected"</s:if>>3</option>
									 </select>&nbsp;&nbsp;个月
								</td>
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">
									筹标期限：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
								  	<input type="text" class="inp188" id="raiseTerm" value="${map.raiseTerm }" disabled='disabled'/>&nbsp;&nbsp;天
								</td> 	
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">
									收益返还方式：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
									<select id="paymentMode" disabled='disabled' >
										<option value="">－请选择－</option>
                                        <option value="1" <s:if test="#request.map.paymentMode==1">selected="selected"</s:if>>按月还息</option>
									 </select>
								</td> 	
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">
									最小体验金额：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
								  	<select id="minSum" disabled='disabled' >
										<option value="">－请选择－</option>
                                        <option value="1000" <s:if test="#request.map.minTenderedSum==1000">selected="selected"</s:if> >1000</option>
                                        <option value="2000" <s:if test="#request.map.minTenderedSum==2000">selected="selected"</s:if> >2000</option>
                                        <option value="5000" <s:if test="#request.map.minTenderedSum==5000">selected="selected"</s:if> >5000</option>
                                        <option value="10000" <s:if test="#request.map.minTenderedSum==10000">selected="selected"</s:if> >10000</option>
									 </select>
								</td> 	
							</tr>
							<tr>
							    <td height="36" align="right" class="blue12">
									最大体验金额：
								</td>
								<td align="left" class="f66">
									<span style="color: red; float: none;" class="formtips">*</span>
								  	<select id="maxSum" disabled='disabled' >
										<option value="" >－请选择－</option>
                                        <option value="1000" <s:if test="#request.map.maxTenderedSum==1000">selected="selected"</s:if>>1000</option>
                                        <option value="2000" <s:if test="#request.map.maxTenderedSum==2000">selected="selected"</s:if>>2000</option>
                                        <option value="5000" <s:if test="#request.map.maxTenderedSum==5000">selected="selected"</s:if>>5000</option>
                                        <option value="10000" <s:if test="#request.map.maxTenderedSum==10000">selected="selected"</s:if>>10000</option>
                                        <option value="-1" <s:if test="#request.map.maxTenderedSum==-1">selected="selected"</s:if>>不限制</option>
									 </select>
								</td> 	
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
									详细描述：
									<span style="color: red; float: none;" class="formtips">*</span>
								</td>
								<td align="left" class="f66">${map.detail }
									<!--<textarea>  id="tr_content" name="content" rows="20" class="textareash" 
                                            style="width: 670px; padding:5px;"
										<s:property value="%{#request.map.detail}"/>
									</textarea>  -->
								</td>
							</tr>
							<tr>
								<td height="36" align="right" class="blue12">
								</td>
								<td align="left" class="f66">
									<input type="button" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;" onclick="javascript:history.go(-1);">
									&nbsp;&nbsp;&nbsp;&nbsp;
									<s:if test="#request.map.status==1">
										<input id="search" type="button" onclick="investTrial('${map.id}')"  value="&nbsp;&nbsp;还  款&nbsp;&nbsp;" name="search" />
									</s:if>
								</td>
							</tr>
								</table></td>
							</tr>
						</table>
						 </td>
						  </tr>
						</table>
						<br />
					</div>
	<script  type="text/javascript">
    function  investTrial(id){
         $.jBox("iframe:experienceInvestInit.mht?id="+id, {
             title: "体验标还款",
             width: 600,
             height: 260,
             buttons: { '关闭': true }
         })
	}
	</script>
	</body>
</html>
