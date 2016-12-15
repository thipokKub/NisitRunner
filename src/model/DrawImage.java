package model;

import Utility.ProgressHolder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DrawImage extends Moveable{

	private Image background;
	
	/*
	 * Constructor for DrawImage where
	 * x, y is the position on the screen with reference point on top-left of the image
	 * source is the source of the image file
	 */
	
	public DrawImage(int x, int y, String source) throws ResourceException {
		super(x, y);
		ProgressHolder.checkAvalible(source);
		background = new Image(ClassLoader.getSystemResource(source).toString());
		background = Entity.scale(background, ProgressHolder.windowWidth, ProgressHolder.windowHeight, false);
		z = 1000;
	}
	
	/*
	 * Constructor for DrawImage where
	 * x, y is the position on the screen with reference point on top-left of the image
	 * source is the source of the image file
	 * width, height is the size of the image that will be resized to
	 */
	
	public DrawImage(int x, int y, int width, int height, String source) {
		super(x, y);
		background = new Image(ClassLoader.getSystemResource(source).toString());
		background = Entity.scale(background, width, height, false);
		z = 1000;
	}
	
	/*
	 * Constructor for DrawImage where
	 * x, y is the position on the screen with reference point on top-left of the image
	 * Need to set up background before used
	 */
	
	public DrawImage(int x, int y) {
		super(x, y);
		background = null;
		z = 1000;
	}
	
	//Setter for background
	
	public void setImage(Image x) {
		background = x;
	}
	
	//Method for resizing width and height of the background
	
	public void setSize(int width, int height) {
		background = Entity.scale(background, width, height, false);
	}
	
	//Method for resizing width and height of the background to the correct height with the same aspect ratio as the original
	
	public void setSizeByHeight(int height) {
		double ratio = background.getWidth()/background.getHeight();
		background = Entity.scale(background, (int)(ratio*height), height, false);
	}
	
	//Draw image onto canvas
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if(background != null) {
			gc.drawImage(background, x, y);
		}
	}
	
	//Setter for destroy

	@Override
	public boolean isDestroy() {
		return false;
	}
	
	//Method handle moving DrawImage around

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

}
