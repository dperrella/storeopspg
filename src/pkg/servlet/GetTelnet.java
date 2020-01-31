package pkg.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
 
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelExec;

public class GetTelnet extends HttpServlet {
	private static final long serialVersionUID = 1L;       

    public GetTelnet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
       
		System.out.println("Telnet");
		String resp = "";
		//String command = request.getParameter("command");
		String s = "";
		int k = 0;
		String flag = "KO";

/**/
		try {
		Runtime runTime=Runtime.getRuntime();
        String command[]={"cmd.exe","/C start telnet 2.229.69.115 23"};
        //Process process=runTime.exec("cmd.exe /C start telnet 192.168.0.246");
        Process process=runTime.exec(command);
         
        BufferedReader input = new BufferedReader
           (new InputStreamReader
                (process.getInputStream()));
        BufferedReader errorInput= new BufferedReader
           (new InputStreamReader
                   (process.getErrorStream()));
         
        System.out.println("***** Command Output ********");
        String line=input.readLine();
        while(line!=null)
        {
            System.out.println(""+line);
            line=input.readLine();
        }
         
         
        String commandError=errorInput.readLine();
        if(commandError!=null)
            System.out.println("*** Command Errors ***");
        while(commandError != null)
        {
            System.out.println(commandError);
            commandError= errorInput.readLine();
        }
         
         
    } catch (IOException e) {
        System.out.println("Exception....");
        e.printStackTrace();
    }
/**/
		//resp = Integer.toString(k);

        resp = "";
        
		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}
