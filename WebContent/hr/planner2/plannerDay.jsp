<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<c:if test="${nremp>0}">




<div id="sked${day}"></div>

<div id="totali${day}"></div>

<%
if(session.getAttribute("idcus").toString().equals("9")) {
%>
<hr id="mainhr${day}"/>
<div id="main${day}" style="min-height:180px"></div>
<%
}
%>

<script>

//alert("${locationsDis}");

//--------------------------- Data --------------------------------
var locations = [
    ${locations}
];
var events = [
	${events}
];

//alert(JSON.stringify(events));
var $sked${day} = $('#sked${day}').skedTape({
    caption: '<%=propLang.get("emp") %>',
    start: new Date(Date.UTC(${a}, ${m}-1, ${d}, 6, 0)),
    end: new Date(Date.UTC(${a}, ${m}-1, ${d}, 24, 0)),
    showEventTime: true,
    showEventDuration: true,
    scrollWithYWheel: false,
    locations: locations.slice(),
    events: events.slice(),
    maxTimeGapHi: 60 * 1000, // 1 minute
    minGapTimeBetween: 0 * 60 * 1000,
    snapToMins: 15,
    zoom:1,
    editMode: false,
    formatters: {
        date: function (date) {
            return $.fn.skedTape.format.date(date, 'l', '.');
        },
        duration: function (ms, opts) {
            return $.fn.skedTape.format.duration(ms, {
                hrs: 'h.',
                min: 'min.'
            });
        },
    },
    
    postRenderLocation: function($el, location, canAdd) {
        
    	//alert(location.userData.trasf);
    	var dis = "";
    	if(location.userData.trasf=='TRASF') {
    		//canAdd = false;
    		dis = "disabled";
    	}
    	
    	this.constructor.prototype.postRenderLocation($el, location, canAdd);
        
        
        $el.prepend('<input id="${day}'+location.id+'" value="-" style="width:30px;text-align:right; margin-right:5px; background: transparent; border:0px;font-weight:bold;color:#000" readonly/>')
    	$el.prepend('<input id="monte${day}'+location.id+'" value="'+location.userData.monte+'" style="width:30px;text-align:right; margin-right:5px; background: transparent; border:0px;font-weight:bold;color:#FAD733" readonly/>')
    	
        $el.prepend('<a onclick="openPlan(\''+location.id+'\',\'${id_departments}\',\'${year}\',\'${week}\',\'${day}\')" '+dis+' href="#" class="getModal btn btn-default btn-xs aaa" title="Mostra Pianificazioni Dipendente"><i class="icon-calendar text-muted"/></a> ');
        
    }
});

$sked${day}.on('event:dragEnded.skedtape', function(e) {
    console.log(e.detail.event);
});
$sked${day}.on('event:click.skedtape', function(e) {
    //$sked1.skedTape('removeEvent', e.detail.event.id);
    planEdit(e.detail.event);
});

$sked${day}.on('event:contextmenu.skedtape', function(e) {
  	//alert(e.detail.event.id);
  	//$sked1.skedTape('removeEvent', e.detail.event.id);
});


$sked${day}.on('timeline:click.skedtape', function(e, api) {
    
	
	//alert(JSON.stringify(e.detail.locationId));
	
	var locationsDis = ${locationsDis};
	
	var ins = true;
	for(i=0;i<locationsDis.length;i++) {
		
		//alert(plan.location+" "+locationsDis[i])
		if(locationsDis[i]==e.detail.locationId) {
			ins = false;
		}
	}
	
	if(ins) {
		planInsert${day}();
	}
	
    
});

$sked${day}.on('event:dragEnd.skedtape', function(e) {
	
	
	planInsertDB${day}(e.detail.event , ${locationsDis});
	

});






$( '#totali${day}').empty();
$( '#main${day}').empty();


var aside_width = $('#sked${day}').find('.sked-tape__locations').width();

//alert(aside_width);
var min_width = $('.sked-tape__time-canvas').css("min-width");

