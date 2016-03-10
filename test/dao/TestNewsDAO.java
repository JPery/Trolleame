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
import dao.NewsDAO;
import model.News;
import dao.JDBCNewsDAOImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestNewsDAO {

	static DBConnection dbConn;
	static NewsDAO newsDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConnection();
		conn = dbConn.create();
	    newsDAO = new JDBCNewsDAOImpl();
		newsDAO.setConnection(conn);
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
		
		List<News> newsList = newsDAO.getAll();
		
		long id = 0;
		for(News news: newsList){
			assertEquals(news.getId(),id);			
			id++;
		}
	}
	
	@Test
	public void test2BaseDataByOwner() {
		
		List<News> newsList = newsDAO.getAllByOwner(0);
		for(News news: newsList)			
			assertEquals(news.getOwner(),0);			
	}
	
	@Test
	public void test3Add(){
		News news01 = new News();
		news01.setOwner(1);
		news01.setTitle("Varios pasajeros de un tranvía de Manchester defienden a una pareja española de un xenófobo");
		news01.setText("Un hombre los increpó por hablar en español y los invitó a volver a su país. Varios pasajeros se levantaron de sus asientos y le recriminaron su actitud xenófoba. El incidente fue grabado por un joven peruano.");
		news01.setUrl("http://www.20minutos.es/noticia/2691637/0/defienden-pareja/espanola-ataque/xenofobo-manchester/");
		news01.setCategory("actualidad");
		
		long id= newsDAO.add(news01);
		News news02 = newsDAO.get(id);
		
		
		assertEquals(news01.getTitle(),news02.getTitle());
		assertEquals(news01.getOwner(),news02.getOwner());
	}
	
	@Test
	public void test4BaseDataByCategory() {
		
		List<News> newsList = newsDAO.getAllByCategory("actualidad");
		for(News news: newsList)			
			assertEquals(news.getCategory(),"actualidad");			
	}
	
	
	@Test
	public void test5Modify(){
		
		long newsid = 0;
		
		News news01 = new model.News();
		news01.setId(newsid);
		news01.setTitle("Actualizado: Emperador de EEUU o Rey de Alemania: personajes que se atribuyeron un cargo rimbombante por la cara");
		newsDAO.save(news01);
		
		List<News> newsList = newsDAO.getAll();
		News news02 = newsList.get((int) newsid);
		assertEquals(news01.getTitle(),news02.getTitle());
	}
	
	@Test
	public void test6Delete(){
		 
		 long id = 3;
		
		 newsDAO.delete(id);
		 
		 List<News> newsList = newsDAO.getAll();
		 for(model.News news: newsList)
				assertNotEquals(news.getId(),id);
		 
	}

}
