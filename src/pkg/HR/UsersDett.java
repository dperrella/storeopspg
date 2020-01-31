package pkg.HR;

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
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		fun.clear();
		lang.clear();
		
		Connection conn = null;
		Statement st = null;
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
			
			$idusr = ""+req.getParameter("idusr");
			$idusr=Utility.noNull($idusr);
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
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
					sql   = "delete from zutefunz where idusr = '"+$idusr+"' "
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
					
					// Inserimento in rel_user_emp
					String $id_employee = req.getParameter("id_employee");
					sql   = "insert into rel_emp_user "+
							"(id_employee, id_user) "+
							"values "+
							"('"+$id_employee+"', '"+$idusr.replaceAll("'", "''").toUpperCase()+"') ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					
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
					sql   = "delete from zutefunz where idusr = '"+$idusr+"' "
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
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
						
				}
				
				else if(tipoForm.equals("E")) {
					
					// Cancella il contenuto della tabella zutefunz per l'utente
					sql   = "delete from zutefunz where idusr = '"+$idusr+"' ";
					st.executeUpdate(sql);
					
					// Cancella la relazione
					sql   = "delete from rel_emp_user where id_user = '"+$idusr+"' ";
					st.executeUpdate(sql);
					
					// Cancellazione
					System.out.println("ELI...");
					sql   = "delete from zutenti where idusr = '"+$idusr+"' ";
					st.executeUpdate(sql);
					
					
					// *blank id
					$idusr = "";
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "I";
					
				}
				
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
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select * from zutenti where idusr = '"+$idusr+"' ";
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
					"left join zutefunz b on a.cdfun = b.cdfun and b.idusr = '"+$idusr+"' "+
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
			
			
			obj.put("idusr", $idusr);
			obj.put("readonly", $readonly);
			obj.put("pwd", $pwd);
			obj.put("nome", $nome);
			obj.put("cogn", $cogn);
			obj.put("tipo", $tipo);
			obj.put("foto", $foto);
			obj.put("mail", $mail);
			obj.put("db", $db);
			obj.put("style", $style);
			obj.put("logo", $logo);
			obj.put("sysinf", $sysinf);
			obj.put("language", $language);
			
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
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		
		return new ModelAndView("/hr/config/users-dett", obj);
	}
	
}
