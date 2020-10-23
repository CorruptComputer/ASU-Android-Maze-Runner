package edu.angelo.finalprojectgupton;

import java.util.List;
import java.util.logging.Logger;

import android.graphics.Color;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;

public class GameScreen extends Screen {
    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    private GameState state = GameState.Ready;
    final private World world;
    private int oldScore = 0;
    private String score = "0";

    public GameScreen(Game game) {
        super(game);
        world = new World();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if (state == GameState.Ready) {
            updateReady(touchEvents);
        }
        if (state == GameState.Running) {
            updateRunning(touchEvents, deltaTime);
        }
        if (state == GameState.Paused) {
            updatePaused(touchEvents);
        }
        if (state == GameState.GameOver) {
            updateGameOver(touchEvents);
        }
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if (touchEvents.size() > 0) {
            state = GameState.Running;
        }
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i += 1) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y < 64) {
                    if (Settings.soundEnabled) {
                        Assets.paused.play(1);
                    }
                    state = GameState.Paused;
                    return;
                }
            }
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (event.y > 64) {
                    world.tap(event.x);
                }
            }
        }

        world.update(deltaTime);
        if (world.gameOver) {
            if (Settings.soundEnabled) {
                Assets.gameover.play(1);
            }
            state = GameState.GameOver;
        }
        if (oldScore != world.score) {
            if (Settings.soundEnabled && oldScore < world.score) {
                Assets.eat.play(1);
            }

            oldScore = world.score;
            score = "" + oldScore;
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        System.out.println("Player row = " + world.tileSet.playerRow);

        int len = touchEvents.size();
        for (int i = 0; i < len; i += 1) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 80 && event.x <= 240) {
                    if (event.y > 100 && event.y <= 148) {
                        if (Settings.soundEnabled) {
                            Assets.click.play(1);
                        }
                        state = GameState.Running;
                        return;
                    }
                    if (event.y > 148 && event.y < 196) {
                        if (Settings.soundEnabled) {
                            Assets.click.play(1);
                        }

                        Settings.addScore(world.score);
                        Settings.save(game.getFileIO());
                        game.setScreen(new MainMenuScreen(game));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i += 1) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x >= 128 && event.x <= 192 &&
                        event.y >= 200 && event.y <= 264) {
                    if (Settings.soundEnabled) {
                        Assets.click.play(1);
                    }
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        drawWorld(world);
        if (state == GameState.Ready) {
            drawReadyUI();
        }
        if (state == GameState.Running) {
            drawRunningUI();
        }
        if (state == GameState.Paused) {
            drawPausedUI();
        }
        if (state == GameState.GameOver) {
            drawGameOverUI();
        }

        drawText(g, score, g.getWidth() - score.length() * 20, 0);
    }

    private void drawWorld(World world) {
        Graphics g = game.getGraphics();

        for (int i = 0; i < TileSet.NUM_ROWS; i++) {
            for (int k = 0; k < TileSet.NUM_COLS; k++) {
                if (world.tileSet.tiles[i][k].isWall) {
                    // draw walls
                    switch (world.tileSet.tiles[i][k].wallHealth) {
                        case 4:
                            g.drawPixmap(Assets.wall4, world.tileSet.tiles[i][k].xLoc, world.tileSet.tiles[i][k].yLoc);
                            break;
                        case 3:
                            g.drawPixmap(Assets.wall3, world.tileSet.tiles[i][k].xLoc, world.tileSet.tiles[i][k].yLoc);
                            break;
                        case 2:
                            g.drawPixmap(Assets.wall2, world.tileSet.tiles[i][k].xLoc, world.tileSet.tiles[i][k].yLoc);
                            break;
                        case 1:
                            g.drawPixmap(Assets.wall1, world.tileSet.tiles[i][k].xLoc, world.tileSet.tiles[i][k].yLoc);
                            break;
                    }
                } else {
                    // draw floors
                    g.drawPixmap(Assets.floor, world.tileSet.tiles[i][k].xLoc, world.tileSet.tiles[i][k].yLoc);
                }

            }
        }

        // draw player
        Tile playerLoc = world.tileSet.tiles[world.tileSet.playerRow][world.tileSet.playerCol];
        g.drawPixmap(Assets.player, playerLoc.xLoc, playerLoc.yLoc);

        // dark area at the top
        g.drawRect(0, 0, World.WORLD_WIDTH+1, 65, Color.argb(150, 0, 0, 0));

        // lines for tapping
        g.drawLine(96, 0, 96, World.WORLD_HEIGHT, Color.BLACK);
        g.drawLine(World.WORLD_WIDTH - 96, 0, World.WORLD_WIDTH - 96, World.WORLD_HEIGHT, Color.BLACK);

        // fire at the bottom
        g.drawPixmap(Assets.fire, 0, World.WORLD_HEIGHT-32);
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.ready, 47, 100);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.pause, 80, 100);
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.gameOver, 62, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
    }

    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i += 1) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX;
            int srcWidth;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {
        if (state == GameState.Running) {
            state = GameState.Paused;
        }
        if (world.gameOver) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
