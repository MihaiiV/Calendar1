package Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class WiewEventPanel extends JPanel{

    JFrame f;

    private TreeMap<String, ArrayList<String>> map;

    private JScrollPane pane;

    private JTextArea text;

    private JComboBox<String> date,hour;

    private JButton wiew, cancel,delete,deleteAll;

    private Font font;

    private JPanel northPanel,centrePanel,southPanel;

    private JLabel currentDate, currentHour;

    private File file;

    private Path path;

    private Actions actions = new Actions();

    public WiewEventPanel(TreeMap<String, ArrayList<String>> map,JFrame f){

        this.f = f;

        this.map = map;

        file = new File("C:\\Calendar\\CalendarNote.txt");

        path = Paths.get("C:\\Calendar\\CalendarNote.txt");

        setLayout(new BorderLayout());

        addComponents();
    }

    public void addComponents(){

        text = new JTextArea(3,20);

        font = new Font("Arial",Font.BOLD,16);

        text.setFont(font);

        text.setText("Chose a date");

        text.setForeground(Color.GRAY);

        text.setEditable(false);

        pane = new JScrollPane(text);

        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        currentHour = new JLabel("Hour");

        currentDate = new JLabel("Date");

        currentHour.setFont(font);

        currentDate.setFont(font);

        date = new JComboBox<>();

        hour = new JComboBox<>();

        date.setFont(new Font("Arial",Font.PLAIN,14));

        hour.setFont(new Font("Arial",Font.PLAIN,14));

        addCheckBoxDate();

        wiew = new JButton("View");

        cancel = new JButton("Cancel");

        delete = new JButton("Delete");

        deleteAll = new JButton("Delete all");

        addActions();

        northPanel = new JPanel();

        centrePanel = new JPanel();

        southPanel = new JPanel();

        northPanel.add(currentDate);

        northPanel.add(date);

        northPanel.add(currentHour);

        northPanel.add(hour);

        centrePanel.add(pane);

        southPanel.add(wiew);

        southPanel.add(delete);

        southPanel.add(deleteAll);

        southPanel.add(cancel);

        add(northPanel,BorderLayout.NORTH);

        add(centrePanel,BorderLayout.CENTER);

        add(southPanel,BorderLayout.SOUTH);
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

                    keys = key.substring(3);

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

    public void addCheckBoxDate(){

        date.removeAllItems();

        Set<String> key = map.keySet();

        StringBuilder string = new StringBuilder();

        Iterator<String> i = key.iterator();

        date.addItem("All");

        while (i.hasNext()){

            string.append(i.next());

            date.addItem(string.toString());

            string.setLength(0);
        }

        addCheckBoxHour(String.valueOf(date.getSelectedItem()));
    }

    public void addCheckBoxHour(String key){

        hour.removeAllItems();

        hour.addItem("All");

        if(key.equals("All")){

            return;
        }

        ArrayList<String> array = map.get(key);

        HashSet<String> duplicate = new HashSet<>();

        StringBuilder string = new StringBuilder();

        Iterator<String> i = null;

        if(array != null){

            i = array.iterator();
        }

        while (i != null && i.hasNext()){

            string.append(i.next());

            if(duplicate.add(string.substring(0, 11))) {

                hour.addItem(string.substring(0, 11));
            }

            string.setLength(0);
        }
    }

    public void addActions(){

        date.addActionListener(actions);

        wiew.addActionListener(actions);

        delete.addActionListener(actions);

        deleteAll.addActionListener(actions);

        cancel.addActionListener(actions);

        date.addActionListener(actions);

        hour.addActionListener(actions);

    }

    public void viewEvents(){

        StringBuilder key = new StringBuilder();

        ArrayList<String> notes = null;

        Set<String> keys = map.keySet();

        Iterator<String> iterator = keys.iterator();

        text.setText("");

        text.setForeground(Color.BLACK);

        if(date.getSelectedItem().equals("All")){

            while (iterator.hasNext()){

                key.append(iterator.next());

                notes = map.get(key.toString());

                text.append(key.toString() + "\n");

                for(String i : notes){

                    text.append(i.substring(0,11) + " - " + i.substring(11) + "\n");
                }

                key.setLength(0);
            }
        }

        else {

            if(hour.getSelectedItem().equals("All")){

                key.append(date.getSelectedItem());

                text.append(key.toString() + "\n");

                notes = map.get(key.toString());

                for(String i : notes){

                    text.append(i.substring(0,11) + " - " + i.substring(11) + "\n");
                }
            }

            else {

                key.append(date.getSelectedItem());

                text.append(key.toString() + "\n");

                notes = map.get(key.toString());

                key.setLength(0);

                key.append(hour.getSelectedItem());

                for(String i : notes){

                    if(i.indexOf(key.toString()) == 0){

                        text.append(i.substring(0,11) + " - " + i.substring(11) + "\n");
                    }
                }
            }
        }
    }

    public void deleteEvents(){

        StringBuilder key = new StringBuilder();

        ArrayList<String> notes = null;

        key.append(date.getSelectedItem());

        setTextArrea();

        if(key.toString().equals("All")){

            int i = JOptionPane.showConfirmDialog(null,"Delete All Events?", "Delete Events",JOptionPane.YES_NO_OPTION);

            if(i == JOptionPane.YES_OPTION) {

                map.clear();

                if (file.delete()) {

                    addCheckBoxDate();

                    try {
                        Files.createFile(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        else {

            if(hour.getSelectedItem().equals("All")) {

                map.remove(key.toString());

                printFile();
            }

            else {

                notes = map.get(key.toString());

                key.setLength(0);

                int i = JOptionPane.showConfirmDialog(null,"Delete " + hour.getSelectedItem() + "Events?", "Delete Events",JOptionPane.YES_NO_OPTION);

                if(i == JOptionPane.YES_OPTION) {

                    key.append(hour.getSelectedItem());

                    for (int j = 0; j < notes.size(); j++) {

                        if (notes.get(j).contains(key.toString())) {

                            notes.remove(j--);
                        }
                    }
                }

                if(notes.isEmpty()){

                    key.setLength(0);

                    key.append(date.getSelectedItem());

                    map.remove(key.toString());
                }
            }

            printFile();

            addCheckBoxDate();
        }

        addCheckBoxDate();
    }

    public void deleteAllEvents(){

        int i = JOptionPane.showConfirmDialog(null,"Delete All Events?", "Delete Events",JOptionPane.YES_NO_OPTION);

        if(i == JOptionPane.YES_OPTION) {

            map.clear();

            if (file.delete()) {

                addCheckBoxDate();

                try {
                    Files.createFile(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        addCheckBoxDate();
    }

    public void setTextArrea(){

        text.setText("Chose a date");

        text.setForeground(Color.GRAY);
    }

    private class Actions extends AbstractAction{

        public void actionPerformed(ActionEvent e) {

            if(e.getSource().equals(date)){

                addCheckBoxHour(String.valueOf(date.getSelectedItem()));
            }

            else if(e.getSource().equals(wiew)){

                viewEvents();
            }

            else if(e.getSource().equals(delete)){

                deleteEvents();
            }

            else if(e.getSource().equals(deleteAll)){

                deleteAllEvents();
            }

            else if(e.getSource().equals(cancel)){

                setTextArrea();

                f.setVisible(false);

            }

            else if(e.getSource().equals(date)){

                setTextArrea();

            }

            else if(e.getSource().equals(hour)){

                setTextArrea();
            }


        }
    }
}
