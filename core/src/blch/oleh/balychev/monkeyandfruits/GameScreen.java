package blch.oleh.balychev.monkeyandfruits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by User on 11.03.2018.
 */

public class GameScreen implements Screen {

    private static final long INTERVAL = 1_000_000_000;
    private static final int SPEED = 200;
    private static final int BEER_EFFECT_LENGTH = 3;

    final Game mGame;
    OrthographicCamera mCamera;
    Vector3 mTouchPos;
    GameObjectsLab mLab;

    // Счетчики и прочее
    private int mScore;
    private long mLastDropTime;
    private int mLifeAmount;
    private int mLastBeerEffect;
    private boolean mBeerEffect;

    // Текстуры
    Texture mMonkeyImage;
    Texture mBackgroundImage;
    Texture mHeartImage;

    // Музыка
    Music mMusic;
    Sound mSound;

    // Местоположения
    Rectangle mMonkey;
    private Array<FallingObject> mFallingObjects;
  //  private Map<String, Texture> mTextureMap;
    private Texture mBeerImage;


    public GameScreen(Game game) {
        mGame = game;
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, Size.WIDTH, Size.HEIGHT);

        mScore = 0;
        mLifeAmount = 3;
        mBeerEffect = false;

        mFallingObjects = new Array<>();
        mLab = GameObjectsLab.getInstance();
        mTouchPos = new Vector3();


        initTextures();



        // Музыка
        mSound = Gdx.audio.newSound(Gdx.files.internal("effect.wav"));
        mMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        mMusic.setLooping(true);
        mMusic.play();

        mMonkey = new Rectangle();
        mMonkey.x = Size.WIDTH/2 - Size.PLAYER/2;
        mMonkey.y = 20;
        mMonkey.width = Size.PLAYER;
        mMonkey.height = Size.PLAYER;
    }

    private void initTextures(){

        // Установка изображений
        mBackgroundImage = new Texture("background.jpg");
        mMonkeyImage = new Texture("player_monkey.png");
        mHeartImage = new Texture("heart.png");
        mBeerImage = new Texture("beer.png");

    }

    private void spawnRaindrop(){
        Rectangle rectangle = new Rectangle();
        rectangle.x = MathUtils.random(0, Size.WIDTH - Size.OBJECT);
        rectangle.y = Size.HEIGHT;
        rectangle.width = Size.OBJECT;
        rectangle.height = Size.OBJECT;
        mFallingObjects.add(new FallingObject(rectangle,
                mLab.getGameObjectById(MathUtils.random(0, mLab.getSizeOfList()-1))));
        mLastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.6f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mCamera.update();

        mGame.mBatch.setProjectionMatrix(mCamera.combined);

        mGame.mBatch.begin();
        mGame.mBatch.draw(mBackgroundImage, 0, 0);
        mGame.mFont.draw(mGame.mBatch, "Score: " + mScore, 0, Size.HEIGHT);

        // Отрисовка жизней
        mGame.mBatch.draw(mHeartImage, Size.WIDTH - Size.SMALL_OBJECT*2-Size.OFFSET,
                Size.HEIGHT-Size.SMALL_OBJECT-Size.OFFSET, Size.SMALL_OBJECT, Size.SMALL_OBJECT);
        mGame.mFont.draw(mGame.mBatch, " x " + mLifeAmount,
                Size.WIDTH - Size.SMALL_OBJECT-Size.OFFSET, Size.HEIGHT-2*Size.OFFSET);

        if (mBeerEffect){
            // Отрисовка эффекта от пива
            mGame.mBatch.draw(mBeerImage, Size.WIDTH - Size.SMALL_OBJECT*2-Size.OFFSET,
                    Size.HEIGHT-2*Size.SMALL_OBJECT-2*Size.OFFSET, Size.SMALL_OBJECT, Size.SMALL_OBJECT);
            mGame.mFont.draw(mGame.mBatch, " x " + mLastBeerEffect,
                    Size.WIDTH - Size.SMALL_OBJECT-Size.OFFSET, Size.HEIGHT-3*Size.OFFSET - Size.SMALL_OBJECT);
        }

        // Прорисовка обезьяны
        mGame.mBatch.draw(mMonkeyImage, mMonkey.x, mMonkey.y);

        // Прорисовка падающих объектов
        for (FallingObject fo : mFallingObjects){
            mGame.mBatch.draw(new Texture(fo.getGameObject().getImgName()), fo.getRectangle().x,
                    fo.getRectangle().y, fo.getRectangle().width, fo.getRectangle().height);
        }
        mGame.mBatch.end();

        if (Gdx.input.isTouched()){
            mTouchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            mCamera.unproject(mTouchPos);
            mMonkey.x =(mBeerEffect ? Size.WIDTH - mTouchPos.x : mTouchPos.x) - Size.PLAYER/2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            mMonkey.x -= 1000*Gdx.graphics.getDeltaTime() * (mBeerEffect ? -1 : 1) ;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            mMonkey.x += 1000*Gdx.graphics.getDeltaTime() * (mBeerEffect ? -1: 1);

        if (mMonkey.x < 0)
            mMonkey.x = 0;

        if (mMonkey.x > Size.WIDTH - Size.PLAYER)
            mMonkey.x = Size.WIDTH - Size.PLAYER;

        if (TimeUtils.nanoTime() - mLastDropTime > GameScreen.INTERVAL)
            spawnRaindrop();

        // Логика собирания объектов
        Iterator<FallingObject> iterator = mFallingObjects.iterator();
        while (iterator.hasNext()){
            FallingObject fo = iterator.next();
            fo.getRectangle().y -= GameScreen.SPEED * Gdx.graphics.getDeltaTime();

            if (fo.getRectangle().y + Size.OBJECT < 0){
                if(fo.getGameObject().isNecessary()) {
                    mSound.play();
                    mLifeAmount--;
                    end();
                }
                iterator.remove();
            }
            if (fo.getRectangle().overlaps(mMonkey)){
                if (fo.getGameObject().isNecessary()) {
                    mScore++;
                    decreaseBeerEffect();
                } else {
                    if(fo.getGameObject().getName().equals("bomb")){
                        mSound.play();
                        mLifeAmount = 0;
                    } else if (fo.getGameObject().getName().equals("heart")){
                        mLifeAmount++;
                    } else if(fo.getGameObject().getName().equals("beer")) {
                        mBeerEffect = true;
                        mLastBeerEffect += GameScreen.BEER_EFFECT_LENGTH;

                    } else {
                        mSound.play();
                        mLifeAmount--;
                    }
                    end();
                }
                iterator.remove();
            }
        }
    }

    private void decreaseBeerEffect(){
        if(mBeerEffect){
            mLastBeerEffect--;
            if (mLastBeerEffect == 0) mBeerEffect = false;
        }
    }

    private void end(){
        if(mLifeAmount==0){
            mGame.setScreen(new EndScreen(mGame, mScore));
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
        mMonkeyImage.dispose();
        mBackgroundImage.dispose();
        mHeartImage.dispose();
        mMusic.dispose();
        mSound.dispose();
        mBeerImage.dispose();
    }
}
