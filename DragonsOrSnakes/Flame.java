import greenfoot.*; 

public class Flame extends Actor
{
    private int lifeTime = 75; // levetid i frames
    private Boss boss;          // reference til bossen

    public Flame(Boss boss) {
        this.boss = boss;
    }

    public void act()
    {
        // Opdater position, så flammen følger bossens X-akse
        if (boss != null && boss.getWorld() != null) {
            setLocation(boss.getX(), boss.getY() - 300); // 50 pixels over boss
        }

        // Formindsk levetiden
        lifeTime--;
        if (lifeTime <= 0) {
            getWorld().removeObject(this);
            return;
        }

        // Tjek om Wizard rører flammen
        Wizard wizard = (Wizard) getOneIntersectingObject(Wizard.class);
        if (wizard != null) {
            wizard.hitByFlame();
        }
    }
}
