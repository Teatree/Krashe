package game.actors;

import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.actor.CompositeItem;

/**
 * Created by Teatree on 6/14/2015.
 */
public interface BugController {

    void updateRect();
    Rectangle getBoundsRectangle();
    CompositeItem getCompositeItem();
}
