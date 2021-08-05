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

	TimeInterval(){
		//for recurring events
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
	
	public String getTime2() {
		String a = start.toString();
		String[] a1 = a.split(":");
		
		String b = end.toString();
		String[] b1 = b.split(":");
		
		return a1[0] + " - " + b1[0];
	}

}
