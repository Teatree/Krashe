package game.actors.controllers;

import com.uwsoft.editor.renderer.actor.CompositeItem;
import com.uwsoft.editor.renderer.script.IScript;

/**
 * Created by MainUser on 12/07/2015.
 */
public class UmbrellaController implements IScript{
    private CompositeItem item;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private float percent;
    private int alignment;

    @Override
    public void init(CompositeItem item) {
        this.item = item;
        startX = 1900;
        startY = 100;
        endX = 100;
        endY = 100;
        percent = 0.5f;
        alignment = 0;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void act(float delta) {
        item.setPosition(startX + (endX - startX) * percent,
                startY + (endY - startY) * percent * percent,
                alignment);
    }
}
