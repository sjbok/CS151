
package Calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarEvents {
	public static HashMap<LocalDate, ArrayList<Event>> hash;

	/**
	 * A class that contains all events for the calendar
	 * Takes events of specific date and adds into HashMap. HashMap is then sorted by time
	 */

	CalendarEvents()
	{
		hash = new HashMap<LocalDate, ArrayList<Event>>();
	}

	public static Boolean add(Event e) {
		ArrayList<Event> events = new ArrayList<Event>(); // Declare new event

		if (hash.containsKey(e.getDate())) // Checking if hash already has events on that date
		{
			Boolean check = timeCheck(e.getTime().getst(), e.getTime().getet(), e.getDate());
			if(check)
			{
				return false;
			}
			else
			{
				hash.get(e.getDate()).add(e); // Hash adds more events on that date
				return true;
			}
		} else {
			events.add(e); // New event
			hash.put(e.getDate(), events); // Puts new event into HashMap with new key
			return true;
		}
	}

	/**
	 * Checks for time conflicts when creating a new event
	 */

	public static Boolean timeCheck(LocalTime st, LocalTime et, LocalDate ld)
	{
		if(hash.containsKey(ld)) // Checks whether the specific date has events
		{
			for(int i = 0; i < hash.get(ld).size(); i++) // Checking all events on that day
			{
				TimeInterval a = hash.get(ld).get(i).getTime();
				if(a.getst().isBefore(st) && a.getet().isAfter(et)) // Checking if created time interval is between existing event times
				{
					return true;
				}
				if(a.getst().isAfter(st) && a.getet().isAfter(et) && a.getst().isBefore(et)) // Checking if created time interval overlaps
				// from the left existing event times
				{
					return true;
				}
				if(a.getst().isBefore(st) && a.getet().isBefore(et) && a.getet().isAfter(st)) // Checking if created time interval overlaps 
				// from the right existing event times
				{
					return true;
				}
				if(a.getst().isAfter(st) && a.getet().isBefore(et)) // Checking if created time interval completely covers existing event times
				{
					return true;
				}
				if(a.getst().equals(st) || a.getet().equals(et)) // Checking if created times are equal to existing event times
				{
					return true;
				}
			}
		}
		return false; // No conflict
	}

	/**
	 * Gets all events on a specific date
	 * @param ld
	 */

	public String getEvents(LocalDate ld)
	{
		String month = ld.getMonth().toString();
		month = month.substring(0,1).toUpperCase() + month.substring(1).toLowerCase();
		String date = month + " " + ld.getDayOfMonth() + ", " + ld.getYear();
		String text = "";
		if (hash.containsKey(ld)) // Checking if hash already has events on that date
		{
			for(Event e: hash.get(ld)){
				text += e.toString() + "\n";
			}
			String out = "Events on " + date + ":\n" + text;
			return out;
		}
		else
		{		
			text = "No current events on " + date;
			return text;
		}
		
	}
}