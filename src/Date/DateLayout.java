package Date;

import java.awt.*;

public class DateLayout implements LayoutManager{

    private final Dimension size;


    public DateLayout(Dimension size){

        this.size = size;

        size.width -= 20;
    }

    public void addLayoutComponent(String name, Component comp) {

    }


    public void removeLayoutComponent(Component comp) {

    }


    public Dimension preferredLayoutSize(Container parent) {

        return new Dimension(1,1);
    }


    public Dimension minimumLayoutSize(Container parent) {

        return new Dimension(1,1);
    }

    public void layoutContainer(Container parent) {

        int x = 3, y = 0;

        int n = parent.getComponentCount();

        for (int i = 0; i < n; i++) {
            Component c = parent.getComponent(i);

            if (c.isVisible()) {

                if (i == 0) {

                    c.setBounds(x, y, size.width, size.height / 6);

                    y += size.height / 6;
                } else if (i == 1) {

                    c.setBounds(x, y, size.width, size.height / 2 + size.height / 25);

                    y += size.height / 2 + size.height / 25;
                } else {

                    c.setBounds(x, y, size.width, size.height / 5 - size.height / 50);
                }

            }
        }

    }

    public Dimension getSize() {

        Dimension newSize = new Dimension(size.width, size.height);

        newSize.height /= 2;

        return newSize;
    }
}
