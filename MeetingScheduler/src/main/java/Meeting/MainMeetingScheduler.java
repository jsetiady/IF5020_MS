package Meeting;

/*
 * Description: 
 */

import java.util.Scanner;

import com.model.User;

public class MainMeetingScheduler {

	public static void main(String args[]) {
		//variable initialization
		Scanner scanner = new Scanner(System.in);
		String email, password;
		int choise;
		
		System.out.println("Welcome to meeting scheduler");
		
		//@putra, please implements Log -in logic here :)
		
		
		//temporary
		System.out.println("Enter username: ");
		email = scanner.nextLine();
		System.out.println("Enter password: ");
		password = scanner.nextLine();
		System.out.println("Press enter to continue");
		scanner.nextLine();
		
		User user = new User(email, password);
		
		if (user.isAuth(email, password)) {
			System.out.println("Hey " + email + " You are logged in as Superuser!");
		} else {
			System.out.println("Unauthorized user");
		}
		
		System.out.println("Wow, You have 2 pending meeting invitation! ");
		System.out.println(" ");
		System.out.println("================================");
		System.out.println("1. Manage user");
		System.out.println("2. Create Meeting");
		System.out.println("3. View Created Meeting");
		System.out.println("4. View Your Schedule");
		System.out.println("5. View Meeting Invitation");
		System.out.print("Enter your choise: ");
		choise = scanner.nextInt();
		
		switch(choise) {
			case 1:
				//@putra
				//call menu for manage user
				System.out.println("not yet available");
				break;
			case 2:
				System.out.println("not yet available");
				break;
			case 3:
				System.out.println("not yet available");
				break;
			case 4:
				System.out.println("not yet available");
				break;
			case 5:
				System.out.println("not yet available");
				break;
			default:
				System.out.println("menu not found!");
		}
		
		System.out.println("Thank you for using IF5021 Meeting Scheduler!");
		
		
	}
}
