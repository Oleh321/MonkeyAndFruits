package blch.oleh.balychev.monkeyandfruits.constant;

/**
 * Created by User on 11.03.2018.
 */

public class Constants {

    public static final long INITIAL_INTERVAL = 1_000_000_000;
    public static final int INITIAL_SPEED = 200;
    public static final int BEER_EFFECT_LENGTH = 3;
    public static final int LIFE_AMOUNT = 3;
    public static final float PLAYER_SPEED = 1000;

    public static class Size {
        public static final int PLAYER = 128;
        public static final int OBJECT = 64;
        public static final int OFFSET = 5;
        public static final int SMALL_OBJECT = 32;
        public static final int WIDTH = 1280;
        public static final int HEIGHT = 720;
    }

    public static class Object {
        public static final String  FRUIT = "fruit";
        public static final String  HOT = "hot";
        public static final String  BOMB = "bomb";
        public static final String  HEART = "heart";
        public static final String  BEER = "beer";
    }


}
