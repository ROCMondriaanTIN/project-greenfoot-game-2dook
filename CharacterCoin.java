
/**
 * Write a description of class CharacterCoin here.
 *
 * @author G. A
 * @version 0.1
 */
public class CharacterCoin extends Tile {

    /**
     * Act - do whatever the CharacterCoin wants to do. This method is called
     * whenever the 'Act' or 'Run' button gets pressed in the environment.
     */
    int switchTo;
    // String kleur;

    /*
    ID: 0 -> Groene hero
    ID: 1 -> Blauwe hero
    ID: 2 -> Roze hero
     */
    
    public CharacterCoin(String image, int width, int height,int switchToHeroNum) {
        super(image, width, height);
        switch (switchToHeroNum) {
            case 0:
                this.switchTo = switchToHeroNum;
                this.setImage("hud_p1.png");
                break;

            case 1:
                this.switchTo = switchToHeroNum;
                this.setImage("hud_p2.png");
                break;

            case 2:
                this.switchTo = switchToHeroNum;
                this.setImage("hud_p3.png");
                break;

            default:
                break;
        }
    }

    public void act() {
        // applyVelocity();
    }
}
