import org.newdawn.slick.Image;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Input;

//Remake the placeholder maps with the opposite orientation

public class TwoDSlickPrototype extends BasicGame{
    private TiledMap currentRoom;
    public static Animation sprite, up, down, left, right, shoot;
    public float x = 500f, y = 500f;
    private boolean[][] blocked;
    private boolean[][] passable;
    
    int currentRow = 0;
    int currentCol = 0;
    
    Room[][]level;
    
    int RXC;
    int RYC;
    //private Room placeholderRoom = Room.generateRoom(level, RXC, RYC);
    //dimensions of the sprite
    private static final int SIZE = 34;
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
            app.setDisplayMode(1000, 1000, false);
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
        
        Image [] movementUp = {new Image("Schreiber.png")};
        Image [] movementDown = {new Image("Schreiber.png")};
        Image [] movementLeft = {new Image("Schreiber.png")};
        Image [] movementRight = {new Image("Schreiber.png")};
        int duration = 300;
        
        level = Room.generateLevel(1);
        Room firstRoom = level[0][Room.getFirstRoom()];
        currentRoom = firstRoom.getMap();
        //currentRoom = new TiledMap("StartingRoom1.tmx", "");
        currentCol = Room.getFirstRoom();
        //placeholderRoom = Room.generateRoom(level, x, y);
        //placeholder = placeholderRoom.getMap();
        
        up = new Animation(movementUp, duration, false);
        down = new Animation(movementDown, duration, false);
        left = new Animation(movementLeft, duration, false);
        right = new Animation(movementRight, duration, false);
        
        //original orientation
        sprite = right;
        //build a collision map
        blocked = new boolean[currentRoom.getWidth()][currentRoom.getHeight()];
        passable = new boolean[currentRoom.getWidth()][currentRoom.getHeight()];
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
                    passable[xAxis][yAxis] = true;
                }
            }
        }
    }
    public void update(GameContainer container, int delta) throws SlickException
    {
        Input input = container.getInput();
        if (input.isKeyDown(Input.KEY_UP))
        {
            sprite = up;
            if(!isBlocked(x, y - delta * 0.1f))
            {
                sprite.update(delta);
                //the lower the delta the slower the sprite will animate
                y -= delta * 0.1f;
            }
            //else if (isBlocked(x, y - delta * 0.1f))
            //{
                //sprite.update(delta);
                //y = y;
            //}
            else if(isDoor(x, y - delta * .1f))
            {
                x = 500f;
                y = 500f;
                currentRoom = level[currentRow + 1][currentCol].getMap();
                currentRow = currentRow + 1;
                currentRoom.render(0, 0);
                sprite.draw(500, 500);
            }
        }
        else if (input.isKeyDown(Input.KEY_DOWN))
        {
            sprite = down;
            if (!isBlocked(x, y + 344 + delta * 0.1f))
            {
                sprite.update(delta);
                y += delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_LEFT))
        {
            sprite = left;
            if (!isBlocked(x - delta * 0.1f, y))
            {
                sprite.update(delta);
                x -= delta * 0.1f;
            }
        }
        else if (input.isKeyDown(Input.KEY_RIGHT))
        {
            sprite = right;
            if (!isBlocked(x + 263 + delta * 0.1f, y))
            {
                sprite.update(delta);
                x += delta * 0.1f;
            }
        }
    }
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        currentRoom.render(0, 0);
        sprite.draw((int)x, (int)y);
    }
    private boolean isBlocked(float x, float y)
    {
        int xBlock = (int) x / SIZE;
        int yBlock = (int)y / SIZE;
        return blocked[xBlock][yBlock];
    }
    private boolean isDoor(float x, float y)
    {
        int xBlock = (int) x / SIZE;
        int yBlock = (int)y / SIZE;
        return passable[xBlock][yBlock];
    }
    public static Animation getSprite()
    {
        return sprite;
    }
}