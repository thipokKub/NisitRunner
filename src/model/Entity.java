package model;

import Utility.ProgressHolder;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public abstract class Entity implements IRenderable {
	protected int x,y,z;
	
	public Entity(int x,int y){
		this.x=x;
		this.y=y;
		z=1;
	}
	@Override
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		// TODO Auto-generated method stub
		this.z=z;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public static Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
		ImageView imageView = new ImageView(source);
	    imageView.setPreserveRatio(preserveRatio);
	    imageView.setFitWidth(targetWidth);
	    imageView.setFitHeight(targetHeight);
	    
	    SnapshotParameters sp = new SnapshotParameters();
	    sp.setFill(Color.TRANSPARENT);
	    return imageView.snapshot(sp, null);
	}
	
	public static boolean isCollide(int posXA, int posYA, int heightA, int widthA, int posXB, int posYB, int heightB, int widthB) {
		return (((posXA <= posXB && posXB <= (posXA + widthA)) && (posYA <= posYB && posYB <= (posYA + heightA))) || ((posXB <= posXA && posXA <= (posXB + widthB)) && (posYB <= posYA && posYA <= (posYB + heightB))));
	}
	
	public static boolean isCollideElipse(int pointXA, int pointYA, int centerXB, int centerYB, int heightRadiusB, int widthRadiusB) {
		if(heightRadiusB > widthRadiusB) {
			return (Math.pow(heightRadiusB*pointXA, 2) + Math.pow(widthRadiusB*pointYA, 2) <= Math.pow(widthRadiusB*heightRadiusB, 2));
		}
		else {
			return Math.pow(widthRadiusB*pointXA, 2) + Math.pow(heightRadiusB*pointYA, 2) <= Math.pow(widthRadiusB*heightRadiusB, 2);
		}
	}
}
