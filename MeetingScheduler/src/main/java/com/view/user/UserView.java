package com.view.user;

import java.io.Console;
import java.util.Scanner;

import com.model.user.Authority;
import com.model.user.User;
import com.view.meeting.MeetingView;

/**
 * @author jessiesetiady
 *
 */
public class UserView {
		
	public void displayLogin() {
		//variables
		Scanner s = new Scanner(System.in);
		String username, password;
		
		//ask for username and password
		System.out.print("email: ");
		username = s.nextLine();
		System.out.print("password: ");
		password = s.nextLine();
		
		//@putra, please implements auth logic in UserController
		//authenticated means user is exist, return user object
		//else return null
		//if (user.isAuth(email, password)) { // <-- put nanti diganti ya, method ini balikannya objek user
			System.out.println("let's say user has been authenticated");
			System.out.println();
			//call displayMenu(User);
			displayMenu(new User("jeje@gmail.com", "12344"));
		//} else {
		//System.out.println("please check your username and password");
		//Try again? (Y/N) --> if 'Y' repeat until authenticated
		//}
		
		
		//if authenticated
		//call displayMenu(User);
	}
	
	public void displayMenu(User user) {
		user.setAuthority(Authority.ADMIN);
		if(user.getAuthority().equals(Authority.ADMIN)) {
			//call displayAdminMainMenu()
		} else {
			displayUserMainMenu(user);
		}
	}
	
	public void displayUserMainMenu(User user) {
		Scanner s = new Scanner(System.in);
		MeetingView meetingView = new MeetingView();
		int choice = 0;
		
		do {
			System.out.println("Hello, " + user.getEmail() + "!");
			System.out.println("You have 0 new meeting invitation");
			System.out.println("--------------------------------");
			System.out.println();
			System.out.println("MAIN MENU");
			System.out.println("--------------------------------");
			System.out.println("1. Create new meeting");
			System.out.println("2. Show created meeting");
			System.out.println("3. My schedule");
			System.out.println("4. Meeting invitation");
			System.out.println("5. Change password");
			System.out.println("6. Log out and exit");
			System.out.println("--------------------------------");
			System.out.print("Enter your choice: ");
			try {
				choice = s.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input");
			}
			System.out.println();
			
			switch(choice) {
				case 1:
					meetingView.createMeetingView(user.getEmail());
					break;
				case 2: break;
				case 3: break;
				case 4: break;
				case 5: break;
				case 6: break;
				default: System.out.println("Menu not found");
			}
		} while(choice != 6);
	}
	
}
