import org.newdawn.slick.Image;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Input;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NPC{
    int health;
    int attack;
    public static float x = 100f;
    public static float y = 700f;
    public static Animation enemySprite;
    Timer movement = new Timer();
    public static int duration = 300;
    public NPC(String name) throws SlickException
    {
        int duration = 300;
        if (name.equals("placeholder"))
        {
            Image[] enemy = {new Image("Enemy.png")};
            enemySprite = new Animation(enemy, duration, false);
            //int delta = appgc.setTargetFrameRate(100);
            health = 5;
            attack = 1;
            int speed = 30;
            
            movement.scheduleAtFixedRate(new NPCMove(), 1000L, 1000L);
        }
        }
    class NPCMove extends TimerTask {
        public void run()
        {
            if (x < 800)
            {
                enemySprite.update(30);
                x += 30 * .1f;
            }
        }
    }
    
    public static void spawnEnemies(GameContainer container, Graphics g) throws SlickException
    {
        Image[] enemy = {new Image("Enemy.png")};
        NPC enemy1 = new NPC("placeholder");
        enemySprite = new Animation(enemy, duration, false);
        enemySprite.draw(900f, 900f);
    }
    public void attack(int attack)
    {
        
    }
}