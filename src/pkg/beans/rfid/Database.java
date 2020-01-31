package pkg.beans.rfid;

import pkg.properties.ReadProperties;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
    private static Database ourInstance = new Database();

    private Connection connection;

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() { }

    private void connect(){

    	try {
            ReadProperties prop = new ReadProperties("connection.properties");
            String usr = prop.get("usrRfid");
            String psw = prop.get("pswRfid");
            String url = prop.get("urlRfid");
            String drv = prop.get("drv");
            String ssl = prop.get("sslRfid");
            String database  = prop.get("databaseRfid");

            //System.out.println(url+database+","+usr+","+psw);
            if(Boolean.parseBoolean(ssl)) {
                Class.forName(drv);
                url = url+database+"?user="+usr+"&password="+psw+"&ssl=true";
                connection = DriverManager.getConnection(url);
            }
            else {
                Class.forName(drv);
                connection = DriverManager.getConnection(url+database, usr, psw);
            }


        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect(){
        try {
            connection.close();
            connection = null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        if (connection != null)
            disconnect();
    }

    public void openConnection(){
        if (connection == null)
            connect();
    }

    public void close(ResultSet rs){
        if (rs != null){
            try{
                rs.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public ResultSet executeQuery(String sql){
        try{
            PreparedStatement ps = connection.prepareCall(sql);
            ps.execute();
            return ps.getResultSet();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean importCSV(File csv, String table){
        String sql = "COPY " + table + " FROM '" + csv.getPath() + "' DELIMITERS ';' CSV header;";
        System.out.println(sql);
        try{
            PreparedStatement ps = connection.prepareCall(sql);
            ps.execute();
            ps.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection (){
        return connection;
    }

}
