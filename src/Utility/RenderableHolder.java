package Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.image.Image;
import model.IRenderable;

public class RenderableHolder {
	private List<IRenderable> entities;
	private Comparator<IRenderable> comparator;
	//public static Image star;
	//public static Image[] arrow;
	public final static RenderableHolder instance = new RenderableHolder();
	
	public RenderableHolder(){
		entities = new ArrayList<IRenderable>();
		comparator = (IRenderable o1, IRenderable o2) -> {
			if (o1.getZ() > o2.getZ())
				return 1;
			return -1;
		};
		//arrow = new Image[4];
	}
	static{
		loadResource();
	}
	
	public void sort() {
		instance.getEntities().sort(comparator);
	}
	
	public synchronized void add(IRenderable entity){
		instance.getEntities().add(entity);
		Collections.sort(instance.getEntities(), comparator);
	}
	private static void loadResource() {
		// TODO Auto-generated method stub
		//star = new Image(ClassLoader.getSystemResource("star.png").toString());
	}
	public synchronized void remove(int index){
		//Fill in here
		instance.getEntities().remove(index);
	}

	public synchronized static RenderableHolder getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}

	public synchronized List<IRenderable> getEntities() {
		// TODO Auto-generated method stub
		return entities;
	}
}
