package pkg.rfidws;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.properties.ReadProperties;
import okhttp3.MediaType;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.cert.X509Certificate;
import java.io.FileWriter;

import com.csvreader.CsvWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.beans.Rfid_documents;
import pkg.beans.Rfid_documents_details;
import pkg.beans.Rfid_functions;
import pkg.beans.Rfid_scanner;
import pkg.beans.Rfid_step;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetComuni
 */
@WebServlet("/rfid/ws/PushDocsTransact")
public class PushDocsTransact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PushDocsTransact() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static final MediaType MEDIA_TYPE_MARKDOWN
    	= MediaType.parse("text/x-markdown; charset=utf-8");
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement st = null;
		
		String idflow = request.getParameter("idflow");	
		String idstep = request.getParameter("idstep");	
		String docnum = request.getParameter("docnum");
		
		JSONObject responseJson = new JSONObject();
		String output = "";
		
		try {
			
			/*
		     *  fix for
		     *    Exception in thread "main" javax.net.ssl.SSLHandshakeException:
		     *       sun.security.validator.ValidatorException:
		     *           PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
		     *               unable to find valid certification path to requested target
		     */
		    TrustManager[] trustAllCerts = new TrustManager[] {
		       new X509TrustManager() {
		          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		            return null;
		          }

		          public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

		          public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

		       }
		    };

		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		    // Create all-trusting host name verifier
		    HostnameVerifier allHostsValid = new HostnameVerifier() {
		        public boolean verify(String hostname, SSLSession session) {
		          return true;
		        }
		    };
		    // Install the all-trusting host verifier
		    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		    /*
		     * end of the fix
		     */
		    
		    
			conn=Utility.getConnRfid();
			
			// Testata documento
			Rfid_documents doc = new Rfid_documents();
			doc.setId_flow(Integer.parseInt(idflow));
			doc.setId_step(Integer.parseInt(idstep));
			doc.setDocnum(Integer.parseInt(docnum));
			
			doc.selectRowFromDocnum(conn);
			
			if(doc.checkDocRef_destination(conn)) {
			
				// Legge il documento da documents_detail
				Rfid_documents_details det = new Rfid_documents_details();
				det.setId_flow(Integer.parseInt(idflow));
				det.setId_step(Integer.parseInt(idstep));
				det.setDocnum(Integer.parseInt(docnum));
				
				List<Rfid_documents_details> dd = new ArrayList<Rfid_documents_details>();
				dd = det.selectAll(conn, "");
				
				// Scrive il file csv
				String nomeFile = generateString()+".csv";
				
				ReadProperties prop = new ReadProperties("connection.properties");
				String root = prop.get("rootStampe");
				
				String outputFile = root+nomeFile;
				
				// Controlliamo se il file esiste gia
				boolean alreadyExists = new File(outputFile).exists();
					
				try {
					
					CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ';');
					
					// Se il file non esiste mette le intestazioni
					if (!alreadyExists)
					{
						csvOutput.write("code");
						csvOutput.write("stock");
						csvOutput.endRecord();
					}
					// altrimenti va in add
					
					// Scrive i record
					for(int i=0;i<dd.size();i++) {
						csvOutput.write(dd.get(i).getBarcode());
						csvOutput.write(""+(dd.get(i).getQty_theoretical()));
						csvOutput.endRecord();
					}
					
					csvOutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// Legge il config dello step per sapere quale funzione richiamare
				Rfid_step step = new Rfid_step();
				step.setIdflow(Integer.parseInt(idflow));
				step.setIdstep(Integer.parseInt(idstep));
				step.selectRow(conn);
				// Leggo i parametri per il before
				String config_during = step.getConfig_during();
				
				// Lo trasformo in json
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(config_during);
				JSONObject jsonObject = (JSONObject) _obj;
				System.out.println(jsonObject);
		
				// loop array
				JSONArray msg = (JSONArray) jsonObject.get("config");
				Iterator<String> iterator = msg.iterator();
				
				JSONObject cfg_bef = new JSONObject();
				while (iterator.hasNext()) {
				    
					Object obj2 = iterator.next();
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    
				    String name = (String) jsonObject2.get("name");
				    String tp = (String) jsonObject2.get("type");
				    
				    if(tp.equalsIgnoreCase("Text"))
				    	cfg_bef.put(name, (String) jsonObject2.get("value"));
				    else
				    	cfg_bef.put(name, jsonObject2.get("value"));
				}
				// leggo i parametri per l'importazione dei documenti
				String fun_push_doc = ""+cfg_bef.get("fun_push_doc");
				fun_push_doc=Utility.noNull(fun_push_doc);
				
				/*
				if(fun_push_doc.length()==0) {
					
					output = "DON'T Sent to TransAct";
					
				}
				else {
				*/
					// legge come costruire la funzione per spingere su TransAct
					Rfid_functions fun = new Rfid_functions();
					fun.setName(fun_push_doc);
					fun.selectFunctionByName(conn);
					
					String parm = fun.getParameters();
					System.out.println(parm);
					
					parser = new JSONParser();
					_obj = parser.parse(parm);
					jsonObject = (JSONObject) _obj;
					System.out.println(jsonObject);
			
					// loop array
					msg = (JSONArray) jsonObject.get("parameters");
					iterator = msg.iterator();
					
					cfg_bef = new JSONObject();
					while (iterator.hasNext()) {
					    
						Object obj2 = iterator.next();
					    JSONObject jsonObject2 = (JSONObject) obj2;
					    
					    String name = (String) jsonObject2.get("name");
					    String tp = (String) jsonObject2.get("type");
					    
					    if(tp.equalsIgnoreCase("Text"))
					    	cfg_bef.put(name, (String) jsonObject2.get("value"));
					    else
					    	cfg_bef.put(name, jsonObject2.get("value"));
					}
					String fun_url = (String) cfg_bef.get("url");
					String fun_token = (String) cfg_bef.get("token");
					String fun_username = (String) cfg_bef.get("username");
					String fun_password = (String) cfg_bef.get("password");
					String fun_shop = (String) cfg_bef.get("shop");
					String fun_type = (String) cfg_bef.get("type");
					String fun_file_type = (String) cfg_bef.get("file_type");
					
					
					// Spinge su TransAct
					Map<String, String> params = new HashMap<String, String>();
			        //params.put("foo", hash);
			        //params.put("bar", caption);
		
			        String result = multipartRequest(fun_url+"?token="+fun_token+"&username="+fun_username+"&password="+fun_password+"&shop="+fun_shop+"&type="+fun_type, params, outputFile, "file", "MIME_TEXT_PLAIN");
					System.out.println(result);
					
					_obj = parser.parse(result);
					jsonObject = (JSONObject) _obj;
					
					JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
					String printcode = (String) jsonObject2.get("printcode");
					
					// update documents
					doc.setDocref_destination(printcode);
					doc.updateDocref_destination(conn);
					
					// update documents_details
					det.setPackage_id(printcode);
					det.setId_flow(Integer.parseInt(idflow));
					det.setId_step(Integer.parseInt(idstep));
					det.setDocnum(Integer.parseInt(docnum));
					det.updatePackageId(conn);
					
					
					
					
					/*
					// crea scanner
					Rfid_scanner scan = new Rfid_scanner();
					scan.setId_flow(Integer.parseInt(idflow));
					scan.setId_step(Integer.parseInt(idstep));
					scan.setIddoc(doc.getIddoc());
					scan.insert(conn);
					*/
					
					
					
					output = "OK! Sent to TransAct";
					
				//}
			}
			else {
				
				output = "Already sent to TransAct";
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			output = "KO! "+e.getMessage();
			
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		responseJson.put("message", output);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(responseJson.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
	
	
	
	public String multipartRequest(String urlTo, Map<String, String> parmas, String filepath, String filefield, String fileMimeType) throws Exception  {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;

        String twoHyphens = "--";
        String boundary = "*****" + Long.toString(System.currentTimeMillis()) + "*****";
        String lineEnd = "\r\n";

        String result = "";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        String[] q = filepath.split("/");
        int idx = q.length - 1;

        try {
            File file = new File(filepath);
            FileInputStream fileInputStream = new FileInputStream(file);

            URL url = new URL(urlTo);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + q[idx] + "\"" + lineEnd);
            outputStream.writeBytes("Content-Type: " + fileMimeType + lineEnd);
            outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);

            // Upload POST Data
            Iterator<String> keys = parmas.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = parmas.get(key);

                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                outputStream.writeBytes("Content-Type: text/plain" + lineEnd);
                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(value);
                outputStream.writeBytes(lineEnd);
            }

            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


            if (200 != connection.getResponseCode()) {
                throw new Exception("Failed to upload code:" + connection.getResponseCode() + " " + connection.getResponseMessage());
            }

            inputStream = connection.getInputStream();

            result = this.convertStreamToString(inputStream);

            fileInputStream.close();
            inputStream.close();
            outputStream.flush();
            outputStream.close();
            
            return result;

        } catch (Exception e) {
        	throw new Exception(e.getMessage());
        }
		
		

    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
    
}
