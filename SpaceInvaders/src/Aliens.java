import java.awt.*;
import objectdraw.*;

public class Aliens extends ActiveObject implements Shootable{

	protected VisibleImage vi; 
	protected DrawingCanvas c; 
	protected FilledRect t; 
	Shootable ship;
	Shootable[] shippy = new Shootable[1];
	boolean alive;
	Pack pack;

	
	public Aliens(Image i, Ship ship, double x, double y, double h, double w, Pack pack, DrawingCanvas c) { 
		this.c = c; 
		this.ship = ship;
		this.pack = pack;
		shippy[0] = ship;
		vi = new VisibleImage(i,x,y,h,w,c);
		alive = true;
		start(); //initializes stuff 
	}
	
	
	public void kill(){
        alive = false;
        vi.hide(); 
        pack.killed();
    }
	
	public boolean checkAlive(){
		return alive;
	}
	
	public Drawable2DInterface getBody(){
        return vi;
    }
	
	public void MoveX(int dx) { 
		vi.move(dx, 0);
	}
	
	public void MoveY(int dy) { 
		vi.move(0, dy);
	}
	
	// shoot method 
	public Bullet Alienshoot() { 
		return new Bullet(vi.getX()+vi.getWidth()/2, vi.getY()+vi.getHeight(), 10, shippy, Color.YELLOW, c); 
	}
	

	
	
	
}