package pkg.utility;

import java.io.PrintWriter;

public class DecathlonEncode {

	public static String SKU = "1111231909938";
	public static String BCODE1 = "1111231";
	public static String BCODE2 = "909938";
	public static int FROM = 1;
	public static int TO = 350;
	public static String CODE = "2046797";
	public static String DESCRIPTION = "Chest protection";
	public static String ZPL = "^XA\n" + 
			"^WVY^RS8^RU^RB96,8,3,3,24,20,38^FS\n" + 
			"^RR3,0,^RFW,E^FD48,1,5,##bcode1##,##bcode2##,##serial##^FS\n" + 
			"^FO18,25^BRN,6,2,2,25,10^FD010##sku##21##serial##^FS\n" + 
			"^FO45,100^A0N,22,22^FD(01)0##sku##(21)##serial##^FS\n" + 
			"^FO30,130^A0N,30,30^FDItem: ##code##^FS\n" + 
			"^FO425,110^XGE:LOGORFID.GRF^FS\n" + 
			"^FO30,155^A0N,23,23^FD##description##^FS\n" + 
			"^XZ";
	
	
	
	public static void main(String[] args) {
		
		try {
			String zpl = "";
			String serial = "";
			for(int i = FROM; i<=TO; i++) {
				
				serial = String.format("%012d", i);
				
				zpl+=ZPL.replaceAll("##bcode1##", BCODE1)
						.replaceAll("##bcode2##", BCODE2)
						.replaceAll("##serial##", serial)
						.replaceAll("##sku##", SKU)
						.replaceAll("##code##", CODE)
						.replaceAll("##description##", DESCRIPTION);
						
						
				
			}
			
			PrintWriter writer = new PrintWriter("/Users/danieleperrella/desktop/_1111231909938.txt", "UTF-8");
			writer.println(zpl);
			writer.close();
			
			System.out.println(zpl);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		

	}

}