var tt = '<div class="sked-tape__hours">'
    +'<ul>'
    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T06:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v6">0</div></li>'
    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T07:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v7">0</div></li>'
    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T08:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v8">0</div></li>'
    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T09:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v9">0</div></li>'
    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T10:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v10">0</div></li>'
    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T11:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v11">0</div></li>'
    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T12:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v12">0</div></li>'
    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T13:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v13">0</div></li>'
	+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T14:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v14">0</div></li>'
	+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T15:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v15">0</div></li>'
	+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T16:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v16">0</div></li>'
	+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T17:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v17">0</div></li>'
	+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T18:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v18">0</div></li>'
	+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T19:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v19">0</div></li>'
	+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T20:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v20">0</div></li>'
	+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T21:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v21">0</div></li>'
	+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T22:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v22">0</div></li>'
	+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T23:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}v23">0</div></li>'
	+'	<li><time datetime="2019-01-15T00:00:00.000Z">|</time></li>'
	+'	</ul>'
	+'	</div>';
	
	
	
	
var tt2 = '<div class="sked-tape__hours">'
	    +'<ul>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T06:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t6">0</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T07:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t7">0</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T08:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t8">0</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T09:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t9">0</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T10:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t10">0</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T11:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t11">0</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T12:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t12">0</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T13:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t13">0</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T14:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t14">0</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T15:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t15">0</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T16:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t16">0</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T17:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t17">0</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T18:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t18">0</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T19:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t19">0</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T20:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t20">0</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T21:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t21">0</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T22:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t22">0</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T23:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}t23">0</div></li>'
		+'	<li><time datetime="2019-01-15T00:00:00.000Z">|</time></li>'
		+'	</ul>'
		+'	</div>';

var minNum = "${minNum}";
var minNumVal = minNum.split(",");		
		
var tt3 = '<div class="sked-tape__hours">'
	    +'<ul>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T06:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m6">'+minNumVal[0]+'</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T07:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m7">'+minNumVal[1]+'</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T08:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m8">'+minNumVal[2]+'</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T09:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m9">'+minNumVal[3]+'</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T10:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m10">'+minNumVal[4]+'</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T11:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m11">'+minNumVal[5]+'</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T12:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m12">'+minNumVal[6]+'</div></li>'
	    +'	<li style="width: 5.55556%;"><time datetime="2019-01-14T13:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m13">'+minNumVal[7]+'</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T14:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m14">'+minNumVal[8]+'</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T15:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m15">'+minNumVal[9]+'</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T16:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m16">'+minNumVal[10]+'</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T17:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m17">'+minNumVal[11]+'</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T18:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m18">'+minNumVal[12]+'</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T19:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m19">'+minNumVal[13]+'</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T20:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m20">'+minNumVal[14]+'</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T21:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m21">'+minNumVal[15]+'</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T22:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m22">'+minNumVal[16]+'</div></li>'
		+'	<li style="width: 5.55556%;"><time datetime="2019-01-14T23:00:00.000Z">|</time><div align="center" style="text-align:center" id="${day}m23">'+minNumVal[17]+'</div></li>'
		+'	<li><time datetime="2019-01-15T00:00:00.000Z">|</time></li>'
		+'	</ul>'
		+'	</div>';
		
	

var hh = $('#sked${day}').find('.sked-tape__time-canvas');

/*
var as1${day} = "<div style=\"border-top:1px solid #f1f1f1;float:left;width:"+(aside_width+4)+"px; height: 25px\"><b>Totale Ore Fascia</b></div>";
var tt1${day} = "<div style=\"border-top:1px solid #f1f1f1;float:left;min-width:"+min_width+"; margin-left:0px; height: 25px\" id=\"tot1${day}\">"+tt+"</div><div style=\"clear:both;\"></div>";

var as2${day} = "<div style=\"border-top:1px solid #f1f1f1;float:left;width:"+(aside_width+4)+"px; height: 25px\"><b>Totale Numero Addetti</b></div>";
var tt2${day} = "<div style=\"border-top:1px solid #f1f1f1;float:left;min-width:"+min_width+"; margin-left:0px; height: 25px\" id=\"tot1${day}\">"+tt2+"</div><div style=\"clear:both;\"></div>";

var as3${day} = "<div style=\"border-top:1px solid #f1f1f1;float:left;width:"+(aside_width+4)+"px; height: 25px\"><b>Minimo Numero Addetti</b></div>";
var tt3${day} = "<div style=\"border-top:1px solid #f1f1f1;float:left;min-width:"+min_width+"; margin-left:0px; height: 25px\" id=\"tot1${day}\">"+tt3+"</div><div style=\"clear:both;\"></div>";
*/

