import greenfoot.*;

public class Win extends World
{
    private int timer = 0;
    private int waitTime = 1000; // antal act-calls (~5 sekunder ved 60 fps)
    private GreenfootImage backgroundImage;

    public Win()
    {    
        super(500, 700, 1); 
        // Gem original baggrund
        backgroundImage = new GreenfootImage(getBackground());
        updateText(5); // start med 5 sekunders nedtælling
    }

    public void act()
    {
        timer++;

        int countdown = (waitTime - timer) / 60; // konverter til sekunder
        if (countdown < 0) countdown = 0;

        updateText(countdown);

        // restart hvis Enter trykkes eller tid udløber
        if (Greenfoot.isKeyDown("enter")) {
            Greenfoot.setWorld(new PingWorld(PingWorld.lastPlayers));
        }
        else if (timer > waitTime) {
            Greenfoot.setWorld(new IntroWorld());
        }
    }

    private void updateText(int seconds)
    {
        // Lav en kopi af original baggrund
        GreenfootImage bg = new GreenfootImage(backgroundImage);

        bg.setColor(greenfoot.Color.YELLOW);
        String message = "EZ Win! Press ENTER or wait " + seconds + "s";
        
        // Brug standard Greenfoot font
        int textWidth = message.length() * 8; // ca. bredde i pixels
        bg.drawString(message, (getWidth() - textWidth)/2, 50); // centreret øverst

        setBackground(bg);
    }
}
