package pkg.utility;

public class PlannerMail {
	
	public static String getMess(String mess, String link, String tplink, String year, String week, String id_departments, String idusr) {
		
		String msg = "";
		
		msg+="<html>";
		msg+="<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />";
		msg+="<meta name=\"viewport\" content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0;\">";
		msg+="<title>Planner</title>";

		msg+="<style type=\"text/css\">";

		msg+="div, p, a, li, td { -webkit-text-size-adjust:none; }";

		msg+="*{";
		msg+="-webkit-font-smoothing: antialiased;";
		msg+="-moz-osx-font-smoothing: grayscale;";
		msg+="}";

		msg+=".ReadMsgBody";
		msg+="{width: 100%; background-color: #ffffff;}";
		msg+=".ExternalClass";
		msg+="{width: 100%; background-color: #ffffff;}";
		msg+="body{width: 100%; height: 100%; background-color: #ffffff; margin:0; padding:0; -webkit-font-smoothing: antialiased;}";
		msg+="html{width: 100%; background-color: #ffffff;}";

		msg+="@font-face {";
		msg+="    font-family: 'proxima_novalight';src: url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-light-webfont.eot');src: url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-light-webfont.eot?#iefix') format('embedded-opentype'),url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-light-webfont.woff') format('woff'),url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-light-webfont.ttf') format('truetype');font-weight: normal;font-style: normal;}";

		msg+="@font-face {";
		msg+="    font-family: 'proxima_nova_rgregular'; src: url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-regular-webfont.eot');src: url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-regular-webfont.eot?#iefix') format('embedded-opentype'),url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-regular-webfont.woff') format('woff'),url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-regular-webfont.ttf') format('truetype');font-weight: normal;font-style: normal;}";

		msg+="@font-face {";
		msg+="    font-family: 'proxima_novasemibold';src: url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-semibold-webfont.eot');src: url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-semibold-webfont.eot?#iefix') format('embedded-opentype'),url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-semibold-webfont.woff') format('woff'),url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-semibold-webfont.ttf') format('truetype');font-weight: normal;font-style: normal;}";
		    
		msg+="@font-face {";
		msg+="	font-family: 'proxima_nova_rgbold';src: url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-bold-webfont.eot');src: url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-bold-webfont.eot?#iefix') format('embedded-opentype'),url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-bold-webfont.woff') format('woff'),url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-bold-webfont.ttf') format('truetype');font-weight: normal;font-style: normal;}";
			
		msg+="@font-face {";
		msg+="    font-family: 'proxima_novablack';src: url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-black-webfont.eot');src: url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-black-webfont.eot?#iefix') format('embedded-opentype'),url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-black-webfont.woff') format('woff'),url('http://rocketway.net/themebuilder/template/templates/titan/font/proximanova-black-webfont.ttf') format('truetype');font-weight: normal;font-style: normal;}";
		    
		msg+="@font-face {font-family: 'proxima_novathin';src: url('http://rocketway.net/themebuilder/template/templates/mason/font/proximanova-thin-webfont.eot');src: url('http://rocketway.net/themebuilder/template/templates/mason/font/proximanova-thin-webfont.eot?#iefix') format('embedded-opentype'),url('http://rocketway.net/themebuilder/template/templates/mason/font/proximanova-thin-webfont.woff') format('woff'),url('http://rocketway.net/themebuilder/template/templates/mason/font/proximanova-thin-webfont.ttf') format('truetype');font-weight: normal;font-style: normal;}";

		msg+="p {padding: 0!important; margin-top: 0!important; margin-right: 0!important; margin-bottom: 0!important; margin-left: 0!important; }";

		msg+=".hover:hover {opacity:0.85;filter:alpha(opacity=85);}";

		msg+=".image77 img {width: 250px; height: auto;}";
		msg+=".image772 img {width: 200px; height: auto;}";
		msg+=".avatar125 img {width: 125px; height: auto;}";
		msg+=".icon61 img {width: 61px; height: auto;}";
		msg+=".logo img {width: 75px; height: auto;}";
		msg+=".icon18 img {width: 18px; height: auto;}";

		msg+="</style>";

		msg+="<!-- @media only screen and (max-width: 640px) ";
		msg+="		   {*/";
		msg+="		   -->";
		msg+="<style type=\"text/css\"> @media only screen and (max-width: 640px){";
		msg+="		body{width:auto!important;}";
		msg+="		table[class=full2] {width: 100%!important; clear: both; }";
		msg+="		table[class=mobile2] {width: 100%!important; padding-left: 20px; padding-right: 20px; clear: both; }";
		msg+="		table[class=fullCenter2] {width: 100%!important; text-align: center!important; clear: both; }";
		msg+="		td[class=fullCenter2] {width: 100%!important; text-align: center!important; clear: both; }";
		msg+="		td[class=pad15] {width: 100%!important; padding-left: 15px; padding-right: 15px; clear: both;}";
				
		msg+="} </style>";
		msg+="<!--";
		msg+="@media only screen and (max-width: 479px) ";
		msg+="		   {";
		msg+="		   -->";
		msg+="<style type=\"text/css\"> @media only screen and (max-width: 479px){";
		msg+="		body{width:auto!important;}";
		msg+="		table[class=full2] {width: 100%!important; clear: both; }";
		msg+="		table[class=mobile2] {width: 100%!important; padding-left: 20px; padding-right: 20px; clear: both; }";
		msg+="		table[class=fullCenter2] {width: 100%!important; text-align: center!important; clear: both; }";
		msg+="		td[class=fullCenter2] {width: 100%!important; text-align: center!important; clear: both; }";
		msg+="		table[class=full] {width: 100%!important; clear: both; }";
		msg+="		table[class=mobile] {width: 100%!important; padding-left: 20px; padding-right: 20px; clear: both; }";
		msg+="		table[class=fullCenter] {width: 100%!important; text-align: center!important; clear: both; }";
		msg+="		td[class=fullCenter] {width: 100%!important; text-align: center!important; clear: both; }";
		msg+="		td[class=pad15] {width: 100%!important; padding-left: 15px; padding-right: 15px; clear: both;}";
		msg+="		.erase {display: none;}";
						
		msg+="		}";
		msg+="} </style>";

		msg+="</head>";
		msg+="<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">";


		msg+="<!-- Notification 1  -->";
		msg+="<div style=\"display: none\" id=\"element_040774300415068865\"></div><!-- End Notification 1 -->";

		msg+="<!-- Notification 2  -->";
		msg+="<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"full\"  bgcolor=\"#2a2a2a\"style=\"background-color: rgb(42, 42, 42);\">";
		msg+="	<tr>";
		msg+="		<td bgcolor=\"#2a2a2a\"style=\"background-color: rgb(42, 42, 42);\">";
						
		msg+="			<!-- Mobile Wrapper -->";
		msg+="			<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#2a2a2a\"style=\"background-color: rgb(42, 42, 42);\">";
		msg+="				<tr>";
		msg+="					<td width=\"100%\" align=\"center\" bgcolor=\"#2a2a2a\"style=\"background-color: rgb(42, 42, 42);\">";
								
		msg+="						<div class=\"sortable_inner ui-sortable\">";
								
		msg+="						<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" object=\"drag-module-small\">";
		msg+="							<tr>";
		msg+="								<td align=\"center\" width=\"352\" valign=\"middle\">";
										
		msg+="									<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="										<tr>";
		msg+="											<td width=\"100%\" height=\"50\"></td>";
		msg+="										</tr>";
		msg+="									</table>";
																			
		msg+="								</td>";
		msg+="							</tr>";
		msg+="						</table>";
								
		msg+="						</div>";
		msg+="					</td>";
		msg+="				</tr>";
		msg+="			</table>";
					
		msg+="			<table width=\"392\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"full\">";
		msg+="				<tr>";
		msg+="					<td align=\"center\" width=\"20\" valign=\"middle\" bgcolor=\"#2a2a2a\"style=\"background-color: rgb(42, 42, 42);\"></td>";
		msg+="					<td align=\"center\" width=\"352\" valign=\"middle\">";
					
		msg+="						<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"full\" style=\"border-top-right-radius: 5px; border-top-left-radius: 5px;\">";
		msg+="							<tr>";
		msg+="								<td align=\"center\" width=\"352\" valign=\"middle\" bgcolor=\"#22a7f0\" style=\"-webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px;\">";
										
		msg+="									<div class=\"sortable_inner ui-sortable\">";
								
		msg+="									<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#e85140\" style=\"border-top-right-radius: 5px; border-top-left-radius: 5px; background-color: rgb(29, 200, 233);\"object=\"drag-module-small\">";
		msg+="										<tr>";
		msg+="											<td align=\"center\" width=\"352\" valign=\"middle\">";
														
		msg+="												<table width=\"300\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="													<tr>";
		msg+="														<td width=\"100%\" height=\"50\"></td>";
		msg+="													</tr>";
		msg+="												</table>";							
		msg+="											</td>";
		msg+="										</tr>";
		msg+="									</table>";
											
		msg+="									<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#e85140\"object=\"drag-module-small\" style=\"background-color: rgb(29, 200, 233);\">";
		msg+="										<tr>";
		msg+="											<td align=\"center\" width=\"352\" valign=\"middle\" class=\"image77\">";
													
		msg+="												<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="													<tr>";
		msg+="														<td width=\"100%\" align=\"center\" style=\"text-align: center;\"><span ><img src=\"http://suite.smartretail.it/images/logo-smart-retail3.png\" width=\"250\" alt=\"\" border=\"0\" ></span></td>";
		msg+="													</tr>";
		msg+="												</table>";							
		msg+="											</td>";
		msg+="										</tr>";
		msg+="									</table>";
											
		msg+="									<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#e85140\"object=\"drag-module-small\" style=\"background-color: rgb(29, 200, 233);\">";
		msg+="										<tr>";
		msg+="											<td align=\"center\" width=\"352\" valign=\"middle\">";
													
		msg+="												<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
															
		msg+="													<tr>";
		msg+="														<td width=\"100%\" height=\"30\"></td>";
		msg+="													</tr>";
		msg+="												</table>";							
		msg+="											</td>";
		msg+="										</tr>";
		msg+="									</table>";
											
		msg+="									<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#e85140\"object=\"drag-module-small\" style=\"background-color: rgb(29, 200, 233);\">";
		msg+="										<tr>";
		msg+="											<td align=\"center\" width=\"352\" valign=\"middle\">";
													
		msg+="												<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="													<tr>";
		msg+="														<td valign=\"middle\" width=\"100%\" style=\"text-align: center; font-family: Helvetica, Arial, sans-serif; font-size: 15px; color: rgb(255, 255, 255); line-height: 24px;\"class=\"fullCenter\" >";
		msg+="															<!--[if !mso]><!--><span style=\"font-family: 'proxima_nova_rgregular', Helvetica; font-weight: normal;\"><!--<![endif]-->"+mess;
		msg+="															<!--[if !mso]><!--></span><!--<![endif]-->";
		msg+="														</td>";
		msg+="													</tr>";
		msg+="												</table>";							
		msg+="											</td>";
		msg+="										</tr>";
		msg+="									</table>";

		msg+="									<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#e85140\"style=\"border-bottom-left-radius: 5px; border-bottom-right-radius: 5px; background-color: rgb(29, 200, 233);\" object=\"drag-module-small\">";
		msg+="										<tr>";
		msg+="											<td align=\"center\" width=\"352\" valign=\"middle\">";
													
		msg+="												<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="													<tr>";
		msg+="														<td width=\"100%\" height=\"50\"></td>";
		msg+="													</tr>";
		msg+="												</table>";
																						
		msg+="											</td>";
		msg+="										</tr>";
		msg+="									</table>";
											
		msg+="									<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#e85140\"object=\"drag-module-small\" style=\"background-color: rgb(29, 200, 233);\">";
		msg+="										<tr>";
		msg+="											<td align=\"center\" width=\"352\" valign=\"middle\" class=\"image772\">";
													
		msg+="												<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="													<tr>";
		msg+="														<td width=\"100%\" align=\"center\" style=\"text-align: center;\"><span ><img src=\"http://suite.smartretail.it/images/calendar.png\" width=\"250\" alt=\"\" border=\"0\" ></span>";
		msg+="														</td>";
		msg+="													</tr>";
		msg+="												</table>";	

		msg+="											</td>";
		msg+="										</tr>";
		msg+="									</table>";

		msg+="									<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#e85140\"style=\"border-bottom-left-radius: 5px; border-bottom-right-radius: 5px; background-color: rgb(29, 200, 233);\" object=\"drag-module-small\">";
		msg+="										<tr>";
		msg+="											<td align=\"center\" width=\"352\" valign=\"middle\">";
													
		msg+="												<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="													<tr>";
		msg+="														<td width=\"100%\" height=\"50\"></td>";
		msg+="													</tr>";
		msg+="												</table>";
																						
		msg+="											</td>";
		msg+="										</tr>";
		msg+="									</table>";
											
		msg+="									<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#e85140\"object=\"drag-module-small\" style=\"background-color: rgb(29, 200, 233);\">";
		msg+="										<tr>";
		msg+="											<td align=\"center\" width=\"352\" valign=\"middle\">";
													
		msg+="												<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="													<!----------------- Button Center ----------------->";
		msg+="													<tr>";
		msg+="														<td align=\"center\">";
		msg+="															<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">"; 
		msg+="																<tr> ";
		msg+="																	<td align=\"center\" height=\"45\"bgcolor=\"#ffffff\" style=\"border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; padding-left: 30px; padding-right: 30px; font-weight: bold; font-family: Helvetica, Arial, sans-serif; color: rgb(42, 42, 42); text-transform: uppercase; background-color: rgb(255, 255, 255);\">";
		msg+="																		<!--[if !mso]><!--><span style=\"font-family: 'proxima_nova_rgbold', Helvetica; font-weight: normal;\"><!--<![endif]-->";
		msg+="																			<a href=\"http://suite.smartretail.it/plannerRedirect.htm?link="+link+"&tplink="+tplink+"&year="+year+"&week="+week+"&id_departments="+id_departments+"&idusr="+idusr+"\" style=\"color: rgb(42, 42, 42); font-size: 15px; text-decoration: none; line-height: 34px; width: 100%;\">Visualizza Settimana</a>";
		msg+="																		<!--[if !mso]><!--></span><!--<![endif]-->";
		msg+="																	</td>"; 
		msg+="																</tr>";
		msg+="															</table>"; 
		msg+="														</td>";
		msg+="													</tr>";
		msg+="													<!----------------- End Button Center ----------------->";
		msg+="												</table>";							
		msg+="											</td>";
		msg+="										</tr>";
		msg+="									</table>";
											
		msg+="									<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#e85140\"object=\"drag-module-small\" style=\"background-color: rgb(29, 200, 233);\">";
		msg+="										<tr>";
		msg+="											<td align=\"center\" width=\"352\" valign=\"middle\">";
													
		msg+="												<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="													<tr>";
		msg+="														<td width=\"100%\" height=\"35\"></td>";
		msg+="													</tr>";
		msg+="												</table>";
		msg+="											</td>";
		msg+="										</tr>";
		msg+="									</table>";
											
		msg+="									<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#e85140\"object=\"drag-module-small\" style=\"background-color: rgb(29, 200, 233);\">";
		msg+="										<tr>";
		msg+="											<td align=\"center\" width=\"352\" valign=\"middle\">";
													
		msg+="												<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="													<tr>";
		msg+="														<td valign=\"middle\" width=\"100%\" style=\"text-align: center; font-family: Helvetica, Arial, sans-serif; font-size: 14px; color: rgb(255, 255, 255); line-height: 24px;\"class=\"fullCenter\" >";
		msg+="															<span ><!--[if !mso]><!--><span style=\"font-family: 'proxima_nova_rgregular', Helvetica; font-weight: normal;\"><!--<![endif]-->Altrimenti vai al <!--[if !mso]><!--></span><!--<![endif]--></span> ";
		msg+="															<!--[if !mso]><!--><span style=\"font-family: 'proxima_novasemibold', Helvetica; font-weight: normal;\"><!--<![endif]--><a href=\"http://suite.smartretail.it\"  style=\"color: rgb(255, 255, 255);\">Login</a><!--[if !mso]><!--></span><!--<![endif]-->";
		msg+="														</td>";
		msg+="													</tr>";
		msg+="												</table>";							
		msg+="											</td>";
		msg+="										</tr>";
		msg+="									</table>";
											
		msg+="									<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#e85140\"style=\"border-bottom-left-radius: 5px; border-bottom-right-radius: 5px; background-color: rgb(29, 200, 233);\" object=\"drag-module-small\">";
		msg+="										<tr>";
		msg+="											<td align=\"center\" width=\"352\" valign=\"middle\">";
													
		msg+="												<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="													<tr>";
		msg+="														<td width=\"100%\" height=\"50\"></td>";
		msg+="													</tr>";
		msg+="												</table>";
																						
		msg+="											</td>";
		msg+="										</tr>";
		msg+="									</table>";
																			
		msg+="									</div>";
											
		msg+="								</td>";
		msg+="							</tr>";
		msg+="						</table>";
								
		msg+="					</td>";
		msg+="					<td align=\"center\" width=\"20\" valign=\"middle\" bgcolor=\"#2a2a2a\"></td>";
		msg+="				</tr>";
		msg+="			</table>";
					
		msg+="			<!-- Mobile Wrapper -->";
		msg+="			<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" bgcolor=\"#2a2a2a\"style=\"background-color: rgb(42, 42, 42);\">";
		msg+="				<tr>";
		msg+="					<td width=\"100%\" align=\"center\" bgcolor=\"#2a2a2a\"style=\"background-color: rgb(42, 42, 42);\">";
								
		msg+="						<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" object=\"drag-module-small\">";
		msg+="							<tr>";
		msg+="								<td align=\"center\" width=\"352\" valign=\"middle\">";
										
		msg+="									<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="										<tr>";
		msg+="											<td width=\"100%\" height=\"30\"></td>";
		msg+="										</tr>";
		msg+="									</table>";
																			
		msg+="								</td>";
		msg+="							</tr>";
		msg+="						</table>";
								
		msg+="						<table width=\"352\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" class=\"mobile\" object=\"drag-module-small\">";
		msg+="							<tr>";
		msg+="								<td align=\"center\" width=\"352\" valign=\"middle\">";
										
		msg+="									<table width=\"265\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" style=\"text-align: center; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;\" class=\"fullCenter\">";
		msg+="										<tr>";
		msg+="											<td width=\"100%\" height=\"50\"></td>";
		msg+="										</tr>";
		msg+="										<tr>";
		msg+="											<td width=\"100%\" height=\"1\" style=\"font-size: 1px; line-height: 1px;\">&nbsp;</td>";
		msg+="										</tr>";
		msg+="									</table>";
																			
		msg+="								</td>";
		msg+="							</tr>";
		msg+="						</table>";
											
		msg+="					</td>";
		msg+="				</tr>";
		msg+="			</table>";
					
		msg+="		</div>";
		msg+="		</td>";
		msg+="	</tr>";
		msg+="</table><!-- End Notification 2 -->";

		msg+="<!-- Notification 3 -->";
		msg+="<div style=\"display: none\" id=\"element_08801515453960747\"></div><!-- End Notification 3 -->";

		msg+="<!-- Notification 4  -->";
		msg+="<div style=\"display: none\" id=\"element_021636921958997846\"></div><!-- End Notification 4 -->";

		msg+="<!-- Notification 5 -->";
		msg+="<div style=\"display: none\" id=\"element_014803599403239787\"></div><!-- End Notification 5 -->";

		msg+="<!-- Notification 6 -->";
		msg+="<div style=\"display: none\" id=\"element_08976250828709453\"></div><!-- End Notification 6 -->";
		msg+="</div>";
		msg+="</body></html>	<style>body{ background: none !important; } </style>";
		
		return msg;
		
	}
	
}
