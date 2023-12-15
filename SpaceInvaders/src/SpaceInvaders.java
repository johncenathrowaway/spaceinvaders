import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SpaceInvaders extends WindowController implements KeyListener {


	// Constants for the window
	private static final int HEIGHT=800;
	private static final int WIDTH = 800;
	
	private static final int SHIP_WIDTH = 50;
	private static final int SHIP_HEIGHT = 50;
	
	private static final int NUM_ROWS = 6; 
	
	
	// Constants for the space ship. 
	// Move into a dedicated class?
	private Image[] alienImages; 
	private Pack pack; 
	private Ship ship;
	private Aliens alien;
	private boolean playing = false;
	private int finalScore; 
	private Image shipImage; 
	private Text clickPrompt; 
	private Text loseMessage;
	private Text winMessage; 
	private String totalPoints; 
	private String loseMessageString; 
	private String winMessageString; 
	private boolean loseOnce = false; 
	private boolean wonOnce = false; 

	// remember whether a key is currently depressed
	private boolean keyDown;
	
	public void begin() {
		
		/* This code will make it so the window cannot be resized */
	
		FilledRect background = new FilledRect(0,0,WIDTH, HEIGHT, canvas);
		background.setColor(Color.BLACK);
		
		shipImage = getImage("ship.png");
		
		clickPrompt = new Text("Click to play", canvas.getWidth()/2, canvas.getHeight()/2, canvas);
		clickPrompt.setFontSize(20);
		clickPrompt.setColor(Color.white);
		clickPrompt.moveTo(clickPrompt.getX()-clickPrompt.getWidth()/2, clickPrompt.getY());
	
		

		alienImages = new Image[NUM_ROWS];

		
		for(int i=0;i<=5;i++){
			String s = Integer.toString(i+1); 
			alienImages[i] = getImage("invader" + s +".png");
		}
		
	
		playing = false;
		
		
		requestFocus();
		addKeyListener(this);
		canvas.addKeyListener(this);
	}
	

	
	
	
	
	// Handle the arrow keys by telling the ship to go in the direction of the arrow.
	public void keyTyped(KeyEvent e)
	{
		if ( e.getKeyCode() == KeyEvent.VK_SPACE || 
				 e.getKeyCode() == KeyEvent.VK_UP ) {
	    if(playing) {	
			ship.shoot(); } 
			    // insert code to handle press of up arrow or space bar
			}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
			// insert code to handle press of left arrow
			
			ship.setDirection(-1); // sets direction of ship to move left 

		} else if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) {
			// insert code to handle press of right arrow

			ship.setDirection(1); // sets direction of ship to move right 
        }
	}

	// Remember that the key is no longer down.
	public void keyReleased(KeyEvent e)
	{
		keyDown = false;
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT || 
                    e.getKeyCode() == KeyEvent.VK_RIGHT ) {
		    // insert code to handle key release (optional stopping of base)
		
			ship.setDirection(0); // ship will stop moving 
		}
	}

	// Handle the pressing of the key the same as typing.
	public void keyPressed(KeyEvent e)
	{
		if (!keyDown)
		{
			keyTyped(e);
		}

		keyDown = true;
	}
	
	public void win() { 
		playing = false; 
		totalPoints = pack.getScore(); 
		pack.clear(); 
		ship.clear(); 
		
		winMessageString = "You Win! Click to play again.\n Total Score: " + pack.getScore(); 

		winMessage = new Text(winMessageString, canvas.getWidth()/2-300, canvas.getHeight()/2,canvas); 
		winMessage.setFontSize(20);
		winMessage.setColor(Color.white);
		winMessage.show(); // displays lose message 
		
		wonOnce = true; 
	}

	public void lose() { 
		playing = false; 
		totalPoints = pack.getScore(); 
		pack.clear(); 
		ship.clear(); 

		loseMessageString = "You Lose! Click to play again.\n Total Score: " + pack.getScore(); 

		loseMessage = new Text(loseMessageString, canvas.getWidth()/2-300, canvas.getHeight()/2,canvas); 
		loseMessage.setColor(Color.white);
		loseMessage.setFontSize(20); 
		loseMessage.show(); // display win message 
		
		loseOnce = true; 
	}
	
	public void onMouseClick(Location l) {
		
		if(!playing) { 
		ship = new Ship(shipImage, pack, WIDTH/2, HEIGHT-SHIP_HEIGHT-75, SHIP_HEIGHT, SHIP_WIDTH, canvas, this);		
		pack = new Pack(alienImages, canvas, this, ship); // restarts game 
		ship.setTarget(pack);
		clickPrompt.hide(); 
		
		if(wonOnce) { 
		winMessage.hide(); } 
		if(loseOnce) { 
		loseMessage.hide(); 
		}
		playing = true;  
		}
		
	}
	
    public static void main(String[] args) { 
        new SpaceInvaders().startController(WIDTH, HEIGHT); 
        
	}
	
}