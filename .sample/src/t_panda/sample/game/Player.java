package t_panda.sample.game;

import java.awt.*;

import t_panda.game.Flag32;
import t_panda.game.IKeyInput;
import t_panda.game.MouseInput;
import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Tag;
import t_panda.sample.util.RoundFilledRect;

class Player extends RoundFilledRect {
    private int spd = 8;

    Player(int centerX, int centerY, int width, int height) {
        super(centerX, centerY, width, height, new Flag32<Tag>().set(Tag.PLAYER));
        setColor(Color.cyan);
        setArcHeight(10);
        setArcWidth(10);
    }

    @Override
    public void update(IKeyInput<Key> keyInput, MouseInput mouseInput) {
        super.update(keyInput, mouseInput);
        if(keyInput.getKey(Key.LEFT)) {
            move(-spd, 0);
            if(getHitLeft() < 0)
                setCenterX(getHitWidth()>>1);
        }
        if(keyInput.getKey(Key.RIGHT)) {
            move(spd, 0);
            if(getHitRight() > getSceneWidth())
                setCenterX(getSceneWidth() - (getHitWidth()>>1));
        }
    }
}
