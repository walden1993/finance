var ss = window.ss ={
	callbacks:{}
};
(function(){
	var centerURL = "";
	ss.centerURLMapping ={
		HR_FUND:centerURL + "hr_fund.mht",
		QUESTIONS:centerURL +"hr.fund.paper.questions",
		QUESTIONS_SCORE:centerURL +"hr.fund.paper.score"
	}
	// 页面Ajax请求
    ss.postRequest = function (params) {
        $.ajax({
            type: "POST",
            url: params.url,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            async: false,
            cache: false,
            data: JSON.stringify(params.param),
            success: function (data) {
                if (typeof data === "string") {
                    data = JSON.parse(data);
                }
                if (undefined !== params.callback) {
                    params.callback(data);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (params.errCallback) {
                    params.errCallback(XMLHttpRequest, textStatus, errorThrown);
                }else{
                    var errorMsg = "网络异常,请重新进入";
                    ss.showPageDialog({msg: errorMsg});
                }
            }
        });
    };
    //  提示框
    var appShowCode = true;
    var PageDialog = function (params) {
        //		内容信
        this.iconClass = "msg-icon-info";
        if (undefined !== params) {
            if (params.msg !== undefined) {
                this.msg = params.msg;
            } else {
                this.msg = "网络错误";
            }
            //		回调函数
            this.callback = params.callback;
            if (appShowCode && undefined !== params.code) {
                this.msg += ("Error code:" + params.code);
            }
            if(undefined !== params.iconClass && params.iconClass === "ok"){
                this.iconClass = "msg-icon-ok";
            }
            if(undefined !== params.timeOut){
                this.timeOut = params.timeOut;
                this.timerCallback = params.timerCallback;
            }
        } else {
            this.msg = "网络错误";
            this.callback = undefined;
        }
    };
    PageDialog.prototype.show = function () {
        // 弹出框html
        var dialogNode = $("<div class='pagePromptBox'>" +
            "<div class='page-prompt-msg-box'>" +
            "<p class='msg-box-p'><i class='"+this.iconClass+"'></i><span>" + this.msg + "</span></p>" +
            "</div></div>");
        var callback = this.callback;
        var timerCallback = this.timerCallback;
        var nowTimer = null;
        if(this.timeOut){
            nowTimer = setTimeout(function(){
                timerCallback();
                nowTimer = null;
            }, 3000);
        }
        // 弹出框click事件绑定
        $(dialogNode).on("click", function () {
            clearTimeout(nowTimer);
            nowTimer = null;
            if (undefined !== callback) {
                callback();
            }
            $(this).remove("slow");
        });
        $("body").append(dialogNode);
    };
    PageDialog.prototype.tapshow = function () {
        // 弹出框html
        var dialogNode = $("<div class='pagePromptBox'>" +
            "<div class='page-prompt-msg-box'>" +
            "<p class='msg-box-p'><i class='"+this.iconClass+"'></i><span>" + this.msg + "</span></p>" +
            "</div></div>");
        var callback = this.callback;
        var timerCallback = this.timerCallback;
        var nowTimer = null;
        if(this.timeOut){
            nowTimer = setTimeout(function(){
                timerCallback();
                nowTimer = null;
            }, 3000);
        }
        // 弹出框click事件绑定
        $(dialogNode).on("tap", function () {
            if(nowTimer != null){
                clearTimeout(nowTimer);
                nowTimer = null;
            }
            if (undefined !== callback) {
                callback();
            }
            $(this).remove("slow");
        });
        $("body").append(dialogNode);
    };
    // 页面调起弹出框方法
    ss.showPageDialog = function (params) {
        new PageDialog(params).show();
    };
    ss.tapShowPageDialog = function (params) {
        new PageDialog(params).tapshow();
    };
    // 页面加载等待弹出框
    var LoadWaitBox = {
        show: function () {
            var node = $("<div id='loadWaittingBox' class='wait-box-layer'><div class='box'><span class='desc'>Loading</span><span class='dot-point'>" +
                "<i class='block block-a1'></i></span><span class='dot-point'><i class='block block-a2'></i></span><span " +
                "class='dot-point'><i class='block block-a3'></i></span></div>");
            $("body").append(node);
        },
        close: function () {
            var node = document.getElementById("loadWaittingBox");
            if (null !== node) {
                $(node).remove();
            }
        }
    };
    ss.showWaitBox = function () {
        new LoadWaitBox.show();
    };
    ss.closeWaitBox = function () {
        new LoadWaitBox.close();
    };
})(ss);