<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>				    


<div class="table-responsive">
	<table class="table b-light">
      <tr>
    	<th style="border-top:0px"><%=propLang.get("emp") %></th>
    	<th style="width:9%;border-top:0px"><%=propLang.get("lun") %></th>
    	<th style="width:9%;border-top:0px"><%=propLang.get("mar") %></th>
    	<th style="width:9%;border-top:0px"><%=propLang.get("mer") %></th>
    	<th style="width:9%;border-top:0px"><%=propLang.get("gio") %></th>
    	<th style="width:9%;border-top:0px"><%=propLang.get("ven") %></th>
    	<th style="width:9%;border-top:0px"><%=propLang.get("sab") %></th>
    	<th style="width:9%;border-top:0px"><%=propLang.get("dom") %></th>
    	<th style="width:9%;border-top:0px"><%=propLang.get("totali") %></th>
    	<th style="width:9%;border-top:0px">Monte Ore</th>
     </tr>
     <tbody>
        <c:forEach items="${plan}" var="plan" varStatus="status">
      	<tr>
      		<td class="b-r bg-emp">
      			<span class="text-ellipsis"><a onclick="openPlan('${plan.id_employee}','${id_departments}','${year}','${week}','${day}')" href="#" class="getModal btn btn-default btn-xs aaa" title="Mostra Pianificazioni Dipendente"><i class="icon-calendar text-muted"/></i></a>  
				${plan.name} <b>${plan.surname}</b></span>
			</td>
			<td class="b-r">
				<input type="text" name="tot0" id="tot0${plan.id_employee}" value="${plan.lun2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
			</td>
			<td class="b-r">
				<input type="text" name="tot1" id="tot1${plan.id_employee}" value="${plan.mar2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
			</td>
			<td class="b-r">
				<input type="text" name="tot2" id="tot2${plan.id_employee}" value="${plan.mer2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
			</td>
			<td class="b-r">
				<input type="text" name="tot3" id="tot3${plan.id_employee}" value="${plan.gio2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
			</td>
			<td class="b-r">
				<input type="text" name="tot4" id="tot4${plan.id_employee}" value="${plan.ven2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
			</td>
			<td class="b-r">
				<input type="text" name="tot5" id="tot5${plan.id_employee}" value="${plan.sab2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
			</td>
			<td class="b-r">
				<input type="text" name="tot6" id="tot6${plan.id_employee}" value="${plan.dom2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/> 
			</td>
			<td class="b-r">
				<input type="text" name="totDef" id="totDef${plan.id_employee}" value="${plan.tot_emp2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
			</td>
			<td>
				<input type="text" name="totDef" id="totDef${plan.id_employee}" value="${plan.monte*-1}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#FAD733;font-size:15px" readonly/>
			</td>
		</tr>
	  </c:forEach>
	  </tbody>
	  <tfoot>
	    <tr>
	    	<td class="b-r" style="border-top:2px solid #c0c0c0">
				<b><%=propLang.get("totali") %></b>
			<td class="b-r" style="border-top:2px solid #c0c0c0">
				<input type="text" name="tottot0" id="tottot0" value="${lun2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
			</td>
			<td class="b-r" style="border-top:2px solid #c0c0c0">
				<input type="text" name="tottot1" id="tottot1" value="${mar2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
			</td>
			<td class="b-r" style="border-top:2px solid #c0c0c0">
				<input type="text" name="tottot2" id="tottot2" value="${mer2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
			</td>
			<td class="b-r" style="border-top:2px solid #c0c0c0">
				<input type="text" name="tottot3" id="tottot3" value="${gio2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
			</td>
			<td class="b-r" style="border-top:2px solid #c0c0c0">
				<input type="text" name="tottot4" id="tottot4" value="${ven2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
			</td>
			<td class="b-r" style="border-top:2px solid #c0c0c0">
				<input type="text" name="tottot5" id="tottot5" value="${sab2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
			</td>
			<td class="b-r" style="border-top:2px solid #c0c0c0">
				<input type="text" name="tottot6" id="tottot6" value="${dom2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
			</td>
			<td class="b-r" style="border-top:2px solid #c0c0c0">
				<input type="text" name="tottotDef" id="tottotDef" value="${tot2}" style="width:50px;text-align:right;background: transparent; 
				border:0px;font-weight:bold;color:#000;font-size:15px" readonly/>
			</td>
			<td style="border-top:2px solid #c0c0c0">&nbsp;</td>
	    </tr>
     </tfoot>
   	</table>
   	
   	
	<div class="line line-dashed b-b line-lg pull-in"></div>
	
   	<div class="row row-sm">
	<c:forEach items="${tab}" var="tiles" varStatus="status">
		
		<div class="col-xs-12 col-md-6 ${col}" align="center">
			<div class="bg-light lter wrapper-md r">
                <span class="h4 block">${tiles.cmp9}</span>
                <div class="h1 m-t-xs block text-ellipsis" style="color:${tiles.xdtab}">${tiles.cmp8}</div>
            </div>
            <!-- 
            <div class="bg-light lter tiles_loader text-center" id="tiles_${tiles.xcdel}">
                  	<div class="row"><div class="col-sm-12"><div class="sparkline2 css" data-type="line" data-height="60" data-width="100%" data-line-width="3" data-line-color="${tiles.xdtab}" data-spot-color="${tiles.cmp10}" data-fill-color="${tiles.cmp10}" data-spot-radius="1" data-highlight-line-color="#fff">${tiles.cmp1},${tiles.cmp2},${tiles.cmp3},${tiles.cmp4},${tiles.cmp5},${tiles.cmp6},${tiles.cmp7}</div></div></div>
               </div>
            -->
           </div>
           
      	</c:forEach>
   </div>
   
    
	<div class="line line-dashed b-b line-lg pull-in"></div>
						     
	<div id="bar-chart" style="height:350px"></div>
				
