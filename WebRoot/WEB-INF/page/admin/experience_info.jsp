<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
<title>借款管理首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<link href="../css/admin/admin_custom_css.css" rel="stylesheet"
	type="text/css"
/>
</head>
<body>
	<div id="right">
		<div style="padding: 15px 10px 0px 10px;">
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100" id="today" class="main_alll_h2"><a
							href="queryVouchersInit.mht"
						>代金券批次列表</a></td>
						<td width="2">&nbsp;</td>
						<td width="100" id="today" height="28" class="xxk_all_a"><a
							href="experienceInit.mht"
						>代金券列表</a></td>
						<td width="2">&nbsp;</td>
						<td width="100" class="white12"></td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</div>
			<div
				style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1160px; padding-top: 10px; background-color: #fff;"
			>
				<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
					width="100%" border="0"
				>
					<tbody>
						<tr>
							<td class="f66" align="left" width="50%" height="36px">批次号:
								<input id="batch" value="${batch}" placeholder="请先输入批次号后再查询" />&nbsp;&nbsp;
								券号: <input id="number" value="" />&nbsp;&nbsp; 用户名: <input
								id="name" value=""
							/>&nbsp;&nbsp; 使用项目: <input id="project" value="" />&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td class="f66" align="left" width="50%" height="36px">状态: <s:select
									id="state" list="#{-1:'全部',0:'未绑定',3:'已绑定',2:'已使用',1:'冻结'}"
								></s:select>&nbsp;&nbsp; 绑定时间: <input id="timeStart" class="Wdate"
								value=""
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"
							/>&nbsp;&nbsp; -- <input id="timeEnd" class="Wdate" value=""
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"
							/>&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; <input id="search"
								type="button" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;" name="search"
							/> &nbsp;&nbsp; <input id="search1" type="button"
								value="&nbsp批量绑定&nbsp;" name="search1"
							/> &nbsp;&nbsp; <input id="excel" type="button" value="导出Excel"
								name="excel"
							/></td>
						</tr>
					</tbody>
				</table>
				<span id="divList"><img src="../images/admin/load.gif"
					class="load" alt="加载中..."
				/> </span>
				<div></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script language="javascript" type="text/javascript"
	src="../My97DatePicker/WdatePicker.js"
></script>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>

<script type="text/javascript">
	$(function() {
		if ($("#batch").val() != null && $("#batch").val() != '') {
			param["paramMap.batch"] = $("#batch").val();
		}
		initListInfo(param);
		$('#search').click(function() {
			param["pageBean.pageNum"] = 1;
			param["paramMap.batch"] = $("#batch").val();
			param["paramMap.number"] = $("#number").val();
			param["paramMap.name"] = $("#name").val();
			param["paramMap.state"] = $("#state").val();
			param["paramMap.project"] = $("#project").val();
			param["paramMap.timeStart"] = $("#timeStart").val();
			param["paramMap.timeEnd"] = $("#timeEnd").val();

			if ($("#timeStart").val() > $("#timeEnd").val()) {
				alert("起止日期不能大于截止日期");
				return;
			}

			if ($("#batch").val() == null || $("#batch").val() == '') {
				alert("为防止数据量过大，请输入批次号");
				return;
			} else if (isNaN($("#batch").val())) {
				alert("请输入正确的批次号");
				return;
			}

			initListInfo(param);
		});
		$("#excel")
				.click(
						function() {
							$("#excel").attr("disabled", true);
							param["paramMap.batch"] = $("#batch").val();
							param["paramMap.number"] = $("#number").val();
							param["paramMap.name"] = $("#name").val();
							param["paramMap.state"] = $("#state").val();
							param["paramMap.project"] = $("#project").val();
							param["paramMap.timeStart"] = $("#timeStart").val();
							param["paramMap.timeEnd"] = $("#timeEnd").val();
							window.location.href = encodeURI(encodeURI("excelImportExperience.mht"))
									+ "?paramMap.batch="
									+ $("#batch").val()
									+ "&paramMap.number="
									+ $("#number").val()
									+ "&paramMap.name="
									+ $("#name").val()
									+ "&paramMap.state="
									+ $("#state").val()
									+ "&paramMap.project="
									+ $("#project").val()
									+ "&paramMap.timeStart="
									+ $("#timeStart").val()
									+ "&paramMap.timeEnd=" + $("#timeEnd").val();
							setTimeout("test()", 4000);
						});
		
		$("#search1").click(function(){
			var url= "experienceBindManyInit.mht?batchId="+$("#batch").val();
            $.jBox("iframe:"+url, {
            title: "批量绑定体验券",
            width: 400,
            height: 200,
            buttons: { '关闭': false }
        });
		})
	});
	function test() {
		$("#excel").attr("disabled", false);
	}
	function initListInfo(praData) {
		$.post("experienceList.mht", praData, initCallBack);
	}
	function initCallBack(data) {
		$("#divList").html(data);
	}
</script>
</html>
