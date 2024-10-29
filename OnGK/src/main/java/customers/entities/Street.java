package customers.entities;


import org.bson.codecs.pojo.annotations.BsonProperty;


public class Street {
	@BsonProperty("name")
	private String name;
	
	@BsonProperty("suffix")
	private String suffix;
	
	@BsonProperty("number")
	private String number;
	
	
	public Street() {
		
	}


	public Street (String name, String suffix, String number) {
		this.name = name;
		this.suffix = suffix;
		this.number = number;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	@Override
	public String toString() {
		return "Street [name=" + name + ", suffix=" + suffix + ", number=" + number + "]";
	}
	
}
