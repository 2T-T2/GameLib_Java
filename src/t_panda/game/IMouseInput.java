package t_panda.game;

/**
 * マウス入力情報の取得に関する機能て提供します
 */
public interface IMouseInput {
    /**
     * 指定されたマウスボタンが離されたときのX座標
     * @param button 離されたときの座標が知りたいマウスボタン
     * @return マウスボタンが離されたときのX座標
     */
    int getReleaseX(MouseButtons button);
    /**
     * 指定されたマウスボタンが離されたときのY座標
     * @param button 離されたときの座標が知りたいマウスボタン
     * @return マウスボタンが離されたときのY座標
     */
    int getReleaseY(MouseButtons button);
    /**
     * 指定されたマウスボタンがクリックされたときのX座標
     * @param button クリックされたときの座標が知りたいマウスボタン
     * @return マウスボタンがクリックされたときのX座標
     */
    int getClickX(MouseButtons button);
    /**
     * 指定されたマウスボタンがクリックされたときのY座標
     * @param button クリックされたときの座標が知りたいマウスボタン
     * @return マウスボタンがクリックされたときのY座標
     */
    int getClickY(MouseButtons button);
    /**
     * 現在のマウスカーソルのX座標を返します。
     * @return 現在のマウスカーソルのX座標
     */
    int getCurrentX();
    /**
     * 現在のマウスカーソルのY座標を返します。
     * @return 現在のマウスカーソルのY座標
     */
    int getCurrentY();
    /**
     * 指定されたマウスボタンがクリックされたかどうかを判定します
     * @param button クリックされたかどうかを判定するマウスボタン
     * @return 指定されたマウスボタンがクリックされたかどうか
     */
    boolean isClick(MouseButtons button);
    /**
     * 指定されたマウスボタンが離されたかどうかを判定します
     * @param button 離されたかどうかを判定するマウスボタン
     * @return 指定されたマウスボタンが離されたかどうか
     */
    boolean isRelease(MouseButtons button);
}