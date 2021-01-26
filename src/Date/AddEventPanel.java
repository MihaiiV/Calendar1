package Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class AddEventPanel extends JPanel {

    private final JFrame f;

    private static final String[] monthName = {"January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"};

    private final TreeMap<String, ArrayList<String>> map;

    private final File file;

    private final JComboBox<Integer> day;
    private final JComboBox<Integer> year;

    private final JComboBox<String> month;
    private final JComboBox<String> minutes;
    private final JComboBox<String> hours;

    private final JCheckBox am;
    private final JCheckBox pm;

    private final JButton add;
    private final JButton cancel;

    private final JTextField text;

    private final GregorianCalendar calendar;

    public AddEventPanel(JFrame f) {

        this.f = f;

        setFocusable(true);

        calendar = new GregorianCalendar();

        map = new TreeMap<>();

        file = new File("C:\\Calendar\\CalendarNote.txt");

        JLabel day1 = new JLabel("Day");

        day1.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel month1 = new JLabel("Month");

        month1.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel year1 = new JLabel("Year");

        year1.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel hour = new JLabel("Set Time");

        hour.setFont(new Font("Arial", Font.PLAIN, 20));

        text = new JTextField(22);

        text.setFont(new Font("Arial", Font.PLAIN, 20));

        text.setText("Add event");

        text.setForeground(Color.GRAY);

        text.addFocusListener(new Focus());

        add = new JButton("Add");

        cancel = new JButton("Cancel");

        day = new JComboBox<>();

        year = new JComboBox<>();

        minutes = new JComboBox<>();

        for (int i = calendar.get(Calendar.YEAR); i < 2100; i++) {

            year.addItem(i);
        }

        year.setSelectedItem(calendar.get(Calendar.YEAR));

        month = new JComboBox<>();

        for (int i = 0; i < 12; i++) {

            month.addItem(monthName[i]);
        }

        for (int i = 0; i < 60; i++) {

            if (i < 10) {

                minutes.addItem("0" + i);
            } else {

                minutes.addItem("" + i);
            }

        }

        month.setSelectedItem(monthName[calendar.get(Calendar.MONTH)]);

        for (int i = 1; i <= daysInMonth(month.getSelectedIndex(), (Integer) year.getSelectedItem()); i++) {

            day.addItem(i);
        }

        day.setSelectedItem(calendar.get(Calendar.DAY_OF_MONTH));

        hours = new JComboBox<>();

        for (int i = 0; i <= 12; i++) {

            if (i < 10) {

                hours.addItem("0" + i);
            } else {

                hours.addItem("" + i);
            }
        }

        Actions actions = new Actions();

        am = new JCheckBox("Am");

        pm = new JCheckBox("Pm");

        am.setFont(new Font("Arial", Font.PLAIN, 20));

        pm.setFont(new Font("Arial", Font.PLAIN, 20));

        am.setSelected(true);

        inputFile();

        deleteOldEvents();

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                requestFocus();
            }
        });

        hours.addActionListener(actions);
        month.addActionListener(actions);
        year.addActionListener(actions);
        am.addActionListener(actions);
        pm.addActionListener(actions);
        add.addActionListener(actions);
        cancel.addActionListener(actions);
        add(day1);
        add(day);
        add(month1);
        add(month);
        add(year1);
        add(year);
        add(hour);
        add(hours);
        add(minutes);
        add(am);
        add(pm);
        add(text);
        add(add);
        add(cancel);
    }

    public int daysInMonth(int month, int year) {

        if (month % 2 == 0 && month <= 6 || month % 2 != 0 && month > 6) {

            return 31;
        } else if (month == 1 && Math.abs(year - 2000) % 4 == 0) {

            return 29;
        } else if (month == 1 && Math.abs(year - 2000) % 4 != 0) {

            return 28;
        }

        return 30;
    }

    private class Actions extends AbstractAction {

        public void actionPerformed(ActionEvent e) {

            if (e.getSource().equals(month) || e.getSource().equals(year)) {

                day.removeAllItems();

                for (int i = 1; i <= daysInMonth(month.getSelectedIndex(), (Integer) year.getSelectedItem()); i++) {

                    day.addItem(i);
                }
            } else if (e.getSource().equals(am)) {

                if (am.isSelected() && pm.isSelected()) {

                    pm.setSelected(false);

                } else if (!am.isSelected() && !pm.isSelected()) {

                    pm.setSelected(true);
                }

                if("00".equals(hours.getSelectedItem()) && pm.isSelected()){

                    hours.setSelectedIndex(1);
                }

                else if("12".equals(hours.getSelectedItem()) && am.isSelected()){

                    hours.setSelectedIndex(11);
                }

            } else if (e.getSource().equals(pm)) {

                if (pm.isSelected() && am.isSelected()) {

                    am.setSelected(false);
                } else if (!pm.isSelected() && !am.isSelected()) {

                    am.setSelected(true);
                }

                if(hours.getSelectedItem().equals("00") && pm.isSelected()){

                    hours.setSelectedIndex(1);
                }

                else if(hours.getSelectedItem().equals("12") && am.isSelected()){

                    hours.setSelectedIndex(11);
                }

            } else if (e.getSource().equals(hours)) {

                if (hours.getSelectedItem().equals("12")) {

                    pm.setSelected(true);

                    am.setSelected(false);
                } else if (hours.getSelectedItem().equals("00")) {

                    am.setSelected(true);

                    pm.setSelected(false);
                }
            } else if (e.getSource().equals(add)) {

                int validMonth = 0;

                for (int i = 0; i < monthName.length; i++) {

                    if (monthName[i].equals(month.getSelectedItem())) {

                        validMonth = i;
                    }
                }

                if (!text.getText().equals("Add event")) {

                   /* if (validMonth == calendar.get(Calendar.MONTH)) {

                        if ((int) day.getSelectedItem() >= calendar.get(Calendar.DAY_OF_MONTH)) {

                            addEvent();

                            printFile();
                        }

                    }

                    else if(validMonth > calendar.get(Calendar.MONTH) || (int) year.getSelectedItem() > calendar.get(Calendar.YEAR)){

                        addEvent();

                        printFile();
                    }*/

                    addEvent();

                    printFile();
                }

                //printNotes();

            } else if (e.getSource().equals(cancel)) {

                text.setText("Add event");

                text.setForeground(Color.GRAY);

                f.setVisible(false);

            }
        }
    }

    private class Focus extends FocusAdapter {

        public void focusGained(FocusEvent e) {
            super.focusGained(e);

            if (text.getText().equals("Add event")) {

                text.setText("");

                text.setForeground(Color.BLACK);
            }
        }

        public void focusLost(FocusEvent e) {
            super.focusLost(e);

            if (text.getText().equals("")) {

                text.setText("Add event");

                text.setForeground(Color.GRAY);
            }
        }
    }

    public void addEvent() {

        StringBuilder key = new StringBuilder();

        ArrayList<String> notes;

        String newKey;

        key.append(year.getSelectedItem()).append(" ");

        if (month.getSelectedIndex() < 9) {

            key.append("0").append(month.getSelectedIndex() + 1).append(" ");
        } else {

            key.append(month.getSelectedIndex()).append(1).append(" ");

        }

        if ((int) day.getSelectedItem() < 10) {
            key.append("0").append(day.getSelectedItem());
        } else {
            key.append(day.getSelectedItem());
        }

        newKey = key.toString();

        if (map.containsKey(newKey)) {

            notes = map.get(newKey);

        } else {

            notes = new ArrayList<>();

            map.put(newKey, notes);

        }
        newKey = am.isSelected() ? "Am" : "Pm";
        notes.add(hours.getSelectedItem() + " : " + minutes.getSelectedItem() + " " + newKey + " " + text.getText());

        hours.setSelectedIndex(0);

        minutes.setSelectedIndex(0);

        am.setSelected(true);

        pm.setSelected(false);

        sortEvents(notes);
    }

    public void sortEvents(ArrayList<String> array) {

        String[] sorted = new String[array.size()];

        for (int i = 0; i < array.size(); i++) {

            if (array.get(i).indexOf("Am") == 8) {

                sorted[i] = 'A' + array.get(i);
            } else {

                sorted[i] = 'P' + array.get(i);

            }
        }

        Arrays.sort(sorted);

        array.clear();

        for (int i = 0; i < sorted.length; i++) {

            array.add(sorted[i].substring(1));
        }
    }

    public void printFile() {

        StringBuilder key = new StringBuilder();

        ArrayList<String> notes = null;

        Set<String> keys = map.keySet();

        Iterator<String> iterator = keys.iterator();

        try {
            PrintWriter print = new PrintWriter(file);

            while (iterator.hasNext()) {

                key.append(iterator.next());

                notes = map.get(key.toString());

                print.print("key");

                print.println(key.toString());

                for (String i : notes) {

                    print.println(i);
                }

                key.setLength(0);
            }

            print.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void inputFile(){

        StringBuilder key = new StringBuilder();

        ArrayList<String> notes = null;

        String keys = null;

        try {
            Scanner input = new Scanner(file);

            while (input.hasNext()){

                key.append(input.nextLine());

                if(key.indexOf("key") == 0 && key.length() == 13){

                    keys = key.toString().substring(3);

                    key.setLength(0);

                    if(input.hasNext()){

                        key.append(input.nextLine());
                    }

                    else {

                        return;
                    }

                    notes = new ArrayList<>();
                }

                notes.add(key.toString());

                key.setLength(0);

                if(!map.containsKey(keys)){

                    map.put(keys,notes);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void deleteOldEvents(){

        StringBuilder key = new StringBuilder();

        ArrayList<String> notes = null;

        Set<String> keys = map.keySet();

        Iterator<String> iterator = keys.iterator();

        String currentDate = "" + calendar.get(Calendar.YEAR);

        if(calendar.get(Calendar.MONTH ) < 9){

            currentDate += " " + "0" + (calendar.get(Calendar.MONTH) + 1);
        }

        else{

            currentDate += " " + (calendar.get(Calendar.MONTH) + 1);
        }

        if(calendar.get(Calendar.DAY_OF_MONTH) < 10){

            currentDate += " " + "0" + calendar.get(Calendar.DAY_OF_MONTH);
        }

        else{

            currentDate += " " + calendar.get(Calendar.DAY_OF_MONTH);
        }

        while (iterator.hasNext()){

            key.append(iterator.next());

            if(key.toString().compareTo(currentDate) < 0){

                map.remove(key.toString());

                keys = map.keySet();

                iterator = keys.iterator();

            }

            key.setLength(0);
        }

        printFile();
    }

    public TreeMap<String, ArrayList<String>> returnMap(){

        return map;
    }

    public void printNotes() {

        StringBuilder key = new StringBuilder();

        ArrayList<String> notes;

        Set<String> keys = map.keySet();

        for (String s : keys) {

            key.append(s);

            notes = map.get(key.toString());

            for (String i : notes) {

                System.out.println(i);
            }

            key.setLength(0);
        }
    }
}

