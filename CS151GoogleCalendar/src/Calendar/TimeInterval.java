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

		this.start = LocalTime.parse(s);
		this.end = LocalTime.parse(e);
	}
	
	TimeInterval(int s, int e){
		//for recurring events
		this.start=LocalTime.of(s, 0);
		this.end=LocalTime.of(e, 0);
	}

	

	public void setStart(String s){
		this.start = LocalTime.parse(s);
	}

	public void setEnd(String e){
		this.end = LocalTime.parse(e);
	}
	
	public LocalTime getst()
	{
		return start;
	}
	
	public LocalTime getet()
	{
		return end;
	}

	/**
	 * @return appended string of time interval
	 */
	public String getTime(){
		return start.toString() + " - " + end.toString();
	}

}
