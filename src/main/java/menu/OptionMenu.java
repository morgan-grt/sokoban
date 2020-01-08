/**
* menu d'option, complemente le menu principal (propose des options suplémentaire de jeu)
*/

package menu;

import interfaceGraph.GameGUI;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OptionMenu extends AbstractMenu{

    public OptionMenu(GameGUI frame) throws IOException {
        super(frame, "RETURN", "QUIT", "option");
        remove(button2);
        button2 = createJButton("ANYTIME?",192,96);
        setJButtonIcon(button2,"loop_icon");
        button2.addActionListener((ActionListener) frame);
        setJButton(button2,0,2,1,1);

        button1.setPreferredSize(new Dimension(320, 96));
        setJButtonIcon(button1,"return_icon");
        combo2 = createJComboBox(listSprites,128,96);
        combo2.addActionListener((ActionListener) frame);
        setJComboBox(combo2,1,1,1,1);

        combo3 = createJComboBox(listSprites,128,96);
        combo3.addActionListener((ActionListener) frame);
        setJComboBox(combo3,1,2,1,1);

        combo1 = createJComboBox(listLevels,160,288);
        combo1.addActionListener((ActionListener) frame);
        setJComboBox(combo1,2,0,1,3);

        button3 = createJButton("2 PLAYER",192,96);
        button3.addActionListener((ActionListener) frame);
        setJButton(button3,0,1,1,1);

    }
}
