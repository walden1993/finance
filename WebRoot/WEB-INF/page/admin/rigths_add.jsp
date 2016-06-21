<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>添加管理组</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../script/autoComplete/jquery.autocomplete.css"
	rel="stylesheet" type="text/css"
/>
<script type="text/javascript" src="../script/jquery-2.0.js">
	
</script>
<script type="text/javascript" src="../script/jquery.dispatch.js">
	
</script>
<script src="../script/autoComplete/jquery.autocomplete.min.js"></script>
<script type="text/javascript">
	var iscArr = [];
	iscArr.push({
		k:-1,
		v:'导航菜单'
	})
	$(function() {
		var iscObj = $("#roleId");
		var iscObjOption = iscObj.find("option").each(function(index, element) {
			var _this = $(this);
			if (_this.attr("value") != "0") {
				iscArr.push({
					k : _this.attr("value"),
					v : _this.html()
				});
			} else {
				//_this.remove();   
			}
		});
		$("#roleIdstr").autocomplete(iscArr, {
			max : 50, //列表里的条目数
			minChars : 0, //自动完成激活之前填入的最小字符
			width : 150, //提示的宽度，溢出隐藏
			scrollHeight : 300, //提示的高度，溢出显示滚动条
			matchContains : true, //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示
			autoFill : true, //自动填充
			formatItem : function(row, i, max) {
				return row.v;
			},
			formatMatch : function(row, i, max) {
				return row.k + row.v;
			},
			formatResult : function(row) {
				return row.v;
			}
		}).result(function(event, row, formatted) {
			$("#parentID").val(row.k)
		});
		
		$("#btn").click(function(){
			
			if($("#name").val()==null ||$("#name").val()==''){
                alert("请填写功能名称")
                return;
            }
			if($("#action").val()==null ||$("#action").val()==''){
                alert("请填写功能action")
                return;
            }
			if($("#roleIdstr").val()==null ||$("#roleIdstr").val()==''){
                $("#parentID").val(null)
                alert("请选择父级功能")
                return;
            }
			if($("#indexs").val()!=null && isNaN($("#indexs").val())){
                alert("请填写正确的排序")
                return;
			}else{
				$("#indexs").val(0)
			}
			if($("#summary").val()==null || $("#summary").val()==''){
				$("#summary").val($("#name").val());
			}
			if($("#description").val()==null || $("#description").val()==''){
                $("#description").val($("#name").val());
            }
			param["paramMap.name"] = $("#name").val();
			param["paramMap.action"] = $("#action").val();
			param["paramMap.parentID"] = $("#parentID").val();
			param["paramMap.indexs"] = $("#indexs").val();
			param["paramMap.summary"] = $("#summary").val();
			param["paramMap.description"] = $("#description").val();
			param["paramMap.isLog"] = $("#isLog").val();
			param["paramMap.isIntercept"] = $("#isIntercept").val();
			param["paramMap.isQuery"] = $("#isQuery").val();
			//$.post("addRightMethod.mht",param,function(data){
				//if(data==1){
					//alert("新增功能成功");
					$("#addRightMethod").submit();
				//}else{
				//	alert("新增功能失败")
				//}
			//})
		})
	})
