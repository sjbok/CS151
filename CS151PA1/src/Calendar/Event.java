package Calendar;

public class Event 
{
	private String name;
	private TimeInterval ti;	
	
	public Event(String name, TimeInterval ti)
	{
		this.name = name;
		this.ti = ti;
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
		return name + " : " + ti.toString();
	}
}
