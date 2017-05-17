package cat.flx.sprite;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

class Game {
    private Context context;

    private BitmapSet bitmapSet;
    private SpearBitmapSet spearBitmapSet;
    private DragonBitmapSet dragonBitmapSet;
    private Scene scene;
    private Bonk bonk;
    private Audio audio;
    private List<Coin> coins;
    private List<Enemy> enemies;
    private List<Spear> spears;
    private int screenOffsetX, screenOffsetY;
    private int spearCounter = 0;
    private int enemyCounter = 0;

    Game(Activity activity) {
        this.context = activity;
        audio = new Audio(activity);
        Start();
    }
    void Start(){
        bitmapSet = new BitmapSet(context.getResources());
        spearBitmapSet = new SpearBitmapSet(context.getResources());
        dragonBitmapSet = new DragonBitmapSet(context.getResources());
        scene = new Scene(this);
        bonk = new Bonk(this);
        coins = new ArrayList<>();
        enemies = new ArrayList<>();
        spears = new ArrayList<>();
        scene.loadFromFile(R.raw.scene);
        bonk.x = 16 * 10;
        bonk.y = 0;
    }
    Context getContext() { return context; }
    Resources getResources() { return context.getResources(); }

    BitmapSet getBitmapSet() { return bitmapSet; }
    SpearBitmapSet getSpearBitmapSet() {return spearBitmapSet; }
    DragonBitmapSet getDragonBitmapSet() {return dragonBitmapSet; }
    Scene getScene() { return scene; }
    Audio getAudio() { return audio; }
    Bonk getBonk() { return bonk; }

    void addCoin(Coin coin) {
        coins.add(coin);
    }
    List<Coin> getCoins() { return coins; }

    void physics() {
        bonk.physics();
        enemyCounter++;
        spearCounter ++;
        for(Enemy enemy : enemies) {
            enemy.physics();
            if (bonk.state == 3) continue;
            if (enemy.getCollisionRect().intersect(bonk.getCollisionRect())) {
                    audio.die();
                    bonk.state = 3;
            }
        }
        for (Spear spear : spears){
            spear.physics();
            if (spear.getCollisionRect().intersect(bonk.getCollisionRect())){
                audio.die();
                bonk.state = 3;
            }
        }
        for(Coin coin : coins) {
            coin.physics();
            if (coin.getCollisionRect().intersect(bonk.getCollisionRect())) {
                audio.coin();
                coin.x = -1000;
                coin.y = -1000;
            }
        }
    }

    private float sc;
    private int scX, scY;

    void draw(Canvas canvas) {
        if (canvas.getWidth() == 0) return;
        if (sc == 0) {
            scY = 16 * 16;
            sc = canvas.getHeight() / (float) scY;
            scX = (int) (canvas.getWidth() / sc);
        }
        screenOffsetX = Math.min(screenOffsetX, bonk.x - 100);
        screenOffsetX = Math.max(screenOffsetX, bonk.x - scX + 100);
        screenOffsetX = Math.max(screenOffsetX, 0);
        screenOffsetX = Math.min(screenOffsetX, scene.getWidth() - scX - 1);
        screenOffsetY = Math.min(screenOffsetY, bonk.y - 50);
        screenOffsetY = Math.max(screenOffsetY, bonk.y - scY + 75);
        screenOffsetY = Math.max(screenOffsetY, 0);
        screenOffsetY = Math.min(screenOffsetY, scene.getHeight() - scY);
        canvas.scale(sc, sc);
        canvas.translate(-screenOffsetX, -screenOffsetY);
        scene.draw(canvas);
        bonk.draw(canvas);

        if (enemyCounter > 500){
            enemies.add(new Enemy(this));
            enemyCounter = 0;
        }

        for(Enemy enemy : enemies) {
            enemy.draw(canvas);
        }
        for(Coin coin : coins) {
            coin.draw(canvas);
        }
        if (spearCounter > 100){
            spears.add(new Spear(this));
            spearCounter = 0;
        }

        for(Spear spear : spears) {
            spear.draw(canvas);
        }
    }

    private int keyCounter = 0;
    private boolean keyLeft, keyRight, keyJump;
    void keyLeft(boolean down) { keyCounter = 0; if (down) keyLeft = true; }
    void keyRight(boolean down) { keyCounter = 0; if (down) keyRight = true; }
    void keyJump(boolean down) { keyCounter = 0; if (down) keyJump = true; }

    private boolean left, right, jump;
    void left(boolean down) {
        if (left && !down) left = false;
        else if (!left && down) left = true;
    }
    void right(boolean down) {
        if (right && !down) right = false;
        else if (!right && down) right = true;
    }
    void jump(boolean down) {
        if (jump && !down) jump = false;
        else if (!jump && down) jump = true;
    }

    void events() {
        if (++keyCounter > 2) {
            keyCounter = 0;
            keyLeft = keyRight = keyJump = false;
        }
        if (keyLeft || left) { bonk.left(); }
        if (keyRight || right) { bonk.right(); }
        if (keyJump || jump) { bonk.jump(); }
    }
}
