package com.tenikya.evolve;

import java.applet.Applet;
import java.awt.*;
import java.util.Random;

public class TurretController {

    private Turret the_turret;
    private int tur_height;
    private Missile[] the_missile;
    private int nextMissile = 1;

    private int height;
    private int min_x, max_x;

    private int mis_min_x, mis_max_x;
    private int tur_y;
    //AudioClip missilesound;

    static final int MAX_MISSILES = 200;
    static final int MISS_WIDTH = 3;
    static final int MISS_HEIGHT = 20;
    static final int MISS_SPEED = -20;
    static final Color MISS_COLOUR = Color.red;

    SpaceInvaders game;

    public TurretController(GameContext gc, Applet a) {
        int width = gc.getWidth();
        this.height = gc.getHeight();
        the_turret = new Turret(gc.getTurretImage(), a, this);
        this.randomiser = new Random();
        int tur_width = gc.getTurretImage().getWidth(a) / 2;
        tur_height = gc.getTurretImage().getHeight(a);

        tur_y = height - tur_height;

        min_x = tur_width;
        max_x = width - tur_width;

        mis_min_x = min_x - 2;
        mis_max_x = max_x - 2;

        game = (SpaceInvaders) a;

        the_turret.setPosition(width / 2 - tur_width, tur_y);
    }

    public void fireMissile(int x) {
        if (!the_missile[nextMissile].isActive()) {
            if (x <= mis_min_x) {
                the_missile[nextMissile].init(mis_min_x);
            } else if (x >= mis_max_x) {
                the_missile[nextMissile].init(mis_max_x);
            } else {
                the_missile[nextMissile].init(x - 2);
            }
            //missilesound.play();
            nextMissile = (nextMissile + 1) % MAX_MISSILES;
        }
    }

    public void update() {
        for (int n = 0; n < MAX_MISSILES; n++) {
            the_missile[n].update();
        }
        int tx = the_turret.getX();

        tx += (int) (TURRET_V * randomiser.nextDouble());
        if (tx < min_x) {
            tx = min_x;
            TURRET_V = -TURRET_V;
        } else if (tx > max_x) {
            tx = max_x;
            TURRET_V = -TURRET_V;
        }
        the_turret.setPosition(tx, tur_y);

        if (randomiser.nextDouble() > 0.9) {
            fireMissile(tx);
        }
    }

    public void paint(Graphics g) {
        the_turret.paint(g);
        for (int n = 0; n < MAX_MISSILES; n++) {
            the_missile[n].paint(g);
        }

    }

    public int getTurretX() {
        return the_turret.getX();
    }

    public void newGame() {
        the_turret.restore();
        the_turret.newGame();

    }

    public java.util.Random randomiser;
    public int TURRET_V = 12;

    public void loadMissiles(Intersect[] aliens) {
        the_missile = new Missile[MAX_MISSILES];
                for (int n = 0; n < MAX_MISSILES; n++) {
                    the_missile[n] = new Missile(MISS_WIDTH, MISS_HEIGHT, MISS_COLOUR,
                            MISS_SPEED, height - tur_height,
                            0, aliens);
                }
    }

    public Intersect getTurret() {
        return this.the_turret;
    }
}
