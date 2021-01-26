package Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NotePanel extends JPanel {

    private JButton ok, cancel;

    private JTextField text;

    private final Focus focus;

    private final Mouse mouse;

    private final Key key;

    public NotePanel() {

        setLayout(new BorderLayout());

        focus = new Focus(this);

        mouse = new Mouse();

        key = new Key();

        addMouseListener(mouse);

        JLabel l = new JLabel("Today");

        l.setFont(new Font("Arial",Font.PLAIN,24));

        add(l,BorderLayout.NORTH);

        JLabel c = new JLabel("No Event");

        c.setFont(new Font("Arial",Font.PLAIN,18));

        add(c,BorderLayout.CENTER);


    }

    public void setComponents(){

        text = new JTextField(23);

        text.setFont(new Font("Arial",Font.PLAIN,16));

        text.setCaretColor(Color.GRAY);

        text.setText("Type Event");

        text.setFocusable(true);

        text.addFocusListener(focus);

        text.addMouseListener(mouse);

        text.addKeyListener(key);

        add(text);

        ok = new JButton("Ok");

        cancel = new JButton("Cancel");

        add(ok);

        add(cancel);

        ok.setVisible(false);

        cancel.setVisible(false);

    }

    private class Focus extends FocusAdapter{

        private final NotePanel panel;

        public Focus(NotePanel panel){

            this.panel = panel;
        }

        public void focusGained(FocusEvent e) {
            super.focusGained(e);

            if(text.getText().equals("Type Event")){

                text.setText("");
            }
        }

        public void focusLost(FocusEvent e) {
            super.focusLost(e);

            if(text.getText().equals("")){

                text.setText("Type Event");

                ok.setVisible(false);

                cancel.setVisible(false);

                repaint();
            }

            if(!ok.isVisible() || !cancel.isVisible()){

                panel.requestFocus();
            }
        }
    }

    private class Mouse extends MouseAdapter{

        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            if(!e.getSource().equals(text)){

                requestFocus();
            }
        }

        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if(e.getSource().equals(text) && e.getClickCount() >= 2){

                text.selectAll();
            }
        }
    }

    private class Key extends KeyAdapter{

        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);

            ok.setVisible(true);

            cancel.setVisible(true);
        }
    }
}
