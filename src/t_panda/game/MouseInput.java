package t_panda.game;

import java.io.Serializable;

/**
 * マウス入力を保持するクラス。取得される座標は、実際の座標ではなく、ゲーム画面が大きくなっても、座標の値は変わりません。
 */
public class MouseInput implements IMouseInput, Serializable {
    /**
     * クリックされて無い時に返す座標の値
     * @see #getClickX(MouseButtons)
     * @see #getClickY(MouseButtons)
     */
    public static final int NOT_CLICK = -1;
    /**
     * マウスボタンが離されて無い時に返す座標の値
     * @see #getReleaseX(MouseButtons)
     * @see #getReleaseY(MouseButtons)
     */
    public static final int NOT_RELEASE = -1;

    /**  */ private static final int RELEASE_IDX = 0;
    /**  */ private static final int CLICK_IDX = 1;
    /**  */ private static final int CURRENT_IDX = 2;
    /**  */ private static final int X_IDX = 0;
    /**  */ private static final int Y_IDX = 1;

    /**  */ private float scale = 1.0f;
    /**  */ private int[][][] state;
    MouseInput(){
        state = new int[3][][];
        state[MouseInput.RELEASE_IDX] = new int[MouseButtons.values().length][2];
        state[MouseInput.CLICK_IDX]   = new int[MouseButtons.values().length][2];
        state[MouseInput.CURRENT_IDX] = new int[1][2];
    }

    @Override
    public int getReleaseX(MouseButtons button) { return state[MouseInput.RELEASE_IDX][button.getButtonId()][MouseInput.X_IDX]; }
    @Override
    public int getReleaseY(MouseButtons button) { return state[MouseInput.RELEASE_IDX][button.getButtonId()][MouseInput.Y_IDX]; }
    @Override
    public int getClickX(MouseButtons button) { return state[MouseInput.CLICK_IDX][button.getButtonId()][MouseInput.X_IDX]; }
    @Override
    public int getClickY(MouseButtons button) { return state[MouseInput.CLICK_IDX][button.getButtonId()][MouseInput.Y_IDX]; }
    @Override
    public int getCurrentX() { return state[MouseInput.CURRENT_IDX][0][MouseInput.X_IDX]; }
    @Override
    public int getCurrentY() { return state[MouseInput.CURRENT_IDX][0][MouseInput.Y_IDX]; }
    @Override
    public boolean isClick(MouseButtons button) { return state[MouseInput.CLICK_IDX][button.getButtonId()][MouseInput.X_IDX] != -1; }
    @Override
    public boolean isRelease(MouseButtons button) { return state[MouseInput.RELEASE_IDX][button.getButtonId()][MouseInput.X_IDX] != -1; }

    /**
     * 実際の描画サイズと、ゲームのサイズとのスケールを設定します。設定する値は、実際に入力された値です。
     * @param scale 実際の描画サイズと、ゲームのサイズとのスケール
     */
    public void setScale(float scale) { this.scale = scale; }
    /**
     * 指定されたマウスボタンが離されたときのX座標を設定します。設定する値は、実際に入力された値です。
     * @param button_id マウスボタンのIDです。
     * @param x ボタンが離されたときのX座標
     * @see java.awt.event.MouseEvent
     */
    public void setReleaseX(int button_id, int x) { state[MouseInput.RELEASE_IDX][button_id][MouseInput.X_IDX] = (int)(x*scale); }
    /**
     * 指定されたマウスボタンが離されたときのY座標を設定します。設定する値は、実際に入力された値です。
     * @param button_id マウスボタンのIDです。
     * @param y ボタンが離されたときのY座標
     * @see java.awt.event.MouseEvent
     */
    public void setReleaseY(int button_id, int y) { state[MouseInput.RELEASE_IDX][button_id][MouseInput.Y_IDX] = (int)(y*scale); }
    /**
     * マウスがクリックされたときにのX座標を設定します。設定する値は、実際に入力された値です。
     * @param button_id マウスボタンのIDです。
     * @param x クリックされたときのX座標
     * @see java.awt.event.MouseEvent
     */
    public void setClickX(int button_id, int x) { state[MouseInput.CLICK_IDX][button_id][MouseInput.X_IDX] = (int)(x*scale); }
    /**
     * マウスがクリックされたときにのY座標を設定します。設定する値は、実際に入力された値です。
     * @param button_id マウスボタンのIDです。
     * @param y クリックされたときのY座標
     * @see java.awt.event.MouseEvent
     */
    public void setClickY(int button_id, int y) { state[MouseInput.CLICK_IDX][button_id][MouseInput.Y_IDX] = (int)(y*scale); }
    /**
     * マウスの現在X座標を設定します。設定する値は、実際に入力された値です。
     * @param x マウスの現在X座標
     */
    public void setCurrentX(int x) { state[MouseInput.CURRENT_IDX][0][MouseInput.X_IDX] = (int)(x*scale); }
    /**
     * マウスの現在Y座標を設定します。設定する値は、実際に入力された値です。
     * @param y マウスの現在Y座標
     */
    public void setCurrentY(int y) { state[MouseInput.CURRENT_IDX][0][MouseInput.Y_IDX] = (int)(y*scale); }
}
