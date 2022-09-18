package t_panda.sample.util;

import java.awt.Color;
import java.awt.Graphics;

import t_panda.game.Flag32;
import t_panda.game.GObject;
import t_panda.sample.Defined.*;

public class RoundStrokeRect extends GObject<Scenes, Tag, Key> {
    private Color myColor;
    private int arcW;
    private int arcH;

    public RoundStrokeRect(int centerX, int centerY, int width, int height, Flag32<Tag> tags) {
        super(centerX, centerY, centerX, centerY, width, height, width, height, tags.set(Tag.RECT));
        myColor = Color.red;
    }
    public RoundStrokeRect(int centerX, int centerY, int width, int height) {
        this(centerX, centerY, width, height, new Flag32<>());
    }
    @Override
    protected void drawToObjImage(Graphics g) {
        g.setColor(myColor);
        g.fillRoundRect(0, 0, getDrawWidth(), getDrawHeight(), arcW, arcH);
    }
    public void setColor(Color color) { this.myColor = color; }
    public void setArcWidth(int arcW) { this.arcW = arcW; }
    public void setArcHeight(int arcH) { this.arcH = arcH; }
}
