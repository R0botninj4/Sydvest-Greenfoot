import greenfoot.*;
import java.util.List;

public class Fireball extends Actor {
    private Actor target;

    public Fireball(Actor targetActor) {
        target = targetActor;
    }

    public void act() {
        // Fjern fireball hvis target er væk
        if (target == null || target.getWorld() == null) {
            if (getWorld() != null) {
                getWorld().removeObject(this);
            }
            return;
        }

        // Flyv mod target
        turnTowards(target.getX(), target.getY());
        move(4);

        // Tjek kollision med fjender
        List<Actor> hitEnemies = getIntersectingObjects(Actor.class);

        for (Actor enemy : hitEnemies) {
            if (enemy.getWorld() == null) continue;

            // Ram Dragon
            if (enemy instanceof Dragon) {
                getWorld().removeObject(enemy); // fjern Dragon
                if (getWorld() != null) getWorld().removeObject(this); // fjern fireball

                GreenfootSound shootEffect = new GreenfootSound("DragonDeath.mp3");
                shootEffect.play();
                MyWorld.killCounter.add(1); // +1 kill
                break;
            }

            // Ram Boss
            if (enemy instanceof Boss) {
                ((Boss) enemy).takeDamage(1); // træk 1 HP fra Boss
                if (getWorld() != null) getWorld().removeObject(this); // fjern fireball

                break;
            }
        }
    }
}
