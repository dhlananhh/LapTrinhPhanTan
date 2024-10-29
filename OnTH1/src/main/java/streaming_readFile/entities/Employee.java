package streaming_readFile.entities;


import java.util.List;


public class Employee {
	private String name;
	private boolean isRetired;
	private int age;
	private List<String> skills;
	
	
	public Employee() {
		
	}
	
	
	public Employee(String name, boolean isRetired, int age, List<String> skills) {
		this.name = name;
		this.isRetired = isRetired;
		this.age = age;
		this.skills = skills;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean isRetired() {
		return isRetired;
	}


	public void setRetired(boolean isRetired) {
		this.isRetired = isRetired;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public List<String> getSkills() {
		return skills;
	}


	public void setSkills(List<String> skills) {
		this.skills = skills;
	}


	@Override
	public String toString() {
		return "Employee [name=" + name + ", isRetired=" + isRetired + ", age=" + age + ", skills=" + skills + "]";
	}
	
}
