angular.module('starter.controllers', ['ngResource'])

.controller('TabCtrl', function($scope,$ionicNavBarDelegate,UrlUtil) {
	$scope.UrlUtil = UrlUtil;
	$scope.myGoBack = function() {
	    $ionicNavBarDelegate.back();
	  };
})

.controller('TestCtrl', function($scope, $ionicScrollDelegate, $timeout) {
	  $scope.items = [];
	  function addImage() {
	    var i = $scope.items.length;
	    $scope.items.push({
	      text: 'Item ' + i,
	      image: 'http://placekitten.com/'+(100+50%i)+'/'+(100+50%i)
	    });
	  }
	  for (var i = 0; i < 20; i++) addImage();

	  $scope.scrollBottom = $ionicScrollDelegate.scrollBottom;

	  $scope.loadMore = function() {
	    $timeout(function() {
	      var n = 100;//1 + Math.floor(4*Math.random());
	      for (var i = 0; i < n; i++) addImage();
	      $scope.$broadcast('scroll.infiniteScrollComplete');
	      $scope.$broadcast('scroll.refreshComplete');
	    }, 1);
	  };
	})

.controller('DashCtrl', function($scope,Gallery,$ionicSlideBoxDelegate,Home,UrlUtil,$timeout) {
	$scope.pages = Gallery;
	$scope.nextSlide = function(index) {
	    if(index == $ionicSlideBoxDelegate.$getByHandle("banner-handle").slidesCount()-1){
	    	$timeout(function(){
	    		$ionicSlideBoxDelegate.$getByHandle("banner-handle").slide(0, 0);
	    	},3000)
	    }
	};
	Home.index().query(function(data){
		$scope.UrlUtil = UrlUtil;
		$scope.map = data;
		$scope.show = true;
	});
	$timeout(function(){
		$ionicSlideBoxDelegate.$getByHandle("banner-handle").update();
	},1000)
})
//借款管理
.controller('borrowCtrl', function($scope) {
	$scope.$on('to-title', function(event,data) {
	    $scope.title = data;	   //标题
	});
})
.controller('borrowOneCtrl', function($scope,UrlUtil,UserCenter,$timeout,User,$location,$stateParams) {
	$scope.$emit('to-title', '招标中');
	$scope.items=[];
	UserCenter.param.queryType ='2';
	UserCenter.param.curPage=1;
	  //上拉刷新
	  $scope.doRefresh = function() {
		  UserCenter.param.curPage=1;
		  UserCenter.homeBorrowRecycleList().query(function(response){
			  $scope.items = response.pageBean.page;
			  // 停止广播ion-refresher
			  $scope.$broadcast('scroll.refreshComplete');
			});
	  };  
	  UserCenter.param.hasmore = true;
	  //更多
	  $scope.loadMore = function() {
		  $timeout(function() {
			  if(!UserCenter.param.hasmore){
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  return;
			  }
			  UserCenter.homeBorrowRecycleList().query(function(response){
				  UserCenter.param.hasmore = response.pageBean.page.length>0;
				  for(var i=0;i<response.pageBean.page.length;i++){
					  $scope.items.push(response.pageBean.page[i]);
				  }
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  UserCenter.param.curPage++;
			  });
		  },1000);
	  };
	  $scope.moreDataCanBeLoaded = function(){
		  return UserCenter.param.hasmore;
	  }
	  $scope.$on('stateChangeSuccess', function() {
		    $scope.loadMore();
	  });
})
.controller('borrowTwoCtrl', function($scope,UrlUtil,UserCenter,$timeout,User,$location,$stateParams) {
	$scope.$emit('to-title', '回款中');
	$scope.items=[];
	UserCenter.param.queryType ='3';
	UserCenter.param.curPage=1;
	  //上拉刷新
	  $scope.doRefresh = function() {
		  UserCenter.param.curPage=1;
		  UserCenter.homeBorrowRecycleList().query(function(response){
			  $scope.items = response.pageBean.page;
			  // 停止广播ion-refresher
			  $scope.$broadcast('scroll.refreshComplete');
			});
	  };  
	  UserCenter.param.hasmore = true;
	  //更多
	  $scope.loadMore = function() {
		  $timeout(function() {
			  if(!UserCenter.param.hasmore){
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  return;
			  }
			  UserCenter.homeBorrowRecycleList().query(function(response){
				  UserCenter.param.hasmore = response.pageBean.page.length>0;
				  for(var i=0;i<response.pageBean.page.length;i++){
					  $scope.items.push(response.pageBean.page[i]);
				  }
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  UserCenter.param.curPage++;
			  });
		  },1000);
	  };
	  $scope.moreDataCanBeLoaded = function(){
		  return UserCenter.param.hasmore;
	  }
	  $scope.$on('stateChangeSuccess', function() {
		    $scope.loadMore();
	  });
})
.controller('borrowThreeCtrl', function($scope,UrlUtil,UserCenter,$timeout,User,$location,$stateParams) {
	$scope.$emit('to-title', '已回款');
	$scope.items=[];
	UserCenter.param.queryType ='4';
	UserCenter.param.curPage=1;
	  //上拉刷新
	  $scope.doRefresh = function() {
		  UserCenter.param.curPage=1;
		  UserCenter.homeBorrowRecycleList().query(function(response){
			  $scope.items = response.pageBean.page;
			  // 停止广播ion-refresher
			  $scope.$broadcast('scroll.refreshComplete');
			});
	  };  
	  UserCenter.param.hasmore = true;
	  //更多
	  $scope.loadMore = function() {
		  $timeout(function() {
			  if(!UserCenter.param.hasmore){
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  return;
			  }
			  UserCenter.homeBorrowRecycleList().query(function(response){
				  UserCenter.param.hasmore = response.pageBean.page.length>0;
				  for(var i=0;i<response.pageBean.page.length;i++){
					  $scope.items.push(response.pageBean.page[i]);
				  }
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  UserCenter.param.curPage++;
			  });
		  },1000);
	  };
	  $scope.moreDataCanBeLoaded = function(){
		  return UserCenter.param.hasmore;
	  }
	  $scope.$on('stateChangeSuccess', function() {
		    $scope.loadMore();
	  });
})

