package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Comment;



public class JDBCCommentDAOImpl implements CommentDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCCommentDAOImpl.class.getName());

	@Override
	public List<Comment> getAll() {

		if (conn == null) return null;
						
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comment");
						
			while ( rs.next() ) {
				Comment comment = new Comment();
				comment.setId(rs.getLong("id"));
				comment.setOwner(rs.getLong("owner"));
				comment.setNews(rs.getLong("news"));
				comment.setDateStamp(rs.getDate("datestamp"));
				comment.setTimeStamp(rs.getTime("timestamp"));
				comment.setText(rs.getString("text"));
				comment.setLikes(rs.getInt("likes"));
				
				commentList.add(comment);
				logger.info("fetching commentList: "+comment.getId()+" "+comment.getOwner()+" "+comment.getNews()+" "
				+comment.getDateStamp()+" "+comment.getTimeStamp()+" "+comment.getText() +" "+comment.getLikes());
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commentList;
	}

	@Override
	public List<Comment> getAllByOwner(long owner) {
		
		if (conn == null) return null;
						
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comment WHERE owner="+owner);

			while ( rs.next() ) {
				Comment comment = new Comment();
				comment.setId(rs.getLong("id"));
				comment.setOwner(rs.getLong("owner"));
				comment.setNews(rs.getLong("news"));
				comment.setDateStamp(rs.getDate("datestamp"));
				comment.setTimeStamp(rs.getTime("timestamp"));
				comment.setText(rs.getString("text"));
				comment.setLikes(rs.getInt("likes"));
				commentList.add(comment);
				
				logger.info("fetching commentList by owner("+owner+": "+comment.getId()+" "+comment.getOwner()+" "+comment.getNews()+" "
						+comment.getDateStamp()+" "+comment.getTimeStamp()+" "+comment.getText() +" "+comment.getLikes());
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commentList;
	}
	
	@Override
	public List<Comment> getAllByNews(long news) {
		
		if (conn == null) return null;
						
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comment WHERE news ='"+news+"'");

			while ( rs.next() ) {
				Comment comment = new Comment();
				comment.setId(rs.getLong("id"));
				comment.setOwner(rs.getLong("owner"));
				comment.setNews(rs.getLong("news"));
				comment.setDateStamp(rs.getDate("datestamp"));
				comment.setTimeStamp(rs.getTime("timestamp"));
				comment.setText(rs.getString("text"));
				comment.setLikes(rs.getInt("likes"));
				commentList.add(comment);
				
				logger.info("fetching commentList by news("+news+": "+comment.getId()+" "+comment.getOwner()+" "+comment.getNews()+" "
						+comment.getDateStamp()+" "+comment.getTimeStamp()+" "+comment.getText() +" "+comment.getLikes());
			
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commentList;
	}
	
	@Override
	public Comment get(long id) {
		if (conn == null) return null;
		
		Comment comment = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comment WHERE id ="+id);			 
			if (!rs.next()) return null;
			comment= new Comment();
			comment.setId(rs.getLong("id"));
			comment.setOwner(rs.getLong("owner"));
			comment.setNews(rs.getLong("news"));
			comment.setDateStamp(rs.getDate("datestamp"));
			comment.setTimeStamp(rs.getTime("timestamp"));
			comment.setText(rs.getString("text"));
			comment.setLikes(rs.getInt("likes"));
						
			logger.info("fetching comment by id("+id+": "+comment.getId()+" "+comment.getOwner()+" "+comment.getNews()+" "
					+comment.getDateStamp()+" "+comment.getTimeStamp()+" "+comment.getText() +" "+comment.getLikes());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comment;
	}
	
	

	@Override
	public long add(Comment comment) {
		long id=-1;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Comment (owner,news,datestamp,timestamp,text) VALUES('"+
									comment.getOwner()+"','"+
									comment.getNews()+"','"+
									new java.sql.Date(comment.getDateStamp().getTime())+"','"+
									comment.getTimeStamp()+"','"+
									comment.getText()+"')",Statement.RETURN_GENERATED_KEYS);
				ResultSet genKeys = stmt.getGeneratedKeys();
				
				if (genKeys.next())
				    id = genKeys.getInt(1);		
									
				logger.info("creating Comment:("+id+": "+comment.getOwner()+" "+comment.getNews()+" "+comment.getDateStamp()+" "+comment.getTimeStamp()
				+" "+comment.getText());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}

	@Override
	public boolean save(Comment comment) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE Comment SET text='"+
									comment.getText()+"', likes='"+
									comment.getLikes()+"' WHERE id = "+comment.getId());
				logger.info("updating Comment: "+comment.getId()+" "+comment.getOwner()+" "+comment.getNews()
				+" "+comment.getText()+" "+comment.getLikes());
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
				stmt.executeUpdate("DELETE FROM Comment WHERE id ="+id);
				logger.info("deleting Comment: "+id);
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
