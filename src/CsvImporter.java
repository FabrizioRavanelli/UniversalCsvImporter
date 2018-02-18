import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import Entities.*;

public class CsvImporter {
	
	private CustomerDao _customerDao;
	private PaymentformDao _paymentformDao;
	private PaymentplanDao _paymentplanDao;
	
	public CsvImporter(CustomerDao customerDao, PaymentformDao paymentformDao, PaymentplanDao paymentplanDao) {
		_customerDao = customerDao;
		_paymentformDao = paymentformDao;
		_paymentplanDao = paymentplanDao;
	}
	
	public void InitialBulk(String csvFile) {
		Long index = 1L;
		ArrayList<String> paymentformCodes = new ArrayList<String>();
		ArrayList<String> paymentplanCodes = new ArrayList<String>();
		
		ArrayList<String[]> lines = ReadFile(csvFile);
		
		for (String[] line: lines) {
			if (!paymentformCodes.contains(line[14])){
				paymentformCodes.add(line[14]);
			}
			if (!paymentplanCodes.contains(line[16])){
				paymentplanCodes.add(line[16]);
			}
		}
		
		//first: paymentform
		ArrayList<Paymentform> paymentforms = new ArrayList<>();
		for (String paymentformCode: paymentformCodes) {
			Paymentform paymentform = ToPaymentform(paymentformCode);
			paymentform.setId(index);
			paymentforms.add(paymentform);
			index++;
		}
		_paymentformDao.insertPaymentforms(paymentforms);
		
		//second: paymentplan
		index = 1L;
		ArrayList<Paymentplan> paymentplans = new ArrayList<>();
		for (String paymentplanCode: paymentplanCodes) {
			Paymentplan paymentplan = ToPaymentplan(paymentplanCode);
			paymentplan.setId(index);
			paymentplans.add(paymentplan);
			index++;
		}
		_paymentplanDao.insertPaymentplans(paymentplans);
		
		//third: customers
		index = 1L;
		ArrayList<Customer> customers = new ArrayList<>();
		for (String[] line: lines) {
			Customer customer = ToCustomer(line);
			customer.setId(index);
			customers.add(customer);
			index++;
		}
		_customerDao.insertCustomers(customers);
	}
	
	public void ProcessUpdateFile(String csvFile) {
		ArrayList<String[]> lines = ReadFile(csvFile);
		
		addNewPayments(lines);
		
		ArrayList<String> initialCustomerCodes = _customerDao.getCustomerCodes();
		ArrayList<String> newCustomers = new ArrayList<String>();
		
		Long index = _customerDao.getMaxId();
		ArrayList<Customer> insertCustomers = new ArrayList<Customer>();
		ArrayList<Customer> updateCustomers = new ArrayList<Customer>();
		for (String[] line: lines) {
			String code = line[0];
			newCustomers.add(code);
			Customer customer = _customerDao.getCustomer(code);
			if(customer != null) {
				Customer newCustomer = ToCustomer(line);
				if(!customer.same(newCustomer)) {
					//4) exists in the table and in csv and are different: update the table and set the column updateTimeStamp
					customer.setId(index);
					updateCustomers.add(newCustomer);
					index++;
				}
				else {
					//3) exists in the table and in csv and all the data are the same: it does nothing
				}
			}
			else {
				//1) the record exists in csv and does not exist in the table: insert in the table and 
				//set the column inclusiontimestamp; 
				Customer insertCustomer = ToCustomer(line);
				insertCustomer.setId(index);
				insertCustomers.add(insertCustomer);
				index++;
			}
		}
		
		ArrayList<String> deleteCustomers = new ArrayList<String>();
		for (String initialCustomerCode: initialCustomerCodes) {
			if(!newCustomers.contains(initialCustomerCode)) {
				//2) exists in the table and does not exist in csv: set the column deleteTimeStamp in the table  
				//if it is null
				deleteCustomers.add(initialCustomerCode);
			}
		}
		
		//insertCustomers
		_customerDao.insertCustomers(insertCustomers);
		//updateCustomers
		_customerDao.updateCustomers(updateCustomers);
		//deleteCustomers
		_customerDao.deleteCustomers(deleteCustomers);
		
	}
	
