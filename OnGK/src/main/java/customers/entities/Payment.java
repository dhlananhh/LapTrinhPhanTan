package customers.entities;


import java.util.Date;
import org.bson.codecs.pojo.annotations.BsonProperty;


public class Payment {
	@BsonProperty("date")
	private Date date;
	
	@BsonProperty("value")
	private double value;
	
	
	public Payment() {
		
	}


	public Payment (Date date, double value) {
		this.date = date;
		this.value = value;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public double getValue() {
		return value;
	}


	public void setValue(double value) {
		this.value = value;
	}


	@Override
	public String toString() {
		return "Payment [date=" + date + ", value=" + value + "]";
	}
	
}
