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

public class Room{
    public static TiledMap playerRoom;
    public float x;
    public float y;
    //Talk to Schreiber about statics
    private static Animation getSprite = TwoDSlickPrototype.getSprite();
    public Room(TiledMap room, NPC[] enemies, int x, int y)
    {
        
    }
    public static Room generateRoom(int level, int xCoord, int yCoord) throws SlickException
    {
        //http://gamedevelopment.tutsplus.com/tutorials/create-a-procedurally-generated-dungeon-cave-system--gamedev-10099
        NPC[] enemy;
    
        Random rnd = new Random();
        int CRE = rnd.nextInt(6);
        int RD = rnd.nextInt(100);
        int doors = rnd.nextInt(4);
        

        float x = 100f, y = 100f;

        TiledMap[][] levelOne = new TiledMap[10][10];
        TiledMap[][] levelTwo = new TiledMap[10][10];
        TiledMap[][] levelThree = new TiledMap[10][10];
        enemy = new NPC[CRE];
        if (RD < 5)
        {
            playerRoom = new TiledMap("Placeholder3.tmx", "");
            if(getSprite.equals("up"))
            {
                x = 400;
                y = 10;
            }
        }
        else if (RD < 90 && RD > 5)
        {
            playerRoom = new TiledMap("Placeholder3.tmx", "");
        }
        else if (RD >90 && RD < 100)
        {
            playerRoom = new TiledMap("Placeholder3.tmx", "");
        }
        Room currentRoom = new Room(playerRoom, enemy, xCoord, yCoord);
        return currentRoom;
    }
    public TiledMap getMap()
    {
        return playerRoom;
    }
}