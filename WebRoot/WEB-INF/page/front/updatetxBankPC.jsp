<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>三好资本</title>
</head>
<body style="width: 700px;">
        <div>
        <table class="table-condensed  mt10 mb10" width="100%">
          <tr>
            <td width="30%" align="right">开户名称：<br /></td>
            <td width="70%">
            ${bank.cardUserName }
            </td>
          </tr>
          <tr>
            <td align="right">开户银行名称：<br /></td>
            <td>
           ${bank.bankName }
          </tr>
          <tr>
            <td align="right">银行卡号：<br /></td>
            <td>${bank.cardNo }</td>
          </tr>
          <tr id="jg">
                    <td align="right">开户行所在地：
                    </td>
                    <td>
                        <s:select id="province" name="workPro"
                            cssStyle="width:107px;"
                            list="#request.provinceList" listKey="regionId"
                            listValue="regionName" headerKey="" headerValue="--请选择--"
                            onchange="javascript:if($('#province').val()!=''){$('#u_Pro_City').html('')}" />
                        <s:select id="city" name="cityId"
                            cssStyle="width:107px;"
                            list="#request.cityList" listKey="regionId"
                            listValue="regionName" headerKey="" headerValue="--请选择--"
                            onchange="javascript:if($('#city').val()!=''){$('#u_Pro_City').html('')}" />
                        <span class="red">*</span>
                        <span style="color: red; float: none;" id="u_Pro_City"
                            class="formtips"></span>
                    </td>
                </tr>
          <tr>
            <td align="right">三好资本交易密码：<br /></td>
            <td><input type="password" class="inp188" style="border: 1px solid #dcdcdc;"  id="dealpwd"/>
              <span class="txt"><i class="red">*非银行卡交易密码,<a href="searchDealPwdSetp1.mht" id="searchDealPwd" class="ln26 blue f14  fancybox.ajax">忘记交易密码？</a></i></span><br /></td>
          </tr>
         </table>
         <div class="control-group text-center">
            <span style="color: red; float: none;" id="bk1_tip" class="formtips"></span>
            <span id="bankInfo" ></span><br />
            <input type="button" class="button w100" id="addbank" value="确定"/>
            <input type="button"  class="button_over pl50   w100" id="addbankquxiao" value="取消"/>
      </div>
          
          </div>
     <div  class="ml100 red">
      <div>温馨提示*：为了您的提现金额能快速到账，请填写正确的信息</div>
    </div>
<script>
function citySelectInit(parentId, regionType){
    var _array = [];
    _array.push("<option value=''>--请选择--</option>");
    var param = {};
    param["regionType"] = regionType;
    param["parentId"] = parentId;
    $.post("ajaxqueryRegion.mht",param,function(data){
        for(var i = 0; i < data.length; i ++){
            _array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
        }
        $("#city").html(_array.join(""));
    });
}
function registedPlaceCity(parentId, regionType){
    var _array = [];
    _array.push("<option value=''>--请选择--</option>");
    var param = {};
    param["regionType"] = regionType;
    param["parentId"] = parentId;
    $.post("ajaxqueryRegion.mht",param,function(data){
        for(var i = 0; i < data.length; i ++){
            _array.push("<option value='"+data[i].regionId+"'>"+data[i].regionName+"</option>");
        }
        $("#registedPlaceCity").html(_array.join(""));
    });
}
$("#province").change(function(){
    var provinceId = $("#province").val();
    citySelectInit(provinceId, 2);
});
     $("#registedPlacePro").change(function(){
    var provinceId = $("#registedPlacePro").val();
    registedPlaceCity(provinceId, 2);
});

$("#addbank").click(function(){
    addBankInfo();
});
$("#addbankquxiao").click(function(){
    $.fancybox.close();
});
//添加提现银行信息
function addBankInfo(){
        if($("#dealpwd").val()=="" || isBlank($("#province").val()) || isBlank($("#city").val())){
           $("#bk1_tip").html("请完整填写信息,带*号的为必填选项");
           return;
         }
         param["paramMap.bankId"] = ${bank.id};
         param["paramMap.dealpwd"] = $("#dealpwd").val();
         param["paramMap.province"] = $("#province").val();
         param["paramMap.city"] = $("#city").val();
        if(!window.confirm("确定地址填写正确?")){
            return;
        }
        
        $.dispatchPost("updateBankPC.mht",param,function(data){
           if(data == 3){
              alert("请正确填写参数");
              $("#addbank").attr("disabled",false);
           }else if(data == 1){
               alert("修改成功！");
               window.parent.window.jBox.close();
            }else if(data == 2){
                alert("修改失败");
                $("#addbank").attr("disabled",false);
             }else if(data == 5){
              alert("交易密码错误");
              $("#addbank").attr("disabled",false);
           }
        });
}

</script>
</body>
</html>
