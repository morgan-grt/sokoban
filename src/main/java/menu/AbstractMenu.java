/**
* classe abstraite du menu
*/

package menu;
import interfaceGraph.GameGUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public abstract class AbstractMenu extends JPanel{
    protected final String[] listLevels = new String[]
    {"level1", "level2", "level3", "level4", "level5"
            , "level6", "level7", "level8"
            , "level9", "level10","level11"};
    protected final String[] listSprites = new String[]
    {"Mario", "Luigi", "Toad", "Peach", "Koopa","Yoshi"};
    private JLabel label;
    private final static String error = "ERROR";
    protected JButton button1, button2, button3, button4;
    protected JComboBox combo1, combo2, combo3;
    private GameGUI frame;
    private Image img;
    public final static Color color1 = new Color(0,0,0,75);
    public final static Color color2 = new Color(0,114,152);
    public final static Font font = new Font("Bitstream Vera Sans Mono",Font.PLAIN,28);
    public final static Font font2 = new Font("Bitstream Vera Sans Mono",Font.ITALIC,14);
    public GridBagConstraints pos = new GridBagConstraints();

    public AbstractMenu(GameGUI frame, String BUTTON1, String BUTTON2, String nomImgFond) throws IOException {
        this.frame = frame;
        button1 = createJButton(BUTTON1,320,96);
        setJButtonIcon(button1,"play_icon");
        button1.addActionListener((ActionListener) this.frame);
        button2 = createJButton(BUTTON2,320,96);
        setJButtonIcon(button2,"cross_icon");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        setLayout(new GridBagLayout());
        pos.fill = GridBagConstraints.BOTH;
        setJButton(button1,0, 0, 2, 1);
        setJButton(button2,0,1,1,1);

        try{
            this.img = ImageIO.read(this.getClass().getResource("/sprites/" + nomImgFond + ".png"));
        }
        catch(FileNotFoundException fnfe){

        }

    }

    public JButton getButton1(){
        return button1;
    }

    public JButton getButton2(){
        return button2;
    }

    public JButton getButton3(){
        return button3;
    }

    public JButton getButton4(){
        return button4;
    }

    public JComboBox getCombo1(){
        return combo1;
    }

    public JComboBox getCombo2(){
        return combo2;
    }

    public JComboBox getCombo3(){
        return combo3;
    }

    public JButton createJButton(String NAME, int width, int height){
        JButton button = new JButton(NAME);
        button.setPreferredSize(new Dimension(width, height));
        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setBackground(color1);
        button.setFocusable(false);
        button.setRolloverEnabled(false);
        button.setBorder(new LineBorder(Color.WHITE));
        return button;
    }

    public void setJButtonIcon(JButton button,String imgString) throws IOException{
        try {
            Image icon = ImageIO.read(this.getClass().getResource("/sprites/" + imgString + ".png"));
            button.setIcon(new ImageIcon(icon));
        }
        catch (IOException ex) {}
    }

    public JComboBox createJComboBox(String[] tab, int width, int height){
        JComboBox box = new JComboBox<String>(tab);
        box.setPreferredSize(new Dimension(width, height));
        box.setFont(font);
        box.setForeground(Color.white);
        box.setBackground(color2);
        box.setFocusable(false);
        box.setBorder(new LineBorder(Color.WHITE));
        return box;
    }

    public void setJButton(JButton button, int x, int y, int width, int height){
        pos.gridx = x;
        pos.gridwidth = width;
        pos.gridheight = height;
        pos.gridy = y;
        add(button,pos);
    }

    public void setJComboBox(JComboBox button, int x, int y, int width, int height){
        pos.gridx = x;
        pos.gridwidth = width;
        pos.gridheight = height;
        pos.gridy = y;
        add(button,pos);
    }

    public void setWinner(String name){
        if (label != null){
            remove(label);
            label = new JLabel("You won together!");
        }
        else{
            label = new JLabel(name+" won the Game!");
        }
        label.setFont(font);
        label.setBackground(Color.darkGray);
        label.setForeground(Color.darkGray);
        label.setLayout(new GridBagLayout());
        GridBagConstraints pos = new GridBagConstraints();
        //case de depart du composant
        pos.gridx=0;
        pos.gridy=4;
        this.add(label,pos);
    }

    public void resetLabel(){
        try {
            this.remove(label);
            label = null;
        }
        catch(NullPointerException npe){}
    }

    public String[] getListSprites(){
        return listSprites;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, frame.getWidth(), frame.getHeight(), null);
    }
}
