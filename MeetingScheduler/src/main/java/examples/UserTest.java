package examples;

import java.util.List;

public class UserTest {
	private String name;
	private int age;
	private List<String> messages;

	//getters and setters
	
	public String getName() {
		return this.name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	public List<String> getMessages() {
		return this.messages;
	}
}
