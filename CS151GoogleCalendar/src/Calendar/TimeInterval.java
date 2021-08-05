package Calendar;
/**
 * @author Liz Huelfenhaus
 * @version 1.0 8/5/2021
 */
import java.time.LocalTime;

/**
 * Time interval class that is used in the event class
 */
public class TimeInterval {
	private LocalTime start;
	private LocalTime end;

	/**
	 * Creates a time interval, removes the colon from the String if necessary
	 * @param s - start time
	 * @param e - end time
	 */
	TimeInterval(String s, String e){
		s.replace(":", "");
		e.replace(":", "");

		this.start = LocalTime.parse(s);
		this.end = LocalTime.parse(e);
	}

	/**
	 * Creates a time interval for recurring events
	 * @param s - the start hour
	 * @param e -  the end hour
	 */
	
	TimeInterval(int s, int e){
		//for recurring events
		this.start=LocalTime.of(s, 0);
		this.end=LocalTime.of(e, 0);
	}

	/**
	 * @return - the start time of the time interval
	 */
	public LocalTime getst()
	{
		return start;
	}

	/**
	 * @return - the end time of the time interval
	 */
	
	public LocalTime getet()
	{
		return end;
	}

	/**
	 * @return - appended string of time interval
	 */
	public String getTime(){
		return start.toString() + " - " + end.toString();
	}

}