var as1${day} = "<tr><td style=\"width:"+(aside_width+4)+"px;\"><b>Totale Ore Fascia</b></<td>";
var tt1${day} = "<td style=\"width:"+min_width+";\">"+tt+"</td></tr>";

var as2${day} = "<tr><td style=\"width:"+(aside_width+4)+"px;\"><b>Totale Numero Addetti</b></<td>";
var tt2${day} = "<td style=\"width:"+min_width+";\">"+tt2+"</td></tr>";

var as3${day} = "<tr><td style=\"width:"+(aside_width+4)+"px;\"><b>Minimo Numero Addetti</b></<td>";
var tt3${day} = "<td style=\"width:"+min_width+";\">"+tt3+"</td></tr>";


$( "#totali${day}" ).append( "<table class=\"table\">"+as1${day} + "" +tt1${day} + "" + as2${day} + "" +tt2${day} + "" + as3${day} + "" +tt3${day}+ "</table>");

$('.aaa').tooltipster();

function getHeatMap(kpi, descr) {
	
	var idstores = '2374';
	var dtda = '${d}-${m}-${a}';
	var dtal = '${d}-${m}-${a}';
	
	myChart3.dispose();
	$('#main${day}').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
	
	//alert('../ajax/getHeatMap.jsp?kpi='+kpi+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=${uid}&tipo=${tipo}');
	console.log('HEATMAP: ../planner2/ajax/getHeatMap.jsp?kpi='+kpi+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=AAA&tipo=D');
	
	$.ajax({
	      url: '../planner2/ajax/getHeatMap.jsp',
	      type: 'POST',
	      dataType: 'html',
	      async: true,
	      data: 'kpi='+kpi+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=AAA&tipo=D',
	      success: function (data) {
	    	  //hideLoader();
			  //alert(data);
			  
			  var res = data.split('##@@');
			  var max=res[0];
			  var heatmap=res[1];
			  var XA=res[2];
			  var YA=res[3];
			  //alert(heatmap);
			  //alert(YA);
			  var array = JSON.parse(heatmap);
			  var xa2=XA.split(",");
			  var ya2=YA.split(",");
			  
			  option2 = {
			    title: {
			        text: 'Smart Counter ${dayName} Last Week'
			    },	
			    tooltip: {
			        position: 'top'
			    },
			    animation: true,
			    grid: {
			        left: '1%',
			        top: '70',
			        right: '10%',
			        bottom: '10%',
			        containLabel: true
			    },
			    xAxis: {
			        type: 'category',
			        //axisLabel: { rotate: 60, },
			        axisLabel: { rotate: 90, },
			        //data: ['00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24'],
			        data: xa2,
			        splitArea: {
			            show: true
			        }
			    },
			    yAxis: {
			        type: 'category',
			        data: ya2,
			        splitArea: {
			            show: true
			        }
			    },
			    visualMap: {
			        min: 0,
			        max: max,
			        calculable: true,
			        orient: 'vertical',
			        left: 'right',
			        top: 'center',
			        inRange: {
			        	color: [ '#3F4787',  '#F9CD2F',  '#EF2027']
			    	}
			    },
			    series: [{
			        name: descr,
			        type: 'heatmap',
			        data: array,
			        label: {
			            normal: {
			                show: true
			            }
			        },
			        itemStyle: {
			            emphasis: {
			                shadowBlur: 10,
			                shadowColor: 'rgba(0, 0, 0, 0.5)'
			                
			            },
			            normal: {
		                    color: '#EE5154'
		                }
			        }
			    }]
			
		 		
		 	}
			
			myChart3 = echarts.init(document.getElementById('main${day}'));
			myChart3.setOption(option2);
			
			//hideLoader2();
			  
	      },
	      error: function () {
	      	//alert('Errore! si e\' verificato un errore.');
	      }
	});
	
}

<%
if(session.getAttribute("idcus").toString().equals("9")) {
%>
var myChart3 = echarts.init(document.getElementById('main${day}'));

getHeatMap('VI2','VISITORS');
<%
}
%>




</script>
</c:if>
<c:if test="${nremp==0}">
                
                
	  <div class="wrapper text-center">
	  	<h3><%=propLang.get("msgNoPlan") %></h3>
	  </div>
						  
                
</c:if>