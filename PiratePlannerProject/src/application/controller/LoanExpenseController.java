package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.Budget;
import application.model.Calendar;
import application.model.Expense;
import application.model.LoanExpense;
import application.model.RecurringExpense;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * The LoanExpenseController class is the controller for LoanExpense.fxml which loads the information in creating a new LoanExpense
 * @author Leon Yuan slt733
 *
 */
public class LoanExpenseController implements Initializable, EventHandler<ActionEvent> {

	@FXML TextField termText;
	@FXML TextField aprText;
	@FXML TextField amountText;
	@FXML TextField nameText;
	private LoanExpense expense;
	@FXML Label errorLabel;
	
	/**
	 * The handle method is not used
	 * @param event is not used
	 */
	@Override
	public void handle(ActionEvent event) {}

	/**
	 * The initialize method sets all of the text in the fields to certain value only if there is editing to occur
	 * @param location is not used
	 * @param resources is not used
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Expense exp = Calendar.getMonth().getBudget().getExpense();
		if(exp != null && exp instanceof LoanExpense) {
			expense = (LoanExpense)Calendar.getMonth().getBudget().getExpense();
			amountText.setText(String.format("%.2f", expense.getAmountBorrowed()));
			nameText.setText("" + expense.getName());
			termText.setText("" + expense.getTerm());
			aprText.setText("" + expense.getApr());
		}
	}

	/**
	 * The handleOneTime method changes the scene to the OneTimeExpense.fxml 
	 * @param event is not used
	 */
	@FXML public void handleOneTime(ActionEvent event) {
		Main.changeNewWindowScene("view/OneTimeExpense.fxml");
	}

	/**
	 * The handleRecur method changes the scene to the RecurringExpense.fxml
	 * @param event is not used
	 */
	@FXML public void handleRecur(ActionEvent event) {
		Main.changeNewWindowScene("view/RecurringExpense.fxml");
	}

	/**
	 * The handleExit method closes the stage that the current FXML is on
	 * @param event is not used
	 */
	@FXML public void handleExit(ActionEvent event) {
		Main.closeNewWindow();
	}

	/**
	 * The handleSave method does error checking when trying to save a LoanExpense to see that everything is filled out and exits after it saves
	 * @param event is not used
	 */
	@FXML public void handleSave(ActionEvent event) {
		String name = nameText.getText();
		Budget budget = Calendar.getMonth().getBudget();
		if(!name.equals("")) {
			try {
	
				double amount = Double.parseDouble(amountText.getText());
				double apr = Double.parseDouble(aprText.getText());
				if(apr < 0 || apr > 100) {
					errorLabel.setText("Invalid APR value");
					return;
				}
				int term = Integer.parseInt(termText.getText());
				LoanExpense loan = new LoanExpense(name, amount, term, apr);
				if(budget.getExpense() != null) {
					System.out.println(budget.getExpense());
					budget.removeExpense(budget.getExpense().hashCode());
					System.out.println("Log: Editing an Expense");
				}
				budget.addExpense(loan);
				Main.closeNewWindow();
			}catch(NumberFormatException e) {
				errorLabel.setText("Invalid values for Amount, APR, or Term");
			}
		}
		else 
			errorLabel.setText("Please enter a title");
	}

	/**
	 * The getExpense method is the LoanExpense that is being edited
	 * @return LoanExpense which is the LoanExpense that is being edited
	 */
	public LoanExpense getExpense() {
		return expense;
	}

	/**
	 * The setExpense method changes expense to a different LoanExpense
	 * @param expense is a different LoanExpense which will be expense's new value
	 */
	public void setExpense(LoanExpense expense) {
		this.expense = expense;
	}
	
	
}
