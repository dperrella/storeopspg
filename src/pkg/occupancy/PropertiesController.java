package pkg.occupancy;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
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
import pkg.occupancy.model2.Labels;
import pkg.occupancy.model2.Properties;
import pkg.utility.TrippleDes;
import pkg.utility.Utility;

public class PropertiesController extends AbstractController {
	@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		long ts = new Date().getTime();
        obj.put("ts", ts);
        
        HttpSession session = req.getSession();
		String idcus = ""+session.getAttribute("idcus");
		idcus = Utility.noNull(idcus);
        
		List<Properties> prop1 = new ArrayList<Properties>();
		prop1.clear();
		
		List<Properties> prop2 = new ArrayList<Properties>();
		prop2.clear();
		
		List<Properties> prop3 = new ArrayList<Properties>();
		prop3.clear();
		
		List<Properties> prop4 = new ArrayList<Properties>();
		prop4.clear();
		
		List<Properties> prop5 = new ArrayList<Properties>();
		prop5.clear();
		
		List<Properties> prop6 = new ArrayList<Properties>();
		prop6.clear();
		
		List<Properties> prop7 = new ArrayList<Properties>();
		prop7.clear();
		
		List<Tables> tmz = new ArrayList<Tables>();
		tmz.clear();
		
		List<Labels> labels = new ArrayList<Labels>();
		labels.clear();
		
		List<Properties> openingHours = new ArrayList<Properties>();
		openingHours.clear();
		
		Connection conn = null;
		
		// Per operazioni di CRUD
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		String azionesettings = ""+req.getParameter("azionesettings");
		azionesettings=Utility.noNull(azionesettings);
		
		String azioneLabel = ""+req.getParameter("azioneLabel");
		azioneLabel=Utility.noNull(azioneLabel);
		
		String azioneLabelGroup = ""+req.getParameter("azioneLabelGroup");
		azioneLabelGroup=Utility.noNull(azioneLabelGroup);
		
		String msg = "";
		
