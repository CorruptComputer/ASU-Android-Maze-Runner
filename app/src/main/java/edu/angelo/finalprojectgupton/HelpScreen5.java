package edu.angelo.finalprojectgupton;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;

import java.util.List;

public class HelpScreen5 extends Screen {
    public HelpScreen5(Game game) {
        super(game);
    }

    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i += 1) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP) {
                game.setScreen(new HelpScreen6(game));
                if (Settings.soundEnabled) {
                    Assets.click.play(1);
                }
                return;
            }
        }
    }

    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        for (int i = 0; i < TileSet.NUM_ROWS; i++) {
            for (int k = 0; k < TileSet.NUM_COLS; k++) {
                g.drawPixmap(Assets.floor, k*32, (i*32) - 96);
            }
        }

        // draw player
        g.drawPixmap(Assets.player, 3*32, (6*32) - 96);

        // lines for tapping
        g.drawLine(96, 0, 96, World.WORLD_HEIGHT, Color.BLACK);
        g.drawLine(World.WORLD_WIDTH - 96, 0, World.WORLD_WIDTH - 96, World.WORLD_HEIGHT, Color.BLACK);

        // draw text
        g.drawPixmap(Assets.help5_1, 3*32, (7*32) - 96);
        g.drawPixmap(Assets.wall3, 4*32, (6*32) - 96);
        g.drawPixmap(Assets.wall2, 5*32, (6*32) - 96);
        g.drawPixmap(Assets.wall1, 6*32, (6*32) - 96);

        // draw text
        g.drawPixmap(Assets.help5_2, 3*32, (12*32) - 96);
        g.drawPixmap(Assets.wall4, 3*32, (11*32) - 96);

        // dark area at the top
        g.drawRect(0, 0, World.WORLD_WIDTH+1, 65, Color.argb(150, 0, 0, 0));

        // fire at the bottom
        g.drawPixmap(Assets.fire, 0, World.WORLD_HEIGHT-32);
    }

    public void pause() {
        Settings.save(game.getFileIO());
    }

    public void resume() {
    }

    public void dispose() {
    }
}
