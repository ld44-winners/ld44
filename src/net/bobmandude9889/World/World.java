package net.bobmandude9889.World;

import java.util.ArrayList;
import java.util.List;

public class World {

	public Map map;

	public List<Entity> entityList;
	
	public World(Map map){
		this.map = map;
		this.entityList = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity entity){
		this.entityList.add(entity);
	}
	
	public void tickVelocity() {
		for (Entity e : entityList) {
			Velocity v = e.getVelocity();
			if(v != null)
				e.setLocation(e.getLocation().plus(v.x, v.y));
		}
	}
	
}
