package pkg.rfid2;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.rfid.ReadersType;
import pkg.beans.rfid.Readers;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ReadersDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<ReadersType> type = new ArrayList<ReadersType>();
		type.clear();
		
		HttpSession session = req.getSession();
		int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
		String id = ""+req.getParameter("id");
		id = Utility.noNull(id);
		id=id.replaceAll("\\?cd=undefined", "");
		String tipoForm = req.getParameter("tipoForm");
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnRfid();
			if(tipoForm.equals("E")) {
				msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";
			}
			
			if(!tipoForm.equals("I")) {
				Readers readers = new Readers();
				readers.setId(Integer.parseInt(id));
				readers.selectRow(conn);
				obj.put("id", id);
				obj.put("description", readers.getDescription());
				obj.put("ip", readers.getIp());
				obj.put("port", readers.getPort());
				obj.put("enabled", readers.getEnabled());
				obj.put("idtype", readers.getReaderType());
			}
			
			type = ReadersType.getReadersType(conn);
			
			obj.put("type", type);
			obj.put("tipoForm", tipoForm);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div></div>";
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid2/anag/readers-dett", obj);
	}
	
}
