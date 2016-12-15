package instance;

import Utility.AudioUtility;
import Utility.ProgressHolder;
import Utility.RenderableHolder;
import gui.GameMenu;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import logic.GameManager;
import model.ButtonImage;
import model.DrawImage;
import model.ResourceException;

public class HelpButton extends ButtonImage {

	private static final String filePath = "resource/button/help.png";
	private static DrawImage[] helpFrame; 
	private int frameStart, frameTrack, frameBefore;
	
	public HelpButton(int x, int y, int width, int height) {
		super(x, y, filePath, filePath, filePath, width, height);
		z = 10000;
		if(helpFrame == null) {
			helpFrame = new DrawImage[4];
			try {
				helpFrame[0] = new DrawImage(0, 0, "resource/instruction/help_frame0.png");
				helpFrame[0].setZ(10001);
				helpFrame[1] = new DrawImage(0, 0, "resource/instruction/help_frame1.png");
				helpFrame[1].setZ(10001);
				helpFrame[2] = new DrawImage(0, 0, "resource/instruction/help_frame2.png");
				helpFrame[2].setZ(10001);
				helpFrame[3] = new DrawImage(0, 0, "resource/instruction/help_frame3.png");
				helpFrame[3].setZ(10001);
			}catch(ResourceException e) {}
		}
	}
	
	public DrawImage[] getHelpFrame() {
		return helpFrame;
	}

	@Override
	public void myCallBackOnClicked(MouseEvent e) {
		if(isMouseHover((int)e.getSceneX(), (int)e.getSceneY())) {
			AudioUtility.playClick();
			toggle();
			System.out.println(isClicked);
			frameStart = 0;
			frameTrack = 0;
			frameBefore = 3;
			if(isClicked) {
				ProgressHolder.BlackScreenTransparent.resetDrawDissolveHold();
				GameMenu.helpCalled = true;
				Thread showInstruction = new Thread(() -> {
					try {
						Thread.sleep(500);
						while(isClicked && frameStart < 6*ProgressHolder.frameRate && GameMenu.helpCalled) {
							if((frameStart % ProgressHolder.frameRate == 0) || (frameStart % ProgressHolder.frameRate == ProgressHolder.frameRate/3) || (frameStart % ProgressHolder.frameRate == 2*ProgressHolder.frameRate/3)) {
								if(RenderableHolder.instance.getEntities().contains(helpFrame[frameBefore])) {
									RenderableHolder.instance.getEntities().remove(helpFrame[frameBefore]);
								}
								RenderableHolder.instance.getEntities().add(helpFrame[frameTrack]);
								frameTrack = (frameTrack+1)%4;
								frameBefore = (frameBefore+1)%4;
							}
							frameStart++;
							Thread.sleep((long) ProgressHolder.frameTime);
						}
						if(frameStart >= 6*ProgressHolder.frameRate || !GameMenu.helpCalled) {
							for(int i =0; i< 4; i++) {
								if(RenderableHolder.instance.getEntities().contains(helpFrame[i])) {
									RenderableHolder.instance.getEntities().remove(helpFrame[i]);
								}
							}
							frameStart = -1;
							isClicked = false;
						}
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				});
				
				ProgressHolder.AllThreads.add(showInstruction);
				showInstruction.start();
			}
			else {
				GameMenu.helpCalled = false;
				for(int i =0; i< 4; i++) {
					if(RenderableHolder.instance.getEntities().contains(helpFrame[i])) {
						RenderableHolder.instance.getEntities().remove(helpFrame[i]);
					}
				}
			}
			
		};
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
	public void move() {
		
	}
	
}
