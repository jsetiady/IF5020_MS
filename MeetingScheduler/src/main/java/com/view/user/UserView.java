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
	
	public User login(String email, String password) {
		return uc.checkLogin(email, password);
	}
	
	public void showListUser() {
		List<User> listUser = new ArrayList<User>();
		listUser = uc.getAllUser();
		
		System.out.println("+===========================================================================================================+");
		System.out.println("|LIST USER                                                                                                  |");
		System.out.println("+===========================================================================================================+");
		System.out.println("|First Name          |LastName       |Address             |Phone          |DOB       |  Sex |      Email    |");
		System.out.println("+-----------------------------------------------------------------------------------------------------------+");
		for (User usr: listUser) {
			System.out.printf(" %-20s %-15s %-20s %-15s %-12s %-4s %-15s",usr.getFirstName(), usr.getLastName(), usr.getAddress(), usr.getPhone(), usr.getDob(), usr.getSex(), usr.getEmail());
			System.out.println();
		}
		
	}
	
	
	public void viewUserByEmail(String email) {
		viewDetailUser(uc.getUserByEmail(email));
	}
	
	public void viewDetailUser (User user) {
		String auth, status;
		if(user==null) {
			System.out.println("User not found");
		} else {
			System.out.println("+---------------------------------------------+");
			System.out.println("|DETAIL USER                                  |");
			System.out.println("+-------------------------------------------- +");
			System.out.println(" First Name   :" + user.getFirstName());  
			System.out.println(" Last Name    :" + user.getLastName()); 
			System.out.println(" Address      :" + user.getAddress()); 
			System.out.println(" Phone        :" + user.getPhone());
			System.out.println(" DOB          :" + user.getDob()); 
			System.out.println(" Sex          :" + user.getSex()); 
			System.out.println(" Email        :" + user.getEmail());
			
			if (user.isAdmin()==true) {
				auth = "Yes";
			} else {
				auth = "No";
			}
			
			if (user.isActive()==true) {
				status = "Yes";
			} else {
				status = "No";
			}
			System.out.println(" Admin        :" + auth);
			System.out.println(" Active       :" + status);
		}
	}
	
	
	public void deleteUser(String email) {
		
		uc.deleteUser(email);
		
	}
	
	public void createUser() 
	{
		User user = new User();
		char status, auth;
		
		System.out.println("|===========================================|");
		System.out.println("|        IF5021 MEETING SCHEDULER           |");
		System.out.println("|===========================================|");
		System.out.println("|CREATE USER                                |");
		System.out.println("|===========================================|");
		System.out.print("  First Name       :"); user.setFirstName(scan.nextLine()); 
		System.out.print("  Last Name        :"); user.setLastName(scan.nextLine());
		System.out.print("  Address          :"); user.setAddress(scan.nextLine());
		System.out.print("  Phone            :"); user.setPhone(scan.nextLine());
		System.out.print("  DOB              :"); user.setDob(scan.nextLine());
		System.out.print("  Sex              :"); user.setSex(scan.next(".").charAt(0));
		scan.nextLine();
		System.out.print("  Email            :"); user.setEmail(scan.nextLine());
		System.out.print("  Password         :"); user.setPassword(scan.nextLine());
		System.out.print("|is Active ? [Y/N] :"); status = scan.next(".").charAt(0);
		scan.nextLine();
		System.out.print("|is Admin ? [Y/N]  :"); auth = scan.next(".").charAt(0);
		scan.nextLine();
		
		if (status=='Y' || status=='y') {
			user.setActive(true);
		} else {
			user.setActive(false);
		}
		
		if (auth=='Y' || auth=='y') {
			user.setAdmin(true);
		} else {
			user.setAdmin(false);
		}
		
		
		uc.add(user);
		uc.save();
	}
	
	public void editUser(String email) {
		User user = new User();
		char status, auth;
		System.out.println("|===========================================|");
		System.out.println("|        IF5021 MEETING SCHEDULER           |");
		System.out.println("|===========================================|");
		System.out.println("|EDIT USER                                  |");
		System.out.println("|===========================================|");
		System.out.print("|First Name        :"); user.setFirstName(scan.nextLine()); 
		System.out.print("|Last Name         :"); user.setLastName(scan.nextLine());
		System.out.print("|Address           :"); user.setAddress(scan.nextLine());
		System.out.print("|Phone             :"); user.setPhone(scan.nextLine());
		System.out.print("|DOB               :"); user.setDob(scan.nextLine());
		System.out.print("|Sex               :"); user.setSex(scan.next(".").charAt(0));
		scan.nextLine();
		user.setEmail(email);
		System.out.print("|Password          :"); user.setPassword(scan.nextLine());
		System.out.print("|is Active ? [Y/N] :"); status = scan.next(".").charAt(0);
		scan.nextLine();
		System.out.print("|is Admin ? [Y/N]  :"); auth = scan.next(".").charAt(0);
		scan.nextLine();
		
		if (status=='Y' || status=='y') {
			user.setActive(true);
		} else {
			user.setActive(false);
		}
		
		if (auth=='Y' || auth=='y') {
			user.setAdmin(true);
		} else {
			user.setAdmin(false);
		}
		
		uc.editUser(user, email);
	
	}
	
	public void findUser(String email) {
		//String email;
		System.out.print("Masukkan email :");
		
		email = scan.nextLine();
		
		User user = uc.findUserByEmail(email);
		
		if(user != null ) {
			
			System.out.println("|===========================================|");
			System.out.println("|        IF5021 MEETING SCHEDULER           |");
			System.out.println("|===========================================|");
			System.out.println("|VIEW DETAIL                                |");
			System.out.println("|===========================================|");
			System.out.println(" First Name	\t:" + user.getFirstName());  
			System.out.println(" Last Name 	\t:" + user.getLastName()); 
			System.out.println(" Address	\t:" + user.getAddress()); 
			System.out.println(" Phone	\t\t:" + user.getPhone());
			System.out.println(" DOB	\t\t:" + user.getDob()); 
			System.out.println(" Sex	\t\t:" + user.getSex()); 
			System.out.println(" Email	\t\t:" + user.getEmail());
			String status;
			if (user.isActive()==true) {
				status = "Active";
			} else {
				status = "Non-Active";
			}
			System.out.println(" Status	\t\t:" + status);
			System.out.println("--------------------------------------------");
			System.out.println("[B/b] Back");
			System.out.println("--------------------------------------------");
			
			char back;
			MenuView menuView = new MenuView();
			
			back = scan.next(".").charAt(0);
			
			if (back=='B' || back=='b') {
				menuView.menuHome();
			} else {
				System.out.println("Unknown character...!");
			}
			
			
		} else {
			System.out.println("Data not found!!");
		}
	}
		
	public void displayLogin() {
		System.out.println("let's say user has been authenticated");
		System.out.println();
		//call displayMenu(User);
		displayMenu(new User("jeje@gmail.com", "12344"));
	}
	
	public void displayMenu(User user) {
		MenuView menuView = new MenuView();
		if(user.isAdmin()) {
			menuView.menuHome();
		} else {
			displayUserMainMenu(user);
		}
	}
	
	public void displayUserMainMenu(User user) {
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
			System.out.println("6. Edit Profile");
			System.out.println("7. Log out and exit");
			System.out.println("--------------------------------");
			System.out.print("Enter your choice: ");
			try {
				choice = scan.nextInt();
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
				case 7: break;
				default: System.out.println("Menu not found");
			}
		} while(choice != 7);
	}
	
}
