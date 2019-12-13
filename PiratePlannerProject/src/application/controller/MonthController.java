package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Main;
import application.model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * The MonthController class is the controller for Month.fxml
 * @author Leon Yuan slt733
 *
 */
public class MonthController implements Initializable, EventHandler<ActionEvent>{

	
	@FXML Label dynamicTimeLabel;
	@FXML Button budgetButton;
	@FXML Button week1Button;
	@FXML Button prevMonButton;
	@FXML Button nextMonButton;
	@FXML Button monthButton;
	@FXML GridPane calendarGrid;
	private Time task;
	@FXML Label monthLabel;

	/**
	 * The handle method is not used
	 * @param event is not used
	 */
	@Override
	public void handle(ActionEvent event) {}

	/**
	 * The initialize method initializes the Calendar if the program has just loaded and loads the Month and binds the timeLabel to a Task which is being concurrently run
	 * @param location is not used
	 * @param resources is not used
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO: Check the current date
		if(Calendar.getMonth() == null)
			Calendar.initialize();
		// TODO: Load the current month as well as the days
		loadMonth();
		
		task = new Time();
		dynamicTimeLabel.textProperty().bind(task.messageProperty());
		Thread t = new Thread(task);
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * The loadMonth month loads all of the days of the month into the GridPane and will account for when the first day of the month actually start and if there is a 5th/6th week or not
	 */
	private void loadMonth() {
		monthLabel.setText(Calendar.getMonth().getMonthName().toUpperCase() +  " '" + Calendar.getYear() % 100);
		ArrayList<Day> dayList = Calendar.getMonth().getDays();
		int index = 0, index2 = 0;
		boolean flag = false;
		for(Node node: calendarGrid.getChildren()) {
			Button b = (Button) node;
			b.setDisable(false);
			b.setOpacity(1);
			Day day = null;
			if(index2 < dayList.size()) {
				day = dayList.get(index2);
				if (day.dayOfWeek() == index % 7 + 1) {
					b.setText(Integer.toString(day.getDay()));
					index2++;
				}
				else {
					b.setText("");
				}
			}
			else {
				b.setText("");
				if(index % 7 == 0)
					flag = true;
			}
			if(flag) {
				b.setDisable(true);
				b.setOpacity(0);
			}
			index++;

		}
	}

	/**
	 * The handleBudget method changes the Scene to the Budget.fxml
	 * @param event is not used
	 */
	@FXML public void handleBudget(ActionEvent event) {
		Main.changeScene("view/Budget.fxml");
	}

	/**
	 * The handleDay method changes the Scene to one of the Days after changing the day of the Month and will do checking to see that it is an actual day
	 * @param event is not used
	 */
	@FXML public void handleDay(ActionEvent event) {
		for(Node node: calendarGrid.getChildren()) {
			Button temp = (Button) node;
			if(event.getSource() == node && !temp.getText().equals("")) {
				// TODO: Pass information of the node to the next screen
				Calendar.setDayOfMonth(Integer.parseInt(temp.getText()));
				Main.changeScene("view/Day.fxml");
			}
		}
	}

	/**
	 * The handleNext method loads the next Month and all of its events, todos, and expenses
	 * @param event is not used
	 */
	@FXML public void handleNext(ActionEvent event) {
		Calendar.nextMonth();
		loadMonth();
	}

	/**
	 * The handlePrev methods loads the previous Month and all of it events todos, and expenses
	 * @param event is not used
	 */
	@FXML public void handlePrev(ActionEvent event) {
		Calendar.prevMonth();
		loadMonth();
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
