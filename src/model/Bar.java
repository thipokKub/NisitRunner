package model;

import Utility.ProgressHolder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bar extends Entity{
	
	private boolean destroy;
	private String name;
	private int barWidth;
	private int progress;
	private static final int barHeight = 30;
	private static final int padding = 10;
	private Color myColor;
	
	/*
	 * Constructor for Bar entity
	 */
	
	public Bar(int x, int y, int width, String Label, int progress, Color myColor) {
		super(x, y);
		setProgress(progress);
		name = Label;
		barWidth = width;
		this.myColor = myColor;
		z=30;
	}
	
	//Setter for Color
	
	public void setColor(Color Color) {
		myColor = Color;
	}
	
	/*
	 * draw method for Bar entity(non-Javadoc)
	 * @see model.IRenderable#draw(javafx.scene.canvas.GraphicsContext)
	 * 
	 * will draw label with bar with color that had been set
	 * and within the bar, the length of inner bar will depends on barWidth and progress
	 */

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(myColor);
		gc.setStroke(myColor);
		gc.setLineWidth(2);
		gc.setFont(ProgressHolder.f);
		int fontWidth = (int) ProgressHolder.fontLoader.computeStringWidth(name, gc.getFont());
		int fontHeight = (int) ProgressHolder.fontLoader.getFontMetrics(gc.getFont()).getLineHeight();
		
		gc.strokeText(name, x, y + fontHeight);
		gc.strokeRect(x + fontWidth + padding, y + 10, barWidth, barHeight);
		gc.fillRect(x + fontWidth + padding + 3, y+13, (int)((barWidth-6)*(progress/100.0)), barHeight-6);
	}
	
	//Setter for progress
	
	public void setProgress(int progress) {
		if(progress > 100) this.progress = 100;
		else if(progress < 0) this.progress = 0;
		else this.progress = progress;
	}
	
	//Getter for progress
	
	public int getProgress() {
		return progress;
	}
	
	//Update progress by amount
	
	public void updateProgress(int amount) {
		setProgress(progress + amount);
	}
	
	//Getter for destroy

	@Override
	public boolean isDestroy() {
		return destroy;
	}
	
	//Getter for barWidth
	
	public int getWidth() {
		return barWidth;
	}
	
	//Setter for text
	
	public void setText(String text) {
		name = text;
	}
	
	//Getter for text
	
	public String getText() {
		return name;
	}
	
}
