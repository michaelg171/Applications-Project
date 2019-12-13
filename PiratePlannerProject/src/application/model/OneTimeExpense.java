package application.model;
/**
 * OneTimeExpense is a class that represents a type of Expense that happens only once
 * @author Zachary Zeller (jjh088)
 * Fall 2018
 */
public class OneTimeExpense extends Expense{
	
	private double singleExpense;
	/**
	 * OneTimeExpense is a constructor of a one time expense
	 * @param name: String
	 * @param singleExpense: double
	 */
	public OneTimeExpense(String name, double singleExpense) {
		super(name);
		this.singleExpense = singleExpense;
	}
	/**
	 * getSingleExpense is a method that returns a single expense
	 * @return singleExpense: double
	 */
	public double getSingleExpense() {
		return singleExpense;
	}
	/**
	 * setSingleExpense is a method that sets a single expense
	 * @param singleExpense: double
	 */
	public void setSingleExpense(double singleExpense) {
		this.singleExpense = singleExpense;
	}
	/**
	 * computeMonthlyCost is a method that returns the monthlyCost of a single expense
	 * @return singleExpense: double
	 */
	@Override
	public double computeMonthlyCost() {
		return singleExpense;
	}

}
