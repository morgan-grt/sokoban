/*
*Classe qui définit le jeu
*/
package game;
import static game.Coordinate.Action.*;
import gameModel.*;
import gameModel.GameElement.playerCommand;
import static gameModel.GameElement.playerCommand.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

public class Game extends Observable {
    
    protected GameElement[][] tab;
    protected final Player player;
    protected int numberZoneFinal;
    private static LevelLoaderFromFile loader;
    protected int cost = 0;
    protected int sCost = 0;
    protected playerCommand pCommand;
    protected Game previous;
    
    public Game(LevelLoaderFromFile loader, Player player){
        this.player = player;
        this.loader = loader;
        initGame();
    }
    
    public Game(Player player){
        this.player = player.getCopy();
    }
            
    public void initGame(){        
        this.tab = loader.getTab();
        this.numberZoneFinal = loader.getNumberZoneFinal();
        loader.initPlayerPosition();
    }
    
    public GameElement[][] getTab(){
        return this.tab;
    }
    
    public LevelLoader getLoader(){
        return loader;
    }
    
    public void setAction(int key){
        Coordinate ae = validMoves(player.setPlayerCommand(key));
        doAction(ae);
        this.setChanged();
        this.notifyObservers();
    }
    
    public Game doAction(Coordinate ae){
        int pX = player.getX();
        int pY = player.getY();
        try{
            if(ae.k == IMPOSSIBLE){
                System.out.println("Coup impossible ou non valide");
            }
            else if(ae.k == VIDE){
                tab[pX + ae.x][pY + ae.y] = player;
                player.setPos(pX + ae.x, pY + ae.y);
                tab[pX][pY] = new Vide(pX,pY);
            }
            else if(ae.k == CAISSE){
                if (tab[pX + 2*ae.x][pY + 2*ae.y] instanceof Zone){
                    if (loader.getZoneFinal()[pX + ae.x][pY + ae.y] != true){
                        numberZoneFinal--;
                    }
                }
                else if (loader.getZoneFinal()[pX + ae.x][pY + ae.y] == true){
                    numberZoneFinal++;
                }
                tab[pX + ae.x][pY + ae.y] = player;
                player.setPos(pX + ae.x, pY + ae.y);
                tab[pX][pY] = new Vide(pX,pY);
                tab[pX + 2*ae.x][pY + 2*ae.y] 
                        = new Caisse(pX + 2*ae.x,pY + 2*ae.y);
            }
        }
        catch(ArrayIndexOutOfBoundsException aioobe){System.out.println("ae error");}
        zoneUpdate();
        sCost++;
        return this;
    }
    
    public Game doActionCP(Coordinate ae){
        Game gameCP = getCopy();
        gameCP.setSCost(this.sCost);

        int pX = gameCP.player.getX();
        int pY = gameCP.player.getY();
        try{
            if(ae.k == IMPOSSIBLE){
                System.out.println("Coup impossible ou non valide");
            }
            else if(ae.k == VIDE){
                gameCP.tab[pX + ae.x][pY + ae.y] = gameCP.player;
                gameCP.player.setPos(pX + ae.x, pY + ae.y);
                gameCP.tab[pX][pY] = new Vide(pX,pY);
            }
            else if(ae.k == CAISSE){
                if (gameCP.tab[pX + 2*ae.x][pY + 2*ae.y] instanceof Zone){
                    if (gameCP.loader.getZoneFinal()[pX + ae.x][pY + ae.y] != true){
                        gameCP.numberZoneFinal--;
                    }
                }
                else if (gameCP.loader.getZoneFinal()[pX + ae.x][pY + ae.y] == true){
                    gameCP.numberZoneFinal++;
                }
                gameCP.tab[pX + ae.x][pY + ae.y] = gameCP.player;
                gameCP.player.setPos(pX + ae.x, pY + ae.y);
                gameCP.tab[pX][pY] = new Vide(pX,pY);
                gameCP.tab[pX + 2*ae.x][pY + 2*ae.y] 
                        = new Caisse(pX + 2*ae.x,pY + 2*ae.y);
            }
            else if(ae.k == NEUTRE){
                return gameCP;
            }
        }
        catch(ArrayIndexOutOfBoundsException aioobe){System.out.println("ae error");}
        gameCP.zoneUpdate();
        gameCP.sCost++;
        return gameCP;
    }
    
    public Coordinate validMoves(playerCommand pc){
        Coordinate ae = new Coordinate(pc);
        int tabX = player.getX()+ae.x;
        int tabY = player.getY()+ae.y;
        if (tab[tabX][tabY] instanceof Caisse){
            if (tab[tabX+ae.x][tabY+ae.y] instanceof Mur){
                ae.k = IMPOSSIBLE;
            }
            else if (tab[tabX+ae.x][tabY+ae.y] instanceof Caisse){
                ae.k = IMPOSSIBLE;
            }
            else if (isCoinDeadlock(tabX+ae.x,tabY+ae.y,ae) && player instanceof APlayer){
                ae.k = IMPOSSIBLE;
            }
            else if (isBlockDeadlock(tabX+ae.x,tabY+ae.y,ae) && player instanceof APlayer){
                ae.k = IMPOSSIBLE;
            }
            else{ae.k = CAISSE;}
        }
        else if (tab[tabX][tabY] instanceof Mur){
            ae.k = IMPOSSIBLE;
        }
        else if (tab[tabX][tabY] instanceof Vide 
                || tab[tabX][tabY] instanceof Zone){
            ae.k = VIDE;
        }
        return ae;
    }
    
