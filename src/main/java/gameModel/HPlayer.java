/**
* joueur humain
*/

package gameModel;
import controler.Controler;
import game.Coordinate;
import game.Game;

public class HPlayer extends Player{

    public HPlayer(String namePlayer, Coordinate coord){
        super(namePlayer,coord);
        this.controler = new Controler(this);
    }

    public HPlayer(String namePlayer){
        super(namePlayer);
        this.controler = new Controler(this);
    }

    @Override
    public GameElement.playerCommand setPlayerCommand(int key) {
        return controler.getHPlayerCommand(key);
    }

    @Override
    public void setCurrentState(Game State) {
    }

    @Override
    public void setListMoves(){
    }
}
