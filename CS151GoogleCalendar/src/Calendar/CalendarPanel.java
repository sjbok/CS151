package Calendar;
/**
 * @author Sung Jun Bok
 * @author Liz Huelfenhaus
 * @author Vinayan Kathiresan
 * @version 1.0 8/5/2021
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import javax.swing.*;

/**
 * A class that is used for the printed calendar panel portion of the system
 */

public class CalendarPanel extends JPanel {
	private LocalDate cal = LocalDate.now();
	private LocalDate temp = cal;
	private JPanel buttonPanel;
	private JPanel createPanel;
	private JPanel calPanel;
	private JPanel[] days;
	public static JFrame frame;

	/**
	 * Constructs a calendar panel with the backend calendar events
	 * @param e - backend calendar events
	 */
	public CalendarPanel(CalendarEvents e) {
		int width = 360;
		int height = 450;

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setPreferredSize(new Dimension(width, height));
		this.setAlignmentX(Component.LEFT_ALIGNMENT);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setPreferredSize(new Dimension(width, 30));
		buttonPanel.setMinimumSize(new Dimension(width, 30));
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		createPanel = new JPanel();
		createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.X_AXIS));
		createPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JButton today = new JButton("Today");
		JButton nextButton = new JButton(">");
		JButton prevButton = new JButton("<");

		today.addActionListener(event ->
				changeMonths("t"));
		nextButton.addActionListener(event ->
				changeDays("n"));
		prevButton.addActionListener(event ->
				changeDays("p"));


		JButton create = new JButton("CREATE");
		create.setForeground(Color.white);
		create.setBackground(Color.RED);
		create.setBorderPainted(false);
		create.setOpaque(true);
		create.addActionListener(event ->
				createEvent());

		// Add components
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		buttonPanel.add(today);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPanel.add(prevButton);
		buttonPanel.add(nextButton);
		createPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		createPanel.add(create);

		this.add(buttonPanel);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(createPanel);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.draw(cal);
	}

	/**
	 * Draws the calendar starting with todays date
	 * @param cal - the selected date
	 */
	public void draw(LocalDate cal) {
		Calendar c = new Calendar();
		calPanel = new JPanel();
		calPanel.setLayout(new BoxLayout(calPanel, BoxLayout.X_AXIS));
		calPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JButton nextMonth = new JButton(">");
		JButton prevMonth = new JButton("<");

		nextMonth.addActionListener(event ->
				changeMonths("n"));
		prevMonth.addActionListener(event ->
				changeMonths("p"));

		JLabel label = new JLabel(cal.getMonth() + " " + cal.getYear()); // Printing this month and this year
		label.setFont(new Font("Arial", Font.BOLD, 20));

		calPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		calPanel.add(label);
		calPanel.add(Box.createHorizontalGlue());
		calPanel.add(prevMonth);
		calPanel.add(nextMonth);

		this.add(calPanel);
		days = c.printCalendar(cal);
		for (int i = 0; i < days.length; i++) {
			this.add(days[i]);
		}
	}

	/**
	 * Changes the month view of the calendar with < > buttons
	 * @param a - A string that represents the < > button pressed
	 */
	public void changeMonths(String a) {
		if (a.equals("n")) {
			temp = temp.plusMonths(1);
		} else if (a.equals("p")) {
			temp = temp.minusMonths(1);
		} else if (a.equals("t")) {
			temp = cal;
			if(!ViewPanel.checkView.equals("a"))
			{
				String check = ViewPanel.checkView;
				ViewPanel.view(temp, check);
			}			
		}
		this.remove(calPanel);
		for (int i = 0; i < days.length; i++) {
			this.remove(days[i]);
		}
		this.draw(temp);
		this.revalidate();
	}

	/**
	 * Changes the day view of the calendar with < > buttons
	 * @param a - A string that represents the < > button pressed
	 */

	public void changeDays(String a)
	{
		if (a.equals("n"))
		{
			if(ViewPanel.checkView.equals("d"))
			{
				LocalDate test = LocalDate.of(Calendar.selectYear, Calendar.selectMonth, Calendar.selectDay);
				if(Calendar.selectDay == test.getMonth().length(test.isLeapYear()))
				{
					changeMonths("n");
					Calendar.selectDay = 1;
					Calendar.selectMonth = Calendar.day.get(Calendar.selectDay - 1).getMonth();
					Calendar.selectYear = Calendar.day.get(Calendar.selectDay - 1).getYear();
				}
				else if(test.getMonth().length(test.isLeapYear()) == Calendar.selectDay + 1)
				{
					Calendar.selectDay = Calendar.day.get(Calendar.selectDay).getDay();
					Calendar.selectMonth = Calendar.day.get(Calendar.selectDay - 1).getMonth();
					Calendar.selectYear = Calendar.day.get(Calendar.selectDay - 1).getYear();			
				}
				else
				{
					Calendar.selectDay = Calendar.day.get(Calendar.selectDay).getDay();
					Calendar.selectMonth = Calendar.day.get(Calendar.selectDay).getMonth();
					Calendar.selectYear = Calendar.day.get(Calendar.selectDay).getYear();
				}
				LocalDate ld = LocalDate.of(Calendar.selectYear, Calendar.selectMonth, Calendar.selectDay);			
				for(int i = 0; i < Calendar.day.size(); i++)
				{
					if(Calendar.day.get(i).getDay() == ld.getDayOfMonth())
					{			
						Calendar.day.get(i).changeColour();
						Calendar.day.get(i).repaint();	
						String check = ViewPanel.checkView;
						ViewPanel.view(ld, check);
					}
					else
					{
						Calendar.day.get(i).defaultColour();
						Calendar.day.get(i).repaint();
					}
				}
			}
			else if(ViewPanel.checkView.equals("w"))
			{
				LocalDate test = LocalDate.of(Calendar.selectYear, Calendar.selectMonth, Calendar.selectDay);
				LocalDate test2 = test.plusWeeks(1);
				Calendar.selectYear = test2.getYear();
				Calendar.selectMonth = test2.getMonthValue();
				Calendar.selectDay = test2.getDayOfMonth();
				if(test2.getMonthValue() != test.getMonthValue())
				{
					changeMonths("n");
					Calendar.selectYear = test2.getYear();
					Calendar.selectMonth = test2.getMonthValue();
					Calendar.selectDay = test2.getDayOfMonth();
				}
				LocalDate ld = test2;			
				for(int i = 0; i < Calendar.day.size(); i++)
				{
					if(Calendar.day.get(i).getDay() == ld.getDayOfMonth())
					{			
						Calendar.day.get(i).changeColour();
						Calendar.day.get(i).repaint();	
						String check = ViewPanel.checkView;
						ViewPanel.view(ld, check);
					}
					else
					{
						Calendar.day.get(i).defaultColour();
						Calendar.day.get(i).repaint();
					}
				}
			}
			else if(ViewPanel.checkView.equals("m"))
			{
				LocalDate test = LocalDate.of(Calendar.selectYear, Calendar.selectMonth, 1);
				LocalDate test2 = test.plusMonths(1);
				changeMonths("n");
				Calendar.selectYear = test2.getYear();
				Calendar.selectMonth = test2.getMonthValue();
				Calendar.selectDay = test2.getDayOfMonth();
				LocalDate ld = test2;			
				for(int i = 0; i < Calendar.day.size(); i++)
				{
					if(Calendar.day.get(i).getDay() == ld.getDayOfMonth())
					{			
						Calendar.day.get(i).changeColour();
						Calendar.day.get(i).repaint();	
						String check = ViewPanel.checkView;
						ViewPanel.view(ld, check);
					}
					else
					{
						Calendar.day.get(i).defaultColour();
						Calendar.day.get(i).repaint();
					}
				}
			}
			this.revalidate();
		}
		else if (a.equals("p"))
		{
			if(ViewPanel.checkView.equals("d"))
			{
				LocalDate test = LocalDate.of(Calendar.selectYear, Calendar.selectMonth, Calendar.selectDay);
				if(Calendar.selectDay == 1)
				{
					changeMonths("p");
					test = test.minusMonths(1);
					Calendar.selectDay = test.getMonth().length(test.isLeapYear());
					Calendar.selectMonth = Calendar.day.get(Calendar.selectDay - 1).getMonth();
					Calendar.selectYear = Calendar.day.get(Calendar.selectDay - 1).getYear();
				}
				else if(Calendar.selectDay == 2)
				{
					Calendar.selectDay = Calendar.day.get(Calendar.selectDay - 2).getDay();
					Calendar.selectMonth = Calendar.day.get(Calendar.selectDay).getMonth();
					Calendar.selectYear = Calendar.day.get(Calendar.selectDay).getYear();			
				}
				else
				{
					Calendar.selectDay = Calendar.day.get(Calendar.selectDay - 2).getDay();
					Calendar.selectMonth = Calendar.day.get(Calendar.selectDay - 2).getMonth();
					Calendar.selectYear = Calendar.day.get(Calendar.selectDay - 2).getYear();
				}
				LocalDate ld = LocalDate.of(Calendar.selectYear, Calendar.selectMonth, Calendar.selectDay);
				for(int i = 0; i < Calendar.day.size(); i++)
				{
					if(Calendar.day.get(i).getDay() == ld.getDayOfMonth())
					{			
						Calendar.day.get(i).changeColour();
						Calendar.day.get(i).repaint();	
						String check = ViewPanel.checkView;
						ViewPanel.view(ld, check);
					}
					else
					{
						Calendar.day.get(i).defaultColour();
						Calendar.day.get(i).repaint();
					}
				}
			}
			else if(ViewPanel.checkView.equals("w"))
			{
				LocalDate test = LocalDate.of(Calendar.selectYear, Calendar.selectMonth, Calendar.selectDay);
				LocalDate test2 = test.minusWeeks(1);
				Calendar.selectYear = test2.getYear();
				Calendar.selectMonth = test2.getMonthValue();
				Calendar.selectDay = test2.getDayOfMonth();
				if(test2.getMonthValue() != test.getMonthValue())
				{
					changeMonths("p");
					Calendar.selectYear = test2.getYear();
					Calendar.selectMonth = test2.getMonthValue();
					Calendar.selectDay = test2.getDayOfMonth();
				}
				LocalDate ld = test2;			
				for(int i = 0; i < Calendar.day.size(); i++)
				{
					if(Calendar.day.get(i).getDay() == ld.getDayOfMonth())
					{			
						Calendar.day.get(i).changeColour();
						Calendar.day.get(i).repaint();	
						String check = ViewPanel.checkView;
						ViewPanel.view(ld, check);
					}
					else
					{
						Calendar.day.get(i).defaultColour();
						Calendar.day.get(i).repaint();
					}
				}
			}
			else if(ViewPanel.checkView.equals("m"))
			{
				LocalDate test = LocalDate.of(Calendar.selectYear, Calendar.selectMonth, 1);
				LocalDate test2 = test.minusMonths(1);				
				changeMonths("p");
				Calendar.selectYear = test2.getYear();
				Calendar.selectMonth = test2.getMonthValue();
				Calendar.selectDay = test2.getDayOfMonth();
				LocalDate ld = test2;			
				for(int i = 0; i < Calendar.day.size(); i++)
				{
					if(Calendar.day.get(i).getDay() == ld.getDayOfMonth())
					{			
						Calendar.day.get(i).changeColour();
						Calendar.day.get(i).repaint();	
						String check = ViewPanel.checkView;
						ViewPanel.view(ld, check);
					}
					else
					{
						Calendar.day.get(i).defaultColour();
						Calendar.day.get(i).repaint();
					}
				}
			}			
			this.revalidate();
		}
	}

	/**
	 * Creates an event when the create event button is selected
	 */

	public void createEvent() {
		EventPanel ep = new EventPanel();
		frame = new JFrame("Create Event");
		frame.setResizable(false);
		frame.getContentPane().add(ep);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}