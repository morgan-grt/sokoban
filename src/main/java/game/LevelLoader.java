/**
* permet la creation du plateau
*/
package game;
import gameModel.GameElement;

public interface LevelLoader {
    public GameElement[][] getTab();
    public boolean[][] getZoneFinal();
    public int getNumberZoneFinal();
    public void initPlayerPosition();
}
