package game.actors;

import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.stages.GameStage;
import sun.java2d.opengl.OGLContext;

/**
 * Created by NastyaJoe on n/n/2015.
 */
public class ChargerBugController extends BugController implements IScript  {

    public static final int MAX_IDLE_COUNT = 600;
    public static final int MIN_IDLE_COUNTER = 290;
    public static final int IDLE_MVMNT_SPEED = 95; // 100
    public static final int PREPARING_MVMNT_SPEED = 32; // 32
    public static final int CHARGING_MVMNT_SPEED = 705;
    public static final int PREPARATION_TIME = 200;

    private State state;
    private int counter = 0;

    public ChargerBugController(Overlap2DStage stage) {
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item = item;
        boundsRect = new Rectangle();

        spriterActor = item.getSpriterActorById("chargerBug");

        item.setOrigin(item.getWidth() / 2, 0);

        state = State.IDLE;
        counter = rand.nextInt(MAX_IDLE_COUNT - MIN_IDLE_COUNTER)+MIN_IDLE_COUNTER;
    }

    @Override
    public void act(float delta) {
        if(!((GameStage)stage).isGameOver()) {
            updateRect();
            counter--;

            // Move
            item.setX(item.getX() + velocity);

            // Idle
            if (state == State.IDLE) {
                velocity = delta * IDLE_MVMNT_SPEED;
                if (counter == 0) {
                    counter = PREPARATION_TIME;
                    state = State.PREPARING;
                }
            }
            // Preparing
            else if (state == State.PREPARING) {
                velocity = delta * PREPARING_MVMNT_SPEED;
                if (counter == 0) {
                    state = State.CHARGING;
                    velocity = delta * CHARGING_MVMNT_SPEED;
                }
            }
            // Charging
            else if (state == State.CHARGING) {
                velocity += delta * 3.4;
            }
        }


//        if(!((GameStage)stage).isGameOver()) {
//            updateRect();
//            item.setY(startYPosition + (-(float) Math.cos(item.getX() / 20) * 75));
//            item.setX(item.getX() + velocity);
//            velocity += delta * 0.4;
//        }

    }

    @Override
    public void updateRect() {
        boundsRect.x = (int)item.getX();
        boundsRect.y = (int)item.getY();
        boundsRect.width = (int)item.getWidth();
        boundsRect.height = (int)item.getHeight();
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

    public enum State{
        IDLE,
        PREPARING,
        CHARGING;
    }
}