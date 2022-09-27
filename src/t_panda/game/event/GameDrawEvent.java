package t_panda.game.event;

import t_panda.game.IGame;

/**
 * ゲームの描画がされた時に呼び出されるイベント。
 */
public class GameDrawEvent {
    private IGame<?,?> drawnGame;

    /**
     * コンストラクタ
     * @param drawnGame 描画がされたゲームオブジェクト
     */
    public GameDrawEvent(IGame<?,?> drawnGame) {
        this.drawnGame = drawnGame;
    }
    /**
     * 描画がされたゲームオブジェクトを取得します。
     * @return 描画がされたゲームオブジェクト
     */
    public IGame<?,?> getDrawnGame() {
        return drawnGame;
    }
}