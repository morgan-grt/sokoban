/**
* joueur ia (algorithme A*)
*/

package gameModel;
import controler.Controler;
import game.Coordinate;
import static game.Coordinate.Action.*;
import game.Game;
import gameModel.GameElement.playerCommand;
import static gameModel.GameElement.playerCommand.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.LinkedList;

public class APlayer extends Player {
    protected String namePlayer;
    protected int i = 0, n = 1;
    protected List<playerCommand> listMoves;
    protected Game game;
    private HashSet<Game> openList;
    private HashSet<Game> closedList;
    private Game currentState;
    protected List<playerCommand> listMovesAnityme = new ArrayList<>();

    public APlayer(String namePlayer, Coordinate coord){
        super(namePlayer,coord);
        this.controler = new Controler(this);
    }

    public APlayer(String namePlayer,String sprite){
        super(namePlayer);
        this.controler = new Controler(this);
    }


    @Override
    public void setCurrentState(Game state){
        game = state;
    }

    @Override
    public void setListMoves(){
        listMoves = new ArrayList<>(AStar(game));
    }

    @Override
    public playerCommand setPlayerCommand(int key) {
        if (listMovesAnityme.size() > i){
            i++;
            return controler.getAPlayerCommand(listMovesAnityme.get(i-1));
        }
        else if (listMoves.size() == 0 || i >= listMoves.size()){
            return controler.getAPlayerCommand(CENTER);
        }
        i += 1;
        return controler.getAPlayerCommand(listMoves.get(i-1));
    }

    private List<playerCommand> AStar(Game start){
        openList = new HashSet<>();
        closedList = new HashSet<>();
        int boucle = 0;
        openList.add(start);
        double beginTime = System.currentTimeMillis();
        while(!openList.isEmpty()){
            currentState = getLowestCost();
            openList.remove(currentState);
            closedList.add(currentState);
            if(currentState.playerIsWinner()){
                System.out.println("I find in "+boucle+" tour, in: "
                        + ((System.currentTimeMillis() - beginTime) / 1000F)
                        + " seconds!");
                return findPath(start,currentState);
            }
            if ( boucle > 1000000){System.out.println("I break! so hard for me!");break;}
            HashSet<Game> stateList = getState();
            for(Game state : stateList){
                if (!closedList.contains(state) && !openList.contains(state)){
                    state.setPrevious(currentState);
                    state.setDist();
                    openList.add(state);
                }
            }
            boucle++;
        }
        return new ArrayList<>();
    }

    public void AStarAnityme(Game start){
        openList = new HashSet<>();
        closedList = new HashSet<>();
        int boucle = 0;
        openList.add(start);
        double beginTime = System.currentTimeMillis();
        while(!openList.isEmpty()){
            currentState = getLowestCost();
            if (currentState.getSCost() == n){
                while (listMovesAnityme.size() > currentState.getSCost()){
                    listMovesAnityme.remove(listMovesAnityme.size() - 1);
                    n--;
                }
                listMovesAnityme.add(currentState.getPC());
                n++;
            }
            openList.remove(currentState);
            closedList.add(currentState);
            if(currentState.playerIsWinner()){
                System.out.println("I find in "+boucle+" tour, in: "
                        + ((System.currentTimeMillis() - beginTime) / 1000F)
                        + " seconds!");
                listMoves = findPath(start,currentState);
                break;
            }
            if ( boucle > 1000000){System.out.println("I break! so hard for me!");break;}
            HashSet<Game> stateList = getState();
            for(Game state : stateList){
                if (!closedList.contains(state) && !openList.contains(state)){
                    state.setPrevious(currentState);
                    state.setDist();
                    openList.add(state);
                }
            }
            boucle++;
        }
    }

    private HashSet<Game> getState(){
        HashSet<Game> stateList = new HashSet<>();
        List<playerCommand> adjacentCoord = currentState.getPlayer().getAdjacent();
        for(playerCommand coup : adjacentCoord){
            Coordinate aecoup = currentState.validMoves(coup);
            if(aecoup.getK() != IMPOSSIBLE && aecoup.getK() != NEUTRE){
                Game newState = currentState.doActionCP(aecoup);
                newState.setPC(coup);
                if (!closedList.contains(newState)){
                    stateList.add(newState);
                }
            }
        }
        return stateList;
    }

    private Game getLowestCost(){
        Game lowest = openList.iterator().next();
        for (Game state : openList){
            if (state.getCost() < lowest.getCost()){
                lowest = state;
            }
            else if (state.getCost() == lowest.getCost()){
                if (state.getSCost() > lowest.getSCost()){
                    lowest = state;
                }
            }
        }
        return lowest;
    }

    private List<playerCommand> findPath(Game start, Game goal) {
        LinkedList<playerCommand> path = new LinkedList<>();
        Game current = goal;
        boolean done = false;
        while (!done) {
            path.addFirst(current.getPC());
            current = (Game) current.getPrevious();
            if (current.equals(start)) {
                done = true;
            }
        }
        return path;
    }

    public List<playerCommand> getListMoves(){
        return listMoves;
    }

    public List<playerCommand> getListMovesAnityme(){
        return listMovesAnityme;
    }

    public Game getGame(){
        return game;
    }

    public void setListMoves(List<playerCommand> list){
        listMoves = list;
    }

    public void setI(int n){
        i = n;
    }

}