</script>
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="28" class="xxk_all_a"><a href="queryRoleList.mht">管理组列表</a>
						</td>
						<td width="2">&nbsp;</td>
						<td width="100" class="xxk_all_a"><a href="addRoleInit.mht">添加管理组</a>
						</td>
						<td width="2">&nbsp;</td>
						<td width="100" height="28" class="xxk_all_a"><a
							href="queryAdminInit.mht"
						>管理员列表</a>
						</td>
						<td width="2">&nbsp;</td>
						<td width="100" class="xxk_all_a"><a href="addAdminInit.mht">添加管理员</a>
						</td>
						<td>&nbsp;</td>
						<td width="100" class="main_alll_h2"><a href="addRights.mht">添加功能</a>
						</td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>
			<s:form action="addRightMethod.mht" id="addRightMethod" >
				<div style="width: auto; background-color: #FFF; padding: 10px;">
					<table width="100%" border="0" cellspacing="1" cellpadding="3">
						<tr>
							<td style="height: 25px;" align="right" class="blue12">
								功能名称：</td>
							<td align="left" class="f66"><s:textfield  id="name"
									name="paramMap.name" theme="simple" cssClass="in_text_2" maxlength="100"
									cssStyle="width: 150px"
								>
								</s:textfield> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.name"
									/> </span>
							</td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right"
								class="blue12"
							>功能action名称：</td>
							<td align="left" class="f66"><s:textfield  id="action"
									name="paramMap.action" theme="simple" cssClass="in_text_2"
									cssStyle="width: 150px"  maxlength="100"
								>
								</s:textfield> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.action"
									/> </span>
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">
								功能描述：</td>
							<td align="left" class="f66"><s:textarea rows="4"   maxlength="200" id="description"
									name="paramMap.description" theme="simple"
								/><span style="color: red;"><s:fielderror
										fieldName="paramMap.description"
									/> </span>
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">
								是否记录日志：</td>
							<td align="left" class="f66"><s:radio list="#{1:'是',2:'否' }"  id="isLog"
									name="paramMap.isLog" value="1"
								>
								</s:radio> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.isLog"
									/> </span>
							</td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right"
								class="blue12"
							>是否权限拦截：</td>
							<td align="left" class="f66"><s:radio list="#{1:'是',2:'否' }" id="isIntercept"
									name="paramMap.isIntercept" value="1"
								>
								</s:radio> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.isIntercept"
									/> </span>
							</td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right"
								class="blue12"
							>简介：</td>
							<td align="left" class="f66"><s:textfield  id="summary"
									name="paramMap.summary" theme="simple" cssClass="in_text_2"  maxlength="50"
									cssStyle="width: 150px"
								>
								</s:textfield> <span style="color: red;"><s:fielderror
										fieldName="paramMap.summary"
									/> </span>
							</td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right"
								class="blue12"
							>父级功能：</td>
							<td align="left" class="f66"><s:textfield id="roleIdstr">
								</s:textfield><s:hidden id="parentID" name="paramMap.parentID"></s:hidden> <i style="display: none;"> <s:select id="roleId"
										list="#session.list" listKey="id" listValue="summary"
										headerKey="null" headerValue="--请选择--"
									>
									</s:select> </i> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.roleId"
									/> </span>
							</td>
						</tr>
						<tr>
							<td style="height: 25px;" align="right" class="blue12">
								是否查询：</td>
							<td align="left" class="f66"><s:radio list="#{1:'是',2:'否' }" id="isQuery"
									name="paramMap.isQuery" value="1"
								>
								</s:radio> <span style="color: red;">*<s:fielderror
										fieldName="paramMap.isQuery"
									/> </span>
							</td>
						</tr>
						<tr>
							<td style="width: 100px; height: 25px;" align="right"
								class="blue12"
							>排序：</td>
							<td align="left" class="f66"><s:textfield maxlength="10" id="indexs"
									name="paramMap.indexs" theme="simple" cssClass="in_text_2"
									cssStyle="width: 150px"
								>
								</s:textfield> <span style="color: red;"><s:fielderror
										fieldName="paramMap.indexs"
									/> </span>
							</td>
						</tr>
						<tr>
							<td height="36" align="right" class="blue12">&nbsp;</td>
							<td>
								<button type="button"  id="btn"
									style="background-image: url('../images/admin/btn_queding.jpg');width: 70px;border: 0;height: 26px"
								></button> &nbsp;
								<button type="reset"
									style="background-image: url('../images/admin/btn_chongtian.jpg');width: 70px;height: 26px;border: 0px"
								></button>&nbsp; &nbsp;<span style="color: red;"> <s:fielderror
										fieldName="actionMsg" theme="simple"
									>
									</s:fielderror> </span>
							</td>
						</tr>
					</table>
					<br />
				</div>
			</s:form>
		</div>
	</div>
</body>
</html>
