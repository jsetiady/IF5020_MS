package com.view.user;

import java.util.Scanner;

import com.model.user.User;

public class MenuView {
	
	Scanner scan = new Scanner(System.in);
	
	public User menuLogin() {
		User user = new User();
		System.out.println("|===========================================");
		System.out.println("|        IF5021 MEETING SCHEDULER           ");
		System.out.println("|===========================================");
		System.out.println("|LOGIN                                      ");
		System.out.println("|===========================================");
		System.out.print("|Username		|"); user.setEmail(scan.nextLine());
		System.out.print("|Password		|"); user.setPassword(scan.nextLine());
		System.out.println("|-------------------------------------------");
		System.out.println("");
		
		return user;
	}
	
	public int menuHome() {
		int choice;
		
		System.out.println("|===========================================|");
		System.out.println("|        IF5021 MEETING SCHEDULER           |");
		System.out.println("|===========================================|");
		System.out.println("|MENU UTAMA                                 |");
		System.out.println("|===========================================|");
		System.out.println("| [1] Create user --------------------------|");
		System.out.println("| [2] Show User List -----------------------|");
		System.out.println("| [3] Find User ----------------------------|");
		System.out.println("| [4] Logout -------------------------------|");
		System.out.println("|-------------------------------------------|");
		System.out.print("| Enter your choice: "); choice = scan.nextInt();
		System.out.println("");
		
		return choice;
	}
}
