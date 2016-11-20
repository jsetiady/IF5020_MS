import java.util.Scanner;

import com.controller.user.UserController;
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
		controller.createDummyUser();
		controller.save();
		
		menu.menuLogin();
		
		do {
			System.out.println("Please type your choice...");
			choice = scan.nextInt();
			switch(choice) {
			case 1:
				//@putra
				//call menu for manage user
				controller.add(userView.createUser());
				int option;
				option = scan.nextInt();
				scan.nextLine();
				if (option==1) {
					//controller.add(userView.createUser());
					controller.save();
				} else if (option==2) {
					menu.menuHome();
				}
				break;
			case 2:
				userView.showListUser(controller.getAllUser());
				break;
			case 3:
				userView.findUser();
				break;
			case 4:
				System.out.println("Thanks for using IF5021 Meeting Scheduler");
				menu.menuLogin();
				break;
			default:
				System.out.println("menu not found!");
			}
		} while (choice != 4);
			
	}
}