.controller('AboutCtrl', function($scope) {})

.controller('IntroduceCtrl', function($scope) {})

.controller('HelpCtrl', function($scope) {})

.controller('ContactCtrl', function($scope) {})

.controller('ZhishiCtrl', function($scope) {})

.controller('QuanliCtrl', function($scope) {})

.controller('ServiceagreeCtrl', function($scope) {})

.controller('DisclaimerCtrl', function($scope) {})

.controller('moneyManagerCtrl', function($scope,$ionicSideMenuDelegate) {
	$scope.toggleRight = function() {
	    $ionicSideMenuDelegate.toggleRight();
	  };
})
//资金流水记录
.controller('detailCtrl', function($scope,UrlUtil,UserCenter,$timeout,User,$location) {
	  $scope.UrlUtil = UrlUtil;
	  $scope.items = [];
	  $scope.map = UserCenter.getFundListInit().query();
  	UserCenter.param.curPage=1;
  	  //上拉刷新
  	  $scope.doRefresh = function() {
  		  UserCenter.param.curPage=1;
  		  UserCenter.getFundList().query(function(response){
  			  $scope.items = response.pageBean.page;
  			  // 停止广播ion-refresher
  			  $scope.$broadcast('scroll.refreshComplete');
  			});
  	  };  
  	  UserCenter.param.hasmore = true;
  	  //更多
  	  $scope.loadMore = function() {
  		  $timeout(function() {
  			  if(!UserCenter.param.hasmore){
  				  $scope.$broadcast('scroll.infiniteScrollComplete');
  				  return;
  			  }
  			  UserCenter.getFundList().query(function(response){
  				  UserCenter.param.hasmore = response.pageBean.page.length>0;
  				  for(var i=0;i<response.pageBean.page.length;i++){
  					  $scope.items.push(response.pageBean.page[i]);
  				  }
  				  $scope.$broadcast('scroll.infiniteScrollComplete');
  				  UserCenter.param.curPage++;
  			  });
  		  },1000);
  	  };
  	  $scope.moreDataCanBeLoaded = function(){
  		  return UserCenter.param.hasmore;
  	  }
  	  $scope.$on('stateChangeSuccess', function() {
  		    $scope.loadMore();
  	  });
})
.controller('rechargeCtrl', function($scope) {
	
})
//提现
.controller('withdrawCtrl', function($scope,UserCenter) {
	$scope.map = UserCenter.withdrawLoad().query();
})
.controller('bankCtrl', function($scope,UserCenter) {
	UserCenter.mybank().query(function(data){
		$scope.item = data.cardlist;
		$scope.show = true;
		$(function() {
		    $( "#accordion" ).accordion({
		      collapsible: true,
		      heightStyle: "content"
		    });
	    });
	});
})

