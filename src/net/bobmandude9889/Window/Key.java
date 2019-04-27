package net.bobmandude9889.Window;

import java.util.ArrayList;
import java.util.List;

public class Key {
	
	public static List<Integer> pressed = new ArrayList<Integer>();
	
	public static boolean isPressed(int key){
		return pressed.contains((Integer) key);
	}
	
	public static void add(int key){
		pressed.add(key);
	}
	
	public static void remove(int key){
		pressed.remove(pressed.indexOf(key));
	}
	
}
