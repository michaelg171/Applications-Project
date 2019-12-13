package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.*;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;

/**
 * The EventController class is a controller class for Event.fxml which loads a page which has time, title, and a description for an Event
 * @author Leon Yuan slt733
 *
 */
public class EventController implements Initializable, EventHandler<ActionEvent>{

	@FXML TextField minTextField;
	@FXML TextField hourTextField;
	@FXML ToggleButton pmButton;
	@FXML ToggleButton amButton;
	@FXML TextField titleText;
	@FXML Label timeLabel;
	@FXML TextArea descriptionText;
	@FXML Label titleLabel;
	private StringProperty timeProperty;
	private String time;
	private String ampm;
	private CalendarEvent event;
	@FXML Label errorLabel;

	/**
	 * The handle method is not used
	 * @param event is not used
	 */
	@Override
	public void handle(ActionEvent event) {}

	/**
	 * The initialize method presets certain values and creates a prompt for the user and loads in a different event if we are editing the event
	 * @param location is not used
	 * @param resources is not used
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		event = Calendar.getDay().getEvent();
		titleText.setPromptText("Title");
		descriptionText.setPromptText("Description");
		ampm = "AM";
		
		titleLabel.textProperty().bind(titleText.textProperty());
		
		timeProperty = new SimpleStringProperty("12:00 AM");
		timeLabel.textProperty().bind(timeProperty);
		
		if(event != null) {
			titleText.setText(event.getTitle());
			time = event.getTime();
			if(time.indexOf("AM") != -1) 
				ampm = "AM";
			else {
				ampm = "PM";
				handlePM();
			}
			hourTextField.setText(time.substring(0, time.indexOf(":")));
			minTextField.setText(time.substring(time.indexOf(":") + 1, time.indexOf(" ")));
			updateTime();
			descriptionText.setText(event.getDescription());
		}
	}

	/**
	 * The handleSave method does error checking in regarding the Event and will only save if there is a title and the time is proper and adds it into the Day
	 */
	@FXML public void handleSave() {
		String title = titleText.getText();
		String description = descriptionText.getText();
		if(!title.equals("")) {
			try {
				int hour = Integer.parseInt(hourTextField.getText());
				int min = Integer.parseInt(minTextField.getText());
				if(inbounds(hour, min)) {
					updateTime();
					Day day = Calendar.getDay();
					if(event == null) {
						day.addEvent(new CalendarEvent(title, time, description));
						System.out.println("Log: Added an Event to the Calendar");
					}
					else {
						day.removeEvent(event.hashCode());
						day.addEvent(new CalendarEvent(title, time, description));
						System.out.println("Log: Edited an Event to the Calendar");
					}
					errorLabel.setText("");
					Main.closeNewWindow();
				}
				else {
					errorLabel.setText("Please enter a valid time");
				}
			} catch (NumberFormatException e) {
				errorLabel.setText("Please enter a valid time");
			}
		}
		else {
			errorLabel.setText("Please enter a title");
		}
	}
	/**
	 * The inbounds method returns a boolean regarding if the time is a valid time
	 * @param hour is the hour in the TextField
	 * @param min is the minute in the textField
	 * @return boolean which is whether it is a valid time or not
	 */
	private boolean inbounds(int hour, int min) {
		return 0 < hour && hour < 13 && -1 < min && min < 60;
	}

	/**
	 * The handleExit method closes the new Stage for showing the Event
	 * @param event is not used
	 */
	@FXML public void handleExit(ActionEvent event) {
		Main.closeNewWindow();
	}

	/**
	 * The handleAM method swaps the amButton and pmButton simulating if the AM button is pressed
	 */
	@FXML public void handleAM() {
		amButton.setSelected(true);
		pmButton.setSelected(false);
		ampm = "AM";
		updateTime();
	}

	/**
	 * The handlePM method swaps the pmButton and amButton simulating if the PM button is pressed
	 */
	@FXML public void handlePM() {
		pmButton.setSelected(true);
		amButton.setSelected(false);
		ampm = "PM";
		updateTime();
	}

