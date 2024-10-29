package ex3;


import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Student {
	private String firstName;
	private String lastName;
	private double age;
	private int grade;
	private boolean isCurrent;
	
	
	// --- constructor ---
	public Student() {
		
	}


	// --- constructor ---
	public Student(String firstName, String lastName, double age, int grade, boolean isCurrent) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.grade = grade;
		this.isCurrent = isCurrent;
	}


	// --- getters/setters ---
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public double getAge() {
		return age;
	}


	public void setAge(double age) {
		this.age = age;
	}


	public int getGrade() {
		return grade;
	}


	public void setGrade(int grade) {
		this.grade = grade;
	}


	public boolean checkIsCurrent() {
		return isCurrent;
	}


	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
	
	
	private static String[] firstNames = {
		"Thanh", "Thao", "Tuan", "Hong", "Lan", "Phuong"
	};
	
	
	private static String[] lastNames = {
		"Nguyen", "Le", "Tran", "Mai", "Lam", "Ha"	
	};
	
	
	// tạo dữ liệu sinh viên
	private static Student[] generateStudentData() {
		final int N_STUDENTS = 80_000_000;
		final int N_CURRENT_STUDENTS = 1_000_000;
		Student[] students = new Student[N_STUDENTS];
		Random r = new Random(123);
		
		for (int i = 0; i < N_STUDENTS; i++) {
			final String firstName = firstNames[r.nextInt(firstNames.length)];
			final String lastName = lastNames[r.nextInt(lastNames.length)];
			final double age = r.nextDouble() * 100.0;
			final int grade = 1 + r.nextInt(100);
			final boolean current = (i < N_CURRENT_STUDENTS);
			students[i] = new Student(firstName, lastName, age, grade, current);
		}
		
		return students;
	}
	
	
	// hàm main: In kết quả
	public static void main(String[] args) {
		System.out.println("Week 3: Lab Fork/Join Framework");
		System.out.println("Exercise 3");
		System.out.println("1. Compute the average age of all actively enrolled students and compare execute time: ");
		System.out.println("a) Using loop");
		Student[] students = generateStudentData();
		
		/*
			1. Compute the average age of all actively enrolled students
			and compare execute time
		*/
		
		// a) Using loop
		
		long startTime = System.currentTimeMillis();
		double sumAgeLoop = 0;
		int countLoop = 0;
		for (Student student : students) {
			if (student.checkIsCurrent()) {
				sumAgeLoop += student.getAge();
				countLoop++;
			}
		}
		double averageAgeLoop = sumAgeLoop / countLoop;
		long endTime = System.currentTimeMillis();
		System.out.println("Average age (using loop): " + averageAgeLoop);
		System.out.println("Execution time (using loop): " + (endTime - startTime) + " ms");
		
		// b) Using stream
		System.out.println("b) Using stream");
		startTime = System.currentTimeMillis();
		double averageAgeStream = Arrays.stream(students)
				.filter(Student::checkIsCurrent)
				.mapToDouble(Student::getAge)
				.average()
				.orElse(0);
		endTime = System.currentTimeMillis();
		System.out.println("Average age (using stream): " + averageAgeLoop);
		System.out.println("Execution time (using stream): " + (endTime - startTime) + " ms");
		
		
		/*
			2. Compute the number of students 
			(using parallel stream) with conditions:
		*/
		
		// a) Student is not currently active.
		
		System.out.println("2. Compute the number of students (using parallel stream) with conditions:");
		System.out.println("a) Student is not currently active.");
		
		long countInactiveStudents =  Arrays.stream(students)
				.parallel()
				.filter(student -> !student.checkIsCurrent())
				.count();
		System.out.println("Number of inactive students: " + countInactiveStudents);
		
		// b) Age > 20
		System.out.println("b) Age > 20");
		long countStudentsAgeAbove20 = Arrays.stream(students)
				.parallel()
				.filter(student -> student.getAge() > 20)
				.count();
		System.out.println("Number of students with age above 20: " + countStudentsAgeAbove20);
		
		// c) Grade < 65
		System.out.println("c) Grade < 65");
		long countStudentsGradeBelow65 = Arrays.stream(students)
				.parallel()
				.filter(student -> student.getGrade() < 65)
				.count();
		System.out.println("Number of students with grade below 65: " + countStudentsGradeBelow65);
	}
}
