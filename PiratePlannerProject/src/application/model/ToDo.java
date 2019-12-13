package application.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ToDo implements Comparable<ToDo>{
	private boolean checked;
	private String task, description;
	private BooleanProperty completed;
	
	public ToDo(String task, String description) {
		this.task = task;
		this.description = description;
		this.checked = false;
		completed = new SimpleBooleanProperty(checked);
	}
	public ToDo(String task, String description, boolean checked) {
		this.task = task;
		this.description = description;
		this.checked = checked;
		completed = new SimpleBooleanProperty(checked);
	}
	public final BooleanProperty completedProperty() {
		return completed;
	}
	public final void setCompleted(final boolean completed) {
		checked = completed;
		System.out.println("Log: Got Checked");
		this.completed.set(completed);
	}
	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		completed.set(checked);
		this.checked = checked;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}
	@Override
	public int compareTo(ToDo o) {
		// TODO Auto-generated method stub
		return task.toLowerCase().compareTo(o.task.toLowerCase());
	}
}
