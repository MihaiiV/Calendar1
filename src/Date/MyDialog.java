package Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MyDialog extends JPanel {

    private JDialog dialog;

    private  final JButton yes, no;

    private final JLabel label;

    private final Frame f;

    private final Action action;

    private final JPanel buttonPanel;

    public MyDialog(Frame f) {

        this.f = f;

        setLayout(new BorderLayout());

        buttonPanel = new JPanel();

        buttonPanel.setBackground(Color.white);

        label = new JLabel("       Are you sure you want to exit?");

        label.setFont(new Font("Arial", Font.BOLD, 16));

        label.setOpaque(true);

        label.setBackground(Color.white);

        action = new Action();

        yes = new JButton("Yes");

        no = new JButton("No");

        yes.addActionListener(action);

        no.addActionListener(action);

        buttonPanel.add(yes);

        buttonPanel.add(no);

        add(label, BorderLayout.CENTER);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class Action extends AbstractAction {

        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == yes) {

                dialog.dispose();

                f.close();

            } else if (e.getSource() == no) {

                dialog.setVisible(false);
            }
        }
    }

    public void showDialog() {

        if (dialog == null || dialog.getOwner() != this.f) {

            dialog = new JDialog(this.f, true);

            dialog.add(this);

            dialog.setResizable(false);

            dialog.setSize(300, 130);
        }

        dialog.setLocation(f.getX() + f.getWidth() / 2 - dialog.getWidth() / 2, f.getY() + f.getHeight() / 2 - dialog.getHeight() / 2);

        dialog.setTitle("Confirm Exit");

        dialog.setVisible(true);

    }

}
