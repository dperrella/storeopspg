package pkg.HR;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.properties.ReadProperties;
import pkg.Stampe.PrintPlanner;

public class PlannerStampa extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		
		// Id_departments
		String id_departments = req.getParameter("id_departments");
		
		// Anno
		String year = req.getParameter("year");
		
		// Settimana
		String week = req.getParameter("week");
		
		String msg = "";
		
		System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "{}");
		
		try {
			
			String nomeFile = PrintPlanner.genStampa(id_departments, year, week);
			
			byte[] b = nomeFile.getBytes("UTF-8");
			nomeFile = new String(b, "UTF-8");
			
			nomeFile = nomeFile.replaceAll("\\\\", "%5C").replaceAll("\\\\", "%5C").replaceAll("\\\\", "%5C").replaceAll("\\\\", "%5C");
			
			if(nomeFile.length()==0) {
				msg = "<div class=\"panel-body\"><div class=\"alert alert-danger\">"+propLang.get("plaError")+"</div></div>";
			}
			else {
				
				msg = "<div class=\"panel-body\"><div class=\"alert alert-success\">"+propLang.get("plaMsgSuccess")+" <strong>"+year+"/"+week+"</strong>.</div>";
					
				msg+= "<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
				
				msg+= "<div class=\"row\"><div class=\"col-sm-3\">"+
					  "  <a href=\"../planner/planner-list-tab2.htm?tp=I&year="+year+"&week="+week+"&id_departments="+id_departments+"\" class=\"btn btn-default btn-block btn-lg\">"+
					  "		<i class=\"fa fa-file-text-o\"></i> "+propLang.get("plaBtnVis")+"</a>"+
					  "</div>"+
				
					  "<div class=\"col-sm-3\">"+
					 // "  <a href=\"../../Browser.jsp?sort=1&file="+nomeFile+"\" class=\"btn btn-primary btn-block btn-lg\" target=\"_blank\">"+
					  "  <a href=\"../../servlet/ws/Download?fileName="+nomeFile+"\" class=\"btn btn-primary btn-block btn-lg\" target=\"_blank\">"+
					  "	   <i class=\"fa fa-save\"></i> "+propLang.get("plaBtnScarica")+"</a>"+
					  "</div>"+
						  
	 				  "</div>";	
				
				msg+= "<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
				
				msg+= "<div class=\"padder\" align=\"center\"><span class=\"h4 block font-thin\">"+propLang.get("plaSend")+"</span></div>";
		        
				msg+= "<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
				
				msg+= "<div id=\"msg\"></div>";
				
				msg+= "<form data-validate=\"parsley\" class=\"form-horizontal\" method=\"post\" onsubmit=\"return control(this)\">";
				
				msg+= "<input type=\"hidden\" id=\"id_departments\" name=\"id_departments\" value=\""+id_departments+"\">"+
					  "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">"+
					  "<input type=\"hidden\" id=\"nomeFile\" name=\"nomeFile\" value=\""+nomeFile+"\">"+
					  "<input type=\"hidden\" id=\"azione\" name=\"azione\" value=\"S\">";
				
				msg+= "<div class=\"form-group\">"+
	                  "<label class=\"col-sm-2 control-label\">"+propLang.get("plaMail")+"</label>"+
	                  "<div class=\"col-sm-10\">"+
	                  "<input type=\"text\" id=\"dest\" name=\"dest\" class=\"form-control\" data-type=\"email\" data-required=\"true\">"+
	                  "</div></div>";
				
				msg+= "<div class=\"form-group\">"+
	                  "<label class=\"col-sm-2 control-label\">"+propLang.get("plaOggetto")+"</label>"+
	                  "<div class=\"col-sm-10\">"+
	                  "<input type=\"text\" id=\"ogg\" name=\"ogg\" class=\"form-control\">"+
	                  "</div></div>";
				
				msg+= "<div class=\"form-group\">"+
                      "<label class=\"col-sm-2 control-label\">"+propLang.get("plaMess")+"</label>"+
                      "<div class=\"col-sm-10\">"+
                      "  <div class=\"btn-toolbar m-b-sm btn-editor\" data-role=\"editor-toolbar\" data-target=\"#editor\">"+
                      "    <div class=\"btn-group\">"+
                      "      <a class=\"btn btn-default btn-sm dropdown-toggle\" data-toggle=\"dropdown\" title=\"Font\"><i class=\"fa fa-font\"></i><b class=\"caret\"></b></a>"+
                      "        <ul class=\"dropdown-menu\">"+
                      "        </ul>"+
                      "    </div>"+
                      "    <div class=\"btn-group\">"+
                      "      <a class=\"btn btn-default btn-sm dropdown-toggle\" data-toggle=\"dropdown\" title=\"Font Size\"><i class=\"fa fa-text-height\"></i>&nbsp;<b class=\"caret\"></b></a>"+
                      "        <ul class=\"dropdown-menu\">"+
                      "        <li><a data-edit=\"fontSize 5\" style=\"font-size:24px\">Huge</a></li>"+
                      "        <li><a data-edit=\"fontSize 3\" style=\"font-size:18px\">Normal</a></li>"+
                      "        <li><a data-edit=\"fontSize 1\" style=\"font-size:14px\">Small</a></li>"+
                      "        </ul>"+
                      "    </div>"+
                      "    <div class=\"btn-group\">"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"bold\" title=\"Bold (Ctrl/Cmd+B)\"><i class=\"fa fa-bold\"></i></a>"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"italic\" title=\"Italic (Ctrl/Cmd+I)\"><i class=\"fa fa-italic\"></i></a>"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"strikethrough\" title=\"Strikethrough\"><i class=\"fa fa-strikethrough\"></i></a>"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"underline\" title=\"Underline (Ctrl/Cmd+U)\"><i class=\"fa fa-underline\"></i></a>"+
                      "    </div>"+
                      "    <div class=\"btn-group\">"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"insertunorderedlist\" title=\"Bullet list\"><i class=\"fa fa-list-ul\"></i></a>"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"insertorderedlist\" title=\"Number list\"><i class=\"fa fa-list-ol\"></i></a>"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"outdent\" title=\"Reduce indent (Shift+Tab)\"><i class=\"fa fa-dedent\"></i></a>"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"indent\" title=\"Indent (Tab)\"><i class=\"fa fa-indent\"></i></a>"+
                      "    </div>"+
                      "    <div class=\"btn-group\">"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"justifyleft\" title=\"Align Left (Ctrl/Cmd+L)\"><i class=\"fa fa-align-left\"></i></a>"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"justifycenter\" title=\"Center (Ctrl/Cmd+E)\"><i class=\"fa fa-align-center\"></i></a>"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"justifyright\" title=\"Align Right (Ctrl/Cmd+R)\"><i class=\"fa fa-align-right\"></i></a>"+
                      "     <a class=\"btn btn-default btn-sm\" data-edit=\"justifyfull\" title=\"Justify (Ctrl/Cmd+J)\"><i class=\"fa fa-align-justify\"></i></a>"+
                      "    </div>"+
                      "    <div class=\"btn-group\">"+
                      "      <a class=\"btn btn-default btn-sm dropdown-toggle\" data-toggle=\"dropdown\" title=\"Hyperlink\"><i class=\"fa fa-link\"></i></a>"+
                      "      <div class=\"dropdown-menu\">"+
                      "        <div class=\"input-group m-l-xs m-r-xs\">"+
                      "          <input class=\"form-control input-sm\" placeholder=\"URL\" type=\"text\" data-edit=\"createLink\"/>"+
                      "          <div class=\"input-group-btn\">"+
                      "            <button class=\"btn btn-default btn-sm\" type=\"button\">Add</button>"+
                      "          </div>"+
                      "        </div>"+
                      "      </div>"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"unlink\" title=\"Remove Hyperlink\"><i class=\"fa fa-cut\"></i></a>"+
                      "    </div>"+
                      "    <div class=\"btn-group hide\">"+
                      "      <a class=\"btn btn-default btn-sm\" title=\"Insert picture (or just drag & drop)\" id=\"pictureBtn\"><i class=\"fa fa-picture-o\"></i></a>"+
                      "      <input type=\"file\" data-role=\"magic-overlay\" data-target=\"#pictureBtn\" data-edit=\"insertImage\" />"+
                      "    </div>"+
                      "    <div class=\"btn-group\">"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"undo\" title=\"Undo (Ctrl/Cmd+Z)\"><i class=\"fa fa-undo\"></i></a>"+
                      "      <a class=\"btn btn-default btn-sm\" data-edit=\"redo\" title=\"Redo (Ctrl/Cmd+Y)\"><i class=\"fa fa-repeat\"></i></a>"+
                      "    </div>"+
                      "  </div>"+
                      "  <div id=\"editor\" class=\"form-control\" style=\"overflow:scroll;height:150px;max-height:150px\">"+
                      ""+
                      "  </div>"+
                      "</div>"+
                      "</div>"+
                      "</div>"+
                      "</div>";
				
				msg+= "<footer class=\"panel-footer text-right bg-light lter\">"+
					  "<button type=\"submit\" id=\"submit\" class=\"btn btn-success btn-lg\">"+propLang.get("plaBtnSend")+"</button>"+
					  "</footer>";

				
				/*
				msg+= "<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
				
				msg+= "<div class=\"form-group\">"+
                      "<label class=\"col-sm-2 control-label\"></label>"+
                      "<div class=\"col-sm-10\"><button type=\"submit\" id=\"submit\" class=\"btn btn-success btn-lg\">Invia Mail</button></div></div>";
			    */
				
				msg+= "</form>";
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		
		}
		
		obj.put("dsfun", "Stampa Pianificazione");
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/planner/planner-stampa", obj);
	}
	
}