    private void zoneUpdate(){
        for (int i = 0; i<loader.getZoneFinal().length;i++){
            for (int j = 0; j<loader.getZoneFinal()[i].length;j++){
                if (loader.getZoneFinal()[i][j] == true){
                    if (tab[i][j] instanceof Vide){                   
                        tab[i][j] = new Zone(i,j);
                    }
                }
            }
        }
    }
    
    public Game getCopy(){
        Game cp = new Game(player);
        cp.numberZoneFinal = numberZoneFinal;
        cp.loader = loader;
        cp.tab = getCopyTab();
        return cp;
    }
    
    public void gameReset(){
        initGame();
        this.setChanged();
        this.notifyObservers();
    }
    
    public String situationToString(){
        String ligne ="";
        if( this.tab == null){
            return ligne + "vide";
        }
        for (int i = 0; i<this.tab.length;i++){
            for (GameElement item : this.tab[i]) {
                ligne += item.getSymbol();
            }
            if(i < this.tab.length){
                ligne += System.lineSeparator() + "";
            }
        }
        return ligne;
    }
    
    public boolean playerIsWinner(){
        return (numberZoneFinal == 0);
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public playerCommand getPC(){
        return pCommand;
    }
    
    public void setPC(playerCommand pc){
        pCommand = pc;
    }
    
    public Game getPrevious(){
        return previous;
    }
    
    public void setPrevious(Game state){
        previous = state;
    }
    
    public List<Coordinate> returnCoord(String element){
    List<Coordinate> listCoord = new ArrayList<>();
    for(int i = 0;i<tab.length;i++){
        for(int j = 0;j<tab[0].length;j++){
            if(tab[i][j] instanceof Caisse && element.equals("Caisse")){
                listCoord.add(tab[i][j].getCoord()); // RETOURNE LA LISTE DES COORD DES CAISSES
            }
            if(loader.getZoneFinal()[i][j] && element.equals("Zone")){
                listCoord.add(tab[i][j].getCoord()); // RETOURNE LA LISTE DES COORD DES ZONES
            }
        }   
    }
    return listCoord;
    }
    
    public List<GameElement> returnElement(String element){
    List<GameElement> listCoord = new ArrayList<>();
    for(int i = 0;i<tab.length;i++){
        for(int j = 0;j<tab[0].length;j++){
            if(tab[i][j] instanceof Caisse && element.equals("Caisse")){
                listCoord.add(tab[i][j]); // RETOURNE LA LISTE DES CAISSES
            }
            if(loader.getZoneFinal()[i][j] && element.equals("Zone")){
                listCoord.add(tab[i][j]); // RETOURNE LA LISTE DES ZONES
            }
        }   
    }
    return listCoord;
    }
    
    public void setDist(){
        List<Coordinate> listCaisse = returnCoord("Caisse");
        List<Coordinate> listZone = returnCoord("Zone");
        Set<Coordinate> intersection = new HashSet<Coordinate>(listZone);
        intersection.retainAll(listCaisse);
        listCaisse.removeAll(intersection);
        listZone.removeAll(intersection);
        int distCost = 0;
        for (Coordinate caisse : listCaisse){
            int minCost = Integer.MAX_VALUE;
            for (Coordinate zone : listZone){
                int dist = getManhattanDistance(caisse,zone)
                        + 20 * getManhattanDistance(player.getCoord(),caisse);
                if (dist < minCost){
                    minCost = dist;
                }
            }
            distCost += minCost;
        }
        this.setCost(distCost+getSCost());
    }
        
    public int getManhattanDistance(Coordinate c1, Coordinate c2) {
        return Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y);
    }
    
    public int getCost(){
        return cost;
    }
    
    public void setCost(int n){
        cost = n;
    }
    
    public int getSCost(){
        return sCost;
    }
    
    public void setSCost(int n){
        sCost = n;
    }
    
    public boolean[][] getZoneFinal(){
        return loader.getZoneFinal();
    }

    public GameElement[][] getCopyTab() {
        GameElement[][] cp = new GameElement[tab.length][tab[0].length];
        for(int i = 0; i<tab.length;i++){
            for (int j = 0; j<tab[i].length;j++){
                if (tab[i][j] instanceof Caisse){
                        cp[i][j] = new Caisse(i,j);
                    }
                    else if (tab[i][j] instanceof Zone){
                        cp[i][j] = new Zone(i,j);
                    }
                    else if (tab[i][j] instanceof Player){
                        cp[i][j] = player.getCopy();
                    }
                    else{
                        cp[i][j] = tab[i][j];
                    }
            }
        }
        return cp;
    }
    
