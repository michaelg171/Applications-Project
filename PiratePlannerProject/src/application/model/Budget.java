package application.model;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Budget is a class that represents a list of Expenses
 * @author Zachary Zeller (jjh088)
 * Fall 2018
 */
public class Budget {
	private HashMap <Integer, Expense> expenses;
	private double currBudget;
	private double currExpense;
	private Expense expense;

	/**
	 * Budget is the constructor for the Budget 
	 */
	public Budget() {
		this.expenses = new HashMap<Integer,Expense>();
		currBudget = 0;
		currExpense = 0;
	}
	/**
	 * getCurrBudget is a method that returns the current budget
	 * @return: double
	 */
	public double getCurrBudget() {
		return currBudget;
	}
	/**
	 * setCurrBudget is a method that sets a current budget
	 * @param currBudget: double
	 */
	public void setCurrBudget(double currBudget) {
		this.currBudget = currBudget;
	}
	/**
	 * updateExpenseList is a method that updates a budget
	 * @return ObervableList<Expense>
	 */
	public ObservableList<Expense> updateExpenseList(){
		ArrayList<Expense> expenseList = new ArrayList<Expense>();
		expenseList.addAll(expenses.values());
		Collections.sort(expenseList);
		return FXCollections.observableList(expenseList);
	}
	/**
	 * getIncomeString is a method that returns a string of 
	 * how much is left in the budget
	 * @return String
	 */
	public String getIncomeString() {
		if(currBudget > currExpense)
			return String.format("$%.2f Left to Budget", (currBudget - currExpense));
		return String.format("$%.2f Down this Month", (currExpense - currBudget));
	}

	/**
	 * This is the getter for the Expenses HashMap<Integer, Expense>
	 * @return a HashMap<Integer, Expense> of the expenses
	 */
	public HashMap<Integer, Expense> getExpenses() {
		return this.expenses;
	}

	/**
	 * @param expenses
	 */
	public void setExpenses(HashMap<Integer, Expense> expenses) {
		this.expenses = expenses;
	}
	
