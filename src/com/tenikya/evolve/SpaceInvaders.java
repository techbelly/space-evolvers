package com.tenikya.evolve;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;


public class SpaceInvaders extends Applet implements Runnable {

    Thread this_thread;
    Graphics offscreen;
    Image image;

    boolean gameInProgress = true;
    boolean IntroScreen = true;

    TurretController tc;
    AlienController ac;
    GameContext gc = GameContext.getInstance();

    int score = 0;
    Font scoreFont = new Font("Helvetica", Font.BOLD, 14);

    public void init() {
        setBackground(Color.black);

        gc.setWidth(getBounds().width);
        gc.setHeight(getBounds().height);

        showStatus("Loading Media...hold your horses!!");
        loadMedia(gc);
        tc = new TurretController(gc, this);
        ac = new AlienController(gc, this, tc);
        tc.loadMissiles(ac.getAliens());
        ac.loadMissiles(tc.getTurret());
        image = createImage(getBounds().width, getBounds().height);
        offscreen = image.getGraphics();
        newGame();
    }

    public void loadMedia(GameContext gc) {
        MediaTracker t = new MediaTracker(this);
        java.net.URL documentroot = getDocumentBase();
        Image turretImage = getImage(documentroot, "../media/turret.gif");
        t.addImage(turretImage, 0);
        gc.setTurretImage(turretImage);
        Image alienImage = getImage(documentroot, "../media/alien.gif");
        t.addImage(alienImage, 0);
        gc.setAlienImage(alienImage);
        Image explodeImage = getImage(documentroot, "../media/explode.gif");
        t.addImage(explodeImage, 0);
        gc.setExplodeImage(explodeImage);
        AudioClip alienfire = getAudioClip(documentroot, "../media/alienfire.au");
        gc.setAlienfire(alienfire);
        AudioClip missilesound = getAudioClip(documentroot, "../media/fire.au");
        gc.setMissilefire(missilesound);
        AudioClip hitsound = getAudioClip(documentroot, "../media/hit.au");
        gc.setHitsound(hitsound);
        try {
            t.waitForAll();
        } catch (InterruptedException e) {
           showStatus("Error Loading Media!!");
        }
        if (t.isErrorAny()) {
            showStatus("Error Loading Media!!");
        } else if (t.checkAll()) {
            showStatus("Media Loaded, starting game...");
        }
    }

    public boolean mouseMove(Event e, int x, int y) {
        if (gameInProgress) {
            tc.moveTurret(x);
        }
        return true;
    }

    public boolean mouseDrag(Event e, int x, int y) {
        if (gameInProgress) {
            tc.moveTurret(x);
        }

        return true;
    }

    public boolean mouseDown(Event e, int x, int y) {
        if (e.shiftDown()) {
        } else if (gameInProgress) {
            tc.fireMissile(x);
        }
        return true;
    }

    public void start() {
        this_thread = new Thread(this);
        if (this_thread != null) {
            this_thread.start();
        }
    }

    public void stop() {
        if (this_thread != null && this_thread.isAlive()) {
        }
    }

    public void dialogDone() {
    }


    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        offscreen.setColor(Color.black);
        offscreen.fillRect(0, 0, getBounds().width, getBounds().height);

        if (gameInProgress) {
            tc.paint(offscreen);
            ac.paint(offscreen);
            offscreen.setColor(Color.white);
            offscreen.setFont(scoreFont);
            offscreen.drawString("Generations : " + ac.generations, 50, 18);
        }

        g.drawImage(image, 0, 0, this);
    }

    public void run() {

        while (true) {
            repaint();
            if (gameInProgress) {
                tc.update();
                ac.update();
            }

            Thread.currentThread().yield();
        }
    }


    public void aliensDead() {
        if (gameInProgress) {
            IntroScreen = false;
        }
        gameInProgress = false;
    }

    public void turretDead() {
        if (gameInProgress) {
            IntroScreen = false;
        }
        gameInProgress = false;
    }

    public void addScore(int x) {
        score += x;
    }

    public void newGame() {
        score = 0;
        tc.newGame();
        ac.newGame();
    }

    public static int gameSpeed = 1;
}