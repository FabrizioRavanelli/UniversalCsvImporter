package Entities;

import java.sql.Timestamp;

public class Customer {	  

	 private Long id;
	 private Timestamp updatetimestamp;
	 private Timestamp deletetimestamp;
	 private Timestamp inclusiontimestamp;
	 private Long optlock;
	 private String neighborhood;
	 private String zipcode;
	 private String code;
	 private String govid;
	 private String email;
	 private String street;
	 private String localid;
	 private String city;
	 private String commercialname;
	 private String contact;
	 private String streetnumber;
	 private String note;
	 private String name;
	 private String phone;
	 private String state;
	 private Long paymentform_id;
	 private Long paymentplan_id;
	 private String paymentformcode;
	 private String paymentplancode;
	 
	 public Customer() {
		 this.optlock = 0L;
		 this.paymentplan_id = 0L;
		 this.paymentform_id = 0L;
	 }
	 
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Long getOptlock() {
	        return optlock;
	    }
	 
	    public void setOptlock(Long optlock) {
	        this.optlock = optlock;
	    }
	 
	    public String getNeighborhood() {
	        return neighborhood;
	    }

	    public void setNeighborhood(String neighborhood) {
	        this.neighborhood = neighborhood;
	    }
	 
	    public String getZipCode() {
	        return zipcode;
	    }
	 
	    public void setZipCode(String zipcode) {
	        this.zipcode = zipcode;
	    }
	 
	    public String getCode() {
	        return code;
	    }
	 
	    public void setCode(String code) {
	        this.code = code;
	    }
	 
	    public String getGovid() {
	        return govid;
	    }
	 
	    public void setGovid(String govid) {
	        this.govid = govid;
	    }
	 
	    public String getEmail() {
	        return email;
	    }
	 
	    public void setEmail(String email) {
	        this.email = email;
	    }
	 
	    public String getStreet() {
	        return street;
	    }
	 
	    public void setStreet(String street) {
	        this.street = street;
	    }
	 
	    public String getLocalid() {
	        return localid;
	    }
	 
	    public void setLocalid(String localid) {
	        this.localid = localid;
	    }
	 
	    public String getCity() {
	        return city;
	    }

	    public void setCity(String city) {
	        this.city = city;
	    }
	 
	    public String getCommercialname() {
	        return commercialname;
	    }

	    public void setCommercialname(String commercialname) {
	        this.commercialname = commercialname;
	    }

	    public String getContact() {
	        return contact;
	    }

	    public void setContact(String contact) {
	        this.contact = contact;
	    }

	    public String getStreetnumber() {
	        return streetnumber;
	    }

	    public void setStreetnumber(String streetnumber) {
	        this. streetnumber = streetnumber;
	    }

	    public String getNote() {
	        return note;
	    }
	    
	    public void setNote(String note) {
	        this.note = note;
	    }
	    
	    public String getName() {
	        return name;
	    }
	    
	    public void setName(String name) {
	        this.name = name;
	    }
	    
	    public String getPhone() {
	        return phone;
	    }
	    
	    public void setPhone(String phone) {
	        this.phone = phone;
	    }
	    
	    public String getState() {
	        return state;
	    }
	    
	    public void setState(String state) {
	        this.state = state;
	    }
	    
	    public Long getPaymentformId() {
	        return paymentform_id;
	    }
	    
	    public void setPaymentformId(Long paymentform_id) {
	        this.paymentform_id = paymentform_id;
	    }
	    
	    public String getPaymentformCode() {
	        return paymentformcode;
	    }
	    
	    public void setPaymentformCode(String paymentformcode) {
	        this.paymentformcode = paymentformcode;
	    }
	    
	    public Long getPaymentplanId() {
	        return paymentplan_id;
	    }    
	    
	    public void setPaymentplanId(Long paymentplan_id) {
	        this.paymentplan_id = paymentplan_id;
	    }
	    
	    public String getPaymentplanCode() {
	        return paymentplancode;
	    }    
	    
	    public void setPaymentplanCode(String paymentplancode) {
	        this.paymentplancode = paymentplancode;
	    } 
	    
	    public Timestamp getUpdatetimestamp() {
	        return updatetimestamp;
	    }    
	    
	    public void setUpdatetimestamp(Timestamp updatetimestamp) {
	        this.updatetimestamp = updatetimestamp;
	    } 
	    
	    public Timestamp getDeletetimestamp() {
	        return deletetimestamp;
	    }    
	    
	    public void setDeletetimestamp(Timestamp deletetimestamp) {
	        this.deletetimestamp = deletetimestamp;
	    } 
	    
	    public Timestamp getInclusiontimestamp() {
	        return inclusiontimestamp;
	    }    
	    
	    public void setInclusiontimestamp(Timestamp inclusiontimestamp) {
	        this.inclusiontimestamp = inclusiontimestamp;
	    } 
	    
		public boolean same(Customer customer) {
			if(!this.city.equalsIgnoreCase(customer.city))
				return false;
			if(!this.code.equalsIgnoreCase(customer.code))
				return false;
			if(!this.commercialname.equalsIgnoreCase(customer.commercialname))
				return false;
			if(!this.contact.equalsIgnoreCase(customer.contact))
				return false;
			if(!this.email.equalsIgnoreCase(customer.email))
				return false;
			if(!this.govid.equalsIgnoreCase(customer.govid))
				return false;
			if(!this.localid.equalsIgnoreCase(customer.localid))
				return false;
			if(!this.neighborhood.equalsIgnoreCase(customer.neighborhood))
				return false;
			if(!this.note.equalsIgnoreCase(customer.note))
				return false;
			if(!this.phone.equalsIgnoreCase(customer.phone))
				return false;
			if(!this.state.equalsIgnoreCase(customer.state))
				return false;
			if(!this.street.equalsIgnoreCase(customer.street))
				return false;
			if(!this.streetnumber.equalsIgnoreCase(customer.streetnumber))
				return false;
			if(!this.zipcode.equalsIgnoreCase(customer.zipcode))
				return false;
			if(!this.paymentformcode.equalsIgnoreCase(customer.paymentformcode))
				return false;
			if(!this.paymentplancode.equalsIgnoreCase(customer.paymentplancode))
				return false;
			return true;
		}
	    
}
