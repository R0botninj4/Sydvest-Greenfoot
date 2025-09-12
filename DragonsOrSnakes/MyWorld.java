import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class MyWorld extends World
{
    public static Counter killCounter = new Counter("Kills: ");
    public static Liv Liv = new Liv("HP: ");
    public GreenfootSound BossMusic = new GreenfootSound("BossMusic.mp3");
    private boolean bossSpawned = false;

    public MyWorld()
    {    
        super(1000, 600, 1); 
        
        addObject(new Wizard(), 100, 200);
        addObject(killCounter, 50, 20);
        addObject(Liv, 50, 50);
        killCounter.setValue(0);
        Liv.setValue(3);

        // Spawn lidt startdrager
        for (int i = 0; i < 5; i++) {
            int newX = Greenfoot.getRandomNumber(250)+300;
            int newY = Greenfoot.getRandomNumber(350)+25;
            addObject(new Dragon(), newX, newY);
        }
    }

    public void act()
    {
        // Hvis bossen ikke er spawnet endnu → fortsæt normal dragon spawn
        if (!bossSpawned) {
            if (Greenfoot.getRandomNumber(100) < 1) {
                randomDragon(1);
            }
        }

        // Tjek om vi skal spawne boss
        if (killCounter.getValue() >= 25 && !bossSpawned) {
            spawnBoss();
        }
    }
    
    public void randomDragon(int howMany)
    {
        for(int i=0; i<howMany; i++) {
            Dragon d = new Dragon();
            int x = Greenfoot.getRandomNumber(getWidth());
            int y = Greenfoot.getRandomNumber(getHeight()/2); // kun i toppen
            addObject(d, x, y);
        }
    }
    
    private void spawnBoss() {
        // Fjern alle drager
        List<Dragon> allDragons = getObjects(Dragon.class);
        removeObjects(allDragons);
        
        // Spawn boss i bunden
        Boss boss = new Boss();
        addObject(boss, getWidth()/2, getHeight()-50);
        BossMusic.play();
        bossSpawned = true;
    }
}