		try {
			// Apertura connessione
			conn = Utility.getConnData();
			
			// Inizializzazione Bean
			Properties bean = new Properties();
			bean.setIdCustomer(Integer.parseInt(idcus));
			
			// In caso di azione...
			if(azione.length()>0) {
				
				bean.deleteAll(conn);
				
				//String[] ids=req.getParameterValues("id");
				String[] keys=req.getParameterValues("key");
				String[] descriptions=req.getParameterValues("description");
				String[] values=req.getParameterValues("val");
				String[] tile=req.getParameterValues("tile");
				
				for(int i=0; i<values.length; i++)
				{
					//bean.setId(Integer.parseInt(ids[i]));
					bean.setKey(keys[i]);
					bean.setDescription(descriptions[i]);
					bean.setValue(values[i]);
					bean.setTile(Integer.parseInt(tile[i]));
					
					bean.add(conn);
					
				}
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>Operation Successful.</strong></div></div>";
				
			}
			
			// In caso di azione...
			if(azionesettings.length()>0) {
				
				String hhopen = req.getParameter("hhopen");
				String hhclose = req.getParameter("hhclose");
				String mmopen = req.getParameter("mmopen");
				String mmclose = req.getParameter("mmclose");
				
				Calendar calendar = Calendar.getInstance();
	            calendar.setTime(new Date());
	            int dow = calendar.get(Calendar.DAY_OF_WEEK);
	            
	            int day = 0;
	            switch(dow) {
	            case 1:
	            	day = 6;
	            	break;
	            case 2:
	            	day = 0;
	            	break;
	            case 3:
	            	day = 1;
	            	break;
	            case 4: 
	            	day = 2;
	            	break;
	            case 5:
	            	day = 3;
	            	break;
	            case 6:
	            	day = 4;
	            	break;
	            case 7:
	            	day = 5;
	            	break;
	            }
	            System.out.println("DOW      : "+calendar.get(Calendar.DAY_OF_WEEK));
	            
	            try {
					for(int h=0;h<7;h++) {
						
						String chkH24 = ""+req.getParameter("h24_"+h);
						chkH24= Utility.noNull(chkH24);
						boolean h24 = false;
						if(chkH24.length()==0)
							h24 = false;
						else
							h24 = true;
						
						String fromhour = req.getParameter("fromhour_"+h);
						System.out.println("fromhour_"+h+" = "+fromhour);
						String fromminute = req.getParameter("fromminute_"+h);
						String tohour = req.getParameter("tohour_"+h);
						String tominute = req.getParameter("tominute_"+h);
						
						
						if(h24) 
							Properties.updateOpeningHour(conn, h, "00:00:00", "23:59:59", h24, Integer.parseInt(idcus));
						else
							Properties.updateOpeningHour(conn, h, fromhour+":"+fromminute+":00", tohour+":"+tominute+":00", h24, Integer.parseInt(idcus));
						
						
						if(h==day) {
							hhopen = fromhour;
							hhclose = tohour;
							mmopen = fromminute;
							mmclose = tominute;
						}
						
					}
					
	            }
				catch(Exception e) {
					e.printStackTrace();
				}
	            
				bean.getSettings(conn);
				
				String mailfrom = req.getParameter("mailfrom");
				String mailto = req.getParameter("mailto");
				String mailsmtp = req.getParameter("mailsmtp");
				String mailport = req.getParameter("mailport");
				String mailuser = req.getParameter("mailuser");
				String mailpass = req.getParameter("mailpass");
				String template = req.getParameter("template");
				String mailsend = req.getParameter("mailsend");
//				String hhopen = req.getParameter("hhopen");
//				String hhclose = req.getParameter("hhclose");
//				String mmopen = req.getParameter("mmopen");
//				String mmclose = req.getParameter("mmclose");
				
				hhopen = hhopen+":"+mmopen+":00";
				hhclose = hhclose+":"+mmclose+":00";
				
				System.out.println(hhopen);
				System.out.println(hhclose);
				
				
				boolean oldMl = bean.isMachineLearning();
				
				String chkMailwrn = ""+req.getParameter("chkMailwrn");
				chkMailwrn = Utility.noNull(chkMailwrn);
				String chkMailover = ""+req.getParameter("chkMailover");
				chkMailover = Utility.noNull(chkMailover);
				String mchkMgwrn = ""+req.getParameter("msgwrn");
				mchkMgwrn = Utility.noNull(mchkMgwrn);
				String chkMachineLearning = ""+req.getParameter("machineLearning");
				chkMachineLearning = Utility.noNull(chkMachineLearning);
				
				String chkEnableNormal = ""+req.getParameter("enableNormal");
				chkEnableNormal = Utility.noNull(chkEnableNormal);
				String chkEnableWarning = ""+req.getParameter("enableWarning");
				chkEnableWarning = Utility.noNull(chkEnableWarning);
				String chkEnableError = ""+req.getParameter("enableError");
				chkEnableError = Utility.noNull(chkEnableError);
				
				String chkReset1 = ""+req.getParameter("reset1");
				chkReset1 = Utility.noNull(chkReset1);
				String chkReset2 = ""+req.getParameter("reset2");
				chkReset2 = Utility.noNull(chkReset2);
				
				String chkAvgqueue = ""+req.getParameter("avgqueue");
				chkAvgqueue = Utility.noNull(chkAvgqueue);
				
				String chkSM = ""+req.getParameter("sm");
				chkSM= Utility.noNull(chkSM);
				
				
				boolean mailwrn = false;
				boolean mailover = false;
				boolean msgwrn = false;
				boolean machineLearning = false;
				
				boolean enableNormal = false;
				boolean enableWarning = false;
				boolean enableError = false;
				
				boolean reset1 = false;
				boolean reset2 = false;
				
				boolean avgqueue = false;
				
				boolean sm = false;
				
				
				
				
				if(chkMailwrn.length()==0)
					mailwrn = false;
				else
					mailwrn = true;
				
				if(chkMailover.length()==0)
					mailover = false;
				else
					mailover = true;
				
				if(mchkMgwrn.length()==0)
					msgwrn = false;
				else
					msgwrn = true;
				
				if(chkMachineLearning.length()==0)
					machineLearning = false;
				else
					machineLearning = true;

				if(chkEnableNormal.length()==0)
					enableNormal = false;
				else
					enableNormal = true;
				if(chkEnableWarning.length()==0)
					enableWarning = false;
				else
					enableWarning = true;
				if(chkEnableError.length()==0)
					enableError = false;
				else
					enableError = true;
				
				if(chkReset1.length()==0)
					reset1 = false;
				else
					reset1 = true;
				if(chkReset2.length()==0)
					reset2 = false;
				else
					reset2 = true;
				
				if(chkAvgqueue.length()==0)
					avgqueue = false;
				else
					avgqueue = true;
				
				if(chkSM.length()==0)
					sm = false;
				else
					sm = true;
				
				
				String mailtype = req.getParameter("mailtype");
				
				String dataType = req.getParameter("dataType");
				
				String df = req.getParameter("df");
				
				String trafficLightCountType = req.getParameter("traffic_light_count_type");
				
				String tz = req.getParameter("tz");
				
				String ntp = req.getParameter("ntp");
				String privacy = req.getParameter("privacy");
				
				String resolution = req.getParameter("res");
				
				bean.setMailfrom(mailfrom);
				bean.setMailto(mailto);
				bean.setMailsmtp(mailsmtp);
				bean.setMailport(mailport);
				bean.setMailuser(mailuser);
				bean.setMailpass(mailpass);
				bean.setTemplate(template);
				bean.setMailsend(mailsend);
				bean.setMailwrn(mailwrn);
				bean.setMailover(mailover);
				bean.setMailtype(mailtype);
				bean.setHhopen(hhopen);
				bean.setHhclose(hhclose);
				bean.setMsgwrn(msgwrn);
				bean.setMachineLearning(machineLearning);
				bean.setEnableNormal(enableNormal);
				bean.setEnableWarning(enableWarning);
				bean.setEnableError(enableError);
				bean.setDataType(dataType);
				bean.setDf(df);
				bean.setTrafficLightCountType(trafficLightCountType);
				bean.setTz(tz);
				bean.setNtp(ntp);
				bean.setPrivacy(privacy);
				
				bean.setReset1(reset1);
				bean.setReset2(reset2);
				
				bean.setRes(resolution);
				
				bean.setAvgqueue(avgqueue);
				bean.setSm(sm);
				
				bean.setSettings(conn);
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>Operation Successful.</strong></div></div>";
				
			}
			
			// In caso di azione...
			if(azioneLabel.length()>0) {
				String lang = req.getParameter("lang");
				System.out.println("lang: "+lang);
				
				Labels l = new Labels();
				l.addLang(conn, lang, Integer.parseInt(idcus));
				
				msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>Operation Successful.</strong></div></div>";
				
				obj.put("_modal", true);
				obj.put("_lang", lang);
				
				
				
			}
			
			// In caso di azione...
			if(azioneLabelGroup.length()>0) {
				
				System.out.println(azioneLabelGroup);
				
				String tf = req.getParameter("tipoForm");
				String lang = req.getParameter("lang");
				System.out.println("lang: "+lang);
				
				System.out.println(tf);
				System.out.println(lang);
				
				
				Labels l = new Labels();
				
				if(tf.equals("M")) {
					l.deleteByLang(conn, lang, Integer.parseInt(idcus));
					
					
					String[] keys=req.getParameterValues("key");
					String[] descriptions=req.getParameterValues("description");
					String[] values=req.getParameterValues("val");
					String[] tile=req.getParameterValues("tile");
					String[] langs=req.getParameterValues("lang");
					
					
					for(int i=0; i<values.length; i++)
					{
						l.setKey(keys[i]);
						l.setDescription(descriptions[i]);
						l.setValue(values[i]);
						l.setTile(Integer.parseInt(tile[i]));
						l.setLang(langs[i]);
						l.setIdCustomer(Integer.parseInt(idcus));
						
						l.add(conn);
					}
					
					msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>Operation Successful.</strong></div></div>";
					
					
					
					
				}
				
				if(tf.equals("E")) {
					l.deleteByLang(conn, lang, Integer.parseInt(idcus));
					
					msg = "<div class=\"wrapper\"><div class=\"alert alert-success\"><strong>Operation Successful.</strong></div></div>";
					
				}
				
			}
			
			Properties.setHoursDft(conn, Integer.parseInt(idcus));
			
			// Selezione record (tutti)
			prop1 = Properties.getPropertiesByTile(conn, 1, Integer.parseInt(idcus));
			
			if(prop1.size()==0) {
				Properties.setPropertiesDft(conn, Integer.parseInt(idcus));
				Properties.setSettingsDft(conn, Integer.parseInt(idcus));
			}
			
			// Selezione record (tutti)
			prop1 = Properties.getPropertiesByTile(conn, 1, Integer.parseInt(idcus));
			prop2 = Properties.getPropertiesByTile(conn, 2, Integer.parseInt(idcus));
			prop3 = Properties.getPropertiesByTile(conn, 3, Integer.parseInt(idcus));
			prop4 = Properties.getPropertiesByTile(conn, 4, Integer.parseInt(idcus));
			prop5 = Properties.getPropertiesByTile(conn, 5, Integer.parseInt(idcus));
			prop6 = Properties.getPropertiesByTile(conn, 6, Integer.parseInt(idcus));
			prop7 = Properties.getPropertiesByTile(conn, 7, Integer.parseInt(idcus));
			
			labels = Labels.getLabelsGrouped(conn, Integer.parseInt(idcus));
			
			openingHours = Properties.getOpeningHours(conn, Integer.parseInt(idcus));
			
			obj.put("ip", "NOT Avalilable in Cloud");
    		obj.put("mac", "NOT Avalilable in Cloud");
    		
    		// timezones
			tmz = Properties.getTmz(conn);
			
			// settings
			bean.getSettings(conn);
			
			
			obj.put("mailfrom", bean.getMailfrom());
			obj.put("mailto", bean.getMailto());
			obj.put("mailsmtp", bean.getMailsmtp());
			obj.put("mailport", bean.getMailport());
			obj.put("mailuser", bean.getMailuser());
			obj.put("mailpass", bean.getMailpass());
			obj.put("template", bean.getTemplate());
			obj.put("mailsend", bean.getMailsend());
			
			
			
			String hhopen = "07:00:00";
			String hhclose= "21:00:00";
			
			if(Utility.noNull(bean.getHhopen()).length()>0)
				hhopen = bean.getHhopen();
			if(Utility.noNull(bean.getHhclose()).length()>0)
				hhclose = bean.getHhclose();
			
//			obj.put("hhopen", bean.getHhopen());
//			obj.put("hhclose", bean.getHhclose());
			
			obj.put("hhopen", hhopen);
			obj.put("hhclose", hhclose);
			
//			String time1[] = bean.getHhopen().split(":");
//			String time2[] = bean.getHhclose().split(":");
			String time1[] = hhopen.split(":");
			String time2[] = hhclose.split(":");
			obj.put("hh1", time1[0]);
			obj.put("mm1", time1[1]);
			obj.put("hh2", time2[0]);
			obj.put("mm2", time2[1]);
			
			
			
			
			
			
			
			boolean mailwrn = bean.isMailwrn();
			boolean mailover = bean.isMailover();
			boolean msgwrn = bean.isMsgwrn();
			boolean machineLearning = bean.isMachineLearning();
			
			boolean enableNormal = bean.isEnableNormal();
			boolean enableWarning = bean.isEnableWarning();
			boolean enableError = bean.isEnableError();
			
			boolean reset1 = bean.isReset1();
			boolean reset2 = bean.isReset2();
			boolean avgqueue = bean.isAvgqueue();
			boolean sm = bean.isSm();
			
			if(mailwrn)
				obj.put("chkMailwrn", "checked");
			else
				obj.put("chkMailwrn", "");
			
			if(mailover)
				obj.put("chkMailover", "checked");
			else
				obj.put("chkMailover", "");
			
			if(msgwrn)
				obj.put("chkMsgwrn", "checked");
			else
				obj.put("chkMsgwrn", "");
			
			if(machineLearning)
				obj.put("chkMachineLearning", "checked");
			else
				obj.put("chkMachineLearning", "");
			
			if(enableNormal)
				obj.put("chkEnableNormal", "checked");
			else
				obj.put("chkEnableNormal", "");
			if(enableWarning)
				obj.put("chkEnableWarning", "checked");
			else
				obj.put("chkEnableWarning", "");
			if(enableError)
				obj.put("chkEnableError", "checked");
			else
				obj.put("chkEnableError", "");
			
			if(reset1)
				obj.put("chkReset1", "checked");
			else
				obj.put("chkReset1", "");
			if(reset2)
				obj.put("chkReset2", "checked");
			else
				obj.put("chkReset2", "");
			
			if(avgqueue)
				obj.put("chkAvgqueue", "checked");
			else
				obj.put("chkAvgqueue", "");
			
			if(sm)
				obj.put("chkSM", "checked");
			else
				obj.put("chkSM", "");
			
			
			obj.put("mailtype", bean.getMailtype());
			obj.put("dataType", bean.getDataType());
			obj.put("df", bean.getDf());
			obj.put("trafficLightCountType", bean.getTrafficLightCountType());
			obj.put("tz", bean.getTz());
			obj.put("ntp", bean.getNtp());
			obj.put("privacy", bean.getPrivacy());
			
			obj.put("logo", bean.getLogo());
			obj.put("res", bean.getRes());
			
			
			String ns = bean.getNormalSound();
            String ws = bean.getWarningSound();
            String es = bean.getErrorSound();
            
            ns = "../../sounds/normal.wav";
            ws = "../../sounds/warning.wav";
            es = "../../sounds/error.wav";
            
            obj.put("normalSound", ns);
            obj.put("warningSound", ws);
            obj.put("errorSound", es);
            
            
            
			
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
		
		obj.put("prop1", prop1);
		obj.put("prop2", prop2);
		obj.put("prop3", prop3);
		obj.put("prop4", prop4);
		obj.put("prop5", prop5);
		obj.put("prop6", prop6);
		obj.put("prop7", prop7);
		obj.put("labels", labels);
		obj.put("openingHours", openingHours);
		
		obj.put("tmz", tmz);
		
		obj.put("msg", msg);
		
        
		return new ModelAndView("/occupancy/config/properties", obj);
	}
}
