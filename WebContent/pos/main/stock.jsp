<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java" 
contentType="text/html; charset=ISO-8859-1"
import="pkg.properties.ReadProperties"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propIdx = new ReadProperties(session.getAttribute("language").toString()+".properties");
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  
  <title>Stock | <%=propLang.get("APPS") %></title>
  <link rel="shortcut icon" href="../../images/logos.ico">
  <meta name="description" content="ZPL" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

  
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
  
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>

  <script src="../../js/jquery.min.js"></script>
  <script src="../../js/bootstrap.js"></script>
  
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  
  <script src="../../js/app.js"></script>
  <script src="../../js/gethoms.js"></script>    
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  <script src="../../js/datatables/jquery.dataTables.min.js"></script>
  <script src="../../js/datatables/jquery.csv-0.71.min.js"></script>

    <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->
<style type="text/css">
     #loading-image {
		background-color: #333;
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 1;
	}	
	#loading-image2 {
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}
</style>  
</head>
<body>
<div id="loading-image">
		<img src="../../images/loading.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 208px;
				   height: 13px;
				   margin-top: -8px;
				   margin-left: -104px;" />
  </div>
  <div id="loading-image2" style="display:none">
		<img src="../../images/spinner.gif" alt="Loading..." 
			style="position: absolute;
				   top: 50%;
				   left: 50%;
				   width: 200px;
				   height: 200px;
				   margin-top: -100px;
				   margin-left: -50px;" />
  </div>
  <section class="vbox">
    <header class="bg-white-only header header-md navbar navbar-fixed-top-xs">
		
		<%@ include file="../../inc2/top.jsp" %>
		
      <div class="navbar-right ">
        <ul class="nav navbar-nav m-n hidden-xs nav-user user">
          <%@ include file="../../inc2/topMenu.jsp" %>
          
          <%@ include file="../../inc2/top2.jsp" %>
        </ul>
      </div>      
    </header>
    <section>
      <section class="hbox stretch">
        <!-- .aside -->
        <aside class="bg-black dk nav-xs aside hidden-print" id="nav">           
          <section class="vbox">
            <section class="w-f-md scrollable">
              <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="10px" data-railOpacity="0.2">
                

                <!-- nav -->                 
                
                <%@ include file="../../inc/menu-left.jsp" %>
                
                <!-- / nav -->
                
                
              </div>
            </section>
  
				<%@ include file="../../inc2/leftFooter.jsp" %>
			
          </section>
        </aside>

        <section id="content">
          <section class="hbox stretch">   
          <%@ include file="../../inc2/menuApp8.jsp" %>       
            <section>
              <section class="vbox">
                <section class="scrollable padder" id="bjax-target">
                      
                	<div class="row row-sm">
                      
		                <h2 class="font-thin m-b"><%="Stock" %></h2>

		              		
					<div class="row">
								<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading">
									<%="Stock" %>
							</div>
							<!-- /.panel-heading -->
							<div class="panel-body">

			              <div class="table-responsive">

							<table width="100%">
							<tr>
								<td width="34%"><%=propLang.get("tick_stor") %></td>
								<td width="33%"><%=propLang.get("tick_ware") %></td>
								<td width="33%"><%=propLang.get("tick_cate") %></td>
							</tr>
							<tr>
								<td width="34%">									
									<select id="Stores" class="form-control" style="width: 95%;" onchange="SelChange();">
										<option value="">-- Choise Store --</option>
										${List}
									</select>
								</td>
								<td width="33%">
									<select id="Warehouse" class="form-control" style="width: 95%;" disabled>
									</select>
								</td>
								<td width="33%">
									<select id="Category" class="form-control" style="width: 95%;"  disabled>
									</select>							
								</td>
							</tr>						
							<tr><td colspan="3">&nbsp;</td></tr>
							<tr>
								<td width="34%"><%=propLang.get("tick_art") %></td>
								<td width="33%"><%=propLang.get("tick_col") %></td>
								<td width="33%"><%=propLang.get("tick_part") %></td>
							</tr>
							<tr>
								<td width="34%">
									<input type="text" id="Article" class="form-control" placeholder="-- Choise Article --" style="width: 95%;" autocomplete="on" list="Article_List" readonly onkeypress="LoadArt()">
									<datalist id="Article_List">

									</datalist>									
								</td>
								<td width="33%">
									<input type="text" id="Color" class="form-control" placeholder="-- Choise Color --" style="width: 95%;" autocomplete="on" list="Color_List" readonly>
									<datalist id="Color_List">

									</datalist>									
								</td>
								<td width="33%">
									<input type="text" id="Parts" class="form-control" placeholder="-- Choise Part --" style="width: 95%;" autocomplete="on" list="Color_List" readonly>
									<datalist id="Parts_List">

									</datalist>	
								</td>
							</tr>						
							<tr><td colspan="3">&nbsp;</td></tr>
							<tr>
								<td width="34%"><%=propLang.get("tick_stag") %></td>
								<td width="33%">&nbsp;</td>
								<td width="33%">&nbsp;</td>
							</tr>
							<tr>
								<td width="34%">
									<select id="Spring" class="form-control" style="width: 95%;"  disabled>
									</select>
								</td>
								<td width="33%">&nbsp;</td>
								<td width="33%">&nbsp;</td>
							</tr>						
							<tr><td colspan="3" id="trLoad" style="visibility:hidden;"><div style="align: center;" align="center"><img src="../../images/loading222.gif" width="79px" height="50px" /></div></td></tr>																									
							</table>

		  				  </div>
								<div style="margin-left: 0px;">
										<table width="100%">
								              <tbody>
								              	<tr>
									              <td align="left">
										              <!--  <button type="button" id="back" class="btn btn-default" onclick="history.back()"><i class="fa fa-chevron-circle-left" aria-hidden="true"></i> Back</button>    -->
										              <button type="button" id="delete" class="btn btn-default" onclick="window.location.reload(true);;"><i class="fa fa-refresh" aria-hidden="true"></i><%=propLang.get("tick_canc") %></button>                    
										              <button type="button" id="Save" class="btn btn-default" onclick="StockSearch()"><i class="fa fa-search" aria-hidden="true"></i> <%=propLang.get("tick_cerc") %></button>
									              </td>
								        	  	</tr>
								              	<tr><td>&nbsp;</td></tr>								        	  	
								        	  </tbody>
								        </table>
								</div>	
								<!--  -->
			              <div class="table-responsive"  id="stock_list">
			                <table class="table table-striped m-b-none" id="stock"></table>
		  				  </div>
		  				  									  				  
							</div>
							<!-- /.panel-body -->
						</div>
								</div>
								
							
							<!--  -->
															
							<!--  -->	
								
					</div>

		  				
		  				</div>
		  				  
			        </section> 
	              </section>
	            </section>               
               
                
	        </section>
            
          </section>
          <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen,open" data-target="#nav,html"></a>
        </section>
      </section>
    </section>    
  </section>
 
