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

	/*
	 * Constructor for ButtonImage
	 * x,y is the starting point of button on screen (reference point is on left-top of the image)
	 * onClick refer to location of image when the button was clicked
	 * onRelease refer to location of image when the button was released
	 * onHover refer to location of image when the mouse hover above it
	 * width is the width of the button
	 * height is the height of the button
	 */
	
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
	
	//Inversely changing isClcik value
	//Change when the button was clicked
	
	@Override
	public boolean toggle() {
		isClicked = !isClicked;
		return isClicked;
	}
	
	//get button width - assumming all the image size were the same (it was resized to width and height)
	
	public int getWidth() {
		return (int)buttonOnReleased.getWidth();
	}
	
	//get button height
	
	public int getHeight() {
		return (int)buttonOnReleased.getHeight();
	}
	
	//Getter for isClicked
	
	@Override
	public boolean isClicked() {
		return isClicked;
	}
	
	//Method that check if mousX, mouseY was on the button
	
	@Override
	public boolean isMouseHover(int mouseX, int mouseY) {
		isHovered = Entity.isCollide(mouseX, mouseY, 0, 0, x, y, (int)buttonOnReleased.getHeight(), (int)buttonOnReleased.getWidth());
		return isHovered;
	}
	
	//Method that reset important parameter so it can be used again
	
	public void reset() {
		destroy = false;
		isClicked = false;
		isHovered = false;
	}
	
	//Method that detect if MouseX, MouseY was on the elipse
	
	@Override
	public boolean isMouseHoverElipse(int MouseX, int MouseY) {
		int centerX = x-(int)(buttonOnReleased.getWidth()/2.0);
		int centerY = y-(int)(buttonOnReleased.getHeight()/2.0);
		int widthRadius = (int)(buttonOnReleased.getWidth()/2.0);
		int heightRadius = (int)(buttonOnReleased.getHeight()/2.0);
		
		isHovered = Entity.isCollideElipse(MouseX, MouseY, centerX, centerY, heightRadius, widthRadius);
		return isHovered;
	}
	
	//Method that handle with drawing image on screen
	
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
