package game.actors.controllers;

import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.Overlap2DStage;
import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;
import game.stages.GameStage;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * Created by NastyaJoe on n/n/2015.
 */
public class QueenBeeBugController extends SimpleBugController implements IScript  {

    public QueenBeeBugController(Overlap2DStage stage) {
        super(stage);
        this.stage = stage;
    }

    @Override
    public void init(CompositeItem item) {
        this.item = item;

        points = 100;

        boundsRect = new Rectangle();

        spriterActor = item.getSpriterActorById("drunkBug");

        item.setOrigin(item.getWidth() / 2, 0);
    }
}