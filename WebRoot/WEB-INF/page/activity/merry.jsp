<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本12月圣诞活动专题</title>
<jsp:include page="/include/head_gywm.jsp"></jsp:include>
<style type="text/css">
/*top_bar*/
html, body, div, p, h1, h2, h3, h4, h5, h6, blockquote,ol, ul, li, dl, dt, dd, form, fieldset, legend, button, input, textarea, pre, code, th, td{margin:0; padding:0;font-family:"Microsoft Yahei";font-family:"微软雅黑";}img{border:none;}
ul, ol, li{list-style:none;}
button, input, select, textarea{color:#333; font-family:"Microsoft Yahei";}
a{outline:none;text-decoration:none;color:#333;}
a:active{noOutline:expression(this.onFocus=this.blur());}
:focus{outline:0;}
a:hover{color:#ff6666;text-decoration:none;}
.clearfix:after {content:"\200B";display:block;height:0;clear:both;}
.clearfix {*zoom:1;}
.clear{clear:both;line-height:0;}
.fr{float:right;}.fl{float:left;}.red{color:#ff000a;}.tc{ text-align:center;}.f24{ font-size:20px;}
.mr5{ margin-right:5px;}.mt40{ margin-top:40px;}.mt10{margin-top:10px;}.mt60{margin-top:60px;}.mb20{margin-bottom:30px;}
.top_bar .nav li{float:left;color:#a7a7a7;width:80px; margin-left:10px;}
.w998{width:998px; margin:0 auto;}
.banner{background: url(activity/20141225/images/mey_pic02.jpg) no-repeat center center;width:100%; display:block; height:507px;}
.mey_containt{background:url(activity/20141225/images/mey_bj.jpg) repeat; width:100%;height:auto;padding:20px 0;}
.running_info{background:url(activity/20141225/images/mey_pic01.jpg) no-repeat; width:998px;height:644px;}
.mey_btn{padding:480px 0 0 120px;}
.w1000{background:#fff;width:998px; margin:0 auto;border:1px solid #be2835;border-top:none;}
.float_block{ position:fixed; left:50%; margin-left:520px; top:450px;_position: absolute; _top:expression(150px+((e=document.documentElement.scrollTop)?e:document.body.scrollTop)+'px');}
.info{padding:30px 35px;}.w382{width:382px; margin:160px 30px 0 0;} h2{color:#d0211c; margin-bottom:8px;}
.ranking{background:#fffbe8;border:1px solid #fae6c0;}
.ranking li{ line-height:42px;height:42px;border-bottom:1px dashed #99978b;float:left;width:100%;}
.ranking li div{float:left; margin:0 8px; text-align:center;}.list_one{width:60px;}.list_two{width:130px;}.list_three{width:120px;}
.ranking li.tit{ background:url(activity/20141225/images/nov_37.png) repeat-x top left; width:100%;height:52px; line-height:52px;color:#fff;font-size:18px;border-bottom:1px solid #fae5bd;}
.ranking li .nub{display:block; border-radius:8px;background:#cfc4aa;height:30px;width:30px; line-height:30px;margin:6px 0 0 18px;color:#fff;}
.ranking li .nub_one{background:#ff1800;}.ranking li .nub_two{background:#f0c400;}.ranking li .nub_three{background:#ffa188;}
.population{ margin-top:15px;font-size:18px;}.ml5{ margin-left:5px;}
.explain{background:#fff;padding:20px 0 20px 0;width:998px;margin:0px auto 0px auto;border:1px solid #be2835;}
.explain .tit img{margin:0; padding:0;}
.explain .info{padding:40px 0px 0px 18px;}
.explain .info p{ line-height:30px;color:#333;}
.notice{font-size:18px; margin-top:20px;padding-bottom:25px;}
.notice .title{background:url(activity/20141225/images/gq_07.jpg) no-repeat; display:block;width:54px;height:55px;float:left; text-align:center;color:#fff;float:left; margin-right:15px; line-height:24px;padding-top:2px;}
.footer{margin-top:25px;padding-bottom:25px;line-height:30px;}
.titinfo{ background:url(activity/20141105/images/nov_37.png) repeat-x top left; width:100%;height:52px; line-height:52px;color:#fff;font-size:18px;border-bottom:1px solid #fae5bd;}
.titinfo div{float:left;margin:0 8px; text-align:center;}
#sItem{height:215px;overflow:hidden;list-style:none;}
</style>
</head>
<body>
<jsp:include page="/include/top.jsp"></jsp:include> 
<div class="banner"></div>
<div class="mey_containt">
   <div class="w998 clearfix"><img src="activity/20141225/images/mey_pic03.jpg" width="998"  class="mb20"/></div>
   
  <div class="w998 running_info clearfix">
      <div class="fl mey_btn"><a href="#001"><img src="activity/20141225/images/nov_26.png" width="268" height="58" /></a></div>
      <div class="fr w382 clearfix">
            <h2 class="clearfix" style="color:#d0211c; margin-bottom:8px;">邀请的好友</h2>
            <div class="titinfo">
                     <div class="list_one">序号</div>
                     <div class="list_two">用户名</div>
                     <div class="list_three">好友邀请人数</div>
              </div>
            <ul class="ranking clearfix"  id="sItem">
                <s:iterator var="bean" value="#request.myvalidList" status="sta">
                    <li>
                    <s:if test="#sta.index==0">
                         <div class="list_one"><span class="nub_one nub">${sta.index+1}</span></div>
                    </s:if>
                    <s:elseif test="#sta.index==1">
                             <div class="list_one"><span class="nub_two nub">${sta.index+1}</span></div>
                    </s:elseif>
                    <s:elseif test="#sta.index==2">
                          <div class="list_one"><span class="nub_three nub">${sta.index+1}</span></div>
                    </s:elseif>
                    <s:else>
                         <div class="list_one"><span class="nub">${sta.index+1}</span></div>
                    </s:else>
                        <div class="list_two">${bean.username}</div>
                        <div class="list_three">${bean.ct}人</div>
                   </li>       
                </s:iterator>        
            </ul>
            
            <p class="population">我的有效邀请人数
            <s:if test="#session.user==null">
                   <a href="login.mht" class="red ml5">未登录</a>
            </s:if>
            <s:else>
                    <span class="red ml5">
                            ${myCt}人
                    </span>
            </s:else>
            </p>
          </div>
  </div>
  
  <div class="explain mt30">
          <a name="001" id="#001"><div class="tit"><img src="activity/20141225/images/nov_51.jpg"/></div></a>
    <div class="info"> 
              <p class="f24 mt10">1、登录后，在平台左上角，记下自己的用户名</p>
              <p class="tc"><img src="activity/20141225/images/gq_pic05.jpg" width="855" height="475" /></p>
              <p class="f24">2、将您的【用户名】发给好友，请他注册时填写</p>
              <p class="tc"><img src="activity/20141225/images/gq_pic06.jpg"/></p>
              <p class="f24">3、您还可以进入会员中心，复制邀请链接发送给好友，请他通过此链接注册</p>
              <p class="tc"><img src="activity/20141225/images/gq_pic07.png"/></p>
          </div>
  </div>
</div>

<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
	$(document).ready(function(){
		if($("#sItem>li").length>5){
			marqueen('sItem').start(40/*速度默认100*/);
		}
	});
</script>

 <script type=text/javascript >
 
 (function(A){
	   function _ROLL(obj){
	      this.ele = document.getElementById(obj);
	      this.interval = false;
	      this.currentNode = 0;
	      this.passNode = 0;
	      this.speed = 100;
	      this.childs = _childs(this.ele);
	      this.childHeight = parseInt(_style(this.childs[0])['height']);
	          addEvent(this.ele,'mouseover',function(){
	                                               window._loveYR.pause();
	                                            });
	          addEvent(this.ele,'mouseout',function(){
	                                               window._loveYR.start(_loveYR.speed);
	                                            });
	   }
	   function _style(obj){
	     return obj.currentStyle || document.defaultView.getComputedStyle(obj,null);
	   }
	   function _childs(obj){
	      var childs = [];
	      for(var i=0;i<obj.childNodes.length;i++){
	         var _this = obj.childNodes[i];
	         if(_this.nodeType===1){
	            childs.push(_this);
	         }
	      }   
	      return childs;
	   }
	    function addEvent(elem,evt,func){
	       if(-[1,]){
	           elem.addEventListener(evt,func,false);   
	       }else{
	           elem.attachEvent('on'+evt,func);
	       };
	    }
	    function innerest(elem){
	      var c = elem;
	      while(c.childNodes.item(0).nodeType==1){
	          c = c.childNodes.item(0);
	      }
	      return c;
	    }
	   _ROLL.prototype = {
	      start:function(s){
	              var _this = this;
	              _this.speed = s || 100;
	              _this.interval = setInterval(function(){
	                            _this.ele.scrollTop += 1;
	                            _this.passNode++;
	                            if(_this.passNode%_this.childHeight==0){
	                                  var o = _this.childs[_this.currentNode] || _this.childs[0];
	                                  _this.currentNode<(_this.childs.length-1)?_this.currentNode++:_this.currentNode=0;
	                                  _this.passNode = 0;
	                                  _this.ele.scrollTop = 0;
	                                  _this.ele.appendChild(o);
	                            }
	                          },_this.speed);
	      },
	      pause:function(){
	         var _this = this;
	         clearInterval(_this.interval);
	      }
	   }
	    A.marqueen = function(obj){A._loveYR = new _ROLL(obj); return A._loveYR;}
	})(window);
	
	</script>
</body>
</html>
