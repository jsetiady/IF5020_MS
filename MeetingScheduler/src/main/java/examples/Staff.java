package examples;


import java.math.BigDecimal;
import java.util.List;


public class Staff {
	private String name;
	private int age;
	private String position;
	private BigDecimal salary;
	private List<String> skills;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getPosition() {
		return this.position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public BigDecimal getSalary() {
		return this.salary;
	}
	
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	
	public List<String> getSkills() {
		return this.skills;
	}
	
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

}
