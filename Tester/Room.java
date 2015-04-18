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
    public String type;
    public TiledMap playerRoom;
    public float x;
    public float y;
    
    Random rnd = new Random();
    int doors;
    int levelX;
    int levelY;
    int roomFill;
    
    int levelCheck;
    
    public Room[][] level;
    //Talk to Schreiber about statics
    private static Animation getSprite = TwoDSlickPrototype.getSprite();
    public Room(String roomType, int level) throws SlickException
    {
        //move generateRoom into the constructor
       type = roomType;
        
       Random rnd2 = new Random();
       int CRE = rnd2.nextInt(6);
       int RD = rnd2.nextInt(100);
       int doors = rnd2.nextInt(4);
       if (RD < 5)
       {
           playerRoom = new TiledMap("Placeholder3.tmx", "");
           type = "treasure";
       }
       else if (RD < 90 && RD > 5)
       {
           playerRoom = new TiledMap("Placeholder3.tmx", "");
           type = "standard";
       }
       else if (RD >90 && RD < 100)
       {
           playerRoom = new TiledMap("Placeholder3.tmx", "");
       }
    }
    public void generateLevel() throws SlickException
    {
        //create a 2D room array of dimensions 4-10 up and down
        //Randomly fill a random amount of the rooms with randomly pre-generated rooms
        //Check to see that every room has at least one other room touching it
        //    -if not, add room "corridor" between them
        //Select one room to become the boss room.
        levelCheck = 1;
        
        levelX = rnd.nextInt(25) + 10;
        levelY = rnd.nextInt(25) + 10;
        
        int bossCol = rnd.nextInt(levelX);
        
        level = new Room[levelY][levelX];
        for (int fillRow = 0; fillRow < levelY; fillRow = fillRow + 1)
        {
            for (int fillCol = 0; fillCol < levelX; fillCol = fillCol + 1)
            {
                roomFill = rnd.nextInt(10);
                if(roomFill > 5)
                {
                    level[fillRow][fillCol] = new Room("random", levelCheck);
                }
            }
        }
        
        check(level);
        check(level);
        
        level[levelY][bossCol] = new Room("boss", levelCheck);
        check(level);
    }
    //public static Room generateRoom(int level) throws SlickException
    //{
        //http://gamedevelopment.tutsplus.com/tutorials/create-a-procedurally-generated-dungeon-cave-system--gamedev-10099
        //generate the entire map at the beginning
        //spawn enemies upon player entering
       // Random rnd2 = new Random();
       // int CRE = rnd2.nextInt(6);
       // int RD = rnd2.nextInt(100);
       // int doors = rnd2.nextInt(4);
       // if (RD < 5)
       // {
            //playerRoom = new TiledMap("Placeholder3.tmx", "");
       //     type = "treasure";
       // }
       // else if (RD < 90 && RD > 5)
       // {
            //playerRoom = new TiledMap("Placeholder3.tmx", "");
       //     type = "standard";
       // }
       // else if (RD >90 && RD < 100)
       // {
            //playerRoom = new TiledMap("Placeholder3.tmx", "");
       // }
       // Room currentRoom = new Room(type);
        //return currentRoom;
    //}
    public TiledMap getMap()
    {
        return playerRoom;
    }
    //checks to see if the rooms all connect to other rooms
    public void check(Room[][] level)
    {
        for (int checkRow = 0; checkRow < levelY; checkRow = checkRow + 1)
        {
            for (int checkCol = 0; checkCol < levelX; checkCol = checkCol + 1)
            {
                if(checkRow > 0 && checkCol > 0)
                {
                    if(checkRow < levelY - 1 && checkCol < levelX - 1)
                    {
                        //if(level[checkRow][checkCol] != undefined)
                        //{
                               //if(level[checkRow + 1][checkCol]  && level[checkRow - 1][checkCol] && level[checkRow][checkCol - 1] && level[checkRow][checkCol + 1] == undefined])
                               //{
                                      //Maybe check to see if corridor will connect (wait and see if it's a problem)
                                      
                                      //connect(level[checkRow][checkCol], checkCol, checkRow);
                               //}
                        //}
                    }
                }
            }
        }
    }
    //creates corridors connecting isolated rooms
    public void connect(Room from, int fromX, int fromY) throws SlickException
    {
        int connectDirection = rnd.nextInt(4) + 1;
        int up = level.length;
        int side = level[0].length;
        if(fromY < up - 1 && fromY > 1)
        {
            if (connectDirection == 1)
            {
                level[fromY + 1][fromX] = new Room("corridorVertical", levelCheck);
            }
            else if(connectDirection == 2)
            {
                level[fromY - 1][fromX] = new Room("corridorVertical", levelCheck);
            }
            else if(connectDirection == 3)
            {
                level[fromY][fromX - 1] = new Room("corridorHorizontal", levelCheck);
            }
            else if(connectDirection == 4)
            {
                level[fromY][fromX + 1] = new Room("corridorHorizontal", levelCheck);
            }
        }
    }
}