.controller('userInfoCtrl', function($scope,$ionicSideMenuDelegate) {
	$scope.toggleRight = function() {
	    $ionicSideMenuDelegate.toggleRight();
	  };
})
//安全中心
.controller('infoCtrl', function($scope,UrlUtil,$location,User,UserCenter,$ionicModal,Util,$ionicLoading) {
	$scope.UrlUtil = UrlUtil;
	UserCenter.securityCent().query(function(response){
		if(response!=-1){
			$scope.map = response;
		}
	});
	var modal = Util.modal($scope,'realname.html');
	$scope.startRealName = function(){
		$scope.data = {};
		modal.show();
	};
	$scope.hideRealName = function(){
		modal.hide();
	};
	$scope.identifier = function(valid){
		if(!valid){
			return;
		}
		$ionicLoading.show({
			templateUrl:'templates/loading.html'
		})
		UserCenter.param["paramMap.realName"] = $scope.data.realName;
		UserCenter.param["paramMap.idNo"] = $scope.data.cardNo;
		UserCenter.identifier().success(function(response){
			$ionicLoading.hide();
			Util.popup('提示',response.msg,function(res){
				$scope.map = UserCenter.securityCent().query();
				$scope.hideRealName();
			})
		});
	};
})
.controller('realNameCtrl', function($scope) {
	
})
.controller('forgetPwdCtrl', function($scope,Sms,$interval,User,$location,$ionicPopup) {
	$scope.paramMap = {};
	$scope.init = function() {
		i=0;
		$scope.ok_email = false;
		$scope.ok_code = false;
    }
	$scope.$watch('paramMap.cellcode', function(newValue,oldValue,scope){
		if(newValue && newValue.length==4){
			Sms.param["paramMap.cellphone"] = $scope.paramMap.phone;
			Sms.param["paramMap.cellcode"] = $scope.paramMap.cellcode;
			Sms.validateCode().success(function(data){
				if(data==33){
					$scope.errorInfo = "";
					$scope.ok_code = true;
		        }else if(data==34){
		        	$scope.errorInfo = "验证码不正确";
		        }else if(data==35){
		        	$scope.errorInfo = "验证码已过期";
		        }
			})
		}
	});
	
	$scope.updatePwd = function(valid){
		if(!valid){
			return;
		}else{
			User.param["paramMap.phone"] = $scope.paramMap.phone;
			User.param["paramMap.newPassword"] = $scope.paramMap.password;
			User.param["paramMap.confirmpassword"] = $scope.paramMap.confirmPassword;
			User.updateLoginPwd().success(function(data){
				if(data==1){
					$ionicPopup.alert({
					       title: '提示',
					       content: '修改成功',
					       buttons: [
					                 {
					                   text: '确定',
					                   type: 'button-calm'
					                 }
					               ]
					      }).then(function(res) {
					    	  $location.path("/user/login");
					      });
				     }else if(data==3){
				    	 $scope.errorInfo = "新密码不能为空";
				     }else if(data==4){
				    	 $scope.errorInfo = '出现未知问题，请联系客服';
				     }else if(data==0){
				    	 $scope.errorInfo = "修改密码失败,请重新填写";
				     }else if(data==5){
				    	 $scope.errorInfo = "两次输入密码不相同,请从新输入";
				     }else if(data==6){
				    	 $scope.errorInfo = "密码长度范围为6-20";
				     }else if(data==7){
				    	 $scope.errorInfo = "请输入邮箱或者电话号码修改";
				     }else if(data==10){
				    	 $scope.errorInfo = "登录不能与交易密码相同，请重新输入";
				     }
			});
		}
	};
	
	$scope.sendMail = function(){
		Sms.param["paramMap.email"] = $scope.paramMap.email;
		Sms.sendMail().success(function(data){
			if(data.mailAddress=='0'){
				    $scope.errorInfo = "请输入正确的邮箱";
				    $scope.ok_email = false;
		        }else if(data.mailAddress=='1'){
		        	$scope.errorInfo = "该邮箱不存在";
		        	$scope.ok_email = false;
		        }else{
		          codeTime();
		          $scope.ok_email = true;
		          $scope.paramMap.mailAddress = data.mailAddress;
		          $scope.errorInfo = "";
		        }
		});
	};
	
	$scope.sendSms = function(){
		Sms.param["paramMap.cellphone"] = $scope.paramMap.phone;
		Sms.sendSmsNoReg().success(function(data){
			if(data=="-1"){
                $scope.errorInfo = "验证码发送失败,请重试";
            }else if(data=="-2"){
            	$scope.errorInfo = "请输入您的手机号码";
            }else if(data=="1"){
            	$scope.errorInfo = "";
            	codeTime()
            }else if(data=="3"){
            	$scope.errorInfo = "对不起,您今日的短信发送量已上限";
            }else if(data=="-3"){
            	$scope.errorInfo = "您输入的手机号不存在";
            }
		});
	};
	var i = 60;
	function codeTime(){
		$scope.start = true;
		var smsTime = $interval(function(){
			if(i==0){
				cancel();
			}else{
				$scope.codetime = (--i)+"秒";
			}
		},1000);
		function cancel(){
			i=60;
			$scope.start = false;
			$scope.codetime = "重新发送";
			$interval.cancel(smsTime);
		}
	}
})
//投资列表
.controller('FriendsCtrl', function($scope, FinancList,UrlUtil,$ionicListDelegate,$timeout) { 
  $scope.UrlUtil = UrlUtil;
  $scope.items = [];
  FinancList.param['paramMap.curPage']=1;
  //上拉刷新
  $scope.doRefresh = function() {
	  FinancList.param['paramMap.curPage']=1;
	  FinancList.getList().query(function(response){
		  $scope.items = response.pageBean.page;
		  // 停止广播ion-refresher
		  $scope.$broadcast('scroll.refreshComplete');
		});
	  FinancList.param['paramMap.curPage']++;
  };  
  FinancList.param.hasmore = true;
  //更多
  $scope.loadMore = function() {
	  $timeout(function() {
		  if(!FinancList.param.hasmore){
			  $scope.$broadcast('scroll.infiniteScrollComplete');
			  return;
		  }
		  FinancList.getList().query(function(response){
			  FinancList.param.hasmore = response.pageBean.page.length>0;
			  for(var i=0;i<response.pageBean.page.length;i++){
				  $scope.items.push(response.pageBean.page[i]);
			  }
			  $scope.$broadcast('scroll.infiniteScrollComplete');
			  FinancList.param['paramMap.curPage']++;
		  });
	  },1000);
  };
  $scope.moreDataCanBeLoaded = function(){
	  return FinancList.param.hasmore;
  }
  $scope.$on('stateChangeSuccess', function() {
	    $scope.loadMore();
  });
  $ionicListDelegate.showReorder(true);
})

