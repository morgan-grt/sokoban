/**
 * affichage du menu
 */
package menu;
import interfaceGraph.GameGUI;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PrincipalMenu extends AbstractMenu {


    public PrincipalMenu(GameGUI frame) throws IOException{
        super(frame,"PLAY","QUIT","principal");
        button2.setPreferredSize(new Dimension(128, 96));
        button1.setPreferredSize(new Dimension(320, 96));

        button3 = createJButton("OPTIONS",192,96);
        setJButtonIcon(button3,"option_icon");
        button3.addActionListener((ActionListener) frame);
        setJButton(button3,1,1,1,1);

    }
}
