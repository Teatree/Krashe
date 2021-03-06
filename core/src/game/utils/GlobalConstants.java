package game.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by Teatree on 6/14/2015.
 */
public abstract class GlobalConstants {
    public static final int ANGERED_BEES_MODE_DURATION = 3000;

    public static final int DANDELION_GROWING_DURATION = 200;
    public static final int DANDELION_IDLE_DURATION = 200;
    public static final int DANDELION_UMBRELLA_DUYING_POINT = 200;
    public static final int DANDELION_DUYING_DURATION = 400;
    public static final int DANDELION_SPAWN_CHANCE_MAX = 2000;
    public static final int DANDELION_SPAWN_CHANCE_MIN = 1500;

    public static final int COCOON_SPAWNING_DURATION = 200;
    public static final int COCOON_HIT_DURATION = 40;
    public static final int COCOON_HEALTH = 20;
    public static final int COCOON_SPAWN_MAX = 2000;
    public static final int COCOON_SPAWN_MIN = 1500;

    public static final int BEE_SPAWN_INTERVAL_ANGERED = 50;
    public static final int BEE_SPAWN_INTERVAL_REGULAR = 200;

    public static long BEST_SCORE = 0;

    public static final int DEFAULT_MAX_HP = 2;

    public static String CUR_SCREEN = "MENU";

    public static int POINT_TRAVEL = Gdx.graphics.getHeight()-685;

}
