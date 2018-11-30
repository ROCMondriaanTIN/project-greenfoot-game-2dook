
import greenfoot.*;
import java.util.List;

/**
 *
 * @author R. Springer
 */
public class Hero extends Mover {
// Stats

    private final double gravity;
    private final double acc;
    private final double drag;
    int charNum;
    private int ster;
// Data
    public String worldName;
    private boolean inAir;
    public int direction = 2;
    public int animationTimer = 0;
    public int PicNum = 1;
// Inventory
    public boolean hasRedKey;
    public boolean hasBlueKey;
    public boolean hasGreenKey;
    public boolean hasYellowKey;

    /*
    ID: 0 -> Groene hero
    ID: 1 -> Blauwe hero
    ID: 2 -> Roze hero
     */
    public Hero(String worldName, int charNum) {
        super();
        gravity = 8;
        acc = 0.6;
        drag = 0.8;
        // setImage("p" + charNum + "_front.png");
        this.worldName = worldName;
        this.charNum = charNum;
    }

    @Override
    public void act() {
        handleInput();
        ster();
        door();
        key();
        unlock();
        velocityX *= drag;
        velocityY += acc;
        if (velocityY > gravity) {
            velocityY = gravity;
        }
        applyVelocity();
        checkForIntersectingObjects();

    }

    public void checkForIntersectingObjects() {
        for (Actor enemy : getIntersectingObjects(Enemy.class)) {
            if (enemy != null) {
                // getWorld().removeObject(this);
                getWorld().addObject(new GameOver(), 500, 200);
                getWorld().removeObject(this);
                // setLocation(300, 200);
                break;
            }
        }
        for (WaterTop wt : getIntersectingObjects(WaterTop.class)) {
            if (wt != null) {
                getWorld().addObject(new GameOver(), 500, 200);

                getWorld().removeObject(this);
                break;
            }
        }
    }

    public void unlock() {
        List<Lock> locks = this.getNeighbours(100, true, Lock.class);
        if (!locks.isEmpty()) {
            for (int i = 0; i < locks.size(); i++) {
                if (hasRedKey && locks.get(i).kleur == "RED") {
                    locks.get(i).setLocation(1000, 1000);
                } else if (hasBlueKey && locks.get(i).kleur == "BLUE") {
                    locks.get(i).setLocation(1000, 1000);
                } else {
                    // System.out.println("No key.");
                }
            }
        }
    }

    public void hideHero() {
        this.setImage(new GreenfootImage(1, 1));
    }

    public void door() {
        for (Door d1 : getIntersectingObjects(Door.class)) {
            if (d1 != null) {
                System.out.println(d1);
            }
        }
    }

    public boolean keySpace() {
        boolean keySpace = Greenfoot.isKeyDown("space");
        return keySpace;
    }

    public boolean keyRight() {
        boolean keyRight = Greenfoot.isKeyDown("right");
        return keyRight;
    }

    public boolean keyLeft() {
        boolean keyLeft = Greenfoot.isKeyDown("left");
        return keyLeft;
    }

    public void animatieStanding() {
        if (keySpace() == false && keyLeft() == false && keyRight() == false && velocityY == 0) {
            setImage("alien" + charNum + "_stand" + direction + ".png");
        }
    }

    public void animatieJump() {
        if (velocityY != 0) {
            setImage("alien" + charNum + "_jump" + direction + ".png");
        }
    }

    public boolean opGrond() {
        Actor onder = getOneObjectAtOffset(0, getImage().getHeight() / 2, Tile.class);
        Tile tile = (Tile) onder;
        return tile != null && tile.isSolid == true;
    }

    public void handleInput() {
        animatieStanding();
        animatieJump();
        if (keySpace() && opGrond() == true) {
            velocityY = -14;
        } else if (Greenfoot.isKeyDown("up") && opGrond() == true) {
            velocityY = -14;
        }
        if (Greenfoot.isKeyDown("h")) {
            velocityY = -14;
        }
        if (keyLeft() && keyRight() == false) {
            velocityX = -4;
            direction = 1;
            if (animationTimer % 10 == 0 && velocityY == 0) {
                animatie();
            }
            animationTimer++;

        } else if (keyRight()) {
            velocityX = 4;
            direction = 2;
            if (animationTimer % 10 == 0 && velocityY == 0) {
                animatie();
            }
            animationTimer++;
        }
    }

    public void animatie() {

        if (PicNum == 1) {
            setImage("alien" + charNum + "_walk" + direction + "1.png");
        } else if (PicNum == 2) {
            setImage("alien" + charNum + "_walk" + direction + "2.png");
            PicNum = 1;
            return;
        }
        PicNum++;
    }

    public int getSter() {
        return this.ster;
    }

    public void ster() {
        if (isTouching(Ster.class)) {
            removeTouching(Ster.class);
            getWorld().getObjects(SterCount.class).get(0).starsCollected++;
        }
    }

    public void key() {
        for (KeySpawnable ks : getIntersectingObjects(KeySpawnable.class)) {
            if (ks != null) {
                switch (ks.keyColor) {
                    case "BLUE":
                        hasBlueKey = true;
                        removeTouching(KeySpawnable.class);
                        break;
                    case "RED":
                        hasRedKey = true;
                        removeTouching(KeySpawnable.class);
                        break;
                    case "GREEN":
                        hasGreenKey = true;
                        removeTouching(KeySpawnable.class);
                        break;

                    default:
                        System.out.println("No key color specified in constructor. Check KeySpawnable.java");
                }
            }
        }
    }

    public int getWidth() {
        return getImage().getWidth();
    }

    public int getHeight() {
        return getImage().getHeight();
    }
}
