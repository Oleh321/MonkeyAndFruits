package blch.oleh.balychev.monkeyandfruits.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import blch.oleh.balychev.monkeyandfruits.MyGame;
import blch.oleh.balychev.monkeyandfruits.constant.Constants;
import blch.oleh.balychev.monkeyandfruits.controller.GameController;
import blch.oleh.balychev.monkeyandfruits.model.FallingObject;
import blch.oleh.balychev.monkeyandfruits.datafiller.GameObjectsLab;
import blch.oleh.balychev.monkeyandfruits.model.GameObject;

/**
 * Created by User on 11.03.2018.
 */

public class GameScreen implements Screen {

    private final MyGame mGame;
    private GameController mController;
    private OrthographicCamera mCamera;

    // Текстуры
    private Texture mMonkeyImage;
    private Texture mBackgroundImage;

    private Array<Texture> mFallingObjectImages;

    private Vector3 mTouchPos;

    // Музыка
    private Music mMusic;
    private Sound mSound;

    public GameScreen(final MyGame game) {
        mGame = game;
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, Constants.Size.WIDTH, Constants.Size.HEIGHT);

        mController = new GameController();

        initTextures();

        mTouchPos = new Vector3();

        // Музыка
        mSound = Gdx.audio.newSound(Gdx.files.internal("effect.wav"));
        mMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        mMusic.setLooping(true);
        mMusic.play();
    }

    // Установка изображений
    private void initTextures(){
        mBackgroundImage = new Texture("background.jpg");
        mMonkeyImage = new Texture("player_monkey.png");

        mFallingObjectImages = new Array<>();
        for (GameObject go : mController.getLab().getList()){
            mFallingObjectImages.add(new Texture(go.getImgName()));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.6f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mCamera.update();

        SpriteBatch gameButch = mGame.getBatch();
        BitmapFont gameFont = mGame.getFont();

        gameButch.setProjectionMatrix(mCamera.combined);

        gameButch.begin();

        gameButch.draw(mBackgroundImage, 0, 0);
        gameFont.draw(gameButch, "Score: " + mController.getScore(), 0, Constants.Size.HEIGHT);

        // Отрисовка жизней
        drawLife(gameButch, gameFont);

        if (mController.isBeerEffect()){
            // Отрисовка эффекта от пива
           drawBeerEffect(gameButch, gameFont);
        }

        Rectangle monkey = mController.getMonkey();

        // Прорисовка обезьяны
        gameButch.draw(mMonkeyImage, monkey.x, monkey.y);

        // Прорисовка падающих объектов
        for (FallingObject fo : mController.getFallingObjects()){
            gameButch.draw(mFallingObjectImages.get(mController.getLab()
                            .getIdByGameObject(fo.getGameObject())),
                    fo.getRectangle().x,
                    fo.getRectangle().y,
                    fo.getRectangle().width,
                    fo.getRectangle().height);
        }

        gameButch.end();

        if (Gdx.input.isTouched()){
            mTouchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            mCamera.unproject(mTouchPos);
            // Перемещение игрока с учетом эффекта
            monkey.x = (mController.isBeerEffect() ? Constants.Size.WIDTH - mTouchPos.x : mTouchPos.x)
                    - Constants.Size.PLAYER/2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT ) || Gdx.input.isKeyPressed(Input.Keys.A))
            monkey.x -= Constants.PLAYER_SPEED * Gdx.graphics.getDeltaTime()
                    * (mController.isBeerEffect() ? -1 : 1) ;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            monkey.x += Constants.PLAYER_SPEED * Gdx.graphics.getDeltaTime()
                    * (mController.isBeerEffect() ? -1 : 1) ;

        // Не даем игроку убежать за пределы поля
        if (monkey.x < 0)
            monkey.x = 0;

        if (monkey.x > Constants.Size.WIDTH - Constants.Size.PLAYER)
            monkey.x = Constants.Size.WIDTH - Constants.Size.PLAYER;

        if (TimeUtils.nanoTime() - mController.getLastDropTime() > Constants.INITIAL_INTERVAL)
            mController.spawnFallingObject();

        collectingFallingObjects();
    }

    // Логика собирания падающих объектов
    private void collectingFallingObjects(){
        Iterator<FallingObject> iterator = mController.getFallingObjects().iterator();
        while (iterator.hasNext()){
            FallingObject fo = iterator.next();
            fo.getRectangle().y -= Constants.INITIAL_SPEED * Gdx.graphics.getDeltaTime();

            if (fo.getRectangle().y + Constants.Size.OBJECT < 0){
                if(fo.getGameObject().getType().equals(Constants.Object.FRUIT)) {
                    mSound.play();
                    mController.decreaseLife();
                    checkOnLose();
                }
                iterator.remove();
            }
            if (fo.getRectangle().overlaps(mController.getMonkey())){
                if (fo.getGameObject().getType().equals(Constants.Object.FRUIT)) {
                    mController.increaseScore();
                    mController.decreaseBeerEffect();
                } else {
                    if(fo.getGameObject().getType().equals(Constants.Object.BOMB)){
                        mSound.play();
                        mController.setToZeroLife();
                    } else if(fo.getGameObject().getType().equals(Constants.Object.HEART)){
                        mController.increaseLife();
                    } else if(fo.getGameObject().getType().equals(Constants.Object.BEER)){
                        mController.initBeerEffect();
                    } else {
                        mSound.play();
                        mController.decreaseLife();
                    }
                    checkOnLose();
                }
                iterator.remove();
            }
        }
    }

    private void checkOnLose() {
        if(mController.isLose()){
            mGame.setScreen(new ResultScreen(mGame, mController.getScore()));
            dispose();
        }
    }

    // Отрисовка жизней в правом верхнем углу
    private void drawLife(SpriteBatch gameButch, BitmapFont gameFont){
        gameButch.draw(mFallingObjectImages.get(
                mController.getLab().getFirstIdByType(Constants.Object.HEART)),
                Constants.Size.WIDTH - 2*Constants.Size.SMALL_OBJECT - Constants.Size.OFFSET,
                Constants.Size.HEIGHT- Constants.Size.SMALL_OBJECT- Constants.Size.OFFSET,
                Constants.Size.SMALL_OBJECT,
                Constants.Size.SMALL_OBJECT);

        gameFont.draw(gameButch, " x " + mController.getLifeAmount(),
                Constants.Size.WIDTH - Constants.Size.SMALL_OBJECT - Constants.Size.OFFSET,
                Constants.Size.HEIGHT - 2*Constants.Size.OFFSET);

    }

    // Отрисовка эффекта от пива в правом верхнем углу под жизнями
    private void drawBeerEffect(SpriteBatch gameButch, BitmapFont gameFont){
        gameButch.draw(mFallingObjectImages.get(
                mController.getLab().getFirstIdByType(Constants.Object.BEER)),
                Constants.Size.WIDTH - 2*Constants.Size.SMALL_OBJECT - Constants.Size.OFFSET,
                Constants.Size.HEIGHT- 2*Constants.Size.SMALL_OBJECT- 2*Constants.Size.OFFSET,
                Constants.Size.SMALL_OBJECT,
                Constants.Size.SMALL_OBJECT);

        gameFont.draw(gameButch, " x " + mController.getLastBeerEffect(),
                Constants.Size.WIDTH - Constants.Size.SMALL_OBJECT - Constants.Size.OFFSET,
                Constants.Size.HEIGHT - Constants.Size.SMALL_OBJECT  - 3*Constants.Size.OFFSET);
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
        mMusic.dispose();
        mSound.dispose();
        for (Texture texture : mFallingObjectImages)
            texture.dispose();
    }
}
