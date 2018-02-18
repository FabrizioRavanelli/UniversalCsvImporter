import java.sql.*;
import java.util.ArrayList;

import Entities.*;


public class CustomerDao {
	
	public boolean insertCustomers(ArrayList<Customer> customers) {
	    Connection connection = ConnectionFactory.getConnection();
	    try {
	    	PreparedStatement ps = connection.prepareStatement("INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	    	
	    	final int batchSize = 1000;
	    	int count = 0;

	    	for (Customer customer : customers) {
		        ps.setLong(1, customer.getId());
		        ps.setTimestamp(2, null);//updatetimestamp
		        ps.setTimestamp(3, null);//deletetimestamp
		        ps.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));//inclusiontimestamp
		        ps.setLong(5, customer.getOptlock());
		        ps.setString(6, customer.getNeighborhood());
		        ps.setString(7, customer.getZipCode());
		        ps.setString(8, customer.getCode());
		        ps.setString(9, customer.getGovid());
		        ps.setString(10, customer.getEmail());
		        ps.setString(11, customer.getStreet());
		        ps.setString(12, customer.getLocalid());
		        ps.setString(13, customer.getCity());
		        ps.setString(14, customer.getCommercialname());
		        ps.setString(15, customer.getContact());
		        ps.setString(16, customer.getStreetnumber());
		        ps.setString(17, customer.getNote());
		        ps.setString(18, customer.getName());
		        ps.setString(19, customer.getPhone());
		        ps.setString(20, customer.getState());
		        ps.setLong(21, customer.getPaymentformId());
		        ps.setLong(22, customer.getPaymentplanId());
	    		ps.addBatch();
	    		
	    		if(++count % batchSize == 0) {
	    			ps.executeBatch();
	    		}
	    	}
	    	int affectedRecords[] = ps.executeBatch(); // insert remaining records
	    	ps.close();
	        return true;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}

