var ss = window.ss ={
	callbacks:{}
};
(function(){
	var baseURL ="";
	ss.urlMapping ={
		//topbanner
		TOP_BANNER:baseURL+"part_topbanner.mht",
		//购买金额
		BUY_SUM:baseURL+"part_buySum.mht",
		//基金开户
		FUND_OPEN_AN_ACCOUNT:baseURL+"part_fundOpenAnAccount.mht",
		//确认购买
		BUY_CONFIRM:baseURL+"part_buyConfirm.mht",
		//认购成功
		SUBSCRIPTION_SUCCESS:baseURL+"part_subscriptionSuccess.mht",
		//申购成功
		APPLY_FOR_PURCHASE_SUCCESS:baseURL+"part_applyForPurchaseSuccess.mht",
		//赎回
		REDEMPTION:baseURL+"part_redemption.mht",
		//赎回成功
		REDEMPTION_SUCCESS:baseURL+"part_redemptionSuccess.mht",
		//赎回确认
		REDEMPTION_CONFIRM:baseURL+"part_redemptionConfirm.mht",
		//我的基金
		MY_FUND:baseURL+"part_myFund.mht",
		//定投
		AIP:baseURL+"par_AIP.mht",
		//资金记录
		CAPITAL_RECORD:baseURL+"part_capitalRecord.mht",
		//公募基金
		PUBLIC_FUND:baseURL+"part_publicFund.mht",
		//风险承受能力测试
		RISK_TEST:baseURL+"part_riskTest.mht"
	}
	var centerURL = "";
	ss.centerURLMapping ={
		HR_FUND:centerURL + "hr_fund.mht",
		//5问卷调查题目查询接口
		QUESTIONS:centerURL +"hr.fund.paper.questions",
		//6问卷调查题目测评接口
		QUESTIONS_SCORE:centerURL +"hr.fund.paper.score",
		//8客户基金账户详情查询接口
		ACCT:centerURL +"hr.fund.acct",
		//9客户基金账户资金余额查询接口
		ACCT_CAPITALS:centerURL +"hr.fund.acct.capitals",
		//10客户基金份额明细查询接口
		ACCT_SHARES:centerURL +"hr.fund.acct.shares",
		//11客户基金交易申请记录查询接口
		TRADE_APPLIES:centerURL +"hr.fund.acct.trade.applies",
		//12客户基金交易确认记录查询接口
		TRADE_CONFIRMS:centerURL +"hr.fund.acct.trade.confirms",
		//13客户基金申购接口
		ACCT_ALLOT:centerURL +"hr.fund.acct.allot",
		//14客户基金赎回接口
		ACCT_REDEEM:centerURL +"hr.fund.acct.redeem",
		//15客户基金撤单接口
		ACCT_UNDOTRADE:centerURL +"hr.fund.acct.undotrade"
	}
	ss.getRequest = function(params){
		$.get(ss.urlMapping[params.url],function(data){
			params.callback(data);
		})
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
                    //ss.showPageDialog({msg: errorMsg});
                }
            }
        });
    };
})(ss);