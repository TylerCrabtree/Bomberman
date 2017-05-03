
package bman;
import java.util.jar.*;

//import org.newdawn.slick.BasicGame;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Fire extends Entity {
	
	// Bomberman bomberman;
	 
		 private Vector velocity;
		 public Fire(final float x, final float y, final float vx, final float vy) {
		 super(x, y);
		 addImageWithBoundingBox(ResourceManager.getImage("resource/fire.png"));
		 velocity = new Vector(x/10000, 0);
		 }
		
		 
	 
public void setVelocity(final Vector v) {
		 velocity = v;
		}

public void destroy() {
	removeImage(ResourceManager
			 .getImage("resource/fire.png"));	}

public void createBomb() {
	 addImageWithBoundingBox(ResourceManager.getImage("resource/fire.png"));

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



