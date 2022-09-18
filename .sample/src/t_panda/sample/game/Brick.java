package t_panda.sample.game;

import java.awt.*;
import java.util.ArrayList;

import t_panda.game.Flag32;
import t_panda.sample.Application;
import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Scenes;
import t_panda.sample.Defined.Tag;
import t_panda.sample.event.ScoreChangeListener;
import t_panda.sample.util.FilledRect;

class Brick extends FilledRect {
    private ArrayList<ScoreChangeListener> scoreChangeListeners;

    Brick(int centerX, int centerY, int width, int height) {
        super(centerX, centerY, width, height, new Flag32<Tag>().set(Tag.BRICK));
        setColor(new Color((int)(Application.r.nextDouble()*0xff0000)+0x00f00f));
        scoreChangeListeners = new ArrayList<>();
    }
    @Override
    public void init(InitArg<Scenes, Tag, Key> initArg) {
        super.init(initArg);
        if(scoreChangeListeners == null)
            scoreChangeListeners = new ArrayList<>();
    }
    @Override
    public void removed() {
        super.removed();
        for (ScoreChangeListener listener : this.scoreChangeListeners) {
            listener.add(100);
        }
        scoreChangeListeners.clear();
        scoreChangeListeners = null;
    }

    void addScoreChangeListener(ScoreChangeListener listener) {
        this.scoreChangeListeners.add(listener);
    }
}
