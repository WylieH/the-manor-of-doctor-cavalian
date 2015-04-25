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
    //Add a path generator that returns a boolean 2D array
    //Path generator creates a jagged, somewhat randomized path leading from starting room, in ary[0], to boss room, in ary[final]
    //Create some random offshoot rooms from the main path.
    //Use level generator to fiill a Room[][] based on the boolean[][] from pathgenerator
    
    //add boolean "entered" to the room to check if the player has entered already
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
        levelY = rnd.nextInt(30) + 15;
        
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
    }
    public static int[][] makePath(int rows, int cols)
    {
        //7 is the starting room
        //1 is the basic, generic, enemy room
        //2 is the connector turning right
        //3 is the connector turning left
        //4 is the connector vertically
        //5 is the connector horizontally
        //6 is the boss room
        
        //Instead of making the rooms and then the path
        //Make one room, the path to the next room, then the next room
        //Use two randomly generated ints, one to determine if a row will have a room and another to determine where
        
        Random rnd3 = new Random();
        int[][] path = new int[rows][cols];
        int firstRoom = rnd3.nextInt(cols);
        
        int location;
        int locHoriz = 0;
        int locVert = 0;
        //fills everything with zeroes
        for (int fillRow = 0; fillRow < rows; fillRow = fillRow + 1)
        {
            for (int fillCol = 0; fillCol < cols; fillCol = fillCol + 1)
            {
                path[fillRow][fillCol] = 0;
            }
        }
        path[0][firstRoom] = 7;
        //randomly makes one element of each row into a room
        for (int assignPath = 1; assignPath < rows; assignPath = assignPath + 1)
        {
            int pathRoom = rnd3.nextInt(cols);
            int createRoom = rnd3.nextInt(3);
            if (createRoom >= 1)
            {
                path[assignPath][pathRoom] = 1;
            }
        }
        //creates the boss room at the end
        for (int bossCheck = 0; bossCheck < cols; bossCheck = bossCheck + 1)
        {
            int boss = rnd3.nextInt(cols);
            path[rows - 1][boss] = 6;
            break;
        }
        //finds a from room
        for (int roomRow = 0; roomRow < rows - 1; roomRow = roomRow + 1)
        {
            for (int roomCol = 0; roomCol < cols; roomCol = roomCol + 1)
            {
                if (path[roomRow][roomCol] == 1 || path[roomRow][roomCol] == 7)
                {
                int roomFromColumn = roomCol;
                int roomFromRow = roomRow;
                //checks for the next level with a room
                for (int checkPath = roomRow; checkPath < rows - 1; checkPath = checkPath + 1)
                {
                    for (int roomLoc = 0; roomLoc < cols; roomLoc = roomLoc + 1)
                    {
                        if (path[checkPath][roomLoc] == 1 || path[checkPath][roomLoc] == 6)
                        {
                            int roomToColumn = roomLoc;
                            int roomToRow = checkPath;
                            //creates a corridor down to the level of the next room
                            for (int connectVert = roomFromRow + 1; connectVert < roomToRow - 1; connectVert = connectVert + 1)
                            {
                                path[connectVert][roomFromColumn] = 4;
                                
                            }
                            if (roomToColumn > roomFromColumn)
                            {
                                path[roomToRow][roomFromColumn] = 3;
                                for (int connectHor = roomFromColumn + 1; connectHor < roomToColumn; connectHor = connectHor + 1)
                                {
                                    path[roomToRow][connectHor] = 5;
                                }
                            }
                            else if (roomToColumn < roomFromColumn)
                            {
                                path[roomToRow][roomFromColumn] = 2;
                                for (int connectHor = roomToColumn + 1; connectHor < roomFromColumn; connectHor = connectHor + 1)
                                {
                                    path[roomToRow][connectHor] = 5;
                                }
                            }
                        }
                    }
                }
            }
            }
        }
        
        return path;
    }
    public TiledMap getMap()
    {
        return playerRoom;
    }
}