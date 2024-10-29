package exercise04.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
	
	private String orderId;
	private LocalDate orderDate;
	private OrderStatus orderStatus;
	private LocalDate shippedDate;
	private Customer customer;
	private Staff staff;
	private List<OrderDetail> orderDetails;
	
	private double orderTotal;
	private Address shippingAddress;
	
	public Order() {
		this(LocalDate.now(), OrderStatus.NEW);
	}

	public Order(LocalDate orderDate, OrderStatus orderStatus) {
		super();
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.orderDetails = new ArrayList<OrderDetail>();
		this.orderTotal = getOrderTotal();
	}

	/**
	 * Add order detail
	 * 
	 * @param product
	 * @param order
	 * @param quantity
	 * @param color
	 * @param price
	 * @param discount
	 */
	public void addOrderDetail(Product product, Order order, int quantity, String color, double price, double discount) {
		OrderDetail orderDetail = new OrderDetail(product, quantity, color, price, discount);
		this.orderDetails.add(orderDetail);
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDate getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(LocalDate shippedDate) {
		this.shippedDate = shippedDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public double getOrderTotal() {
		double total = 0.0d;
		for(OrderDetail orderDetail : orderDetails) {
			total +=orderDetail.getLineTotal();
		}
		
		return total;
		
//		return orderDetails.stream()
//				.mapToDouble(orderDetail -> orderDetail.getLineTotal())
//				.sum();
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", orderStatus=" + orderStatus
				+ ", shippedDate=" + shippedDate + ", customer=" + customer + ", staff=" + staff + ", orderDetails="
				+ orderDetails + ", orderTotal=" + orderTotal + ", shippingAddress=" + shippingAddress + "]";
	}
	
	
	
}
