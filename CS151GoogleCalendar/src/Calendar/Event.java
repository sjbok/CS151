package Calendar;

import java.time.LocalDate;

public class Event {
	private String name;
	private TimeInterval ti;
	private LocalDate ld;

	public Event(String name, TimeInterval ti, LocalDate ld) {
		this.name = name;
		this.ti = ti;
		this.ld = ld;
	}
	public Event(){
		//for recurring events
	}

	public String getName() {
		return name;
	}

	public TimeInterval getTime() {
		return ti;
	}

	public String toString() {
		return name + ":\t\t" + ti.getTime();
	}
	
	public String MonthtoString(int count) {
		String out = name + ": ";
		if(name.length() > 13)
		{
			out = name.substring(0, 13) + "-\n";
			for(int i = 0; i < count; i++)
			{
				out = out + "\t";
			}
			out = out + name.substring(13, name.length()) + ": ";
		}
		return out + ti.getTime2();
	}

	public String agendaString(){ return ld.toString() + " " + name; }

	public LocalDate getDate() {
		return ld;
	}


}