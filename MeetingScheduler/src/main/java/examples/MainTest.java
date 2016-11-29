package examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainTest {
	
	public static void main(String args[]) {
		UserTest user = new UserTest();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ObjectMapper mapper = new ObjectMapper();
		

		GenericViewer<UserTest> view = new GenericViewer<UserTest>(user);
		user.setName("mkyong");
		user.setAge(33);

		List<String> msg = new ArrayList<String>();
		msg.add("hello jackson 1");
		msg.add("hello jackson 2");
		msg.add("hello jackson 3");

		user.setMessages(msg);
		
		ArrayList<UserTest> test = new ArrayList<UserTest>();
		test.add(user);
		test.add(user);
		//System.out.println(gson.toJson(test));
		
		try {
			//Convert object to JSON string and save into file directly
			mapper.writeValue(new File("resources/test.json"), test);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("test");
		
		//view.printListMeetingObject(user);
		
		
		

	}


}
