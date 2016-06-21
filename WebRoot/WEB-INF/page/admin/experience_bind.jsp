<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
<title>绑定体验券</title>
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
	$(document).ready(function(){
		var oldText = "";
		var len = 0
		//获得输入框
		var usernameInput = $("#username");
		var usernameInputOffSet = usernameInput.offset();
		var index = 0;
		var cacheData;
		//首先要隐藏自动补全显示内容
		$("#auto").hide().css("border","1px #D9D9D9 solid")
		.css("top",usernameInputOffSet.top+usernameInput.height()+"px")
		.css("left",usernameInputOffSet.left+"px").css("position","absolute").css("z-index","999999").width(usernameInput.width()).css("background-color","#fff");
		usernameInput.keyup(function(event){
			//处理文本框中的键盘事件
            var myEvent = event || window.event;
            var keyCode = myEvent.keyCode;
			var autoDiv = $("#auto");
			//处理输入框键盘事件
			var username = $("#username").val();
			if(username==null || $.trim(username)==''){//输入框内容为空的时候
				autoDiv.hide();
				return ;
			}
			
			param["paramMap.username"] =$.trim(username);
			
			if(oldText!=username){//第一次的内容跟第二次的内容不相同的时候才去后台重新查询
			    //初始化索引以及显示div
				index = 0;
				len = 0;
				autoDiv.html("");
				//请求服务器
				$.dispatchPost("seachUser.mht",param,function(data){
                //返回的是一个List集合
                //迭代出集合中的所有元素
                if(data.length>1){//[]也代表一个元素，所以是从1开始的
                    len = data.length;
					cacheData = data;
                    $(data).each(function(i){
                    //list[i] = data[i];//得到一个map对象。map中包含了id、username
                    $("<div>").css("cursor","pointer").html(data[i].username).click(function(){
						usernameInput.val(autoDiv.find("div").eq(i).html());
						usernameInput.attr("uid",cacheData[i].id);
                    autoDiv.hide();
					}).appendTo(autoDiv)
                })
                autoDiv.show();
                }else{
                    autoDiv.hide();
                }
                
                autoDiv.find("div").eq(index).css("background-color","#4D90FE");
				oldText = username;
            })
			}
			if(len>1){//有数据的时候才做处理
				if(keyCode==38){//向上健
                    if(index>0){
                        autoDiv.find("div").eq(index).css("background-color","");
                        autoDiv.find("div").eq(index-1).css("background-color","#4D90FE");
                        index = index-1;
                    }else{
                        index = len - 1;
                        autoDiv.find("div").eq(0).css("background-color","");
                         autoDiv.find("div").eq(index).css("background-color","#4D90FE");
                    }
                }
                if(keyCode==40){//向下建
                    if(index<len-1){
                        autoDiv.find("div").eq(index+1).css("background-color","#4D90FE");
                        autoDiv.find("div").eq(index).css("background-color","");
                        index = index+1;
                    }else{
                        index =0;
                         autoDiv.find("div").eq(index).css("background-color","#4D90FE");
                         autoDiv.find("div").eq(len - 1).css("background-color","");
                    }
                }
				
				if(keyCode==13){//回车键
					usernameInput.val(autoDiv.find("div").eq(index).html());
					usernameInput.attr("uid",cacheData[index].id);
					autoDiv.hide();
				}
			}
			
		});
		
	       $("body").click(function(){
		   	    $("#auto").hide();
		   });
		   
		   $("#bind").click(function(){
		   	      param["paramMap.userId"]=$("#username").attr("uid");
				  param["paramMap.username"]=$("#username").val();
				  param["paramMap.ticketNo"]=$("#ticketNo").val();
				  if(param["paramMap.username"]==null || $.trim(param["paramMap.username"])==''){//输入框内容为空的时候
				        alert("请输入你要绑定的用户名");
                         return ;
                     }
		   	      $.dispatchPost("experienceBind.mht",param,function(data){
				  	       if(data.ret<=-1){
						   	      alert(data.ret_desc);
								  window.parent.history.go(-1);
								  window.parent.location.reload();
								  window.parent.jBox.close();
						   }else if(data==11){
						   	     alert("绑定失败，用户不存在！");
						   }else{
						   	      alert(data.ret_desc)
						   }
				  })
		   });
		   
		   $("#cacel").click(function(){
		   	      window.parent.jBox.close();
		   });
	});
	
</script>
</head>
<body>
	<%--<s:if test="#paramMap!=null && #paramMap!=''">--%>
        <div>
             <input type="hidden" id="ticketNo" value="${paramMap.ticketNo}"/>
            <div align="center" style="margin-top:25px;">
            	<table>
                <tr class="f66">
                    <td align="right">券号：</td>
                    <td>${paramMap.ticketNo}</td>
                </tr>
                <tr class="f66">
                    <td align="right">类型：</td>
                    <td>体验券<%--<if test="%{#paramMap==1}">现金券</if><s:else>体验券</s:else>--%></td>
                </tr>
                <tr class="f66">
                    <td align="right">金额：</td>
                    <td>${paramMap.amount}元</td>
                </tr>
                <tr>
                    <td align="right"><h4>绑定用户名：</h4></td>
                    <td><input type="text" id="username" uid="" autocomplete="off"   class="inp80" /><span class="red">(*自动补全)</span></td>
                </tr>
                <tr>
                    <td><input type="button" value="绑定" id="bind" /></td>
                    <td><input type="button" id="cacel" value="取消"/></td>
                </tr>
            </table>
				
            </div><br/>
            <div id="auto"></div><!--用于显示自动补全的用户--><br/>
        </div>
	<%--</s:if>
	<s:else>没有查询到此体验券，请重新选择！！！</s:else>--%>
</body>
</html>