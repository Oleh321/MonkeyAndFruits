package blch.oleh.balychev.monkeyandfruits.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import blch.oleh.balychev.monkeyandfruits.constant.Constants;
import blch.oleh.balychev.monkeyandfruits.datafiller.GameObjectsLab;
import blch.oleh.balychev.monkeyandfruits.model.FallingObject;
import blch.oleh.balychev.monkeyandfruits.screen.GameScreen;

/**
 * Created by User on 11.03.2018.
 */

public class GameController {

    // Счетчики и прочее
    private int mScore;
    private long mLastDropTime;
    private int mLifeAmount;
    private int mLastBeerEffect;
    private boolean mBeerEffect;

    private GameObjectsLab mLab;

    // Местоположения игровых объектов
    private Array<FallingObject> mFallingObjects;
    private Rectangle mMonkey;

    public GameController() {
        mScore = 0;
        mLifeAmount = Constants.LIFE_AMOUNT;
        mBeerEffect = false;
        mFallingObjects = new Array<>();
        mLab = GameObjectsLab.getInstance();

        // Установка расположения и размеров игрока
        mMonkey = new Rectangle();
        mMonkey.x = Constants.Size.WIDTH/2 - Constants.Size.PLAYER/2;
        mMonkey.y = 20;
        mMonkey.width = Constants.Size.PLAYER;
        mMonkey.height = Constants.Size.PLAYER;

    }

    public void spawnFallingObject(){
        Rectangle rectangle = new Rectangle();
        rectangle.x = MathUtils.random(0, Constants.Size.WIDTH - Constants.Size.OBJECT);
        rectangle.y = Constants.Size.HEIGHT;
        rectangle.width = Constants.Size.OBJECT;
        rectangle.height = Constants.Size.OBJECT;
        mFallingObjects.add(new FallingObject(rectangle,
                mLab.getGameObjectById(MathUtils.random(0, mLab.getSizeOfList()-1))));
        mLastDropTime = TimeUtils.nanoTime();
    }

    public int getScore() {
        return mScore;
    }

    public long getLastDropTime() {
        return mLastDropTime;
    }

    public int getLifeAmount() {
        return mLifeAmount;
    }

    public int getLastBeerEffect() {
        return mLastBeerEffect;
    }

    public boolean isBeerEffect() {
        return mBeerEffect;
    }

    public GameObjectsLab getLab() {
        return mLab;
    }

    public Array<FallingObject> getFallingObjects() {
        return mFallingObjects;
    }

    public Rectangle getMonkey() {
        return mMonkey;
    }

    public void decreaseLife() {
        mLifeAmount--;
    }

    public void increaseLife() {
        mLifeAmount++;
    }
    public void increaseScore() {
        mScore++;
    }

    public void setToZeroLife() {
        mLifeAmount = 0;
    }

    public void setBeerEffect(boolean beerEffect) {
        mBeerEffect = beerEffect;
    }

    public void initBeerEffect(){
        mBeerEffect = true;
        mLastBeerEffect += Constants.BEER_EFFECT_LENGTH;
    }

    public boolean isLose(){
        return mLifeAmount == 0;
    }

    public void decreaseBeerEffect(){
        if(mBeerEffect){
            mLastBeerEffect--;
            if (mLastBeerEffect == 0)
                mBeerEffect = false;
        }
    }
}
