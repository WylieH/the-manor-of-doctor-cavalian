import org.newdawn.slick.Image;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Input;

//libraries into userlib
//move libraries into repository
public class TwoDSlickPrototype extends BasicGame{
    private TiledMap placeholder;
    private Animation sprite, up, down, left, right;
    private float x = 34f, y = 34f;
    private boolean[][] blocked;
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
            app.setDisplayMode(500, 400, false);
            app.start();
        }
        catch (SlickException e)
        {
           e.printStackTrace(); 
        }
    }
    public void init(GameContainer container) throws SlickException
    {
        Image [] movementUp = {new Image("Computer Science/Schreiber.png")};
        Image [] movementDown = {new Image("Computer Science/Schreiber.png")};
        Image [] movementLeft = {new Image("Computer Science/Schreiber.png")};
        Image [] movementRight = {new Image("Computer Science/Schreiber.png")};
        int[] duration = {300, 300};
        placeholder = new TiledMap("Computer Science/blackbox.png");
        up = new Animation(movementUp, duration, false);
        down = new Animation(movementDown, duration, false);
        left = new Animation(movementLeft, duration, false);
        right = new Animation(movementRight, duration, false);
        //original orientation
        sprite = right;
        //build a collision map
        blocked = new boolean[placeholder.getWidth()][placeholder.getHeight()];
        for(int xAxis = 0; xAxis < placeholder.getWidth(); xAxis = xAxis + 1)
        {
            for(int yAxis = 0; yAxis < placeholder.getHeight(); yAxis = yAxis + 1)
            {
                int tileID = placeholder.getTileId(xAxis, yAxis, 0);
                String value = placeholder.getTileProperty(tileID, "blocked", "false");
                if ("true".equals(value))
                {
                    blocked[xAxis][yAxis] = true;
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
        }
        else if (input.isKeyDown(Input.KEY_DOWN))
        {
            sprite = down;
            if (!isBlocked(x, y + SIZE + delta * 0.1f))
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
            if (!isBlocked(x + SIZE + delta * 0.1f, y))
            {
                sprite.update(delta);
                x += delta * 0.1f;
            }
        }
    }
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        placeholder.render(0, 0);
        sprite.draw((int)x, (int)y);
    }
    private boolean isBlocked(float x, float y)
    {
        int xBlock = (int) x / SIZE;
        int yBlock = (int)y / SIZE;
        return blocked[xBlock][yBlock];
    }
}