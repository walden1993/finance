
lastScrollY=0;
function heartBeat(){ 
var diffY;
if (document.documentElement && document.documentElement.scrollTop)
diffY = document.documentElement.scrollTop;
else if (document.body)
diffY = document.body.scrollTop
else
{/*Netscape stuff*/}
percent=.1*(diffY-lastScrollY); 
if(percent>0)percent=Math.ceil(percent); 
else percent=Math.floor(percent); 
document.getElementById("lovexin12").style.top=parseInt(document.getElementById
("lovexin12").style.top)+percent+"px";
document.getElementById("lovexin14").style.top=parseInt(document.getElementById
("lovexin12").style.top)+percent+"px";
lastScrollY=lastScrollY+percent; 
}
/*suspendcode12="<div id=\"lovexin12\" style='left:5px;POSITION:absolute;TOP:200px;'>&nbsp;</div>"
suspendcode14="<div id=\"lovexin14\" style='right:5px;POSITION:absolute;TOP:200px;'></div>"*/
	
	suspendcode12="<div id=\"lovexin12\" style='left:5px;POSITION:absolute;TOP:200px;'>&nbsp;</div>"
		suspendcode14="<div id=\"lovexin14\" style='right:5px;POSITION:absolute;TOP:200px;'>" +
				"<img src=images/app_android.png width=125px height=125px border=0><br/><img src=images/android.jpg width=125px border=0/><br/>" +
				"<img src=images/app_ios.png width=125px height=125px border=0></a><br/><img src=images/ios.jpg width=125px border=0/>" +
				"</div>"
	
document.write(suspendcode12); 
document.write(suspendcode14); 
window.setInterval("heartBeat()",1);
