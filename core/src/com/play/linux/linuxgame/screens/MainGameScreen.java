package com.play.linux.linuxgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.play.linux.linuxgame.LinuxGame;
import com.play.linux.linuxgame.entities.Asteroid;
import com.play.linux.linuxgame.tools.CollisionRect;
import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa MainGameScreen implementująca interfejs Screen.
 */
public class MainGameScreen implements Screen {

    public static final float SPEED = 300;
    public static final float MIN_ASTEROID_SPAWN_TIME_LVL_1_2 = 0.3f;
    public static final float MIN_ASTEROID_SPAWN_TIME_3 = 0.2f;
    public static final float MAX_ASTEROID_SPAWN_TIME_LVL_1 = 0.6f;
    public static final float MAX_ASTEROID_SPAWN_TIME_LVL_2 = 0.5f;
    public static final float MAX_ASTEROID_SPAWN_TIME_LVL_3 = 0.4f;
    public static final int TUX_WIDTH_PIXEL = 140;
    public static final int TUX_HEIGHT_PIXEL = 140;
    public static final int TOP_BORDER = 350;
    float x;
    float y;
    Texture img;
    LinuxGame game;
    float asteroidSpawnTimer;

    private float timer = 7;
    private BitmapFont font;

    Random random;

    ArrayList<Asteroid> asteroids;

    Texture blank;
    BitmapFont timeFont;
    CollisionRect playerRect;

    float health = 1; //0 means game over=showing finish game screen | 1 hit=1 live less

