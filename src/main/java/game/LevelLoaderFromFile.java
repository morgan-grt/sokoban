/**
* permet le chargement du fichier et creer un plateau de jeu
*/
package game;
import gameModel.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LevelLoaderFromFile implements LevelLoader{
    private String level;
    private GameElement [][] tab;
    private boolean[][] tabZoneFinal;
    private int numberZoneFinal = 0;
    private Coordinate positionInitialePlayer;
    protected Player player;

    public LevelLoaderFromFile(String level,Player player){
        this.level = level;
        this.player = player;
        this.tab = this.chargerLevel();
    }

    @Override
    public void initPlayerPosition(){
        player.setCoord(new Coordinate(positionInitialePlayer.x
                , positionInitialePlayer.y));
    }


    @Override
    public GameElement[][] getTab(){
        GameElement[][] tabCopy =
                new GameElement[tab.length][tab[0].length];
        for (int i = 0; i<tab.length;i++){
                for (int j = 0; j<tab[0].length;j++){
                    if (tab[i][j] instanceof Caisse){
                        tabCopy[i][j] = new Caisse(i,j);
                    }
                    else if (tab[i][j] instanceof Zone){
                        tabCopy[i][j] = new Zone(i,j);
                    }
                    else if (tab[i][j] instanceof Player){
                        tabCopy[i][j] = player.getCopy();
                    }
                    else{
                        tabCopy[i][j] = tab[i][j];
                    }

                }
        }
        return tabCopy;
    }

    public boolean[][] getZoneFinal(boolean bool){
        if (bool){
            return tabZoneFinal;
        }
        else{
            boolean[][] zoneFinalCopy =
                    new boolean[tabZoneFinal.length][tabZoneFinal[0].length];
            for (int i = 0; i<tabZoneFinal.length;i++){
                    for (int j = 0; j<tabZoneFinal[0].length;j++){
                        zoneFinalCopy[i][j] = tabZoneFinal[i][j];
                    }
            }
            return zoneFinalCopy;
        }
    }

    @Override
    public boolean[][] getZoneFinal(){
        return getZoneFinal(true);
    }

    @Override
    public int getNumberZoneFinal(){
        return numberZoneFinal;
    }

    public GameElement chooseDecor(Character c, int x, int y){
        GameElement gameElement;
            switch (c) {
                case '$':
                    gameElement = new Caisse(x,y);
                    break;
                case '#':
                    gameElement = new Mur(x,y);
                    break;
                case '.':
                    gameElement = new Zone(x,y);
                    break;
                default:
                    gameElement = new Vide(x,y);
                    break;
            }
        return gameElement;
    }

    public GameElement[][] chargerPlateau(Reader r){
        GameElement[][] levelTab = null;
        List<String> lines=new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(r);
            String line=br.readLine();
            int n = 0,k  = 0;
            while (line!=null){
                n++;
                lines.add(line);
                k = line.length();
                line=br.readLine();
            }
            levelTab = new GameElement[n][k];
            tabZoneFinal = new boolean[n][k];
            for (int i = 0; i<n;i++){
                String temp = lines.get(i);
                for (int j = 0; j<k;j++){
                    if (temp.charAt(j) == '@'){
                        levelTab[i][j] = player;
                        positionInitialePlayer=new Coordinate(i,j);
                        player.setCoord(new Coordinate(i,j));
                    }
                    else {
                        levelTab[i][j] = this.chooseDecor(temp.charAt(j),i,j);
                    }
                    if (levelTab[i][j] instanceof Zone){
                        tabZoneFinal[i][j] = true;
                        numberZoneFinal++;
                    }
                    else{tabZoneFinal[i][j] = false;}

                }
            }
            return levelTab;
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return levelTab;
    }

    public GameElement[][] chargerLevel(){
        File inputFile;
        GameElement[][] tabTemp = null;
        try{
            inputFile = new File("build/levels/" + this.level + ".xsb");
            tabTemp = this.chargerPlateau(new FileReader(inputFile));
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(LevelLoaderFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }



        return tabTemp;
    }

}
