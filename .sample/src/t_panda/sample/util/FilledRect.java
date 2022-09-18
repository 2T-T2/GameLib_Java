package t_panda.sample.util;

import java.awt.Color;
import java.awt.Graphics;

import t_panda.game.Flag32;
import t_panda.game.GObject;
import t_panda.game.IFlag;
import t_panda.sample.Defined.*;

public class FilledRect extends GObject<Scenes, Tag, Key> {
    private Color myColor;

    public FilledRect(int centerX, int centerY, int width, int height, IFlag<Tag> tags) {
        super(centerX, centerY, centerX, centerY, width, height, width, height, tags.set(Tag.RECT));
        myColor = Color.red;
    }
    public FilledRect(int centerX, int centerY, int width, int height) {
        this(centerX, centerY, width, height, new Flag32<>());
    }
    @Override
    protected void drawToObjImage(Graphics g) {
        g.setColor(myColor);
        g.fillRect(0, 0, getDrawWidth(), getDrawHeight());
    }
    public void setColor(Color color) { this.myColor = color; }
}
