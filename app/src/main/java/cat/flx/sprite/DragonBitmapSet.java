package cat.flx.sprite;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by dam on 8/5/17.
 */

public class DragonBitmapSet {
    private Bitmap[] bitmaps;

    private int[][] sheetInfo = {
            { 281, 165, 30, 22, 0 },//  0: firing right 1
            { 317, 165, 37, 22, 0 },//  1: firing right 2
            { 360, 163, 41, 24, 0 },	//  2: firing right 3
            { 407, 167, 44, 22, 0 },	//  3: firing right 4
            { 281, 165, 30, 22, 1 },//  4: firing right 1
            { 317, 165, 37, 22, 1 },//  5: firing right 2
            { 360, 163, 41, 24, 1 },	//  6: firing right 3
            { 407, 167, 44, 22, 1 },	//  7: firing right 4
    };

    public Bitmap getBitmap(int i) { return bitmaps[i]; }
    private Paint paint;
    void drawBitmap(Canvas canvas, int x, int y, int i) {
        Bitmap sprite = getBitmap(i);
        canvas.drawBitmap(sprite, x, y, paint);
    }

    public DragonBitmapSet(Resources res) {
        paint = new Paint();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        Bitmap bitmapsBMP = BitmapFactory.decodeResource(res, R.raw.blackdragonpeke, opts);
        Matrix rot1 = new Matrix();
        Matrix rot2 = new Matrix();
        rot2.setScale(-1, 1);
        bitmaps = new Bitmap[sheetInfo.length];
        for (int i = 0; i < sheetInfo.length; i++) {
            int x = sheetInfo[i][0];
            int y = sheetInfo[i][1];
            int w = sheetInfo[i][2];
            int h = sheetInfo[i][3];
            boolean mustRotate = (sheetInfo[i][4] == 1);
            bitmaps[i] = Bitmap.createBitmap(bitmapsBMP, x, y, w, h,
                    mustRotate?rot2:rot1, true);
        }
        bitmapsBMP.recycle();
    }
}
