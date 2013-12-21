package com.tenikya.evolve;

import java.applet.Applet;
import java.awt.*;


class Turret extends GameSprite implements Moveable, Intersect {

    TurretController controller;
    boolean explode;
    int explodecount, count;
    SpaceInvaders parent;


    public Turret(Image i, Applet a, TurretController t) {
        super(i, a);
        parent = (SpaceInvaders) a;
        controller = t;
        explode = false;
        explodecount = 0;
        count = 5;
    }

    public void setPosition(int x, int y) {
        locx = x;
        locy = y;
    }

    public void setVelocity(int x, int y) {
    }

    public void updatePosition() {
    }

    public boolean intersect(int x1, int y1, int x2, int y2) {

        return visible && (x2 >= locx) && (locx + width >= x1) && (y2 >= locy) &&
                (locy + height >= y1);
    }

    public void hit(int ID) {
            explode = true;
            parent.ac.turretHit(ID);
    }

    public int getX() {
        return locx;
    }

    public void newGame() {
        explode = false;
    }


    public void paint(Graphics g) {
        if (visible) {
            if (explode && explodecount < count) {
                g.setColor(Color.red);
                g.fillRect(locx, locy, width, height);
                explodecount++;
            } else {
                if (explode) {
                    explodecount = 0;
                    explode = false;
                }
                g.drawImage(sprite, locx, locy, parent);
            }
        }
    }
}