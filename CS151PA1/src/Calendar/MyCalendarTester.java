package Calendar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class MyCalendarTester
{	
	public static void main(String[] args) throws IOException
	{
		MyCalendar mc = new MyCalendar(); // Calling MyCalendar
		TextManipulate tm = new TextManipulate(); // Calling TextManipulate
		LocalDate cal = LocalDate.now(); // Taking today's date
		mc.printCalendar(cal, false); // Printing today's date on calendar
		
		FileReader fr = new FileReader("events.txt");
		BufferedReader br = new BufferedReader(fr);
		String line;
		String line2;
		while ((line = br.readLine()) != null)
		{
			line2 = br.readLine();
			mc.addLine(line, line2); // Adding to HashMap
		}
		br.close();
		fr.close();
		tm.load();
		System.out.println("\nLoading is done!\n");
		
		Boolean quit = true;
		while(quit) // while loop to continue the BufferedReader until user quits
		{
			BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in)); //Initiating BufferedReader onto console
			System.out.println("___________________________________________________________________________________________");
			System.out.println("Select one of the following main menu options:\r\n"
								+ "[V]iew by, [C]reate, [G]o to, [E]vent list, [D]elete, [Q]uit");
			String ans = br1.readLine();
			if(ans.toUpperCase().equals("Q")) // Boolean check for quit
			{
				quit = false; // Boolean change
				br1.close(); // Closing BufferedReader
				tm.filecheck(); // Checking for existing files
				File file = new File("delete.txt");
		        file.delete(); // Deleting delete.txt
		        file = new File("temp.txt");
		        file.delete(); // Deleting temp.txt
				System.out.println("Goodbye!");
			}
			else
			{
				if(ans.toUpperCase().equals("V")) // View
				{
					Boolean check = true;
					while(check) // another while loop for a different user menu until user goes back
					{
						System.out.println("___________________________________________________________________________________________");
						System.out.println("\n[D] for Day view or [M] for Month view?");
			        	String input = br1.readLine();
			        	if(input.toUpperCase().equals("G")) // boolean check to go back
			        	{
			        		check = false;
			        	}
			        	else
			        	{
			        		if(input.toUpperCase().equals("D")) // View by Day
							{		
			        			LocalDate temp = cal;
			        			while(check)
		        				{			        				
			        				mc.viewDay(temp); // Showing events on the day
		        					System.out.println("\n[P]revious or [N]ext or [G]o back to the main menu?");
						        	String input1 = br1.readLine();
						        	if(input1.toUpperCase().equals("G")) // boolean check to go back
						        	{
						        		check = false;
						        	}
						        	else
						        	{					  
						        		if(input1.toUpperCase().equals("P")) // Previous
										{
						        			temp = temp.minusDays(1); // One day before
										}
						        		
						        		if(input1.toUpperCase().equals("N")) // Next
										{
						        			temp = temp.plusDays(1); // One day after
										}
						        	}
		        				}
							}
			        		
			        		if(input.toUpperCase().equals("M")) // View by Month
							{
			        			LocalDate temp = cal;
			        			while(check)
		        				{
			        				mc.viewMonth(temp); // Showing days with events on the month
		        					System.out.println("\n[P]revious or [N]ext or [G]o back to the main menu?");
						        	String input1 = br1.readLine();
						        	if(input1.toUpperCase().equals("G")) // boolean check to go back
						        	{
						        		check = false;
						        	}
						        	else
						        	{
						        		
						        		if(input1.toUpperCase().equals("P")) // Previous
										{						  
						        			temp = temp.minusMonths(1); // One month before
										}
						        		
						        		if(input1.toUpperCase().equals("N")) // Next
										{
						        			temp = temp.plusMonths(1); // One month after
										}
						        	}
		        				}			    				
							}		        		
			        	}
					}
				}
				
				if(ans.toUpperCase().equals("C")) // Create
				{
					System.out.println("___________________________________________________________________________________________");
					System.out.println("\nEnter the name of event");
					String input = br1.readLine(); // Taking name
					System.out.println("\nEnter the date of event in MM/DD/YYYY");
					String[] date = br1.readLine().split("/"); // Taking date
					LocalDate ld = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1])); // Formatting date
					System.out.println("\nEnter the starting time of event in 24 hour format HH:MM (example 06:30 or 18:45)");
					LocalTime st = LocalTime.parse(br1.readLine()); // Taking st
					System.out.println("\nEnter the ending time of event in 24 hour format HH:MM (example 06:30 or 18:45)");
					LocalTime et = LocalTime.parse(br1.readLine()); // Taking et
					
					if(mc.timeCheck(st, et, ld)) // Checking if time clashes with existing events
					{
						System.out.println("ERROR: Time clash\nEvent couldn't be created as it conflicts with other existing events");
					}
					else
					{
						FileWriter fw = new FileWriter("temp.txt", true); // Writes new created events in temp.txt
						String[] event = mc.format(input, st, et, ld);
						fw.append(event[0] + "\n");
						fw.append(event[1] + "\n");
						mc.addLine(event[0], event[1]);
						fw.close();
					}				
				}
				
				if(ans.toUpperCase().equals("G")) // Goto
				{
					System.out.println("___________________________________________________________________________________________");
					System.out.println("\nEnter the date of event in MM/DD/YYYY");
					String[] date = br1.readLine().split("/"); // Takes the date
					LocalDate ld = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1])); // Formatting the date
					mc.viewDay(ld); // Showing all events on that date
				}
				
				if(ans.toUpperCase().equals("E")) // Events
				{
					tm.filecheck(); // Checking files to know whether there were created or deleted events. Force all current events to go to output.txt
					ArrayList<String> recur = new ArrayList<String>();
					ArrayList<String> onetime = new ArrayList<String>();
					FileReader f = new FileReader("output.txt"); // Taking events from output.txt
					BufferedReader b = new BufferedReader(f);
					String linee;
					String linee2;
					while ((linee = b.readLine()) != null)
					{
						linee2 = b.readLine();
						String name = linee; // Every odd lines are event names
						String[] count = linee2.split(" "); // Every even lines are split
						
						
						if(count.length > 3) // Catching recurring events
						{			
							recur.add(name); // Adding name
							recur.add(linee2); // Adding specifications
						}
						else // Catching one time events
						{
							LocalTime st = LocalTime.parse(count[1]); // Starting time	
							LocalTime et = LocalTime.parse(count[2]); // Ending time
							TimeInterval ti = new TimeInterval(st, et);
							String[] date = count[0].split("/"); // Splitting the event date
							LocalDate sd = LocalDate.of(2000+Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1])); // Declaring start date
							onetime.add(sd.getYear() + " " + sd.getDayOfWeek() + " " + sd.getMonth() +" "+ sd.getDayOfMonth() +" "+ ti.toString() +" "+ name); 
							// Adding one time events in specific format
						}
					}
					b.close();
					f.close();								
					ArrayList<String> sortrecur = mc.recursort(recur); // Sorting the recurring events
					System.out.println("ONE TIME EVENTS:\n"); // Printing out the one time events
					for(int i = 0; i < onetime.size(); i++)
					{
						System.out.println(onetime.get(i));
					}
					System.out.println("______________________________________________________________");
					System.out.println("RECURRING EVENTS:\n"); // Printing out the recurring events
					for(int i = 0; i < sortrecur.size(); i++)
					{
						System.out.println(sortrecur.get(i));
					}
				}
				
				if(ans.toUpperCase().equals("D")) // Delete
				{
					System.out.println("___________________________________________________________________________________________");
					System.out.println("\n[S]elected, [A]ll, [DR]Delete Recurring");
					String input1 = br1.readLine();
					
					if(input1.toUpperCase().equals("S")) // Selected
					{
						System.out.println("\nEnter the date in MM/DD/YYYY");
						String[] date = br1.readLine().split("/"); // Takes inputed date
						LocalDate ld = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1])); // Format the date
						if(mc.hash.containsKey(ld)) // Check whether HashMap has the date
						{
							if(mc.eventcheck(ld)) // If the date has events, checking if one time events exist on this date
							{
								mc.viewDay(ld); // Showing day view of that date
								System.out.println("\nEnter the name of the event to delete:");
								String delete = br1.readLine(); // Taking name of one time event
								FileWriter fw = new FileWriter("delete.txt", true); // Adding the name and specifications of that event to delete.txt
								String[] write = mc.convert(delete, ld); // Converts the String and date into correct format
								fw.append(write[0] + "\n");
								fw.append(write[1] + "\n");
								fw.close();
								tm.delete(); // Calls delete() to create output.txt without deleted events
								mc.hashclear(); // Clears the HashMap
								FileReader f = new FileReader("output.txt"); // Reads from output.txt
								BufferedReader b = new BufferedReader(f);
								String linee;
								String linee2;
								while ((linee = b.readLine()) != null)
								{
									linee2 = b.readLine();
									mc.addLine(linee, linee2); // Adds events onto HashMap
								}
								b.close();
								f.close();
							}
							else // ONly recurring events on that day
							{
								System.out.println("ERROR: No One Time Events\nNo one time events exist on that date");
							}					
						}
						else // No events on that day
						{
							System.out.println("ERROR: No One Time Events\nNo one time events exist on that date");
						}
					}
					
					if(input1.toUpperCase().equals("A")) // All
					{
						System.out.println("\nEnter the date in MM/DD/YYYY");
						String[] date = br1.readLine().split("/"); // Takes inputed date
						LocalDate ld = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1])); // Format the date
						String[] remove = mc.removeAll(ld); // Calls removeAll method
						FileWriter fw = new FileWriter("delete.txt", true); // Adding the name and specifications of that event to delete.txt
						for(int i = 0; i < remove.length; i++)
						{
							fw.append(remove[i] + "\n");
						}
						fw.close();
						tm.delete(); // Calls delete() to create output.txt without deleted events
						mc.hashclear(); // Clears the HashMap
						FileReader f = new FileReader("output.txt"); // Reads from output.txt
						BufferedReader b = new BufferedReader(f);
						String linee;
						String linee2;
						while ((linee = b.readLine()) != null)
						{
							linee2 = b.readLine();
							mc.addLine(linee, linee2); // Adds events onto HashMap
						}
						b.close();
						f.close();
					}
					
					if(input1.toUpperCase().equals("DR")) // Delete Recurring
					{
						System.out.println("\nEnter the name of a recurring event to delete:");
						String delete = br1.readLine(); // Takes name of recurring event
						FileReader fr1 = new FileReader("temp.txt"); // Reads from temp.txt
						BufferedReader br2 = new BufferedReader(fr1);
						String line3;
						while ((line3 = br2.readLine()) != null)
						{
							if(line3.contains(delete)) // When inputed name matches event name on temp.txt
							{
								FileWriter fw = new FileWriter("delete.txt", true); // Writes to delete.txt the deleted recurring event with format from temp.txt
								String write = line3;
								String write1 = br2.readLine();
								fw.append(write + "\n");
								fw.append(write1 + "\n");
								fw.close();
							}
						}
						br2.close();
						fr1.close();
						tm.delete(); // Calls delete() to create output.txt without deleted events
						mc.hashclear(); // Clears the HashMap
						FileReader f = new FileReader("output.txt"); // Reads from output.txt
						BufferedReader b = new BufferedReader(f);
						String linee;
						String linee2;
						while ((linee = b.readLine()) != null)
						{
							linee2 = b.readLine();
							mc.addLine(linee, linee2); // Adds events onto HashMap
						}
						b.close();
						f.close();
					}
				}
			}
		}
	}

}
