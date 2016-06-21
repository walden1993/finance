<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
<title>批量扣费</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="pragma" content="no-cache" />
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="expires" content="0" />
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
        <meta http-equiv="description" content="This is my page" />
        <link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
        <link href="../css/css.css" rel="stylesheet" type="text/css" />
        
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>

<script type="text/javascript">
    
</script>
</head>
<body>
	       <div align="center" class="red">
	       	       <s:actionerror />
	       </div>
	
        <div align="center">
             <s:form action="batchChargeBack.mht"  id="batchChargeBack"  enctype="multipart/form-data"  method="post">
             <table>
                <tr>
                    <td align="right"><h5>导入扣费清单：</h5></td>
                    <td><s:file label="导入"  name="files.files"  value="导入"  id="files" ></s:file></td>
                </tr>
                <tr>
                    <td><input type="button" id="bind" value="扣费"  /></td><%--
                    <td><input type="button" id="cacel" value="取消"/></td>
                --%></tr>
            </table>
            <s:hidden name="paramMap.sumMoney" id="sumMoney" value="0"></s:hidden>
             </s:form>
        </div>
        <div align="center">
            <span><span class="red">(请先下载充值模版)</span>模版<a href="#" onclick='javascript:downModel();' target="_blank"  style="color: #00F;font-size: 0.83em">下载</a></span>
        </div>
		<script type="text/javascript">
			 function downModel(){
				window.location.href = encodeURI(encodeURI("downBatchChargeBackModel.mht"));
			}
			$(function(){
				$("#cacel").click(function(){
					window.parent.jBox.close();
				})
				$("#bind").click(function(){
					var sumMoney=prompt("请输入扣费总额","")
					if(sumMoney<=0){
						alert("扣费总金额不能为0");
						return;
					}else{
						$("#sumMoney").val(sumMoney);
						$("#batchChargeBack").submit();
						setTimeout(function(){
							alert("扣费完毕，请查看扣费结果。");
							$("#files").val("");
						},500);
					}
				})
			})
		</script>
</body>
</html>