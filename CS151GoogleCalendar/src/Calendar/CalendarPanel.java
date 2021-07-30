package Calendar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CalendarPanel extends JPanel
{
	private LocalDate cal = LocalDate.now();
	private LocalDate temp = cal;
	private JPanel buttonPanel;
	private JPanel createPanel;
	private JPanel calPanel;
	private JPanel[] days;
	
	public CalendarPanel()
	{	
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
		JButton previousButton = new JButton("<");
		
		today.addActionListener(event ->
		changeMonths("t"));	
		
		JButton create = new JButton("CREATE");
		create.setBackground(Color.RED);

		// Add components
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		buttonPanel.add(today);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPanel.add(previousButton);
		buttonPanel.add(nextButton);
		createPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		createPanel.add(create);

		this.add(buttonPanel);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(createPanel);		
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.draw(cal);
	}
	
	public void draw(LocalDate cal) 
	{		
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
		for(int i = 0; i < days.length; i++)
		{
			this.add(days[i]);
		}
	}
	
	public void changeMonths(String a)
	{
		if(a.equals("n"))
		{
			temp = temp.plusMonths(1);	
		}
		else if(a.equals("p"))
		{
			temp = temp.minusMonths(1);	
		}
		else if(a.equals("t"))
		{
			temp = cal;
		}
		this.remove(calPanel);
		for(int i = 0; i < days.length; i++)
		{
			this.remove(days[i]);
		}
		this.draw(temp);
		this.revalidate();		
	}
	
}