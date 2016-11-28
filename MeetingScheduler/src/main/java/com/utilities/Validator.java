package com.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * @author jessiesetiady
 *
 */
public class Validator {
	
	public String getAndValidateInput(Scanner s, String label, String type) {
		int test;
		String input, regex = ".", errorMsg = ".";
		String[] daterange;
		
		
		if(type.equals("date")) { //format: dd/mm/yyyy
			regex = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
			errorMsg = "  Err: Invalid date format. Please re-enter.";
		}
		else if(type.equals("dob")) { //format: dd/mm/yyyy
			regex = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
			errorMsg = "  Err: Invalid date format. Please re-enter.";
		}
		else if(type.equals("duration")) { //positive integer between 1-8
			regex = "^[1-9]+$";
			errorMsg = "  Err: Please enter a value between 1-9";
		}
		else if(type.equals("time")) { //format: hh:mm
			regex = "[0-9]{2}:[0-9]{2}";
			errorMsg = "  Err: Invalid time format. Please re-enter.";
		}
		else if(type.equals("YN")) {
			regex = "[NYny]{1}";
			errorMsg = "  Err: Please enter either Y or N character";
		}
		else if(type.equals("MF")) {
			regex = "[MFmf]{1}";
			errorMsg = "  Err: Please enter either M or F character";
		}
		else if(type.equals("text")) {
			regex = "^(?!\\s*$).+";
			errorMsg = "  Err: This field cannot be empty";
		}
		else if(type.equals("number")) {
			regex = "[0-9]";
			errorMsg = "  Err: Invalid number format. Please re-enter.";
		}
		else if(type.equals("email")) {
			regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			errorMsg = "  Err: Invalid email format, please re-enter";
		}
		else if(type.equals("daterange")) {
			//a.replaceAll("\\s+","");
			regex = "[0-9]{2}/[0-9]{2}/[0-9]{4}\\s-\\s[0-9]{2}/[0-9]{2}/[0-9]{4}";
			errorMsg = "  Err: Invalid date range format, please re-enter. Format: dd/mm/yyyy - dd/mm/yyyy";
		}
		else if(type.equals("participant")) {
			regex = "(^$|^.*@.*\\..*$)";
			errorMsg = "  Err: Invalid participant list format";
		}
		
		do {
			test = 1;
			System.out.print(label);
			input = s.nextLine();
			
			if (!input.matches(regex)) {
				System.out.println(errorMsg);
				test = 0;
			} else {
				if(type.equals("daterange")) {
					daterange = input.replaceAll("\\s","").split("-");
					
					if(isValidDate(daterange[0]) && isValidDate(daterange[1])) {
						if(strToDate(daterange[0]).before(getSpecificDate("today", 1))) {
							System.out.println("  Err: Invalid start date"); //date[0] before today+2
							test = 0;
						}
						if(strToDate(daterange[1]).before(strToDate(daterange[0]))) {
							System.out.println("  Err: Invalid end date"); //date[0] before today+2
							test = 0;
						}
					} else {
						System.out.println("  Err: Invalid date value. Please re-enter");
						test = 0;
					}
				} else if(type.equals("date")) {
					if(!isValidDate(input)) {
						System.out.println("  Err: Invalid date value. Please re-enter");
						test = 0;
					}
				}
				
			}
		} while (test == 0);
		return input;
	}
	
	
	public boolean isValidNumber(String number) {
		String regex = "^[0-9]*";
		return number.matches(regex);
	}
	
	public boolean isValidEmail(String strEmail) {
		String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		return strEmail.matches(regex);
	}
	
	public String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);

	}
	
	public String getAndValidateInput(Scanner s, String label, String type, String dr) {
		String input;
		String[] daterange;
		Date min, max, d;
		int test;
		
		do {
			test = 1;
			input = getAndValidateInput(s, label, type);
			d = strToDate(input);
			
			daterange = dr.replaceAll("\\s","").split("-");
			min = strToDate(daterange[0]);
			max = strToDate(daterange[1]);
			
			if(!(min.getTime() <= d.getTime() && d.getTime() <= max.getTime())) {
				System.out.println("  Negotiation date must between the proposed date range. Please re-enter");
				test = 0;
			}
		} while(test == 0);
		
		return input;
	}
	
	public boolean isValidDate(String dateString)
	{
	    // Parse the date parts to integers
	    String[] parts = dateString.split("/");
	    int day = Integer.parseInt(parts[0]);
	    int month = Integer.parseInt(parts[1]);
	    int year = Integer.parseInt(parts[2]);

	    // Check the ranges of month and year
	    if(year < 2016 || year > 3000 || month == 0 || month > 12)
	        return false;

	    double[] monthLength = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	    
	    // Adjust for leap years
	    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
	        monthLength[1] = 29;

	    // Check the range of the day
	    return day > 0 && day <= monthLength[month - 1];
	   
	}
	
	public Date strToDate(String startDateString) {
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	    Date startDate = null;
	    try {
	        startDate = df.parse(startDateString);
	    } catch (ParseException e) {
	    	System.out.println("Not a valid date");
	        //e.printStackTrace();
	    }
	    return startDate;
	}
	
	public Date strToDateTime(String startDateString) {
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm"); 
	    Date startDate = null;
	    try {
	        startDate = df.parse(startDateString);
	    } catch (ParseException e) {
	    	System.out.println("Not a valid date");
	        //e.printStackTrace();
	    }
	    return startDate;
	}
	
	public String dateToString(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String reportDate = df.format(date);
		return reportDate;
	}
	
	public Date getSpecificDate(String startDate, int c) {
		Calendar calendar;
		Date date = null;
		if(startDate.equals("today")) {
			calendar = Calendar.getInstance(); // starts with today's date and time
			calendar.add(Calendar.DAY_OF_YEAR, c);  // advances day by c
			date = calendar.getTime(); // gets modified time
		} else {
			date = strToDate(startDate);
		    calendar = new GregorianCalendar();
		    calendar.setTime(date);
		    calendar.add(Calendar.DAY_OF_MONTH, c);
		    date = calendar.getTime();
		}
		return date;
	}
	
}


