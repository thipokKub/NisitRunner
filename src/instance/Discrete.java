package instance;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Utility.ProgressHolder;
import javafx.scene.input.KeyCode;
import model.Character;
import model.Obstacle;
import model.ResourceException;

public class Discrete extends model.Character{
	
	private int frameCounter;
	private final int totalTimeMove;
	public int objectToRelease;
	private ArrayList<Obstacle> Obstacle;
	
	public Discrete(int x, int y) throws ResourceException {
		super(x, y, new ArrayList<String>() {
			{
				add("resource/character/danger/monster/discrete/frame0.png");
				add("resource/character/danger/monster/discrete/frame1.png");
			}});
		frameCounter = 0;
		totalTimeMove = (int)(0.5*ProgressHolder.frameRate); //unit in frame
		objectToRelease = 5; //5 Activity
		// TODO Auto-generated constructor stub
		Obstacle = new ArrayList<Obstacle>() {
			{
				add(new Obstacle(-10, -10, "Midterm"));
				add(new Obstacle(-10, -10, "Final"));
			}};
	}
	
	public Obstacle releaseObstacle() {
		
		if(Obstacle.size() > 0) {
			Obstacle tmp = Obstacle.get(0);
			Obstacle.remove(0);
			tmp.setX(ProgressHolder.monsterPositionX);
			tmp.setY(ProgressHolder.monsterPositionY);
			tmp.setZ(1015);
			tmp.resizeByHeight(ProgressHolder.characterSize/2);
			return tmp;
		}
		
		return null;
	}

	@Override
	public void myCallBackOnPressedKeyBoard(KeyCode e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void myCallBackOnReleaseKeyBoard(KeyCode e) {
		// TODO Auto-generated method stub
		
	}
	
	public void resetToo() throws ResourceException {
		this.reset();
		Obstacle = new ArrayList<Obstacle>() {
			{
				add(new Obstacle(-10, -10, "Midterm"));
				add(new Obstacle(-10, -10, "Final"));
			}};
		this.destroy = false;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if(frameCounter < totalTimeMove/2) translateX(-1);
		else translateX(1);
		frameCounter = (frameCounter+1)%totalTimeMove;
		if(Obstacle.size() == 0) {
			translateY(-5);
			if(y+getHeight() <= 0) {
				destroy = true;
			}
		}
	}

}
