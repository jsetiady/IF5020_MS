package com.controller.user;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.user.User;
/**
 * @author jessiesetiady
 *
 */
public class UserController {

	ArrayList<User> listUser= new ArrayList<User>();
	/**
	public User authenticateUser(String email, String password){
		try {
			Object obj = parser.parse(new FileReader("D:\\user.json"));
			
			JSONObject object = (JSONObject) obj;
			
			String name = (String) object.get("name");
			System.out.println(email.equals(name));
			
		} catch (Exception e) {
			// handle exception
			System.out.println("Error");
		}
		//if (FOUND) then
		//return User object
		//else
		//return null
		
		return null;
	}
	*/
	
	/**
	
	public void createUser(User user) {
		
	}
	
	public void save(User user) {
		listUser.add(user);
	}
	
	*/

	public List<User> getAllUser() {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			listUser = mapper.readValue(new File("D:\\user.json"), new TypeReference<List<User>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listUser;
	}
	
	public User getUserByEmail(String email) {
		List<User> listuser = new ArrayList<User>();
		listuser = getAllUser();
		User user = null;
		for(int i=0;i<listuser.size();i++) {
			if(listuser.get(i).getEmail().equals(email)) {
				user = listuser.get(i);
			}
		}
		return user;
	}
	
	
	public void save () {
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonString;
		
		try {
			//Convert object to JSON string and save into file directory
			mapper.writeValue(new File("D:\\user.json"), listUser);
			
			//Convert object to Json String
			 jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(listUser);
			 
			 //System.out.println(jsonString);
			 
			 
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createDummyUser() {
		User user = new User();
		User user2 = new User();
		
		user.setFirstName("Kurnia Ramadh");
		user.setLastName("Putra");
		user.setAddress("Jl.Pahlawan");
		user.setPhone("8896876765");
		user.setDob("11/03/1991");
		user.setSex('P');
		user.setEmail("putra@gmail.com");
		user.setPassword("1234");
		user.setAdmin(true);
		user.setActive(true);
		
		user2.setFirstName("Jessie Andika");
		user2.setLastName("Setiady");
		user2.setAddress("Jl.Supratman");
		user2.setPhone("8896876765");
		user2.setDob("11/03/1990");
		user2.setSex('W');
		user2.setEmail("jeje@gmail.com");
		user2.setPassword("1234");
		user2.setAdmin(false);
		user2.setActive(true);
		
		listUser.add(user);
		listUser.add(user2);
	}
	
	public void add(User user) {
		listUser.add(user);
	}
	
	public User checkLogin(String email, String password) {
		ObjectMapper mapper = new ObjectMapper();
		User usr = new User();
		usr = null;
		try {
			// Convert JSON String from file into object
			List<User> listUser = mapper.readValue(new File("D:\\user.json"), new TypeReference<List<User>>(){});
			
			for (User user: listUser) {
				if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
					usr = user;
				} 			
			}
			
			
		} catch (Exception e) {
			// handle exception
		}
		
		return usr;
	}
	
	
}
