package cat.flx.sprite;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import static android.R.drawable.ic_media_ff;
import static android.R.drawable.ic_media_next;
import static android.R.drawable.ic_media_pause;
import static android.R.drawable.ic_media_play;
import static android.R.drawable.ic_media_rew;

public class MainActivity extends AppCompatActivity {
    Game game;
    ImageButton pause;
    ImageButton speed;
    ImageButton restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_main);
        final GameView gameView = (GameView) findViewById(R.id.view);
        pause = (ImageButton) findViewById(R.id.pause);
        speed = (ImageButton) findViewById(R.id.speed);
        restart = (ImageButton) findViewById(R.id.imageButton);
        gameView.setPauseButton(pause);
        gameView.setSpeedButton(speed);
        gameView.setRestart(restart);
        restart.setVisibility(View.INVISIBLE);
        game = new Game(this);
        gameView.setGame(game);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameView.isPaused()){
                    gameView.setPaused(false);
                    pause.setBackgroundResource(ic_media_pause);
                }else {
                    gameView.setPaused(true);
                    pause.setBackgroundResource(ic_media_play);
                }
            }
        });
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameView.getSpeed() == 50) {
                    gameView.setSpeed(10);
                    speed.setBackgroundResource(ic_media_rew);
                }else{
                    gameView.setSpeed(50);
                    speed.setBackgroundResource(ic_media_ff);
                }
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.Start();
                restart.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override public void onResume() {
        super.onResume();
        game.getAudio().startMusic();
    }

    @Override public void onPause() {
        game.getAudio().stopMusic();
        super.onPause();
    }

    @Override public boolean dispatchKeyEvent(KeyEvent event) {
        boolean down = (event.getAction() == KeyEvent.ACTION_DOWN);
        switch(event.getKeyCode()) {
            case KeyEvent.KEYCODE_Z:
                game.keyLeft(down); break;
            case KeyEvent.KEYCODE_X:
                game.keyRight(down); break;
            case KeyEvent.KEYCODE_M:
                game.keyJump(down); break;
        }
        return true;
    }
}
