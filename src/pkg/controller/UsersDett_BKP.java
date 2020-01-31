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

public class UsersDett_BKP extends AbstractController {
	
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
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			// Relazioni
			String $rel_user_customers="";
			String $rel_user_stores="";
			
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
					
					
					sql   = "insert into zutenti "+
							"(idusr, pwd, nome, cogn, tipo, foto, mail, db, style, logo, sysinf, language) "+
							"values "+
							"('"+$idusr.replaceAll("'", "''").toUpperCase()+"', "+
							"'"+$pwd.replaceAll("'", "''")+"', "+
							"'"+$nome.replaceAll("'", "''").toUpperCase()+"', "+
							"'"+$cogn.replaceAll("'", "''").toUpperCase()+"', "+
							"'"+$tipo.replaceAll("'", "''").toUpperCase()+"', "+
							"'default-icon-user.png', "+
							"'"+$mail.replaceAll("'", "''").toUpperCase()+"', "+
							"'smarthr', 'bg-info', 'logo.png', 'SmartHR', '"+$language+"' ) ";
					
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
					sql   = "delete from rel_user_customers where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					System.out.println(sql);
					st.executeUpdate(sql);
					
					// Inserimento in rel_user_customers
					$rel_user_customers = req.getParameter("rel_user_customers");
					String temp1[] = $rel_user_customers.split(",");
					for(int c=0; c<temp1.length; c++) {
						sql   = "insert into rel_user_customers "+
								"(idusr, idcustomer) "+
								"values "+
								"('"+$idusr.replaceAll("'", "''").toUpperCase()+"', "+temp1[c]+") ";
						try {
							st.executeUpdate(sql);
						}
						catch(Exception e) {}
					}
					
					// Cancella il contenuto della tabella rel_user_stores per l'utente
					sql   = "delete from rel_user_stores where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					System.out.println(sql);
					st.executeUpdate(sql);
					
					// Inserimento in rel_user_stores
					$rel_user_stores = req.getParameter("rel_user_stores");
					String temp2[] = $rel_user_stores.split(",");
					for(int s=0; s<temp2.length; s++) {
						sql   = "insert into rel_user_stores "+
								"(idusr, idstore) "+
								"values "+
								"('"+$idusr.replaceAll("'", "''").toUpperCase()+"', "+temp2[s]+") ";
						try {
							st.executeUpdate(sql);
						}
						catch(Exception e) {}
					}
					
					// Per ogni applicazione in zapp imposto le autorizzazioni 
					sql   = "select cdapp from zapp ";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						
						// Inserimento in zutefunz (se modificato)
						String $utefunz = req.getParameter("zutefunz_"+rs.getString("cdapp"));
						
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
					$pwd = req.getParameter("pwd");
					$nome = req.getParameter("nome");
					$cogn = req.getParameter("cogn");
					$tipo = req.getParameter("tipo");
					$mail = req.getParameter("mail");
					$language = req.getParameter("language");
					
					sql  =  "update zutenti "+
							"set nome = '"+$nome.replaceAll("'", "''").toUpperCase()+"', "+
							"cogn = '"+$cogn.replaceAll("'", "''").toUpperCase()+"', "+
							"tipo = '"+$tipo+"', mail = '"+$mail.toUpperCase().replaceAll("'", "''")+"', "+
							"language = '"+$language+"' "+
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
					sql   = "delete from rel_user_customers where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					System.out.println(sql);
					st.executeUpdate(sql);
					
					// Inserimento in rel_user_customers
					$rel_user_customers = req.getParameter("rel_user_customers");
					String temp1[] = $rel_user_customers.split(",");
					for(int c=0; c<temp1.length; c++) {
						sql   = "insert into rel_user_customers "+
								"(idusr, idcustomer) "+
								"values "+
								"('"+$idusr.replaceAll("'", "''").toUpperCase()+"', "+temp1[c]+") ";
						System.out.println(sql);
						if(temp1[c].length()>0)
							st.executeUpdate(sql);
					}
					
					// Cancella il contenuto della tabella rel_user_stores per l'utente
					sql   = "delete from rel_user_stores where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					System.out.println(sql);
					st.executeUpdate(sql);
					
					// Inserimento in rel_user_stores
					$rel_user_stores = req.getParameter("rel_user_stores");
					String temp2[] = $rel_user_stores.split(",");
					for(int s=0; s<temp2.length; s++) {
						sql   = "insert into rel_user_stores "+
								"(idusr, idstore) "+
								"values "+
								"('"+$idusr.replaceAll("'", "''").toUpperCase()+"', "+temp2[s]+") ";
						System.out.println(sql);
						if(temp2[s].length()>0)
							st.executeUpdate(sql);
					}
					
					// Per ogni applicazione in zapp imposto le autorizzazioni 
					sql   = "select cdapp from zapp ";
					rs = st.executeQuery(sql);
					while(rs.next()) {
						
						// Inserimento in zutefunz (se modificato)
						String $utefunz = req.getParameter("zutefunz_"+rs.getString("cdapp"));
						
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
					
					// Cancella il contenuto della tabella rel_user_customers per l'utente
					sql   = "delete from rel_user_customers where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
					st.executeUpdate(sql);
					
					// Cancella il contenuto della tabella rel_user_stores per l'utente
					sql   = "delete from rel_user_stores where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
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
					
				}
				rs.close();
				
				// *blank variabili
				$rel_user_customers = "";
				$rel_user_stores = "";
				
				sql   = "select idcustomer "+
						"from rel_user_customers where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
				rs = st.executeQuery(sql);
				while(rs.next()) {
					$rel_user_customers+=rs.getString("idcustomer")+",";
				}
				rs.close();
				
				if($rel_user_customers.length()>0)
					$rel_user_customers=$rel_user_customers.substring(0,$rel_user_customers.length()-1);
				
				
				sql   = "select idstore "+
						"from rel_user_stores where idusr = '"+$idusr.replaceAll("'", "''").toUpperCase()+"' ";
				rs = st.executeQuery(sql);
				while(rs.next()) {
					$rel_user_stores+=rs.getString("idstore")+",";
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
			sql  =  "select a.cdfun, b.cdfun aut, l.descr "+
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
				
				fun.add(new Function($cdfun,$descr,"","","",$aut,""+nrRiga,"",""));
				
				$aut = "";
			}
			rs.close();
			
			// ==========================
			// Lista Ruoli
			sql  =  "select * from roles ";
			rs=st.executeQuery(sql);
			
			// Campi database
			String $id="";
			String $name="";
			String $description="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id=rs.getString("id");
				$name=rs.getString("name");
				$description=rs.getString("description");
				
				rol.add(new Roles($id,$name,$description,"","","","",""));
				
			}
			rs.close();
			
			
			// ====================
			// Lista applicazioni
			sql  =  "select a.cdapp, b.cdapp aut, a.nome, a.descr "+
					"from zapp a "+
					"left join zuteapp b on a.cdapp = b.cdapp and b.idusr = '"+$idusr+"' "+
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
			
			obj.put("rel_user_customers", $rel_user_customers);
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
