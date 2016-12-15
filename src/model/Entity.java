package model;

import Utility.ProgressHolder;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public abstract class Entity implements IRenderable {
	protected int x,y,z;
	
	/*
	 * Constructor for Entity where
	 * x, y is the position on the screen with reference point on top-left of the image
	 */
	
	public Entity(int x,int y){
		this.x=x;
		this.y=y;
		z=1;
	}
	
	//Getter for z
	
	@Override
	public int getZ() {
		return z;
	}
	
	//Setter for z
	
	public void setZ(int z) {
		// TODO Auto-generated method stub
		this.z=z;
	}
	
	//Getter for x
	
	public int getX() {
		return x;
	}
	
	//Setter for x
	
	public void setX(int x) {
		this.x = x;
	}
	
	//Getter for y
	
	public int getY() {
		return y;
	}
	
	//Setter for y
	
	public void setY(int y) {
		this.y = y;
	}
	
	/*Scale Image source to
	 * targetWidth and targetHeight
	 * with the option to preserveRatio
	 */
	
	public static Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
		ImageView imageView = new ImageView(source);
	    imageView.setPreserveRatio(preserveRatio);
	    imageView.setFitWidth(targetWidth);
	    imageView.setFitHeight(targetHeight);
	    
	    SnapshotParameters sp = new SnapshotParameters();
	    sp.setFill(Color.TRANSPARENT);
	    return imageView.snapshot(sp, null);
	}
	
	//Method that check if two boxes collide
	
	public static boolean isCollide(int posXA, int posYA, int heightA, int widthA, int posXB, int posYB, int heightB, int widthB) {
		return (((posXA <= posXB && posXB <= (posXA + widthA)) && (posYA <= posYB && posYB <= (posYA + heightA))) || ((posXB <= posXA && posXA <= (posXB + widthB)) && (posYB <= posYA && posYA <= (posYB + heightB))));
	}
	
	//Method that check if a point was in the elipse
	
	public static boolean isCollideElipse(int pointXA, int pointYA, int centerXB, int centerYB, int heightRadiusB, int widthRadiusB) {
		if(heightRadiusB > widthRadiusB) {
			return (Math.pow(heightRadiusB*pointXA, 2) + Math.pow(widthRadiusB*pointYA, 2) <= Math.pow(widthRadiusB*heightRadiusB, 2));
		}
		else {
			return Math.pow(widthRadiusB*pointXA, 2) + Math.pow(heightRadiusB*pointYA, 2) <= Math.pow(widthRadiusB*heightRadiusB, 2);
		}
	}
}
