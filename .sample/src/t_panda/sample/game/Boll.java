package t_panda.sample.game;

import t_panda.game.Flag32;
import t_panda.game.IGObject;
import t_panda.game.IKeyInput;
import t_panda.game.MouseInput;
import t_panda.sample.Defined.Key;
import t_panda.sample.Defined.Scenes;
import t_panda.sample.Defined.Tag;
import t_panda.sample.util.FilledCircle;

class Boll extends FilledCircle {
    private int spdY = -6;
    private int spdX = 6;
    private int preHitLeft, preHitTop;
    private BoundAxis bDirection = null;

    private enum BoundAxis { X, Y, BOTH }

    Boll(int centerX, int centerY, int r) {
        super(centerX, centerY, r, new Flag32<Tag>().set(Tag.BOLL));
    }

    public void setSpdY(int spdY) {
        this.spdY = spdY;
    }

    public int getSpdY() {
        return this.spdY;
    }

    public void setSpdX(int spdX) {
        this.spdX = spdX;
    }

    public int getSpdX() {
        return this.spdX;
    }

    @Override
    public void init(InitArg<Scenes, Tag, Key> initArg) {
        super.init(initArg);
    }
    @Override
    public void update(IKeyInput<Key> keyInput, MouseInput mouseInput) {
        super.update(keyInput, mouseInput);
        boundIfNeeded();
        preHitLeft = getHitLeft();
        preHitTop = getHitTop();
        move(spdX, spdY);
        if(getHitTop() > getSceneHeight()) destroy();
    }

    boolean checkBound(IGObject<Scenes,Tag,Key> object) {
        if(!this.isHit(object)) return false;

        if(object.hasTag(Tag.PLAYER)) {
            setCenterY(object.getHitTop() - (getHitHeight()>>1) + 1);
            bDirection = BoundAxis.Y;

        } else if(object.hasTag(Tag.BRICK)) {
            if(object.getHitLeft() < preHitLeft && object.getHitLeft()+object.getHitWidth() > preHitLeft)
                bDirection = BoundAxis.Y;
            else if (object.getHitTop() < preHitTop && object.getHitTop()+object.getHitHeight() > preHitTop)
                bDirection = BoundAxis.X;
            else
                bDirection = BoundAxis.BOTH;
        }
        return true;
    }

    private void boundIfNeeded() {
        if(getHitTop() < 0) {
            setCenterY(getHitHeight()>>1);
            bDirection = BoundAxis.Y;
        }
        if(getHitLeft() < 0 || getHitRight() > getSceneWidth())
            bDirection = BoundAxis.X;

        if(bDirection == null) return;
        if(bDirection == BoundAxis.X)
            spdX = -spdX;
        else if(bDirection == BoundAxis.Y)
            spdY = -spdY;
        else {
            spdX = -spdX;
            spdY = -spdY;
        }
        bDirection = null;
    }
}
