import greenfoot.*;


/**
 * A Ball is a thing that bounces of walls and paddles (or at least i should).
 * 
 * @author The teachers 
 * @version 1
 */
public class Ball extends Actor
{
    private static final int BALL_SIZE = 25;
    private static final int BOUNCE_DEVIANCE_MAX = 5;
    private static final int STARTING_ANGLE_WIDTH = 90;
    private static final int DELAY_TIME = 100;

    private int speed;
    private boolean hasBouncedHorizontally;
    private boolean hasBouncedVertically;
    private int delay;
    private int bouncecount = 1;

    /**
     * Contructs the ball and sets it in motion!
     */
    public Ball()
    {
        createImage();
        init();
    }
    private GreenfootImage ball = new GreenfootImage("ball.png");
    /**
     * Creates and sets an image of a black ball to this actor.
     */
    private void createImage()
    {
        //GreenfootImage ballImage = new GreenfootImage(BALL_SIZE,BALL_SIZE);
        //ballImage.setColor(Color.BLACK);
        //ballImage.fillOval(0, 0, BALL_SIZE, BALL_SIZE);
        //setImage(ballImage);
        setImage(ball);
    }

    /**
     * Act - do whatever the Ball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (delay > 0)
        {
            delay--;
        }
        else
        {
            moveBall();
            checkBounceOffPaddle();
            checkBounceOffWalls();
            //checkBounceOffCeiling();
            checkRestart();
            
        }
        
        
    }    

    /**
     * Returns true if the ball is touching one of the side walls.
     */
    private boolean isTouchingSides()
    {
        return (getX() <= BALL_SIZE/2 || getX() >= getWorld().getWidth() - BALL_SIZE/2);
    }

    /**
     * Returns true if the ball is touching the ceiling.
     */
    private boolean isTouchingCeiling()
    {
        return (getY() <= BALL_SIZE/2);
    }

    /**
     * Returns true if the ball is touching the floor.
     */
    private boolean isTouchingFloor()
    { 
        return (getY() >= getWorld().getHeight() - BALL_SIZE/2);
    }
    /**
     * Returns true if the ball is touching the player.
     */
    private boolean isTouchingPaddle() {
        return (getOneIntersectingObject(Paddle.class) != null);
    }

    /**
     * Check to see if the ball should bounce off one of the walls.
     * If touching one of the walls, the ball is bouncing off.
     */
    private void checkBounceOffWalls()
    {
        if (isTouchingSides())
        {
            Greenfoot.playSound("hit.mp3");
            if (! hasBouncedHorizontally)
            {
                revertHorizontally();
            }
        }
        else
        {
            hasBouncedHorizontally = false;
        }
    }

    /**
     * Check to see if the ball should bounce off the ceiling.
     * If touching the ceiling the ball is bouncing off.
     */
    private void checkBounceOffCeiling()
    {
        Paddle2 paddle2 = (Paddle2) getOneIntersectingObject(Paddle2.class);
        if (isTouchingCeiling())
        {
            if (! hasBouncedVertically)
            {
                revertVertically();
            }
        }
        else
        {
            hasBouncedVertically = false;
        }
    }
        void checkBounceOffPaddle() {
        double verticalMotion = Math.sin(Math.toRadians(getRotation()));
    
        // --- Paddle (bund) ---
        Paddle paddle = (Paddle) getOneIntersectingObject(Paddle.class);
        if (paddle != null && !hasBouncedVertically && verticalMotion > 0) {
            int deltaX = getX() - paddle.getX();
            double factor = (double) deltaX / (paddle.getImage().getWidth() / 2);
            int angle = 270 + (int)(factor * 60); // bounce up
            setRotation(angle);
            hasBouncedVertically = true;
            bouncecount++;
            if (bouncecount % 10 == 0) {
                speed = speed * 2;
                PingWorld.Level.add(1);
            }
        
        }

    
        // --- Paddle2 (midt) ---
        Paddle2 paddle2 = (Paddle2) getOneIntersectingObject(Paddle2.class);
        if (paddle2 != null && !hasBouncedVertically && verticalMotion < 0) {
            int deltaX = getX() - paddle2.  getX();
            double factor = (double) deltaX / (paddle2.getImage().getWidth() / 2);
            int angle = 90 + (int)(factor * 60); // bounce opad
            setRotation(angle);
            hasBouncedVertically = true;
        }
    
        // --- Paddle3 (top) ---
        Paddle3 paddle3 = (Paddle3) getOneIntersectingObject(Paddle3.class);
        if (paddle3 != null && !hasBouncedVertically && verticalMotion < 0) {
            int deltaX = getX() - paddle3.getX();
            double factor = (double) deltaX / (paddle3.getImage().getWidth() / 2);
            int angle = 90 + (int)(factor * 60); // bounce down
            setRotation(angle);
            hasBouncedVertically = true;
        }


    
        // --- Ingen paddle ramt ---
        if (paddle == null && paddle2 == null && paddle3 == null) {
            hasBouncedVertically = false;
        }
    }







