<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
<script src="../script/jquery-2.0.js" type="text/javascript"></script>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>

<script type="text/javascript">
</script>
<script>
$(function(){
 $("#dd").click(function(){
 var param = {};
 param["paramMap.typeId"]  = $("#typeId").val();
 param["paramMap.selectValue"]  = $("#selectValue").val();
 param["paramMap.selectName"]  = $("#selectName").val();
 param["paramMap.deleted"]  = $("#deleted").val();
 param["paramMap.selectKey"] = $("#selectKey").val();
 param["paramMap.introduce"] = $("#introduce").val();
 var type = parseInt('${type}');
 if(type==1){
	 if(param["paramMap.selectValue"] == null || param["paramMap.selectValue"]==''){
		 alert("请输入参数值")
		 return;
	 }else if(isNaN(param["paramMap.selectValue"])){
	 	alert("参数值只能是整数类型")
         return;
	 }
	 if( param["paramMap.selectName"]==null ||  param["paramMap.selectName"]==''){
		 alert("请输入参数名称")
         return;
	 }
	  if( param["paramMap.selectKey"]==null ||  param["paramMap.selectKey"]==''){
         alert("请输入参数码")
         return;
     }else {
	 	var hasKey = false;
	 	$.dispatchPost("validataSyspar.mht",param,function(data){
			if(data==2){//存在
				hasKey = true;
			}else{
				hasKey = false;//不存在
			}
			if(hasKey){
                alert("参数码已存在，请重新输入。");
				return;
            }else{
				 window.parent.ffff('${type}',param)
			}
		});
		
	 }
 }
 if(type == 2){
	 if( param["paramMap.selectName"]==null ||  param["paramMap.selectName"]==''){
         alert("请输入参数类型名称")
         return;
     }
	  window.parent.ffff('${type}',param)
 }
 });


});

function delChild(id){
	 if(!window.confirm("确定要删除吗?")){
         return;
     }
	$.dispatchPost("deleteSysparChild.mht","id="+id,function(data){
		   if(data==1){
			   alert("操作成功!");
			   window.location.reload();
		   }else{
			   alert("操作失败！");
		   }
	});
}
var save=true;
var param = {};
function updateChild(id){
	if(!save){
		alert("请先保存正在修改的数据");
		return;
	}
	var tr = $("#"+id);
	var selectName = tr.find('td:eq(1)');//
 	var selectValue = tr.find('td:eq(2)');
	var introduce = tr.find('td:eq(4)');
	var deleted = tr.find('td:eq(5)');
	var link =tr.find('td:eq(6)');
	if(tr.attr("index")=='0'){
		selectName = tr.find('td:eq(1)');//
        selectValue = tr.find('td:eq(2)');
        introduce = tr.find('td:eq(4)');
        deleted = tr.find('td:eq(5)');
        link =tr.find('td:eq(6)');
	}else{
		 selectName = tr.find('td:eq(0)');//
         selectValue = tr.find('td:eq(1)');
		 introduce = tr.find('td:eq(3)');
         deleted = tr.find('td:eq(4)');
         link =tr.find('td:eq(5)');
	}
	param["selectName"] = selectName.html();
	param["selectValue"] = selectValue.html();
	param["deleted"] =deleted.html();
	param["introduce"] =introduce.html();
	selectName.html("<input type=\"text\"  id=\"selectName\" />");
	selectName.find("input").attr("value",param["selectName"]);
	selectValue.html("<input type=\"text\"  id=\"selectValue\" />");
	selectValue.find("input").attr("value",param["selectValue"]);
	introduce.html("<textarea type=\"text\"  id=\"introduce\"  rows=\"5\"  maxlength=\"255\" >"+param["introduce"]+"</textarea>");
	//introduce.find("textarea").attr("value",param["introduce"]);
	deleted.html("<select  name=\"deleted\"  id=\"deleted\"><option value=\"1\">启用</option><option value=\"2\">未启用</option></select>");
	//var ind = param["deleted"].trim()=='启用'?0:1;
	var ind = param["deleted"].trim()=='启用'?0:1;
	deleted.find("select option:eq("+ind+")").attr("selected","selected");
	var s = link.find("a:eq(0)").attr("href","javaScript:exceUpdate('"+id+"')");
	s.html("保存");
	s.append("&nbsp;<a href=\"javaScript:cacelUpdate('"+id+"')\">取消</a>");
	save = false;
}

