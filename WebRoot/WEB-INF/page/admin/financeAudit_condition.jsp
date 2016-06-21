<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<title>等级条件查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<link href="../script/bootstrap/css/bootstrap.min.css" rel="stylesheet"  />
<body>

	<div class="panel p10">
		<table class="table table-bordered">
			<thead class="bg-info">
				<tr>
					<td>理财师级别</td>
					<td>用户名</td>
					<td>真是姓名</td>
					<td>投资总额</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>理财师级别</td>
					<td>用户名</td>
					<td>真是姓名</td>
					<td>投资总额</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="panel-group" id="accordion">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion"
						href="#collapseOne"> 铜牌理财师条件 </a>
				</h4>
			</div>
			<div id="collapseOne" class="panel-collapse collapse in">
				<div class="panel-body">
					<dl>
						<dd>有完全民事行为能力</dd>
						<dd>认同平台的各项规章制度和风险提示</dd>
						<dd>个人投资额满1万</dd>
						<dd>个人名下有有效客户3人以上并合计投资额超3万</dd>
					</dl>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion"
						href="#collapseTwo"> 银牌理财师条件 </a>
				</h4>
			</div>
			<div id="collapseTwo" class="panel-collapse collapse">
				<div class="panel-body">
					<dl>
						<dd>有完全民事行为能力</dd>
						<dd>认同平台的各项规章制度和风险提示</dd>
						<dd>个人投资额满3万</dd>
						<dd>成功辅导过3名以上铜牌理财师</dd>
					</dl>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion"
						href="#collapseThree"> 金牌理财师条件 </a>
				</h4>
			</div>
			<div id="collapseThree" class="panel-collapse collapse">
				<div class="panel-body">
					<ul>
						<li>有完全民事行为能力</li>
						<li>认同平台的各项规章制度和风险提示</li>
						<li>个人投资额满5万</li>
						<li>成功辅导过3名银牌以上理财师</li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript"
		src="../script/bootstrap/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="../script/bootstrap/js/bootstrap.min.js"></script>

	<div>
		<div></div>
	</div>
</body>
<footer> </footer>
</html>
