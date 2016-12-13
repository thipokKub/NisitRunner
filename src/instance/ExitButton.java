package instance;

import Utility.AudioUtility;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class ExitButton extends model.ButtonImage{
	
	public ExitButton(int x, int y, int width, int height) {
		
		super(x, y, "resource/button/exit_push.png", "resource/button/exit_normal.png", "resource/button/exit_hold.png", width, height);
	}

	@Override
	public void myCallBackOnClicked(MouseEvent e) {
		//Call game scene
		if(isMouseHover((int)e.getSceneX(), (int)e.getSceneY())) {
			AudioUtility.playClick();
			if(!isClicked()) {
				toggle();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setContentText("Are you sure to exit the game?");
				Platform.runLater(() -> {
					if(alert.showAndWait().get() == ButtonType.OK) {
						Platform.exit();
					}
				});
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
			if(isClicked()) {
				toggle();
			}
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
