package Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Panel extends JPanel {

    private static final String[] dayName = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
    private static final String[] monthName = {"January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"};

    private final Rectangle2D[][] matrix;
    private final byte[][] matrixDays;
    private final GregorianCalendar calendar;
    private final Frame frame;
    private final Font font;
    private final JLabel currentMonth;

    private double x;
    private double y;
    private FontRenderContext context;
    private String data;
    private Point2D drawRectangle, targetRectangle;

    public Panel(Frame frame, Dimension size){

        this.frame = frame;

        font = new Font("Arial",Font.PLAIN,20);

        data = "";

        calendar = new GregorianCalendar();

        drawRectangle = new Point2D.Double(-1,-1);

        targetRectangle = new Point2D.Double(-1,-1);

        currentMonth = new JLabel();

        currentMonth.setText(monthName[frame.getDate().get(Calendar.MONTH)] + ", " + frame.getDate().get(Calendar.YEAR));

        currentMonth.setFont(new Font("Arial",Font.PLAIN,18));

        add(currentMonth);

        matrix = new Rectangle2D.Double[7][7];

        matrixDays = new byte[7][7];

        x = 0.0;
        y = currentMonth.getHeight() + 5.0;
        double z = size.height - size.height / 9.0;
        z /= 7.0;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                matrix[i][j] = new Rectangle2D.Double(x, z + y, size.width / 7.0 - 1, z - 1);
                x += size.width / 7.0;
            }
            x = 0;
            y += z;
        }

        Mouse mouse = new Mouse();
        addMouseMotionListener(mouse);
        addMouseListener(mouse);
    }


    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g;

        super.paintComponent(g);

        drawData(g2);

    }

    public void drawData(Graphics2D g2){

        context = g2.getFontRenderContext();

        currentMonth.setText(monthName[frame.getDate().get(Calendar.MONTH)] + ", " + frame.getDate().get(Calendar.YEAR));

        g2.setFont(font);

        byte day = 1, dayOfWeek = (byte) getDayOfWeek(frame.getDate());

        for (byte i = 0; i < 7; i++){
            for (byte j = 0; j < 7; j++) {

                day = dataName(i, j, day, dayOfWeek);

                setDataPosition(i, j);

                if (day - 1 == calendar.get(Calendar.DAY_OF_MONTH) && frame.getDate().get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && frame.getDate().get(Calendar.MONTH) == calendar.get(Calendar.MONTH)){

                    data = "" + calendar.get(Calendar.DAY_OF_MONTH);

                    g2.setColor(Color.CYAN);

                    g2.fill(matrix[i][j]);

                    g2.setColor(Color.BLACK);

                    g2.draw(matrix[i][j]);

                    g2.drawString(data, (int) x, (int) y);

                    if(checkClick(i,j,day)){

                        Rectangle2D currentDayTarget = new Rectangle2D.Double();

                        currentDayTarget.setRect(matrix[i][j].getX() + 5, matrix[i][j].getY() + 5,matrix[i][j].getWidth() - 10, matrix[i][j].getHeight() - 12);

                        g2.setColor(Color.BLACK);

                        g2.draw(currentDayTarget);
                    }

                    continue;
                }

                if (i != 0 && day == 1 || day > calculateDays(frame.getDate().get(Calendar.MONTH), frame.getDate().get(Calendar.YEAR)) + 1) {

                    g2.setColor(Color.GRAY);
                } else {

                    g2.setColor(Color.BLACK);
                }

                if (checkTarget(i, j, day)) {

                    g2.setColor(Color.BLUE);

                    g2.draw(matrix[i][j]);
                }

                if (checkClick(i, j, day)) {

                    g2.setColor(Color.BLACK);

                    g2.draw(matrix[i][j]);
                }

                if (i != 0 && day == 1 || day > calculateDays(frame.getDate().get(Calendar.MONTH), frame.getDate().get(Calendar.YEAR)) + 1) {

                    g2.setColor(Color.GRAY);
                } else {

                    g2.setColor(Color.BLACK);
                }

                g2.drawString(data, (int) x, (int) y);
            }
        }
    }

    public void noTarget(){

        targetRectangle.setLocation(-1,-1);

        drawRectangle.setLocation(-1,-1);
    }


    public int calculateDays(int month, int year){

        if(month % 2 == 0 && month <= 6 || month % 2 != 0 && month > 6){

            return 31;
        }

        else if(month == 1 && Math.abs(year - 2000) % 4 == 0){

            return 29;
        }

        else if(month == 1 && Math.abs(year - 2000) % 4 != 0){

            return 28;
        }

        return 30;
    }

    public void setDataPosition(byte i, byte j){

        Rectangle2D bounds = font.getStringBounds(data, context);

        x = matrix[i][j].getX() + matrix[i][j].getWidth() / 2 - bounds.getWidth() / 2;

        y = matrix[i][j].getY() + matrix[i][j].getHeight()  - bounds.getHeight() / 2.4;
    }

    public int getDayOfWeek(GregorianCalendar calendar){

        GregorianCalendar c = new GregorianCalendar();

        c.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1);

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;

        if(dayOfWeek == 0){

            dayOfWeek = 7;
        }

        return dayOfWeek;
    }

    public byte dataName(byte i, byte j, byte day, byte dayOfWeek) {

        matrixDays[i][j] = 0;

        if (i == 0) {

            data = dayName[j];
        } else if (dayOfWeek > i * (j + 1) && day == 1){

            if(frame.getDate().get(Calendar.MONTH) == Calendar.JANUARY){

                dayOfWeek = (byte) (31 - dayOfWeek + 1 + i * (j + 1));
            }

            else {

                dayOfWeek = (byte) (calculateDays(frame.getDate().get(Calendar.MONTH) - 1, calendar.get(Calendar.YEAR)) - dayOfWeek + 1 + i * (j + 1));

            }

            data = "" + dayOfWeek;
        }

        else if(dayOfWeek == i * (j + 1) || day > 1 && day <= calculateDays(frame.getDate().get(Calendar.MONTH),frame.getDate().get(Calendar.YEAR))){

            matrixDays[i][j] = day;

            data = "" + day++;
        }

        else {

            data = "" + (day++ - calculateDays(frame.getDate().get(Calendar.MONTH),frame.getDate().get(Calendar.YEAR)));
        }

        return day;
    }

    public boolean checkTarget(byte i, byte j, byte day) {
        if (matrix[i][j].contains(drawRectangle) && i > 0) {
            return day > 1 && day <= calculateDays(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)) + 1;
        }

        return false;
    }

    public boolean checkClick(byte i, byte j, byte day) {
        if (matrix[i][j].contains(targetRectangle) && i > 0) {
            return day > 1 && day <= calculateDays(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)) + 1;
        }

        return false;
    }

    public int returnTargetDays(){

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){

                if(matrix[i][j].contains(targetRectangle)){

                    return matrixDays[i][j];
                }
            }

        }

        return 0;
    }

    private class Mouse extends MouseAdapter{

        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);

            drawRectangle = e.getPoint();

            repaint();
        }

        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            targetRectangle = e.getPoint();

            requestFocus();

            repaint();
        }

        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);

            drawRectangle.setLocation(-1,-1);

            repaint();
        }
    }
}
