package Calendar;

public class TimeInterval 
{
	private int st;
	private int et;
	
	public TimeInterval(int st, int et)
	{
		this.st = st;
		this.et = et;
	}
	
	public String toString() 
	{
		return st + " - " + et;
	}
	
	public int getst() 
	{
		return st;
	}
	
	public int getet() 
	{
		return et;
	}
	
}
