/**
* element zone du jeu
*/

package gameModel;

public class Zone extends GameElement{
    protected char Symbol = '.';

    public Zone(int x, int y){
        super('.',x,y);
    }
}
