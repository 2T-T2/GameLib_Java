package t_panda.game;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * {@link #draw(Graphics)}を実装します
 */
public interface IDrawable {
    /**
     * 描画関数。
     * @param g 描画先グラフィクス
     */
    void draw(Graphics g);
    /**
     * 自身のイメージからGraphicsオブジェクトを取得します
     * @return Graphicsオブジェクト
     */
    Graphics getGraphics();
    /**
     * 自身のイメージからGraphics2Dオブジェクトを生成します。これはサポートをしない場合があります
     * @return Graphics2Dオブジェクト
     */
    Graphics2D createGraphics();
    /**
     * 自身の描画範囲矩形の中心X座標を取得します
     * @return 自身の描画範囲矩形の中心X座標
     */
    int getDrawCenterX();
    /**
     * 自身の描画範囲矩形の中心Y座標を取得します
     * @return 自身の描画範囲矩形の中心Y座標
     */
    int getDrawCenterY();
    /**
     * 自身の描画範囲矩形の中心X座標をセット
     * @param x 自身の描画範囲矩形の中心X座標
     */
    void setDrawCenterX(int x);
    /**
     * 自身の描画範囲矩形の中心Y座標をセット
     * @param y 自身の描画範囲矩形の中心Y座標
     */
    void setDrawCenterY(int y);
    /**
     * 自身の描画矩形範囲横幅を取得します。
     * @return 自身の描画矩形範囲横幅
     */
    int getDrawWidth();
    /**
     * 自身の描画矩形範囲縦幅を取得します。
     * @return 自身の描画矩形範囲縦幅
     */
    int getDrawHeight();
    /**
     * 描画矩形範囲左辺X座標を取得します。{@link #getDrawCenterX()} - ({@link #getDrawWidth()} >> 1)
     * @return 描画矩形範囲左辺X座標
     */
    default int getDrawLeft()  { return getDrawCenterX()-(getDrawWidth()>>1); }
    /**
     * 描画矩形範囲上辺Y座標を取得します。{@link #getDrawCenterY()} - ({@link #getDrawHeight()} >> 1)
     * @return 描画矩形範囲上辺Y座標
     */
    default int getDrawTop()   { return getDrawCenterY()-(getDrawHeight()>>1); }
    /**
     * 描画矩形範囲右辺X座標を取得します。{@link #getDrawCenterX()} + ({@link #getDrawWidth()} >> 1)
     * @return 描画矩形範囲右辺X座標
     */
    default int getDrawRight() { return getDrawCenterX()+(getDrawWidth()>>1); }
    /**
     * 描画矩形範囲下辺Y座標を取得します。{@link #getDrawCenterY()} + ({@link #getDrawHeight()} >> 1)
     * @return 描画矩形範囲下辺Y座標
     */
    default int getDrawBottom(){ return getDrawCenterY()+(getDrawHeight()>>1); }
}
