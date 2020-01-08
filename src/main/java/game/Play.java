package game;
import java.io.*;
import interfaceGraph.*;

/**
 *main class to launch in terminal or interface graphic
 * depending on an argument
 */
public class Play {
    public static void main(String[] args) throws FileNotFoundException, IOException {
	if (args.length != 0){
	        boolean wantFrame = (Integer.parseInt(args[0]) != 0);                       
	        if(!wantFrame){
	            GameView gameView = new GameView();
	        }
	        else{
	            GameGUI gameGUI = new GameGUI();
	        }
    	}
    	else{
			GameGUI gameGUI = new GameGUI();
    	}
    }
}
