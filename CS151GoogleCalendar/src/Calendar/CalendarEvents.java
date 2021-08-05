
package Calendar;
/**
 * @author Liz Huelfenhaus
 * @author Sung Bok Jun
 * @version 1.0 8/5/2021
 */

import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The base of the Calendar which contains all the events
 * Takes events of specific date and adds into HashMap. HashMap is then sorted by time
 */

public class CalendarEvents {
	public static HashMap<LocalDate, ArrayList<Event>> hash;
	public static String text = "";

	/**
	 * Constructs an empty calendar events
	 * Creates the hashmap of events with the date as the key
	 */

	CalendarEvents()
	{
		hash = new HashMap<LocalDate, ArrayList<Event>>();
	}

	/**
	 * Adds an event to the calendar
	 * @param e - the event to be added
	 * @return - true if the add was successful, and false if there was a conflict
	 */
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
	 * Checks for conflicts when creating events
	 * @param st - start time
	 * @param et - end time
	 * @param ld - date requested
	 * @return - returns a true if there is a conflict, false is there is no conflict
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
	 * Gets all events on a selected date
	 * @param ld - the selected date
	 * @param check - the view that is selected
	 * @return - returns a String of the events for the appropriate view
	 */

	public String getEvents(LocalDate ld, String check)
	{
		ViewPanel.viewArea.setFont(new Font("Arial", Font.PLAIN, 12));
		String text = "";

		/**
		 * Shows the day view for the selected day
		 */
		if(check.equals("d"))
		{
			String month = ld.getMonth().toString();
			month = month.substring(0,1).toUpperCase() + month.substring(1).toLowerCase();
			String date = month + " " + ld.getDayOfMonth() + ", " + ld.getYear();		
			if (hash.containsKey(ld)) // Checking if hash already has events on that date
			{
				ArrayList<Event> test = hash.get(ld); // Declare new event list containing all events from HashMap on that day
				Collections.sort(test, new SortTime()); // Sorts all events by time
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
		/**
		 * Shows the week view for the selected day starting from
		 * the previous Sunday to the following Saturday
		 */

		else if(check.equals("w"))
		{			
			ViewPanel.viewArea.setFont(new Font("Arial", Font.PLAIN, 12));
			String day = "";
			if(!ld.getDayOfWeek().toString().equals("SUNDAY"))
			{
				while(!ld.getDayOfWeek().toString().equals("SUNDAY"))
				{
					ld = ld.minusDays(1);		
				}			
			}
			String out = "";
			for(int i = 0; i < 7; i++)
			{
				text = "";
				day = ld.getDayOfWeek().toString();
				day = day.substring(0, 3);
				out = out + day + " " + ld.getDayOfMonth() + "\n\n";			
				if (hash.containsKey(ld)) // Checking if hash already has events on that date
				{
					ArrayList<Event> test = hash.get(ld); // Declare new event list containing all events from HashMap on that day
					Collections.sort(test, new SortTime()); // Sorts all events by time
					for(Event e: hash.get(ld)){
						text += e.toString() + "\n";
					}
					out = out + text + "________________________________________________________________________\n";
				}
				else
				{		
					text = "No current events";
					out = out + text + "\n\n________________________________________________________________________\n";
				}
				ld = ld.plusDays(1);
			}
			return out;
		}
		/**
		 * Shows the month view for the selected month
		 */
		else if(check.equals("m"))
		{
			ViewPanel.viewArea.setFont(new Font("Arial", Font.PLAIN, 10));
			String day = "";
			int currentMonth = ld.getMonthValue();
			int monthCheck = ld.getMonthValue();
			LocalDate ld2 = LocalDate.of(ld.getYear(), ld.getMonthValue(), 1);
			if(!ld2.getDayOfWeek().toString().equals("SUNDAY"))
			{
				while(!ld2.getDayOfWeek().toString().equals("SUNDAY"))
				{
					ld2 = ld2.minusDays(1);				
				}			
			}
			String out = "";
			outerloop:
			for(int j = 0; j < 6; j++)
			{
				if(j == 5)
				{
					monthCheck = ld2.getMonthValue();
				}
				if(currentMonth != monthCheck)
				{
					break outerloop;
				}
				for(int i = 0; i < 7; i++)
				{
					text = "";
					day = ld2.getDayOfWeek().toString();
					day = day.substring(0, 3);
					out = out + day + " " + ld2.getDayOfMonth() + "\t";
					ld2 = ld2.plusDays(1);
				}				
				out = out + "\n\n";
				for(int i = 0; i < 7; i++)
				{
					if(hash.containsKey(ld2.minusDays(7 - i)))
					{
						if(hash.get(ld2.minusDays(7 - i)).size() == 1)
						{
							text = hash.get(ld2.minusDays(7 - i)).size() + " Event\t";
						}
						else
						{
							text = hash.get(ld2.minusDays(7 - i)).size() + " Events\t";
						}
					}
					else
					{
						text = "\t";
					}
					out = out + text;
				}			
				out = out + "\n\n\n\n\n";
			}		
			return out;
		}
		return text;
	}

	/**
	 * Shows the agenda by recursively collecting all dates in the range
	 * @param sd - start date for the range
	 * @param ed - end date for the range
	 * @return - returns a String of all agenda events
	 */
	
	public String viewAgenda(LocalDate sd, LocalDate ed){
		ViewPanel.viewArea.setFont(new Font("Arial", Font.PLAIN, 12));
		if(!sd.isAfter(ed)) {
			if(hash.containsKey(sd)) {
				for (Event e : hash.get(sd)) {
					text += e.agendaString();
					text += "\n";
				}
			}
			viewAgenda(sd.plusDays(1), ed);
		}
		if(text.equals(""))
			text = "No events during this range.";
		return text;
	}
}