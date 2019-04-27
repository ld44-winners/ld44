package net.bobmandude9889.World;

import java.awt.image.BufferedImage;

public interface Entity {
	
	public Location getLocation();
	public void setLocation(Location location);
	public Velocity getVelocity();
	public void setVelocity(Velocity velocity);
	public BufferedImage getImage();
	public Size getSize();
	public double getRotation();
	
}
