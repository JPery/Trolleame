package dao;

import java.sql.Connection;
import java.util.List;

import model.Comment;




public interface CommentDAO {
	public List<Comment> getAll();
	public List<Comment>  getAllByOwner(long owner);
	public List<Comment> getAllByNews(long news);
	public Comment get(long id);	
	public long add(Comment comment);
	public boolean save(Comment comment);
	public boolean delete(long id);
	public void setConnection(Connection conn);
}
