package pkg.beans;

public class Aree {
	private String id_store;
	private String id_area;
	private String description;
	private String codice;
	private String store_description;
	private String esterna;
	private String gruppo;
	private String tipo;
	private String id_area_from;
	private String id_area_to;
	
	private String cmp1;
	private String cmp2;
	private String cmp3;
	private String cmp4;
	
	
	
	public Aree() {}

	public Aree(String id_store, String id_area, String description, 
			String codice, String store_description, String esterna, String gruppo, String tipo,
			String id_area_from, String id_area_to,
			String cmp1, String cmp2, String cmp3, String cmp4) {
		
		this.id_store=id_store;
		this.id_area=id_area;
		this.description=description;
		this.codice=codice;
		this.store_description=store_description;
		this.esterna=esterna;
		this.gruppo=gruppo;
		this.tipo=tipo;
		this.id_area_from=this.id_area_from;
		this.id_area_to=this.id_area_to;
		
		this.cmp1=cmp1;
		this.cmp2=cmp2;
		this.cmp3=cmp3;
		this.cmp4=cmp4;
	
	}
	
	public String getId_store() {
		return id_store;
	}
	public void setId_store(String id_store) {
		this.id_store = id_store;
	}

	public String getId_area() {
		return id_area;
	}
	public void setId_area(String id_area) {
		this.id_area = id_area;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public String getStore_description() {
		return store_description;
	}
	public void setStore_description(String store_description) {
		this.store_description = store_description;
	}
	
	public String getEsterna() {
		return esterna;
	}
	public void setEsterna(String esterna) {
		this.esterna = esterna;
	}
	
	public String getGruppo() {
		return gruppo;
	}
	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getId_area_from() {
		return id_area_from;
	}
	public void setId_area_from(String id_area_from) {
		this.id_area_from = id_area_from;
	}
	
	public String getId_area_to() {
		return id_area_to;
	}
	public void setId_area_to(String id_area_to) {
		this.id_area_to = id_area_to;
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
