package application.model;

import java.util.HashMap;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Calendar class stores all of the different values of where the calendar is currently located
 * @author Leon Yuan slt733
 *
 */
public class Calendar {
	private static Month month;
	private static Day day;
	private static int year;
	private static SimpleListProperty<Expense> expensesProperty;
	private static SimpleStringProperty incomeProperty;
	
	/**
	 * The setDay method sets the day to a new day and updates Year and month if they are different
	 * @param newMonth is the value of the new month
	 * @param newDay is the number of Day of the new Day
	 * @param newYear is the year of what its being changed to
	 */
	public static void setDay(int newMonth, int newDay, int newYear) {
		if(newYear != year || !Time.month(newMonth).equals(month.getMonthName()))
			month = new Month(Time.abbrevMonth(newMonth), year);
		if(newYear != year)
			year = newYear;
		day = getMonth().getDays().get(newDay - 1);
	}
	/**
	 * The setDayOfMonth method changes the day of the month to a new Day
	 * @param newDay is the new Day number that it will be changed to
	 */
	public static void setDayOfMonth(int newDay) {
		day = getMonth().getDays().get(newDay - 1);
	}
	/**
	 * The initialize method initializes the first values of the month
	 */
	public static void initialize() {
		// TODO Auto-generated method stub
		expensesProperty = new SimpleListProperty<Expense>();
		incomeProperty = new SimpleStringProperty();
		month = new Month(Time.abbrevMonth(Time.getCurrentMonth()), Time.getCurrentYear());
		year = Time.getCurrentYear();
		day = month.getDays().get(0);
		month.loadMonth("", 0);
	}
	/**
	 * The prevMonth method changes the values of the month to the previous month
	 */
	public static void prevMonth() {
		month.saveMonth();
		int prevMonth = Time.revAbbrevMonth(month.getMonthName()) - 1;
		System.out.println("Log: Loading " + prevMonth + " " + month.getYear());
		double income = month.getBudget().getCurrBudget();
		if(prevMonth == 0) 
			setDay(12, 1, --year);
		else 
			setDay(prevMonth, 1, year);
		month.loadMonth("", income);
		setExpensesProperty();
	}
	/**
	 * The nextMonth method changes the values of the month to the next month
	 */
	public static void nextMonth() {
		month.saveMonth();
		String path = "Calendar/" + year + "/" + month.getMonthName() + "/";
		int nextMonth = Time.revAbbrevMonth(month.getMonthName()) + 1;
		double income = month.getBudget().getCurrBudget();
		System.out.println("Log: Loading " + nextMonth + " " + month.getYear());
		if(nextMonth == 13) 
			setDay(1, 1, ++year);
		else 
			setDay(nextMonth, 1, year);
		month.loadMonth(path, income);
		setExpensesProperty();
	}
	/**
	 * The setExpensesProperty method returns a ListProperty which can be binded to which shows all the different expenses for the month
	 * @return a ListProperty of the Expenses
	 */
	public static ListProperty<Expense> setExpensesProperty() {
		expensesProperty.set(month.getBudget().updateExpenseList());
		return expensesProperty;
	}
	/**
	 * The updateBudgetProperty method returns a StringProperty which can be binded to which shows the current Budget
	 * @return a StringProperty of the current Budget
	 */
	public static StringProperty updateBudgetProperty() {
		//TODO: Add the currentLeft in Budget
		incomeProperty.set(month.getBudget().getIncomeString());
		return incomeProperty;
	}
	/**
	 * The getMonth method returns a Month object of the current Month
	 * @return a Month object which is the class variable
	 */
	public static Month getMonth() {
		return month;
	}
	/**
	 * The getDay method returns a Day object of the current Day
	 * @return a Day object which is the class variable
	 */
	public static Day getDay() {
		return day;
	}
	/**
	 * The getYear method returns an int of the year
	 * @return an int of the year
	 */
	public static int getYear() {
		return year;
	}
	/**
	 * The getExpensesProperty returns a ListProperty of all the expenses
	 * @return a SimpleListProperty<Expense> of all expenses
	 */
	public static SimpleListProperty<Expense> getExpensesProperty() {
		return expensesProperty;
	}
	/**
	 * The setExpensesProperty sets the ListProperty to a new ListProperty
	 * @param expensesProperty is the new expenses Property
	 */
	public static void setExpensesProperty(SimpleListProperty<Expense> expensesProperty) {
		Calendar.expensesProperty = expensesProperty;
	}
	/**
	 * The getIncomeProperty is the getter for the incomeProperty
	 * @return incomeProperty which is the current incomeProperty
	 */
	public static SimpleStringProperty getIncomeProperty() {
		return incomeProperty;
	}
	/**
	 * The setIncomeProperty is the setter for the incomeProperty
	 * @param incomeProperty which is the new SimpleStringProperty which is the new value of incomeProperty
	 */
	public static void setIncomeProperty(SimpleStringProperty incomeProperty) {
		Calendar.incomeProperty = incomeProperty;
	}
	/**
	 * The setMonth is the setter for the Month object
	 * @param month is the new Month object
	 */
	public static void setMonth(Month month) {
		Calendar.month = month;
	}
	/**
	 * The setDay is the setter for the Day object
	 * @param day is the new Day object
	 */
	public static void setDay(Day day) {
		Calendar.day = day;
	}
	/**
	 * The setYear is the setter for the year value
	 * @param year is the new year that it will be set to
	 */
	public static void setYear(int year) {
		Calendar.year = year;
	}
	

}
