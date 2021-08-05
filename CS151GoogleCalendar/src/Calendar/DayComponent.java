package Calendar;

/**
 * @author Sung Jun Bok
 * @version 1.0 8/5/2021
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.LocalDate;

import javax.swing.JComponent;

/**
 * A class that represenets a day in the JPanel array for the calendar view
 */

public class DayComponent extends JComponent
{
	private Color DARK_RED = new Color(204,0,0);
	private Color VERY_LIGHT_RED = new Color(255,102,102);
	private Color colour = Color.BLACK;
	private int day;
	private int month;
	private int year;
	private String drawDay;

	/**
	 * Creates a day component
	 * @param day - the calendar day
	 * @param month - the calendar month
	 * @param year - the calendar year
	 * @param width - width of the component
	 * @param height - height of the component
	 */

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

	/**
	 * Changes color when the day selected is changed
	 */
	public void changeColour()
	{
		colour = VERY_LIGHT_RED;
	}

	/**
	 * The default color of each day component on the JPanel array
	 */
	public void defaultColour()
	{
		colour = Color.BLACK;
	}
}
