<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>借款产品参数</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		
		
	</head>
	<body>
			<div id="right">
				<div style="padding: 15px 10px 0px 10px;">
					<div>
						<table  border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td width="100" class="xxk_all_a">
								<a href="linkageinit.mht">借款目的</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"   class="xxk_all_a">
								<a href="queryAcountIndex.mht">担保机构</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"   class="xxk_all_a">
								<a href="queryInversIndex.mht">反担保方式</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100"   class="xxk_all_a">
								<a href="queryImageIndex.mht">系统头像设置</a>
							</td>
							<td width="2">
                                &nbsp;
                            </td>
                            <td width="100"   class="xxk_all_a">
                                <a href="querySysparIndex.mht">系统参数设置</a>
                            </td>
                            <td height="28"   class=""><!-- main_alll_h2 -->
                                <a href="querySysparList.mht">单参数设置</a>
                            </td>
							<td>
								&nbsp;
							</td>
							</tr>
						</table>
					</div>
					 
					<div id="showcontent" style="width: auto; background-color: #FFF; padding: 10px;">
						<!-- 内容显示位置 -->
						<table id="gvNews" style="width: 100%; color: #333333;" cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
							<tr class=gvHeader>
								<th style="width: 100px; height: 25px;" align="right"
									class="blue12">
									参数值1
								</th>
								<th style="width: 100px; height: 25px;" align="right"
									class="blue12">
									参数值2
								</th>
								<th style="width: 100px; height: 25px;" align="right"
									class="blue12">
									金额参数
								</th>
								<th style="width: 100px; height: 25px;" align="right"
									class="blue12">
									整形参数
								</th>
								<th style="width: 100px; height: 25px;" align="right"
									class="blue12">
									日期参数
								</th>
								<th style="width: 100px; height: 25px;" align="right"
									class="blue12">
									参数说明
								</th>
								<th align="left" class="f66">
								操作
								</th>
							</tr>
							<s:set name="counts" value="#request.pageNum"/>
							<s:iterator value="pageBean.page" var="bean" status="s">
							<tr  class="gvItem">
								<td style="width: 100px; height: 25px;" align="right" class="blue12">
									<input type="text" id="bizValue${id }" value="${bizValue}" />
								</td>
								<td style="width: 100px; height: 25px;" align="right" class="blue12">
									<input type="text" id="bakValue${id }" value="${bakValue}" size="50"/>
								</td>
								<td style="width: 100px; height: 25px;" align="right" class="blue12">
									<input type="text" id="feeValue${id }" value="${feeValue}" size="6"/>
								</td>
								<td style="width: 100px; height: 25px;" align="right" class="blue12">
									<input type="text" id="iValue${id }" value="${iValue}" size="6"/>
								</td>
								<td style="width: 100px; height: 25px;" align="right" class="blue12">
									<input type="text" id="dateValue${id }" value="${dateValue}" />
								</td>
								<td style="width: 100px; height: 25px;" align="right" class="blue12">
									<input type="text" id="remark${id }" value="${remark}" size="60" />
								</td>
								<td align="left" class="f66">
									<a href="#" onclick="modifyEmail('${id}');">修改</a>
								</td>
							</tr>
							</s:iterator>
							
						</table>
						
						
						<br />
						
					</div>
				</div>
			</div>
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>		
		<script>
		
		//修改电子邮件
		function modifyEmail(id){
			//window.location.href="updateParamById.mht?id="+id;
			 param['paramMap.id']=id;
		     param['paramMap.bizValue']=$('#bizValue'+id).val();
		     param['paramMap.bakValue']=$('#bakValue'+id).val();
		     param['paramMap.feeValue']=$('#feeValue'+id).val();
		     param['paramMap.iValue']=$('#iValue'+id).val();
		     param['paramMap.dateValue']=$('#dateValue'+id).val();
		     param['paramMap.remark']=$('#remark'+id).val();
	         $.dispatchPost('updateParamById.mht',param,function(data){
			     /*if(data.msg == 1){
			       alert('发送成功');
			       window.parent.window.jBox.close() ;
			     }else if(data.msg == 8){
			        alert("黑名单用户不能发生站内信");
			     }else{
			       $('.tck').html(data);
			     }*/
			     alert("修改成功。");
			     window.location.href="querySysparList.mht";
	         });

			
		}
		</script>
	</body>
</html>
