package application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import application.Main;
import application.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * The BudgetController class is the controller for Budget.fxml which loads a list of all of the current expenses and how much money is left for the month
 * @author Leon Yuan slt733
 *
 */
public class BudgetController implements Initializable, EventHandler<ActionEvent> {

	@FXML Label monthLabel;
	@FXML GridPane budgetGrid;
	@FXML Label dynamicTimeLabel;
	@FXML ToggleButton toggleButton;
	@FXML Label budgetLabel;
	@FXML Button newBudgetButton;
	private Time task;
	@FXML ListView<Expense> expenseListView;
	@FXML Label errorLabel;
	
	/**
	 * The handle method is not used
	 * @param event is not used
	 */
	@Override
	public void handle(ActionEvent event) {}

	/**
	 * The initialize method instantiates another thread so that their is a current time constantly being shown in the bottom right hand corner as well as binding the listview and the label
	 * @param location is not used
	 * @param resources is not used
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		task = new Time();
		dynamicTimeLabel.textProperty().bind(task.messageProperty());
		Thread t = new Thread(task);
		t.setDaemon(true);
		t.start();
		
		budgetLabel.textProperty().bind(Calendar.updateBudgetProperty());
		
		expenseListView.itemsProperty().bind(Calendar.setExpensesProperty());
		
		loadMonth();
	}

	/**
	 * The handleCalendar method changes the scene to Month.fxml when the button is pressed 
	 * @param event is not used
	 */
	@FXML public void handleCalendar(ActionEvent event) {
		Main.changeScene("view/Month.fxml");
	}

	/**
	 * The handlePrev method changes the month to the previous month and will load files regarding the budget to show the expenses from last month
	 * @param event is not used
	 */
	@FXML public void handlePrev(ActionEvent event) {
		Calendar.prevMonth();
		Calendar.setExpensesProperty();
		Calendar.updateBudgetProperty();
		loadMonth();
	}
	
	/**
	 * The handleEdit method allows for the user to change the expense and will load all the previous values of the expense
	 * @param event is not used
	 */
	@FXML public void handleEdit(ActionEvent event) {
		Expense expense = expenseListView.getSelectionModel().getSelectedItem();
		Calendar.getMonth().getBudget().setExpense(expense);
		if(expense instanceof LoanExpense) {
			Main.createNewWindow("view/LoanExpense.fxml");
		}
		else if(expense instanceof RecurringExpense) {
			Main.createNewWindow("view/RecurringExpense.fxml");
		}
		else if(expense instanceof OneTimeExpense) {
			Main.createNewWindow("view/OneTimeExpense.fxml");
		}
	}

	/**
	 * The handleNext method changes the month to the next month and will load file regarding the budget to show the expenses from next month
	 * @param event
	 */
	@FXML public void handleNext(ActionEvent event) {
		// TODO: Change the month of the current Budget
		Calendar.nextMonth();
		Calendar.setExpensesProperty();
		Calendar.updateBudgetProperty();
		loadMonth();
	}
	
	/**
	 * The loadMonth method updates the current month
	 */
	private void loadMonth() {
		monthLabel.setText(Calendar.getMonth().getMonthName().toUpperCase() +  " '" + Calendar.getYear() % 100);
	}

	/**
	 * The handleNewBudget method opens a new window to create a new expense
	 * @param event is not used
	 */
	@FXML public void handleNewBudget(ActionEvent event) {
		Budget budget = Calendar.getMonth().getBudget();
		if(budget.getCurrBudget() > 0) {
			budget.setExpense(null);
			Main.createNewWindow("view/OneTimeExpense.fxml");
		}
		else {
			errorLabel.setText("Please enter an income");
		}
	}

	/**
	 * The handleCurrBudget method opens a new window to change the current Income 
	 * @param event is not used
	 */
	@FXML public void handleCurrBudget(ActionEvent event) {
		errorLabel.setText("");
		Main.createNewWindow("view/BaselineBudget.fxml");
	}
	/**
	 * The getTask method returns the Time object which is the new Thread that is created
	 * @return a Time object which is the other thread being ran to get the current time
	 */
	public Time getTask() {
		return task;
	}

	/**
	 * The setTask method sets task to a new Time object
	 * @param task is  the new Time object which task will become
	 */
	public void setTask(Time task) {
		this.task = task;
	}
}
