package Calendar;
/**
 * @author Sung Jun Bok
 * @author Liz Huelfenhaus
 * @author Vinayan Kathiresan
 * @version 1.0 8/5/2021
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A class that is used to view events in a calendar
 */
public class ViewPanel extends JPanel
{
	public static JTextArea viewArea;
	private static CalendarEvents calendarEvents;
	public static String checkView = "d"; // default day view
	public static JFrame frame;

	/**
	 * Constructs a view panel
	 * @param e - the CalendarEvent reference
	 */
	public ViewPanel(CalendarEvents e)
	{
		calendarEvents = e;

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setPreferredSize(new Dimension(500, 600));
		this.setAlignmentX(Component.LEFT_ALIGNMENT);

		viewArea = new JTextArea();
		viewArea.setEditable(false);
		LocalDate ld = LocalDate.now();
		view(ld, checkView);

		JButton day = new JButton("Day");
		JButton week = new JButton("Week");
		JButton month = new JButton("Month");
		JButton agenda = new JButton("Agenda");
		JButton fromfile = new JButton("From File");

		JPanel buttonPanel = new JPanel();
		JPanel filePanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
		filePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		buttonPanel.add(day);
		buttonPanel.add(week);
		buttonPanel.add(month);
		buttonPanel.add(agenda);
		filePanel.add(fromfile);

		/**
		 * Adds an action listener to the day view button
		 * Calls the function to show the proper view
		 */

		day.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int day = Calendar.selectDay;
				int month = Calendar.selectMonth;
				int year = Calendar.selectYear;
				LocalDate ld = LocalDate.of(year, month, day);
				checkView = "d";
				view(ld, checkView);
			}
		});

		/**
		 * Adds an action listener to the week view button
		 * Calls the function to show the proper view
		 */

		week.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int day = Calendar.selectDay;
				int month = Calendar.selectMonth;
				int year = Calendar.selectYear;
				LocalDate ld = LocalDate.of(year, month, day);
				checkView = "w";
				view(ld, checkView);
			}
		});

		/**
		 * Adds an action listener to the month view button
		 * Calls the function to show the proper view
		 */

		month.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int day = Calendar.selectDay;
				int month = Calendar.selectMonth;
				int year = Calendar.selectYear;
				LocalDate ld = LocalDate.of(year, month, day);
				checkView = "m";
				view(ld, checkView);
			}
		});

		/**
		 * Adds an action listener to the agenda button
		 * Creates a new panel to allow the user to select a range
		 * Calls the view agenda function to print the events for the range
		 */

		agenda.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				frame = new JFrame("Agenda");

				JPanel main = new JPanel(new BorderLayout());

				JPanel panel = new JPanel();
				JButton submit = new JButton("Submit");

				JLabel format = new JLabel("             MM/DD/YYYY Format             ");
				JLabel spacer = new JLabel("                                            ");
				JPanel formatPanel = new JPanel();
				formatPanel.add(format);
				formatPanel.add(spacer);

				JTextField startDate = new JTextField(15);
				JLabel start = new JLabel("Start Date:");

				JTextField endDate = new JTextField(15);
				JLabel end = new JLabel(" End Date:");

				main.add(formatPanel, BorderLayout.NORTH);

				panel.add(start);
				panel.add(startDate);

				panel.add(end);
				panel.add(endDate);

				JPanel panel2 = new JPanel();
				panel2.add(submit);

				main.add(panel, BorderLayout.CENTER);
				main.add(panel2, BorderLayout.SOUTH);

				submit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						LocalDate sd = LocalDate.parse(startDate.getText(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
						LocalDate ed = LocalDate.parse(endDate.getText(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));

						if(ed.isAfter(sd)) {
							JOptionPane.showMessageDialog(null, "Success.");
							viewAgenda(sd,ed,"");
							startDate.setText("");
							endDate.setText("");
							frame.setVisible(false);
						}
						else
							JOptionPane.showMessageDialog(null, "Invalid range, please try again.");
					}
				});

				frame.setResizable(false);
				frame.getContentPane().add(main);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});

		/**
		 * Creates an action listener to the from file button
		 * Creates a panel with options for the user to select a file
		 * Creates recurring events from the input file
		 */

		fromfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt", "text");
				fc.setFileFilter(filter);
		    	fc.setCurrentDirectory(new File("."));
		    	JPanel p = new JPanel();
		    	int val = fc.showOpenDialog(p);
		    	File file = fc.getSelectedFile();
		    	if(val == JFileChooser.APPROVE_OPTION)
		    	{
		    		if (file.getName().equals("input.txt")) {
			    		Scanner reader=null;
			    		try
			    		{
							reader = new Scanner(file);
						}
						catch(FileNotFoundException exception) {
							JOptionPane.showMessageDialog(null, "File not found.");
						}
						
						while(reader.hasNextLine()) {
							recurringEvents(reader.nextLine());
						}
						JOptionPane.showMessageDialog(null, "Events added.");
			        } else {
			        	JOptionPane.showMessageDialog(null, "Invalid file.");
			        }
		    	}
			}
		});

		this.add(filePanel);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(buttonPanel);
		this.add(viewArea);
	}

	/**
	 * The view function for day, month, year
	 * Enters the calendar events function to show the proper view
	 * @param t - the date selected
	 * @param check - A string that represents the preferred view
	 */
	public static void view(LocalDate t, String check) { // Make this view method use all different views
		viewArea.setText(calendarEvents.getEvents(t, check));
	}

	/**
	 * The view agenda function for a time frame
	 * Enters the calender events class to get all the events
	 * @param sd - start date
	 * @param ed - end date
	 * @param text - the text string that will show all events for the range
	 */
	public static void viewAgenda(LocalDate sd, LocalDate ed, String text){
		checkView = "a";
		calendarEvents.text = "";
		viewArea.setText(calendarEvents.viewAgenda(sd,ed));

	}

	/**
	 * Used to parse the events from the input file for recurring events
	 * @param values - a string that represents data for a recurring event
	 */
	public void recurringEvents(String values) {
		String[] val = values.split(";");
		String name = val[0];
		int year = Integer.parseInt(val[1]);
		int startMonth = Integer.parseInt(val[2]);
		int endMonth = Integer.parseInt(val[3]);
		String daysOfWeek = val[4];
		int startTime=Integer.parseInt(val[5]);
		int endTime=Integer.parseInt(val[6]);
		
		LocalDate cal = LocalDate.now();
		TimeInterval ti;
		LocalDate ld;
		
		//month(i), day(j)
		for(int i = startMonth; i <= endMonth; i++) {
			cal = LocalDate.of(year, i, 1);
			int daysInMonth = cal.getMonth().length(cal.isLeapYear());
			for(int j = 1; j <= daysInMonth; j++) {
				if(daysOfWeek.contains(weekDay(year, i, j))) {
					ti = new TimeInterval(startTime, endTime);
					ld=LocalDate.of(year, i, j);
					Event e = new Event(name, ti,ld );
					//Add event to Hashmap
					CalendarEvents.add(e);
				}
			}
		}
		LocalDate date = LocalDate.of(Calendar.selectYear, Calendar.selectMonth, Calendar.selectDay);
		view(date, checkView);
	}

	/**
	 * Used to created the weekdays for the week view
	 * @param year
	 * @param month
	 * @param day
	 * @return - returns a string of all days of the week
	 */
	
	private String weekDay(int year, int month, int day) {
		LocalDate cal= LocalDate.now();
		cal = LocalDate.of(year, month, day);
		int dayOfWeek = cal.getDayOfWeek().getValue();
		switch (dayOfWeek) {
		   case 1:
               return "M";
           case 2:
               return "T";
           case 3:
               return "W";
           case 4:
               return "H";
           case 5:
               return "F";
           case 6:
               return "A";
           case 7:
               return "S";
           default:
               return null;
		}
	}
}