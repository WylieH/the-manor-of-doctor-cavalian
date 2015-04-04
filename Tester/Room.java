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
    public Room(TiledMap room, NPC[] enemies)
    {
        
    }
    public static Room generateRoom(int level, String roomType) throws SlickException
    {
        
        NPC[] enemy;
    
        Random rnd = new Random();
        int CRE = rnd.nextInt(6);
    
        TiledMap[][] levelOne = new TiledMap[10][10];
        TiledMap[][] levelTwo = new TiledMap[10][10];
        TiledMap[][] levelThree = new TiledMap[10][10];
        enemy = new NPC[CRE];
        playerRoom = new TiledMap("Placeholder3", "");
        Room currentRoom = new Room(playerRoom, enemy);
        return currentRoom;
    }
    public TiledMap getMap()
    {
        return playerRoom;
    }
}