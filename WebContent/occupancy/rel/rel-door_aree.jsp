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
<!DOCTYPE html>
<html lang="it" class="app">
<head>  
  <meta charset="utf-8" />
  <title>Desk | Smart Suite</title>
  <meta name="description" content="Desk Smart Suite" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="../../css/bootstrap.css?2" type="text/css" />
  <link rel="stylesheet" href="../../css/animate.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="../../css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="../../css/font.css" type="text/css" />
  <link rel="stylesheet" href="../../css/app.css?2" type="text/css" />  
  <link rel="stylesheet" href="../../js/datepicker/bootstrap-datepicker.css" type="text/css" />
  <link rel="stylesheet" href="../../js/bootstrap-select/css/bootstrap-select.css?v=3" type="text/css" />
  <link rel="stylesheet" href="../../js/datatables/datatables.css" type="text/css"/>
  
  
  <script src="../../js/jquery.min.js"></script>
  
  <!--[if lt IE 9]>
    <script src="../../js/ie/html5shiv.js"></script>
    <script src="../../js/ie/respond.min.js"></script>
    <script src="../../js/ie/excanvas.js"></script>
  <![endif]-->
  <style>
     #loading-image {
		background-color: #333;
		width: 100%;
		height: 100%;
		position: fixed;
		z-index: 10000;
	}	
		
	#ajaxModal {
		z-index: 10000;
	}
  </style>
</head>
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
        <!-- /.aside -->
        
        <section id="content">
          <section class="vbox">
            <section class="" id="bjax-target">
              <section class="hbox stretch">
              
              <!-- menu app -->
              
              <%@ include file="../../inc2/menuApp16.jsp" %>
              
              <section>
                  <section class="vbox">
                  
                	<section class="scrollable padder-lg " id="bjax-target">
	              <h2 class="font-thin m-b">Settings</h2>
	              
						<section class="panel panel-default">
			              <header class="panel-heading font-bold">
			                Door Aree Relation
			              </header>
			              
			              <div id="msg">${msg}</div>
			              
			              <form action="rel-door_aree.htm" data-validate="parsley" method="post" >
			              <input type="hidden" name="azione" value="S"/>
			              <div class="table-responsive">
			                <table class="table table-striped m-b-none" id="example">
			                  <thead>
								  <tr>
							        <th width="30%">Area OUT</th>
							        <th width="40%" colspan="2">Door</th>
							        <th width="30%">Area IN</th>
							      </tr>
							  </thead>
	      					  
	      					  <tbody>
							  <c:forEach items="${rel}" var="rel" varStatus="status">
								<tr>
								  <td><input type="hidden" value="${rel.idDoor}" name="idDoor"/>
								  <select class="form-control" id="_out_${rel.idDoor}" name="_out" onchange="checkSubArea('${rel.idDoor}')">
								  <option value="-1">Outdoor Area</option>
								  <optgroup id="_optgroup_out_${rel.idDoor}" label="AREAS">
								  <c:forEach items="${aree}" var="aree" varStatus="status">
								  <option value="${aree.id}" data-id="${aree.subArea}">[${aree.id}] ${aree.description}</option>
								  </c:forEach>
								  </optgroup>
								  </select>
								  <div id="msg_out_${rel.idDoor}"></div>
								  <script>
								  $('#_out_${rel.idDoor}').val('${rel._out}');
								  </script>
								  </td>
								  
								  <td><span class="label bg-light">${rel.idDoor}</span></td>
								  <td>${rel.doorDescription}</td>
								  
								  <td><select class="form-control" id="_in_${rel.idDoor}" name="_in" data-required="true" onchange="checkSubArea('${rel.idDoor}')">
								  <option value="-1">Outdoor Area</option>
								  <optgroup id="_optgroup_in_${rel.idDoor}" label="AREAS">
								  <c:forEach items="${aree}" var="aree" varStatus="status">
								  <option value="${aree.id}" data-id="${aree.subArea}">[${aree.id}] ${aree.description}</option>
								  </c:forEach>
								  </optgroup>
								  </select>
								  <div id="msg_in_${rel.idDoor}"></div>
								  <script>
								  $('#_in_${rel.idDoor}').val('${rel._in}');
								  </script>
								  </td>
								  
								</tr>
							  </c:forEach>
		  					  </tbody>
		  					  <tfoot>
		  					  	<tr>
		  					  		<td colspan="4" align="right">
		  					  			<input type="submit" name="sumbit_btn" class="btn btn-lg btn-primary" value="Save Settings"/>
		  					  		</td>
		  					  	</tr>
		  					  </tfoot>
		  					</table>
		  				  </div>
		  				  </form>
		  					
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
  <!-- Bootstrap -->
  <script src="../../js/bootstrap.js"></script>
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  <!-- App -->
  <script src="../../js/app.js"></script>  
  <script src="../../js/slimscroll/jquery.slimscroll.min.js"></script>
  <script src="../../js/app.plugin.js"></script>
  
  <!-- parsley -->
  <script src="../../js/parsley/parsley.min.js"></script>
  <script src="../../js/parsley/parsley.extend.js"></script>
  

  <script>
  	jQuery(window).load(function() {
		jQuery('#loading-image').hide();
	});
	  
	function showLoader() {
	  jQuery('#loading-image').show();
	}
	function hideLoader() {
	  jQuery('#loading-image').hide();
	}
	
	function checkSubArea(iddoor) {
		
		var s = $('#_in_'+iddoor);
		var s2 = $('#_out_'+iddoor);
		
		var isSubArea = ''+$(s).find(':selected').attr('data-id');
		var isSubArea2 = ''+$(s2).find(':selected').attr('data-id');
		//alert(isSubArea+' '+isSubArea2);
		
		if(isSubArea=='true'||isSubArea2=='true') {
			
			$('#_out_'+iddoor).val('-1');
			$('#msg_out_'+iddoor).html('Could not select SubArea');
			
			
			$('#_optgroup_out_'+iddoor+'').attr('disabled', true);
			
		}
		
		
		$('#_optgroup_out_'+iddoor+'').attr('disabled', false);
		
	}
	
  </script>
  
</body>
</html>