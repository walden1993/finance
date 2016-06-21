<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>体验标还款</title>
    
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="expires" content="0" />
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
        <meta http-equiv="description" content="This is my page" />
		<link rel="stylesheet" href="../script/bootstrap/css/bootstrap.css">
		<script type="text/javascript" src="../script/bootstrap/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src= "../script/bootstrap/js/bootstrap.js"></script>
		
  </head>
  
  <body>
            <div class="span6 pagination-centered" style="margin-top: 20px;">
                <div class="span6" >
					<table class=" table  table-bordered">
                    	<thead>
                        	<tr class="tab-pane">
                            	<td>标题</td>
                                <td>偿还期数</td>
                                <td>偿还利息</td>
                                <td>计划还款日期</td>
								<td>实际还款时间</td>
                                <td>操作</td>
                            </tr>
                        </thead>
                        <tbody>
                        	<s:iterator value="pageBean.page" var="bean" status="s">
                        	   <tr class="success">
                                <td>${paramMap.borrowTitle}</td>
                                <td>${bean.seq_id }</td>
                                <td>${bean.repaymentAmount}</td>
                                <td>${bean.planRepaymentDate}</td>
								<td>${bean.repaymentTime}</td>
                                <td>
                                    <s:if test="#bean.repaymentTime==null ">
                                       <a href="javascript:experienceInvest('${bean.id}','${paramMap.id}')"  >立即还款</a>
                                    </s:if>
                                    <s:else>
                                        已还款
                                    </s:else>
                                </td>
                            </tr>
                        	</s:iterator>
                        </tbody>
                    </table>
                <div>
     </div>
  </body>
  <script>
  	function experienceInvest(repayment_id,borrow_id){
  		var param = {};
		param['paramMap.repayment_id'] = repayment_id;
		param['paramMap.borrow_id'] = borrow_id;
		$.post("experienceInvest.mht",param,function(data){
			if(data==2){
				alert("参数丢失，请重新操作。");
			}else{
				alert(data.ret_msg);
				if(data.ret==1){//成功
					window.parent.history.go(0);
					window.parent.jBox.close();
				}
			}
		});
	}
	
  </script>
</html>
