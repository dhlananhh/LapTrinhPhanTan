package restaurants.entities;


import java.util.Date;
import org.bson.codecs.pojo.annotations.BsonProperty;


public class Grade {
	@BsonProperty("date")
	private Date date;
	
	@BsonProperty("grade")
	private String grade;
	
	@BsonProperty("score")
	private int score;

	
	public Grade() {

	}


	public Grade (Date date, String grade, int score) {
		this.date = date;
		this.grade = grade;
		this.score = score;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	@Override
	public String toString() {
		return "Grade [date=" + date + ", grade=" + grade + ", score=" + score + "]";
	}
	
}
