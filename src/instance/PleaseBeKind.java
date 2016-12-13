package instance;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import model.Character;

public class PleaseBeKind extends model.Character{

	public PleaseBeKind(int x, int y) {
		super(x, y, new ArrayList<String>() {
			{
				add("resource/character/danger/PleaseDontGiveMe/frame0.png");
				add("resource/character/danger/PleaseDontGiveMe/frame1.png");
				add("resource/character/danger/PleaseDontGiveMe/frame2.png");
				add("resource/character/danger/PleaseDontGiveMe/frame3.png");
			}});
	}

	@Override
	public void myCallBackOnPressedKeyBoard(KeyCode e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void myCallBackOnReleaseKeyBoard(KeyCode e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

}
