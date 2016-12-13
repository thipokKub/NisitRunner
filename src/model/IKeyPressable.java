package model;

import javafx.scene.input.KeyCode;

public interface IKeyPressable {
	public void myCallBackOnPressedKeyBoard(KeyCode e);
	public void myCallBackOnReleaseKeyBoard(KeyCode e);
}
