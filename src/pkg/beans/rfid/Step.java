package pkg.beans.rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Step implements CRUD {
	
	private int idStep;
	private String description;
	private boolean canCreateDoc;
	private int idFlow;
	private int idInterface;
	private int idStepType;
	private String color;
	private int count;
	private String type;
	private String valtype;
	
	public Step() {	}

	public Step(int idStep, String description, boolean canCreateDoc, int idFlow, int idInterface, int idStepType, 
			String color, int count, String type, String valtype) {
		this.idStep = idStep;
		this.description = description;
		this.canCreateDoc = canCreateDoc;
		this.idFlow = idFlow;
		this.idInterface = idInterface;
		this.idStepType = idStepType;
		this.color = color;
		this.count = count;
		this.type = type;
		this.valtype = valtype;
	}


	public int getIdStep() {
		return idStep;
	}


	public void setIdStep(int idStep) {
		this.idStep = idStep;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isCanCreateDoc() {
		return canCreateDoc;
	}


	public void setCanCreateDoc(boolean canCreateDoc) {
		this.canCreateDoc = canCreateDoc;
	}


	public int getIdFlow() {
		return idFlow;
	}


	public void setIdFlow(int idFlow) {
		this.idFlow = idFlow;
	}


	public int getIdInterface() {
		return idInterface;
	}


	public void setIdInterface(int idInterface) {
		this.idInterface = idInterface;
	}


	public int getIdStepType() {
		return idStepType;
	}


	public void setIdStepType(int idStepType) {
		this.idStepType = idStepType;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getValtype() {
		return valtype;
	}
	public void setValtype(String valtype) {
		this.valtype = valtype;
	}


	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from f_step where idstep = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idStep);
			rs = st.executeQuery();
			if(rs.next()) {
				setDescription(rs.getString("description"));				
				setCanCreateDoc(rs.getBoolean("can_create_doc"));
				setIdFlow(rs.getInt("idflow"));
				setIdInterface(rs.getInt("idinterface"));
				setIdStepType(rs.getInt("idsteptype"));
				setColor(rs.getString("color"));
				setType(rs.getString("type"));
				setValtype(rs.getString("valtype"));
			}
			rs.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
	}
	
	public static int getNrStepByIdCustomer(Connection conn, int idCustomer) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		int nrStep = 0;
		try {
			sql = "select count(*) nr from step where idflow in(select idflow from flow where id_customer = ?) ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			while(rs.next()) {
				nrStep = rs.getInt("nr");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		return nrStep;
	}
	
	public static ArrayList<Step> getStepByIdCustomer(Connection conn, int idCustomer){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Step> stepList = null;
		try {
			stepList = new ArrayList<>();
			sql = "select * from step where idflow in(select idflow from flow where id_customer = ?) ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idCustomer);
			rs = st.executeQuery();
			int count = 0;
			while(rs.next()) {
				count ++;
				stepList.add(new Step(rs.getInt("idstep"), rs.getString("description"), rs.getBoolean("can_create_doc"),
						             rs.getInt("idflow"), rs.getInt("idinterface"), rs.getInt("idsteptype"), rs.getString("color"), count,
						             rs.getString("type"), rs.getString("valtype")));
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		return stepList;
	}
	
	
	public static ArrayList<Step> getStepByIdFlow(Connection conn, int idFlow){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Step> stepList = null;
		try {
			stepList = new ArrayList<>();
			sql = "select * from step where idflow = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idFlow);
			rs = st.executeQuery();
			int count = 0;
			while(rs.next()) {
				count++;
				stepList.add(new Step(rs.getInt("idstep"), rs.getString("description"), rs.getBoolean("can_create_doc"),
						             rs.getInt("idflow"), rs.getInt("idinterface"), rs.getInt("idsteptype"), rs.getString("color"), 
						             count, rs.getString("type"), rs.getString("valtype")));
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		return stepList;
	}
	
	@Override
	public void update(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "update f_step set description = ?, can_create_doc = ?, idinterface= ?, idsteptype = ?, color = ? where idstep = ?";
	      
		  st = conn.prepareStatement(sql);
	      st.setString(1, this.description);
	      st.setBoolean(2, this.canCreateDoc);
	      st.setInt(3, this.idInterface);
	      st.setInt(4, this.idStepType);
	      st.setString(5,  this.color);
	      st.setInt(6, this.idStep);
	      // execute the java preparedstatement
	      
	      System.out.println(st.toString());
	      st.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
	}
	
	@Override
	public void add(Connection conn){
		PreparedStatement st = null;
		try{
			String sql = " insert into f_step (description, can_create_doc, idflow, idinterface, idsteptype, color)" + 
					" values (?, ?, ?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.description);
	        st.setBoolean(2, this.canCreateDoc);
	        st.setInt(3, this.idFlow);
	        st.setInt(4, this.idInterface);
	        st.setInt(5, this.idStepType);
	        st.setString(6, this.color);
			// execute the java preparedstatement
			st.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
	}
	
	@Override
	public void delete(Connection conn){
		PreparedStatement st = null;
		try{
		  String sql = "delete from f_step where idstep = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idStep);
	      
	      // execute the java preparedstatement
	      st.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
	}
	
	public void deleteByFlow(Connection conn){
		PreparedStatement st = null;
		try{
		  String sql = "delete from step where idflow = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, this.idFlow);
	      // execute the java preparedstatement
	      st.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
	}
	
	public void deleteGroupedStep(Connection conn, int grouped_idstep) {
		
		PreparedStatement st = null;
		try{
		  String sql = "delete from grouped_step where grouped_idstep = ? ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, grouped_idstep);
	      // execute the java preparedstatement
	      System.out.println(st.toString());
	      st.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		
	}
	
	public void addGroupedStep(Connection conn, int grouped_idstep, int idstep) {
		
		PreparedStatement st = null;
		try{
		  String sql = "insert into grouped_step (grouped_idstep, idstep) values (?, ?) ";
	      st = conn.prepareStatement(sql);
	      st.setInt(1, grouped_idstep);
	      st.setInt(2, idstep);
	      
	      // execute the java preparedstatement
	      st.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		
	}
	
	
	
	public static ArrayList<Step> getGroupedStepByIdFlow(Connection conn, int idFlow){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Step> stepList = null;
		try {
			stepList = new ArrayList<>();
			sql = "select * from f_step where idflow = ? and type = 'G' ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idFlow);
			System.out.println(st.toString());
			rs = st.executeQuery();
			int count = 0;
			while(rs.next()) {
				count++;
				stepList.add(new Step(rs.getInt("idstep"), rs.getString("description"), rs.getBoolean("can_create_doc"),
						             rs.getInt("idflow"), rs.getInt("idinterface"), rs.getInt("idsteptype"), rs.getString("color"), 
						             count, rs.getString("type"), rs.getString("valtype")));
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		return stepList;
	}
	
	public static String selectIdstepByGroupedStep(Connection conn, int grouped_idstep) {
		
		String ids = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select * from grouped_step " + 
					"where grouped_idstep = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, grouped_idstep);
			rs = st.executeQuery();
			while(rs.next()) {
				ids+=rs.getString("idstep")+",";
			}
			rs.close();
			if(ids.length()>0)
				ids = ids.substring(0, ids.length()-1);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}	
		return ids;
		
	}
	
	public void addGrouped(Connection conn){
		PreparedStatement st = null;
		ResultSet rs = null;
		
		System.out.println(this.color);
		
		try{
			String sql = " insert into step (description, can_create_doc, idflow, idinterface, idsteptype, color, type, valtype)" + 
					" values (?, ?, ?, ?, ?, ?, ?, ?) returning idstep ";
			st = conn.prepareStatement(sql);
			st.setString(1, this.description);
	        st.setBoolean(2, this.canCreateDoc);
	        st.setInt(3, this.idFlow);
	        st.setInt(4, this.idInterface);
	        st.setInt(5, this.idStepType);
	        st.setString(6, this.color);
	        st.setString(7, this.type);
	        st.setString(8, this.valtype);
	        
	        rs = st.executeQuery();
	        if(rs.next()) {
	        	this.idStep = rs.getInt("idstep");
	        }
	        rs.close();
	        
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
	}
	
	public static String selectIdstepByGroupedStep2(Connection conn, int grouped_idstep) {
		
		String ids = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select * from grouped_step " + 
					"where grouped_idstep = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, grouped_idstep);
			rs = st.executeQuery();
			while(rs.next()) {
				ids+=rs.getString("idstep")+",";
			}
			rs.close();
			if(ids.length()>0) {
				ids = ids.substring(0, ids.length()-1);
			}
			else {
				ids = ""+grouped_idstep;
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}	
		return ids;
		
	}
	
	public static boolean isStepInventory(Connection conn, int idstep) {
		
		boolean is = false;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select inventory " + 
				"from step s join step_type t on s.idsteptype = t.idstep_type " + 
				"where s.idstep = ? ";
			st = conn.prepareStatement(sql);
			st.setInt(1, idstep);
			rs = st.executeQuery();
			System.out.println(st.toString());
			if(rs.next()) {
				is=rs.getBoolean("inventory");
			}
			rs.close();
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}	
		return is;
		
	}
}
