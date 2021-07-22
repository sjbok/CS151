package Calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MyCalendar 
{
	HashMap<LocalDate, ArrayList<Event>> hash = new HashMap<LocalDate, ArrayList<Event>>();
	
	/**
	 * Takes events of specific date and adds into HashMap. HashMap is then sorted by time
	 */
	public void add(Event e, LocalDate ld)
	{	
		ArrayList<Event> events = new ArrayList<Event>(); // Declare new event
		if(hash.containsKey(ld)) // Checking if hash already has events on that date
		{			
			hash.get(ld).add(e); // Hash adds more events on that date
		}
		else
		{
			events.add(e); // New event
			hash.put(ld, events); // Puts new event into HashMap with new key
		}
		ArrayList<Event> test = hash.get(ld); // Declare new event list containing all events from HashMap on that day
		Collections.sort(test, new SortTime()); // Sorts all events by time
	}
	
	/**
	 * Prints today's month with either brackets around today's date or around days with events. 
     * This algorithm takes into measure the different number of days every month, 
     * special months like February/leap years, and the possibility of 4 weeks - 6 weeks within a month
	 */
	public void printCalendar(LocalDate c, Boolean check)
	{  
		String[] week = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"}; // Used to compare
		String[] week1 = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"}; // Used to print
		int[] end = new int[6]; // Array to hold all the end of week days (Sat)
		String[][] days = new String[6][7]; // Maximum 6 weeks possible in 1 month
		System.out.print(c.getMonth() + " " + c.getYear()); // Printing this month and this year

		LocalDate x = LocalDate.of(c.getYear(), c.getMonth(), 1);
		int a = x.getDayOfWeek().getValue(); // This month's first day of week value
		if(a == 7) // Changing Sunday value from 7 to 0
		{
			a = 0;
		}

		for(int i = 0; i < week.length; i++)
		{			
			if(x.getDayOfWeek().toString().contains(week[i])) // Determining what day the 1st of month is
			{				
				days[0][a] = "1\t"; // Adding the 1st of the month on the correct day of week
			}
			for(int j = a - 1; j >= 0; j--)
			{
				days[0][j] = "\t"; // All days of week before the 1st become empty				
			}
		}
		int count = 2;
		for(int j = a + 1; j < 7; j++) // Filling in the rest of the days in the 1st week of the month
		{
			days[0][j] = count+"\t";
			count++;
		}
		end[0] = count; // Taking in the first end of week(Sat) day

		for(int i = 1; i < 6; i++) // Filling in the rest of the weeks
		{
			for(int j = 0; j < 7; j++)
			{
				days[i][j] = count+"\t";
				count++;
			}
			end[i] = count - 1; // Recording all Saturday dates (-1 to compensate the count++)
		}
		LocalDate d = c.plusMonths(1);
		LocalDate y = LocalDate.of(d.getYear(), d.getMonth(), 1); // Determining the day of week for the next month
		int b = y.getDayOfWeek().getValue(); // Next month's first day of week value
		if(b == 7) // Changing Sunday value from 7 to 0
		{
			b = 0;
		}
		int delete = 0; // Variable holding how many rows require removing excess dates. Delete will either be 1 or 2

		for(int i = 0; i < end.length; i++)
		{
			if(c.getMonthValue() == 2) // Specifying the odd month February
			{
				if(end[i] > 29) // Using array end[] to determine number of weeks with days greater than 29
				{
					delete++;
				}
			}
			else
			{
				if(end[i] > 31) // Using array end[] to determine number of weeks with days greater than 31
				{
					delete++;
				}
			}			
		}
		if(delete == 1 && y.getDayOfWeek().getValue() == 6) // For exception months with 30 days and ends on Friday
		{
			delete++;
		}
		
		for(int i = 0; i < week.length; i++)
		{
			if(delete == 1) // Delete equals 1 (Meaning the month has 6 weeks)
			{
				if(y.getDayOfWeek().toString().contains(week[i])) // Determining what day the 1st of next month is
				{
					days[6-delete][b] = "\t"; // Removing the date on the day of the 1st of next month
				}
				for(int j = b; j < 7; j++)
				{
					days[6-delete][j] = "\t"; // Removing the dates after the day of the 1st of next month of that week
				}
			}
			else // Delete equals 2 (Meaning the month has 5 weeks)
			{
				if(y.getDayOfWeek().toString().contains(week[i])) // Determining what day the 1st of next month is
				{
					days[6-delete][b] = "\t"; // Removing the date on the day of the 1st of next month
				}
				for(int j = b; j < 7; j++)
				{
					days[6-delete][j] = "\t"; // Removing the dates after the day of the 1st of next month of that week
				}
				for(int k = 0; k < 7; k++)
				{
					days[6-delete+1][k] = "\t"; // Removing the dates of the excess week (week 6)
				}
			}			
		}

		if(check) // Checking if calendar is printing today's date or events for month view. True means printing events for month view
		{	
			int n = hash.size();
			List<LocalDate> temp = new ArrayList<LocalDate>(n); // temp list is used to store all hash keys
			for(LocalDate ld : hash.keySet())
			{
				temp.add(ld);
			}
			for(int i = 0; i < temp.size(); i++) // checking if temp key years and months are equal to date c
			{
				if(c.getMonth().equals(temp.get(i).getMonth()) && c.getYear() == temp.get(i).getYear())
				{
					int row = 0; // Variable to hold the event's day
					int day = temp.get(i).getDayOfMonth(); // Event's date
					LocalDate z = LocalDate.of(c.getYear(), c.getMonth(), day);
					int e = z.getDayOfWeek().getValue(); // Event's day of week value
					if(e == 7) // Changing Sunday value from 7 to 0
					{
						e = 0;
					}

					for(int j = 0; j < end.length; j++)
					{
						if(day <= end[j]) // Using array end[] to determine which week the event falls in
						{
							row = j;
							break;
						}
					}
					days[row][e] = "{" + day + "}\t"; // Adding brackets to Event's date in the array
				}
			}
		}
		else // Printing today's date only
		{
			int row = 0; // Variable to hold the week of today's date
			int today = c.getDayOfMonth(); // Today's date
			LocalDate z = LocalDate.of(c.getYear(), c.getMonth(), today);
			int e = z.getDayOfWeek().getValue(); // Today's day of week value
			if(e == 7) // Changing Sunday value from 7 to 0
			{
				e = 0;
			}

			for(int i = 0; i < end.length; i++)
			{
				if(today <= end[i]) // Using array end[] to determine which week today falls in
				{
					row = i;
					break;
				}
			}
			days[row][e] = "[" + today + "]\t"; // Adding brackets to today's date in the array
		}
		
		System.out.println();
		for(int i = 0; i < week1.length; i++)
		{
			System.out.print(week1[i] + "\t"); // Printing the days of week from array week1[]
		}
		System.out.println();
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				System.out.print(days[i][j]); // Printing the days[] array in 2D format
			}
			System.out.println();
		}		
	}
	/**
	 * Takes events from events.txt file and decodes the text into events.
	 * Then the add method is called to add these events into HashMap
	 */
	public void addLine(String line, String line2) // Reading events.txt file and adding to HashMap
	{
		String name = line; // Every odd lines are event names
		String DoW = "";
		String[] count = line2.split(" "); // Every even lines are split
		
		if(count.length > 3) // Catching recurring events
		{
			String days = count[0]; // Collecting repeating days
			String[] day = days.split(""); // Split repeating days into array
			for(int i = 0; i < day.length; i++) // Numbering the repeating days
			{
				if(day[i].toUpperCase().equals("S"))
				{
					DoW += "7";
				}
				if(day[i].toUpperCase().equals("M"))
				{
					DoW += "1";
				}
				if(day[i].toUpperCase().equals("T"))
				{
					DoW += "2";
				}
				if(day[i].toUpperCase().equals("W"))
				{
					DoW += "3";
				}
				if(day[i].toUpperCase().equals("R"))
				{
					DoW += "4";
				}
				if(day[i].toUpperCase().equals("F"))
				{
					DoW += "5";
				}
				if(day[i].toUpperCase().equals("A"))
				{
					DoW += "6";
				}
			}
			LocalTime st = LocalTime.parse(count[1]); // Starting time			
			LocalTime et = LocalTime.parse(count[2]); // Ending Time
			TimeInterval ti = new TimeInterval(st, et);
			String[] date = count[3].split("/"); // Splitting the starting date
			String[] date2 = count[4].split("/"); // Splitting the ending date
			String[] temp = DoW.split(""); // Splitting the recurring days of week values
			int[] temp2 = new int[temp.length];
			for(int i = 0; i < temp.length; i++)
			{
				temp2[i] = Integer.parseInt(temp[i]); // Putting the recurring days of week values into int temp2[] array
			}
			LocalDate sd = LocalDate.of(2000+Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1])); // Declaring start date
			LocalDate ed = LocalDate.of(2000+Integer.parseInt(date2[2]), Integer.parseInt(date2[0]), Integer.parseInt(date2[1])); // Declaring end date
			Boolean check = true;
			Event e = new Event(name, ti); // Creating new event
			add(e, sd); // Adding the first event with start date
			while(check) // While loop until start date equals the end date
			{		
				sd = sd.plusDays(1); // Start date increment by 1
				for(int i = 0; i < temp2.length; i++)
				{
					if(sd.getDayOfWeek().getValue() == temp2[i]) // Adding events on the days of week that matches temp2[] array
					{
						add(e, sd);
					}
				}				
				if(sd.equals(ed)) // While loop check
				{
					check = false;
				}
			}
		}
		else // Catching one time events
		{
			LocalTime st = LocalTime.parse(count[1]); // Starting time	
			LocalTime et = LocalTime.parse(count[2]); // Ending time
			TimeInterval ti = new TimeInterval(st, et);
			String[] date = count[0].split("/"); // Splitting the event date
			LocalDate sd = LocalDate.of(2000+Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1])); // Declaring start date
			Event e = new Event(name, ti); // Creating new event
			add(e, sd); // Adding the event
		}			
	}
	/**
	 * Takes events of specific date and displays all events on that date
	 */
	public void viewDay(LocalDate c)
	{
		System.out.println("________________________________________\n");
		System.out.println(c.getDayOfWeek() + ", " + c.getDayOfMonth() + " " + c.getMonth() + ", " + c.getYear()); // Printing the specified date
		if(hash.containsKey(c)) // Checking whether the date contains any events
		{
			String output = hash.get(c).toString();
			output = output.substring(1, output.length() - 1); // Removing [] from the toString
			String[] output1 = output.split(", "); // Removing "," from the toString			
			for(int i = 0; i < output1.length; i++)
			{
				System.out.println(output1[i]);
			}
		}
		System.out.println("________________________________________");
	}
	/**
	 * Takes events of specific month and displays all days with events on that month
	 */
	public void viewMonth(LocalDate c)
	{
		printCalendar(c, true); // Calling method printCalendar
	}
	/**
	 * Takes name, st, et, ld and formats it into the txt file format
	 */
	public String[] format(String name, LocalTime st, LocalTime et, LocalDate ld)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/YY");
		String[] event = new String[2];
		event[0] = name; // First line name
		event[1] = dtf.format(ld) + " " + st + " " + et; // Second line date, st, et
		
		return event;
	}
	/**
	 * When creating new events, checks whether there's time conflict with existing events
	 */
	public Boolean timeCheck(LocalTime st, LocalTime et, LocalDate ld)
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
			}
		}
		return false; // No conflict
	}
	/**
	 * Converts a given name and date into the correct format for deletion
	 */
	public String[] convert(String name, LocalDate ld)
	{
		for(int i = 0; i < hash.get(ld).size(); i++)
		{
			if(hash.get(ld).get(i).getName().equals(name)) // Finds the correct name event
			{	
				String[] remove = format(name, hash.get(ld).get(i).getTime().getst(), hash.get(ld).get(i).getTime().getet(), ld); // Method format is called
				return remove;
			}
		}
		return null;
	}
	/**
	 * RemoveAll finds all one time events on a specific date and removes it. Dates inputed without one time events show an error message
	 */
	public String[] removeAll(LocalDate ld)
	{
		int n = hash.size();
		List<Integer> num = new ArrayList<Integer>();
		List<LocalDate> temp1 = new ArrayList<LocalDate>(n); // Holding all hash keys
		for(LocalDate d : hash.keySet())
		{
			temp1.add(d); // Adding keys
		}
		if(hash.containsKey(ld)) // Checking if hash has events on given date
		{
			for(int i = 0; i < hash.get(ld).size(); i++) // Using nested for loop to determine whether events on given
				               							 // date is repeated anywhere else on another date
			{
				outerloop:
				for(int j = 0; j < hash.size(); j++)
				{
					for(int k = 0; k < hash.get(temp1.get(j)).size(); k++)
					{
						if(hash.get(ld).get(i).getName().contains(hash.get(temp1.get(j)).get(k).getName()) && !ld.isEqual(temp1.get(j)))
						{				
							num.add(i); // Adding index's of events which are repeated (recurring events)
							break outerloop;
						}
					}
				}		
			}
			String[] remove = new String[hash.get(ld).size()*2];
			int count = 0;			
			for(int i = 0; i < hash.get(ld).size(); i++)
			{
				if(!num.contains(i)) // Only one time events
				{
					String name = hash.get(ld).get(i).getName();
					String[] temp = format(name, hash.get(ld).get(i).getTime().getst(), hash.get(ld).get(i).getTime().getet(), ld); // Formatting the events
					for(int j = 0; j < 2; j++)
					{
						remove[count] = temp[j]; // Adding the one time event
						count++;
					}	
				}										
			}
			if(remove[0] == null) // Only recurring events on that day
			{
				System.out.println("ERROR: No One Time Events\nNo one time events exist on that date");
				String[] a = {""};
				return a;
			}
			else // Date has one time events
			{
				System.out.println("Successfully removed the following one time events:");
				for(int i = 0; i < remove.length; i++)
				{
					if(remove[i] != null)
					{
						System.out.println(remove[i]); // Showing all one time events which are deleted
					}	
				}
				return remove;			
			}
		}
		else // No events on that date
		{
			System.out.println("ERROR: No One Time Events\nNo one time events exist on that date");
			String[] a = {""};
			return a;
		}
	}
	/**
	 * Used to clear the HashMap. Used when deleting events in order to update the calendar
	 */
	public void hashclear()
	{
		hash.clear();
	}
	/**
	 * Check to see whether one time events exist on specific date. Same logic with removeAll method
	 */
	public Boolean eventcheck(LocalDate ld)
	{
		int n = hash.size();
		List<Integer> num = new ArrayList<Integer>();
		List<LocalDate> temp1 = new ArrayList<LocalDate>(n); // Holding all hash keys
		for(LocalDate d : hash.keySet())
		{
			temp1.add(d); // Adding keys
		}
		for(int i = 0; i < hash.get(ld).size(); i++) // Using nested for loop to determine whether events on given
				 									 // date is repeated anywhere else on another date
		{
			outerloop:
			for(int j = 0; j < hash.size(); j++)
			{
				for(int k = 0; k < hash.get(temp1.get(j)).size(); k++)
				{
					if(hash.get(ld).get(i).getName().contains(hash.get(temp1.get(j)).get(k).getName()) && !ld.isEqual(temp1.get(j)))
					{				
						num.add(i); // Adding index's of events which are repeated (recurring events)
						break outerloop;
					}
				}
			}		
		}
		String[] remove = new String[hash.get(ld).size()*2];
		int count = 0;			
		for(int i = 0; i < hash.get(ld).size(); i++)
		{
			if(!num.contains(i)) // Only one time events
			{
				String name = hash.get(ld).get(i).getName();
				String[] temp = format(name, hash.get(ld).get(i).getTime().getst(), hash.get(ld).get(i).getTime().getet(), ld); // Formatting the events
				for(int j = 0; j < 2; j++)
				{
					remove[count] = temp[j]; // Adding the one time event
					count++;
				}	
			}										
		}
		if(remove[0] == null) // If remove[] has no one time events
		{
			return false;
		}
		else // remove[] has one time events
		{
			return true;
		}
	}
	/**
	 * Takes an ArrayList, converts String into LocalDate format, and sorts an ArrayList by date.
	 * The sorted date is then used to create a new ArrayList filled with events in that correct order
	 */
	public ArrayList<String> recursort(ArrayList<String> recur)
	{
		ArrayList<LocalDate> ld = new ArrayList<LocalDate>(); // Holding all dates
		ArrayList<String> out = new ArrayList<String>();
		for(int i = 1; i < recur.size(); i += 2)
		{			
			String[] split = recur.get(i).split(" ");
			String[] date = split[3].split("/");
			ld.add(LocalDate.of(2000+Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]))); // Adding the dates
		}
		Collections.sort(ld, new SortDate()); // Using Collections.sort with custom sorting method
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
		for(int i = 0; i < ld.size(); i++)
		{
			for(int j = 1; j < recur.size(); j+= 2)
			{
				if(recur.get(j).contains(formatter.format(ld.get(i)))) // Checking if event contains the sorted date
				{
					out.add(recur.get(j-1)); // Adding the event name
					out.add(recur.get(j)); // Adding event specifications
				}
			}
		}
		return out;
	}
	

	
}
