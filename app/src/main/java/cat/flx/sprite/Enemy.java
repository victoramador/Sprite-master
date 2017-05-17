package cat.flx.sprite;


import android.graphics.Canvas;
import android.graphics.Rect;

public class Enemy {
    private static int[][] states = {
            { 20, 20, 20, 16, 17, 20, 20, 20, 18, 19 }
    };
    int[][] getStates() { return states; }
    Game game;
    private int spriteIdx;
    int idxCounter;
    int x, y, vy,vx, newy, newx, dir, xCounter;
    int padLeft, padTop, colWidth, colHeight;
    Enemy(Game game) {
        this.game = game;
        padLeft = padTop = 6;
        colWidth = 20; colHeight = 16;
        dir = 1;
        x = (int) (Math.random()*500+1);
        y = 0;
        xCounter = 0;
        vx = 3;
        spriteIdx = 1;
        idxCounter = 0;
    }
    private Rect colRect = new Rect();
    Rect getCollisionRect() {
        colRect.set(x + padLeft, y + padTop, x + padLeft + colWidth, y + padTop + colHeight);
        return colRect;
    }
    void draw(Canvas canvas) {
        game.getDragonBitmapSet().drawBitmap(canvas, x, y, spriteIdx);
    }
    void physics() {

        Scene scene = game.getScene();

        // detect wall to right
        vy = 3;
        newy = y + vy;
        if (xCounter > 100){
            vx = -3;
            xCounter = 0;
        }else if (xCounter < -100){
            vx = 3;
            xCounter = 0;
        }
        newx = x + vx;
        if (vx > 0) {
            int col = (newx + padLeft + colWidth) / 16;
            int r1 = (newy + padTop) / 16;
            int r2 = (newy + padTop + colHeight - 1) / 16;
            for (int row = r1; row <= r2; row++) {
                if (scene.isWall(row, col)) {
                    newx = col * 16 - padLeft - colWidth - 1;
                    break;
                }
            }
        }
        // detect wall to left
        if (vx < 0) {
            int col = (newx + padLeft) / 16;
            int r1 = (newy + padTop) / 16;
            int r2 = (newy + padTop + colHeight - 1) / 16;
            for (int row = r1; row <= r2; row++) {
                if (scene.isWall(row, col)) {
                    newx = (col + 1) * 16 - padLeft;
                    break;
                }
            }
        }

        if (vy >= 0) {
            int c1 = (newx + padLeft) / 16;
            int c2 = (newx + padLeft + colWidth) / 16;
            int row = (newy + padTop + colHeight) / 16;
            for (int col = c1; col <= c2; col++) {
                if (scene.isGround(row, col)) {
                    newy = row * 16 - padTop - colHeight;
                    vy = 0;
                    break;
                }
            }
        }

        // apply resulting physics
        x = newx;
        y = newy;
        xCounter = xCounter + vx;
        idxCounter++;
        if (vx > 0) {
            if (spriteIdx > 3)
                spriteIdx = 0;
            if (idxCounter>3) {
                spriteIdx++;
                idxCounter = 0;
                if (spriteIdx > 3)
                    spriteIdx = 0;
            }
        }
        if (vx < 0) {
            if (spriteIdx<4)
                spriteIdx = 4;
            if (idxCounter>7) {
                spriteIdx++;
                idxCounter = 4;
                if (spriteIdx > 7)
                    spriteIdx = 4;
            }
        }
    }
}
