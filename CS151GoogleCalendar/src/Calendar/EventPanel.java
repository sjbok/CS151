package Calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel implements ItemListener{
    private final JTextArea eventTextArea;
    private JComboBox start;
    private JComboBox end;
    private JComboBox dayBox;
	private JComboBox monthBox;
	private JTextField years;
	private JPanel date;
	private JPanel times;
    private String[] time = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", 
    		"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00" , "22:00", "23:00"};   

    public EventPanel(){
        this.setPreferredSize(new Dimension(200,300));
        eventTextArea = new JTextArea();
        eventTextArea.setEditable(false);
                      
        LocalTime ld = LocalTime.now();
    	int count = 0;
    	int hour = ld.getHour();
    	for(int i = 0; i < time.length; i++)
        {
        	String[] hours = time[i].split(":");
        	if(hour == Integer.parseInt(hours[0]))
        	{
        		count = i;
        	}
        }
          	
        start = new JComboBox(time);
        start.setSelectedIndex(count);
        start.addItemListener(this);     
        findEnd(count, time);
        int day = Calendar.selectDay;
        int month = Calendar.selectMonth;
        int year = Calendar.selectYear;
        makeDate(day, month, year);        
        
        this.add(new JLabel("Title: "));
        this.add(new JTextField(15));
        
        date = new JPanel();
        //date.setLayout(new BoxLayout(date, BoxLayout.Y_AXIS));       
        date.add(years);     
        date.add(new JLabel("/"));
        date.add(monthBox);       
        date.add(new JLabel("/"));
        date.add(dayBox);
        monthBox.addItemListener(this);
        
        times = new JPanel();
        times.add(new JLabel("Start:"));
        times.add(start);
        times.add(new JLabel("End:"));
        times.add(end);
        
        this.add(new JLabel("Date (YYYY/MM/DD):"));
        this.add(date);        
        this.add(times);
        

    }
   
    public void itemStateChanged(ItemEvent e)
    {
        if(e.getSource() == start) 
        {       	 
        	times.remove(end);
        	String hours = e.getItem().toString();
        	String[] hour = hours.split(":");
        	int count = Integer.parseInt(hour[0]);
        	findEnd(count, time);        	
        	times.add(end);
        	this.revalidate();
        }
        if(e.getSource() == monthBox)
        {
        	date.remove(dayBox);
        	String month = e.getItem().toString();
        	LocalDate ld = LocalDate.of(Integer.parseInt(years.getText()), Integer.parseInt(month), 1); 
        	String[] days = new String[ld.getMonth().length(ld.isLeapYear())];
        	for(int i = 0; i < days.length; i++)
        	{
        		days[i] = i + 1 + "";
        	}
        	dayBox = new JComboBox(days);
        	date.add(dayBox);
        	date.revalidate();
        	date.repaint();
        	this.revalidate();
        	this.repaint();
        }
    }
    
    public void findEnd(int count, String[] time)
    {
    	int num = 0;
        String[] endtime = new String[23 - count];
        for(int i = count + 1; i < time.length; i++)
        {
        	endtime[num] = time[i];
        	num++;	
        }   
        if(endtime.length < 3)
        {
        	endtime = new String[23 - count + 1];
        	num = 0;
        	for(int i = count + 1; i < time.length; i++)
            {
            	endtime[num] = time[i];
            	num++;	
            }
        	endtime[endtime.length - 1] = "00:00";
        }
        end = new JComboBox(endtime);
    }
    
    public void makeDate(int day, int month, int year)
    {
    	LocalDate ld = LocalDate.of(year, month, day);  	
    	String[] days = new String[ld.getMonth().maxLength()];
    	String[] months = new String[12];
    	int daycount = 0;
    	int monthcount = 0;
    	for(int i = 0; i < days.length; i++)
    	{
    		days[i] = i + 1 + "";
    		if(days[i].equals(day+""))
    		{
    			daycount = i;
    		}
    	}
    	for(int i = 0; i < months.length; i++)
    	{
    		months[i] = i + 1 + "";
    		if(months[i].equals(month+""))
    		{
    			monthcount = i;
    		}
    	}
    	dayBox = new JComboBox(days);
    	dayBox.setSelectedIndex(daycount);
    	monthBox = new JComboBox(months);
    	monthBox.setSelectedIndex(monthcount);
    	years = new JTextField(year+"");
    }

}

