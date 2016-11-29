
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';

     var k = 1024;

     sizes = ['B','KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

     i = Math.floor(Math.log(bytes) / Math.log(k));

	return (bytes / Math.pow(k, i)).toPrecision(5) + ' ' + sizes[i]; 
    //toPrecision(3) 后面保留一位小数，如1.0GB                                                                                                                  //return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}
window.onload=function(){
	//清空数组
	allUrlTitle.splice(0,allUrlTitle.length);//清空数组 
	//
	var urlStr = location.search.substring(1);
	var end=urlStr.indexOf('_CurrentFlag');
	var start=urlStr.substring(0,end).lastIndexOf('&');
	var curName=urlStr.substring(start+1,end);
	
	if($.cookie("bundleID")){ 
		var filesTab=$("#filesTable");
		var filesArr=$.cookie("bundleID").split("&");
		$.each(filesArr,function(i, n)
		{
			var simple=n.split("=");
			var otr=$("<tr class='btnLike' onclick='getAllData("+'"'+simple[1]+'"'+");getData("+'"'+simple[1]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));return false;'></tr>");	
			//otr.html("<td class='name'>包名：</td><td class='existname'>"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));return false;'><span>Analyze</span></button></td>");
			otr.html("<td class='name'>包名：</td><td class='existname'>"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button style='display: none' id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));return false;'><span>Analyze</span></button></td>");
			//otr.html("<td class='name'>包名：</td><td class='existname'"+"onclick='getData("+'"'+simple[1]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));CleanTrClick2($(this));return false;'"+">"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");return false;'"+" style='display: none;'><span>Analyze</span></button></td>");
			//otr.html("<td class='name'>包名：</td><td class='existname'"+"onclick='setTimerGetData("+'"'+simple[1]+'"'+");TrClick2($(this));CleanTrClick2($(this));return false;'"+">"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");return false;'"+" style='display: none;'><span>Analyze</span></button></td>");
			filesTab.append(otr);
			allTitle.push(simple[0]);
			if(curName==simple[0]){
				getAllData(simple[1]);
				getData(simple[1]);
				getSenData(simple[1]);
				getABData(simple[1]);
				TrChange(otr);
			}
		});
		
	}
	
	//获取URL地址栏内容进行显示
	
	var urlStrOp=urlStr.replace("_CurrentFlag","");
	if(urlStrOp!=""){
		var filesTab=$("#filesTable");
		 var urlArr= urlStrOp.split('&');
		 var isHas ;
		 $.each(urlArr,function(i, n){
			
			var simple=n.split("=");
			isHas = arrContains(allTitle,simple[0]);
			if (!isHas) {
				var otr=$("<tr class='btnLike' onclick='getAllData("+'"'+simple[2]+'"'+");getData("+'"'+simple[2]+'"'+");getSenData("+'"'+simple[2]+'"'+");getABData("+'"'+simple[2]+'"'+");TrClick2($(this));return false;'></tr>");	
				//otr.html("<td class='name'>包名：</td><td class='existname'>"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));return false;'><span>Analyze</span></button></td>");
				otr.html("<td class='name'>包名：</td><td class='existname'>"+simple[0]+"</td><td class='size'>包大小：</td>"+"<td>"+bytesToSize(simple[1])+"</td>"+"<td bundleID='"+simple[2]+"' class='analyzeBtn'><button style='display: none' id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));return false;'><span>Analyze</span></button></td>");
				//otr.html("<td class='name'>包名：</td><td class='existname'"+"onclick='getData("+'"'+simple[1]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));CleanTrClick2($(this));return false;'"+">"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");return false;'"+" style='display: none;'><span>Analyze</span></button></td>");
				//otr.html("<td class='name'>包名：</td><td class='existname'>"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));return false;'><span>Analyze</span></button></td>");
				//otr.html("<td class='name'>包名：</td><td class='existname'"+"onclick='setTimerGetData("+'"'+simple[1]+'"'+");TrClick2($(this));CleanTrClick2($(this));return false;'"+">"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");return false;'"+" style='display: none;'><span>Analyze</span></button></td>");
				filesTab.append(otr);
				allTitle.push(simple[0]);
				allUrlTitle.push(simple[0]);
				if(curName==simple[0]){
					getAllData(simple[2]);
					getData(simple[2]);
					getSenData(simple[2]);
					getABData(simple[2]);
					TrChange(otr);
				}
			}
			else{
				var nameLi=filesTab.find(".existname");
				$.each(nameLi,function(i, n){
					if($(n).html()==simple[0]){
						$(n).parent().remove();
						var otr=$("<tr class='btnLike' onclick='getAllData("+'"'+simple[2]+'"'+");getData("+'"'+simple[2]+'"'+");getSenData("+'"'+simple[2]+'"'+");getABData("+'"'+simple[2]+'"'+");TrClick2($(this));return false;'></tr>");	
						//otr.html("<td class='name'>包名：</td><td class='existname'>"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));return false;'><span>Analyze</span></button></td>");
						otr.html("<td class='name'>包名：</td><td class='existname'>"+simple[0]+"</td>td class='size'>包大小：</td>"+"<td>"+bytesToSize(simple[1])+"</td>"+"<td bundleID='"+simple[2]+"' class='analyzeBtn'><button style='display: none' id='analyze' "+"onclick='getData("+'"'+simple[2]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));return false;'><span>Analyze</span></button></td>");
						//otr.html("<td class='name'>包名：</td><td class='existname'"+"onclick='getData("+'"'+simple[1]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));CleanTrClick2($(this));return false;'"+">"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");return false;'"+" style='display: none;'><span>Analyze</span></button></td>");
						//otr.html("<td class='name'>包名：</td><td class='existname'>"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");getSenData("+'"'+simple[1]+'"'+");getABData("+'"'+simple[1]+'"'+");TrClick2($(this));return false;'><span>Analyze</span></button></td>");
						//otr.html("<td class='name'>包名：</td><td class='existname'"+"onclick='setTimerGetData("+'"'+simple[1]+'"'+");TrClick2($(this));CleanTrClick2($(this));return false;'"+">"+simple[0]+"</td><td bundleID='"+simple[1]+"' class='analyzeBtn'><button id='analyze' "+"onclick='getData("+'"'+simple[1]+'"'+");return false;'"+" style='display: none;'><span>Analyze</span></button></td>");
						filesTab.append(otr);
						allTitle.push(simple[0]);
						allUrlTitle.push(simple[0]);
						if(curName==simple[0]){
							getAllData(simple[2]);
							getData(simple[2]);
							getSenData(simple[2]);
							getABData(simple[2]);
							TrChange(otr);
						}
					}
				});
			}
		});
	}
	var oMenu=document.getElementById('menu');
	var ali=oMenu.getElementsByTagName('li');
	var aCon=oMenu.getElementsByClassName('content-wrap');
	
	for(var i=0; i<5; i++){
		(function(index){
			ali[i].onclick=function(){
				for(var i=0; i<5; i++){
					ali[i].className='';
					aCon[i].style.display='none';
				}
				this.className='current';
				aCon[index].style.display='block';	
			}
		})(i);
	}
	$( "#accordionAllSce" ).accordion();
	$( "#accordionRes" ).accordion();
	$( "#accordionSce" ).accordion();
	$( "#accordionAB" ).accordion();
	$('.chatContainerAllSce').css("display","none");
	$('.chatContainer').css("display","none");
	$('.chatContainerSce').css("display","none");
	$('.chatContainerAB').css("display","none");
	$('#repeatRes').css("display","none");
	$('#repeatRes').css("height","auto");
	$( "#tabsAllSce" ).tabs();
	$( "#tabsAllSce" ).css("paddingTop","2px");
	$( "#tabsAllSce" ).css("height","auto");
	$( "#tabsRes" ).tabs();
	$( "#tabsRes" ).css("paddingTop","2px");
	$( "#tabsRes" ).css("height","auto");
	$( "#tabsSce" ).tabs();
	$( "#tabsSce" ).css("paddingTop","2px");
	$( "#tabsSce" ).css("height","auto");
	$( "#tabsAB" ).tabs();
	$( "#tabsAB" ).css("paddingTop","2px");
	$( "#tabsAB" ).css("height","auto");
	
	
}
function TrClick(curThis){
	//tableborder选中行变色与隔行变色
	var tbList = curThis.parent().parent().parent().children("tr");
	tbList.each(function() { 
		var self = this; 
		$(self).children("td").each(function() {
			var selfin = this;
			$(selfin).removeClass('trSelected');
			});
		});
	curThis.parent().parent().children("td").each(function() {
		var selfcur = this;
		$(selfcur).addClass('trSelected');
	});
}
function CleanTrClick(curThis){
	//tableborder选中行变色与隔行变色
	var tbList = curThis.parent().parent().parent().parent().parent().parent().parent().find("#filesTable").find("tr");
	tbList.each(function() { 
		var self = this; 
		$(self).children("td").each(function() {
			var selfin = this;
			$(selfin).removeClass('trSelected');
			});
		});

}
function CleanTrClick2(curThis){
	//tableborder选中行变色与隔行变色
	var tbList = curThis.parent().parent().parent().parent().parent().parent().find("#tableborder").find("tr");
	tbList.each(function() { 
		var self = this; 
		$(self).children("td").each(function() {
			var selfin = this;
			$(selfin).removeClass('trSelected');
			});
		});

}
function TrChange(curThis){
	//tableborder选中行变色与隔行变色
	var tbList = curThis.parent().children("tr");
	tbList.each(function() { 
		var self = this; 
		$(self).children("td").each(function() {
			var selfin = this;
			$(selfin).removeClass('trSelected');
			});
		});
	curThis.children("td").each(function() {
		var selfcur = this;
		$(selfcur).addClass('trSelected');
	});
}
function TrClick2(curThis){
	//tableborder选中行变色与隔行变色
	var tbList = curThis.parent().children("tr");
	tbList.each(function() { 
		var self = this; 
		$(self).children("td").each(function() {
			var selfin = this;
			$(selfin).removeClass('trSelected');
			});
		});
	curThis.children("td").each(function() {
		var selfcur = this;
		$(selfcur).addClass('trSelected');
	});
	var name=curThis.find(".existname").html();
	var urlStr = location.search.replace("_CurrentFlag","");
	if(urlStr.indexOf(name)!=-1){
		location.search=urlStr.replace(name,name+"_CurrentFlag");
	}
	else{
		if(location.search==""){
			location.search=name+"_CurrentFlag"+"="+curThis.find(".analyzeBtn").attr("bundleid");
		}
		else{
			location.href=urlStr+"&"+name+"_CurrentFlag"+"="+curThis.find(".analyzeBtn").attr("bundleid");;
		}
	}
}
function GetCookie(mname)
{
     var cookies= $.cookie(mname).split('&');
     var res=[];
     for(var i=0;i<cookies.length;i++)
     {
    	 one=cookies[i];
         res.push(one);
     }
     return res ;
}
function GetTitleArr(mname)
{
     var cookies= $.cookie(mname).split('&');
     var res=[];
     for(var i=0;i<cookies.length;i++)
     {
    	 var one=cookies[i].split('=');
         res.push(one[0]);
     }
     return res ;
}
function arrContains(arr,element) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == element) {
            return true;
        }
    }
    return false;
}
function createDialog(title,text){
	var div=$("<div></div>");
	div.attr("title",title);
	var p=$("<p></p>");
	p.html(text);
	div.append(p);
	return div;
}
function setbundleIDCookie(addCookie){
	var IDtempl=[];
	if($.cookie("bundleID")){ 
		IDtemp=GetCookie("bundleID");
		IDtemp.push(addCookie);
		//设置Cookie值 
		$.cookie("bundleID",IDtemp.join("&"),{ 
		expires:1,//设置保存期限 
		path:"/"//设置保存的路径 
		});
	}
	else
		{
			$.cookie("bundleID",addCookie,{ 
				expires:1,//设置保存期限 
				path:"/"//设置保存的路径 
				});
		}
}
function deleteBundleIDCookie(delCookie){
	if($.cookie("bundleID")){ 
		var IDtemp=GetCookie("bundleID");
		//删除
		IDtemp.splice($.inArray(delCookie,IDtemp),1);
		//重新设置Cookie值 
		$.cookie("bundleID",IDtemp.join("&"),{ 
		expires:1,//设置保存期限 
		path:"/"//设置保存的路径 
		});
	}
}
function createResourceLi(classid,data,type){
	var resourceLi=$(classid);
	resourceLi.empty();//先清空div内容
	var jsonarray= data[type];
	var oTable=$("<table id='resourceTable'></table>");
	var oth=$("<tr><th>序号</th><th>类名</th><th>资源名称</th><th>资源大小(字节)</th></tr>");
	oTable.append(oth);
	resourceLi.append(oTable);
	$.each(jsonarray,function(i, n)
	{
		var otr=$("<tr title='资源来源于："+n.sourcefile+"'></tr>");
		otr.tooltip();
		otr.html("<td>"+(i+1).toString()+"</td><td>"+n.classname+"</td><td>"+n.name+"</td><td>"+parseFloat(n.size).toLocaleString()+"</td>");
		oTable.append(otr);
	});
	$(classid+">#resourceTable>tbody>tr:odd").addClass("odd"); 
	$(classid+">#resourceTable>tbody>tr:even").addClass("even"); 
	$(classid+">#resourceTable>tbody>tr").mouseover(function () { 
		$(this).toggleClass(".hover"); 
	}); 
	$(classid+">#resourceTable>tbody>tr").mouseout(function () { 
		$(this).toggleClass(".hover"); 
	});
}

