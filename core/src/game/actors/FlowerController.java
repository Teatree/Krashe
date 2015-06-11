package game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.script.IScript;
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
                        Actions.moveTo(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 100, 0.7f)));
    }

    public void addMovementActionDown() {
        item.addAction(
                Actions.sequence(
                        Actions.moveTo(Gdx.graphics.getWidth() - 200, 0, 0.7f)));
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isTouched() && !isMovingUp) {
            isMovingUp = true;
            saIdle.setAnimation(0);
        }

        if (isMovingUp) {
            addMovementActionUp();
            saClose.setVisible(false);
            saIdle.setVisible(true);
            if (item.getY() > (Gdx.graphics.getHeight() - 120)) {
                isMovingUp = false;
            }
        }

        if (!isMovingUp && item.getY() != (Gdx.graphics.getWidth() - 100)){  // WRONG, you are checking Y again Width! numbnuts
            addMovementActionDown();
            saClose.setVisible(true);
            saIdle.setVisible(false);
            saIdle.setAnimation(0);
        }
    }


//    private void checkForCollisions() {
//        // Ray size is the exact size of the deltaY change we plan for this frame
//        float raySize = -(verticalSpeed+ Gdx.graphics.getDeltaTime())*Gdx.graphics.getDeltaTime();
//
//        // only check for collisions when moving down
//        if(verticalSpeed >= 0) return;
//
//        // Vectors of ray from middle bottom
//        Vector2 rayFrom = new Vector2((item.getX()+item.getWidth()/2)* PhysicsBodyLoader.SCALE, item.getY()*PhysicsBodyLoader.SCALE);
//        Vector2 rayTo = new Vector2((item.getX()+item.getWidth()/2)*PhysicsBodyLoader.SCALE, (item.getY() - raySize)*PhysicsBodyLoader.SCALE);
//
//        // Cast the ray
//        stage.getWorld().rayCast(new RayCastCallback() {
//            @Override
//            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
//                // Stop the player
//                verticalSpeed = 0;
//
//                // reposition player slightly upper the collision point
//                item.setY(point.y/PhysicsBodyLoader.SCALE+0.1f);
//
//                // make sure it is grounded, to allow jumping again
//                isGrounded = true;
//
//                return 0;
//            }
//        }, rayFrom, rayTo);
//    }

    @Override
    public void dispose() {

    }


}
