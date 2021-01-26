package Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.GregorianCalendar;

public class Frame extends JFrame {

    private JFrame frameEvent;

    private Toolkit kit;

    private Dimension screenSize, screenlocation;

    private MyDialog dialog;

    private boolean closeWindow;

    private TimePanel show;

    private Panel date;

    private NotePanel note;

    private AddEventPanel eventPanel;

    private WiewEventPanel wiewEventPanel;

    private DateLayout myLayout;

    private GregorianCalendar calendar;

    private JMenuBar bar;

    private JMenu file;

    private JMenuItem setDate, setEvent, wiewEvent;

    private SetDateDialog dateDialog;

    private Actions actions;

    private File directory, files;

    private Path path;

    public Frame(){

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }

        kit = Toolkit.getDefaultToolkit();

        screenSize = kit.getScreenSize();

        screenlocation = kit.getScreenSize();

        if(screenSize.width < 1150){

            screenSize.setSize(1150,screenSize.height);
        }

        if(screenSize.height < 768){

            screenSize.setSize(screenSize.width,768);
        }

        if(screenSize.width > 1366){

            screenSize.width = 1366;
        }

        if(screenSize.height > 768){

            screenSize.height = 768;
        }

        setSize(screenSize.width/ 4,screenSize.height - screenSize.height / 4);

        myLayout = new DateLayout(getSize());

        setLayout(myLayout);

        setLocation(screenlocation.width / 2 - getWidth() / 2,screenlocation.height / 2 - getHeight() / 2);

        //setResizable(false);

        setTitle("Calendar");

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        dialog = new MyDialog(this);

        closeWindow = false;

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                dialog.showDialog();

                if(closeWindow){

                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }

                else{

                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        calendar = new GregorianCalendar();

        dateDialog = new SetDateDialog(this);

        createPath();

        setMenus();

        addPanel();

        setFrameEvent();

        setVisible(true);
    }

    public void close(){

        closeWindow = true;
    }

    public void setDate(GregorianCalendar calendar){

        this.calendar = calendar;

        date.noTarget();

        date.repaint();
    }

    public GregorianCalendar getDate(){

        return calendar;
    }

    public int returnCurrentDate(){

        return date.returnTargetDays();
    }

    public void createPath(){

        directory = new File("C:\\Calendar");

        path = Paths.get("C:\\Calendar\\CalendarNote.txt");

        if(!directory.exists()){

            if (!directory.mkdir()) {
                return;
            }
        }

        files = new File("C:\\Calendar\\CalendarNote.txt");

        if(!files.exists()){

            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.setAttribute(Path.of("C:\\Calendar"), "dos:hidden", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPanel(){

        show = new TimePanel();

        show.setBorder(BorderFactory.createTitledBorder("Show"));

        date = new Panel(this,myLayout.getSize());

        date.setBorder(BorderFactory.createTitledBorder("Date"));

        note = new NotePanel();

        note.setBorder(BorderFactory.createTitledBorder("Note"));

        show.setFocusable(true);

        date.setFocusable(true);

        note.setFocusable(true);

        add(show);

        add(date);

        add(note);
    }

    public void setMenus(){

        bar = new JMenuBar();

        actions = new Actions();

        file = new JMenu("File");

        setDate = new JMenuItem("Set date");

        setDate.addActionListener(actions);

        setEvent = new JMenuItem("Add Event");

        setEvent.addActionListener(actions);

        wiewEvent = new JMenuItem("View Event");

        wiewEvent.addActionListener(actions);

        file.add(setDate);

        file.add(setEvent);

        file.add(wiewEvent);

        bar.add(file);

        setJMenuBar(bar);
    }

    public void setFrameEvent(){

        frameEvent = new JFrame();

        frameEvent.setSize(400,200);

        frameEvent.setLocation(screenlocation.width / 2 - frameEvent.getWidth() / 2, screenlocation.height / 2 - 200);

        frameEvent.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frameEvent.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                wiewEventPanel.setTextArrea();

                frameEvent.setVisible(false);
            }
        });

        frameEvent.setResizable(false);

        eventPanel = new AddEventPanel(frameEvent);

        wiewEventPanel = new WiewEventPanel(eventPanel.returnMap(),frameEvent);
    }

    private class Actions extends AbstractAction{

        public void actionPerformed(ActionEvent e) {

            if(e.getSource().equals(setDate)){

                dateDialog.showDialog();
            }

            else if(e.getSource().equals(setEvent)){

                frameEvent.remove(wiewEventPanel);

                frameEvent.add(eventPanel);

                frameEvent.repaint();

                frameEvent.setVisible(true);
            }

            else if(e.getSource().equals(wiewEvent)){

                frameEvent.remove(eventPanel);

                frameEvent.add(wiewEventPanel);

                wiewEventPanel.inputFile();

                wiewEventPanel.addCheckBoxDate();

                frameEvent.repaint();

                frameEvent.setVisible(true);
            }
        }
    }
}
