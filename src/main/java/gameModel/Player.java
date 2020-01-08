/**
* classe abstraite du joueur pour implementer le joueur humain et l'ia
*/

package gameModel;
import controler.Controler;
import game.Coordinate;
import static game.Coordinate.Action.IMPOSSIBLE;
import game.Game;
import static gameModel.GameElement.playerCommand.CENTER;
import java.util.ArrayList;
import java.util.List;

public abstract class Player extends GameElement {
    protected String namePlayer;
    protected Controler controler;
    protected String sprite;

    public Player(String namePlayer, Coordinate coord){
        super('@');
        this.namePlayer = namePlayer;
        this.coord = coord;
    }

    public Player(String namePlayer){
        super('@');
        this.namePlayer = namePlayer;
    }


    public List<Coordinate> listValidMoves(Game game){
        List<Coordinate> listMoves = new ArrayList<>();

        for(playerCommand mov : playerCommand.values() ){
            if(game.validMoves(mov).getK() != IMPOSSIBLE & mov != CENTER){
                listMoves.add(game.validMoves(mov));
                System.out.println(mov);
            }
        }
        return listMoves;
    }

    public abstract void setCurrentState(Game State);
    //@Override
    public abstract playerCommand setPlayerCommand(int key);

    //@Override
    public void setPosX(int i){
        coord.setX(i);
    }

    public abstract void setListMoves();

    //@Override
    public void setPosY(int j){
        coord.setY(j);
    }

    //@Override
    public void setPos(int i, int j){
        coord.setX(i);
        coord.setY(j);
    }

    @Override
    public String toString(){
        return namePlayer;
    }

    public String getSprite(){
        return sprite;
    }

    public Player getCopy(){
        if (this instanceof APlayer)
            return new APlayer(this.namePlayer,new Coordinate(coord.getX(),coord.getY()));
        else
            return new HPlayer(this.namePlayer,new Coordinate(coord.getX(),coord.getY()));
    }
}
