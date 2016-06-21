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
							<td width="100" id="today" height="28"  class="xxk_all_a">
                                <a href="awardParamDetailInit.mht">奖品管理</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
                            <td width="100" id="today" height="28"  class="main_alll_h2">
                                <a href="clearAwardInit.mht">清空过期奖品</a>
                            </td>
                            <td width="2">
                                &nbsp;
                            </td>
						</tr>
					</table>
				</div>
			</div>
			<s:form action="clearAward.mht" enctype="multipart/form-data">
			<div >
				<table style="margin-bottom: 8px;margin-left: 20px;" cellspacing="0" cellpadding="0" border="0">
					<tbody>
					<tr>
						<td class="f66" align="left"  height="36px">
							清空类别：
						</td>
						<td class="f66" align="left"  height="36px">
						<s:radio name="paramMap.type" value="2" theme="simple" list="#{'1':'清空可用余额','2':'清空红包金额'}"></s:radio>
						</td>
					</tr>
					<tr>
						<td class="f66" align="left"  height="36px">
							清空名单：
						</td>
						<td class="f66" align="left"  height="36px">
						<s:file name="files.files" ></s:file><a style="color: red;" target="_blank" href="downModelClearAward.mht">下载模版</a>
						</td>
					</tr>
					<tr>
						<td class="f66" align="left" height="36px">
							<input type="submit" id="savePrize" name="savePrize" value="保存"  />
						</td>
					</tr>
					<tr>
						<td colspan="2" class="f66" align="left" height="36px">
							<div>
								<dl>
									<dt style="color: red;">注意：</dt>
									<dd>1.清空可用余额必须传入金额</dd>
									<dd>2.红包金额清空可以不必传入金额</dd>
									<dd>3.清空可用余额请确保金额正确，程序不做金额判断</dd>
								</dl>
							</div>
						</td>
					</tr>
					</tbody>
				</table>
			</div>
			</s:form>
		</div>
	</body>
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="../script/jbox/jquery.jBox-zh-CN.js"></script>
</html>
