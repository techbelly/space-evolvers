package com.tenikya.evolve;

import java.applet.Applet;
import java.awt.*;


class GameSprite {
    protected boolean visible;
    protected boolean active;
    protected int locx;
    protected int locy;

    protected int width, height;

    protected Image sprite;
    protected Applet parent;

    public void setVisible(boolean b) {
        visible = b;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean b) {
        active = b;
    }

    public void restore() {
        setVisible(true);
        setActive(true);
    }

    public GameSprite(Image i, Applet a) {
        locx = 0;
        locy = 0;
        sprite = i;
        parent = a;
        if (sprite != null) {
            width = sprite.getWidth(parent);
            height = sprite.getHeight(parent);
        }
        restore();
    }

    public GameSprite(int x, int y, Image i, Applet a) {
        locx = x;
        locy = y;
        sprite = i;
        parent = a;
        if (sprite != null) {
            width = sprite.getWidth(parent);
            height = sprite.getHeight(parent);
        }
        restore();
    }

    public void update() {
    }

    public void paint(Graphics g) {
        if (visible) {
            g.drawImage(sprite, locx, locy, parent);
        }
    }
}