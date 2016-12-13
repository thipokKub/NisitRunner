package instance;

import Utility.AudioUtility;
import gui.SettingScene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import model.ButtonImage;

public class VolumeDown extends ButtonImage{
	private static final String path = "resource/Button/-.png";
	public VolumeDown(int x, int y, int width, int height) {
		super(x, y, path, path, path, width, height);
	}

	@Override
	public void myCallBackOnClicked(MouseEvent e) {
		if(isMouseHover((int)e.getSceneX(), (int)e.getSceneY())) {
			AudioUtility.playClick();
			AudioUtility.setVolume(AudioUtility.getVolume() - 1);
			AudioUtility.stopBackgroundMusic();
			AudioUtility.playBackgroundMusic();
		}
	}

	@Override
	public void myCallBackOnHovered(MouseEvent e) {}

	@Override
	public void myCallBackOnRelease(MouseEvent e) {}

	@Override
	public void myCallBackOnPressedKeyBoard(KeyCode e) {}

	@Override
	public void myCallBackOnReleaseKeyBoard(KeyCode e) {}

	@Override
	public void move() {}

}
