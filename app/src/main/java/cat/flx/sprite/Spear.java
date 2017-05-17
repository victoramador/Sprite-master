package cat.flx.sprite;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Billy on 20/04/2017.
 */

public class Spear {
    Game game;
    int x, y, vy, newy, newx;
    int padLeft, padTop, colWidth, colHeight;
    private Paint debugPaint = new Paint();
    private int spriteIdx;
    public Spear(Game game) {
        this.game = game;
        padLeft = padTop = 7;
        colWidth = 15; colHeight = 32;
        spriteIdx = (int) (Math.random()*52);
        x = (int) (Math.random()*500+1);
        y = 0;
        vy = 0;
        newx = x;
        newy = y;
    }

    private Rect colRect = new Rect();
    Rect getCollisionRect() {
        colRect.set(x + padLeft, y + padTop, x + padLeft + colWidth, y + padTop + colHeight);
        return colRect;
    }
    void draw(Canvas canvas) {
        game.getSpearBitmapSet().drawBitmap(canvas, x, y, spriteIdx);
    }
    void physics(){
        Scene scene = game.getScene();
        vy = 3;
        newy = y + vy;
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
        y = newy;
    }
}
