package instance;

import Utility.AudioUtility;
import Utility.ProgressHolder;
import gui.SettingScene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class BackButton extends model.ButtonImage{
	
	private static final String path = "resource/button/back.png";
	
	public BackButton(int x, int y, int width, int height) {
		super(x, y, path, path, path, width, height);
		
	}

	@Override
	public void myCallBackOnClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(isMouseHover((int)e.getSceneX(), (int)e.getSceneY())) {
			AudioUtility.playClick();
			if(!isClicked()) {
				toggle();
				SettingScene.me.drawFadeOut = true;
			}
		}
	}
	
	public void reset() {
		isClicked = false;
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
