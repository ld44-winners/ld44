package net.bobmandude9889.Test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.bobmandude9889.Render.Camera;
import net.bobmandude9889.Window.Key;
import net.bobmandude9889.Window.Window;

public class KeyHandler implements KeyListener {

	Camera camera;
	Window window;
	
	public KeyHandler(Camera camera, Window window) {
		this.camera = camera;
		this.window = window;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!Key.isPressed(e.getKeyCode())){
			Key.add(e.getKeyCode());
		}
		window.display.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (Key.isPressed(e.getKeyCode()))
			Key.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
