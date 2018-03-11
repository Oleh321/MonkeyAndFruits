package blch.oleh.balychev.monkeyandfruits;

import java.util.ArrayList;

/**
 * Created by User on 11.03.2018.
 */

public class GameObjectsLab {



    private static GameObjectsLab object;
    private ArrayList<GameObject> list;

    private GameObjectsLab(){
        list = new ArrayList<GameObject>();
        list.add(new GameObject("apple", true, "apple.png"));
        list.add(new GameObject("banana", true, "banana.png"));
        list.add(new GameObject("beer", false, "beer.png"));
        list.add(new GameObject("bomb", false, "bomb.png"));
        list.add(new GameObject("cherries", true, "cherries.png"));
        list.add(new GameObject("chili", false, "chili.png"));
        list.add(new GameObject("garlic", false, "garlic.png"));
        list.add(new GameObject("grapes", true, "grapes.png"));
        list.add(new GameObject("heart", false, "heart.png"));
        list.add(new GameObject("orange", true, "orange.png"));
        list.add(new GameObject("peach", true, "peach.png"));
        list.add(new GameObject("pear", true, "pear.png"));
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

}
