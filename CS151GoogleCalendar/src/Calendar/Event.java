package Calendar;
/**
 * @author Sung Jun Bok
 * @author Liz Huelfenhaus
 * @version 1.0 8/5/2021
 */

import java.time.LocalDate;

/**
 * A class the create an event for the calendar events class
 */
public class Event {
	private String name;
	private TimeInterval ti;
	private LocalDate ld;

	/**
	 * Constructs a new one time event
	 * @param name - title of the event
	 * @param ti - time interval of the event
	 * @param ld - date of the event
	 */
	public Event(String name, TimeInterval ti, LocalDate ld) {
		this.name = name;
		this.ti = ti;
		this.ld = ld;
	}

	/**
	 * @return - time interval of the event
	 */
	public TimeInterval getTime() {
		return ti;
	}

	/**
	 * @return - returns a string format of the event title and time interval
	 */
	public String toString() {
		return name + ":\t\t" + ti.getTime();
	}

	/**
	 * @return - returns a string format of the date and name of the event for the agenda view
	 */
	public String agendaString(){ return ld.toString() + " " + name; }

	/**
	 * @return - returns the date of the event
	 */
	public LocalDate getDate() {
		return ld;
	}

}