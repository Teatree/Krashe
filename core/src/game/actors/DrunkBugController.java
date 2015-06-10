package game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.actor.SpriterActor;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.script.IScript;

/**
 * Created by CyberJoe on 10/18/2014.
 */
public class DrunkBugController implements IScript {

    private Overlap2DStage stage;

    private CompositeItem item;
    private SpriterActor spriterActor;

    private float velocity = 0;
    private float startYPosition;

    public DrunkBugController(Overlap2DStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item = item;
        startYPosition= MathUtils.random(200, Gdx.graphics.getHeight() - 100);
        item.setX(0);
        item.setY(startYPosition -100);

        spriterActor = item.getSpriterActorById("drunkBug");

        item.setOrigin(item.getWidth() / 2, 0);
    }

    @Override
    public void act(float delta) {
        item.setY(startYPosition + (-(float) Math.cos(item.getX() / 20) * 75));
        item.setX(item.getX() + velocity);
        velocity+=delta*0.02;
    }

//    /*
//    Ray cast down, and if collision is happening stop player and reposition to closest point of collision
//     */
//    private void checkForCollisions() {
//        // Ray size is the exact size of the deltaY change we plan for this frame
//        float raySize = -(verticalSpeed+Gdx.graphics.getDeltaTime())*Gdx.graphics.getDeltaTime();
//
//        // only check for collisions when moving down
//        if(verticalSpeed >= 0) return;
//
//        // Vectors of ray from middle bottom
//        Vector2 rayFrom = new Vector2((item.getX()+item.getWidth()/2)*PhysicsBodyLoader.SCALE, item.getY()*PhysicsBodyLoader.SCALE);
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