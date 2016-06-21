// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic',"w5c.validator", "ui.bootstrap", "hljs","ngMessages"/*,'chieffancypants.loadingBar'*/,'ngAnimate','ngRoute','starter.controllers', 'starter.services', 'starter.filters'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})

.config(function($stateProvider, $urlRouterProvider,$locationProvider,$httpProvider,$ionicConfigProvider,/*cfpLoadingBarProvider,*/w5cValidatorProvider) {
	  //验证框架
	// 全局配置
    w5cValidatorProvider.config({
        blurTrig: true,//光标移除元素后是否验证并显示错误提示信息
        showError: true,//可以是bool和function，每个元素验证不通过后调用该方法显示错误信息，默认true，显示错误信息在元素的后面。
        removeError: true//可以是bool和function，每个元素验证通过后调用该方法移除错误信息，默认true，验证通过后在元素的后面移除错误信息。
    });

    w5cValidatorProvider.setRules({
        email: {
            required: "输入的邮箱地址不能为空",
            email: "输入邮箱地址格式不正确"
        },
        phone: {
            required: "输入的手机号码不能为空",
            email: "输入邮箱地址格式不正确",
            minlength: "密码长度不能小于{minlength}",
            maxlength: "密码长度不能大于{maxlength}",
            pattern:"手机号码格式不正确"
        },
        username: {
            required: "输入的用户名不能为空",
            pattern: "用户名必须输入字母、数字、下划线,以字母开头",
            w5cuniquecheck:"输入用户名已经存在，请重新输入"
        },
        password: {
            required: "密码不能为空",
            minlength: "密码长度不能小于{minlength}",
            maxlength: "密码长度不能大于{maxlength}"
        },
        repeatPassword: {
            required: "重复密码不能为空",
            repeat: "两次密码输入不一致"
        },
        number: {
            required: "数字不能为空"
        },
        code:{
        	required: "验证码不能为空"
        },
        protocol:{
        	required: "请仔细阅读并同意三好贷注册协议"
        }
    });
		
	  //loading的提示
	  //cfpLoadingBarProvider.includeSpinner = false;  
	  // $ionicConfigProvider.views.transition('none');
      // 头部配置  
      $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';  
      $httpProvider.defaults.headers.post['Accept'] = 'application/json, text/javascript, */*; q=0.01';  
      $httpProvider.defaults.headers.post['X-Requested-With'] = 'XMLHttpRequest';
      
	  /**
	   *序列化参数
	   */
	  var param = function(obj) {
	    var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
	    for(name in obj) {
	      value = obj[name];
	      if(value instanceof Array) {
	        for(i=0; i<value.length; ++i) {
	          subValue = value[i];
	          fullSubName = name + '[' + i + ']';
	          innerObj = {};
	          innerObj[fullSubName] = subValue;
	          query += param(innerObj) + '&';
	        }
	      }
	      else if(value instanceof Object) {
	        for(subName in value) {
	          subValue = value[subName];
	          fullSubName = name + '[' + subName + ']';
	          innerObj = {};
	          innerObj[fullSubName] = subValue;
	          query += param(innerObj) + '&';
	        }
	      }
	      else if(value !== undefined && value !== null){
	    	query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
		    //query += name + '=' + value + '&';
	      }
	    }
	    return query.length ? query.substr(0, query.length - 1) : query;
	  };
	  $httpProvider.defaults.transformRequest = [function(data) {
		  return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
	  }];
	
  $stateProvider
  .state('tab', {
      url: "/tab",
      abstract: true,
      templateUrl: "templates/tabs.html",
      controller: 'TabCtrl'
    })
	
    .state('test', {
      url: "/test",
      templateUrl: "templates/test.html",
	  controller: 'TestCtrl'
    })	

    // Each tab has its own nav history stack:
	//首页
    .state('tab.dash', {
      url: '/dash',
      views: {
        'tab-dash': {
          templateUrl: 'templates/tab-dash.html',
          controller: 'DashCtrl'
        }
      }
    })
	//我要投资
    .state('tab.friends', {
      url: '/friends',
      views: {
        'tab-friends': {
          templateUrl: 'templates/tab-friends.html',
          controller: 'FriendsCtrl'
        }
      }
    })
	//标的详情
    .state('friend-detail', {
      url: '/friend-detail/:id',
      controller: 'FriendDetailCtrl',
      templateUrl: 'templates/friend-detail.html'
    })
	//会员中心
    .state('tab.account', {
      url: '/account',
      views: {
        'tab-account': {
          templateUrl: 'templates/tab-account.html',
          controller: 'AccountCtrl'
        }
      }
    })
	//关于我们
	.state('tab.about', {
      url: '/about',
      views: {
        'tab-about': {
          templateUrl: 'templates/tab-about.html',
          controller: 'AboutCtrl'
        }
      }
    })
    .state('tab.introduce', {//用到父view的时候url要加上父url
      url: '/about/introduce',
      views: {
          'tab-about': {
          templateUrl: 'templates/about/introduce.html',
          controller: 'IntroduceCtrl'
        }
      }	  
    })	
    .state('tab.help', {//用到父view的时候url要加上父url
      url: '/about/help',
      views: {
          'tab-about': {
          templateUrl: 'templates/about/help.html',
          controller: 'HelpCtrl'
        }
      }	  
    })	
    .state('tab.contact', {//用到父view的时候url要加上父url
      url: '/about/contact',
      views: {
          'tab-about': {
          templateUrl: 'templates/about/contact.html',
          controller: 'ContactCtrl'
        }
      }	  
    })		
    .state('tab.zhishi', {//用到父view的时候url要加上父url
      url: '/about/zhishi',
      views: {
          'tab-about': {
          templateUrl: 'templates/about/zhishi.html',
          controller: 'ZhishiCtrl'
        }
      }	  
    })			
    .state('tab.quanli', {//用到父view的时候url要加上父url
      url: '/about/quanli',
      views: {
          'tab-about': {
          templateUrl: 'templates/about/quanli.html',
          controller: 'QuanliCtrl'
        }
      }	  
    })		
    .state('tab.serviceagree', {//用到父view的时候url要加上父url
      url: '/about/serviceagree',
      views: {
          'tab-about': {
          templateUrl: 'templates/about/serviceagree.html',
          controller: 'ServiceagreeCtrl'
        }
      }	  
    })	
    .state('tab.disclaimer', {//用到父view的时候url要加上父url
      url: '/about/disclaimer',
      views: {
          'tab-about': {
          templateUrl: 'templates/about/disclaimer.html',
          controller: 'DisclaimerCtrl'
        }
      }	  
    })
    
    //登录、注册、找回密码模块
    .state('user', {
      url: "/user",
      templateUrl: "templates/user/user.html"
    })
    
    //登录
    .state('user.login', {
      url: "/login",
      templateUrl: "templates/login.html",
	  controller: 'LoginCtrl'
    })
    //忘记密码
    .state('user.forgetPwd', {
      url: "/forgetPwd",
      templateUrl: "templates/forgetPwd.html",
	  controller: 'forgetPwdCtrl'
    })	
    //注册
    .state('user.register', {
      url: "/register/:id",
      templateUrl: "templates/register.html",
	  controller: 'RegisterCtrl'
    })	
    //注册第二步
    .state('user.registerTwo', {
      url: "/registerTwo",
      templateUrl: "templates/registerTwo.html",
	  controller: 'RegisterTwoCtrl'
    })
    //注册完成
    .state('user.registerSuccess', {
      url: "/registerSuccess",
      templateUrl: "templates/registerSuccess.html",
	  controller: 'registerSuccessCtrl'
    })
    //条款
    .state('user.reg_terms_and_conditions', {
      url: "/reg_terms_and_conditions",
      templateUrl: "templates/static_page/reg_terms_and_conditions.html"
    })
    //协议
    .state('user.reg_terms_of_use', {
      url: "/reg_terms_of_use",
      templateUrl: "templates/static_page/reg_terms_of_use.html"
    })
    .state('mm', {//mm moneyManager
      url: "/mm",
      abstract: true,
      templateUrl: "templates/usercenter/moneymanager/moneymanger.html",
	  controller: 'moneyManagerCtrl'
    })    
    
    .state('mm.detail', {
    url: "/detail",
    views: {
      'menuContent': {
        templateUrl: "templates/usercenter/moneymanager/fundDetail.html",
        controller: 'detailCtrl'
      }
    }
  })

  .state('mm.recharge', {
    url: "/recharge",
    views: {
      'menuContent': {
        templateUrl: "templates/usercenter/moneymanager/recharge.html",
        controller: 'rechargeCtrl'
      }
    }
  })
    .state('mm.withdraw', {
      url: "/withdraw",
      views: {
        'menuContent': {
          templateUrl: "templates/usercenter/moneymanager/withdraw.html",
          controller: 'withdrawCtrl'
        }
      }
    })

  .state('mm.bank', {
    url: "/bank",
    views: {
      'menuContent': {
        templateUrl: "templates/usercenter/moneymanager/bank.html",
        controller: 'bankCtrl'
      }
    }
  })
  .state('ui', {//ui userinfo
    url: "/ui",
    abstract: true,
    templateUrl: "templates/usercenter/userinfo/userinfomanger.html",
	controller: 'userInfoCtrl'
  })
  .state('ui.info', {
    url: "/info",
    views: {
      'menuContent': {
        templateUrl: "templates/usercenter/userinfo/info.html",
        controller: 'infoCtrl'
      }
    }
  })
  .state('ui.realname', {
    url: "/realname",
    views: {
      'menuContent': {
        templateUrl: "templates/usercenter/userinfo/realname.html",
        controller: 'realNameCtrl'
      }
    }
  })  
  .state('borrow', {
      url: "/borrow",
      abstract: true,
      templateUrl: "templates/usercenter/borrowmanger/tabs.html",
      controller: 'borrowCtrl'
    })
    //招标中
    .state('borrow.1', {
      url: '/1:1',
      views: {
        'borrow-1': {
          templateUrl: 'templates/usercenter/borrowmanger/tab-1.html',
          controller: 'borrowOneCtrl'
        }
      }
    })
    //回款中
    .state('borrow.2', {
      url: '/2:2',
      views: {
        'borrow-2': {
          templateUrl: 'templates/usercenter/borrowmanger/tab-2.html',
          controller: 'borrowTwoCtrl'
        }
      }
    })
    //已回款
    .state('borrow.3', {
      url: '/3:3',
      views: {
        'borrow-3': {
          templateUrl: 'templates/usercenter/borrowmanger/tab-3.html',
          controller: 'borrowThreeCtrl'
        }
      }
    })
  	
  //咨询一块
  .state('consult', {//mm moneyManager
      url: "/consult",
      abstract: true,
      templateUrl: "templates/consult/consult.html",
	  controller: 'consultCtrl'
    })    
    //三好贷动态
    .state('consult.dynamic', {
    url: "/dynamic",
    views: {
      'consult-dynamic': {
        templateUrl: "templates/consult/dynamic.html",
        controller: 'dynamicCtrl'
      }
    }
  })
  //互联网金融 
  .state('consult.finance', {
    url: "/finance",
    views: {
      'consult-finance': {
        templateUrl: "templates/consult/finance.html",
        controller: 'financeCtrl'
      }
    }
  })
   //公告
    .state('consult.notice', {
      url: "/notice",
      views: {
        'consult-notice': {
          templateUrl: "templates/consult/notice.html",
          controller: 'noticeCtrl'
        }
      }
    })
    //公告详情
    .state('notice-detail', {
      url: "/notice-detail/:id",
      templateUrl: "templates/consult/notice-detail.html",
      controller: 'noticeDetailCtrl'
    })
    //咨询详情
    .state('consult-detail', {
      url: "/consult-detail/:id",
      templateUrl: "templates/consult/consult-detail.html",
      controller: 'consultDetailCtrl'
    })
    ;
  
   
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/tab/dash');
})
;