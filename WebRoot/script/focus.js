/*nav*/
$(function(){
	 $('.index_main_nav li').mouseover(function(){
          $(this).children().next().show();
		  $(this).addClass("on");
     }); 
	 $('.index_main_nav li').mouseout(function(){
          $(this).children().next().hide();
		  $(this).removeClass("on");
     }); 
});

//tab锟叫伙拷
function setTab(name,cursel,n){
for(i=1;i<=n;i++){
var menu=document.getElementById(name+i);
var con=document.getElementById("con_"+name+"_"+i);
menu.className=i==cursel?"hover":"";
con.style.display=i==cursel?"block":"none";
}
}