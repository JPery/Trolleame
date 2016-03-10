package dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import controller.DBConnection;
import dao.CommentDAO;
import model.Comment;
import dao.JDBCCommentDAOImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestCommentDAO {

	static DBConnection dbConn;
	static CommentDAO commentDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConnection();
		conn = dbConn.create();
	    commentDAO = new JDBCCommentDAOImpl();
		commentDAO.setConnection(conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		dbConn.destroy(conn);
	}

	@Before
	public void setUpBeforeMethod() throws Exception {
	
	}

	@Test
	public void test1BaseData() {
		
		List<Comment> commentList = commentDAO.getAll();
		
		long id = 0;
		for(Comment comment: commentList){
			assertEquals(comment.getId(),id);			
			id++;
		}
	}
	
	@Test
	public void test2BaseDataByOwner() {
		
		List<Comment> commentList = commentDAO.getAllByOwner(0);
		for(Comment comment: commentList)			
			assertEquals(comment.getOwner(),0);			
	}
	
	@Test
	public void test3Add(){
		Comment comment01 = new Comment();
		comment01.setOwner(1);
		comment01.setNews(1);
		comment01.setText("Ya no nos sorprende esta noticia");
		
		
		long id= commentDAO.add(comment01);
		Comment comment02 = commentDAO.get(id);
		
		
		assertEquals(comment01.getText(),comment02.getText());
		assertEquals(comment01.getOwner(),comment02.getOwner());
	}
	
	@Test
	public void test4BaseDataByNews() {
		
		List<Comment> commentList = commentDAO.getAllByNews(1);
		for(Comment comment: commentList)			
			assertEquals(comment.getNews(),1);			
	}
	
	
	@Test
	public void test5Modify(){
		
		long commentid = 0;
		
		Comment comment01 = new Comment();
		comment01.setId(commentid);
		comment01.setText("Actualizado: Ha habido cada tío raro con ansias de aparentar sin parar...");
		commentDAO.save(comment01);
		
		List<Comment> commentList = commentDAO.getAll();
		Comment comment02 = commentList.get((int) commentid);
		assertEquals(comment01.getText(),comment02.getText());
	}
	
	@Test
	public void test6Delete(){
		 
		 long id = 5;
		
		 commentDAO.delete(id);
		 
		 List<Comment> commentList = commentDAO.getAll();
		 for(model.Comment comment: commentList)
				assertNotEquals(comment.getId(),id);
		 
	}

}
