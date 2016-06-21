<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<script type="text/javascript">
       function mediaDetail(param){
          $.post("frontMediaReportDetails.mht","id="+param,function(data){
                  $("#showcontent").html("");
                  $("#showcontent").html("<h3>"+data.title+"</h3>"+
                                 "<p class='time'></p>"+
                               "<p class='zw'>"+data.content+"</p>");
          });
       }
</script>
            <s:if test="pageBean.page==null || pageBean.page.size==0">
               <div align="center">
                  <span>暂无数据</span>
               </div>
        </s:if>
     <s:else>
       <s:iterator value="pageBean.page" var="bean">
           <li><a href="frontNewsDetails.mht?id=${bean.id}" target="_blank"><span class="lbt">${bean.title}</span><span class="ldt">${fn:split(bean.publishTime,' ')[0]}</span></a></li>
        </s:iterator>
     </s:else>



