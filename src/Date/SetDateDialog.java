package Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SetDateDialog extends JPanel {

    private JComboBox<Integer> day, year;

    private JComboBox<String> month;

    private GregorianCalendar calendar;

    private JDialog dialog;

    private Frame f;

    private JButton ok, cancel;

    private JPanel comboPanel, buttonPanel;

    private Actions actions;

    private boolean setMonth;

    private Mouse mouse;

    private static final String[] monthName = {"January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"};

    public SetDateDialog(Frame f) {

        this.f = f;

        setLayout(new BorderLayout());

        calendar = new GregorianCalendar();

        day = new JComboBox<>();

        year = new JComboBox<>();

        for (int i = 1900; i < 2100; i++) {

            year.addItem(i);
        }
        year.setSelectedItem(f.getDate().get(Calendar.YEAR));

        month = new JComboBox<>();

        for (int i = 0; i < 12; i++) {

            month.addItem(monthName[i]);
        }

        month.setSelectedItem(monthName[f.getDate().get(Calendar.MONTH)]);

        for (int i = 1; i <= daysInMonth(f.getDate().get(Calendar.MONTH), f.getDate().get(Calendar.YEAR)); i++) {
            day.addItem(i);
        }

        day.setSelectedItem(f.getDate().get(Calendar.DAY_OF_MONTH));

        setMonth = true;

        comboPanel = new JPanel();

        comboPanel.add(day);
        comboPanel.add(month);
        comboPanel.add(year);

        ok = new JButton("Ok");

        cancel = new JButton("Cancel");

        actions = new Actions();

        month.addActionListener(actions);

        ok.addActionListener(actions);

        cancel.addActionListener(actions);

        buttonPanel = new JPanel();

        buttonPanel.add(ok);

        buttonPanel.add(cancel);

        buttonPanel.setFocusable(true);

        comboPanel.setFocusable(true);

        mouse = new Mouse();

        buttonPanel.addMouseListener(mouse);

        comboPanel.addMouseListener(mouse);

        add(comboPanel, BorderLayout.CENTER);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public int daysInMonth(int month, int year) {
        if (month % 2 == 0 && month <= 6 || month % 2 != 0 && month > 6) {
            return 31;
        }
        if (month == 1 && Math.abs(year - 2000) % 4 == 0) {
            return 29;
        }
        if (month == 1 && Math.abs(year - 2000) % 4 != 0) {
            return 28;
        }

        return 30;
    }

    public void showDialog() {
        if (dialog == null || dialog.getOwner() != f) {
            dialog = new JDialog(this.f, true);
            dialog.add(this);
            dialog.setResizable(false);
            dialog.setSize(300, 130);
            dialog.setTitle("Set Date");
        }
        year.setSelectedItem(f.getDate().get(Calendar.YEAR));

        month.setSelectedItem(monthName[f.getDate().get(Calendar.MONTH)]);

        day.setSelectedItem(f.getDate().get(Calendar.DAY_OF_MONTH));

        dialog.setLocation(f.getX() + f.getWidth() / 2 - dialog.getWidth() / 2, f.getY() + f.getHeight() / 2 - dialog.getHeight() / 2);

        dialog.setVisible(true);

    }

    private class Actions extends AbstractAction {

        public void actionPerformed(ActionEvent e) {

            int monthNumber = 0;

            if (e.getSource().equals(cancel)) {

                setMonth = true;

                dialog.setVisible(false);
            } else if (e.getSource().equals(ok)) {

                setMonth = true;

                for (int i = 0; i < 12; i++) {

                    if (monthName[i].equals(month.getSelectedItem())) {

                        monthNumber = i;
                    }
                }

                calendar.set((int) year.getSelectedItem(), monthNumber, (int) day.getSelectedItem());

                f.setDate(calendar);

                dialog.setVisible(false);
            } else if (e.getSource().equals(month)) {

                if (setMonth) {

                    month.setSelectedItem(calendar.get(Calendar.MONTH));

                    setMonth = false;
                }

                for (int i = 0; i < 12; i++) {

                    if (monthName[i].equals(month.getSelectedItem())) {

                        monthNumber = i;
                    }
                }

                day.removeAllItems();

                for (int i = 1; i <= daysInMonth(monthNumber, calendar.get(Calendar.YEAR)); i++) {

                    day.addItem(i);
                }

                day.setSelectedItem(1);
            }
        }
    }

    private class Mouse extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            requestFocus();
        }
    }
}

