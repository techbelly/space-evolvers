package com.tenikya.evolve;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.util.Random;

public class TurretController {

    private Turret the_turret;
    private int tur_width;
    private int tur_height;
    private Missile[] the_missile;
    private int nextMissile = 1;

    static int width, height;
    private int min_x, max_x;

    private int tur_min_x, tur_max_x;
    private int mis_min_x, mis_max_x;
    private int tur_y;
    AudioClip missilesound;

    static final int MAX_MISSILES = 200;
    static final int MISS_WIDTH = 3;
    static final int MISS_HEIGHT = 20;
    static final int MISS_SPEED = -20;
    static final Color MISS_COLOUR = Color.red;

    SpaceInvaders game;

    public TurretController(int width, int height, Image turImage, Intersect
            target[], Applet a) {
        this.width = width;
        this.height = height;
        the_turret = new Turret(turImage, a, this);
        this.randomiser = new Random();
        tur_width = turImage.getWidth(a) / 2;
        tur_height = turImage.getHeight(a);

        tur_y = height - tur_height;
        min_x = tur_width;
        max_x = width - tur_width;
        tur_min_x = 0;
        tur_max_x = width - tur_width * 2;
        mis_min_x = min_x - 2;
        mis_max_x = max_x - 2;

        game = (SpaceInvaders) a;

        the_turret.setPosition(width / 2 - tur_width, tur_y);

        the_missile = new Missile[MAX_MISSILES];
        for (int n = 0; n < MAX_MISSILES; n++) {
            the_missile[n] = new Missile(MISS_WIDTH, MISS_HEIGHT, MISS_COLOUR,
                    MISS_SPEED, height - tur_height,
                    0, target);
        }
    }

    public void moveTurret(int x) {
        /*	if (x<=min_x) {
                the_turret.setPosition(tur_min_x,tur_y);
            } else if (x>= max_x) {
                the_turret.setPosition(tur_max_x,tur_y);
            } else {
                the_turret.setPosition(x-tur_width,tur_y);
            }
        */
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

        tx += (int) (TURRET_V * randomiser.nextDouble() - (TURRET_V / 20));
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

    public Turret getTurret() {
        return the_turret;

    }

    public int getTurretY() {
        return tur_y;
    }

    public int getTurretX() {
        return the_turret.getX();
    }

    public int getLives() {
        return the_turret.getLives();
    }

    public void gunDead() {
        the_turret.suspend();
        for (int n = 0; n < MAX_MISSILES; n++) {
            the_missile[n].suspend();
        }
        game.turretDead();

    }

    public void newGame() {
        the_turret.restore();
        the_turret.newGame();

    }

    public java.util.Random randomiser;
    public int TURRET_V = 12;
}
