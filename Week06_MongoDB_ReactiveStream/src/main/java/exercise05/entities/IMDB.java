package exercise05.entities;

public class IMDB {
	private double rating;
	private int votes;
	private int id;
	
	
	public IMDB() {

	}
	
	
	public IMDB(double rating, int votes, int id) {
		this.rating = rating;
		this.votes = votes;
		this.id = id;
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	public int getVotes() {
		return votes;
	}


	public void setVotes(int votes) {
		this.votes = votes;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "IMDB [rating=" + rating + ", votes=" + votes + ", id=" + id + "]";
	}
	
}
