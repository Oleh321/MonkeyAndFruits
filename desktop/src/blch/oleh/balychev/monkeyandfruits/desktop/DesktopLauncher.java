package blch.oleh.balychev.monkeyandfruits.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import blch.oleh.balychev.monkeyandfruits.MyGame;
import blch.oleh.balychev.monkeyandfruits.constant.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Monkey and Fruits";
		config.width = Constants.Size.WIDTH;
		config.height = Constants.Size.HEIGHT;
		new LwjglApplication(new MyGame(), config);
	}
}
