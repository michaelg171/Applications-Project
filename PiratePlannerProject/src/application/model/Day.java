package application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * @author Richard Michael Galindo
 * Day class is a class that creates a day object
 * it contains
 * note, month: String
 * events: HashMap<Integer, CalenderEvent>
 * todos: HashMap<Integer, ToDo>
 * day, year: int
 * eventsProperty: SimpleListProperty<CalendarEvent>
 * todoProperty: SimpleListProperty<ToDo>
 * currToDo:ToDo*/
public class Day{
	private String note, month;
	private HashMap<Integer, CalendarEvent> events;
	private HashMap<Integer, ToDo> todos;
	private int day, year;
	private SimpleListProperty<CalendarEvent> eventsProperty;
	private SimpleListProperty<ToDo> todoProperty;
	private CalendarEvent event;
	private ToDo currToDo;

	/**
	 * Day Constructor
	 * Creates a Day object
	 * @param month: String
	 * @param day: int
	 * @param year: int
	 */
	public Day(String month, int day, int year){
		this.day = day;
		this.month = month;
		this.year = year;
		this.note = "";
		events = new HashMap<Integer, CalendarEvent>();
		todos = new HashMap<Integer, ToDo>();
		eventsProperty = new SimpleListProperty<CalendarEvent>(getEventList());
		todoProperty = new SimpleListProperty<ToDo>(getToDoList());
	}
	/**
	 * dayOfWeek
	 * returns an int value 1-7 for the day of the week
	 * @return day: int
	 */
	public int dayOfWeek() {
		return Time.dayOfWeek(Time.revAbbrevMonth(month), day, year);
	}
	/**
	 * addToDo
	 * adds a ToDo object to the todo hash map Day object
	 * @param todo: ToDo
	 */
	public void addToDo(ToDo todo) {
		todos.put(todo.hashCode(), todo);
		updateToDoProperty();
	}
	/*removeToDo
	 * removes the todo object from the hash map 
	 * @param1 hashcode: int */
	public void removeToDo(int hashcode) {
		todos.remove(hashcode);
		updateToDoProperty();
	}/*addEvent
	adds an event to the CalendarEvent hash map
	@param event: CalendarEvent*/
	public void addEvent(CalendarEvent event) {
		events.put(event.hashCode(), event);
		updateEventProperty();
	}
	/**
	 * removeEvent
	 * removes an event form the CalendarEvent HashMap 
	 * @param code:int
	 */
	public void removeEvent(int hashcode) {
		events.remove(hashcode);
		updateEventProperty();
	}
	/**
	 * getCurrToDo
	 * returns the current todo object
	 * @return currToDo: ToDo
	 */
	public ToDo getCurrToDo() {
		return currToDo;
	}
	/*setCurrToDo
	 * sets a todo object to the day
	 * @param currToDo: ToDo*/
	public void setCurrToDo(ToDo currToDo) {
		this.currToDo = currToDo;
	}
	/**
	 * loadDay
	 * loads the contents of a specific day from a file
	 * it will fill the days ToDo if any
	 * the Days CalendarEvent HashMap if any
	 *  and the Days Notes if any
	 */
	public void loadDay() {
		String path = "Calendar/" + year + "/" + month + "/" + day + "/ToDos.txt"; 
		File file = new File(path);
		if(file.exists()) {
			try {
				Scanner scan = new Scanner(file);
				while(scan.hasNextLine()) {
					String [] token = scan.nextLine().split("\\|");
					ToDo temp = new ToDo(token[0], token[1], token[2].equals("true"));
					addToDo(temp);
				}
				scan.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		path = "Calendar/" + year + "/" + month + "/" + day + "/Events.txt";
		file = new File(path);
		if(file.exists()) {
			try {
				Scanner scan = new Scanner(file);
				while(scan.hasNextLine()) {
					String [] token = scan.nextLine().split("\\|");
					CalendarEvent temp = new CalendarEvent(token[0], token[1], token[2]);
					addEvent(temp);
				}
				scan.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		path = "Calendar/" + year + "/" + month + "/" + day + "/Notes.txt";
		file = new File(path);
		if(file.exists()) {
			try {
				Scanner scan = new Scanner(file);
				String lines = "";
				while(scan.hasNextLine()) {
					String line = scan.nextLine();
					if(scan.hasNextLine())
						line += "\n";
					lines += line;
				}
				setNote(lines);
				scan.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * saveDay
	 * this will find the specifice file based on a path to the file
	 * if the path does not exist then the directories and the file 
	 * will be created it will then save the data enterd by the user
	 * it will save the 
	 * ToDo object if any
	 * Notes if any
	 * CalendarEvent hashmap if any
	 */
	public void saveDay() {
		String path = "Calendar/" + year + "/" + month + "/" + day;
		File file = new File(path);
		if(!file.exists() && todos.size() > 0) 
			file.mkdirs();
		if(file.exists()) {
			try {
				FileWriter fw = new FileWriter(new File(path + "/ToDos.txt"));
				boolean flag = true;
				for(ToDo td: todos.values()) {
					if(flag) {
						fw.write(td.getTask() + "|" + td.getDescription() + "|" + td.getChecked() + "\n");
						flag = false;
					}
					else
						fw.append(td.getTask() + "|" + td.getDescription() + "|" + td.getChecked() + "\n");
				}
				System.out.println("Log: Saved ToDos");
				fw.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		if(!file.exists() && events.size() > 0) 
			file.mkdirs();
		if(file.exists()) {
			try {
				FileWriter fw = new FileWriter(new File(path + "/Events.txt"));
				boolean flag = true;
				for(CalendarEvent ce: events.values()) {
					if(flag) {
						fw.write(ce.getTitle() + "|" + ce.getTime() + "|" + ce.getDescription() + "\n");
						flag = false;
					}
					else
						fw.append(ce.getTitle() + "|" + ce.getTime() + "|" + ce.getDescription() + "\n");
				}
				System.out.println("Log: Saved CalendarEvents");
				fw.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		if(!file.exists() && !note.equals("")) 
			file.mkdirs();
		if(file.exists()) {
			try {
				FileWriter fw = new FileWriter(new File(path + "/Notes.txt"));
				fw.write(note);
				System.out.println("Log: Saved notes");
				fw.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * getEvent
	 * returns an event from the day
	 * @return event: CalendarEvent
	 */
	public CalendarEvent getEvent() {
		return event;
	}
	/**
	 * setEvent
	 * sets an event for the day
	 * @param event: CalendarEvent
	 */
	public void setEvent(CalendarEvent event) {
		this.event = event;
	}
	/**
	 * getNote
	 * returns a note from the day
	 * @return note: String
	 */
	public String getNote() {
		return note;
	}
	/**
	 * setNote
	 * sets a note for the Day
	 * @param note: String
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * getEvents
	 * returns an entire hash map of events for the day
	 * @return getEvents: HashMap<Integer, CalenderEvent>()
	 */
	public HashMap<Integer, CalendarEvent> getEvents() {
		return events;
	}
	/**
	 * setEvents
	 * sets a hashmap of events for the day
	 * @param events: HashMap<Integer, CalendarEvent>
	 */
	public void setEvents(HashMap<Integer, CalendarEvent> events) {
		this.events = events;
	}
	/**
	 * getTodos
	 * returns hashmap of todos from a day
	 * @return HashMap<Integer, ToDo>
	 */
	public HashMap<Integer, ToDo> getTodos() {
		return todos;
	}
	/**
	 *setToDos
	 * sets a hashMap of todos to a day object
	 * @param todos: HashMap<Integer, ToDo>
	 */
	public void setTodos(HashMap<Integer, ToDo> todos) {
		this.todos = todos;
	}
	/**
	 * getMonth
	 * returns the name of the month this day is in
	 * @return month:String  
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * setMonth
	 * sets the name of the month this day is in
	 * @param month:String
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * getDay
	 * gets the int value of this day 1-7
	 * @return day: int
	 */
	public int getDay() {
		return day;
	}
	/**
	 * setDay
	 * sets the int value of the day 1-7
	 * @param day: int
	 */
	public void setDay(int day) {
		this.day = day;
	}
	/**
	 * getYear
	 * returns the year int value (ex."2018") this day is in
	 * @return year:int
	 */
	public int getYear() {
		return year;
	}
	/**
	 * setYear 
	 * sets the year for this specific day
	 * @param year:int
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * getToDoList
	 * returns an ArrayList of ToDo object 
	 * for the day for the view
	 * @return ObservableList<ToDo>
	 */
	public ObservableList<ToDo> getToDoList(){
		ArrayList<ToDo> todoList = new ArrayList<ToDo>();
		todoList.addAll(todos.values());
		Collections.sort(todoList);
		return FXCollections.observableArrayList(todoList);
	}
	/**
	 * updateToDoProperty
	 * updates the ToDo List by calling the get ToDoList
	 * @return todoProperty:SimpleListProperty<ToDo>
	 */
	public SimpleListProperty<ToDo> updateToDoProperty(){
		todoProperty.set(getToDoList());
		return todoProperty;
	}
	/**
	 * getEventList
	 * creates an ArrayList and fills the array list with the events 
	 * sorts the list and returns the arrayList for the View
	 * @returns: events: ObservabelList<CalendarEvent>
	 */
	public ObservableList<CalendarEvent> getEventList(){
		ArrayList<CalendarEvent> eventList = new ArrayList<CalendarEvent>();
		eventList.addAll(events.values());
		Collections.sort(eventList);
		ObservableList<CalendarEvent> events = FXCollections.observableArrayList(eventList);
		return events;
	}
	/**
	 * updateEventProperty
	 * updates the list propertys for the events
	 * @returns eventsProperty: SimpleListProperty<CalendarEvent>
	 */
	public SimpleListProperty<CalendarEvent> updateEventProperty(){
		eventsProperty.set(getEventList());
		return eventsProperty;
	}
}
