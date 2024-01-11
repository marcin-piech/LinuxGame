package com.play.linux.linuxgame.tools;

/**
 * Klasa CollisionRect reprezentuje prostokątny obszar kolizji w grze.
 * Wykorzystywana jest do sprawdzania kolizji między obiektami.
 */
public class CollisionRect {
    float x, y;
    int width, height;

    /**
     * Konstruktor klasy CollisionRect.
     * Inicjalizuje prostokątny obszar kolizji o zadanych parametrach.
     *
     * @param x      Współrzędna x górnego lewego rogu obszaru kolizji.
     * @param y      Współrzędna y górnego lewego rogu obszaru kolizji.
     * @param width  Szerokość obszaru kolizji.
     * @param height Wysokość obszaru kolizji.
     */
    public CollisionRect (float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Metoda przemieszcza obszar kolizji do nowych współrzędnych.
     *
     * @param x Nowa współrzędna x górnego lewego rogu obszaru kolizji.
     * @param y Nowa współrzędna y górnego lewego rogu obszaru kolizji.
     */
    public void move (float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Metoda sprawdzająca, czy dany obszar kolizji koliduje z innym obszarem kolizji.
     *
     * @param rect Inny obszar kolizji do sprawdzenia kolizji.
     * @return True, jeśli obszary kolizji kolidują, w przeciwnym razie false.
     */
    public boolean collidesWith (CollisionRect rect){
        return x < rect.x + rect.width && y < rect.y + rect.height && x + width > rect.x && y + height > rect.y;
    }
}