.controller('InvestCtrl', function($scope,FinancDetail,$ionicLoading) {
	   $scope.data = {};
        $scope.hideModal = function() {
          $scope.modal.hide();
        };
        $scope.removeModal = function() {
          $scope.modal.remove();
        };
        $scope.investModal = function(valid) {
        	if(!valid){
        		if(!$scope.data.money){
        			$scope.money = true;
        			return;
        		}
        		if(!$scope.data.password){
        			$scope.password = true;
        			return;
        		}
        	}
        	$scope.password = false;
        	$scope.money = false;
        	var params = {};
        	params['paramMap.id'] = $scope.investId;
            params['wap'] = 'wap';
        	params['paramMap.annualRate'] = $scope.map.investDetailMap.annualRate;
        	params["paramMap.deadline"] = $scope.map.investDetailMap.deadline;
        	params['paramMap.amount'] = $scope.data.money;
        	params['paramMap.dealPWD'] = $scope.data.password;
        	
        	if(isNaN(params['paramMap.amount'])|| params['paramMap.amount']<0){
        		$scope.openPopup("请输入正确的金额");
        		return;
        	}else if(params['paramMap.amount'].toString().indexOf(".")>0){
        		$scope.openPopup("请输入正确的金额,只能是整数");
        		return;
        	}
        	
        	$ionicLoading.show({
    			templateUrl:'templates/loading.html'
    		});
        	
        	FinancDetail.invest(params).save(function(response){
        		$ionicLoading.hide();
        		if(response.msg==1){
        			$scope.openPopup("投标成功！");
        			$scope.modal.hide();
        			$scope.$emit("updateFriend");
        		}else if(response.updatePwd){
        			$scope.openPopup("为了您的账户安全，交易密码不能与登录密码相同，请立即修改交易密码。");
        		}else{
        			$scope.openPopup(response.msg);
        		}
        	})
        };
})

