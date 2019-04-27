package net.bobmandude9889.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

import net.bobmandude9889.Render.Camera;
import net.bobmandude9889.Resource.ResourceLoader;
import net.bobmandude9889.Window.Window;
import net.bobmandude9889.World.Location;
import net.bobmandude9889.World.OrthogonalMapLoader;
import net.bobmandude9889.World.Velocity;
import net.bobmandude9889.World.World;

public class Main {
	
	static World world;
	static Camera camera;
	static Window window;
	
	static long lastUpdate;
	static long lastTick;
	
	static long fps = 60;

	public static void main(String[] args){
		world = new World(OrthogonalMapLoader.load("map.tmx"));
		camera = new Camera(new Location(0,0),50, world);
		window = new Window(1066,600,"Game", camera, ResourceLoader.loadImage("space.png"));
		window.addKeyListener(new KeyHandler(camera,window));
		//Sound sound = ResourceLoader.loadSound("sound.wav");
		//sound.play(50f);
		Ship ship = new Ship(new Location(2,2), new Velocity(0,0),camera);
		world.addEntity(ship);
		startLoop();
	}
	
	public static void startLoop() {
		lastUpdate = System.currentTimeMillis();
		lastTick = lastUpdate;
		while (true) {
			if (System.currentTimeMillis() - lastUpdate >= 1000/fps) {
				try {
					lastUpdate = System.currentTimeMillis();
					window.display.paintImmediately(0, 0, window.display.getWidth(), window.display.getHeight());
					if(System.currentTimeMillis() - lastTick > (1000/20)) {
						camera.world.tickVelocity();
						lastTick = lastUpdate;
					}
				} catch(Exception e) {
					e.printStackTrace();
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					String stack = sw.toString();
					JOptionPane.showMessageDialog(null, stack + "\n\nPlease post this message on the games page");
					System.exit(0);
				}
			}
		}
	}
	
}
