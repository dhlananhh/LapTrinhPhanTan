package exercise05.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Movies {
	@BsonId
	private ObjectId id;
	
	private String plot;
	private List<String> genres;
	private List<String> cast;
	
	@BsonProperty("num_mflix_comments")
	private int numMflixComments;
	private String title;
	
	@BsonProperty("full_plot")
	private String fullPlot;
	private List<String> countries;
	private LocalDate released;
	private List<String> directors;
	private String rated;
	private Awards awards;
	private LocalDate lastUpdated;
	private int year;
	private IMDB imdb;
	private String type;
	private Tomatoes tomatoes;
	
	
	public Movies() {

	}
	
	
	public Movies(ObjectId id, String plot, List<String> genres, List<String> cast, int numMflixComments, String title,
			String fullPlot, List<String> countries, LocalDate released, List<String> directors, String rated,
			Awards awards, LocalDate lastUpdated, int year, IMDB imdb, String type, Tomatoes tomatoes) {
		this.id = id;
		this.plot = plot;
		this.genres = genres;
		this.cast = cast;
		this.numMflixComments = numMflixComments;
		this.title = title;
		this.fullPlot = fullPlot;
		this.countries = countries;
		this.released = released;
		this.directors = directors;
		this.rated = rated;
		this.awards = awards;
		this.lastUpdated = lastUpdated;
		this.year = year;
		this.imdb = imdb;
		this.type = type;
		this.tomatoes = tomatoes;
	}


	public ObjectId getId() {
		return id;
	}


	public void setId(ObjectId id) {
		this.id = id;
	}


	public String getPlot() {
		return plot;
	}


	public void setPlot(String plot) {
		this.plot = plot;
	}


	public List<String> getGenres() {
		return genres;
	}


	public void setGenres(List<String> genres) {
		this.genres = genres;
	}


	public List<String> getCast() {
		return cast;
	}


	public void setCast(List<String> cast) {
		this.cast = cast;
	}


	public int getNumMflixComments() {
		return numMflixComments;
	}


	public void setNumMflixComments(int numMflixComments) {
		this.numMflixComments = numMflixComments;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getFullPlot() {
		return fullPlot;
	}


	public void setFullPlot(String fullPlot) {
		this.fullPlot = fullPlot;
	}


	public List<String> getCountries() {
		return countries;
	}


	public void setCountries(List<String> countries) {
		this.countries = countries;
	}


	public LocalDate getReleased() {
		return released;
	}


	public void setReleased(LocalDate released) {
		this.released = released;
	}


	public List<String> getDirectors() {
		return directors;
	}


	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}


	public String getRated() {
		return rated;
	}


	public void setRated(String rated) {
		this.rated = rated;
	}


	public Awards getAwards() {
		return awards;
	}


	public void setAwards(Awards awards) {
		this.awards = awards;
	}


	public LocalDate getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(LocalDate lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public IMDB getImdb() {
		return imdb;
	}


	public void setImdb(IMDB imdb) {
		this.imdb = imdb;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Tomatoes getTomatoes() {
		return tomatoes;
	}


	public void setTomatoes(Tomatoes tomatoes) {
		this.tomatoes = tomatoes;
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
		Movies other = (Movies) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		return "Movies [id=" + id + ", plot=" + plot + ", genres=" + genres + ", cast=" + cast + ", numMflixComments="
				+ numMflixComments + ", title=" + title + ", fullPlot=" + fullPlot + ", countries=" + countries
				+ ", released=" + released + ", directors=" + directors + ", rated=" + rated + ", awards=" + awards
				+ ", lastUpdated=" + lastUpdated + ", year=" + year + ", imdb=" + imdb + ", type=" + type
				+ ", tomatoes=" + tomatoes + "]";
	}
	
}