	public boolean updateCustomers(ArrayList<Customer> customers) {

	    Connection connection = ConnectionFactory.getConnection();
	    try {
	    	PreparedStatement ps = connection.prepareStatement("UPDATE customer SET updatetimestamp=?, neighborhood=?, zipcode=?, govid=?, email=?, street=?, localid=?, city=?, commercialname=?, contact=?, streetnumber=?, note=?, name=?, phone=?, state=?, paymentform_id=?, paymentplan_id=? WHERE code=?");
	    	
	    	final int batchSize = 1000;
	    	int count = 0;

	    	for (Customer customer : customers) {
		        ps.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));//updatetimestamp
		        ps.setString(2, customer.getNeighborhood());
		        ps.setString(3, customer.getZipCode());
		        ps.setString(4, customer.getGovid());
		        ps.setString(5, customer.getEmail());
		        ps.setString(6, customer.getStreet());
		        ps.setString(7, customer.getLocalid());
		        ps.setString(8, customer.getCity());
		        ps.setString(9, customer.getCommercialname());
		        ps.setString(10, customer.getContact());
		        ps.setString(11, customer.getStreetnumber());
		        ps.setString(12, customer.getNote());
		        ps.setString(13, customer.getName());
		        ps.setString(14, customer.getPhone());
		        ps.setString(15, customer.getState());
		        ps.setLong(16, customer.getPaymentformId());
		        ps.setLong(17, customer.getPaymentplanId());
	    		ps.setString(18, customer.getCode());
		        ps.addBatch();
	    		
	    		if(++count % batchSize == 0) {
	    			ps.executeBatch();
	    		}
	    	}
	    	int affectedRecords[] = ps.executeBatch(); // insert remaining records
	    	ps.close();
	        return true;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}

	public boolean deleteCustomers(ArrayList<String> codes) {
	    Connection connection = ConnectionFactory.getConnection();
	    try {
	    	PreparedStatement ps = connection.prepareStatement("UPDATE customer SET deletetimestamp=? WHERE code=? and deletetimestamp is null");
	    	
	    	final int batchSize = 1000;
	    	int count = 0;

	    	for (String code : codes) {
		        ps.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));//deletetimestamp
	    		ps.setString(2, code);
		        ps.addBatch();
	    		
	    		if(++count % batchSize == 0) {
	    			ps.executeBatch();
	    		}
	    	}
	    	int affectedRecords[] = ps.executeBatch(); // insert remaining records
	    	ps.close();
	        return true;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}

	public ArrayList<String> getCustomerCodes1() {
		ArrayList<String> codes = null;
	    Connection connection = ConnectionFactory.getConnection();
	        try {
	        	codes = new ArrayList<String>();
	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT distinct code FROM customer");
	            if(rs.next())
	            {
	            	codes.add(rs.getString("code"));
	            }
                return codes;

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    return codes;
	}
	
	public ArrayList<String> getCustomerCodes() {
		ArrayList<String> codes = null;
	    Connection connection = ConnectionFactory.getConnection();
	        try {
	        	codes = new ArrayList<String>();
	            Statement stmt = connection.createStatement();
	            boolean finish = false;
	            int index = 0;
	            int limit = 1000;
	            while(!finish)
	            {
	            	finish = true;
	            	ResultSet rs = stmt.executeQuery("SELECT code FROM customer LIMIT "+ limit +" OFFSET " + limit * index);
	            	while (rs.next()) 
		            {
		            	finish = false;
	            		codes.add(rs.getString("code"));
		            }
		            index++;
	            }

	            stmt.close();
                return codes;

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    return codes;
	}
	
	
	public Customer getCustomer(String code) {
		Connection connection = ConnectionFactory.getConnection();
	        try {
	        	Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT a.id, a.updatetimestamp, a.deletetimestamp, a.inclusiontimestamp, a.optlock, a.neighborhood, a.zipcode, a.code, a.govid, a.email, a.street, a.localid, a.city, a.commercialname, a.contact, a.streetnumber, a.note, a.name, a.phone, a.state, a.paymentform_id, a.paymentplan_id, b.code paymentform_code, c.code paymentplan_code FROM public.customer a join paymentform b on a.paymentform_id=b.id join paymentplan c on a.paymentplan_id=c.id where a.code =  '"+ code +"'");
	            if(rs.next())
	            {
	            	//codes.add(rs.getString("code"));
	            	Customer customer = new Customer();
	            	customer.setId(rs.getLong("id"));
	            	customer.setCode(rs.getString("code"));
	            	customer.setName(rs.getString("name"));

	            	customer.setUpdatetimestamp(rs.getTimestamp("updatetimestamp"));
	            	customer.setDeletetimestamp(rs.getTimestamp("deletetimestamp"));
	            	customer.setInclusiontimestamp(rs.getTimestamp("inclusiontimestamp"));
	            	customer.setOptlock(rs.getLong("optlock"));
	            	customer.setNeighborhood(rs.getString("neighborhood"));
	            	customer.setZipCode(rs.getString("zipcode"));
	            	customer.setGovid(rs.getString("govid"));
	            	customer.setEmail(rs.getString("email"));
	            	customer.setStreet(rs.getString("street"));
	            	customer.setLocalid(rs.getString("localid"));
	            	customer.setCity(rs.getString("city"));
	            	customer.setCommercialname(rs.getString("commercialname"));
	            	customer.setContact(rs.getString("contact"));
	            	customer.setStreetnumber(rs.getString("streetnumber"));
	            	customer.setNote(rs.getString("note"));
	            	customer.setPhone(rs.getString("phone"));
	            	customer.setState(rs.getString("state"));
	            	customer.setPaymentformId(rs.getLong("paymentform_id"));
	            	customer.setPaymentplanId(rs.getLong("paymentplan_id"));
	            	customer.setPaymentformCode(rs.getString("paymentform_code"));
	            	customer.setPaymentplanCode(rs.getString("paymentplan_code"));

	            	stmt.close();
	            	//connection.close();
	            	return customer;
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    return null;
	}
	
	public Long getMaxId(){
		Long id = 0L;
	    Connection connection = ConnectionFactory.getConnection();
	        try {
	        	Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT max(id) maxid FROM customer");
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

}
