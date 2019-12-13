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
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

/**
 * The ToDoController class is the controller for ToDo.fxml which loads a User Prompt for inserting a new ToDo
 * @author Leon Yuan slt733
 *
 */
/**
 * @author leon
 *
 */
/**
 * @author leon
 *
 */
public class ToDoController implements Initializable, EventHandler<ActionEvent>{

	@FXML Label titleLabel;
	@FXML TextField titleText;
	@FXML TextArea descriptionText;
	private ToDo todo;
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
	 * The initialize method sets the prompt and opens up the todo if there is one to be edited
	 * @param location is not used
	 * @param resources is not used
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		titleText.setPromptText("Task Title");
		descriptionText.setPromptText("Task Description");
		
		titleLabel.textProperty().bind(titleText.textProperty());
		
		todo = Calendar.getDay().getCurrToDo();
		if(todo != null) {
			titleText.setText(todo.getTask());
			descriptionText.setText(todo.getDescription());
		}
	}

	/**
	 * The handleSave method performs errorchecking and saves the ToDo if all requirements are met
	 * @param event is not used
	 */
	@FXML public void handleSave(ActionEvent event) {
		if(!titleLabel.equals("")) {
			Day day = Calendar.getDay();
			if(todo == null) {
				day.addToDo(new ToDo(titleText.getText(), descriptionText.getText()));
				System.out.println("Log: Added a ToDo");
			}
			else {
				ToDo newToDo = new ToDo(titleText.getText(), descriptionText.getText());
				if(Calendar.getDay().getCurrToDo().completedProperty().get()) {
					System.out.println("Log: Editing Checked ToDo");
					newToDo.setChecked(true);
				}
				day.removeToDo(todo.hashCode());
				day.addToDo(newToDo);
				System.out.println("Log: Edited a ToDo");
			}
			errorLabel.setText("");
			Main.closeNewWindow();
		} else {
			errorLabel.setText("Please enter a title");
		}
	}

	/**
	 * The handleExit method just closes the new Window
	 * @param event is not used
	 */
	@FXML public void handleExit(ActionEvent event) {
		Main.closeNewWindow();
	}

	/**
	 * The getTodo method returns a todo which is currently stored in the todo class variable
	 * @return a ToDo object which is the class variable
	 */
	public ToDo getTodo() {
		return todo;
	}

	/**
	 * The setTodo method sets the todo class variable to the ToDo given
	 * @param todo is the new class variable 
	 */
	public void setTodo(ToDo todo) {
		this.todo = todo;
	}
	

}
