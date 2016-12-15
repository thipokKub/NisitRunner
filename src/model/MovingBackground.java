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
	
	/* Constructor for MovingBackground where
	 * x, y is the position on the screen with reference point on top-left of the image
	 * fileName is the path of the file
	 * direction determine it to be moved left or right (+ right/ - left)
	 * scaleFit is used if the background will be scaled up to screen height
	 */
	
	public MovingBackground(int x, int y, int speed, String fileName, int direction, boolean scaleFit) throws ResourceException {
		super(x, y);
		this.direction = (direction > 0) ? 1 : -1;
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
	
	//Setter for background with fileName as file path
	
	public void setBackground(String fileName) throws ResourceException {
		ProgressHolder.checkAvalible(fileName);
		background = new Image(ClassLoader.getSystemResource(fileName).toString());
		imgX = (int) background.getWidth();
		bg = background.getPixelReader();
	}
	
	//Setter for background with bg as Image
	
	public void setBackground(Image bg) {
		background = bg;
		imgX = (int) background.getWidth();
		this.bg = background.getPixelReader();
	}
	
	//Setter for speed
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	//Getter for speed
	
	public int getSpeed() {
		return speed;
	}
	
	//Resize the background to fit height of gc
	
	public void fitHeight(GraphicsContext gc) {
		if(background != null) {
			double ratio = background.getWidth()/background.getHeight();
			System.out.println(" " + gc);
			background = Entity.scale(background, (int)(ratio*gc.getCanvas().getHeight()), (int) gc.getCanvas().getHeight(), false);
			imgX = (int) background.getWidth();
			this.bg = background.getPixelReader();
		}
	}
	
	//Method that handle drawing image on screen
	
	@Override
	public void draw(GraphicsContext gc) {
		int width = (int)(background.getWidth());
		
		if(imgX > 0) {
			gc.drawImage(background, imgX-width, 0);
		}
		if(width-imgX >0) {
			gc.drawImage(background, imgX, 0);
		}

		imgX = (imgX-speed)%width;
		if(imgX < 0) imgX += width;
		
	}
	
	//Getter for destroy

	@Override
	public boolean isDestroy() {
		return destroy;
	}
	
	//Setter for destroy
	
	public void setDestory(boolean value) {
		destroy = value;
		//Called when change game scene
	}

}
