package com.view.user;

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
	
	public void showListUser(List<User> listUser) {
		listUser = uc.getAllUser();
		int choice;
		
		do {
			System.out.println("|======================================================================================================================================|");
			System.out.println("|                                                       IF5021 MEETING SCHEDULER                                                       |");
			System.out.println("|======================================================================================================================================|");
			System.out.println("|LIST USER-----------------------------------------------------------------------------------------------------------------------------|");
			System.out.println("|======================================================================================================================================|");
			System.out.println("|Full Name\t\t\t|Address\t\t|Phone\t\t\t|DOB\t\t\t|Sex\t\t|Email\t\t                                                                   |");
			System.out.println("|--------------------------------------------------------------------------------------------------------------------------------------|");
			for (User usr: listUser) {
				System.out.println("|"+usr.getFirstName()+" "+usr.getLastName()+ "\t\t|"+usr.getAddress()+"\t\t|"+usr.getPhone()+"\t\t|"+usr.getDob()+"\t\t|"+usr.getSex()+"\t\t|"+usr.getEmail());
			}
			System.out.println("|--------------------------------------------------------------------------------------------------------------------------------------|");
			//System.out.println("[1] Add User");
			System.out.println("[1] Edit User");
			System.out.println("[2] View Detail User");
			System.out.println("[3] Back");
			System.out.println("|--------------------------------------------------------------------------------------------------------------------------------------|");
			
			
			System.out.print("Enter your choice: ");
			
			MenuView menuView = new MenuView();
			choice = scan.nextInt();
			
			switch(choice) {
				//case 1: createUser(); break;
				//case 1: editUser(); break;
				case 2: System.out.println("View Detail User"); break;
				case 3: menuView.menuHome(); break;
				default: break;
			}
		} while (choice != 3);
		
		
	}
	
	public void viewDetailUser (User user) {
		System.out.println("First Name :" + user.getFirstName());
		System.out.println("Last Name :" + user.getLastName());
		System.out.println("Address :" + user.getAddress());
		System.out.println("Phone :" + user.getPhone());
	}
	
	public void editUser(String email) {
		/*
		 * 1. Minta input data user (Misalnya minta email yang akan diubah)
		 * 2. Cari user menggunakan finduser menggunakan email yang mau diubah (controller)
		 * 3. Jalankan method edit untuk menyimpan ke file json (controller)
		 * */
		email = scan.nextLine();

		
		System.out.println("Edit User");
	}
	
	public void delete() {
		String email;
		
		/**
		 * 1. Minta input email
		 * 2. Jalankan method delete 
		 * 3. (get List User dari json, looping, if (user:users), users.remove(user), simpan ke json
		 * 
		 */
		System.out.println("Delete user");
		
	}
	
	public User createUser() 
	{
		User user = new User();
		scan.nextLine();
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
		scan.nextLine();
		System.out.println("--------------------------------------------|");
		System.out.println("[1] Save");
		System.out.println("[2] Back");
		System.out.println("--------------------------------------------|");
		
		
		return user;
		
	}
	
	public void findUser() {
		String email;
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