	/**
	 * The handleHourUp method simulates when the arrow is pressed and includes it's own error checking to make sure that the hours are between 1 and 12 
	 */
	@FXML public void handleHourUp() {
		try {
			int hr = Integer.parseInt(hourTextField.getText());
			if(1 > hr || hr > 12)
				throw new NumberFormatException();
			hr = hr + 1 == 13? 1 : hr + 1;
			hourTextField.setText(Integer.toString(hr));
			if(hr == 12) {
				if(ampm.equals("AM")) handlePM();
				else handleAM();
			}
			updateTime();
		} catch (NumberFormatException e) {
			System.out.println("Log: Invalid Text");
		}
	}

	/**
	 * The handleHourDown method simulates when the arrow is pressed and includes it's own error checking to make sure that hours are between 1 and 12
	 */
	@FXML public void handleHourDown() {
		try {
			int hr = Integer.parseInt(hourTextField.getText());
			if(1 > hr || hr > 12)
				throw new NumberFormatException();
			hr = hr - 1 == 0? 12 : hr - 1;
			hourTextField.setText(Integer.toString(hr));
			if(hr == 11) {
				if(ampm.equals("AM")) handlePM();
				else handleAM();
			}
			updateTime();
		} catch (NumberFormatException e) {
			errorLabel.setText("Please enter a valid hour");
			System.out.println("Log: Invalid Text");
		}
	}
	/**
	 * The updateTime method changes the time variable and changes the StringProperty thus updating the time of the event
	 */
	private void updateTime() {
		time = hourTextField.getText() + ":" + String.format("%02d", Integer.parseInt(minTextField.getText())) + " " + ampm;
		timeProperty.set(time);
	}

	/**
	 * The handleMinUP method changes the minutes to the next 15 minute increment greater than it and performs it's own error checking 
	 * @param event is not used
	 */
	@FXML public void handleMinUp() {
		try {
			int min = Integer.parseInt(minTextField.getText());
			if(min < 0 || min > 59)
				throw new NumberFormatException();
			min = min / 15 * 15 + 15 == 60? 0 : min / 15 * 15 + 15;
			minTextField.setText(String.format("%02d", min));
			updateTime();
		} catch (NumberFormatException e) {
			errorLabel.setText("Please eneter a valid minute");
			System.out.println("Log: Invalid Text");
		}
	}

	/**
	 * The handle MinDown method changes the minutes to the next 15 minute increment lower than it and performs it's own error checking
	 * @param event
	 */
	@FXML public void handleMinDown() {
		try {
			int min = Integer.parseInt(minTextField.getText());
			if(min < 0 || min > 59)
				throw new NumberFormatException();
			min = (min + 14) / 15 * 15 - 15 == -15 ? 45 : (min + 14) / 15 * 15 - 15;
			minTextField.setText(String.format("%02d", min));
			updateTime();
		} catch (NumberFormatException e) {
			errorLabel.setText("Please eneter a valid minute");
			System.out.println("Log: Invalid Text");
		}
	}

	/**
	 * The getTimeProperty method is the getter method for the StringProperty that the timeLabel is binded to
	 * @return a StringProperty in which the timeLabel is binded to
	 */
	public StringProperty getTimeProperty() {
		return timeProperty;
	}

	/**
	 * The setTimeProperty method is the setter method for the timeProperty which will not change the timeLabel
	 * @param timeProperty is the new StringProperty that this.timeProperty will become
	 */
	public void setTimeProperty(StringProperty timeProperty) {
		this.timeProperty = timeProperty;
	}

	/**
	 * The getTime method is the getter method for the formatted String of the time of the event
	 * @return a String of the time of the event 
	 */
	public String getTime() {
		return time;
	}

	/**
	 * The setTime method is the setter method changes the value of the time but won't change the timeLabel
	 * @param time is the new String that time will be changed to
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * The getAmpm method is the getter method to tell whether it is AM or PM
	 * @return a String of whether it is AM/PM
	 */
	public String getAmpm() {
		return ampm;
	}

	/**
	 * The setAmpm method is the setter method to change it to either AM or PM
	 * @param ampm is the new String that will be either AM/PM
	 */
	public void setAmpm(String ampm) {
		this.ampm = ampm;
	}

	/**
	 * The getEvent method is the getter method of the currentEvent which is being edited
	 * @return a CalendarEvent which is the CalendarEvent being changed
	 */
	public CalendarEvent getEvent() {
		return event;
	}

	/**
	 * The setEvent method is the setter method of the currentEvent which changes which event is being edited
	 * @param event is the new CalendarEvent that is being edited
	 */
	public void setEvent(CalendarEvent event) {
		this.event = event;
	}	
}
