/*
*Permet l'affichage des panels en JFrame
*/
package interfaceGraph;
import controler.AThread;
import game.Game;
import game.LevelLoaderFromFile;
import menu.*;
import gameModel.APlayer;
import gameModel.HPlayer;
import gameModel.Player;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;

public class GameGUI extends JFrame implements KeyListener,ActionListener{
    private Game currentGame, game1, game2;
    private GamePanel currentPanel, panel1, panel2;
    private GamePrincipalPanel panelPrincipal;
    private Player player1, player2;
    private AbstractMenu menu, pause, victory, option;
    private String level = "level1";
    private int numberMenu;
    private String sprite1 = "Mario",sprite2 = "Mario";
    private boolean want2Player = false, wantAnityme = false;
    
    public GameGUI() throws IOException {
        menu = new PrincipalMenu(this);
        pause = new PauseMenu(this);
        victory = new VictoryMenu(this);
        option = new OptionMenu(this);
        numberMenu = 0;
        this.setTitle("Sokoban major");
        this.setSize(1024,576);
        //Positionnement au centre
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(1,2));
        this.setContentPane(menu);
        //Ferme la fenetre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Visibilité       
        this.setVisible(true);
        
    }
    
    private void switchGame() {
        currentGame = currentGame == game1 ? game2 : game1;
        currentPanel = currentPanel == panel1 ? panel2 : panel1;
    }
    
    private void refreshSize(){
        this.setSize((game1.getTab()[0].length*(panel1.getCote()))
                +(game2.getTab()[0].length*(panel2.getCote())+64)
                , Math.max((game1.getTab().length*(panel1.getCote()))
                        ,(game2.getTab().length*(panel2.getCote())))+128);
        refresh();
    }
    
    public void switchMenu(int number){
        numberMenu = number;
        if (numberMenu == 0) {
            this.setContentPane(menu);
        }
        else if (numberMenu == 1){
            if (panel1 != null) {
                this.setContentPane(panelPrincipal);
            }
        }
        else if (numberMenu == 2){
            this.setContentPane(pause);
        }
        else if (numberMenu == 3){
            this.setContentPane(victory);
        }
        else if (numberMenu == 4){
            this.setContentPane(option);
        }
        refresh();
    }
    
    private void initGame(){
        this.removeKeyListener(this);
        this.removeKeyListener(this);
        initGame1();
        initGame2();
        panelPrincipal = new GamePrincipalPanel(panel1,panel2,this);
        currentGame = game1;
        currentPanel = panel1;
        this.addKeyListener(this);
        this.addKeyListener(this);
    }
    
    public void initGame1(){
        player1 = new HPlayer("Player1");
        game1 = new Game(new LevelLoaderFromFile(level,player1),player1);
        panel1 = new GamePanel(game1,this,sprite1);
        panel1.setPlayerImage(sprite1);
    }
    
    public void initGame2(){
        if (want2Player){
            player2 = new HPlayer("Player2");
        }
        else{
            int randomIndex = new Random()
                    .nextInt(menu.getListSprites().length);
            sprite2 = menu.getListSprites()[randomIndex];
            player2 = new APlayer("Player2",sprite2);
        }
        game2 = new Game(new LevelLoaderFromFile(level,player2),player2);
        panel2 = new GamePanel(game2,this,sprite2);
        panel2.setPlayerImage(sprite2);
        if (player2 instanceof APlayer){
            player2.setCurrentState(game2);
            if (wantAnityme){
                AThread aThread = new AThread((APlayer) player2,this);
                aThread.start();
            }
            else{
                player2.setListMoves();
            }
        }
    }
    
    private void init(boolean bool){
        want2Player = bool;
        initGame();
        refreshSize();
        switchMenu(1);
        refresh();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {        
        int code = e.getKeyCode();
        if (e.getKeyChar() == 'p'){
            switchMenu(2);
        }
        else{
            try{
                currentGame.setAction(code);
                if(currentGame.playerIsWinner()){
                    victory.setWinner(currentGame.getPlayer().toString());
                    this.removeKeyListener(this);
                    this.removeKeyListener(this);
                    switchMenu(3);
                }
            }
            catch(NullPointerException npe){}
        }
        switchGame();
        refresh();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    @Override
    public void keyTyped(KeyEvent e) {         
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.getButton1()){
            init(false);
        }
        else if (e.getSource() == pause.getButton1()){
            refreshSize();
            switchMenu(1);
        }
        else if (e.getSource() == option.getCombo1()){
            level = (String) option.getCombo1().getSelectedItem();
        }
        else if (e.getSource() == option.getCombo2()){
            sprite1 = (String) option.getCombo2().getSelectedItem();
        }
        else if (e.getSource() == option.getCombo3()){
            sprite2 = (String) option.getCombo3().getSelectedItem();
        }
        else if (e.getSource() == victory.getButton1() 
                || e.getSource() == pause.getButton3()){
            victory.resetLabel();
            switchMenu(0);
        }
        else if (e.getSource() == menu.getButton3()){
            switchMenu(4);
        }
        else if (e.getSource() == option.getButton1()){
            switchMenu(0);
        }
        else if (e.getSource() == option.getButton3()){
            init(true);
        }
        else if (e.getSource() == option.getButton2()){
            wantAnityme = wantAnityme == true ? false : true;
        }
        try{
            if (e.getSource() == panel1.getReplayButton()){
                initGame1();
                panelPrincipal.setPanel1(panel1);
            }
            else if (e.getSource() == panel2.getReplayButton()){
                initGame2();
                panelPrincipal.setPanel2(panel2);
            }
        }
        catch(NullPointerException npe){}
        refresh();
    }
    
    public void refresh(){
        repaint();
        revalidate();
    }
    
    public GamePrincipalPanel getPanelPrincipal(){
        return panelPrincipal;
    }
    
    public GamePanel getPanel1(){
        return panel1;
    }
    
    public GamePanel getPanel2(){
        return panel2;
    }
}
