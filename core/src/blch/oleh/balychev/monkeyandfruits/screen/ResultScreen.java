package blch.oleh.balychev.monkeyandfruits.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import blch.oleh.balychev.monkeyandfruits.MyGame;
import blch.oleh.balychev.monkeyandfruits.constant.Constants;

/**
 * Created by User on 11.03.2018.
 */

class ResultScreen implements Screen {

    private final MyGame mGame;
    private int mScore;
    private OrthographicCamera mCamera;

    public ResultScreen(final MyGame game, int score) {
        mGame = game;
        mScore = score;
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, Constants.Size.WIDTH, Constants.Size.HEIGHT);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mCamera.update();

        mGame.getBatch().setProjectionMatrix(mCamera.combined);

        mGame.getBatch().begin();
        mGame.getFont().draw(mGame.getBatch(), "Your score = " + mScore, Constants.Size.WIDTH/2 - 50, Constants.Size.HEIGHT/2 + 25);
        mGame.getFont().draw(mGame.getBatch(), "Tap to play again.", Constants.Size.WIDTH/2 - 50, Constants.Size.HEIGHT/2 - 25);
        mGame.getBatch().end();

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
