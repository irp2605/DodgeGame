import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class World {
    public ArrayList<NonPlayer> getNonplayerList() {
        return NonplayerList;
    }

    private ArrayList<NonPlayer> NonplayerList;
    private File f;
    private Scanner fileReader;
    public World(File f) {
        try {
            this.f = f;
            fileReader = new Scanner(f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void textConverter() {
        try{
            int curr = -1;
            int c=0;
            while(fileReader.hasNextInt()) {
                curr=fileReader.nextInt();
                c++;
                if(curr==0) {
                    NonplayerList.add(new NonPlayer(0, 0, c%10, c/10, 0, 10, 10));
                }
                if(curr==4) {
                    NonplayerList.add(new Wall(0, Constants.WALL, c%10, c/10, 0, 10, 10));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
