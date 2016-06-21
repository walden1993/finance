<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
    <div id="right">
        <table id="help" style="width: 100%; color: #333333;"
            cellspacing="1" cellpadding="3" bgcolor="#dee7ef" border="0">
            <tbody>
                <tr class=gvHeader>
                    <th scope="col">
                        系统参数类型
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
                            ${name }
                            </td>
                            <td>
                            <a style="cursor: pointer;" ids="${id }"  typename="${name }"  type="1"   onclick="javascript:fff(this)">新增子级</a> 
                                &nbsp;&nbsp;
                                <a style="cursor: pointer;" ids="${id }"   typename="${name }"  type="2"   onclick="javascript:fff(this)">修改</a> 
                                &nbsp;&nbsp;
                                <a style="cursor: pointer;" ids="${id }"  typename="${name }"  type="3"  onclick="javascript:fff(this)">查看子级</a> 
                                &nbsp;&nbsp;
                             <a style="cursor: pointer;" id="shanchu"   typename="${name }"     onclick="del('${id }')" >删除</a>
                            </td>
                        </tr>
                    </s:iterator>
                </s:else>
            </tbody>
        </table>
    </div>
<script type="text/javascript" >

   function fff(data){
                var t = $(data).attr("ids");
                var typename = $(data).attr("typename");
                var type = $(data).attr("type");
                var title = '';
                switch (parseInt(type)) {
				case 1:
					title = "新增系统参数["+typename+"]";
					break;
			    case 2:
			    	title= "修改类型名称["+typename+"]";
                    break;
                case 3:
                	title = "查看系统参数["+typename+"]";
                     break;
				default:
					break;
				}
                ShowIframe(title,'addSysparChildInit.mht?id='+t+'&typename='+typename+'&type='+type,type=='3'?800:600,type=='2'?150:300);
            }
            function ffff(t,p){
            	var url ='';
               switch (parseInt(t)) {
			case 1:
				url="addSysparChild.mht";
				break;
			case 2:
				url ="updateSyspar.mht";
				break;
			case 3:
				break;
			default:
				break;
			}
               exce(url,p);
               ClosePop();
            }
            
            function exce(url,p){
            	$.dispatchPost(url,p,function(data){
                    if(data==1){
                      alert("操作成功");
                      window.location.reload();
                    }else if(data==3){
                    	 alert("系统参数码已存在，请重新输入");
                    }
                    else{
                      alert("操作失败");
                    }
                })
            }
            
            
            function del(id){
                if(!window.confirm("确定要删除吗?")){
                    return;
                }
                var param ={};
                param["paramMap.id"] = id;
                $.post("deleteSyspar.mht",param,function(data){
                	  if(data == 1){
                		  alert("删除失败，参数丢失。")
                	  }else if(data==2){
                		  alert("删除失败，此参数类型含有子级，请删除子级后在操作。")
                	  }else if(data==3){
                          alert("删除成功。")
                          window.location.reload();
                      }else{
                    	  alert("删除失败。")
                      }
                })
            }
            
</script>
<script>

</script>




    <shove:page curPage="${pageBean.pageNum}" showPageCount="10" pageSize="${pageBean.pageSize }" theme="jsNumber" totalCount="${pageBean.totalNum}"></shove:page>
