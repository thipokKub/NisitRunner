package instance;

import Utility.AudioUtility;
import Utility.ProgressHolder;
import gui.GameMenu;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class GameTitle extends model.ButtonImage {
	
	private int Count;
	
	public GameTitle(int x, int y, int width, int height) {
		super(x, y, "resource/Button/logo_home.png", "resource/Button/logo_home.png", "resource/Button/logo_home.png", width, height);
		Count = 0;
	}
	
	public Image getButtonImg() {
		if(isClicked) {
			return buttonOnClicked;
		}
		else if(isHovered) {
			return buttonOnHover;
		}
		return buttonOnReleased;
	}

	@Override
	public void myCallBackOnClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(isMouseHover((int)e.getSceneX(), (int)e.getSceneY())) {
			AudioUtility.playClick();
			if(!isClicked()) {
				toggle();
				ProgressHolder.mainCharacter.translateX(10);
				ProgressHolder.mainCharacter.jump();
				if(Count%6 == 2) {
					new Thread(() -> {
						try {
							Thread.sleep(500);
							AudioUtility.playActivated();
							System.out.println("Activated");
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}).start();
					ProgressHolder.isGodMode = true;
					
				}
				else if(Count%6 == 5) {
					ProgressHolder.isGodMode = false;
					new Thread(() -> {
						try {
							Thread.sleep(500);
							AudioUtility.playDeactivated();
							System.out.println("Deactivated");
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}).start();
				}
				Count++;
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

}
