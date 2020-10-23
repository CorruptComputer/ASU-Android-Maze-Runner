package edu.angelo.finalprojectgupton;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
        Assets.wall4 = g.newPixmap("wall4.png", PixmapFormat.ARGB4444);
        Assets.wall3 = g.newPixmap("wall3.png", PixmapFormat.ARGB4444);
        Assets.wall2 = g.newPixmap("wall2.png", PixmapFormat.ARGB4444);
        Assets.wall1 = g.newPixmap("wall1.png", PixmapFormat.ARGB4444);
        Assets.floor = g.newPixmap("floor.png", PixmapFormat.ARGB4444);
        Assets.fire = g.newPixmap("fire.png", PixmapFormat.ARGB4444);
        Assets.player = g.newPixmap("player.png", PixmapFormat.ARGB4444);
        Assets.help1 = g.newPixmap("help1.png", PixmapFormat.ARGB4444);
        Assets.help2 = g.newPixmap("help2.png", PixmapFormat.ARGB4444);
        Assets.help3 = g.newPixmap("help3.png", PixmapFormat.ARGB4444);
        Assets.help4 = g.newPixmap("help4.png", PixmapFormat.ARGB4444);
        Assets.help5_1 = g.newPixmap("help5-1.png", PixmapFormat.ARGB4444);
        Assets.help5_2 = g.newPixmap("help5-2.png", PixmapFormat.ARGB4444);
        Assets.help6 = g.newPixmap("help6.png", PixmapFormat.ARGB4444);
        Assets.click = game.getAudio().newSound("click.ogg");
        Assets.eat = game.getAudio().newSound("eat.ogg");
        Assets.bitten = game.getAudio().newSound("bitten.ogg");
        Assets.paused = game.getAudio().newSound("paused.ogg");
        Assets.gameover = game.getAudio().newSound("gameover.ogg");
        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }

    public void present(float deltaTime) {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
    }
}
