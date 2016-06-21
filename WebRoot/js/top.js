$(function(){
	 $('.x_sidenav li').mouseover(function(){
         $(this).children().next().show();
		  $(this).addClass("x_cur")  
    }); 
	
	 $('.x_sidenav li').mouseout(function(){
         $(this).children().next().hide();
		  $(this).removeClass("x_cur") 
    }); 
	 
	 //头部qq，微博，微信弹出
   $('.header .head_top .left .mr10').mouseover(function () {
       var class_id = $(this).attr("data-id");
       $('#' + class_id).show();
       var imgurl = $(this).find('img').first().attr("src").replace('_h', "");
       $(this).find('img').first().attr("src", imgurl.replace('.png', "") + '_h.png');
   }).mouseout(function () {
       var class_id = $(this).attr("data-id");
       $('#' + class_id).hide();
       var imgurl = $(this).find('img').first().attr("src");
       $(this).find('img').first().attr("src", imgurl.replace('_h', ""));
   });
});

/*(function(a){
	a.fn.hoverClass=function(b){
		var a=this;
		a.each(function(c){
			a.eq(c).hover(function(){
				$(this).addClass(b)
			},function(){
				$(this).removeClass(b)
			})
		});
		return a
	};
})(jQuery);

$(function(){
	$("#navbox").hoverClass("current");
});

banner
 控制左右按钮显示 
		jQuery(".fullSlide").hover(function(){ jQuery(this).find(".prev,.next").stop(true,true).fadeTo("show",0.5) },function(){ jQuery(this).find(".prev,.next").fadeOut() });

		 调用SuperSlide 
		jQuery(".fullSlide").slide({ titCell:".hd ul", mainCell:".bd ul", effect:"fold",  autoPlay:true, autoPage:true, trigger:"click",
			startFun:function(i){
				var curLi = jQuery(".fullSlide .bd li").eq(i);  当前大图的li 
				if( !!curLi.attr("_src") ){
					curLi.css("background-image",curLi.attr("_src")).removeAttr("_src")  将_src地址赋予li背景，然后删除_src 
				}
			}
		});

gonggao
jQuery(".txtScroll-top").slide({titCell:".hd ul",mainCell:".bd ul",autoPage:true,effect:"top",autoPlay:true,vis:1});

paiming
jQuery(".txtMarquee-top").slide({mainCell:".bd ul",autoPlay:true,effect:"topMarquee",vis:5,interTime:50,trigger:"click"});

meiti
jQuery(".picScroll-top").slide({titCell:".hd ul",mainCell:".bd ul",autoPage:true,effect:"top",autoPlay:true,scroll:2,vis:2,delayTime:5000,interval:5000});

tab
jQuery(".hasMoreTab").slide({ mainCell:".conWrap", targetCell:".more a", effect:"fold"});
*/
/*hezuolianjie*/
//jQuery(".flowMarquee-top").slide({mainCell:".bd ul",autoPlay:true,effect:"topMarquee",vis:2,interTime:80,trigger:"click"});