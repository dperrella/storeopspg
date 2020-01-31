package pkg.rfid2;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.rfid.Database;
import pkg.beans.rfid.Log;
import pkg.utility.Utility;

public class ImportLog extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Map<String, Object> obj = new HashMap<String, Object>();
        
        Connection conn = null;
        
        try {
        	conn = Utility.getConnRfid();
        	LinkedList<Log> logList = Log.getLogs(conn);
            obj.put("logList",logList);
            
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally {
        	if(conn!=null)
        		conn.close();
        }
        
        return new ModelAndView("/rfid2/transact/import-log", obj);
    }

}