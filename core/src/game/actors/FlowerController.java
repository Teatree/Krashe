package game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.script.IScript;
import static game.utils.GlobalConstants.POINT_TRAVEL;
import com.badlogic.gdx.utils.Timer.Task;

import java.util.Timer;

/**
 * Created by MainUser on 07/06/2015.
 */
public class FlowerController implements IScript {

    private Overlap2DStage stage;

    private CompositeItem item;

    private boolean isMovingUp = false;

    public SpriterActor saIdle;
    public SpriterActor saClose;
    public SpriterActor saOpen;
    public Image itemHeadImg;
    public Image itemPeduncleImg;


    public FlowerController(Overlap2DStage stage){ this.stage = stage; }

    @Override
    public void init(CompositeItem item) {
        this.item = item;

        item.setX(Gdx.graphics.getWidth() - 200);
        item.setY(0);
        item.setOrigin(item.getWidth()/2, 0);
    }

    public void addMovementActionUp() {
        item.addAction(
                Actions.sequence(
                        Actions.moveTo(1802, POINT_TRAVEL, 0.7f)));
    }

    public void addMovementActionDown() {
        item.addAction(
                Actions.sequence(
                        Actions.moveTo(1802, -585, 0.7f)));
    }
    @Override
    public void act(float delta) {

        if (Gdx.input.isTouched() && !isMovingUp) {
            isMovingUp = true;
            saClose.setAnimation(1);
        }

        if (!isMovingUp && item.getY() >= POINT_TRAVEL-20){  // WRONG, you are checking Y against Width! numbnuts
            addMovementActionDown();
            saIdle.setVisible(true);
            saClose.setVisible(false);

            itemHeadImg.setVisible(false);
            itemPeduncleImg.setVisible(false);
        }

        if (isMovingUp) {
            addMovementActionUp();
            saIdle.setVisible(false);
            saClose.setVisible(false);

            itemHeadImg.setVisible(true);
            itemPeduncleImg.setVisible(true);
            if (item.getY() > POINT_TRAVEL-20) {
                isMovingUp = false;
            }
        }

    }


    private void checkForCollisions() {
        // Vectors of ray from middle bottom
        Vector2 rayFrom = new Vector2((item.getX()+item.getWidth()/2)* PhysicsBodyLoader.SCALE, item.getY()*PhysicsBodyLoader.SCALE);
        Vector2 rayTo = new Vector2((item.getX()+item.getWidth()/2)*PhysicsBodyLoader.SCALE, (item.getY())*PhysicsBodyLoader.SCALE);

        // Cast the ray
        stage.getWorld().rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                // reposition player slightly upper the collision point
                item.setY(point.y/PhysicsBodyLoader.SCALE+0.1f);

                return 0;
            }
        }, rayFrom, rayTo);
    }

    @Override
    public void dispose() {

    }


}
