package cat.flx.sprite;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by Billy on 20/04/2017.
 */

public class SpearBitmapSet {
    private Bitmap[] bitmaps;

    private int[][] sheetInfo = {
            { 0, 0, 32, 26, 0 },	//  1
            { 32, 0, 32, 26, 0 },	//  2
            { 64, 0, 32, 26, 0 },	//  3
            { 96, 0, 32, 26, 0 },	//  4
            { 128, 0, 32, 26, 0 },	//  5
            { 160, 0, 32, 26, 0 },	//  6
            { 192, 0, 32, 26, 0 },	//  7
            { 224, 0, 32, 26, 0 },	//  8
            { 0, 32, 32, 26, 0 },	//  9
            { 32, 32, 32, 26, 0 },	//  10
            { 64, 32, 32, 26, 0 },	//  11
            { 96, 32, 32, 26, 0 },	//  12
            { 128, 32, 32, 26, 0 },	//  13
            { 160, 32, 32, 26, 0 },	//  14
            { 192, 32, 32, 26, 0 },	//  15
            { 224, 32, 32, 26, 0 },	//  16
            { 0, 64, 32, 26, 0 },	//  17
            { 32, 64, 32, 26, 0 },	//  18
            { 64, 64, 32, 26, 0 },	//  19
            { 96, 64, 32, 26, 0 },	//  20
            { 128, 64, 32, 26, 0 },	//  21
            { 160, 64, 32, 26, 0 },	//  22
            { 192, 64, 32, 26, 0 },	//  23
            { 224, 64, 32, 26, 0 },	//  24
            { 0, 96, 32, 26, 0 },	//  25
            { 32, 96, 32, 26, 0 },	//  26
            { 64, 96, 32, 26, 0 },	//  27
            { 96, 96, 32, 26, 0 },	//  28
            { 128, 96, 32, 26, 0 },	//  29
            { 160, 96, 32, 26, 0 },	//  30
            { 192, 96, 32, 26, 0 },	//  31
            { 224, 96, 32, 26, 0 },	//  32
            { 0, 128, 32, 26, 0 },	//  33
            { 32, 128, 32, 26, 0 },	//  34
            { 64, 128, 32, 26, 0 },	//  35
            { 96, 128, 32, 26, 0 },	//  36
            { 128, 128, 32, 26, 0 },//  37
            { 160, 128, 32, 26, 0 },//  38
            { 192, 128, 32, 26, 0 },//  39
            { 224, 128, 32, 26, 0 },//  40
            { 0, 160, 32, 26, 0 },	//  41
            { 32, 160, 32, 26, 0 },	//  42
            { 64, 160, 32, 26, 0 },	//  43
            { 96, 160, 32, 26, 0 },	//  44
            { 128, 160, 32, 26, 0 },	//  45
            { 160, 160, 32, 26, 0 },	//  46
            { 192, 160, 32, 26, 0 },	//  47
            { 224, 160, 32, 26, 0 },	//  48
            { 0, 192, 32, 26, 0 },	//  49
            { 32, 192, 32, 26, 0 },	//  50
            { 64, 192, 32, 26, 0 },	//  51
            { 96, 192, 32, 26, 0 },	//  52
    };

    public Bitmap getBitmap(int i) { return bitmaps[i]; }
    private Paint paint;
    void drawBitmap(Canvas canvas, int x, int y, int i) {
        Bitmap sprite = getBitmap(i);
        canvas.drawBitmap(sprite, x, y, paint);
    }
    public SpearBitmapSet(Resources res) {
        paint = new Paint();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        Bitmap bitmapsBMP = BitmapFactory.decodeResource(res, R.raw.spearicons, opts);
        Matrix rot1 = new Matrix();
        rot1.postRotate(130.0f);
        bitmaps = new Bitmap[sheetInfo.length];
        for (int i = 0; i < sheetInfo.length; i++) {
            int x = sheetInfo[i][0];
            int y = sheetInfo[i][1];
            int w = sheetInfo[i][2];
            int h = sheetInfo[i][3];
            bitmaps[i] = Bitmap.createBitmap(bitmapsBMP, x, y, w, h,rot1, true);
        }
        bitmapsBMP.recycle();
    }
}
