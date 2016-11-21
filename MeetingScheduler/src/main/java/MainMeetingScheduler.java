import java.util.Scanner;

import com.controller.user.UserController;
import com.model.user.User;
import com.view.user.MenuView;
import com.view.user.UserView;

/**
 * @author jessiesetiady
 *
 */
public class MainMeetingScheduler {

	
	private static Scanner scan;

	public static void main(String args[]) {
		scan = new Scanner(System.in);
		int choice;
		MenuView menu = new MenuView();
		UserView userView = new UserView();
		UserController controller = new UserController();
		//controller.createDummyUser();
		//controller.save();
		
		menu.menuLogin();
		/** 1. find User by email
		 * 2. Jika ketemu, return nya User else null
		 * 3. Jika returnnya user, cek user.getPassword() == password ?
		 * 4. Jika benar, masuk ke menu admin, jika salah balik ke form login lagi
		 */
		
		
		//System.out.println(user.getEmail() + " " + user.getPassword());
		
		//controller.findUser(user.getEmail());
		
		//userView.displayMenu(user);
		//userView.displayUserMainMenu(user);
		System.out.print("Type your choice :");
		do {
			choice = scan.nextInt();
			switch(choice) {
			case 1:
				//@putra
				//call menu for manage user
				controller.add(userView.createUser());
				int option;
				option = scan.nextInt();
				if (option==1) {
					controller.add(userView.createUser());
				} else if (option==2) {
					controller.save();

					menu.menuHome();
				} else if (option==2) {
					menu.menuHome();

				}
				break;
			case 2:
				//controller.getAllUser().stream().forEach(System.out::println);
				userView.showListUser(controller.getAllUser());
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
		} while (choice != 4);
		
		
		
		
		
	}
	
	
	
	/*
	public static void main(String args[]) {
		//variable initialization
		Scanner scanner = new Scanner(System.in);
		String email, password;
		int choice;
		
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
>>>>>>> staging
	}
	
	*/
}
