package instance;

import Utility.AudioUtility;
import Utility.ProgressHolder;
import gui.GameMenu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class CreditsButton extends model.ButtonImage{
	
	private int frameStart;
	
	public CreditsButton(int x, int y, int width, int height) {
		super(x, y, "resource/button/credit_push.png", "resource/button/credit_normal.png", "resource/button/credit_hold.png", width, height);
		frameStart = -1;
	}

	@Override
	public void myCallBackOnClicked(MouseEvent e) {
		//Call game scene
		if(isMouseHover((int)e.getSceneX(), (int)e.getSceneY())) {
			AudioUtility.playClick();
			if(!isClicked()) {
				toggle();
				System.out.println("Credits was clicked");
				if(frameStart == -1) {
					frameStart = ProgressHolder.frameCounter;
				}
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
			if(ProgressHolder.frameCounter - frameStart <= 3*ProgressHolder.frameRate) {
				GameMenu.showCredits = true;
			}
			else {
				GameMenu.showCredits = false;
				frameStart = -1;
				ProgressHolder.CreditsOne.resetDrawDissolveHold();
				ProgressHolder.CreditsTwo.resetDrawDissolveHold();
				ProgressHolder.GameCredits.resetDrawDissolveHold();
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
