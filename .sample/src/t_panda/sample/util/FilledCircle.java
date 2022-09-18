package t_panda.sample.util;

import java.awt.Color;
import java.awt.Graphics;

import t_panda.game.Flag32;
import t_panda.game.GObject;
import t_panda.game.IFlag;
import t_panda.sample.Defined.*;

public class FilledCircle extends GObject<Scenes, Tag, Key> {
    private Color col;

    public FilledCircle(int centerX, int centerY, int r, IFlag<Tag> tags) {
        super(centerX, centerY,centerX, centerY, r, r, r, r, tags.set(Tag.CIRCLE));
        this.col = Color.red;
    }
    public FilledCircle(int centerX, int centerY, int r) {
        this(centerX, centerY, r, new Flag32<>());
    }

    @Override
    protected void drawToObjImage(Graphics g) {
        g.setColor(this.col);
        g.fillOval(0, 0, getDrawWidth(), getDrawHeight());
    }

    public void setColor(Color col) {
        this.col = col;
    }
    public void setCenterX(int cx) {
        setHitCenterX(cx);
        setDrawCenterX(cx);
    }
    public void setCenterY(int cy) {
        setHitCenterY(cy);
        setDrawCenterY(cy);
    }
}
