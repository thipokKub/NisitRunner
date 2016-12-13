package instance;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Utility.AudioUtility;
import Utility.ProgressHolder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import logic.GameManager;

public class MainCharacterMale extends model.Character {
	private String name;
	private int score;
	private KeyCode previous;
	public boolean gotHitFront, jumpUp;
	private int frameLeft, frameHPDecreaseStart, frameLag;
	
	public MainCharacterMale(int x, int y, String name) {
		super(x, y, new ArrayList<String>() {
		{
			add("resource/character/main/male/Running/frame0.png");
			add("resource/character/main/male/Running/frame1.png");
			add("resource/character/main/male/Running/frame2.png");
			add("resource/character/main/male/Running/frame3.png");
			add("resource/character/main/male/Running/frame4.png");
			add("resource/character/main/male/Running/frame5.png");
			add("resource/character/main/male/Running/frame6.png");
			add("resource/character/main/male/Running/frame7.png");
		}});
		this.name = name;
		this.score = 0;
		previous = null;
		gotHitFront = false;
		jumpUp = false;
		
		//For checking if character got to badGuy for more than frameSkip then bounce him off!
		frameLeft = 0; //track frameStart
		frameHPDecreaseStart = 0; //track for starting frame when touch the badGuy
		frameLag = 0; //For main character to blink
	}
	
	public void updateAtGameMenu() {
		translateX(ThreadLocalRandom.current().nextInt(-5, 6));
	}
	
	public void resetToo() {
		this.reset();
		gravity = 30;
		this.score = 0;
		previous = null;
		gotHitFront = false;
		jumpUp = false;
		
		//For checking if character got to badGuy for more than frameSkip then bounce him off!
		frameLeft = 0; //track frameStart
		frameHPDecreaseStart = 0; //track for starting frame when touch the badGuy
		frameLag = 0; //For main character to blink
		ProgressHolder.health = 100;
		destroy = false;
	}

	@Override
	public void myCallBackOnPressedKeyBoard(KeyCode e) {
		// TODO Auto-generated method stub
		if(e == KeyCode.SPACE) {
			jump();
		}
		if(previous != null) {
			if(e == ProgressHolder.CharacterRunKeyCodeOne && previous == ProgressHolder.CharacterRunKeyCodeTwo) {
				translateX(15);
			}
			else if(e == ProgressHolder.CharacterRunKeyCodeTwo && previous == ProgressHolder.CharacterRunKeyCodeOne) {
				translateX(10);
			}
		}
		previous = e;
	}

	@Override
	public void myCallBackOnReleaseKeyBoard(KeyCode e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if(frameCounterJump > 0) translateX(5);
	}
	
	public void gotHit(boolean gotHitFront, boolean jumpUp) {
		this.gotHitFront = gotHitFront;
		this.jumpUp = jumpUp;
		if(frameHPDecreaseStart == 0) {
			frameHPDecreaseStart = ProgressHolder.frameCounter;
		}
		if(((ProgressHolder.frameCounter - frameHPDecreaseStart) >= ProgressHolder.frameSkip)||jumpUp) {
			AudioUtility.playGotHit();
			frameLeft = 7;
			frameHPDecreaseStart = 0;
			frameLag = 20;
			if(jumpUp) jumpHurt();
		}
		
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		
		if(!((5 <= frameLag && frameLag <= 8)||(14 <= frameLag && frameLag <= 17))) {
			gc.drawImage(frameDisplay.get(frameCounter), x, y);
		}
		
		if(frameLeft != 0) {
			if(!jumpUp) {
				if(!gotHitFront) ProgressHolder.mainCharacter.translateX(frameLeft*frameLeft);
				else ProgressHolder.mainCharacter.translateX(-frameLeft*frameLeft);
			}
			frameLeft--;
		}
		
		if(frameLeft < 0) {
			frameLeft = 0;
			gotHitFront = false;
			jumpUp = false;
		}
		
		if(frameLag > 0) frameLag--;
		else frameLag = 0;
	}
}
