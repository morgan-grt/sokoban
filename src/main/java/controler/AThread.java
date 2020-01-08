/**
* controleur de l'ia
*/

package controler;

import gameModel.APlayer;
import interfaceGraph.GameGUI;

public class AThread extends Thread {
    private APlayer player;
    private GameGUI frame;

    public AThread(APlayer player){
        this.player = player;
    }

    public AThread(APlayer player, GameGUI frame){
        this(player);
        this.frame = frame;
    }

    @Override
    public void run(){
        player.AStarAnityme(player.getGame());
        frame.getPanel2().gameReset();
        player.setI(0);
        player.getListMovesAnityme().clear();

        //frame.getPanelPrincipal().setPanel2(frame.getPanel2());
        stop();
    }
}
