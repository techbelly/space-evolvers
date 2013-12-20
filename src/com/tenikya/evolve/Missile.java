package com.tenikya.evolve;

import java.awt.*;


class Missile extends RectSprite {
    protected int vy;
    protected int start_y;
    protected int stop_y;
    Intersect target[];
    int firerID;


    public Missile(int w, int h, Color c, int vy, int start_y, int stop_y,
                   Intersect target[]) {
        super(w, h, c);
        setFill(true);
        firerID = 1000; //default
        this.vy = vy;
        this.start_y = start_y;
        this.stop_y = stop_y;
        this.target = target;
        suspend();
    }

    public void init(int x) {
        locx = x;
        locy = start_y;
        count = 0;
        restore();
    }

    public void init(int x, int y) {
        locx = x;
        locy = y;
        count = 0;
        restore();
    }

    public void setFirerID(int ID) {
        firerID = ID;
    }


    public void update() {
        if (active) {
            count++;
            boolean susp = false;
            if (count > maxCount) {
                susp = true;
            }
            int yAcceleration = 0;
            if (gravity) {
                yAcceleration += 3;
            }
            vy = vy + yAcceleration;
            locy += vy;
            locx += vx;
            if (stop_y < start_y) {
                if (locy < stop_y) {
                    susp = true;
                }
            } else if (locy > stop_y) {
                susp = true;
            }


            if (susp == true) {
                suspend();
            } else {
                if (target != null) {
                    for (int i = 0; i < target.length; i++) {
                        if (target[i].intersect(locx, locy, locx + width, locy + height)) {
                            target[i].hit(firerID);
                            suspend();
                            break;
                        }
                    }
                }

            }
        }
    }

    public int count = 0;
    public boolean gravity = false;
    static public int maxCount = 50;
    public int vx = 0;

    /**
     * Insert the method's description here.
     * Creation date: (04/12/00 03:15:21)
     * @return boolean
     */
    public boolean isGravity() {
        return gravity;
    }

    /**
     * Insert the method's description here.
     * Creation date: (04/12/00 03:15:21)
     * @param newGravity boolean
     */
    public void setGravity(boolean newGravity) {
        gravity = newGravity;
    }
}