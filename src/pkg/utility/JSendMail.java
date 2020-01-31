package pkg.utility;


import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;

import java.io.FileNotFoundException;

import pkg.properties.ReadProperties;

public class JSendMail 
{
	public static String msgMail(String _to,
								 String _subject,
								 String _message,
								 String _attach,
								 String _from) throws MessagingException, FileNotFoundException 
	{
		_attach = _attach.trim();
		
		new JSendMail().run(_to, _subject, _message, _attach, _from);
		
		return "";
	}

	private void run(String _to, String _subject, String _message, String _attach, String _from) throws MessagingException, FileNotFoundException 
	{
		// Reperimento parametri da file 'app.properties'
		ReadProperties prop = new ReadProperties("connection.properties");
		String _type	= prop.get("mailtype");
		
		// Se non viene passato come parametro il MITTENTE viene letto dal file di properties
		if(_from.trim().length()==0)
		{
			_from = prop.get("mailfrom");
		}
		
		// Replace di '#' con '@' nel caso la mail arrivi da AS400
		_from = _from.replaceAll("#", "@");
		
		Message message = new MimeMessage(getSession());
		
		// Richiesta conferma di lettura...
		/*
		message.setHeader("Disposition-Notification-To", _from);
		message.setHeader("Return-Recepit-To", _from);
		*/
		
		message.addRecipient(RecipientType.TO, new InternetAddress(_to));
		message.addFrom(new InternetAddress[] 
		{ 
			new InternetAddress(_from) 
		});
		
		// Oggetto del messaggio
		message.setSubject(_subject);
		
		if(_attach.length()>0)
		{
			// Allegato
			Multipart multipart = new MimeMultipart();
	
			// Crea la parte testuale del messaggio
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(_message, _type);
			
			// Aggiunge la paorte testuale..
			multipart.addBodyPart( messageBodyPart1 );
			
			DataSource source = null;
			BodyPart messageBodyPart2 = null;
			
			// ----------------------------------------------------------
			// Spezza l'array dell'allegato per costruire il messaggio...
			// ----------------------------------------------------------
			// Tolgo l'ultima virgola...
			_attach = _attach.substring(0,_attach.length()-1);
			
			String []temp = null;
			temp = _attach.split(",");
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
			message.setContent(_message, _type);
		}
		// Invia il messaggio
		Transport.send(message);
		
		// Output per la console JAVA
		System.out.println("Il messaggio '" + _subject + "' e' stato inviato correttamente a " + _to + ".");
	}

	private Session getSession() throws FileNotFoundException 
	{
		Authenticator authenticator = new Authenticator();
		
		//Reperimento parametri da file 'app.properties'
		ReadProperties prop = new ReadProperties("connection.properties");
		
		String _smtp	= prop.get("mailsmtp");
		String _port	= prop.get("mailport");
		
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		properties.setProperty("mail.smtp.auth", "true");

		properties.setProperty("mail.smtp.host", _smtp);
		properties.setProperty("mail.smtp.port", _port);

		return Session.getInstance(properties, authenticator);
	}

	private class Authenticator extends javax.mail.Authenticator 
	{
		private PasswordAuthentication authentication;

		public Authenticator() throws FileNotFoundException 
		{
			// Reperimento parametri da file 'app.properties'
			ReadProperties prop = new ReadProperties("connection.properties");
			
			String _usr	= prop.get("mailusr");
			String _psw	= prop.get("mailpsw");
			
			String username = _usr;
			String password = _psw;
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() 
		{
			return authentication;
		}
	}
}
