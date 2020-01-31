package pkg.rfid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.DevicesRfid;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RelUserDevices extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Users> users = new ArrayList<Users>();
		List<DevicesRfid> dev = new ArrayList<DevicesRfid>();
		
		// Pulisce la lista
		users.clear();
		dev.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		
		Connection connAdmin = null;
		PreparedStatement stAdmin = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String idusr = ""+req.getParameter("idusr");
		idusr = Utility.noNull(idusr);
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String iddevices = ""+req.getParameter("iddevices");
		iddevices = Utility.noNull(iddevices);
		
		obj.put("idusr", idusr);
		
		try {
			// Apre la connessione
			conn = Utility.getConnRfid();
			// Apre la connessione Admin
			connAdmin = Utility.getConnAdmin();
			
			if(azione.length()>0) {
				
				
				// Cancella il contenuto della tabella rel_user_customers per l'utente
				sql   = "delete from rel_user_devices where idusr = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, idusr);
				
				// Esegue l'sql
				st.executeUpdate();
				
				// Inserimento in rel_user_customers
				String temp1[] = iddevices.split(",");
				
				for(int c=0; c<temp1.length; c++) {
					
					if(temp1[c].length()>0) {
						
						System.out.println(temp1[c]);
						sql   = "insert into rel_user_devices "+
								"(idusr, iddevice) "+
								"values "+
								"(?, ?) ";
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setString(1, idusr.toUpperCase());
						st.setInt(2, Integer.parseInt(temp1[c]));
						
						// Esegue l'sql
						st.executeUpdate();
						
					}
					
					msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
						"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
				}
				
				
			}
			
			
			
			// ==================
			// Lista Utenti
			sql  =  "select * from zutenti ";
			
			// Prepara il cursore
			stAdmin = connAdmin.prepareStatement(sql);
			
			// Esegue l'sql
			rs=stAdmin.executeQuery();
			
			// Campi datebase
			String $idusr="";
			String $pwd="";
			String $nome="";
			String $cogn="";
			String $tipo="";
			String $foto="";
			String $usrmail="";
			String $db="";
			String $style="";
			String $logo="";
			String $sysinf="";
			String $language="";
			while(rs.next()) {
				
				count+=1;
				
				$idusr=rs.getString("idusr");
				$pwd=rs.getString("pwd");
				$nome=rs.getString("nome");
				$cogn=rs.getString("cogn");
				$tipo=rs.getString("tipo");
				$foto=rs.getString("foto");
				$usrmail=rs.getString("mail");
				$db=rs.getString("db");
				$style=rs.getString("style");
				$logo=rs.getString("logo");
				$sysinf=rs.getString("sysinf");
				$language=""+rs.getString("language");
				$language=Utility.noNull($language);
				
				users.add(new Users($idusr,$pwd,$nome,$cogn,$tipo,$foto,$usrmail,$db,$style,$logo,$sysinf,$language,"","","",""));
				
			}
			rs.close();
			// Fine Lista Utenti
			// ==================
			
			// sql da eseguire
			sql  =  "select a.* , b.iddevice as rel_iddevice "
					+ "from devices a "
					+ "left join rel_user_devices b on a.iddevice = b.iddevice::integer and b.idusr = '"+idusr+"' ";
			
			System.out.println(sql);
			
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" and a.name || a.description like ? ";
			}
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Se ha parametri li passa all'sql
			if(hasCond==true) {
				st.setString(1, "%" + q + "%");
			}
			
			System.out.println(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $iddevice="";
			String $name="";
			String $description="";
			String $rifidi="";
			String $ip="";
			String $port="";
			String $urlws="";
			String $origin_warehouse="";
			String $mac="";
			String $idflow_default="";
			String $idstep_default="";
			String $id_dev_printer="";
			String $devicetype="";
			String $idname="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				if(rs.getString("iddevice").equals(rs.getString("rel_iddevice"))) {
					$cmp3 = "checked";
				}
				count+=1;
				
				$iddevice=rs.getString("iddevice");
				$name=rs.getString("name");
				$description=rs.getString("description");
				$rifidi=rs.getString("rifidi");
				$ip=rs.getString("ip");
				$port=rs.getString("port");
				$urlws=rs.getString("urlws");
				$origin_warehouse=rs.getString("origin_warehouse");
				$mac=rs.getString("mac");
				$idflow_default=rs.getString("idflow_default");
				$idstep_default=rs.getString("idstep_default");
				$id_dev_printer=rs.getString("id_dev_printer");
				$devicetype=rs.getString("devicetype");
				$idname=rs.getString("idname");
				
				dev.add(new DevicesRfid($iddevice, $name, $description,$rifidi,$ip,$port,
						$urlws,$origin_warehouse,$mac,$idflow_default,$idstep_default,
						$id_dev_printer,$devicetype,$idname,
						$cmp1,$cmp2,$cmp3,$cmp4));
				
				$cmp3 = "";
				
			}
			rs.close();
			
			if(st!=null)
				st.close();
			if(stAdmin!=null)
				stAdmin.close();
			
			conn.close();
			connAdmin.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Utenti / Negozi");
		obj.put("users", users);
		obj.put("dev", dev);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid/rel/rel_user_devices", obj);
	}
	
}
