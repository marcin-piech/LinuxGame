package com.play.linux.linuxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.play.linux.linuxgame.screens.MainGameScreen;

public class LinuxGame extends Game {

	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
	}
}
