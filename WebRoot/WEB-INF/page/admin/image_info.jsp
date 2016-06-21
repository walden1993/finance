<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/popom.js"></script>
	<div id="right">
		<table id="help" style="width: 100%; color: #333333;"
			cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
			<tbody>
				<tr class=gvHeader>
					<th scope="col">
						序号
					</th>
					<th scope="col">
						地址
					</th>
					<th scope="col">
						图片
					</th>
					<th scope="col">
						操作
					</th>
				</tr>
				<s:if test="pageBean.page==null || pageBean.page.size==0">
					<tr align="center" class="gvItem">
						<td colspan="8">暂无数据</td>
					</tr>
				</s:if>
				<s:else>
					<s:iterator value="pageBean.page" var="bean" status="st">
						<tr class="gvItem">
							<td>
							${st.count }
							</td>
								<td>
							../${imagePath }
							</td>
								<td>
							<img src="../${imagePath }" width="100" height="100"></img>
							</td>
							<td>
								<a style="cursor: pointer;"  onclick="javascript:updateImage(${id })">修改</a> 
								&nbsp;&nbsp;
							 <a style="cursor: pointer;" id="shanchu" onclick="del(${id })">删除</a>
							</td>
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
	</div>
<script type="text/javascript">

   function fff(data){
                var t = $(data).attr("ids");
		    	ShowIframe("修改",'updateinvers.mht?id='+t,300,300);
		    }
		    function ffff(f,d){
		    	var param = {};
		    	param["paramMap.id"]=d;
		    	param["paramMap.tagd"] = f;
		    	$.post("updateinversReal.mht",param,function(data){
		    	var callBack = data.msg;
	      if(callBack == undefined || callBack == ''){
		                 $('#right').html(data);
		                 $(this).show();
		                 }
		    	if(data==1){
		    	  alert("修改成功");
		    	  window.location.reload();
		    	}else{
		    	alert("修改失败");
		    	}
		    	});
		      ClosePop();
		    }

</script>
<script>
var dat ='';
function updateImage(dat1){
	dat = dat1;
	 $("#img").attr("value","");
  	var dir = getDirNum();
  	var json = "{'fileType':'JPG,BMP,GIF,TIF,PNG','fileSource':'user/"+dir+"','fileLimitSize':0.5,'title':'上传图片','cfn':'uploadCall2','cp':'img'}";
    json = encodeURIComponent(json);
    //window.showModalDialog("uploadFileAction.mht?obj="+json,window,"dialogWidth=500px;dialogHeight=400px");
    window.open('uploadFileAction.mht?obj='+json,'newwindow','width=500,height=400,left=400,top=150,resizable=yes');
} 
</script>


<script>
      	function getDirNum(){
		      var date = new Date();
		 	  var m = date.getMonth()+1;
		 	  var d = date.getDate();
		 	  if(m<10){
		 	  	m = "0"+m;
		 	  }
		 	  if(d<10){
		 	  	d = "0"+d;
		 	  }
		 	  var dirName = date.getFullYear()+""+m+""+d;
		 	  return dirName; 
			}
			function uploadCall2(basepath,fileName,cp){
		  		if(cp == "img"){
		  			var path = "upload/"+basepath+"/"+fileName;
					$("#img").attr("value",path);
					    var headImgPath = path;
    	if(headImgPath!=""){
				var param1 = {};
				param1["paramMap.id"] = dat;
				alert(dat)
				param1["paramMap.titleName"] = headImgPath;
					$.post("updateSysImage.mht",param1,function(data){
						var callBack = data.msg;
						if(data==0){
						alert("你输入的图片已经存在");
						window.location.reload();
						}else if(data==1){
						alert("操作成功");
						window.location.reload();
						}
					});
		  }
				}
			}
			</script>
			
	<script>
			
				 	function del(id){
		 		if(!window.confirm("确定要删除吗?")){
		 			return;
		 		}
		 		
		 		$.post("deletSysImage.mht",{"id":id},function(data){
		 		   var callBack = data.msg;
		 	       if(callBack == undefined || callBack == ''){
		                 $('#right').html(data);
		                 $(this).show();
		            }
		 		});
	 			
		 	}
			function uploadCall(basepath,fileName,cp){
				var path = "upload/"+basepath+"/"+fileName;
				var param1 = {};
				param1["pageBean.pageNum"] = 1;
				param1["paramMap.titleName"] = path;
					$.post("addSysImage.mht",param1,function(data){
					var callBack = data.msg;
					if(data==0){
						alert("操作失败");
					}else if(data==1){
						alert("操作成功");
					}
					if(callBack == undefined || callBack == ''){
		                 initListInfo(param);
		             }
					});
			}
		</script>		
			
			
	<shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
