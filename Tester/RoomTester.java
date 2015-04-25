import java.util.Random;
public class RoomTester{

    public RoomTester()
    {
        
    }
    public static void main(String[] args)
    {
        Random rnd = new Random();
        int levelX = rnd.nextInt(5) + 3;
        int levelY = rnd.nextInt(10) + 8;
        int[][] pathTest;
        pathTest = Room.makePath(levelY, levelX);
        for (int row = 0; row < pathTest.length; row = row + 1)
        {
            for (int col = 0; col < pathTest[0].length; col = col + 1)
            {
                System.out.print(pathTest[row][col] + " ");
            }
            System.out.println(" ");
        }
    }
}