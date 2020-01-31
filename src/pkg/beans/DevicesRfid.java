package pkg.beans;

public class DevicesRfid {
	
	private String iddevice;
    private String name;
    private String description;
    private String rifidi;
    private String ip;
    private String port;
    private String urlws;
    private String origin_warehouse;
    private String mac;
    private String idflow_default;
    private String idstep_default;
    private String id_dev_printer;
    private String devicetype;
    private String idname;
    
    private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
         
    public DevicesRfid() {}

    public DevicesRfid(String iddevice, String name, String description,
                 String rifidi, String ip, String port, String urlws, String origin_warehouse,
                 String mac, String idflow_default, String idstep_default, String id_dev_printer,
                 String devicetype, String idname,
                 String cmp1, String cmp2, String cmp3, String cmp4) {
         
          this.iddevice=iddevice;
          this.name=name;
          this.description=description;
          this.rifidi=rifidi;
          this.ip=ip;
          this.port=port;
          this.urlws=urlws;
          this.origin_warehouse=origin_warehouse;
          this.mac=mac;
          this.idflow_default=idflow_default;
          this.idstep_default=idstep_default;
          this.id_dev_printer=id_dev_printer;
          this.devicetype=devicetype;
          this.idname=idname;      
          
          this.cmp1=cmp1;
  		  this.cmp2=cmp2;
  		  this.cmp3=cmp3;
  		  this.cmp4=cmp4;
   
    }
   
    public String getIddevice() {
          return iddevice;
    }
    public void setIddevice(String iddevice) {
          this.iddevice = iddevice;
    }

    public String getName() {
          return name;
    }
    public void setName(String name) {
          this.name = name;
    }
   
    public String getDescription() {
          return description;
    }
    public void setDescription(String description) {
          this.description = description;
    }
   
    public String getRifidi() {
          return rifidi;
    }
    public void setRifidi(String rifidi) {
          this.rifidi = rifidi;
    }
   
    public String getIp() {
          return ip;
    }
    public void setIp(String ip) {
          this.ip = ip;
    }
   
    public String getPort() {
          return port;
    }
    public void setPort(String port) {
          this.port = port;
    }
   
    public String getUrlws() {
          return urlws;
    }
    public void setUrlws(String urlws) {
          this.urlws = urlws;
    }
   
    public String getOrigin_warehouse() {
          return origin_warehouse;
    }
    public void setOrigin_warehouse(String origin_warehouse) {
          this.origin_warehouse = origin_warehouse;
    }
   
    public String getMac() {
          return mac;
    }
    public void setMac(String mac) {
          this.mac = mac;
    }
   
    public String getIdflow_default() {
          return idflow_default;
    }
    public void setIdflow_default(String idflow_default) {
          this.idflow_default = idflow_default;
    }
   
    public String getIdstep_default() {
          return idstep_default;
    }
    public void setIdstep_default(String idstep_default) {
          this.idstep_default = idstep_default;
    }
   
    public String getId_dev_printer() {
          return id_dev_printer;
    }
    public void setId_dev_printer(String id_dev_printer) {
          this.id_dev_printer = id_dev_printer;
    }
   
    public String getDevicetype() {
          return devicetype;
    }
    public void setDevicetype(String devicetype) {
          this.devicetype = devicetype;
    }
   
    public String getIdname() {
          return idname;
    }
    public void setIdname(String idname) {
          this.idname = idname;
    }     
    
    
    public String getCmp1() {
		return cmp1;
	}
	public void setCmp1(String cmp1) {
		this.cmp1 = cmp1;
	}
	
	public String getCmp2() {
		return cmp2;
	}
	public void setCmp2(String cmp2) {
		this.cmp2 = cmp2;
	}
	
	public String getCmp3() {
		return cmp3;
	}
	public void setCmp3(String cmp3) {
		this.cmp3 = cmp3;
	}
	
	public String getCmp4() {
		return cmp4;
	}
	public void setCmp4(String cmp4) {
		this.cmp4 = cmp4;
	}
	
}
