/**
* classe principale de l'interface graphique
*/

package interfaceGraph;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class GamePrincipalPanel extends JPanel {
    protected GamePanel panel1, panel2;

    public GamePrincipalPanel(GamePanel panel1, GamePanel panel2, GameGUI frame){
        this.panel1 = panel1;
        this.panel2 = panel2;
        setLayout(new GridLayout(1,2));
        add(panel1);
        add(panel2);
    }

    public GamePanel getPanel1(){
        return panel1;
    }

    public GamePanel getPanel2(){
        return panel2;
    }

    public void setPanel1(GamePanel panel){
        panel1 = panel;
        update();
    }

    public void setPanel2(GamePanel panel){
        panel2 = panel;
        update();
    }

    public void update(){
        removeAll();
        add(panel1);
        add(panel2);
        refresh();
    }

    public void refresh(){
        repaint();
        revalidate();
    }
}
