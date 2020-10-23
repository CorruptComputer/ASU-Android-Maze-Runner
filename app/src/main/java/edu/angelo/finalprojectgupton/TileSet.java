package edu.angelo.finalprojectgupton;

class TileSet {
    final static int NUM_ROWS = 18;
    final static int NUM_COLS = 10;
    Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];
    int playerRow;
    int playerCol;


    TileSet() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int k = 0; k < NUM_COLS; k++) {
                tiles[i][k] = new Tile(k*32, (i*32) - 96);
            }
        }

        playerRow = 12;
        playerCol = 5;
        if (tiles[playerRow][playerCol].isWall) {
            tiles[playerRow][playerCol].isWall = false;
            tiles[playerRow][playerCol].wallHealth = 0;
        }
    }

    boolean move(int row, int col) {
        if (col < 0 || col >= NUM_COLS) {
            return false;
        }

        if (tiles[row][col].isWall) {
            tiles[row][col].hit();
            return false;
        }

        if (playerRow < 4) {
            return false;
        }

        playerRow = row;
        playerCol = col;
        return true;
    }

    void advance() {
        for (Tile[] ts: tiles) {
            for (Tile t: ts) {
                t.advance();
            }
        }

        // is our last row fully off the screen?
        if (tiles[NUM_ROWS-1][0].yLoc >= World.WORLD_HEIGHT + 32) {

            // shift all the rows down by 1
            for (int i = NUM_ROWS - 1; i > 0; i--) {
                for (int k = 0; k < NUM_COLS; k++) {
                    tiles[i][k].copy(tiles[i - 1][k]);
                }
            }

            // reset the top row
            for (int k = 0; k < NUM_COLS; k++) {
                tiles[0][k].reset();
            }

            playerRow += 1;
        }
    }
}
