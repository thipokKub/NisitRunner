package model;

import javafx.scene.input.MouseEvent;

public interface IClickable {
	
	//Method to be called if mouse was clicked on that object
	public void myCallBackOnClicked(MouseEvent e);
	//Method to be called if mouse was hovered on that object
	public void myCallBackOnHovered(MouseEvent e);
	//Method to be called if mouse was released on that object
	public void myCallBackOnRelease(MouseEvent e);
	//Method check if mouse is hovered as a box
	public boolean isMouseHover(int MouseX, int MouseY);
	//Method check if mouse is hovered as a elipse
	public boolean isMouseHoverElipse(int MouseX, int MouseY);
	//Getter for isClicked
	public boolean isClicked();
	//Inversely change value of isClicked
	public boolean toggle();
}
