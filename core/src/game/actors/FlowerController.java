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

/**
 * Created by MainUser on 07/06/2015.
 */
public class FlowerController implements IScript {

    private Overlap2DStage stage;

    private CompositeItem item;

    public SpriterActor spriterActor;
    public SpriterActor spriterActor2;

    public FlowerController(Overlap2DStage stage){ this.stage = stage; }

    @Override
    public void init(CompositeItem item) {
        this.item = item;
        if(spriterActor != null) {
            spriterActor.setVisible(false);
        }
        item.setX(Gdx.graphics.getWidth() - 200);
        item.setY(0);
        item.setOrigin(item.getWidth()/2, 0);
    }

    public void addMovementAction() {
        item.addAction(Actions.sequence(
                Actions.moveTo(Gdx.graphics.getWidth() - 100,
                        Gdx.graphics.getHeight() - 100, 0.2f),
                Actions.moveTo(Gdx.graphics.getWidth() - 200, 0, 0.2f)));
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isTouched()){
            addMovementAction();
            spriterActor2.setVisible(false);
            spriterActor.setVisible(true);
        }else{
            spriterActor.setVisible(false);
            spriterActor2.setVisible(true);
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
