package com.play.linux.linuxgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.play.linux.linuxgame.LinuxGame;
import com.play.linux.linuxgame.entities.Asteroid;
import com.play.linux.linuxgame.entities.Bullet;
import com.play.linux.linuxgame.tools.CollisionRect;

import java.util.ArrayList;
import java.util.Random;

public class MainGameScreen implements Screen {

    public static final float SPEED = 300;
    public static final float MIN_ASTEROID_SPAWN_TIME = 0.1f;
    public static final float MAX_ASTEROID_SPAWN_TIME = 0.4f;
    public static final int TUX_WIDTH_PIXEL = 140;
    public static final int TUX_HEIGHT_PIXEL = 140;
    public static final int TOP_BORDER = 350;
    float x = LinuxGame.WIDTH/2;
    float y;
    Texture img;
    LinuxGame game;
    float asteroidSpawnTimer;

    Random random;

    ArrayList<Bullet> bullets;
    ArrayList<Asteroid> asteroids;

    Texture blank;
    BitmapFont livesFont;
    CollisionRect playerRect;

    float lives = 3; //0 means game over=showing finish game screen | 1 hit=1 live less

    public MainGameScreen(LinuxGame game){
        this.game = game;
        bullets = new ArrayList<Bullet>();
        asteroids = new ArrayList<Asteroid>();
        livesFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

        lives = 3; //0 means game over=showing finish game screen | 1 hit=1 live less

        playerRect = new CollisionRect(0, 0, TUX_WIDTH_PIXEL, TUX_HEIGHT_PIXEL);
        blank = new Texture("blank.png");
        random = new Random();

        asteroidSpawnTimer =
                random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME;
    }
    @Override
    public void show() {
        img = new Texture("Tux.png");
    }

    @Override
    public void render(float delta) {

        // Asteroid spawn code
        asteroidSpawnTimer -= delta;
        if (asteroidSpawnTimer <= 0){
            asteroidSpawnTimer =
                    random.nextFloat() * ((MAX_ASTEROID_SPAWN_TIME - MIN_ASTEROID_SPAWN_TIME) + MIN_ASTEROID_SPAWN_TIME);
            asteroids.add(new Asteroid(random.nextInt(Gdx.graphics.getWidth() - Asteroid.WIDTH)));
        }

        //Update asteroids
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();
        for(Asteroid asteroid : asteroids) {
            asteroid.update(delta);
            if (asteroid.remove){
                asteroidsToRemove.add(asteroid);
            }
        }
        asteroids.removeAll(asteroidsToRemove);

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            y += SPEED * Gdx.graphics.getDeltaTime();
            if (y + TOP_BORDER > Gdx.graphics.getHeight()){
                y = Gdx.graphics.getHeight() - TOP_BORDER;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            y -= SPEED * Gdx.graphics.getDeltaTime();
            if (y < 0) {
                y = 0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            x += SPEED * Gdx.graphics.getDeltaTime();
            if (x + TUX_WIDTH_PIXEL > Gdx.graphics.getWidth()){
                x = Gdx.graphics.getWidth() - TUX_WIDTH_PIXEL;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            x -= SPEED * Gdx.graphics.getDeltaTime();

            if (x < 0){
                x = 0;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            bullets.add(new Bullet(x + 2, 0));
            bullets.add(new Bullet(x + TUX_WIDTH_PIXEL - 4, 0));
        }
        ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        for(Bullet bullet : bullets){
            bullet.update(delta);
            if (bullet.remove){
                bulletsToRemove.add(bullet);
            }
        }
        //After player moves, update collision rect
        playerRect.move(x, y);

        for(Asteroid asteroid : asteroids) {
            if (asteroid.getCollisionRect().collidesWith(playerRect)) {
                asteroidsToRemove.add(asteroid);
                lives -= 1;
            }
        }

        ScreenUtils.clear(0.3f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        GlyphLayout livesLayout = new GlyphLayout(livesFont, "" + lives);
        livesFont.draw(game.batch, livesLayout, Gdx.graphics.getWidth() / 2 - livesLayout.width / 2,
                Gdx.graphics.getHeight() - livesLayout.height - 10);

        for(Bullet bullet : bullets){
            bullet.render(game.batch);
        }

        for (Asteroid asteroid : asteroids) {
            asteroid.render(game.batch);
        }

        game.batch.draw(blank, 0, 0, Gdx.graphics.getWidth() * lives, 10);
        game.batch.draw(img, x, y);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
