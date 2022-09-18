package t_panda.game.event;

/**
 * ゲーム描画イベントリスナ
 */
public interface GameDrawListener {
    /**
     * ゲームが描画されたときに呼び出されます。
     * @param e ゲーム描画イベントオブジェクト
     */
    void gameDraw(GameDrawEvent e);
}
