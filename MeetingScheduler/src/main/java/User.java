
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
	
	
	public User(String email) {
		this.email = email;
	}
	
	public User() {
		this("");
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
}
