<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.util.Date"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%
//Legge il file di properties associato alla lingua sul profilo
ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
%>

<!-- <form id="actionForm"  method="post"> -->

<!-- <div class="modal-dialog"> -->
<!--   <div class="modal-content"> -->
<!-- 	<div class="modal-header"> -->
<!-- 		<a class="close" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();">&times;</a> -->
<%-- 		<h3 class="font-thin"><%=propLang.get("img_all") %></h3> --%>
<!-- 	</div> -->
<!-- 	<div class="modal-body" id="modalBody" > -->
		<div id="myBody">	
<!--   			<div class="line line-dashed b-b line-lg pull-in"></div> -->
<%--   			<label><%=propLang.get("itm_vendor_name") %></label> --%>
<!--   			<div class="input-group"> -->
<!--  				<select id="idvendor" name="idvendor" class="form-control ss" data-required="true"> -->
<!--      	  		</select> -->
<!-- 		 		<span class="input-group-addon btn-default myPlus" id="addVendor"><i class="fa fa-plus" aria-hidden="true"></i></span>    -->
<!--  			</div> -->
		</div>
<!-- 	</div> -->
<!-- 	<div class="modal-footer"> -->
<%-- 		<a class="btn btn-default" data-dismiss="modal" onclick="$('.modal').remove(); $('div.modal-backdrop').remove();"><%=propLang.get("pop_ap_chiudi") %></a> --%>
<!-- 	</div> -->
<!--   </div> -->
<!-- </div> -->
<!-- </form> -->
<script src="js/jquery.magnific-popup.min.js?v=<%=new Date()%>"></script>
<script src="js/photosJs.js?v=<%=new Date()%>"></script>
<script>
	var id = "${id}";
	label = "<%=propLang.get("img_add")%>";
	$(document).ready(function(){
		showLoader();
		$('div.modal-backdrop').remove();
		$.ajax({
			url: '../../ats/GetPhoto',
			type: 'GET',
			data:{idItem:id},
			dataType: "json",
			success: function (data) {
							
							 var json = data;
							 var compiledList = [];
								for(var i = 0; i < json.listings.length; i++) {
									var obj = json.listings[i];
								  	compiledList.push({src: obj.url, type: 'image', title: obj.title});
								}
							if(json.listings.length > 0){
								$.magnificPopup.open({
									  type:'image',
									  mainClass: 'custom-popup-class',
									  items: compiledList,
									  gallery: {
									      enabled: true
									    }
									});
							}
							hideLoader();
					}
			});

	});
	

</script>
