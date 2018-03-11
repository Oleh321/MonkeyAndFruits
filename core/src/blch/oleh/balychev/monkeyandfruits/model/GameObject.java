package blch.oleh.balychev.monkeyandfruits.model;

public class GameObject {
    private String mType;
    private String mImgName;

    public GameObject(String type, String imgName) {
        mType = type;
        mImgName = imgName;
    }

    public String getType() {
        return mType;
    }

    public String getImgName() {
        return mImgName;
    }
}
