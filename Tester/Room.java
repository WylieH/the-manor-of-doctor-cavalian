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
    //Add a path generator that returns an int 2D array
    //Path generator creates a randomized path leading from starting room, in ary[0], to boss room, in ary[final]
    //Create some random offshoot rooms from the main path.
    //Use level generator to fiill a Room[][] based on the boolean[][] from pathgenerator
    
    //add boolean "entered" to the room to check if the player has entered already
    public String type;
    public TiledMap playerRoom;
    
    int levelCheck;
    public static int startingRoomLocation = 0;
    boolean entered;
    NPC[] enemies;
    //Talk to Schreiber about statics
    public Room(String roomType, int level) throws org.newdawn.slick.SlickException
    {
        Random rnd = new Random();
        //move generateRoom into the constructor
        if (roomType.equals("startingRoom") && level == 1)
        {
            playerRoom = new TiledMap("StartingRoom1.tmx", "");
            enemies = new NPC[0];
        }
        else if (roomType.equals("bossEntranceUp") && level == 1)
        {
            playerRoom = new TiledMap("BossEntranceUp.tmx", "");
        }
        else if (roomType.equals("bossEntranceRight") && level == 1)
        {
            playerRoom = new TiledMap("BossEntranceRight.tmx", "");
        }
        else if (roomType.equals("bossEntranceLeft") && level == 1)
        {
            playerRoom = new TiledMap("BossEntranceLeft.tmx", "");
        }
        else if (roomType.equals("basicUpDown") && level == 1)
        {
            playerRoom = new TiledMap("BasicUpDown.tmx", "");
            enemies = new NPC[rnd.nextInt(4) + 2];
        }
        else if (roomType.equals("basicUpLeft") && level == 1)
        {
            playerRoom = new TiledMap("BasicUpLeft.tmx", "");
            enemies = new NPC[rnd.nextInt(4) + 2];
        }
        else if (roomType.equals("basicUpRight") && level == 1)
        {
            playerRoom = new TiledMap("BasicUpRight.tmx", "");
            enemies = new NPC[rnd.nextInt(4) + 2];
        }
        else if (roomType.equals("basicLeftDown") && level == 1)
        {
            playerRoom = new TiledMap("BasicLeftDown.tmx", "");
            enemies = new NPC[rnd.nextInt(4) + 2];
        }
        else if (roomType.equals("basicRightDown") && level == 1)
        {
            playerRoom = new TiledMap("BasicRightDown.tmx", "");
            enemies = new NPC[rnd.nextInt(4) + 2];
        }
        else if (roomType.equals("connectorVertical") && level == 1)
        {
            playerRoom = new TiledMap("ConnectorVertical.tmx", "");
            enemies = new NPC[rnd.nextInt(2) + 1];
        }
        else if (roomType.equals("connectHorizontal") && level == 1)
        {
            playerRoom = new TiledMap("ConnectorHorizontal.tmx", "");
            enemies = new NPC[rnd.nextInt(2) + 1];
        }
        else if (roomType.equals("connectRight") && level == 1)
        {
            playerRoom = new TiledMap("ConnectorRight.tmx", "");
            enemies = new NPC[rnd.nextInt(2) + 1];
        }
        else if (roomType.equals("connectLeft") && level == 1)
        {
            playerRoom = new TiledMap("ConnectorLeft.tmx", "");
            enemies = new NPC[rnd.nextInt(2) + 1];
        }
        entered = false;
    }
    //public void enter() throws SlickException
    //{
    //    entered = true;
    //    NPC.render();
    //}
    public boolean isEntered()
    {
        return entered;
    }
    public static int getFirstRoom()
    {
        return startingRoomLocation;
    }
    public static Room[][] generateLevel(int levelCheck) throws org.newdawn.slick.SlickException
    {
        //create a 2D room array of dimensions 4-10 up and down
        //Randomly fill a random amount of the rooms with randomly pre-generated rooms
        //Check to see that every room has at least one other room touching it
        //    -if not, add room "corridor" between them
        //Select one room to become the boss room.
        Random rnd = new Random();
        int levelX;
        int levelY;
        levelX = rnd.nextInt(5) + 3;
        levelY = rnd.nextInt(10) + 8;
        int[][] path = Room.makePath(levelY, levelX);
        Room[][] level = new Room[levelY][levelX];
        

        for (int row = 0; row < levelY; row = row + 1)
        {
            for (int col = 0; col < levelX; col = col + 1)
            {
                if (path[row][col] == 7)
                {
                    level[row][col] = new Room("startingRoom", levelCheck);
                    startingRoomLocation = col;
                }
                else if (path[row][col] == 6)
                {
                    if (row > 0 && path[row - 1][col] != 0)
                    {
                        level[row][col] = new Room("bossEntranceUp", levelCheck);
                    }
                    else if (col > 0 && path[row][col - 1] != 0)
                    {
                        level[row][col] = new Room("bossEntranceLeft", levelCheck);
                    }
                    else if (path[row][col + 1] != 0)
                    {
                        level[row][col] = new Room("bossEntranceRight", levelCheck);
                    }
                }
                else if (path[row][col] == 1)
                {
                    //starts up
                    if (row > 0 && path[row - 1][col] != 0 && path[row + 1][col] != 0)
                    {
                        level[row][col] = new Room("basicUpDown", levelCheck);
                    }
                    else if (row > 0 && path[row - 1][col] != 0 && path[row][col - 1] != 0)
                    {
                        level[row][col] = new Room("basicUpLeft", levelCheck);
                    }
                    else if (row > 0 && path[row - 1][col] != 0 && path[row][col + 1] != 0)
                    {
                        level[row][col] = new Room("basicUpRight", levelCheck);
                    }
                    //starts left
                    else if (col > 0 &&path[row][col - 1] != 0 && path[row + 1][col] != 0)
                    {
                        level[row][col] = new Room("basicLeftDown", levelCheck);
                    }
                    //starts right
                    else if (path[row][col + 1] != 0 && path[row + 1][col] != 0)
                    {
                        level[row][col] = new Room("basicRightDown", levelCheck);
                    }
                }
                else if (path[row][col] == 4)
                {
                    level[row][col] = new Room("connectorVertical", levelCheck);
                }
                else if (path[row][col] == 5)
                {
                    level[row][col] = new Room("connectHorizontal", levelCheck);
                }
                else if (path[row][col] == 2)
                {
                    level[row][col] = new Room("connectRight", levelCheck);
                }
                else if (path[row][col] == 3)
                {
                    level[row][col] = new Room("connectLeft", levelCheck);
                }
            }
        }
        
        return level;
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
        int column = 0;
        boolean room = false;
        //randomly makes one element of each row into a room
        for (int currentRow = 0; currentRow < rows - 1; currentRow = currentRow + 1)
        {
            //finds a column with an integer that isn't zero in it
            for (int currentCol = 0; currentCol < cols; currentCol = currentCol + 1)
            {
                if (path[currentRow][currentCol] == 1 || path[currentRow][currentCol] == 7)
                //path[currentRow][currentCol] != 0 && path[currentRow][currentCol] != 5 && path[currentRow][currentCol] != 3 && path[currentRow][currentCol] != 2
                {
                    column = currentCol;
                    room = true;
                }
            }
            int ifMakeRoom = rnd3.nextInt(3);
            int whereMakeRoom = rnd3.nextInt(cols);
            if (ifMakeRoom < 1 && room)
            {
                path[currentRow + 1][column] = 4;
            }
            else if (ifMakeRoom >= 1 && room)
            {
                if (whereMakeRoom < column)
                {
                    path[currentRow + 1][column] = 3;
                    for (int connectRoom = column - 1; connectRoom > whereMakeRoom; connectRoom = connectRoom - 1)
                    {
                        path[currentRow + 1][connectRoom] = 5;
                    }
                }
                else if (whereMakeRoom > column)
                {
                    path[currentRow + 1][column] = 2;
                    for (int connectRoom = column + 1; connectRoom < whereMakeRoom; connectRoom = connectRoom + 1)
                    {
                        path[currentRow + 1][connectRoom] = 5;
                    }
                }
                path[currentRow + 1][whereMakeRoom] = 1;
            }
        }
        for (int boss = 0; boss < cols; boss = boss + 1)
        {
            if (path[rows - 1][boss] == 1 || path[rows - 1][boss] == 4)
            {
                path[rows - 1][boss] = 6;
            }
        }
        return path;
    }
    public TiledMap getMap()
    {
        return playerRoom;
    }
}