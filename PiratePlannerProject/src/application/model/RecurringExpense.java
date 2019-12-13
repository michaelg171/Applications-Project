package application.model;
/**
 * Class that handles Recurring Expenses
 * @author Zachary Zeller (jjh088)
 * Fall 2018
 */
public class RecurringExpense extends Expense{

	private double expense;
	private int term;
	/**
	 * Constructor of a Recurring expense
	 * @param expense: double
	 * @param term: int
	 */
	public RecurringExpense(String name, double expense, int term) {
		super(name);
		this.expense = expense;
		this.term = term;	
	}
	/**
	 * Method that returns an Recurring expense
	 * @return double: expense
	 */
	public double getExpense() {
		return expense;
	}
	/**
	 * Method that sets a Recurring Expense
	 * @param expense: double
	 */
	public void setExpense(double expense) {
		this.expense = expense;
	}
	/**
	 * getTerm is a method that returns the term length of a recurring expense
	 * @return term: int
	 */
	public int getTerm() {
		return term;
	}
	/**
	 * setTerm is a method that sets the term length of a recurring expense
	 * @param term: int
	 */
	public void setTerm(int term) {
		this.term = term;
	}
	/**
	 * Method that computes the monthly cost of a recurring expense
	 * @return expense: double
	 */
	public double computeMonthlyCost() {
		return expense;
	}
	/**
	 * The equals method allows for RecurringExpenses to be checked if they're equal
	 * @param o is the other RecurringExpense
	 * @return boolean which is true if the expense has the same name and the same amount
	 */
	public boolean equals(RecurringExpense o) {
		return getName().equals(o.getName()) && expense == o.getExpense();
	}
}
