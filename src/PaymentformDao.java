import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Entities.Paymentform;

public class PaymentformDao {

	public Paymentform getPaymentform(String code) {
	    Connection connection = ConnectionFactory.getConnection();
	        try {
	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM paymentform WHERE code='" + code +"'");
	            if(rs.next())
	            {
	            	Paymentform paymentform = new Paymentform();
	            	paymentform.setId(rs.getLong("id"));
	            	paymentform.setCode(rs.getString("code"));
	            	paymentform.setName(rs.getString("name"));
	            	stmt.close();
	            	//connection.close();
	            	return paymentform;
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    return null;
	}
	
	public ArrayList<String> getPaymentforms(){
		ArrayList<String> codes = null;
	    Connection connection = ConnectionFactory.getConnection();
	        try {
	        	codes = new ArrayList<String>();
	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT distinct code FROM paymentform");
	            while(rs.next())
	            {
	            	codes.add(rs.getString("code"));
	            }
	            stmt.close();
                return codes;
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    return codes;
	}
	
	public Long getMaxId(){
		Long id = 0L;
	    Connection connection = ConnectionFactory.getConnection();
	        try {
	        	Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT max(id) maxid FROM paymentform");
	            if(rs.next())
	            {
	            	id = rs.getLong("maxid");
	            }
	            stmt.close();
                return id;
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    return id;
	}
	
	public boolean insertPaymentforms(ArrayList<Paymentform> paymentforms) {

	    Connection connection = ConnectionFactory.getConnection();
	    try {
	    	PreparedStatement ps = connection.prepareStatement("INSERT INTO paymentform VALUES (?, ?, ?, ?, ?, ?, ?)");
	    	
	    	final int batchSize = 1000;
	    	int count = 0;

	    	for (Paymentform paymentform : paymentforms) {
		        ps.setLong(1, paymentform.getId());
		        ps.setTimestamp(2, null);//updatetimestamp
		        ps.setTimestamp(3, null);//deletetimestamp
		        ps.setTimestamp(4, null);//inclusiontimestamp
		        ps.setLong(5, 0);
		        ps.setString(6, paymentform.getCode());
		        ps.setString(7, paymentform.getName());
	    		ps.addBatch();
	    		
	    		if(++count % batchSize == 0) {
	    			ps.executeBatch();
	    		}
	    	}
	    	int affectedRecords[] =ps.executeBatch(); // insert remaining records
	    	ps.close();
	    	//connection.close();
	    	
	    	
	        //int i = ps.executeUpdate();
	      //if(i == 1) {
	        return true;
	      //}
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
}
