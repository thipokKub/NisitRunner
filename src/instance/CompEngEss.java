package instance;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Utility.ProgressHolder;
import gui.GameScene;
import javafx.scene.input.KeyCode;
import model.Character;
import model.Obstacle;
import model.ResourceException;

public class CompEngEss extends model.Character{
	
	private int frameCounter;
	private final int totalTimeMove;
	private ArrayList<Obstacle> Activity;
	
	public CompEngEss(int x, int y) throws ResourceException {
		super(x, y, new ArrayList<String>() {
			{
				add("resource/character/danger/monster/comengess/frame0.png");
				add("resource/character/danger/monster/comengess/frame1.png");
			}});
		frameCounter = 0;
		totalTimeMove = (int)(0.5*ProgressHolder.frameRate); //unit in frame
		// TODO Auto-generated constructor stub
		Activity = new ArrayList<Obstacle>() {
			{
				add(new Obstacle(-10, -10, "Activity"));
				add(new Obstacle(-10, -10, "Activity"));
				add(new Obstacle(-10, -10, "Activity"));
				add(new Obstacle(-10, -10, "Activity"));
				add(new Obstacle(-10, -10, "Quiz"));
				add(new Obstacle(-10, -10, "Quiz"));
			}};
	}
	
	public Obstacle releaseObstacle() {
		if(Activity.size() > 0) {
			int index = ThreadLocalRandom.current().nextInt(0, Activity.size());
			Obstacle tmp = Activity.get(index);
			Activity.remove(index);
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
		Activity = new ArrayList<Obstacle>() {
			{
				add(new Obstacle(-10, -10, "Activity"));
				add(new Obstacle(-10, -10, "Activity"));
				add(new Obstacle(-10, -10, "Activity"));
				add(new Obstacle(-10, -10, "Activity"));
				add(new Obstacle(-10, -10, "Quiz"));
				add(new Obstacle(-10, -10, "Quiz"));
			}};
		this.destroy = false;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
		if(frameCounter < totalTimeMove/2) translateX(-1);
		else translateX(1);
		frameCounter = (frameCounter+1)%totalTimeMove;
		if(Activity.size() == 0) {
			translateY(-5);
			if(y+getHeight() <= 0) {
				destroy = true;
			}
		}
	}

}
