package exercise05.entities;


public class Viewer {
	private int rating;
	private int numReviews;
	private int meter;
	
	
	public Viewer() {

	}
	
	
	public Viewer(int rating, int numReviews, int meter) {
		this.rating = rating;
		this.numReviews = numReviews;
		this.meter = meter;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}


	public int getNumReviews() {
		return numReviews;
	}


	public void setNumReviews(int numReviews) {
		this.numReviews = numReviews;
	}


	public int getMeter() {
		return meter;
	}


	public void setMeter(int meter) {
		this.meter = meter;
	}


	@Override
	public String toString() {
		return "Viewer [rating=" + rating + ", numReviews=" + numReviews + ", meter=" + meter + "]";
	}
	
}
