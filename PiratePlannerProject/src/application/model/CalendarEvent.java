package application.model;

/**
 * The CalendarEvent class represents an Event that will be placed on the Calendar
 * @author Richard Michael Galindo
 *
 */
public class CalendarEvent implements Comparable<CalendarEvent> {
	private String time;
	private String title;
	private String description;
	
	
	/**
	 * CalendarEvent Constructor
	 * @param a is the title
	 * @param b is the time
	 * @param c is the description
	 */
	public CalendarEvent(String a, String b, String c) {
		this.title = a;
		this.time = b;
		this.description = c;
	}
	/**
	 * The getTime method returns a String of the time
	 * @return a String of the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * The setTime method sets a String to the time
	 * @param time a String which is the new value of time
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * The getTitle method returns a String of the title
	 * @return a String of title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * The setTitle method sets a String to the time 
	 * @param title a String which is the new value of title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * The getDescription method returns a String of the description
	 * @return a String of description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * The setDescription method sets a String to the description
	 * @param description a String  which is the value of description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * The toString method is the String that will be shown on the ListView
	 * @return a String in the format on the ListView
	 */
	public String toString() {
		return String.format("%-20s%s", time, title);
		
	}
	/**
	 * The saveString method is the String that will be saved (deprecated)
	 * @return a String which is the save String
	 */
	public String saveString() {
		return this.getTitle() + ":" + this.getTime() + ":" + this.getDescription();
	}
	/**
	 * The compareTo method is the comparing method to check where something should go in the ListView
	 * @param o is the CalendarEvent being compared
	 * @return an int to determine the location of where it'd go in the list
	 */
	@Override
	public int compareTo(CalendarEvent o) {
		// TODO Auto-generated method stub
		String otime = o.getTime();
		String ampm = time.substring(time.length() - 2);
		String oampm = otime.substring(otime.length() - 2);
		System.out.println("Log: AMPM :" + ampm + " Other AMPM: " + oampm);
		if(ampm.equals(oampm)) {
			int id = time.indexOf(":");
			int oid = otime.indexOf(":");
			int hr = Integer.parseInt(time.substring(0, id)) % 12;
			int ohr = Integer.parseInt(otime.substring(0, oid)) % 12;
			if(hr == ohr) {
				int min = Integer.parseInt(time.substring(id + 1, id + 3));
				int omin = Integer.parseInt(otime.substring(oid + 1, oid + 3));
				return min - omin;
			}
			else return hr - ohr;
		}
		else return ampm.compareTo(oampm);
	}
}
