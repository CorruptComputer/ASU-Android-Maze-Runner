package edu.angelo.finalprojectgupton;

class World {
    static final int WORLD_WIDTH = 320;
    static final int WORLD_HEIGHT = 480;
    TileSet tileSet;
    boolean gameOver = false;
    int score = 0;

    private static final int SCORE_INCREMENT = 10;
    private static final float TICK_DECREMENT = 0.005f;
    private static final float TICK_MIN = 0.025f;
    private float tick = 0.06f;
    private float tickTime = 0;
    private boolean moved = false;

    World() {
        tileSet = new TileSet();
    }

    void update(float deltaTime) {
        if (gameOver) {
            return;
        }

        tickTime += deltaTime;

        while (tickTime > tick) {
            tickTime -= tick;
            tileSet.advance();


        }

        if (tileSet.playerRow >= 15) {
            gameOver = true;
        }
    }

    void tap(int x) {
        if (x < 96) {
            tileSet.move(tileSet.playerRow, tileSet.playerCol - 1);
        } else if (x < World.WORLD_WIDTH - 96) {
            moved = tileSet.move(tileSet.playerRow - 1, tileSet.playerCol);
        } else {
            tileSet.move(tileSet.playerRow, tileSet.playerCol + 1);
        }

        if (moved) {
            score += SCORE_INCREMENT;
            moved = false;
            if (score % 100 == 0 && tick - TICK_DECREMENT >= TICK_MIN) {
                tick -= TICK_DECREMENT;
            }
        }

    }
}
