// Descomentar si se se va a usar un Listener para iniciar la conexi�n:
//package listener;
//Comentar si se va a usar un Listner para iniciar la conexi�n:
package listener;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.logging.Logger;


// Descomentar si se se va a usar un Listener para iniciar la conexi�n:
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;


//Descomentar si se se va a usar un Listener para iniciar la conexi�n:
@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

//Comentar si se va a usar un Listener para iniciar la conexi�n:
//public class DBConnection {
	
	private static final Logger logger = Logger.getLogger(ServletContextListener.class.getName());
	
	//Descomentar si se se va a usar un Listener para iniciar la conexi�n:
	public void contextInitialized(ServletContextEvent event) {

	//Comentar si se va a usar un Listner para iniciar la conexi�n:
	//public Connection create(){
		
		logger.info("Creating DB");
		Connection conn = null;
		
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// Descomentar las siguientes l�neas para su uso en nuestra aplicaci�n web y comentar para pruebas solo del DAO:
			ServletContext sc = event.getServletContext();
			
			conn = DriverManager.getConnection("jdbc:hsqldb:file:" + sc.getRealPath("WEB-INF/news/"),
													"sa", // username
													""); //password
			//Comentar las siguientes l�nea para  su uso en nuestra aplicaci�n web y comentar para pruebas solo del DAO:
			//conn = DriverManager.getConnection("jdbc:hsqldb:mem:/localhost/news",
			//		"sa",                     // username
			//		"");					  //password

			Statement stmt = conn.createStatement();
			
			//Delete db schema
			//stmt.executeUpdate("DROP TABLE IF EXISTS Comment; DROP TABLE IF EXISTS News;DROP TABLE IF EXISTS User" );
			//Init db schema
			try{
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS User (ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0) NOT NULL PRIMARY KEY,"
						+ "NAME VARCHAR(40) NOT NULL, "
						+ "PASSWORD VARCHAR(64) NOT NULL, "
						+ "IMG VARCHAR(120), "
						+ "EMAIL VARCHAR(50) NOT NULL)" );
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS News (ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0) NOT NULL PRIMARY KEY, "
						+ "OWNER INTEGER NOT NULL, "
						+ "DATESTAMP DATE default now,"
						+ "TIMESTAMP TIME default '00:00:00',"
						+ "TITLE VARCHAR(150) NOT NULL, "
						+ "TEXT  VARCHAR(400) NOT NULL, "
						+ "URL  VARCHAR(150) NOT NULL, "
						+ "CATEGORY VARCHAR(20) NOT NULL, "
						+ "IMG VARCHAR(120), "
						+ "LIKES INTEGER default 0, "
						+ "HITS INTEGER default 0, "
						+ "FOREIGN KEY (OWNER) REFERENCES USER(ID) ON DELETE CASCADE)" );
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Comment (ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0) NOT NULL PRIMARY KEY, "
						+ "OWNER INTEGER NOT NULL, "
						+ "NEWS INTEGER NOT NULL, "
						+ "DATESTAMP DATE default now,"
						+ "TIMESTAMP TIME default '00:00:00',"
						+ "TEXT  VARCHAR(400) NOT NULL, "
						+ "LIKES INTEGER default 0, "
						+ "FOREIGN KEY (OWNER) REFERENCES USER(ID) ON DELETE CASCADE," 
						+ "FOREIGN KEY (NEWS) REFERENCES NEWS(ID) ON DELETE CASCADE)" );
				
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			//init data
			stmt.executeUpdate("INSERT INTO User (name, password, email, img) SELECT 'vader', '370b126df07859afa569cd82582bc43dfb2ce3ba8069dbbcbef6b7215b7a76c6', 'darth.vader@darksideoftheforce.org', '/Trolleame/img/users/user0.jpg' FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from User where name='vader')  LIMIT 1");
			stmt.executeUpdate("INSERT INTO User (name, password, email, img) SELECT 'doe', '96d9632f363564cc3032521409cf22a852f2032eec099ed5967c0d000cec607a', 'jdoe@nothing.es', '/Trolleame/img/users/user1.jpg' FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from User where name='doe')  LIMIT 1");
			
			
			
			stmt.executeUpdate("INSERT INTO News (owner,title,text,url,category,img) "
					+ "SELECT 0,'Emperador de EEUU o Rey de Alemania: personajes que se atribuyeron un cargo rimbombante por la cara',"
					+ "'De toda la vida ha habido gente a la que le han dado un cargo de autoridad y lo ha ejercido con orgullo. El poder atrae, y por eso también hay quienes, a pesar de no haber recibido cargo alguno, se han autoproclamado sultanes, gobernadores o emperadores de la forma más extravagante posible.',"
					+ "'http://www.cookingideas.es/by-the-face-20160307.html','ocio', '/Trolleame/img/news/news0.jpg' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from News where id=0)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO News (owner,title,text,url,category,img) "
					+ "SELECT 1,'Un dron de Hacienda descubre 2.500 viviendas ilegales en Menorca',"
					+ "'Un dron de Hacienda descubre 2.500 viviendas irregulares en Menorca, isla verde que es Reserva de la Biosfera desde 1993. La cifra no incluye las posibles irregularidades urbanísticas en los pueblos de Sant Lluís, Maé y Ciutadella, ya que o no se dispone de datos o aún se están procesando.',"
					+ "'http://www.cronicaglobal.com/es/notices/2016/03/un-dron-de-hacienda-descubre-2.500-viviendas-ilegales-en-menorca-34556.php','actualidad', '/Trolleame/img/news/news1.jpg'"
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from News where id=1)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO News (owner,title,text,url,category,img) "
					+ "SELECT 0,'Darth Maul - Aprendiz (Un Fan-Film de Star Wars)',"
					+ "'Como la mayoría de los fans de Star Wars, el director de cine con sede en Alemania Shawn Bu pensó que Darth Maul, el malo en Star Wars: Episodio I - La Amenaza Fantasma, merecía más tiempo en pantalla. Para ayudar a solucionar este problema, Shawn y su equipo pasaron casi dos años la creación de este cortometraje.',"
					+ "'https://www.youtube.com/watch?v=Djo_91jN3Pk','ocio','/Trolleame/img/news/news2.jpg' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from News where id=2)  LIMIT 1");
						
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 1,0,'Ha habido cada tío raro con ansias de aparentar' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=0)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 0,0,'Ya te digo' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=1)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 0,1,'Y los demás pagando todos los permisos...' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=2)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 1,1,'Hay gente con mucha cara' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=3)  LIMIT 1");
			stmt.executeUpdate("INSERT INTO Comment (owner,news,text) "
					+ "SELECT 0,1,'Y tanto' "
					+ "FROM INFORMATION_SCHEMA.TABLES WHERE not exists (select  * from Comment where id=4)  LIMIT 1");
			
			// Descomentar las siguientes l�neas para su uso en nuestra aplicaci�n web y comentar para pruebas solo del DAO:
			sc.setAttribute("dbConn", conn);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                     
		
		logger.info("DB created");
		
		//return conn;
	}
	
	//Descomentar si se se va a usar un Listener para destruir la conexi�n:
    public void contextDestroyed(ServletContextEvent arg0)  { 
	//Comentar si se va a usar un Listener para destruir la conexi�n:	 
	//public void destroy(Connection conn){
		
		logger.info("Destroying DB");
		try {
			logger.info("DB shutdown start");
			//Descomentar si se se va a usar un Listener para destruir la conexi�n:
	   		ServletContext sc = arg0.getServletContext();
	   		Connection conn = (Connection) sc.getAttribute("dbConn");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("SHUTDOWN");
			conn.close();
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				logger.info("DB deregistering drivers");
				Driver driver = drivers.nextElement();
				try {
					DriverManager.deregisterDriver(driver);
					logger.info(String.format("deregistering jdbc driver: %s", driver));
				} catch (SQLException e) {
					logger.severe(String.format("Error deregistering driver %s %s", driver, e));
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("DB destroyed");
	}

	
   
	
	

}
