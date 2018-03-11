package blch.oleh.balychev.monkeyandfruits;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends com.badlogic.gdx.Game {
	SpriteBatch mBatch;
	BitmapFont mFont;
	
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
}
