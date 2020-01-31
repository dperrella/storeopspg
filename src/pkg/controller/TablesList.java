package pkg.controller;

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

import pkg.beans.Tables;
import pkg.utility.Utility;
import pkg.properties.ReadProperties;

public class TablesList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables> tab = new ArrayList<Tables>();
		
		HttpSession session = req.getSession();
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		tab.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String xcdtb = req.getParameter("xcdtb");
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		String readonly = "";
		if(!tipoForm.equals("M"))
			readonly = "readonly";
		
		try {
			
			if(q.length()>0) {
				cond+=" and trim(xcdel) || upper(trim(xdtab)) like '%"+q.toUpperCase()+"%' ";
			}
			
			conn = Utility.getConnAdmin();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			if(azione.length()>0) {
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					
					String $xcdtb = "TAB";
					String $xcdel = req.getParameter("xcdel");
					String $xdtab = req.getParameter("xdtab");
					
					sql   = "insert into ztables "+
							"(xcdtb, xcdel, xdtab, ata32) "+
							"values "+
							"('"+$xcdtb+"', '"+$xcdel+"', '"+$xdtab.replaceAll("'", "''").toUpperCase()+"', ' ') ";
					st.executeUpdate(sql);
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("tab_success")+"</strong></div>";
					
				}
				else if(tipoForm.equals("M")) {
					
					System.out.println("MOD...");
					
					// cancello l'inetro contenuto della tabella
					sql   = "delete from ztables where xcdtb = '"+xcdtb+"' ";
					st.executeUpdate(sql);
					
					// Riga
					String[] cd = req.getParameterValues("nrrig");
					String xcdel 	= "";
					String xdtab	= "";
					
					if(cd!=null) {
						// prendiamo tutti i valori dell'input multiplo
						if (cd.length>0) {
							
							System.out.println(cd.length);
							
							// riversiamo l'array
							for(int i=0; i<cd.length; i++) {
								
								xcdel = req.getParameterValues("xcdel")[i];
								xdtab = req.getParameterValues("xdtab")[i];
								
								sql   = "insert into ztables (xcdtb, xcdel, xdtab, ata32) "+
										"values ('"+xcdtb+"', '"+xcdel+"', '"+xdtab.replaceAll("'", "''")+"', ' ') ";
								st.executeUpdate(sql);
								
							}
							
						}
						
					}
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("tab_success")+"</strong></div>";
					
				}
				else if(tipoForm.equals("E")) {
					
					System.out.println("ELI...");
					
					// Riga
					String[] cd = req.getParameterValues("nrrig");
					String post 	= "";
					String cmp1		= "";
					String xcdel 	= "";
					String xdtab	= "";
					
					if(cd!=null) {
						// prendiamo tutti i valori dell'input multiplo
						if (cd.length>0) {
							
							System.out.println(cd.length);
							
							// riversiamo l'array
							for(int i=0; i<cd.length; i++) {
								
								cmp1 = req.getParameterValues("nrrig")[i];
								xcdel = req.getParameterValues("xcdel")[i];
								xdtab = req.getParameterValues("xdtab")[i];
								
								try {
									post = ""+req.getParameter("post"+cmp1);
									post = Utility.noNull(post);
								}
								catch(Exception e) {}
								
								if(post.length()>0) {
									sql   = "delete  from ztables where xcdtb = '"+xcdtb+"' and xcdel = '"+xcdel+"' ";
									st.executeUpdate(sql);
									
									tipoForm = "M";
								}
								
							}
							
						}
						
					}
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("tab_success")+"</strong></div>";
					
				}
				
				
				
				
				
			}
			
			sql  =  "select * from ztables where xcdtb = '"+xcdtb+"' and ata32 = ' ' "+cond;
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $xcdtb="";
			String $xcdel="";
			String $xdtab="";
		
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$xcdtb=rs.getString("xcdtb");
				$xcdel=rs.getString("xcdel");
				$xdtab=rs.getString("xdtab");
				
				tab.add(new Tables($xcdtb,$xcdel,$xdtab,""+count,"readonly",$cmp3,$cmp4));
				
			}
			
			// primo record di default
			if(count==0&&tipoForm.equals("M")) {
				readonly = "";
				tab.add(new Tables(xcdtb,"","",""+count,$cmp2,$cmp3,$cmp4));
			}
			rs.close();
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("tab_error")+e+"</div>";			
		}
		
		obj.put("dsfun", "Lista Tabelle");
		obj.put("tab", tab);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("tipoForm", tipoForm);
		obj.put("readonly", readonly);
		obj.put("xcdtb", xcdtb);
		
		return new ModelAndView("/app/config/tables-list", obj);
	}
	
}