</div>

<script>
//based on prepared DOM, initialize echarts instance
var myChart = echarts.init(document.getElementById('bar-chart'));
	
	// specify chart configuration item and data
option = {
		color: ['#1ab667','#3F4787'],
		    
	    title : {
	        text: 'Week Planner',
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            
	            type : 'shadow'      
	        }
	    },
	    legend: {
	        data:[]
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '0%',
	        containLabel: true
	    },
	    toolbox: {
	        show : true,
	        showTitle: false,
	        feature : {
	        	
	        	//dataView : {show: true, readOnly: false, title: 'Data View'},
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	        	show: true,
	        	//axisLabel: { rotate: 90, width: 50},
	            type : 'category',
	            //showMaxLabel: true,
	            //axisLabel: { rotate: 45, },
	            // Lista negozi
	            data : ['<%=propLang.get("lun") %>','<%=propLang.get("mar") %>','<%=propLang.get("mer") %>','<%=propLang.get("gio") %>','<%=propLang.get("ven") %>','<%=propLang.get("sab") %>','<%=propLang.get("dom") %>']
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'Totale ore in Dipartimento',
	            type:'bar',
	            //barWidth: '80%',
	            data:[${lun},${mar},${mer},${gio},${ven},${sab},${dom}],
	            markLine : {
	                data : [
	                    {type : 'max', name: 'Max'}
	                ]
	            },
	            itemStyle: {
	                
	                emphasis: {
	        	        barBorderWidth: 1,
	        	        shadowBlur: 10,
	        	        shadowOffsetX: 0,
	        	        shadowOffsetY: 0,
	        	        shadowColor: 'rgba(0,0,0,0.5)'
	        	    }
	            },
	        },
	        {
	            name:'Totale ore Pianificate',
	            type:'bar',
	            //barWidth: '80%',
	            data:[${lun2},${mar2},${mer2},${gio2},${ven2},${sab2},${dom2}],
	            markLine : {
	                data : [
	                    {type : 'max', name: 'Max'}
	                ]
	            },
	            itemStyle: {
	                
	                emphasis: {
	        	        barBorderWidth: 1,
	        	        shadowBlur: 10,
	        	        shadowOffsetX: 0,
	        	        shadowOffsetY: 0,
	        	        shadowColor: 'rgba(0,0,0,0.5)'
	        	    }
	            },
	        }
	    ]
	
	};

// use configuration item and data specified to show chart
myChart.setOption(option);

$('.aaa').tooltipster();
</script>