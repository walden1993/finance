<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>用户统计首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="today" height="28"  class="xxk_all_a">
								<a href="allUserStatics.mht">总报表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="tomorrow"  class="xxk_all_a">
								<a href="monthUserStatics.mht">平台月报表</a>
							</td>
							<td>
								<a href="alldayStatics.mht" class="main_alll_h2">平台日统计</a>
							</td>
							</tr>
					</table>
					
					
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					
		            <!-- span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>  -->
		          <!-- div align="right">  <input type="button" id="excelExport" value="导出" /> </div> -->

<%
java.util.Calendar c = java.util.Calendar.getInstance();
String m1= new java.text.SimpleDateFormat("yyyyMM").format(c.getTime());
c.add(java.util.Calendar.MONTH, -1);
String m2= new java.text.SimpleDateFormat("yyyyMM").format(c.getTime());
c.add(java.util.Calendar.MONTH, -1);
String m3= new java.text.SimpleDateFormat("yyyyMM").format(c.getTime());
c.add(java.util.Calendar.MONTH, -1);
String m4= new java.text.SimpleDateFormat("yyyyMM").format(c.getTime());
%>
	<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
		width="100%" border="0">
		<tbody>
			<tr>
				<td class="f66" align="left" width="50%" height="36px">
					月份:
					<select id="monthsel"  >
					</select>&nbsp;&nbsp; 
					日期:
					<input id="dateStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
					--
					<input id="dateEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp; 
					PV：
					<input id="pvs"  value="" size="5"/>&nbsp;&nbsp;
					--
					<input id="pve" value=""  size="8"/>&nbsp;&nbsp;
					UV：
					<input id="uvs"  value="" size="5"/>&nbsp;&nbsp;
					--
					<input id="uve" value=""  size="8"/>&nbsp;&nbsp;
					<input id="queryBtn" type="button" value=" 查询 " name="queryBtn" />&nbsp;&nbsp;
				    <input id="excelExport" type="button" value=" 导出 " name="excelExport" /> 
				</td>
				
			</tr>
			
		</tbody>
	</table>

</div>
<span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>

			</div>
		</div>
	</body>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		addMonthSelect();
		$('#excelExport').click(function(){
		   param["pageBean.pageNum"]=1;
		   exportInfo(param);
		});
		$('#queryBtn').click(function(){
		   param["pageBean.pageNum"]=1;
		   initListInfo(param);
		});
		$("#divList").html("");
	});
	function initListInfo(praData){
		praData["paramMap.monthsel"] = $("#monthsel").val();
		praData["paramMap.dateStart"] = $("#dateStart").val();
		praData["paramMap.dateEnd"] = $("#dateEnd").val();
		praData["paramMap.pvs"] = $("#pvs").val();
		praData["paramMap.pve"] = $("#pve").val();
		praData["paramMap.uvs"] = $("#uvs").val();
		praData["paramMap.uve"] = $("#uve").val();
		
		if ($("#monthsel").val() == "" && $("#dateStart").val()=="" && $("#dateEnd").val()==""
			&& $("#pvs").val()== "" && $("#pve").val()== "" && $("#uvs").val()== "" && $("#uve").val()== ""){
			alert("请至少选择一个条件");
			return ;
		}
		
 		$.dispatchPost("alldayStaticsList.mht",praData,initCallBack);
 	}
	function exportInfo(praData){
		var monthsel = $('#monthsel').val();
		var dateStart = $('#dateStart').val();
		var dateEnd = $('#dateEnd').val();

		var pvs = $('#pvs').val();
		var pve = $('#pve').val();
		var uvs = $('#uvs').val();
		var uve = $('#uve').val();
		window.location.href="alldayStaticsExcel.mht?monthsel="+monthsel+
		"&dateStart="+dateStart+"&dateEnd="+dateEnd+"&pvs="+pvs+"&pve="+pve+"&uvs="+uvs+"&uve="+uve;
	    setTimeout("test()",3000);
 	}
 	
 	function initCallBack(data){
		if (data == "1"){
			alert("月份/日期选择不合理");
			return;
		}
 	 	
		$("#divList").html(data);
	}
 	function test(){
	   $("#excelExport").attr("disabled",false);
	}
	//isEmployee
	function addMonthSelect(){
		var m1 = '<%=m1%>';
		var m2 = '<%=m2%>';
		var m3 = '<%=m3%>';
		var m4 = '<%=m4%>';
		var smonth='${smonth}';
		var options = "<option value=''>全部</option>";
		
		options +=  "<option value='"+m1+"'>"+m1+"</option>";
		options +=  "<option value='"+m2+"'>"+m2+"</option>";
		options +=  "<option value='"+m3+"'>"+m3+"</option>";
		options +=  "<option value='"+m4+"'>"+m4+"</option>";
		$('#monthsel').html(options);
		$("#monthsel").val(smonth);
	}
	
	function queryNewInfo(){
		var smonth = $('#isEmployee').val();
		window.location.href="monthUserStatics.mht?smonth="+smonth;
	}
</script>
</html>
