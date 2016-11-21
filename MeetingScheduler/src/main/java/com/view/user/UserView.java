package com.view.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.controller.user.UserController;
import com.model.user.User;
import com.view.meeting.MeetingView;

/**
 * @author jessiesetiady
 *
 */
public class UserView {
	Scanner scan = new Scanner(System.in);
	UserController uc = new UserController();
	
	public boolean login(String email, String password) {
		if(uc.checkLogin(email, password)!=null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void showListUser() {
		List<User> listUser = new ArrayList<User>();
		listUser = uc.getAllUser();
		
		System.out.println("|===========================================|");
		System.out.println("|        IF5021 MEETING SCHEDULER           |");
		System.out.println("|===========================================|");
		System.out.println("|LIST USER                                	|");
		System.out.println("|===========================================|");
		System.out.println("|First Name\t\t|LastName\t\t|Address\t\t|Phone\t\t|DOB\t\t|Sex\t|Email\t\t");
		for (User usr: listUser) {
			System.out.println("|"+usr.getFirstName()+"\t\t|"+usr.getLastName()+"\t\t|"+usr.getAddress()+"\t\t|"+usr.getPhone()+"\t\t|"+usr.getDob()+"\t\t|"+usr.getSex()+"\t\t|"+usr.getEmail());
		}
		
	}
	
	public void showListUser(List<User> listUser) {
		listUser = uc.getAllUser();
		
		System.out.println("|===========================================|");
		System.out.println("|        IF5021 MEETING SCHEDULER           |");
		System.out.println("|===========================================|");
		System.out.println("|LIST USER                                	|");
		System.out.println("|===========================================|");
		System.out.println("|First Name\t\t|LastName\t\t|Address\t\t|Phone\t\t|DOB\t\t|Sex\t|Email\t\t");
		for (User usr: listUser) {
			System.out.println("|"+usr.getFirstName()+"\t\t|"+usr.getLastName()+"\t\t|"+usr.getAddress()+"\t\t|"+usr.getPhone()+"\t\t|"+usr.getDob()+"\t\t|"+usr.getSex()+"\t\t|"+usr.getEmail());
		}
		
	}
	
	public void viewDetailUser (User user) {
		System.out.println("First Name :" + user.getFirstName());
		System.out.println("Last Name :" + user.getLastName());
		System.out.println("Address :" + user.getAddress());
		System.out.println("Phone :" + user.getPhone());
	}
	
	public User createUser() 
	{
		User user = new User();
		
		System.out.println("|===========================================|");
		System.out.println("|        IF5021 MEETING SCHEDULER           |");
		System.out.println("|===========================================|");
		System.out.println("|CREATE USER                                |");
		System.out.println("|===========================================|");
		System.out.print("|First Name       :"); user.setFirstName(scan.nextLine()); 
		System.out.print("|Last Name        :"); user.setLastName(scan.nextLine());
		System.out.print("|Address          :"); user.setAddress(scan.nextLine());
		System.out.print("|Phone            :"); user.setPhone(scan.nextLine());
		System.out.print("|DOB              :"); user.setDob(scan.nextLine());
		System.out.print("|Sex              :"); user.setSex(scan.next(".").charAt(0));
		scan.nextLine();
		System.out.print("|Email            :"); user.setEmail(scan.nextLine());
		System.out.print("|Password         :"); user.setPassword(scan.nextLine());
		System.out.print("|Admin         	:"); user.setAdmin(scan.nextBoolean());
		System.out.print("|Active         :"); user.setActive(scan.nextBoolean());
		System.out.println("--------------------------------------------|");
		System.out.println("[1] Add More");
		System.out.println("[2] Save");
		
		
		return user;
		
	}
		
	public void displayLogin() {
		//variables
		MenuView menuView = new MenuView();
		String username, password;
		
		//ask for username and password
		/**
		System.out.print("email: ");
		username = s.nextLine();
		System.out.print("password: ");
		password = s.nextLine();
		*/
		
		//User user = menuView.menuLogin();
		
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
		MenuView menuView = new MenuView();
		System.out.println(user.isAdmin());
		if(user.isAdmin()) {
			menuView.menuHome();
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
				case 2:
					meetingView.displayCreatedMeeting(user.getEmail());
					break;
				case 3: 
					meetingView.viewMeetingScheduleByEmail(user.getEmail());
					break;
				case 4: break;
				case 5: break;
				case 6: break;
				default: System.out.println("Menu not found");
			}
		} while(choice != 6);
	}
	
}
