package pkg.utility;
import java.sql.*;

public class ProvaDow {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = Utility.getConnData();
		Statement st = conn.createStatement();
		
		String sql = "select min(extract(hour from fromhour)) from dailyoperation_stores where id_Stores::text = (select unnest(ARRAY[string_to_array(96::text,'*'::text)])); ";
		
		String sql2= "select max(extract(hour from tohour)) from dailyoperation_stores where id_Stores::text = (select unnest(ARRAY[string_to_array(96::text,'*'::text)])) ";
		
		String sql3= "select def.kpi,extract(isodow from c)::integer c_dow, extract(hour from c)::integer c_hour,avg(coalesce(val,null,0)) val from \n" + 
				"(select * from 	(select distinct unnest(ARRAY[string_to_array('AIN','*'::text)]) kpi) a,\n" + 
				"(select unnest(ARRAY[string_to_array('96','*'::text)])::integer id_store) b,\n" + 
				"generate_series('2018-11-15'::timestamp with time zone,'2018-11-15 23:59:59'::timestamp with time zone,'1 hour'::interval) c where c < now()) def\n" + 
				"				left join mvkpicountergroup_hour_201811 k \n" + 
				"				on def.id_store = k.id_store and k.c_dow = extract(isodow from c) and k.c_hour = extract(hour from c) and k.kpi = def.kpi and def.c::date = datestart::date\n" + 
				"			where extract(hour from c) between 7 and 21\n" + 
				"			group by def.kpi,extract(isodow from c), extract(hour from c) ";
		
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			System.out.println(rs.getInt(1));
		}
		rs.close();
		
		rs = st.executeQuery(sql2);
		if(rs.next()) {
			System.out.println(rs.getInt(1));
		}
		rs.close();
		
		rs = st.executeQuery(sql3);
		while(rs.next()) {
			System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
		}
		rs.close();
		
		String sql4 = "generate_series('2018-11-15'::timestamp with time zone,'2018-11-15 23:59:59'::timestamp with time zone ";
		rs = st.executeQuery(sql4);
		if(rs.next()) {
			System.out.println(rs.getString(1));
		}
		rs.close();
		st.close();
		conn.close();
	}

}
