/*
* menu de victoire
*/


package menu;
import interfaceGraph.GameGUI;
import java.io.IOException;

public class VictoryMenu extends AbstractMenu{
    public VictoryMenu(GameGUI frame) throws IOException {
        super(frame,"MENU","QUIT","victory");
        setJButtonIcon(button1,"menu_icon");
    }
}
