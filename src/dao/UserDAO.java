package dao;

import java.sql.Connection;
import java.util.List;

import model.User;

public interface UserDAO {
	public User get(long id);
	public User get(String name);
	public List<User> getAll();
	public long add(User User);
	public boolean save(User User);
	public boolean delete(long id);
	public void setConnection(Connection conn);
}
