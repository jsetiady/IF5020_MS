import java.util.Scanner;

import com.model.user.User;
import com.view.meeting.MeetingView;
import com.view.user.UserView;

/**
 * @author jessiesetiady
 *
 */
public class NewInterfaceMain {
	
	private static boolean exit = false;
	
	public static void showHelp(int role) {
		switch(role) {
			case 1: 
				System.out.println("Administrator command");
				System.out.println("---------------------");
				System.out.println("list-user");
				System.out.println("detail-user <email>");
				System.out.println("add-user");
				System.out.println("edit-user <email>");
				System.out.println("del-user <email>");
				System.out.println();
				break;
			case 2:
				System.out.println("Initiator command");
				System.out.println("---------------------");
				System.out.println("create-meeting");
				System.out.println("list-meeting");
				System.out.println("detail-meeting <meeting-id>");
				System.out.println("edit-meeting <meeting-id>");
				System.out.println("cancel-meeting <meeting-id>");
				System.out.println("run scheduler <meeting-id>");
				System.out.println();
			break;
			case 3:
				System.out.println("Participant command");
				System.out.println("---------------------");
				System.out.println("list-invitation");
				System.out.println("detail-invitation <meeting-id>");
				System.out.println("accept-invitation <meeting-id>");
				System.out.println("reject-invitation <meeting-id>");
				break;
		}
	}
	
	public static boolean checkCommandRole(int expectedRole, int actualRole) {
		if(expectedRole == actualRole) {
			return true;
		}
		return false;
	}
	
	public static void showErrorPrivilegeCommand() {
		System.out.println("You do not have privilege to execute this command");
	}
		
	public static void processMenu(String command, String email, int role) {
		MeetingView mv = new MeetingView();
		UserView uv = new UserView();
		String[] cmd = command.split(" ");
		boolean test;
		switch(cmd[0]) {
			case "list-user":
				if (checkCommandRole(1, role))
					uv.showListUser();
				else
					showErrorPrivilegeCommand();
				break;
			case "add-user":
				if (checkCommandRole(1, role))
					uv.createUser();
				else
					showErrorPrivilegeCommand();
				break;
			case "detail-user":
				if (checkCommandRole(1, role))
					uv.viewUserByEmail(cmd[1]);
				else
					showErrorPrivilegeCommand();
				break;
				
			case "edit-user": break;
			case "del-user": break;
			
			case "create-meeting" :
				if (checkCommandRole(1, role))
					mv.createMeetingView(email);
				else
					showErrorPrivilegeCommand();
				break;
			case "list-meeting" : 
				if (checkCommandRole(1, role))
					mv.displayCreatedMeeting(email);
				else
					showErrorPrivilegeCommand();
				break;
			case "detail-meeting" :
				if (checkCommandRole(1, role))
					mv.viewMeetingByID(cmd[1]);
				else
					showErrorPrivilegeCommand();
				break;
			case "edit-meeting <meeting-id>" : break;
			case "cancel-meeting <meeting-id>" : break;
			case "run scheduler <meeting-id>" : break;
			
			case "list-invitation" : break;
			case "detail-invitation <meeting-id>" : break;
			case "accept-invitation <meeting-id>" : break;
			case "reject-invitation <meeting-id>" : break;
			
			case "help" : showHelp(role); break;
			case "exit" : break;
			
			default: System.out.println("Command does not exist"); break;
		}
	}
	
	public static String roleToString(int role) {
		switch(role) {
			case 1: return "Administrator";
			case 2: return "Meeting initiator";
			case 3: return "Meeting participant";
		}
		return "";
	}
	
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);
		UserView uv = new UserView();
		User user;
		String email, password, command;
		int role;
		boolean login = false;
		
		System.out.println("Welcome to Meeting Scheduler");
		do {
			System.out.print("Please enter your email\t\t: "); email = s.nextLine();
			System.out.print("Please enter your password\t: "); password = s.nextLine();
			user = uv.login(email, password);
			if(user!=null) {
				login = true;
			} else {
				System.out.println("## wrong email or password ##");
			}
		} while(!login);
		
		login = false;
		do {
			do {
			System.out.print("Please select a role:\n1.administrator\n2.initiator\n3.participant\n> ");role = s.nextInt(); s.nextLine();
				if(role<1 || role>3) {
					System.out.println("## Role does not exist, please re-enter. ##");
				}
			} while(role<1 || role>3);
			
			if(role==1) {
				if(user.isAdmin()) {
					login = true;
				} else {
					System.out.println("## You don't have Admin privilege. Please choose another role. ##");
				}
			} else {
				login = true;
			}
			
			if(login) {
				System.out.println("\nYou have signed in as a " + roleToString(role) + ".");
				System.out.println("Waiting for your command...\n");	
			}
		} while(!login);
		do {
			System.out.print("> "); command = s.nextLine();
			processMenu(command, email, role);
		} while(!command.equals("exit"));
	}
	
}
