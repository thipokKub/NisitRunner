package model;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ResourceException extends Exception{
	
	public ResourceException() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Resource Loading Error, Press ok to exit");
		alert.setHeaderText(null);
		
		alert.showAndWait();
		Platform.exit();
	}
	
	public ResourceException(String filePath) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setContentText("Resource Loading Error\n\"" + filePath + "\"\n does not exists\n\n Press ok to exit");
		alert.setHeaderText(null);
		
		alert.showAndWait();
		Platform.exit();
	}

}
