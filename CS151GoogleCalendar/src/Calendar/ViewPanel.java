package Calendar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import javax.swing.*;

public class ViewPanel extends JPanel
{
	public static JTextArea viewArea;
	private static CalendarEvents calendarEvents;
	public static String checkView = "d"; // default day view
	public static JFrame frame;
	public ViewPanel(CalendarEvents e)
	{
		calendarEvents = e;

		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setPreferredSize(new Dimension(500, 600));
		this.setAlignmentX(Component.LEFT_ALIGNMENT);

		viewArea = new JTextArea();
		viewArea.setEditable(false);
		LocalDate ld = LocalDate.now();
		view(ld, checkView);

		JButton day = new JButton("Day");
		JButton week = new JButton("Week");
		JButton month = new JButton("Month");
		JButton agenda = new JButton("Agenda");
		JButton fromfile = new JButton("From File");

		JPanel buttonPanel = new JPanel();
		JPanel filePanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
		filePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		buttonPanel.add(day);
		buttonPanel.add(week);
		buttonPanel.add(month);
		buttonPanel.add(agenda);
		filePanel.add(fromfile);

		day.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int day = Calendar.selectDay;
				int month = Calendar.selectMonth;
				int year = Calendar.selectYear;
				LocalDate ld = LocalDate.of(year, month, day);
				checkView = "d";
				view(ld, checkView);
			}
		});

		week.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int day = Calendar.selectDay;
				int month = Calendar.selectMonth;
				int year = Calendar.selectYear;
				LocalDate ld = LocalDate.of(year, month, day);
				checkView = "w";
				view(ld, checkView);
			}
		});

		month.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int day = Calendar.selectDay;
				int month = Calendar.selectMonth;
				int year = Calendar.selectYear;
				LocalDate ld = LocalDate.of(year, month, day);
				checkView = "m";
				view(ld, checkView);
			}
		});

		agenda.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame("Agenda");

				JPanel main = new JPanel(new BorderLayout());

				JPanel panel = new JPanel();
				JButton submit = new JButton("Submit");

				JLabel format = new JLabel("             MM/DD/YYYY Format             ");
				JLabel spacer = new JLabel("                                            ");
				JPanel formatPanel = new JPanel();
				formatPanel.add(format);
				formatPanel.add(spacer);

				JTextField startDate = new JTextField(15);
				JLabel start = new JLabel("Start Date:");

				JTextField endDate = new JTextField(15);
				JLabel end = new JLabel(" End Date:");

				main.add(formatPanel, BorderLayout.NORTH);

				panel.add(start);
				panel.add(startDate);

				panel.add(end);
				panel.add(endDate);

				JPanel panel2 = new JPanel();
				panel2.add(submit);

				main.add(panel, BorderLayout.CENTER);
				main.add(panel2, BorderLayout.SOUTH);

				submit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						LocalDate sd = LocalDate.parse(startDate.getText(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
						LocalDate ed = LocalDate.parse(endDate.getText(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));

						if(ed.isAfter(sd)) {
							JOptionPane.showMessageDialog(null, "Success.");
							viewAgenda(sd,ed);
							startDate.setText("");
							endDate.setText("");
							frame.setVisible(false);
						}
						else
							JOptionPane.showMessageDialog(null, "Invalid range, please try again.");
					}
				});

				frame.setResizable(false);
				frame.getContentPane().add(main);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});

		fromfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame("From File...");
				JPanel panel = new JPanel();
				String[] files = {"notafile.txt", "CS151GoogleCalendar/input.txt","notafile2.txt"};
				JComboBox select = new JComboBox(files);

				panel.add(select);
				select.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						File file = new File(select.getSelectedItem().toString());
						try{
							Scanner reader = new Scanner(file);
							while(reader.hasNextLine()){
								reader.useDelimiter(";");
								Event event = new Event();
								TimeInterval ti = new TimeInterval();

								reader.nextLine();
								frame.setVisible(false);
							}
						}
						catch(FileNotFoundException exception){
							JOptionPane.showMessageDialog(null, "File not found.");
						}
					}
				});

				frame.add(panel);
				frame.setSize(300,300);
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
				frame.setVisible(true);

			}
		});

		this.add(filePanel);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(buttonPanel);
		this.add(viewArea);
	}


	public static void view(LocalDate t, String check) { // Make this view method use all different views
		viewArea.setText(calendarEvents.getEvents(t, check));
	}

	public static void viewAgenda(LocalDate sd, LocalDate ed){
		checkView = "a";
		calendarEvents.text = "";
		viewArea.setText(calendarEvents.viewAgenda(sd,ed));

	}
}