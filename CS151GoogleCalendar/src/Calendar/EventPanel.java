package Calendar;

/**
 * @author Sung Jun Bok
 * @author Liz Huelfenhaus
 * @version 1.0 8/5/2021
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A class for the panel to create a new event
 */

public class EventPanel extends JPanel implements ItemListener{
    private Event create;
    private JComboBox start;
    private JComboBox end;
    private JComboBox dayBox;
    private JComboBox monthBox;
    private JTextField years;
    private JTextField title;
    private JPanel date;
    private JPanel times;
    private JButton save;
    private String[] time = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
            "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00" , "22:00", "23:00"};

    /**
     * Constructs a new event panel when the create button is selected in CalendarPanel
     * Gives the user the ability to provide input for the new event
     */

    public EventPanel(){
        this.setPreferredSize(new Dimension(300,300));

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
        title = new JTextField(15);
        this.add(title);

        date = new JPanel();
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

        /**
         * Adds an actionlistener to the save button
         * Checks that no conflicts for the event are present
         */
        save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeInterval ti = new TimeInterval(start.getSelectedItem().toString(), end.getSelectedItem().toString());
                LocalDate ld = LocalDate.of(Integer.parseInt(years.getText()), Integer.parseInt(monthBox.getSelectedItem().toString()),
                        Integer.parseInt(dayBox.getSelectedItem().toString()));
                if(!title.getText().isBlank())
                {
                    create = new Event(title.getText(), ti, ld);
                    Boolean pass = CalendarEvents.add(create);
                    if(pass)
                    {
                        JOptionPane.showMessageDialog(null, "Event saved.");
                        CalendarPanel.frame.setVisible(false);
                        String check = ViewPanel.checkView;
                        ViewPanel.view(ld, check);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Error: Time conflict.");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please enter a title for the event.");
                }
            }
        });

        this.add(new JLabel("Date (YYYY/MM/DD):"));
        this.add(date);
        this.add(times);
        this.add(Box.createRigidArea(new Dimension(500, 0)));
        this.add(save);
    }

    /**
     * Called when the combo boxes are changed
     * @param e - combobox item event
     */
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

    /**
     * Creates a date for the combo boxes
     * @param day
     * @param month
     * @param year
     */
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

    /**
     * @return - returns the created event
     */
    public Event getEvent(){
        return this.create;
    }

}