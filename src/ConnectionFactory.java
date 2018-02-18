import org.postgresql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Connect to Database
 */
public class ConnectionFactory {
    public static final String URL = "jdbc:postgresql://192.168.0.130/postgres";
    public static final String USER = "postgres";
    public static final String PASS = "admin";
    private static Connection _connection = null;
    /**
     * Get a connection to database
     * @return Connection object
     */
    public static Connection getConnection()
    {
      try {
    	  if(_connection == null) {
              DriverManager.registerDriver(new Driver());
              _connection = DriverManager.getConnection(URL, USER, PASS);
    	  }
          return _connection;
      } catch (SQLException ex) {
          throw new RuntimeException("Error connecting to the database", ex);
      }
    }
}