function bodyEmpty(){
	$("#repeatRes").empty();
	$(".chatContainerAllSce").empty();
	$(".chatContainer").empty();
	$(".chatContainerSce").empty();
	$(".chatContainerAB").empty();
	$(".allData").empty();
	$(".texture2DAllSce").empty();
	$(".animationClipAllSce").empty();
	$(".audioClipAllSce").empty();
	$(".meshAllSce").empty();
	$(".commeshAllSce").empty();
	$(".lightprobAllSce").empty();
	$(".allResource").empty();
	$(".texture2DRes").empty();
	$(".animationClipRes").empty();
	$(".audioClipRes").empty();
	$(".meshRes").empty();
	$(".allScence").empty();
	$(".texture2DSce").empty();
	$(".animationClipSce").empty();
	$(".audioClipSce").empty();
	$(".meshSce").empty();
	$(".commeshSce").empty();
	$(".lightprobSce").empty();
	$(".allAB").empty();
	$(".texture2DAB").empty();
	$(".animationClipAB").empty();
	$(".audioClipAB").empty();
	$(".meshAB").empty();
	$(".commeshAB").empty();
	$(".lightprobAB").empty();
}
function createRepeatRes(data,repeatResId){
	var resId=$(repeatResId);
	var jsonarray= data.repeatRes;
	var oul=$("<ul id='black' class='treeview-black'>");
	oul.html("<li><span>资源名称(资源类型     资源占用总大小)</span><ul><li><span>资源来源(使用资源大小)</span></li></ul></li>");
	$.each(jsonarray,function(i, n)
	{
		var oli=$("<li></li>");
		var ospan=$("<span>"+n.name+"("+n.classname+"&nbsp;&nbsp;"+bytesToSize(n.size)+")</span>");
		oli.append(ospan);
		var ul=$("<ul></ul>");
		$.each(n.quote,function(j, m)
		{
			var li=$("<li></li>");
			var span=$("<span>"+m.name+"("+bytesToSize(m.size)+")</span>");
			li.append(span);
			ul.append(li);
		});
		oli.append(ul);
		oul.append(oli);
		resId.append(oul);
	});
	$("#black").treeview({
		animated: "fast",
		collapsed: true
	});
}
/*function createRepeatRes(data,repeatResId){
	var resId=$(repeatResId);
	var jsonarray= data.repeatRes;
	$.each(jsonarray,function(i, n)
	{
		var oh3=$("<h3>"+n.name+"</h3>");
		console.log(n.name);
		var odiv=$("<div class='heightAuto'></div>");
		$.each(n.quote,function(j, m)
		{
			var op=$("<p>"+m.name+"</p>");
			console.log(m.name);
			odiv.append(op);
		});
		
		resId.append(oh3);
		resId.append(odiv);
	});
	resId.accordion();
	resId.css("height","auto");
}*/
function getAllData(IDdata){
	$.ajax({
        type: "GET",
        url: "checkall?t="+new Date().getTime(),
        //url: "upLoadFiles/json.txt?t="+new Date().getTime(),
        data: {bundleID:IDdata},
        dataType: "json",
        success: function(data){
        			reData=data.state;
                    if(data.state==-1)
                    	{
	                    	$(".dialogTipAll").remove();
	                    	var p=$("<p class='dialogTipAll'></p>");
	                    	p.html("全部资源解析出错,所解析的文件不存在，请上传！");
	                    	$("#allResTab").append(p);
                    	}
                    else if(data.state==0)
                	{
                    	$(".dialogTipAll").remove();
                    	var p=$("<p class='dialogTipAll'></p>");
                    	p.html("全部资源仍在解析中，请稍后...");
                    	$("#allResTab").append(p);
                	}
                    else if(data.state==-2)
                    {
                    	$(".dialogTipAll").remove();
                    	var p=$("<p class='dialogTipAll'></p>");
                    	p.html("所解析的全部资源文件不存在！");
                    	$("#allResTab").append(p);
                	}
                    else if(data.state==1)
                	{
                    	$(".dialogTipAll").remove();
                    	createResourceLi(".allData",data,"top30");
                    	createResourceLi(".texture2DAllSce",data,"Texture2D");
                    	createResourceLi(".animationClipAllSce",data,"AnimationClip");
                    	createResourceLi(".audioClipAllSce",data,"AudioClip");
                    	createResourceLi(".meshAllSce",data,"Mesh");
                    	createResourceLi(".commeshAllSce",data,"CombineMesh");
                    	createResourceLi(".lightprobAllSce",data,"LightProb");
                    	drawChat(data,".chatContainerAllSce","全部资源");
                    	createRepeatRes(data,"#repeatRes");
                	}
                    else
                    {
                    	$(".dialogTipAll").remove();
                    	var p=$("<p class='dialogTipAll'></p>");
                    	p.html("全部资源解析出错,状态码错误...");
                    	$("#allResTab").append(p);
                    }
                 }
    });
}
function getData(IDdata){
	bodyEmpty();
	$.ajax({
        type: "GET",
        //url: "upLoadFiles/sharedata0.txt?t="+new Date().getTime(),
        url: "check?t="+new Date().getTime(),
        //data: {bundleID:$(".analyzeBtn").attr("bundleID"),type:"top30"},
        data: {bundleID:IDdata,type:"top30"},
        dataType: "json",
        success: function(data){
        			reData=data.state;
                    if(data.state==-1)
                    	{
                    		//createDialog("Resource解析出错","所解析的文件不存在，请上传！").dialog();
                    	 	//alert("所解析的文件不存在，请上传！");
                    		$(".dialogTipRes").remove();
	                    	var p=$("<p class='dialogTipRes'></p>");
	                    	p.html("Resource解析出错,所解析的文件不存在，请上传！");
	                    	$("#resTab").append(p);
                    	}
                    else if(data.state==0)
                	{
                    	//createDialog("请等待...","Resource资源仍在解析中，请稍后...").dialog();
                		//alert("仍在解析中，请稍后...");
                    	$(".dialogTipRes").remove();
                    	var p=$("<p class='dialogTipRes'></p>");
                    	p.html("Resource资源仍在解析中,请稍后...");
                    	$("#resTab").append(p);
                	}
                    else if(data.state==1)
                	{
                    	$(".dialogTipRes").remove();
                    	createResourceLi(".allResource",data,"top30");
                    	createResourceLi(".texture2DRes",data,"Texture2D");
                    	createResourceLi(".animationClipRes",data,"AnimationClip");
                    	createResourceLi(".audioClipRes",data,"AudioClip");
                    	createResourceLi(".meshRes",data,"Mesh");
                    	drawChat(data,".chatContainer","Resources");
                	}
                    else
                    {
                    	//createDialog("Resource解析出错","状态码错误...").dialog();
                    	//alert("状态码错误...");
                    	$(".dialogTipRes").remove();
                    	var p=$("<p class='dialogTipRes'></p>");
                    	p.html("Resource解析出错,状态码错误...");
                    	$("#resTab").append(p);
                    }
                 }
    });
}
function getSenData(IDdata){
	$.ajax({
        type: "GET",
        //url: "upLoadFiles/json.txt?t="+new Date().getTime(),
        url: "checkscene?t="+new Date().getTime(),
        //data: {bundleID:$(".analyzeBtn").attr("bundleID"),type:"top30"},
        data: {bundleID:IDdata},
        dataType: "json",
        success: function(data){
        			reData=data.state;
                    if(data.state==-1)
                    	{
                    		//createDialog("场景解析出错","所解析的文件不存在，请上传！").dialog();
                    	 	//alert("所解析的文件不存在，请上传！");
	                    	$(".dialogTipSce").remove();
	                    	var p=$("<p class='dialogTipSce'></p>");
	                    	p.html("场景解析出错,所解析的文件不存在，请上传！");
	                    	$("#sceTab").append(p);
                    	}
                    else if(data.state==0)
                	{
                    	//createDialog("请等待...","场景资源仍在解析中，请稍后...").dialog();
                		//alert("仍在解析中，请稍后...");
                    	$(".dialogTipSce").remove();
                    	var p=$("<p class='dialogTipSce'></p>");
                    	p.html("场景资源仍在解析中，请稍后...");
                    	$("#sceTab").append(p);
                	}
                    
                    else if(data.state==1)
                	{
                    	$(".dialogTipSce").remove();
                    	createResourceLi(".allScence",data,"top30");
                    	createResourceLi(".texture2DSce",data,"Texture2D");
                    	createResourceLi(".animationClipSce",data,"AnimationClip");
                    	createResourceLi(".audioClipSce",data,"AudioClip");
                    	createResourceLi(".meshSce",data,"Mesh");
                    	createResourceLi(".commeshSce",data,"CombineMesh");
                    	createResourceLi(".lightprobSce",data,"LightProb");
                    	drawChat(data,".chatContainerSce","场景");
                	}
                    else
                    {
                    	//createDialog("场景资源仍在解析中，请稍后...").dialog();
                    	//alert("状态码错误...");
                    	$(".dialogTipSce").remove();
                    	var p=$("<p class='dialogTipSce'></p>");
                    	p.html("场景解析出错,状态码错误...");
                    	$("#sceTab").append(p);
                    }
                 }
    });
}
function getABData(IDdata){
	$.ajax({
        type: "GET",
        //url: "upLoadFiles/json.txt?t="+new Date().getTime(),
        url: "checkab?t="+new Date().getTime(),
        //data: {bundleID:$(".analyzeBtn").attr("bundleID"),type:"top30"},
        data: {bundleID:IDdata},
        dataType: "json",
        success: function(data){
        			reData=data.state;
                    if(data.state==-1)
                    	{
                    		//createDialog("Assetbundle解析出错","所解析的文件不存在，请上传！").dialog();
                    	 	//alert("所解析的文件不存在，请上传！");
	                    	$(".dialogTipAb").remove();
	                    	var p=$("<p class='dialogTipAb'></p>");
	                    	p.html("Assetbundle解析出错,所解析的文件不存在，请上传！");
	                    	$("#abTab").append(p);
                    	}
                    else if(data.state==0)
                	{
                    	//createDialog("请等待...","Assetbundle资源仍在解析中，请稍后...").dialog();
                		//alert("仍在解析中，请稍后...");
                    	$(".dialogTipAb").remove();
                    	var p=$("<p class='dialogTipAb'></p>");
                    	p.html("Assetbundle资源仍在解析中，请稍后...");
                    	$("#abTab").append(p);
                	}
                    else if(data.state==-2)
                    {
                		//createDialog("Assetbundle解析出错","所解析的Assetbundle文件不存在！").dialog();
                	 	//alert("所解析的文件不存在，请上传！");
                    	$(".dialogTipAb").remove();
                    	var p=$("<p class='dialogTipAb'></p>");
                    	p.html("所解析的Assetbundle文件不存在！");
                    	$("#abTab").append(p);
                	}
                    else if(data.state==1)
                	{
                    	$(".dialogTipAb").remove();
                    	createResourceLi(".allAB",data,"top30");
                    	createResourceLi(".texture2DAB",data,"Texture2D");
                    	createResourceLi(".animationClipAB",data,"AnimationClip");
                    	createResourceLi(".audioClipAB",data,"AudioClip");
                    	createResourceLi(".meshAB",data,"Mesh");
                    	createResourceLi(".commeshAB",data,"CombineMesh");
                    	createResourceLi(".lightprobAB",data,"LightProb");
                    	drawChat(data,".chatContainerAB","Assetbundle");
                	}
                    else
                    {
                    	//createDialog("Assetbundle解析出错","状态码错误...").dialog();
                    	//alert("状态码错误...");
                    	$(".dialogTipAb").remove();
                    	var p=$("<p class='dialogTipAb'></p>");
                    	p.html("Assetbundle解析出错,状态码错误...");
                    	$("#abTab").append(p);
                    }
                 }
    });
}
function setTimerGetData(IDdata){
	//清空显示
	bodyEmpty();
	//AllResource
	getAllData(IDdata);
	//Resource
	getData(IDdata);
	//sence
	getSenData(IDdata);
	//Assetbundle
	getABData(IDdata);
	var _interval = setInterval(function(){(function getData(IDdata){
		//AllResource
		getAllData(IDdata);
		//Resource
		getData(IDdata);
		//sence
		getSenData(IDdata);
		//Assetbundle
		getABData(IDdata);
		
	})(IDdata)}, 30000);   //定时的设置
}


