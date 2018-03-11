package blch.oleh.balychev.monkeyandfruits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Font;

/**
 * Created by User on 11.03.2018.
 */

public class FirstScreen implements Screen {

    final Game mGame;
    OrthographicCamera mCamera;


    public FirstScreen( final Game game) {
        mGame = game;
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, Size.WIDTH, Size.HEIGHT);



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mCamera.update();

        mGame.mBatch.setProjectionMatrix(mCamera.combined);

        mGame.mBatch.begin();
        mGame.mFont.draw(mGame.mBatch, "Collect all fruits!", Size.WIDTH/2 - 50, Size.HEIGHT/2 + 25);
        mGame.mFont.draw(mGame.mBatch, "Tap to start.", Size.WIDTH/2 - 50, Size.HEIGHT/2 - 25);
        mGame.mBatch.end();

        if (Gdx.input.isTouched()){
            mGame.setScreen(new GameScreen(mGame));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
