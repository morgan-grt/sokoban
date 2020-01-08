/*
 * Panel personnalisée qui extends d'un Panel classique
 */
package interfaceGraph;
import game.*;
import gameModel.Caisse;
import gameModel.Mur;
import gameModel.Player;
import gameModel.Zone;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observer;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import static menu.AbstractMenu.color1;
import static menu.AbstractMenu.font2;

public class GamePanel extends JPanel implements Observer{
    private final int cote;
    protected Game game;
    private JButton replay;
    protected int replayX, replayY;
    private Image murImg, caisseImg, zoneImg, playerImg, caisseOnImg;
    private String spritePlayer;

    /**
     * Constructeur d'un panneau personalise qui agrandis les fonctionalitees de
     * base d'un panneau
     * @param frame
     * @param game
     */
    public GamePanel(Game game,JFrame frame, String spritePlayer) {
        super();
        this.cote = (game.getTab()[0].length) >= 13 ? 32 : 64;
        this.game = game;
        this.game.addObserver(this);
        this.setLayout(null);
        replayX = game.getTab()[0].length*this.cote+64;
        replayY = game.getTab().length*this.cote+64;
        replay = new JButton("RETRY");
        replay.setBounds(replayX/4, replayY, 128, 32);
        replay.addActionListener((ActionListener) frame);
        replay.setFont(font2);
        replay.setForeground(Color.BLACK);
        replay.setBackground(color1);
        replay.setRolloverEnabled(false);
        replay.setBorder(new LineBorder(Color.BLACK));
        replay.setVisible(true);
        replay.setFocusable(false);
        this.spritePlayer = spritePlayer;
        try {
            Image icon = ImageIO.read(this.getClass().getResource("/sprites/" + "retry_icon" + ".png"));
            replay.setIcon(new ImageIcon(icon));
        } 
        catch (IOException ex) {}
        this.add(replay);
        chargeImage();
    }
    
    public void chargeImage(){
        try {
            murImg = ImageIO.read(this.getClass().getResource("/sprites/" + "mur" + ".png"));
            caisseImg = ImageIO.read(this.getClass().getResource("/sprites/" + "caisse" + ".png"));
            zoneImg = ImageIO.read(this.getClass().getResource("/sprites/" + "zone" + ".png"));
            playerImg = ImageIO.read(this.getClass().getResource("/sprites/" + spritePlayer + ".png"));
            caisseOnImg = ImageIO.read(this.getClass().getResource("/sprites/" + "caisse_zone" + ".png"));
        } catch (IOException ex) {
            //Si on arrive pas à lire l'image une erreur est lancée
            Logger.getLogger(GamePanel.class.getName()).log(Level.WARNING, null, ex);
        }
    }
    
    public void afficheImg(Graphics g, int x, int y, Image img){
        g.drawImage(img, x, y, cote, cote, null);
    }
    
    public void gameReset(){
        game.gameReset();
    }
    
    public JButton getReplayButton(){
        return replay;
    }
    
    @Override
    public void update(Observable obs, Object arg){
        this.repaint();
    }
    
    public int getCote(){
        return cote;
    }
    
    public void refresh(){
        repaint();
        revalidate();
    }
    
    public void setPlayerImage(String img){
        try {
            playerImg = ImageIO.read(this.getClass().getResource("/sprites/" + img + ".png"));
        } catch (IOException ex) {
            //Si on arrive pas à lire l'image une erreur est lancée
            Logger.getLogger(GamePanel.class.getName()).log(Level.WARNING, null, ex);
        }
    }
    
    /**
     * Surcharge de la methode paint qui est appelée a chaque fois que l'on met
     * à jour l'ecran
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < game.getTab().length; i++) {
            for (int j = 0; j < game.getTab()[i].length; j++) {
                if (game.getTab()[i][j] instanceof Mur){
                    afficheImg(g, j * cote, i * cote, murImg);
                }
                else if (game.getTab()[i][j] instanceof Caisse){
                    afficheImg(g, j * cote, i * cote, caisseImg);
                    if (game.getLoader().getZoneFinal()[i][j]){
                        afficheImg(g, j * cote, i * cote, caisseOnImg);
                    }
                }
                else if (game.getTab()[i][j] instanceof Zone){
                    afficheImg(g, j * cote, i * cote, zoneImg);
                }
                else if (game.getTab()[i][j] instanceof Player){
                    afficheImg(g, j * cote, i * cote, playerImg);
                }
                
            }
        }
    }
    
    public int getReplayX(){
        return replayX;
    }
    
    public int getReplayY(){
        return replayY;
    }
}
