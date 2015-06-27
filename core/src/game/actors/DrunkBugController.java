package game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.script.IScript;
import game.stages.GameStage;

/**
 * Created by CyberJoe on 10/18/2014.
 */
public class DrunkBugController extends BugController implements IScript  {

    public DrunkBugController(Overlap2DStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item = item;
        boundsRect = new Rectangle();

//        startYPosition= MathUtils.random(200, Gdx.graphics.getHeight() - 100);
//        item.setX(0);
//        item.setY(startYPosition -100);

        spriterActor = item.getSpriterActorById("drunkBug");

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

    }
}