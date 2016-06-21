<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags"  prefix="s"%>

<script type="text/javascript">
    $(function(){
        param['paramMap.mailStatus'] = -1;
        param["pageBean.pageNum"] = 1;
	    initListInfo(param);
	 });
	 
	 function initListInfo(praData){
		$.dispatchPost("querySysMails.mht",praData,initCallBack);
	}
	function initCallBack(data){
		$("#sys_mail_list").html(data);
	}
	
    function showSysMailInfo(id,type){
		 $("#srh").hide();
		 $("#biaoge").hide();
		 $("#show_mail").show();
		 var curPage = $("#curPage").val();
		
	    $.post("queryEmailById.mht?mailId="+id+"&type="+type+"&curPage="+curPage,function(da){
		     $("#show_mail").html(da);
		});
     }
     
     
     function delSys(){
 		$("#delSys").attr("disabled",true);
 		var stIdArray = [];
		$("input[name='sysMail']:checked").each(function(){
			stIdArray.push($(this).val());
		});
		if(stIdArray.length == 0){
			alert("请选择需要删除的内容");
			$("#delSys").attr("disabled",false);
			return ;
		}
		if(!window.confirm("确定要删除吗?")){
		  $("#delSys").attr("disabled",false);
 			return;
 		}
		var ids = stIdArray.join(",");
		$.dispatchPost("deleteSysMails.mht",{ids:ids},function(data){
           $("#sys_mail_list").html(data);
           $("#delSys").attr("disabled",false);
        });
	}
	
	function readedSys(){
	     $("#readedSys").attr("disabled",true);	
 		var stIdArray = [];
		$("input[name='sysMail']:checked").each(function(){
			stIdArray.push($(this).val());
		});
		if(stIdArray.length == 0){
			alert("请选择要标记的内容");
			$("#readedSys").attr("disabled",false);
			return ;
		}
		if(!window.confirm("确定要将所选邮件标记为已读吗?")){
 			 $("#readedSys").attr("disabled",false);
 			return;
 		}
		var ids = stIdArray.join(",");
		param["paramMap.ids"] = ids;
		param["paramMap.type"]="readed";
		$.dispatchPost("updateSys2Readed.mht",param,function(data){
           $("#sys_mail_list").html(data);
           $("#readedSys").attr("disabled",false);
        });
	}
	
	
	function unReadSys(){
	    $("#unReadSys").attr("disabled",true);
 		var stIdArray = [];
		$("input[name='sysMail']:checked").each(function(){
			stIdArray.push($(this).val());
		});
		if(stIdArray.length == 0){
			alert("请选择要标记的内容");
			$("#unReadSys").attr("disabled",false);
			return ;
		}
		if(!window.confirm("确定要将所选邮件标记为未读吗?")){
 	    	$("#unReadSys").attr("disabled",false);
 			return;
 		}
		var ids = stIdArray.join(",");
		param["paramMap.ids"] = ids;
		param["paramMap.type"]="unread";
		$.dispatchPost("updateSys2UNReaded.mht",param,function(data){
           $("#sys_mail_list").html(data);
           $("#unReadSys").attr("disabled",false);
        });
	}
	
	function showUnReadSys(){
	  param['paramMap.mailStatus'] = 1;
	  param["pageBean.pageNum"] = 1;
	  initListInfo(param);
	}
	
	function returnToPage_s(pNum){
	  $("#show_mail").hide();
	  $("#srh").show();
	  $("#biaoge").show();
	   param['paramMap.pageNum'] = pNum;
	   initListInfo(param);
	}
</script>

<div class="boxmain2">
          <div class="srh" id="srh">
        <input class="scbtn" onclick="delSys();" value="删除" type="button" id="delSys"/>
        <input class="scbtn" onclick="readedSys();" value="标记为已读" type="button" id="readedSys"/>
        <input class="scbtn" onclick="unReadSys();" value="标记为未读" type="button" id="unReadSys"/>
        <a href="javascript:void(0);" class="scbtn" onclick="showUnReadSys();">未读邮件</a>
        </div>
        <span id="sys_mail_list"></span>
           <span id="show_mail"></span> 
    </div>