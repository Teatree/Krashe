package game.actors.controllers.powerups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.stages.GameStage;
import game.utils.GlobalConstants;

import java.util.Random;
import static game.stages.GameScreenScript.*;
/**
 * Created by MainUser on 12/07/2015.
 */
public class ButterflyController implements IScript{
    private CompositeItem item;
    public Random random = new Random();
    public Rectangle boundsRect = new Rectangle();
    float velocityX;
    float velocityY;
    float gravity;
    float speedIncrCoeficient = 1f;
    float gravityDecreaseMultiplier = 1.5f;

    @Override
    public void init(CompositeItem item) {
        this.item = item;

        pushUmbrella(310, 400, 45, 55);
    }

    @Override
    public void dispose() {
        item.dispose();
    }

    public void pushUmbrella(int randXmin, int randXmax, int randYmin, int randYmax) {
        velocityX = ((random.nextInt(randXmax-randXmin)+randXmin)*-1)*speedIncrCoeficient;
//        gravity *= speedIncrCoeficient/2;
        System.out.println("velocityX " + velocityX);
        if(item.getY()> Gdx.graphics.getHeight()/2){
            velocityY = (random.nextInt((randYmax-randYmin)+randYmin)*-1)*speedIncrCoeficient;
        }else {
            velocityY = (random.nextInt((randYmax - randYmin) + randYmin))*speedIncrCoeficient;
        }
        System.out.println("velocityY " + velocityY);
//        speedIncrCoeficient += 0.5f;
        gravity = Math.abs(velocityX/(7-speedIncrCoeficient*gravityDecreaseMultiplier));
        speedIncrCoeficient += 0.1f;
        gravityDecreaseMultiplier -= 0.05f;
        System.out.println("gravity " + gravity);
    }

    @Override
    public void act(float delta) {
        if(isGameAlive()) {
            velocityX += gravity * delta;
            item.setX(item.getX() + velocityX * delta);
            item.setY(item.getY() + velocityY * delta);
        }
    }

    public void updateRect() {
        boundsRect.x = (int)item.getX();
        boundsRect.y = (int)item.getY();
        boundsRect.width = (int)item.getWidth();
        boundsRect.height = (int)item.getHeight();
    }

    public Rectangle getBoundsRectangle() {
        updateRect();
        return boundsRect;
    }

    public CompositeItem getCompositeItem(){
        return item;
    }
}
