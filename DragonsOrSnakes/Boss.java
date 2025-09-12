import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Boss extends Actor
{
    private int speed = 1;             // bevægelse
    private int shootCooldown = 0;     // skydetimer
    private int hp = 100;              // nuværende HP
    private int maxHp = 100;           // max HP
    private int Cooldown = 200;        // Boss skydetimer

    public void act()
    {
        moveBoss();
        shootFlameAutomatically();
        updateImage(); // tegn HP-bar
    }

    private void moveBoss() {
        setLocation(getX() + speed, getY());

        if (getX() <= 0) speed = 1;
        if (getX() >= getWorld().getWidth() - 1) speed = -1;
    }

    private void shootFlameAutomatically() {
        shootCooldown++;
    
        if (shootCooldown >= Cooldown) {
            Flame flame = new Flame(this); // send reference til boss
            getWorld().addObject(flame, getX(), getY() - 300); // startposition
            shootCooldown = 0;
        }
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp <= 0) {
            die();
        }
    }
    
    private void die() {
        World world = getWorld(); // gem verdenen først
        if (world != null) {
            // Stop musikken
            ((MyWorld)world).BossMusic.stop();
    
            // Fjern bossen
            world.removeObject(this);
        }
    
        MyWorld.killCounter.add(1); // tæller kill
        Greenfoot.setWorld(new Win());
    }


    public int getHP() {
        return hp;
        }

    private void updateImage() {
        // Kopiér bossens normale billede (du skal have et billede fx "boss.png")
        GreenfootImage baseImage = new GreenfootImage("boss.png");
    
        // Tegn HP-bar ovenpå
        int barWidth = 100;
        int barHeight = 15;
        int hpWidth = (int)((hp / (double) maxHp) * barWidth);
    
        GreenfootImage hpBar = new GreenfootImage(barWidth, barHeight);
        hpBar.setColor(Color.RED);
        hpBar.fillRect(0, 0, barWidth, barHeight); // rød baggrund
    
        hpBar.setColor(Color.GREEN);
        hpBar.fillRect(0, 0, hpWidth, barHeight); // grøn HP
    
        // Sæt HP-baren ovenpå bossens billede
        baseImage.drawImage(hpBar, 50, 100); // justér y-position hvis ønsket
    
        setImage(baseImage);
    }

}
