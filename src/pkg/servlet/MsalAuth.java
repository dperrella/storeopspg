package pkg.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.utility.Utility;

/**
 * Servlet implementation class GetNazioni
 */
@WebServlet("/msal/auth")
public class MsalAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MsalAuth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			HttpSession session = request.getSession();
			
			System.out.println("----  "+session.getAttribute("origin")+" ----");
			session.setAttribute("origin", "Microsoft");
			session.setAttribute("fromHalo", true);
			Thread.sleep(500);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		//response.sendRedirect("../secure/auth.htm");
		
		
		
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // send HTML page to client
        

        out.println("<!DOCTYPE html><html lang=\"it\" class=\"app\">");
        out.println("<head>");
        out.println("<title>Wait...</title>");
        out.println("<link rel=\"stylesheet\" href=\"../js/jPlayer/jplayer.flat.css?2\" type=\"text/css\" />");
        out.println("<link rel=\"stylesheet\" href=\"../css/bootstrap.css?2\" type=\"text/css\" />");
        out.println("<link rel=\"stylesheet\" href=\"../css/animate.css?2\" type=\"text/css\" />");
        out.println("<link rel=\"stylesheet\" href=\"../css/font-awesome.min.css?2\" type=\"text/css\" />");
        out.println("<link rel=\"stylesheet\" href=\"../css/simple-line-icons.css?2\" type=\"text/css\" />");
        out.println("<link rel=\"stylesheet\" href=\"../css/font.css?2\" type=\"text/css\" />");
        out.println("<link rel=\"stylesheet\" href=\"../css/app.css?2\" type=\"text/css\" />  ");
        out.println("<!--[if lt IE 9]>");
        out.println("<script src=\"../js/ie/html5shiv.js\"></script>");
        out.println("<script src=\"../js/ie/respond.min.js\"></script>");
        out.println("<script src=\"../js/ie/excanvas.js\"></script>");
        out.println("<![endif]-->");
        out.println("</head>");
        out.println("<body class=\"bg-dark dker\">");
        out.println("<section id=\"content\" class=\"m-t-lg wrapper-md animated fadeInUp\">");
        out.println("<div class=\"container\">");
        out.println("<header class=\"wrapper text-center\"><img src=\"../images/gif-load.gif\" /></header>");
        out.println("<section class=\"m-b-lg\">");
        out.println("<header class=\"wrapper text-center\">");
        out.println("<strong>Please Wait... Loading Smart Suite from Halo</strong>");
        out.println("</header>");
        out.println("</section>");
        out.println("</div>");
        out.println("</section>");
        
        out.println("<script src=\"../js/jquery.min.js\"></script>");
        out.println("<script src=\"../js/bootstrap.js\"></script>");
        out.println("<script src=\"../js/app.js\"></script>  ");
        out.println("<script src=\"../js/slimscroll/jquery.slimscroll.min.js\"></script>");
        out.println("<script src=\"../js/app.plugin.js\"></script>");
        out.println("");
        out.println("");
        out.println("");
        
        
        out.println("<script>");
        out.println("location.href='../secure/auth.htm'");
        out.println("</script>");
        out.println("</body></html>");
		
//		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		try {
//			HttpSession session = request.getSession();
//			session.setAttribute("origin", "Microsoft");
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		response.sendRedirect("../secure/auth.htm");
		doGet(request, response);
		
	}

}
