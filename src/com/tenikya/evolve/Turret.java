package com.tenikya.evolve;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;


class Turret extends GameSprite implements Moveable, Intersect {

    static final int NUM_LIVES = 1000;

    AudioClip hitsound;
    TurretController controller;
    protected int lives = NUM_LIVES;
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
        //hitsound.play();
        if (lives == 0) {
            controller.gunDead();
        } else {
            //	lives--;
            explode = true;
            parent.ac.turretHit(ID);
        }
    }

    public int getX() {
        return locx;
    }

    public int getLives() {
        return lives;
    }

    public void newGame() {
        lives = NUM_LIVES;
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