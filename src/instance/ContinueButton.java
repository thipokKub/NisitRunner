package instance;

import Utility.AudioUtility;
import gui.GameMenu;
import gui.ResultScene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class ContinueButton extends model.ButtonImage{
	
	private static final String path = "resource/button/continue.png";
	
	public ContinueButton(int x, int y, int height) {
		super(x, y, path, path, path, (int)((2557.0/497.0)*height), height);
	}

	@Override
	public void myCallBackOnClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(isMouseHover((int)e.getSceneX(), (int)e.getSceneY())) {
			AudioUtility.playClick();
			if(!isClicked()) {
				toggle();
				//Call new scene
				System.out.println("Called for New Scene");
				ResultScene.drawFadeOut = true;
				GameMenu.reset();
			}
		}
	}
	
	public void reset() {
		isClicked = false;
	}

	@Override
	public void myCallBackOnHovered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void myCallBackOnRelease(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void myCallBackOnPressedKeyBoard(KeyCode e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void myCallBackOnReleaseKeyBoard(KeyCode e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

}
