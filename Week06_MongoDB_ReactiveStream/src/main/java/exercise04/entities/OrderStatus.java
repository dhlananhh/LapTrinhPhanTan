package exercise04.entities;

public enum OrderStatus {
	
	NEW ("new"),
	IN_PROGRESS ("in process"),
	COMPLETED ("completed"),
	PARTIALLY_SHIPPED ("partially shipped"),
	ON_HOLD ("on hold"),
	CANCALLED ("cancelled"),
	AWAITING_EXCHANGE ("awaiting_exchange");
	
	private String status;

	private OrderStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "OrderStatus [status=" + status + "]";
	}
	
	
}
