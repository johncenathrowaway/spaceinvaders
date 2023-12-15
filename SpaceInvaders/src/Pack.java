import java.util.*;
import objectdraw.*;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class Pack extends ActiveObject {

    protected static final int ALIENS_PER_ROW = 9;
    protected static final int NUM_ROWS = 6; 
    protected static final int ALIEN_HEIGHT = 40;
    protected static final int ALIEN_WIDTH = 40;
    protected static final int ALIEN_GAP = 30;
    protected static final int TOTAL_ALIENS = ALIENS_PER_ROW * NUM_ROWS; 
    protected static final int dx = 40;
    protected static final int dy = 70;
    
    protected Aliens[] aliens;
    protected SpaceInvaders controller;
    protected boolean moving;
    protected boolean shooting;
    protected int aliensAlive;
    protected Image[] images;
    protected ArrayList<Integer> shootingAliens = new ArrayList<Integer>(Arrays.asList(5,11,17,23,29,35,41,47,53)); 
    protected double pauseLength = 1500.0;   
    protected ArrayList<Shootable> bullets;
    protected int shooter;
    protected Random random = new Random();
    protected Ship ship;
    protected boolean shot;
    

    protected DrawingCanvas canvas;

    public Pack(Image[] images, DrawingCanvas canvas, SpaceInvaders controller, Ship ship) {
    	
    	bullets = new ArrayList<Shootable>();
    	this.controller = controller;
        aliens = new Aliens[ALIENS_PER_ROW*NUM_ROWS];
        this.images = images;
        moving = true;
        shooting = true;
        
        for(int j=0;j< ALIENS_PER_ROW; j++) {
            for(int i=0;i<NUM_ROWS;i++){
                aliens[(j*NUM_ROWS)+i] = new Aliens(images[i], ship,(j+1)*ALIEN_GAP+j*ALIEN_WIDTH,
                (i+1)*ALIEN_WIDTH+i*ALIEN_GAP-25, ALIEN_WIDTH, ALIEN_HEIGHT, this, canvas) ; 
            }
        }
        aliensAlive = TOTAL_ALIENS;
 
        start();
    }
    

    public void clear(){
    	for(Aliens a: aliens ) { 
   		   a.vi.removeFromCanvas();
      }
     	shooting = false; 
    }

    public void killed(){
        aliensAlive--;
 
    }

    /*public void shoot(){
    	 if(shooting) { 
    	    	
    	    	for(int j = (int)(Math.random()*5); j>=0; j--) { 
    	    		int i = (int)(Math.random() * TOTAL_ALIENS);	
    	    		if(aliens[i].checkAlive()) {
    	    			if(shootingAliens.contains(i)) { 
    	    				bullets.add(aliens[i].Alienshoot());
    	    				pause((int)(Math.random()*10)); 
    	    			}
    	    		}
    }
    }
    }
    */
    public void shoot() {
        if (shooting) {
            for (int j = 0; j < ALIENS_PER_ROW; j++) {
                int i = getBottomAlienIndex(j);

                if (i != -1 && aliens[i].checkAlive()) {
                    // Introduce a random element to determine whether the alien shoots
                    double shootProbability = 0.1; // Adjust this probability based on your game logic
                    if (Math.random() < shootProbability) {
                        bullets.add(aliens[i].Alienshoot());
                        pause((int) (Math.random() * 10));
                    }
                }
            }
        }
    }

    // Get the index of the bottom alien in a specific column
    private int getBottomAlienIndex(int column) {
        for (int i = NUM_ROWS - 1; i >= 0; i--) {
            int index = (column * NUM_ROWS) + i;
            if (aliens[index].checkAlive()) {
                return index;
            }
        }
        return -1; // Return -1 if all aliens in the column are dead
    }

    public Shootable[] getTargets(){
        ArrayList<Shootable> targets = bullets;
        Collections.addAll(targets, aliens);
        return targets.toArray(new Shootable[0]);
    }
    
    public String getScore(){
        int maxScore = aliens.length * 10;
        int userScore = (aliens.length-aliensAlive)*10;
        return userScore + "/" + maxScore;
    }
    
    public boolean invadersAreAlive(){
        return aliensAlive>0;
    }
    
    public void moveDown() { 
    	for(Aliens a: aliens ) { 
 		   a.MoveY(dy);
    }
    }
    
    public void moveLeft() { 
    	for(Aliens a: aliens ) { 
 		   a.MoveX(-1*dx);
    }
    }
    public void moveRight() { 
    	for(Aliens a: aliens ) { 
 		   a.MoveX(dx);
    }
    }
    public void changePause() { 
    	pauseLength = pauseLength*0.75; 
    }
    
    public void run(){
    	
    	while(shooting) { 
			
	    	  // TODO: CHECK IF IT IS FRONT ROW, ONLY FRONT ROW CAN SHOOT 
		   shoot(); 
	       pause(pauseLength);
	   	  
	       for (int i = 0; i<4; i++) { 
	   	   moveRight(); 
	   	   shoot(); 
	   	   pause(pauseLength); 
	   	  } 
	   	  
	   	  moveDown();  
	   	  shoot(); 
	   	  changePause(); 
	   	  pause(pauseLength);
	   	  
	   	  for (int i = 0; i<4; i++) { 
	     	   moveLeft(); 
	     	   shoot(); 
	     	   pause(pauseLength); }
	   	  
	   	  moveDown(); 
	   	  shoot(); 
	   	  changePause();
	   	  
	   	  if (aliensAlive <= 0)  { 
	   		  shooting = false; 
	   	  }
	    	 
	     

// makes ship move in a spiral (down, left, down, right, etc) and the pause gets shorter after the aliens move down  
	   	  if(!invadersAreAlive() ) { 
	   		  controller.win(); }
	   	  else{ 
	   		  controller.lose(); 
	   	  }
    	}
    	}
} 	