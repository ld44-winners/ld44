package net.bobmandude9889.Render;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

import net.bobmandude9889.Window.Window;
import net.bobmandude9889.World.Entity;
import net.bobmandude9889.World.Location;
import net.bobmandude9889.World.OrthogonalLayer;
import net.bobmandude9889.World.OrthogonalMap;
import net.bobmandude9889.World.Size;
import net.bobmandude9889.World.Tile;
import net.bobmandude9889.World.World;

public class Camera {

	public Location location;
	public int zoom;
	public World world;

	public Camera(Location loc, int zoom, World world) {
		this.location = loc;
		this.zoom = zoom;
		this.world = world;
	}

	public void move(Location loc) {
		this.location = loc;
	}

	public Location getLocation() {
		return location;
	}

	public Location getTopLeft(Window window, Dimension dimension) {
		return location.plus(-((double) dimension.width / 2) / zoom, -((double) dimension.height / 2) / zoom);
	}

	public void render(Graphics g, Window window, Dimension dimension) {
		Image background = window.getBackgroundImage();
		Image buffer = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bg = (Graphics2D) buffer.getGraphics();
		bg.drawImage(background, 0, 0, dimension.width, dimension.height, null);
		Location loc = getTopLeft(window, dimension);
		if (world.map instanceof OrthogonalMap) {
			OrthogonalMap map = (OrthogonalMap) world.map;
			// Render tiles
			for (int i = 0; i < map.layers.length; i++) {
				OrthogonalLayer layer = map.layers[i];
				int renderToX = (int) (Math.ceil(loc.x + 2) + Math.ceil((dimension.width / zoom)));
				int startingX = (int) getTopLeft(window, dimension).x;
				if (startingX < 0)
					startingX = 0;
				for (int x = startingX; x < (renderToX > map.width ? map.width : renderToX); x++) {
					int renderToY = (int) (Math.ceil(loc.y + 2) + Math.ceil((dimension.height / zoom)));
					int startingY = (int) getTopLeft(window, dimension).y;
					if (startingY < 0)
						startingY = 0;
					for (int y = startingY; y < (renderToY > map.height ? map.height : renderToY); y++) {
						if (layer != null) {
							Tile t = layer.getTile(x, y);
							if (t != null) {
								Location topLeft = getTopLeft(window, dimension);
								Point render = new Point((int) (zoom * (x - topLeft.x)), (int) (zoom * (y - topLeft.y)));
								Image img = t.getImage();
								Image scaled = img.getScaledInstance(zoom, zoom, Image.SCALE_REPLICATE);
								bg.drawImage(scaled, render.x, render.y, zoom, zoom, null);
							}
						}
					}
				}
			}
		}
		// Render Entities
		List<Entity> entityList = world.entityList;
		for (Entity e : entityList) {
			Location eLoc = e.getLocation();
			Location topLeft = getTopLeft(window, dimension);
			Point render = new Point((int) (this.zoom * (eLoc.x - topLeft.x)), (int) (this.zoom * (eLoc.y - topLeft.y)));
			BufferedImage img = e.getImage();
			Size size = e.getSize();
			Dimension imgSize = new Dimension((int) (this.zoom * size.width), (int) (this.zoom * size.height));
			BufferedImage rotImage = new BufferedImage(imgSize.width, imgSize.height, BufferedImage.TYPE_INT_ARGB);
			rotImage.getGraphics().drawImage(img, 0, 0, imgSize.width, imgSize.height, null);
			AffineTransform rt = AffineTransform.getRotateInstance(Math.toRadians(e.getRotation()), rotImage.getWidth() / 2, rotImage.getHeight() / 2);
			AffineTransformOp rtOp = new AffineTransformOp(rt, AffineTransformOp.TYPE_BILINEAR);
			rotImage = rtOp.filter(rotImage, null);
			if (render.x + imgSize.width > 0 && render.y + imgSize.getHeight() > 0 && render.x < dimension.width && render.y < dimension.height) {
//				double rotRad = Math.toRadians(e.getRotation());
//				Dimension rotSize = new Dimension((int) (imgSize.width * Math.abs(Math.sin(rotRad)) + imgSize.height * Math.abs(Math.cos(rotRad))), (int) (imgSize.height * Math.abs(Math.sin(rotRad)) + imgSize.width * Math.abs(Math.cos(rotRad))));
//				Point center = new Point(render.x + imgSize.width / 2, render.y + imgSize.height / 2);
//				Point rotRender = new Point(center.x - rotSize.width / 2, center.y - rotSize.height / 2);
//				AffineTransform backup = bg.getTransform();
//				AffineTransform a = AffineTransform.getRotateInstance(rotRad, center.x, center.y);
//				bg.setTransform(a);
				bg.drawImage(rotImage, render.x, render.y, null);
				//bg.setTransform(backup);
			}
		}

		g.drawImage(buffer, 0, 0, null);
	}

}
