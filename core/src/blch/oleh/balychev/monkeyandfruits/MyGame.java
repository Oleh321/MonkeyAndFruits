package blch.oleh.balychev.monkeyandfruits;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import blch.oleh.balychev.monkeyandfruits.screen.FirstScreen;

public class MyGame extends Game {
	private SpriteBatch mBatch;
	private BitmapFont mFont;
	
	@Override
	public void create () {
		mBatch = new SpriteBatch();
		mFont = new BitmapFont();
		this.setScreen(new FirstScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		mBatch.dispose();
		mFont.dispose();
	}

	public SpriteBatch getBatch() {
		return mBatch;
	}

	public BitmapFont getFont() {
		return mFont;
	}
}
