package pkg.utility;

import java.io.FileNotFoundException;
import java.util.*; 
import javax.mail.*; 
import javax.mail.internet.*;

import pkg.properties.ReadProperties;

import javax.activation.*; 

public class JSendMailSSL { 
	public static void main(String[] args) throws FileNotFoundException 
	{ 
		
		ReadProperties prop = new ReadProperties("connection.properties");
		String to = "dperrella@mcsistemi.net";
		String from = prop.get("mailfrom");
		String host = prop.get("mailsmtp");
		String username = prop.get("mailusr");
		String password = prop.get("mailpsw");
		String obj = "oggetto";
		String msg = "messaggio!!!";
		String attach = "";
				
				
				
		sendMail(to, obj, msg, attach, from);
	}
	
	public static void sendMail(String to, String obj, String msg, String attach, String from ) throws FileNotFoundException 
	{ 
		
		ReadProperties prop = new ReadProperties("connection.properties");
		
		String host = prop.get("mailsmtp2");
		String port = prop.get("mailport2");
		final String username = prop.get("mailusr2");
		final String password = prop.get("mailpsw2");
		
		System.out.println("TLSEmail Start"); 
		// Get the session object 
		
		// Get system properties 
		Properties properties = System.getProperties(); 
		
		// Setup mail server 
		properties.setProperty("mail.smtp.host", host); 
		
		// SSL Port 
		properties.put("mail.smtp.port", port); 
		
		// enable authentication 
		properties.put("mail.smtp.auth", "true"); 
		
		// SSL Factory 
		properties.put("mail.smtp.socketFactory.class", 
				"javax.net.ssl.SSLSocketFactory"); 

		// creating Session instance referenced to 
		// Authenticator object to pass in 
		// Session.getInstance argument 
		Session session = Session.getDefaultInstance(properties, 
			new javax.mail.Authenticator() { 
				
				// override the getPasswordAuthentication 
				// method 
				protected PasswordAuthentication 
						getPasswordAuthentication() { 
					return new PasswordAuthentication(username, 
													password); 
				} 
			}); 
	 
	
		//compose the message 
		try { 
			// javax.mail.internet.MimeMessage class is mostly 
			// used for abstraction. 
			MimeMessage message = new MimeMessage(session); 
			
			// header field of the header. 
			message.setFrom(new InternetAddress(from)); 
			
			message.addRecipient(Message.RecipientType.TO, 
								new InternetAddress(to)); 
			message.setSubject(obj); 
			//message.setText(msg);
			
			if(attach.length()>0)
			{
				// Allegato
				Multipart multipart = new MimeMultipart();
		
				// Crea la parte testuale del messaggio
				BodyPart messageBodyPart1 = new MimeBodyPart();
				messageBodyPart1.setContent(message, "text/html");
				
				// Aggiunge la paorte testuale..
				multipart.addBodyPart( messageBodyPart1 );
				
				DataSource source = null;
				BodyPart messageBodyPart2 = null;
				
				// ----------------------------------------------------------
				// Spezza l'array dell'allegato per costruire il messaggio...
				// ----------------------------------------------------------
				// Tolgo l'ultima virgola...
				attach = attach.substring(0,attach.length()-1);
				
				String []temp = null;
				temp = attach.split(",");
				for(int i=0; i < temp.length ; i++)
				{
					// Crea l'allegato
					source = new FileDataSource(temp[i]);
					messageBodyPart2 = new MimeBodyPart();
					messageBodyPart2.setDataHandler( new DataHandler(source) );
					messageBodyPart2.setFileName(source.getName());
					// Aggiunge l'allegato..
					multipart.addBodyPart( messageBodyPart2 );
				}
				
				// Imposta come contenuto del messaggio l'oggetto multipart
				message.setContent(multipart); 
			}
			else
			{
				message.setContent(msg, "text/html");
			}
		
			// Send message 
			Transport.send(message); 
			System.out.println("Yo it has been sent.."); 
		} 
		catch (MessagingException mex) { 
			mex.printStackTrace(); 
		} 
	} 
} 
