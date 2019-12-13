package application.model;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.concurrent.Task;
/**
 * @author Richard Michael Galindo
 * Time class is a class for the calender to utilize to get values
 * The class utilizes the java api gregorianCalendar
 */

public class Time extends Task<String>{
	private static Calendar cali = new GregorianCalendar();
	
	/**
	 * The days method returns a string of the name of the day based on the int value 
	 * @param days is a number from 1 - 7 representing the day of the week
	 * @return a String which is the name of the Day of the Week
	 */
	public static String days(int day) {
		String strDay = "";
		switch(day) {
			case 1:	strDay = "Sun";
				break;
			case 2: strDay = "Mon";
				break;
			case 3:	strDay = "Tue";
				break;
			case 4:	strDay = "Wed";
				break;
			case 5:	strDay = "Thu";
				break;
			case 6:	strDay = "Fri";
				break;
			case 7:	strDay = "Sat";
				break;
		}
		return strDay;
	}
	    /** month
         * returns a string of the name of the month based on the int value 
         * @param month: int 0-11
         * @returns strMonth: String*/

	public static String month(int month){
		String strMonth = " ";
		month--;
		switch(month) {
			case 0: strMonth = "January";
				break;
			case 1: strMonth = "Febuary";
				break;
			case 2: strMonth = "March";
				break;
			case 3: strMonth = "April";
				break;
			case 4: strMonth = "May";
				break;
			case 5: strMonth = "June";
				break;
			case 6: strMonth = "July";
				break;
			case 7:	strMonth = "August";
				break;
			case 8: strMonth = "September";
				break;
			case 9: strMonth = "October";
				break;
			case 10: strMonth = "November";
				break;
			case 11: strMonth = "December";
				break;
		}	
		return strMonth;
	}
	/**
	 * abbrevMonth
	 * based on the int value submitted this function will return a string
	 * abbreviating of the month the value corresponds to
	 * @param Month: int
	 * @return strMonth: String 
	 */
	public static String abbrevMonth(int Month){
		String strMonth = " ";
		Month--;
		switch(Month) {
			case 0: strMonth = "Jan";
				break;
			case 1: strMonth = "Feb";
				break;
			case 2: strMonth = "Mar";
				break;
			case 3: strMonth = "Apr";
				break;
			case 4: strMonth = "May";
				break;
			case 5: strMonth = "Jun";
				break;
			case 6: strMonth = "Jul";
				break;
			case 7:	strMonth = "Aug";
				break;
			case 8: strMonth = "Sep";
				break;
			case 9: strMonth = "Oct";
				break;
			case 10: strMonth = "Nov";
				break;
			case 11: strMonth = "Dec";
				break;
		}	
		return strMonth;
	}
	/**revAbbrevMonth
	 * this method takes in an abbreviated string of the month
	 * then returns the int value associated with the abbreviation
	 * @param month:String
	 * @return 1-12:int
	 */
	public static int revAbbrevMonth(String month) {
		switch(month) {
			case "Jan": return 1;
			case "Feb": return 2;
			case "Mar": return 3;
			case "Apr": return 4;
			case "May": return 5;
			case "Jun": return 6;
			case "Jul": return 7;
			case "Aug": return 8;
			case "Sep": return 9;
			case "Oct": return 10;
			case "Nov": return 11;
			case "Dec": return 12;
		}
		return -1;
	}
	/**
	 * numDays
	 * retuens the number of days for a month given the abbrevMonth name
	 * @param abbrevMonth: String
	 * @return int: temp.getActualMaximum(Calendar.DAY_OF_MONTH)
	 */
	public static int numDays(String abbrevMonth) {
		int month = revAbbrevMonth(abbrevMonth);
		GregorianCalendar temp = (GregorianCalendar) cali.clone();
		temp.set(Calendar.MONTH, month - 1);
		return temp.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * getCurrentMonth
	 * returns the current month int value
	 * @return cali.get(Calendar.Month)+1: int
	 */
	public static int getCurrentMonth() {
		cali = new GregorianCalendar();
		return cali.get(Calendar.MONTH) + 1;
	}
	/**
	 * getCurrentYear
	 * returns the current year
	 * @return cali.get(Calendar.YEAR)
	 */
	public static int getCurrentYear() {
		cali = new GregorianCalendar();
		return cali.get(Calendar.YEAR);
	}
	
	/**
	 * time
	 * returns the string value of the current mont day year hour minute am or pm
	 * @return time: String
	 */  
	public static String time(){
		cali = new GregorianCalendar();
		int hour = cali.get(Calendar.HOUR) == 0 ? 12 : cali.get(Calendar.HOUR);
		int minute = cali.get(Calendar.MINUTE);
		String amPM = cali.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
		String month = abbrevMonth(cali.get(Calendar.MONTH) + 1);
		int day = cali.get(Calendar.DAY_OF_MONTH);
		int year = cali.get(Calendar.YEAR);
		return String.format("%s %d, %d  %d:%02d %s", month, day, year, hour, minute, amPM);
	}
	/**
	 * dayOfWeek
	 * returns the day of the week based on the three parameters enterd
	 * @param month: int
	 * @param day: int
	 * @param year: int
	 */
	public static int dayOfWeek(int month, int day, int year) {
		GregorianCalendar temp = (GregorianCalendar) cali.clone();
		temp.set(year, month - 1, day);
		return temp.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * The call method runs when Time is ran as a Thread
	 * @return a String which is the value when being called
	 */
	@Override
	protected String call() throws Exception {
		// TODO Auto-generated method stub
		
		boolean flag = true;
		while(flag) {
			updateMessage(time());
			Thread.sleep(1000);
		}
		return null;
	}
	/**
	 * The getCali method returns a Calendar object
	 * @return a Calendar object
	 */
	public static Calendar getCali() {
		return cali;
	}
	/**
	 * The setCali method sets cali to a new Calendar object
	 * @param cali is the new Calendar object
	 */
	public static void setCali(Calendar cali) {
		Time.cali = cali;
	}
	
}
