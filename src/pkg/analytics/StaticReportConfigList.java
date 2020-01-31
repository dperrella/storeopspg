package pkg.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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

import pkg.beans.Device_Detail;
import pkg.beans.Devices;
import pkg.beans.Eventcode;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class StaticReportConfigList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables2> rc = new ArrayList<Tables2>();
		rc.clear();
		List<Tables2> rcLine = new ArrayList<Tables2>();
		rcLine.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		
		String $id_store = req.getParameter("id_store");
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm = Utility.noNull(tipoForm);
		
		String $id_linea = ""+req.getParameter("id_linea");
		$id_linea=Utility.noNull($id_linea);
		String $descrizione = ""+req.getParameter("descrizione");
		$descrizione=Utility.noNull($descrizione);
		String $ordine = ""+req.getParameter("ordine");
		$ordine=Utility.noNull($ordine);
		String $parent_id = ""+req.getParameter("parent_id");
		$parent_id=Utility.noNull($parent_id);
		
		try {
			
			conn = Utility.getConnAnalytics();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					System.out.println("INSERT...");
					
					if($id_linea.length()==0) {
						$id_linea = Utility.getNewProgr(conn, "flowreport_config", "id_linea", " where id_store = "+$id_store+" ");
						System.out.println($id_linea);
					}
					
					if($parent_id.length()==0)
						$parent_id = "0";
					
					sql   = "insert into flowreport_config "+
							"(id_store, id_linea, descrizione, ordine, parentid) "+
							"values "+
							"(?, ?, ?, ?, ?) ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt($id_store));
					st.setInt(2, Integer.parseInt($id_linea));
					st.setString(3, $descrizione);
					st.setInt(4, Integer.parseInt($ordine));
					st.setInt(5, Integer.parseInt($parent_id));
					// Esegue l'sql
					try {
						st.executeUpdate();
						
						msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
							"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
					}
					catch(Exception e){
						msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
							"<div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div>";
					}
					
					
					$id_linea = "";
					$descrizione = "";
					$ordine = "";
					$parent_id = "";
				}
				
				if(tipoForm.equals("M")) {
					System.out.println("UPDATE (?)...");
					
					// Cancella la lista per id_store
					sql   = "delete from flowreport_config where id_store = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setInt(1, Integer.parseInt($id_store));
					st.executeUpdate();
					
					// Inserisce i record nell'ordine giusto
					String[] cd = req.getParameterValues("id");
					
					if(cd!=null) {
						// prendiamo tutti i valori dell'input multiplo
						if (cd.length>0) {
							
							System.out.println(cd.length);
							
							// riversiamo l'array
							for(int i=0; i<cd.length; i++) {
								
								if($id_linea.length()==0) {
									$id_linea = Utility.getNewProgr(conn, "flowreport_config", "id_linea", " where id_store = "+$id_store+" ");
									System.out.println($id_linea);
								}
								$descrizione = req.getParameterValues("descrizione")[i];
								$ordine = req.getParameterValues("ordine")[i];
								$parent_id = req.getParameterValues("parent_id")[i];
								
								if($parent_id.length()==0)
									$parent_id = "0";
								
								sql   = "insert into flowreport_config "+
										"(id_store, id_linea, descrizione, ordine, parentid) "+
										"values "+
										"(?, ?, ?, ?, ?) ";
								// Prepara il cursore
								st = conn.prepareStatement(sql);
								
								// Passa i parametri
								st.setInt(1, Integer.parseInt($id_store));
								st.setInt(2, Integer.parseInt($id_linea));
								st.setString(3, $descrizione);
								st.setInt(4, Integer.parseInt($ordine));
								st.setInt(5, Integer.parseInt($parent_id));
								// Esegue l'sql
								try {
									st.executeUpdate();
									
									msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
										"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
								}
								catch(Exception e){
									msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
										"<div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div>";
								}
								
							}
							
						}
						
					}
					
					$id_linea = "";
					$descrizione = "";
					$ordine = "";
					$parent_id = "";
					
				}
				
				if(tipoForm.equals("E")) {
					System.out.println("DELETE...");
					
					String[] cd = req.getParameterValues("id");
					String chk = "";
					String riga = "";
					
					if(cd!=null) {
						// prendiamo tutti i valori dell'input multiplo
						if (cd.length>0) {
							
							System.out.println(cd.length);
							
							// riversiamo l'array
							for(int i=0; i<cd.length; i++) {
								
								riga = req.getParameterValues("id")[i];
								
								try {
									chk = ""+req.getParameter("chk"+riga);
									chk = Utility.noNull(chk);
								}
								catch(Exception e) {}
								
								if(chk.length()>0) {
									
									sql   = "delete from flowreport_config "+
											"where id = ? ";
									// Prepara il cursore
									st = conn.prepareStatement(sql);
									
									// Passa i parametri
									st.setInt(1, Integer.parseInt(riga));
									
									// Esegue l'sql
									try {
										st.executeUpdate();
										
										msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
											"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
									}
									catch(Exception e){
										msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
											"<div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div>";
									}
									
								}
							}
							
						}
						
					}
					
					$id_linea = "";
					$descrizione = "";
					$ordine = "";
					$parent_id = "";
				}
				
			}
			
			// Dettagli Area flowreport_config
			sql   = "select a.* "+
					"from flowreport_config a  "+
					"where a.id_store = ? "+
					"order by id_linea, ordine ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Parametri
			st.setInt(1, Integer.parseInt($id_store));
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			count = 0;
			while(rs.next()) {
				
				count+=1;
				
				rc.add(new Tables2("","",rs.getString("descrizione"),
						""+count,rs.getString("id"),rs.getString("id_linea"),
						rs.getString("ordine"),rs.getString("parentid"),"","","","",""));
				
			}
			rs.close();
			st.close();
			
			// Linee flowreport_config
			sql   = "select id_linea "+
					"from flowreport_config a  "+
					"where a.id_store = ? "+
					"group by id_linea "+
					"order by id_linea ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Parametri
			st.setInt(1, Integer.parseInt($id_store));
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			count = 0;
			while(rs.next()) {
				
				count+=1;
				
				rcLine.add(new Tables2("","","",
						rs.getString("id_linea"),"","",
						"","","","","","",""));
				
			}
			rs.close();
			st.close();
			
			
			
			
			obj.put("count", count);
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		obj.put("dsfun", "Dettaglio Area");
		
		obj.put("count", count);
		obj.put("msg", msg);
		
		obj.put("id_store", $id_store);
		obj.put("id_linea", $id_linea);
		obj.put("descrizione", $descrizione);
		obj.put("ordine", $ordine);
		obj.put("parent_id", $parent_id);
		
		
		obj.put("rc", rc);
		obj.put("rcLine", rcLine);
		
		
		return new ModelAndView("/app/report/static-report-config-list", obj);
	}
	
}
