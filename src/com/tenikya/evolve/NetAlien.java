package com.tenikya.evolve;

import java.applet.Applet;
import java.awt.*;

class NetAlien extends GameSprite implements Intersect, Moveable {
    int vx, vy;
    protected Image explodeImage;
    boolean hit;
    int hitcount, count;
    AlienController aliencontrol;
    public FloatGenes myGenes;
    public Brain myBrain;
    int lowesty = 0;
    int age = 0;
    int shotsFired = 0;
    public boolean shoot = false;
    int yippee = 0;
    //weights
    static public double lowestyw = 15.0;
    static public double agew = 20.0;
    static public double shotsw = -400;
    static public double yippeew = 6000;
    static public int maxAge = 1200;

    public NetAlien(Image i, Image e, int x, int y, int vx, int vy, Applet a, AlienController b) {
        super(x, y, i, a);
        this.vx = vx;
        this.vy = vy;
        aliencontrol = b;
        explodeImage = e;
        this.spaceheight = b.height;
        this.spacewidth = b.width;
        hit = false;
        hitcount = 0;
        count = 5;
        myGenes = new FloatGenes(NUM_GENES);
        myBrain = new Brain(myGenes);
    }

    public void setPosition(int x, int y) {
        locx = x;
        locy = y;
    }

    public int getX() {
        return locx;
    }

    public int getY() {
        return locy;
    }

    public void setVelocity(int x, int y) {
        vx = vx;
        vy = vy;
    }


    public boolean intersect(int x1, int y1, int x2, int y2) {

        return visible && (x2 >= locx) && (locx + width >= x1) && (y2 >= locy) &&
                (locy + height >= y1);
    }

    public void hit(int x) {
        hit = true;
    }

    public void expire() {
        hit = true;
    }

    public void updatePosition() {
    }

    public void update() {

        if (!hit) {
            doTick();
        } else {
            if (hitcount == count) {
                aliencontrol.alienHit(this);
            } else {
                hitcount++;
                locy += 10;
                locx += vx;
            }
        }
    }

    public void paint(Graphics g) {
        if (visible) {
            if (hit) {
                g.drawImage(explodeImage, locx, locy, parent);
            } else {
                g.drawImage(sprite, locx, locy, parent);
            }
        }
    }

    public void doTick() {
        age++;
        if (age > maxAge) {
            expire();
        }
        int xAcceleration = 0;
        int yAcceleration = 0;
        double wTX = (double) aliencontrol.getTurretX() / (double) spacewidth - 0.5;
        double wMX = (double) locx / (double) spacewidth - 0.5;
        double wYX = (double) locy / (double) spaceheight - 0.5;
        double wAGE = (double) age / (double) maxAge - 0.5;
        double nMX = (double) ((locx - neighbour.locx) / (double) spacewidth) / 2;
        double nMY = (double) ((locy - neighbour.locy) / (double) spaceheight) / 2;
        double vMX = (double) (vx) / (POWERWEIGHT * 2);
        double vMY = (double) (vy) / (POWERWEIGHT * 2);
        myBrain.decisionTime(wTX, wMX, wYX, wAGE, nMX, nMY, vMX, vMY);
        if (myBrain.fireLeftEngine()) {
            xAcceleration += (int) (myGenes.GetGene(LEFTPOWER) * POWERWEIGHT);
        }
        if (myBrain.fireRightEngine()) {
            xAcceleration -= (myGenes.GetGene(RIGHTPOWER) * POWERWEIGHT);
        }
        if (myBrain.fireThruster()) {
            yAcceleration -= (myGenes.GetGene(THRUSTPOWER) * POWERWEIGHT);
        }
        if (Gravity) {
            yAcceleration += (GRAVITY_CONSTANT);
        }
        if (myBrain.fireGun()) {
            shotsFired++;
            shoot = true;
        }
        // GCSE Physics !! v = u + at
        vx = vx + xAcceleration;
        vy = vy + yAcceleration;
        locx = locx + vx + (int) (Math.random() * 8 - 4);
        locy = locy + vy + (int) (Math.random() * 8 - 4);
        if (locx < 50 || locx > (spacewidth - 50)) expire();
        if (locy < 50 || locy > (spaceheight - 50)) expire();
    }


    public void resetParams() {

        age = 0;
        lowesty = 0;
        shotsFired = 0;
        hit = false;
        hitcount = 0;
        yippee = 0;
        myBrain.setWeights(myGenes);
        vx = 0;
        vy = 0;
    }

    public void goodshot() {
        yippee++;
    }

    public int FIREPOWER = 35;
    public boolean Gravity = true;
    public int GRAVITY_CONSTANT = 3;
    public int LEFTPOWER = 32;
    public NetAlien neighbour;
    public int NUM_GENES = 100;
    public int POWERWEIGHT = 30;
    public int RIGHTPOWER = 33;
    public int spaceheight;
    public int spacewidth;
    public int THRUSTPOWER = 34;

    public long fitness() {
        //	System.out.print(age+" "+lowesty+" "+(yippee+1)+" "+shotsFired+"\n");
        long fit = (long) (age * agew + lowesty * lowestyw + yippee * yippeew + shotsFired *
                shotsw);
        return fit;
    }

    /**
     * Insert the method's description here.
     * Creation date: (04/12/00 02:42:36)
     * @return NetAlien
     */
    public NetAlien getNeighbour() {
        return neighbour;
    }

    /**
     * Insert the method's description here.
     * Creation date: (04/12/00 02:51:11)
     * @return int
     */
    public int getNUM_GENES() {
        return NUM_GENES;
    }

    /**
     * Insert the method's description here.
     * Creation date: (04/12/00 02:42:36)
     * @param newNeighbour NetAlien
     */
    public void setNeighbour(NetAlien newNeighbour) {
        neighbour = newNeighbour;
    }

    /**
     * Insert the method's description here.
     * Creation date: (04/12/00 02:51:11)
     * @param newNUM_GENES int
     */
    public void setNUM_GENES(int newNUM_GENES) {
        NUM_GENES = newNUM_GENES;
    }
}
