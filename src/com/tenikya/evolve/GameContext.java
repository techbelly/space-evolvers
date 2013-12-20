package com.tenikya.evolve;

import java.util.Properties;

public class GameContext extends Properties {

    // Singleton Pattern Stuff
    private static GameContext myHandle;
    private GameContext() {};
    static public GameContext getInstance() {
        if (myHandle == null) {
            myHandle = new GameContext();
        }
        return myHandle;
    }

}
