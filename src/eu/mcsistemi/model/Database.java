package eu.mcsistemi.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import pkg.properties.ReadProperties;

public class Database {
    private static Database ourInstance = new Database();

    private Connection connection;

    public static Database getInstance() {
        return ourInstance;
    }

    
    private Database() { }

    private void connect(){
//        try {
//            connection = DriverManager.getConnection(connectionUrl);
//        }catch (Exception e){
//            e.printStackTrace();
//            connection = null;
//        }
        try {
            ReadProperties prop = new ReadProperties("connection.properties");
            String usr = prop.get("usrData");
            String psw = prop.get("pswData");
            String url = prop.get("urlData");
            String drv = prop.get("drv");
            String ssl = prop.get("sslData");
            String database  = prop.get("databaseData");

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

    
    public Connection getConnection (){
        return connection;
    }

}
