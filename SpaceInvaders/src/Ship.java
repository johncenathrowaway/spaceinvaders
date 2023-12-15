import java.awt.*;
import java.util.ArrayList;

import objectdraw.*;
public class Ship extends ActiveObject implements Shootable{
	
	protected VisibleImage vi; 
	protected DrawingCanvas c; 
	protected FilledRect t; 
	Pack pack;
	
	
	public ArrayList<Bullet> bullets;
	boolean alive;
	boolean cleared;
	
	SpaceInvaders controller; 
	
	private static final int SPEED = 1;
	private int velocity; 
	
	public Ship(Image i, Pack pack, double x, double y, double h, double w,DrawingCanvas c, SpaceInvaders controller) { 
		this.c = c; 
		this.controller = controller; 
		this.pack = pack;
		vi = new VisibleImage(i,x,y,h,w,c); 
		alive = true;
		bullets = new ArrayList<Bullet>();
		cleared = false;
		start(); //initializes stuff 
	}

	public void setDirection(int direction) { 
		if (direction == 1) { 
			velocity = SPEED; // 1 = right, so keeps direction positive
		}
		else if(direction == -1) { 
			velocity = -1*SPEED;  // -1 = left, so changes to neg direction (left) 
		}
		else { 
			velocity = 0; 
		}
		
	}
	
	public void clear() {
		cleared = true;
		vi.removeFromCanvas();
	}
	
	public void shoot() {
		if(!cleared) {
		bullets.add(new Bullet(vi.getX()+vi.getWidth()/2, vi.getY(), -10, pack.getTargets(), Color.RED, c));
		}
	}
		
	
	public void kill(){
		alive = false;
		vi.hide(); 
        controller.lose(); 
    }
	
	public boolean checkAlive() {
		return alive;
	}
	
	public Drawable2DInterface getBody(){
        return vi;
    }
	
	public void setTarget(Pack pack){
		this.pack = pack;
	}
	
	public void run() { 
		while(true) { 
			double lastX = vi.getX();
            if((lastX+velocity+vi.getWidth())<c.getWidth() && (lastX+velocity)>0)  { // checks if ship is in bounds
            	vi.move(velocity, 0); // moves by designated direction 
            	pause(10); 
            }		
            if(cleared) {
            	break;
            }
            
		}
		//controller.lose();
	}
}