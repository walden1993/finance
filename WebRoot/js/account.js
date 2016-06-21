;(function($, win) {
	var GT = {};
    GT.AjaxFun= {};
    jQuery.extend(GT.AjaxFun,{
        defaults : {
            url : '',
            data : '',
            dataType :'json',
            type : 'GET',
            cache : false,
            async : true,
            containerId:'',
            showContent:'',
            selectedTime :''
        },
        setAjaxFun : function(settings){
            var self = this;
            if(typeof(settings)!='undefined'){
                self.defaults = jQuery.extend(this.defaults,settings);
                self.ajaxFunInit();
            }
        },
        ajaxFunInit : function(){
            var self = this;
            $.ajax({
                url : self.defaults.url,
                data : self.defaults.data,
                dataType : self.defaults.dataType,
                type : self.defaults.type,
                cache : self.defaults.cache,
                async : self.defaults.async,
                beforeSend:function(){
                	if($('#'+self.defaults.containerId).parent().find('.loadingStyle').length == 0){
                		$('#'+self.defaults.containerId).append('<div class="loadingStyle"></div>');
                	}
                },
                success : function(data){
                	if(self.defaults.showContent == 'chart'){
                		GT.chartContainerInit(data);
                		GT.receivedPayments();
//                	}else if(self.defaults.showContent == 'incomeChart'){
//                		GT.incomeChartContainerInit(data);
//                		$('#'+self.defaults.containerId).find('.loadingStyle').remove();
                	}else if(self.defaults.showContent == 'foreType'){
                		GT.foreDefaultList(data);
                		$('#'+self.defaults.containerId).find('.loadingStyle').remove();
                	}else if(self.defaults.showContent =='repayment'){
                		GT.repaymentInit(data,self.defaults.selectedTime);
                		$('#'+self.defaults.containerId).find('.loadingStyle').remove();
                	}
                }
            });
        }
    }),
	GT.formatNumber = function(number){
    	var numberVal = '';
    	if(String(number).indexOf(".") > 0){
    		//numberVal = String(number).replace(".","");
    		numberVal = String(number).split('.')[0];
    	}else{
    		numberVal = String(number);
    	}
    	numberVal = numberVal.replace(/\,/g, "");
	    if (numberVal == "") return "";
	    if (numberVal < 0) {
	        return '-' +  GT.outputDollars(Math.floor(Math.abs(numberVal) - 0) + '') + GT.outputCents(Math.abs(numberVal) - 0);
	    } else {
	    	if(String(number).indexOf(".") > 0){
		        return  '￥'+GT.outputDollars(Math.floor(numberVal - 0) + '') + '.'+String(number).split('.')[1];

	    	}else{
		        return  '￥'+GT.outputDollars(Math.floor(numberVal - 0) + '') + GT.outputCents(numberVal - 0);

	    	}
	    }
    },
    GT.outputDollars = function(number) {
        if (number.length <= 3) {
            return (number == '' ? '0' : number);
        } else {
            var mod = number.length % 3;
            var output = (mod == 0 ? '' : (number.substring(0, mod)));
            for (i = 0; i < Math.floor(number.length / 3); i++) {
                if ((mod == 0) && (i == 0)) {
                    output += number.substring(mod + 3 * i, mod + 3 * i + 3);
                } else {
                    output += ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
                }
            }
            return (output);
        }
    },
    GT.outputCents = function(amount) {
        amount = Math.round(((amount) - Math.floor(amount)) * 100);
        return (amount < 10 ? '.0' + amount : '.' + amount);
    },
    GT.ShdVal = function(){
    	var myDate = new Date();
    	var month = '';(myDate.getMonth() + 1) < 10 ? month = '0'+ (myDate.getMonth() + 1) :month = (myDate.getMonth() + 1);
    	var getDateV ='';myDate.getDate() < 10 ? getDateV = '0'+ myDate.getDate() :getDateV = myDate.getDate();
    	var myDateV = myDate.getFullYear() +'-'+ month + '-'+ getDateV;
    	 GT.AjaxFun.setAjaxFun({
    		 url : 'getHuiKuanCalendar.mht',
             data :{date:myDateV},
             containerId:'repaymentUl',
             showContent :'repayment'
         });
    },
    
    GT.repaymentInit = function(data,timeVal){
    	var results = data;
    	var myDate = new Date();
    	var month = '';(myDate.getMonth() + 1) < 10 ? month = '0'+ (myDate.getMonth() + 1) :month = (myDate.getMonth() + 1);
    	var getDateV ='';myDate.getDate() < 10 ? getDateV = '0'+ myDate.getDate() :getDateV = myDate.getDate();
    	var myDateV = myDate.getFullYear() +'-'+ month + '-'+ getDateV;
    	var opposite = true;
		if($('#dateWdate').html() == '' || $('#dateWdate').html() == null){
//			var dateVal = results.dates.split(',');dateVal.push('%y-%M-%d');
			var dateVals = results.dates.split(',');
			if(dateVals == ''){
				//dateVal ='%y-%M-%d';
				opposite = false;
				dateVals = false;
			}
			WdatePicker({
	        	eCont:'dateWdate',
	        	skin:'blue',
	        	opposite:opposite,
	        	//disabledDates:[],
	        	specialDates:dateVals,
	        	minDate:'%y-%M-..-01',
	        	maxDate:'%y-%M-..-%ld',
	        	onpicking:function(dp){
	    			$('#repaymentUl').find('li').not('li:first').remove();
	    			GT.AjaxFun.setAjaxFun({
	    	             url : 'getHuiKuanCalendar.mht',
	    	             data :{date:dp.cal.getNewDateStr()},
	    	             containerId:'repaymentUl',
	    	             showContent :'repayment',
	    	             selectedTime : dp.cal.getNewDateStr()
	    	         })
	        	}
	        })
		}
    	var htmlStr ='',classStr ='';
    	if(results.result != '' && results.result !=null){
    		$.each(results.result,function(i,o){
        		i%2 ? classStr='even':classStr='';
        		htmlStr +='<li class="'+ classStr +'"><span class="repaymentSpan1" title="'+ o.borrowTitle +'"><a href="homeBorrowRecycleList.mht?id='+ o.invest_id +'">'+ o.borrowTitle +'</a></span><span class="repaymentSpan2">'+ GT.formatNumber(o.money) +'</span></li>';
        	});
        	$('#repaymentUl').append(htmlStr);
        	$('.repaymentList').show();
        	$('#dateContainer').find('.noDateListInfo').remove();
    	}else{
    		var errorStr = '';
    		
    		if(timeVal == '' || timeVal == myDateV || timeVal == undefined){
    			errorStr = '今天';
    		}else{
    			errorStr = '&nbsp;'+ timeVal +'&nbsp;';
    		}
    		if($('#dateContainer').find('.noDateListInfo').length > 0){$('#dateContainer').find('.noDateListInfo').remove();}
			$('.repaymentList').after('<div class="noDateListInfo"><i></i><div class="noReceivedPaymentsFont">亲，您'+ errorStr +'木有回款，去看看其他天的吧!</div></div>');
    		$('.repaymentList').hide();
    	}
    	var myDate = new Date();
    	var myDateV = myDate.getFullYear() +'-'+ (myDate.getMonth() + 1) + '-'+ myDate.getDate();
    	var timeStr = '';
    	timeVal == '' ? timeStr = myDateV : timeStr = timeVal;
    	var str = timeStr.split('-')[0]+'年'+timeStr.split('-')[1]+'月'+timeStr.split('-')[2]+'日';
    	$('#systemTime').html(myDate.getFullYear() +'年'+ (myDate.getMonth() + 1) +'月');
    	$('.repaymentTimeTotal').show().html(str+'回款总额：￥'+results.total);
    },
   

	GT.pageInitFun = function(){

		$('#repaymentDate').on('click',function(){
			var self = $(this);var parent = self.parent();
			parent.find('.receivedPaymentsed').removeClass('receivedPaymentsed').addClass('receivedPayments');
			parent.find('.currSocialStatus').removeClass('currSocialStatus');
			self.find('.isNoClick').addClass('currSocialStatus');
			$('.dataList').removeClass('dataList').addClass('dataListed');
			parent.find('.beforeOneTab').removeClass('beforeOneTab');
			self.addClass('beforeOneTab');
			$('#dateContainer').show();
			if($('.noReceivedPayments').length > 0){
				$('.noReceivedPayments').hide();
			}else{
				$('#foreshowList').hide();
			}
			if($('.repaymentUl').find('li').length <= 1){
				var myDate = new Date();
		    	var month = '';(myDate.getMonth() + 1) < 10 ? month = '0'+ (myDate.getMonth() + 1) :month = (myDate.getMonth() + 1);
		    	var getDateV ='';myDate.getDate() < 10 ? getDateV = '0'+ myDate.getDate() :getDateV = myDate.getDate();
		    	var myDateV = myDate.getFullYear() +'-'+ month + '-'+ getDateV;
				GT.AjaxFun.setAjaxFun({
		             url : 'getHuiKuanCalendar.mht',
		             data :{date:myDateV},
		             containerId:'repaymentUl',
		             showContent :'repayment'
		         })
			}
		});
	
	},
	GT.pageInitFun();
	GT.ShdVal();
}(jQuery, window));
