import greenfoot.*;


/**
 * A paddle is an object that goes back and forth. Though it would be nice if balls would bounce of it.
 * 
 * @author The teachers 
 * @version 1
 */
public class Paddle2 extends Actor
{
    private int width;
    private int height;
    private int dx;
    private int speed;
    private int max_Y; 
    private int is_Y;
    private int min_Y; 
    
   

    /**
     * Constructs a new paddle with the given dimensions.
     */
    public Paddle2(int width, int height)
    {
        this.width = width;
        this.height = height;
        dx = 1;
        createImage();
        speed = 3;
        max_Y = 400;
        min_Y = 200;
    }

    /**
     * Act - do whatever the CopyOfPaddle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        tryChangeDirection();
        setLocation(getX() + dx, getY());
        //moving();
    }    

    
    
    private void moving()
    {
        if (Greenfoot.isKeyDown("w")){
        move(speed);
        }
        if (Greenfoot.isKeyDown("s")){
        move(-speed);
        }   
    }
    /**
     * Will rotate the paddle 180 degrees if the paddle is at worlds edge.
     */
    private void tryChangeDirection()
    {
        //Check to see if we are touching the outer boundaries of the world:
        // IF we are touching the right boundary OR we are touching the left boundary:
        if(getX() + width/2 >= getWorld().getWidth() || getX() - width/2 <= 0)
        {
            //Change our 'x' direction to the inverted direction:
            dx = dx * -1;
            int is_Y = Greenfoot.getRandomNumber(max_Y - min_Y + 1) + min_Y;
            setLocation(getX(), is_Y);
        }
    }
    private GreenfootImage paddle3 = new GreenfootImage("paddle3.png");
    /**
     * Creates and sets an image for the paddle, the image will have the same dimensions as the paddles width and height.
     */
    private void createImage()
    {
        setImage(paddle3);
    }

}
