
			
			<%
			if(session.getAttribute("tipoLogin").toString().trim().equals("2")||session.getAttribute("tipo").toString().trim().equals("9")) {
			%>
			<!-- 
			<li style="display:in-line;">
				<a href="#" onclick="GetPagesLocked()" id="lock">
					<i class="fa fa-unlock-alt" id="locks" aria-hidden="true"></i>
				</a>				
			</li>
			-->
			<li style="display:in-line;">
				<!-- <a href="#" class="dropdown-toggle lt" data-toggle="modal" data-target="#myModalUserss" class="hidden-xs"> original -->
				<a href="#" class="dropdown-toggle lt" data-toggle="modal" data-target="#myModalUserss" class="hidden-xs" onclick="GetPeopless()">
					<i class="icon-user"></i>&nbsp;
					<i class="fa fa-exchange"></i>&nbsp;
					<i class="icon-user"></i>
				</a>
			</li>
			<%
			}
			%>


			<!-- 
			<li class="hidden-xs" id="id_smsation">
				<a href="../config/sms-list.htm" class="dropdown-toggle lt">
					<i class="icon-bubbles"></i>
					<span id="sms"></span>
				</a>
			</li>
			-->

			
			<li class="hidden-xs" id="id_notification">
				<a href="../../app/config/notification-list.htm">
					<i class="icon-bell"></i>
					<span id="not"></span>
				</a>
			</li>


			<!-- 
			<li style="display:in-line;" class="hidden-xs">
				<a href="#" class="dropdown-toggle lt" data-toggle="dropdown">
					<i class="icon-clock"></i>
				</a>
				<section class="dropdown-menu aside-xs animated fadeInUp" style="width: 170px;">
					<input id="timepicker1" type="text" class="form-control input-small " style="background: #fff; width:164px; border: 0px;" readonly="">
				</section>
			</li>
			-->

			<li style="display:in-line" class="hidden-xs">
				<a href="#" class="dropdown-toggle lt" data-toggle="dropdown">
					<i class="icon-screen-desktop"></i>
				</a>
				<section class="dropdown-menu aside-xs animated fadeInUp">
					<section class="panel bg-white">
						<div class="panel-heading b-light bg-light">
							<strong>Applicazioni</strong>
						</div>
						<div class="list-group">
							<%=session.getAttribute("app") %>
						</div>
					</section>
				</section>
			</li>
	