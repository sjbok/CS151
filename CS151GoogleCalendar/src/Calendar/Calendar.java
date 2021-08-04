package Calendar;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.*;

public class Calendar extends JPanel
{	
	private ArrayList<DayComponent> day = new ArrayList<DayComponent>();
	public static LocalDate selectDate = LocalDate.now();
	public static int selectDay;
	public static int selectMonth;
	public static int selectYear;
	
	public JPanel[] printCalendar(LocalDate c)
	{  
		JPanel calPanel[] = new JPanel[7];
		for(int i = 0; i < calPanel.length; i++)
		{
			calPanel[i] = new JPanel();
			calPanel[i].setLayout(new BoxLayout(calPanel[i], BoxLayout.X_AXIS));
			calPanel[i].setAlignmentX(LEFT_ALIGNMENT);
		}		
		
		String[] week = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"}; // Used to compare
		String[] week1 = {"S", "M", "T", "W", "T", "F", "S"}; // Used to print
		int[] end = new int[6]; // Array to hold all the end of week days (Sat)
		String[][] days = new String[6][7]; // Maximum 6 weeks possible in 1 month			
		
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
		days[row][e] = today + "\t";; // Adding brackets to today's date in the array
		
		calPanel[0].add(Box.createRigidArea(new Dimension(25, 0)));
		for(int i = 0; i < week1.length; i++)
		{
			JLabel temp = new JLabel(week1[i]); // Adding the days of week from array week1[]
			temp.setFont(new Font("Arial", Font.PLAIN, 16));
			calPanel[0].add(temp);
			calPanel[0].add(Box.createRigidArea(new Dimension(40, 0)));
		}
		int index = 0;
		for(int i = 0; i < 6; i++)
		{
			calPanel[i+1].setAlignmentX(Component.LEFT_ALIGNMENT);
			for(int j = 0; j < 7; j++)
			{
				if(!days[i][j].equals("\t"))
				{
					days[i][j] = days[i][j].trim();
					day.add(new DayComponent(Integer.parseInt(days[i][j]), c.getMonthValue(), c.getYear(), 60, 60)); // 				
					calPanel[i+1].add(day.get(index));	
					DayComponent dayc = day.get(index);
					day.get(index).addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {						
							for(int i = 0; i < day.size(); i++)
							{
								if(day.get(i).getDay() == dayc.getDay())
								{
									day.get(i).changeColour();
									day.get(i).repaint();
									selectDay = day.get(i).getDay();
									selectMonth = day.get(i).getMonth();
									selectYear = day.get(i).getYear();
									selectDate = LocalDate.of(selectYear, selectMonth, selectDay);
								}
								else
								{
									day.get(i).defaultColour();
									day.get(i).repaint();
								}
							}
						}
					});
					index++;
				}
				else
				{
					DayComponent day = new DayComponent(0, c.getMonthValue(), c.getYear(), 60, 60);
					calPanel[i+1].add(day);	
				}
			}
		}		
		selectDay = LocalDate.now().getDayOfMonth();
		selectMonth = LocalDate.now().getMonthValue();
		selectYear = LocalDate.now().getYear();
		return calPanel;
	}

	public LocalDate getSelectDate(){
		return selectDate;
	}
}
