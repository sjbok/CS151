package Calendar;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ViewPanel extends JPanel
{
<<<<<<< HEAD
	private JTextArea viewArea;
	private CalendarEvents calendarEvents;
=======
	private static JTextArea viewArea;
	private static CalendarEvents calendarEvents;
	public static String checkView = "d"; // default day view
>>>>>>> cde478d0be73316894e3dac7afcd57e7f0638aff
	
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
		
		month.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
				viewByDay(Calendar.selectDate);
=======
				int day = Calendar.selectDay;
		        int month = Calendar.selectMonth;
		        int year = Calendar.selectYear;
		        LocalDate ld = LocalDate.of(year, month, day);
		        checkView = "m";
				view(ld, checkView);
>>>>>>> cde478d0be73316894e3dac7afcd57e7f0638aff
			}
		});
		
		this.add(filePanel);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(buttonPanel);
		this.add(viewArea);		
	}

<<<<<<< HEAD

	public void viewByDay(LocalDate t){
		String str = calendarEvents.getEvents(t);
		viewArea.setText(str);

=======

	public static void view(LocalDate t, String check){ // Make this view method use all different views
		if(check.equals("d"))
		{
			viewArea.setText(calendarEvents.getEvents(t));
		}
		else if(check.equals("w"))
		{
			
		}
		else if(check.equals("m"))
		{
			
		}
>>>>>>> cde478d0be73316894e3dac7afcd57e7f0638aff
	}

	/**
	 * Prints the calendar view by month
	 * @param m month
	 * @param d day
	 * @param y year
	 */
//
//	public void viewByWeek(Month m, int d, int y){
//		//add functionality to viewByWeek
//	}

}
