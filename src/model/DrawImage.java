package model;

import Utility.ProgressHolder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DrawImage extends Moveable{

	private Image background;
	
	public DrawImage(int x, int y, String source) throws ResourceException {
		super(x, y);
		ProgressHolder.checkAvalible(source);
		background = new Image(ClassLoader.getSystemResource(source).toString());
		background = Entity.scale(background, ProgressHolder.windowWidth, ProgressHolder.windowHeight, false);
		z = 1000;
	}
	
	public DrawImage(int x, int y, int width, int height, String source) {
		super(x, y);
		background = new Image(ClassLoader.getSystemResource(source).toString());
		background = Entity.scale(background, width, height, false);
		z = 1000;
	}
	
	public DrawImage(int x, int y) {
		super(x, y);
		background = null;
		z = 1000;
	}
	
	public void setImage(Image x) {
		background = x;
	}
	
	public void setSize(int width, int height) {
		background = Entity.scale(background, width, height, false);
	}
	
	public void setSizeByHeight(int height) {
		double ratio = background.getWidth()/background.getHeight();
		background = Entity.scale(background, (int)(ratio*height), height, false);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if(background != null) {
			gc.drawImage(background, x, y);
		}
	}

	@Override
	public boolean isDestroy() {
		return false;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

}
