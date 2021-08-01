package Calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class EventPanel extends JPanel{
    private final JTextArea eventTextArea;
    private JTextField title, date, start, end;
    private Event createEvent;

    public EventPanel(){
        //this.setLayout(new GridLayout());
        this.setPreferredSize(new Dimension(200,200));

        eventTextArea = new JTextArea();
        eventTextArea.setEditable(false);
        this.add(new JLabel("Title: "));
        this.add(title = new JTextField(15));
        this.add(new JLabel("Date: "));
        this.add(date = new JTextField(15));
        this.add(new JLabel("Start:"));
        this.add(start = new JTextField(15));
        this.add(new JLabel(" End:"));
        this.add(end = new JTextField(15));
        JOptionPane.showMessageDialog(null,this,"Create Event", JOptionPane.QUESTION_MESSAGE);


        TimeInterval ti = new TimeInterval(Integer.parseInt(start.getText()), Integer.parseInt(end.getText()));
        createEvent = new Event(title.getText(), ti);
        date.getText();
    }

    public Event getEvent(){
        return createEvent;
    }
    public LocalDate getDate(){
        date.getText();
        //need to create local date from joptionpane

        int m,d,y;
        

        LocalDate ld = LocalDate.of()

    }

}

