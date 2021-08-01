package Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class CalendarGUI 
{
	JFrame frame;
	JPanel panel;
	CalendarPanel calPanel;
	ViewPanel viewPanel;
	private CalendarEvents calendarEvents;

	public CalendarGUI() 
	{
		calendarEvents = new CalendarEvents();
		frame = new JFrame();
		frame.setTitle("Google Calendar");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		panel = new JPanel();

		calPanel = new CalendarPanel(calendarEvents);
		viewPanel = new ViewPanel(calendarEvents);

		panel.add(calPanel);
		panel.add(viewPanel);
		frame.add(panel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}
