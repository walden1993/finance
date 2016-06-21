angular.module('starter.services', ['ngResource'])

.factory("Gallery",function($resource,UrlUtil){
	return ['upload/a.jpg'];
})
.factory("Home",function($resource,UrlUtil){
	function index(){
		return $resource(UrlUtil.root+'index.do', {}, {
			query: {method:'GET', params:{'paramMap.wap':'wap'}}
		});
	}
	return {
		index:index
	}
})
.factory("About",function($resource,UrlUtil){
	var param = {};
	param['wap'] = 'wap';
	param["pageBean.pageNum"] = 1;
	param['curPage'] = 1;
	param.hasmore = false;
	//官方公告列表
	function frontQueryNewsListPage(){
		return $resource(UrlUtil.root+'queryNewsListPage.do?', {}, {
			query: {method:'GET', params:param}
		  });
	};
	
	//公共详情
	function frontNewsDetails(){
		return $resource(UrlUtil.root+'frontNewsDetails.do', {}, {
			query: {method:'GET', params:param}
		  });
	};
	
	//三好贷动态
	function queryMediaReportListPage2(){
		param["newsType"] = 1;
		return $resource(UrlUtil.root+'queryMediaReportListPage2.do', {}, {
			query: {method:'GET', params:param}
		  });
	};
	
	//互联网金融
	function queryMediaReportListPage1(){
		param["newsType"] = 2;
		return $resource(UrlUtil.root+'queryMediaReportListPage1.do', {}, {
			query: {method:'GET', params:param}
		  });
	};
	
	//详情
	function frontMedialinkId(){
		return $resource(UrlUtil.root+'frontMedialinkId.do', {}, {
			query: {method:'GET', params:param}
		  });
	};
	
	return {
		frontQueryNewsListPage:frontQueryNewsListPage,
		queryMediaReportListPage2:queryMediaReportListPage2,
		queryMediaReportListPage1:queryMediaReportListPage1,
		frontMedialinkId:frontMedialinkId,
		frontNewsDetails:frontNewsDetails,
		param:param
	}
})
.factory("UrlUtil",function(){
	var url = {};
	var hostname = window.location.hostname;
	if(hostname == '172.16.3.179'){
		url["root"] =window.location.protocol+'//172.16.3.179/sp2p_web/';
	}else if(hostname == '119.147.208.220'){
		url["root"] =window.location.protocol+'//119.147.208.220:8080/test/';
	}else{
		url["root"] =window.location.protocol+'//www.sanhaodai.com/';
	}
	return url;
})

.factory('Friends', function() {
  var friends = [
    { id: 0, name: 'Scruff McGruff' },
    { id: 1, name: 'G.I. Joe' },
    { id: 2, name: 'Miss Frizzle' },
    { id: 3, name: 'Ash Ketchum' }
  ];

  return {
    all: function() {
      return friends;
    },
    get: function(friendId) {
      return friends[friendId];
    }
  }
})

.factory('Phone', function($resource){
  return $resource('phones/:phoneId.json', {}, {
	query: {method:'GET', params:{phoneId:'phones'}, isArray:true}
  });
})
.factory('name', function(){
  var titleName = "111";
})

.factory('FinancList', function($resource,UrlUtil){
	 var param = {};
	  //页码
	  var _curPage = 1;
	  //期限
	  var _deadline = 1;
	  //状态
	  var _mode = 1;
	  //标题
	  var _title = "";
	  //还款方式
	  var _paymentMode = 1;
	  //借款目的
	  var _purpose = "";
	  //
	  var _reward = "";
	  //借款总额
	  var _arStart = 1;
	  var _arEnd = 2000000;
	  //类型
	  var _type = 1;
	  param.hasmore = false;
	  param ['paramMap.curPage']=_curPage;
	  param["wap"] = 'wap';
	function getList(){
		return $resource(UrlUtil.root+'financeList.do', {}, {
			query: {method:'GET',params: param}
		  });
	}
	return {
		getList:getList,
		param:param
	};
})

