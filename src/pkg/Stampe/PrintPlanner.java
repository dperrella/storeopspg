package pkg.Stampe;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;
import pkg.utility.JSendMail;

public class PrintPlanner {
	
	public static String genStampa (String id_departments, String year, String week) throws FileNotFoundException, MessagingException {
		
		String fileJasper = "";
		String filePdf    = "";
		
		try {
			ReadProperties prop = new ReadProperties("connection.properties");
			
			String usr	 = prop.get("usr");
			String psw   = prop.get("psw");
			
			Map parameters = new HashMap();
		
			fileJasper = "planner.jasper";
			filePdf    = "planner_"+id_departments+"_"+year+"_"+week+".pdf";
			
			parameters.put("id_departments", id_departments);
			parameters.put("year", year);
			parameters.put("week", week);
			parameters.put("path", prop.get("rootTemplates"));
			parameters.put("SUBREPORT_DIR", prop.get("rootTemplates"));
			
			//File jasper sorgente 
			String fileName = prop.get("rootTemplates") + fileJasper; 
			//File pdf di destinazione 
			String destFileNamePdf = prop.get("rootStampe") + filePdf; 		
		
			//Preparazione del file da stampare (in questa fase si esegue la query e si inseriscono 
			//i valori estratti dalla query) 
			JasperPrint jasperPrint=JasperFillManager.fillReport(fileName, parameters, Utility.getConnHR()); 
		
			//Creazione del PDF 
			JasperExportManager.exportReportToPdfFile(jasperPrint, destFileNamePdf); 
		
			return destFileNamePdf;
		}
		catch (Exception e) { 
			e.printStackTrace();
			JSendMail.msgMail("danieleperrella@yahoo.it","Errore jasper",e.getMessage(),"","info@smarthr.it");
			
			return "";
		} 
	}
}
