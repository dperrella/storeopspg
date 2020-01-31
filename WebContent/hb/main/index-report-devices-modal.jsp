<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<div class="modal-dialog modal-lg">
  <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a>
		<h3 class="font-thin">Report Trigger ${kpi_selected} - ${dsstore}</h3>
	</div>
	<div class="modal-body">	            
	
		<form action="index-report-devices-modal.htm" id="deskForm" name="deskForm" method="post">
	        <input type="hidden" name="kpi" id="kpi" value="${kpi}"/>
	        <input type="hidden" name="idstores" id="idstores" value="${idstores}"/>
	        <input type="hidden" name="tipoPeriod" id="tipoPeriod" value="${tipoPeriod}"/>
	        <input type="hidden" name="tipoPeriod2" id="tipo" value="Y"/>
	        <input type="hidden" name="dtda" id="dtda" value="${dtda}"/>
	        <input type="hidden" name="dtal" id="dtal" value="${dtal}"/>
	        <input type="hidden" name="quarter" id="quarterForm" value="${quarter}"/>
	        <input type="hidden" name="comparison" id="comparison" value="${comparison}"/>
	        <input type="hidden" name="group" id="group" value="${group}"/>
	        <input type="hidden" name="tipo_chart" id="tipo_chart" value="${tipo_chart}"/>
	        <input type="hidden" name="special" id="special" value=""/>
	        <input type="hidden" name="upd" id="upd" value=""/>
	        <input type="hidden" name="id_advanced" id="id_advanced" value=""/>
	        <input type="hidden" name="pag" id="pag" value="${pag}"/>
	        <input type="hidden" name="nrPag" id="nrPag" value="${nrPag}"/>
	        <input type="hidden" name="nrRecord" id="nrRecord" value="${nrRecord}"/>
	        <input type="hidden" name="numRow" id="numRow" value="${numRow}"/>
	        
	        <input type="hidden" name="kpi_selected" id="kpi_selected" value="${kpi_selected}"/>
	        <input type="hidden" name="uid" id="uid" value="${uid}"/>
       </form>      
			                  <div id="msg">${msg}</div>
			                    	
							    	<div id="ajaxChart">
							    	<div class="row">
							    		
							    		<div class="col-sm-${chart_col}">
							    			
							    			<div id="main" style="height:450px;"></div>
			                    	
							    			
							    		</div>
							    		<c:if test = "${nrdev > 1}">
							    		<div class="col-sm-4">
							    			
							    			<div id="pie" style="height:450px;"></div>
							    	
							    		</div>
							    		</c:if>
							    	</div>
							    	</div>
							    	
							    	<div class="line line-dashed b-b line-lg pull-in"></div>
						    
							  		<div id="main2_det" style="height:450px;"></div>
			                       	
				               	
				               	<div id="table_wrapper2" class="table-responsive horizontal">
				               		<table id="tableData" class="table table-striped b-t-none b-b b-light">
				               		<thead>
				               		
				               			${total}
				               			
				               			<tr>
				               				${th}
				               			</tr>
				               		</thead>
				               		<tbody>
				               		<!--  Periodo -->  
									<c:forEach items="${sto}" var="sto" varStatus="status">
										${sto.xdtab}
									</c:forEach>
					           	    </tbody>
					           	    </table>
					           	    
				           		</div>
				           	
				           
			             	
	     </div>
	      
	 </div>