.factory('FinancDetail', function($resource,UrlUtil){
	function getDetail(){
		return $resource(UrlUtil.root+'financeDetail.do', {}, {
			query: {method:'GET'}
		  });
	};
	function investInit(params){
		return $resource(UrlUtil.root+'financeInvestInitWap.do', {}, {
			query: {method:'GET',params:params}
		  });
	};
	function invest(params){
		return $resource(UrlUtil.root+'financeInvest.do', {}, {
			save: {method:'GET',params:params}
		  });
	};
  return {
	  getDetail:getDetail,
	  investInit:investInit,
	  invest:invest
  }
})

.factory('UserCenter', function($resource,UrlUtil,User,$http){
	var param = {};
	param["wap"] = 'wap';
	param.hasmore = false;
	param['curPage'] = 1;
	//首页
    function home(){
    	return  $http.post(UrlUtil.root+'homeWap.do', param);
    };
    //资金记录初始化
    function getFundListInit(){
    	return  $resource(UrlUtil.root+'queryFundrecordInit.do', {}, {
			query: {method:'GET',params:param}
		  });
    }
    //资金记录列表
    function getFundList(){
    	param['pageBean.page'] = param['curPage'];
    	return  $resource(UrlUtil.root+'queryFundrecordList.do', {}, {
			query: {method:'GET',params: param}
		});
    }
    //安全中心
    function securityCent(){
    	return  $resource(UrlUtil.root+'securityCent.do', {}, {
			query: {method:'GET',params: param}
	    });
    }
    //实名认证
    function identifier(){
    	return  $http.post(UrlUtil.root+'identifier.do', param);
    }
    //我的投资
    function homeBorrowRecycleList(){
    	return  $resource(UrlUtil.root+'homeBorrowRecycleList.do', {}, {
			query: {method:'GET',params: param}
	    });
    }
    //银行卡
    function mybank(){
    	return  $resource(UrlUtil.root+'mybank.do', {}, {
			query: {method:'GET',params: param}
	    });
    }
    //提现初始化
    function withdrawLoad(){
    	return  $resource(UrlUtil.root+'withdrawLoad.do', {}, {
			query: {method:'GET',params: param}
	    });
    }
    
    return {
    	home:home,
    	getFundListInit:getFundListInit,
    	getFundList:getFundList,
    	param:param,
    	securityCent:securityCent,
    	identifier:identifier,
    	homeBorrowRecycleList:homeBorrowRecycleList,
    	mybank:mybank,
    	withdrawLoad:withdrawLoad
    };
})

.factory('User', function($resource,UrlUtil,$http,$q){
    var param = {};
    param['wap'] = 'wap';
	function updateLoginPwd(){
    	return $http.post(UrlUtil.root+'updatePasswordPhone.do',param);
    }
	
    function login(user){
    	return $http.post(UrlUtil.root+'logining1.do',user);  
    }
    
    function logout(){
    	return $http.get(UrlUtil.root+'logout.do');
    }
    
    function hasLogin(){
    	return {  
    	    query : function() {  
    	      var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行  
    	      $http({method: 'GET', url: UrlUtil.root+'hasLogin.do'}).  
    	      success(function(data, status, headers, config) {  
    	        deferred.resolve(data);  // 声明执行成功，即http请求数据成功，可以返回数据了  
    	      }).  
    	      error(function(data, status, headers, config) {  
    	        deferred.reject(data);   // 声明执行失败，即服务器返回错误  
    	      });  
    	      return deferred.promise;   // 返回承诺，这里并不是最终数据，而是访问最终数据的API  
    	    } // end query  
    	  };  
    }
    
    function register(user,step){
    	switch (step) {
		case 1:
			return $http.post(UrlUtil.root+'registerOneWap.do',user);
			break;
		case 2:
			return $http.post(UrlUtil.root+"sendSMSReg.do",user);
			break;
		case 3:
			return $resource(UrlUtil.root+'registerTwoWap.do', {}, {  
	            save: {  
	                method: "POST",  
	                params: user
	            }  
	        });
			break;
		default:
			break;
		}
    }
    
    function getPhone(){
    	return $resource(UrlUtil.root+'getPhone.do', {}, {  
            query: { method: "GET"}  
        });
    }
    
    return {
    	login:login,
    	logout:logout,
    	hasLogin:hasLogin,
    	register:register,
    	getPhone:getPhone,
    	param:param,
    	updateLoginPwd:updateLoginPwd
    };
})

