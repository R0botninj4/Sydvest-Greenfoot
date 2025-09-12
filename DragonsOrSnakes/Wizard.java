import greenfoot.*;
import java.util.List;

public class Wizard extends Actor
{
    private static final int MAX_COOLDOWN = 50;
    private int cooldown = MAX_COOLDOWN;
    private int lives = 3;
    private int invincibleTimer = 0;
    private static final int INVINCIBLE_DURATION = 50;

    private GreenfootImage wizNormal = new GreenfootImage("wiz.png");
    private GreenfootImage wizFlipped = new GreenfootImage("wiz_flip.png");    

    public void act() {
        // cooldown og immunitet
        cooldown--;  
        if (invincibleTimer > 0) {
            invincibleTimer--;
        }

        // Skyde
        if (Greenfoot.isKeyDown("space") && cooldown <= 0) {
            GreenfootSound shootEffect = new GreenfootSound("FireBall.mp3");
            shootEffect.setVolume(100);
            shootEffect.play();

            Actor nearestEnemy = getNearestEnemy();
            if (nearestEnemy != null) {
                Fireball f = new Fireball(nearestEnemy);
                getWorld().addObject(f, getX(), getY());
                cooldown = MAX_COOLDOWN;
            }
        }

        // Tjek kollision med fjender
        if (isTouchingEnemy() && invincibleTimer == 0) {
            hitByEnemy();
            return;
        }

        // Bevægelse
        moving();
    }   

    private void moving() {
        if (Greenfoot.isKeyDown("a")) {
            setRotation(0);
            move(-5);
            setImage(wizFlipped);
        }

        if (Greenfoot.isKeyDown("d")) {
            setRotation(0);
            move(5);
            setImage(wizNormal);
        }

        if (Greenfoot.isKeyDown("w")) {
            setLocation(getX(), getY() - 3);
        }

        if (Greenfoot.isKeyDown("s")) {
            setLocation(getX(), getY() + 3);
        }
    }

    // Find nærmeste fjende (Dragon eller Boss)
    private Actor getNearestEnemy() {
    Actor nearest = null;
    double nearestDistance = Double.MAX_VALUE;

    // Tjek både Dragon og Boss
    for (Actor a : getWorld().getObjects(Dragon.class)) {
        double dist = Math.hypot(getX() - a.getX(), getY() - a.getY());
        if (dist < nearestDistance) {
            nearestDistance = dist;
            nearest = a;
        }
    }

    for (Actor a : getWorld().getObjects(Boss.class)) {
        double dist = Math.hypot(getX() - a.getX(), getY() - a.getY());
        if (dist < nearestDistance) {
            nearestDistance = dist;
            nearest = a;
        }
    }

    return nearest;
}


    // Tjek om Wizard rører en fjende
    private boolean isTouchingEnemy() {
        Actor enemy = getOneObjectAtOffset(0, 0, Actor.class);
        return (enemy instanceof Dragon || enemy instanceof Boss);
    }

    // Wizard rammes af fjende
    private void hitByEnemy() {
        lives--;  
        invincibleTimer = INVINCIBLE_DURATION; // midlertidig immunitet

        if (lives <= 0) {
            MyWorld.Liv.add(-1); // fjern liv
            die();
        } else {
            hitByEnemyLiv();
        }    
    }

    private void hitByEnemyLiv() {
        MyWorld.Liv.add(-1); // træk 1 liv
    }

    private void die() {
        World world = getWorld();   
        world.removeObject(this);

        GameOver g = new GameOver();
        world.addObject(g, 500, 250);
        Greenfoot.stop();
    }
    public void hitByFlame() {
        if (invincibleTimer > 0) return; // midlertidigt immune
    
        lives--;  
        invincibleTimer = INVINCIBLE_DURATION; // gør wizard midlertidigt immune
    
        if (lives <= 0) {
            MyWorld.Liv.add(-1); // fjern liv
            die();
        } else {
            hitByEnemyLiv(); // træk et liv fra UI
        }
    }

}