    @Override
    public boolean equals(Object o) {
        Game otherGame = (Game) o;
        if (otherGame == null || !(otherGame instanceof Game)) {
            return false;
        } 
        else if (this.hashCode() == otherGame.hashCode()){
            return true;
        }
        
        boolean bool = true;
        
        if (otherGame.player.getX() != this.player.getX() 
                || otherGame.player.getY() != this.player.getY()) {
            bool = false;
        }
        List<GameElement> list = this.returnElement("Caisse");
        for (GameElement caisse : list){
            Coordinate coord = new Coordinate(caisse.getX(), caisse.getY());
                if (!(otherGame.tab[coord.x][coord.y] instanceof Caisse)){
                    bool = false;
                }
        }
        
        return bool;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        int tmp = 1;
        hash = 37 * hash + player.getX()*25000 + player.getY()*200;
        for (Coordinate coord : returnCoord("Caisse")){
            tmp += tmp*((coord.getX()+10)*200 + coord.getY());
        }
        hash = 37 * hash + tmp;
        return hash;
    }
    
    public String getCoordStringCaisse(){
        String string = " coord: ";
        for (Coordinate coord : returnCoord("Caisse")){
            string += coord+" ";
        }
        return string;
    }
    
    public boolean isCoinDeadlock(int x, int y, Coordinate coord){
        if (tab[x][y] instanceof Zone){
            return false;
        }
        if (coord.getX() == -1 && coord.getY() == 0){
            return isCoinDeadlock(x,y,UP,RIGHT) 
                    || isCoinDeadlock(x,y,UP,LEFT);
        }
        else if (coord.getX() == 1 && coord.getY() == 0){
            return isCoinDeadlock(x,y,DOWN,RIGHT) 
                    || isCoinDeadlock(x,y,DOWN,LEFT);
        }
        else if (coord.getX() == 0 && coord.getY() == -1){
            return isCoinDeadlock(x,y,UP,LEFT) 
                    || isCoinDeadlock(x,y,DOWN,LEFT);
        }
        else if (coord.getX() == 0 && coord.getY() == 1){
            return isCoinDeadlock(x,y,UP,RIGHT) 
                    || isCoinDeadlock(x,y,DOWN,RIGHT);
        }
        else{
            return false;
        }
    }
    
    public boolean isCoinDeadlock(int x, int y, playerCommand pc1, playerCommand pc2){
        try{
        return (tab[x+pc1.getX()][y+pc1.getY()] instanceof Mur 
                && tab[x+pc2.getX()][y+pc2.getY()] instanceof Mur);
        }
        catch(ArrayIndexOutOfBoundsException aioobe){return false;}
    }
    
    public boolean isBlockDeadlock(int x, int y, Coordinate coord){
        if (tab[x][y] instanceof Zone){
            return false;
        }
        if (coord.getX() == -1 && coord.getY() == 0){
            return isBlockDeadlock(x,y,UP,RIGHT,coord) 
                    || isBlockDeadlock(x,y,UP,LEFT,coord);
        }
        else if (coord.getX() == 1 && coord.getY() == 0){
            return isBlockDeadlock(x,y,DOWN,RIGHT,coord) 
                    || isBlockDeadlock(x,y,DOWN,LEFT,coord);
        }
        else if (coord.getX() == 0 && coord.getY() == -1){
            return isBlockDeadlock(x,y,UP,LEFT,coord) 
                    || isBlockDeadlock(x,y,DOWN,LEFT,coord);
        }
        else if (coord.getX() == 0 && coord.getY() == 1){
            return isBlockDeadlock(x,y,UP,RIGHT,coord) 
                    || isBlockDeadlock(x,y,DOWN,RIGHT,coord);
        }
        else{
            return false;
        }
    }
    
    public boolean isBlockDeadlock(int x, int y, playerCommand pc1, playerCommand pc2, Coordinate coord){
        try{
            if ((x-coord.getX() == x+pc1.getX() || x-coord.getX() == x+pc2.getX()) 
                    && (y-coord.getY() == y+pc1.getY() || y-coord.getY() == y+pc2.getY())){
                return false;
            }
            return ((tab[x+pc1.getX()][y+pc1.getY()] instanceof Mur 
                    || tab[x+pc1.getX()][y+pc1.getY()] instanceof Caisse)
                    && (tab[x+pc2.getX()][y+pc2.getY()] instanceof Mur 
                    || tab[x+pc2.getX()][y+pc2.getY()] instanceof Caisse) 
                    && (tab[x+pc1.getX()+pc2.getX()][y+pc1.getY()+pc2.getY()] instanceof Mur 
                    || tab[x+pc1.getX()+pc2.getX()][y+pc1.getY()+pc2.getY()] instanceof Caisse));
        }
        catch(ArrayIndexOutOfBoundsException aioobe){return false;}
    }
    
    
}