.controller('FriendDetailCtrl', function($scope, $stateParams, FinancDetail,User,IConstant,$ionicModal,$location,$ionicPopup) {
    $( "#accordion" ).accordion({
      collapsible: true,
      heightStyle: "content"
    });
    $scope.show = false;
	$scope.openModal = function() {
	   $scope.modal.show();
	 };
	 $ionicModal.fromTemplateUrl('templates/invest/invest.html', function(modal) {
	   $scope.modal = modal;
	 }, {
	   scope:$scope,
	   animation: 'slide-in-up',
	   focusFirstInput: true
	 });
  
  $scope.$on("updateFriend",function(){
	  $scope.friend = FinancDetail.getDetail().query({id: $stateParams.id,wap:'wap'});
  })
	 
  $scope.friend = FinancDetail.getDetail().query({id: $stateParams.id,wap:'wap'},function(response){
	  $scope.show = true;
  });
  $scope.invest = function(id){
	  var promise = User.hasLogin().query(); // 同步调用，获得承诺接口  
	    promise.then(function(data) {  // 调用承诺API获取数据 .resolve  
	        if(data.error==1){
	        	$scope.investId = id;
	        	FinancDetail.investInit({id:id}).query(function(response){
	     		   if(response.msg){
	     			  $scope.openPopup(response.msg);
	     		   }else{
	     			  $scope.map = response;
	     			  $scope.openModal();
	     		   }
	     	   });
	        }else{
	        	$location.path('/user/login');
	        }
	    }, function(data) {  // 处理错误 .reject  
	    	$location.path('/user/login');
	    });
  }
   $scope.openPopup = function(content) {
      $ionicPopup.alert({
       title: '提示',
       content: content,
       buttons: [
                 {
                   text: '确定',
                   type: 'button-calm'
                 }
               ]
      }).then(function(res) {});
    };
  /*添加新的事件，我们把大图片的ngSrc指令绑定到mainImageUrl属性上。
	同时我们注册一个ngClick处理器到缩略图上。当一个用户点击缩略图的任意一个时，这个处理器会使用setImage事件处理函数来    把mainImageUrl属性设置成选定缩略图的URL。
    <img ng-src="{{img}}" ng-click="setImage(img)">
  */
  $scope.setImage = function(imageUrl) {
    $scope.mainImageUrl = imageUrl;
  }
})
//用户中心
.controller('AccountCtrl', function($scope,User,UrlUtil,$location,UserCenter,$ionicLoading,$ionicPopup) {
	$scope.UrlUtil = UrlUtil;
	UserCenter.home().success(function(response){
		$scope.show = true;
		$scope.usercenter = response;
	});
    $scope.logout = function(){
    	var confirmPopup = $ionicPopup.confirm({
            title: '退出',
            scope:$scope,
            template: '<span class=\'text-center\'>您确定要退出三好贷?</span>',
            buttons: [
                      { text: '取消' },
                      {
                        text: '确定',
                        type: 'button-calm',
                        onTap: function(e) {
                        	$ionicLoading.show({template:'注销中...'});
                        	User.logout().success(function(){
                        		$ionicLoading.hide();
                        		$location.path('/user/login');
                        	});
                          }
                      }
                    ]
          });
 	};
})

