/**
* classe abstraite qui permet l'implémentation des elements du jeu
*/

package gameModel;

import game.Coordinate;
import static gameModel.GameElement.playerCommand.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GameElement {
    protected char symbol;
    protected Coordinate coord;
    public static enum playerCommand{
        UP(-1,0),
        DOWN(1,0),
        RIGHT(0,1),
        LEFT(0,-1),
        CENTER(0,0);
        protected int x;
        protected int y;

        playerCommand(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }

        public void setX(int i){
            x = i;
        }

        public void setY(int j){
            y = j;
        }
    }

    public GameElement(char symbol, int x, int y){
        coord = new Coordinate(x,y);
    }
    public GameElement(char symbol){
        this.symbol = symbol;
    }

    public List<playerCommand> getAdjacent(){
        List<playerCommand> list = new ArrayList<>();
        for (playerCommand pc : playerCommand.values()){
            if (pc != CENTER){
                list.add(pc);
            }
        }
        return list;
    }

    public int getX(){
        return coord.getX();
    }

    public Coordinate getCoord(){
        return coord;
    }

    public void setCoord(Coordinate coord){
        this.coord = coord;
    }

    public int getY(){
        return coord.getY();
    }

    public String getTuplePosition(){
        return "(" + coord.getX() + "," + coord.getY() + ")";
    }

    public char getSymbol(){
        return this.symbol;
    }
}
