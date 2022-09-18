package t_panda.game;

import java.awt.event.MouseEvent;

/**
 * マウスボタンEnum
 */
public enum MouseButtons {
    /** どれでもない */   NOBUTTON(MouseEvent.NOBUTTON),
    /** たぶん左ボタン */ BUTTON1(MouseEvent.BUTTON1),
    /** たぶん右ボタン */ BUTTON2(MouseEvent.BUTTON2),
    /** マウスホイール */ BUTTON3(MouseEvent.BUTTON3),
    ;
    private final int btnId;
    private MouseButtons(int btnId) { this.btnId = btnId; }
    /**
     * マウスボタンの識別ID(MouseEventで定義された定数)を取得します。
     * @return マウスボタンの識別ID(MouseEventで定義された定数)
     */
    public int getButtonId() { return this.btnId; }
}