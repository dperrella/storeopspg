package pkg.controller;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Function;
import pkg.beans.Roles;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class UsersDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		List<Function> fun = new ArrayList<Function>();
		List<Tables> lang = new ArrayList<Tables>();
		List<Roles> rol = new ArrayList<Roles>();
		List<Function> app = new ArrayList<Function>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		fun.clear();
		lang.clear();
		rol.clear();
		app.clear();
		
		
		Connection conn = null;
		Statement st = null;
		Statement up = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		String msgEli = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		ReadProperties prop = new ReadProperties("connection.properties");
		String cdapp = prop.get("cdapp");
		
		try {
			
			// Campi database
			String $idusr="";
			String $readonly="";
			
			String $pwd="";
			String $nome="";
			String $cogn="";
			String $tipo="";
			String $foto="";
			String $mail="";
			String $db="";
			String $style="";
			String $logo="";
			String $sysinf="";
			String $language="";
			String $token="";
			
			String $tipo_hr="";
			
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			// Relazioni
			String $rel_user_customers="";
			String $rel_user_stores="";
			String $rel_user_country="";
			String $rel_user_country2="";
			
			
			$idusr = ""+req.getParameter("idusr");
			$idusr = Utility.noNull($idusr);
			$idusr = $idusr.toUpperCase();
			
			conn = Utility.getConnAdmin();
			
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			up = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			String tpFrm = tipoForm;
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					$pwd = req.getParameter("pwd");
					
					// Trasformo la password in MD5
					MessageDigest md = MessageDigest.getInstance("MD5");
					md.update($pwd.getBytes());
					byte[] digest = md.digest();
					StringBuffer sb = new StringBuffer();
					for (byte b : digest) {
						sb.append(String.format("%02x", b & 0xff));
					}
					$pwd = sb.toString();
					
					$nome = req.getParameter("nome");
					$cogn = req.getParameter("cogn");
					$tipo = req.getParameter("tipo");
					$mail = req.getParameter("mail");
					$language = req.getParameter("language");
					$token = req.getParameter("token");
					$tipo_hr = ""+req.getParameter("tipo_hr");
					$tipo_hr = Utility.noNull($tipo_hr);
					
					sql   = "insert into zutenti "+
							"(idusr, pwd, nome, cogn, tipo, foto, mail, db, style, logo, sysinf, language, token, tipo_hr) "+
							"values "+
							"('"+$idusr.replaceAll("'", "''").toUpperCase().trim()+"', "+
							"'"+$pwd.replaceAll("'", "''").trim()+"', "+
							"'"+$nome.replaceAll("'", "''").toUpperCase().trim()+"', "+
							"'"+$cogn.replaceAll("'", "''").toUpperCase().trim()+"', "+
							"'"+$tipo.replaceAll("'", "''").toUpperCase().trim()+"', "+
							"'default-icon-user.png', "+
							"'"+$mail.replaceAll("'", "''").toUpperCase().trim()+"', "+
							"'smarthr', 'bg-info', 'logo.png', 'SmartHR', '"+$language+"' , '"+$token+"', '"+$tipo_hr+"' ) ";
					
					System.out.println(sql);
					st.executeUpdate(sql);
					
					// Funzioni
					String[] cd = req.getParameterValues("nrRiga");
					String nrRiga = "";
					String cdfun = "";
					String post = "";
					
					// Cancella il contenuto della tabella zutefunz per l'utente
					sql   = "delete from zutefunz where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' "
							+ "and cdfun in(select cdfun from zfunzioni where cdapp = '"+cdapp+"') ";
					st.executeUpdate(sql);
					
					if(cd!=null) {
						// prendiamo tutti i valori dell'input multiplo
						if (cd.length>0) {
							
							// riversiamo l'array
							for(int i=0; i<cd.length; i++) {
								
								nrRiga = req.getParameterValues("nrRiga")[i];
								cdfun = req.getParameterValues("cdfun")[i];
								
								System.out.println(cdfun);
								
								try {
									post = ""+req.getParameter("post"+nrRiga);
									
									System.out.println(post);
									
									post = Utility.noNull(post);
									
								}
								catch(Exception e) {
									e.printStackTrace();
								}
								
								if(post.length()>0) {
									
									// Inserisce autorizzazione
									sql   = "insert into zutefunz (cdfun, cdazi, cdcli, idusr) "+
											"values "+
											"('"+cdfun+"', 'T', ' ', '"+$idusr.replaceAll("'", "''").toUpperCase()+"') ";
									System.out.println(sql);
									st.executeUpdate(sql);
									
								}
								
							}
							
						}
					
					}
					
					// Applicazioni
					String[] cdApp = req.getParameterValues("nrRigaApp");
					String nrRigaApp = "";
					String cdfunApp = "";
					String postApp = "";
					
					// Cancella il contenuto della tabella zuteapp per l'utente
					sql   = "delete from zuteapp where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					st.executeUpdate(sql);
					
					if(cdApp!=null) {
						// prendiamo tutti i valori dell'input multiplo
						if (cdApp.length>0) {
							
							// riversiamo l'array
							for(int i=0; i<cdApp.length; i++) {
								
								nrRigaApp = req.getParameterValues("nrRigaApp")[i];
								cdfunApp = req.getParameterValues("cdfunApp")[i];
								
								System.out.println(cdfunApp);
								
								try {
									postApp = ""+req.getParameter("postApp"+nrRigaApp);
									
									System.out.println(postApp);
									
									postApp = Utility.noNull(postApp);
									
								}
								catch(Exception e) {
									e.printStackTrace();
								}
								
								if(postApp.length()>0) {
									
									// Inserisce autorizzazione
									sql   = "insert into zuteapp (cdapp, cdazi, cdcli, idusr) "+
											"values "+
											"('"+cdfunApp+"', 'T', ' ', '"+$idusr.replaceAll("'", "''").toUpperCase()+"') ";
									System.out.println(sql);
									st.executeUpdate(sql);
									
								}
								
							}
							
						}
					
					}
					
					
					// Cancella il contenuto della tabella rel_user_customers per l'utente
					sql   = "delete from rel_user_category where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					System.out.println(sql);
					st.executeUpdate(sql);
					
					if($tipo.equals("2")) {
						// Non inserisco nulla
					}
					else if($tipo.equals("3")||$tipo.equals("4")) {
						
						$rel_user_customers = req.getParameter("rel_user_customers");
						String t1[] = $rel_user_customers.split(",");
						
						$rel_user_country = req.getParameter("rel_user_country");
						String t2[] = $rel_user_country.split(",");
						
						$rel_user_stores = req.getParameter("rel_user_stores");
						String t4[] = $rel_user_stores.split(",");
						
						System.out.println("!"+$rel_user_stores+"!");
						
						if($rel_user_stores.length()>0) {
							
							for(int i=0;i<t4.length;i++) {
								
								String fld[] = t4[i].split(";");
								String cus = fld[0];
								String naz = fld[1];
								String sto = fld[2];
								
								sql   = "insert into rel_user_category "+
										"(idusr, category1, category2, category3) "+
										"values "+
										"('"+$idusr+"','"+cus+"','"+naz+"','"+sto+"') ";
								st.executeUpdate(sql);
								
								
							}
							
						}
						else if($rel_user_country.length()>0) {
							
							for(int i=0;i<t2.length;i++) {
								
								String fld[] = t2[i].split(";");
								String cus = fld[0];
								String naz = fld[1];
								
								sql   = "insert into rel_user_category "+
										"(idusr, category1, category2, category3) "+
										"values "+
										"('"+$idusr+"','"+cus+"','"+naz+"','*') ";
								st.executeUpdate(sql);
								
							}
							
						}
						else if($rel_user_customers.length()>0) {
							
							for(int i=0;i<t1.length;i++) {
								
								sql   = "insert into rel_user_category "+
										"(idusr, category1, category2, category3) "+
										"values "+
										"('"+$idusr+"','"+t1[i]+"','*','*') ";
								st.executeUpdate(sql);
								
							}
						}
						
					}
					else if($tipo.equals("5")) {
						
						$rel_user_country2 = req.getParameter("rel_user_country2");
						String t3[] = $rel_user_country2.split(",");
						
						for(int i=0;i<t3.length;i++) {
							
							sql   = "insert into rel_user_category "+
									"(idusr, category1, category2, category3) "+
									"values "+
									"('"+$idusr+"','*','"+t3[i]+"','*') ";
							st.executeUpdate(sql);
							
						}
						
					}
					else if($tipo.equals("6")) {
						
						$rel_user_customers = req.getParameter("rel_user_customers");
						String t1[] = $rel_user_customers.split(",");
						
						$rel_user_country = req.getParameter("rel_user_country");
						String t2[] = $rel_user_country.split(",");
						
						if($rel_user_country.length()>0) {
							
							for(int i=0;i<t2.length;i++) {
								
								String fld[] = t2[i].split(";");
								String cus = fld[0];
								String naz = fld[1];
								
								sql   = "insert into rel_user_category "+
										"(idusr, category1, category2, category3) "+
										"values "+
										"('"+$idusr+"','"+cus+"','"+naz+"','*') ";
								st.executeUpdate(sql);
								
							}
							
						}
						else if($rel_user_customers.length()>0) {
							
							for(int i=0;i<t1.length;i++) {
								
								sql   = "insert into rel_user_category "+
										"(idusr, category1, category2, category3) "+
										"values "+
										"('"+$idusr+"','"+t1[i]+"','*','*') ";
								st.executeUpdate(sql);
								
							}
						}
					}
					
					
					
					sql   = "select cdapp from zapp ";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						
						// Inserimento in zutefunz (se modificato)
						String $utefunz = req.getParameter("zutefunz_"+rs.getString("cdapp"));
						
						$utefunz = Utility.noNull(""+$utefunz);

						
						if($utefunz.length()>0) {
							
							// Cancella il contenuto della tabella rel_user_stores per l'utente
							sql   = "delete from zutefunz "
									+ "where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' "
									+ "and cdfun in(select cdfun from zfunzioni where cdapp = '"+rs.getString("cdapp")+"') ";
							System.out.println(sql);
							up.executeUpdate(sql);
							
							String temp3[] = $utefunz.split(",");
							for(int f=0; f<temp3.length; f++) {
								sql   = "insert into zutefunz "+
										"(cdfun, cdazi, cdcli, idusr) "+
										"values "+
										"('"+temp3[f]+"', 'T', '', '"+$idusr.replaceAll("'", "''").toUpperCase()+"') ";
								System.out.println(sql);
								try {
									up.executeUpdate(sql);
								}
								catch(Exception e) {}
							}
						}
						
					}
					rs.close();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					
					tpFrm = "M";
					
				}
				
				else if(tipoForm.equals("M")) {
					
					// Modifica
					System.out.println("MDO...");
					$pwd = ""+req.getParameter("pwd");
					$nome = req.getParameter("nome");
					$cogn = req.getParameter("cogn");
					$tipo = req.getParameter("tipo");
					$mail = req.getParameter("mail");
					$language = req.getParameter("language");
					$token = req.getParameter("token");
					$tipo_hr = ""+req.getParameter("tipo_hr");
					$tipo_hr = Utility.noNull($tipo_hr);
					
					
					sql  =  "update zutenti "+
							"set nome = '"+$nome.replaceAll("'", "''").toUpperCase()+"', "+
							"cogn = '"+$cogn.replaceAll("'", "''").toUpperCase()+"', "+
							"tipo = '"+$tipo+"', mail = '"+$mail.toUpperCase().replaceAll("'", "''")+"', "+
							"language = '"+$language+"', token = '"+$token+"', tipo_hr = '"+$tipo_hr+"', active = true "+
							"where idusr = '"+$idusr+"' ";
					st.executeUpdate(sql);
				
					// Funzioni
					String[] cd = req.getParameterValues("nrRiga");
					String nrRiga = "";
					String cdfun = "";
					String post = "";
					
					// Cancella il contenuto della tabella zutefunz per l'utente
					sql   = "delete from zutefunz where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' "
							+ "and cdfun in(select cdfun from zfunzioni where cdapp = '"+cdapp+"') ";
					st.executeUpdate(sql);
					
					if(cd!=null) {
						// prendiamo tutti i valori dell'input multiplo
						if (cd.length>0) {
							
							// riversiamo l'array
							for(int i=0; i<cd.length; i++) {
								
								nrRiga = req.getParameterValues("nrRiga")[i];
								cdfun = req.getParameterValues("cdfun")[i];
								
								try {
									post = ""+req.getParameter("post"+nrRiga);
									post = Utility.noNull(post);
								}
								catch(Exception e) {
									e.printStackTrace();
									
								}
								
								if(post.length()>0) {
									
									// Inserisce autorizzazione
									sql   = "insert into zutefunz (cdfun, cdazi, cdcli, idusr) "+
											"values "+
											"('"+cdfun+"', 'T', ' ', '"+$idusr.replaceAll("'", "''").toUpperCase()+"') ";
									System.out.println(sql);
									st.executeUpdate(sql);
									
								}
								
							}
							
						}
					
					}
					
					// Applicazioni
					String[] cdApp = req.getParameterValues("nrRigaApp");
					String nrRigaApp = "";
					String cdfunApp = "";
					String postApp = "";
					
					// Cancella il contenuto della tabella zuteapp per l'utente
					sql   = "delete from zuteapp where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					st.executeUpdate(sql);
					
					if(cdApp!=null) {
						// prendiamo tutti i valori dell'input multiplo
						if (cdApp.length>0) {
							
							// riversiamo l'array
							for(int i=0; i<cdApp.length; i++) {
								
								nrRigaApp = req.getParameterValues("nrRigaApp")[i];
								cdfunApp = req.getParameterValues("cdfunApp")[i];
								
								System.out.println(cdfunApp);
								
								try {
									postApp = ""+req.getParameter("postApp"+nrRigaApp);
									
									System.out.println(postApp);
									
									postApp = Utility.noNull(postApp);
									
								}
								catch(Exception e) {
									e.printStackTrace();
								}
								
								if(postApp.length()>0) {
									
									// Inserisce autorizzazione
									sql   = "insert into zuteapp (cdapp, cdazi, cdcli, idusr) "+
											"values "+
											"('"+cdfunApp+"', 'T', ' ', '"+$idusr.replaceAll("'", "''").toUpperCase()+"') ";
									System.out.println(sql);
									st.executeUpdate(sql);
									
								}
								
							}
							
						}
					
					}
					
					// Cancella il contenuto della tabella rel_user_customers per l'utente
					sql   = "delete from rel_user_category where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					System.out.println(sql);
					st.executeUpdate(sql);
					
					if($tipo.equals("2")) {
						// Non inserisco nulla
					}
					else if($tipo.equals("3")||$tipo.equals("4")) {
						
						$rel_user_customers = req.getParameter("rel_user_customers");
						String t1[] = $rel_user_customers.split(",");
						
						$rel_user_country = req.getParameter("rel_user_country");
						String t2[] = $rel_user_country.split(",");
						
						System.out.println($rel_user_country);
						
						$rel_user_stores = req.getParameter("rel_user_stores");
						String t4[] = $rel_user_stores.split(",");
						
						System.out.println("!"+$rel_user_stores+"!");
						
						if($rel_user_stores.length()>0) {
							
							for(int i=0;i<t4.length;i++) {
								
								String fld[] = t4[i].split(";");
								String cus = fld[0];
								String naz = fld[1];
								String sto = fld[2];
								
								sql   = "insert into rel_user_category "+
										"(idusr, category1, category2, category3) "+
										"values "+
										"('"+$idusr+"','"+cus+"','"+naz+"','"+sto+"') ";
								st.executeUpdate(sql);
								
								
							}
							
						}
						else if($rel_user_country.length()>0) {
							
							for(int i=0;i<t2.length;i++) {
								
								String fld[] = t2[i].split(";");
								String cus = fld[0];
								String naz = fld[1];
								
								sql   = "insert into rel_user_category "+
										"(idusr, category1, category2, category3) "+
										"values "+
										"('"+$idusr+"','"+cus+"','"+naz+"','*') ";
								System.out.println(sql);
								st.executeUpdate(sql);
								
							}
							
						}
						else if($rel_user_customers.length()>0) {
							
							for(int i=0;i<t1.length;i++) {
								
								sql   = "insert into rel_user_category "+
										"(idusr, category1, category2, category3) "+
										"values "+
										"('"+$idusr+"','"+t1[i]+"','*','*') ";
								st.executeUpdate(sql);
								
							}
						}
						
					}
					else if($tipo.equals("5")) {
						
						$rel_user_country2 = req.getParameter("rel_user_country2");
						String t3[] = $rel_user_country2.split(",");
						
						for(int i=0;i<t3.length;i++) {
							
							sql   = "insert into rel_user_category "+
									"(idusr, category1, category2, category3) "+
									"values "+
									"('"+$idusr+"','*','"+t3[i]+"','*') ";
							st.executeUpdate(sql);
							
						}
						
					}
					else if($tipo.equals("6")) {
						
						$rel_user_customers = req.getParameter("rel_user_customers");
						String t1[] = $rel_user_customers.split(",");
						
						$rel_user_country = req.getParameter("rel_user_country");
						String t2[] = $rel_user_country.split(",");
						
						if($rel_user_country.length()>0) {
							
							for(int i=0;i<t2.length;i++) {
								
								String fld[] = t2[i].split(";");
								String cus = fld[0];
								String naz = fld[1];
								
								sql   = "insert into rel_user_category "+
										"(idusr, category1, category2, category3) "+
										"values "+
										"('"+$idusr+"','"+cus+"','"+naz+"','*') ";
								st.executeUpdate(sql);
								
							}
							
						}
						else if($rel_user_customers.length()>0) {
							
							for(int i=0;i<t1.length;i++) {
								
								sql   = "insert into rel_user_category "+
										"(idusr, category1, category2, category3) "+
										"values "+
										"('"+$idusr+"','"+t1[i]+"','*','*') ";
								st.executeUpdate(sql);
								
							}
						}
					}
					
					
					
					// Per ogni applicazione in zapp imposto le autorizzazioni 
					sql   = "select cdapp from zapp ";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						
						// Inserimento in zutefunz (se modificato)
						String $utefunz = ""+req.getParameter("zutefunz_"+rs.getString("cdapp"));
						$utefunz=Utility.noNull($utefunz);
						
						
						if($utefunz.length()>0) {
							
							// Cancella il contenuto della tabella rel_user_stores per l'utente
							sql   = "delete from zutefunz "
									+ "where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' "
									+ "and cdfun in(select cdfun from zfunzioni where cdapp = '"+rs.getString("cdapp")+"') ";
							System.out.println(sql);
							up.executeUpdate(sql);
							
							String temp3[] = $utefunz.split(",");
							for(int f=0; f<temp3.length; f++) {
								sql   = "insert into zutefunz "+
										"(cdfun, cdazi, cdcli, idusr) "+
										"values "+
										"('"+temp3[f]+"', 'T', '', '"+$idusr.replaceAll("'", "''").toUpperCase()+"') ";
								System.out.println(sql);
								try {
									up.executeUpdate(sql);
								}
								catch(Exception e) {}
							}
						}
						
					}
					rs.close();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancella il contenuto della tabella zuteapp per l'utente
					sql   = "delete from zuteapp where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					st.executeUpdate(sql);
					
					// Cancella il contenuto della tabella zutefunz per l'utente
					sql   = "delete from zutefunz where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					st.executeUpdate(sql);
					
					// Cancella il contenuto della tabella rel_user_category per l'utente
					sql   = "delete from rel_user_category where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					st.executeUpdate(sql);
					
					// Cancellazione
					System.out.println("ELI...");
					sql   = "delete from zutenti where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					st.executeUpdate(sql);
					
					// *blank id
					$idusr = "";
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "I";
					
				}
				
				
				
				tipoForm = tpFrm;	
				
			}
			
			if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
				
				$readonly = "readonly";
				
				// Messaggio conferma cancellazione record
				if(tipoForm.equals("E")) {
					msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";	
				}
				// Bottone non attivo in visualizzazione
				if(tipoForm.equals("V")) {
					btnActive = "disabled";	
				}
				
				
				// ===================
				// Dettaglio / Modifica / Elimina 
				sql  =  "select * from zutenti where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
				rs=st.executeQuery(sql);
				if(rs.next()) {
					
					$pwd=rs.getString("pwd");
					$nome=rs.getString("nome");
					$cogn=rs.getString("cogn");
					$tipo=rs.getString("tipo");
					$foto=rs.getString("foto");
					$mail=rs.getString("mail");
					$db=rs.getString("db");
					$style=rs.getString("style");
					$logo=rs.getString("logo");
					$sysinf=rs.getString("sysinf");
					$language=""+rs.getString("language");
					$language=Utility.noNull($language);
					$token=""+rs.getString("token");
					$token=Utility.noNull($token);
					$tipo_hr = ""+rs.getString("tipo_hr");
					$tipo_hr = Utility.noNull($tipo_hr);
					
				}
				rs.close();
				
				// *blank variabili
				$rel_user_customers = "";
				$rel_user_country = "";
				$rel_user_country2 = "";
				
				$rel_user_stores = "";
				
				sql   = "select category1 "+
						"from rel_user_category where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' group by category1 ";
				rs = st.executeQuery(sql);
				while(rs.next()) {
					String cat1 = rs.getString("category1");
					if(cat1.equals("*")) {
						cat1 = "";
					}
					
					if(cat1.length()>0) {
						$rel_user_customers+=rs.getString("category1")+",";
					}
				}
				rs.close();
				
				if($rel_user_customers.length()>0)
					$rel_user_customers=$rel_user_customers.substring(0,$rel_user_customers.length()-1);
				
				sql   = "select category1, category2 "+
						"from rel_user_category where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' "+
						"group by category1, category2 ";
				rs = st.executeQuery(sql);
				while(rs.next()) {
					String cat2 = rs.getString("category2");
					if(cat2.equals("*"))
						cat2 = "";
					
					if(cat2.length()>0) {
						$rel_user_country+=rs.getString("category1")+";"+rs.getString("category2")+",";
						$rel_user_country2+=rs.getString("category2")+",";
					}
				}
				rs.close();
				
				if($rel_user_country.length()>0) {
					$rel_user_country=$rel_user_country.substring(0,$rel_user_country.length()-1);
					$rel_user_country2=$rel_user_country2.substring(0,$rel_user_country2.length()-1);
				}
				
				sql   = "select category1, category2, category3 "+
						"from rel_user_category where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' "+
						"group by category1, category2, category3 ";
				rs = st.executeQuery(sql);
				while(rs.next()) {
					String cat3 = rs.getString("category3");
					if(cat3.equals("*"))
						cat3 = "";
					
					if(cat3.length()>0) {
						$rel_user_stores+=rs.getString("category1")+";"+rs.getString("category2")+";"+rs.getString("category3")+",";
					}
				}
				rs.close();
				
				if($rel_user_stores.length()>0)
					$rel_user_stores=$rel_user_stores.substring(0,$rel_user_stores.length()-1);
				
				
				
				
				
				
			}
			
			// ====================
			// Lista Lingue
			sql  =  "select xcdel, xdtab from ztables "+
					"where xcdtb = 'LAN' and ata32 = ' ' ";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				lang.add(new Tables("LAN",rs.getString("xcdel"),rs.getString("xdtab"),"","","",""));
			}
			rs.close();
			
			// ====================
			// Lista funzioni
			sql  =  "select a.cdfun, a.tipo, b.cdfun aut, l.descr "+
					"from zfunlang l, zfunzioni a "+
					"left join zutefunz b on a.cdfun = b.cdfun and b.idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' "+
					"where l.cdfun = a.cdfun and l.lang = '"+(String)session.getAttribute("language")+"' "+
					"and cdapp = '"+cdapp+"' ";
					
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			String $cdfun = "";
			String $descr = "";
			String $aut = "";
			int nrRiga = 0;
			while(rs.next()) {
				
				nrRiga+=1;
				$cdfun = rs.getString("cdfun");
				$descr = rs.getString("descr");
				$aut = ""+rs.getString("aut");
				$aut = Utility.noNull($aut);
				
				if($aut.length()>0) {
					$aut = "checked";
				}
				
				if(rs.getString("tipo").trim().equals("YY")) {
					
					if(session.getAttribute("username").toString().toUpperCase().trim().equals("DEVELOP")) {
						
						fun.add(new Function($cdfun,$descr,"","","",$aut,""+nrRiga,"",""));
						
						$aut = "";
						
					}
					
				}
				else {
					
					fun.add(new Function($cdfun,$descr,"","","",$aut,""+nrRiga,"",""));
					
					$aut = "";
					
				}
				
			}
			rs.close();
			
			// ==========================
			// Lista Ruoli
			String role = (String) session.getAttribute("tipo").toString().trim();
			
			sql  =  "select * from roles ";
			rs=st.executeQuery(sql);
			
			// Campi database
			String $id="";
			String $name="";
			String $description="";
			String $is_admin="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id=rs.getString("id");
				$name=rs.getString("name");
				$description=rs.getString("description");
				$is_admin=rs.getString("is_admin");
				
				if(role.equals("2")) {
					rol.add(new Roles($id,$name,$description,$is_admin,"","","",""));
				}
				else {
					if(role.equals("4")) {
						if($id.equals("3")||$id.equals("4")) {
							rol.add(new Roles($id,$name,$description,$is_admin,"","","",""));
						}
					}
					if(role.equals("5")) {
						if($id.equals("3")||$id.equals("4")||$id.equals("5")) {
							rol.add(new Roles($id,$name,$description,$is_admin,"","","",""));
						}
					}
					if(role.equals("6")) {
						if($id.equals("6")) {
							rol.add(new Roles($id,$name,$description,$is_admin,"","","",""));
						}
					}
					if(role.equals("7")) {
						if($id.equals("7")) {
							rol.add(new Roles($id,$name,$description,$is_admin,"","","",""));
						}
					}
				}
			}
			rs.close();
			
			
			// ====================
			// Lista applicazioni
			
			// Per ogni applicazione in zapp imposto le autorizzazioni 
			String apps = session.getAttribute("arrayApp").toString();
			String temp[]=apps.split(";");
			cond = "";
			for(int i=0;i<temp.length;i++){
				cond+="'"+temp[i]+"',";
			}
			if(cond.length()>0) 
				cond=cond.substring(0,cond.length()-1);
			
			if(!$idusr.equals("DEVELOP")) {
				cond="where a.cdapp in("+cond+") ";
			}
			else {
				cond="";
			}
			
			sql  =  "select a.cdapp, b.cdapp aut, a.nome, a.descr "+
					"from zapp a "+
					"left join zuteapp b on a.cdapp = b.cdapp and b.idusr = '"+$idusr+"' "+
					cond+
					"order by a.cdapp ";
					
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			$cdfun = "";
			String $nomeApp = "";
			$descr = "";
			$aut = "";
			nrRiga = 0;
			while(rs.next()) {
				
				nrRiga+=1;
				$cdfun = rs.getString("cdapp");
				$nomeApp = rs.getString("nome");
				$descr = rs.getString("descr");
				$aut = ""+rs.getString("aut");
				$aut = Utility.noNull($aut);
				
				if($aut.length()>0) {
					$aut = "checked";
				}
				
				app.add(new Function($cdfun,$descr,$nomeApp,"","",$aut,""+nrRiga,"",""));
				
				$aut = "";
			}
			rs.close();
			
			
			
			obj.put("idusr", $idusr);
			obj.put("readonly", $readonly);
			obj.put("pwd", $pwd);
			obj.put("nome", $nome);
			obj.put("cogn", $cogn);
			obj.put("tipo", $tipo.trim());
			obj.put("foto", $foto);
			obj.put("mail", $mail);
			obj.put("db", $db);
			obj.put("style", $style);
			obj.put("logo", $logo);
			obj.put("sysinf", $sysinf);
			obj.put("language", $language);
			obj.put("token", $token);
			obj.put("tipo_hr", $tipo_hr.trim());
			
			obj.put("rel_user_customers", $rel_user_customers);
			obj.put("rel_user_country", $rel_user_country);
			obj.put("rel_user_country2", $rel_user_country2);
			obj.put("rel_user_stores", $rel_user_stores);
			
			
			up.close();
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Utente");
		obj.put("tipoForm", tipoForm);
		
		obj.put("fun", fun);
		obj.put("lang", lang);
		obj.put("rol", rol);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		obj.put("app", app);
		
		return new ModelAndView("/app/config/users-dett", obj);
	}
	
}
