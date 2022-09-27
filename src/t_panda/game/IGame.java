package t_panda.game;

import java.awt.Color;
import java.awt.image.ImageObserver;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import t_panda.game.event.ChangeSceneListener;
import t_panda.game.event.GameDrawListener;

/**
 * ゲームの機能を実装するインターフェイス
 */
public interface IGame<SCENE extends Enum<SCENE>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> extends IUpdatable<VKEYPAD>, ISceneChanger<SCENE>, IDrawable, Runnable, ImageObserver, Serializable {
    /**
     * 透明色ARGB(0,0,0,0)
     */
    static final Color TRANSEPARENT = new Color(0,0,0,0);

    /**
     * ゲーム画面イメージを取得します。
     * @return ゲーム画面イメージ
     */
    BufferedImage getImage();

    /**
     * マウスの現在X座標をセットします
     * @param x マウスの現在X座標
     */
    void setMouseCurrentX(int x);
    /**
     * マウスの現在Y座標をセットします
     * @param y マウスの現在Y座標
     */
    void setMouseCurrentY(int y);
    /**
     * マウスがクリックされたときに呼び出します。
     * @param button マウスボタンのIDです。
     * @param x クリックされたときのX座標
     * @see java.awt.event.MouseEvent
     */
    void setMouseClickX(int button, int x);
    /**
     * マウスがクリックされたときに呼び出します。
     * @param button マウスボタンのIDです。
     * @param y クリックされたときのY座標
     * @see java.awt.event.MouseEvent
     */
    void setMouseClickY(int button, int y);
    /**
     * マウスボタンが離されたときに呼び出します。
     * @param button マウスボタンのIDです。
     * @param x ボタンが離されたときのX座標
     * @see java.awt.event.MouseEvent
     */
    void setMouseReleaseX(int button, int x);
    /**
     * マウスボタンが離されたときに呼び出します。
     * @param button マウスボタンのIDです。
     * @param y ボタンが離されたときのY座標
     * @see java.awt.event.MouseEvent
     */
    void setMouseReleaseY(int button, int y);
    /**
     * 実際のマウス座標をゲーム座標にスケーリングする時の倍率を設定します。
     * @param f 実際のマウス座標をゲーム座標にスケーリングする時の倍率
     */
    void setMouseInputScale(float f);
    /**
     * キーが押されたときに呼び出します。
     * @param keyCode キーコード
     * @see java.awt.event.KeyEvent
     */
    void pressKey(int keyCode);
    /**
     * キーが離されたときに呼び出します。
     * @param keyCode キーコード
     * @see java.awt.event.KeyEvent
     */
    void releaseKey(int keyCode);
    /**
     * ゲームスレッドを停止してゲームを終了します
     */
    void requestGameThreadStop();
    /**
     * ゲームスレッドを一時停止するかどうかを設定します。
     * @param b ゲームスレッドを一時停止するかどうか
     */
    void requestSetGameThreadPouse(boolean b);

    /**
     * 現在のシーンを返します。
     * @return 現在のシーン
     */
    IScene<SCENE, ?, VKEYPAD, ?, ?> getCurrentScene();
    /**
     * ゲームのFpsを取得します。
     * @return ゲームのFps
     */
    int getFps();
    /**
     * ゲームの解像度横幅を取得します。
     * @return ゲームの解像度横幅
     */
    int getGameWidth();
    /**
     * ゲームの解像度縦幅を取得します。
     * @return ゲームの解像度縦幅
     */
    int getGameHeight();
    /**
     * {@link #run()}が呼び出されてからのフレーム数を取得します。
     * @return  {@link #run()}が呼び出されてからのフレーム数
     */
    long getFrameCount();

    /**
     * ゲームのFpsをセットします
     * @param fps ゲームのFps
     */
    void setFps(int fps);

    /**
     * シーンが切り替わった時に発生するイベントのリスナを追加します。
     * @param listener シーンが切り替わった時に発生するイベントのリスナ
     */
    void addChangeSceneListener(ChangeSceneListener<SCENE> listener);
    /**
     * ゲーム描画イベントを受け取るリスナーを追加します。
     * @param listener ゲーム描画イベントを受けとるリスナー。
     */
    void addGameDrawListener(GameDrawListener listener);
    /**
     * シーンが切り替わった時に発生するイベントのリスナを削除します。
     * @param listener シーンが切り替わった時に発生するイベントのリスナ
     */
    void removeChangeSceneListener(ChangeSceneListener<SCENE> listener);

    /**
     * IGameオブジェクトを生成する機能を提供します。
     */
    public interface IBuilder<SCENE extends Enum<SCENE>, VKEYPAD extends Enum<VKEYPAD> & IKeyCodeGetable> {
        /**
         * ビルダーをビルドしてGameオブジェクトを生成します。
         * @return ビルダーをビルドして生成したIGameオブジェクト
         */
        IGame<SCENE, VKEYPAD> build();
    }
}
