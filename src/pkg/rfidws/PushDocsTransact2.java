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
@WebServlet("/rfid/ws/PushDocsTransact2")
public class PushDocsTransact2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PushDocsTransact2() {
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
		String fun_url = request.getParameter("url");
		String fun_token = request.getParameter("token");
		String fun_username = request.getParameter("username");
		String fun_password = request.getParameter("password");
		String fun_shop = request.getParameter("shop");
		String fun_type = request.getParameter("type");
		
		System.out.println("");
		System.out.println(fun_shop);
		System.out.println("");
		
		
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
			
			if(doc.checkDocExchange(conn)) {
				
				System.out.println("a");
			
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
				
				
					
					
				// Spinge su TransAct
				Map<String, String> params = new HashMap<String, String>();
		        //params.put("foo", hash);
		        //params.put("bar", caption);
	
				System.out.println(fun_url+"?token="+fun_token+"&username="+fun_username+"&password="+fun_password+"&shop="+fun_shop+"&type="+fun_type);
				
		        String result = multipartRequest(fun_url+"?token="+fun_token+"&username="+fun_username+"&password="+fun_password+"&shop="+fun_shop+"&type="+fun_type, params, outputFile, "file", "MIME_TEXT_PLAIN");
				System.out.println(result);
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(result);
				JSONObject jsonObject = (JSONObject) _obj;
				
				JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
				String printcode = (String) jsonObject2.get("printcode");
				
				// update documents
				
				doc.setId_flow(Integer.parseInt(idflow));
				doc.setId_step(Integer.parseInt(idstep));
				doc.setDoc_exchange(printcode);
				doc.updateDoc_exchange(conn);
				
				// update documents_details
				det.setPackage_id(printcode);
				det.setId_flow(Integer.parseInt(idflow));
				det.setId_step(Integer.parseInt(idstep));
				det.setDocnum(Integer.parseInt(docnum));
				det.updatePackageId(conn);
				
				
				
				
				
				output = "OK! Sent to TransAct";
					
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
