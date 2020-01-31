package pkg.beans.signin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Users {
	
	private String idusr;
	private String pwd;
	private String nome;
	private String cogn;
	private String tipo;
	private String foto;
	private String mail;
	private String language;
	private String token;
	private boolean active;
	private String origin;
	
	public Users() {}

	public Users(String idusr, String pwd, String nome, String cogn, 
			String tipo, String foto, String mail, String language,
			String token, boolean active, String origin) {
		this.idusr = idusr;
		this.pwd = pwd;
		this.nome = nome;
		this.cogn = cogn;
		this.tipo = tipo;
		this.foto = foto;
		this.mail = mail;
		this.language = language;
		this.token = token;
		this.active = active;
		this.origin = origin;
	}
	
	public String getIdusr() {
		return idusr;
	}

	public void setIdusr(String idusr) {
		this.idusr = idusr;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCogn() {
		return cogn;
	}

	public void setCogn(String cogn) {
		this.cogn = cogn;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void selectRow(Connection conn) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "select * from zutenti where idusr = ?";
			st = conn.prepareStatement(sql);
			st.setString(1, idusr);
			rs = st.executeQuery();
			
			
			if(rs.next()) {
				setIdusr(rs.getString("idusr"));				
				setNome(rs.getString("nome"));
				setCogn(rs.getString("cogn"));
				setTipo(rs.getString("tipo"));
				setFoto(rs.getString("foto2"));
				setMail(rs.getString("mail"));
				setLanguage(rs.getString("language"));
				setToken(rs.getString("token"));
				setActive(rs.getBoolean("active"));
				setOrigin(rs.getString("origin"));
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
	
	public static boolean userExist(Connection conn, String idusr) {
		boolean exist = false;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		try {
			
			sql = "select * from zutenti where idusr = ? ";
			st = conn.prepareStatement(sql);
			st.setString(1, idusr);
			rs = st.executeQuery();
			if(rs.next()) {
				exist = true;
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
		return exist;
		
	}

	public static ArrayList<Users> getUsers(Connection conn, boolean active){
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		ArrayList<Users> usersList = null;
		try {
			usersList = new ArrayList<>();
			sql = "select * from zutenti where active = ? ";
			st = conn.prepareStatement(sql);
			st.setBoolean(1, active);
			rs = st.executeQuery();
			while(rs.next()) {
				usersList.add(new Users(
						rs.getString("idusr"), 
						rs.getString("pwd"), 
						rs.getString("nome"),
						rs.getString("cogn"),
						rs.getString("tipo"),
						rs.getString("foto"),
						rs.getString("mail"),
						rs.getString("language"),
						rs.getString("token"),
						rs.getBoolean("active"), 
						rs.getString("origin")));
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
		return usersList;
	}
	
	public void add(Connection conn) {
		PreparedStatement st = null;
		try{
			String sql = " insert into zutenti (idusr, nome, cogn, mail, active, origin, foto, language, style, logo, tipo, db, sysinf, foto2)" + 
					" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			st = conn.prepareStatement(sql);
			st.setString(1, this.idusr);
			st.setString(2, this.nome);
			st.setString(3, this.cogn);
		    st.setString(4, this.mail);
		    st.setBoolean(5, this.active);
		    st.setString(6, this.origin);
		    st.setString(7, "default-icon-user.png");
		    st.setString(8, this.language);
		    st.setString(9, "bg-info");
		    st.setString(10, "logo.png");
		    st.setString(11, "3");
		    st.setString(12, "smarthr");
		    st.setString(13, "SmartHR");
		    st.setString(14, foto);
		    
		    
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
	
	public void delete(Connection conn) {
		PreparedStatement st = null;
		try{
		  String sql = "delete from zutenti where idusr = ?";
	      st = conn.prepareStatement(sql);
	      st.setString(1, this.idusr);
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
	
	
}
