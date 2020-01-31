function Autorized2(usr,pwd,link){
//	function Autorized(usr,pwd,link){	


				/*
				if(link.trim() == "http://cloud.smart-control.it/SmartAnalytics/login-esegui.htm"){
					location.href=link+"?username="+usr+"&password="+pwd;
				}else{
					location.href=link;	
				}
				*/
				//location.href=link;
				usr = "ADMIN";
				pwd = "admin";
				location.href=link+"?username="+usr+"&password="+pwd;
	
}


