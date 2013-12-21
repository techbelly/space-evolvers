package com.tenikya.evolve;

import java.applet.AudioClip;
import java.awt.*;

public class GameContext {

    // Singleton Pattern Stuff
    private static GameContext myHandle;
    private GameContext() {};
    static public GameContext getInstance() {
        if (myHandle == null) {
            myHandle = new GameContext();
        }
        return myHandle;
    }

    private int width;
    private int height;
    private Image turretImage;
    private Image alienImage;
    private Image explodeImage;
    private AudioClip alienfire;
    private AudioClip missilefire;
    private AudioClip hitsound;
    

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Image getTurretImage() {
        return turretImage;
    }

    public void setTurretImage(Image turretImage) {
        this.turretImage = turretImage;
    }

    public Image getAlienImage() {
        return alienImage;
    }

    public void setAlienImage(Image alienImage) {
        this.alienImage = alienImage;
    }

    public Image getExplodeImage() {
        return explodeImage;
    }

    public void setExplodeImage(Image explodeImage) {
        this.explodeImage = explodeImage;
    }

    public AudioClip getAlienfire() {
        return alienfire;
    }

    public void setAlienfire(AudioClip alienfire) {
        this.alienfire = alienfire;
    }

    public AudioClip getMissilefire() {
        return missilefire;
    }

    public void setMissilefire(AudioClip missilefire) {
        this.missilefire = missilefire;
    }

    public AudioClip getHitsound() {
        return hitsound;
    }

    public void setHitsound(AudioClip hitsound) {
        this.hitsound = hitsound;
    }
}
