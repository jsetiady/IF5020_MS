package com.model;

/*
 * Filename: User.java
 * Author: jessie setiady
 * Description:
 * Date created: 
 */

public class User {
	private String email;
	private String password;
	private boolean isAdmin;
	private boolean isActive = true;
	
	//Data nanti di load dari json file
	private String[][] accounts = {{"jeje@gmail.com", "1234"}, {"putra@gmail.com", "1234"}, {"siti", "1234"}};
	
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	
	/*
	 * TODO:
	 * 1. Add User
	 * 2. Modify
	 * 3. List
	 * 4. View Detail
	 * 5. Delete or remove (suggestion: update attribute isActive)
	 * 6. getAllUser
	 * 7. validateLogin (find user by username)
	 */
	
	public boolean isAuth(String email, String password) {
		return ((email.equals(accounts[0][0])) && (password.equals(accounts[0][1])));
	}
}
