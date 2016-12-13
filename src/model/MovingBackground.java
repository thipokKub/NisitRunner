package model;

import Utility.ProgressHolder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class MovingBackground extends Entity{
	
	private int speed;
	private int direction;
	private Image background;
	private PixelReader bg;
	private int imgX;
	private boolean destroy;
	
	public MovingBackground(int x, int y, int speed, String fileName, int direction, boolean scaleFit) throws ResourceException {
		super(x, y);
		this.direction = (direction > 0) ? 1 : -1;
		//direction is positive - right, otherwise left
		this.speed = this.direction*speed;
		System.out.println(fileName);
		
		this.setBackground(fileName);
		
		if(scaleFit)
			background = Entity.scale(background, ProgressHolder.windowWidth, ProgressHolder.windowHeight, false);
		
		bg = background.getPixelReader();
		imgX = (int) background.getWidth();
		destroy = false;
		z = 10;
	}
	
	public void setBackground(String fileName) throws ResourceException {
		ProgressHolder.checkAvalible(fileName);
		background = new Image(ClassLoader.getSystemResource(fileName).toString());
		imgX = (int) background.getWidth();
		bg = background.getPixelReader();
	}
	
	public void setBackground(Image bg) {
		background = bg;
		imgX = (int) background.getWidth();
		this.bg = background.getPixelReader();
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void fitHeight(GraphicsContext gc) {
		if(background != null) {
			double ratio = background.getWidth()/background.getHeight();
			System.out.println(" " + gc);
			background = Entity.scale(background, (int)(ratio*gc.getCanvas().getHeight()), (int) gc.getCanvas().getHeight(), false);
			imgX = (int) background.getWidth();
			this.bg = background.getPixelReader();
		}
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		int width = (int)(background.getWidth());
		int height = (int)(background.getHeight());
		if(imgX > 0) {
			WritableImage movingBackgroundOne = new WritableImage(bg, width-imgX, 0, imgX, height);
			gc.drawImage(movingBackgroundOne, x, y);
		}
		if(width-imgX > 0) {
			WritableImage movingBackgroundTwo = new WritableImage(bg, 0, 0, width-imgX, height);
			gc.drawImage(movingBackgroundTwo, x+imgX, y);
		}
		imgX = (imgX-speed)%width;
		if(imgX < 0) imgX += width;
	}

	@Override
	public boolean isDestroy() {
		return destroy;
	}
	
	public void setDestory(boolean value) {
		destroy = value;
		//Called when change game scene
	}

}
