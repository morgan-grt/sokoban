/**
* controle le mouvement effectue par le joueur
*/

package controler;

import gameModel.GameElement.playerCommand;
import static gameModel.GameElement.playerCommand.*;
import gameModel.Player;

public class Controler {
    private Player player;

    public Controler(Player player){
        this.player = player;
    }

    public playerCommand getHPlayerCommand(int key){
        if (player.toString() == "Player2"){
            if (key == 37){
                return LEFT;
            }
            else if (key == 38){
                return UP;
            }
            else if (key == 39){
                return RIGHT;
            }
            else if (key == 40){
                return DOWN;
            }
        }
        else{
            if (key == 81 || key == 4){
                return LEFT;
            }
            else if (key == 90 || key == 8){
                return UP;
            }
            else if (key == 68 || key == 6){
                return RIGHT;
            }
            else if (key == 83 || key == 2){
                return DOWN;
            }
        }
        return CENTER;
    }

    public playerCommand getAPlayerCommand(playerCommand key){
        return key;
    }
}
