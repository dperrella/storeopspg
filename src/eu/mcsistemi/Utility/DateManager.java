package eu.mcsistemi.Utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateManager {
	
	public static SimpleDateFormat normalDateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static SimpleDateFormat importexportDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat timeDateFormat = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat yMdDateFormat = new SimpleDateFormat("yyyy/MM/dd");


    public static Date addSeconds(Date date, int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, i);  // number of days to add
        return DateManager.changeDateFormat(c.getTime(), normalDateformat);
    }

    public static Date addMinutes(Date date, int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, i);  // number of days to add
        return DateManager.changeDateFormat(c.getTime(), normalDateformat);
    }

    public static Date addHours(Date date, int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, i);  // number of days to add
        return DateManager.changeDateFormat(c.getTime(), normalDateformat);
    }

    public static Date addDay(Date date, int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, i);  // number of days to add
        return DateManager.changeDateFormat(c.getTime(), normalDateformat);
    }

    public static Date addWeek(Date date, int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, i*7);  // number of days to add
        return DateManager.changeDateFormat(c.getTime(), normalDateformat);
    }

    public static Date addMonth(Date date, int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, i);  // number of days to add
        return DateManager.changeDateFormat(c.getTime(), normalDateformat);
    }

    public static Date addYear(Date date, int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, i);  // number of days to add
        return DateManager.changeDateFormat(c.getTime(), normalDateformat);
    }

    public static Date changeDateFormat(Date date, SimpleDateFormat sdf){
        try{
            return sdf.parse(sdf.format(date));
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Date changeDateFormat(String s, SimpleDateFormat inputSDF, SimpleDateFormat outputSDF){
        try{
            Date d = inputSDF.parse(s);
            return changeDateFormat(d, outputSDF);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static Date getTime (String s){
        try{
            return timeDateFormat.parse(s);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static Date getDate(Timestamp t){
    	if(t != null)
    		return new Date(t.getTime());
    	return null;
    }

    public static Date normalizeDate(String s){
        try{
            Date d = importexportDateFormat.parse(s);
            return changeDateFormat(d, normalDateformat);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static Date makeDateExportable(String s){
    	try{
            Date d = normalDateformat.parse(s);
            return changeDateFormat(d, importexportDateFormat);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static String toString (Date d, SimpleDateFormat sdf) {
    	if (d!= null){
    		try{
                return sdf.format(d);
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
    	}
    	return "";
    }

    public static Date toDate (String s, SimpleDateFormat sdf){
        try{
            return sdf.parse(s);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateFromMilliseconds(long millisec){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisec);
        return calendar.getTime();
    }

}
