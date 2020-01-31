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
import pkg.beans.Tables;
import pkg.beans.licence.MobileApp;
import pkg.beans.licence.MobileLicence;
import pkg.beans.rfid.Readers;
import pkg.beans.Device_Detail;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class LicenceDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		List<Tables> cus = new ArrayList<Tables>();
		cus.clear();
		List<MobileApp> mobapp = new ArrayList<MobileApp>();
		mobapp.clear();
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		String msg = "";
		
		String idapp = ""+req.getParameter("idapp");
		idapp = Utility.noNull(idapp);
		idapp=idapp.replaceAll("\\?cd=undefined", "");
		String idcustomer = ""+req.getParameter("idcustomer");
		idcustomer = Utility.noNull(idcustomer);
		idcustomer=idcustomer.replaceAll("\\?cd=undefined", "");
		String macaddr = ""+req.getParameter("macaddr");
		macaddr = Utility.noNull(macaddr);
		macaddr=macaddr.replaceAll("\\?cd=undefined", "");
		String tipoForm = req.getParameter("tipoForm");
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		obj.put("tipoForm", tipoForm);
		
		
		try {
			
			conn = Utility.getConnData();
			
			
			if(tipoForm.equals("E")) {
				msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";
			}
			
			if(!tipoForm.equals("I")) {
				MobileLicence moblic = new MobileLicence();
				moblic.setIdApp(Integer.parseInt(idapp));
				moblic.setIdCustomer(Integer.parseInt(idcustomer));
				moblic.setMacAddr(macaddr);
				moblic.selectRow(conn);
				
				obj.put("idapp", idapp);
				obj.put("idcustomer", idcustomer);
				obj.put("macaddr", macaddr);
				
			}
			
			Tables t = new Tables();
			cus = t.selectCustomers(conn, (String)session.getAttribute("username"));
			
			mobapp = MobileApp.getApps(conn);
			
			obj.put("msg", msg);
			obj.put("cus", cus);
			obj.put("mobapp", mobapp);
			
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
		

		return new ModelAndView("/app/licence/licence-dett", obj);
	}
	
}
