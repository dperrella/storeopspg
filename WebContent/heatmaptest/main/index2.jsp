<%@ page 
language="java" 
contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"
import="java.sql.*"
import="pkg.utility.Utility"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Heatmap</title>
<style>
#baidu-img {
    position: absolute;
    left: 0;
    top: 120px;
    width:1024px
}

</style>
</head>
<body>
<h1>Smart Light</h1>
<label>Select Hour</label><br/>
<select id="hour" name="hour" onchange="reload(this.value)">
	<option value="9">09:00 - 10:00</option>
	<option value="10">10:00 - 11:00</option>
	<option value="11">11:00 - 12:00</option>
	<option value="12">12:00 - 13:00</option>
	<option value="13">13:00 - 14:00</option>
	<option value="14">14:00 - 15:00</option>
	<option value="15">15:00 - 16:00</option>
	<option value="16">16:00 - 17:00</option>
	<option value="17">17:00 - 18:00</option>
	<option value="18">18:00 - 19:00</option>
	<option value="19">19:00 - 20:00</option>
	<option value="20">20:00 - 21:00</option>
</select>

 <div id="graphic">
    <img id="baidu-img" src="mappa.png" style="width:1024px" />
    <div id="main" class="main"  style="margin-top:48px;height:320px;width:1024px"></div>
</div><!--/span-->


<%
String hour = ""+request.getParameter("hour");
hour=Utility.noNull(hour);
if(hour.length()==0)
	hour = "9";

Connection conn = null;
Statement st = null;
ResultSet rs = null;
String sql = "";

String hm = "";
try {
	String usr = "postgres";
	String psw = "Tammurriata$";
	String url = "jdbc:postgresql://13.94.141.15:5432/";
	String drv = "org.postgresql.Driver";
	String database  = "test";
	
	Class.forName(drv);
	conn = DriverManager.getConnection(url+database, usr, psw);
	st = conn.createStatement();
	
	sql = "select * from heatmap where hour = "+hour+" ";
	rs=st.executeQuery(sql);
	
	while(rs.next()) {
		hm+="["+rs.getString("v1")+","+rs.getString("v2")+","+rs.getString("v3")+"],";
	}
	rs.close();
	if(hm.length()>0)
		hm=hm.substring(0,hm.length()-1);
	
	hm = "["+hm+"]";
	
}
catch(Exception e) {
	e.printStackTrace();
}

%>

<script src="../../js/jquery.min.js"></script>
 
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">
	
	$('#hour').val('<%=hour%>');

	function reload(hour) {
		
		location.href="index2.jsp?hour="+hour;
		
	}
	var heatData = [];
	for (var i = 0; i < 20; ++i) {
	    heatData.push([
	        400 + Math.random() * 300,
	        5 + Math.random() * 10,
	        Math.random()
	    ]);
	}
	for (var i = 0; i < 100; ++i) {
	    heatData.push([
	        100 + Math.random() * 600,
	        150 + Math.random() * 50,
	        Math.random()
	    ]);
	}
	for (var i = 0; i < 200; ++i) {
	    heatData.push([
	        Math.random() * 1000,
	        Math.random() * 800,
	        Math.random() * 0.5
	    ]);
	}
	
	console.log(((heatData)));
	
	
	/*
	for(i=0;i<heatData.length;i++) {
		
		$.ajax({
		      url: 'writeHeatMap.jsp',
		      type: 'POST',
		      dataType: 'html',
		      async: true,
		      data: "heatData="+heatData[i],
		      success: function (data) {
					
					
		      },
		      error: function () {
		      	//alert('Errore! si e\' verificato un errore.');
		      }
		});
		
	}
	*/
	
	//alert(heatData.length);
	
   require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });
    
    require(
        [
            'echarts',
            'echarts/chart/heatmap' 
        ],
        function (ec) {
            var myChart = ec.init(document.getElementById('main')); 
            
            
            
            option = {
           	    title : {
           	        text: ''
           	    },
           	    series : [
           	        {
           	            type : 'heatmap',
           	            data : <%=hm%>,
           	            hoverable : false,
           	            gradientColors: [{
           	                offset: 0.4,
           	                color: 'green'
           	            }, {
           	                offset: 0.5,
           	                color: 'yellow'
           	            }, {
           	                offset: 0.8,
           	                color: 'orange'
           	            }, {
           	                offset: 1,
           	                color: 'red'
           	            }],
           	            minAlpha: 0.2,
           	            valueScale: 2,
           	            opacity: 0.8
           	        }
           	    ]
           	};
    		
    		
    		

    		
            myChart.setOption(option); 
        }
    );
    
</script>
<%
st.close();
conn.close();
%>
</body>
</html>