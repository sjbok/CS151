package Calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.LocalDate;

import javax.swing.JComponent;

public class DayComponent extends JComponent
{
	Color DARK_RED = new Color(204,0,0);
	Color VERY_LIGHT_RED = new Color(255,102,102);
	private Color colour = Color.BLACK;
	private int day;
	private int month;
	private int year;
	private String drawDay;
	

	public DayComponent(int day, int month, int year, int width, int height) 
	{
		this.day = day;
		this.drawDay = Integer.toString(day);
		this.month = month;
		this.year = year;
		this.setSize(width, height);
		this.setPreferredSize(new Dimension(width, height));
	}

	public int getDay()
	{
		return day;
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		LocalDate cal = LocalDate.now();
		if(day > 0) 
		{
			g2d.setColor(colour);
			if(day == cal.getDayOfMonth() && month == cal.getMonthValue() && year == cal.getYear())
			{
				g2d.setColor(DARK_RED);
			}
			g2d.drawRect(10, 0, 40, 40);
			g2d.drawString(drawDay, 25, 25);
		}
	}
	
	public void changeColour()
	{
		colour = VERY_LIGHT_RED;
	}
	
	public void defaultColour()
	{
		colour = Color.BLACK;
	}
}