    /**
     * Konstruktor klasy MainGameScreen.
     * @param game Referencja do obiektu gry.
     */
    public MainGameScreen(LinuxGame game){
        this.game = game;
        x = LinuxGame.WIDTH/2;
        y = 15;
        timeFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        asteroids = new ArrayList<Asteroid>();

        playerRect = new CollisionRect(0, 0, TUX_WIDTH_PIXEL, TUX_HEIGHT_PIXEL);
        blank = new Texture("blank.png");
        random = new Random();
        font = new BitmapFont();
        if (LinuxGame.level == 1) {
            asteroidSpawnTimer =
                    random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME_LVL_1 - MIN_ASTEROID_SPAWN_TIME_LVL_1_2) + MIN_ASTEROID_SPAWN_TIME_LVL_1_2;
        } else if (LinuxGame.level == 2) {
            asteroidSpawnTimer =
                    random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME_LVL_2 - MIN_ASTEROID_SPAWN_TIME_LVL_1_2) + MIN_ASTEROID_SPAWN_TIME_LVL_1_2;
        } else if (LinuxGame.level == 3 || LinuxGame.level == 4) {
            asteroidSpawnTimer =
                    random.nextFloat() * (MAX_ASTEROID_SPAWN_TIME_LVL_3 - MIN_ASTEROID_SPAWN_TIME_3) + MIN_ASTEROID_SPAWN_TIME_3;
        }
    }

    /**
     * Metoda wywoływana, gdy ekran jest wyświetlany.
     */
    @Override
    public void show() {
        img = new Texture("Tux.png");
    }

    /**
     * Metoda tworząca obraz głównego ekranu gry.
     * @param delta Czas pomiędzy bieżącą a poprzednią klatką.
     */
    @Override
    public void render(float delta) {

        //Update asteroids
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();
        for(Asteroid asteroid : asteroids) {
            asteroid.update(delta);
            if (asteroid.remove){
                asteroidsToRemove.add(asteroid);
            }
        }

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

        //After player moves, update collision rect
        playerRect.move(x, y);

        for(Asteroid asteroid : asteroids) {
            if (asteroid.getCollisionRect().collidesWith(playerRect)) {
                asteroidsToRemove.add(asteroid);
                health -= 0.5f;

                // if health is depleted, go to game over screen
                if(health <= 0) {
                    this.dispose();
                    game.setScreen(new GameOverScreen(game));
                    return;
                }
            }
        }
        asteroids.removeAll(asteroidsToRemove);

        ScreenUtils.clear(0.3f, 0.3f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        // Timer
        timer -= Gdx.graphics.getDeltaTime();
        if (timer < 0) {
            timer = 0; // Ensure the timer doesn't go below 0
        }


        if (timer <= 0) {
            game.setScreen(new QuestionScreen(game));
            if (LinuxGame.level == 4) {
                game.setScreen(new GameOverScreen(game)); // add score!!
            }
        }

        // Asteroid spawn code
        asteroidSpawnTimer -= delta;

        if (LinuxGame.level == 1) {
            if (asteroidSpawnTimer <= 0){
                asteroidSpawnTimer =
                        random.nextFloat() * ((MAX_ASTEROID_SPAWN_TIME_LVL_1 - MIN_ASTEROID_SPAWN_TIME_LVL_1_2) + MIN_ASTEROID_SPAWN_TIME_LVL_1_2);
                asteroids.add(new Asteroid(random.nextInt(Gdx.graphics.getWidth() - Asteroid.WIDTH)));
            }
        } else if (LinuxGame.level == 2) {
            if (asteroidSpawnTimer <= 0){
                asteroidSpawnTimer =
                        random.nextFloat() * ((MAX_ASTEROID_SPAWN_TIME_LVL_2 - MIN_ASTEROID_SPAWN_TIME_LVL_1_2) + MIN_ASTEROID_SPAWN_TIME_LVL_1_2);
                asteroids.add(new Asteroid(random.nextInt(Gdx.graphics.getWidth() - Asteroid.WIDTH)));
            }
        } else if (LinuxGame.level == 3 || LinuxGame.level == 4) {
            if (asteroidSpawnTimer <= 0){
                asteroidSpawnTimer =
                        random.nextFloat() * ((MAX_ASTEROID_SPAWN_TIME_LVL_3 - MIN_ASTEROID_SPAWN_TIME_3) + MIN_ASTEROID_SPAWN_TIME_3);
                asteroids.add(new Asteroid(random.nextInt(Gdx.graphics.getWidth() - Asteroid.WIDTH)));
            }
        }

        GlyphLayout healthLayout = new GlyphLayout(timeFont, "" + ((int)timer + 1));
        timeFont.draw(game.batch, healthLayout, Gdx.graphics.getWidth() / 2 - healthLayout.width / 2,
                Gdx.graphics.getHeight() - healthLayout.height - 10);

        for (Asteroid asteroid : asteroids) {
            asteroid.render(game.batch);
        }

        game.batch.draw(blank, 0, 0, Gdx.graphics.getWidth() * health, 12);
        game.batch.draw(img, x, y);
        game.batch.end();
    }

    /**
     * Metoda wywoływana, gdy rozmiar okna gry ulega zmianie.
     * @param width  Nowa szerokość okna.
     * @param height Nowa wysokość okna.
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Metoda wywoływana, gdy gra zostaje zawieszona (np. przez wejście w tryb pauzy).
     * W tym przypadku metoda jest pusta, ponieważ gra nie obsługuje funkcji pauzy.
     */
    @Override
    public void pause() {

    }

    /**
     * Metoda wywoływana, gdy gra zostaje wznowiona po zatrzymaniu.
     * W tym przypadku metoda jest pusta, ponieważ gra nie obsługuje funkcji pauzy.
     */
    @Override
    public void resume() {

    }

    /**
     * Metoda wywoływana, gdy aktualny ekran gry zostaje ukryty (np. przejście do innego ekranu).
     * W tym przypadku metoda jest pusta, ponieważ brak dodatkowej obsługi ukrywania ekranu.
     */
    @Override
    public void hide() {

    }

    /**
     * Metoda wywoływana przed zniszczeniem obiektu klasy (np. zamykaniem gry).
     * W tym przypadku metoda jest pusta, ponieważ brak dodatkowej obsługi zwalniania zasobów.
     */
    @Override
    public void dispose() {

    }
}
