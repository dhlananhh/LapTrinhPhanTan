package customers.entities;


import java.util.List;
import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;


public class Customer {
	@BsonId
	private String id;
	
	@BsonProperty("title")
	private String title;
	
	@BsonProperty("first")
	private String first;
	
	@BsonProperty("last")
	private String last;
	
	@BsonProperty("email")
	private String email;
	
	@BsonProperty("dob")
	private String dob;
	
	@BsonProperty("address")
	private Address address;
	
	@BsonProperty("user_name")
	private String userName;
	
	@BsonProperty("package")
	private String packages;
	
	@BsonProperty("prio_support")
	private boolean prioSupport;
	
	@BsonProperty("registered_on")
	private String registeredOn;
	
	@BsonProperty("transactions")
	private String transactions;
	
	@BsonProperty("payments")
	private List<Payment> payments;
	
	
	public Customer() {
		
	}


	public Customer (String id, String title, String first, String last, 
			String email, String dob, Address address, String userName, 
			String packages, boolean prioSupport, String registeredOn, 
			String transactions, List<Payment> payments) {
		this.id = id;
		this.title = title;
		this.first = first;
		this.last = last;
		this.email = email;
		this.dob = dob;
		this.address = address;
		this.userName = userName;
		this.packages = packages;
		this.prioSupport = prioSupport;
		this.registeredOn = registeredOn;
		this.transactions = transactions;
		this.payments = payments;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getFirst() {
		return first;
	}


	public void setFirst(String first) {
		this.first = first;
	}


	public String getLast() {
		return last;
	}


	public void setLast(String last) {
		this.last = last;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPackages() {
		return packages;
	}


	public void setPackages(String packages) {
		this.packages = packages;
	}


	public boolean checkPrioSupport() {
		return prioSupport;
	}


	public void setPrioSupport(boolean prioSupport) {
		this.prioSupport = prioSupport;
	}


	public String getRegisteredOn() {
		return registeredOn;
	}


	public void setRegisteredOn(String registeredOn) {
		this.registeredOn = registeredOn;
	}


	public String getTransactions() {
		return transactions;
	}


	public void setTransactions(String transactions) {
		this.transactions = transactions;
	}


	public List<Payment> getPayments() {
		return payments;
	}


	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		return "Customer [id=" + id + ", title=" + title + ", first=" + first + ", last=" + last + ", email=" + email
				+ ", dob=" + dob + ", address=" + address + ", userName=" + userName + ", packages=" + packages
				+ ", prioSupport=" + prioSupport + ", registeredOn=" + registeredOn + ", transactions=" + transactions
				+ ", payments=" + payments + "]";
	}
	
}
