package t_panda.sample.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import t_panda.game.Flag32;
import t_panda.game.GObject;
import t_panda.game.IFlag;
import t_panda.sample.Defined.*;

public class Text extends GObject<Scenes, Tag, Key>  {
    public enum HAlign { LEFT, CENTER, RIGHT }
    public enum VAlign { TOP_1, TOP_2, CENTER_1, CENTER_2, BOTTOM_1, BOTTOM_2, BOTTOM_3 }

    private Color myCol;
    private Font font;
    private String text;
    private HAlign hAling;
    private VAlign vAling;

    public Text(String text, int centerX, int centerY, int width, int height, IFlag<Tag> tags) {
        super(centerX, centerY, centerX, centerY, width, height, width, height, tags.set(Tag.TEXT));
        this.myCol = Color.black;
        this.font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        this.hAling = HAlign.CENTER;
        this.vAling = VAlign.CENTER_1;
        this.text = text;
    }
    public Text(String text, int centerX, int centerY, int width, int height) {
        this(text, centerX, centerY, width, height, new Flag32<>());
    }

    @Override
    protected void drawToObjImage(Graphics g) {
        g.setColor(this.myCol);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int x = calcTextDrawX(fm);;
        int y = calcTextDrawY(fm);
        g.drawString(this.text, x, y);
    }
    protected int calcTextDrawX(FontMetrics fm) {
        switch (hAling) {
            case LEFT:
                return 0;
            case CENTER:
                return (getDrawWidth()>>1) - (fm.stringWidth(this.text) >> 1);
            default:
                return 0;
        }
    }
    protected int calcTextDrawY(FontMetrics fm) {
        switch (vAling) {
            case TOP_1:
                return getDrawTop() + fm.getAscent();
            case TOP_2:
                return getDrawTop() + fm.getAscent()+fm.getLeading();
            case CENTER_1:
                return (getDrawHeight()>>1) + (fm.getAscent() >> 1);
            case CENTER_2:
                return (getDrawHeight()>>1) + ((fm.getAscent()+fm.getLeading()) >> 1);
            case BOTTOM_1:
                return getDrawBottom() - fm.getDescent();
            case BOTTOM_2:
                return getDrawBottom() - fm.getDescent()-fm.getLeading();
            default:
                return getDrawBottom();
        }
    }

    public void setText(String text) { this.text = text; }
    public void setColor(Color col) { this.myCol = col; }
    public void setFont(Font font) { this.font = font; }
    public void setVAlign(VAlign vAlign) { this.vAling = vAlign; }
    public void setHAlign(HAlign hAlign) { this.hAling = hAlign; }
    public String getText() { return text; }
}
