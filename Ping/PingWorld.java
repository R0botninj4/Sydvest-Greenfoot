import greenfoot.*;

/**
 * The Ping World is where Balls and Paddles meet to play pong.
 */
public class PingWorld extends World {
    private static final int WORLD_WIDTH = 500;
    private static final int WORLD_HEIGHT = 700;
    public static Counter Level = new Counter("Level: ");
    public static Counter Score = new Counter("Player1 Score: ");
    public static Counter Score2 = new Counter("Player2 Score: ");
    private int players; // 0, 1 eller 2 spillere
    public static int lastPlayers = 1;
    public static GreenfootSound song = new GreenfootSound ( "fot.mp3" );
    public PingWorld(int players) {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1);
        this.players = players;

        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);

        // tilføj bold
        addObject(new Ball(), WORLD_WIDTH / 2, WORLD_HEIGHT / 2);

        // spiller 1 (altid human, styrer med A/D)
        addObject(new Paddle(100, 20, players), WORLD_WIDTH / 2, WORLD_HEIGHT - 50);

        // midt-paddle (AI)
        if (players < 2) {
        int start_Y = Greenfoot.getRandomNumber(400);
        addObject(new Paddle2(100, 10), WORLD_WIDTH / 2, start_Y);
        }

        // spiller 2 eller AI (øverst)
        Paddle3 paddle3 = new Paddle3(100, 20, players);
        addObject(paddle3, WORLD_WIDTH / 2, 50);

        // level counter
        addObject(Level, WORLD_WIDTH - 50, 20);
        Level.setValue(1);
        addObject(Score, 70, 670);
        Score.setValue(0);
        addObject(Score2, 70, WORLD_HEIGHT-670);
        Score2.setValue(0);
        
        song.playLoop();
        
        song.setVolume ( 20 );
    }

    public int getPlayers() {
        return players;
    }
}
