package com.view.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.controller.user.UserController;
import com.model.user.User;
import com.utilities.Validator;
import com.view.meeting.MeetingView;

/**
 * @author jessiesetiady
 *
 */
public class UserView {
	Scanner scan = new Scanner(System.in);
	UserController uc = new UserController();
	Validator val = new Validator();
	
	public User login(String email, String password) {
		return uc.checkLogin(email, password);
	}
	
	public boolean convertStringToBool(String x) {
		if (x.equals("Y")) {
			return true;
		} else {
			return false;
		}
	}
	
	public char convertStringToChar(String x) {
		if (x.equals("M")) {
			return 'M';
		} else {
			return 'F';
		}
	}
	
	public void showListUser() {
		List<User> listUser = new ArrayList<User>();
		listUser = uc.getAllUser();
		
		System.out.println("+------------------------------------------------------------------------------------------------------------------------------+");
		System.out.println("|LIST USER                                                                                                                     |");
		System.out.println("+------------------------------------------------------------------------------------------------------------------------------+");
		System.out.println("|First Name          |LastName       |Address             |Phone          |DOB       |  Sex |      Email    |is Active|is Admin|");
		System.out.println("+------------------------------------------------------------------------------------------------------------------------------+");
		for (User usr: listUser) {
			System.out.printf(" %-20s %-15s %-20s %-15s %-12s %-4s %-18s %-8s %-4s",usr.getFirstName(), usr.getLastName(), usr.getAddress(), usr.getPhone(), usr.getDob(), usr.getSex(), usr.getEmail(), usr.isActive(), usr.isAdmin());
			System.out.println();
		}
		System.out.println("+------------------------------------------------------------------------------------------------------------------------------+");
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
			System.out.println("+-------------------------------------------- +");
		}
	}
	
	
	public void deleteUser(String email) {
		String choice;
		if (uc.findUserByEmail(email)!=null) {
			
			choice = val.getAndValidateInput(scan, "Are you sure to delete this user ? [Y/N]", "YN");
			
			if (choice.equals("Y")) {
				uc.deleteUser(email);
				System.out.println("Data has been deleted succesfully...");
			} else {
				System.out.println("Delete user has been canceled");
			}
			
		} else {
			System.out.println("Email doesn't exist...!");
		}
		
	}
	
	public void createUser() 
	{
		User user = new User();
		String firstName, lastName, address, phone, dob, sex, email, password, isActive, isAdmin;
		
		System.out.println("+-------------------------------------------+");
		System.out.println("|CREATE USER                                |");
		System.out.println("+-------------------------------------------+");
		email = val.getAndValidateInput(scan, "  Email             :", "email");
		if (uc.findUserByEmail(email)==null) {
			
			firstName = val.getAndValidateInput(scan, "  First Name        :", "text");
			lastName = val.getAndValidateInput(scan, "  Last Name         :", "text");
			address = val.getAndValidateInput(scan, "  Address           :", "text");
			phone = val.getAndValidateInput(scan, "  Phone             :", "text");
			dob = val.getAndValidateInput(scan, "  DOB [dd/mm/yyy]   :", "dob");
			sex = val.getAndValidateInput(scan, "  Sex [M/F]         :", "MF");
			isActive = val.getAndValidateInput(scan, "  is Active ? [Y/N] :", "YN");
			isAdmin = val.getAndValidateInput(scan, "  is Admin ? [Y/N]  :", "YN");
			password = val.getAndValidateInput(scan, "  Password          :", "text");
			
			user.setEmail(email);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setAddress(address);
			user.setPhone(phone);
			user.setDob(dob);
			user.setSex(convertStringToChar(sex));
			user.setActive(convertStringToBool(isActive));
			user.setAdmin(convertStringToBool(isAdmin));
			
			
			uc.add(user);
			uc.save();
			System.out.println();
			System.out.println("  Data has been saved succesfully...");
			
		} else {
			System.out.println("Email already exist, please use another email!");
		}
	}
	
	public void editUser(String email) {
		User user = new User();
		String firstName, lastName, address, phone, dob, sex, password, isActive, isAdmin;
		
		
		if (uc.findUserByEmail(email)!=null) {
			System.out.println("+-------------------------------------------+");
			System.out.println("|EDIT USER                                  |");
			System.out.println("+-------------------------------------------+");
			firstName = val.getAndValidateInput(scan, "  First Name        :", "text");
			lastName = val.getAndValidateInput(scan, "  Last Name         :", "text");
			address = val.getAndValidateInput(scan, "  Address           :", "text");
			phone = val.getAndValidateInput(scan, "  Phone             :", "text");
			dob = val.getAndValidateInput(scan, "  DOB [dd/mm/yyy]   :", "dob");
			sex = val.getAndValidateInput(scan, "  Sex [M/F]         :", "MF");
			isActive = val.getAndValidateInput(scan, "  is Active ? [Y/N] :", "YN");
			isAdmin = val.getAndValidateInput(scan, "  is Admin ? [Y/N]  :", "YN");
			password = val.getAndValidateInput(scan, "  Password          :", "text");
			
			user.setEmail(email);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setAddress(address);
			user.setPhone(phone);
			user.setDob(dob);
			user.setSex(convertStringToChar(sex));
			user.setActive(convertStringToBool(isActive));
			user.setAdmin(convertStringToBool(isAdmin));
			
			uc.editUser(user, email);
			System.out.println("  Data has been updated succesfully...");
			
		} else {
			System.out.println("Email doesn't exist...!");
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
