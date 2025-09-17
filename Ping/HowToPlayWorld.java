import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HowToPlayWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HowToPlayWorld extends World
{

    /**
     * Constructor for objects of class HowToPlayWorld.
     * 
     */
    public HowToPlayWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(500, 700, 1); 
    }


    public void act() {
    
        if (Greenfoot.isKeyDown("Enter")) {
            Greenfoot.setWorld(new IntroWorld());
        }
        GreenfootImage bg = new GreenfootImage("HowToPlay.png");

        bg.setColor(greenfoot.Color.YELLOW);
        String message = "Press ENTER to go Back";
        
        // Brug standard Greenfoot font
        int textWidth = message.length() * 8; // ca. bredde i pixels
        bg.drawString(message, (getWidth() - textWidth)/2, 50); // centreret øverst
        setBackground(bg);
    }
}