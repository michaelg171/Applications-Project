package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.Calendar;
import application.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * The OneTimeExpenseController is the controller class for the OneTimeExpense.fxml which loads the information in creating a OneTimeExpense
 * @author Leon Yuan slt733
 *
 */
public class OneTimeExpenseController implements Initializable, EventHandler<ActionEvent> {

	@FXML TextField amountText;
	@FXML TextField nameText;
	@FXML Label errorLabel;
	private OneTimeExpense expense;

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
		Expense exp =  Calendar.getMonth().getBudget().getExpense();
		if(exp != null && exp instanceof OneTimeExpense) {
			expense = (OneTimeExpense)Calendar.getMonth().getBudget().getExpense();
			OneTimeExpense oneTime = (OneTimeExpense) expense;
			amountText.setText(String.format("%.2f", oneTime.getSingleExpense()));
			nameText.setText("" + oneTime.getName());
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
	 * The handleRecur method changes the scene to the RecurringExpense.fxml
	 * @param event is not used
	 */
	@FXML public void handleRecur(ActionEvent event) {
		Main.changeNewWindowScene("view/RecurringExpense.fxml");
	}

	/**
	 * The handleSave method does error checking when trying to save a OneTimeExpense to see that everything is filled out and exits after it saves
	 * @param event is not used
	 */
	@FXML public void handleSave(ActionEvent event) {
		String name = nameText.getText();
		Budget budget = Calendar.getMonth().getBudget();
		if(!name.equals("")) {
			try {
	
				double amount = Double.parseDouble(amountText.getText());
				OneTimeExpense newExpense = new OneTimeExpense(name, amount);
				if(budget.getExpense() != null)
					budget.removeExpense(budget.getExpense().hashCode());
				budget.addExpense(newExpense);
				Main.closeNewWindow();
			}catch(NumberFormatException e) {
				errorLabel.setText("Invalid values for Amount");
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
	 * The getExpense method is the OneTimeExpense that is being edited
	 * @return OneTimeExpense which is the OneTimeExpense that is being edited
	 */
	public OneTimeExpense getExpense() {
		return expense;
	}
	/**
	 * The setExpense method changes expense to a different OneTimeExpense
	 * @param expense is a different OneTimeExpense which will be expense's new value
	 */
	public void setExpense(OneTimeExpense expense) {
		this.expense = expense;
	}
	

}
