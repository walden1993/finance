//menu
$(function(){
	 $('.index_main_nav li').mouseover(function(){
          $(this).children().next().show();
		  $(this).addClass("on");
     }); 
	 $('.index_main_nav li').mouseout(function(){
          $(this).children().next().hide();
		  $(this).removeClass("on");
     }); 
	 
	 function topsearch(){
		var scrTop=$(window).scrollTop();
		if(scrTop<100){
			$('.top_search').stop(true,false).animate({
				'bottom':'-92px'
			},0);
		}else{
			$('.top_search').stop(true,false).animate({
				'bottom':'0px'
			},400);
			$('.copyright').css('padding-bottom','88px')
		}
	}
	topsearch();
	$(window).scroll(function(){
		topsearch();
	});
	
});

function turnoff(obj){
    document.getElementById(obj).style.display="none";};
	

//tab切换
//function setTab(name,cursel,n){
//for(i=1;i<=n;i++){
//var menu=document.getElementById(name+i);
//var con=document.getElementById("con_"+name+"_"+i);
//menu.className=i==cursel?"hover":"";
//con.style.display=i==cursel?"block":"block";
//}
//}