function exceUpdate(id){
	var newparam = {};
	newparam["paramMap.selectName"] = $("#selectName").val();
	newparam["paramMap.selectValue"] = $("#selectValue").val();
	newparam["paramMap.deleted"] = $("#deleted").val();
	newparam["paramMap.introduce"] = $("#introduce").val();
	newparam["paramMap.id"] = id;
	if(newparam["paramMap.selectName"]==null ||newparam["paramMap.selectName"]==''){
		alert("请输入参数的名称。")
		return;
	}else if(newparam["paramMap.selectName"].length>50){
		alert("参数的名称已超过最大长度，请重新输入。")
        return;
	}
	if(newparam["paramMap.selectValue"]==null ||newparam["paramMap.selectValue"]==''){
        alert("请输入参数的值。")
        return;
    }else if(newparam["paramMap.selectValue"].length>18){
		alert("参数的值已超过最大长度，请重新输入。")
		return;
	}else if(isNaN(newparam["paramMap.selectValue"])){
        alert("参数值只能是整数类型")
         return;
     }
	$.dispatchPost("updateSysparChild.mht",newparam,function(data){
		if(data==1){
			alert("更新成功！");
			param["selectName"] = newparam["paramMap.selectName"];
            param["selectValue"] = newparam["paramMap.selectValue"];
			 param["introduce"] = newparam["paramMap.introduce"];
            param["deleted"] =newparam["paramMap.deleted"]=='1'?'启用':'未启用';
			cacelUpdate(id);
		}else{
			  alert("更新失败！")
		}
	});
	
}


function cacelUpdate(id){
	var tr = $("#"+id);
    var selectName = tr.find('td:eq(1)');//
    var selectValue = tr.find('td:eq(2)');
    var introduce = tr.find('td:eq(4)');
    var deleted = tr.find('td:eq(5)');
    var link =tr.find('td:eq(6)');
    if(tr.attr("index")=='0'){
        selectName = tr.find('td:eq(1)');//
        selectValue = tr.find('td:eq(2)');
        introduce = tr.find('td:eq(4)');
        deleted = tr.find('td:eq(5)');
        link =tr.find('td:eq(6)');
    }else{
         selectName = tr.find('td:eq(0)');//
         selectValue = tr.find('td:eq(1)');
         introduce = tr.find('td:eq(3)');
         deleted = tr.find('td:eq(4)');
         link =tr.find('td:eq(5)');
    }
	
	save = true;
	selectName.html(param["selectName"]);
	selectValue.html(param["selectValue"]);
	introduce.html(param["introduce"]);
	deleted.html(param["deleted"]);
	var s = link.find("a:eq(0)");
	s.attr("href","javascript:updateChild('"+id+"')");
	s.html("修改")
	s.append("&nbsp;<a href=\"javaScript:delChild('"+id+"')\">删除</a>");
	link.find("a:eq(1)").remove();
	param={};
}

</script>
<s:if test="%{#request.type==1}">
<div id="right"
            style="background-image: url(images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
            <div style="padding: 15px 10px 0px 10px;">
                <div>
                    <s:hidden  value="%{#request.id}"  id="typeId"  />
                    <div
                        style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 400px; padding-top: 10px; background-color: #fff;">
                        <table id="help" style="width: 100%; color: #333333;"
            cellspacing="1" cellpadding="3" border="0">
                            <tbody>
                                
                                  <tr>
                                    <td class="f66" align="right" width="20%" height="36px">
                                        系统参数类型：
                                    </td>
                                    <td   class="f66" >${typename }</td>
                                 </tr>
                                <tr>
                                    <td class="f66" align="right" width="20%" height="36px">
                                        系统参数名称：
                                    </td>
                                    <td><input type="text"  id="selectName" /></td>
                                 </tr>
                                 <tr>
                                    <td class="f66" align="right" width="20%" height="36px">
                                        系统参数值：
                                    </td>
                                    <td>
                                    <input type="text"  id="selectValue" />
                                    </td>
                                 </tr>
								 <tr>
                                    <td class="f66" align="right" width="20%" height="36px">
                                        系统参数码：
                                    </td>
                                    <td>
                                    <input type="text"  id="selectKey"  /><span  class="f66" style="color:red">*(业务英文缩写，例如：借款金额=>JKJE)</span>
                                    </td>
                                 </tr>
								 <tr>
                                    <td class="f66" align="right" width="20%" height="36px">
                                        参数介绍：
                                    </td>
                                    <td>
                                    <textarea id="introduce" rows="4" maxlength="255"></textarea>
                                    </td>
                                 </tr>
                                  <tr>
                                    <td class="f66" align="right" width="20%" height="36px">
                                        是否启用：
                                    </td>
                                  <td><s:select list="#{1:'启用',2:'未启用' }"  name="deleted"  id="deleted"  value="1"></s:select></td>
                                 </tr>
                                          <tr>
                                        <td class="f66"   style="padding-left:  185px" width="25%" height="50px">
                                        <input type="button"   id="dd"  style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"/>
                                    </td>
                                 </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            </div>
