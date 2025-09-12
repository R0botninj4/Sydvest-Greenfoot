import greenfoot.*;
import java.util.List;


/**
 * Write a description of class Dragon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dragon extends Actor
{ 
    public void act() {
        List<Wizard> wizards = getWorld().getObjects(Wizard.class);
        if (!wizards.isEmpty()) {
        Wizard w = wizards.get(0);   // tag den første
        turnTowards(w.getX(), w.getY());
        move(1);
    }
    }
    
}

