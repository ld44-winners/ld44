package net.bobmandude9889.Test;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.sun.glass.events.KeyEvent;

import net.bobmandude9889.Render.Camera;
import net.bobmandude9889.Resource.ResourceLoader;
import net.bobmandude9889.Window.Key;
import net.bobmandude9889.World.Entity;
import net.bobmandude9889.World.Location;
import net.bobmandude9889.World.Size;
import net.bobmandude9889.World.Velocity;

public class Ship implements Entity{

	Location location;
	Velocity velocity;
	double rotation = 0;
	Camera camera;
	
	BufferedImage img;
	
	public Ship(Location location, Velocity velocity, Camera camera){
		this.location = location;
		this.velocity = velocity;
		this.camera = camera;
		this.img = ResourceLoader.loadImage("ship.png");
	}
	
	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;
		camera.location = this.location;
	}

	@Override
	public Velocity getVelocity() {
		if (Key.isPressed(KeyEvent.VK_LEFT)) {
			rotation -= 1;
		} else if (Key.isPressed(KeyEvent.VK_RIGHT)) {
			rotation += 1;
		}
		if (rotation < 0)
			rotation = 360 + rotation;
		if (rotation > 360)
			rotation -= 360;
		
		return velocity;
	}

	@Override
	public void setVelocity(Velocity velocity) {
		this.velocity = velocity;
	}

	@Override
	public BufferedImage getImage() {
//		double rotationRequired = Math.toRadians(rotation);
//		double locationX = img.getWidth() / 2;
//		double locationY = img.getHeight() / 2;
//		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
//		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//		return op.filter(img, null);
		return img;
	}

	@Override
	public Size getSize() {
//		double sinRot = Math.abs(Math.sin(Math.toRadians(rotation)));
//		double cosRot = Math.abs(Math.cos(Math.toRadians(rotation)));
//		Size size = new Size(sinRot + cosRot,sinRot + cosRot);
//		System.out.println(size.width + "," + size.height);
//		return size;
		return new Size(1,1);
	}
	
	@Override
	public double getRotation() {
		return rotation;
	}

}
