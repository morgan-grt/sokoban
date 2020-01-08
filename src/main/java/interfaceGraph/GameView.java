/*
*Permet d'écouter le jeu et de le jouer en invite de commande
*/
package interfaceGraph;
import game.*;
import gameModel.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class GameView implements Observer{
    protected Game game;
    
    public GameView(){
        initGame();
        this.game.addObserver(this);
        System.out.println(this.game.situationToString());
        Scanner scanner = new Scanner(System.in);
        int inputScanner = scanner.nextInt();
        System.out.println(inputScanner);
        this.game.setAction(inputScanner);
    }
    
    public void initGame(){
        System.out.println("Choix du niveau: entre 1 et 10");
        Scanner scanner = new Scanner(System.in);
        int inputLevel = scanner.nextInt();
        String level = "level"+inputLevel;
        System.out.println("Niveau choisis: "+level);
        Player player = new APlayer("Player","Mario");
        game = new Game(new LevelLoaderFromFile(level,player),player);
        if (player instanceof APlayer){
            player.setCurrentState(game);
        }
        
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        if (game.playerIsWinner()){
            System.out.println(game.situationToString() + "\n");
            System.out.println("Vous avez gagné!");
        }
        else{
            System.out.println(game.situationToString());
            Scanner scanner = new Scanner(System.in);
            int inputScanner = scanner.nextInt();
            System.out.println(inputScanner);
            game.setAction(inputScanner);
        }
    }
    
}
