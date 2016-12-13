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

	public GameText(int x, int y, String name, GraphicsContext gc, Color Color) {
		super(x, y);
		text = name;
		width = (int)ProgressHolder.fontLoader.computeStringWidth(text, gc.getFont());
		height = (int)ProgressHolder.fontLoader.getFontMetrics(gc.getFont()).getLineHeight();
		this.Color = Color;
		z=40;
		myFont = null;
	}
	
	public static int getWidth(String s, Font f) {
		return (int)ProgressHolder.fontLoader.computeStringWidth(s, f);
	}
	
	public static int getLineHeight(Font f) {
		return (int)ProgressHolder.fontLoader.getFontMetrics(f).getLineHeight();
	}
	
	public static int getWidth(String s) {
		return (int)ProgressHolder.fontLoader.computeStringWidth(s, GameManager.gc.getFont());
	}
	
	public static int getLineHeight() {
		return (int)ProgressHolder.fontLoader.getFontMetrics(GameManager.gc.getFont()).getLineHeight();
	}
	
	public void setFont(Font f) {
		myFont = f;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void draw(GraphicsContext gc) {
		if(myFont == null) gc.setFont(ProgressHolder.f);
		else gc.setFont(myFont);
		gc.setFill(Color);
		gc.setStroke(Color);
		gc.setLineWidth(2);
		
		gc.strokeText(text, x, y-height);
	}

	@Override
	public boolean isDestroy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

}
