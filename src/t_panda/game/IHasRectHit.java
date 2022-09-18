package t_panda.game;

/**
 * 矩形のあたり判定機能を提供するinterface
 */
public interface IHasRectHit {
    /**
     * 自身のあたり判定の中央X座標を取得します
     * @return 自身のあたり判定の中央X座標
     */
    int getHitCenterX();
    /**
     * 自身のあたり判定の中央Y座標を取得します
     * @return 自身のあたり判定の中央Y座標
     */
    int getHitCenterY();
    /**
     * 自身のあたり判定の中央X座標をセット
     * @param x 自身のあたり判定の中央X座標
     */
    void setHitCenterX(int x);
    /**
     * 自身のあたり判定の中央Y座標をセット
     * @param y 自身のあたり判定の中央Y座標
     */
    void setHitCenterY(int y);
    /**
     * 指定された矩形範囲が自身の当たり判定矩形に衝突しているかを判定します
     * @param centerX 衝突を判定したい矩形の中心X座標
     * @param centerY 衝突を判定したい矩形の中心Y座標
     * @param width  衝突を判定したい矩形の横幅
     * @param height  衝突を判定したい矩形の縦幅
     * @return 指定された矩形範囲が自身の当たり判定矩形に衝突しているか
     */
    default boolean isHit(int centerX, int centerY, int width, int height) {
        if(getHitLeft()  > centerX+(width>>1)) return false;
        if(getHitRight() < centerX-(width>>1)) return false;
        if(getHitTop()   > centerY+(height>>1)) return false;
        if(getHitBottom()< centerY-(height>>1)) return false;
        return true;
    }
    /**
     * 指定された点が自身の当たり判定矩形に衝突しているかを判定します
     * @param x 衝突を判定したいX座標
     * @param y 衝突を判定したいY座標
     * @return 指定された点が自身の当たり判定矩形に衝突しているか
     */
    default boolean isHit(int x, int y) {
        return getHitLeft()   <= x && getHitRight() >= x &&
               getHitBottom() <= y && getHitTop()   >= y;
    }
    /**
     * 指定された矩形当たり判定を持つオブジェクトと衝突したかを判定します
     * @param other 衝突を判定したい矩形当たり判定を持つオブジェクト
     * @return 指定された矩形当たり判定を持つオブジェクトが自身と衝突したか
     */
    default boolean isHit(IHasRectHit other) {
        return this.isHit(other.getHitCenterX(), other.getHitCenterY(), other.getHitWidth(), other.getHitHeight());
    }
    /**
     * 当たり判定矩形範囲左辺X座標を取得します。{@link #getHitCenterX()} - ({@link #getHitWidth()} >> 1)
     * @return 当たり判定矩形範囲左辺X座標
     */
    default int getHitLeft()  { return getHitCenterX()-(getHitWidth()>>1); }
    /**
     * 当たり判定矩形範囲上辺Y座標を取得します。{@link #getHitCenterY()} - ({@link #getHitHeight()} >> 1)
     * @return 当たり判定矩形範囲上辺Y座標
     */
    default int getHitTop()   { return getHitCenterY()-(getHitHeight()>>1); }
    /**
     * 当たり判定矩形範囲右辺X座標を取得します。{@link #getHitCenterX()} + ({@link #getHitWidth()} >> 1)
     * @return 当たり判定矩形範囲右辺X座標
     */
    default int getHitRight() { return getHitCenterX()+(getHitWidth()>>1); }
    /**
     * 当たり判定矩形範囲下辺Y座標を取得します。{@link #getHitCenterY()} + ({@link #getHitHeight()} >> 1)
     * @return 当たり判定矩形範囲下辺Y座標
     */
    default int getHitBottom(){ return getHitCenterY()+(getHitHeight()>>1); }
    /**
     * 自身の当たり判定矩形範囲横幅を取得します。
     * @return 自身の当たり判定矩形範囲横幅
     */
    int getHitWidth();
    /**
     * 自身の当たり判定矩形範囲縦幅を取得します。
     * @return 自身の当たり判定矩形範囲縦幅
     */
    int getHitHeight();
}
