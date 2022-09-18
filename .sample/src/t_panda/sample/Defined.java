package t_panda.sample;

import static java.awt.event.KeyEvent.*;

import java.awt.Color;
import java.awt.Font;

import t_panda.game.IKeyCodeGetable;

public class Defined {
    public enum Scenes {
        TITLE, GAME, GAMEOVER
    }
    public enum Tag {
        RECT, TEXT, HOLDER, PLAYER, BRICK, CIRCLE, BOLL

    }
    public enum Key implements IKeyCodeGetable {
        LEFT(VK_S), RIGHT(VK_F), UP(VK_E), DOWN(VK_D),
        A(VK_J), B(VK_K), X(VK_I), Y(VK_L), L(VK_U), R(VK_O),
        START(VK_W), SELECT(VK_T)
        ;

        private int keycode;
        private Key(int keycode) { this.keycode = keycode; }
        @Override public int getKeyCode() { return this.keycode; }
        public void setKeyCode(int keycode) { this.keycode = keycode; }
    }
    public static class Fonts {
        public static final Font S_FONT  = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        public static final Font M_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 24);
        public static final Font L_FONT  = new Font(Font.MONOSPACED, Font.PLAIN, 48);
    }
    public static class Colors {
        public static final Color light_black = new Color(32,32,32);
    }
}
