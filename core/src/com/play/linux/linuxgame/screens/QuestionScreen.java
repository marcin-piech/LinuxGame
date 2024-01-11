package com.play.linux.linuxgame.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.play.linux.linuxgame.LinuxGame;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Klasa QuestionScreen implementująca interfejs Screen.
 * Ekran zawierający pytania oraz odpowiedzi dla gracza.
 */
public class QuestionScreen implements Screen {
    LinuxGame game;
    BitmapFont questionFont;
    public static String[] questions = new String[3];
    public static String[] answers = new String[6];
    public static int questionNumber = 0;
    public static int answer1 = 0;
    public static int answer2 = 1;

    /**
     * Konstruktor klasy QuestionScreen.
     * @param game Referencja do obiektu gry.
     */
    public QuestionScreen(LinuxGame game) {
        this.game = game;
        questions[0] = "Command that lists Files with \nDetailed Information \nand Hidden Files";
        questions[1] = "To set permissions \n-rw-rwx---\n you need to type in";
        questions[2] = "Which month was the\n shortest in history";

        answers[0] = "ls -lh";
        answers[1] = "ls -la";
        answers[2] = "chmod 670 file_name";
        answers[3] = "chmod 230 file_name";
        answers[4] = "February";
        answers[5] = "October";

        questionFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));

    }

    /**
     * Metoda wywoływana, gdy ekran staje się aktywny i jest przygotowany do wyświetlenia.
     * Tutaj można umieścić kod inicjalizacyjny zasobów i operacji przygotowujących ekran do wyświetlenia.
     */
    @Override
    public void show() {

    }

    /**
     * Metoda renderująca ekran z pytaniami i odpowiedziami.
     * @param delta Czas pomiędzy bieżącą a poprzednią klatką.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        GlyphLayout questionLayout = new GlyphLayout(questionFont,
                "Choose the correct answer: \n\n" + questions[questionNumber],
                Color.WHITE
                , 0,
                Align.left, false);
        questionFont.draw(game.batch, questionLayout, Gdx.graphics.getWidth() / 2 - questionLayout.width / 2,
                Gdx.graphics.getHeight() - 30);

        GlyphLayout answer1Layout = new GlyphLayout(questionFont, answers[answer1]);
        GlyphLayout answer2Layout = new GlyphLayout(questionFont, answers[answer2]);
        GlyphLayout zadneLayout = new GlyphLayout(questionFont, "NONE");

        float answer1X = Gdx.graphics.getWidth() / 2 - answer1Layout.width / 2;
        float answer1Y = Gdx.graphics.getHeight() / 2 - answer1Layout.height / 2 - 100;
        float answer2X = Gdx.graphics.getWidth() / 2 - answer2Layout.width / 2;
        float answer2Y = Gdx.graphics.getHeight() / 2 - answer2Layout.height / 2 - answer1Layout.height - 130;
        float zadneX = Gdx.graphics.getWidth() / 2 - zadneLayout.width / 2 ;
        float zadneY = Gdx.graphics.getHeight() / 2 - zadneLayout.height / 2 - answer2Layout.height - 190;

        float touchX = Gdx.input.getX(), touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

        //if try again and main menu is being pressed
        if(Gdx.input.isTouched()) {
            if (touchX > answer1X && touchX < answer1X + answer1Layout.width && touchY > answer1Y - answer1Layout.height && touchY < answer1Y) {
                if (LinuxGame.level == 2){
                    LinuxGame.score += 1;
                }
                this.dispose();
                game.batch.end();
                game.setScreen(new MainGameScreen(game));
                questionNumber += 1;
                answer1 += 2;
                answer2 += 2;
                LinuxGame.level += 1;

                System.out.println(LinuxGame.score);
                System.out.println(LinuxGame.level);
                return;
            }

            if (touchX > answer2X && touchX < answer2X + answer2Layout.width && touchY > answer2Y - answer2Layout.height && touchY < answer2Y) {
                if (LinuxGame.level == 1){
                    LinuxGame.score += 1;
                }
                this.dispose();
                game.batch.end();
                game.setScreen(new MainGameScreen(game));
                questionNumber += 1;
                answer1 += 2;
                answer2 += 2;
                LinuxGame.level += 1;
                System.out.println(LinuxGame.score);
                System.out.println(LinuxGame.level);
                return;
            }

            if (touchX > zadneX && touchX < zadneX + zadneLayout.width && touchY > zadneY - zadneLayout.height && touchY < zadneY) {
                if (LinuxGame.level == 3){
                    LinuxGame.score += 1;
                }
                this.dispose();
                game.batch.end();
                game.setScreen(new MainGameScreen(game));
                questionNumber += 1;
                answer1 += 2;
                answer2 += 2;
                LinuxGame.level += 1;
                System.out.println(LinuxGame.score);
                System.out.println(LinuxGame.level);
                return;
            }
        }

        //Draw buttons
        questionFont.draw(game.batch, answer1Layout, answer1X, answer1Y);
        questionFont.draw(game.batch, answer2Layout, answer2X, answer2Y);
        questionFont.draw(game.batch, zadneLayout, zadneX, zadneY);

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