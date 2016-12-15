package model;

import Utility.CodeUtility;
import Utility.ProgressHolder;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class GameInput extends Application {
	
	public abstract void updateDraw(); //For Drawing - IRenderable
	public abstract void updateMove(); //For Moveable
	public abstract void updateValue(); //For Bar
	
	public abstract void setUpThreadAndAnimation(); //Method that handle screen animation
	public abstract void addEventListener(); //Method that add event listener to scene
	public abstract void setUpCanvas(); //Method that was called before by setting up the parameters for each Scene
	
	//Method to run if mouse was clicked
	
	public void updateOnMouseClick(MouseEvent e) {
		for(ButtonImage x: ProgressHolder.updateOnClicked) {
			x.myCallBackOnClicked(e);
		}
	};
	
	//Method to run if mouse was moved
	
	public void updateOnMouseHover(MouseEvent e) {
		for(ButtonImage x: ProgressHolder.updateOnHover) {
			x.myCallBackOnHovered(e);
		}
	};
	
	//Method to run if mouse was released
	
	public void updateOnMouseRelease(MouseEvent e) {
		for(ButtonImage x: ProgressHolder.updateOnReleased) {
			x.myCallBackOnRelease(e);
		}
	};
	
	@Override
	public void start(Stage primaryStage) throws Exception {}
	
	//Method to run if keyboard was pressed
	
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

	//Method to run if keyboard was released
	
	public void dropKey(KeyCode new_code) {
		// TODO Auto-generated method stub
		//Fill in here
		CodeUtility.keyPressed.remove(new_code);
		for(IKeyPressable x: ProgressHolder.updateOnKeyReleased) {
			x.myCallBackOnReleaseKeyBoard(new_code);
		}
	}

}
