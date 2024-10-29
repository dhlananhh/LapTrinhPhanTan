package exercise01.entities;


public class OrderDetails {
	private int quantity;
	private String color;
	private int productId;
	private double lineTotal;
	private double price;
	private double discount;
	
	
	public OrderDetails() {
		
	}


	public OrderDetails(int quantity, String color, int productId, double lineTotal, double price, double discount) {
		this.quantity = quantity;
		this.color = color;
		this.productId = productId;
		this.lineTotal = lineTotal;
		this.price = price;
		this.discount = discount;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public double getLineTotal() {
		return lineTotal;
	}


	public void setLineTotal(double lineTotal) {
		this.lineTotal = lineTotal;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public double getDiscount() {
		return discount;
	}


	public void setDiscount(double discount) {
		this.discount = discount;
	}


	@Override
	public String toString() {
		return "OrderDetails [quantity=" + quantity + ", color=" + color + ", productId=" + productId + ", lineTotal="
				+ lineTotal + ", price=" + price + ", discount=" + discount + "]";
	}
	
}
