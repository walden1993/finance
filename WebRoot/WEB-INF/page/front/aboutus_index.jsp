<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/index.css" />

<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" />
<link rel="stylesheet" href="css/lightbox.css" type="text/css" media="screen" />
<jsp:include page="/include/head.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="css/new_inside.css" />   
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>	

<div class="n_about_top clearfix">
	<div class="n_about_top">
	  <div class="w1002 location clearfix"><a href="<%=application.getAttribute("basePath")%>">首页</a> &gt; 关于我们</div>
	  <div class="w1002 anchor clearfix">
	  <ul class="n_about_menu">
	       <li id="aboutower1" class="on" ><a href="javascript:loading(typeid)" >关于我们</a></li>
	       <li id="communique2"><a href="javascript:loading(typeid)" >网站公告</a></li>
	       <li ><a href="javascript:loading(typeid)" >平台资讯</a></li>
	       <li id="teamwork4"><a href="javascript:loading(typeid)" >合作伙伴</a></li>
	       <li id="touchus5"><a href="javascript:loading(typeid)" >联系我们</a></li>
	       <li><a href="javascript:loading(typeid)" >帮助中心</a></li>
	   </ul>
	   </div>
	</div>
</div>

<div id="container">

<!--介绍-->
<div class="n_gray_bj">
   <div class="w1002 pt20 pb20 fang"><h1>关于我们</h1></div>
   <div class="w1002 pb20  fang"><h2>合作机构</h2>
   </div>
  <div class="w1002 n_about_partner clearfix">
    <dl class="clearfix">
       <dt><a href="#"><img src="images/abt_pic16.png" width="185" height="81" /></a></dt>
       <dd>
       <p class="part_tit"><a href="#">深圳市华融融资担保有限公司</a></p>
       <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;深圳市华融投资担保有限公司是深圳市经贸局于2002年8月批准成立的综合性金融投资担保公司。公司资金实力雄厚，业务门类齐全，先后获得“全国万亿担保规模上榜机构30强”、“中国十大AAA级信用担保机构”和“中国最佳风险管理金奖”等荣誉，在企业界和金融界享有较高知名度和美誉度。</p></dd>
     </dl>
    <dl class="clearfix">
       <dt><a href="#"><img src="images/abt_pic16.png" width="185" height="81" /></a></dt>
       <dd>
       <p class="part_tit"><a href="#">深圳市创新投资发展有限公司</a></p>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其母公司深圳市创新投资集团于2002年10月正式成立，母公司是以资本为主要联结纽带的母子公司为主体的大型投资企业集团，集团核心企业——深圳市创新投资集团有限公司的前身为1999年8月26日成立的深圳市创新科技投资有限公司。目前已发展成为国内实力最强、影响力最大的本土创业投资机构。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创新资本以成为具有国际竞争力的创业投资集团，跻身国际知名创业投资机构为目标，为股东实现价值最大化；为员工提供前景广阔的职业发展空间；探索具有中国特色的创业投资事业发展之路；为中国创业投资发展环境的改善做出贡献。
</dd>
     </dl>
     <dl class="clearfix">
       <dt><a href="#"><img src="images/abt_pic16.png" width="185" height="81" /></a></dt>
       <dd>
       <p class="part_tit"><a href="#">中国工商银行</a></p>
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中国工商银行股份有限公司成立于1984年，是中国最大的国有独资商业银行，世界五百强企业之一，列中国五大银行之首，拥有中国最大的客户群。</dd>
     </dl>
     <dl class="clearfix">
       <dt><a href="#"><img src="images/abt_pic16.png" width="185" height="81" /></a></dt>
       <dd>
       <p class="part_tit"><a href="#">深圳市华联发展投资有限公司</a></p>
       深圳市华联发展投资有限公司是华联发展集团旗下的金融投资公司。华联发展集团是以房地产开发为主业、高新技术和创业投资为辅业、资产逾百亿的股权多元化大型企业集团，是国家520户重点企业之一，目前拥有十多家全资、控股企业及“华联控股”、“深中冠”两家上市公司，产业主要分布在深圳、浙江和上海三地。</dd>
     </dl>
   </div>
  
</div>

<!--友情链接-->
<div class="w1002  n_about_mission clearfix">
  <div class="pb20 fang"><h2>友情链接</h2></div>
   <ol class="n_about_link w1002">
     <li class="n_about_link li"><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
     <li><img src="images/abt_pic17.jpg" width="185" height="81" /><p><a href="#">网贷110</a></p></li>
   </ol>
