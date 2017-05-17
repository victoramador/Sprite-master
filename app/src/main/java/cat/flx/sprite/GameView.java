package cat.flx.sprite;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


class GameView extends View {

    Game game;
    private int speed = 50;
    private ImageButton pauseButton, speedButton,restart;
    private boolean paused = false;


    public GameView(Context context) { this(context, null, 0); }
    public GameView(Context context, AttributeSet attrs) { this(context, attrs, 0); }
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        }


    void setGame(Game game) { this.game = game; }

    @Override public void onMeasure(int widthSpec, int heightSpec) {
        int w = MeasureSpec.getSize(widthSpec);
        int h = MeasureSpec.getSize(heightSpec);
        setMeasuredDimension(w, h);
    }

    private boolean locked = false;
    @Override public void onDraw(Canvas canvas) {
        this.postInvalidateDelayed(speed);
        if (!paused) {
            if (locked) return;
            locked = true;
            if (game == null) return;
            game.events();
            game.physics();
            game.draw(canvas);
            locked = false;
            if (game.getBonk().state == 3)
                restart.setVisibility(VISIBLE);
        }else{
            game.draw(canvas);
        }
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        if (!paused) {
            int act = event.getActionMasked();
            int i = event.getActionIndex();
            boolean down = (act != MotionEvent.ACTION_UP) &&
                    (act != MotionEvent.ACTION_POINTER_UP) &&
                    (act != MotionEvent.ACTION_CANCEL);
            int x = (int) (event.getX(i)) * 100 / getWidth();
            int y = (int) (event.getY(i)) * 100 / getHeight();
            if (y < 75) {         // VERTICAL DEAD-ZONE
                game.left(false);
                game.right(false);
            } else if (x < 17) {    // LEFT
                if (down) game.right(false);
                game.left(down);
            } else if (x < 33) {  // RIGHT
                if (down) game.left(false);
                game.right(down);
            } else if (x < 83) {  // DEAD-ZONE
                game.left(false);
                game.right(false);
            } else if (x > 83) {  // JUMP
                game.jump(down);
            }
        }
        return true;
    }

    public ImageButton getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(ImageButton pauseButton) {
        this.pauseButton = pauseButton;
    }

    public ImageButton getSpeedButton() {
        return speedButton;
    }

    public void setSpeedButton(ImageButton speedButton) {
        this.speedButton = speedButton;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public ImageButton getRestart() {
        return restart;
    }

    public void setRestart(ImageButton restart) {
        this.restart = restart;
    }
}
