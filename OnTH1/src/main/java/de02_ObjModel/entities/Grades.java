package de02_ObjModel.entities;

public class Grades {
	private long date;
	private String grade;
	private int score;
	
	
	public Grades() {

	}


	public Grades (long date, String grade, int score) {
		this.date = date;
		this.grade = grade;
		this.score = score;
	}


	public long getDate() {
		return date;
	}


	public void setDate(long date) {
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
		return "Grades [date=" + date + ", grade=" + grade + ", score=" + score + "]";
	}
	
}
