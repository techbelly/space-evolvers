package com.tenikya.evolve;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;


public class AlienController {

    public long generations = 0;
    static int width, height;
    private NetAlien[] aliens;
    static final int NUM_ALIENS = 20;
    static final int SPACER = 20;
    protected Image sprite;
    protected Image explode;
    private Missile[] the_missile;
    private int nextMissile = 1;
    Intersect target[];
    AudioClip missilesound;

    static final int MAX_MISSILES = 100;
    static final int MISS_WIDTH = 3;
    static final int MISS_HEIGHT = 5;
    static final int MISS_SPEED = 0;
    static final Color MISS_COLOUR = Color.green;
    static final double AGGRESSION = 0.97;
    private TurretController turretController;


    public AlienController(GameContext gc, Applet a, TurretController t) {

        this.width = gc.getWidth();
        this.height = gc.getHeight();
        this.sprite = gc.getAlienImage();
        this.explode = gc.getExplodeImage();
        this.missilesound = gc.getAlienfire();
        this.turretController = t;
        aliens = new NetAlien[NUM_ALIENS];
        for (int n = 0; n < aliens.length; n++) {
            aliens[n] = new NetAlien(sprite, explode, 0, 0, 10, 0, a, this);
            if (n != 0) aliens[n].setNeighbour(aliens[n - 1]);
            initPosition(aliens[n], n);
        }
        aliens[0].setNeighbour(aliens[aliens.length - 1]);
        target = new Intersect[1];
        the_missile = new Missile[MAX_MISSILES];
    }

    public void loadMissiles(Intersect t) {

        target[0] = t;

        for (int n = 0; n < MAX_MISSILES; n++) {
            the_missile[n] = new Missile(MISS_WIDTH, MISS_HEIGHT, MISS_COLOUR,
                    MISS_SPEED, 50,
                    height, target);
        }
    }


    private void initPosition(Moveable m, int n) {
        m.setPosition((int) (Math.random() * (width - 100) + 50), (int) (height / 2.0));
    }

    public void paint(Graphics g) {
        for (int n = 0; n < aliens.length; n++) {
            aliens[n].paint(g);
        }
        for (int n = 0; n < MAX_MISSILES; n++) {
            the_missile[n].paint(g);
        }

    }

    public void update() {
        for (int n = 0; n < aliens.length; n++) {
            if (aliens[n].isActive()) {
                aliens[n].update();
                if (aliens[n].shoot) {
                    fireMissile(aliens[n].locx, aliens[n].locy, aliens[n].vx, aliens[n].vy, n);
                    //missilesound.play();
                    aliens[n].shoot = false;
                }

            }

        }

        for (int n = 0; n < MAX_MISSILES; n++) {
            the_missile[n].update();
        }

    }


    public NetAlien[] getAliens() {
        return aliens;
    }

    public void fireMissile(int x, int y, int owner) {
        if (!the_missile[nextMissile].isActive()) {
            the_missile[nextMissile].init(x - 2, y + 20);
            the_missile[nextMissile].setFirerID(owner);
        }
        nextMissile = (nextMissile + 1) % MAX_MISSILES;
    }

    public void newGame() {
    }


    public void turretHit(int ID) {
        aliens[ID].goodshot();
    }

    public int getTurretX() {
        return this.turretController.getTurretX();
    }


    public void alienHit(NetAlien goner) {
        generations++;
        long minFitness = 0;
        int fittestAlien = 0;
        for (int n = 0; n < aliens.length; n++) {
            long testfit = aliens[n].fitness();
            if (testfit > minFitness) {
                fittestAlien = n;
                minFitness = testfit;
            }

        }
        goner.myGenes.MutateFrom(aliens[fittestAlien].myGenes);
        goner.resetParams();
        initPosition(goner, 2);
    }


    public void fireMissile(int x, int y, int vx, int vy, int owner) {
        if (!the_missile[nextMissile].isActive()) {
            the_missile[nextMissile].init(x - 2, y + 20);
            the_missile[nextMissile].setFirerID(owner);
            the_missile[nextMissile].vx = vx;
            the_missile[nextMissile].vy = vy;
            the_missile[nextMissile].gravity = true;
        }
        nextMissile = (nextMissile + 1) % MAX_MISSILES;
    }
}