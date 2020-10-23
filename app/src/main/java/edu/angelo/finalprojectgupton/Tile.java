package edu.angelo.finalprojectgupton;


import java.util.Random;

class Tile {
    boolean isWall;
    int wallHealth = 0;
    static Random rdm = new Random();
    int xLoc;
    int yLoc;

    Tile (int x, int y) {
        this.xLoc = x;
        this.yLoc = y;
        this.generate();
    }

    private void generate() {
        this.isWall = rdm.nextBoolean();
        if (this.isWall) {
            this.wallHealth = rdm.nextInt(4) + 1;
        }
    }


    void copy(Tile t) {
        this.xLoc = t.xLoc;
        this.yLoc = t.yLoc;
        this.isWall = t.isWall;
        this.wallHealth = t.wallHealth;
    }

     void reset() {
        this.yLoc = -64;
        this.generate();
    }


    void hit() {
        if (this.wallHealth == 4) {
            return;
        }

        this.wallHealth -= 1;
        if (this.wallHealth == 0) {
            this.isWall = false;
        }
    }

    void advance() {
        this.yLoc += 4;
    }
}
