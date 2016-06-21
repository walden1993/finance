<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link rel="stylesheet" href="../script/bootstrap/css/bootstrap.css">
        <script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
        <script type="text/javascript" src= "../script/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript">
    
</script>
<body>
    <div class="span6 pagination-centered" style="margin-top: 20px;">
        <div class="span6">
                分配到：<s:select id="userId"  list="#request.map"  headerKey="" headerValue="请选择"  listValue="userName"  listKey="id"  ></s:select>
        </div> 
        <div class="control-group">
                <a class="btn btn-info"  onclick="confirmfenpei()"> 确&nbsp;&nbsp;认</a>
               <a class="btn btn-inverse"    onclick="cacel()">取&nbsp;&nbsp;消</a>
         <div >
    </div>
    <script type="text/javascript" src="../script/jquery.dispatch.js"></script>
      <script>
      
    function cacel(){
    	window.parent.jBox.close();
    }
      
    function confirmfenpei(){
        var param = {};
        param['paramMap.userId'] = $("#userId").val();
        if(isBlank(param['paramMap.userId'])){
        	alert("请选择客服人员");
        	return;
        }
        param['paramMap.id'] = '${id}';
        $.dispatchPost("borrowAskFenpei.mht",param,function(data){
            if(data==-1){
                alert("分配失败");
            }else{
                alert("分配成功");
                if(data==1){//成功
                    window.parent.history.go(0);
                    window.parent.jBox.close();
                }
            }
        });
    }
    
  </script>
</body>

