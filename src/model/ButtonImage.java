package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class ButtonImage extends Moveable implements IClickable, IKeyPressable{
	
	private boolean destroy;
	protected Image buttonOnReleased;
	protected Image buttonOnClicked;
	protected Image buttonOnHover;
	protected boolean isClicked;
	protected boolean isHovered;

	public ButtonImage(int x, int y, String onClick, String onRelease, String onHover, int width, int height) {
		super(x, y);
		setDestroy(false);
		System.out.println(onClick + " " + onRelease + " " + onHover);
		buttonOnReleased = Entity.scale(new Image(ClassLoader.getSystemResource(onRelease).toString()), width, height, false);
		buttonOnClicked = Entity.scale(new Image(ClassLoader.getSystemResource(onClick).toString()), width, height, false);
		buttonOnHover = Entity.scale(new Image(ClassLoader.getSystemResource(onHover).toString()), width, height, false);
		isClicked = false;
		isHovered = false;
		z=20;
	}
	
	@Override
	public boolean toggle() {
		isClicked = !isClicked;
		return isClicked;
	}
	
	public int getWidth() {
		return (int)buttonOnReleased.getWidth();
	}
	
	public int getHeight() {
		return (int)buttonOnReleased.getHeight();
	}
	
	@Override
	public boolean isClicked() {
		return isClicked;
	}
	
	@Override
	public boolean isMouseHover(int mouseX, int mouseY) {
		isHovered = Entity.isCollide(mouseX, mouseY, 0, 0, x, y, (int)buttonOnReleased.getHeight(), (int)buttonOnReleased.getWidth());
		return isHovered;
	}
	
	public void reset() {
		destroy = false;
		isClicked = false;
		isHovered = false;
	}
	
	@Override
	public boolean isMouseHoverElipse(int MouseX, int MouseY) {
		int centerX = x-(int)(buttonOnReleased.getWidth()/2.0);
		int centerY = y-(int)(buttonOnReleased.getHeight()/2.0);
		int widthRadius = (int)(buttonOnReleased.getWidth()/2.0);
		int heightRadius = (int)(buttonOnReleased.getHeight()/2.0);
		
		isHovered = Entity.isCollideElipse(MouseX, MouseY, centerX, centerY, heightRadius, widthRadius);
		return isHovered;
	}

	@Override
	public void draw(GraphicsContext gc) {
		if(isClicked) {
			gc.drawImage(buttonOnClicked, x, y);
		}
		else if(isHovered) {
			gc.drawImage(buttonOnHover, x, y);
		}
		else {
			gc.drawImage(buttonOnReleased, x, y);
		}
	}

	@Override
	public boolean isDestroy() {
		return destroy;
	}
	
	public void setDestroy(boolean value) {
		destroy = value;
		//Called when change game scene
	}

}
