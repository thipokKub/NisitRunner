package model;

import Utility.ProgressHolder;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.CodeUtility;

public abstract class GameInput extends Application {
	
	public abstract void updateDraw(); //For Drawing - IRenderable
	public abstract void updateMove(); //For Moveable
	public abstract void updateValue(); //For Bar
	
	public abstract void setUpThreadAndAnimation();
	public abstract void addEventListener();
	public abstract void setUpCanvas();
	
	private static KeyCode previousKey;
	
	public void updateOnMouseClick(MouseEvent e) {
		for(ButtonImage x: ProgressHolder.updateOnClicked) {
			x.myCallBackOnClicked(e);
		}
	};
	
	public void updateOnMouseHover(MouseEvent e) {
		for(ButtonImage x: ProgressHolder.updateOnHover) {
			x.myCallBackOnHovered(e);
		}
	};
	public void updateOnMouseRelease(MouseEvent e) {
		for(ButtonImage x: ProgressHolder.updateOnReleased) {
			x.myCallBackOnRelease(e);
		}
	};
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	}
	
	public void receiveKey(KeyCode new_code) {
		// TODO Auto-generated method stub
		//Fill in here		
		
		if(CodeUtility.keyTriggered.indexOf(new_code) >= 0) {
			CodeUtility.keyTriggered.remove(new_code);
		} else {
			CodeUtility.keyTriggered.add(new_code);
		}
		if(CodeUtility.keyPressed.indexOf(new_code) < 0) {
			CodeUtility.keyPressed.add(new_code);
		}
		
		for(IKeyPressable x: ProgressHolder.updateOnKeyPressed) {
			x.myCallBackOnPressedKeyBoard(new_code);
		}
		
		
		
	}

	public void dropKey(KeyCode new_code) {
		// TODO Auto-generated method stub
		//Fill in here
		CodeUtility.keyPressed.remove(new_code);
		for(IKeyPressable x: ProgressHolder.updateOnKeyReleased) {
			x.myCallBackOnReleaseKeyBoard(new_code);
		}
	}

}
