package com.tenikya.evolve;

import java.awt.*;


class RectSprite {

    protected boolean visible;
    protected boolean active;
    protected int locx;
    protected int locy;

    protected Color colour;
    protected boolean fill;

    protected int width, height;


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean b) {
        visible = b;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean b) {
        active = b;
    }

    public void suspend() {
        setVisible(false);
        setActive(false);
    }

    public void restore() {
        setVisible(true);
        setActive(true);
    }

    public boolean getFill() {
        return fill;
    }

    public void setFill(boolean b) {
        fill = b;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color c) {
        colour = c;
    }

    public RectSprite(int w, int h, Color c) {
        locx = 0;
        locy = 0;
        width = w;
        height = h;
        colour = c;
        fill = false;
        restore();
    }

    public RectSprite(int x, int y, int w, int h, Color c) {
        locx = x;
        locy = y;
        width = w;
        height = h;
        colour = c;
        fill = false;
        restore();
    }

    public void update() {
    }

    public void paint(Graphics g) {
        if (visible) {
            g.setColor(colour);
            if (fill) {
                g.fillRect(locx, locy, width, height);
            } else {
                g.drawRect(locx, locy, width, height);
            }

        }

    }

}