.factory('name', function(){
  var titleName = "111";
})
.factory('Sms', function(UrlUtil,$http){
	var param = {};
	param["wap"] = "wap";
	function sendSms(vioce){
		return $http.post(UrlUtil.root+"sendSMSReg.do",param);
	}
	function sendMail(){
		return $http.post(UrlUtil.root+"forgetpasswordsenEml.do",param);
	}
	function sendSmsNoReg(vioce){
		return $http.post(UrlUtil.root+"ajaxSendSMS1.do",param);
	}
	function validateCode(){
		return $http.post(UrlUtil.root+"ajaxCheckRegister.do",param);
	}
    return {
    	sendSms:sendSms,
    	sendMail:sendMail,
    	sendSmsNoReg:sendSmsNoReg,
    	param:param,
    	validateCode:validateCode
    }
})
.factory('Phone', function($resource){
      return $resource('phones/:phoneId.json', {}, {
        query: {method:'GET', params:{phoneId:'phones'}, isArray:true}
      });
})
.factory('IConstant', function($http){
	var IConstant = {
			Error:'错误!',
			Success:'提示!',
			Info:'信息!',
			Warnning:'警告!',
			LoginFormError:{
				UserName_Require:'用户名不能为空',
				UserPwd_Require:'密码不能为空'
			},
			LoginError:{
				Code:"验证码错误",
				NoMatchUP:"用户名或密码错误！",
				UserDisable:"该用户已被禁用！",
				HasTwo:"用户名或密码错误!还有2次机会,您可以尝试找回密码",
				HasOne:"用户名或密码错误!还有1次机会,您可以尝试找回密码",
				Freeze:"该用户已被限制登录，请于三小时以后登录！",
				Exception:"该用户账号出现异常，请速与管理员联系!"
			},
		   LoadingTeml:{
			   Login:"登录中...",
			   Logout:'注销中...'
		   }
	}
	return IConstant;
})

.factory('Util', function($ionicModal,$ionicPopup){
	function modal($scope,templateUrl,options){
		function init(){
			try {
				if($scope.modal){
					$scope.modal.show();
				}else{
					var defaults = angular.extend({
			            scope:$scope,
			            animation: 'slide-in-up',
			            focusFirstInput: true
			          },options);
					$ionicModal.fromTemplateUrl(templateUrl,  defaults).then(function(modal){
						$scope.modal = modal;
						$scope.modal.show();
				   });
				}
			} catch (e) {
				var defaults = angular.extend({
		            scope:$scope,
		            animation: 'slide-in-up',
		            focusFirstInput: true
		          },options);
				$ionicModal.fromTemplateUrl(templateUrl,  defaults).then(function(modal){
						$scope.modal = modal;
						$scope.modal.show();
				});
			}
		}
    	function show(){
    		init();
    	};
    	function hide(){
    		$scope.modal.hide();
    	};
    	function remove(){
    		$scope.modal.remove();
    	};
    	return {
    		show:show,
    		hide:hide,
    		remove:remove
    	};
	}
	
	function Popup(title,content,callBack){
		$ionicPopup.alert({
		       title: title || '提示',
		       content: content,
		       buttons: [
		                 {
		                   text: '确定',
		                   type: 'button-calm'
		                 }
		               ]
		      }).then(callBack);
	}
	
	return {
		modal:modal,
		popup:Popup
	}
})
.factory('sessionRecoverer', ['$q', '$injector','$location', function($q, $injector,$location) {
        var sessionRecoverer = {
            request: function (config) {
                return config || $q.when(config);
            },
            response:function(response)
            {            
                switch (response.status) {
                    case (200):
                        if(response.data==401){
                        	$location.path("/user/login");
                        };
                        break;
                    case (500):
                    	//console.log("服务器系统内部错误");
                        break;
                    case (401):
                    	//console.log("未登录");
                        break;
                    case (403):
                    	//console.log("无权限执行此操作");
                        break;
                    case (408):
                    	//console.log("请求超时");
                        break;
                    default:
                    	//console.log("未知错误");
                }
                return response;
            }
        };
        return sessionRecoverer;
    }]).config(['$httpProvider', function($httpProvider) {
    	$httpProvider.defaults.withCredentials = true;
        $httpProvider.interceptors.push('sessionRecoverer');
    }])
;

