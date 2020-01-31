package pkg.beans.rfid;
import pkg.utility.DateManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;

public class Log {

    private Database database = Database.getInstance();

    private String filename;
    private String size;
    private String importTimestamp;
    private String elaborationTimestamp;
    private boolean elaborated;
    private boolean error;
    private String elaborationResult;

    
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getImportTimestamp() {
		return importTimestamp;
	}
	public void setImportTimestamp(String importTimestamp) {
		this.importTimestamp = importTimestamp;
	}
	public String getElaborationTimestamp() {
		return elaborationTimestamp;
	}
	public void setElaborationTimestamp(String elaborationTimestamp) {
		this.elaborationTimestamp = elaborationTimestamp;
	}
	public boolean isElaborated() {
		return elaborated;
	}
	public void setElaborated(boolean elaborated) {
		this.elaborated = elaborated;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getElaborationResult() {
		return elaborationResult;
	}
	public void setElaborationResult(String elaborationResult) {
		this.elaborationResult = elaborationResult;
	}
	
	public Log(String filename, String size, String importTimestamp, String elaborationTimestamp, boolean elaborated,
			boolean error, String elaborationResult) {
		this.filename = filename;
		this.size = size;
		this.importTimestamp = importTimestamp;
		this.elaborationTimestamp = elaborationTimestamp;
		this.elaborated = elaborated;
		this.error = error;
		this.elaborationResult = elaborationResult;
	}
	
	public static LinkedList<Log> getLogsByElaborated(Connection conn, boolean elaborated) {

        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "";
        LinkedList<Log> logList = null;
        try {
            sql = "select * from transact.log where elaborated = ? ";
            st = conn.prepareStatement(sql);
            st.setBoolean(1, elaborated);
            rs = st.executeQuery();
            logList = new LinkedList<Log>();
            int count = 0;
            while(rs.next()) {
                count++;
                logList.add(new Log(rs.getString("filename"),rs.getString("size"),DateManager.toString(DateManager.normalizeDate(rs.getString("importtimestamp")),DateManager.normalDateformat),
                                   DateManager.toString(DateManager.normalizeDate(rs.getString("elaborationtimestamp")),DateManager.normalDateformat),rs.getBoolean("elaborated"),
                                   rs.getBoolean("error"),rs.getString("elaborationResult")));
            }
            System.out.println(count);
            rs.close();

        }
        catch(Exception e) {
            e.printStackTrace();

            return null;
        }
        finally {
            try {
                if(st!=null)
                    st.close();
            }
            catch(Exception e) {}
        }

        return logList;




    }
    public static LinkedList<Log> getLogsByElaborated(Connection conn, boolean elaborated, int min, int max) {

        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "";
        LinkedList<Log> logList = null;

        try {
            sql = "select * from transact.log where elaborated = ? limit ?,?";
            st = conn.prepareStatement(sql);
            st.setBoolean(1, elaborated);
            st.setInt(2, min);
            st.setInt(1, max);
            rs = st.executeQuery();
            logList = new LinkedList<Log>();
            while(rs.next()) {
            	  logList.add(new Log(rs.getString("filename"),rs.getString("size"),DateManager.toString(DateManager.normalizeDate(rs.getString("importtimestamp")),DateManager.normalDateformat),
                          DateManager.toString(DateManager.normalizeDate(rs.getString("elaborationtimestamp")),DateManager.normalDateformat),rs.getBoolean("elaborated"),
                          rs.getBoolean("error"),rs.getString("elaborationResult")));
            }
            rs.close();

        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                if(st!=null)
                    st.close();
            }
            catch(Exception e) {}
        }
        return null;
    }
    public boolean checkFileInLog(){
        String sql = "select count(filename) from transact.log where filename = ?";
        try{
            PreparedStatement ps = database.getConnection().prepareCall(sql);
            ps.setString(1, filename);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                if (rs.getInt("count") == 0){
                    ps.close();
                    return false;
                }
                else if (rs.getInt("count") > 0) {
                    ps.close();
                    return true;
                }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static LinkedList<Log> getLogs(Connection conn) {

        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "";
        LinkedList<Log> logList = null;
        try {
            sql = "select * from transact.log ";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            logList = new LinkedList<Log>();
            int count = 0;
            while(rs.next()) {
                count++;
                logList.add(new Log(rs.getString("filename"),rs.getString("size"),DateManager.toString(DateManager.normalizeDate(rs.getString("importtimestamp")),DateManager.normalDateformat),
                                   DateManager.toString(DateManager.normalizeDate(rs.getString("elaborationtimestamp")),DateManager.normalDateformat),rs.getBoolean("elaborated"),
                                   rs.getBoolean("error"),rs.getString("elaborationResult")));
            }
            System.out.println(count);
            rs.close();

        }
        catch(Exception e) {
            e.printStackTrace();

            return null;
        }
        finally {
            try {
                if(st!=null)
                    st.close();
            }
            catch(Exception e) {}
        }

        return logList;
    }
    
    public static LinkedList<Log> getLogsDash(Connection conn) {

        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "";
        LinkedList<Log> logList = null;
        try {
            sql = "select * from transact.log order by importtimestamp desc limit 10 ";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            logList = new LinkedList<Log>();
            int count = 0;
            while(rs.next()) {
                count++;
                logList.add(new Log(rs.getString("filename"),rs.getString("size"),DateManager.toString(DateManager.normalizeDate(rs.getString("importtimestamp")),DateManager.normalDateformat),
                                   DateManager.toString(DateManager.normalizeDate(rs.getString("elaborationtimestamp")),DateManager.normalDateformat),rs.getBoolean("elaborated"),
                                   rs.getBoolean("error"),rs.getString("elaborationResult")));
            }
            System.out.println(count);
            rs.close();

        }
        catch(Exception e) {
            e.printStackTrace();

            return null;
        }
        finally {
            try {
                if(st!=null)
                    st.close();
            }
            catch(Exception e) {}
        }

        return logList;
    }
}
