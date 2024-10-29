package exercise01.entities;


import java.time.LocalDate;
import java.util.List;


public class Order {
	private String id;
	private LocalDate orderDate;
	private Customer customer;
	private Enum orderStatus;
	private LocalDate shippedDate;
	private Staff staff;
	private List<OrderDetails> orderDetails;
	private double orderTotal;
	private ShippingAddress shippingAddress;
	
	
	public Order() {
	
	}


	public Order (String id, LocalDate orderDate, Customer customer, Enum orderStatus, 
			LocalDate shippedDate, Staff staff, List<OrderDetails> orderDetails, 
			double orderTotal, ShippingAddress shippingAddress) {
		this.id = id;
		this.orderDate = orderDate;
		this.customer = customer;
		this.orderStatus = orderStatus;
		this.shippedDate = shippedDate;
		this.staff = staff;
		this.orderDetails = orderDetails;
		this.orderTotal = orderTotal;
		this.shippingAddress = shippingAddress;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public LocalDate getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Enum getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(Enum orderStatus) {
		this.orderStatus = orderStatus;
	}


	public LocalDate getShippedDate() {
		return shippedDate;
	}


	public void setShippedDate(LocalDate shippedDate) {
		this.shippedDate = shippedDate;
	}


	public Staff getStaff() {
		return staff;
	}


	public void setStaff(Staff staff) {
		this.staff = staff;
	}


	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}


	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}


	public double getOrderTotal() {
		return orderTotal;
	}


	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}


	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}


	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}


	@Override
	public String toString() {
		return "Order [id=" + id + ", orderDate=" + orderDate + ", customer=" + customer 
				+ ", orderStatus=" + orderStatus + ", shippedDate=" + shippedDate 
				+ ", staff=" + staff + ", orderDetails=" + orderDetails
				+ ", orderTotal=" + orderTotal + ", shippingAddress=" + shippingAddress + "]";
	}
	
}
