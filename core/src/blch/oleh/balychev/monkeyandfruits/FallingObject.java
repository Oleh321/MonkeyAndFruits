package blch.oleh.balychev.monkeyandfruits;

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

    public void setGameObject(GameObject gameObject) {
        mGameObject = gameObject;
    }

    public Rectangle getRectangle() {
        return mRectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        mRectangle = rectangle;
    }
}
