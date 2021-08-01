package Calendar;

import java.time.LocalDate;

public class Event
{
	private String name;
	private TimeInterval ti;
	private LocalDate ld;
	
	public Event(String name, TimeInterval ti, LocalDate ld)
	{
		this.name = name;
		this.ti = ti;
		this.ld = ld;
	}
	
	public String getName()
	{
		return name;
	}
	
	public TimeInterval getTime() 
	{
		return ti;
	}
	
	public String toString() 
	{
		return name + " : " + ti.getTime();
	}

	public LocalDate getDate(){ return ld; }
}
