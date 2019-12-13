package application.model;

import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author Richard Michael Galindo
 * Month class creates a month object 
 * the Month object contains 4 data types
 */
public class Month {
	private ArrayList<Day> days;
	private String monthName;
	private int year;
	private Budget budget;
	/**
	 * Month constructor creates  a Month object
	 * @param name: String
	 * @param year: int
	 */
	public Month (String name, int year) {
		days = new ArrayList<Day>();
		budget = new Budget();
		monthName = name;
		this.year = year;
		for(int i = 1; i <= Time.numDays(monthName); i++) {
			addDay(monthName, i, year);
		}
	}
	/**
	 *SetBudgetIncome sets the income for the Budget
	 * @param income:double
	 */
	public void setBudgetIncome(double income) {
		System.out.println("Log: Set a new Budget Income");
		budget.setCurrBudget(income);
		Calendar.updateBudgetProperty();
	}
	/**
	 * getBudget
	 * @returns budget for the month
	 * */
	public Budget getBudget() {
		return budget;
	}
	/**
	 * setBudget sets the budget for this month object
	 * @param budget:Budget
	 * */
	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	/** 
	 * getDays returns and arrayList of Day objects for this month
	 * @return days:ArrayList<Day>
	 * */
	public ArrayList<Day> getDays() {
		return days;
	}
	/**
	 * getMonthName
	 * @returns monthName:String
	 */
	public String getMonthName() {
		return monthName;
	}
	/**
	 * saveMonth
	 * saves the data to a file for this particular month
	 */
	public void saveMonth() {
		budget.saveBudget();
		for(Day day: days) {
			day.saveDay();
		}
	}
	/**
	 * loadMonth
	 * loads into a Month object the data for the Month from a file
	 * @param path:String file path
	 * @param income: double
	 */
	public void loadMonth(String path, double income) {
		budget.loadBudget(path, income);
		for(Day day: days) {
			day.loadDay();
		}
	}
	/**
	 * addDay
	 * adds a day to the month object
	 * @param month:String
	 * @param day:int
	 * @param year:int
	 */
	public void addDay(String month, int day, int year) {
		days.add(new Day(month, day, year));
	}
	/**
	 * getYear
	 * @return year:int
	 */
	public int getYear() {
		return year;
	}
	/**
	 * setYear
	 * sets the year for this month
	 * @param year: int
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * setDays
	 * sets an array list of days for this month
	 * @param ArrayList<Day>
	 */
	public void setDays(ArrayList<Day> days) {
		this.days = days;
	}
	/**
	 * setMonthName
	 * sets the name for the month
	 * @param monthName:String
	 */
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}	
}
