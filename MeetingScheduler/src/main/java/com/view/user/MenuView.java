package com.view.user;

import java.util.Scanner;

import com.controller.user.UserController;
import com.model.user.User;

public class MenuView {
	
	Scanner scan = new Scanner(System.in);
	UserView userView = new UserView();
	
	UserController uc = new UserController();
	
	public void menuLogin() {
		int test;
		do {
			test = 1;
			User user = new User();
			System.out.println("|===========================================");
			System.out.println("|        IF5021 MEETING SCHEDULER           |");
			System.out.println("|===========================================");
			System.out.println("|LOGIN                                      |");
			System.out.println("|===========================================");
			System.out.print(" Username \t:"); user.setEmail(scan.nextLine());
			System.out.print(" Password \t:"); user.setPassword(scan.nextLine());
			System.out.println("");
			
			
			if(uc.checkLogin(user.getEmail(), user.getPassword()) != null) {
				userView.displayMenu(uc.checkLogin(user.getEmail(), user.getPassword()));
				test=0;
			} else {
				System.out.println("email not found");
			}
		} while(test==1);
		
	}
	
	public void menuHome() {
		
		System.out.println("|===========================================|");
		System.out.println("|        IF5021 MEETING SCHEDULER           |");
		System.out.println("|===========================================|");
		System.out.println("|ADMIN MENU                                 |");
		System.out.println("|===========================================|");
		System.out.println("| [1] Create user --------------------------|");
		System.out.println("| [2] Show User List -----------------------|");
		System.out.println("| [3] Find User ----------------------------|");
		System.out.println("| [4] Logout -------------------------------|");
		System.out.println("|-------------------------------------------|");
		System.out.println("");
		

	}
}
