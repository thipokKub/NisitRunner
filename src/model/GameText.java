package model;

import Utility.ProgressHolder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.GameManager;

public class GameText extends Moveable{
	
	private String text;
	private int width, height;
	private Color Color;
	private Font myFont;

	/* Constructor for GameText where
	 * x, y is the position on the screen with reference point on top-left of the image
	 * name is the text that will be written on canvas
	 * gc is GraphicsContext to be used
	 * Color is the color of the text will be on the screen
	 */
	
	public GameText(int x, int y, String name, GraphicsContext gc, Color Color) {
		super(x, y);
		text = name;
		width = (int)ProgressHolder.fontLoader.computeStringWidth(text, gc.getFont());
		height = (int)ProgressHolder.fontLoader.getFontMetrics(gc.getFont()).getLineHeight();
		this.Color = Color;
		z=40;
		myFont = null;
	}
	
	//get width for string s with font f
	
	public static int getWidth(String s, Font f) {
		return (int)ProgressHolder.fontLoader.computeStringWidth(s, f);
	}
	
	//get height for font f
	
	public static int getLineHeight(Font f) {
		return (int)ProgressHolder.fontLoader.getFontMetrics(f).getLineHeight();
	}
	
	//get width for string s with default font
	
	public static int getWidth(String s) {
		return (int)ProgressHolder.fontLoader.computeStringWidth(s, GameManager.gc.getFont());
	}
	
	//get height for default font
	
	public static int getLineHeight() {
		return (int)ProgressHolder.fontLoader.getFontMetrics(GameManager.gc.getFont()).getLineHeight();
	}
	
	//Setter for Font f
	
	public void setFont(Font f) {
		myFont = f;
	}
	
	//getter for width
	
	public int getWidth() {
		return width;
	}
	
	//getter for height
	
	public int getHeight() {
		return height;
	}
	
	//getter for text
	
	public String getText() {
		return text;
	}
	
	//setter for text
	
	public void setText(String text) {
		this.text = text;
	}
	
	//Method that handle drawing text on screen

	@Override
	public void draw(GraphicsContext gc) {
		if(myFont == null) gc.setFont(ProgressHolder.f);
		else gc.setFont(myFont);
		gc.setFill(Color);
		gc.setStroke(Color);
		gc.setLineWidth(2);
		
		gc.strokeText(text, x, y-height);
	}

	//Getter for destroy
	
	@Override
	public boolean isDestroy() {
		// TODO Auto-generated method stub
		return false;
	}
	
	//Method that handle movement of GameText
	
	@Override
	public void move() {}

}
