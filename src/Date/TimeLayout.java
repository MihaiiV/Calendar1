package Date;

import java.awt.*;

public class TimeLayout implements LayoutManager {

    private int minWidth = 0;
    private int minHeight = 0;
    private int preferredWidth = 0;
    private int preferredHeight = 0;
    private boolean sizesSet = false;

    public void addLayoutComponent(String name, Component comp) {

    }


    public void removeLayoutComponent(Component comp) {

    }

    public void setSizes(Container parent) {

        if (sizesSet) return;

        int n = parent.getComponentCount();

        preferredWidth = 0;
        preferredHeight = 0;
        minWidth = 0;
        minHeight = 0;

        for(int i = 0; i < n - 1; i++){

            Component c = parent.getComponent(i);

            if(c.isVisible()){

                Dimension d = c.getPreferredSize();

                preferredHeight += preferredHeight + d.height;

                preferredWidth  += preferredWidth + d.width;
            }

            minHeight = preferredHeight / 2;

            minWidth = preferredWidth / 2;

            sizesSet = true;
        }

    }

    public Dimension preferredLayoutSize(Container parent) {

        setSizes(parent);
        Insets insets = parent.getInsets();
        int width = preferredWidth + insets.left + insets.right;
        int height = preferredHeight + insets.top + insets.bottom;
        return new Dimension(width, height);
    }


    public Dimension minimumLayoutSize(Container parent) {

        setSizes(parent);
        Insets insets = parent.getInsets();
        int width = minWidth + insets.left + insets.right;
        int height = minHeight + insets.top + insets.bottom;
        return new Dimension(width, height);

    }

    public void layoutContainer(Container parent) {

        setSizes(parent);

        int x = 25, y = 10;

        int n = parent.getComponentCount();
        for (int i = 0; i < n; i++) {
            Component c = parent.getComponent(i);

            if (c.isVisible()) {

                Dimension d = c.getMinimumSize();

                c.setBounds(x, y, x + d.width, y + d.height);

                if (i == 0) {

                    x = x + d.width + 10;

                    y = 22;
                } else {

                    x = 25;

                    y = d.height + 18;

                }

            }
        }

    }
}
