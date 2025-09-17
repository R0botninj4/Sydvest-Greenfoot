import greenfoot.*;

public class IntroWorld extends World {
    private static final int WORLD_WIDTH = 500;
    private static final int WORLD_HEIGHT = 700;

    public IntroWorld() {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1);
        
        GreenfootImage bg = new GreenfootImage("start.png"); // Brug billedet som base
        GreenfootImage title = new GreenfootImage("Pingus pongus the soccer game", 28, Color.YELLOW, null);
        bg.drawImage(title, (WORLD_WIDTH - title.getWidth()) / 2, 50);
        setBackground(bg);
        


        // Tilføj knapper
        addObject(new Button("0 Player"), WORLD_WIDTH / 2, 250);
        addObject(new Button("1 Player"), WORLD_WIDTH / 2, 350);
        addObject(new Button("2 Player"), WORLD_WIDTH / 2, 450);
        addObject(new Button("How to Play"), WORLD_WIDTH / 2, 550);
    }

    public void act() {
        if (Greenfoot.mouseClicked(null)) {
            Actor clicked = Greenfoot.getMouseInfo().getActor();
            if (clicked instanceof Button) {
                Button btn = (Button) clicked;
            if (btn.getText().equals("1 Player")) {
                Greenfoot.setWorld(new PingWorld(1));
            } else if (btn.getText().equals("2 Player")) {
                Greenfoot.setWorld(new PingWorld(2));
            } else if (btn.getText().equals("0 Player")) {
                Greenfoot.setWorld(new PingWorld(0));
            }    
            else if (btn.getText().equals("How to Play")) {
                Greenfoot.setWorld(new HowToPlayWorld());
            }
        }
    }
    }
}
