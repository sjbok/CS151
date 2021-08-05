package Calendar;
/**
 * @author Sung Jun Bok
 * @version 1.0 8/5/2021
 */
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A class that creates the main GUI frame for the system
 */
public class CalendarGUI 
{
	JFrame frame;
	JPanel panel;
	CalendarPanel calPanel;
	ViewPanel viewPanel;
	private CalendarEvents calendarEvents;

	/**
	 * Constructs a a CalendarGUi and creates the backend calendar events and the main frame for the system
	 * Adds the view panel and calendar panel
	 */
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
