package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.Calendar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

/**
 * The BaselineBudgetController is the controller for BaselineBudget.fxml which loads the current income of the user and changes the budget income
 * @author Leon Yuan slt733
 *
 */
public class BaselineBudgetController implements Initializable, EventHandler<ActionEvent>{

	@FXML TextField incomeLabel;
	@FXML Label errorLabel;

	/**
	 * The handle method is not used
	 * @param event is not used
	 */
	@Override
	public void handle(ActionEvent event) {}

	/**
	 * The initialize method sets the initial value of the Monthly income to the current Budget for the month
	 * @param location is not used
	 * @param resources is not used
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		incomeLabel.setText(String.format("%.2f", Calendar.getMonth().getBudget().getCurrBudget()));
	}

	/**
	 * The handleExit method leaves the screen without saving the information
	 * @param event is not used
	 */
	@FXML public void handleExit(ActionEvent event) {
		Main.closeNewWindow();
	}

	/**
	 * The handleSave method leaves the screen while saving the information only if the information is valid and a suitable number and changes the current budget income
	 * @param event is not used
	 */
	@FXML public void handleSave(ActionEvent event) {
		try {
			double income = Double.parseDouble(incomeLabel.getText());
			Calendar.getMonth().setBudgetIncome(income);
			errorLabel.setText("");
			Main.closeNewWindow();
		}catch(NumberFormatException e) {
			errorLabel.setText("Invalid value for Income");
		}
	}

}
