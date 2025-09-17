import greenfoot.*;

public class Paddle extends Actor {
    private int width;
    private int height;
    private int speed;
    private boolean chasingBall;
    private int targetX;
    private int players; // hvor mange spillere der blev valgt
    private GreenfootImage player = new GreenfootImage("player1.png");

    public Paddle(int width, int height, int players) {
        this.width = width;
        this.height = height;
        this.players = players;
        this.speed = 3;
        this.chasingBall = false;
        this.targetX = getWorldCenterX();
        setImage(player);
    }

    public void act() {
        if (getWorld() == null) return;

        if (players == 0) {
            handleAIControl();
        } else {
            handlePlayerControl();
        }
    }

    private void handlePlayerControl() {
        if (Greenfoot.isKeyDown("d")) {
            move(speed);
        }
        if (Greenfoot.isKeyDown("a")) {
            move(-speed);
        }
    }

    private void handleAIControl() {
        if (getWorld().getObjects(Ball.class).isEmpty()) return;

        Ball ball = (Ball) getWorld().getObjects(Ball.class).get(0);

        // tjek om bolden er på vej ned (mod Paddle i bunden)
        double verticalMotion = Math.sin(Math.toRadians(ball.getRotation()));

        if (verticalMotion > 0) { 
            // bolden bevæger sig nedad → jagt bolden
            chasingBall = true;
            targetX = ball.getX();
        } else if (chasingBall) {
            // bolden er vendt → tilbage til midten
            chasingBall = false;
            targetX = getWorldCenterX();
        }

        moveTowardsTarget();
    }

    private void moveTowardsTarget() {
        if (Math.abs(getX() - targetX) > speed) {
            if (getX() < targetX) {
                setLocation(getX() + speed, getY());
            } else {
                setLocation(getX() - speed, getY());
            }
        }
    }

    private int getWorldCenterX() {
        if (getWorld() != null) {
            return getWorld().getWidth() / 2;
        }
        return 250; // fallback
    }
}
