

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class TimeConverter {
    private static String[] timeZones = TimeZone.getAvailableIDs();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Time Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // create hour, minute, and AM/PM selection
        JPanel timePanel = new JPanel(new FlowLayout());
        JLabel hourLabel = new JLabel("Hour:");
        timePanel.add(hourLabel);
        JComboBox<Integer> hourComboBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            hourComboBox.addItem(i);
        }
        timePanel.add(hourComboBox);

        JLabel minuteLabel = new JLabel("Minute:");
        timePanel.add(minuteLabel);
        JComboBox<Integer> minuteComboBox = new JComboBox<>();
        for (int i = 0; i < 60; i++) {
            minuteComboBox.addItem(i);
        }
        timePanel.add(minuteComboBox);

        JLabel amPmLabel = new JLabel("AM/PM:");
        timePanel.add(amPmLabel);
        JComboBox<String> amPmComboBox = new JComboBox<>();
        amPmComboBox.addItem("AM");
        amPmComboBox.addItem("PM");
        timePanel.add(amPmComboBox);
        panel.add(timePanel);

        // create timezone selection
        JLabel timeZoneLabel = new JLabel("Time Zone:");
        panel.add(timeZoneLabel);
        JComboBox<String> timeZoneComboBox = new JComboBox<>(timeZones);
        panel.add(timeZoneComboBox);

        // create target timezone selection
        JLabel targetTimeZoneLabel = new JLabel("Target Time Zone:");
        panel.add(targetTimeZoneLabel);
        JComboBox<String> targetTimeZoneComboBox = new JComboBox<>(timeZones);
        panel.add(targetTimeZoneComboBox);

        // create submit button
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton);
        submitButton.addActionListener(e -> {
            int hour = (int) hourComboBox.getSelectedItem();
            int minute = (int) minuteComboBox.getSelectedItem();
            String amPm = (String) amPmComboBox.getSelectedItem();
            String timeZoneId = (String) timeZoneComboBox.getSelectedItem();
            String targetTimeZoneId = (String) targetTimeZoneComboBox.getSelectedItem();

            // create LocalDateTime object with the selected hour and minute
            LocalDateTime localDateTime = LocalDateTime.of(2023, 3, 4, hour, minute);

            // adjust the hour for AM/PM
            if (amPm.equals("PM")) {
                localDateTime = localDateTime.plusHours(12);
            }

            // convert to the selected timezone
            ZoneId zoneId = ZoneId.of(timeZoneId);
            ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

            // convert to the target timezone
            ZoneId targetZoneId = ZoneId.of(targetTimeZoneId);
            ZonedDateTime targetZonedDateTime = zonedDateTime.withZoneSameInstant(targetZoneId);

            // format the result
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
            String time = targetZonedDateTime.format(formatter);
            String timeZone = targetZoneId.getId();
            String result = String.format("[%s %s]", time, timeZone);
            JOptionPane.showMessageDialog(frame, result);
        });

        frame.pack();
        frame.setVisible(true);
    }
}
