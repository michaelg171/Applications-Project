package application.model;
/**
 * Abstract class of an Expense
 * @author Zachary Zeller (jjh088)
 * Fall 2018
 */
public abstract class Expense implements Comparable<Expense>{
	private String name;
	
	/**
	 * Constructor that makes an Expense
	 * @param name: String
	 */
	public Expense(String name) {
		this.setName(name);
	}
	/**
	 * Method that gets a name of an Expense
	 * @return name: String
	 */
	public String getName() {
		return name;
	}
	/**
	 * Method that sets a name of an Expense
	 * @param name: String
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Method that the sub classes implement (computes the monthly cost)
	 * @return double
	 */
	public abstract double computeMonthlyCost();
	/**
	 * Method that sub classes implement (Saves an expense)
	 */
	public String toString() {
		return String.format("%-40s $%.2f", name ,computeMonthlyCost());
	}

	/**
	 * The compareTo method allows for the Budget to sort all of the expenses based on impact to the monthly budget
	 * @param o is the Expense object being compared to
	 * @return an int of where it should be in the list when sorted
	 */
	@Override
	public int compareTo(Expense o) {
		return (int)(o.computeMonthlyCost() - computeMonthlyCost());
	}
}

