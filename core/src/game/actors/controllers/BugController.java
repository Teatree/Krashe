package game.actors.controllers;

import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import game.stages.GameStage;

import java.util.Random;

/**
 * Created by Teatree on 6/14/2015.
 */
public abstract class BugController {

    protected Overlap2DStage stage;

    protected CompositeItem item;
    protected SpriterActor spriterActor;

    protected int points;

    public Random rand = new Random();

    protected Rectangle boundsRect = new Rectangle();

    protected float velocity = 0;
    public float startYPosition;

    public abstract CompositeItem getCompositeItem();

    public boolean isOutOfBounds(){
        FlowerController flowerController = ((GameStage) stage).flowerController;
        if (boundsRect.getX() >= flowerController.headBoundsRect.getX()+flowerController.headBoundsRect.getWidth()+100){
            return true;
        }
        return false;
    }

    public void updateRect() {
        boundsRect.x = (int)item.getX();
        boundsRect.y = (int)item.getY();
        boundsRect.width = (int)item.getWidth()/2;
        boundsRect.height = (int)item.getHeight()/2;
    }

    public Rectangle getBoundsRectangle() {
        updateRect();
        return boundsRect;
    }

    public int getPoints(){
        return points;
    }
}
