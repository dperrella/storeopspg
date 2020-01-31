package eu.mcsistemi.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;


public class ReadProperties {

  private final Properties prop;

 
  
  public ReadProperties(String fileName) throws FileNotFoundException{
	prop = new Properties();

    //InputStream config = new FileInputStream("/Users/mattia/Documents/workspace/AtSourceWeb/connection.properties");
    InputStream config = new FileInputStream("connection.properties");

    try{
      prop.load(config);
    }
    catch (Exception e){
      e.printStackTrace();
    }

    System.out.println(prop.getProperty("database"));
    System.out.println(prop.getProperty("usr"));
    System.out.println(prop.getProperty("psw"));
    
  }

  public String get(String key){
    return (String)prop.get(key);
  }
}