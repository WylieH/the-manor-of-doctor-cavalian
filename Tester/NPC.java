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

public class NPC{
    int health;
    int attack;
    public static float x = 900f;
    public static float y = 900f;
    public static Animation enemySprite;

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
            x = 100f;
            y = 700f;
            while (x < 800)
            {
                
            }
        }
    }
    //public void update(GameContainer container, int delta) throws SlickException
    //{
    //    while (x != TwoDSlickPrototype.getPlayerX())
    //        {
    //            if (x > TwoDSlickPrototype.getPlayerX())
    //            {
    //                x -= delta * .05f;
     //           }
       //         else if (x < TwoDSlickPrototype.getPlayerX())
         //       {
           //         x += delta * .05f;
             //   }
               // while (y != TwoDSlickPrototype.getPlayerY())
                //{
                  //  if (y > TwoDSlickPrototype.getPlayerY())
                    //{
                      //  y -= delta * .05f;
                   // }
              //      else if (y < TwoDSlickPrototype.getPlayerY())
                //    {
                  //      y += delta * .05f;
                    //}
           //     }
      //      }
    //  }
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