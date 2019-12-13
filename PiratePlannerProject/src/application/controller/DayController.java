package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * The DayController class is the controller for Day.fxml which loads a list of all the ToDos and the CalendarEvents and some notes for that day
 * @author Leon Yuan slt733
 *
 */
public class DayController implements Initializable, EventHandler<ActionEvent>{

	@FXML TextArea notesText;
	@FXML GridPane eventGrid;
	@FXML Label dynamicTimeLabel;
	@FXML ScrollPane toDoPane;
	@FXML Label dayOfWeekLabel;
	@FXML Label dayLabel;
	private Time task;
	@FXML ListView<CalendarEvent> eventsList;
	@FXML GridPane toDoGrid;
	@FXML ListView<ToDo> todoList;

	/**
	 * The handle method is not used
	 * @param event is not used
	 */
	@Override
	public void handle(ActionEvent event) {}

	/**
	 * The initialize method presets the labels and binds the two ListViews to those that are in the Day valueobject as well as creating a Time thread to have a clock in the bottom right corner
	 * @param location is not used
	 * @param resources is not used
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Day day = Calendar.getDay();
		dayLabel.setText(day.getMonth() + " " + day.getDay() + ", " + day.getYear());
		dayOfWeekLabel.setText(Time.days(day.dayOfWeek()).toUpperCase());
		notesText.setText(day.getNote());
		
		task = new Time();
		dynamicTimeLabel.textProperty().bind(task.messageProperty());
		Thread t = new Thread(task);
		t.setDaemon(true);
		t.start();
		
		eventsList.itemsProperty().bind(day.updateEventProperty());
		
		StringConverter<ToDo> converter = new StringConverter<ToDo>(){
			@Override
			public String toString(ToDo object) {
				return object.getTask();
			}
			@Override
			public ToDo fromString(String string) {
				return null;
			}
		};
		todoList.setCellFactory(CheckBoxListCell.forListView(ToDo::completedProperty, converter));
		todoList.itemsProperty().bind(day.updateToDoProperty());
		day.updateToDoProperty().forEach(task -> task.completedProperty().addListener((observable, wasSelected, isSelected) -> {
			if(isSelected) {
				task.setChecked(true);
			}
		}));
	}

	/**
	 * The handleCalendar method sets the notes of the Day as well as changes the Scene
	 * @param event is not used
	 */
	@FXML public void handleCalendar(ActionEvent event) {
		Calendar.getDay().setNote(notesText.getText());
		Main.changeScene("view/Month.fxml");
	}

	/**
	 * The handleEvent method makes sure that it's adding a new Event to the eventsList
	 * @param event is not used
	 */
	@FXML public void handleEvent(ActionEvent event) {
		Calendar.getDay().setEvent(null);
		Main.createNewWindow("view/Event.fxml");
	}

	/**
	 * The handleToDo method adds a new ToDo to the todoList
	 * @param event is not used
	 */
	@FXML public void handleToDo(ActionEvent event) {
		Calendar.getDay().setCurrToDo(null);
		Main.createNewWindow("view/ToDo.fxml");
	}

	/**
	 * The handleEditEvent method loads the Event in to the Day and creates a new Stage for Event.fxml
	 */
	@FXML public void handleEditEvent() {
		CalendarEvent event = eventsList.getSelectionModel().getSelectedItem();
		Calendar.getDay().setEvent(event);
		Main.createNewWindow("view/Event.fxml");
	}

	/**
	 * The handleEditToDo method loads the ToDo in to the Day and creates a new Stage for ToDo.fxml
	 */
	@FXML public void handleEditToDo() {
		ToDo todo = todoList.getSelectionModel().getSelectedItem();
		Calendar.getDay().setCurrToDo(todo);
		Main.createNewWindow("view/ToDo.fxml");
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
