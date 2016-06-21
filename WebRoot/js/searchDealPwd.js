function searchDealPwd(){
    jQuery.jBox.open("iframe:searchDealPwdSetp1.mht", "交易密码找回", 500,250,{ buttons: { }});
}

function reloadsearchDealPwd(){
    //window.jBox.close();
    searchDealPwd();
}

function overrideHA(){
    $("#jbox-content").css("height","450px");
}

function overrideHB(){
    $("#jbox-content").css("height","250px");
}