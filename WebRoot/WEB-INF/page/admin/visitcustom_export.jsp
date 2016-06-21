<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
<title>批量录入回访记录</title>
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
             <s:form action="visitCustomExport.mht"  id="visitCustomExport"  enctype="multipart/form-data"  method="post">
             <table>
                <tr>
                    <td align="right"><h5>导入清单：</h5></td>
                    <td><s:file label="导入"  name="files.files"  value="导入"  ></s:file></td>
                </tr>
                <tr>
                    <td><input type="button" id="bind" value="导入"  /></td>
                    <td><input type="button" id="cacel" value="取消"/>&nbsp;&nbsp;<span>模版<a href="downVisitCustomModel.mht"   style="color: #00F;font-size: 0.83em">下载</a></span></td>
                </tr>
            </table>
             </s:form>
        </div>
		<script type="text/javascript">
			$(function(){
				$("#cacel").click(function(){
					window.parent.jBox.close();
				})
				$("#bind").click(function(){
					$("#visitCustomExport").submit();
				})
			})
		</script>
</body>
</html>