	private ArrayList<String[]> ReadFile(String csvFile) {
        String line = "";
        String cvsSplitBy = ";(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))";
        ArrayList<String[]> lines = new ArrayList<String[]>();
        

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	//ignore headerLine: 2 rows
        	String headerLine1 = br.readLine();
        	String headerLine2 = br.readLine();
        	
            while ((line = br.readLine()) != null) {
                // use comma as separator
                lines.add(line.split(cvsSplitBy,-1));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
	}
	
	private Paymentform ToPaymentform(String code) {
		Paymentform paymentform = new Paymentform();
		paymentform.setCode(code);
		paymentform.setName(code);
		return paymentform;
	}
	
	private Paymentplan ToPaymentplan(String code) {
		Paymentplan paymentplan = new Paymentplan();
		paymentplan.setCode(code);
		paymentplan.setName(code);
		return paymentplan;
	}
	
	private Customer ToCustomer(String[] line) {
		String codePaymentform = line[14];
		String codePaymentplan = line[16];
		Paymentform paymentform = _paymentformDao.getPaymentform(codePaymentform);
		if(paymentform == null) {
			throw new UnsupportedOperationException("Paymentform not found");
		}
		Paymentplan paymentplan = _paymentplanDao.getPaymentplan(codePaymentplan);
		if(paymentplan == null) {
			throw new UnsupportedOperationException("Paymentplan not found");
		}
		
		Customer customer = new Customer();
		customer.setCity(line[7]);
		customer.setCode(line[0]);
		customer.setCommercialname(line[4]);
		customer.setContact(line[11]);
		customer.setEmail(line[15]);
		customer.setGovid(line[1]);
		//customer.setId(line[0]);
		customer.setLocalid(line[2]);
		customer.setName(line[3]);
		customer.setNeighborhood(line[6]);
		customer.setNote(line[19]);
		customer.setPhone(line[10]);
		customer.setState(line[8]);
		customer.setStreet(line[5]);
		customer.setStreetnumber(line[13]);
		customer.setZipCode(line[9]);
		customer.setPaymentformId(paymentform.getId());//line[14]);
		customer.setPaymentplanId(paymentplan.getId());
		customer.setPaymentformCode(paymentform.getCode());//line[14]);
		customer.setPaymentplanCode(paymentplan.getCode());
		return customer;
	}
	
	private void addNewPayments(ArrayList<String[]> lines) {
		ArrayList<String> initialPaymentformCodes = _paymentformDao.getPaymentforms();
		ArrayList<String> initialPaymentplanCodes = _paymentplanDao.getPaymentplans();
		Long indexFormMax = _paymentformDao.getMaxId();
		Long indexPlanMax = _paymentplanDao.getMaxId();
		ArrayList<String> paymentformCodes = new ArrayList<String>();
		ArrayList<String> paymentplanCodes = new ArrayList<String>();
		
		for (String[] line: lines) {
			if (!paymentformCodes.contains(line[14])){
				paymentformCodes.add(line[14]);
			}
			if (!paymentplanCodes.contains(line[16])){
				paymentplanCodes.add(line[16]);
			}
		}
		
		indexFormMax++;
		ArrayList<Paymentform> newPaymentforms = new ArrayList<Paymentform>();
		for (String paymentformCode: paymentformCodes) {
			if(!initialPaymentformCodes.contains(paymentformCode)) {
				Paymentform paymentform = ToPaymentform(paymentformCode);
				paymentform.setId(indexFormMax);
				newPaymentforms.add(paymentform);
				indexFormMax++;
			}
		}
		_paymentformDao.insertPaymentforms(newPaymentforms);
		
		indexPlanMax++;
		ArrayList<Paymentplan> newPaymentplans = new ArrayList<Paymentplan>();
		for (String paymentplanCode: paymentplanCodes) {
			if(!initialPaymentplanCodes.contains(paymentplanCode)) {
				Paymentplan paymentplan = ToPaymentplan(paymentplanCode);
				paymentplan.setId(indexPlanMax);
				newPaymentplans.add(paymentplan);
				indexPlanMax++;
			}
		}
		_paymentplanDao.insertPaymentplans(newPaymentplans);
	}

}
