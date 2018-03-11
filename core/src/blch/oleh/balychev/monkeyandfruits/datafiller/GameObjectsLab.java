package blch.oleh.balychev.monkeyandfruits.datafiller;

import java.util.ArrayList;

import blch.oleh.balychev.monkeyandfruits.constant.Constants;
import blch.oleh.balychev.monkeyandfruits.model.GameObject;

/**
 * Created by User on 11.03.2018.
 */

public class GameObjectsLab {

    private static GameObjectsLab object;
    private ArrayList<GameObject> list;

    private GameObjectsLab(){
        list = new ArrayList<>();
        list.add(new GameObject(Constants.Object.FRUIT, "apple.png"));
        list.add(new GameObject(Constants.Object.FRUIT, "banana.png"));
        list.add(new GameObject(Constants.Object.BEER,"beer.png"));
        list.add(new GameObject(Constants.Object.BOMB, "bomb.png"));
        list.add(new GameObject(Constants.Object.FRUIT, "cherries.png"));
        list.add(new GameObject(Constants.Object.HOT,  "chili.png"));
        list.add(new GameObject(Constants.Object.HOT,  "garlic.png"));
        list.add(new GameObject(Constants.Object.FRUIT, "grapes.png"));
        list.add(new GameObject(Constants.Object.HEART, "heart.png"));
        list.add(new GameObject(Constants.Object.FRUIT, "orange.png"));
        list.add(new GameObject(Constants.Object.FRUIT, "peach.png"));
        list.add(new GameObject(Constants.Object.FRUIT,  "pear.png"));
    }

    public int getFirstIdByType(String type){
        for(int i = 0; i < list.size(); i++){
            if (list.get(i).getType().equals(type))
                return i;
        }
        return -1;
    }

    public ArrayList<GameObject> getList() {
        return list;
    }

    public static GameObjectsLab getInstance(){
        return object == null ? object = new GameObjectsLab() : object;
    }

    public GameObject getGameObjectById(int id){
        return list.get(id);
    }

    public int getSizeOfList(){
        return list.size();
    }

    public int getIdByGameObject(GameObject gameObject) {
        return list.indexOf(gameObject);
    }
}