</div>
  
  <script>
  
    
  	
    jQuery(window).load(function() {
		
	});
      
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
	
	function showLoader2() {
	  jQuery('#loading-image2').show();
	}
	function hideLoader2() {
	  jQuery('#loading-image2').hide();
	}
	
	
	
	
	function convertHex(hex,opacity){
        hex = hex.replace('#','');
        r = parseInt(hex.substring(0,2), 16);
        g = parseInt(hex.substring(2,4), 16);
        b = parseInt(hex.substring(4,6), 16);

        result = 'rgba('+r+','+g+','+b+','+opacity/100+')';
        
        //alert(result);
        
        return result;
    }
	
	function loadChart(kpi, kpidesc) {
		
		$.ajax({
		      url: '../../hb/ajax/ReportDevicesChart.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'kpi_selected='+kpi+'&kpi_desc_selected='+kpidesc+'&idstores=${idstores}&dtda=${dtda}&dtal=${dtal}&tipoPeriod=${tipoPeriod}&uid=${uid}',
		      success: function (data) {
		    	  
		    	  $('#ajaxChart').html('');
		    	  $('#ajaxChart').html(data);
		    	  
		    	  //alert(data);
		    	  hideLoader2();
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
	
	
	
	
	
	function chgCol() {
		
		$('#tableData td').each(function() {
		    var $this = $(this);
		    var value = $this.text();

		    if(!/^\s*\d+(\.\d+)?\s*$/.test(value)) {
		        return;
		    }

		    if(value == 0) {
		        $this.css('color', '#c0c0c0');
		    }
		    else {
		    	$this.css('color', '#50555A');
		    }
		});
	}
	chgCol();
	
	
	
	
	
	
	
	
	
	
	
	
	
	var rfs;
	function timerResize() {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 100);
	}
	
	function resize() {
		myChart.resize();
		myChart2.resize()
		myChart3.resize()
		//spark();
	}
	
	$(window).resize(function(e) {
		clearTimeout(rfs);
		rfs = setTimeout(resize, 500);
	});
	
	
	
	
	
	
	
	function excelExport() {
		
		var data_type = 'data:application/vnd.ms-excel';
        var table_div = document.getElementById('table_wrapper2');//table_wrapper is table id
        var table_html = table_div.outerHTML.replace(/ /g, '%20');

        var a = document.createElement('a');
        a.href = data_type + ', ' + table_html;
        a.download = 'table_name_' + Math.floor((Math.random() * 9999999) + 1000000) + '.xls';
        a.click();
		
	}
	
	
	
	
	/*
	// HEATMAP
    // based on prepared DOM, initialize echarts instance
    var myChart3 = echarts.init(document.getElementById('main2'));
	
    var hours = ['12a', '1a', '2a', '3a', '4a', '5a', '6a',
 		        '7a', '8a', '9a','10a','11a',
 		        '12p', '1p', '2p', '3p', '4p', '5p',
 		        '6p', '7p', '8p', '9p', '10p', '11p'];
	var days = ['Saturday', 'Friday', 'Thursday',
	        'Wednesday', 'Tuesday', 'Monday', 'Sunday'];

	var data = [[0,0,5],[0,1,1],[0,2,0],[0,3,0],[0,4,0],[0,5,0],[0,6,0],[0,7,0],[0,8,0],[0,9,0],[0,10,0],[0,11,2],[0,12,4],[0,13,1],[0,14,1],[0,15,3],[0,16,4],[0,17,6],[0,18,4],[0,19,4],[0,20,3],[0,21,3],[0,22,2],[0,23,5],[1,0,7],[1,1,0],[1,2,0],[1,3,0],[1,4,0],[1,5,0],[1,6,0],[1,7,0],[1,8,0],[1,9,0],[1,10,5],[1,11,2],[1,12,2],[1,13,6],[1,14,9],[1,15,11],[1,16,6],[1,17,7],[1,18,8],[1,19,12],[1,20,5],[1,21,5],[1,22,7],[1,23,2],[2,0,1],[2,1,1],[2,2,0],[2,3,0],[2,4,0],[2,5,0],[2,6,0],[2,7,0],[2,8,0],[2,9,0],[2,10,3],[2,11,2],[2,12,1],[2,13,9],[2,14,8],[2,15,10],[2,16,6],[2,17,5],[2,18,5],[2,19,5],[2,20,7],[2,21,4],[2,22,2],[2,23,4],[3,0,7],[3,1,3],[3,2,0],[3,3,0],[3,4,0],[3,5,0],[3,6,0],[3,7,0],[3,8,1],[3,9,0],[3,10,5],[3,11,4],[3,12,7],[3,13,14],[3,14,13],[3,15,12],[3,16,9],[3,17,5],[3,18,5],[3,19,10],[3,20,6],[3,21,4],[3,22,4],[3,23,1],[4,0,1],[4,1,3],[4,2,0],[4,3,0],[4,4,0],[4,5,1],[4,6,0],[4,7,0],[4,8,0],[4,9,2],[4,10,4],[4,11,4],[4,12,2],[4,13,4],[4,14,4],[4,15,14],[4,16,12],[4,17,1],[4,18,8],[4,19,5],[4,20,3],[4,21,7],[4,22,3],[4,23,0],[5,0,2],[5,1,1],[5,2,0],[5,3,3],[5,4,0],[5,5,0],[5,6,0],[5,7,0],[5,8,2],[5,9,0],[5,10,4],[5,11,1],[5,12,5],[5,13,10],[5,14,5],[5,15,7],[5,16,11],[5,17,6],[5,18,0],[5,19,5],[5,20,3],[5,21,4],[5,22,2],[5,23,0],[6,0,1],[6,1,0],[6,2,0],[6,3,0],[6,4,0],[6,5,0],[6,6,0],[6,7,0],[6,8,0],[6,9,0],[6,10,1],[6,11,0],[6,12,2],[6,13,1],[6,14,3],[6,15,4],[6,16,0],[6,17,0],[6,18,0],[6,19,0],[6,20,1],[6,21,2],[6,22,2],[6,23,6]];
	data = data.map(function (item) {
	    return [item[1], item[0], item[2] || '-'];
	});

	console.log("${heatmap}");
	
 	// specify chart configuration item and data
    option2 = {
    		
	    tooltip: {
	        position: 'top'
	    },
	    animation: true,
	    grid: {
	        height: '70%',
	        y: '10%'
	    },
	    xAxis: {
	        type: 'category',
	        data: [${xAxis}],
	        splitArea: {
	            show: true
	        }
	    },
	    yAxis: {
	        type: 'category',
	        data: [${yAxis}],
	        splitArea: {
	            show: true
	        }
	    },
	    visualMap: {
	        min: 0,
	        max: ${max},
	        calculable: true,
	        orient: 'vertical',
	        left: 'right',
	        top: 'center',
	        inRange: {
	            
	        	//color: [ '#3F4787',  '#F9CD2F',  '#EF8384', '#EE6B6D', '#EE5254', '#EE393D',    '#EF2027']
	        	color: [ '#3F4787',  '#F9CD2F',  '#EF2027']
	        }
	    },
	    series: [{
	        name: '${kpi_desc_selected}',
	        type: 'heatmap',
	        data: [${heatmap}],
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
    // use configuration item and data specified to show chart
    myChart3.setOption(option2);
	*/
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
	var myChart3 = echarts.init(document.getElementById('main2_det'));
	
	getHeatMap('${kpi_selected}','${kpi_desc_selected}');
	loadChart('${kpi_selected}','${kpi_desc_selected}');
	
	function getHeatMap(kpi, descr) {
		
		var idstores = $('#idstores').val();
		var dtda = $('#dtda').val();
		var dtal = $('#dtal').val();
		
		myChart3.dispose();
		$('#main2_det').html("<div align='center'><img src='../../images/loader_xs.gif'/></div>");
		
		$.ajax({
		      url: '../../hb/ajax/getHeatMap.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: 'kpi='+kpi+'&idstores='+idstores+'&dtda='+dtda+'&dtal='+dtal+'&uid=${uid}&pag=D',
		      success: function (data) {
		    	  //hideLoader();
				  //alert(data);
				  
				  var res = data.split('##@@');
				  var max=res[0];
				  var heatmap=res[1];
				  var XA=res[2];
				  
				  //alert(heatmap);
				  //alert(heatmap);
				  var array = JSON.parse(heatmap);
				  var xa2=XA.split(",");
				  
				  option2 = {
				    		
				    tooltip: {
				        position: 'top'
				    },
				    animation: true,
				    grid: {
				        height: '70%',
				        y: '10%'
				    },
				    xAxis: {
				        type: 'category',
				        axisLabel: { rotate: 60, },
				        //data: ['00-01','01-02','02-03','03-04','04-05','05-06','06-07','07-08','08-09','09-10','10-11','11-12','12-13','13-14','14-15','15-16','16-17','17-18','18-19','19-20','20-21','21-22','22-23','23-24'],
				        data: xa2,
				        splitArea: {
				            show: true
				        }
				    },
				    yAxis: {
				        type: 'category',
				        //data: ['Saturday','Friday','Thursday','Wednesday','Tuesday','Monday','Sunday'],
				        data: ['<%=propLang.get("sab")%>','<%=propLang.get("ven")%>','<%=propLang.get("gio")%>','<%=propLang.get("mer")%>','<%=propLang.get("mar")%>','<%=propLang.get("lun")%>','<%=propLang.get("dom")%>'],
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
				
				myChart3 = echarts.init(document.getElementById('main2_det'));
				myChart3.setOption(option2);
				  
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	
  </script> 
    
</body>
</html>