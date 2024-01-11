package com.play.linux.linuxgame.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.play.linux.linuxgame.LinuxGame;

/**
 * Klasa GameOverScreen implementująca interfejs Screen.
 * Ekran wyświetlający się po zakończeniu gry, przedstawiający wynik gracza oraz dostarczający opcji "Try Again" i "Main Menu".
 */
public class GameOverScreen implements Screen {

    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 100;

    LinuxGame game;
    String message;

    Texture gameOverBanner;
    BitmapFont scoreFont;

    /**
     * Konstruktor klasy GameOverScreen.
     * @param game Referencja do obiektu gry.
     */
    public GameOverScreen(LinuxGame game) {
        this.game = game;


        Preferences prefs = Gdx.app.getPreferences("linuxgame");
        gameOverBanner = new Texture("game_over.png");
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
    }

    @Override
    public void show() {

    }

    /**
     * Metoda renderująca obraz ekranu Game Over.
     * @param delta Czas pomiędzy bieżącą a poprzednią klatką.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();


        if (LinuxGame.score == 0) {
            message = "You need to \nfocus on learning Linux.";
        } else if (LinuxGame.score == 1){
            message = "You could have \ndone it better.";
        } else if (LinuxGame.score == 2){
            message = "Quite good. \nKeep going.";
        } else if (LinuxGame.score == 3){
            message = "You are MASTER!";
        }

        game.batch.draw(gameOverBanner, Gdx.graphics.getWidth() / 2 - BANNER_WIDTH / 2,
                Gdx.graphics.getHeight() - BANNER_HEIGHT - 15, BANNER_WIDTH, BANNER_HEIGHT);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: \n" + LinuxGame.score, Color.WHITE, 0, Align.left,
                false);

        GlyphLayout highscoreLayout = new GlyphLayout(scoreFont, "Output: \n" + message, Color.WHITE, 0, Align.left,
                false);
        scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2,
                Gdx.graphics.getHeight() - BANNER_HEIGHT - 15 * 2);
        scoreFont.draw(game.batch, highscoreLayout, Gdx.graphics.getWidth() / 2 - highscoreLayout.width / 2,
                Gdx.graphics.getHeight() - BANNER_HEIGHT - scoreLayout.height - 15 * 3);

        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "Try Again");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Main Menu");

        float tryAgainX = Gdx.graphics.getWidth() / 2 - tryAgainLayout.width / 2;
        float tryAgainY = Gdx.graphics.getHeight() / 2 - tryAgainLayout.height / 2;
        float mainMenuX = Gdx.graphics.getWidth() / 2 - mainMenuLayout.width / 2;
        float mainMenuY = Gdx.graphics.getHeight() / 2 - mainMenuLayout.height / 2 - tryAgainLayout.height - 15;

        float touchX = Gdx.input.getX(), touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

        //if try again and main menu is being pressed
        if(Gdx.input.isTouched()) {
            if (touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
                this.dispose();
                game.batch.end();
                QuestionScreen.questionNumber = 0;
                QuestionScreen.answer1 = 0;
                QuestionScreen.answer2 = 1;
                LinuxGame.score = 0;
                LinuxGame.level = 1;
                Timer.schedule(new Timer.Task() {
                    /**
                     * Implementacja interfejsu java.lang.Runnable, która jest używana do zdefiniowania kodu, który
                     * ma być wykonany w osobnym wątku w
                     * określonym czasie
                     */
                    @Override
                    public void run() {
                        game.setScreen(new MainGameScreen(game));
                    }
                }, 0.5f);
                return;
            }

            if (touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY) {
                this.dispose();
                game.batch.end();
                QuestionScreen.questionNumber = 0;
                QuestionScreen.answer1 = 0;
                QuestionScreen.answer2 = 1;
                LinuxGame.score = 0;
                LinuxGame.level = 1;
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.setScreen(new MainMenuScreen(game));
                    }
                }, 0.5f);
                return;
            }
        }
        //Draw buttons
        scoreFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY);
        scoreFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY);

        game.batch.end();
    }

    /**
     * Metoda wywoływana, gdy rozmiar ekranu ulega zmianie.
     * @param i  Nowa szerokość ekranu.
     * @param i1 Nowa wysokość ekranu.
     */
    @Override
    public void resize(int i, int i1) {

    }

    /**
     * Metoda wywoływana, gdy gra zostaje zatrzymana (np. przez wejście w tryb pauzy).
     */
    @Override
    public void pause() {

    }

    /**
     * Metoda wywoływana, gdy gra zostaje wznowiona po zatrzymaniu.
     */
    @Override
    public void resume() {

    }

    /**
     * Metoda wywoływana, gdy aktualny ekran gry zostaje ukryty (np. przejście do innego ekranu).
     */
    @Override
    public void hide() {

    }

    /**
     * Metoda wywoływana przed zniszczeniem obiektu klasy (np. zamykaniem gry).
     */
    @Override
    public void dispose() {

    }
}