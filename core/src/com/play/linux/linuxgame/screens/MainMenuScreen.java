package com.play.linux.linuxgame.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.play.linux.linuxgame.LinuxGame;

/**
 * Klasa MainMenuScreen implementujaca klase Screen.
 */
public class MainMenuScreen implements Screen{

    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_BUTTON_Y = 300;

    LinuxGame game;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;

    /**
     * Konstruktor klasy MainMenuScreen.
     * @param game Referencja do obiektu gry.
     */
    public MainMenuScreen (LinuxGame game) {
        this.game = game;
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
    }
    @Override
    public void show() {

    }

    /**
     * Metoda tworząca obraz glownego menu.
     * @param delta Czas pomiędzy bieżącą a poprzednią klatką.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        int x = LinuxGame.WIDTH/2 - EXIT_BUTTON_WIDTH/2;
        if (Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && LinuxGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && LinuxGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y){
            game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y,EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
        if (Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && LinuxGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && LinuxGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y){
            game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y,PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()){
                game.setScreen(new MainGameScreen(game));
            }
        } else {
            game.batch.draw(playButtonInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
        game.batch.end();
    }

    /**
     * Metoda wywoływana, gdy rozmiar ekranu ulega zmianie.
     * @param width  Nowa szerokość ekranu.
     * @param height Nowa wysokość ekranu.
     */
    @Override
    public void resize(int width, int height) {
        // Kod obsługi zmiany rozmiaru ekranu
    }

    /**
     * Metoda wywoływana, gdy gra zostaje zatrzymana (np. przez wejście w tryb pauzy).
     */
    @Override
    public void pause() {
        // Kod obsługi zatrzymania gry
    }

    /**
     * Metoda wywoływana, gdy gra zostaje wznowiona po zatrzymaniu.
     */
    @Override
    public void resume() {
        // Kod obsługi wznowienia gry
    }

    /**
     * Metoda wywoływana, gdy aktualny ekran gry zostaje ukryty (np. przejście do innego ekranu).
     */
    @Override
    public void hide() {
        // Kod obsługi ukrycia aktualnego ekranu
    }

    /**
     * Metoda wywoływana przed zniszczeniem obiektu klasy (np. zamykaniem gry).
     */
    @Override
    public void dispose() {
        // Kod obsługi zwalniania zasobów przed zniszczeniem obiektu
    }
}