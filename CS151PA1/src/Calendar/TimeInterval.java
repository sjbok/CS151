package Calendar;

import java.time.LocalTime;

public class TimeInterval 
{
	private LocalTime st;
	private LocalTime et;
	
	public TimeInterval(LocalTime st, LocalTime et)
	{
		this.st = st;
		this.et = et;
	}
	
	public String toString() 
	{
		return st + " - " + et;
	}
	
	public LocalTime getst()
	{
		return st;
	}
	
	public LocalTime getet()
	{
		return et;
	}
	
}
