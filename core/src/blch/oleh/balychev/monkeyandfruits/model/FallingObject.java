package blch.oleh.balychev.monkeyandfruits.model;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by User on 11.03.2018.
 */

public class FallingObject {
    private GameObject mGameObject;
    private Rectangle mRectangle;

    public FallingObject(Rectangle rectangle, GameObject gameObject) {
        mGameObject = gameObject;
        mRectangle = rectangle;
    }

    public GameObject getGameObject() {
        return mGameObject;
    }

    public Rectangle getRectangle() {
        return mRectangle;
    }



}
