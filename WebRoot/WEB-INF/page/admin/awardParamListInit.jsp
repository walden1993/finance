<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
<link rel="stylesheet" href="../script/bootstrap/css/bootstrap.min.css">
<link href="../css/common.css" rel="stylesheet" type="text/css">
<link id="skin" rel="stylesheet" href="../css/jbox/Skins/Blue/jbox.css" />
<body>
    <div class="span6 pagination-centered" style="margin-top: 20px;">
    <div class="span6 text-right  p10" >(实际价值为0代表非自动发放奖品)<input type="button"  onclick="addAwardInfo(${id })" class="btn pr20 pl20"   value="新增"/></div>
        <div class="span6 p10">
            <table class=" table  table-bordered">
                <thead>
                    <td>编号</td>
                    <td>中奖状态</td>
                    <td>奖品名称</td>
                    <td>是否可中</td>
                    <td>中奖率</td>
                    <td>排序</td>
                    <td>中奖次数</td>
                    <td>实际价值</td>
                    <td>操作</td>
                <thead>
                <tbody id="divList">
                </tbody>
            </table>
        </div>
    </div>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>

<script type="text/javascript">
	$(function(){
	    initListInfo(param);
	});
	function initListInfo(){
 		$.dispatchPost("listAwardParam.mht",{id:${id}},initCallBack);
 	}
	
	function deleteParam(id){
		if(!confirm("确定删除?")){
			return;
		}
        $.dispatchPost("awardParamDelete.mht",{id:id},function(data){
        	initListInfo();
        });
        
    }
	
 	function initCallBack(data){
		$("#divList").html(data);
	}
	function addPrizeParam(){
		window.location.href="gotoAddAwardParam.mht";
	}
	
	function addAwardInfo(id){
		jQuery.jBox.open("iframe:addAwardInfoInit.mht?id="+id, 
            "新增奖品",
            600,
            380,{ buttons: { }}
        );
	}
</script>
</body>
</html>
