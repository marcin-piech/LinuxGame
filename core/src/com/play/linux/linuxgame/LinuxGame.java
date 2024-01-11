package com.play.linux.linuxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.play.linux.linuxgame.screens.MainMenuScreen;

/**
 * Klasa LinuxGame rozszerzająca główną klasę Game z biblioteki libGDX.
 */
public class LinuxGame extends Game {

	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	public static int level = 1;
	public static int score = 0;
	public SpriteBatch batch;
	private BitmapFont font;

	/**
	 * Metoda wywoływana podczas tworzenia gry.
	 * Inicjalizuje obiekt SpriteBatch i ustawia ekran gry na MainMenuScreen.
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	/**
	 * Metoda renderująca, wywoływana w każdej klatce gry.
	 */
	@Override
	public void render () {
		super.render();
	}

	/**
	 * Metoda wywoływana przed zniszczeniem obiektu gry (np. zamykaniem gry).
	 * Zajmuje się zwalnianiem zasobów.
	 */
	@Override
	public void dispose () {
		batch.dispose();
	}
}
