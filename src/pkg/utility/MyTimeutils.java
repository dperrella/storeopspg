package pkg.utility;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MyTimeutils {

	public static boolean isDebug = false;
	
	public static Calendar getCalendarGMT() {
		return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	}
	
	public static Calendar getCalendarGMTTimestamp(long ts) {
		Calendar c = getCalendarGMT();
		c.setTimeInMillis(ts * 1000);
		return c;
	}
	
	public static Calendar getCalendarinTimeZone(String tz) {
		return Calendar.getInstance(TimeZone.getTimeZone(tz));
	}
	
	public static Calendar getCalendarinTimeZoneTimestamp(String tz, long millis) {
		Calendar c =  Calendar.getInstance(TimeZone.getTimeZone(tz));
		c.setTimeInMillis(millis);
		return c;
	}

	/*
	public static Calendar removeGMTFromCalendar(Calendar c) {
		return removeGMTFromCalendar(c, TimeZone.getDefault());
	}

	public static Calendar removeGMTFromCalendar(Calendar c, TimeZone myZone) {
		c.add(Calendar.MILLISECOND, myZone.getRawOffset() * -1);
		c.add(Calendar.MILLISECOND, myZone.getDSTSavings() * -1);
		return c;
	}

	public static Calendar addGMTFromCalendar(Calendar c) {
		return addGMTFromCalendar(c, TimeZone.getDefault());
	}

	public static Calendar addGMTFromCalendar(Calendar c, TimeZone myZone) {
		c.add(Calendar.MILLISECOND, myZone.getRawOffset() * 1);
		c.add(Calendar.MILLISECOND, myZone.getDSTSavings() * 1);
		return c;
	}
	
	public static long getOffSet() {
		return getOffSet(TimeZone.getDefault());
	}
	
	public static long getOffSet(TimeZone myZone) {
		long val = 0;
		val+= myZone.getRawOffset() * 1;
		val+= myZone.getDSTSavings() * 1;
		return val;
	}

	public static Calendar setnewTimezoneFromCalendar(Calendar c, TimeZone myZone) {
		c = removeGMTFromCalendar(c);
		c = addGMTFromCalendar(c, myZone);
		return c;
	}
	*/

	public static long convertToLocalTime(long time) {
		return time;
	}

	public static void showTimeLog(long starttime, String timezone) {
		showTimeLog("", starttime, timezone);
	}

	public static void showTimeLog(String testo, long starttime, String timezone) {
		if (isDebug) {
			System.out.println(testo + " TIMESTAMP: " + starttime + " - TIMEZONE: " + timezone);
			Calendar c = getCalendarGMT();
			c.setTimeInMillis(starttime * 1000);
			String s1 =  c.getTime().toString();
			//c = removeGMTFromCalendar(c, TimeZone.getTimeZone(timezone));
			c = getCalendarinTimeZone(timezone);
			String s2 =  c.getTime().toString();
			System.out.println(s1 +" / " + s2);
		}
	}
	
	public static void printLog(String testo) {
		if (isDebug) {
			Calendar unitTime = getCalendarGMT();
			String a = DateFormat.getDateTimeInstance().format(unitTime.getTime());
			System.out.println(a + ": " + testo);
		}
	}
	
	public static long getTimestamp() {
		//getInstance usa il timezone del locale default
		Calendar c = getCalendarGMT();
		String tmp = ("" + c.getTimeInMillis()).substring(0, ("" + c.getTimeInMillis()).length() - 3);
		long ts = Long.parseLong(tmp);
		return ts;
		/*
		Calendar c = getCalendarGMT();
		c = MyTimeutils.addGMTFromCalendar(c);
		String tmp = ("" + c.getTimeInMillis()).substring(0, ("" + c.getTimeInMillis()).length() - 3);
		long ts = Long.parseLong(tmp);
		return ts;
		*/
	}
}
