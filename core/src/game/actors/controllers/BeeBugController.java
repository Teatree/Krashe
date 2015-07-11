package game.actors.controllers;

import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.stages.GameStage;

/**
 * Created by NastyaJoe on n/n/2015.
 */
public class BeeBugController extends BugController implements IScript  {

    public BeeBugController(Overlap2DStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item = item;
        boundsRect = new Rectangle();
        points = 15;

//        startYPosition= MathUtils.random(200, Gdx.graphics.getHeight() - 100);
//        item.setX(0);
//        item.setY(startYPosition -100);

        spriterActor = item.getSpriterActorById("beeBug");

        item.setOrigin(item.getWidth() / 2, 0);
    }

    @Override
    public void act(float delta) {
        if(!((GameStage)stage).isGameOver()) {
            updateRect();
            item.setY(startYPosition + (-(float) Math.cos(item.getX() / 20) * 75));
            item.setX(item.getX() + velocity);
            velocity += delta * 0.4;
        }
    }

//    /*

   

    @Override
    public CompositeItem getCompositeItem() {
        return item;
    }

    @Override
    public void dispose() {
        spriterActor.dispose();
        item.dispose();
    }
}