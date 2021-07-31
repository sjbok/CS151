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

	public CalendarGUI() 
	{
		frame = new JFrame();
		frame.setTitle("Google Calendar");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		panel = new JPanel();

		calPanel = new CalendarPanel();
		viewPanel = new ViewPanel();

		panel.add(calPanel);
		panel.add(viewPanel);
		frame.add(panel);

		frame.pack();
		frame.setVisible(true);
	}
	
}
