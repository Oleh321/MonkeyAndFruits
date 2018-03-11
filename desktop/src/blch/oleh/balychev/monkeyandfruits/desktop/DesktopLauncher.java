package blch.oleh.balychev.monkeyandfruits.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import blch.oleh.balychev.monkeyandfruits.Game;
import blch.oleh.balychev.monkeyandfruits.Size;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Monkey and Fruits";
		config.width = Size.WIDTH;
		config.height = Size.HEIGHT;
		new LwjglApplication(new Game(), config);
	}
}
