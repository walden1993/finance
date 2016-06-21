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
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
		<link id="skin" rel="stylesheet" href="../css/jbox/Skins/Blue/jbox.css" />
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="today" height="28"  class="xxk_all_a">
								<a href="awardManage.mht">抽奖管理</a>
							</td>
							<td width="2">
                                &nbsp;
                            </td>
							<td width="100" id="today" height="28"  class="main_alll_h2">
                                <a href="awardParamDetailInit.mht">奖品管理</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                            <!-- <td class="xxk_all_a" >
                                <a href="clearAwardInit.mht">清空过期奖品</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td> -->
						</tr>
					</table>
					
					
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">

	<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0" width="100%" border="0">
		<tbody>
			<tr>
				<td class="f66" align="left" width="50%" height="36px">
					奖品名称:
					<input id="paramName"  type="text"/>&nbsp;&nbsp;
					<input id="queryBtn" type="button" value=" 查询 " name="queryBtn" />&nbsp;&nbsp;
				    <input id="addAward" type="button" value="新增奖品" name="addAward" /> 
				</td>
				
			</tr>
			
		</tbody>
	</table>

</div>
<span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>

			</div>
		</div>
	</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
<script type="text/javascript">
	$(function(){
	    param["pageBean.pageNum"] = 1;
	    initListInfo(param);
	    $("#queryBtn").click(function(){
	    	param["pageBean.pageNum"] = 1;
	    	param["paramMap.name"] = $("#paramName").val();
	        initListInfo(param);
	    })
	    
	    $('#addAward').click(function(){
	    	jQuery.jBox.open("iframe:gotoAddAwardParam.mht", 
	                "新增奖品",
	                340,
	                340,{ buttons: { }}
	            );
        });
	});
	
	function initListInfo(param){
	   $.dispatchPost("awardParamDetail.mht",param,initCallBack);
	}
	
	function initCallBack(data){
	     $("#divList").html(data);
	}
	
	function deleteParamDetail(id){
		param["paramMap.id"] = id;
		if(confirm("确定删除?")){
			$.dispatchPost("awardParamDetailDelete.mht",param,function(data){
				if(data<0){
					alert("删除失败!")
				}else{
					alert("删除成功!")
					initListInfo(param);
				}
			});
		}
		
	}
	
	function updateParamDetail(id){
		jQuery.jBox.open("iframe:gotoAddAwardParam.mht?id="+id, 
	            "修改奖品",
	            340,
	            340,{ buttons: { }}
	        );
    }
	
</script>
</html>
