<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<div class="box p30" style="display:none;">
        <div class="boxmain2">
        <div class="biaoge2">
        <table border="0" cellspacing="0" cellpadding="0" style="line-height: 50px;">
  <tr>
    <td >发件人：</td>
    <td colspan="2"><strong><s:property value="#request.userName" default="---" ></s:property></strong></td>
  </tr>
  <tr>
    <td>收件人：</td>
    <td><input type="text" class="form-control" id="receiver" />
    </td>
    <td><span style="color: red; float: none;" id="s_receiver" class="formtips"></span></td>
  </tr>
  <tr>
    <td>标题：</td>
    <td><input type="text" class="form-control" id="title_s" maxlength="200"/>
    </td><td><span style="color: red; float: none;" id="s_title" class="formtips"></span></td>
  </tr>
  <tr>
    <td valign="top">内容：</td>
    <td><textarea class="form-control" rows="3" id="content"></textarea>
    </td><td><span style="color: red; float: none;" id="s_content" class="formtips"></span></td>
  </tr>
  <tr>
    <td>验证码：</td>
    <td><input type="text" class="form-control" id="code"/>
      </td>
      <td><img src="admin/imageCode.mht?pageId=userlogin" title="点击更换验证码"
		style="cursor: pointer;" id="codeNum" width="46" height="18"
		onclick="javascript:switchCode()" />
		<span style="color: red; float: none;" id="s_code" class="formtips"></span></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td style="padding-top:20px;" colspan="2">
    <input type="button" class="button w140 lh_initial" onclick="addMail()" id="btnSave" value="提交发送"/></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class="txt" colspan="2">* 温馨提示：如果要给管理员发送信息，请输入收件人admin</td>
  </tr>
        </table>

        </div>
    </div>
</div>