function drawChat(data,chatClass,title){
	var jsonarray= data.percentage;
	var drawArray = new Array();
	$.each(jsonarray,function(i, n)
	{
		drawArray[i]=new Array();
		drawArray[i][0]=n.classname;
		drawArray[i][1]=n.percent;
	});
	// Build the chart
	$(chatClass).css('display','block');
	$(chatClass).css("height","auto");
    $(chatClass).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            width:780,
            plotShadow: false
        },
        title: {
            text: title+'资源各类型占用百分比'
        },
        tooltip: {
        	pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
        },
        subtitle: {
            text: '解压后资源总大小为'+data.size+'M'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.0f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                },
                events: {
                    click: function(e) {
                    	//createDialog("资源详细信息提示",'此项资源总大小为: '+data.size*e.point.y*0.01+', 所占百分比为: '+e.point.y+'%').dialog();
                    }
                }
        
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: drawArray
        }]
    });
    
    $(chatClass+' .highcharts-container').css("marginLeft","-50px");
    if(!$(chatClass).hasClass("ui-accordion-content-active"))
	{
		$(chatClass).css("display","none");
	}
}
function setTimerGetSenData(IDdata){
	getSenData(IDdata);
		var _intervalSen = setInterval(function(){(function getSenData(IDdata){
			$.ajax({
		        type: "GET",
		        //url: "upLoadFiles/json.txt?t="+new Date().getTime(),
		        url: "checkscene?t="+new Date().getTime(),
		        data: {bundleID:IDdata},
		        dataType: "json",
		        success: function(data){
		                    if(data.state==-1)
		                    	{
		                    		//createDialog("解析出错","所解析的文件不存在，请上传！").dialog();
		                    		//alert("所解析的文件不存在，请上传！");
		                    	}
		                    else if(data.state==0)
		                	{
		                    	//createDialog("请等待...","场景资源仍在解析中，请稍后...").dialog();
		                		//alert("仍在解析中，请稍后...");
		                	}
		                    else if(data.state==1)
		                	{
		                    	
		                    	createResourceLi(".allScence",data,"top30");
		                    	createResourceLi(".texture2DSce",data,"Texture2D");
		                    	createResourceLi(".animationClipSce",data,"AnimationClip");
		                    	createResourceLi(".audioClipSce",data,"AudioClip");
		                    	createResourceLi(".meshSce",data,"Mesh");
		                    	drawChat(data,".chatContainerSce","场景");
		                    	clearInterval(_intervalSen);
		                	}
		                    else
		                    {
		                    	//createDialog("解析出错","状态码错误...").dialog();
		                    	//alert("状态码错误...");
		                    }
		                 }
		    });
			
		})(IDdata)}, 30000);   //定时的设置
	
}
function setTimerGetABData(IDdata){
	getABData(IDdata);
	var _intervalSen = setInterval(function(){(function getABData(IDdata){
		$.ajax({
	        type: "GET",
	        //url: "upLoadFiles/json.txt?t="+new Date().getTime(),
	        url: "checkab?t="+new Date().getTime(),
	        data: {bundleID:IDdata},
	        dataType: "json",
	        success: function(data){
	                    if(data.state==-1)
	                    	{
	                    		//createDialog("解析出错","所解析的文件不存在，请上传！").dialog();
	                    		//alert("所解析的文件不存在，请上传！");
	                    	}
	                    else if(data.state==0)
	                	{
	                    	//createDialog("请等待...","Assetbundle资源仍在解析中，请稍后...").dialog();
	                		//alert("仍在解析中，请稍后...");
	                	}
	                    else if(data.state==1)
	                	{
	                    	
	                    	createResourceLi(".allAB",data,"top30");
	                    	createResourceLi(".texture2DAB",data,"Texture2D");
	                    	createResourceLi(".animationClipAB",data,"AnimationClip");
	                    	createResourceLi(".audioClipAB",data,"AudioClip");
	                    	createResourceLi(".meshAB",data,"Mesh");
	                    	drawChat(data,".chatContainerAB","Assetbundle");
	                    	clearInterval(_intervalSen);
	                	}
	                    else
	                    {
	                    	//createDialog("解析出错","状态码错误...").dialog();
	                    	//alert("状态码错误...");
	                    }
	                 }
	    });
		
	})(IDdata)}, 30000);   //定时的设置

}
