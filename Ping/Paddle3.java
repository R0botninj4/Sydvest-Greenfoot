import greenfoot.*;

public class Paddle3 extends Actor {
    private int speed;
    private boolean chasingBall;
    private int targetX;
    private int players; // hvor mange spillere der blev valgt

    public Paddle3(int width, int height, int players) {
        setImage(new GreenfootImage("player2.png"));
        speed = 3;
        chasingBall = false;
        this.players = players;
        targetX = getWorldCenterX(); // start i midten
    }

    public void act() {
        if (getWorld() == null) return;

        // hvis 2-player → menneske styrer med piletaster
        if (players == 2) {
            handlePlayerControl();
        } else {
            handleAIControl();
        }
    }

    private void handlePlayerControl() {
        if (Greenfoot.isKeyDown("right")) {
            move(speed);
        }
        if (Greenfoot.isKeyDown("left")) {
            move(-speed);
        }
    }

    private void handleAIControl() {
        if (getWorld().getObjects(Ball.class).isEmpty()) return;

        Ball ball = (Ball) getWorld().getObjects(Ball.class).get(0);

        // tjek om bolden er på vej op (mod paddle3)
        double verticalMotion = Math.sin(Math.toRadians(ball.getRotation()));

        if (verticalMotion < 0) {
            // jagt bolden
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
