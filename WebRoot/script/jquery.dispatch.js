var param = {};

/**
 * 
 * @Description 发送post请求 当有拦截器返回信息进行处理
 * @param url
 *            请求地址
 * @param param
 *            请求参数
 * @param callBack
 *            成功后回调方法
 * @Author Yang Cheng
 * @Date: 2012-2-17 18：00
 * @Version 1.0
 * 
 */ 
$.dispatchPost = function(url,param,callBack){
	url = url+"?nowTime"+new Date().getTime();
	$.post(url,param,function(data){
		if(data == "noLogin"){
			window.location.href="login.mht";
			return;
		}
		if(data=="network"){
		   window.location.href="weihui.jsp";
		  return;
		}
		if(data=="virtual"){
		   window.location.href="noPermission.mht";
		  return;
		}
		if(data == "pagejump"){
			window.location.href="adminMessage.mht";
			return;
		}
		callBack(data);
	});
}

/**
 * 获取指定参数
 * 方式一: GetRequest() 返回参数数组
 * 方式二: GetRequest(param) 返回指定的参数值
 * @param param
 * @returns
 */
function GetRequest(param) {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
       var str = url.substr(1);
       strs = str.split("&");
       for(var i = 0; i < strs.length; i ++) {
          theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
       }
    }
    return param?theRequest[param]:theRequest;
 }


/**
 * 
 * @Description 跳转页面方法
 * @param i
 *            跳转页数
 * @Author Yang Cheng
 * @Date: 2012-2-17 18：10
 * @Version 1.0
 * 
 */
function doJumpPage(i){
	// if(i==""){
	// alert("输入格式不正确12!");
	// return;
	// }
	if(isNaN(i)){
		alert("输入格式不正确!");
		return;
	}
	$("#pageNum").val(i);
	param["pageBean.pageNum"]=i;
	// 回调页面方法
	initListInfo(param);
}

function checkbox_All_Reverse(obj,itemName){
	$("input[name=" + itemName + "]").attr("checked",obj.checked);
}

// 表格隔行变色
function trEvenColor(){
	$("#tableTr tr:even").css("background-color","#f9f9f9");
}

function setCookies(cookieName,value,days){
	$.cookie(cookieName, value, { expires: days });
}
function getCookies(cookieName){
	return $.cookie(cookieName);
}

 function getFlexObject(movieName) {   
    return document[movieName];   
}

 /**
  * 空字符串判断
  * @param str
  * @returns {Boolean}
  */
function isBlank(str){
	if(str==null || str==''){
		return true;
	}
	return false;
}
 
onerror = handleErr;
var error_info;
window.debug = false;
/**
 * 异常抓取
 * @param msg
 * @param url
 * @param l
 */
function handleErr(msg, url, l){
    error_info = "本页面存在错误。\n\n";
    error_info += "错误：" + msg + "\n\n";
    error_info += "地址：" + url + "\n\n";
    error_info += "行：" + l + "\n\n";
    error_info += "点击‘确定’继续。";
    if(debug)log(error_info);
}
$(function(){
	trEvenColor();
})

/**fancybox提示框*/
function show(msg,callback){
	var options = {
		minWidth	: 200,
		minHeight	: 10,
		fitToView	: false,
		autoResize	: true,
		autoSize	: true,
		closeClick	: true,
		openEffect	: 'fade',
		closeEffect	: 'fade',
		autoCenter 	: true,
		beforeClose:callback
    };
	$.fancybox.open('<p class=\'title  text-center\'>'+msg+'</p>',options);
}

function hideAndShow(str){
	$(str).hide();
	$(str).show();
}
/**
 * 验证邮箱
 * @param email
 * @returns
 */
function validateEmail(email){
	return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(email)
}
/**
 * 验证手机号 更新时间2014-11-21 08:51:37
 * @param phone
 * @returns
 */
function validatePhone(phone){
	return /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(phone);
}

/**
 * 打印日志
 * @param str
 */
function log(str){
	console.log(str);
}


function addCookie(objName,objValue,objHours){//添加cookie
	var str = objName + "=" + escape(objValue);
	if(objHours > 0){//为0时不设定过期时间，浏览器关闭时cookie自动消失
		var date = new Date();
		var ms = objHours*3600*1000;
		date.setTime(date.getTime() + ms);
		str += "; expires=" + date.toGMTString();
	}
	document.cookie = str;
}

function getCookie(objName){//获取指定名称的cookie的值
	var arrStr = document.cookie.split("; ");
	for(var i = 0;i < arrStr.length;i ++){
		var temp = arrStr[i].split("=");
		if(temp[0] == objName) return unescape(temp[1]);
	}
}

function delCookie(name){//为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
	var date = new Date();
	date.setTime(date.getTime() - 10000);
	document.cookie = name + "=a; expires=" + date.toGMTString();
}

function isMobile(){
	if(/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))){ 
	    if(window.location.href.indexOf("?mobile")<0){ 
	        try{ 
	            if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)){ //手机
	                return true;
	            }else if(/iPad/i.test(navigator.userAgent)){ //ipad
	            	return true;
	            }else{ //电脑
	               return false;
	            } 
	        }catch(e){return false} 
	    } 
	}
}


/*
function floatChange(){
	var $liCur = $(".help_nav ul li.cur1"),
    curP = $liCur.position().left,
    curW = $liCur.outerWidth(true),
    $slider = $(".curBg"),
    $navBox = $(".nav");
   $targetEle = $(".help_nav ul li a"),
  $slider.animate({
    "left":curP,
    "width":curW
  });
  $targetEle.mouseenter(function () {
    var $_parent = $(this).parent(),
      _width = $_parent.outerWidth(true),
      posL = $_parent.position().left;
    $slider.stop(true, true).animate({
      "left":posL,
      "width":_width
    }, "fast");
  });
  $navBox.mouseleave(function (cur, wid) {
    cur = curP;
    wid = curW;
    $slider.stop(true, true).animate({
      "left":cur,
      "width":wid
    }, "fast");
  });
}*/