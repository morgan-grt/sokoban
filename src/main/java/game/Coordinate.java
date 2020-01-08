/**
* coordonnees de l'element a placer dans le tableau d'objets
*/

package game;
import static game.Coordinate.Action.*;
import gameModel.GameElement.playerCommand;

public class Coordinate{

    public static enum Action{
        NEUTRE(-1),
        IMPOSSIBLE(0),
        VIDE(1),
        CAISSE(2);
        public int k;

        Action(int k){
            this.k = k;
        }
    }

    protected Action k;
    protected int x;
    protected int y;

    public Coordinate(int x,int y){
        this.x = x;
        this.y = y;
        this.k = NEUTRE;
    }

    public Coordinate(playerCommand pc){
        this.x = pc.getX();
        this.y = pc.getY();
        this.k = NEUTRE;
    }

    public Coordinate(){
        this.k = NEUTRE;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Action getK(){
        return k;
    }

    public void setX(int i){
        x = i;
    }

    public void setY(int j){
        y = j;
    }

    public void setK(Action a){
        k = a;
    }

    @Override
    public boolean equals(Object o) {
        Coordinate otherCoord = (Coordinate) o;
        if (otherCoord == null || !(otherCoord instanceof Coordinate)) {
            return false;
        }
        if (otherCoord.getX() == getX() && otherCoord.getY() == getY()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "("+x+","+y+") : " +k;
    }
}