</div>
</div>


<div id="showcontent1" class="w1002 n_about_mission clearfix"  style="display: none">
    <div >
        ${paramMap.content}
    </div>
    <div class="fr"><img src="images/abt_pic02.jpg" width="437" height="371" /></div>
</div>

<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="js/js.js"></script>
<script type="text/javascript">jQuery(".tabsList").slide({ titCell:".tit",triggerTime:0 });</script>
<script>

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
       var str = url.substr(1);
       strs = str.split("&");
       for(var i = 0; i < strs.length; i ++) {
          theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
       }
    }
    return theRequest;
 }

$(function(){
 var typeId = parseInt(GetRequest()['typeId']);//当前打开的
 loading(typeId)
)}

function loading(typeid){
	 param["typeId"] = typeid;
	 $.dispatchPost("getMessageBytypeId.mht", param, function(data) {
	      $("#container").html(data)
	 });
}

<%--
$(function(){
	$("#showcontent1").html("");
	var adf = "${typeId}";
	if (adf == "7"){
		touchUs5();
	}
     //样式选中
	//联系我们 
	 $("#touchus5").click(touchUs5);

		//关于我们
		$("#aboutower1").click(function(){
			removeClassOn();
			$("#aboutUsAll").css({"display":"block"});
			$("#aboutower1").addClass('on');
			$("#showcontent1").html("");
		});

			//资费说明
		$("#costdesc").click(function(){
			
		});


			//招贤纳士
		$("#invite").click(function(){
			
		});

		//合作伙伴
		$("#teamwork4").click(function(){
			removeClassOn();
			$("#teamwork4").addClass('on');
			$.post("queryMessageDetail.mht","typeId=11",function(data){
		        $("#showcontent1").html("");
		        $("#showcontent1").html("<div class='w1002 n_about_mission clearfix'><div class='content_bar'></div>"+
		             "<div class='content_text'><p class='zw'>"+data.content+"</p></div></div>");
		        $("#columName1").html("合作伙伴");
		    });
		});

		//友情链接
		$("#links").click(function(){
			
		});

		//媒体报道
		$("#mediaReport").click(function(){
			
		});

		//官方公告
		$("#communique2").click(function(){
			removeClassOn();
			$("#communique2").addClass('on');
			param["pageBean.pageNum"] = 1; 
			 queryGfgg(param);
		});
	
		
	
});
function touchUs5(){
	 removeClassOn();
	 $("#touchus5").addClass('on');
	 $.post("queryMessageDetail.mht","typeId=7",function(data){
		 removeClassOn();
		 $("#touchus5").addClass('on');
        $("#showcontent1").html("");
        $("#showcontent1").html("<div class='w1002 n_about_mission clearfix'><div class='content_bar'></div>"+
                "<div class='content_text'><p class='zw'>"+data.content+"</p></div></div>");
        $("#columName1").html("联系我们 ");
   });
 }
function removeClassOn(){
	$("#aboutUsAll").css({"display":"none"});
	$("#aboutower1").removeClass('on');
	$("#communique2").removeClass('on');
	$("#teamwork4").removeClass('on');
	$("#touchus5").removeClass('on');
}
function communique2(){
	$("#aboutUsAll").css({"display":"none"});
	alert(12);
	
}
function doMtbdJumpPage(i){
		if(isNaN(i)){
			alert("输入格式不正确!");
			return;
		}
		$("#pageNum").val(i);
		param["pageBean.pageNum"]=i;
		//回调页面方法
		queryMtbd(param);
	}
	function queryMtbd(parDate){
		 $.post("queryMediaReportListPage.mht",parDate,function(data){
	         $("#showcontent1").html("");
	         $("#showcontent1").html(data);
	    });
	}

	function doqueryGfggJumpPage(i){
		if(isNaN(i)){
			alert("输入格式不正确!");
			return;
		}
		$("#pageNum").val(i);
		param["pageBean.pageNum"]=i;
		//回调页面方法
		queryGfgg(param);
	}
	function queryGfgg(parDate){
		 $.post("queryNewsListPage.mht",parDate,function(data){
	         $("#showcontent1").html("");
	         $("#showcontent1").html(data);
	         $("#columName1").html("网站公告");
	         
	    });
	}
--%></script>
</body>
</html>