.controller('PhoneListCtrl',function($scope, Phone) {
  $scope.phones = Phone.query();
  $scope.orderProp = 'age';
})

.controller('PhoneDetailCtrl',function($scope, $routeParams, Phone,$ionicLoading) {
  $scope.phone = Phone.get({phoneId: $routeParams.phoneId}, function(phone) {
    $scope.mainImageUrl = phone.images[0];
  });

  $scope.setImage = function(imageUrl) {
    $scope.mainImageUrl = imageUrl;
  }
})
.controller('TestCtrl', function($scope, $interval) {
	$scope.pages = [1,2,3,4];
  })
.controller('registerSuccessCtrl',function(){
	
})
//注册第二步
.controller('RegisterTwoCtrl', function($scope,Sms,$interval,$ionicLoading,User,$location) {
	User.getPhone().query(function(data){
		$scope.phone = data.msg;
	})
	$scope.paramMap = {};
	
	$scope.register = function(isValid){
		if(!isValid){
			return;
		}else{
			var param = {};
			param['paramMap.cellcode'] = $scope.paramMap.cellcode;
			param['paramMap.tradPassWord'] = $scope.paramMap.dealPassword;
			param['paramMap.reTradPassWord'] = $scope.paramMap.confirmDealPassword;
			User.register(param,3).save(function(data){
				if(data.code!=6){
					$scope.errorInfo = data.msg;
					$scope.error = true;
				}else{
					$location.path("/user/registerSuccess");
				}
	         });
		}
	}
	
	$scope.sendSms = function(btn){
	  	$ionicLoading.show({
			template:'短信发送中...'
		});
		Sms.sendSms(true).success(function(response){
			$ionicLoading.hide();
			codeTime();
		});
		
		function codeTime(){
			var i = 60;
			$scope.start = true;
			var smsTime = $interval(function(){
				if(i==0){
					i=60;
					$scope.start = false;
					$scope.codetime = "重新发送";
					$interval.cancel(smsTime);
				}else{
					$scope.codetime = (--i)+"秒后重新发送";
				}
			},1000);
		}
	};
})
//注册第一步
.controller('RegisterCtrl', function($scope,$stateParams,UrlUtil,User,$location,$ionicLoading,Util) {
	//使用条款&隐私条款
	$scope.regProtocol = function(tempalte){
		if(tempalte){//使用条款
			
		}else{//隐私条款
			
		}
	};
	
	//$scope.refferee = true;
	$scope.UrlUtil = UrlUtil;
	$scope.paramMap = {};
	
	//推荐人
	$scope.paramMap.refferee = $stateParams.id||'';
	
	$scope.key = 'a55562e4790170209fcf2bd6b8267cf2';
	var param = {};
	$scope.error = false;
	$scope.register = function(isValid){
		$scope.error = false;
		if(!isValid) return;
		$ionicLoading.show({
			template:'注册中...'
		})
		param["paramMap.type"]=1;
	    param["paramMap.userName"] = $scope.paramMap.userName;
	    param["paramMap.cellphone"] = $scope.paramMap.cellphone;
	    param["paramMap.code"] = $scope.paramMap.code;
	    param["paramMap.password"] = $scope.paramMap.password;
	    param["paramMap.source"]='WAP';
	    param["paramMap.confirmPassword"] = $scope.paramMap.confirmPassword;
	    if($scope.paramMap.refferee){
	    	param["paramMap.refferee"] = $scope.paramMap.refferee;
	    }else{
	    	param["paramMap.refferee"] = "";
	    }
		User.register(param,1).success(function(data){
					   $ionicLoading.hide();
					   if(data.code==99){
						   $location.path('/user/registerTwo');
					   }else if(data.code==16){
						   $scope.errorInfo = "注册失败！";
						   $scope.error = true;
					   }else{
						   $scope.errorInfo = data.msg;
						   $scope.error = true;	           
					   }
					   if($scope.error){
						   $scope.switchCode();
					   }
		         })
	};
	
	var timenow = new Date();
	$scope.switchCode = function(){
 	    $("#code").attr("src",UrlUtil.root+"admin/imageCode.mht?pageId=userreg&d="+timenow.getTime());
 	};
 	$scope.timenow = timenow;
 	
})
.controller('LoginCtrl', function($scope, UrlUtil,User,$location,$ionicLoading,IConstant) {
	    $scope.loginData = {};
	    $scope.UrlUtil = UrlUtil;
	    
	    var timenow = new Date();
	 	$scope.switchCode = function(){
	 	    $("#code").attr("src",UrlUtil.root+"admin/imageCode.mht?pageId=userlogin&d="+timenow.getTime());
	 	}
	 	$scope.timenow = timenow;
	 	
	 	$scope.signup = function(isValid){
	 		if(!$scope.loginData.username){
	 			$scope.loginData.usernameError = IConstant["LoginFormError"]["UserName_Require"];return;
	 		}else{
	 			$scope.loginData.usernameError = '';
	 		}
	 		if(!$scope.loginData.password){
	 			$scope.loginData.passwordError = IConstant["LoginFormError"]["UserPwd_Require"];return;
	 		}else{
	 			$scope.loginData.passwordError = '';
	 		}
	 		
	 		$ionicLoading.show({
	 			template: IConstant["LoadingTeml"]["Login"]
	 		});
	 		var paramMap = {};
	 		paramMap["paramMap.email"] = $scope.loginData.username;
	 		paramMap["paramMap.password"] = $scope.loginData.password;
	 		paramMap["paramMap.code"] = $scope.loginData.code.toString();
	 		paramMap["paramMap.pageId"] = 'userlogin';
	 		paramMap["paramMap.wap"] = 'userlogin';
	 		User.login(paramMap).success(function(data){
	 			$ionicLoading.hide();
	 			if(data.error == 1){
	 				User.user_key = data.user_key;
	 				$location.path("/tab/account");
	 			}else if (data[0] == 2) {
	 				$scope.loginData.loginError = IConstant["LoginError"]["Code"];
	 			} else if (data[0] == 3) {
	 				$scope.loginData.loginError =IConstant["LoginError"]["NoMatchUP"];
	 			} else if (data[0] == 4) {
	 				$scope.loginData.loginError = IConstant["LoginError"]["UserDisable"];
	 			}else if (data[0] == 8) {
	 				$scope.loginData.loginError =IConstant["LoginError"]["HasTwo"];
	 			}else if (data[0] == 9) {
	 				$scope.loginData.loginError =IConstant["LoginError"]["HasOne"];
	 			}else if (data[0] == 5) {
	 				$scope.loginData.loginError =IConstant["LoginError"]["Freeze"];
	 			}else if (data[0] == 7) {
	 				$scope.loginData.loginError =IConstant["LoginError"]["Exception"];
	 			}else{
	 				User.user_key = data[0].user_key;
	 				$location.path("/tab/account");
	 			}
	 			$scope.switchCode();
	 		});
	 	}
	 	$scope.goback = function(){
	 		$location.path("/tab/dash");
	 	}
  })
  //咨询
