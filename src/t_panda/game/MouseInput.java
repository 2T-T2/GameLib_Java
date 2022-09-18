package t_panda.game;

import java.io.Serializable;

/**
 * マウス入力を保持するクラス。取得される座標は、実際の座標ではなく、ゲーム画面が大きくなっても、座標の値は変わりません。
 */
public class MouseInput implements Serializable {
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

    /**
     * 指定されたマウスボタンが離されたときのX座標
     * @param button 離されたときの座標が知りたいマウスボタン
     * @return マウスボタンが離されたときのX座標
     */
    public int getReleaseX(MouseButtons button) { return state[MouseInput.RELEASE_IDX][button.getButtonId()][MouseInput.X_IDX]; }
    /**
     * 指定されたマウスボタンが離されたときのY座標
     * @param button 離されたときの座標が知りたいマウスボタン
     * @return マウスボタンが離されたときのY座標
     */
    public int getReleaseY(MouseButtons button) { return state[MouseInput.RELEASE_IDX][button.getButtonId()][MouseInput.Y_IDX]; }
    /**
     * 指定されたマウスボタンがクリックされたときのX座標
     * @param button クリックされたときの座標が知りたいマウスボタン
     * @return マウスボタンがクリックされたときのX座標
     */
    public int getClickX(MouseButtons button) { return state[MouseInput.CLICK_IDX][button.getButtonId()][MouseInput.X_IDX]; }
    /**
     * 指定されたマウスボタンがクリックされたときのY座標
     * @param button クリックされたときの座標が知りたいマウスボタン
     * @return マウスボタンがクリックされたときのY座標
     */
    public int getClickY(MouseButtons button) { return state[MouseInput.CLICK_IDX][button.getButtonId()][MouseInput.Y_IDX]; }
    /**
     * 現在のマウスカーソルのX座標を返します。
     * @return 現在のマウスカーソルのX座標
     */
    public int getCurrentX() { return state[MouseInput.CURRENT_IDX][0][MouseInput.X_IDX]; }
    /**
     * 現在のマウスカーソルのY座標を返します。
     * @return 現在のマウスカーソルのY座標
     */
    public int getCurrentY() { return state[MouseInput.CURRENT_IDX][0][MouseInput.Y_IDX]; }

    /**
     * 指定されたマウスボタンがクリックされたかどうかを判定します
     * @param button クリックされたかどうかを判定するマウスボタン
     * @return 指定されたマウスボタンがクリックされたかどうか
     */
    public boolean isClick(MouseButtons button) { return state[MouseInput.CLICK_IDX][button.getButtonId()][MouseInput.X_IDX] != -1; }
    /**
     * 指定されたマウスボタンが離されたかどうかを判定します
     * @param button 離されたかどうかを判定するマウスボタン
     * @return 指定されたマウスボタンが離されたかどうか
     */
    public boolean isRelease(MouseButtons button) { return state[MouseInput.RELEASE_IDX][button.getButtonId()][MouseInput.X_IDX] != -1; }

    void setScale(float scale) { this.scale = scale; }
    void setReleaseX(int button_id, int value) { state[MouseInput.RELEASE_IDX][button_id][MouseInput.X_IDX] = (int)(value*scale); }
    void setReleaseY(int button_id, int value) { state[MouseInput.RELEASE_IDX][button_id][MouseInput.Y_IDX] = (int)(value*scale); }
    void setClickX(int button_id, int value) { state[MouseInput.CLICK_IDX][button_id][MouseInput.X_IDX] = (int)(value*scale); }
    void setClickY(int button_id, int value) { state[MouseInput.CLICK_IDX][button_id][MouseInput.Y_IDX] = (int)(value*scale); }
    void setCurrentX(int value) { state[MouseInput.CURRENT_IDX][0][MouseInput.X_IDX] = (int)(value*scale); }
    void setCurrentY(int value) { state[MouseInput.CURRENT_IDX][0][MouseInput.Y_IDX] = (int)(value*scale); }
}
