package bman;
import java.util.jar.*;

//import org.newdawn.slick.BasicGame;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Enemy extends Entity {
	
	// Bomberman bomberman;
	 
		 private Vector velocity;
		 public Enemy(final float x, final float y, final float vx, final float vy) {
		 super(x, y);
		 addImageWithBoundingBox(ResourceManager.getImage("resource/dragon.png"));
		 velocity = new Vector(x/5000, 0);
		 }
		
		 
	 
public void setVelocity(final Vector v) {
		 velocity = v;
		}

public void destroy() {
	removeImage(ResourceManager
			 .getImage("resource/dragon.png"));	}

public void createBomb() {
	 addImageWithBoundingBox(ResourceManager.getImage("resource/dragon.png"));

}
 



public Vector getVelocity() {
		 return velocity;
		}

public void update(final int delta) {
	 translate(velocity.scale(delta));
	}

public void bounce(float surfaceTangent) {
	 velocity = velocity.bounce(surfaceTangent);
	}
}



