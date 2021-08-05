package Calendar;

import java.awt.Font;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CalendarEvents {
	public static HashMap<LocalDate, ArrayList<Event>> hash;
	public static String text = "";

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

	public String getEvents(LocalDate ld, String check)
	{
		ViewPanel.viewArea.setFont(new Font("Arial", Font.PLAIN, 12));
		String text = "";
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
		else if(check.equals("w"))
		{
			ViewPanel.viewArea.setFont(new Font("Arial", Font.PLAIN, 12));
			String day = "";
			if(!ld.getDayOfWeek().toString().equals("SUNDAY"))
			{
				while(!ld.getDayOfWeek().toString().equals("SUNDAY"))
				{
					ld = ld.plusDays(1);
					day = ld.getDayOfWeek().toString();
				}
				ld = ld.minusWeeks(1);
			}
			String out = "";
			for(int i = 0; i < 7; i++)
			{
				text = "";
				day = ld.getDayOfWeek().toString();
				day = day.substring(0, 3);
				out = out + day + " " + ld.getDayOfMonth() + "\n";
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
		else if(check.equals("m"))
		{
			ViewPanel.viewArea.setFont(new Font("Arial", Font.PLAIN, 10));
			String day = "";
			if(!ld.getDayOfWeek().toString().equals("SUNDAY"))
			{
				while(!ld.getDayOfWeek().toString().equals("SUNDAY"))
				{
					ld = ld.plusDays(1);
					day = ld.getDayOfWeek().toString();
				}
				ld = ld.minusWeeks(1);
			}
			String out = "";
			for(int i = 0; i < 35; i++)
			{
				text = "";
				day = ld.getDayOfWeek().toString();
				day = day.substring(0, 3);
				out = out + day + " " + ld.getDayOfMonth() + "\n";
				ld = ld.plusDays(1);
			}
			return out;
		}

		return text;
	}

	public String viewAgenda(LocalDate sd, LocalDate ed){
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