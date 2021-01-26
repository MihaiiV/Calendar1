package Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimePanel extends JPanel{

    private static final String[] monthName = {"January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"};

    private static final String[] dayName = { "Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday" };

    private JLabel time,date,pm;

    private Calendar calendar;

    public TimePanel(){

        setLayout(new TimeLayout());

        setLabels();

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                requestFocus();
            }
        });

        Timer timer = new Timer(1000, e -> setTime());

        timer.start();
    }

    public void setTime() {

        calendar = new GregorianCalendar();

        LocalTime localTime = LocalTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("a");

        pm.setText(localTime.format(dateTimeFormatter));

        date.setText(dayName[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + monthName[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR));

        if (calendar.get(Calendar.HOUR) == 0 && pm.getText().equals("PM")){

            if (calendar.get(Calendar.MINUTE) < 10) {

                if (calendar.get(Calendar.SECOND) < 10) {

                    time.setText("" + 12 + " : 0" + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                } else {

                    time.setText("" + 12 + " : 0" + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }
            } else {

                if (calendar.get(Calendar.SECOND) < 10) {

                    time.setText("" + 12 + " : " + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                } else {

                    time.setText("" + 12 + " : " + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }

            }

        }

        else if(calendar.get(Calendar.HOUR) < 10) {

            if(calendar.get(Calendar.MINUTE) < 10){

                if(calendar.get(Calendar.SECOND) < 10){

                    time.setText("0" + calendar.get(Calendar.HOUR) + " : 0" + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                }

                else{

                    time.setText("0" + calendar.get(Calendar.HOUR) + " : 0" + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }
            }

            else {

                if(calendar.get(Calendar.SECOND) < 10){

                    time.setText("0" + calendar.get(Calendar.HOUR) + " : " + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                }

                else{

                    time.setText("0" + calendar.get(Calendar.HOUR) + " : " + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }

            }

        }

        else {

            if(calendar.get(Calendar.MINUTE) < 10){

                if(calendar.get(Calendar.SECOND) < 10){

                    time.setText("" + calendar.get(Calendar.HOUR) + " : 0" + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                }

                else{

                    time.setText("" + calendar.get(Calendar.HOUR) + " : 0" + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }
            }

            else {

                if(calendar.get(Calendar.SECOND) < 10){

                    time.setText("" + calendar.get(Calendar.HOUR) + " : " + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                }

                else{

                    time.setText("" + calendar.get(Calendar.HOUR) + " : " + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }

            }
        }
    }

    public void setLabels(){

        calendar = Calendar.getInstance();

        LocalTime localTime = LocalTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("a");

        pm = new JLabel();

        pm.setText(localTime.format(dateTimeFormatter));

        pm.setFont(new Font("Arial",Font.PLAIN,20));

        time = new JLabel();

        if (calendar.get(Calendar.HOUR) == 0 && pm.getText().equals("PM")) {

            if (calendar.get(Calendar.MINUTE) < 10) {

                if (calendar.get(Calendar.SECOND) < 10) {

                    time.setText("" + 12 + " : 0" + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                } else {

                    time.setText("" + 12 + " : 0" + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }
            } else {

                if (calendar.get(Calendar.SECOND) < 10) {

                    time.setText("" + 12 + " : " + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                } else {

                    time.setText("" + 12 + " : " + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }

            }
        }

        else if(calendar.get(Calendar.HOUR) < 10){

            if(calendar.get(Calendar.MINUTE) < 10){

                if(calendar.get(Calendar.SECOND) < 10){

                    time.setText("0" + calendar.get(Calendar.HOUR) + " : 0" + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                }

                else{

                    time.setText("0" + calendar.get(Calendar.HOUR) + " : 0" + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }
            }

            else {

                if(calendar.get(Calendar.SECOND) < 10){

                    time.setText("0" + calendar.get(Calendar.HOUR) + " : " + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                }

                else{

                    time.setText("0" + calendar.get(Calendar.HOUR) + " : " + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }

            }

        }

        else {

            if(calendar.get(Calendar.MINUTE) < 10){

                if(calendar.get(Calendar.SECOND) < 10){

                    time.setText("" + calendar.get(Calendar.HOUR) + " : 0" + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                }

                else{

                    time.setText("" + calendar.get(Calendar.HOUR) + " : 0" + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }
            }

            else {

                if(calendar.get(Calendar.SECOND) < 10){

                    time.setText("" + calendar.get(Calendar.HOUR) + " : " + calendar.get(Calendar.MINUTE) + " : " + "0" + calendar.get(Calendar.SECOND));
                }

                else{

                    time.setText("" + calendar.get(Calendar.HOUR) + " : " + calendar.get(Calendar.MINUTE) + " : " + calendar.get(Calendar.SECOND));
                }

            }
        }

        time.setFont(new Font("Arial",Font.PLAIN,40));

        add(time);

        add(pm);

        date = new JLabel();

        date.setText(dayName[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + monthName[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR));

        date.setFont(new Font("Arial",Font.PLAIN,18));

        add(date);
    }

}

