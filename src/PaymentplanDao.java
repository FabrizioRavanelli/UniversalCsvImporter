import java.sql.*;
import java.util.ArrayList;

import Entities.*;

public class PaymentplanDao {
	public Paymentplan getPaymentplan(String code) {
	    Connection connection = ConnectionFactory.getConnection();
	        try {
	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM paymentplan WHERE code='" + code +"'");
	            if(rs.next())
	            {
	            	Paymentplan paymentplan= new Paymentplan();
	            	paymentplan.setId(rs.getLong("id"));
	            	paymentplan.setCode(rs.getString("code"));
	            	paymentplan.setName(rs.getString("name"));
	            	stmt.close();
	            	return paymentplan;
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    return null;
	}
	
	public ArrayList<String> getPaymentplans(){
		ArrayList<String> codes = null;
	    Connection connection = ConnectionFactory.getConnection();
	        try {
	        	codes = new ArrayList<String>();
	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT distinct code FROM paymentplan");
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
	            ResultSet rs = stmt.executeQuery("SELECT max(id) maxid FROM paymentplan");
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
	public boolean insertPaymentplans(ArrayList<Paymentplan> paymentplans) {

	    Connection connection = ConnectionFactory.getConnection();
	    try {
	    	PreparedStatement ps = connection.prepareStatement("INSERT INTO paymentplan VALUES (?, ?, ?, ?, ?, ?, ?)");
	    	
	    	final int batchSize = 1000;
	    	int count = 0;

	    	for (Paymentplan paymentplan : paymentplans) {
		        ps.setLong(1, paymentplan.getId());
		        ps.setTimestamp(2, null);//updatetimestamp
		        ps.setTimestamp(3, null);//deletetimestamp
		        ps.setTimestamp(4, null);//inclusiontimestamp
		        ps.setLong(5, 0);
		        ps.setString(6, paymentplan.getCode());
		        ps.setString(7, paymentplan.getName());
	    		ps.addBatch();
	    		
	    		if(++count % batchSize == 0) {
	    			ps.executeBatch();
	    		}
	    	}
	    	int affectedRecords[] =ps.executeBatch(); // insert remaining records
	    	ps.close();
	    	//connection.close();
	    	
	    	
	      //  int i = ps.executeUpdate();
	      //if(i == 1) {
	        return true;
	      //}
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	/*
	public boolean insertPaymentplan(Paymentplan paymentplan) {
		Connection connection = ConnectionFactory.getConnection();
	    try {
	    	PreparedStatement ps = connection.prepareStatement("INSERT INTO paymentplan VALUES (?, ?, ?, ?, ?, ?, ?)");
	        ps.setLong(1, paymentplan.getId());
	        ps.setTimestamp(2, null);//updatetimestamp
	        ps.setTimestamp(3, null);//deletetimestamp
	        ps.setTimestamp(4, null);//inclusiontimestamp
	        ps.setLong(5, 0);
	        ps.setString(6, paymentplan.getCode());
	        ps.setString(7, paymentplan.getName());
	        int i = ps.executeUpdate();
	    	ps.close();
	    	//connection.close();
	      if(i == 1) {
	        return true;
	      }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
*/
}
