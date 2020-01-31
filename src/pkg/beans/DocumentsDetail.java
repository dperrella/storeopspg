package pkg.beans;
 
public class DocumentsDetail {
       private String id_documents;
       private String idflow;
       private String idstep;
       private String epc;
       private String qty_theoretical;
       private String qty_phisycal;
       private String id_loadunits;
       private String id_package;
       private String id_pallet;
       private String status;
       private String barcode;
 
      
      
      
       public DocumentsDetail() {}
 
       public DocumentsDetail(String id_documents, String idflow, String idstep, String epc,
                    String qty_theoretical, String qty_phisycal, String id_loadunits,
                    String id_package, String id_pallet,String status,String barcode) {
            
             this.id_documents=id_documents;
             this.idflow=idflow;
             this.idstep=idstep;
             this.epc=epc;
             this.qty_theoretical=qty_theoretical;
             this.qty_phisycal=qty_phisycal;
             this.id_loadunits=id_loadunits;
             this.id_package=id_package;
             this.id_pallet=id_pallet;
             this.status=status;
             this.barcode=barcode;
 
      
       }
      
       public String getId_documents() {
             return id_documents;
       }
       public void setId_documents(String id_documents) {
             this.id_documents = id_documents;
       }
 
       public String getIdflow() {
             return idflow;
       }
       public void setIdflow(String idflow) {
             this.idflow = idflow;
       }
      
       public String getIdstep() {
             return idstep;
       }
       public void setIdstep(String idstep) {
             this.idstep = idstep;
       }
      
       public String getEpc() {
             return epc;
       }
       public void setEpc(String epc) {
             this.epc = epc;
       }
      
       public String getQty_theoretical() {
             return qty_theoretical;
       }
       public void setQty_theoretical(String qty_theoretical) {
             this.qty_theoretical = qty_theoretical;
       }
      
       public String getQty_phisycal() {
             return qty_phisycal;
       }
       public void setQty_phisycal(String qty_phisycal) {
             this.qty_phisycal = qty_phisycal;
       }
      
       public String getId_loadunits() {
             return id_loadunits;
       }
       public void setId_loadunits(String id_loadunits) {
             this.id_loadunits = id_loadunits;
       }
      
       public String getId_package() {
             return id_package;
       }
       public void setId_package(String id_package) {
             this.id_package = id_package;
       }
      
       public String getId_pallet() {
             return id_pallet;
       }
       public void setId_pallet(String id_pallet) {
             this.id_pallet = id_pallet;
       }
      
       public String getStatus() {
             return status;
       }
       public void setStatus(String status) {
             this.status = status;
       }
      
       public String getBarcode() {
             return barcode;
       }
       public void setBarcode(String barcode) {
             this.barcode = barcode;
       }
 
}
 