	/**
	 * addExpense is a method that adds an expense to the list
	 * @param expense: Expense
	 */
	public void addExpense(Expense expense) {
		this.expenses.put(expense.hashCode(), expense);
		currExpense += expense.computeMonthlyCost();
		Calendar.updateBudgetProperty();
		Calendar.setExpensesProperty();
	}
	/**
	 * The removeExpense is a method that removes an expense based off the hashcode
	 * @param hashcode: int
	 */
	public void removeExpense(int hashcode) {
		Expense temp = expenses.remove(hashcode);
		currExpense -= temp.computeMonthlyCost();
		Calendar.updateBudgetProperty();
		Calendar.setExpensesProperty();
	}
	/**
	 * The getCurrExpense is a method that gets the currExpense double
	 * @return a double which is currExpense
	 */
	public double getCurrExpense() {
		return currExpense;
	}
	/**
	 * The setCurrExpense is a method that sets the currExpense variable to something new
	 * @param currExpense is a double which is the new value of currExpense
	 */
	public void setCurrExpense(double currExpense) {
		this.currExpense = currExpense;
	}
	/**
	 * The getExpense method is the getter for Expense
	 * @return an Expense which is the currently accessed Expense
	 */
	public Expense getExpense() {
		return expense;
	}
	/**
	 * The setExpense method is the setter for Expense
	 * @param expense is the new value for the expense class variable
	 */
	public void setExpense(Expense expense) {
		this.expense = expense;
	}
	/**
	 * The saveBudget method saves all the things inside a budget into files
	 */
	public void saveBudget() {
		Month month = Calendar.getMonth();
		String path = "Calendar/" + month.getYear() + "/" + month.getMonthName();
		File file = new File(path);
		if(!file.exists() && (expenses.size() > 0 || currBudget > 0))
			file.mkdirs();
		if(file.exists() && currBudget > 0) {
			try {
				FileWriter fw  = new FileWriter(new File(path + "/Income.txt"));
				fw.write("" + currBudget);
				fw.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		if(file.exists() && expenses.size() > 0) {
			try {
				FileWriter loanfw = new FileWriter(new File(path + "/LoanExpense.txt"));
				FileWriter recurfw = new FileWriter(new File(path + "/RecurringExpense.txt"));
				FileWriter onetimefw = new FileWriter(new File(path + "/OneTimeExpense.txt"));
				loanfw.write(""); recurfw.write(""); onetimefw.write("");
				for(Expense e : expenses.values()) {
					if(e instanceof LoanExpense) {
						LoanExpense temp = (LoanExpense) e;
						loanfw.append(temp.getName() + "|" + temp.getAmountBorrowed() + "|" + temp.getApr() + "|" + temp.getTerm() + "\n");
						System.out.println("Log: Saved a Loan Expense");
					}
					else if(e instanceof RecurringExpense) {
						RecurringExpense temp = (RecurringExpense) e;
						recurfw.append(temp.getName() + "|" + temp.getExpense() + "|" + temp.getTerm() + "\n");
						System.out.println("Log: Saved a Recurring Expense");
					}
					else if(e instanceof OneTimeExpense) {
						OneTimeExpense temp = (OneTimeExpense) e;
						onetimefw.append(temp.getName() + "|" + temp.getSingleExpense() + "\n");
						System.out.println("Log: Saved a One Time Expense");
					}
				}
				System.out.println("Log: Finished Saving Expenses");
				loanfw.close();
				recurfw.close();
				onetimefw.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * The loadBudget loads files into the Budget
	 * @param oldPath is the oldPath in which to load all the previous Expenses from 
	 * @param income is the previous income
	 */
	public void loadBudget(String oldPath, double income) {
		loadOldBudget(oldPath);
		Month month = Calendar.getMonth();
		String path = "Calendar/" + month.getYear() + "/" + month.getMonthName() + "/";
		File file = new File(path + "LoanExpense.txt");
		if(file.exists()) {
			try {
				Scanner scan = new Scanner(file);
				while(scan.hasNextLine()) {
					String [] tokens = scan.nextLine().split("\\|");
					try {
						double amount = Double.parseDouble(tokens[1]);
						double apr = Double.parseDouble(tokens[2]);
						int term = Integer.parseInt(tokens[3]);
						LoanExpense loan = new LoanExpense(tokens[0], amount, term, apr);
						boolean flag = true;
						for(Expense e : expenses.values()) {
							if(e instanceof LoanExpense) {
								LoanExpense temp = ((LoanExpense)e);
								if(temp.equals(loan)) {
									temp.setTerm(Math.max(temp.getTerm(), term));
									System.out.println("Log: LoanExpense Conflict");
								}
								flag = false;
							}
						}
						if(flag)
							addExpense(loan);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				scan.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		file = new File(path + "/RecurringExpense.txt");
		if(file.exists()) {
			try {
				Scanner scan = new Scanner(file);
				while(scan.hasNextLine()) {
					String [] tokens = scan.nextLine().split("\\|");
					try {
						double amount = Double.parseDouble(tokens[1]);
						int term = Integer.parseInt(tokens[2]);
						RecurringExpense recur = new RecurringExpense(tokens[0], amount, term);
						boolean flag = true;
						for(Expense e : expenses.values()) {
							if(e instanceof RecurringExpense) {
								RecurringExpense temp = ((RecurringExpense)e);
								if(temp.equals(recur)) {
									temp.setTerm(Math.max(temp.getTerm(), term));
									System.out.println("Log: RecurringExpense Conflict");
								}
								flag = false;
							}
						}
						if(flag)
							addExpense(recur);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				scan.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		file = new File(path + "/OneTimeExpense.txt");
		if(file.exists()) {
			try {
				Scanner scan = new Scanner(file);
				while(scan.hasNextLine()) {
					String [] tokens = scan.nextLine().split("\\|");
					try {
						double amount = Double.parseDouble(tokens[1]);
						OneTimeExpense onetime = new OneTimeExpense(tokens[0], amount);
						addExpense(onetime);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
				scan.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		file = new File(path + "/Income.txt");
		if(file.exists()) {
			try {
				Scanner scan = new Scanner(file);
				try {
					setCurrBudget(Double.parseDouble(scan.nextLine()));
				} catch(NumberFormatException e) {
					e.printStackTrace();
				}
				scan.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		else
			setCurrBudget(income);
	}
	
	/**
	 * The loadBudget loads all of the old RecurringExpense/LoanExpense into this month
	 * @param oldPath is the path to the previous month
	 */
	public void loadOldBudget(String oldPath) {
		if(!oldPath.equals("")) {
			File file = new File(oldPath + "/LoanExpense.txt");
			if(file.exists()) {
				try {
					Scanner scan = new Scanner(file);
					while(scan.hasNextLine()) {
						String [] tokens = scan.nextLine().split("\\|");
						try {
							double amount = Double.parseDouble(tokens[1]);
							double apr = Double.parseDouble(tokens[2]);
							int term = Integer.parseInt(tokens[3]);
							LoanExpense loan = new LoanExpense(tokens[0], amount, term, apr);
							if(term > 1)
								addExpense(new LoanExpense(tokens[0], amount - loan.computeMonthlyCost() , term - 1, apr));
						} catch(NumberFormatException e) {
							e.printStackTrace();
						}
					}
					scan.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			file = new File(oldPath + "/RecurringExpense.txt");
			if(file.exists()) {
				try {
					Scanner scan = new Scanner(file);
					while(scan.hasNextLine()) {
						String [] tokens = scan.nextLine().split("\\|");
						try {
							double amount = Double.parseDouble(tokens[1]);
							int term = Integer.parseInt(tokens[2]);
							if(term > 1)
								addExpense(new RecurringExpense(tokens[0], amount, term - 1));
						} catch(NumberFormatException e) {
							e.printStackTrace();
						}
					}
					scan.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
