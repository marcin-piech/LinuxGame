package com.play.linux.linuxgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.play.linux.linuxgame.tools.CollisionRect;

/**
 * Klasa Asteroid reprezentuje obiekty asteroidy (w kontekście gry, są to śnieżki).
 * Odpowiada za ich ruch, aktualizację logiki gry oraz renderowanie.
 */
public class Asteroid {
    public static final int SPEED = 250;
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    private static Texture texture;
    float x, y;
    CollisionRect rect;
    public boolean remove = false;

    /**
     * Konstruktor klasy Asteroid.
     * Tworzy obiekt asteroidy (śnieżki) na podanej pozycji x, a y ustawia na górnej krawędzi ekranu.
     * @param x Pozycja x początkowej lokalizacji asteroidy.
     */
    public Asteroid(float x){
        this.x = x;
        this.y = Gdx.graphics.getHeight();
        this.rect = new CollisionRect(x, y, WIDTH, HEIGHT);

        if (texture == null) {
            texture = new Texture("asteroid.png");
        }
    }

    /**
     * Metoda aktualizująca logikę asteroidy w każdej klatce.
     * @param deltaTime Czas w sekundach między bieżącą klatką a poprzednią.
     */
    public void update (float deltaTime) {
        y -= SPEED * deltaTime;
        if (y < -HEIGHT)
            remove = true;

        rect.move(x, y);
    }

    /**
     * Metoda renderująca asteroidę, czyli rysująca jej teksturę.
     * @param batch Obiekt SpriteBatch do rysowania.
     */
    public void render (SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    /**
     * Metoda zwracająca obiekt CollisionRect, reprezentujący obszar kolizji asteroidy.
     * @return Obiekt rect reprezentujący obszar kolizji asteroidy.
     */
    public CollisionRect getCollisionRect() {
        return rect;
    }

    /**
     * Metoda zwracająca współrzędną x asteroidy.
     * @return Współrzędna x asteroidy.
     */
    public float getX() {
        return x;
    }

    /**
     * Metoda zwracająca współrzędną y asteroidy.
     * @return Współrzędna y asteroidy.
     */
    public float getY() {
        return y;
    }
}
