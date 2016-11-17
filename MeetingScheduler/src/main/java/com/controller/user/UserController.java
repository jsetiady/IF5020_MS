package com.controller.user;

import java.util.ArrayList;
import java.util.List;

import com.model.user.User;
/**
 * @author jessiesetiady
 *
 */
public class UserController {
	ArrayList<User> listUser= new ArrayList<User>();
	
	public User authenticateUser(String email, String password){
		//if (FOUND) then
		//return User object
		//else
		//return null
		
		return null;
	}
	
	public void save(User user) {
		listUser.add(user);
	}
	
	
	public List<User> getAllUser() {
		
		return listUser;
	}
}
