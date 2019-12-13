package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.Budget;
import application.model.Calendar;
import application.model.Expense;
import application.model.OneTimeExpense;
import application.model.RecurringExpense;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * The RecurringExpenseController is the controller class for the RecurringExpense.fxml which loads the information in creating a RecurringExpense
 * @author Leon Yuan slt733
 *
 */
public class RecurringExpenseController implements Initializable, EventHandler<ActionEvent>{

	@FXML TextField nameText;
	@FXML TextField amountText;
	@FXML TextField termText;
	private RecurringExpense expense;
	@FXML Label errorLabel;
	
	/**
	 * The handle method is not used
	 * @param event is not used
	 */
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * The initialize method sets all of the text in the fields to certain value only if there is editing to occur
	 * @param location is not used
	 * @param resources is not used
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Expense exp =  Calendar.getMonth().getBudget().getExpense();
		if(exp != null && exp instanceof RecurringExpense) {
			expense =  (RecurringExpense) Calendar.getMonth().getBudget().getExpense();
			amountText.setText(String.format("%.2f", expense.getExpense()));
			nameText.setText("" + expense.getName());
			termText.setText("" + expense.getTerm());
		}
	}

	/**
	 * The handleLoan method changes the scene to the LoanExpense.fxml
	 * @param event is not used
	 */
	@FXML public void handleLoan(ActionEvent event) {
		Main.changeNewWindowScene("view/LoanExpense.fxml");
	}

	/**
	 * The handleOneTime method changes the scene to the OneTimeExpense.fxml 
	 * @param event is not used
	 */
	@FXML public void handleOneTime(ActionEvent event) {
		Main.changeNewWindowScene("view/OneTimeExpense.fxml");
	}

	/**
	 * The handleSave method does error checking when trying to save a RecurringExpense to see that everything is filled out and exits after it saves
	 * @param event is not used
	 */
	@FXML public void handleSave(ActionEvent event) {
		String name = nameText.getText();
		Budget budget = Calendar.getMonth().getBudget();
		if(!name.equals("")) {
			try {
				double amount = Double.parseDouble(amountText.getText());
				int term = Integer.parseInt(termText.getText());
				RecurringExpense recur = new RecurringExpense(name, amount, term);
				if(budget.getExpense() != null)
					budget.removeExpense(budget.getExpense().hashCode());
				budget.addExpense(recur);
				errorLabel.setText("");
				Main.closeNewWindow();
			}catch(NumberFormatException e) {
				errorLabel.setText("Invalid amount for Amount or Term");
			}
		}
		else
			errorLabel.setText("Please enter a title");
	}

	/**
	 * The handleExit method closes the stage that the current FXML is on
	 * @param event is not used
	 */
	@FXML public void handleExit(ActionEvent event) {
		Main.closeNewWindow();
	}
	/**
	 * The getExpense method is the RecurringExpense that is being edited
	 * @return RecurringExpense which is the RecurringExpense that is being edited
	 */
	public RecurringExpense getExpense() {
		return expense;
	}
	/**
	 * The setExpense method changes expense to a different RecurringExpense
	 * @param expense is a different RecurringExpense which will be expense's new value
	 */
	public void setExpense(RecurringExpense expense) {
		this.expense = expense;
	}
	
	

}
