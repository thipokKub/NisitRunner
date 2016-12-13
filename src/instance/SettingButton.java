package instance;

import Utility.AudioUtility;
import Utility.ProgressHolder;
import gui.GameMenu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class SettingButton extends model.ButtonImage{
	
	private int frameStart;
	
	public SettingButton(int x, int y, int width, int height) {
		
		super(x, y, "resource/button/setting_push.png", "resource/button/setting_normal.png", "resource/button/setting_hold.png", width, height);
		frameStart = -1;
	}
	
	public void resetFrameStart() {
		frameStart = -1;
	}
	
	@Override
	public void reset() {
		setDestroy(false);
		isClicked = false;
		isHovered = false;
		frameStart = -1;
	}
	
	@Override
	public void myCallBackOnClicked(MouseEvent e) {
		//Call game scene
		if(isMouseHover((int)e.getSceneX(), (int)e.getSceneY())) {
			AudioUtility.playClick();
			if(!isClicked()) {
				toggle();
				System.out.println("Setting was clicked");
				GameMenu.settingCalled = true;
				frameStart = ProgressHolder.frameCounter;
				translateX(10);
			}
		};
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void myCallBackOnHovered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!isMouseHover((int)e.getSceneX(), (int)e.getSceneY())) {
			if(isClicked()) toggle();
		}
	}

	@Override
	public void myCallBackOnRelease(MouseEvent e) {
		// TODO Auto-generated method stub
		if(this.isClicked) this.toggle();
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
	public void draw(GraphicsContext gc) {
		if(frameStart != -1) {
			if(x <= ProgressHolder.windowWidth) {
				translateX(20);
			}
			if(x > ProgressHolder.windowWidth) {
				GameMenu.drawFadeOut = true;
			}
		}
		if(isClicked) {
			gc.drawImage(buttonOnClicked, x, y);
		}
		else if(isHovered) {
			gc.drawImage(buttonOnHover, x, y);
		}
		else {
			gc.drawImage(buttonOnReleased, x, y);
		}
	}

}
