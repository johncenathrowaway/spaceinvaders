import java.awt.Color;
import objectdraw.*;

public class Bullet extends ActiveObject implements Shootable{
    FilledRect bullet;
    DrawingCanvas canvas;
    double velocity;
    private final int WIDTH = 4;
    private final int HEIGHT = 12;
    protected boolean flying;
    protected int canvasHeight;
    Shootable[] targets;

    public Bullet(double x, double y, double velocity, Shootable[] targets, Color color, DrawingCanvas canvas){
        bullet = new FilledRect(x-WIDTH/2, y, WIDTH, HEIGHT, canvas);
        bullet.setColor(color);
        this.velocity = velocity;
        this.targets = targets;
        flying = true;
        canvasHeight = canvas.getHeight();
        start();
    }
    
    public void kill(){
        flying = false;
        bullet.removeFromCanvas();
    }

    public void checkCollision(){
        for(Shootable target: targets){
            if(target.checkAlive() && bullet.overlaps(target.getBody())){
            	flying=false;
            	target.kill();
            	this.kill();
            }
        }
    }

    public boolean checkAlive(){
        return flying;
    }
  
    public Drawable2DInterface getBody(){
        return bullet;
    }

    public void run(){
        while(flying){
            double currentY = bullet.getY();
            if(currentY>0&&currentY<canvasHeight){
                bullet.move(0,velocity*4/5);
                checkCollision();
            }
            else{
                this.kill();
            }
            pause(22);
        }
        
    }
}