package edu.angelo.finalprojectgupton;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;

public class MazeRunnerGame extends AndroidGame {

    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
