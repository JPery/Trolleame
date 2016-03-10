package dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;


import model.User;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import controller.DBConnection;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestUserDAO {

	static DBConnection dbConn;
	static UserDAO userDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConnection();
		conn = dbConn.create();
	    userDAO = new JDBCUserDAOImpl();
		userDAO.setConnection(conn);
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
		
		User user01 = userDAO.get(0);
		assertEquals(user01.getId(),0);
		assertEquals(user01.getName(),"vader");
		assertEquals(user01.getPassword(),"anakin");
		assertEquals(user01.getEmail(),"darth.vader@darksideoftheforce.org");
		
		User user02 = userDAO.get(1);
		assertEquals(user02.getId(),1);
		assertEquals(user02.getName(),"doe");
		assertEquals(user02.getPassword(),"john");
		assertEquals(user02.getEmail(),"jdoe@nothing.es");
		
		User user03 = userDAO.get("vader");
		assertEquals(user03.getId(),0);
		assertEquals(user03.getName(),"vader");
		assertEquals(user03.getPassword(),"anakin");
		assertEquals(user03.getEmail(),"darth.vader@darksideoftheforce.org");
		
		userDAO.getAll();
		
		
		
	}
	
	
	@Test
	public void test2Add(){
		User user01 = new User();
		user01.setName("newUser");
		user01.setEmail("newUser@unex.es");
		user01.setPassword("12345");
		userDAO.add(user01);
		
		User user02 = userDAO.get(2);
		assertEquals(user01.getName(),user02.getName());
		assertEquals(user01.getEmail(),user02.getEmail());
		assertEquals(user01.getPassword(),user02.getPassword());
		
		userDAO.getAll();
	}
	
	@Test
	public void test3Modify(){
		User user01 = new User();
		user01.setId(2);
		user01.setName("newUser - cambiado");
		userDAO.save(user01);
		
		User user02 = userDAO.get(2);		
		assertEquals(user01.getName(),user02.getName());
		
		userDAO.getAll();
	}
	
	@Test
	public void test4Delete(){
		 userDAO.delete(2);
		 
		 User user02 = userDAO.get(2);
 		 assertEquals(null, user02);
 		 
 		userDAO.getAll();
	}

}
