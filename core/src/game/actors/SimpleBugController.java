package game.actors;

import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.stages.GameStage;

import java.util.Random;

/**
 * Created by NastyaJoe on n/n/2015.
 */
public class SimpleBugController extends BugController implements IScript  {

    private int xCoefficient = 90;
    private int yCoefficient = 75;

    public SimpleBugController(Overlap2DStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item = item;
        boundsRect = new Rectangle();

        spriterActor = item.getSpriterActorById("chargerBug");

        item.setOrigin(item.getWidth() / 2, 0);
    }

    @Override
    public void act(float delta) {
//        counter++;
        float moveCoefficient = (-(float) Math.sin(item.getX() / xCoefficient) * yCoefficient);

        if(!((GameStage)stage).isGameOver()) {
            updateRect();
            item.setY(startYPosition + moveCoefficient);
            System.out.println("sin are fun: " + moveCoefficient);
            item.setX(item.getX() + velocity);
            velocity += delta * 0.4;
        }
    }

//    /*

    @Override
    public void updateRect() {
        boundsRect.x = (int)item.getX();
        boundsRect.y = (int)item.getY();
        boundsRect.width = (int)item.getWidth();
        boundsRect.height = (int)item.getHeight();

//        stage.getActors().get(1).setBounds(boundsRect.getX(), boundsRect.getY(), boundsRect.getWidth(), boundsRect.getHeight());
    }

    @Override
    public Rectangle getBoundsRectangle() {
        updateRect();
        return boundsRect;
    }

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