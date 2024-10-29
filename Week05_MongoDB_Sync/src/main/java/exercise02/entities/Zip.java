package exercise02.entities;


import org.bson.types.ObjectId;

public class Zip {
	private ObjectId id;
	private String city;
	private String zip;
	private Location loc;
	private double pop;
	private String state;
	
	
	public Zip() {
		
	}


	public Zip (ObjectId id, String city, String zip, Location loc, double pop, String state) {
		this.id = id;
		this.city = city;
		this.zip = zip;
		this.loc = loc;
		this.pop = pop;
		this.state = state;
	}


	public ObjectId getId() {
		return id;
	}


	public void setId(ObjectId id) {
		this.id = id;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public Location getLoc() {
		return loc;
	}


	public void setLoc(Location loc) {
		this.loc = loc;
	}


	public double getPop() {
		return pop;
	}


	public void setPop(double pop) {
		this.pop = pop;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	@Override
	public String toString() {
		return "Zip [id=" + id + ", city=" + city + ", zip=" + zip + ", loc=" + loc + ", pop=" + pop + ", state="
				+ state + "]";
	}
	
}
