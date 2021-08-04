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
	private JTextArea viewArea;
	private CalendarEvents calendarEvents;
	
	public ViewPanel(CalendarEvents e)
	{
		calendarEvents = e;

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setPreferredSize(new Dimension(500, 600));	
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		viewArea = new JTextArea();
		viewArea.setEditable(false);
		
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
				viewByDay(Calendar.selectDate);
			}
		});
		
		this.add(filePanel);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(buttonPanel);
		this.add(viewArea);		
	}


	public void viewByDay(LocalDate t){
		String str = calendarEvents.getEvents(t);
		viewArea.setText(str);

	}

	/**
	 * Prints the calendar view by month
	 * @param m month
	 * @param d day
	 * @param y year
	 */

	public void viewByWeek(Month m, int d, int y){
		//add functionality to viewByWeek
	}

}
