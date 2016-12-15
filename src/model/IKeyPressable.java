package model;

import javafx.scene.input.KeyCode;

public interface IKeyPressable {
	//Method to be called if keyboard was pressed for that object
	public void myCallBackOnPressedKeyBoard(KeyCode e);
	//Method to be called if keyboard was released for that object
	public void myCallBackOnReleaseKeyBoard(KeyCode e);
}
