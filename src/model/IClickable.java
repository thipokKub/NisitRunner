package model;

import javafx.scene.input.MouseEvent;

public interface IClickable {
	public void myCallBackOnClicked(MouseEvent e);
	public void myCallBackOnHovered(MouseEvent e);
	public void myCallBackOnRelease(MouseEvent e);
	public boolean isMouseHover(int MouseX, int MouseY);
	public boolean isMouseHoverElipse(int MouseX, int MouseY);
	public boolean isClicked();
	public boolean toggle();
}
