package Calendar;

import java.time.LocalTime;

/**
 * Time interval class that is used in the event class
 */
public class TimeInterval {
	private LocalTime start;
	private LocalTime end;

	/**
	 * Creates a time interval
	 * @param s start time
	 * @param e end time
	 */
	TimeInterval(String s, String e){
		s.replace(":", "");
		e.replace(":", "");

		start = LocalTime.parse(s);
		end = LocalTime.parse(e);
	}

	/**
	 * @return appended string of time interval
	 */
	public String getTime(){
		return start.toString() + " - " + end.toString();
	}

}