<script src="../../js/alertify.min.js"></script>
<link href="../../css/alertify.core.css" rel="stylesheet">
<link href="../../css/alertify.default.css" rel="stylesheet">   
<!--  
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/jquery-ui.min.js"></script>

<script src="http://www.jbPivot.org/js/vendor/jbpivot.min.js"></script>
<link rel="stylesheet" href="http://www.jbPivot.org/css/jbpivot.min.css">
-->
<script src="../../js/orb/orb.min.js"></script>
<link rel="stylesheet" href="../../js/orb/orb.min.css">

<script>
function StockSearch(){

	
	if (document.getElementById("Stores").value == ""){
		alertify.log("Store Obbligatorio");
		document.getElementById("Stores").focus();
		return;
	}
	
	if (document.getElementById("Warehouse").value == ""){
		alertify.log("Warehouse Obbligatorio");
		document.getElementById("Warehouse").focus();
		return;
	}	
	
	
	document.getElementById("stock_list").style.visibility= "visible";
	document.getElementById("stock_list").innerHTML = "<div style='align: center;' align='center'><img src='../../images/loading222.gif' width='79px' height='50px' /></div>";

	var ids = $("#Stores").val();
	var war = $("#Warehouse").val();
	var cat = $("#Category").val();
	var spr = $("#Spring").val();
	var art = $("#Article").val();
	var col = $("#Color").val();
	var parts = $("#Parts").val();

	var urls = "../../GetStocks?ids="+ids+"&war="+war+"&cat="+cat+"&spr="+spr+"&art="+art+"&col="+col+"&parts="+parts;

	$.getJSON(urls, function (data) {

		var list = data.listings;
	  		var codart = "";
	  		var parte = "";
	  		var codcol = "";
		  	var variante = "";
		  	var p1 = "";
		  	var p2 = "";
		  	var p3 = "";
		  	var p4 = "";
		  	var p5 = "";
		  	var p6 = "";
		  	var p7 = "";
		  	var p8 = "";
		  	var p9 = "";
		  	var p10 = "";
		  	var p11 = "";
		  	var p12 = "";
		  	var p13 = "";
		  	var p14 = "";
		  	var p15 = "";
		  	var p16 = "";
		  	var p17 = "";
		  	
		  	var storedata_ini = "";
		  	var storedata_fin = "";

		  	var storedata_ini = "[";
	 		var storedata_fin = "]";
	 		var storedata_row = "";
	 		
		 	$.each(list, function(index, list) {
		  		var codart = list.Codart;
		  		var parte = list.Parte;
		  		var codcol = list.Codcol;
			  	var variante = list.Variante;
			  	
			  	if (list.P1 != "-"){
				  	var p1 = list.P1.split("*");
				  	p1 = "<span style='font-weight: bold'>"+ p1[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p1[1] +"</span>";
			  	}else{
			  		var p1 = list.P1;
			  	}
			  	if (list.P2 != "-"){
				  	var p2 = list.P2.split("*");
				  	p2 = "<span style='font-weight: bold'>"+ p2[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p2[1] +"</span>";
			  	}else{
			  		var p2 = list.P2;
			  	}
			  	if (list.P3 != "-"){
				  	var p3 = list.P3.split("*");
				  	p3 = "<span style='font-weight: bold'>"+ p3[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p3[1] +"</span>";
			  	}else{
			  		var p3 = list.P3;
			  	}
			  	if (list.P4 != "-"){
				  	var p4 = list.P4.split("*");
				  	p4 = "<span style='font-weight: bold'>"+ p4[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p4[1] +"</span>";
			  	}else{
			  		var p4 = list.P4;
			  	}
			  	if (list.P5 != "-"){
				  	var p5 = list.P5.split("*");
				  	p5 = "<span style='font-weight: bold'>"+ p5[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p5[1] +"</span>";
			  	}else{
			  		var p5 = list.P5;
			  	}
			  	if (list.P6 != "-"){
				  	var p6 = list.P6.split("*");
				  	p6 = "<span style='font-weight: bold'>"+ p6[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p6[1] +"</span>";
			  	}else{
			  		var p6 = list.P6;
			  	}
			  	if (list.P7 != "-"){
				  	var p7 = list.P7.split("*");
				  	p7 ="<span style='font-weight: bold'>"+ p7[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p7[1] +"</span>";
			  	}else{
			  		var p7 = list.P7;
			  	}
			  	if (list.P8 != "-"){
				  	var p8 = list.P8.split("*");
				  	p8 = "<span style='font-weight: bold'>"+ p8[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p8[1] +"</span>";
			  	}else{
			  		var p8 = list.P8;
			  	}
			  	if (list.P9 != "-"){
				  	var p9 = list.P9.split("*");
				  	p9 = "<span style='font-weight: bold'>"+ p9[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p9[1] +"</span>";
			  	}else{
			  		var p9 = list.P9;
			  	}
			  	if (list.P10 != "-"){
				  	var p10 = list.P10.split("*");
				  	p10 ="<span style='font-weight: bold'>"+ p10[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p10[1] +"</span>";
			  	}else{
			  		var p10 = list.P10;
			  	}
			  	if (list.P11 != "-"){
				  	var p11 = list.P11.split("*");
				  	p11 = "<span style='font-weight: bold'>"+ p11[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p11[1] +"</span>";
			  	}else{
			  		var p11 = list.P11;
			  	}
			  	if (list.P12 != "-"){
				  	var p12 = list.P12.split("*");
				  	p12 = "<span style='font-weight: bold'>"+ p12[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p12[1] +"</span>";
			  	}else{
			  		var p12 = list.P12;
			  	}
			  	if (list.P13 != "-"){
				  	var p13 = list.P13.split("*");
				  	p13 = "<span style='font-weight: bold'>"+ p13[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p13[1] +"</span>";
			  	}else{
			  		var p13 = list.P13;
			  	}
			  	if (list.P14 != "-"){
				  	var p14 = list.P14.split("*");
				  	p14 = "<span style='font-weight: bold'>"+ p14[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p14[1] +"</span>";
			  	}else{
			  		var p14 = list.P14;
			  	}			
			  	if (list.P15 != "-"){
				  	var p15 = list.P15.split("*");
				  	p15 = "<span style='font-weight: bold'>"+ p15[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p15[1] +"</span>";
			  	}else{
			  		var p15 = list.P15;
			  	}			
			  	
			  	if (list.P16 != "-"){
				  	var p16 = list.P16.split("*");
				  	p16 = "<span style='font-weight: bold'>"+ p16[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p16[1] +"</span>";
			  	}else{
			  		var p16 = list.P16;
			  	}		
			  	if (list.P17 != "-"){
				  	var p17 = list.P17.split("*");
				  	p17 = "<span style='font-weight: bold'>"+ p17[0] + "</span>&nbsp;&nbsp;&nbsp;<span class='label bg-light' style='font-style: italic;'>"+ p17[1] +"</span>";
			  	}else{
			  		var p17 = list.P17;
			  	}			  	
			  	/*
			  	var p1 = list.P1;
			  	var p2 = list.P2;
			  	var p3 = list.P3;
			  	var p4 = list.P4;
			  	var p5 = list.P5;
			  	var p6 = list.P6;
			  	var p7 = list.P7;
			  	var p8 = list.P8;
			  	var p9 = list.P9;
			  	var p10 = list.P10;
			  	var p11 = list.P11;
				*/
				
		 		if (storedata_row == ""){
//		 			storedata_row = '{"codart":"'+codart+'","parte":"'+parte+'","codcol":"'+codcol+'","variante":"'+variante+'","p1":"'+p1+'","p2":"'+p2+'","p3":"'+p3+'","p4":"'+p4+'","p5":"'+p5+'","p6":"'+p6+'","p7":"'+p7+'","p8":"'+p8+'","p9":"'+p9+'","p10":"'+p10+'","p11":"'+p11+'","p12":"'+p12+'","p13":"'+p13+'","p14":"'+p14+'","p15":"'+p15+'","p16":"'+p16+'","p17":"'+p17+'"}';
		 			storedata_row = '{"codart":"'+codart+'","parte":"'+parte+'","codcol":"'+codcol+'","variante":"'+variante+'","p1":"'+p1+'","p2":"'+p2+'","p3":"'+p3+'","p4":"'+p4+'","p5":"'+p5+'","p6":"'+p6+'","p7":"'+p7+'","p8":"'+p8+'","p9":"'+p9+'","p10":"'+p10+'","p11":"'+p11+'","p12":"'+p12+'","p13":"'+p13+'","p14":"'+p14+'","p15":"'+p15+'"}';
		 		}else{
//		 			storedata_row = storedata_row + ' , {"codart":"'+codart+'","parte":"'+parte+'","codcol":"'+codcol+'","variante":"'+variante+'","p1":"'+p1+'","p2":"'+p2+'","p3":"'+p3+'","p4":"'+p4+'","p5":"'+p5+'","p6":"'+p6+'","p7":"'+p7+'","p8":"'+p8+'","p9":"'+p9+'","p10":"'+p10+'","p11":"'+p11+'","p12":"'+p12+'","p13":"'+p13+'","p14":"'+p14+'","p15":"'+p15+'","p16":"'+p16+'","p17":"'+p17+'"}';
		 			storedata_row = storedata_row + ' , {"codart":"'+codart+'","parte":"'+parte+'","codcol":"'+codcol+'","variante":"'+variante+'","p1":"'+p1+'","p2":"'+p2+'","p3":"'+p3+'","p4":"'+p4+'","p5":"'+p5+'","p6":"'+p6+'","p7":"'+p7+'","p8":"'+p8+'","p9":"'+p9+'","p10":"'+p10+'","p11":"'+p11+'","p12":"'+p12+'","p13":"'+p13+'","p14":"'+p14+'","p15":"'+p15+'"}';
		 		}
		 		
			});

		 	
		 	storedata = JSON.parse(storedata_ini + storedata_row + storedata_fin);
		 	document.getElementById("stock_list").innerHTML = "<table id='stock' class='table table-striped m-b-none' width='100%'></table>";
		 	
		 	/*
	 		var assetshead = "<thead><th width='15%'>CodArt</th><th width='10%'>Parte</th><th width='10%'>CodCol</th><th width='10%'>Variante</th> "+
	 		"<th width='5%'><br><br><br><br>39<br>UN<br>38/24<br><br>26<br>26/32<br>39/41</th> "+
	 		"<th width='5%'><br><br>XS<br><br>40<br>XXXL<br>40/26<br>40/41<br>27<br>27/32<br></th> "+
	 		"<th width='5%'>42<br>42/26<br>S<br>S/M<br>41<br><br>42/28<br>42/43<br>28<br>28/32<br></th> "+
	 		"<th width='5%'>44<br>44/28<br>M<br><br>42<br><br>44/30<br>44/45<br>29<br>29/32<br>42/46</th> "+
	 		"<th width='5%'>46<br>46/30<br>L<br>L/XL<br>43<br><br>46/32<br><br>30<br>30/32<br></th> "+
	 		"<th width='5%'>48<br>48/32<br>XL<br><br>44<br><br><br><br>31<br>31/32<br></th> "+
	 		"<th width='5%'>50<br>50/34<br>XXL<br><br>45<br><br><br><br>32<br>32/32<br></th> "+
	 		"<th width='5%'>52<br>52/36<br>XXXL<br><br><br><br><br><br>33<br>33/34<br></th> "+
	 		"<th width='5%'>54<br>54/38<br><br><br><br><br><br><br>34<br>34/34<br></th> "+
	 		"<th width='5%'>56<br><br><br><br><br><br><br><br>36<br>36/34<br></th> "+
	 		"<th width='5%'><br><br><br><br><br><br><br><br><br>38<br>38/34<br></th> "+
	 		"</thead>";
	 		*/
	 		var assetshead = "<thead><th width='5%'>CodArt</th><th width='5%'>Parte</th><th width='5%'>CodCol</th><th width='5%'>Variante</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		"<th width='5%'>&nbsp;</th> "+
	 		//"<th width='5%'>&nbsp;</th> "+
	 		//"<th width='5%'>&nbsp;</th> "+	 		
	 		"</thead>";

	 		document.getElementById("stock").innerHTML = assetshead;
	 		$('#stock').dataTable({
	 		      "language": {
	 		          "lengthMenu": "_MENU_ &nbsp;&nbsp;<%=propLang.get("dt_nrRecord") %>",
	 		          "zeroRecords": "<%=propLang.get("dt_nessunrecord") %>",
	 		          "info": "<%=propLang.get("dt_pagina") %> _PAGE_ <%=propLang.get("dt_di") %> _PAGES_ <%=" - " + propLang.get("nRec") %> _MAX_ <%=" : "+propLang.get("nRecVis")+"("%> _START_ <%=" - "%> _END_ <%=")"%>",
	 		          "infoEmpty": "<%=propLang.get("dt_nessunrecord2") %>",
	 		          "infoFiltered": "(Filtrati da _MAX_ records totali)",
	 		          "decimal": ",",
	 		          "thousands": ".",
	 		          search: "<%=propLang.get("dt_cerca") %>:&nbsp;&nbsp; "
	 		      },
	 			"bProcessing": true,
	 			"aaData": storedata,
	 		    "aoColumns": [{
	 		        "mDataProp": "codart"
	 		    }, {
	 		        "mDataProp": "parte"
	 		    }, {
	 		        "mDataProp": "codcol"
	 		    }, {
	 		        "mDataProp": "variante"
	 		    }, {
	 		        "mDataProp": "p1"
	 		    }, {
	 		        "mDataProp": "p2"
	 		    }, {
	 		        "mDataProp": "p3"
	 		    }, {
	 		        "mDataProp": "p4"
	 		    }, {
	 		        "mDataProp": "p5"
	 		    }, {
	 		        "mDataProp": "p6"
	 		    }, {
	 		        "mDataProp": "p7"
	 		    }, {
	 		        "mDataProp": "p8"
	 		    }, {
	 		        "mDataProp": "p9"
	 		    }, {
	 		        "mDataProp": "p10"
	 		    }, {
	 		        "mDataProp": "p11"
	 		    }, {
	 		        "mDataProp": "p12"
	 		    }, {
	 		        "mDataProp": "p13"
	 		    }, {
	 		        "mDataProp": "p14"
	 		    }, {
	 		        "mDataProp": "p15"
	 		    }],	 		    
	 		    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-6'i><'col-sm-6'p>>",
	 		    "bPaginate":true,
	 		   	"bDestroy": true
	 		});
					 	
	});

}

function SelChange(){
	
	document.getElementById("trLoad").style.visibility = "visible";
	
	var Ids = $("#Stores").val();
	
	var urls = "../../GetParameters?id="+Ids;

	$.getJSON(urls, function (data) {
			
		var list = data.listings;

		 	$.each(list, function(index, list) {
		 		document.getElementById("Category").innerHTML = list.Listcat;
		 		document.getElementById("Spring").innerHTML = list.Listspr;
		 		document.getElementById("Article_List").innerHTML = list.Listart;
		 		document.getElementById("Color_List").innerHTML = list.Listcol;
		 		document.getElementById("Parts_List").innerHTML = list.Listpar;
		 		document.getElementById("Warehouse").innerHTML = list.Listloc;
		 		
				document.getElementById("Category").disabled = false;
				document.getElementById("Spring").disabled = false;
				document.getElementById("Article").readOnly  = false;
				document.getElementById("Color").readOnly  = false;
				document.getElementById("Parts").readOnly  = false;
				document.getElementById("Warehouse").disabled  = false;
				document.getElementById("trLoad").style.visibility = "hidden";
			});
					 	
	});	

}


function LoadArt(){

	var Ids = $("#Stores").val();
	
	if($("input[id$='Article']").val().length >= 5){
		
		/**/	
	var urls = "../../GetArticles?id="+Ids+"&codart="+$("input[id$='Article']").val();

	$.getJSON(urls, function (data) {
			
		var list = data.listings;

		 	$.each(list, function(index, list) {
		 		document.getElementById("Article_List").innerHTML = list.Listart;
				document.getElementById("Article").readOnly  = false;
			});
					 	
	});			
		/**/
		
	}
	
}

</script>
  <script>
  jQuery(window).load(function() {
		jQuery('#loading-image').hide();
		jQuery('#loading-image2').hide();
		
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
	
  </script>
  
  <%@ include file="../../inc/autorized.jsp" %>
  </body>
</html>