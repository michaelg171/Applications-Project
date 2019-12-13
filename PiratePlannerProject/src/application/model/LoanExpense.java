package application.model;
/**
 * LoanExpense is a class that represents a type of Expense using loans
 * @author Zachary Zeller
 * Fall 2018
 */
public class LoanExpense extends Expense{

	private double amountBorrowed;
	private int term;
	private double apr;

	/**
	 * LoanExpense is a constructor that represents a loan expense
	 * @param amountBorrowed: double
	 * @param term: int
	 * @param apr: double
	 */
	public LoanExpense(String name, double amountBorrowed, int term, double apr) {
		super(name);
		this.amountBorrowed = amountBorrowed;
		this.term = term;
		this.apr = apr;
	}
	/**
	 * getAmountBorrowed is a method that returns the amount borrowed
	 * @return double: amountBorrowed
	 */
	public double getAmountBorrowed() {
		return amountBorrowed;
	}
	/**
	 * setAmountBorrowed is a method that sets the amount borrowed of a loan
	 * @param amountBorrowed: double
	 */
	public void setAmountBorrowed(double amountBorrowed) {
		this.amountBorrowed = amountBorrowed;
	}
	/**
	 * getTerm is a method that returns the term length of a loan
	 * @return term: int
	 */
	public int getTerm() {
		return term;
	}
	/**
	 * setTerm is a method that sets the term length of a loan
	 * @param term: int
	 */
	public void setTerm(int term) {
		this.term = term;
	}
	/**
	 * getApr is a method that returns the apr of a loan
	 * @return apr: double
	 */
	public double getApr() {
		return apr;
	}
	/**
	 * setApr is a method that sets the apr of a loan
	 * @param apr: double
	 */
	public void setApr(double apr) {
		this.apr = apr;
	}
	/**
	 * computeMonthlyCost is a method that computes the monthly cost of a loan
	 * @return monthlyCost: double
	 */
	public double computeMonthlyCost() {
		if(term <= 0) {
			return 0;
		}else if(amountBorrowed <=0) {
			return 0;
		}else {
			double i = ((apr/100)/12);
			double d = (Math.pow(i+1, term)-1)/(Math.pow(i+1,term)*i);
			double monthlyCost = amountBorrowed/d;
			return monthlyCost;
		}
	}
	/**
	 * The equals method allows for LoanExpenses to be compared
	 * @param o is the other LoanExpense
	 * @return a boolean which says if it's the same name as well as the same APR
	 */
	public boolean equals(LoanExpense o) {
		return o.getName().equals(getName()) && apr == o.getApr();
	}
}
