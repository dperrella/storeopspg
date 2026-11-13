package pkg.controller;

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

import pkg.beans.Aree;
import pkg.beans.Devices;
import pkg.beans.StoreTicket;
import pkg.beans.Tables;
import pkg.beans.licence.MobileApp;
import pkg.beans.licence.MobileLicence;
import pkg.beans.rfid.Readers;
import pkg.beans.rfid.Site;
import pkg.beans.Device_Detail;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class StoresTicket extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<StoreTicket> stList = new ArrayList<StoreTicket>();
		stList.clear();
		
		
		HttpSession session = req.getSession();
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		
		String idstore = ""+req.getParameter("idstore");
		idstore = Utility.noNull(idstore);
		idstore=idstore.replaceAll("\\?cd=undefined", "");
		
		
		String err = ""+req.getParameter("err");
		err = Utility.noNull(err);
		err=err.replaceAll("\\?cd=undefined", "");
		
		
		
		try {
			
			conn = Utility.getConnData();
			
			stList = StoreTicket.getTicketsByIdStore(conn, Integer.parseInt(idstore));
			obj.put("stList", stList);
			
			StoreTicket st = new StoreTicket();
			st.setIdStore(Integer.parseInt(idstore));
			st.newTicket(conn);
			
			String tipoForm = "";
			if(st.getId()!=null) {
				System.out.println("Edit...");
				tipoForm = "E";
				obj.put("title", "View Ticket No. "+st.getCallId());
			}
			else {
				System.out.println("New...");
				tipoForm = "I";
				obj.put("title", "Create New Ticket");
			}
			
			obj.put("id", st.getId());
			obj.put("idStore", st.getIdStore());
			obj.put("callId", st.getCallId());
			obj.put("chargeCode", st.getChargeCode());
			obj.put("sapCode", st.getSapCode());
			obj.put("openDate", st.getOpenDate());
			
			
			if(err.length()>0&&tipoForm.equals("I")) {
				obj.put("fromControl", "readonly");
				obj.put("callDescriptionShort", err);
			}
			else {
				obj.put("callDescriptionShort", st.getCallDescriptionShort());
			}
			
			if(tipoForm.equals("E")) {
				obj.put("fromControl", "readonly");
			}
			
			obj.put("callDescriptionLong", st.getCallDescriptionLong());
			obj.put("open", st.getOpen());
			
			
			
			obj.put("idstore", idstore);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		

		return new ModelAndView("/app/anag/stores-ticket", obj);
	}
	
}
