package dao;

import java.sql.Connection;
import java.util.List;

import model.News;



public interface NewsDAO {
	public List<News> getAll();
	public List<News>  getAllByOwner(long owner);
	public List<News> getAllByCategory(String category);
	public List<News> getAllBySearch(String search);
	public News get(long id);	
	public long add(News news);
	public boolean save(News news);
	public boolean delete(long id);
	public void setConnection(Connection conn);
	public List<News> getAllByOrder(String category, String order);
}
