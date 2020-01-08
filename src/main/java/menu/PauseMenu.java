/**
* menu de pause, accessible avec la touche 'p'
*/

package menu;
import interfaceGraph.GameGUI;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PauseMenu extends AbstractMenu {
    public PauseMenu(GameGUI frame) throws IOException {
        super(frame,"RESUME","QUIT","pause");
        setJButtonIcon(button1,"replay_icon");
        button2.setPreferredSize(new Dimension(160, 96));
        button3 = createJButton("MENU",160,96);
        setJButtonIcon(button3,"menu_icon");
        button3.addActionListener((ActionListener) frame);
        setJButton(button3,1,1,1,1);
    }
}
