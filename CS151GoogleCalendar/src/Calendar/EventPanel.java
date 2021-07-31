package Calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventPanel extends JPanel{
    private final JTextArea eventTextArea;

    public EventPanel(){
        //this.setLayout(new GridLayout());
        this.setPreferredSize(new Dimension(200,300));

        eventTextArea = new JTextArea();
        eventTextArea.setEditable(false);
        this.add(new JLabel("Title: "));
        this.add(new JTextField(15));
        this.add(new JLabel("Date: "));
        this.add(new JTextField(15));
        this.add(new JLabel("Start:"));
        this.add(new JTextField(15));
        this.add(new JLabel(" End:"));
        this.add(new JTextField(15));
        JOptionPane.showMessageDialog(null,this,"Create Event", JOptionPane.QUESTION_MESSAGE);

    }

}