    /**
     * Check to see if the ball should be restarted.
     * If touching the floor the ball is restarted in initial position and speed.
     */
    private void checkRestart()
    {
    PingWorld world = (PingWorld) getWorld(); // hent reference til verdenen

    if (isTouchingFloor()) {
        PingWorld.Score.add(1); // player 1 scorer
        if (world.getPlayers() < 2) { // kun game over hvis < 2 players
            Greenfoot.setWorld(new GameOverWorld());
            Greenfoot.playSound("gameover.mp3");
            PingWorld.song.stop();
        } else {
            init(); // reset bolden
            setLocation(world.getWidth() / 2, world.getHeight() / 2);
        }
    }
    
    if (isTouchingCeiling()) {
        PingWorld.Score2.add(1); // player 2 scorer
        if (world.getPlayers() < 2) {
            Greenfoot.setWorld(new Win());
            Greenfoot.playSound("Win.mp3");
            PingWorld.song.stop();
        } else {
            init(); // reset bolden
            setLocation(world.getWidth() / 2, world.getHeight() / 2);
        }
    }
    }


        private void revertVertically()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX) - BOUNCE_DEVIANCE_MAX / 2;
        int newAngle = (360 - getRotation() + randomness + 360) % 360;
    
        // avoid near-perfect vertical angles
        if (newAngle % 180 < 15 || newAngle % 180 > 165) {
            newAngle += 20 * (Greenfoot.getRandomNumber(2) == 0 ? 1 : -1);
        }
    
        setRotation(newAngle);
        hasBouncedVertically = true;
        }
    
    private void revertHorizontally()
    {
        int randomness = Greenfoot.getRandomNumber(BOUNCE_DEVIANCE_MAX) - BOUNCE_DEVIANCE_MAX / 2;
        int newAngle = (180 - getRotation() + randomness + 360) % 360;
    
        // avoid near-perfect vertical angles
        if (newAngle % 180 < 15 || newAngle % 180 > 165) {
            newAngle += 20 * (Greenfoot.getRandomNumber(2) == 0 ? 1 : -1);
        }
    
        setRotation(newAngle);
        hasBouncedHorizontally = true;
    }

    private void moveBall()
    {   
        double angle = Math.toRadians(getRotation());
        int dx = (int)Math.round(Math.cos(angle) * speed);
        int dy = (int)Math.round(Math.sin(angle) * speed);
        setLocation(getX() + dx, getY() + dy);
    }

    
    /**
     * Initialize the ball settings.
     */
    private void init()
    {
        speed = 2;
        delay = DELAY_TIME;
        hasBouncedHorizontally = false;
        hasBouncedVertically = false;
        setRotation(Greenfoot.getRandomNumber(STARTING_ANGLE_WIDTH)+STARTING_ANGLE_WIDTH/2);
    }

}
