package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.User;

public class JDBCUserDAOImpl implements UserDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUserDAOImpl.class.getName());
	
	@Override
	public User get(long id) {
		if (conn == null) return null;
		
		User user = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.User WHERE id ="+id);			 
			if (!rs.next()) return null; 
			user  = new User();	 
			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			logger.info("fetching User by id: "+id+" -> "+user.getId()+" "+user.getName()+" "+user.getEmail()+" "+user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public User get(String name) {
		if (conn == null) return null;
		
		User user = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Public.User WHERE name ='"+name+"'");			 
			if (!rs.next()) return null; 
			user  = new User();	 
			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			logger.info("fetching User by name: "+ name + " -> "+ user.getId()+" "+user.getName()+" "+user.getEmail()+" "+user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	
	public List<User> getAll() {

		if (conn == null) return null;
		
		ArrayList<User> users = new ArrayList<User>();
		try {
			Statement stmt;
			ResultSet rs;
			synchronized(conn){
			  stmt = conn.createStatement();
			  rs = stmt.executeQuery("SELECT * FROM Public.User");
			}
			while ( rs.next() ) {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				
				
				users.add(user);
				logger.info("fetching users: "+user.getId()+" "+user.getName()+" "+user.getEmail()+" "+user.getPassword());
								
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
	

	@Override
	public long add(User user) {
		long id=-1;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO User (name,email,password) VALUES('"
									+user.getName()+"','"
									+user.getEmail()+"','"
									+user.getPassword()+"')",Statement.RETURN_GENERATED_KEYS);
				
				ResultSet genKeys = stmt.getGeneratedKeys();
				
				if (genKeys.next())
				    id = genKeys.getInt(1);
				
				
				logger.info("creating User("+id+"): "+user.getId()+" "+user.getName()+" "+user.getEmail()+" "+user.getPassword());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public boolean save(User user) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE User SET name='"+user.getName()
									+"', email='"+user.getEmail()
									+"', password='"+user.getPassword()
									+"' WHERE id = "+user.getId());
				logger.info("updating User: "+user.getId()+" "+user.getName()+" "+user.getEmail()+" "+user.getPassword());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return done;

	}

	@Override
	public boolean delete(long id) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM User WHERE id ="+id);
				logger.info("deleting User: "+id);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}

	
}
