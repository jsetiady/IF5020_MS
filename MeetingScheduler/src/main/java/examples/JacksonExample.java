package examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

class User {
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
}




public class JacksonExample {
	public static void main(String[] args) {

		ObjectMapper mapper = new ObjectMapper();

		//For testing
		User user = createDummyUser();

		try {
			//Convert object to JSON string and save into file directly
			mapper.writeValue(new File("D:\\user.json"), user);

			//Convert object to JSON string
			String jsonInString = mapper.writeValueAsString(user);
			System.out.println(jsonInString);

			//Convert object to JSON string and pretty print
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
			System.out.println(jsonInString);


		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static User createDummyUser(){

		User user = new User();

		user.setName("mkyong");
		user.setAge(33);

		List<String> msg = new ArrayList<String>();
		msg.add("hello jackson 1");
		msg.add("hello jackson 2");
		msg.add("hello jackson 3");

		user.setMessages(msg);

		return user;

	}
}