.controller('consultCtrl', function($scope,$location) {
	$scope.$on('to-title', function(event,data) {
	    $scope.title = data;	   //标题
	});
	$scope.back = function(){
		$location.path("#/tab/dash");
	}
})
//公告
.controller('noticeCtrl', function($scope,About,$timeout ) {
	$scope.$emit('to-title', '公告');	
	$scope.items = [];
	About.param["pageBean.pageNum"]=1;
	  //上拉刷新
	  $scope.doRefresh = function() {
		  About.param["pageBean.pageNum"]=0;
		  About.frontQueryNewsListPage().query(function(response){
			  $scope.items = response.page;
			  // 停止广播ion-refresher
			  $scope.$broadcast('scroll.refreshComplete');
			});
		  About.param["pageBean.pageNum"]++;
	  };  
	  About.param.hasmore = true;
	  //更多
	  $scope.loadMore = function() {
		  $timeout(function() {
			  if(!About.param.hasmore){
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  return;
			  }
			  About.frontQueryNewsListPage().query(function(response){
				  About.param.hasmore = response.page.length>0;
				  for(var i=0;i<response.page.length;i++){
					  $scope.items.push(response.page[i]);
				  }
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  About.param["pageBean.pageNum"]++;
			  });
		  },1000);
	  };
	  $scope.moreDataCanBeLoaded = function(){
		  return About.param.hasmore;
	  };
	  $scope.$on('stateChangeSuccess', function() {
		    $scope.loadMore();
	  });
})
//公告详情
.controller("noticeDetailCtrl",function($scope,About,$stateParams){
	$scope.noticeDetail = About.frontNewsDetails().query({id: $stateParams.id,wap:'wap'},function(response){
		  $scope.show = true;
	});
})
//三好贷动态
.controller('dynamicCtrl', function($scope,About,$timeout,UrlUtil) {
	$scope.UrlUtil = UrlUtil;
	$scope.$emit('to-title', '三好贷动态');	
	$scope.items = [];
	About.param["curPage"]=1;
	  //上拉刷新
	  $scope.doRefresh = function() {
		  About.param["curPage"]=1;
		  About.queryMediaReportListPage2().query(function(response){
			  $scope.items = response.page;
			  // 停止广播ion-refresher
			  $scope.$broadcast('scroll.refreshComplete');
			});
	  };  
	  About.param.hasmore = true;
	  //更多
	  $scope.loadMore = function() {
		  $timeout(function() {
			  if(!About.param.hasmore){
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  return;
			  }
			  About.queryMediaReportListPage2().query(function(response){
				  About.param.hasmore = response.page.length>0;
				  for(var i=0;i<response.page.length;i++){
					  $scope.items.push(response.page[i]);
				  }
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  About.param["curPage"]++;
			  });
		  },1000);
	  };
	  $scope.moreDataCanBeLoaded = function(){
		  return About.param.hasmore;
	  };
	  $scope.$on('stateChangeSuccess', function() {
		    $scope.loadMore();
	  });
})
//互联网金融
.controller('financeCtrl', function($scope,About,$timeout,UrlUtil) {
	$scope.UrlUtil = UrlUtil;
	$scope.$emit('to-title', '互联网金融');
	$scope.items = [];
	About.param["curPage"]=1;
	  //上拉刷新
	  $scope.doRefresh = function() {
		  About.param["curPage"]=1;
		  About.queryMediaReportListPage1().query(function(response){
			  $scope.items = response.page;
			  // 停止广播ion-refresher
			  $scope.$broadcast('scroll.refreshComplete');
			});
	  };  
	  About.param.hasmore = true;
	  //更多
	  $scope.loadMore = function() {
		  $timeout(function() {
			  if(!About.param.hasmore){
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  return;
			  }
			  About.queryMediaReportListPage1().query(function(response){
				  About.param.hasmore = response.page.length>0;
				  for(var i=0;i<response.page.length;i++){
					  $scope.items.push(response.page[i]);
				  }
				  $scope.$broadcast('scroll.infiniteScrollComplete');
				  About.param["curPage"]++;
			  });
		  },1000);
	  };
	  $scope.moreDataCanBeLoaded = function(){
		  return About.param.hasmore;
	  };
	  $scope.$on('stateChangeSuccess', function() {
		    $scope.loadMore();
	  });
})
//咨询详情
.controller("consultDetailCtrl",function($scope,About,$stateParams){
	$scope.consultDetail = About.frontMedialinkId().query({id: $stateParams.id,wap:'wap'},function(response){
		  $scope.show = true;
	});
})
  ;