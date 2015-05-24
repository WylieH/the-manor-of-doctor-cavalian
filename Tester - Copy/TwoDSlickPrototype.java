import org.newdawn.slick.Image;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Input;

import java.util.Timer;
import java.util.TimerTask;

//Remake the placeholder maps with the opposite orientation

//If a major error comes up, it could be because you made x and y static

//http://www.aseprite.org/
//http://pyxeledit.com/features.php

public class TwoDSlickPrototype extends BasicGame{
    private TiledMap currentRoom;
    public Room playerRoom;
    public static Animation sprite, up, down, left, right, shoot;
    int playerHealth;
    public static float x = 300f, y = 300f;
    private boolean[][] blocked;
    private boolean[][] Door;
    
    int currentRow = 0;
    int currentCol = 0;
    
    static int speed;
    NPC enemy;

    Room[][]level;
    
    //dimensions of the sprite
    private static final int SIZE = 32;
    public TwoDSlickPrototype()
    {
        super ("Prototype game");
    }
    public static void main(String[] args)
    {
        AppGameContainer app;
        try
        {
            app = new AppGameContainer(new TwoDSlickPrototype());
            app.setDisplayMode(640, 640, false);
            app.start();
        }
        catch (SlickException e)
        {
           e.printStackTrace(); 
        }
    }
    public void init(GameContainer container) throws SlickException
    {
        //Player Sprite
        int x = 0;
        int y = 0;
        playerHealth = 5;
        
        Image [] movementUp = {new Image("Sprite idle right.png")};
        Image [] movementDown = {new Image("Sprite idle right.png")};
        Image [] movementLeft = {new Image("Sprite idle right.png")};
        Image [] movementRight = {new Image("Sprite idle right.png")};
        int duration = 300;
        
        level = Room.generateLevel(1);
        playerRoom = level[0][Room.getFirstRoom()];
        currentRoom = playerRoom.getMap();
        //currentRoom = new TiledMap("StartingRoom1.tmx", "");
        currentCol = Room.getFirstRoom();
        currentRow = 0;
        playerRoom.entered = true;
        //placeholderRoom = Room.generateRoom(level, x, y);
        //placeholder = placeholderRoom.getMap();
        
        up = new Animation(movementUp, duration, false);
        down = new Animation(movementDown, duration, false);
        left = new Animation(movementLeft, duration, false);
        right = new Animation(movementRight, duration, false);
        
        //original orientation
        sprite = right;
        //build a collision map
        blocked = new boolean[640][640];
        Door = new boolean[640][640];
        for(int xAxis = 0; xAxis < currentRoom.getWidth(); xAxis = xAxis + 1)
        {
            for(int yAxis = 0; yAxis < currentRoom.getHeight(); yAxis = yAxis + 1)
            {
                //Add/pull Tiled Property door
                int tileID = currentRoom.getTileId(xAxis, yAxis, 0);
                String value = currentRoom.getTileProperty(tileID, "blocked", "false");
                String valueDoor = currentRoom.getTileProperty(tileID, "Door", "false");
                if (value.equals("true"))
                {
                    blocked[xAxis][yAxis] = true;
                }
                if (valueDoor.equals("true"))
                {
                    Door[xAxis][yAxis] = true;
                }
            }
        }
    }
    public void update(GameContainer container, int delta) throws SlickException
    {
        Input input = container.getInput();
        speed = delta;
        if (input.isKeyDown(Input.KEY_UP))
        {
            sprite = up;
            //x + SIZE, 
            if(y > 81)
            {
                sprite.update(delta);
                //the lower the delta the slower the sprite will animate
                y -= delta * 0.2f;
                
                if(y <= 70)
                {
                    sprite.update(delta);
                    y-= delta * 0.2f;
                    currentRoom = level[currentRow + 1][currentCol].getMap();
                    playerRoom = level[currentRow + 1][currentCol];
                    x = 200f;
                    y = 200f;
                    currentRow = currentRow + 1;
                }
                
            }
        }
        else if (input.isKeyDown(Input.KEY_DOWN))
        {
            sprite = down;
            if (y < 460)
            {
                sprite.update(delta);
                y += delta * 0.2f;
                
                if(isDoor(x, y + delta * 0.1f))
                {
                    sprite.update(delta);
                    y+= delta * 0.2f;
                    currentRoom = level[currentRow - 1][currentCol].getMap();
                    playerRoom = level[currentRow - 1][currentCol];
                    x = 500f;
                    y = 500f;
                    if (playerRoom.entered == false)
                    {
                        //playerRoom.enter();
                    }
                    currentRow = currentRow - 1;
                }
                
            }
        }
        else if (input.isKeyDown(Input.KEY_LEFT))
        {
            sprite = left;
            if (x > 70)
            {
                sprite.update(delta);
                x -= delta * 0.2f;
                if(isDoor(x - delta * 0.1f, y))
                {
                    sprite.update(delta);
                    x -= delta * 0.2f;
                    currentRoom = level[currentRow][currentCol - 1].getMap();
                    playerRoom = level[currentRow][currentCol - 1];
                    x = 500f;
                    y = 500f;
                    if (playerRoom.entered == false)
                    {
                        //playerRoom.enter();
                        
                    }
                    currentCol = currentCol - 1;
                }
            }
        }
        else if (input.isKeyDown(Input.KEY_RIGHT))
        {
            sprite = right;
            if (x < 480)
            {
                sprite.update(delta);
                x += delta * 0.2f;
                if(isDoor(x + delta * 0.1f, y))
                {
                    sprite.update(delta);
                    x += delta * 0.2f;
                    currentRoom = level[currentRow][currentCol + 1].getMap();
                    playerRoom = level[currentRow][currentCol + 1];
                    x = 500f;
                    y = 500f;
                    if (playerRoom.entered == false)
                    {
                        //playerRoom.enter();
                    }
                    currentCol = currentCol + 1;
                }
            }
        }
    }
    public static int getSpeed()
    {
        return speed;
    }
    
    public static float getPlayerX()
    {
        return x;
    }
    public static float getPlayerY()
    {
        return y;
    }
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        currentRoom.render(0, 0);
        sprite.draw((int)x, (int)y);
            enemy = new NPC("placeholder");
            enemy.enemySprite.draw((int)enemy.x, (int)enemy.y);
    }
    private boolean isBlocked(float x, float y)
    {
        //boolean inBounds = (x >= 0) && (x < currentRoom.getWidth());
        //boolean inBoundsy = (y >= 0) && (x < currentRoom.getHeight());
        int xBlock = (int) x / SIZE;
        int yBlock = (int)y / SIZE;
        return blocked[xBlock][yBlock];
    }
    private boolean isDoor(float x, float y)
    {
        int xBlock = (int) x / SIZE;
        int yBlock = (int)y / SIZE;
        return Door[xBlock][yBlock];
    }
}
