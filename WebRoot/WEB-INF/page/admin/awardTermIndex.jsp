<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>抽奖管理</title>
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
					<table border="0" cellspacing="0" cellpadding="0">
						<tr >
							<td  class="main_alll_h2" >
								<a href="awardManage.mht">抽奖管理</a>
							</td>
							<td width="2">
                                &nbsp;
                            </td>
							<td class="xxk_all_a" >
                                <a href="awardParamDetailInit.mht">奖品管理</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                          <!--   <td class="xxk_all_a" >
                                <a href="clearAwardInit.mht">清空过期奖品</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td> -->
                            <td class="xxk_all_a" >
                                <a href="addHandAwardInit.mht">手动添加红包</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
						</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
	<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
		<tbody>
			<tr>
				<td class="f66" align="left" width="50%" height="36px">
					创建时间:
					<input id="dateStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>&nbsp;&nbsp;
					--
					<input id="dateEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>&nbsp;&nbsp; 
					
					<input id="queryBtn" type="button" value=" 查询 " name="queryBtn" />&nbsp;&nbsp;
				    <input id="addAward" type="button" value=" 新增抽奖 " name="addAward" /> 
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
	    param["pageBean.pageNum"] = 1;
	    initListInfo(param);
	    $("#queryBtn").click(function(){
	    	param["pageBean.pageNum"] = 1;
	    	param["paramMap.dateStart"] = $("#dateStart").val();
	        param["paramMap.dateEnd"] = $("#dateEnd").val();
	        if($("#dateStart").val() &&   $("#dateEnd").val()  &&   $("#dateStart").val()> $("#dateEnd").val()){
                alert("起止日期不能小于截止日期");
                return;
            }
	        initListInfo(param);
	    })
	    
	    $('#addAward').click(function(){
            //$.dispatchPost("addAwardTerm.mht","",initCallBack);
	    	window.location.href="addAwardTerm.mht?type=1";
        });
	});
	
	function initListInfo(param){
	   $.dispatchPost("awardManageInfo.mht",param,initCallBack);
	}
	
	function initCallBack(data){
	$("#divList").html(data);
	}
	
	function awardEdit(id){
		window.location.href="addAwardTerm.mht?type=2&id="+id+"";
	}
	
	function awardParamUser(id){
        window.location.href="awardParamUserInit.mht?termId="+id+"";
    }
	
</script>
</html>