</s:if>
<s:elseif  test="%{#request.type==2}">
<div id="right"
            style="background-image: url(images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
            <div style="padding: 15px 10px 0px 10px;">
                <div>
                    <s:hidden  value="%{#request.id}"  id="typeId"  />
                    <div
                        style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 400px; padding-top: 10px; background-color: #fff;">
                        <table id="help" style="width: 100%; color: #333333;"
            cellspacing="1" cellpadding="3"  border="0">
                            <tbody>
                                  <tr>
                                    <td class="f66" align="right" width="25%" height="36px"  >
                                        系统参数类型：
                                    </td>
                                   <td  class="f66" ><input type="text"  id="selectName"  value="${map.name}" /></td>
                                 </tr>
                                 </tr>
								 <tr>
                                    <td class="f66" align="right" width="25%" height="36px">
                                        系统参数类型介绍：
                                    </td>
                                   <td  class="f66" ><input type="text"  id="introduce"   value="${map.introduce}"/></td>
                                 </tr>
                                 </tr>
                                          <tr>
                                        <td class="f66"   style="padding-left:  185px" width="25%" height="50px">
                                        <input type="button"   id="dd"  style="background-image: url('../images/admin/btn_queding.jpg'); width: 70px; border: 0; height: 26px"/>
                                    </td>
                                 </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            </div>
</s:elseif>
<s:else>
<div id="right"
            style="background-image: url(images/admin/right_bg_top.jpg); background-position: top; background-repeat: repeat-x;">
            <div style="padding: 15px 10px 0px 10px;">
                <div>
                    <s:hidden  value="%{#request.id}"  id="typeId"  />
                    <div
                        style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px;  padding-top: 10px; background-color: #fff;">
                        <table id="help" style="width: 100%; color: #333333;"
            cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
                            <tbody>
                                <s:if test="pageBean.page==null || pageBean.page.size==0">
                    <tr align="center" class="gvItem">
                        <td colspan="8">暂无数据</td>
                    </tr>
                </s:if>
                <s:else>
                <tr class=gvHeader>
                 <td class="f66"   height="36px">
                                        系统参数类型
                                    </td>
                                     <td class="f66"    height="36px">
                                        系统参数名称
                                    </td>
                                    <td class="f66"   height="36px">
                                        系统参数值
                                    </td>
									<td class="f66"  height="36px">
                                        系统参数码
                                    </td>
									<td class="f66"   height="36px">
                                        参数介绍
                                    </td>
                                     <td class="f66"   height="36px">
                                        是否启用
                                    </td>
									
                                    <td class="f66"    height="36px">
                                        操作
                                    </td>
                </tr>
                    <s:iterator value="pageBean.page" var="bean" status="st">
                    
                    <tr class="gvItem"  id="${bean.id}" index=${st.index}>
                         <s:if test="%{#st.index==0}"><td rowspan="${pageBean.pageSize}">${typename }</td></s:if><s:else></s:else>
                                    <td>${selectName }</td>
                                    <td> ${ selectValue}</td>
									<td>${selectKey}</td>
									<td>${introduce}</td>
                                    <td>
                                    <s:if test="#bean.deleted==1">启用</s:if><s:else>未启用</s:else>
                                    </td>
                                    <td align="center">
                                        <a href="javascript:updateChild('${bean.id}')">修改</a>
                                        <a href="javascript:delChild('${bean.id}')">删除</a>
                                    </td>
                    </s:iterator>
                    <script>
                          // var table = $("table tr:eq(1) td:eq(0)").attr("rowspan",'${pageBean.pageSize}')
                    </script>
                </s:else>
                            </tbody>
                        </table>
                        <shove:page url="addSysparChildInit.mht"  curPage="${pageBean.pageNum}" showPageCount="7" pageSize="${pageBean.pageSize }" theme="number" totalCount="${pageBean.totalNum}">
                            <s:param name="id">${id}</s:param>
                            <s:param name="typename">${typename}</s:param>
                            <s:param name="type">${type}</s:param>
                       </shove:page>
                    </div>
                </div>
            </div>
            </div>
</s:else>

