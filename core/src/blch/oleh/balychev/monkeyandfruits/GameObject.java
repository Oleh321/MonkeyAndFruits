package blch.oleh.balychev.monkeyandfruits;

public class GameObject {
    private String name;
    private boolean isNecessary;
    private String imgName;


    public boolean isNecessary() {
        return isNecessary;
    }

    public String getImgName() {
        return imgName;
    }

    public GameObject(String name) {
        this.name = name;
    }

    public GameObject(String name, boolean isNecessary, String imgName) {
        this.name = name;
        this.isNecessary = isNecessary;
        this.imgName = imgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameObject that = (GameObject) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
