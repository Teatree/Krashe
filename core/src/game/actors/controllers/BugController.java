package game.actors.controllers;

import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import game.actors.Flower;
import game.stages.GameStage;

import java.util.Random;

/**
 * Created by Teatree on 6/14/2015.
 */
public  abstract class BugController {

    protected Overlap2DStage stage;

    protected CompositeItem item;
    protected SpriterActor spriterActor;

    public int points;

    public Random rand = new Random();

    protected Rectangle boundsRect = new Rectangle();

    protected float velocity = 0;
    public float startYPosition;

    public abstract void updateRect();
    public abstract Rectangle getBoundsRectangle();
    public abstract CompositeItem getCompositeItem();

    public boolean isOutOfBounds(){
        Flower flower = ((GameStage) stage).flower;
        if (boundsRect.getX() >= flower.getBounds().getX()+flower.getBounds().getWidth()+100){
            return true;
        }
        return false;
    }
}
